package com.snowflake.enums;


import com.snowflake.exception.InnerErrorCode;

public interface GlobalErrorCodeConstants {

    InnerErrorCode SUCCESS = new InnerErrorCode(0, "成功");

    /**
     * 客户端错误段
     */
    InnerErrorCode BAD_REQUEST = new InnerErrorCode(400, "请求参数不正确");
    InnerErrorCode UNAUTHORIZED = new InnerErrorCode(401, "账号认证失败");
    InnerErrorCode FORBIDDEN = new InnerErrorCode(403, "没有该操作权限");
    InnerErrorCode OFFLINE = new InnerErrorCode(403, "该功能已下线");
    InnerErrorCode NOT_FOUND = new InnerErrorCode(404, "请求未找到");
    InnerErrorCode METHOD_NOT_ALLOWED = new InnerErrorCode(405, "请求方法不正确");
    InnerErrorCode LOCKED = new InnerErrorCode(423, "请求失败，请稍后重试"); // 并发请求，不允许
    InnerErrorCode TOO_MANY_REQUESTS = new InnerErrorCode(429, "请求过于频繁，请稍后重试");


    /**
     * 服务端错误段
     */
    InnerErrorCode INTERNAL_SERVER_ERROR = new InnerErrorCode(500, "系统异常");

    /**
     * 自定义错误段
     */
    InnerErrorCode REPEATED_REQUESTS = new InnerErrorCode(900, "重复请求，请稍后重试"); // 重复请求
    InnerErrorCode UNKNOWN = new InnerErrorCode(999, "未知错误");

}
