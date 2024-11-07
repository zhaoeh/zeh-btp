ALTER TABLE futech_bi.bi_messages_i18n MODIFY COLUMN code varchar (50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'code 000段表示spring validation校验语；100段表示静态字典值；200段表示业务异常提示语；300段表示菜单显示语';

CREATE TABLE `bi_messages_i18n_history`
(
    `id`      bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '响应码消息ID',
    `code`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT 'code',
    `message` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'message',
    `locale`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '语言环境',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=109635 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='国际化消息历史表';

