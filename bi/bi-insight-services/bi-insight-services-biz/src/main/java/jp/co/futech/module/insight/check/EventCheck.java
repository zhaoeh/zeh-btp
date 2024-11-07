package jp.co.futech.module.insight.check;

import cn.hutool.core.date.format.FastDateFormat;
import jp.co.futech.framework.mybatis.core.query.LambdaQueryWrapperX;
import jp.co.futech.module.insight.controller.admin.event.vo.EventTaskVo;
import jp.co.futech.module.insight.entity.ms.EventTaskInfoEntity;
import jp.co.futech.module.insight.mapper.ms.EventTaskInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;
import java.util.regex.Pattern;

import static jp.co.futech.framework.common.exception.util.ServiceExceptionUtil.exception;
import static jp.co.futech.module.insight.constant.CommonConstant.*;
import static jp.co.futech.module.insight.exception.ErrorCodeConstants.*;

@Service
public class EventCheck {
    @Resource
    private EventTaskInfoMapper eventTaskInfoMapper;

    public void createCheckEventTask(EventTaskVo eventTaskVo){
        {
            var pattern = Pattern.compile("^[a-zA-Z0-9_]+$");
            if (!pattern.matcher(eventTaskVo.getTaskAliasName()).matches()) {
                throw exception(CHECK_EVENT_TEXT_1);
            }
        }
        {
            var pattern = Pattern.compile("__");
            if (pattern.matcher(eventTaskVo.getTaskAliasName()).matches()) {
                throw exception(CHECK_EVENT_TEXT_2);
            }
        }
        {
            var pattern = Pattern.compile("^_");
            if (pattern.matcher(eventTaskVo.getTaskAliasName()).matches()) {
                throw exception(CHECK_EVENT_TEXT_3);
            }
        }
        {
            var pattern = Pattern.compile("_$");
            if (pattern.matcher(eventTaskVo.getTaskAliasName()).matches()) {
                throw exception(CHECK_EVENT_TEXT_4);
            }
        }
        List<EventTaskInfoEntity> eventList= eventTaskInfoMapper.existsTask(eventTaskVo.getTaskAliasName());
        if(eventList.stream().filter(x->x.getTaskName().equals(eventTaskVo.getTaskName())).findFirst().orElse(null)!=null){
            throw exception(CHECK_EVENT_TASK_NAME);
        }
        if(eventList.stream().filter(x-> StringUtils.isNotBlank(x.getTaskAliasName()) &&
                x.getTaskAliasName().equals(eventTaskVo.getTaskAliasName())).findFirst().orElse(null)!=null){
            throw exception(CHECK_EVENT_TASK_ALIAS_NAME);
        }
    }

    public void deleteCheckEventTask(EventTaskInfoEntity taskDel) {
        if (taskDel == null) {
            throw exception(NO_SUCH_TASK_ERROR);
        }
        if (taskDel.getTaskStatus() == 0) {
            return;
        }
        if (taskDel.getTaskStatus() == 1) {
            throw exception(DELETE_TASK_CHECK_STATUS);
        }
    }


    /**
     * 下线校验
     * @param taskOffline
     */
    public void offlineCheckEventTask(EventTaskInfoEntity taskOffline) {
        if (taskOffline == null) {
            throw exception(NO_SUCH_TASK_ERROR);
        }
        if (taskOffline.getTaskStatus() == TASK_OFFLINE_STATUS) {
            return;
        }
        if (taskOffline.getTaskExecutionStatus() == TASK_EXE_RUNNING_STATUS) {
            throw exception(TASK_IS_RUNNING_ERROR);
        }
    }

    /**
     * 上线校验
     * @param taskOnline
     */
    public void onlineCheckEventTask(EventTaskInfoEntity taskOnline) {
        if (taskOnline == null) {
            throw exception(NO_SUCH_TASK_ERROR);
        }
        if (taskOnline.getTaskStatus() == TASK_ONLINE_STATUS) {
            return;
        }
        if (taskOnline.getTaskStatus() == TASK_DELETE_STATUS) {
            throw exception(ONLINE_TASK_FAILED_ERROR);
        }
    }


    public void checkDateOfRerun(String startDate,String endDate) {
        try {
            var fastDateFormat = FastDateFormat.getInstance("yyyy-MM-dd");
            if (!fastDateFormat.format(fastDateFormat.parse(startDate)).equals(startDate)) {
                throw exception(TIME_FORMAT_ERROR);
            }
            if (!fastDateFormat.format(fastDateFormat.parse(endDate)).equals(endDate)) {
                throw exception(TIME_FORMAT_ERROR);
            }
        } catch (ParseException e) {
            throw exception(TIME_FORMAT_ERROR);
        }
    }

    /**
     * 校验id是否存在
     * @param taskId
     */
    public void checkTaskId(String taskId){
        if (!eventTaskInfoMapper.exists(new LambdaQueryWrapperX<EventTaskInfoEntity>()
                .eq(true, EventTaskInfoEntity::getTaskId, taskId))) {
            throw exception(NO_SUCH_TASK_ERROR);
        }
    }
}
