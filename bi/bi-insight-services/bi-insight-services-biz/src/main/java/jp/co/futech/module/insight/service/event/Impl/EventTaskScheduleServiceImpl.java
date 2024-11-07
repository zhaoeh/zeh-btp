package jp.co.futech.module.insight.service.event.Impl;

import com.alibaba.fastjson2.JSON;
import jp.co.futech.framework.mybatis.core.query.LambdaQueryWrapperX;
import jp.co.futech.module.insight.controller.admin.event.vo.TaskScheduleResultVo;
import jp.co.futech.module.insight.entity.ms.EventTaskChartInfoEntity;
import jp.co.futech.module.insight.entity.ms.EventTaskInfoEntity;
import jp.co.futech.module.insight.entity.ms.EventTaskScheduleInfoEntity;
import jp.co.futech.module.insight.mapper.ms.EventTaskChartInfoMapper;
import jp.co.futech.module.insight.mapper.ms.EventTaskInfoMapper;
import jp.co.futech.module.insight.mapper.ms.EventTaskScheduleInfoMapper;
import jp.co.futech.module.insight.service.event.EventTaskScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.sql.Timestamp;

import static jp.co.futech.framework.common.exception.util.ServiceExceptionUtil.exception;
import static jp.co.futech.module.insight.constant.CommonConstant.*;
import static jp.co.futech.module.insight.exception.ErrorCodeConstants.*;
import static jp.co.futech.module.insight.utils.DateTimeUtils.getNowTimestamp;
import static jp.co.futech.module.insight.utils.SupersetUtils.getAccessToken;
import static jp.co.futech.module.insight.utils.SupersetUtils.refreshDataset;

@Service
public class EventTaskScheduleServiceImpl implements EventTaskScheduleService {
    private Logger logger = LoggerFactory.getLogger(EventTaskScheduleServiceImpl.class);
    @Resource
    private EventTaskInfoMapper eventTaskInfoMapper;
    private EventTaskScheduleInfoMapper eventTaskScheduleInfoMapper;
    @Autowired
    private EventTaskChartInfoMapper eventTaskChartInfoMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${event.task.bh_database}")
    private String database;

    @Value("${event.task.pg_database}")
    private String pgdatabase;
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
    public void updateTaskInfo(TaskScheduleResultVo taskScheduleResultVo) {
        logger.info("airFlow回调开始，请求入参：" + JSON.toJSONString(taskScheduleResultVo));
        String taskId = taskScheduleResultVo.getTask_id();
        Integer scheduleResult = taskScheduleResultVo.getSchedule_result();
        EventTaskInfoEntity eventTaskInfoEntity = eventTaskInfoMapper.selectById(taskId);
        if (eventTaskInfoEntity == null) {
            throw exception(NO_SUCH_TASK_ERROR);
        }
        if (TASK_SCHEDULE_SUCCESS == scheduleResult) {
            logger.info("airFlow回调成功");
            Timestamp nowTs = getNowTimestamp();
            eventTaskInfoEntity.setTaskExecutionStatus(TASK_EXE_SUCCESS_STATUS);
            eventTaskInfoEntity.setUpdateTime(nowTs);
            eventTaskInfoMapper.updateById(eventTaskInfoEntity);
        } else {
            Timestamp nowTs = getNowTimestamp();
            eventTaskInfoEntity.setTaskExecutionStatus(TASK_EXE_FAILED_STATUS);
            eventTaskInfoEntity.setUpdateTime(nowTs);
            eventTaskInfoMapper.updateById(eventTaskInfoEntity);
        }
        EventTaskChartInfoEntity eventTaskChartInfoEntity = eventTaskChartInfoMapper.selectById(taskId);
        if (eventTaskChartInfoEntity == null) {
            throw exception(GET_TASKCHARTINFO_FAILED);
        }
        int datasetId = eventTaskChartInfoEntity.getDatasetId();
        String accessToken = getAccessToken(spsetUrl, spsetUserName, spsetPassword, restTemplate);
        refreshDataset(spsetUrl,accessToken,datasetId, restTemplate);

//        EventTaskScheduleInfoEntity eventTaskScheduleInfoEntity = new EventTaskScheduleInfoEntity();
//        eventTaskScheduleInfoEntity.setTaskId(taskId);
//        eventTaskScheduleInfoEntity.setTaskCate(taskScheduleResultVo.getTask_cate());
//        eventTaskScheduleInfoEntity.setScheduleResult(taskScheduleResultVo.getSchedule_result());
//        eventTaskScheduleInfoEntity.setStartTime(taskScheduleResultVo.getStart_time());
//        eventTaskScheduleInfoEntity.setEndTime(taskScheduleResultVo.getEnd_time());
//        eventTaskScheduleInfoEntity.setLogInfo(taskScheduleResultVo.getLog_info());
//        eventTaskScheduleInfoMapper.insert(eventTaskScheduleInfoEntity);
    }
}
