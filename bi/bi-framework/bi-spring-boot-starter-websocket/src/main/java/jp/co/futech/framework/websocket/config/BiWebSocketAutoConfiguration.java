package jp.co.futech.framework.websocket.config;

import jp.co.futech.framework.mq.redis.config.BiRedisMQConsumerAutoConfiguration;
import jp.co.futech.framework.mq.redis.core.RedisMQTemplate;
import jp.co.futech.framework.websocket.core.handler.JsonWebSocketMessageHandler;
import jp.co.futech.framework.websocket.core.listener.WebSocketMessageListener;
import jp.co.futech.framework.websocket.core.security.LoginUserHandshakeInterceptor;
import jp.co.futech.framework.websocket.core.sender.kafka.KafkaWebSocketMessageConsumer;
import jp.co.futech.framework.websocket.core.sender.kafka.KafkaWebSocketMessageSender;
import jp.co.futech.framework.websocket.core.sender.local.LocalWebSocketMessageSender;
import jp.co.futech.framework.websocket.core.sender.rabbitmq.RabbitMQWebSocketMessageConsumer;
import jp.co.futech.framework.websocket.core.sender.rabbitmq.RabbitMQWebSocketMessageSender;
import jp.co.futech.framework.websocket.core.sender.redis.RedisWebSocketMessageConsumer;
import jp.co.futech.framework.websocket.core.sender.redis.RedisWebSocketMessageSender;
import jp.co.futech.framework.websocket.core.sender.rocketmq.RocketMQWebSocketMessageConsumer;
import jp.co.futech.framework.websocket.core.sender.rocketmq.RocketMQWebSocketMessageSender;
import jp.co.futech.framework.websocket.core.session.WebSocketSessionHandlerDecorator;
import jp.co.futech.framework.websocket.core.session.WebSocketSessionManager;
import jp.co.futech.framework.websocket.core.session.WebSocketSessionManagerImpl;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.List;

/**
 * WebSocket 自动配置
 *
 * @author xingyu4j
 */
@AutoConfiguration(before = BiRedisMQConsumerAutoConfiguration.class) // before BiRedisMQConsumerAutoConfiguration 的原因是，需要保证 RedisWebSocketMessageConsumer 先创建，才能创建 RedisMessageListenerContainer
@EnableWebSocket // 开启 websocket
@ConditionalOnProperty(prefix = "bi.websocket", value = "enable", matchIfMissing = true) // 允许使用 bi.websocket.enable=false 禁用 websocket
@EnableConfigurationProperties(WebSocketProperties.class)
public class BiWebSocketAutoConfiguration {

    @Bean
    public WebSocketConfigurer webSocketConfigurer(HandshakeInterceptor[] handshakeInterceptors,
                                                   WebSocketHandler webSocketHandler,
                                                   WebSocketProperties webSocketProperties) {
        return registry -> registry
                // 添加 WebSocketHandler
                .addHandler(webSocketHandler, webSocketProperties.getPath())
                .addInterceptors(handshakeInterceptors)
                // 允许跨域，否则前端连接会直接断开
                .setAllowedOriginPatterns("*");
    }

    @Bean
    public HandshakeInterceptor handshakeInterceptor() {
        return new LoginUserHandshakeInterceptor();
    }

    @Bean
    public WebSocketHandler webSocketHandler(WebSocketSessionManager sessionManager,
                                             List<? extends WebSocketMessageListener<?>> messageListeners) {
        // 1. 创建 JsonWebSocketMessageHandler 对象，处理消息
        JsonWebSocketMessageHandler messageHandler = new JsonWebSocketMessageHandler(messageListeners);
        // 2. 创建 WebSocketSessionHandlerDecorator 对象，处理连接
        return new WebSocketSessionHandlerDecorator(messageHandler, sessionManager);
    }

    @Bean
    public WebSocketSessionManager webSocketSessionManager() {
        return new WebSocketSessionManagerImpl();
    }

    // ==================== Sender 相关 ====================

    @Configuration
    @ConditionalOnProperty(prefix = "bi.websocket", name = "sender-type", havingValue = "local", matchIfMissing = true)
    public class LocalWebSocketMessageSenderConfiguration {

