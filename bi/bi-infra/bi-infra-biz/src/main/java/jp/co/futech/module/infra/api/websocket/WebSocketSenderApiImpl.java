package jp.co.futech.module.infra.api.websocket;

import cn.hutool.core.util.StrUtil;
import jp.co.futech.framework.common.pojo.CommonResult;
import jp.co.futech.framework.websocket.core.sender.WebSocketMessageSender;
import jp.co.futech.module.infra.api.websocket.dto.WebSocketSendReqDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

import static jp.co.futech.framework.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class WebSocketSenderApiImpl implements WebSocketSenderApi {

    @Resource
    private WebSocketMessageSender webSocketMessageSender;

    @Override
    public CommonResult<Boolean> send(WebSocketSendReqDTO message) {
        if (StrUtil.isNotEmpty(message.getSessionId())) {
            webSocketMessageSender.send(message.getSessionId(),
                    message.getMessageType(), message.getMessageContent());
        } else if (message.getUserType() != null && message.getUserId() != null) {
            webSocketMessageSender.send(message.getUserType(), message.getUserId(),
                    message.getMessageType(), message.getMessageContent());
        } else if (message.getUserType() != null) {
            webSocketMessageSender.send(message.getUserType(),
                    message.getMessageType(), message.getMessageContent());
        }
        return success(true);
    }

}
