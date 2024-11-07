package jp.co.futech.module.insight.service.event.Impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import ft.btp.i18n.annotation.EnableI18nFunctionForMethod;
import jp.co.futech.framework.common.exception.ErrorCode;
import jp.co.futech.framework.common.pojo.PageResult;
import jp.co.futech.framework.mybatis.core.query.LambdaQueryWrapperX;
import jp.co.futech.framework.security.core.LoginUser;
import jp.co.futech.framework.security.core.util.SecurityFrameworkUtils;
import jp.co.futech.module.insight.airflow.EventTaskToAirFlow;
import jp.co.futech.module.insight.check.EventCheck;
import jp.co.futech.module.insight.config.I18nMessagesConfig;
import jp.co.futech.module.insight.controller.admin.event.vo.*;
import jp.co.futech.module.insight.entity.ms.*;
import jp.co.futech.module.insight.mapper.bh.EventTaskMapper;
import jp.co.futech.module.insight.mapper.ms.*;
import jp.co.futech.module.insight.mapper.pg.PgEventTaskMapper;
import jp.co.futech.module.insight.po.req.task.TaskQueryReq;
import jp.co.futech.module.insight.po.req.task.TaskSqlTemplateDataReq;
import jp.co.futech.module.insight.po.req.task.TaskSqlTemplateListReq;
import jp.co.futech.module.insight.po.res.task.FilterSqlVo;
import jp.co.futech.module.insight.service.event.EventTaskService;
import jp.co.futech.module.system.api.user.AdminUserApi;
import jp.co.futech.module.system.api.user.dto.AdminUserRespDTO;
import lombok.Data;
import org.apache.calcite.config.Lex;
import org.apache.calcite.sql.*;
import org.apache.calcite.sql.dialect.PostgresqlSqlDialect;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.pretty.SqlPrettyWriter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

import static jp.co.futech.framework.common.exception.util.ServiceExceptionUtil.exception;
import static jp.co.futech.module.insight.constant.CommonConstant.*;
import static jp.co.futech.module.insight.constant.EventTaskStatusConstant.RUNNING;
import static jp.co.futech.module.insight.constant.SqlTemplateConstant.*;
import static jp.co.futech.module.insight.constant.TimeFormatConstant.YYYYMMDD;
import static jp.co.futech.module.insight.exception.ErrorCodeConstants.*;
import static jp.co.futech.module.insight.utils.DateTimeUtils.getNowDateTimeString;
import static jp.co.futech.module.insight.utils.DateTimeUtils.getNowTimestamp;
import static jp.co.futech.module.insight.utils.SupersetUtils.*;

/**
 *
 */
@Service
public class EventTaskServiceImpl implements EventTaskService {
    private final static Logger logger = LoggerFactory.getLogger(EventTaskServiceImpl.class);

    @Autowired
    private AdminUserApi adminUserApi;
    @Resource
    private EventTaskMapper eventTaskMapper;
    @Resource
    private PgEventTaskMapper pgEventTaskMapper;

    @Resource
    private EventTaskDimMetaInfoMapper eventTaskDimMetaInfoMapper;
    @Resource
    private EventTaskInfoMapper eventTaskInfoMapper;

    @Resource
    private MetaDataService metaDataService;

    @Autowired
    private EventTaskToAirFlow eventTaskToAirFlow;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private EventTaskChartInfoMapper eventTaskChartInfoMapper;

    @Autowired
    protected EventCheck eventCheck;
    @Value("${event.task.pg_database}")
    private String database;
    @Value("${event.task.pg_report_database}")
    private String report_database;
    @Value("${event.task.spset_username}")
    private String spsetUserName;
    @Value("${event.task.spset_password}")
    private String spsetPassword;
    @Value("${event.task.spset_database_id}")
    private int spsetDatabaseId;
    @Value("${event.task.spset_schema}")
    private String spsetSchema;
    @Value("${event.task.spset_url}")
    private String spsetUrl;


    @Override
    public List<TaskSqlTemplateListReq> getSqlTemplate() {
        var getTemplate = metaDataService.selectSqlTemplateMetaData();
        List<TaskSqlTemplateListReq> taskSqlTemplateListReqs = getTemplate.stream()
                .collect(Collectors.groupingBy(EventTaskSqlTemplateEntity::getAggType))
                .entrySet().stream()
                .map(entry -> {
                    List<TaskSqlTemplateDataReq> dataEntities = entry.getValue().stream()
                            .map(item -> new TaskSqlTemplateDataReq(
                                    item.getTemplateType(),
                                    item.getTemplateTypeName(),
                                    item.getTemplateTypeValue()
                            ))
                            .collect(Collectors.toList());
                    EventTaskSqlTemplateEntity firstEntity = entry.getValue().get(0);
                    return new TaskSqlTemplateListReq(
                            firstEntity.getAggType(),
                            firstEntity.getAggTypeName(),
                            dataEntities
                    );
                })
                .collect(Collectors.toList());
        return taskSqlTemplateListReqs;
    }

    @Override
    @Transactional(transactionManager = "msTransactionManager",propagation = Propagation.REQUIRED)
    public String createCustomer(EventTaskVo eventTaskVo) {
        logger.info("自定义创建事件，请求json：" + JSON.toJSONString(eventTaskVo));
        eventCheck.createCheckEventTask(eventTaskVo);
        return saveEventTask(eventTaskVo, TASK_CREATE_CUSTOMER);
    }

    @Override
    @Transactional(transactionManager = "msTransactionManager",propagation = Propagation.REQUIRED)
    public String createAuto(EventTaskVo eventTaskVo) {
        logger.info("自动生成事件，请求json：" + JSON.toJSONString(eventTaskVo));
        eventCheck.createCheckEventTask(eventTaskVo);
        return saveEventTask(eventTaskVo, TASK_CREATE_AUTO);
    }

    public String saveEventTask(EventTaskVo eventTaskVo, String type) {
        String finalQuery = "";
        StringBuilder dimColumnSql = new StringBuilder();
        StringBuilder resColumnSql = new StringBuilder();
        // 创建表sql
        String createTabSql = "";
        // 任务标识名称
        String aliasName = eventTaskVo.getTaskAliasName();
        // 标签名称
        String taskName = eventTaskVo.getTaskName();
        StringBuilder taskResTableName = new StringBuilder();
        taskResTableName.append(aliasName);
        StringBuilder pgDeleteSql = new StringBuilder();
        StringBuilder pgInsertSql = new StringBuilder();
        int isAuto = 0;
        if (type.equals(TASK_CREATE_AUTO)) {
            // 生成执行sql
            //finalQuery = getSelectSql(eventTaskVo, dimColumnSql, resColumnSql);
            finalQuery = getConvertSql(getSelectStandardSql(eventTaskVo, dimColumnSql, resColumnSql), "PostgreSQL");
            // 验证sql是否执行成功,替换时间再发给air
            //tryCatch(eventTaskMapper::explainSyntax, CHECK_EVENT_SQL_FAILED, "EXPLAIN SYNTAX " + finalQuery.toString().replaceAll("[exec_date]", getNowDateTimeString(YYYYMMDD)));
            // 生成创建表DDL
            //createTabSql = createTabDll(taskResTableName.toString(), dimColumnSql, resColumnSql).toString();
            createTabSql = createDdl_to_datasource("postgrel", createStandardTabDll(taskResTableName.toString(), dimColumnSql, resColumnSql).toString());
            // 生成pg数据库的delete和insert的SQL
            pgInsertSql = getPgInsertSql(report_database + taskResTableName.toString(), dimColumnSql, resColumnSql);
            pgDeleteSql = new StringBuilder(getConvertSql(getPgDeleteSql(report_database + taskResTableName.toString()).toString(), "PostgreSQL"));

        } else {
            isAuto = 1;  //自定义sql
            // 生成执行sql
            finalQuery = eventTaskVo.getSelectTaskSql();
            // 验证sql是否执行成功
            tryCatch(eventTaskMapper::explainSyntax, CHECK_EVENT_SQL_FAILED, "EXPLAIN SYNTAX " + finalQuery.toString());
            StringBuilder createDdl = new StringBuilder();
            //createDdl.append(eventTaskVo.getInsertSql()).append(SEMICOLON).append(eventTaskVo.getCreateTableSql());
            createDdl.append(eventTaskVo.getCreateTableSql());
            // 创建表DDL
            createTabSql = createDdl.toString();
            // 验证sql是否执行成功
            validTableName(createTabSql, aliasName);

            String insertSql = eventTaskVo.getInsertTaskSql();
            pgInsertSql.append(insertSql);
            String deleteTaskSql = eventTaskVo.getDeleteTaskSql();
            if (deleteTaskSql != null && deleteTaskSql.length() > 0) {
                pgDeleteSql.append(deleteTaskSql);
            } else {
                pgDeleteSql.append("select 1");
            }
        }
        UUID taskId = UUID.randomUUID();

        Timestamp nowTs = getNowTimestamp();
        // 执行pg数据库SQL
        tryCatch(pgEventTaskMapper::executeDDL, CREATE_TAB_PG_SQL_FAILED, createTabSql.toString());
        // 保存数据
        createEventToMs(eventTaskVo, finalQuery, createTabSql, aliasName, taskName, taskResTableName, taskId, nowTs,dimColumnSql, resColumnSql, isAuto);

        // 发送给airFlow
        try {
            eventTaskToAirFlow.createAirFlow(taskId.toString(), pgDeleteSql.toString(), pgInsertSql.toString(), finalQuery, taskResTableName.toString(), nowTs.toString());
        } catch (Exception e){
            tryCatch(pgEventTaskMapper::executeDDL,DELETE_TAB_PG_SQL_FAILED,getPgDeleteTableSql(taskResTableName.toString()).toString());
            throw e;
        }
        String accessToken = getAccessToken(spsetUrl, spsetUserName, spsetPassword, restTemplate);
        String datasetSql = "select * from " + taskResTableName;
        int datasetId = createDataset(spsetUrl,spsetDatabaseId, spsetSchema, accessToken, datasetSql, taskResTableName.toString(),restTemplate);
        int sliceId = createChart(spsetUrl, accessToken, "table", aliasName, datasetId, restTemplate);
        String supChartUrl = spsetUrl + "/explore/?slice_id=" + sliceId;
        EventTaskChartInfoEntity eventTaskChartInfoEntity = new EventTaskChartInfoEntity();
        eventTaskChartInfoEntity.setTaskId(taskId.toString());
        eventTaskChartInfoEntity.setDatasetId(datasetId);
        eventTaskChartInfoEntity.setSliceId(sliceId);
        eventTaskChartInfoEntity.setChartUrl(supChartUrl);
        eventTaskChartInfoEntity.setCreateTime(nowTs);
        int insert = eventTaskChartInfoMapper.insert(eventTaskChartInfoEntity);
        if(insert !=1) {
            throw exception(INSERT_TASKCHARTINFO_FAILED);
        }
        return taskId.toString();
    }

    public void validTableName(String createTabSql, String aliasName){
        // 定义匹配 CREATE TABLE 后的表名的正则表达式，忽略大小写
        String regex = "(?i)CREATE TABLE\\s+(\\w+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(createTabSql);
        if (matcher.find()) {
            String tableName = matcher.group(1);
            if (!aliasName.equals(tableName)){
                throw exception(CUSTOME_CREATE_TABLENAME_FAILED);
            }
        } else {
            throw exception(CUSTOME_CREATE_TABLENAME_FAILED);
        }
    }

