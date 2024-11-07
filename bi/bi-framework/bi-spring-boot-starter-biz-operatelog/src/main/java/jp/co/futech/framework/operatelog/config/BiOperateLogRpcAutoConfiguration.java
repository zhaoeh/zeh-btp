package jp.co.futech.framework.operatelog.config;

import jp.co.futech.module.system.api.logger.OperateLogApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 操作日志使用到 Feign 的配置项
 *
 * @author futech.co.jp
 */
@AutoConfiguration
@EnableFeignClients(clients = OperateLogApi.class) // 主要是引入相关的 API 服务
public class BiOperateLogRpcAutoConfiguration {

}
