package jp.co.futech.framework.websocket.core.sender.local;

import jp.co.futech.framework.websocket.core.sender.AbstractWebSocketMessageSender;
import jp.co.futech.framework.websocket.core.sender.WebSocketMessageSender;
import jp.co.futech.framework.websocket.core.session.WebSocketSessionManager;

/**
 * 本地的 {@link WebSocketMessageSender} 实现类
 *
 * 注意：仅仅适合单机场景！！！
 *
 * @author futech.co.jp
 */
public class LocalWebSocketMessageSender extends AbstractWebSocketMessageSender {

    public LocalWebSocketMessageSender(WebSocketSessionManager sessionManager) {
        super(sessionManager);
    }

}