    // 加密方法
    public String encrypt(String data, String secret){
        Cipher cipher = null;
        byte[] encrypted = new byte[0];
        SecretKeySpec key = new SecretKeySpec(secret.getBytes(), "AES");
        try {
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            encrypted = cipher.doFinal(data.getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Base64.getEncoder().encodeToString(encrypted);
    }

    // 解密方法
    public String decrypt(String encryptedData, String secret){
        Cipher cipher = null;
        SecretKeySpec key = new SecretKeySpec(secret.getBytes(), "AES");
        byte[] decrypted = new byte[0];
        try {
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new String(decrypted);
    }


    /**
     * 事件保存到数据库
     *
     * @param eventTaskVo
     * @param finalQuery
     * @param createTabSql
     * @param aliasName
     * @param taskName
     * @param taskResTableName
     * @param taskId
     * @param nowTs
     */

    private void createEventToMs(EventTaskVo eventTaskVo, String finalQuery, String createTabSql,
                                 String aliasName, String taskName, StringBuilder taskResTableName,
                                 UUID taskId, Timestamp nowTs,StringBuilder dimColumn,StringBuilder resColumn, int isAuto) {
        EventTaskSqlMetaDataVo sqlMetaData = eventTaskVo.getSqlMetaData();
        List<EventTaskDimensionVo> dimension = new ArrayList<>();
        EventTaskFilterDataVo filterData = new EventTaskFilterDataVo();
        if (sqlMetaData != null) {
            dimension = sqlMetaData.getDimension();
            filterData = eventTaskVo.getSqlMetaData().getFilterData();
        }

        // 保存到mysql
        AdminUserRespDTO user = getAdminUserRes();
        EventTaskInfoEntity eventTaskInfoEntity = EventTaskInfoEntity.builder()
                .taskId(taskId.toString())
                .taskName(taskName)
                .taskAliasName(aliasName)
                .taskResTableName(taskResTableName.toString())
                .sqlMetaData(JSON.toJSONString(eventTaskVo))
                .taskDdlSql(createTabSql)
                .taskDimension(JSON.toJSONString(dimension))
                .taskFilterCriteria(JSON.toJSONString(filterData))
                .taskExeSql(finalQuery.toString())
                .taskStatus(1)
                .taskExecutionStatus(0)
                .createTime(nowTs)
                .createId(String.valueOf(user.getId()))
                .createUsername(user.getUsername())
                .updateTime(nowTs)
                .latestScheduleTs(nowTs)
                .latestScheduleStatus(RUNNING)
                .dimColumn(dimColumn.toString())
                .resColumn(resColumn.toString().replaceFirst(",",""))
                .isAuto(isAuto)
                .build();
        int i = eventTaskInfoMapper.insert(eventTaskInfoEntity);
        if (i != 1) {
            throw exception(ADD_EVENT_TASK_FAILED);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void rerunTask(String taskId, String startDate, String endDate) {
        eventCheck.checkDateOfRerun(startDate, endDate);
        eventCheck.checkTaskId(taskId);
        Timestamp nowTs = getNowTimestamp();
        // 调用airFlow
        eventTaskToAirFlow.rerunAirFlow(taskId, startDate, endDate, nowTs.toString());
        //更新task_event_info表的任务状态
        EventTaskInfoEntity eventTaskInfoEntity = eventTaskInfoMapper.selectById(taskId);
        int taskExecutionStatus = eventTaskInfoEntity.getTaskExecutionStatus();
        if(taskExecutionStatus == TASK_EXE_WAIT_STATUS) { //如果状态是0 说明是第一次手动执行的，状态改为running
            eventTaskInfoEntity.setTaskExecutionStatus(TASK_EXE_RUNNING_STATUS);
        } else {
            eventTaskInfoEntity.setTaskExecutionStatus(TASK_EXE_RERUNNING_STATUS);
        }
        eventTaskInfoEntity.setUpdateTime(nowTs);
        eventTaskInfoMapper.updateById(eventTaskInfoEntity);
    }

    @Override
    public JSONObject getMetaData(String metaType, Set<String> tables) {
        if (TASK_META_TYPE_DIM.equals(metaType)) {  //获取维度信息

            List<EventTaskDimMetaEntity> colReNameDimEntity = new ArrayList<>();
//            //用户属性维度
            List<EventTaskDimMetaEntity> customersEntities = metaDataService.selectDimMetaData(Set.of(TASK_BH_T_CUSTOMERS_ALL));

            if (null != tables && !tables.isEmpty() && !tables.contains(TASK_BH_T_CUSTOMERS_ALL)) {//如果有t_customers_all表则只需要t_customers_all表的信息，因为其他事件表有的字段t_customers_all表没有
                //取出对应表的col数据
                List<EventTaskDimMetaEntity> eventTaskDimMetaEntities = metaDataService.selectDimMetaData(tables);

                Map<String, List<EventTaskDimMetaEntity>> colReNameEntitiesMap = eventTaskDimMetaEntities.stream()
                        .collect(Collectors.groupingBy(EventTaskDimMetaEntity::getColRename));
                String groupTableName = String.join("-", eventTaskDimMetaEntities.stream().map(EventTaskDimMetaEntity::getColGroup).collect(Collectors.toSet()));

                //取tables中的表共有的列
                Map<String, Long> colNameCountMap = eventTaskDimMetaEntities.stream()
                        .collect(Collectors.groupingBy(BaseMetaEntity::getColRename, Collectors.counting()));
                //统计的value数和tables.size数量一致，说明当前字段在每一个表中都存在
                Set<String> colReNameSet = colNameCountMap.entrySet().stream()
                        .filter(entry -> entry.getValue() == tables.size()).map(Map.Entry::getKey).collect(Collectors.toSet());
                List<EventTaskDimMetaEntity> customerEntityFilter = customersEntities.stream().filter(e -> !colReNameSet.contains(e.getColRename())).toList();
                colReNameDimEntity.addAll(customerEntityFilter);

                //取出每个字段对应的Entry
                for (String colReName : colReNameSet) {
                    EventTaskDimMetaEntity eventTaskDimMetaEntity = colReNameEntitiesMap.get(colReName).get(0);
                    eventTaskDimMetaEntity.setColGroup(groupTableName);
                    colReNameDimEntity.add(eventTaskDimMetaEntity);
                }
            } else {
                colReNameDimEntity.addAll(customersEntities);
            }
            Map<String, List<BaseMetaEntity>> DimMetaMap = colReNameDimEntity.stream().collect(Collectors.groupingBy(BaseMetaEntity::getColGroup));

            return JSONObject.parseObject(JSON.toJSONString(DimMetaMap));

        } else if (TASK_META_TYPE_EVENT.equals(metaType)) {  //事件元数据
            //获取事件表元数据
            Set<String> eventBHTableName = Set.of(TASK_BH_T_ORDER_ALL, TASK_BH_T_DEPOSIT_ALL, TASK_BH_T_WITHDRAWAL_ALL, TASK_BH_T_PROMOTION_ALL, TASK_BH_T_CUSTOMERS_ALL);
            List<EventTaskEventMetaEntity> eventTaskEventMetaEntities = metaDataService.selectEventMetaData(eventBHTableName);
            //获取自定义指标的元数据
            List<EventTaskCustomerMetaEntity> eventTaskCustomerMetaEntities = metaDataService.selectCustomerMetaData(eventBHTableName);
            Map<String, List<BaseMetaEntity>> eventCollects = eventTaskEventMetaEntities.stream().collect(Collectors.groupingBy(BaseMetaEntity::getColGroup));
            Map<String, List<EventTaskCustomerMetaEntity>> customerCollects = eventTaskCustomerMetaEntities.stream().collect(Collectors.groupingBy(EventTaskCustomerMetaEntity::getColGroup));
            // 合并eventCollects和customerCollects
            customerCollects.forEach((colGroup, customerList) ->
                    eventCollects.merge(colGroup,
                            new ArrayList<BaseMetaEntity>(customerList),  // 显式类型转换
                            (existingList, newList) -> {
                                existingList.addAll(newList);
                                return existingList;
                            }
                    )
            );
            String eventJson = JSON.toJSONString(eventCollects);
            JSONObject eventObj = JSONObject.parseObject(eventJson);
            return eventObj;
        } else if (TASK_META_TYPE_ALL.equals(metaType)) {
            Set<String> eventBHTableName = Set.of(TASK_BH_T_ORDER_ALL, TASK_BH_T_DEPOSIT_ALL, TASK_BH_T_WITHDRAWAL_ALL, TASK_BH_T_PROMOTION_ALL, TASK_BH_T_CUSTOMERS_ALL);

            List<EventTaskDimMetaEntity> eventTaskDimMetaEntities = metaDataService.selectDimMetaData(eventBHTableName);
            List<EventTaskEventMetaEntity> eventTaskEventMetaEntities = metaDataService.selectEventMetaData(eventBHTableName);
            List<EventTaskOtherMetaEntity> eventTaskOtherMetaEntities = metaDataService.selectOtherMetaData(eventBHTableName);

            if (null != tables && !tables.isEmpty()) { //外层的filter多个表去交集
                List<BaseMetaEntity> cutemorsEntity = new ArrayList<>();
                List<BaseMetaEntity> otherAllEntity = new ArrayList<>();

                for (EventTaskDimMetaEntity eventTaskDimMetaEntity : eventTaskDimMetaEntities) {
                    if (Objects.equals(eventTaskDimMetaEntity.getColTableName(), TASK_BH_T_CUSTOMERS_ALL)) {
                        cutemorsEntity.add(eventTaskDimMetaEntity);
                    } else {
                        otherAllEntity.add(eventTaskDimMetaEntity);
                    }
                }

                for (EventTaskEventMetaEntity eventTaskEventMetaEntity : eventTaskEventMetaEntities) {

                    if (Objects.equals(eventTaskEventMetaEntity.getColTableName(), TASK_BH_T_CUSTOMERS_ALL)) {
                        cutemorsEntity.add(eventTaskEventMetaEntity);
                    } else {
                        otherAllEntity.add(eventTaskEventMetaEntity);
                    }
                }

                for (EventTaskOtherMetaEntity eventTaskOtherMetaEntity : eventTaskOtherMetaEntities) {

                    if (Objects.equals(eventTaskOtherMetaEntity.getColTableName(), TASK_BH_T_CUSTOMERS_ALL)) {
                        cutemorsEntity.add(eventTaskOtherMetaEntity);
                    } else {
                        otherAllEntity.add(eventTaskOtherMetaEntity);
                    }
                }
                List<BaseMetaEntity> colReNameEventEntityList = new ArrayList<>();
                if (!tables.contains(TASK_BH_T_CUSTOMERS_ALL)) {
                    List<BaseMetaEntity> baseMetaEntityStream = otherAllEntity.stream().filter(baseMetaEntity -> tables.contains(baseMetaEntity.getColTableName())).toList();
                    Map<String, Long> colNameCountMap = baseMetaEntityStream.stream().collect(Collectors.groupingBy(BaseMetaEntity::getColRename, Collectors.counting()));
                    String colGroupName = String.join("-", baseMetaEntityStream.stream().map(BaseMetaEntity::getColGroup).collect(Collectors.toSet()));
                    Map<String, List<BaseMetaEntity>> otherAllEntityMap = otherAllEntity.stream().collect(Collectors.groupingBy(BaseMetaEntity::getColRename));
                    Set<String> colReNameSet = colNameCountMap.entrySet().stream()
                            .filter(entry -> entry.getValue() == tables.size()).map(Map.Entry::getKey).collect(Collectors.toSet());

                    List<BaseMetaEntity> customersEntityFilter = cutemorsEntity.stream().filter(e -> !colReNameSet.contains(e.getColRename())).toList();
                    colReNameEventEntityList.addAll(customersEntityFilter);
                    //取出每个字段对应的Entry
                    for (String colReName : colReNameSet) {
                        BaseMetaEntity baseMetaEntity = otherAllEntityMap.get(colReName).get(0);
                        baseMetaEntity.setColGroup(colGroupName);
                        colReNameEventEntityList.add(baseMetaEntity);
                    }
                } else {
                    colReNameEventEntityList.addAll(cutemorsEntity);
                }
                Map<String, List<BaseMetaEntity>> metaEntityMap = colReNameEventEntityList.stream().collect(Collectors.groupingBy(BaseMetaEntity::getColGroup));
                return JSONObject.parseObject(JSON.toJSONString(metaEntityMap));
            } else { //filter all
                Map<String, List<BaseMetaEntity>> dimMetaMap = eventTaskDimMetaEntities.stream().collect(Collectors.groupingBy(BaseMetaEntity::getColGroup));
                Map<String, List<BaseMetaEntity>> eventMetaMap = eventTaskEventMetaEntities.stream().collect(Collectors.groupingBy(BaseMetaEntity::getColGroup));
                Map<String, List<BaseMetaEntity>> otherMetaMap = eventTaskOtherMetaEntities.stream().collect(Collectors.groupingBy(BaseMetaEntity::getColGroup));
                List<EventTaskCustomerMetaEntity> eventTaskCustomerMetaEntities = metaDataService.selectCustomerMetaData(eventBHTableName);
                Map<String, List<BaseMetaEntity>> customerMetaMap = eventTaskCustomerMetaEntities.stream().collect(Collectors.groupingBy(BaseMetaEntity::getColGroup));
                Map<String, List<BaseMetaEntity>> mergeMap = mergeMap(dimMetaMap, eventMetaMap, otherMetaMap, customerMetaMap);
                return JSONObject.parseObject(JSON.toJSONString(mergeMap));
            }
        } else {
            logger.error(TASK_META_TYPE_ERROR.getMsg());
            throw exception(TASK_META_TYPE_ERROR);
        }
    }

    @Override
    public PageResult<EventTaskInfoEntity> searchByPagination(TaskQueryReq req) {
        // checkTagExecutionStatusWithCanBeNull(taskExecutionStatus);
        PageResult<EventTaskInfoEntity> pageResult = this.eventTaskInfoMapper.searchByPagination(req);
        return pageResult;
    }

    @Override
    public JSONArray getPreview(List<String> whereColumnName, String taskId) {
        EventTaskInfoEntity eventTaskInfoEntity = eventTaskInfoMapper.selectById(taskId);
        if (eventTaskInfoEntity == null) {
            throw exception(NO_SUCH_TASK_ERROR);
        }
        String taskResTableName = eventTaskInfoEntity.getTaskResTableName(); //查询当前表所有的列名
        String findColumnSql = "select column_name from information_schema.columns where table_name= '" + taskResTableName + "' and table_schema = '" + spsetSchema + "'";
        List<JSONObject> columns = pgEventTaskMapper.executeSql(findColumnSql);
        List<String> columnList = new ArrayList<>();
        for (JSONObject columnObj : columns) {
            String columnName = columnObj.getString("column_name");
            columnList.add(columnName);
        }
        StringBuilder taskSql = new StringBuilder();
        taskSql.append("select  * from ").append(taskResTableName);
        if (whereColumnName != null && whereColumnName.size() > 0) {
            taskSql.append(" where 1=1 ");
            for (String columnName : whereColumnName) {
                if (!columnName.isEmpty()) {
                    taskSql.append(" and " + columnName + " is not null ");
                }
            }
        }
        taskSql.append(" limit 100");
        List<JSONObject> result = pgEventTaskMapper.executeSql(taskSql.toString());
        if(columnList.size()>0){
            if(result != null && result.size() > 0 ){
                for (JSONObject jsonObject : result) {
                    Set<String> resKeySet = jsonObject.keySet();
                    for (String resCol : columnList) {
                        if(!resKeySet.contains(resCol)){
                            jsonObject.put(resCol, null);
                        }
                    }
                }
            }
        }

        JSONArray jsonArray = new JSONArray(result);
        return jsonArray;
    }

    @Override
    public String getChartUrl(String taskId) {
        EventTaskChartInfoEntity eventTaskChartInfoEntity = eventTaskChartInfoMapper.selectById(taskId);
        if (eventTaskChartInfoEntity == null) {
            throw exception(GET_TASKCHARTINFO_FAILED);
        }
        int datasetId = eventTaskChartInfoEntity.getDatasetId();
        String chartUrl = eventTaskChartInfoEntity.getChartUrl();
        String accessToken = getAccessToken(spsetUrl, spsetUserName, spsetPassword, restTemplate);
        refreshDataset(spsetUrl,accessToken,datasetId, restTemplate);
        return chartUrl;
    }


    public Map<String, List<BaseMetaEntity>> mergeMap(Map<String, List<BaseMetaEntity>>... maps) {
        Map<String, List<BaseMetaEntity>> mergeMap = new HashMap<>();
        for (Map<String, List<BaseMetaEntity>> map : maps) {
            for (Map.Entry<String, List<BaseMetaEntity>> entry : map.entrySet()) {
                List<BaseMetaEntity> baseMetaEntityList = mergeMap.getOrDefault(entry.getKey(), new ArrayList<>());
                baseMetaEntityList.addAll(entry.getValue());
                mergeMap.put(entry.getKey(), baseMetaEntityList);
            }
        }
        return mergeMap;
    }

    public void checkTagExecutionStatusWithCanBeNull(Integer taskExeStatus) {
        List<Integer> taskExeRunningStatus = List.of(TASK_EXE_RUNNING_STATUS, TASK_EXE_RERUNNING_STATUS, TASK_EXE_SUCCESS_STATUS, TASK_EXE_FAILED_STATUS);
        if (!taskExeRunningStatus.contains(taskExeStatus)) {
            throw exception(TASK_EXE_STATUS_ERROR);
        }
    }

    public EventTaskInfoEntity formatTaskInfo(EventTaskInfoEntity eventTaskInfoEntity) {
        eventTaskInfoEntity.setTaskDdlSql("");
        eventTaskInfoEntity.setTaskExeSql("");
        return eventTaskInfoEntity;
    }

    public String getSelectStandardSql(EventTaskVo eventTaskVo, StringBuilder dimSql, StringBuilder resSql) {
        Map<Integer, String> resAliasNameMap = new LinkedHashMap<>();
        EventTaskSqlMetaDataVo sqlMetaData = eventTaskVo.getSqlMetaData();
        List<EventTaskCalcSqlVo> calcSql = sqlMetaData.getCalcSql();
        List<EventTaskDimensionVo> dimensionList = sqlMetaData.getDimension().stream().filter(x -> StringUtils.isNotBlank(x.getColName())).collect(Collectors.toList());
        List<EventTaskDimMetaEntity> userIdMapList = metaDataService.getEventTaskDimMetaMapper().selectList(new LambdaQueryWrapperX<EventTaskDimMetaEntity>().eq(true, EventTaskDimMetaEntity::getColRename, USER_ID));
        List<EventTaskDimMetaEntity> dateMapList = metaDataService.getEventTaskDimMetaMapper().selectList(new LambdaQueryWrapperX<EventTaskDimMetaEntity>().eq(true, EventTaskDimMetaEntity::getColRename, DATE_TIME));

        Map<String, String> tableUserIdMap = userIdMapList.stream().collect(Collectors.toMap(EventTaskDimMetaEntity::getColTableName, EventTaskDimMetaEntity::getColName));
        Map<String, String> tableDateTimeMap = dateMapList.stream().collect(Collectors.toMap(EventTaskDimMetaEntity::getColTableName, EventTaskDimMetaEntity::getColName));

        Map<String, String> relationMap = new HashMap<>();
        relationMap.put("or", " full join ");
        relationMap.put("and", " inner join ");

        Map<String, String> joinOrUnionMap = new HashMap<>();
        joinOrUnionMap.put("or", " union ");
        joinOrUnionMap.put("and", " inner join ");

        StringBuilder taskSql = new StringBuilder();
        taskSql.append("with ");
                //.append(" toSTR_TO_DATE('[exec_date]', '%Y-%m-%d') as exec_dt").append(System.lineSeparator()).append(System.lineSeparator());

        String tableDim = " {beforTable}.{dim} = {currentTable}.{dim}";
        StringBuilder dimFullJoin = new StringBuilder();
        dimFullJoin.append("{beforTable}.dt = {currentTable}.dt ");
        //共有的维度
        StringBuilder dimension = new StringBuilder();
        StringBuilder eventDim = new StringBuilder();
        StringBuilder userDim = new StringBuilder();

        for (int i = 0; i < dimensionList.size(); i++) {
            EventTaskDimensionVo eventTaskDimensionVo = dimensionList.get(i);
            String colName = eventTaskDimensionVo.getColName();
            String colTableName = eventTaskDimensionVo.getColTableName();
            if (colTableName.equals(TAB_T_CUSTOMERS_ALL)) {
                userDim.append(COMMA).append(TAB).append(colName).append(" ");
            } else {
                eventDim.append(COMMA).append(TAB).append(colName).append(" ");
            }
            dimension.append(COMMA).append(TAB).append(colName).append(" ");
            String dimReplace = tableDim.replace("{dim}", colName);
            dimFullJoin.append(" and ").append(dimReplace);
        }
        List<EventTaskFiltersVo> outFilters = sqlMetaData.getFilterData().getFilters();

        Map<String, String> TCustomersAllSql = new HashMap<>();
        Map<String, String> TCustomerAllWhere = new HashMap<>();
        Map<String, String> TOutOtherWhere = new HashMap<>();

        String firstCustomerAllRelation = "";

        for (int i = 0; i < outFilters.size(); i++) {
            EventTaskFiltersVo outFilter = outFilters.get(i);
            String filterColName = outFilter.getFilterColName();
            String filterColTableName = outFilter.getFilterColTableName();
            String dbFilterColTableName = database + outFilter.getFilterColTableName();
            String filterSymbol = outFilter.getFilterSymbol();
            String filterContent = outFilter.getFilterContent();
            String filterColType = outFilter.getFilterColType();
            // 如果数据是日期类型，加toDate
            if (filterColType.equals("3")) {
                filterColName = " STR_TO_DATE(" + filterColName + ", '%Y-%m-%d') ";
            }
            String beforeRelation = (outFilter.getBeforeRelation());
            String symbolStr = getCondition(filterColType, filterSymbol, filterContent);
            if (filterColTableName.equals(TAB_T_CUSTOMERS_ALL)) {
                String customersSql = TCustomersAllSql.getOrDefault(TAB_T_CUSTOMERS_ALL, "select " + tableUserIdMap.get(TAB_T_CUSTOMERS_ALL) + " as user_id from " + dbFilterColTableName + " where 1 = 1 and ");
                String customerWhere = TCustomerAllWhere.getOrDefault(TAB_T_CUSTOMERS_ALL, filterColName + " " + symbolStr);
                if (firstCustomerAllRelation.isEmpty()) {
                    firstCustomerAllRelation = beforeRelation;
                }
                if (i == 0) {
                    String sql = customersSql + " " + filterColName + " " + symbolStr;
                    TCustomersAllSql.put(TAB_T_CUSTOMERS_ALL, sql);
                    TCustomerAllWhere.put(TAB_T_CUSTOMERS_ALL, " and " + customerWhere);
                } else {
                    String sql = customersSql + " " + beforeRelation + " " + filterColName + " " + symbolStr;
                    TCustomersAllSql.put(TAB_T_CUSTOMERS_ALL, sql);
                    String where = customerWhere + " " + beforeRelation + " " + filterColName + " " + symbolStr;
                    TCustomerAllWhere.put(TAB_T_CUSTOMERS_ALL, where);
                }
            } else {
                if (i == 0) {
                    TOutOtherWhere.put("out_where", " and " + filterColName + " " + symbolStr + " ");
                } else {
                    String outWhere = TOutOtherWhere.get("out_where");
                    if (outWhere != null) {
                        String where = outWhere + " " + beforeRelation + " " + filterColName + " " + symbolStr;
                        TOutOtherWhere.put("out_where", where);
                    } else {
                        String where = " " + beforeRelation + " " + filterColName + " " + symbolStr;
                        TOutOtherWhere.put("out_where", where);
                    }
                }
            }
        }


        //复合指标计算
        StringBuilder comSqlStr = new StringBuilder();
        StringBuilder comSqlResCol = new StringBuilder();
        Map<Integer, String> mathOperMap = new HashMap<>();
        mathOperMap.put(1, "+");
        mathOperMap.put(2, "-");
        mathOperMap.put(3, "*");
        mathOperMap.put(4, "/");
        List<EventTaskComSql> comSql = sqlMetaData.getComSql();
        if(comSql != null && !comSql.isEmpty()) {
            List<EventTaskComSql> comSqlList = sqlMetaData.getComSql().stream().filter(eventTaskComSql -> eventTaskComSql.getFirstCol() != null && !eventTaskComSql.getFirstCol().isEmpty()).toList();
            if (!comSqlList.isEmpty()) {
                for (EventTaskComSql eventTaskComSql : comSqlList) {
                    String firstCol = eventTaskComSql.getFirstCol();
                    int mathOperation = eventTaskComSql.getMathOperation();
                    String secondCol = eventTaskComSql.getSecondCol();
                    String aliasName = eventTaskComSql.getAliasName();
                    String mathOpt = mathOperMap.get(mathOperation);
                    comSqlStr.append(", ( ").append(firstCol).append(" ").append(mathOpt).append(" NULLIF( ").append(secondCol).append(" ,0 )) as ").append(aliasName);
                    comSqlResCol.append(", ").append(aliasName);
                }
            }
        }

        List<String> mainGroupbyJoinList = new ArrayList<>();

        String mainTCustomersAllSql = "";
        for (int m = 0; m < calcSql.size(); m++) {
            EventTaskCalcSqlVo eventTaskCalcSqlVo = calcSql.get(m);
            String colTableName = eventTaskCalcSqlVo.getColTableName();
            String dbColTableName = database + eventTaskCalcSqlVo.getColTableName();
            String colName = eventTaskCalcSqlVo.getColName();
            String colRename = eventTaskCalcSqlVo.getColRename();
            String minuDts = "-" + (Integer.valueOf(eventTaskCalcSqlVo.getMinuDts()) - 1);
            String colCalculation = eventTaskCalcSqlVo.getColCalculation();
            String cal = colCalculation.replace("{key}", eventTaskCalcSqlVo.getColName())
                    .replace("{user_id}", "user_id");
            String isCustomer = eventTaskCalcSqlVo.getIsCustomer();
            String customerCalculation = eventTaskCalcSqlVo.getCustomerCalculation();
            String eventAliasName = eventTaskCalcSqlVo.getEventAliasName();
            Map<String, String> customerCaulationMap = new HashMap<>();
            String mainIsCustomer = cal.replace("{user_id}", tableUserIdMap.get(colTableName))
                    .replace("user_id", tableUserIdMap.get(colTableName));
            if (isCustomer.equals("1")) {  // 1 是自定义指标计算逻辑
                colName = "(" + customerCalculation + ") as " + colRename;
                cal = colCalculation.replace("{key}", colRename).replace("{user_id}", "user_id");
                mainIsCustomer = colCalculation.replace("{key}", customerCalculation)
                        .replace("{user_id}", tableUserIdMap.get(colTableName))
                        .replace("user_id", tableUserIdMap.get(colTableName));
                customerCaulationMap.put("1", colRename);
            }
            if(eventAliasName != null && !eventAliasName.isEmpty()) {
                resAliasNameMap.put(m, eventAliasName);
            } else {
                String eventName = colTableName + "_" + colRename + "_" + m;
                resAliasNameMap.put(m, eventName);
            }

            if (TCustomerAllWhere.size() > 0) {
                if (!userDim.isEmpty()) {
                    if (mainTCustomersAllSql.isEmpty()) {
                        mainTCustomersAllSql = "temp_t_customers_all_filter as ( select " + tableUserIdMap.get(TAB_T_CUSTOMERS_ALL) + " as user_id, STR_TO_DATE('[exec_date]', '%Y-%m-%d') as dt " + userDim + " from " + database + TAB_T_CUSTOMERS_ALL + " where 1=1 " + TCustomerAllWhere.getOrDefault(TAB_T_CUSTOMERS_ALL, " and 1=1") + ")";
                        taskSql.append(mainTCustomersAllSql).append(System.lineSeparator());
                    }
                    String mainTempSql = ", main_temp_" + m + "_T_1" + " as (select COALESCE(" + tableUserIdMap.get(colTableName) + ", null) as user_id, " + "COALESCE(STR_TO_DATE('[exec_date]', '%Y-%m-%d'), null) as dt "
                            + eventDim + ", " + colName + " from " + dbColTableName + " where 1=1 " + TOutOtherWhere.getOrDefault("out_where", " and 1 = 1") + getMainDate(minuDts, tableDateTimeMap.get(colTableName)) + " )";
                    taskSql.append(mainTempSql).append(System.lineSeparator());

                    String mainTemp = ", main_temp_" + m + "_T_1_1" + " as ( select temp_t_customers_all_filter.user_id as user_id, " + "main_temp_" + m + "_T_1.dt as dt " + eventDim + userDim + "," + customerCaulationMap.getOrDefault("1", colName) + " from main_temp_" + m + "_T_1 "
                            + "inner join temp_t_customers_all_filter on "
                            + "main_temp_" + m + "_T_1.user_id = temp_t_customers_all_filter.user_id )";
                    taskSql.append(mainTemp).append(System.lineSeparator());
                    String mainSql = ", main_temp_" + m + " as (select user_id, dt "
                            + dimension + ", ( " + cal + " ) as agg_" + m + " from main_temp_" + m + "_T_1_1 where 1=1 "
                            + " group by user_id, dt " + dimension + " )";

                    taskSql.append(mainSql).append(System.lineSeparator());
                } else {
                    if (mainTCustomersAllSql.isEmpty()) {
                        mainTCustomersAllSql = "temp_t_customers_all_filter as ( select " + tableUserIdMap.get(TAB_T_CUSTOMERS_ALL) + " as user_id, STR_TO_DATE('[exec_date]', '%Y-%m-%d') as dt from " + database + TAB_T_CUSTOMERS_ALL + " where 1=1 " + TCustomerAllWhere.getOrDefault(TAB_T_CUSTOMERS_ALL, " and 1=1") + ")";
                        taskSql.append(mainTCustomersAllSql).append(System.lineSeparator());
                    }
                    String mainTempSql = ", main_temp_" + m + "_T" + " as (select COALESCE(" + tableUserIdMap.get(colTableName) + ", null) as user_id, COALESCE(STR_TO_DATE('[exec_date]', '%Y-%m-%d'), null) as dt"
                            + dimension + ", ( " + mainIsCustomer + " ) as agg_" + m + " from " + dbColTableName + " where 1=1 " + TOutOtherWhere.getOrDefault("out_where", " and 1 = 1") + getMainDate(minuDts, tableDateTimeMap.get(colTableName))
                            + " group by user_id, dt" + dimension + " )";
                    taskSql.append(mainTempSql).append(System.lineSeparator());
                    String tableM = "main_temp_" + m + "_T";

                    String mainSql = ", main_temp_" + m + " as (select COALESCE(" + tableM + ".user_id, temp_t_customers_all_filter.user_id ) as user_id, " +
                            "COALESCE( " + tableM + ".dt,temp_t_customers_all_filter.dt) as dt" + dimension + ", agg_" + m + " from " + tableM +
                            " inner join temp_t_customers_all_filter on " + tableM + ".user_id = temp_t_customers_all_filter.user_id ) ";

                    taskSql.append(mainSql).append(System.lineSeparator()).append(System.lineSeparator());
                }
            } else {
                if (!userDim.isEmpty()) {
                    if (mainTCustomersAllSql.isEmpty()) {
                        mainTCustomersAllSql = "temp_t_customers_all_filter as ( select " + tableUserIdMap.get(TAB_T_CUSTOMERS_ALL) + " as user_id, STR_TO_DATE('[exec_date]', '%Y-%m-%d') as dt " + userDim + " from " + database + TAB_T_CUSTOMERS_ALL + " where 1=1 " + TCustomerAllWhere.getOrDefault(TAB_T_CUSTOMERS_ALL, " and 1=1") + ")";
                        taskSql.append(mainTCustomersAllSql).append(System.lineSeparator());
                    }

                    String mainTempSql = ", main_temp_" + m + "_T_1" + " as (select COALESCE(" + tableUserIdMap.get(colTableName) + ", null) as user_id, " + " COALESCE(STR_TO_DATE('[exec_date]', '%Y-%m-%d'), null) as dt "
                            + eventDim + ", " + colName + " from " + dbColTableName + " where 1=1 " + TOutOtherWhere.getOrDefault("out_where", " and 1 = 1") + getMainDate(minuDts, tableDateTimeMap.get(colTableName)) + " )";
                    taskSql.append(mainTempSql).append(System.lineSeparator());

                    String mainTemp = ", main_temp_" + m + "_T_1_1" + " as ( select main_temp_" + m + "_T_1.user_id as user_id, " + "main_temp_" + m + "_T_1.dt as dt " + eventDim + userDim + "," + customerCaulationMap.getOrDefault("1", colName) + " from main_temp_" + m + "_T_1 "
                            + "inner join temp_t_customers_all_filter on "
                            + "main_temp_" + m + "_T_1.user_id = temp_t_customers_all_filter.user_id)";

                    taskSql.append(mainTemp).append(System.lineSeparator());

                    String mainSql = ", main_temp_" + m + " as (select user_id, dt "
                            + dimension + ", ( " + cal + " ) as agg_" + m + " from main_temp_" + m + "_T_1_1 where 1=1 "
                            + " group by user_id, dt " + dimension + " )";
                    taskSql.append(mainSql).append(System.lineSeparator()).append(System.lineSeparator());

                } else {
                    String mainSql = ", main_temp_" + m + " as (select COALESCE(" + tableUserIdMap.get(colTableName) + ", null) as user_id, COALESCE(STR_TO_DATE('[exec_date]', '%Y-%m-%d'),null) as dt "
                            + dimension + ", ( " + mainIsCustomer + " ) as agg_" + m + " from " + dbColTableName + " where 1=1 " + TOutOtherWhere.getOrDefault("out_where", " and 1=1 ") + getMainDate(minuDts, tableDateTimeMap.get(colTableName))
                            + " group by user_id, dt " + dimension + " )";
                    taskSql.append(mainSql).append(System.lineSeparator()).append(System.lineSeparator());
                }
            }
            Boolean subFilterOnlyCutomer = false;
            //处理每个事件内部的子查询filter
            List<EventTaskFiltersColVo> filtersCol = eventTaskCalcSqlVo.getFiltersCol().stream().filter(x -> StringUtils.isNotBlank(x.getFilterColName())).collect(Collectors.toList());
            if ((filtersCol != null && !filtersCol.isEmpty())) {
                Map<String, String> firstNosameSqlMap = new HashMap<>();
                Map<String, String> nosameSqlMap = new HashMap<>();
                Map<String, String> filterRelationMap = new HashMap<>();
                List<String> filterRelationList = new ArrayList<>();
                List<FilterSqlVo> filsterVo = new ArrayList<>();
                Map<String, FilterSqlVo> filterSqlVoMap = new HashMap<>();
                String firstFilterTbaleName = "";
                for (int i = 0; i < filtersCol.size(); i++) {
                    EventTaskFiltersColVo eventTaskFiltersColVo = filtersCol.get(i);
                    String filterColName = eventTaskFiltersColVo.getFilterColName();
                    String isCustomerCacu = eventTaskFiltersColVo.getIsCustomer();
                    String filterCustomerCalculation = eventTaskFiltersColVo.getCustomerCalculation();
                    if("1".equals(isCustomerCacu)) {
                        filterColName = " ( " + filterCustomerCalculation + " ) ";
                    }

                    String filterColTableName = eventTaskFiltersColVo.getFilterColTableName();
                    String dbFilterColTableName = database + eventTaskFiltersColVo.getFilterColTableName();
                    String filterSymbol = eventTaskFiltersColVo.getFilterSymbol();
                    String filterContent = eventTaskFiltersColVo.getFilterContent();
                    String filterColType = eventTaskFiltersColVo.getFilterColType();
                    String beforeRelation = eventTaskFiltersColVo.getBeforeRelation();
                    String relationJoin = relationMap.get(beforeRelation);
                    String joinOrUnion = joinOrUnionMap.get(beforeRelation);
                    String symbolStr = getCondition(filterColType, filterSymbol, filterContent);
                    // 如果数据是日期类型，加toDate
                    if (filterColType.equals("3")) {
                        filterColName = " STR_TO_DATE(" + filterColName + ", '%Y-%m-%d') ";
                    }
                    int isFirstFilterCol = 1;
                    int isTCustomerAll = 1;
                    if (i == 0) {
                        isFirstFilterCol = 0;
                    }

                    if (filterColTableName.equals(TAB_T_CUSTOMERS_ALL)) {
                        isTCustomerAll = 0;
                    }

                    if (filterColTableName.equals(TAB_T_CUSTOMERS_ALL)) {
                        FilterSqlVo filterSqlVo = filterSqlVoMap.getOrDefault(filterColTableName, new FilterSqlVo());
                        if (filterSqlVo.getIsfirstFilter() == -1) {
                            filterSqlVo.setIsfirstFilter(isFirstFilterCol);
                        }
                        filterSqlVo.setIsCustomersAll(isTCustomerAll);
                        filterSqlVo.setBeforeRelation(beforeRelation);
                        if (filterSqlVo.getJoinRelationOp() == null) {
                            filterSqlVo.setJoinRelationOp(relationJoin);
                            filterSqlVo.setJoinOrUnion(joinOrUnion);
                        }
                        String sqlStr = filterSqlVo.getSqlStr();

                        filterSqlVo.setAliasTableName(filterColTableName + "_" + m);
                        if (filterSqlVo.getSqlStr() == null) {
                            String s = "select COALESCE(" + tableUserIdMap.get(filterColTableName) + ",null) as user_id, STR_TO_DATE('[exec_date]', '%Y-%m-%d') as dt from " + database + TAB_T_CUSTOMERS_ALL + " where 1=1 and ";
                            String sql = s + " " + filterColName + " " + symbolStr;
                            filterSqlVo.setSqlStr(sql);
                        } else {
                            String s = sqlStr + " " + beforeRelation + " " + filterColName + " " + symbolStr;
                            filterSqlVo.setSqlStr(s);
                        }
                        filterSqlVo.setFilterColTableName(filterColTableName);
                        filterSqlVoMap.put(filterColTableName, filterSqlVo);

                    } else {
                        FilterSqlVo filterSqlVo = filterSqlVoMap.getOrDefault(filterColTableName, new FilterSqlVo());
                        if (filterSqlVo.getIsfirstFilter() == -1) {
                            filterSqlVo.setIsfirstFilter(isFirstFilterCol);
                        }
                        filterSqlVo.setIsCustomersAll(isTCustomerAll); //不是t_customers_all
                        String sqlStr = filterSqlVo.getSqlStr();
                        filterSqlVo.setAliasTableName(filterColTableName + "_" + m);
                        filterSqlVo.setBeforeRelation(beforeRelation);
                        if (filterSqlVo.getJoinRelationOp() == null) {
                            filterSqlVo.setJoinRelationOp(relationJoin);
                        }

                        if (filterSqlVo.getSqlStr() == null) {
                            String s = "select COALESCE(STR_TO_DATE('[exec_date]', '%Y-%m-%d'),null) as dt, COALESCE(" + tableUserIdMap.get(filterColTableName) + ",null) as user_id from " + dbFilterColTableName + " where 1=1 and ";
                            String sql = s + " " + filterColName + " " + symbolStr;
                            filterSqlVo.setSqlStr(sql);
                        } else {
                            String s = sqlStr + " " + beforeRelation + " " + filterColName + " " + symbolStr;
                            filterSqlVo.setSqlStr(s);
                        }
                        filterSqlVo.setFilterColTableName(filterColTableName);
                        filterSqlVoMap.put(filterColTableName, filterSqlVo);
                    }

                }


                List<FilterSqlVo> filterSqlVos = filterSqlVoMap.values().stream().toList();
                for (int i = 0; i < filterSqlVos.size(); i++) {
                    FilterSqlVo value = filterSqlVos.get(i);
                    if (value.getIsCustomersAll() == 0) {
                        String sqlStr = value.getSqlStr();
                        String s = sqlStr + " group by " + "user_id";
                        String aliasTableName = value.getAliasTableName();
                        taskSql.append(", ").append(aliasTableName + " as ( ").append(s).append(" )").append(System.lineSeparator());
                    } else {
                        String sqlStr = value.getSqlStr();
                        String s = sqlStr + getMainDate(minuDts, tableDateTimeMap.get(value.getFilterColTableName())) + " group by user_id, dt";
                        String aliasTableName = value.getAliasTableName();
                        taskSql.append(", ").append(aliasTableName + " as ( ").append(s).append(" )").append(System.lineSeparator());
                    }
                }

                FilterSqlVo firstFilterSqlVo1 = filterSqlVos.stream().filter(filterSqlVo -> filterSqlVo.getIsfirstFilter() == 0).findFirst().orElse(null);
                List<FilterSqlVo> filterSqlVoList = filterSqlVos.stream().filter(filterSqlVo -> filterSqlVo.getIsfirstFilter() != 0).toList();
                int firstIsCustomersAll1 = firstFilterSqlVo1.getIsCustomersAll();
                String firstFilterAliasTableName = firstFilterSqlVo1.getAliasTableName();

                StringBuilder filterJoinStr = new StringBuilder();

                if (firstIsCustomersAll1 == 0 && filterSqlVoList.size() == 0) {
                    String s = ", join_filter_" + m + " as ( select user_id ,dt from " + firstFilterAliasTableName + ")";
                    filterJoinStr.append(s).append(System.lineSeparator());
                    taskSql.append(filterJoinStr).append(System.lineSeparator());
                    subFilterOnlyCutomer = true;
                } else if (firstIsCustomersAll1 == 0 && filterSqlVoList.size() > 0) {
                    String s = ", join_filter_" + m + " as ( select {user_id} as user_id, {dt} as dt from " + firstFilterAliasTableName;
                    String lastAliasTableName = "";

                    StringBuilder coalseDt = new StringBuilder();
                    coalseDt.append("COALESCE( ");
                    coalseDt.append(firstFilterAliasTableName).append(".dt, ");
                    StringBuilder coalseUserId = new StringBuilder();
                    coalseUserId.append("COALESCE( ");
                    coalseUserId.append(firstFilterAliasTableName).append(".user_id, ");

                    for (int i = 0; i < filterSqlVoList.size(); i++) {
                        FilterSqlVo filterSqlVo = filterSqlVoList.get(i);
                        String currentAliasTableName = filterSqlVo.getAliasTableName();
                        String beforeRelation = filterSqlVo.getBeforeRelation();
                        String beforJoin = filterSqlVo.getJoinRelationOp();

                        if (i != filterSqlVoList.size() - 1) {
                            coalseDt.append(currentAliasTableName).append(".dt,");
                            coalseUserId.append(currentAliasTableName).append(".user_id, ");
                        } else {
                            coalseDt.append(currentAliasTableName).append(".dt");
                            coalseUserId.append(currentAliasTableName).append(".user_id ");
                        }
                        if (i == 0) {
                            filterJoinStr
                                    .append(s).append(" ").append(beforJoin).append(" ").append(currentAliasTableName).append(" on ")
                                    .append(firstFilterAliasTableName).append(".user_id = ").append(currentAliasTableName).append(".user_id")
                                    .append(System.lineSeparator());
                            lastAliasTableName = currentAliasTableName;
                        } else {
                            filterJoinStr
                                    .append(" ").append(beforJoin).append(" ").append(currentAliasTableName).append(" on ")
                                    .append(lastAliasTableName).append(".user_id = ").append(currentAliasTableName).append(".user_id and ")
                                    .append(lastAliasTableName).append(".dt = ").append(currentAliasTableName).append(".dt").append(System.lineSeparator());
                            lastAliasTableName = currentAliasTableName;
                        }
                    }
                    coalseDt.append(") ").toString();
                    coalseUserId.append(") ").toString();

                    String replaceFilterJoinStr = filterJoinStr.append(" )").toString().replace("{user_id}", coalseUserId).replace("{dt}", coalseDt);

                    taskSql.append(replaceFilterJoinStr).append(System.lineSeparator());
                } else if (firstIsCustomersAll1 == 1 && filterSqlVoList.size() == 0) {
                    String s = ", join_filter_" + m + " as ( select user_id, dt from " + firstFilterAliasTableName + ")";
                    filterJoinStr.append(s).append(System.lineSeparator());
                    taskSql.append(filterJoinStr);
                } else if (firstIsCustomersAll1 == 1 && filterSqlVoList.size() > 0) {
                    String s = ", join_filter_" + m + " as ( select {user_id} as user_id, {dt} as dt from " + firstFilterAliasTableName;

                    String lastAliasTableName = firstFilterAliasTableName;
                    Optional<FilterSqlVo> customersAllOpt = filterSqlVoList.stream().filter(filterSqlVo -> filterSqlVo.getIsCustomersAll() == 0).findFirst();
                    List<FilterSqlVo> filterSqlVoF = filterSqlVoList.stream().filter(filterSqlVo -> filterSqlVo.getIsCustomersAll() != 0).toList();
                    filterJoinStr.append(s);
                    StringBuilder coalseDt = new StringBuilder();
                    coalseDt.append("COALESCE( ");
                    coalseDt.append(firstFilterAliasTableName).append(".dt, ");
                    StringBuilder coalseUserId = new StringBuilder();
                    coalseUserId.append("COALESCE( ");
                    coalseUserId.append(firstFilterAliasTableName).append(".user_id, ");
                    for (int i = 0; i < filterSqlVoF.size(); i++) {

                        FilterSqlVo filterSqlVo1 = filterSqlVoF.get(i);
                        String currentAliasName = filterSqlVo1.getAliasTableName();
                        String beforeJoin = filterSqlVo1.getJoinRelationOp();

                        if (i != filterSqlVoF.size() - 1) {
                            coalseDt.append(currentAliasName).append(".dt, ");
                            coalseUserId.append(currentAliasName).append(".user_id, ");
                        } else {
                            coalseDt.append(currentAliasName).append(".dt");
                            coalseUserId.append(currentAliasName).append(".user_id ");
                        }

                        filterJoinStr.append(" ").append(beforeJoin).append(" ").append(currentAliasName).append(" on ")
                                .append(lastAliasTableName).append(".user_id = ").append(currentAliasName).append(".user_id and ")
                                .append(lastAliasTableName).append(".dt = ").append(currentAliasName).append(".dt");
                        lastAliasTableName = currentAliasName;
                    }
                    if (customersAllOpt.isPresent()) {
                        FilterSqlVo filterSqlVo = customersAllOpt.get();
                        String currentAliasTableName = filterSqlVo.getAliasTableName();
                        coalseDt.append(currentAliasTableName).append(".dt");
                        coalseUserId.append(currentAliasTableName).append(".user_id");
                        String beforeJoin = filterSqlVo.getJoinRelationOp();
                        filterJoinStr.append(" ").append(beforeJoin).append(" ").append(currentAliasTableName).append(" on ")
                                .append(lastAliasTableName).append(".user_id = ").append(currentAliasTableName).append(".user_id");
                    }
                    coalseDt.append(")");
                    coalseUserId.append(")");
                    filterJoinStr.append(" )").append(System.lineSeparator());
                    taskSql.append(filterJoinStr.toString().replaceAll("\\{dt}", coalseDt.toString()).replaceAll("\\{user_id}", coalseUserId.toString())).append(System.lineSeparator());
                } else {
                    System.out.println("拼接filter子查询异常：m = " + m);
                }

            }

            String reAgg = colCalculation;
            if (colCalculation.equals("count({key})")) {
                reAgg = "sum({key})";
            } else if (colCalculation.equals("count({user_id})")) {
                reAgg = "sum({key})";
            } else if (colCalculation.equals("count(distinct {user_id})")) {
                reAgg = "sum({key})";
            }
            String reColcaluation = reAgg.replaceAll("\\{key}", "agg_" + m).replaceAll("\\{user_id}", "user_id");
            String mainJoinTableName = "main_temp_" + m;
            String joinFilterTableName = "join_filter_" + m;

            if (filtersCol == null || filtersCol.isEmpty()) {
                String mainJoinSql = ", main_join_sql_" + m + " as ( select ("+ mainJoinTableName + ".user_id ) as user_id, (" + mainJoinTableName + ".dt " + ") as dt " + dimension + ", agg_" + m + " from " + mainJoinTableName + " )";
                taskSql.append(mainJoinSql).append(System.lineSeparator());

            } else {
                String mainJoinSql = ", main_join_sql_" + m + " as ( select COALESCE( "+ mainJoinTableName +".user_id, "+ joinFilterTableName + ".user_id ) as user_id, COALESCE( " + mainJoinTableName + ".dt, " + joinFilterTableName + ".dt) as dt " + dimension + ", agg_" + m + " from " + mainJoinTableName + " inner join " + joinFilterTableName + " on cast("
                        + mainJoinTableName + ".dt as date) = cast(" + joinFilterTableName + ".dt as date) and " + mainJoinTableName + ".user_id = " + joinFilterTableName + ".user_id )";
                taskSql.append(mainJoinSql).append(System.lineSeparator());
            }
            String mainGroupBy = ", main_groupby_sql_" + m + " as ( select STR_TO_DATE('[exec_date]', '%Y-%m-%d') as dt " + dimension + ", (" + reColcaluation + ") as res_" + m + " from main_join_sql_" + m + " group by dt " + dimension + ")";

            taskSql.append(mainGroupBy).append(System.lineSeparator()).append(System.lineSeparator());

            mainGroupbyJoinList.add("main_groupby_sql_" + m); //使用有序的list

        }
        StringBuilder exeSql = new StringBuilder();
        StringBuilder coalseDimAll = new StringBuilder();

        for (int i = 0; i < dimensionList.size(); i++) {
            String dimColName = dimensionList.get(i).getColName();
            StringBuilder coalseDim = new StringBuilder();
            coalseDim.append("COALESCE( ");
            for (int m = 0; m < mainGroupbyJoinList.size(); m++) {
                String currentTable = "main_groupby_sql_" + m;
                if (m != mainGroupbyJoinList.size() - 1) {
                    coalseDim.append(currentTable).append(".").append(dimColName).append(", ");
                } else {
                    coalseDim.append(currentTable).append(".").append(dimColName).append(" ");
                }

            }
            coalseDim.append(") ").append("as ").append(dimColName).append(" ");
            if (i == dimensionList.size() - 1) {
                coalseDimAll.append(", ").append(coalseDim).append(" ").append(System.lineSeparator());
            } else {
                coalseDimAll.append(", ").append(coalseDim).append(System.lineSeparator());
            }
        }
        exeSql.append(", res_full_join as ( select {dt} as dt " + coalseDimAll);
        StringBuilder resStr = new StringBuilder();


        for (int m = 0; m < mainGroupbyJoinList.size(); m++) {
            exeSql.append(", res_" + m).append(TAB).append(AS).append(TAB).append(resAliasNameMap.get(m));
            resStr.append(",").append(resAliasNameMap.get(m));
        }
        if (!comSqlStr.isEmpty() && !comSqlResCol.isEmpty()){
            exeSql.append(" ").append(comSqlStr);
        }
        exeSql.append(" from ");

        StringBuilder dimensionStr = new StringBuilder();
        for (int i = 0; i < dimensionList.size(); i++) {
            dimensionList.get(i);
        }
        String beforMainTble = "";

        StringBuilder coalseDt = new StringBuilder();
        coalseDt.append("COALESCE( ");

        for (int m = 0; m < mainGroupbyJoinList.size(); m++) {
            String currentTable = "main_groupby_sql_" + m;
            coalseDt.append(currentTable).append(".dt, ");
            if (m == mainGroupbyJoinList.size() - 1) {
                coalseDt.append(currentTable).append(".dt ");
            }
            if (m == 0) {
                exeSql.append("main_groupby_sql_" + m);
            } else {
                String fullJinDim = dimFullJoin.toString().replaceAll("\\{beforTable}", beforMainTble).replaceAll("\\{currentTable}", currentTable);
                exeSql.append(" full join ").append("main_groupby_sql_" + m).append(" on ").append(fullJinDim);
            }
            beforMainTble = "main_groupby_sql_" + m;
        }
        coalseDt.append(")");
        exeSql.append(" )").append(System.lineSeparator());
        taskSql.append(exeSql.toString().replaceAll("\\{dt}", coalseDt.toString())).append(System.lineSeparator());
        StringBuilder resultSql = new StringBuilder();
        if (!comSqlStr.isEmpty() && !comSqlResCol.isEmpty()){
            resStr.append(comSqlResCol).append(" ");
        }
        resultSql.append("select dt " + dimension + resStr + " from res_full_join");
        dimSql.append("dt ").append(dimension);
        resSql.append(resStr);
        taskSql.append(resultSql);
        if (taskSql.charAt(5) == ',') {
            taskSql.deleteCharAt(5); // 删除指定位置的字符
        }
        logger.info("数据源测试" + taskSql.toString());
        return taskSql.toString();
    }


    /**
    public String getSelectSql(EventTaskVo eventTaskVo, StringBuilder dimSql, StringBuilder resSql) {
        Map<Integer, String> resAliasNameMap = new LinkedHashMap<>();
        EventTaskSqlMetaDataVo sqlMetaData = eventTaskVo.getSqlMetaData();
        List<EventTaskCalcSqlVo> calcSql = sqlMetaData.getCalcSql();
        List<EventTaskDimensionVo> dimensionList = sqlMetaData.getDimension().stream().filter(x -> StringUtils.isNotBlank(x.getColName())).collect(Collectors.toList());
        List<EventTaskDimMetaEntity> userIdMapList = metaDataService.getEventTaskDimMetaMapper().selectList(new LambdaQueryWrapperX<EventTaskDimMetaEntity>().eq(true, EventTaskDimMetaEntity::getColRename, USER_ID));
        List<EventTaskDimMetaEntity> dateMapList = metaDataService.getEventTaskDimMetaMapper().selectList(new LambdaQueryWrapperX<EventTaskDimMetaEntity>().eq(true, EventTaskDimMetaEntity::getColRename, DATE_TIME));

        Map<String, String> tableUserIdMap = userIdMapList.stream().collect(Collectors.toMap(EventTaskDimMetaEntity::getColTableName, EventTaskDimMetaEntity::getColName));
        Map<String, String> tableDateTimeMap = dateMapList.stream().collect(Collectors.toMap(EventTaskDimMetaEntity::getColTableName, EventTaskDimMetaEntity::getColName));

        Map<String, String> relationMap = new HashMap<>();
        relationMap.put("or", " full join ");
        relationMap.put("and", " inner join ");

        Map<String, String> joinOrUnionMap = new HashMap<>();
        joinOrUnionMap.put("or", " union ");
        joinOrUnionMap.put("and", " inner join ");

        StringBuilder taskSql = new StringBuilder();
        taskSql.append("with").append(" toDate('[exec_date]') as exec_dt").append(System.lineSeparator()).append(System.lineSeparator());

        String tableDim = " {beforTable}.{dim} = {currentTable}.{dim}";
        StringBuilder dimFullJoin = new StringBuilder();
        dimFullJoin.append("{beforTable}.dt = {currentTable}.dt ");
        //共有的维度
        StringBuilder dimension = new StringBuilder();
        StringBuilder eventDim = new StringBuilder();
        StringBuilder userDim = new StringBuilder();

        for (int i = 0; i < dimensionList.size(); i++) {
            EventTaskDimensionVo eventTaskDimensionVo = dimensionList.get(i);
            String colName = eventTaskDimensionVo.getColName();
            String colTableName = eventTaskDimensionVo.getColTableName();
            if (colTableName.equals(TAB_T_CUSTOMERS_ALL)) {
                userDim.append(COMMA).append(TAB).append(colName).append(" ");
            } else {
                eventDim.append(COMMA).append(TAB).append(colName).append(" ");
            }
            dimension.append(COMMA).append(TAB).append(colName).append(" ");
            String dimReplace = tableDim.replace("{dim}", colName);
            dimFullJoin.append(" and ").append(dimReplace);
        }
        List<EventTaskFiltersVo> outFilters = sqlMetaData.getFilterData().getFilters();

        Map<String, String> TCustomersAllSql = new HashMap<>();
        Map<String, String> TCustomerAllWhere = new HashMap<>();
        Map<String, String> TOutOtherWhere = new HashMap<>();

        String firstCustomerAllRelation = "";

        for (int i = 0; i < outFilters.size(); i++) {
            EventTaskFiltersVo outFilter = outFilters.get(i);
            String filterColName = outFilter.getFilterColName();
            String filterColTableName = outFilter.getFilterColTableName();
            String dbFilterColTableName = database + outFilter.getFilterColTableName();
            String filterSymbol = outFilter.getFilterSymbol();
            String filterContent = outFilter.getFilterContent();
            String filterColType = outFilter.getFilterColType();
            // 如果数据是日期类型，加toDate
            if (filterColType.equals("3")) {
                filterColName = " toDate(" + filterColName + ") ";
            }
            String beforeRelation = (outFilter.getBeforeRelation());
            String symbolStr = getCondition(filterColType, filterSymbol, filterContent);
            if (filterColTableName.equals(TAB_T_CUSTOMERS_ALL)) {
                String customersSql = TCustomersAllSql.getOrDefault(TAB_T_CUSTOMERS_ALL, "select " + tableUserIdMap.get(TAB_T_CUSTOMERS_ALL) + " as user_id from " + dbFilterColTableName + " where 1 = 1 and ");
                String customerWhere = TCustomerAllWhere.getOrDefault(TAB_T_CUSTOMERS_ALL, filterColName + " " + symbolStr);
                if (firstCustomerAllRelation.isEmpty()) {
                    firstCustomerAllRelation = beforeRelation;
                }
                if (i == 0) {
                    String sql = customersSql + " " + filterColName + " " + symbolStr;
                    TCustomersAllSql.put(TAB_T_CUSTOMERS_ALL, sql);
                    TCustomerAllWhere.put(TAB_T_CUSTOMERS_ALL, " and " + customerWhere);
                } else {
                    String sql = customersSql + " " + beforeRelation + " " + filterColName + " " + symbolStr;
                    TCustomersAllSql.put(TAB_T_CUSTOMERS_ALL, sql);
                    String where = customerWhere + " " + beforeRelation + " " + filterColName + " " + symbolStr;
                    TCustomerAllWhere.put(TAB_T_CUSTOMERS_ALL, where);
                }
            } else {
                if (i == 0) {
                    TOutOtherWhere.put("out_where", " and " + filterColName + " " + symbolStr + " ");
                } else {
                    String outWhere = TOutOtherWhere.get("out_where");
                    if (outWhere != null) {
                        String where = outWhere + " " + beforeRelation + " " + filterColName + " " + symbolStr;
                        TOutOtherWhere.put("out_where", where);
                    } else {
                        String where = " " + beforeRelation + " " + filterColName + " " + symbolStr;
                        TOutOtherWhere.put("out_where", where);
                    }
                }
            }
        }


        //复合指标计算
        StringBuilder comSqlStr = new StringBuilder();
        StringBuilder comSqlResCol = new StringBuilder();
        Map<Integer, String> mathOperMap = new HashMap<>();
        mathOperMap.put(1, "+");
        mathOperMap.put(2, "-");
        mathOperMap.put(3, "*");
        mathOperMap.put(4, "/");
        List<EventTaskComSql> comSql = sqlMetaData.getComSql();
        if(comSql != null && !comSql.isEmpty()) {
            List<EventTaskComSql> comSqlList = sqlMetaData.getComSql().stream().filter(eventTaskComSql -> eventTaskComSql.getFirstCol() != null && !eventTaskComSql.getFirstCol().isEmpty()).toList();
            if (!comSqlList.isEmpty()) {
                for (EventTaskComSql eventTaskComSql : comSqlList) {
                    String firstCol = eventTaskComSql.getFirstCol();
                    int mathOperation = eventTaskComSql.getMathOperation();
                    String secondCol = eventTaskComSql.getSecondCol();
                    String aliasName = eventTaskComSql.getAliasName();
                    String mathOpt = mathOperMap.get(mathOperation);
                    comSqlStr.append(", ( ").append(firstCol).append(" ").append(mathOpt).append(" NULLIF( ").append(secondCol).append(" ,0 )) as ").append(aliasName);
                    comSqlResCol.append(", ").append(aliasName);
                }
            }
        }

        List<String> mainGroupbyJoinList = new ArrayList<>();

        String mainTCustomersAllSql = "";
        for (int m = 0; m < calcSql.size(); m++) {
            EventTaskCalcSqlVo eventTaskCalcSqlVo = calcSql.get(m);
            String colTableName = eventTaskCalcSqlVo.getColTableName();
            String dbColTableName = database + eventTaskCalcSqlVo.getColTableName();
            String colName = eventTaskCalcSqlVo.getColName();
            String colRename = eventTaskCalcSqlVo.getColRename();
            String minuDts = "-" + (Integer.valueOf(eventTaskCalcSqlVo.getMinuDts()) - 1);
            String colCalculation = eventTaskCalcSqlVo.getColCalculation();
            String cal = colCalculation.replace("{key}", eventTaskCalcSqlVo.getColName())
                    .replace("{user_id}", "user_id");
            String isCustomer = eventTaskCalcSqlVo.getIsCustomer();
            String customerCalculation = eventTaskCalcSqlVo.getCustomerCalculation();
            String eventAliasName = eventTaskCalcSqlVo.getEventAliasName();
            Map<String, String> customerCaulationMap = new HashMap<>();
            String mainIsCustomer = cal;
            if (isCustomer.equals("1")) {  // 1 是自定义指标计算逻辑
                colName = "(" + customerCalculation + ") as " + colRename;
                cal = colCalculation.replace("{key}", colRename).replace("{user_id}", "user_id");
                mainIsCustomer = colCalculation.replace("{key}", customerCalculation).replace("{user_id}", "user_id");
                customerCaulationMap.put("1", colRename);
            }
            if(eventAliasName != null && !eventAliasName.isEmpty()) {
                resAliasNameMap.put(m, eventAliasName);
            } else {
                String eventName = colTableName + "_" + colRename + "_" + m;
                resAliasNameMap.put(m, eventName);
            }

            if (TCustomerAllWhere.size() > 0) {
                if (!userDim.isEmpty()) {
                    if (mainTCustomersAllSql.isEmpty()) {
                        mainTCustomersAllSql = ",temp_t_customers_all_filter as ( select " + tableUserIdMap.get(TAB_T_CUSTOMERS_ALL) + " as user_id, exec_dt as dt " + userDim + " from " + database + TAB_T_CUSTOMERS_ALL + " where 1=1 " + TCustomerAllWhere.getOrDefault(TAB_T_CUSTOMERS_ALL, " and 1=1") + ")";
                        taskSql.append(mainTCustomersAllSql).append(System.lineSeparator());
                    }
                    String mainTempSql = ", main_temp_" + m + "_T_1" + " as (select ifnull(" + tableUserIdMap.get(colTableName) + ", null) as user_id, " + "ifnull(toDate(exec_dt), null) as dt "
                            + eventDim + ", " + colName + " from " + dbColTableName + " where 1=1 " + TOutOtherWhere.getOrDefault("out_where", " and 1 = 1") + getMainDate(minuDts, tableDateTimeMap.get(colTableName)) + " )";
                    taskSql.append(mainTempSql).append(System.lineSeparator());

                    String mainTemp = ", main_temp_" + m + "_T_1_1" + " as ( select temp_t_customers_all_filter.user_id as user_id, " + "main_temp_" + m + "_T_1.dt as dt " + eventDim + userDim + "," + customerCaulationMap.getOrDefault("1", colName) + " from main_temp_" + m + "_T_1 "
                            + "inner join temp_t_customers_all_filter on "
                            + "main_temp_" + m + "_T_1.user_id = temp_t_customers_all_filter.user_id settings join_use_nulls = 1 )";
                    taskSql.append(mainTemp).append(System.lineSeparator());
                    String mainSql = ", main_temp_" + m + " as (select user_id, dt "
                            + dimension + ", ( " + cal + " ) as agg_" + m + " from main_temp_" + m + "_T_1_1 where 1=1 "
                            + " group by user_id, dt " + dimension + " )";

                    taskSql.append(mainSql).append(System.lineSeparator());
                } else {
                    if (mainTCustomersAllSql.isEmpty()) {
                        mainTCustomersAllSql = ",temp_t_customers_all_filter as ( select " + tableUserIdMap.get(TAB_T_CUSTOMERS_ALL) + " as user_id, exec_dt as dt from " + database + TAB_T_CUSTOMERS_ALL + " where 1=1 " + TCustomerAllWhere.getOrDefault(TAB_T_CUSTOMERS_ALL, " and 1=1") + ")";
                        taskSql.append(mainTCustomersAllSql).append(System.lineSeparator());
                    }
                    String mainTempSql = ", main_temp_" + m + "_T" + " as (select ifnull(" + tableUserIdMap.get(colTableName) + ", null) as user_id, ifnull(toDate(exec_dt), null) as dt"
                            + dimension + ", ( " + mainIsCustomer + " ) as agg_" + m + " from " + dbColTableName + " where 1=1 " + TOutOtherWhere.getOrDefault("out_where", " and 1 = 1") + getMainDate(minuDts, tableDateTimeMap.get(colTableName))
                            + " group by user_id, dt" + dimension + " )";
                    taskSql.append(mainTempSql).append(System.lineSeparator());
                    String tableM = "main_temp_" + m + "_T";

                    String mainSql = ", main_temp_" + m + " as (select COALESCE(" + tableM + ".user_id, temp_t_customers_all_filter.user_id ) as user_id, " +
                            "COALESCE( " + tableM + ".dt,temp_t_customers_all_filter.dt) as dt" + dimension + ", agg_" + m + " from " + tableM +
                            " inner join temp_t_customers_all_filter on " + tableM + ".user_id = temp_t_customers_all_filter.user_id settings join_use_nulls = 1 ) ";

                    taskSql.append(mainSql).append(System.lineSeparator()).append(System.lineSeparator());
                }
            } else {
                if (!userDim.isEmpty()) {
                    if (mainTCustomersAllSql.isEmpty()) {
                        mainTCustomersAllSql = ",temp_t_customers_all_filter as ( select " + tableUserIdMap.get(TAB_T_CUSTOMERS_ALL) + " as user_id, exec_dt as dt " + userDim + " from " + database + TAB_T_CUSTOMERS_ALL + " where 1=1 " + TCustomerAllWhere.getOrDefault(TAB_T_CUSTOMERS_ALL, " and 1=1") + ")";
                        taskSql.append(mainTCustomersAllSql).append(System.lineSeparator());
                    }

                    String mainTempSql = ", main_temp_" + m + "_T_1" + " as (select ifnull(" + tableUserIdMap.get(colTableName) + ", null) as user_id, " + " ifnull(toDate(exec_dt), null) as dt "
                            + eventDim + ", " + colName + " from " + dbColTableName + " where 1=1 " + TOutOtherWhere.getOrDefault("out_where", " and 1 = 1") + getMainDate(minuDts, tableDateTimeMap.get(colTableName)) + " )";
                    taskSql.append(mainTempSql).append(System.lineSeparator());

                    String mainTemp = ", main_temp_" + m + "_T_1_1" + " as ( select main_temp_" + m + "_T_1.user_id as user_id, " + "main_temp_" + m + "_T_1.dt as dt " + eventDim + userDim + "," + customerCaulationMap.getOrDefault("1", colName) + " from main_temp_" + m + "_T_1 "
                            + "inner join temp_t_customers_all_filter on "
                            + "main_temp_" + m + "_T_1.user_id = temp_t_customers_all_filter.user_id settings join_use_nulls = 1 )";

                    taskSql.append(mainTemp).append(System.lineSeparator());

                    String mainSql = ", main_temp_" + m + " as (select user_id, dt "
                            + dimension + ", ( " + cal + " ) as agg_" + m + " from main_temp_" + m + "_T_1_1 where 1=1 "
                            + " group by user_id, dt " + dimension + " )";
                    taskSql.append(mainSql).append(System.lineSeparator()).append(System.lineSeparator());

                } else {
                    String mainSql = ", main_temp_" + m + " as (select ifnull(" + tableUserIdMap.get(colTableName) + ", null) as user_id, ifnull(toDate(exec_dt),null) as dt "
                            + dimension + ", ( " + mainIsCustomer + " ) as agg_" + m + " from " + dbColTableName + " where 1=1 " + TOutOtherWhere.getOrDefault("out_where", " and 1=1 ") + getMainDate(minuDts, tableDateTimeMap.get(colTableName))
                            + " group by user_id, dt " + dimension + " )";
                    taskSql.append(mainSql).append(System.lineSeparator()).append(System.lineSeparator());
                }
            }
            Boolean subFilterOnlyCutomer = false;
            //处理每个事件内部的子查询filter
            List<EventTaskFiltersColVo> filtersCol = eventTaskCalcSqlVo.getFiltersCol().stream().filter(x -> StringUtils.isNotBlank(x.getFilterColName())).collect(Collectors.toList());
            if ((filtersCol != null && !filtersCol.isEmpty())) {
                Map<String, String> firstNosameSqlMap = new HashMap<>();
                Map<String, String> nosameSqlMap = new HashMap<>();
                Map<String, String> filterRelationMap = new HashMap<>();
                List<String> filterRelationList = new ArrayList<>();
                List<FilterSqlVo> filsterVo = new ArrayList<>();
                Map<String, FilterSqlVo> filterSqlVoMap = new HashMap<>();
                String firstFilterTbaleName = "";
                for (int i = 0; i < filtersCol.size(); i++) {
                    EventTaskFiltersColVo eventTaskFiltersColVo = filtersCol.get(i);
                    String filterColName = eventTaskFiltersColVo.getFilterColName();
                    String isCustomerCacu = eventTaskFiltersColVo.getIsCustomer();
                    String filterCustomerCalculation = eventTaskFiltersColVo.getCustomerCalculation();
                    if("1".equals(isCustomerCacu)) {
                       filterColName = " ( " + filterCustomerCalculation + " ) ";
                    }

                    String filterColTableName = eventTaskFiltersColVo.getFilterColTableName();
                    String dbFilterColTableName = database + eventTaskFiltersColVo.getFilterColTableName();
                    String filterSymbol = eventTaskFiltersColVo.getFilterSymbol();
                    String filterContent = eventTaskFiltersColVo.getFilterContent();
                    String filterColType = eventTaskFiltersColVo.getFilterColType();
                    String beforeRelation = eventTaskFiltersColVo.getBeforeRelation();
                    String relationJoin = relationMap.get(beforeRelation);
                    String joinOrUnion = joinOrUnionMap.get(beforeRelation);
                    String symbolStr = getCondition(filterColType, filterSymbol, filterContent);
                    // 如果数据是日期类型，加toDate
                    if (filterColType.equals("3")) {
                        filterColName = " toDate(" + filterColName + ") ";
                    }
                    int isFirstFilterCol = 1;
                    int isTCustomerAll = 1;
                    if (i == 0) {
                        isFirstFilterCol = 0;
                    }

                    if (filterColTableName.equals(TAB_T_CUSTOMERS_ALL)) {
                        isTCustomerAll = 0;
                    }

                    if (filterColTableName.equals(TAB_T_CUSTOMERS_ALL)) {
                        FilterSqlVo filterSqlVo = filterSqlVoMap.getOrDefault(filterColTableName, new FilterSqlVo());
                        if (filterSqlVo.getIsfirstFilter() == -1) {
                            filterSqlVo.setIsfirstFilter(isFirstFilterCol);
                        }
                        filterSqlVo.setIsCustomersAll(isTCustomerAll);
                        filterSqlVo.setBeforeRelation(beforeRelation);
                        if (filterSqlVo.getJoinRelationOp() == null) {
                            filterSqlVo.setJoinRelationOp(relationJoin);
                            filterSqlVo.setJoinOrUnion(joinOrUnion);
                        }
                        String sqlStr = filterSqlVo.getSqlStr();

                        filterSqlVo.setAliasTableName(filterColTableName + "_" + m);
                        if (filterSqlVo.getSqlStr() == null) {
                            String s = "select ifnull(" + tableUserIdMap.get(filterColTableName) + ",null) as user_id, toDate(exec_dt) as dt from " + database + TAB_T_CUSTOMERS_ALL + " where 1=1 and ";
                            String sql = s + " " + filterColName + " " + symbolStr;
                            filterSqlVo.setSqlStr(sql);
                        } else {
                            String s = sqlStr + " " + beforeRelation + " " + filterColName + " " + symbolStr;
                            filterSqlVo.setSqlStr(s);
                        }
                        filterSqlVo.setFilterColTableName(filterColTableName);
                        filterSqlVoMap.put(filterColTableName, filterSqlVo);

                    } else {
                        FilterSqlVo filterSqlVo = filterSqlVoMap.getOrDefault(filterColTableName, new FilterSqlVo());
                        if (filterSqlVo.getIsfirstFilter() == -1) {
                            filterSqlVo.setIsfirstFilter(isFirstFilterCol);
                        }
                        filterSqlVo.setIsCustomersAll(isTCustomerAll); //不是t_customers_all
                        String sqlStr = filterSqlVo.getSqlStr();
                        filterSqlVo.setAliasTableName(filterColTableName + "_" + m);
                        filterSqlVo.setBeforeRelation(beforeRelation);
                        if (filterSqlVo.getJoinRelationOp() == null) {
                            filterSqlVo.setJoinRelationOp(relationJoin);
                        }

                        if (filterSqlVo.getSqlStr() == null) {
                            String s = "select ifnull(toDate(exec_dt),null) as dt, ifnull(" + tableUserIdMap.get(filterColTableName) + ",null) as user_id from " + dbFilterColTableName + " where 1=1 and ";
                            String sql = s + " " + filterColName + " " + symbolStr;
                            filterSqlVo.setSqlStr(sql);
                        } else {
                            String s = sqlStr + " " + beforeRelation + " " + filterColName + " " + symbolStr;
                            filterSqlVo.setSqlStr(s);
                        }
                        filterSqlVo.setFilterColTableName(filterColTableName);
                        filterSqlVoMap.put(filterColTableName, filterSqlVo);
                    }

                }

                List<FilterSqlVo> filterSqlVos = filterSqlVoMap.values().stream().toList();
                for (int i = 0; i < filterSqlVos.size(); i++) {
                    FilterSqlVo value = filterSqlVos.get(i);
                    if (value.getIsCustomersAll() == 0) {
                        String sqlStr = value.getSqlStr();
                        String s = sqlStr + " group by " + "user_id";
                        String aliasTableName = value.getAliasTableName();
                        taskSql.append(", ").append(aliasTableName + " as ( ").append(s).append(" )").append(System.lineSeparator());
                    } else {
                        String sqlStr = value.getSqlStr();
                        String s = sqlStr + getMainDate(minuDts, tableDateTimeMap.get(value.getFilterColTableName())) + " group by user_id, dt";
                        String aliasTableName = value.getAliasTableName();
                        taskSql.append(", ").append(aliasTableName + " as ( ").append(s).append(" )").append(System.lineSeparator());
                    }
                }

                FilterSqlVo firstFilterSqlVo1 = filterSqlVos.stream().filter(filterSqlVo -> filterSqlVo.getIsfirstFilter() == 0).findFirst().orElse(null);
                List<FilterSqlVo> filterSqlVoList = filterSqlVos.stream().filter(filterSqlVo -> filterSqlVo.getIsfirstFilter() != 0).toList();
                int firstIsCustomersAll1 = firstFilterSqlVo1.getIsCustomersAll();
                String firstFilterAliasTableName = firstFilterSqlVo1.getAliasTableName();

                StringBuilder filterJoinStr = new StringBuilder();

                if (firstIsCustomersAll1 == 0 && filterSqlVoList.size() == 0) {
                    String s = ", join_filter_" + m + " as ( select user_id ,dt from " + firstFilterAliasTableName + ")";
                    filterJoinStr.append(s).append(System.lineSeparator());
                    taskSql.append(filterJoinStr).append(System.lineSeparator());
                    subFilterOnlyCutomer = true;
                } else if (firstIsCustomersAll1 == 0 && filterSqlVoList.size() > 0) {
                    String s = ", join_filter_" + m + " as ( select {user_id} as user_id, {dt} as dt from " + firstFilterAliasTableName;
                    String lastAliasTableName = "";

                    StringBuilder coalseDt = new StringBuilder();
                    coalseDt.append("COALESCE( ");
                    coalseDt.append(firstFilterAliasTableName).append(".dt, ");
                    StringBuilder coalseUserId = new StringBuilder();
                    coalseUserId.append("COALESCE( ");
                    coalseUserId.append(firstFilterAliasTableName).append(".user_id, ");

                    for (int i = 0; i < filterSqlVoList.size(); i++) {
                        FilterSqlVo filterSqlVo = filterSqlVoList.get(i);
                        String currentAliasTableName = filterSqlVo.getAliasTableName();
                        String beforeRelation = filterSqlVo.getBeforeRelation();
                        String beforJoin = filterSqlVo.getJoinRelationOp();

                        if (i != filterSqlVoList.size() - 1) {
                            coalseDt.append(currentAliasTableName).append(".dt,");
                            coalseUserId.append(currentAliasTableName).append(".user_id, ");
                        } else {
                            coalseDt.append(currentAliasTableName).append(".dt");
                            coalseUserId.append(currentAliasTableName).append(".user_id ");
                        }
                        if (i == 0) {
                            filterJoinStr
                                    .append(s).append(" ").append(beforJoin).append(" ").append(currentAliasTableName).append(" on ")
                                    .append(firstFilterAliasTableName).append(".user_id = ").append(currentAliasTableName).append(".user_id")
                                    .append(System.lineSeparator());
                            lastAliasTableName = currentAliasTableName;
                        } else {
                            filterJoinStr
                                    .append(" ").append(beforJoin).append(" ").append(currentAliasTableName).append(" on ")
                                    .append(lastAliasTableName).append(".user_id = ").append(currentAliasTableName).append(".user_id and ")
                                    .append(lastAliasTableName).append(".dt = ").append(currentAliasTableName).append(".dt").append(System.lineSeparator());
                            lastAliasTableName = currentAliasTableName;
                        }
                    }
                    coalseDt.append(") ").toString();
                    coalseUserId.append(") ").toString();

                    String replaceFilterJoinStr = filterJoinStr.append(" settings join_use_nulls = 1 )").toString().replace("{user_id}", coalseUserId).replace("{dt}", coalseDt);

                    taskSql.append(replaceFilterJoinStr).append(System.lineSeparator());
                } else if (firstIsCustomersAll1 == 1 && filterSqlVoList.size() == 0) {
                    String s = ", join_filter_" + m + " as ( select user_id, dt from " + firstFilterAliasTableName + ")";
                    filterJoinStr.append(s).append(System.lineSeparator());
                    taskSql.append(filterJoinStr);
                } else if (firstIsCustomersAll1 == 1 && filterSqlVoList.size() > 0) {
                    String s = ", join_filter_" + m + " as ( select {user_id} as user_id, {dt} as dt from " + firstFilterAliasTableName;

                    String lastAliasTableName = firstFilterAliasTableName;
                    Optional<FilterSqlVo> customersAllOpt = filterSqlVoList.stream().filter(filterSqlVo -> filterSqlVo.getIsCustomersAll() == 0).findFirst();
                    List<FilterSqlVo> filterSqlVoF = filterSqlVoList.stream().filter(filterSqlVo -> filterSqlVo.getIsCustomersAll() != 0).toList();
                    filterJoinStr.append(s);
                    StringBuilder coalseDt = new StringBuilder();
                    coalseDt.append("COALESCE( ");
                    coalseDt.append(firstFilterAliasTableName).append(".dt, ");
                    StringBuilder coalseUserId = new StringBuilder();
                    coalseUserId.append("COALESCE( ");
                    coalseUserId.append(firstFilterAliasTableName).append(".user_id, ");
                    for (int i = 0; i < filterSqlVoF.size(); i++) {

                        FilterSqlVo filterSqlVo1 = filterSqlVoF.get(i);
                        String currentAliasName = filterSqlVo1.getAliasTableName();
                        String beforeJoin = filterSqlVo1.getJoinRelationOp();

                        if (i != filterSqlVoF.size() - 1) {
                            coalseDt.append(currentAliasName).append(".dt, ");
                            coalseUserId.append(currentAliasName).append(".user_id, ");
                        } else {
                            coalseDt.append(currentAliasName).append(".dt");
                            coalseUserId.append(currentAliasName).append(".user_id ");
                        }

                        filterJoinStr.append(" ").append(beforeJoin).append(" ").append(currentAliasName).append(" on ")
                                .append(lastAliasTableName).append(".user_id = ").append(currentAliasName).append(".user_id and ")
                                .append(lastAliasTableName).append(".dt = ").append(currentAliasName).append(".dt");
                        lastAliasTableName = currentAliasName;
                    }
                    if (customersAllOpt.isPresent()) {
                        FilterSqlVo filterSqlVo = customersAllOpt.get();
                        String currentAliasTableName = filterSqlVo.getAliasTableName();
                        coalseDt.append(currentAliasTableName).append(".dt");
                        coalseUserId.append(currentAliasTableName).append(".user_id");
                        String beforeJoin = filterSqlVo.getJoinRelationOp();
                        filterJoinStr.append(" ").append(beforeJoin).append(" ").append(currentAliasTableName).append(" on ")
                                .append(lastAliasTableName).append(".user_id = ").append(currentAliasTableName).append(".user_id");
                    }
                    coalseDt.append(")");
                    coalseUserId.append(")");
                    filterJoinStr.append(" settings join_use_nulls = 1 )").append(System.lineSeparator());
                    taskSql.append(filterJoinStr.toString().replaceAll("\\{dt}", coalseDt.toString()).replaceAll("\\{user_id}", coalseUserId.toString())).append(System.lineSeparator());
                } else {
                    System.out.println("拼接filter子查询异常：m = " + m);
                }

            }

            String reAgg = colCalculation;
            if (colCalculation.equals("count({key})")) {
                reAgg = "sum({key})";
            } else if (colCalculation.equals("count({user_id})")) {
                reAgg = "sum({key})";
            } else if (colCalculation.equals("count(distinct {user_id})")) {
                reAgg = "sum({key})";
            }
            String reColcaluation = reAgg.replaceAll("\\{key}", "agg_" + m).replaceAll("\\{user_id}", "user_id");
            String mainJoinTableName = "main_temp_" + m;
            String joinFilterTableName = "join_filter_" + m;

            if (filtersCol == null || filtersCol.isEmpty()) {
                String mainJoinSql = ", main_join_sql_" + m + " as ( select user_id, (" + mainJoinTableName + ".dt " + ") as dt " + dimension + ", agg_" + m + " from " + mainJoinTableName + " )";
                taskSql.append(mainJoinSql).append(System.lineSeparator());

            } else {
                String mainJoinSql = ", main_join_sql_" + m + " as ( select user_id, COALESCE( " + mainJoinTableName + ".dt, " + joinFilterTableName + ".dt) as dt " + dimension + ", agg_" + m + " from " + mainJoinTableName + " inner join " + joinFilterTableName + " on cast("
                        + mainJoinTableName + ".dt as date) = cast(" + joinFilterTableName + ".dt as date) and " + mainJoinTableName + ".user_id = " + joinFilterTableName + ".user_id  settings join_use_nulls = 1 )";
                taskSql.append(mainJoinSql).append(System.lineSeparator());
            }
            String mainGroupBy = ", main_groupby_sql_" + m + " as ( select exec_dt as dt " + dimension + ", (" + reColcaluation + ") as res_" + m + " from main_join_sql_" + m + " group by dt " + dimension + ")";

            taskSql.append(mainGroupBy).append(System.lineSeparator()).append(System.lineSeparator());

            mainGroupbyJoinList.add("main_groupby_sql_" + m); //使用有序的list

        }
        StringBuilder exeSql = new StringBuilder();
        StringBuilder coalseDimAll = new StringBuilder();

        for (int i = 0; i < dimensionList.size(); i++) {
            String dimColName = dimensionList.get(i).getColName();
            StringBuilder coalseDim = new StringBuilder();
            coalseDim.append("COALESCE( ");
            for (int m = 0; m < mainGroupbyJoinList.size(); m++) {
                String currentTable = "main_groupby_sql_" + m;
                if (m != mainGroupbyJoinList.size() - 1) {
                    coalseDim.append(currentTable).append(".").append(dimColName).append(", ");
                } else {
                    coalseDim.append(currentTable).append(".").append(dimColName).append(" ");
                }

            }
            coalseDim.append(") ").append("as ").append(dimColName).append(" ");
            if (i == dimensionList.size() - 1) {
                coalseDimAll.append(", ").append(coalseDim).append(" ").append(System.lineSeparator());
            } else {
                coalseDimAll.append(", ").append(coalseDim).append(System.lineSeparator());
            }
        }
        exeSql.append(", res_full_join as ( select {dt} as dt " + coalseDimAll);
        StringBuilder resStr = new StringBuilder();


        for (int m = 0; m < mainGroupbyJoinList.size(); m++) {
            exeSql.append(", res_" + m).append(TAB).append(AS).append(TAB).append(resAliasNameMap.get(m));
            resStr.append(",").append(resAliasNameMap.get(m));
        }
        if (!comSqlStr.isEmpty() && !comSqlResCol.isEmpty()){
            exeSql.append(" ").append(comSqlStr);
        }
        exeSql.append(" from ");

        StringBuilder dimensionStr = new StringBuilder();
        for (int i = 0; i < dimensionList.size(); i++) {
            dimensionList.get(i);
        }
        String beforMainTble = "";

        StringBuilder coalseDt = new StringBuilder();
        coalseDt.append("COALESCE( ");

        for (int m = 0; m < mainGroupbyJoinList.size(); m++) {
            String currentTable = "main_groupby_sql_" + m;
            coalseDt.append(currentTable).append(".dt, ");
            if (m == mainGroupbyJoinList.size() - 1) {
                coalseDt.append(currentTable).append(".dt ");
            }
            if (m == 0) {
                exeSql.append("main_groupby_sql_" + m);
            } else {
                String fullJinDim = dimFullJoin.toString().replaceAll("\\{beforTable}", beforMainTble).replaceAll("\\{currentTable}", currentTable);
                exeSql.append(" full join ").append("main_groupby_sql_" + m).append(" on ").append(fullJinDim);
            }
            beforMainTble = "main_groupby_sql_" + m;
        }
        coalseDt.append(")");
        exeSql.append(" settings join_use_nulls = 1 )").append(System.lineSeparator());
        taskSql.append(exeSql.toString().replaceAll("\\{dt}", coalseDt.toString())).append(System.lineSeparator());
        StringBuilder resultSql = new StringBuilder();
        if (!comSqlStr.isEmpty() && !comSqlResCol.isEmpty()){
            resStr.append(comSqlResCol).append(" ");
        }
        resultSql.append("select dt " + dimension + resStr + " from res_full_join");
        dimSql.append("dt ").append(dimension);
        resSql.append(resStr);
        taskSql.append(resultSql);
        return taskSql.toString();
    }
     */

    /**
     * 下线
     *
     * @param taskId
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void offlineTask(String taskId) {
        EventTaskInfoEntity taskOffline = eventTaskInfoMapper.selectById(taskId);
        // 校验
        eventCheck.offlineCheckEventTask(taskOffline);
        Timestamp nowTs = getNowTimestamp();
        // 调用airFlow
        eventTaskToAirFlow.offlineAirFlow(taskId, nowTs.toString());
        // 获取用户
        AdminUserRespDTO user = getAdminUserRes();
        //更新task_event_info表的任务状态
        EventTaskInfoEntity eventTaskInfoEntity = eventTaskInfoMapper.selectById(taskId);
        eventTaskInfoEntity.setTaskStatus(TASK_OFFLINE_STATUS);
        eventTaskInfoEntity.setUpdateTime(nowTs);
        eventTaskInfoEntity.setUpdateId(String.valueOf(user.getId()));
        eventTaskInfoEntity.setUpdateUsername(user.getUsername());
        eventTaskInfoMapper.updateById(eventTaskInfoEntity);
    }

    /**
     * 上线操作
     *
     * @param taskId
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void onlineTask(String taskId) {
        EventTaskInfoEntity taskOnline = eventTaskInfoMapper.selectById(taskId);
        // 校验
        eventCheck.onlineCheckEventTask(taskOnline);
        Timestamp nowTs = getNowTimestamp();
        // 调用airFlow
        eventTaskToAirFlow.onlineAirFlow(taskId, nowTs.toString());
        // 获取用户
        AdminUserRespDTO user = getAdminUserRes();
        //更新task_event_info表的任务状态
        EventTaskInfoEntity eventTaskInfoEntity = eventTaskInfoMapper.selectById(taskId);
        eventTaskInfoEntity.setTaskStatus(TASK_ONLINE_STATUS);
        eventTaskInfoEntity.setTaskExecutionStatus(TASK_EXE_WAIT_STATUS);
        eventTaskInfoEntity.setUpdateTime(nowTs);
        eventTaskInfoEntity.setUpdateId(String.valueOf(user.getId()));
        eventTaskInfoEntity.setUpdateUsername(user.getUsername());
        eventTaskInfoMapper.updateById(eventTaskInfoEntity);
    }

    /**
     * 获取详情
     *
     * @param taskId
     * @return
     */
    @Override
    public JSONObject getEventTaskInfo(String taskId) {
        EventTaskInfoEntity eventTaskInfo = eventTaskInfoMapper.selectById(taskId);
        if (eventTaskInfo == null) {
            throw exception(NO_SUCH_TASK_ERROR);
        }
        return JSONObject.parseObject(eventTaskInfo.getSqlMetaData());
    }

    /**
     * 删除
     *
     * @param taskId
     */
    @Override
    public void deleteEventTask(String taskId) {
        EventTaskChartInfoEntity eventTaskChartInfoEntity = eventTaskChartInfoMapper.selectById(taskId);
        if (eventTaskChartInfoEntity == null) {
            throw exception(GET_TASKCHARTINFO_FAILED);
        }
        int datasetId = eventTaskChartInfoEntity.getDatasetId();
        String accessToken = getAccessToken(spsetUrl, spsetUserName, spsetPassword, restTemplate);
        deleteDataset(spsetUrl, accessToken, datasetId, restTemplate);
        eventTaskChartInfoEntity.setDatasetStatus(1); //当前superset对应的dataset的状态，0表示在线，1表示删除。
        eventTaskChartInfoMapper.updateById(eventTaskChartInfoEntity);
        EventTaskInfoEntity taskDel = eventTaskInfoMapper.selectById(taskId);
        // 校验
        eventCheck.deleteCheckEventTask(taskDel);
        Timestamp nowTs = getNowTimestamp();
        // 调用airFlow
        eventTaskToAirFlow.deleteAirFlow(taskId, nowTs.toString());
        // 删除pg库表
        String deleteTableSql = getPgDeleteTableSql(taskDel.getTaskAliasName()).toString();
        tryCatch(pgEventTaskMapper::executeDDL, DELETE_TAB_PG_SQL_FAILED, deleteTableSql);
        // 获取用户
        AdminUserRespDTO user = getAdminUserRes();
        // 更新task_event_info表的任务状态
        taskDel.setTaskStatus(TASK_DELETE_STATUS);
        taskDel.setUpdateTime(nowTs);
        taskDel.setUpdateId(String.valueOf(user.getId()));
        taskDel.setUpdateUsername(user.getUsername());
        eventTaskInfoMapper.deleteById(taskDel);
    }

    /**
     * 生成要更新的pg表名
     *
     * @return
     */
    private String getPgNewTabName(String tabName) {
        StringBuilder newTabName = new StringBuilder();
        newTabName.append(DELETE).append(UNDER_LINE).append(tabName).append(UNDER_LINE).append(Instant.now().getEpochSecond());
        StringBuilder sqlTem = new StringBuilder();
        return sqlTem.append("ALTER TABLE ").append(tabName).append("RENAME TO ").append(newTabName).toString();
    }

    /**
     * 生成删除PG表语句
     * @param tabName
     * @return
     */
    private StringBuilder getPgDeleteTableSql(String tabName) {
        StringBuilder newTabName = new StringBuilder();
        return newTabName.append("DROP TABLE IF EXISTS ").append(tabName);
    }


    private String getConvertSql(String StandardSql, String DataSource) {
        class PostgresCustomSqlDialect extends PostgresqlSqlDialect {
            public PostgresCustomSqlDialect(Context context) {
                super(context);
            }

            @Override
            public void unparseCall(SqlWriter writer, SqlCall call, int leftPrec, int rightPrec) {
                // 自动转换 STR_TO_DATE 为 TO_DATE
                if ("STR_TO_DATE".equals(call.getOperator().getName())) {
                    writer.print("TO_DATE(");
                    call.operand(0).unparse(writer, leftPrec, rightPrec); // 日期字符串
                    writer.print(",");
                    // 这里需要将 MySQL 格式字符串 '%Y-%m-%d' 转换为 PostgreSQL 格式 'YYYY-MM-DD'
                    String formatString = call.operand(1).toString().replace("%Y", "YYYY").replace("%m", "MM").replace("%d", "DD");
                    writer.print(formatString);
                    writer.print(")");
                }
                else if (call.getOperator().getName().equalsIgnoreCase("DATE_ADD")) {
                    SqlNode date = call.operand(0); // 获取日期参数
                    SqlCall intervalExpression = call.operand(1); // 获取 INTERVAL 表达式
                    // 将日期部分写入
                    date.unparse(writer, leftPrec, rightPrec);
                    // 使用 writer.print 输出 "+" 运算符
                    writer.print("::DATE + INTERVAL ");
                    if (intervalExpression instanceof SqlBasicCall) {
                        SqlBasicCall intervalCall = (SqlBasicCall) intervalExpression;
                        SqlNode quantityNode = intervalCall.operand(0); // 获取数量部分
                        SqlNode unitNode = intervalCall.operand(1);     // 获取单位部分
                        writer.print("'" + quantityNode.toString() + " " + unitNode.toString() + "' ");
                    }
                }else {
                    super.unparseCall(writer, call, leftPrec, rightPrec);
                }
            }
        }

        class parseConvert {
            private SqlNode parseSql(String sql) throws SqlParseException {
                SqlParser.Config config = SqlParser.config().withLex(Lex.MYSQL).withCaseSensitive(true);
                SqlParser parser = SqlParser.create(sql, config);
                return parser.parseQuery();
            }

            private String convertToPostgresSql(SqlNode sqlNode) {

                SqlWriterConfig sqlWriterConfig;
                if (DataSource == "PostgreSQL") {
                    PostgresqlSqlDialect dialect = new PostgresCustomSqlDialect(PostgresqlSqlDialect.DEFAULT_CONTEXT);
                    sqlWriterConfig = SqlPrettyWriter.config().withKeywordsLowerCase(false).withQuoteAllIdentifiers(false).withDialect(dialect);
                } else {
                    PostgresqlSqlDialect dialect = new PostgresCustomSqlDialect(PostgresqlSqlDialect.DEFAULT_CONTEXT);
                    sqlWriterConfig = SqlPrettyWriter.config().withKeywordsLowerCase(false).withQuoteAllIdentifiers(false).withDialect(dialect);
                }
                return sqlNode.toSqlString(c -> sqlWriterConfig).toString();
            }
        }

        parseConvert parseconvert = new parseConvert();
        SqlNode parsedNode = null;
        try {
            parsedNode = parseconvert.parseSql(StandardSql);
        } catch (SqlParseException e) {
            e.printStackTrace();
        }
        return parseconvert.convertToPostgresSql(parsedNode);

    }

    /**
     * 获取当前用户的信息
     *
     * @return
     */
    private AdminUserRespDTO getAdminUserRes() {
        // 获取操作人
        LoginUser loginUser = SecurityFrameworkUtils.getLoginUser();
        if (loginUser == null) {
            throw new RuntimeException("user_id not exits");
        }
        // 获得用户基本信息
        AdminUserRespDTO user = adminUserApi.getUser(loginUser.getId()).getCheckedData();
        return user;
    }


    private void tryCatch(Consumer<String> action, ErrorCode errorCode, String actionParam) {
        try {
            logger.info("执行方法：" + action + ", 入参：" + actionParam);
            action.accept(actionParam);
        } catch (Exception e) {
            logger.info("执行SQL失败,原因：" + e);
//            throw exception(errorCode);
        }
    }

    /**
     * 生成pg的SQL建表语句
     *
     * @param tabName
     * @param dimColumnName
     * @param resColumnName
     * @return
     */
    private StringBuilder getPgInsertSql(String tabName, StringBuilder dimColumnName, StringBuilder resColumnName) {
        StringBuilder pgInsertSql = new StringBuilder();
        pgInsertSql.append(INSERT_INTO).append(TAB).append(tabName).append(TAB).append(LEFT_PARENTHESIS);
        StringBuilder column = new StringBuilder();
        column.append(dimColumnName).append(resColumnName);
        pgInsertSql.append(column).append(RIGHT_PARENTHESIS).append(SEMICOLON);
        return pgInsertSql;
    }

    private StringBuilder getPgDeleteSql(String tabName) {
        StringBuilder pgDeleteSql = new StringBuilder();
        pgDeleteSql.append(DELETE).append(TAB).append(FROM).append(tabName)
                .append(TAB).append(WHERE).append(TAB).append(DT).append(EQ).append(AIRFLOW_END_TIME);
                //.append(SEMICOLON);
        return pgDeleteSql;
    }



    /**
     * 生成创建表的DLL
     *
     * @param tabName
     * @param dimColumnName
     * @param resColumnName
     * @return
     */
    private StringBuilder createTabDll(String tabName, StringBuilder dimColumnName, StringBuilder resColumnName) {
        StringBuilder sql = new StringBuilder();
        sql.append(CREATE_TAB).append(tabName).append(LEFT_PARENTHESIS).append(NEW_LINE);
        StringBuilder column = new StringBuilder();
        List<String> dimList = Arrays.stream(dimColumnName.toString().split(",")).map(String::trim).toList();
        List<String> resList = Arrays.stream(resColumnName.toString().split(",")).map(String::trim).toList();

        for (String dim : dimList) {
            if (dim != null && !dim.isEmpty()) {
                if (dim.equals(DT)) {
                    column.append(dim).append(TAB).append("DATE").append(COMMA).append(NEW_LINE);
                } else {
                    column.append(dim).append(TAB).append("text").append(COMMA).append(NEW_LINE);
                }
            }
        }
        for (String resColumn : resList) {
            if (resColumn != null && !resColumn.isEmpty()) {
                column.append(resColumn).append(TAB).append("FLOAT").append(COMMA).append(NEW_LINE);
            }
        }
        return sql.append(interceptLast(column.toString())).append(RIGHT_PARENTHESIS);
    }

    private StringBuilder createStandardTabDll(String tabName, StringBuilder dimColumnName, StringBuilder resColumnName) {
        StringBuilder sql = new StringBuilder();
        sql.append(CREATE_TAB).append(tabName).append(LEFT_PARENTHESIS).append(NEW_LINE);
        StringBuilder column = new StringBuilder();
        List<String> dimList = Arrays.stream(dimColumnName.toString().split(",")).map(String::trim).toList();
        List<String> resList = Arrays.stream(resColumnName.toString().split(",")).map(String::trim).toList();

        for (String dim : dimList) {
            if (dim != null && !dim.isEmpty()) {
                if (dim.equals(DT)) {
                    column.append(dim).append(TAB).append("DATE").append(COMMA).append(NEW_LINE);
                } else {
                    column.append(dim).append(TAB).append("VARCHAR").append(COMMA).append(NEW_LINE);
                }
            }
        }
        for (String resColumn : resList) {
            if (resColumn != null && !resColumn.isEmpty()) {
                column.append(resColumn).append(TAB).append("FLOAT").append(COMMA).append(NEW_LINE);
            }
        }
        return sql.append(interceptLast(column.toString())).append(RIGHT_PARENTHESIS);
    }

    private String createDdl_to_datasource(String datasource, String sql) {
        String[][] typeMapping = {{}};
        switch (datasource) {
            case "postgrel":
                typeMapping = new String[][]{
                        {"VARCHAR", "TEXT"},
                        {"DATE", "DATE"},
                        {"FLOAT", "FLOAT"},
                };
                break;
            case "bytehouse":
                typeMapping = new String[][]{
                        {"VARCHAR", "String"},
                        {"DATE", "DATE"},
                        {"FLOAT", "Float64"},
                };
                break;
        }
        for (String[] mapping : typeMapping) {
            String fromType = mapping[0];
            String toType = mapping[1];
            sql = sql.replaceAll("\\b" + fromType + "\\b", toType); // 使用 \\b 确保完全匹配
        }
        // 输出替换后的 SQL 语句
        return sql;
    }


    /**
     * 拼装查询字段或查询条件截取最后一位
     *
     * @param input
     * @return
     */
    private StringBuilder interceptLast(String input) {
        String sqlNew = "";
        if (input.trim().lastIndexOf(COMMA) > 0) {
            // 如果最后一位是逗号
            sqlNew = input.toString().substring(0, input.lastIndexOf(COMMA));
        } else if (input.trim().lastIndexOf(AND) > 0) {
            // 如果最后一位是and
            sqlNew = input.toString().substring(0, input.lastIndexOf(AND));
        } else if (input.trim().lastIndexOf(OR) > 0) {
            // 如果最后一位是or
            sqlNew = input.toString().substring(0, input.lastIndexOf(OR));
        } else {
            sqlNew = input.toString();
        }
        StringBuilder sqlNewBuilder = new StringBuilder(sqlNew);
        return sqlNewBuilder;
    }

    private String getCondition(String colType, String symbol, String content) {
        String operator = "";
        switch (symbol) {
            case "1":
                operator = ">";
                break;
            case "2":
                operator = "<";
                break;
            case "3":
                operator = "=";
                break;
            case "4":
                operator = "!=";
                break;
            case "5":
                operator = ">=";
                break;
            case "6":
                operator = "<=";
                break;
            case "7":
                operator = "IS NULL";
                break;
            case "8":
                operator = "IS NOT NULL";
                break;
            case "9":
                operator = "IN ";
                break;
            case "10":
                operator = "NOT IN";
                break;
            case "11":
                operator = "LIKE";
                break;
            case "12":
                operator = "NOT LIKE";
                break;
        }
        String str = convertToQuotedString(colType, content);
        if (symbol.equals("7") || symbol.equals("8")) {
            return operator;
        }
        if (symbol.equals("9") || symbol.equals("10")) {
            return operator + "(" + str + ")";
        }
        if (symbol.equals("11") || symbol.equals("12")) {
            return operator + " " + "'%" + str.replace("\'", "") + "%'";
        }
        return operator + " " + str;
    }

    public static String convertToQuotedString(String colType, String input) {
        // 使用StringBuilder构建输出字符串
        StringBuilder result = new StringBuilder();
        if (StringUtils.isBlank(input)) {
            return result.toString();
        }
        if (input.contains(",")) {
            // 分割输入字符串
            String[] numbers = input.split(",");
            for (int i = 0; i < numbers.length; i++) {
                if ((colType.equals("1") || colType.equals("3"))) {
                    result.append("'").append(numbers[i]).append("'");
                    if (i < numbers.length - 1) {
                        result.append(",");
                    }
                } else {
                    result.append(numbers[i]);
                    if (i < numbers.length - 1) {
                        result.append(",");
                    }
                }
            }
        } else {
            if ((colType.equals("1") || colType.equals("3"))) {
                result.append("'").append(input).append("'");
            } else {
                result.append(TAB).append(input);
            }
        }
        return result.toString();
    }

    /**
     * 主装日期的查询sql
     *
     * @param manuDts
     * @param dateColumn
     * @return
     */
    private String getMainDate(String manuDts, String dateColumn) {
        StringBuilder dateSql = new StringBuilder();
        return dateSql.append(TAB).append(AND).append(TAB).append(TO_DATE).append(LEFT_PARENTHESIS).append(dateColumn).append(RIGHT_PARENTHESIS)
                .append(GE).append(AIRFLOW_START_TIME.replaceAll("<minus_dt>", manuDts))
                .append(TAB).append(AND).append(TAB).append(TO_DATE).append(LEFT_PARENTHESIS).append(dateColumn).append(RIGHT_PARENTHESIS)
                .append(LE).append(AIRFLOW_END_TIME).toString();
    }

    @Data
    @Component
    public static class MetaDataService {

        @Resource
        private EventTaskDimMetaMapper eventTaskDimMetaMapper;

        @Resource
        private EventTaskEventMetaMapper eventTaskEventMetaMapper;

        @Resource
        private EventTaskOtherMetaMapper eventTaskOtherMetaMapper;

        @Resource
        private EventTaskCustomerMetaMapper eventTaskCustomerMetaMapper;
        @Resource
        private EventTaskSqlTemplateMapper eventTaskSqlTemplateMapper ;

        @EnableI18nFunctionForMethod(mapperConfigure = I18nMessagesConfig.TaskI18nCodeMapper.class)
        public List<EventTaskDimMetaEntity> selectDimMetaData(Set<String> BHTableNames) {
            List<EventTaskDimMetaEntity> eventTaskDimMetaEntities = eventTaskDimMetaMapper.selectList(new LambdaQueryWrapperX<EventTaskDimMetaEntity>()
                    .in(true, EventTaskDimMetaEntity::getColTableName, BHTableNames)
                    .ne(true, EventTaskDimMetaEntity::getColRename, "user_id")
                    .ne(true, EventTaskDimMetaEntity::getColRename, "date_time"));
            return eventTaskDimMetaEntities;
        }

        @EnableI18nFunctionForMethod(mapperConfigure = I18nMessagesConfig.TaskI18nCodeMapper.class)
        public List<EventTaskEventMetaEntity> selectEventMetaData(Set<String> BHTableNames) {
            List<EventTaskEventMetaEntity> eventTaskEventMetaEntities = eventTaskEventMetaMapper.selectList(new LambdaQueryWrapperX<EventTaskEventMetaEntity>()
                    .in(true, EventTaskEventMetaEntity::getColTableName, BHTableNames));
            return eventTaskEventMetaEntities;
        }

        @EnableI18nFunctionForMethod(mapperConfigure = I18nMessagesConfig.TaskI18nCodeMapper.class)
        public List<EventTaskOtherMetaEntity> selectOtherMetaData(Set<String> BHTableNames) {
            List<EventTaskOtherMetaEntity> eventTaskOtherMetaEntities = eventTaskOtherMetaMapper.selectList(new LambdaQueryWrapperX<EventTaskOtherMetaEntity>()
                    .in(true, EventTaskOtherMetaEntity::getColTableName, BHTableNames));
            return eventTaskOtherMetaEntities;
        }

        @EnableI18nFunctionForMethod(mapperConfigure = I18nMessagesConfig.TaskI18nCodeMapper.class)
        public List<EventTaskCustomerMetaEntity> selectCustomerMetaData(Set<String> BHTableNames) {
            List<EventTaskCustomerMetaEntity> eventTaskCustomerMetaEntities = eventTaskCustomerMetaMapper.selectList(new LambdaQueryWrapperX<EventTaskCustomerMetaEntity>()
                    .in(true, EventTaskCustomerMetaEntity::getColTableName, BHTableNames));
            return eventTaskCustomerMetaEntities;
        }

        @EnableI18nFunctionForMethod(mapperConfigure = I18nMessagesConfig.TaskI18nCodeMapper.class)
        public List<EventTaskSqlTemplateEntity> selectSqlTemplateMetaData(){
            var getTemplate = eventTaskSqlTemplateMapper
                    .selectList(new LambdaQueryWrapperX<EventTaskSqlTemplateEntity>().eq(true, EventTaskSqlTemplateEntity::getSqlStatus, 1));
            return getTemplate;
        }

    }


}
