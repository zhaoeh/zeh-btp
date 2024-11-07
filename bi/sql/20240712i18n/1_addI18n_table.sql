-- 向菜单表增加i18n国际化标志字段
ALTER TABLE system_menu
    ADD i18n varchar(50) NULL COMMENT 'i18n国际化标签';

-- 新增 bi_messages_i18n 表，用于维护门户所有的字典数据国际化message
DROP TABLE IF EXISTS `bi_messages_i18n`;

CREATE TABLE `bi_messages_i18n`
(
    `id`      bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '响应码消息ID',
    `code`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT 'code',
    `message` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'message',
    `locale`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '语言环境',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `dict_messages_un` (`code`,`locale`)
) ENGINE=InnoDB AUTO_INCREMENT=770033 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='国际化消息表';