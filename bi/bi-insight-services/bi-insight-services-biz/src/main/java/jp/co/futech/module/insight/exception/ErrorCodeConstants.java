package jp.co.futech.module.insight.exception;

import jp.co.futech.framework.common.exception.ErrorCode;

/**
 * insight 错误码枚举类
 *
 * insight 系统，使用 2-002-000-000 段
 */
public interface ErrorCodeConstants {

    // ========== insight 模块 2-002-000-000 ==========
    ErrorCode NO_SUCH_TASK_ERROR = new ErrorCode(2_002_000_001, "任务不存在，找不到对应的任务","{200000169}");
    ErrorCode OFFLINE_TASK_FAILED_ERROR = new ErrorCode(2_002_000_002, "任务下线失败","{200000170}");
    ErrorCode TASK_IS_RUNNING_ERROR = new ErrorCode(2_002_000_003, "任务正在运行","{200000171}");

    ErrorCode TIME_FORMAT_ERROR = new ErrorCode(2_002_000_003,"时间格式不正确","{200000172}");
    ErrorCode TASK_RERUN_REQUEST_ERROR = new ErrorCode(2_002_000_004, "重跑任务请求失败","{200000173}");
    ErrorCode SCHEDULE_TASK_NOT_EXEIST_ERROR = new ErrorCode(2_002_000_005, "调度任务失败","{200000174}");
    ErrorCode TASK_META_TYPE_ERROR = new ErrorCode(2_002_000_006,"元数据类型不正确","{200000175}");
    ErrorCode TASK_META_TABLES_ERROR = new ErrorCode(2_002_000_007, "元数据查询指定的表名不正确","{200000176}");
    ErrorCode TASK_EXE_STATUS_ERROR = new ErrorCode(2_002_000_008, "任务的运行状态不正确","{200000177}");
    ErrorCode ADD_EVENT_TAG_TASK_FAILED = new ErrorCode(2_002_000_09, "新增事件的自动任务失败","{200000178}");
    ErrorCode ADD_EVENT_TASK_FAILED = new ErrorCode(2_002_000_10, "新增事件任务失败","{200000179}");
    ErrorCode CHECK_EVENT_SQL_FAILED = new ErrorCode(2_002_000_11, "校验SQL模板任务失败","{200000180}");
    ErrorCode CREATE_EVENT_SQL_FAILED = new ErrorCode(2_002_000_12, "创建表SQL模板任务失败","{200000181}");
    ErrorCode EVENT_TASK_AIRFLOW_EXCEPTION = new ErrorCode(2_002_000_13, "访问调度平台失败","{200000182}");
    ErrorCode CHECK_EVENT_TASK_NAME= new ErrorCode(2_002_000_14, "事件名称已被占用","{200000183}");
    ErrorCode CHECK_EVENT_TASK_ALIAS_NAME= new ErrorCode(2_002_000_15, "事件标识已被占用","{200000184}");

    ErrorCode CHECK_EVENT_TEXT_1 = new ErrorCode(2_002_000_16, "任务标识单词的单词之间必须用_连接, 小写","{200000185}");
    ErrorCode CHECK_EVENT_TEXT_2 = new ErrorCode(2_002_000_17, "任务标识不能包含两个连续的_","{200000186}");
    ErrorCode CHECK_EVENT_TEXT_3 = new ErrorCode(2_002_000_18, "任务标识不能以_开始","{200000187}");
    ErrorCode CHECK_EVENT_TEXT_4 = new ErrorCode(2_002_000_19, "任务标识不能以_结尾","{200000188}");
    ErrorCode DELETE_TASK_CHECK_STATUS = new ErrorCode(2_002_000_20, "删除任务状态必须为下线状态","{200000189}");

    ErrorCode DELETE_TASK_FAILED_ERROR = new ErrorCode(2_002_000_21, "删除任务失败","{200000190}");
    ErrorCode ONLINE_TASK_FAILED_ERROR = new ErrorCode(2_002_000_22, "删除状态的任务无法再次上线，请重新创建","{200000191}");

    ErrorCode CREATE_TAB_PG_SQL_FAILED = new ErrorCode(2_002_000_23, "创建PG数据表失败","{200000192}");
    ErrorCode DELETE_TAB_PG_SQL_FAILED = new ErrorCode(2_002_000_24, "删除PG数据表失败","{200000193}");
    ErrorCode SELECT_EVENT_SQL_FAILED = new ErrorCode(2_002_000_25, "查询SQL失败","{200000194}");
    ErrorCode GETSPSET_ACCESS_TOKEN_FAILED = new ErrorCode(2_002_000_26, "获取access_token失败","{200000195}");
    ErrorCode CREATE_DATASET_FAILED = new ErrorCode(2_002_000_27, "创建dataset失败","{200000196}");
    ErrorCode CREATE_CHART_FAILED = new ErrorCode(2_002_000_28, "创建chart失败","{200000197}");
    ErrorCode FIND_DATASET_FAILED = new ErrorCode(2_002_000_29, "没有找到task_id对应的dataset","{200000198}");
    ErrorCode DELETE_DATASET_FAILED = new ErrorCode(2_002_000_30, "删除dataset失败","{200000199}");
    ErrorCode INSERT_TASKCHARTINFO_FAILED = new ErrorCode(2_002_000_31, "新增task_chart_info失败","{200000200}");
    ErrorCode GET_TASKCHARTINFO_FAILED = new ErrorCode(2_002_000_32, "查找task_chart_info失败","{200000201}");
    ErrorCode CUSTOME_CREATE_TABLENAME_FAILED = new ErrorCode(2_002_000_33, "创建的表名需要和任务标识名称相同","{200000202}");
    ErrorCode REFRESH_DATASET_FAILED = new ErrorCode(2_002_000_34, "刷新dataset失败","{200000203}");
}
