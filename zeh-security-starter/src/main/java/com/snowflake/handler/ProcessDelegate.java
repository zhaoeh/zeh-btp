package com.snowflake.handler;

import com.snowflake.hook.AuthAccessFailProcessor;
import com.snowflake.pojo.AuthAccessFailInfo;
import com.snowflake.utils.JsonUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class ProcessDelegate {

    public static void doProcess(AuthAccessFailProcessor processor,
                                 AuthAccessFailHandler handler,
                                 HttpServletRequest request,
                                 AuthAccessFailInfo failInfo) {
        if (Objects.nonNull(processor)) {
            try {
                log.error("API级别认证或者授权失败，异常信息：{}", JsonUtils.toJsonString(failInfo));
                processor.process(request, failInfo, handler);
            } catch (Exception e) {
                log.error("AuthAccessFailProcessor 执行失败，忽略异常，不阻塞主流程，e:", e);
            }
        }
    }
}
