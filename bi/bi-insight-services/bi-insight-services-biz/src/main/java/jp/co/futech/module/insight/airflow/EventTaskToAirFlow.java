package jp.co.futech.module.insight.airflow;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import jp.co.futech.framework.common.exception.ErrorCode;
import jp.co.futech.module.insight.bean.AirFlowInput;
import jp.co.futech.module.insight.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import static jp.co.futech.framework.common.exception.util.ServiceExceptionUtil.exception;
import static jp.co.futech.module.insight.constant.CommonConstant.*;
import static jp.co.futech.module.insight.exception.ErrorCodeConstants.*;

@Component
@Slf4j
public class EventTaskToAirFlow {
    @Value("${event.task.airflowURL}")
    private String scheduleURL;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 新增
     *
     * @param guid
     * @param sql
     * @param taskName
     * @return
     */
    public void createAirFlow(String guid, String deleteSql, String insertSql, String selectSql,  String taskName, String operateTime) {
        AirFlowInput airFlowInput = AirFlowInput.builder()
                .taskId(guid)
                .deleteSql(deleteSql)
                .insertSql(insertSql)
                .selectSql(selectSql)
                .taskType(TASK_OP_ADD)
                .taskName(taskName)
                .operateTime(operateTime)
                .taskCate(TASK_EVENT)
                .build();
        JSONObject airInput = JSONObject.parseObject(JSON.toJSONString(airFlowInput));
        log.info("Access airFlow create json: " + JSON.toJSONString(airInput));
        restTemplateHttp(airInput, ADD_EVENT_TAG_TASK_FAILED);
    }

    /**
     * 下线
     * @param taskId
     * @return
     */
    public void offlineAirFlow(String taskId,String operateTime){
        AirFlowInput airFlowInput = AirFlowInput.builder()
                .taskId(taskId)
                .taskType(TASK_OP_DEACTIVATE)
                .operateTime(operateTime)
                .build();
        JSONObject airInput = JSONObject.parseObject(JSON.toJSONString(airFlowInput));
        log.info("Access airFlow offline json: " + JSON.toJSONString(airInput));
        restTemplateHttp(airInput, OFFLINE_TASK_FAILED_ERROR);
    }

    /**
     * 上线
     * @param taskId
     * @return
     */
    public void onlineAirFlow(String taskId,String operateTime){
        AirFlowInput airFlowInput = AirFlowInput.builder()
                .taskId(taskId)
                .taskType(TASK_OP_ACTIVATE)
                .operateTime(operateTime)
                .build();
        JSONObject airInput = JSONObject.parseObject(JSON.toJSONString(airFlowInput));
        log.info("Access airFlow online json: " + JSON.toJSONString(airInput));
        restTemplateHttp(airInput, ONLINE_TASK_FAILED_ERROR);
    }

    /**
     * 重跑
     * @param taskId
     * @param startDate
     * @param endDate
     * @return
     */
    public void rerunAirFlow(String taskId,String startDate, String endDate,String operateTime){
        AirFlowInput airFlowInput = AirFlowInput.builder()
                .taskId(taskId)
                .taskType(TASK_OP_RERUN)
                .operateTime(operateTime)
                .dateFrom(startDate)
                .dateTo(endDate)
                .build();
        JSONObject airInput = JSONObject.parseObject(JSON.toJSONString(airFlowInput));
        log.info("Access airFlow rerun json: " + JSON.toJSONString(airInput));
        restTemplateHttp(airInput, TASK_RERUN_REQUEST_ERROR);
    }

    /**
     * 删除
     * @param taskId
     * @return
     */
    public void deleteAirFlow(String taskId,String operateTime){
        AirFlowInput airFlowInput = AirFlowInput.builder()
                .taskId(taskId)
                .taskType(TASK_OP_DELETE)
                .operateTime(operateTime)
                .build();
        JSONObject airInput = JSONObject.parseObject(JSON.toJSONString(airFlowInput));
        log.info("Access airFlow delete json: " + JSON.toJSONString(airInput));
        restTemplateHttp(airInput, DELETE_TASK_FAILED_ERROR);
    }

    private void restTemplateHttp(JSONObject airInput, ErrorCode taskFailedError) {
        try {
            ResponseEntity<JSONObject> response = HttpUtils.sentAirflowPost(scheduleURL, airInput, restTemplate);
            JSONObject responseBody = response.getBody();
            log.info("Access airFlow result code: " + response.getStatusCodeValue()+" body:"+responseBody);
            if (response.getStatusCodeValue() != 200) {
                log.info("Access airFlow result error code: " + response.getStatusCodeValue()+" body:"+responseBody);
                throw exception(taskFailedError);
            }
        } catch (Exception e) {
            log.error("Access airFlow result error exception:",e);
            throw exception(EVENT_TASK_AIRFLOW_EXCEPTION);
        }
    }


}
