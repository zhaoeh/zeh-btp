package jp.co.futech.module.insight.mapper.ms;


import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jp.co.futech.framework.common.pojo.PageResult;
import jp.co.futech.framework.common.pojo.SortingField;
import jp.co.futech.framework.mybatis.core.mapper.BaseMapperX;
import jp.co.futech.framework.mybatis.core.query.LambdaQueryWrapperX;
import jp.co.futech.module.insight.entity.ms.EventTaskInfoEntity;
import jp.co.futech.module.insight.po.req.task.TaskQueryReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static jp.co.futech.module.insight.constant.CommonConstant.TASK_OFFLINE_STATUS;
import static jp.co.futech.module.insight.constant.CommonConstant.TASK_ONLINE_STATUS;

/**
 * @author .zhang
 */
@Mapper
public interface EventTaskInfoMapper extends BaseMapperX<EventTaskInfoEntity> {
    default PageResult<EventTaskInfoEntity> searchByPagination(TaskQueryReq req){
        SortingField sortingField = new SortingField();
        sortingField.setField("create_time");
        sortingField.setOrder("desc");
        req.setSortingFields(List.of(sortingField));
        PageResult<EventTaskInfoEntity> pageResult = selectPage(req, new LambdaQueryWrapperX<EventTaskInfoEntity>()
                .in(EventTaskInfoEntity::getTaskStatus, Set.of(TASK_ONLINE_STATUS,TASK_OFFLINE_STATUS))
                .eqIfPresent(EventTaskInfoEntity::getTaskExecutionStatus, req.getTaskExecutionStatus())
                .eqIfPresent(EventTaskInfoEntity::getIsAuto, req.getIsAuto())
                .select(EventTaskInfoEntity::getTaskId,
                        EventTaskInfoEntity::getTaskName,
                        EventTaskInfoEntity::getTaskAliasName,
                        EventTaskInfoEntity::getTaskDimension,
                        EventTaskInfoEntity::getTaskFilterCriteria,
                        EventTaskInfoEntity::getTaskStatus,
                        EventTaskInfoEntity::getTaskExecutionStatus,
                        EventTaskInfoEntity::getTaskGroup,
                        EventTaskInfoEntity::getCreateTime,
                        EventTaskInfoEntity::getUpdateTime,
                        EventTaskInfoEntity::getDimColumn,
                        EventTaskInfoEntity::getResColumn)
                );

        return pageResult;
    }
    default List<EventTaskInfoEntity> existsTask(String req){
        return selectList();
    }
//    default EventTaskInfoEntity existsTaskName(String req){
//        return selectOne(EventTaskInfoEntity::getTaskName,req);
//    }
//    default EventTaskInfoEntity existsTaskAliasName(String req){
//        return selectOne(EventTaskInfoEntity::getTaskAliasName,req);
//    }

}