        @Bean
        public LocalWebSocketMessageSender localWebSocketMessageSender(WebSocketSessionManager sessionManager) {
            return new LocalWebSocketMessageSender(sessionManager);
        }

    }

    @Configuration
    @ConditionalOnProperty(prefix = "bi.websocket", name = "sender-type", havingValue = "redis", matchIfMissing = true)
    public class RedisWebSocketMessageSenderConfiguration {

        @Bean
        public RedisWebSocketMessageSender redisWebSocketMessageSender(WebSocketSessionManager sessionManager,
                                                                       RedisMQTemplate redisMQTemplate) {
            return new RedisWebSocketMessageSender(sessionManager, redisMQTemplate);
        }

        @Bean
        public RedisWebSocketMessageConsumer redisWebSocketMessageConsumer(
                RedisWebSocketMessageSender redisWebSocketMessageSender) {
            return new RedisWebSocketMessageConsumer(redisWebSocketMessageSender);
        }

    }

    @Configuration
    @ConditionalOnProperty(prefix = "bi.websocket", name = "sender-type", havingValue = "rocketmq", matchIfMissing = true)
    public class RocketMQWebSocketMessageSenderConfiguration {

        @Bean
        public RocketMQWebSocketMessageSender rocketMQWebSocketMessageSender(
                WebSocketSessionManager sessionManager, RocketMQTemplate rocketMQTemplate,
                @Value("${bi.websocket.sender-rocketmq.topic}") String topic) {
            return new RocketMQWebSocketMessageSender(sessionManager, rocketMQTemplate, topic);
        }

        @Bean
        public RocketMQWebSocketMessageConsumer rocketMQWebSocketMessageConsumer(
                RocketMQWebSocketMessageSender rocketMQWebSocketMessageSender) {
            return new RocketMQWebSocketMessageConsumer(rocketMQWebSocketMessageSender);
        }

    }

    @Configuration
    @ConditionalOnProperty(prefix = "bi.websocket", name = "sender-type", havingValue = "rabbitmq", matchIfMissing = true)
    public class RabbitMQWebSocketMessageSenderConfiguration {

        @Bean
        public RabbitMQWebSocketMessageSender rabbitMQWebSocketMessageSender(
                WebSocketSessionManager sessionManager, RabbitTemplate rabbitTemplate,
                TopicExchange websocketTopicExchange) {
            return new RabbitMQWebSocketMessageSender(sessionManager, rabbitTemplate, websocketTopicExchange);
        }

        @Bean
        public RabbitMQWebSocketMessageConsumer rabbitMQWebSocketMessageConsumer(
                RabbitMQWebSocketMessageSender rabbitMQWebSocketMessageSender) {
            return new RabbitMQWebSocketMessageConsumer(rabbitMQWebSocketMessageSender);
        }

        /**
         * 创建 Topic Exchange
         */
        @Bean
        public TopicExchange websocketTopicExchange(@Value("${bi.websocket.sender-rabbitmq.exchange}") String exchange) {
            return new TopicExchange(exchange,
                    true,  // durable: 是否持久化
                    false);  // exclusive: 是否排它
        }

    }

    @Configuration
    @ConditionalOnProperty(prefix = "bi.websocket", name = "sender-type", havingValue = "kafka", matchIfMissing = true)
    public class KafkaWebSocketMessageSenderConfiguration {

        @Bean
        public KafkaWebSocketMessageSender kafkaWebSocketMessageSender(
                WebSocketSessionManager sessionManager, KafkaTemplate<Object, Object> kafkaTemplate,
                @Value("${bi.websocket.sender-kafka.topic}") String topic) {
            return new KafkaWebSocketMessageSender(sessionManager, kafkaTemplate, topic);
        }

        @Bean
        public KafkaWebSocketMessageConsumer kafkaWebSocketMessageConsumer(
                KafkaWebSocketMessageSender kafkaWebSocketMessageSender) {
            return new KafkaWebSocketMessageConsumer(kafkaWebSocketMessageSender);
        }

    }

}