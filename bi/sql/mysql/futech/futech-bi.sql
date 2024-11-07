/*
 Navicat Premium Data Transfer

 Source Server         : 本机环境
 Source Server Type    : MySQL
 Source Server Version : 80300 (8.3.0)
 Source Host           : localhost:3306
 Source Schema         : futech-bi

 Target Server Type    : MySQL
 Target Server Version : 80300 (8.3.0)
 File Encoding         : 65001

 Date: 01/03/2024 11:16:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for infra_api_access_log
-- ----------------------------
DROP TABLE IF EXISTS `infra_api_access_log`;
CREATE TABLE `infra_api_access_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `trace_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '链路追踪编号',
  `user_id` bigint NOT NULL DEFAULT 0 COMMENT '用户编号',
  `user_type` tinyint NOT NULL DEFAULT 0 COMMENT '用户类型',
  `application_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '应用名',
  `request_method` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '请求方法名',
  `request_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '请求地址',
  `request_params` varchar(8000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '请求参数',
  `user_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户 IP',
  `user_agent` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '浏览器 UA',
  `begin_time` datetime NOT NULL COMMENT '开始请求时间',
  `end_time` datetime NOT NULL COMMENT '结束请求时间',
  `duration` int NOT NULL COMMENT '执行时长',
  `result_code` int NOT NULL DEFAULT 0 COMMENT '结果码',
  `result_msg` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '结果提示',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_create_time`(`create_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35832 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'API 访问日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of infra_api_access_log
-- ----------------------------

-- ----------------------------
-- Table structure for infra_api_error_log
-- ----------------------------
DROP TABLE IF EXISTS `infra_api_error_log`;
CREATE TABLE `infra_api_error_log`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `trace_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '链路追踪编号\n     *\n     * 一般来说，通过链路追踪编号，可以将访问日志，错误日志，链路追踪日志，logger 打印日志等，结合在一起，从而进行排错。',
  `user_id` int NOT NULL DEFAULT 0 COMMENT '用户编号',
  `user_type` tinyint NOT NULL DEFAULT 0 COMMENT '用户类型',
  `application_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '应用名\n     *\n     * 目前读取 spring.application.name',
  `request_method` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '请求方法名',
  `request_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '请求地址',
  `request_params` varchar(8000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '请求参数',
  `user_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户 IP',
  `user_agent` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '浏览器 UA',
  `exception_time` datetime NOT NULL COMMENT '异常发生时间',
  `exception_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '异常名\n     *\n     * {@link Throwable#getClass()} 的类全名',
  `exception_message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '异常导致的消息\n     *\n     * {@link cn.iocoder.common.framework.util.ExceptionUtil#getMessage(Throwable)}',
  `exception_root_cause_message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '异常导致的根消息\n     *\n     * {@link cn.iocoder.common.framework.util.ExceptionUtil#getRootCauseMessage(Throwable)}',
  `exception_stack_trace` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '异常的栈轨迹\n     *\n     * {@link cn.iocoder.common.framework.util.ExceptionUtil#getServiceException(Exception)}',
  `exception_class_name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '异常发生的类全名\n     *\n     * {@link StackTraceElement#getClassName()}',
  `exception_file_name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '异常发生的类文件\n     *\n     * {@link StackTraceElement#getFileName()}',
  `exception_method_name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '异常发生的方法名\n     *\n     * {@link StackTraceElement#getMethodName()}',
  `exception_line_number` int NOT NULL COMMENT '异常发生的方法所在行\n     *\n     * {@link StackTraceElement#getLineNumber()}',
  `process_status` tinyint NOT NULL COMMENT '处理状态',
  `process_time` datetime NULL DEFAULT NULL COMMENT '处理时间',
  `process_user_id` int NULL DEFAULT 0 COMMENT '处理用户编号',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15093 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统异常日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of infra_api_error_log
-- ----------------------------

-- ----------------------------
-- Table structure for infra_codegen_column
-- ----------------------------
DROP TABLE IF EXISTS `infra_codegen_column`;
CREATE TABLE `infra_codegen_column`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` bigint NOT NULL COMMENT '表编号',
  `column_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字段名',
  `data_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字段类型',
  `column_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字段描述',
  `nullable` bit(1) NOT NULL COMMENT '是否允许为空',
  `primary_key` bit(1) NOT NULL COMMENT '是否主键',
  `auto_increment` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '是否自增',
  `ordinal_position` int NOT NULL COMMENT '排序',
  `java_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Java 属性类型',
  `java_field` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Java 属性名',
  `dict_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '字典类型',
  `example` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '数据示例',
  `create_operation` bit(1) NOT NULL COMMENT '是否为 Create 创建操作的字段',
  `update_operation` bit(1) NOT NULL COMMENT '是否为 Update 更新操作的字段',
  `list_operation` bit(1) NOT NULL COMMENT '是否为 List 查询操作的字段',
  `list_operation_condition` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '=' COMMENT 'List 查询操作的条件类型',
  `list_operation_result` bit(1) NOT NULL COMMENT '是否为 List 查询操作的返回字段',
  `html_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '显示类型',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2015 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '代码生成表字段定义' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of infra_codegen_column
-- ----------------------------

-- ----------------------------
-- Table structure for infra_codegen_table
-- ----------------------------
DROP TABLE IF EXISTS `infra_codegen_table`;
CREATE TABLE `infra_codegen_table`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `data_source_config_id` bigint NOT NULL COMMENT '数据源配置的编号',
  `scene` tinyint NOT NULL DEFAULT 1 COMMENT '生成场景',
  `table_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '表描述',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `module_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模块名',
  `business_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '业务名',
  `class_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '类名称',
  `class_comment` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '类描述',
  `author` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '作者',
  `template_type` tinyint NOT NULL DEFAULT 1 COMMENT '模板类型',
  `front_type` tinyint NOT NULL COMMENT '前端类型',
  `parent_menu_id` bigint NULL DEFAULT NULL COMMENT '父菜单编号',
  `master_table_id` bigint NULL DEFAULT NULL COMMENT '主表的编号',
  `sub_join_column_id` bigint NULL DEFAULT NULL COMMENT '子表关联主表的字段编号',
  `sub_join_many` bit(1) NULL DEFAULT NULL COMMENT '主表与子表是否一对多',
  `tree_parent_column_id` bigint NULL DEFAULT NULL COMMENT '树表的父字段编号',
  `tree_name_column_id` bigint NULL DEFAULT NULL COMMENT '树表的名字字段编号',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 156 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '代码生成表定义' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of infra_codegen_table
-- ----------------------------

-- ----------------------------
-- Table structure for infra_config
-- ----------------------------
DROP TABLE IF EXISTS `infra_config`;
CREATE TABLE `infra_config`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `category` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '参数分组',
  `type` tinyint NOT NULL COMMENT '参数类型',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '参数键名',
  `value` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '参数键值',
  `visible` bit(1) NOT NULL COMMENT '是否可见',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '参数配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of infra_config
-- ----------------------------
INSERT INTO `infra_config` VALUES (2, 'biz', 1, '用户管理-账号初始密码', 'sys.user.init-password', '123456', b'0', '初始化密码 123456', 'admin', '2021-01-05 17:03:48', '1', '2022-03-20 02:25:51', b'0');
INSERT INTO `infra_config` VALUES (7, 'url', 2, 'MySQL 监控的地址', 'url.druid', '', b'1', '', '1', '2023-04-07 13:41:16', '1', '2023-04-07 14:33:38', b'0');
INSERT INTO `infra_config` VALUES (8, 'url', 2, 'SkyWalking 监控的地址', 'url.skywalking', '', b'1', '', '1', '2023-04-07 13:41:16', '1', '2023-04-07 14:57:03', b'0');
INSERT INTO `infra_config` VALUES (9, 'url', 2, 'Spring Boot Admin 监控的地址', 'url.spring-boot-admin', '', b'1', '', '1', '2023-04-07 13:41:16', '1', '2023-04-07 14:52:07', b'0');
INSERT INTO `infra_config` VALUES (10, 'url', 2, 'Swagger 接口文档的地址', 'url.swagger', '', b'1', '', '1', '2023-04-07 13:41:16', '1', '2023-04-07 14:59:00', b'0');
INSERT INTO `infra_config` VALUES (11, 'ui', 2, '腾讯地图 key', 'tencent.lbs.key', 'TVDBZ-TDILD-4ON4B-PFDZA-RNLKH-VVF6E', b'1', '腾讯地图 key', '1', '2023-06-03 19:16:27', '1', '2023-06-03 19:16:27', b'0');
INSERT INTO `infra_config` VALUES (12, 'test2', 2, 'test3', 'test4', 'test5', b'1', 'test6', '1', '2023-12-03 09:55:16', '1', '2023-12-03 09:55:27', b'0');

-- ----------------------------
-- Table structure for infra_data_source_config
-- ----------------------------
DROP TABLE IF EXISTS `infra_data_source_config`;
CREATE TABLE `infra_data_source_config`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键编号',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '参数名称',
  `url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数据源连接',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '密码',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '数据源配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of infra_data_source_config
-- ----------------------------
INSERT INTO `infra_data_source_config` VALUES (14, 'futech-bi-1', 'jdbc:mysql://127.0.0.1:3306/futech-bi-1?allowMultiQueries=true&useUnicode=true&useSSL=false&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&autoReconnect=true&nullCatalogMeansCurrent=true', 'root', 'epmpRAaR2T0FWW9J6yYSBQ==', '1', '2024-02-17 13:35:23', '1', '2024-02-17 13:35:23', b'0');

-- ----------------------------
-- Table structure for infra_demo01_contact
-- ----------------------------
DROP TABLE IF EXISTS `infra_demo01_contact`;
CREATE TABLE `infra_demo01_contact`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '名字',
  `sex` tinyint(1) NOT NULL COMMENT '性别',
  `birthday` datetime NOT NULL COMMENT '出生年',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '简介',
  `avatar` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '示例联系人表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of infra_demo01_contact
-- ----------------------------
INSERT INTO `infra_demo01_contact` VALUES (1, '土豆', 2, '2023-11-07 00:00:00', '<p>天蚕土豆！呀</p>', 'http://127.0.0.1:48080/admin-api/infra/file/4/get/46f8fa1a37db3f3960d8910ff2fe3962ab3b2db87cf2f8ccb4dc8145b8bdf237.jpeg', '1', '2023-11-15 23:34:30', '1', '2023-11-15 23:47:39', b'0', 1);

-- ----------------------------
-- Table structure for infra_demo02_category
-- ----------------------------
DROP TABLE IF EXISTS `infra_demo02_category`;
CREATE TABLE `infra_demo02_category`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '名字',
  `parent_id` bigint NOT NULL COMMENT '父级编号',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '示例分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of infra_demo02_category
-- ----------------------------
INSERT INTO `infra_demo02_category` VALUES (1, '土豆', 0, '1', '2023-11-15 23:34:30', '1', '2023-11-16 20:24:23', b'0', 1);
INSERT INTO `infra_demo02_category` VALUES (2, '番茄', 0, '1', '2023-11-16 20:24:00', '1', '2023-11-16 20:24:15', b'0', 1);
INSERT INTO `infra_demo02_category` VALUES (3, '怪怪', 0, '1', '2023-11-16 20:24:32', '1', '2023-11-16 20:24:32', b'0', 1);
INSERT INTO `infra_demo02_category` VALUES (4, '小番茄', 2, '1', '2023-11-16 20:24:39', '1', '2023-11-16 20:24:39', b'0', 1);
INSERT INTO `infra_demo02_category` VALUES (5, '大番茄', 2, '1', '2023-11-16 20:24:46', '1', '2023-11-16 20:24:46', b'0', 1);
INSERT INTO `infra_demo02_category` VALUES (6, '11', 3, '1', '2023-11-24 19:29:34', '1', '2023-11-24 19:29:34', b'0', 1);

-- ----------------------------
-- Table structure for infra_demo03_course
-- ----------------------------
DROP TABLE IF EXISTS `infra_demo03_course`;
CREATE TABLE `infra_demo03_course`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `student_id` bigint NOT NULL COMMENT '学生编号',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '名字',
  `score` tinyint NOT NULL COMMENT '分数',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '学生课程表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of infra_demo03_course
-- ----------------------------
INSERT INTO `infra_demo03_course` VALUES (2, 2, '语文', 66, '1', '2023-11-16 23:21:49', '1', '2023-11-16 23:21:49', b'0', 1);
INSERT INTO `infra_demo03_course` VALUES (3, 2, '数学', 22, '1', '2023-11-16 23:21:49', '1', '2023-11-16 23:21:49', b'0', 1);
INSERT INTO `infra_demo03_course` VALUES (6, 5, '体育', 23, '1', '2023-11-16 23:22:46', '1', '2023-11-16 15:44:40', b'1', 1);
INSERT INTO `infra_demo03_course` VALUES (7, 5, '计算机', 11, '1', '2023-11-16 23:22:46', '1', '2023-11-16 15:44:40', b'1', 1);
INSERT INTO `infra_demo03_course` VALUES (8, 5, '体育', 23, '1', '2023-11-16 23:22:46', '1', '2023-11-16 15:47:09', b'1', 1);
INSERT INTO `infra_demo03_course` VALUES (9, 5, '计算机', 11, '1', '2023-11-16 23:22:46', '1', '2023-11-16 15:47:09', b'1', 1);
INSERT INTO `infra_demo03_course` VALUES (10, 5, '体育', 23, '1', '2023-11-16 23:22:46', '1', '2023-11-16 23:47:10', b'0', 1);
INSERT INTO `infra_demo03_course` VALUES (11, 5, '计算机', 11, '1', '2023-11-16 23:22:46', '1', '2023-11-16 23:47:10', b'0', 1);
INSERT INTO `infra_demo03_course` VALUES (12, 2, '电脑', 33, '1', '2023-11-17 00:20:42', '1', '2023-11-16 16:20:45', b'1', 1);
INSERT INTO `infra_demo03_course` VALUES (13, 9, '滑雪', 12, '1', '2023-11-17 13:13:20', '1', '2023-11-17 13:13:20', b'0', 1);

-- ----------------------------
-- Table structure for infra_demo03_grade
-- ----------------------------
DROP TABLE IF EXISTS `infra_demo03_grade`;
CREATE TABLE `infra_demo03_grade`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `student_id` bigint NOT NULL COMMENT '学生编号',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '名字',
  `teacher` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '班主任',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '学生班级表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of infra_demo03_grade
-- ----------------------------
INSERT INTO `infra_demo03_grade` VALUES (7, 2, '三年 2 班', '周杰伦', '1', '2023-11-16 23:21:49', '1', '2023-11-16 23:21:49', b'0', 1);
INSERT INTO `infra_demo03_grade` VALUES (8, 5, '华为', '遥遥领先', '1', '2023-11-16 23:22:46', '1', '2023-11-16 23:47:10', b'0', 1);
INSERT INTO `infra_demo03_grade` VALUES (9, 9, '小图', '小娃111', '1', '2023-11-17 13:10:23', '1', '2023-11-17 13:10:23', b'0', 1);

-- ----------------------------
-- Table structure for infra_demo03_student
-- ----------------------------
DROP TABLE IF EXISTS `infra_demo03_student`;
CREATE TABLE `infra_demo03_student`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '名字',
  `sex` tinyint NOT NULL COMMENT '性别',
  `birthday` datetime NOT NULL COMMENT '出生日期',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '简介',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '学生表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of infra_demo03_student
-- ----------------------------
INSERT INTO `infra_demo03_student` VALUES (2, '小白', 1, '2023-11-16 00:00:00', '<p>厉害</p>', '1', '2023-11-16 23:21:49', '1', '2023-11-17 16:49:06', b'0', 1);
INSERT INTO `infra_demo03_student` VALUES (5, '大黑', 2, '2023-11-13 00:00:00', '<p>你在教我做事?</p>', '1', '2023-11-16 23:22:46', '1', '2023-11-17 16:49:07', b'0', 1);
INSERT INTO `infra_demo03_student` VALUES (9, '小花', 1, '2023-11-07 00:00:00', '<p>哈哈哈</p>', '1', '2023-11-17 00:04:47', '1', '2023-11-17 16:49:08', b'0', 1);

-- ----------------------------
-- Table structure for infra_file
-- ----------------------------
DROP TABLE IF EXISTS `infra_file`;
CREATE TABLE `infra_file`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '文件编号',
  `config_id` bigint NULL DEFAULT NULL COMMENT '配置编号',
  `name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '文件名',
  `path` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件路径',
  `url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件 URL',
  `type` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '文件类型',
  `size` int NOT NULL COMMENT '文件大小',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1242 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '文件表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of infra_file
-- ----------------------------

-- ----------------------------
-- Table structure for infra_file_config
-- ----------------------------
DROP TABLE IF EXISTS `infra_file_config`;
CREATE TABLE `infra_file_config`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '配置名',
  `storage` tinyint NOT NULL COMMENT '存储器',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `master` bit(1) NOT NULL COMMENT '是否为主配置',
  `config` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '存储配置',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '文件配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of infra_file_config
-- ----------------------------
INSERT INTO `infra_file_config` VALUES (4, '数据库', 1, '我是数据库', b'0', '{\"@class\":\"jp.co.futech.framework.file.core.client.db.DBFileClientConfig\",\"domain\":\"http://127.0.0.1:48080\"}', '1', '2022-03-15 23:56:24', '1', '2024-01-13 22:24:06', b'0');
INSERT INTO `infra_file_config` VALUES (22, '七牛存储器', 20, '', b'1', '{\"@class\":\"jp.co.futech.framework.file.core.client.s3.S3FileClientConfig\",\"endpoint\":\"s3.cn-south-1.qiniucs.com\",\"domain\":\"http://test.bi.iocoder.cn\",\"bucket\":\"ruoyi-vue-pro\",\"accessKey\":\"3TvrJ70gl2Gt6IBe7_IZT1F6i_k0iMuRtyEv4EyS\",\"accessSecret\":\"wd0tbVBYlp0S-ihA8Qg2hPLncoP83wyrIq24OZuY\"}', '1', '2024-01-13 22:11:12', '1', '2024-01-13 22:24:06', b'0');

-- ----------------------------
-- Table structure for infra_file_content
-- ----------------------------
DROP TABLE IF EXISTS `infra_file_content`;
CREATE TABLE `infra_file_content`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `config_id` bigint NOT NULL COMMENT '配置编号',
  `path` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件路径',
  `content` mediumblob NOT NULL COMMENT '文件内容',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 274 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '文件表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of infra_file_content
-- ----------------------------

-- ----------------------------
-- Table structure for infra_job
-- ----------------------------
DROP TABLE IF EXISTS `infra_job`;
CREATE TABLE `infra_job`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务编号',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '任务名称',
  `status` tinyint NOT NULL COMMENT '任务状态',
  `handler_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '处理器的名字',
  `handler_param` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '处理器的参数',
  `cron_expression` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'CRON 表达式',
  `retry_count` int NOT NULL DEFAULT 0 COMMENT '重试次数',
  `retry_interval` int NOT NULL DEFAULT 0 COMMENT '重试间隔',
  `monitor_timeout` int NOT NULL DEFAULT 0 COMMENT '监控超时时间',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '定时任务表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of infra_job
-- ----------------------------
INSERT INTO `infra_job` VALUES (5, '支付通知 Job', 2, 'payNotifyJob', NULL, '* * * * * ?', 0, 0, 0, '1', '2021-10-27 08:34:42', '1', '2023-07-09 20:51:41', b'0');
INSERT INTO `infra_job` VALUES (17, '支付订单同步 Job', 2, 'payOrderSyncJob', NULL, '0 0/1 * * * ?', 0, 0, 0, '1', '2023-07-22 14:36:26', '1', '2023-07-22 15:39:08', b'0');
INSERT INTO `infra_job` VALUES (18, '支付订单过期 Job', 2, 'payOrderExpireJob', NULL, '0 0/1 * * * ?', 0, 0, 0, '1', '2023-07-22 15:36:23', '1', '2023-07-22 15:39:54', b'0');
INSERT INTO `infra_job` VALUES (19, '退款订单的同步 Job', 2, 'payRefundSyncJob', NULL, '0 0/1 * * * ?', 0, 0, 0, '1', '2023-07-23 21:03:44', '1', '2023-07-23 21:09:00', b'0');
INSERT INTO `infra_job` VALUES (20, 'Job 示例', 2, 'demoJob', '', '0 0 0 * * ?', 1, 10, 0, '1', '2023-09-16 14:01:23', '1', '2023-12-07 19:34:08', b'0');
INSERT INTO `infra_job` VALUES (21, '交易订单的自动过期 Job', 2, 'tradeOrderAutoCancelJob', '', '0 * * * * ?', 3, 0, 0, '1', '2023-09-25 23:43:26', '1', '2023-09-26 19:23:30', b'0');
INSERT INTO `infra_job` VALUES (22, '交易订单的自动收货 Job', 2, 'tradeOrderAutoReceiveJob', '', '0 * * * * ?', 3, 0, 0, '1', '2023-09-26 19:23:53', '1', '2023-09-26 23:38:08', b'0');
INSERT INTO `infra_job` VALUES (23, '交易订单的自动评论 Job', 2, 'tradeOrderAutoCommentJob', '', '0 * * * * ?', 3, 0, 0, '1', '2023-09-26 23:38:29', '1', '2023-09-27 11:03:10', b'0');
INSERT INTO `infra_job` VALUES (24, '佣金解冻 Job', 2, 'brokerageRecordUnfreezeJob', '', '0 * * * * ?', 3, 0, 0, '1', '2023-09-28 22:01:46', '1', '2023-09-28 22:01:56', b'0');
INSERT INTO `infra_job` VALUES (25, '访问日志清理 Job', 2, 'accessLogCleanJob', '', '0 0 0 * * ?', 3, 0, 0, '1', '2023-10-03 10:59:41', '1', '2023-10-03 11:01:10', b'0');
INSERT INTO `infra_job` VALUES (26, '错误日志清理 Job', 2, 'errorLogCleanJob', '', '0 0 0 * * ?', 3, 0, 0, '1', '2023-10-03 11:00:43', '1', '2023-10-03 11:01:12', b'0');
INSERT INTO `infra_job` VALUES (27, '任务日志清理 Job', 2, 'jobLogCleanJob', '', '0 0 0 * * ?', 3, 0, 0, '1', '2023-10-03 11:01:33', '1', '2023-10-03 11:01:42', b'0');

-- ----------------------------
-- Table structure for infra_job_log
-- ----------------------------
DROP TABLE IF EXISTS `infra_job_log`;
CREATE TABLE `infra_job_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志编号',
  `job_id` bigint NOT NULL COMMENT '任务编号',
  `handler_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '处理器的名字',
  `handler_param` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '处理器的参数',
  `execute_index` tinyint NOT NULL DEFAULT 1 COMMENT '第几次执行',
  `begin_time` datetime NOT NULL COMMENT '开始执行时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '结束执行时间',
  `duration` int NULL DEFAULT NULL COMMENT '执行时长',
  `status` tinyint NOT NULL COMMENT '任务状态',
  `result` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '结果数据',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 235 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '定时任务日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of infra_job_log
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `BLOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `SCHED_NAME`(`SCHED_NAME` ASC, `TRIGGER_NAME` ASC, `TRIGGER_GROUP` ASC) USING BTREE,
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `CALENDAR_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `CRON_EXPRESSION` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TIME_ZONE_ID` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
INSERT INTO `qrtz_cron_triggers` VALUES ('schedulerName', 'accessLogCleanJob', 'DEFAULT', '0 0 0 * * ?', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` VALUES ('schedulerName', 'brokerageRecordUnfreezeJob', 'DEFAULT', '0 * * * * ?', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` VALUES ('schedulerName', 'demoJob', 'DEFAULT', '0 0 0 * * ?', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` VALUES ('schedulerName', 'errorLogCleanJob', 'DEFAULT', '0 0 0 * * ?', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` VALUES ('schedulerName', 'jobLogCleanJob', 'DEFAULT', '0 0 0 * * ?', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` VALUES ('schedulerName', 'payNotifyJob', 'DEFAULT', '* * * * * ?', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` VALUES ('schedulerName', 'payOrderExpireJob', 'DEFAULT', '0 0/1 * * * ?', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` VALUES ('schedulerName', 'payOrderSyncJob', 'DEFAULT', '0 0/1 * * * ?', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` VALUES ('schedulerName', 'payRefundSyncJob', 'DEFAULT', '0 0/1 * * * ?', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` VALUES ('schedulerName', 'tradeOrderAutoCancelJob', 'DEFAULT', '0 * * * * ?', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` VALUES ('schedulerName', 'tradeOrderAutoCommentJob', 'DEFAULT', '0 * * * * ?', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` VALUES ('schedulerName', 'tradeOrderAutoReceiveJob', 'DEFAULT', '0 * * * * ?', 'Asia/Shanghai');

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `ENTRY_ID` varchar(95) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `INSTANCE_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `FIRED_TIME` bigint NOT NULL,
  `SCHED_TIME` bigint NOT NULL,
  `PRIORITY` int NOT NULL,
  `STATE` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `JOB_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `JOB_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `ENTRY_ID`) USING BTREE,
  INDEX `IDX_QRTZ_FT_TRIG_INST_NAME`(`SCHED_NAME` ASC, `INSTANCE_NAME` ASC) USING BTREE,
  INDEX `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY`(`SCHED_NAME` ASC, `INSTANCE_NAME` ASC, `REQUESTS_RECOVERY` ASC) USING BTREE,
  INDEX `IDX_QRTZ_FT_J_G`(`SCHED_NAME` ASC, `JOB_NAME` ASC, `JOB_GROUP` ASC) USING BTREE,
  INDEX `IDX_QRTZ_FT_JG`(`SCHED_NAME` ASC, `JOB_GROUP` ASC) USING BTREE,
  INDEX `IDX_QRTZ_FT_T_G`(`SCHED_NAME` ASC, `TRIGGER_NAME` ASC, `TRIGGER_GROUP` ASC) USING BTREE,
  INDEX `IDX_QRTZ_FT_TG`(`SCHED_NAME` ASC, `TRIGGER_GROUP` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `JOB_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `JOB_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `IS_DURABLE` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `IS_UPDATE_DATA` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_J_REQ_RECOVERY`(`SCHED_NAME` ASC, `REQUESTS_RECOVERY` ASC) USING BTREE,
  INDEX `IDX_QRTZ_J_GRP`(`SCHED_NAME` ASC, `JOB_GROUP` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
INSERT INTO `qrtz_job_details` VALUES ('schedulerName', 'accessLogCleanJob', 'DEFAULT', NULL, 'jp.co.futech.framework.quartz.core.handler.JobHandlerInvoker', '0', '1', '1', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000027400064A4F425F49447372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B020000787000000000000000197400104A4F425F48414E444C45525F4E414D457400116163636573734C6F67436C65616E4A6F627800);
INSERT INTO `qrtz_job_details` VALUES ('schedulerName', 'brokerageRecordUnfreezeJob', 'DEFAULT', NULL, 'jp.co.futech.framework.quartz.core.handler.JobHandlerInvoker', '0', '1', '1', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000027400064A4F425F49447372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B020000787000000000000000187400104A4F425F48414E444C45525F4E414D4574001A62726F6B65726167655265636F7264556E667265657A654A6F627800);
INSERT INTO `qrtz_job_details` VALUES ('schedulerName', 'demoJob', 'DEFAULT', NULL, 'jp.co.futech.framework.quartz.core.handler.JobHandlerInvoker', '0', '1', '1', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000027400064A4F425F49447372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B020000787000000000000000147400104A4F425F48414E444C45525F4E414D4574000764656D6F4A6F627800);
INSERT INTO `qrtz_job_details` VALUES ('schedulerName', 'errorLogCleanJob', 'DEFAULT', NULL, 'jp.co.futech.framework.quartz.core.handler.JobHandlerInvoker', '0', '1', '1', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000027400064A4F425F49447372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000000000001A7400104A4F425F48414E444C45525F4E414D457400106572726F724C6F67436C65616E4A6F627800);
INSERT INTO `qrtz_job_details` VALUES ('schedulerName', 'jobLogCleanJob', 'DEFAULT', NULL, 'jp.co.futech.framework.quartz.core.handler.JobHandlerInvoker', '0', '1', '1', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000027400064A4F425F49447372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000000000001B7400104A4F425F48414E444C45525F4E414D4574000E6A6F624C6F67436C65616E4A6F627800);
INSERT INTO `qrtz_job_details` VALUES ('schedulerName', 'payNotifyJob', 'DEFAULT', NULL, 'jp.co.futech.framework.quartz.core.handler.JobHandlerInvoker', '0', '1', '1', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000027400064A4F425F49447372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B020000787000000000000000057400104A4F425F48414E444C45525F4E414D4574000C7061794E6F746966794A6F627800);
INSERT INTO `qrtz_job_details` VALUES ('schedulerName', 'payOrderExpireJob', 'DEFAULT', NULL, 'jp.co.futech.framework.quartz.core.handler.JobHandlerInvoker', '0', '1', '1', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000027400064A4F425F49447372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B020000787000000000000000127400104A4F425F48414E444C45525F4E414D457400117061794F726465724578706972654A6F627800);
INSERT INTO `qrtz_job_details` VALUES ('schedulerName', 'payOrderSyncJob', 'DEFAULT', NULL, 'jp.co.futech.framework.quartz.core.handler.JobHandlerInvoker', '0', '1', '1', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000027400064A4F425F49447372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B020000787000000000000000117400104A4F425F48414E444C45525F4E414D4574000F7061794F7264657253796E634A6F627800);
INSERT INTO `qrtz_job_details` VALUES ('schedulerName', 'payRefundSyncJob', 'DEFAULT', NULL, 'jp.co.futech.framework.quartz.core.handler.JobHandlerInvoker', '0', '1', '1', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000027400064A4F425F49447372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B020000787000000000000000137400104A4F425F48414E444C45525F4E414D45740010706179526566756E6453796E634A6F627800);
INSERT INTO `qrtz_job_details` VALUES ('schedulerName', 'tradeOrderAutoCancelJob', 'DEFAULT', NULL, 'jp.co.futech.framework.quartz.core.handler.JobHandlerInvoker', '0', '1', '1', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000027400064A4F425F49447372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B020000787000000000000000157400104A4F425F48414E444C45525F4E414D4574001774726164654F726465724175746F43616E63656C4A6F627800);
INSERT INTO `qrtz_job_details` VALUES ('schedulerName', 'tradeOrderAutoCommentJob', 'DEFAULT', NULL, 'jp.co.futech.framework.quartz.core.handler.JobHandlerInvoker', '0', '1', '1', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000027400064A4F425F49447372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B020000787000000000000000177400104A4F425F48414E444C45525F4E414D4574001874726164654F726465724175746F436F6D6D656E744A6F627800);
INSERT INTO `qrtz_job_details` VALUES ('schedulerName', 'tradeOrderAutoReceiveJob', 'DEFAULT', NULL, 'jp.co.futech.framework.quartz.core.handler.JobHandlerInvoker', '0', '1', '1', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000027400064A4F425F49447372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B020000787000000000000000167400104A4F425F48414E444C45525F4E414D4574001874726164654F726465724175746F526563656976654A6F627800);

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `LOCK_NAME` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `LOCK_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('schedulerName', 'STATE_ACCESS');
INSERT INTO `qrtz_locks` VALUES ('schedulerName', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `INSTANCE_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `LAST_CHECKIN_TIME` bigint NOT NULL,
  `CHECKIN_INTERVAL` bigint NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
INSERT INTO `qrtz_scheduler_state` VALUES ('schedulerName', 'MacBook-Pro.local1701948801197', 1701948848400, 15000);

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `REPEAT_COUNT` bigint NOT NULL,
  `REPEAT_INTERVAL` bigint NOT NULL,
  `TIMES_TRIGGERED` bigint NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `STR_PROP_1` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `STR_PROP_2` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `STR_PROP_3` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `INT_PROP_1` int NULL DEFAULT NULL,
  `INT_PROP_2` int NULL DEFAULT NULL,
  `LONG_PROP_1` bigint NULL DEFAULT NULL,
  `LONG_PROP_2` bigint NULL DEFAULT NULL,
  `DEC_PROP_1` decimal(13, 4) NULL DEFAULT NULL,
  `DEC_PROP_2` decimal(13, 4) NULL DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `JOB_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `JOB_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint NULL DEFAULT NULL,
  `PREV_FIRE_TIME` bigint NULL DEFAULT NULL,
  `PRIORITY` int NULL DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_TYPE` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `START_TIME` bigint NOT NULL,
  `END_TIME` bigint NULL DEFAULT NULL,
  `CALENDAR_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `MISFIRE_INSTR` smallint NULL DEFAULT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_J`(`SCHED_NAME` ASC, `JOB_NAME` ASC, `JOB_GROUP` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_JG`(`SCHED_NAME` ASC, `JOB_GROUP` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_C`(`SCHED_NAME` ASC, `CALENDAR_NAME` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_G`(`SCHED_NAME` ASC, `TRIGGER_GROUP` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_STATE`(`SCHED_NAME` ASC, `TRIGGER_STATE` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_N_STATE`(`SCHED_NAME` ASC, `TRIGGER_NAME` ASC, `TRIGGER_GROUP` ASC, `TRIGGER_STATE` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_N_G_STATE`(`SCHED_NAME` ASC, `TRIGGER_GROUP` ASC, `TRIGGER_STATE` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_NEXT_FIRE_TIME`(`SCHED_NAME` ASC, `NEXT_FIRE_TIME` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST`(`SCHED_NAME` ASC, `TRIGGER_STATE` ASC, `NEXT_FIRE_TIME` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_MISFIRE`(`SCHED_NAME` ASC, `MISFIRE_INSTR` ASC, `NEXT_FIRE_TIME` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE`(`SCHED_NAME` ASC, `MISFIRE_INSTR` ASC, `NEXT_FIRE_TIME` ASC, `TRIGGER_STATE` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP`(`SCHED_NAME` ASC, `MISFIRE_INSTR` ASC, `NEXT_FIRE_TIME` ASC, `TRIGGER_GROUP` ASC, `TRIGGER_STATE` ASC) USING BTREE,
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
INSERT INTO `qrtz_triggers` VALUES ('schedulerName', 'accessLogCleanJob', 'DEFAULT', 'accessLogCleanJob', 'DEFAULT', NULL, 1696348800000, -1, 5, 'PAUSED', 'CRON', 1696301981000, 0, NULL, 0, 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000037400114A4F425F48414E444C45525F504152414D7400007400124A4F425F52455452595F494E54455256414C737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000074000F4A4F425F52455452595F434F554E547371007E000A000000037800);
INSERT INTO `qrtz_triggers` VALUES ('schedulerName', 'brokerageRecordUnfreezeJob', 'DEFAULT', 'brokerageRecordUnfreezeJob', 'DEFAULT', NULL, 1695909720000, -1, 5, 'PAUSED', 'CRON', 1695909706000, 0, NULL, 0, 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000037400114A4F425F48414E444C45525F504152414D7400007400124A4F425F52455452595F494E54455256414C737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000074000F4A4F425F52455452595F434F554E547371007E000A000000037800);
INSERT INTO `qrtz_triggers` VALUES ('schedulerName', 'demoJob', 'DEFAULT', 'demoJob', 'DEFAULT', NULL, 1701964800000, 1701948834291, 5, 'PAUSED', 'CRON', 1694844083000, 0, NULL, 0, 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000037400114A4F425F48414E444C45525F504152414D7400007400124A4F425F52455452595F494E54455256414C737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000A74000F4A4F425F52455452595F434F554E547371007E000A000000017800);
INSERT INTO `qrtz_triggers` VALUES ('schedulerName', 'errorLogCleanJob', 'DEFAULT', 'errorLogCleanJob', 'DEFAULT', NULL, 1696348800000, -1, 5, 'PAUSED', 'CRON', 1696302043000, 0, NULL, 0, 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000037400114A4F425F48414E444C45525F504152414D7400007400124A4F425F52455452595F494E54455256414C737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000074000F4A4F425F52455452595F434F554E547371007E000A000000037800);
INSERT INTO `qrtz_triggers` VALUES ('schedulerName', 'jobLogCleanJob', 'DEFAULT', 'jobLogCleanJob', 'DEFAULT', NULL, 1696348800000, -1, 5, 'PAUSED', 'CRON', 1696302092000, 0, NULL, 0, 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000037400114A4F425F48414E444C45525F504152414D7400007400124A4F425F52455452595F494E54455256414C737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000074000F4A4F425F52455452595F434F554E547371007E000A000000037800);
INSERT INTO `qrtz_triggers` VALUES ('schedulerName', 'payNotifyJob', 'DEFAULT', 'payNotifyJob', 'DEFAULT', NULL, 1688907102000, 1688907101000, 5, 'PAUSED', 'CRON', 1635294882000, 0, NULL, 0, 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000037400114A4F425F48414E444C45525F504152414D707400124A4F425F52455452595F494E54455256414C737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000074000F4A4F425F52455452595F434F554E5471007E000B7800);
INSERT INTO `qrtz_triggers` VALUES ('schedulerName', 'payOrderExpireJob', 'DEFAULT', 'payOrderExpireJob', 'DEFAULT', NULL, 1690011600000, -1, 5, 'PAUSED', 'CRON', 1690011553000, 0, NULL, 0, 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000037400114A4F425F48414E444C45525F504152414D707400124A4F425F52455452595F494E54455256414C737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000074000F4A4F425F52455452595F434F554E5471007E000B7800);
INSERT INTO `qrtz_triggers` VALUES ('schedulerName', 'payOrderSyncJob', 'DEFAULT', 'payOrderSyncJob', 'DEFAULT', NULL, 1690011600000, 1690011540000, 5, 'PAUSED', 'CRON', 1690007785000, 0, NULL, 0, 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000037400114A4F425F48414E444C45525F504152414D707400124A4F425F52455452595F494E54455256414C737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000074000F4A4F425F52455452595F434F554E5471007E000B7800);
INSERT INTO `qrtz_triggers` VALUES ('schedulerName', 'payRefundSyncJob', 'DEFAULT', 'payRefundSyncJob', 'DEFAULT', NULL, 1690117560000, 1690117500000, 5, 'PAUSED', 'CRON', 1690117424000, 0, NULL, 0, 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000037400114A4F425F48414E444C45525F504152414D707400124A4F425F52455452595F494E54455256414C737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000074000F4A4F425F52455452595F434F554E5471007E000B7800);
INSERT INTO `qrtz_triggers` VALUES ('schedulerName', 'tradeOrderAutoCancelJob', 'DEFAULT', 'tradeOrderAutoCancelJob', 'DEFAULT', NULL, 1695727440000, 1695727380000, 5, 'PAUSED', 'CRON', 1695656605000, 0, NULL, 0, 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000037400114A4F425F48414E444C45525F504152414D7400007400124A4F425F52455452595F494E54455256414C737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000074000F4A4F425F52455452595F434F554E547371007E000A000000037800);
INSERT INTO `qrtz_triggers` VALUES ('schedulerName', 'tradeOrderAutoCommentJob', 'DEFAULT', 'tradeOrderAutoCommentJob', 'DEFAULT', NULL, 1695783840000, 1695783780000, 5, 'PAUSED', 'CRON', 1695742709000, 0, NULL, 0, 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000037400114A4F425F48414E444C45525F504152414D7400007400124A4F425F52455452595F494E54455256414C737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000074000F4A4F425F52455452595F434F554E547371007E000A000000037800);
INSERT INTO `qrtz_triggers` VALUES ('schedulerName', 'tradeOrderAutoReceiveJob', 'DEFAULT', 'tradeOrderAutoReceiveJob', 'DEFAULT', NULL, 1695742740000, 1695742680000, 5, 'PAUSED', 'CRON', 1695727433000, 0, NULL, 0, 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000037400114A4F425F48414E444C45525F504152414D7400007400124A4F425F52455452595F494E54455256414C737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000074000F4A4F425F52455452595F434F554E547371007E000A000000037800);

-- ----------------------------
-- Table structure for system_dept
-- ----------------------------
DROP TABLE IF EXISTS `system_dept`;
CREATE TABLE `system_dept`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '部门名称',
  `parent_id` bigint NOT NULL DEFAULT 0 COMMENT '父部门id',
  `sort` int NOT NULL DEFAULT 0 COMMENT '显示顺序',
  `leader_user_id` bigint NULL DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` tinyint NOT NULL COMMENT '部门状态（0正常 1停用）',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 117 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_dept
-- ----------------------------
INSERT INTO `system_dept` VALUES (100, 'futech.co', 0, 0, 1, '15888888888', '', 0, 'admin', '2021-01-05 17:03:47', '1', '2024-02-28 18:24:22', b'0', 1);
INSERT INTO `system_dept` VALUES (101, '日本总公司', 100, 1, 104, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '1', '2024-02-28 18:24:34', b'0', 1);
INSERT INTO `system_dept` VALUES (102, '菲律宾分公司', 100, 2, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '1', '2024-02-28 18:24:47', b'0', 1);
INSERT INTO `system_dept` VALUES (103, '研发部门', 101, 1, 104, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '103', '2022-01-14 01:04:14', b'0', 1);
INSERT INTO `system_dept` VALUES (104, '市场部门', 101, 2, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '', '2021-12-15 05:01:38', b'0', 1);
INSERT INTO `system_dept` VALUES (105, '测试部门', 101, 3, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '1', '2022-05-16 20:25:15', b'0', 1);
INSERT INTO `system_dept` VALUES (106, '财务部门', 101, 4, 103, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '103', '2022-01-15 21:32:22', b'0', 1);
INSERT INTO `system_dept` VALUES (107, '运维部门', 101, 5, 1, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '1', '2023-12-02 09:28:22', b'0', 1);
INSERT INTO `system_dept` VALUES (108, '市场部门', 102, 1, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '1', '2022-02-16 08:35:45', b'0', 1);
INSERT INTO `system_dept` VALUES (109, '财务部门', 102, 2, NULL, '15888888888', 'ry@qq.com', 0, 'admin', '2021-01-05 17:03:47', '', '2021-12-15 05:01:29', b'0', 1);
INSERT INTO `system_dept` VALUES (110, '新部门', 0, 1, NULL, NULL, NULL, 0, '110', '2022-02-23 20:46:30', '110', '2022-02-23 20:46:30', b'0', 121);
INSERT INTO `system_dept` VALUES (111, '顶级部门', 0, 1, NULL, NULL, NULL, 0, '113', '2022-03-07 21:44:50', '113', '2022-03-07 21:44:50', b'0', 122);
INSERT INTO `system_dept` VALUES (112, '产品部门', 101, 100, 1, NULL, NULL, 1, '1', '2023-12-02 09:45:13', '1', '2023-12-02 09:45:31', b'0', 1);
INSERT INTO `system_dept` VALUES (113, '支持部门', 102, 3, 104, NULL, NULL, 1, '1', '2023-12-02 09:47:38', '1', '2023-12-02 09:47:38', b'0', 1);
INSERT INTO `system_dept` VALUES (114, 'google总部', 0, 1, 129, NULL, NULL, 0, '129', '2024-02-19 11:31:59', '129', '2024-02-28 18:48:39', b'0', 153);
INSERT INTO `system_dept` VALUES (115, '营销部', 114, 2, NULL, NULL, NULL, 0, '129', '2024-02-19 11:32:17', '129', '2024-02-28 18:48:55', b'0', 153);
INSERT INTO `system_dept` VALUES (116, '销售部', 114, 2, NULL, NULL, NULL, 0, '129', '2024-02-19 11:32:37', '129', '2024-02-28 18:49:02', b'0', 153);

-- ----------------------------
-- Table structure for system_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `system_dict_data`;
CREATE TABLE `system_dict_data`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `sort` int NOT NULL DEFAULT 0 COMMENT '字典排序',
  `label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典标签',
  `value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典类型',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
  `color_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '颜色类型',
  `css_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT 'css 样式',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1486 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_dict_data
-- ----------------------------
INSERT INTO `system_dict_data` VALUES (1, 1, '男', '1', 'system_user_sex', 0, 'default', 'A', '性别男', 'admin', '2021-01-05 17:03:48', '1', '2022-03-29 00:14:39', b'0');
INSERT INTO `system_dict_data` VALUES (2, 2, '女', '2', 'system_user_sex', 0, 'success', '', '性别女', 'admin', '2021-01-05 17:03:48', '1', '2023-11-15 23:30:37', b'0');
INSERT INTO `system_dict_data` VALUES (8, 1, '正常', '1', 'infra_job_status', 0, 'success', '', '正常状态', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 19:33:38', b'0');
INSERT INTO `system_dict_data` VALUES (9, 2, '暂停', '2', 'infra_job_status', 0, 'danger', '', '停用状态', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 19:33:45', b'0');
INSERT INTO `system_dict_data` VALUES (12, 1, '系统内置', '1', 'infra_config_type', 0, 'danger', '', '参数类型 - 系统内置', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 19:06:02', b'0');
INSERT INTO `system_dict_data` VALUES (13, 2, '自定义', '2', 'infra_config_type', 0, 'primary', '', '参数类型 - 自定义', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 19:06:07', b'0');
INSERT INTO `system_dict_data` VALUES (14, 1, '通知', '1', 'system_notice_type', 0, 'success', '', '通知', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 13:05:57', b'0');
INSERT INTO `system_dict_data` VALUES (15, 2, '公告', '2', 'system_notice_type', 0, 'info', '', '公告', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 13:06:01', b'0');
INSERT INTO `system_dict_data` VALUES (16, 0, '其它', '0', 'system_operate_type', 0, 'default', '', '其它操作', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 09:32:46', b'0');
INSERT INTO `system_dict_data` VALUES (17, 1, '查询', '1', 'system_operate_type', 0, 'info', '', '查询操作', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 09:33:16', b'0');
INSERT INTO `system_dict_data` VALUES (18, 2, '新增', '2', 'system_operate_type', 0, 'primary', '', '新增操作', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 09:33:13', b'0');
INSERT INTO `system_dict_data` VALUES (19, 3, '修改', '3', 'system_operate_type', 0, 'warning', '', '修改操作', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 09:33:22', b'0');
INSERT INTO `system_dict_data` VALUES (20, 4, '删除', '4', 'system_operate_type', 0, 'danger', '', '删除操作', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 09:33:27', b'0');
INSERT INTO `system_dict_data` VALUES (22, 5, '导出', '5', 'system_operate_type', 0, 'default', '', '导出操作', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 09:33:32', b'0');
INSERT INTO `system_dict_data` VALUES (23, 6, '导入', '6', 'system_operate_type', 0, 'default', '', '导入操作', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 09:33:35', b'0');
INSERT INTO `system_dict_data` VALUES (27, 1, '开启', '0', 'common_status', 0, 'primary', '', '开启状态', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 08:00:39', b'0');
INSERT INTO `system_dict_data` VALUES (28, 2, '关闭', '1', 'common_status', 0, 'info', '', '关闭状态', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 08:00:44', b'0');
INSERT INTO `system_dict_data` VALUES (29, 1, '目录', '1', 'system_menu_type', 0, '', '', '目录', 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:43:45', b'0');
INSERT INTO `system_dict_data` VALUES (30, 2, '菜单', '2', 'system_menu_type', 0, '', '', '菜单', 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:43:41', b'0');
INSERT INTO `system_dict_data` VALUES (31, 3, '按钮', '3', 'system_menu_type', 0, '', '', '按钮', 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:43:39', b'0');
INSERT INTO `system_dict_data` VALUES (32, 1, '内置', '1', 'system_role_type', 0, 'danger', '', '内置角色', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 13:02:08', b'0');
INSERT INTO `system_dict_data` VALUES (33, 2, '自定义', '2', 'system_role_type', 0, 'primary', '', '自定义角色', 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 13:02:12', b'0');
INSERT INTO `system_dict_data` VALUES (34, 1, '全部数据权限', '1', 'system_data_scope', 0, '', '', '全部数据权限', 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:47:17', b'0');
INSERT INTO `system_dict_data` VALUES (35, 2, '指定部门数据权限', '2', 'system_data_scope', 0, '', '', '指定部门数据权限', 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:47:18', b'0');
INSERT INTO `system_dict_data` VALUES (36, 3, '本部门数据权限', '3', 'system_data_scope', 0, '', '', '本部门数据权限', 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:47:16', b'0');
INSERT INTO `system_dict_data` VALUES (37, 4, '本部门及以下数据权限', '4', 'system_data_scope', 0, '', '', '本部门及以下数据权限', 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:47:21', b'0');
INSERT INTO `system_dict_data` VALUES (38, 5, '仅本人数据权限', '5', 'system_data_scope', 0, '', '', '仅本人数据权限', 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:47:23', b'0');
INSERT INTO `system_dict_data` VALUES (39, 0, '成功', '0', 'system_login_result', 0, 'success', '', '登陆结果 - 成功', '', '2021-01-18 06:17:36', '1', '2022-02-16 13:23:49', b'0');
INSERT INTO `system_dict_data` VALUES (40, 10, '账号或密码不正确', '10', 'system_login_result', 0, 'primary', '', '登陆结果 - 账号或密码不正确', '', '2021-01-18 06:17:54', '1', '2022-02-16 13:24:27', b'0');
INSERT INTO `system_dict_data` VALUES (41, 20, '用户被禁用', '20', 'system_login_result', 0, 'warning', '', '登陆结果 - 用户被禁用', '', '2021-01-18 06:17:54', '1', '2022-02-16 13:23:57', b'0');
INSERT INTO `system_dict_data` VALUES (42, 30, '验证码不存在', '30', 'system_login_result', 0, 'info', '', '登陆结果 - 验证码不存在', '', '2021-01-18 06:17:54', '1', '2022-02-16 13:24:07', b'0');
INSERT INTO `system_dict_data` VALUES (43, 31, '验证码不正确', '31', 'system_login_result', 0, 'info', '', '登陆结果 - 验证码不正确', '', '2021-01-18 06:17:54', '1', '2022-02-16 13:24:11', b'0');
INSERT INTO `system_dict_data` VALUES (44, 100, '未知异常', '100', 'system_login_result', 0, 'danger', '', '登陆结果 - 未知异常', '', '2021-01-18 06:17:54', '1', '2022-02-16 13:24:23', b'0');
INSERT INTO `system_dict_data` VALUES (45, 1, '是', 'true', 'infra_boolean_string', 0, 'danger', '', 'Boolean 是否类型 - 是', '', '2021-01-19 03:20:55', '1', '2022-03-15 23:01:45', b'0');
INSERT INTO `system_dict_data` VALUES (46, 1, '否', 'false', 'infra_boolean_string', 0, 'info', '', 'Boolean 是否类型 - 否', '', '2021-01-19 03:20:55', '1', '2022-03-15 23:09:45', b'0');
INSERT INTO `system_dict_data` VALUES (50, 1, '单表（增删改查）', '1', 'infra_codegen_template_type', 0, '', '', NULL, '', '2021-02-05 07:09:06', '', '2022-03-10 16:33:15', b'0');
INSERT INTO `system_dict_data` VALUES (51, 2, '树表（增删改查）', '2', 'infra_codegen_template_type', 0, '', '', NULL, '', '2021-02-05 07:14:46', '', '2022-03-10 16:33:19', b'0');
INSERT INTO `system_dict_data` VALUES (53, 0, '初始化中', '0', 'infra_job_status', 0, 'primary', '', NULL, '', '2021-02-07 07:46:49', '1', '2022-02-16 19:33:29', b'0');
INSERT INTO `system_dict_data` VALUES (57, 0, '运行中', '0', 'infra_job_log_status', 0, 'primary', '', 'RUNNING', '', '2021-02-08 10:04:24', '1', '2022-02-16 19:07:48', b'0');
INSERT INTO `system_dict_data` VALUES (58, 1, '成功', '1', 'infra_job_log_status', 0, 'success', '', NULL, '', '2021-02-08 10:06:57', '1', '2022-02-16 19:07:52', b'0');
INSERT INTO `system_dict_data` VALUES (59, 2, '失败', '2', 'infra_job_log_status', 0, 'warning', '', '失败', '', '2021-02-08 10:07:38', '1', '2022-02-16 19:07:56', b'0');
INSERT INTO `system_dict_data` VALUES (60, 1, '会员', '1', 'user_type', 0, 'primary', '', NULL, '', '2021-02-26 00:16:27', '1', '2022-02-16 10:22:19', b'0');
INSERT INTO `system_dict_data` VALUES (61, 2, '管理员', '2', 'user_type', 0, 'success', '', NULL, '', '2021-02-26 00:16:34', '1', '2022-02-16 10:22:22', b'0');
INSERT INTO `system_dict_data` VALUES (62, 0, '未处理', '0', 'infra_api_error_log_process_status', 0, 'primary', '', NULL, '', '2021-02-26 07:07:19', '1', '2022-02-16 20:14:17', b'0');
INSERT INTO `system_dict_data` VALUES (63, 1, '已处理', '1', 'infra_api_error_log_process_status', 0, 'success', '', NULL, '', '2021-02-26 07:07:26', '1', '2022-02-16 20:14:08', b'0');
INSERT INTO `system_dict_data` VALUES (64, 2, '已忽略', '2', 'infra_api_error_log_process_status', 0, 'danger', '', NULL, '', '2021-02-26 07:07:34', '1', '2022-02-16 20:14:14', b'0');
INSERT INTO `system_dict_data` VALUES (66, 2, '阿里云', 'ALIYUN', 'system_sms_channel_code', 0, 'primary', '', NULL, '1', '2021-04-05 01:05:26', '1', '2022-02-16 10:09:52', b'0');
INSERT INTO `system_dict_data` VALUES (67, 1, '验证码', '1', 'system_sms_template_type', 0, 'warning', '', NULL, '1', '2021-04-05 21:50:57', '1', '2022-02-16 12:48:30', b'0');
INSERT INTO `system_dict_data` VALUES (68, 2, '通知', '2', 'system_sms_template_type', 0, 'primary', '', NULL, '1', '2021-04-05 21:51:08', '1', '2022-02-16 12:48:27', b'0');
INSERT INTO `system_dict_data` VALUES (69, 0, '营销', '3', 'system_sms_template_type', 0, 'danger', '', NULL, '1', '2021-04-05 21:51:15', '1', '2022-02-16 12:48:22', b'0');
INSERT INTO `system_dict_data` VALUES (70, 0, '初始化', '0', 'system_sms_send_status', 0, 'primary', '', NULL, '1', '2021-04-11 20:18:33', '1', '2022-02-16 10:26:07', b'0');
INSERT INTO `system_dict_data` VALUES (71, 1, '发送成功', '10', 'system_sms_send_status', 0, 'success', '', NULL, '1', '2021-04-11 20:18:43', '1', '2022-02-16 10:25:56', b'0');
INSERT INTO `system_dict_data` VALUES (72, 2, '发送失败', '20', 'system_sms_send_status', 0, 'danger', '', NULL, '1', '2021-04-11 20:18:49', '1', '2022-02-16 10:26:03', b'0');
INSERT INTO `system_dict_data` VALUES (73, 3, '不发送', '30', 'system_sms_send_status', 0, 'info', '', NULL, '1', '2021-04-11 20:19:44', '1', '2022-02-16 10:26:10', b'0');
INSERT INTO `system_dict_data` VALUES (74, 0, '等待结果', '0', 'system_sms_receive_status', 0, 'primary', '', NULL, '1', '2021-04-11 20:27:43', '1', '2022-02-16 10:28:24', b'0');
INSERT INTO `system_dict_data` VALUES (75, 1, '接收成功', '10', 'system_sms_receive_status', 0, 'success', '', NULL, '1', '2021-04-11 20:29:25', '1', '2022-02-16 10:28:28', b'0');
INSERT INTO `system_dict_data` VALUES (76, 2, '接收失败', '20', 'system_sms_receive_status', 0, 'danger', '', NULL, '1', '2021-04-11 20:29:31', '1', '2022-02-16 10:28:32', b'0');
INSERT INTO `system_dict_data` VALUES (77, 0, '调试(钉钉)', 'DEBUG_DING_TALK', 'system_sms_channel_code', 0, 'info', '', NULL, '1', '2021-04-13 00:20:37', '1', '2022-02-16 10:10:00', b'0');
INSERT INTO `system_dict_data` VALUES (78, 1, '自动生成', '1', 'system_error_code_type', 0, 'warning', '', NULL, '1', '2021-04-21 00:06:48', '1', '2022-02-16 13:57:20', b'0');
INSERT INTO `system_dict_data` VALUES (79, 2, '手动编辑', '2', 'system_error_code_type', 0, 'primary', '', NULL, '1', '2021-04-21 00:07:14', '1', '2022-02-16 13:57:24', b'0');
INSERT INTO `system_dict_data` VALUES (80, 100, '账号登录', '100', 'system_login_type', 0, 'primary', '', '账号登录', '1', '2021-10-06 00:52:02', '1', '2022-02-16 13:11:34', b'0');
INSERT INTO `system_dict_data` VALUES (81, 101, '社交登录', '101', 'system_login_type', 0, 'info', '', '社交登录', '1', '2021-10-06 00:52:17', '1', '2022-02-16 13:11:40', b'0');
INSERT INTO `system_dict_data` VALUES (83, 200, '主动登出', '200', 'system_login_type', 0, 'primary', '', '主动登出', '1', '2021-10-06 00:52:58', '1', '2022-02-16 13:11:49', b'0');
INSERT INTO `system_dict_data` VALUES (85, 202, '强制登出', '202', 'system_login_type', 0, 'danger', '', '强制退出', '1', '2021-10-06 00:53:41', '1', '2022-02-16 13:11:57', b'0');
INSERT INTO `system_dict_data` VALUES (86, 0, '病假', '1', 'bpm_oa_leave_type', 0, 'primary', '', NULL, '1', '2021-09-21 22:35:28', '1', '2022-02-16 10:00:41', b'0');
INSERT INTO `system_dict_data` VALUES (87, 1, '事假', '2', 'bpm_oa_leave_type', 0, 'info', '', NULL, '1', '2021-09-21 22:36:11', '1', '2022-02-16 10:00:49', b'0');
INSERT INTO `system_dict_data` VALUES (88, 2, '婚假', '3', 'bpm_oa_leave_type', 0, 'warning', '', NULL, '1', '2021-09-21 22:36:38', '1', '2022-02-16 10:00:53', b'0');
INSERT INTO `system_dict_data` VALUES (113, 1, '微信公众号支付', 'wx_pub', 'pay_channel_code', 0, 'success', '', '微信公众号支付', '1', '2021-12-03 10:40:24', '1', '2023-07-19 20:08:47', b'0');
INSERT INTO `system_dict_data` VALUES (114, 2, '微信小程序支付', 'wx_lite', 'pay_channel_code', 0, 'success', '', '微信小程序支付', '1', '2021-12-03 10:41:06', '1', '2023-07-19 20:08:50', b'0');
INSERT INTO `system_dict_data` VALUES (115, 3, '微信 App 支付', 'wx_app', 'pay_channel_code', 0, 'success', '', '微信 App 支付', '1', '2021-12-03 10:41:20', '1', '2023-07-19 20:08:56', b'0');
INSERT INTO `system_dict_data` VALUES (116, 10, '支付宝 PC 网站支付', 'alipay_pc', 'pay_channel_code', 0, 'primary', '', '支付宝 PC 网站支付', '1', '2021-12-03 10:42:09', '1', '2023-07-19 20:09:12', b'0');
INSERT INTO `system_dict_data` VALUES (117, 11, '支付宝 Wap 网站支付', 'alipay_wap', 'pay_channel_code', 0, 'primary', '', '支付宝 Wap 网站支付', '1', '2021-12-03 10:42:26', '1', '2023-07-19 20:09:16', b'0');
INSERT INTO `system_dict_data` VALUES (118, 12, '支付宝 App 支付', 'alipay_app', 'pay_channel_code', 0, 'primary', '', '支付宝 App 支付', '1', '2021-12-03 10:42:55', '1', '2023-07-19 20:09:20', b'0');
INSERT INTO `system_dict_data` VALUES (119, 14, '支付宝扫码支付', 'alipay_qr', 'pay_channel_code', 0, 'primary', '', '支付宝扫码支付', '1', '2021-12-03 10:43:10', '1', '2023-07-19 20:09:28', b'0');
INSERT INTO `system_dict_data` VALUES (120, 10, '通知成功', '10', 'pay_notify_status', 0, 'success', '', '通知成功', '1', '2021-12-03 11:02:41', '1', '2023-07-19 10:08:19', b'0');
INSERT INTO `system_dict_data` VALUES (121, 20, '通知失败', '20', 'pay_notify_status', 0, 'danger', '', '通知失败', '1', '2021-12-03 11:02:59', '1', '2023-07-19 10:08:21', b'0');
INSERT INTO `system_dict_data` VALUES (122, 0, '等待通知', '0', 'pay_notify_status', 0, 'info', '', '未通知', '1', '2021-12-03 11:03:10', '1', '2023-07-19 10:08:24', b'0');
INSERT INTO `system_dict_data` VALUES (123, 10, '支付成功', '10', 'pay_order_status', 0, 'success', '', '支付成功', '1', '2021-12-03 11:18:29', '1', '2023-07-19 18:04:28', b'0');
INSERT INTO `system_dict_data` VALUES (124, 30, '支付关闭', '30', 'pay_order_status', 0, 'info', '', '支付关闭', '1', '2021-12-03 11:18:42', '1', '2023-07-19 18:05:07', b'0');
INSERT INTO `system_dict_data` VALUES (125, 0, '等待支付', '0', 'pay_order_status', 0, 'info', '', '未支付', '1', '2021-12-03 11:18:18', '1', '2023-07-19 18:04:15', b'0');
INSERT INTO `system_dict_data` VALUES (600, 5, '首页', '1', 'promotion_banner_position', 0, 'warning', '', '', '1', '2023-10-11 07:45:24', '1', '2023-10-11 07:45:38', b'0');
INSERT INTO `system_dict_data` VALUES (601, 4, '秒杀活动页', '2', 'promotion_banner_position', 0, 'warning', '', '', '1', '2023-10-11 07:45:24', '1', '2023-10-11 07:45:38', b'0');
INSERT INTO `system_dict_data` VALUES (602, 3, '砍价活动页', '3', 'promotion_banner_position', 0, 'warning', '', '', '1', '2023-10-11 07:45:24', '1', '2023-10-11 07:45:38', b'0');
INSERT INTO `system_dict_data` VALUES (603, 2, '限时折扣页', '4', 'promotion_banner_position', 0, 'warning', '', '', '1', '2023-10-11 07:45:24', '1', '2023-10-11 07:45:38', b'0');
INSERT INTO `system_dict_data` VALUES (604, 1, '满减送页', '5', 'promotion_banner_position', 0, 'warning', '', '', '1', '2023-10-11 07:45:24', '1', '2023-10-11 07:45:38', b'0');
INSERT INTO `system_dict_data` VALUES (1118, 0, '等待退款', '0', 'pay_refund_status', 0, 'info', '', '等待退款', '1', '2021-12-10 16:44:59', '1', '2023-07-19 10:14:39', b'0');
INSERT INTO `system_dict_data` VALUES (1119, 20, '退款失败', '20', 'pay_refund_status', 0, 'danger', '', '退款失败', '1', '2021-12-10 16:45:10', '1', '2023-07-19 10:15:10', b'0');
INSERT INTO `system_dict_data` VALUES (1124, 10, '退款成功', '10', 'pay_refund_status', 0, 'success', '', '退款成功', '1', '2021-12-10 16:46:26', '1', '2023-07-19 10:15:00', b'0');
INSERT INTO `system_dict_data` VALUES (1125, 0, '默认', '1', 'bpm_model_category', 0, 'primary', '', '流程分类 - 默认', '1', '2022-01-02 08:41:11', '1', '2022-02-16 20:01:42', b'0');
INSERT INTO `system_dict_data` VALUES (1126, 0, 'OA', '2', 'bpm_model_category', 0, 'success', '', '流程分类 - OA', '1', '2022-01-02 08:41:22', '1', '2022-02-16 20:01:50', b'0');
INSERT INTO `system_dict_data` VALUES (1127, 0, '进行中', '1', 'bpm_process_instance_status', 0, 'primary', '', '流程实例的状态 - 进行中', '1', '2022-01-07 23:47:22', '1', '2022-02-16 20:07:49', b'0');
INSERT INTO `system_dict_data` VALUES (1128, 2, '已完成', '2', 'bpm_process_instance_status', 0, 'success', '', '流程实例的状态 - 已完成', '1', '2022-01-07 23:47:49', '1', '2022-02-16 20:07:54', b'0');
INSERT INTO `system_dict_data` VALUES (1129, 1, '处理中', '1', 'bpm_process_instance_result', 0, 'primary', '', '流程实例的结果 - 处理中', '1', '2022-01-07 23:48:32', '1', '2022-02-16 09:53:26', b'0');
INSERT INTO `system_dict_data` VALUES (1130, 2, '通过', '2', 'bpm_process_instance_result', 0, 'success', '', '流程实例的结果 - 通过', '1', '2022-01-07 23:48:45', '1', '2022-02-16 09:53:31', b'0');
INSERT INTO `system_dict_data` VALUES (1131, 3, '不通过', '3', 'bpm_process_instance_result', 0, 'danger', '', '流程实例的结果 - 不通过', '1', '2022-01-07 23:48:55', '1', '2022-02-16 09:53:38', b'0');
INSERT INTO `system_dict_data` VALUES (1132, 4, '已取消', '4', 'bpm_process_instance_result', 0, 'info', '', '流程实例的结果 - 撤销', '1', '2022-01-07 23:49:06', '1', '2022-02-16 09:53:42', b'0');
INSERT INTO `system_dict_data` VALUES (1133, 10, '流程表单', '10', 'bpm_model_form_type', 0, '', '', '流程的表单类型 - 流程表单', '103', '2022-01-11 23:51:30', '103', '2022-01-11 23:51:30', b'0');
INSERT INTO `system_dict_data` VALUES (1134, 20, '业务表单', '20', 'bpm_model_form_type', 0, '', '', '流程的表单类型 - 业务表单', '103', '2022-01-11 23:51:47', '103', '2022-01-11 23:51:47', b'0');
INSERT INTO `system_dict_data` VALUES (1135, 10, '角色', '10', 'bpm_task_assign_rule_type', 0, 'info', '', '任务分配规则的类型 - 角色', '103', '2022-01-12 23:21:22', '1', '2022-02-16 20:06:14', b'0');
INSERT INTO `system_dict_data` VALUES (1136, 20, '部门的成员', '20', 'bpm_task_assign_rule_type', 0, 'primary', '', '任务分配规则的类型 - 部门的成员', '103', '2022-01-12 23:21:47', '1', '2022-02-16 20:05:28', b'0');
INSERT INTO `system_dict_data` VALUES (1137, 21, '部门的负责人', '21', 'bpm_task_assign_rule_type', 0, 'primary', '', '任务分配规则的类型 - 部门的负责人', '103', '2022-01-12 23:33:36', '1', '2022-02-16 20:05:31', b'0');
INSERT INTO `system_dict_data` VALUES (1138, 30, '用户', '30', 'bpm_task_assign_rule_type', 0, 'info', '', '任务分配规则的类型 - 用户', '103', '2022-01-12 23:34:02', '1', '2022-02-16 20:05:50', b'0');
INSERT INTO `system_dict_data` VALUES (1139, 40, '用户组', '40', 'bpm_task_assign_rule_type', 0, 'warning', '', '任务分配规则的类型 - 用户组', '103', '2022-01-12 23:34:21', '1', '2022-02-16 20:05:57', b'0');
INSERT INTO `system_dict_data` VALUES (1140, 50, '自定义脚本', '50', 'bpm_task_assign_rule_type', 0, 'danger', '', '任务分配规则的类型 - 自定义脚本', '103', '2022-01-12 23:34:43', '1', '2022-02-16 20:06:01', b'0');
INSERT INTO `system_dict_data` VALUES (1141, 22, '岗位', '22', 'bpm_task_assign_rule_type', 0, 'success', '', '任务分配规则的类型 - 岗位', '103', '2022-01-14 18:41:55', '1', '2022-02-16 20:05:39', b'0');
INSERT INTO `system_dict_data` VALUES (1142, 10, '流程发起人', '10', 'bpm_task_assign_script', 0, '', '', '任务分配自定义脚本 - 流程发起人', '103', '2022-01-15 00:10:57', '103', '2022-01-15 21:24:10', b'0');
INSERT INTO `system_dict_data` VALUES (1143, 20, '流程发起人的一级领导', '20', 'bpm_task_assign_script', 0, '', '', '任务分配自定义脚本 - 流程发起人的一级领导', '103', '2022-01-15 21:24:31', '103', '2022-01-15 21:24:31', b'0');
INSERT INTO `system_dict_data` VALUES (1144, 21, '流程发起人的二级领导', '21', 'bpm_task_assign_script', 0, '', '', '任务分配自定义脚本 - 流程发起人的二级领导', '103', '2022-01-15 21:24:46', '103', '2022-01-15 21:24:57', b'0');
INSERT INTO `system_dict_data` VALUES (1145, 1, '管理后台', '1', 'infra_codegen_scene', 0, '', '', '代码生成的场景枚举 - 管理后台', '1', '2022-02-02 13:15:06', '1', '2022-03-10 16:32:59', b'0');
INSERT INTO `system_dict_data` VALUES (1146, 2, '用户 APP', '2', 'infra_codegen_scene', 0, '', '', '代码生成的场景枚举 - 用户 APP', '1', '2022-02-02 13:15:19', '1', '2022-03-10 16:33:03', b'0');
INSERT INTO `system_dict_data` VALUES (1150, 1, '数据库', '1', 'infra_file_storage', 0, 'default', '', NULL, '1', '2022-03-15 00:25:28', '1', '2022-03-15 00:25:28', b'0');
INSERT INTO `system_dict_data` VALUES (1151, 10, '本地磁盘', '10', 'infra_file_storage', 0, 'default', '', NULL, '1', '2022-03-15 00:25:41', '1', '2022-03-15 00:25:56', b'0');
INSERT INTO `system_dict_data` VALUES (1152, 11, 'FTP 服务器', '11', 'infra_file_storage', 0, 'default', '', NULL, '1', '2022-03-15 00:26:06', '1', '2022-03-15 00:26:10', b'0');
INSERT INTO `system_dict_data` VALUES (1153, 12, 'SFTP 服务器', '12', 'infra_file_storage', 0, 'default', '', NULL, '1', '2022-03-15 00:26:22', '1', '2022-03-15 00:26:22', b'0');
INSERT INTO `system_dict_data` VALUES (1154, 20, 'S3 对象存储', '20', 'infra_file_storage', 0, 'default', '', NULL, '1', '2022-03-15 00:26:31', '1', '2022-03-15 00:26:45', b'0');
INSERT INTO `system_dict_data` VALUES (1155, 103, '短信登录', '103', 'system_login_type', 0, 'default', '', NULL, '1', '2022-05-09 23:57:58', '1', '2022-05-09 23:58:09', b'0');
INSERT INTO `system_dict_data` VALUES (1156, 1, 'password', 'password', 'system_oauth2_grant_type', 0, 'default', '', '密码模式', '1', '2022-05-12 00:22:05', '1', '2022-05-11 16:26:01', b'0');
INSERT INTO `system_dict_data` VALUES (1157, 2, 'authorization_code', 'authorization_code', 'system_oauth2_grant_type', 0, 'primary', '', '授权码模式', '1', '2022-05-12 00:22:59', '1', '2022-05-11 16:26:02', b'0');
INSERT INTO `system_dict_data` VALUES (1158, 3, 'implicit', 'implicit', 'system_oauth2_grant_type', 0, 'success', '', '简化模式', '1', '2022-05-12 00:23:40', '1', '2022-05-11 16:26:05', b'0');
INSERT INTO `system_dict_data` VALUES (1159, 4, 'client_credentials', 'client_credentials', 'system_oauth2_grant_type', 0, 'default', '', '客户端模式', '1', '2022-05-12 00:23:51', '1', '2022-05-11 16:26:08', b'0');
INSERT INTO `system_dict_data` VALUES (1160, 5, 'refresh_token', 'refresh_token', 'system_oauth2_grant_type', 0, 'info', '', '刷新模式', '1', '2022-05-12 00:24:02', '1', '2022-05-11 16:26:11', b'0');
INSERT INTO `system_dict_data` VALUES (1162, 1, '销售中', '1', 'product_spu_status', 0, 'success', '', '商品 SPU 状态 - 销售中', '1', '2022-10-24 21:19:47', '1', '2022-10-24 21:20:38', b'0');
INSERT INTO `system_dict_data` VALUES (1163, 0, '仓库中', '0', 'product_spu_status', 0, 'info', '', '商品 SPU 状态 - 仓库中', '1', '2022-10-24 21:20:54', '1', '2022-10-24 21:21:22', b'0');
INSERT INTO `system_dict_data` VALUES (1164, 0, '回收站', '-1', 'product_spu_status', 0, 'default', '', '商品 SPU 状态 - 回收站', '1', '2022-10-24 21:21:11', '1', '2022-10-24 21:21:11', b'0');
INSERT INTO `system_dict_data` VALUES (1165, 1, '满减', '1', 'promotion_discount_type', 0, 'success', '', '优惠类型 - 满减', '1', '2022-11-01 12:46:41', '1', '2022-11-01 12:50:11', b'0');
INSERT INTO `system_dict_data` VALUES (1166, 2, '折扣', '2', 'promotion_discount_type', 0, 'primary', '', '优惠类型 - 折扣', '1', '2022-11-01 12:46:51', '1', '2022-11-01 12:50:08', b'0');
INSERT INTO `system_dict_data` VALUES (1167, 1, '固定日期', '1', 'promotion_coupon_template_validity_type', 0, 'default', '', '优惠劵模板的有限期类型 - 固定日期', '1', '2022-11-02 00:07:34', '1', '2022-11-04 00:07:49', b'0');
INSERT INTO `system_dict_data` VALUES (1168, 2, '领取之后', '2', 'promotion_coupon_template_validity_type', 0, 'default', '', '优惠劵模板的有限期类型 - 领取之后', '1', '2022-11-02 00:07:54', '1', '2022-11-04 00:07:52', b'0');
INSERT INTO `system_dict_data` VALUES (1169, 1, '通用劵', '1', 'promotion_product_scope', 0, 'default', '', '营销的商品范围 - 全部商品参与', '1', '2022-11-02 00:28:22', '1', '2023-09-28 00:27:42', b'0');
INSERT INTO `system_dict_data` VALUES (1170, 2, '商品劵', '2', 'promotion_product_scope', 0, 'default', '', '营销的商品范围 - 指定商品参与', '1', '2022-11-02 00:28:34', '1', '2023-09-28 00:27:44', b'0');
INSERT INTO `system_dict_data` VALUES (1171, 1, '未使用', '1', 'promotion_coupon_status', 0, 'primary', '', '优惠劵的状态 - 已领取', '1', '2022-11-04 00:15:08', '1', '2023-10-03 12:54:38', b'0');
INSERT INTO `system_dict_data` VALUES (1172, 2, '已使用', '2', 'promotion_coupon_status', 0, 'success', '', '优惠劵的状态 - 已使用', '1', '2022-11-04 00:15:21', '1', '2022-11-04 19:16:08', b'0');
INSERT INTO `system_dict_data` VALUES (1173, 3, '已过期', '3', 'promotion_coupon_status', 0, 'info', '', '优惠劵的状态 - 已过期', '1', '2022-11-04 00:15:43', '1', '2022-11-04 19:16:12', b'0');
INSERT INTO `system_dict_data` VALUES (1174, 1, '直接领取', '1', 'promotion_coupon_take_type', 0, 'primary', '', '优惠劵的领取方式 - 直接领取', '1', '2022-11-04 19:13:00', '1', '2022-11-04 19:13:25', b'0');
INSERT INTO `system_dict_data` VALUES (1175, 2, '指定发放', '2', 'promotion_coupon_take_type', 0, 'success', '', '优惠劵的领取方式 - 指定发放', '1', '2022-11-04 19:13:13', '1', '2022-11-04 19:14:48', b'0');
INSERT INTO `system_dict_data` VALUES (1176, 10, '未开始', '10', 'promotion_activity_status', 0, 'primary', '', '促销活动的状态枚举 - 未开始', '1', '2022-11-04 22:54:49', '1', '2022-11-04 22:55:53', b'0');
INSERT INTO `system_dict_data` VALUES (1177, 20, '进行中', '20', 'promotion_activity_status', 0, 'success', '', '促销活动的状态枚举 - 进行中', '1', '2022-11-04 22:55:06', '1', '2022-11-04 22:55:20', b'0');
INSERT INTO `system_dict_data` VALUES (1178, 30, '已结束', '30', 'promotion_activity_status', 0, 'info', '', '促销活动的状态枚举 - 已结束', '1', '2022-11-04 22:55:41', '1', '2022-11-04 22:55:41', b'0');
INSERT INTO `system_dict_data` VALUES (1179, 40, '已关闭', '40', 'promotion_activity_status', 0, 'warning', '', '促销活动的状态枚举 - 已关闭', '1', '2022-11-04 22:56:10', '1', '2022-11-04 22:56:18', b'0');
INSERT INTO `system_dict_data` VALUES (1180, 10, '满 N 元', '10', 'promotion_condition_type', 0, 'primary', '', '营销的条件类型 - 满 N 元', '1', '2022-11-04 22:59:45', '1', '2022-11-04 22:59:45', b'0');
INSERT INTO `system_dict_data` VALUES (1181, 20, '满 N 件', '20', 'promotion_condition_type', 0, 'success', '', '营销的条件类型 - 满 N 件', '1', '2022-11-04 23:00:02', '1', '2022-11-04 23:00:02', b'0');
INSERT INTO `system_dict_data` VALUES (1182, 10, '申请售后', '10', 'trade_after_sale_status', 0, 'primary', '', '交易售后状态 - 申请售后', '1', '2022-11-19 20:53:33', '1', '2022-11-19 20:54:42', b'0');
INSERT INTO `system_dict_data` VALUES (1183, 20, '商品待退货', '20', 'trade_after_sale_status', 0, 'primary', '', '交易售后状态 - 商品待退货', '1', '2022-11-19 20:54:36', '1', '2022-11-19 20:58:58', b'0');
INSERT INTO `system_dict_data` VALUES (1184, 30, '商家待收货', '30', 'trade_after_sale_status', 0, 'primary', '', '交易售后状态 - 商家待收货', '1', '2022-11-19 20:56:56', '1', '2022-11-19 20:59:20', b'0');
INSERT INTO `system_dict_data` VALUES (1185, 40, '等待退款', '40', 'trade_after_sale_status', 0, 'primary', '', '交易售后状态 - 等待退款', '1', '2022-11-19 20:59:54', '1', '2022-11-19 21:00:01', b'0');
INSERT INTO `system_dict_data` VALUES (1186, 50, '退款成功', '50', 'trade_after_sale_status', 0, 'default', '', '交易售后状态 - 退款成功', '1', '2022-11-19 21:00:33', '1', '2022-11-19 21:00:33', b'0');
INSERT INTO `system_dict_data` VALUES (1187, 61, '买家取消', '61', 'trade_after_sale_status', 0, 'info', '', '交易售后状态 - 买家取消', '1', '2022-11-19 21:01:29', '1', '2022-11-19 21:01:29', b'0');
INSERT INTO `system_dict_data` VALUES (1188, 62, '商家拒绝', '62', 'trade_after_sale_status', 0, 'info', '', '交易售后状态 - 商家拒绝', '1', '2022-11-19 21:02:17', '1', '2022-11-19 21:02:17', b'0');
INSERT INTO `system_dict_data` VALUES (1189, 63, '商家拒收货', '63', 'trade_after_sale_status', 0, 'info', '', '交易售后状态 - 商家拒收货', '1', '2022-11-19 21:02:37', '1', '2022-11-19 21:03:07', b'0');
INSERT INTO `system_dict_data` VALUES (1190, 10, '售中退款', '10', 'trade_after_sale_type', 0, 'success', '', '交易售后的类型 - 售中退款', '1', '2022-11-19 21:05:05', '1', '2022-11-19 21:38:23', b'0');
INSERT INTO `system_dict_data` VALUES (1191, 20, '售后退款', '20', 'trade_after_sale_type', 0, 'primary', '', '交易售后的类型 - 售后退款', '1', '2022-11-19 21:05:32', '1', '2022-11-19 21:38:32', b'0');
INSERT INTO `system_dict_data` VALUES (1192, 10, '仅退款', '10', 'trade_after_sale_way', 0, 'primary', '', '交易售后的方式 - 仅退款', '1', '2022-11-19 21:39:19', '1', '2022-11-19 21:39:19', b'0');
INSERT INTO `system_dict_data` VALUES (1193, 20, '退货退款', '20', 'trade_after_sale_way', 0, 'success', '', '交易售后的方式 - 退货退款', '1', '2022-11-19 21:39:38', '1', '2022-11-19 21:39:49', b'0');
INSERT INTO `system_dict_data` VALUES (1194, 10, '微信小程序', '10', 'terminal', 0, 'default', '', '终端 - 微信小程序', '1', '2022-12-10 10:51:11', '1', '2022-12-10 10:51:57', b'0');
INSERT INTO `system_dict_data` VALUES (1195, 20, 'H5 网页', '20', 'terminal', 0, 'default', '', '终端 - H5 网页', '1', '2022-12-10 10:51:30', '1', '2022-12-10 10:51:59', b'0');
INSERT INTO `system_dict_data` VALUES (1196, 11, '微信公众号', '11', 'terminal', 0, 'default', '', '终端 - 微信公众号', '1', '2022-12-10 10:54:16', '1', '2022-12-10 10:52:01', b'0');
INSERT INTO `system_dict_data` VALUES (1197, 31, '苹果 App', '31', 'terminal', 0, 'default', '', '终端 - 苹果 App', '1', '2022-12-10 10:54:42', '1', '2022-12-10 10:52:18', b'0');
INSERT INTO `system_dict_data` VALUES (1198, 32, '安卓 App', '32', 'terminal', 0, 'default', '', '终端 - 安卓 App', '1', '2022-12-10 10:55:02', '1', '2022-12-10 10:59:17', b'0');
INSERT INTO `system_dict_data` VALUES (1199, 0, '普通订单', '0', 'trade_order_type', 0, 'default', '', '交易订单的类型 - 普通订单', '1', '2022-12-10 16:34:14', '1', '2022-12-10 16:34:14', b'0');
INSERT INTO `system_dict_data` VALUES (1200, 1, '秒杀订单', '1', 'trade_order_type', 0, 'default', '', '交易订单的类型 - 秒杀订单', '1', '2022-12-10 16:34:26', '1', '2022-12-10 16:34:26', b'0');
INSERT INTO `system_dict_data` VALUES (1201, 2, '拼团订单', '2', 'trade_order_type', 0, 'default', '', '交易订单的类型 - 拼团订单', '1', '2022-12-10 16:34:36', '1', '2022-12-10 16:34:36', b'0');
INSERT INTO `system_dict_data` VALUES (1202, 3, '砍价订单', '3', 'trade_order_type', 0, 'default', '', '交易订单的类型 - 砍价订单', '1', '2022-12-10 16:34:48', '1', '2022-12-10 16:34:48', b'0');
INSERT INTO `system_dict_data` VALUES (1203, 0, '待支付', '0', 'trade_order_status', 0, 'default', '', '交易订单状态 - 待支付', '1', '2022-12-10 16:49:29', '1', '2022-12-10 16:49:29', b'0');
INSERT INTO `system_dict_data` VALUES (1204, 10, '待发货', '10', 'trade_order_status', 0, 'primary', '', '交易订单状态 - 待发货', '1', '2022-12-10 16:49:53', '1', '2022-12-10 16:51:17', b'0');
INSERT INTO `system_dict_data` VALUES (1205, 20, '已发货', '20', 'trade_order_status', 0, 'primary', '', '交易订单状态 - 已发货', '1', '2022-12-10 16:50:13', '1', '2022-12-10 16:51:31', b'0');
INSERT INTO `system_dict_data` VALUES (1206, 30, '已完成', '30', 'trade_order_status', 0, 'success', '', '交易订单状态 - 已完成', '1', '2022-12-10 16:50:30', '1', '2022-12-10 16:51:06', b'0');
INSERT INTO `system_dict_data` VALUES (1207, 40, '已取消', '40', 'trade_order_status', 0, 'danger', '', '交易订单状态 - 已取消', '1', '2022-12-10 16:50:50', '1', '2022-12-10 16:51:00', b'0');
INSERT INTO `system_dict_data` VALUES (1208, 0, '未售后', '0', 'trade_order_item_after_sale_status', 0, 'info', '', '交易订单项的售后状态 - 未售后', '1', '2022-12-10 20:58:42', '1', '2022-12-10 20:59:29', b'0');
INSERT INTO `system_dict_data` VALUES (1209, 1, '售后中', '1', 'trade_order_item_after_sale_status', 0, 'primary', '', '交易订单项的售后状态 - 售后中', '1', '2022-12-10 20:59:21', '1', '2022-12-10 20:59:21', b'0');
INSERT INTO `system_dict_data` VALUES (1210, 2, '已退款', '2', 'trade_order_item_after_sale_status', 0, 'success', '', '交易订单项的售后状态 - 已退款', '1', '2022-12-10 20:59:46', '1', '2022-12-10 20:59:46', b'0');
INSERT INTO `system_dict_data` VALUES (1211, 1, '完全匹配', '1', 'mp_auto_reply_request_match', 0, 'primary', '', '公众号自动回复的请求关键字匹配模式 - 完全匹配', '1', '2023-01-16 23:30:39', '1', '2023-01-16 23:31:00', b'0');
INSERT INTO `system_dict_data` VALUES (1212, 2, '半匹配', '2', 'mp_auto_reply_request_match', 0, 'success', '', '公众号自动回复的请求关键字匹配模式 - 半匹配', '1', '2023-01-16 23:30:55', '1', '2023-01-16 23:31:10', b'0');
INSERT INTO `system_dict_data` VALUES (1213, 1, '文本', 'text', 'mp_message_type', 0, 'default', '', '公众号的消息类型 - 文本', '1', '2023-01-17 22:17:32', '1', '2023-01-17 22:17:39', b'0');
INSERT INTO `system_dict_data` VALUES (1214, 2, '图片', 'image', 'mp_message_type', 0, 'default', '', '公众号的消息类型 - 图片', '1', '2023-01-17 22:17:32', '1', '2023-01-17 14:19:47', b'0');
INSERT INTO `system_dict_data` VALUES (1215, 3, '语音', 'voice', 'mp_message_type', 0, 'default', '', '公众号的消息类型 - 语音', '1', '2023-01-17 22:17:32', '1', '2023-01-17 14:20:08', b'0');
INSERT INTO `system_dict_data` VALUES (1216, 4, '视频', 'video', 'mp_message_type', 0, 'default', '', '公众号的消息类型 - 视频', '1', '2023-01-17 22:17:32', '1', '2023-01-17 14:21:08', b'0');
INSERT INTO `system_dict_data` VALUES (1217, 5, '小视频', 'shortvideo', 'mp_message_type', 0, 'default', '', '公众号的消息类型 - 小视频', '1', '2023-01-17 22:17:32', '1', '2023-01-17 14:19:59', b'0');
INSERT INTO `system_dict_data` VALUES (1218, 6, '图文', 'news', 'mp_message_type', 0, 'default', '', '公众号的消息类型 - 图文', '1', '2023-01-17 22:17:32', '1', '2023-01-17 14:22:54', b'0');
INSERT INTO `system_dict_data` VALUES (1219, 7, '音乐', 'music', 'mp_message_type', 0, 'default', '', '公众号的消息类型 - 音乐', '1', '2023-01-17 22:17:32', '1', '2023-01-17 14:22:54', b'0');
INSERT INTO `system_dict_data` VALUES (1220, 8, '地理位置', 'location', 'mp_message_type', 0, 'default', '', '公众号的消息类型 - 地理位置', '1', '2023-01-17 22:17:32', '1', '2023-01-17 14:23:51', b'0');
INSERT INTO `system_dict_data` VALUES (1221, 9, '链接', 'link', 'mp_message_type', 0, 'default', '', '公众号的消息类型 - 链接', '1', '2023-01-17 22:17:32', '1', '2023-01-17 14:24:49', b'0');
INSERT INTO `system_dict_data` VALUES (1222, 10, '事件', 'event', 'mp_message_type', 0, 'default', '', '公众号的消息类型 - 事件', '1', '2023-01-17 22:17:32', '1', '2023-01-17 14:24:49', b'0');
INSERT INTO `system_dict_data` VALUES (1223, 0, '初始化', '0', 'system_mail_send_status', 0, 'primary', '', '邮件发送状态 - 初始化\n', '1', '2023-01-26 09:53:49', '1', '2023-01-26 16:36:14', b'0');
INSERT INTO `system_dict_data` VALUES (1224, 10, '发送成功', '10', 'system_mail_send_status', 0, 'success', '', '邮件发送状态 - 发送成功', '1', '2023-01-26 09:54:28', '1', '2023-01-26 16:36:22', b'0');
INSERT INTO `system_dict_data` VALUES (1225, 20, '发送失败', '20', 'system_mail_send_status', 0, 'danger', '', '邮件发送状态 - 发送失败', '1', '2023-01-26 09:54:50', '1', '2023-01-26 16:36:26', b'0');
INSERT INTO `system_dict_data` VALUES (1226, 30, '不发送', '30', 'system_mail_send_status', 0, 'info', '', '邮件发送状态 -  不发送', '1', '2023-01-26 09:55:06', '1', '2023-01-26 16:36:36', b'0');
INSERT INTO `system_dict_data` VALUES (1227, 1, '通知公告', '1', 'system_notify_template_type', 0, 'primary', '', '站内信模版的类型 - 通知公告', '1', '2023-01-28 10:35:59', '1', '2023-01-28 10:35:59', b'0');
INSERT INTO `system_dict_data` VALUES (1228, 2, '系统消息', '2', 'system_notify_template_type', 0, 'success', '', '站内信模版的类型 - 系统消息', '1', '2023-01-28 10:36:20', '1', '2023-01-28 10:36:25', b'0');
INSERT INTO `system_dict_data` VALUES (1230, 13, '支付宝条码支付', 'alipay_bar', 'pay_channel_code', 0, 'primary', '', '支付宝条码支付', '1', '2023-02-18 23:32:24', '1', '2023-07-19 20:09:23', b'0');
INSERT INTO `system_dict_data` VALUES (1231, 10, 'Vue2 Element UI 标准模版', '10', 'infra_codegen_front_type', 0, '', '', '', '1', '2023-04-13 00:03:55', '1', '2023-04-13 00:03:55', b'0');
INSERT INTO `system_dict_data` VALUES (1232, 20, 'Vue3 Element Plus 标准模版', '20', 'infra_codegen_front_type', 0, '', '', '', '1', '2023-04-13 00:04:08', '1', '2023-04-13 00:04:08', b'0');
INSERT INTO `system_dict_data` VALUES (1233, 21, 'Vue3 Element Plus Schema 模版', '21', 'infra_codegen_front_type', 0, '', '', '', '1', '2023-04-13 00:04:26', '1', '2023-04-13 00:04:26', b'0');
INSERT INTO `system_dict_data` VALUES (1234, 30, 'Vue3 vben 模版', '30', 'infra_codegen_front_type', 0, '', '', '', '1', '2023-04-13 00:04:26', '1', '2023-04-13 00:04:26', b'0');
INSERT INTO `system_dict_data` VALUES (1235, 1, '个', '1', 'product_unit', 0, '', '', '', '1', '2023-05-23 14:38:38', '1', '2023-05-23 14:38:38', b'0');
INSERT INTO `system_dict_data` VALUES (1236, 1, '件', '2', 'product_unit', 0, '', '', '', '1', '2023-05-23 14:38:38', '1', '2023-05-23 14:38:38', b'0');
INSERT INTO `system_dict_data` VALUES (1237, 1, '盒', '3', 'product_unit', 0, '', '', '', '1', '2023-05-23 14:38:38', '1', '2023-05-23 14:38:38', b'0');
INSERT INTO `system_dict_data` VALUES (1238, 1, '袋', '4', 'product_unit', 0, '', '', '', '1', '2023-05-23 14:38:38', '1', '2023-05-23 14:38:38', b'0');
INSERT INTO `system_dict_data` VALUES (1239, 1, '箱', '5', 'product_unit', 0, '', '', '', '1', '2023-05-23 14:38:38', '1', '2023-05-23 14:38:38', b'0');
INSERT INTO `system_dict_data` VALUES (1240, 1, '套', '6', 'product_unit', 0, '', '', '', '1', '2023-05-23 14:38:38', '1', '2023-05-23 14:38:38', b'0');
INSERT INTO `system_dict_data` VALUES (1241, 1, '包', '7', 'product_unit', 0, '', '', '', '1', '2023-05-23 14:38:38', '1', '2023-05-23 14:38:38', b'0');
INSERT INTO `system_dict_data` VALUES (1242, 1, '双', '8', 'product_unit', 0, '', '', '', '1', '2023-05-23 14:38:38', '1', '2023-05-23 14:38:38', b'0');
INSERT INTO `system_dict_data` VALUES (1243, 1, '卷', '9', 'product_unit', 0, '', '', '', '1', '2023-05-23 14:38:38', '1', '2023-05-23 14:38:38', b'0');
INSERT INTO `system_dict_data` VALUES (1244, 0, '按件', '1', 'trade_delivery_express_charge_mode', 0, '', '', '', '1', '2023-05-21 22:46:40', '1', '2023-05-21 22:46:40', b'0');
INSERT INTO `system_dict_data` VALUES (1245, 1, '按重量', '2', 'trade_delivery_express_charge_mode', 0, '', '', '', '1', '2023-05-21 22:46:58', '1', '2023-05-21 22:46:58', b'0');
INSERT INTO `system_dict_data` VALUES (1246, 2, '按体积', '3', 'trade_delivery_express_charge_mode', 0, '', '', '', '1', '2023-05-21 22:47:18', '1', '2023-05-21 22:47:18', b'0');
INSERT INTO `system_dict_data` VALUES (1335, 11, '订单积分抵扣', '11', 'member_point_biz_type', 0, '', '', '', '1', '2023-06-10 12:15:27', '1', '2023-10-11 07:41:43', b'0');
INSERT INTO `system_dict_data` VALUES (1336, 1, '签到', '1', 'member_point_biz_type', 0, '', '', '', '1', '2023-06-10 12:15:48', '1', '2023-08-20 11:59:53', b'0');
INSERT INTO `system_dict_data` VALUES (1341, 20, '已退款', '20', 'pay_order_status', 0, 'danger', '', '已退款', '1', '2023-07-19 18:05:37', '1', '2023-07-19 18:05:37', b'0');
INSERT INTO `system_dict_data` VALUES (1342, 21, '请求成功，但是结果失败', '21', 'pay_notify_status', 0, 'warning', '', '请求成功，但是结果失败', '1', '2023-07-19 18:10:47', '1', '2023-07-19 18:11:38', b'0');
INSERT INTO `system_dict_data` VALUES (1343, 22, '请求失败', '22', 'pay_notify_status', 0, 'warning', '', NULL, '1', '2023-07-19 18:11:05', '1', '2023-07-19 18:11:27', b'0');
INSERT INTO `system_dict_data` VALUES (1344, 4, '微信扫码支付', 'wx_native', 'pay_channel_code', 0, 'success', '', '微信扫码支付', '1', '2023-07-19 20:07:47', '1', '2023-07-19 20:09:03', b'0');
INSERT INTO `system_dict_data` VALUES (1345, 5, '微信条码支付', 'wx_bar', 'pay_channel_code', 0, 'success', '', '微信条码支付\n', '1', '2023-07-19 20:08:06', '1', '2023-07-19 20:09:08', b'0');
INSERT INTO `system_dict_data` VALUES (1346, 1, '支付单', '1', 'pay_notify_type', 0, 'primary', '', '支付单', '1', '2023-07-20 12:23:17', '1', '2023-07-20 12:23:17', b'0');
INSERT INTO `system_dict_data` VALUES (1347, 2, '退款单', '2', 'pay_notify_type', 0, 'danger', '', NULL, '1', '2023-07-20 12:23:26', '1', '2023-07-20 12:23:26', b'0');
INSERT INTO `system_dict_data` VALUES (1348, 20, '模拟支付', 'mock', 'pay_channel_code', 0, 'default', '', '模拟支付', '1', '2023-07-29 11:10:51', '1', '2023-07-29 03:14:10', b'0');
INSERT INTO `system_dict_data` VALUES (1349, 12, '订单积分抵扣（整单取消）', '12', 'member_point_biz_type', 0, '', '', '', '1', '2023-08-20 12:00:03', '1', '2023-10-11 07:42:01', b'0');
INSERT INTO `system_dict_data` VALUES (1350, 0, '管理员调整', '0', 'member_experience_biz_type', 0, '', '', NULL, '', '2023-08-22 12:41:01', '', '2023-08-22 12:41:01', b'0');
INSERT INTO `system_dict_data` VALUES (1351, 1, '邀新奖励', '1', 'member_experience_biz_type', 0, '', '', NULL, '', '2023-08-22 12:41:01', '', '2023-08-22 12:41:01', b'0');
INSERT INTO `system_dict_data` VALUES (1352, 11, '下单奖励', '11', 'member_experience_biz_type', 0, 'success', '', NULL, '', '2023-08-22 12:41:01', '1', '2023-10-11 07:45:09', b'0');
INSERT INTO `system_dict_data` VALUES (1353, 12, '下单奖励（整单取消）', '12', 'member_experience_biz_type', 0, 'warning', '', NULL, '', '2023-08-22 12:41:01', '1', '2023-10-11 07:45:01', b'0');
INSERT INTO `system_dict_data` VALUES (1354, 4, '签到奖励', '4', 'member_experience_biz_type', 0, '', '', NULL, '', '2023-08-22 12:41:01', '', '2023-08-22 12:41:01', b'0');
INSERT INTO `system_dict_data` VALUES (1355, 5, '抽奖奖励', '5', 'member_experience_biz_type', 0, '', '', NULL, '', '2023-08-22 12:41:01', '', '2023-08-22 12:41:01', b'0');
INSERT INTO `system_dict_data` VALUES (1356, 1, '快递发货', '1', 'trade_delivery_type', 0, '', '', '', '1', '2023-08-23 00:04:55', '1', '2023-08-23 00:04:55', b'0');
INSERT INTO `system_dict_data` VALUES (1357, 2, '用户自提', '2', 'trade_delivery_type', 0, '', '', '', '1', '2023-08-23 00:05:05', '1', '2023-08-23 00:05:05', b'0');
INSERT INTO `system_dict_data` VALUES (1358, 3, '品类劵', '3', 'promotion_product_scope', 0, 'default', '', '', '1', '2023-09-01 23:43:07', '1', '2023-09-28 00:27:47', b'0');
INSERT INTO `system_dict_data` VALUES (1359, 1, '人人分销', '1', 'brokerage_enabled_condition', 0, '', '', '所有用户都可以分销', '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1360, 2, '指定分销', '2', 'brokerage_enabled_condition', 0, '', '', '仅可后台手动设置推广员', '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1361, 1, '首次绑定', '1', 'brokerage_bind_mode', 0, '', '', '只要用户没有推广人，随时都可以绑定推广关系', '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1362, 2, '注册绑定', '2', 'brokerage_bind_mode', 0, '', '', '仅新用户注册时才能绑定推广关系', '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1363, 3, '覆盖绑定', '3', 'brokerage_bind_mode', 0, '', '', '如果用户已经有推广人，推广人会被变更', '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1364, 1, '钱包', '1', 'brokerage_withdraw_type', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1365, 2, '银行卡', '2', 'brokerage_withdraw_type', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1366, 3, '微信', '3', 'brokerage_withdraw_type', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1367, 4, '支付宝', '4', 'brokerage_withdraw_type', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1368, 1, '订单返佣', '1', 'brokerage_record_biz_type', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1369, 2, '申请提现', '2', 'brokerage_record_biz_type', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1370, 3, '申请提现驳回', '3', 'brokerage_record_biz_type', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1371, 0, '待结算', '0', 'brokerage_record_status', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1372, 1, '已结算', '1', 'brokerage_record_status', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1373, 2, '已取消', '2', 'brokerage_record_status', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1374, 0, '审核中', '0', 'brokerage_withdraw_status', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1375, 10, '审核通过', '10', 'brokerage_withdraw_status', 0, 'success', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1376, 11, '提现成功', '11', 'brokerage_withdraw_status', 0, 'success', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1377, 20, '审核不通过', '20', 'brokerage_withdraw_status', 0, 'danger', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1378, 21, '提现失败', '21', 'brokerage_withdraw_status', 0, 'danger', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1379, 0, '工商银行', '0', 'brokerage_bank_name', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1380, 1, '建设银行', '1', 'brokerage_bank_name', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1381, 2, '农业银行', '2', 'brokerage_bank_name', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1382, 3, '中国银行', '3', 'brokerage_bank_name', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1383, 4, '交通银行', '4', 'brokerage_bank_name', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1384, 5, '招商银行', '5', 'brokerage_bank_name', 0, '', '', NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0');
INSERT INTO `system_dict_data` VALUES (1385, 21, '钱包', 'wallet', 'pay_channel_code', 0, 'primary', '', '', '1', '2023-10-01 21:46:19', '1', '2023-10-01 21:48:01', b'0');
INSERT INTO `system_dict_data` VALUES (1386, 1, '砍价中', '1', 'promotion_bargain_record_status', 0, 'default', '', '', '1', '2023-10-05 10:41:26', '1', '2023-10-05 10:41:26', b'0');
INSERT INTO `system_dict_data` VALUES (1387, 2, '砍价成功', '2', 'promotion_bargain_record_status', 0, 'success', '', '', '1', '2023-10-05 10:41:39', '1', '2023-10-05 10:41:39', b'0');
INSERT INTO `system_dict_data` VALUES (1388, 3, '砍价失败', '3', 'promotion_bargain_record_status', 0, 'warning', '', '', '1', '2023-10-05 10:41:57', '1', '2023-10-05 10:41:57', b'0');
INSERT INTO `system_dict_data` VALUES (1389, 1, '拼团中', '1', 'promotion_combination_record_status', 0, '', '', '', '1', '2023-10-08 07:24:44', '1', '2023-10-08 07:24:44', b'0');
INSERT INTO `system_dict_data` VALUES (1390, 2, '拼团成功', '2', 'promotion_combination_record_status', 0, 'success', '', '', '1', '2023-10-08 07:24:56', '1', '2023-10-08 07:24:56', b'0');
INSERT INTO `system_dict_data` VALUES (1391, 3, '拼团失败', '3', 'promotion_combination_record_status', 0, 'warning', '', '', '1', '2023-10-08 07:25:11', '1', '2023-10-08 07:25:11', b'0');
INSERT INTO `system_dict_data` VALUES (1392, 2, '管理员修改', '2', 'member_point_biz_type', 0, 'default', '', '', '1', '2023-10-11 07:41:34', '1', '2023-10-11 07:41:34', b'0');
INSERT INTO `system_dict_data` VALUES (1393, 13, '订单积分抵扣（单个退款）', '13', 'member_point_biz_type', 0, '', '', '', '1', '2023-10-11 07:42:29', '1', '2023-10-11 07:42:29', b'0');
INSERT INTO `system_dict_data` VALUES (1394, 21, '订单积分奖励', '21', 'member_point_biz_type', 0, 'default', '', '', '1', '2023-10-11 07:42:44', '1', '2023-10-11 07:42:44', b'0');
INSERT INTO `system_dict_data` VALUES (1395, 22, '订单积分奖励（整单取消）', '22', 'member_point_biz_type', 0, 'default', '', '', '1', '2023-10-11 07:42:55', '1', '2023-10-11 07:43:01', b'0');
INSERT INTO `system_dict_data` VALUES (1396, 23, '订单积分奖励（单个退款）', '23', 'member_point_biz_type', 0, 'default', '', '', '1', '2023-10-11 07:43:16', '1', '2023-10-11 07:43:16', b'0');
INSERT INTO `system_dict_data` VALUES (1397, 13, '下单奖励（单个退款）', '13', 'member_experience_biz_type', 0, 'warning', '', '', '1', '2023-10-11 07:45:24', '1', '2023-10-11 07:45:38', b'0');
INSERT INTO `system_dict_data` VALUES (1398, 5, '网上转账', '5', 'crm_receivable_return_type', 0, 'default', '', '', '1', '2023-10-18 21:55:24', '1', '2023-10-18 21:55:24', b'0');
INSERT INTO `system_dict_data` VALUES (1399, 6, '支付宝', '6', 'crm_receivable_return_type', 0, 'default', '', '', '1', '2023-10-18 21:55:38', '1', '2023-10-18 21:55:38', b'0');
INSERT INTO `system_dict_data` VALUES (1400, 7, '微信支付', '7', 'crm_receivable_return_type', 0, 'default', '', '', '1', '2023-10-18 21:55:53', '1', '2023-10-18 21:55:53', b'0');
INSERT INTO `system_dict_data` VALUES (1401, 8, '其他', '8', 'crm_receivable_return_type', 0, 'default', '', '', '1', '2023-10-18 21:56:06', '1', '2023-10-18 21:56:06', b'0');
INSERT INTO `system_dict_data` VALUES (1402, 1, 'A 农、林、牧、渔业', '1', 'crm_customer_industry', 0, 'default', '', '', '1', '2023-10-28 23:02:15', '1', '2023-10-28 23:02:15', b'0');
INSERT INTO `system_dict_data` VALUES (1403, 2, 'B 采矿业', '2', 'crm_customer_industry', 0, 'default', '', '', '1', '2023-10-28 23:02:29', '1', '2023-10-28 23:02:29', b'0');
INSERT INTO `system_dict_data` VALUES (1404, 3, 'C 制造业', '3', 'crm_customer_industry', 0, 'default', '', '', '1', '2023-10-28 23:02:41', '1', '2023-10-28 23:02:41', b'0');
INSERT INTO `system_dict_data` VALUES (1405, 4, 'D 电力、热力、燃气及水生产和供应业', '4', 'crm_customer_industry', 0, 'default', '', '', '1', '2023-10-28 23:02:54', '1', '2023-10-28 23:02:54', b'0');
INSERT INTO `system_dict_data` VALUES (1406, 5, 'E 建筑业', '5', 'crm_customer_industry', 0, 'default', '', '', '1', '2023-10-28 23:03:03', '1', '2023-10-28 23:03:03', b'0');
INSERT INTO `system_dict_data` VALUES (1407, 6, 'F 批发和零售业', '6', 'crm_customer_industry', 0, 'default', '', '', '1', '2023-10-28 23:03:13', '1', '2023-10-28 23:03:13', b'0');
INSERT INTO `system_dict_data` VALUES (1408, 7, 'G 交通运输、仓储和邮政业', '7', 'crm_customer_industry', 0, 'default', '', '', '1', '2023-10-28 23:03:27', '1', '2023-10-28 23:03:27', b'0');
INSERT INTO `system_dict_data` VALUES (1409, 8, 'H 住宿和餐饮业', '8', 'crm_customer_industry', 0, 'default', '', '', '1', '2023-10-28 23:03:37', '1', '2023-10-28 23:03:37', b'0');
INSERT INTO `system_dict_data` VALUES (1410, 9, 'I 信息传输、软件和信息技术服务业', '9', 'crm_customer_industry', 0, 'default', '', '', '1', '2023-10-28 23:03:47', '1', '2023-10-28 23:03:47', b'0');
INSERT INTO `system_dict_data` VALUES (1411, 10, 'J 金融业', '10', 'crm_customer_industry', 0, 'default', '', '', '1', '2023-10-28 23:03:57', '1', '2023-10-28 23:03:57', b'0');
INSERT INTO `system_dict_data` VALUES (1412, 11, 'K 房地产业', '11', 'crm_customer_industry', 0, 'default', '', '', '1', '2023-10-28 23:04:15', '1', '2023-10-28 23:04:22', b'0');
INSERT INTO `system_dict_data` VALUES (1413, 12, 'L 租赁和商务服务业', '12', 'crm_customer_industry', 0, 'default', '', '', '1', '2023-10-28 23:04:33', '1', '2023-10-28 23:04:33', b'0');
INSERT INTO `system_dict_data` VALUES (1414, 13, 'M 科学研究和技术服务业', '13', 'crm_customer_industry', 0, 'default', '', '', '1', '2023-10-28 23:04:43', '1', '2023-10-28 23:04:43', b'0');
INSERT INTO `system_dict_data` VALUES (1415, 14, 'N 水利、环境和公共设施管理业', '14', 'crm_customer_industry', 0, 'default', '', '', '1', '2023-10-28 23:04:53', '1', '2023-10-28 23:04:53', b'0');
INSERT INTO `system_dict_data` VALUES (1416, 15, 'O 居民服务、修理和其他服务业', '15', 'crm_customer_industry', 0, 'default', '', '', '1', '2023-10-28 23:05:05', '1', '2023-10-28 23:05:05', b'0');
INSERT INTO `system_dict_data` VALUES (1417, 16, 'P 教育', '16', 'crm_customer_industry', 0, 'default', '', '', '1', '2023-10-28 23:05:15', '1', '2023-10-28 23:05:15', b'0');
INSERT INTO `system_dict_data` VALUES (1418, 17, 'Q 卫生和社会工作', '17', 'crm_customer_industry', 0, 'default', '', '', '1', '2023-10-28 23:05:44', '1', '2023-10-28 23:05:44', b'0');
INSERT INTO `system_dict_data` VALUES (1419, 18, 'R 文化、体育和娱乐业', '18', 'crm_customer_industry', 0, 'default', '', '', '1', '2023-10-28 23:05:55', '1', '2023-10-28 23:05:55', b'0');
INSERT INTO `system_dict_data` VALUES (1420, 19, 'S 公共管理、社会保障和社会组织', '19', 'crm_customer_industry', 0, 'default', '', '', '1', '2023-10-28 23:06:05', '1', '2023-10-28 23:06:05', b'0');
INSERT INTO `system_dict_data` VALUES (1421, 20, 'T 国际组织', '20', 'crm_customer_industry', 0, 'default', '', '', '1', '2023-10-28 23:06:15', '1', '2023-10-28 23:06:15', b'0');
INSERT INTO `system_dict_data` VALUES (1422, 1, 'A （重点客户）', '1', 'crm_customer_level', 0, 'primary', '', '', '1', '2023-10-28 23:07:13', '1', '2023-10-28 23:07:13', b'0');
INSERT INTO `system_dict_data` VALUES (1423, 2, 'B （普通客户）', '2', 'crm_customer_level', 0, 'info', '', '', '1', '2023-10-28 23:07:35', '1', '2023-10-28 23:07:35', b'0');
INSERT INTO `system_dict_data` VALUES (1424, 3, 'C （非优先客户）', '3', 'crm_customer_level', 0, 'default', '', '', '1', '2023-10-28 23:07:53', '1', '2023-10-28 23:07:53', b'0');
INSERT INTO `system_dict_data` VALUES (1425, 1, '促销', '1', 'crm_customer_source', 0, 'default', '', '', '1', '2023-10-28 23:08:29', '1', '2023-10-28 23:08:29', b'0');
INSERT INTO `system_dict_data` VALUES (1426, 2, '搜索引擎', '2', 'crm_customer_source', 0, 'default', '', '', '1', '2023-10-28 23:08:39', '1', '2023-10-28 23:08:39', b'0');
INSERT INTO `system_dict_data` VALUES (1427, 3, '广告', '3', 'crm_customer_source', 0, 'default', '', '', '1', '2023-10-28 23:08:47', '1', '2023-10-28 23:08:47', b'0');
INSERT INTO `system_dict_data` VALUES (1428, 4, '转介绍', '4', 'crm_customer_source', 0, 'default', '', '', '1', '2023-10-28 23:08:58', '1', '2023-10-28 23:08:58', b'0');
INSERT INTO `system_dict_data` VALUES (1429, 5, '线上注册', '5', 'crm_customer_source', 0, 'default', '', '', '1', '2023-10-28 23:09:12', '1', '2023-10-28 23:09:12', b'0');
INSERT INTO `system_dict_data` VALUES (1430, 6, '线上咨询', '6', 'crm_customer_source', 0, 'default', '', '', '1', '2023-10-28 23:09:22', '1', '2023-10-28 23:09:22', b'0');
INSERT INTO `system_dict_data` VALUES (1431, 7, '预约上门', '7', 'crm_customer_source', 0, 'default', '', '', '1', '2023-10-28 23:09:39', '1', '2023-10-28 23:09:39', b'0');
INSERT INTO `system_dict_data` VALUES (1432, 8, '陌拜', '8', 'crm_customer_source', 0, 'default', '', '', '1', '2023-10-28 23:10:04', '1', '2023-10-28 23:10:04', b'0');
INSERT INTO `system_dict_data` VALUES (1433, 9, '电话咨询', '9', 'crm_customer_source', 0, 'default', '', '', '1', '2023-10-28 23:10:18', '1', '2023-10-28 23:10:18', b'0');
INSERT INTO `system_dict_data` VALUES (1434, 10, '邮件咨询', '10', 'crm_customer_source', 0, 'default', '', '', '1', '2023-10-28 23:10:33', '1', '2023-10-28 23:10:33', b'0');
INSERT INTO `system_dict_data` VALUES (1435, 10, 'Gitee', '10', 'system_social_type', 0, '', '', '', '1', '2023-11-04 13:04:42', '1', '2023-11-04 13:04:42', b'0');
INSERT INTO `system_dict_data` VALUES (1436, 20, '钉钉', '20', 'system_social_type', 0, '', '', '', '1', '2023-11-04 13:04:54', '1', '2023-11-04 13:04:54', b'0');
INSERT INTO `system_dict_data` VALUES (1437, 30, '企业微信', '30', 'system_social_type', 0, '', '', '', '1', '2023-11-04 13:05:09', '1', '2023-11-04 13:05:09', b'0');
INSERT INTO `system_dict_data` VALUES (1438, 31, '微信公众平台', '31', 'system_social_type', 0, '', '', '', '1', '2023-11-04 13:05:18', '1', '2023-11-04 13:05:18', b'0');
INSERT INTO `system_dict_data` VALUES (1439, 32, '微信开放平台', '32', 'system_social_type', 0, '', '', '', '1', '2023-11-04 13:05:30', '1', '2023-11-04 13:05:30', b'0');
INSERT INTO `system_dict_data` VALUES (1440, 34, '微信小程序', '34', 'system_social_type', 0, '', '', '', '1', '2023-11-04 13:05:38', '1', '2023-11-04 13:07:16', b'0');
INSERT INTO `system_dict_data` VALUES (1441, 1, '上架', '1', 'crm_product_status', 0, 'success', '', '', '1', '2023-10-30 21:49:34', '1', '2023-10-30 21:49:34', b'0');
INSERT INTO `system_dict_data` VALUES (1442, 0, '下架', '0', 'crm_product_status', 0, 'success', '', '', '1', '2023-10-30 21:49:13', '1', '2023-10-30 21:49:13', b'0');
INSERT INTO `system_dict_data` VALUES (1443, 15, '子表', '15', 'infra_codegen_template_type', 0, 'default', '', '', '1', '2023-11-13 23:06:16', '1', '2023-11-13 23:06:16', b'0');
INSERT INTO `system_dict_data` VALUES (1444, 10, '主表（标准模式）', '10', 'infra_codegen_template_type', 0, 'default', '', '', '1', '2023-11-14 12:32:49', '1', '2023-11-14 12:32:49', b'0');
INSERT INTO `system_dict_data` VALUES (1445, 11, '主表（ERP 模式）', '11', 'infra_codegen_template_type', 0, 'default', '', '', '1', '2023-11-14 12:33:05', '1', '2023-11-14 12:33:05', b'0');
INSERT INTO `system_dict_data` VALUES (1446, 12, '主表（内嵌模式）', '12', 'infra_codegen_template_type', 0, '', '', '', '1', '2023-11-14 12:33:31', '1', '2023-11-14 12:33:31', b'0');
INSERT INTO `system_dict_data` VALUES (1447, 1, '负责人', '1', 'crm_permission_level', 0, 'default', '', '', '1', '2023-11-30 09:53:12', '1', '2023-11-30 09:53:12', b'0');
INSERT INTO `system_dict_data` VALUES (1448, 2, '只读', '2', 'crm_permission_level', 0, '', '', '', '1', '2023-11-30 09:53:29', '1', '2023-11-30 09:53:29', b'0');
INSERT INTO `system_dict_data` VALUES (1449, 3, '读写', '3', 'crm_permission_level', 0, '', '', '', '1', '2023-11-30 09:53:36', '1', '2023-11-30 09:53:36', b'0');
INSERT INTO `system_dict_data` VALUES (1450, 0, '未提交', '0', 'crm_audit_status', 0, '', '', '', '1', '2023-11-30 18:56:59', '1', '2023-11-30 18:56:59', b'0');
INSERT INTO `system_dict_data` VALUES (1451, 10, '审批中', '10', 'crm_audit_status', 0, '', '', '', '1', '2023-11-30 18:57:10', '1', '2023-11-30 18:57:10', b'0');
INSERT INTO `system_dict_data` VALUES (1452, 20, '审核通过', '20', 'crm_audit_status', 0, '', '', '', '1', '2023-11-30 18:57:24', '1', '2023-11-30 18:57:24', b'0');
INSERT INTO `system_dict_data` VALUES (1453, 30, '审核不通过', '30', 'crm_audit_status', 0, '', '', '', '1', '2023-11-30 18:57:32', '1', '2023-11-30 18:57:32', b'0');
INSERT INTO `system_dict_data` VALUES (1454, 40, '已取消', '40', 'crm_audit_status', 0, '', '', '', '1', '2023-11-30 18:57:42', '1', '2023-11-30 18:57:42', b'0');
INSERT INTO `system_dict_data` VALUES (1456, 1, '支票', '1', 'crm_receivable_return_type', 0, 'default', '', '', '1', '2023-10-18 21:54:29', '1', '2023-10-18 21:54:29', b'0');
INSERT INTO `system_dict_data` VALUES (1457, 2, '现金', '2', 'crm_receivable_return_type', 0, 'default', '', '', '1', '2023-10-18 21:54:41', '1', '2023-10-18 21:54:41', b'0');
INSERT INTO `system_dict_data` VALUES (1458, 3, '邮政汇款', '3', 'crm_receivable_return_type', 0, 'default', '', '', '1', '2023-10-18 21:54:53', '1', '2023-10-18 21:54:53', b'0');
INSERT INTO `system_dict_data` VALUES (1459, 4, '电汇', '4', 'crm_receivable_return_type', 0, 'default', '', '', '1', '2023-10-18 21:55:07', '1', '2023-10-18 21:55:07', b'0');
INSERT INTO `system_dict_data` VALUES (1460, 5, '网上转账', '5', 'crm_receivable_return_type', 0, 'default', '', '', '1', '2023-10-18 21:55:24', '1', '2023-10-18 21:55:24', b'0');
INSERT INTO `system_dict_data` VALUES (1461, 1, '个', '1', 'crm_product_unit', 0, '', '', '', '1', '2023-12-05 23:02:26', '1', '2023-12-05 23:02:26', b'0');
INSERT INTO `system_dict_data` VALUES (1462, 2, '块', '2', 'crm_product_unit', 0, '', '', '', '1', '2023-12-05 23:02:34', '1', '2023-12-05 23:02:34', b'0');
INSERT INTO `system_dict_data` VALUES (1463, 3, '只', '3', 'crm_product_unit', 0, '', '', '', '1', '2023-12-05 23:02:57', '1', '2023-12-05 23:02:57', b'0');
INSERT INTO `system_dict_data` VALUES (1464, 4, '把', '4', 'crm_product_unit', 0, '', '', '', '1', '2023-12-05 23:03:05', '1', '2023-12-05 23:03:05', b'0');
INSERT INTO `system_dict_data` VALUES (1465, 5, '枚', '5', 'crm_product_unit', 0, '', '', '', '1', '2023-12-05 23:03:14', '1', '2023-12-05 23:03:14', b'0');
INSERT INTO `system_dict_data` VALUES (1466, 6, '瓶', '6', 'crm_product_unit', 0, '', '', '', '1', '2023-12-05 23:03:20', '1', '2023-12-05 23:03:20', b'0');
INSERT INTO `system_dict_data` VALUES (1467, 7, '盒', '7', 'crm_product_unit', 0, '', '', '', '1', '2023-12-05 23:03:30', '1', '2023-12-05 23:03:30', b'0');
INSERT INTO `system_dict_data` VALUES (1468, 8, '台', '8', 'crm_product_unit', 0, '', '', '', '1', '2023-12-05 23:03:41', '1', '2023-12-05 23:03:41', b'0');
INSERT INTO `system_dict_data` VALUES (1469, 9, '吨', '9', 'crm_product_unit', 0, '', '', '', '1', '2023-12-05 23:03:48', '1', '2023-12-05 23:03:48', b'0');
INSERT INTO `system_dict_data` VALUES (1470, 10, '千克', '10', 'crm_product_unit', 0, '', '', '', '1', '2023-12-05 23:04:03', '1', '2023-12-05 23:04:03', b'0');
INSERT INTO `system_dict_data` VALUES (1471, 11, '米', '11', 'crm_product_unit', 0, '', '', '', '1', '2023-12-05 23:04:12', '1', '2023-12-05 23:04:12', b'0');
INSERT INTO `system_dict_data` VALUES (1472, 12, '箱', '12', 'crm_product_unit', 0, '', '', '', '1', '2023-12-05 23:04:25', '1', '2023-12-05 23:04:25', b'0');
INSERT INTO `system_dict_data` VALUES (1473, 13, '套', '13', 'crm_product_unit', 0, '', '', '', '1', '2023-12-05 23:04:34', '1', '2023-12-05 23:04:34', b'0');
INSERT INTO `system_dict_data` VALUES (1474, 1, '打电话', '1', 'crm_follow_up_type', 0, '', '', '', '1', '2024-01-15 20:48:20', '1', '2024-01-15 20:48:20', b'0');
INSERT INTO `system_dict_data` VALUES (1475, 2, '发短信', '2', 'crm_follow_up_type', 0, '', '', '', '1', '2024-01-15 20:48:31', '1', '2024-01-15 20:48:31', b'0');
INSERT INTO `system_dict_data` VALUES (1476, 3, '上门拜访', '3', 'crm_follow_up_type', 0, '', '', '', '1', '2024-01-15 20:49:07', '1', '2024-01-15 20:49:07', b'0');
INSERT INTO `system_dict_data` VALUES (1477, 4, '微信沟通', '4', 'crm_follow_up_type', 0, '', '', '', '1', '2024-01-15 20:49:15', '1', '2024-01-15 20:49:15', b'0');
INSERT INTO `system_dict_data` VALUES (1478, 4, '钱包余额', '4', 'pay_transfer_type', 0, 'info', '', '', '1', '2023-10-28 16:28:37', '1', '2023-10-28 16:28:37', b'0');
INSERT INTO `system_dict_data` VALUES (1479, 3, '银行卡', '3', 'pay_transfer_type', 0, 'default', '', '', '1', '2023-10-28 16:28:21', '1', '2023-10-28 16:28:21', b'0');
INSERT INTO `system_dict_data` VALUES (1480, 2, '微信余额', '2', 'pay_transfer_type', 0, 'info', '', '', '1', '2023-10-28 16:28:07', '1', '2023-10-28 16:28:07', b'0');
INSERT INTO `system_dict_data` VALUES (1481, 1, '支付宝余额', '1', 'pay_transfer_type', 0, 'default', '', '', '1', '2023-10-28 16:27:44', '1', '2023-10-28 16:27:44', b'0');
INSERT INTO `system_dict_data` VALUES (1482, 4, '转账失败', '30', 'pay_transfer_status', 0, 'warning', '', '', '1', '2023-10-28 16:24:16', '1', '2023-10-28 16:24:16', b'0');
INSERT INTO `system_dict_data` VALUES (1483, 3, '转账成功', '20', 'pay_transfer_status', 0, 'success', '', '', '1', '2023-10-28 16:23:50', '1', '2023-10-28 16:23:50', b'0');
INSERT INTO `system_dict_data` VALUES (1484, 2, '转账进行中', '10', 'pay_transfer_status', 0, 'info', '', '', '1', '2023-10-28 16:23:12', '1', '2023-10-28 16:23:12', b'0');
INSERT INTO `system_dict_data` VALUES (1485, 1, '等待转账', '0', 'pay_transfer_status', 0, 'default', '', '', '1', '2023-10-28 16:21:43', '1', '2023-10-28 16:23:22', b'0');

-- ----------------------------
-- Table structure for system_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `system_dict_type`;
CREATE TABLE `system_dict_type`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典名称',
  `type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典类型',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `deleted_time` datetime NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `dict_type`(`type` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 611 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_dict_type
-- ----------------------------
INSERT INTO `system_dict_type` VALUES (1, '用户性别', 'system_user_sex', 0, NULL, 'admin', '2021-01-05 17:03:48', '1', '2022-05-16 20:29:32', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (6, '参数类型', 'infra_config_type', 0, NULL, 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:36:54', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (7, '通知类型', 'system_notice_type', 0, NULL, 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:35:26', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (9, '操作类型', 'system_operate_type', 0, NULL, 'admin', '2021-01-05 17:03:48', '1', '2022-02-16 09:32:21', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (10, '系统状态', 'common_status', 0, NULL, 'admin', '2021-01-05 17:03:48', '', '2022-02-01 16:21:28', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (11, 'Boolean 是否类型', 'infra_boolean_string', 0, 'boolean 转是否', '', '2021-01-19 03:20:08', '', '2022-02-01 16:37:10', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (104, '登陆结果', 'system_login_result', 0, '登陆结果', '', '2021-01-18 06:17:11', '', '2022-02-01 16:36:00', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (106, '代码生成模板类型', 'infra_codegen_template_type', 0, NULL, '', '2021-02-05 07:08:06', '1', '2022-05-16 20:26:50', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (107, '定时任务状态', 'infra_job_status', 0, NULL, '', '2021-02-07 07:44:16', '', '2022-02-01 16:51:11', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (108, '定时任务日志状态', 'infra_job_log_status', 0, NULL, '', '2021-02-08 10:03:51', '', '2022-02-01 16:50:43', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (109, '用户类型', 'user_type', 0, NULL, '', '2021-02-26 00:15:51', '', '2021-02-26 00:15:51', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (110, 'API 异常数据的处理状态', 'infra_api_error_log_process_status', 0, NULL, '', '2021-02-26 07:07:01', '', '2022-02-01 16:50:53', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (111, '短信渠道编码', 'system_sms_channel_code', 0, NULL, '1', '2021-04-05 01:04:50', '1', '2022-02-16 02:09:08', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (112, '短信模板的类型', 'system_sms_template_type', 0, NULL, '1', '2021-04-05 21:50:43', '1', '2022-02-01 16:35:06', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (113, '短信发送状态', 'system_sms_send_status', 0, NULL, '1', '2021-04-11 20:18:03', '1', '2022-02-01 16:35:09', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (114, '短信接收状态', 'system_sms_receive_status', 0, NULL, '1', '2021-04-11 20:27:14', '1', '2022-02-01 16:35:14', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (115, '错误码的类型', 'system_error_code_type', 0, NULL, '1', '2021-04-21 00:06:30', '1', '2022-02-01 16:36:49', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (116, '登陆日志的类型', 'system_login_type', 0, '登陆日志的类型', '1', '2021-10-06 00:50:46', '1', '2022-02-01 16:35:56', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (117, 'OA 请假类型', 'bpm_oa_leave_type', 0, NULL, '1', '2021-09-21 22:34:33', '1', '2022-01-22 10:41:37', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (130, '支付渠道编码类型', 'pay_channel_code', 0, '支付渠道的编码', '1', '2021-12-03 10:35:08', '1', '2023-07-10 10:11:39', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (131, '支付回调状态', 'pay_notify_status', 0, '支付回调状态（包括退款回调）', '1', '2021-12-03 10:53:29', '1', '2023-07-19 18:09:43', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (132, '支付订单状态', 'pay_order_status', 0, '支付订单状态', '1', '2021-12-03 11:17:50', '1', '2021-12-03 11:17:50', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (134, '退款订单状态', 'pay_refund_status', 0, '退款订单状态', '1', '2021-12-10 16:42:50', '1', '2023-07-19 10:13:17', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (138, '流程分类', 'bpm_model_category', 0, '流程分类', '1', '2022-01-02 08:40:45', '1', '2022-01-02 08:40:45', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (139, '流程实例的状态', 'bpm_process_instance_status', 0, '流程实例的状态', '1', '2022-01-07 23:46:42', '1', '2022-01-07 23:46:42', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (140, '流程实例的结果', 'bpm_process_instance_result', 0, '流程实例的结果', '1', '2022-01-07 23:48:10', '1', '2022-01-07 23:48:10', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (141, '流程的表单类型', 'bpm_model_form_type', 0, '流程的表单类型', '103', '2022-01-11 23:50:45', '103', '2022-01-11 23:50:45', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (142, '任务分配规则的类型', 'bpm_task_assign_rule_type', 0, '任务分配规则的类型', '103', '2022-01-12 23:21:04', '103', '2022-01-12 15:46:10', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (143, '任务分配自定义脚本', 'bpm_task_assign_script', 0, '任务分配自定义脚本', '103', '2022-01-15 00:10:35', '103', '2022-01-15 00:10:35', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (144, '代码生成的场景枚举', 'infra_codegen_scene', 0, '代码生成的场景枚举', '1', '2022-02-02 13:14:45', '1', '2022-03-10 16:33:46', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (145, '角色类型', 'system_role_type', 0, '角色类型', '1', '2022-02-16 13:01:46', '1', '2022-02-16 13:01:46', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (146, '文件存储器', 'infra_file_storage', 0, '文件存储器', '1', '2022-03-15 00:24:38', '1', '2022-03-15 00:24:38', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (147, 'OAuth 2.0 授权类型', 'system_oauth2_grant_type', 0, 'OAuth 2.0 授权类型（模式）', '1', '2022-05-12 00:20:52', '1', '2022-05-11 16:25:49', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (149, '商品 SPU 状态', 'product_spu_status', 0, '商品 SPU 状态', '1', '2022-10-24 21:19:04', '1', '2022-10-24 21:19:08', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (150, '优惠类型', 'promotion_discount_type', 0, '优惠类型', '1', '2022-11-01 12:46:06', '1', '2022-11-01 12:46:06', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (151, '优惠劵模板的有限期类型', 'promotion_coupon_template_validity_type', 0, '优惠劵模板的有限期类型', '1', '2022-11-02 00:06:20', '1', '2022-11-04 00:08:26', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (152, '营销的商品范围', 'promotion_product_scope', 0, '营销的商品范围', '1', '2022-11-02 00:28:01', '1', '2022-11-02 00:28:01', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (153, '优惠劵的状态', 'promotion_coupon_status', 0, '优惠劵的状态', '1', '2022-11-04 00:14:49', '1', '2022-11-04 00:14:49', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (154, '优惠劵的领取方式', 'promotion_coupon_take_type', 0, '优惠劵的领取方式', '1', '2022-11-04 19:12:27', '1', '2022-11-04 19:12:27', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (155, '促销活动的状态', 'promotion_activity_status', 0, '促销活动的状态', '1', '2022-11-04 22:54:23', '1', '2022-11-04 22:54:23', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (156, '营销的条件类型', 'promotion_condition_type', 0, '营销的条件类型', '1', '2022-11-04 22:59:23', '1', '2022-11-04 22:59:23', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (157, '交易售后状态', 'trade_after_sale_status', 0, '交易售后状态', '1', '2022-11-19 20:52:56', '1', '2022-11-19 20:52:56', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (158, '交易售后的类型', 'trade_after_sale_type', 0, '交易售后的类型', '1', '2022-11-19 21:04:09', '1', '2022-11-19 21:04:09', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (159, '交易售后的方式', 'trade_after_sale_way', 0, '交易售后的方式', '1', '2022-11-19 21:39:04', '1', '2022-11-19 21:39:04', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (160, '终端', 'terminal', 0, '终端', '1', '2022-12-10 10:50:50', '1', '2022-12-10 10:53:11', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (161, '交易订单的类型', 'trade_order_type', 0, '交易订单的类型', '1', '2022-12-10 16:33:54', '1', '2022-12-10 16:33:54', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (162, '交易订单的状态', 'trade_order_status', 0, '交易订单的状态', '1', '2022-12-10 16:48:44', '1', '2022-12-10 16:48:44', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (163, '交易订单项的售后状态', 'trade_order_item_after_sale_status', 0, '交易订单项的售后状态', '1', '2022-12-10 20:58:08', '1', '2022-12-10 20:58:08', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (164, '公众号自动回复的请求关键字匹配模式', 'mp_auto_reply_request_match', 0, '公众号自动回复的请求关键字匹配模式', '1', '2023-01-16 23:29:56', '1', '2023-01-16 23:29:56', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (165, '公众号的消息类型', 'mp_message_type', 0, '公众号的消息类型', '1', '2023-01-17 22:17:09', '1', '2023-01-17 22:17:09', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (166, '邮件发送状态', 'system_mail_send_status', 0, '邮件发送状态', '1', '2023-01-26 09:53:13', '1', '2023-01-26 09:53:13', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (167, '站内信模版的类型', 'system_notify_template_type', 0, '站内信模版的类型', '1', '2023-01-28 10:35:10', '1', '2023-01-28 10:35:10', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (168, '代码生成的前端类型', 'infra_codegen_front_type', 0, '', '1', '2023-04-12 23:57:52', '1', '2023-04-12 23:57:52', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (169, '商品的单位', 'product_unit', 0, '商品的单位', '1', '2023-05-24 21:23:59', '1', '2023-05-24 21:23:59', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (170, '快递计费方式', 'trade_delivery_express_charge_mode', 0, '用于商城交易模块配送管理', '1', '2023-05-21 22:45:03', '1', '2023-05-21 22:45:03', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (171, '积分业务类型', 'member_point_biz_type', 0, '', '1', '2023-06-10 12:15:00', '1', '2023-06-28 13:48:20', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (173, '支付通知类型', 'pay_notify_type', 0, NULL, '1', '2023-07-20 12:23:03', '1', '2023-07-20 12:23:03', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (174, '会员经验业务类型', 'member_experience_biz_type', 0, NULL, '', '2023-08-22 12:41:01', '', '2023-08-22 12:41:01', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (175, '交易配送类型', 'trade_delivery_type', 0, '', '1', '2023-08-23 00:03:14', '1', '2023-08-23 00:03:14', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (176, '分佣模式', 'brokerage_enabled_condition', 0, NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (177, '分销关系绑定模式', 'brokerage_bind_mode', 0, NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (178, '佣金提现类型', 'brokerage_withdraw_type', 0, NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (179, '佣金记录业务类型', 'brokerage_record_biz_type', 0, NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (180, '佣金记录状态', 'brokerage_record_status', 0, NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (181, '佣金提现状态', 'brokerage_withdraw_status', 0, NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (182, '佣金提现银行', 'brokerage_bank_name', 0, NULL, '', '2023-09-28 02:46:05', '', '2023-09-28 02:46:05', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (183, '砍价记录的状态', 'promotion_bargain_record_status', 0, '', '1', '2023-10-05 10:41:08', '1', '2023-10-05 10:41:08', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (184, '拼团记录的状态', 'promotion_combination_record_status', 0, '', '1', '2023-10-08 07:24:25', '1', '2023-10-08 07:24:25', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (185, '回款-回款方式', 'crm_receivable_return_type', 0, '回款-回款方式', '1', '2023-10-18 21:54:10', '1', '2023-10-18 21:54:10', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (186, '客户所属行业', 'crm_customer_industry', 0, 'CRM 客户所属行业', '1', '2023-10-28 22:57:07', '1', '2023-10-28 15:11:16', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (187, '客户等级', 'crm_customer_level', 0, 'CRM 客户等级', '1', '2023-10-28 22:59:12', '1', '2023-10-28 15:11:16', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (188, '客户来源', 'crm_customer_source', 0, 'CRM 客户来源', '1', '2023-10-28 23:00:34', '1', '2023-10-28 15:11:16', b'0', NULL);
INSERT INTO `system_dict_type` VALUES (600, 'Banner 位置', 'promotion_banner_position', 0, '', '1', '2023-10-08 07:24:25', '1', '2023-11-04 13:04:02', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (601, '社交类型', 'system_social_type', 0, '', '1', '2023-11-04 13:03:54', '1', '2023-11-04 13:03:54', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (604, '产品状态', 'crm_product_status', 0, '', '1', '2023-10-30 21:47:59', '1', '2023-10-30 21:48:45', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (605, 'CRM 数据权限的级别', 'crm_permission_level', 0, '', '1', '2023-11-30 09:51:59', '1', '2023-11-30 09:51:59', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (606, 'CRM 审批状态', 'crm_audit_status', 0, '', '1', '2023-11-30 18:56:23', '1', '2023-11-30 18:56:23', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (607, 'CRM 产品单位', 'crm_product_unit', 0, '', '1', '2023-12-05 23:01:51', '1', '2023-12-05 23:01:51', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (608, 'CRM 跟进方式', 'crm_follow_up_type', 0, '', '1', '2024-01-15 20:48:05', '1', '2024-01-15 20:48:05', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (609, '支付转账类型', 'pay_transfer_type', 0, '', '1', '2023-10-28 16:27:18', '1', '2023-10-28 16:27:18', b'0', '1970-01-01 00:00:00');
INSERT INTO `system_dict_type` VALUES (610, '转账订单状态', 'pay_transfer_status', 0, '', '1', '2023-10-28 16:18:32', '1', '2023-10-28 16:18:32', b'0', '1970-01-01 00:00:00');

-- ----------------------------
-- Table structure for system_error_code
-- ----------------------------
DROP TABLE IF EXISTS `system_error_code`;
CREATE TABLE `system_error_code`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '错误码编号',
  `type` tinyint NOT NULL DEFAULT 0 COMMENT '错误码类型',
  `application_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '应用名',
  `code` int NOT NULL DEFAULT 0 COMMENT '错误码编码',
  `message` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '错误码错误提示',
  `memo` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '备注',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6039 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '错误码表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_error_code
-- ----------------------------

-- ----------------------------
-- Table structure for system_login_log
-- ----------------------------
DROP TABLE IF EXISTS `system_login_log`;
CREATE TABLE `system_login_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `log_type` bigint NOT NULL COMMENT '日志类型',
  `trace_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '链路追踪编号',
  `user_id` bigint NOT NULL DEFAULT 0 COMMENT '用户编号',
  `user_type` tinyint NOT NULL DEFAULT 0 COMMENT '用户类型',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '用户账号',
  `result` tinyint NOT NULL COMMENT '登陆结果',
  `user_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户 IP',
  `user_agent` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '浏览器 UA',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2943 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统访问记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_login_log
-- ----------------------------
INSERT INTO `system_login_log` VALUES (2861, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', NULL, '2024-02-17 11:48:21', NULL, '2024-02-17 11:48:21', b'0', 1);
INSERT INTO `system_login_log` VALUES (2862, 200, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', '1', '2024-02-17 11:52:31', '1', '2024-02-17 11:52:31', b'0', 1);
INSERT INTO `system_login_log` VALUES (2863, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', NULL, '2024-02-17 11:52:39', NULL, '2024-02-17 11:52:39', b'0', 1);
INSERT INTO `system_login_log` VALUES (2864, 200, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', '1', '2024-02-17 13:36:04', '1', '2024-02-17 13:36:04', b'0', 1);
INSERT INTO `system_login_log` VALUES (2865, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', NULL, '2024-02-17 13:36:11', NULL, '2024-02-17 13:36:11', b'0', 1);
INSERT INTO `system_login_log` VALUES (2866, 200, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', '1', '2024-02-17 15:17:51', '1', '2024-02-17 15:17:51', b'0', 1);
INSERT INTO `system_login_log` VALUES (2867, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', NULL, '2024-02-17 15:17:56', NULL, '2024-02-17 15:17:56', b'0', 1);
INSERT INTO `system_login_log` VALUES (2868, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', NULL, '2024-02-17 16:49:17', NULL, '2024-02-17 16:49:17', b'0', 1);
INSERT INTO `system_login_log` VALUES (2869, 200, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', '1', '2024-02-17 16:54:51', '1', '2024-02-17 16:54:51', b'0', 1);
INSERT INTO `system_login_log` VALUES (2870, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', NULL, '2024-02-17 16:55:03', NULL, '2024-02-17 16:55:03', b'0', 1);
INSERT INTO `system_login_log` VALUES (2871, 200, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', '1', '2024-02-17 16:59:04', '1', '2024-02-17 16:59:04', b'0', 1);
INSERT INTO `system_login_log` VALUES (2872, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', NULL, '2024-02-17 16:59:08', NULL, '2024-02-17 16:59:08', b'0', 1);
INSERT INTO `system_login_log` VALUES (2873, 200, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', '1', '2024-02-17 17:34:59', '1', '2024-02-17 17:34:59', b'0', 1);
INSERT INTO `system_login_log` VALUES (2874, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', NULL, '2024-02-17 17:35:09', NULL, '2024-02-17 17:35:09', b'0', 1);
INSERT INTO `system_login_log` VALUES (2875, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', NULL, '2024-02-19 10:40:23', NULL, '2024-02-19 10:40:23', b'0', 1);
INSERT INTO `system_login_log` VALUES (2876, 200, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', '1', '2024-02-19 11:17:22', '1', '2024-02-19 11:17:22', b'0', 1);
INSERT INTO `system_login_log` VALUES (2877, 100, '', 110, 2, 'admin110', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', NULL, '2024-02-19 11:18:10', NULL, '2024-02-19 11:18:10', b'0', 121);
INSERT INTO `system_login_log` VALUES (2878, 200, '', 110, 2, 'admin110', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', '110', '2024-02-19 11:28:06', '110', '2024-02-19 11:28:06', b'0', 121);
INSERT INTO `system_login_log` VALUES (2879, 100, '', 129, 2, 'meng', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', NULL, '2024-02-19 11:28:17', NULL, '2024-02-19 11:28:17', b'0', 153);
INSERT INTO `system_login_log` VALUES (2880, 200, '', 129, 2, 'meng', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', '129', '2024-02-19 16:56:49', '129', '2024-02-19 16:56:49', b'0', 153);
INSERT INTO `system_login_log` VALUES (2881, 100, '', 129, 2, 'meng', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', NULL, '2024-02-19 16:57:06', NULL, '2024-02-19 16:57:06', b'0', 153);
INSERT INTO `system_login_log` VALUES (2882, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', NULL, '2024-02-28 13:41:03', NULL, '2024-02-28 13:41:03', b'0', 1);
INSERT INTO `system_login_log` VALUES (2883, 200, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', '1', '2024-02-28 14:01:47', '1', '2024-02-28 14:01:47', b'0', 1);
INSERT INTO `system_login_log` VALUES (2884, 100, '', 129, 2, 'meng', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', NULL, '2024-02-28 14:02:06', NULL, '2024-02-28 14:02:06', b'0', 153);
INSERT INTO `system_login_log` VALUES (2885, 200, '', 129, 2, 'meng', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', '129', '2024-02-28 14:03:59', '129', '2024-02-28 14:03:59', b'0', 153);
INSERT INTO `system_login_log` VALUES (2886, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', NULL, '2024-02-28 14:07:59', NULL, '2024-02-28 14:07:59', b'0', 1);
INSERT INTO `system_login_log` VALUES (2887, 200, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', '1', '2024-02-28 14:08:39', '1', '2024-02-28 14:08:39', b'0', 1);
INSERT INTO `system_login_log` VALUES (2888, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', NULL, '2024-02-28 14:13:01', NULL, '2024-02-28 14:13:01', b'0', 1);
INSERT INTO `system_login_log` VALUES (2889, 200, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', '1', '2024-02-28 14:13:15', '1', '2024-02-28 14:13:15', b'0', 1);
INSERT INTO `system_login_log` VALUES (2890, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', NULL, '2024-02-28 14:16:55', NULL, '2024-02-28 14:16:55', b'0', 1);
INSERT INTO `system_login_log` VALUES (2891, 200, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', '1', '2024-02-28 14:21:32', '1', '2024-02-28 14:21:32', b'0', 1);
INSERT INTO `system_login_log` VALUES (2892, 100, '', 129, 2, 'meng', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', NULL, '2024-02-28 14:22:07', NULL, '2024-02-28 14:22:07', b'0', 153);
INSERT INTO `system_login_log` VALUES (2893, 200, '', 129, 2, 'meng', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', '129', '2024-02-28 14:23:16', '129', '2024-02-28 14:23:16', b'0', 153);
INSERT INTO `system_login_log` VALUES (2894, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', NULL, '2024-02-28 14:23:58', NULL, '2024-02-28 14:23:58', b'0', 1);
INSERT INTO `system_login_log` VALUES (2895, 200, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', '1', '2024-02-28 14:24:11', '1', '2024-02-28 14:24:11', b'0', 1);
INSERT INTO `system_login_log` VALUES (2896, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', NULL, '2024-02-28 14:24:50', NULL, '2024-02-28 14:24:50', b'0', 1);
INSERT INTO `system_login_log` VALUES (2897, 200, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', '1', '2024-02-28 14:31:57', '1', '2024-02-28 14:31:57', b'0', 1);
INSERT INTO `system_login_log` VALUES (2898, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', NULL, '2024-02-28 14:32:05', NULL, '2024-02-28 14:32:05', b'0', 1);
INSERT INTO `system_login_log` VALUES (2899, 200, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', '1', '2024-02-28 14:34:10', '1', '2024-02-28 14:34:10', b'0', 1);
INSERT INTO `system_login_log` VALUES (2900, 100, '', 129, 2, 'meng', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', NULL, '2024-02-28 14:34:32', NULL, '2024-02-28 14:34:32', b'0', 153);
INSERT INTO `system_login_log` VALUES (2901, 200, '', 129, 2, 'meng', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', '129', '2024-02-28 14:35:48', '129', '2024-02-28 14:35:48', b'0', 153);
INSERT INTO `system_login_log` VALUES (2902, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', NULL, '2024-02-28 14:35:53', NULL, '2024-02-28 14:35:53', b'0', 1);
INSERT INTO `system_login_log` VALUES (2903, 200, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', '1', '2024-02-28 14:37:57', '1', '2024-02-28 14:37:57', b'0', 1);
INSERT INTO `system_login_log` VALUES (2904, 100, '', 129, 2, 'meng', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', NULL, '2024-02-28 14:39:01', NULL, '2024-02-28 14:39:01', b'0', 153);
INSERT INTO `system_login_log` VALUES (2905, 200, '', 129, 2, 'meng', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', '129', '2024-02-28 14:43:26', '129', '2024-02-28 14:43:26', b'0', 153);
INSERT INTO `system_login_log` VALUES (2906, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', NULL, '2024-02-28 14:44:57', NULL, '2024-02-28 14:44:57', b'0', 1);
INSERT INTO `system_login_log` VALUES (2907, 200, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', '1', '2024-02-28 15:05:05', '1', '2024-02-28 15:05:05', b'0', 1);
INSERT INTO `system_login_log` VALUES (2908, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', NULL, '2024-02-28 15:07:49', NULL, '2024-02-28 15:07:49', b'0', 1);
INSERT INTO `system_login_log` VALUES (2909, 200, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', '1', '2024-02-28 15:09:09', '1', '2024-02-28 15:09:09', b'0', 1);
INSERT INTO `system_login_log` VALUES (2910, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', NULL, '2024-02-28 15:09:13', NULL, '2024-02-28 15:09:13', b'0', 1);
INSERT INTO `system_login_log` VALUES (2911, 200, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', NULL, '2024-02-28 17:17:35', NULL, '2024-02-28 17:17:35', b'0', 1);
INSERT INTO `system_login_log` VALUES (2912, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', NULL, '2024-02-28 18:00:19', NULL, '2024-02-28 18:00:19', b'0', 1);
INSERT INTO `system_login_log` VALUES (2913, 200, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', '1', '2024-02-28 18:07:00', '1', '2024-02-28 18:07:00', b'0', 1);
INSERT INTO `system_login_log` VALUES (2914, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', NULL, '2024-02-28 18:07:03', NULL, '2024-02-28 18:07:03', b'0', 1);
INSERT INTO `system_login_log` VALUES (2915, 200, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', '1', '2024-02-28 18:23:07', '1', '2024-02-28 18:23:07', b'0', 1);
INSERT INTO `system_login_log` VALUES (2916, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', NULL, '2024-02-28 18:23:26', NULL, '2024-02-28 18:23:26', b'0', 1);
INSERT INTO `system_login_log` VALUES (2917, 200, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', '1', '2024-02-28 18:27:51', '1', '2024-02-28 18:27:51', b'0', 1);
INSERT INTO `system_login_log` VALUES (2918, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', NULL, '2024-02-28 18:30:38', NULL, '2024-02-28 18:30:38', b'0', 1);
INSERT INTO `system_login_log` VALUES (2919, 200, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', '1', '2024-02-28 18:43:21', '1', '2024-02-28 18:43:21', b'0', 1);
INSERT INTO `system_login_log` VALUES (2920, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', NULL, '2024-02-28 18:43:25', NULL, '2024-02-28 18:43:25', b'0', 1);
INSERT INTO `system_login_log` VALUES (2921, 200, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', '1', '2024-02-28 18:45:16', '1', '2024-02-28 18:45:16', b'0', 1);
INSERT INTO `system_login_log` VALUES (2922, 100, '', 129, 2, 'meng', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', NULL, '2024-02-28 18:45:28', NULL, '2024-02-28 18:45:28', b'0', 153);
INSERT INTO `system_login_log` VALUES (2923, 200, '', 129, 2, 'g-admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', '129', '2024-02-28 18:46:48', '129', '2024-02-28 18:46:48', b'0', 153);
INSERT INTO `system_login_log` VALUES (2924, 100, '', 129, 2, 'gadmin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', NULL, '2024-02-28 18:47:43', NULL, '2024-02-28 18:47:43', b'0', 153);
INSERT INTO `system_login_log` VALUES (2925, 200, '', 129, 2, 'gadmin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', '129', '2024-02-28 18:50:30', '129', '2024-02-28 18:50:30', b'0', 153);
INSERT INTO `system_login_log` VALUES (2926, 100, '', 129, 2, 'gadmin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', NULL, '2024-02-28 18:50:45', NULL, '2024-02-28 18:50:45', b'0', 153);
INSERT INTO `system_login_log` VALUES (2927, 200, '', 129, 2, 'gadmin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', '129', '2024-02-28 18:51:16', '129', '2024-02-28 18:51:16', b'0', 153);
INSERT INTO `system_login_log` VALUES (2928, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', NULL, '2024-02-28 18:51:18', NULL, '2024-02-28 18:51:18', b'0', 1);
INSERT INTO `system_login_log` VALUES (2929, 200, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', '1', '2024-02-28 18:51:56', '1', '2024-02-28 18:51:56', b'0', 1);
INSERT INTO `system_login_log` VALUES (2930, 100, '', 129, 2, 'gadmin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', NULL, '2024-02-28 18:52:08', NULL, '2024-02-28 18:52:08', b'0', 153);
INSERT INTO `system_login_log` VALUES (2931, 200, '', 129, 2, 'gadmin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', '129', '2024-02-28 18:55:46', '129', '2024-02-28 18:55:46', b'0', 153);
INSERT INTO `system_login_log` VALUES (2932, 100, '', 129, 2, 'gadmin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', NULL, '2024-02-28 18:55:56', NULL, '2024-02-28 18:55:56', b'0', 153);
INSERT INTO `system_login_log` VALUES (2933, 200, '', 129, 2, 'gadmin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', '129', '2024-02-28 18:56:52', '129', '2024-02-28 18:56:52', b'0', 153);
INSERT INTO `system_login_log` VALUES (2934, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', NULL, '2024-02-28 18:56:54', NULL, '2024-02-28 18:56:54', b'0', 1);
INSERT INTO `system_login_log` VALUES (2935, 200, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', '1', '2024-02-28 18:58:08', '1', '2024-02-28 18:58:08', b'0', 1);
INSERT INTO `system_login_log` VALUES (2936, 100, '', 129, 2, 'gadmin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', NULL, '2024-02-28 18:58:17', NULL, '2024-02-28 18:58:17', b'0', 153);
INSERT INTO `system_login_log` VALUES (2937, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', NULL, '2024-02-29 10:55:43', NULL, '2024-02-29 10:55:43', b'0', 1);
INSERT INTO `system_login_log` VALUES (2938, 200, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', '1', '2024-02-29 10:55:56', '1', '2024-02-29 10:55:56', b'0', 1);
INSERT INTO `system_login_log` VALUES (2939, 100, '', 129, 2, 'gadmin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', NULL, '2024-02-29 10:56:05', NULL, '2024-02-29 10:56:05', b'0', 153);
INSERT INTO `system_login_log` VALUES (2940, 200, '', 129, 2, 'gadmin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', '129', '2024-02-29 10:56:27', '129', '2024-02-29 10:56:27', b'0', 153);
INSERT INTO `system_login_log` VALUES (2941, 100, '', 1, 2, 'admin', 0, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', NULL, '2024-02-29 17:07:15', NULL, '2024-02-29 17:07:15', b'0', 1);
INSERT INTO `system_login_log` VALUES (2942, 100, '', 1, 2, 'admin', 0, '10.54.5.148', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', NULL, '2024-02-29 17:10:07', NULL, '2024-02-29 17:10:07', b'0', 1);

-- ----------------------------
-- Table structure for system_mail_account
-- ----------------------------
DROP TABLE IF EXISTS `system_mail_account`;
CREATE TABLE `system_mail_account`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `mail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '邮箱',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `host` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'SMTP 服务器域名',
  `port` int NOT NULL COMMENT 'SMTP 服务器端口',
  `ssl_enable` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否开启 SSL',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '邮箱账号表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_mail_account
-- ----------------------------
INSERT INTO `system_mail_account` VALUES (1, '7684413@qq.com', '7684413@qq.com', '1234576', '127.0.0.1', 8080, b'0', '1', '2023-01-25 17:39:52', '1', '2023-12-02 19:51:19', b'0');
INSERT INTO `system_mail_account` VALUES (2, 'ydym_test@163.com', 'ydym_test@163.com', 'WBZTEINMIFVRYSOE', 'smtp.163.com', 465, b'1', '1', '2023-01-26 01:26:03', '1', '2023-04-12 22:39:38', b'0');
INSERT INTO `system_mail_account` VALUES (3, '76854114@qq.com', '3335', '11234', 'yunai1.cn', 466, b'0', '1', '2023-01-27 15:06:38', '1', '2023-01-27 07:08:36', b'1');
INSERT INTO `system_mail_account` VALUES (4, '7685413x@qq.com', '2', '3', '4', 5, b'1', '1', '2023-04-12 23:05:06', '1', '2023-04-12 15:05:11', b'1');

-- ----------------------------
-- Table structure for system_mail_log
-- ----------------------------
DROP TABLE IF EXISTS `system_mail_log`;
CREATE TABLE `system_mail_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户编号',
  `user_type` tinyint NULL DEFAULT NULL COMMENT '用户类型',
  `to_mail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '接收邮箱地址',
  `account_id` bigint NOT NULL COMMENT '邮箱账号编号',
  `from_mail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '发送邮箱地址',
  `template_id` bigint NOT NULL COMMENT '模板编号',
  `template_code` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模板编码',
  `template_nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '模版发送人名称',
  `template_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '邮件标题',
  `template_content` varchar(10240) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '邮件内容',
  `template_params` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '邮件参数',
  `send_status` tinyint NOT NULL DEFAULT 0 COMMENT '发送状态',
  `send_time` datetime NULL DEFAULT NULL COMMENT '发送时间',
  `send_message_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '发送返回的消息 ID',
  `send_exception` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '发送异常',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 356 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '邮件日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_mail_log
-- ----------------------------

-- ----------------------------
-- Table structure for system_mail_template
-- ----------------------------
DROP TABLE IF EXISTS `system_mail_template`;
CREATE TABLE `system_mail_template`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模板名称',
  `code` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模板编码',
  `account_id` bigint NOT NULL COMMENT '发送的邮箱账号编号',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '发送人名称',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模板标题',
  `content` varchar(10240) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模板内容',
  `params` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '参数数组',
  `status` tinyint NOT NULL COMMENT '开启状态',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '邮件模版表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_mail_template
-- ----------------------------
INSERT INTO `system_mail_template` VALUES (13, '后台用户短信登录', 'admin-sms-login', 1, '奥特曼', '你猜我猜', '<p>您的验证码是{code}，名字是{name}</p>', '[\"code\",\"name\"]', 0, '3', '1', '2021-10-11 08:10:00', '1', '2023-12-02 19:51:14', b'0');
INSERT INTO `system_mail_template` VALUES (14, '测试模版', 'test_01', 2, '芋艿', '一个标题', '<p>你是 {key01} 吗？</p><p><br></p><p>是的话，赶紧 {key02} 一下！</p>', '[\"key01\",\"key02\"]', 0, NULL, '1', '2023-01-26 01:27:40', '1', '2023-01-27 10:32:16', b'0');
INSERT INTO `system_mail_template` VALUES (15, '3', '2', 2, '7', '4', '<p>45</p>', '[]', 1, '80', '1', '2023-01-27 15:50:35', '1', '2023-01-27 16:34:49', b'0');

-- ----------------------------
-- Table structure for system_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_menu`;
CREATE TABLE `system_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单名称',
  `permission` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '权限标识',
  `type` tinyint NOT NULL COMMENT '菜单类型',
  `sort` int NOT NULL DEFAULT 0 COMMENT '显示顺序',
  `parent_id` bigint NOT NULL DEFAULT 0 COMMENT '父菜单ID',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '路由地址',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '组件路径',
  `component_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '组件名',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '菜单状态',
  `visible` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否可见',
  `keep_alive` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否缓存',
  `always_show` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否总是显示',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2560 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_menu
-- ----------------------------
INSERT INTO `system_menu` VALUES (1, '系统管理', '', 1, 10, 0, '/system', 'system', NULL, NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (2, '基础设施', '', 1, 20, 0, '/infra', 'monitor', NULL, NULL, 1, b'0', b'1', b'0', 'admin', '2021-01-05 17:03:48', '1', '2024-02-28 18:33:31', b'0');
INSERT INTO `system_menu` VALUES (5, 'OA 示例', '', 1, 40, 1185, 'oa', 'people', NULL, NULL, 0, b'1', b'1', b'1', 'admin', '2021-09-20 16:26:19', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (100, '用户管理', 'system:user:list', 2, 1, 1, 'user', 'user', 'system/user/index', 'SystemUser', 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2023-04-08 08:31:59', b'0');
INSERT INTO `system_menu` VALUES (101, '角色管理', '', 2, 2, 1, 'role', 'peoples', 'system/role/index', 'SystemRole', 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2023-04-08 08:33:59', b'0');
INSERT INTO `system_menu` VALUES (102, '菜单管理', '', 2, 3, 1, 'menu', 'tree-table', 'system/menu/index', 'SystemMenu', 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2023-04-08 08:34:32', b'0');
INSERT INTO `system_menu` VALUES (103, '部门管理', '', 2, 4, 1, 'dept', 'tree', 'system/dept/index', 'SystemDept', 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2023-04-08 08:35:32', b'0');
INSERT INTO `system_menu` VALUES (104, '岗位管理', '', 2, 5, 1, 'post', 'post', 'system/post/index', 'SystemPost', 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2023-04-08 08:36:21', b'0');
INSERT INTO `system_menu` VALUES (105, '字典管理', '', 2, 6, 1, 'dict', 'dict', 'system/dict/index', 'SystemDictType', 1, b'0', b'0', b'0', 'admin', '2021-01-05 17:03:48', '1', '2024-02-19 10:56:21', b'0');
INSERT INTO `system_menu` VALUES (106, '配置管理', '', 2, 6, 2, 'config', 'edit', 'infra/config/index', 'InfraConfig', 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2023-04-08 10:31:17', b'0');
INSERT INTO `system_menu` VALUES (107, '通知公告', '', 2, 8, 1, 'notice', 'message', 'system/notice/index', 'SystemNotice', 1, b'0', b'0', b'0', 'admin', '2021-01-05 17:03:48', '1', '2024-02-19 10:56:09', b'0');
INSERT INTO `system_menu` VALUES (108, '审计日志', '', 1, 9, 1, 'log', 'log', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (109, '令牌管理', '', 2, 2, 1261, 'token', 'online', 'system/oauth2/token/index', 'SystemTokenClient', 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2023-04-08 08:47:41', b'0');
INSERT INTO `system_menu` VALUES (110, '定时任务', '', 2, 12, 2, 'job', 'job', 'infra/job/index', 'InfraJob', 1, b'0', b'0', b'0', 'admin', '2021-01-05 17:03:48', '1', '2024-02-19 10:51:57', b'0');
INSERT INTO `system_menu` VALUES (111, 'MySQL 监控', '', 2, 9, 2, 'druid', 'druid', 'infra/druid/index', 'InfraDruid', 1, b'0', b'0', b'0', 'admin', '2021-01-05 17:03:48', '1', '2024-02-19 10:51:25', b'0');
INSERT INTO `system_menu` VALUES (112, 'Java 监控', '', 2, 11, 2, 'admin-server', 'server', 'infra/server/index', 'InfraAdminServer', 1, b'0', b'0', b'0', 'admin', '2021-01-05 17:03:48', '1', '2024-02-19 10:51:40', b'0');
INSERT INTO `system_menu` VALUES (113, 'Redis 监控', '', 2, 10, 2, 'redis', 'redis', 'infra/redis/index', 'InfraRedis', 1, b'0', b'0', b'0', 'admin', '2021-01-05 17:03:48', '1', '2024-02-19 10:51:32', b'0');
INSERT INTO `system_menu` VALUES (114, '表单构建', 'infra:build:list', 2, 2, 2, 'build', 'build', 'infra/build/index', 'InfraBuild', 1, b'0', b'0', b'0', 'admin', '2021-01-05 17:03:48', '1', '2024-02-19 10:50:36', b'0');
INSERT INTO `system_menu` VALUES (115, '代码生成', 'infra:codegen:query', 2, 1, 2, 'codegen', 'code', 'infra/codegen/index', 'InfraCodegen', 1, b'0', b'0', b'0', 'admin', '2021-01-05 17:03:48', '1', '2024-02-19 10:50:04', b'0');
INSERT INTO `system_menu` VALUES (116, '系统接口', 'infra:swagger:list', 2, 3, 2, 'swagger', 'swagger', 'infra/swagger/index', 'InfraSwagger', 1, b'0', b'0', b'0', 'admin', '2021-01-05 17:03:48', '1', '2024-02-19 10:50:43', b'0');
INSERT INTO `system_menu` VALUES (500, '操作日志', '', 2, 1, 108, 'operate-log', 'form', 'system/operatelog/index', 'SystemOperateLog', 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2023-04-08 08:47:00', b'0');
INSERT INTO `system_menu` VALUES (501, '登录日志', '', 2, 2, 108, 'login-log', 'logininfor', 'system/loginlog/index', 'SystemLoginLog', 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2023-04-08 08:46:18', b'0');
INSERT INTO `system_menu` VALUES (1001, '用户查询', 'system:user:query', 3, 1, 100, '', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1002, '用户新增', 'system:user:create', 3, 2, 100, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1003, '用户修改', 'system:user:update', 3, 3, 100, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1004, '用户删除', 'system:user:delete', 3, 4, 100, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1005, '用户导出', 'system:user:export', 3, 5, 100, '', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1006, '用户导入', 'system:user:import', 3, 6, 100, '', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1007, '重置密码', 'system:user:update-password', 3, 7, 100, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1008, '角色查询', 'system:role:query', 3, 1, 101, '', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1009, '角色新增', 'system:role:create', 3, 2, 101, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1010, '角色修改', 'system:role:update', 3, 3, 101, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1011, '角色删除', 'system:role:delete', 3, 4, 101, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1012, '角色导出', 'system:role:export', 3, 5, 101, '', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1013, '菜单查询', 'system:menu:query', 3, 1, 102, '', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1014, '菜单新增', 'system:menu:create', 3, 2, 102, '', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1015, '菜单修改', 'system:menu:update', 3, 3, 102, '', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1016, '菜单删除', 'system:menu:delete', 3, 4, 102, '', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1017, '部门查询', 'system:dept:query', 3, 1, 103, '', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1018, '部门新增', 'system:dept:create', 3, 2, 103, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1019, '部门修改', 'system:dept:update', 3, 3, 103, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1020, '部门删除', 'system:dept:delete', 3, 4, 103, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1021, '岗位查询', 'system:post:query', 3, 1, 104, '', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1022, '岗位新增', 'system:post:create', 3, 2, 104, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1023, '岗位修改', 'system:post:update', 3, 3, 104, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1024, '岗位删除', 'system:post:delete', 3, 4, 104, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1025, '岗位导出', 'system:post:export', 3, 5, 104, '', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1026, '字典查询', 'system:dict:query', 3, 1, 105, '#', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1027, '字典新增', 'system:dict:create', 3, 2, 105, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1028, '字典修改', 'system:dict:update', 3, 3, 105, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1029, '字典删除', 'system:dict:delete', 3, 4, 105, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1030, '字典导出', 'system:dict:export', 3, 5, 105, '#', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1031, '配置查询', 'infra:config:query', 3, 1, 106, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1032, '配置新增', 'infra:config:create', 3, 2, 106, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1033, '配置修改', 'infra:config:update', 3, 3, 106, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1034, '配置删除', 'infra:config:delete', 3, 4, 106, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1035, '配置导出', 'infra:config:export', 3, 5, 106, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1036, '公告查询', 'system:notice:query', 3, 1, 107, '#', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1037, '公告新增', 'system:notice:create', 3, 2, 107, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1038, '公告修改', 'system:notice:update', 3, 3, 107, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1039, '公告删除', 'system:notice:delete', 3, 4, 107, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1040, '操作查询', 'system:operate-log:query', 3, 1, 500, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1042, '日志导出', 'system:operate-log:export', 3, 2, 500, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1043, '登录查询', 'system:login-log:query', 3, 1, 501, '#', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1045, '日志导出', 'system:login-log:export', 3, 3, 501, '#', '#', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1046, '令牌列表', 'system:oauth2-token:page', 3, 1, 109, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-05-09 23:54:42', b'0');
INSERT INTO `system_menu` VALUES (1048, '令牌删除', 'system:oauth2-token:delete', 3, 2, 109, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-05-09 23:54:53', b'0');
INSERT INTO `system_menu` VALUES (1050, '任务新增', 'infra:job:create', 3, 2, 110, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1051, '任务修改', 'infra:job:update', 3, 3, 110, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1052, '任务删除', 'infra:job:delete', 3, 4, 110, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1053, '状态修改', 'infra:job:update', 3, 5, 110, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1054, '任务导出', 'infra:job:export', 3, 7, 110, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1056, '生成修改', 'infra:codegen:update', 3, 2, 115, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1057, '生成删除', 'infra:codegen:delete', 3, 3, 115, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1058, '导入代码', 'infra:codegen:create', 3, 2, 115, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1059, '预览代码', 'infra:codegen:preview', 3, 4, 115, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1060, '生成代码', 'infra:codegen:download', 3, 5, 115, '', '', '', NULL, 0, b'1', b'1', b'1', 'admin', '2021-01-05 17:03:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1063, '设置角色菜单权限', 'system:permission:assign-role-menu', 3, 6, 101, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-01-06 17:53:44', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1064, '设置角色数据权限', 'system:permission:assign-role-data-scope', 3, 7, 101, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-01-06 17:56:31', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1065, '设置用户角色', 'system:permission:assign-user-role', 3, 8, 101, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-01-07 10:23:28', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1066, '获得 Redis 监控信息', 'infra:redis:get-monitor-info', 3, 1, 113, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-01-26 01:02:31', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1067, '获得 Redis Key 列表', 'infra:redis:get-key-list', 3, 2, 113, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-01-26 01:02:52', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1070, '代码生成案例', '', 1, 1, 2, 'demo', 'ep:aim', 'infra/testDemo/index', NULL, 1, b'0', b'1', b'0', '', '2021-02-06 12:42:49', '1', '2024-02-19 10:50:16', b'0');
INSERT INTO `system_menu` VALUES (1075, '任务触发', 'infra:job:trigger', 3, 8, 110, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-02-07 13:03:10', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1076, '数据库文档', '', 2, 4, 2, 'db-doc', 'table', 'infra/dbDoc/index', 'InfraDBDoc', 1, b'0', b'0', b'0', '', '2021-02-08 01:41:47', '1', '2024-02-19 10:50:52', b'0');
INSERT INTO `system_menu` VALUES (1077, '监控平台', '', 2, 13, 2, 'skywalking', 'eye-open', 'infra/skywalking/index', 'InfraSkyWalking', 1, b'0', b'0', b'0', '', '2021-02-08 20:41:31', '1', '2024-02-19 10:51:48', b'0');
INSERT INTO `system_menu` VALUES (1078, '访问日志', '', 2, 1, 1083, 'api-access-log', 'log', 'infra/apiAccessLog/index', 'InfraApiAccessLog', 0, b'1', b'1', b'1', '', '2021-02-26 01:32:59', '1', '2023-04-08 10:31:34', b'0');
INSERT INTO `system_menu` VALUES (1082, '日志导出', 'infra:api-access-log:export', 3, 2, 1078, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-02-26 01:32:59', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1083, 'API 日志', '', 2, 8, 2, 'log', 'log', NULL, NULL, 1, b'0', b'0', b'0', '', '2021-02-26 02:18:24', '1', '2024-02-19 10:52:10', b'0');
INSERT INTO `system_menu` VALUES (1084, '错误日志', 'infra:api-error-log:query', 2, 2, 1083, 'api-error-log', 'log', 'infra/apiErrorLog/index', 'InfraApiErrorLog', 0, b'1', b'1', b'1', '', '2021-02-26 07:53:20', '1', '2023-04-08 10:32:25', b'0');
INSERT INTO `system_menu` VALUES (1085, '日志处理', 'infra:api-error-log:update-status', 3, 2, 1084, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-02-26 07:53:20', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1086, '日志导出', 'infra:api-error-log:export', 3, 3, 1084, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-02-26 07:53:20', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1087, '任务查询', 'infra:job:query', 3, 1, 110, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2021-03-10 01:26:19', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1088, '日志查询', 'infra:api-access-log:query', 3, 1, 1078, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2021-03-10 01:28:04', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1089, '日志查询', 'infra:api-error-log:query', 3, 1, 1084, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2021-03-10 01:29:09', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1090, '文件列表', '', 2, 5, 1243, 'file', 'upload', 'infra/file/index', 'InfraFile', 0, b'1', b'1', b'1', '', '2021-03-12 20:16:20', '1', '2023-04-08 09:21:31', b'0');
INSERT INTO `system_menu` VALUES (1091, '文件查询', 'infra:file:query', 3, 1, 1090, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-03-12 20:16:20', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1092, '文件删除', 'infra:file:delete', 3, 4, 1090, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-03-12 20:16:20', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1093, '短信管理', '', 1, 11, 1, 'sms', 'validCode', NULL, NULL, 1, b'0', b'1', b'0', '1', '2021-04-05 01:10:16', '1', '2024-02-19 10:53:05', b'0');
INSERT INTO `system_menu` VALUES (1094, '短信渠道', '', 2, 0, 1093, 'sms-channel', 'phone', 'system/sms/channel/index', 'SystemSmsChannel', 0, b'1', b'1', b'1', '', '2021-04-01 11:07:15', '1', '2023-04-08 08:50:41', b'0');
INSERT INTO `system_menu` VALUES (1095, '短信渠道查询', 'system:sms-channel:query', 3, 1, 1094, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-04-01 11:07:15', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1096, '短信渠道创建', 'system:sms-channel:create', 3, 2, 1094, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-04-01 11:07:15', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1097, '短信渠道更新', 'system:sms-channel:update', 3, 3, 1094, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-04-01 11:07:15', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1098, '短信渠道删除', 'system:sms-channel:delete', 3, 4, 1094, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-04-01 11:07:15', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1100, '短信模板', '', 2, 1, 1093, 'sms-template', 'phone', 'system/sms/template/index', 'SystemSmsTemplate', 0, b'1', b'1', b'1', '', '2021-04-01 17:35:17', '1', '2023-04-08 08:50:50', b'0');
INSERT INTO `system_menu` VALUES (1101, '短信模板查询', 'system:sms-template:query', 3, 1, 1100, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-04-01 17:35:17', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1102, '短信模板创建', 'system:sms-template:create', 3, 2, 1100, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-04-01 17:35:17', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1103, '短信模板更新', 'system:sms-template:update', 3, 3, 1100, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-04-01 17:35:17', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1104, '短信模板删除', 'system:sms-template:delete', 3, 4, 1100, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-04-01 17:35:17', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1105, '短信模板导出', 'system:sms-template:export', 3, 5, 1100, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-04-01 17:35:17', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1106, '发送测试短信', 'system:sms-template:send-sms', 3, 6, 1100, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2021-04-11 00:26:40', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1107, '短信日志', '', 2, 2, 1093, 'sms-log', 'phone', 'system/sms/log/index', 'SystemSmsLog', 0, b'1', b'1', b'1', '', '2021-04-11 08:37:05', '1', '2023-04-08 08:50:58', b'0');
INSERT INTO `system_menu` VALUES (1108, '短信日志查询', 'system:sms-log:query', 3, 1, 1107, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-04-11 08:37:05', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1109, '短信日志导出', 'system:sms-log:export', 3, 5, 1107, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-04-11 08:37:05', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1110, '错误码管理', '', 2, 12, 1, 'error-code', 'code', 'system/errorCode/index', 'SystemErrorCode', 1, b'0', b'0', b'0', '', '2021-04-13 21:46:42', '1', '2024-02-19 10:57:43', b'0');
INSERT INTO `system_menu` VALUES (1111, '错误码查询', 'system:error-code:query', 3, 1, 1110, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-04-13 21:46:42', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1112, '错误码创建', 'system:error-code:create', 3, 2, 1110, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-04-13 21:46:42', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1113, '错误码更新', 'system:error-code:update', 3, 3, 1110, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-04-13 21:46:42', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1114, '错误码删除', 'system:error-code:delete', 3, 4, 1110, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-04-13 21:46:42', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1115, '错误码导出', 'system:error-code:export', 3, 5, 1110, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-04-13 21:46:42', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1117, '支付管理', '', 1, 30, 0, '/pay', 'money', NULL, NULL, 1, b'0', b'1', b'0', '1', '2021-12-25 16:43:41', '1', '2024-02-17 12:12:23', b'0');
INSERT INTO `system_menu` VALUES (1118, '请假查询', '', 2, 0, 5, 'leave', 'user', 'bpm/oa/leave/index', 'BpmOALeave', 0, b'1', b'1', b'1', '', '2021-09-20 08:51:03', '1', '2023-04-08 11:30:40', b'0');
INSERT INTO `system_menu` VALUES (1119, '请假申请查询', 'bpm:oa-leave:query', 3, 1, 1118, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-09-20 08:51:03', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1120, '请假申请创建', 'bpm:oa-leave:create', 3, 2, 1118, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-09-20 08:51:03', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1126, '应用信息', '', 2, 1, 1117, 'app', 'table', 'pay/app/index', 'PayApp', 0, b'1', b'1', b'1', '', '2021-11-10 01:13:30', '1', '2023-07-20 12:13:32', b'0');
INSERT INTO `system_menu` VALUES (1127, '支付应用信息查询', 'pay:app:query', 3, 1, 1126, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-11-10 01:13:31', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1128, '支付应用信息创建', 'pay:app:create', 3, 2, 1126, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-11-10 01:13:31', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1129, '支付应用信息更新', 'pay:app:update', 3, 3, 1126, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-11-10 01:13:31', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1130, '支付应用信息删除', 'pay:app:delete', 3, 4, 1126, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-11-10 01:13:31', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1132, '秘钥解析', 'pay:channel:parsing', 3, 6, 1129, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2021-11-08 15:15:47', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1133, '支付商户信息查询', 'pay:merchant:query', 3, 1, 1132, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-11-10 01:13:41', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1134, '支付商户信息创建', 'pay:merchant:create', 3, 2, 1132, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-11-10 01:13:41', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1135, '支付商户信息更新', 'pay:merchant:update', 3, 3, 1132, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-11-10 01:13:41', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1136, '支付商户信息删除', 'pay:merchant:delete', 3, 4, 1132, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-11-10 01:13:41', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1137, '支付商户信息导出', 'pay:merchant:export', 3, 5, 1132, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-11-10 01:13:41', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1138, '租户列表', '', 2, 0, 1224, 'list', 'peoples', 'system/tenant/index', 'SystemTenant', 0, b'1', b'1', b'1', '', '2021-12-14 12:31:43', '1', '2023-04-08 08:29:08', b'0');
INSERT INTO `system_menu` VALUES (1139, '租户查询', 'system:tenant:query', 3, 1, 1138, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-12-14 12:31:44', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1140, '租户创建', 'system:tenant:create', 3, 2, 1138, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-12-14 12:31:44', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1141, '租户更新', 'system:tenant:update', 3, 3, 1138, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-12-14 12:31:44', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1142, '租户删除', 'system:tenant:delete', 3, 4, 1138, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-12-14 12:31:44', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1143, '租户导出', 'system:tenant:export', 3, 5, 1138, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-12-14 12:31:44', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1150, '秘钥解析', '', 3, 6, 1129, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2021-11-08 15:15:47', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1161, '退款订单', '', 2, 3, 1117, 'refund', 'order', 'pay/refund/index', 'PayRefund', 0, b'1', b'1', b'1', '', '2021-12-25 08:29:07', '1', '2023-04-08 10:46:02', b'0');
INSERT INTO `system_menu` VALUES (1162, '退款订单查询', 'pay:refund:query', 3, 1, 1161, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-12-25 08:29:07', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1163, '退款订单创建', 'pay:refund:create', 3, 2, 1161, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-12-25 08:29:07', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1164, '退款订单更新', 'pay:refund:update', 3, 3, 1161, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-12-25 08:29:07', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1165, '退款订单删除', 'pay:refund:delete', 3, 4, 1161, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-12-25 08:29:07', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1166, '退款订单导出', 'pay:refund:export', 3, 5, 1161, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-12-25 08:29:07', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1173, '支付订单', '', 2, 2, 1117, 'order', 'pay', 'pay/order/index', 'PayOrder', 0, b'1', b'1', b'1', '', '2021-12-25 08:49:43', '1', '2023-04-08 10:43:37', b'0');
INSERT INTO `system_menu` VALUES (1174, '支付订单查询', 'pay:order:query', 3, 1, 1173, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-12-25 08:49:43', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1175, '支付订单创建', 'pay:order:create', 3, 2, 1173, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-12-25 08:49:43', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1176, '支付订单更新', 'pay:order:update', 3, 3, 1173, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-12-25 08:49:43', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1177, '支付订单删除', 'pay:order:delete', 3, 4, 1173, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-12-25 08:49:43', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1178, '支付订单导出', 'pay:order:export', 3, 5, 1173, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-12-25 08:49:43', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1185, '工作流程', '', 1, 50, 0, '/bpm', 'tool', NULL, NULL, 1, b'0', b'1', b'0', '1', '2021-12-30 20:26:36', '1', '2024-02-17 12:12:39', b'0');
INSERT INTO `system_menu` VALUES (1186, '流程管理', '', 1, 10, 1185, 'manager', 'nested', NULL, NULL, 0, b'1', b'1', b'1', '1', '2021-12-30 20:28:30', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1187, '流程表单', '', 2, 0, 1186, 'form', 'form', 'bpm/form/index', 'BpmForm', 0, b'1', b'1', b'1', '', '2021-12-30 12:38:22', '1', '2023-04-08 10:50:37', b'0');
INSERT INTO `system_menu` VALUES (1188, '表单查询', 'bpm:form:query', 3, 1, 1187, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-12-30 12:38:22', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1189, '表单创建', 'bpm:form:create', 3, 2, 1187, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-12-30 12:38:22', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1190, '表单更新', 'bpm:form:update', 3, 3, 1187, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-12-30 12:38:22', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1191, '表单删除', 'bpm:form:delete', 3, 4, 1187, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-12-30 12:38:22', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1192, '表单导出', 'bpm:form:export', 3, 5, 1187, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2021-12-30 12:38:22', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1193, '流程模型', '', 2, 5, 1186, 'model', 'guide', 'bpm/model/index', 'BpmModel', 0, b'1', b'1', b'1', '1', '2021-12-31 23:24:58', '1', '2023-04-08 10:53:38', b'0');
INSERT INTO `system_menu` VALUES (1194, '模型查询', 'bpm:model:query', 3, 1, 1193, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2022-01-03 19:01:10', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1195, '模型创建', 'bpm:model:create', 3, 2, 1193, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2022-01-03 19:01:24', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1196, '模型导入', 'bpm:model:import', 3, 3, 1193, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2022-01-03 19:01:35', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1197, '模型更新', 'bpm:model:update', 3, 4, 1193, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2022-01-03 19:02:28', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1198, '模型删除', 'bpm:model:delete', 3, 5, 1193, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2022-01-03 19:02:43', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1199, '模型发布', 'bpm:model:deploy', 3, 6, 1193, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2022-01-03 19:03:24', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1200, '任务管理', '', 1, 20, 1185, 'task', 'cascader', NULL, NULL, 0, b'1', b'1', b'1', '1', '2022-01-07 23:51:48', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1201, '我的流程', '', 2, 0, 1200, 'my', 'people', 'bpm/processInstance/index', 'BpmProcessInstance', 0, b'1', b'1', b'1', '', '2022-01-07 15:53:44', '1', '2023-04-08 11:16:55', b'0');
INSERT INTO `system_menu` VALUES (1202, '流程实例的查询', 'bpm:process-instance:query', 3, 1, 1201, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-01-07 15:53:44', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1207, '待办任务', '', 2, 10, 1200, 'todo', 'eye-open', 'bpm/task/todo/index', 'BpmTodoTask', 0, b'1', b'1', b'1', '1', '2022-01-08 10:33:37', '1', '2023-04-08 11:29:08', b'0');
INSERT INTO `system_menu` VALUES (1208, '已办任务', '', 2, 20, 1200, 'done', 'eye', 'bpm/task/done/index', 'BpmDoneTask', 0, b'1', b'1', b'1', '1', '2022-01-08 10:34:13', '1', '2023-04-08 11:29:00', b'0');
INSERT INTO `system_menu` VALUES (1209, '用户分组', '', 2, 2, 1186, 'user-group', 'people', 'bpm/group/index', 'BpmUserGroup', 0, b'1', b'1', b'1', '', '2022-01-14 02:14:20', '1', '2023-04-08 10:51:06', b'0');
INSERT INTO `system_menu` VALUES (1210, '用户组查询', 'bpm:user-group:query', 3, 1, 1209, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-01-14 02:14:20', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1211, '用户组创建', 'bpm:user-group:create', 3, 2, 1209, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-01-14 02:14:20', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1212, '用户组更新', 'bpm:user-group:update', 3, 3, 1209, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-01-14 02:14:20', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1213, '用户组删除', 'bpm:user-group:delete', 3, 4, 1209, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-01-14 02:14:20', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1215, '流程定义查询', 'bpm:process-definition:query', 3, 10, 1193, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2022-01-23 00:21:43', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1216, '流程任务分配规则查询', 'bpm:task-assign-rule:query', 3, 20, 1193, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2022-01-23 00:26:53', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1217, '流程任务分配规则创建', 'bpm:task-assign-rule:create', 3, 21, 1193, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2022-01-23 00:28:15', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1218, '流程任务分配规则更新', 'bpm:task-assign-rule:update', 3, 22, 1193, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2022-01-23 00:28:41', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1219, '流程实例的创建', 'bpm:process-instance:create', 3, 2, 1201, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2022-01-23 00:36:15', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1220, '流程实例的取消', 'bpm:process-instance:cancel', 3, 3, 1201, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2022-01-23 00:36:33', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1221, '流程任务的查询', 'bpm:task:query', 3, 1, 1207, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2022-01-23 00:38:52', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1222, '流程任务的更新', 'bpm:task:update', 3, 2, 1207, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2022-01-23 00:39:24', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1224, '租户管理', '', 2, 0, 1, 'tenant', 'peoples', NULL, NULL, 0, b'1', b'1', b'1', '1', '2022-02-20 01:41:13', '1', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1225, '租户套餐', '', 2, 0, 1224, 'package', 'eye', 'system/tenantPackage/index', 'SystemTenantPackage', 0, b'1', b'1', b'1', '', '2022-02-19 17:44:06', '1', '2023-04-08 08:17:08', b'0');
INSERT INTO `system_menu` VALUES (1226, '租户套餐查询', 'system:tenant-package:query', 3, 1, 1225, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-02-19 17:44:06', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1227, '租户套餐创建', 'system:tenant-package:create', 3, 2, 1225, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-02-19 17:44:06', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1228, '租户套餐更新', 'system:tenant-package:update', 3, 3, 1225, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-02-19 17:44:06', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1229, '租户套餐删除', 'system:tenant-package:delete', 3, 4, 1225, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-02-19 17:44:06', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1237, '文件配置', '', 2, 0, 1243, 'file-config', 'config', 'infra/fileConfig/index', 'InfraFileConfig', 0, b'1', b'1', b'1', '', '2022-03-15 14:35:28', '1', '2023-04-08 09:16:05', b'0');
INSERT INTO `system_menu` VALUES (1238, '文件配置查询', 'infra:file-config:query', 3, 1, 1237, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-03-15 14:35:28', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1239, '文件配置创建', 'infra:file-config:create', 3, 2, 1237, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-03-15 14:35:28', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1240, '文件配置更新', 'infra:file-config:update', 3, 3, 1237, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-03-15 14:35:28', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1241, '文件配置删除', 'infra:file-config:delete', 3, 4, 1237, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-03-15 14:35:28', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1242, '文件配置导出', 'infra:file-config:export', 3, 5, 1237, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-03-15 14:35:28', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1243, '文件管理', '', 2, 5, 2, 'file', 'download', NULL, '', 1, b'0', b'0', b'0', '1', '2022-03-16 23:47:40', '1', '2024-02-19 10:51:05', b'0');
INSERT INTO `system_menu` VALUES (1247, '敏感词管理', '', 2, 13, 1, 'sensitive-word', 'education', 'system/sensitiveWord/index', 'SystemSensitiveWord', 1, b'0', b'0', b'0', '', '2022-04-07 16:55:03', '1', '2024-02-19 10:52:53', b'0');
INSERT INTO `system_menu` VALUES (1248, '敏感词查询', 'system:sensitive-word:query', 3, 1, 1247, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-04-07 16:55:03', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1249, '敏感词创建', 'system:sensitive-word:create', 3, 2, 1247, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-04-07 16:55:03', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1250, '敏感词更新', 'system:sensitive-word:update', 3, 3, 1247, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-04-07 16:55:03', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1251, '敏感词删除', 'system:sensitive-word:delete', 3, 4, 1247, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-04-07 16:55:03', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1252, '敏感词导出', 'system:sensitive-word:export', 3, 5, 1247, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-04-07 16:55:03', '', '2022-04-20 17:03:10', b'0');
INSERT INTO `system_menu` VALUES (1254, '作者动态', '', 1, 0, 0, 'https://www.iocoder.cn', 'ep:avatar', NULL, NULL, 1, b'0', b'1', b'0', '1', '2022-04-23 01:03:15', '1', '2024-02-17 12:12:05', b'0');
INSERT INTO `system_menu` VALUES (1255, '数据源配置', '', 2, 1, 2, 'data-source-config', 'rate', 'infra/dataSourceConfig/index', 'InfraDataSourceConfig', 1, b'0', b'0', b'0', '', '2022-04-27 14:37:32', '1', '2024-02-19 10:50:25', b'0');
INSERT INTO `system_menu` VALUES (1256, '数据源配置查询', 'infra:data-source-config:query', 3, 1, 1255, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-04-27 14:37:32', '', '2022-04-27 14:37:32', b'0');
INSERT INTO `system_menu` VALUES (1257, '数据源配置创建', 'infra:data-source-config:create', 3, 2, 1255, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-04-27 14:37:32', '', '2022-04-27 14:37:32', b'0');
INSERT INTO `system_menu` VALUES (1258, '数据源配置更新', 'infra:data-source-config:update', 3, 3, 1255, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-04-27 14:37:32', '', '2022-04-27 14:37:32', b'0');
INSERT INTO `system_menu` VALUES (1259, '数据源配置删除', 'infra:data-source-config:delete', 3, 4, 1255, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-04-27 14:37:32', '', '2022-04-27 14:37:32', b'0');
INSERT INTO `system_menu` VALUES (1260, '数据源配置导出', 'infra:data-source-config:export', 3, 5, 1255, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-04-27 14:37:32', '', '2022-04-27 14:37:32', b'0');
INSERT INTO `system_menu` VALUES (1261, 'OAuth 2.0', '', 1, 10, 1, 'oauth2', 'people', NULL, NULL, 1, b'0', b'1', b'0', '1', '2022-05-09 23:38:17', '1', '2024-02-19 10:53:54', b'0');
INSERT INTO `system_menu` VALUES (1263, '应用管理', '', 2, 0, 1261, 'oauth2/application', 'tool', 'system/oauth2/client/index', 'SystemOAuth2Client', 0, b'1', b'1', b'1', '', '2022-05-10 16:26:33', '1', '2023-04-08 08:47:31', b'0');
INSERT INTO `system_menu` VALUES (1264, '客户端查询', 'system:oauth2-client:query', 3, 1, 1263, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-05-10 16:26:33', '1', '2022-05-11 00:31:06', b'0');
INSERT INTO `system_menu` VALUES (1265, '客户端创建', 'system:oauth2-client:create', 3, 2, 1263, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-05-10 16:26:33', '1', '2022-05-11 00:31:23', b'0');
INSERT INTO `system_menu` VALUES (1266, '客户端更新', 'system:oauth2-client:update', 3, 3, 1263, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-05-10 16:26:33', '1', '2022-05-11 00:31:28', b'0');
INSERT INTO `system_menu` VALUES (1267, '客户端删除', 'system:oauth2-client:delete', 3, 4, 1263, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-05-10 16:26:33', '1', '2022-05-11 00:31:33', b'0');
INSERT INTO `system_menu` VALUES (1281, '报表管理', '', 1, 40, 0, '/report', 'chart', NULL, NULL, 1, b'0', b'1', b'0', '1', '2022-07-10 20:22:15', '1', '2024-02-17 12:12:32', b'0');
INSERT INTO `system_menu` VALUES (1282, '报表设计器', '', 2, 1, 1281, 'jimu-report', 'example', 'report/jmreport/index', 'GoView', 0, b'1', b'1', b'1', '1', '2022-07-10 20:26:36', '1', '2023-04-08 10:47:59', b'0');
INSERT INTO `system_menu` VALUES (2000, '商品中心', '', 1, 60, 2362, 'product', 'fa:product-hunt', NULL, NULL, 0, b'1', b'1', b'1', '', '2022-07-29 15:53:53', '1', '2023-09-30 11:52:36', b'0');
INSERT INTO `system_menu` VALUES (2002, '商品分类', '', 2, 2, 2000, 'category', 'ep:cellphone', 'mall/product/category/index', 'ProductCategory', 0, b'1', b'1', b'1', '', '2022-07-29 15:53:53', '1', '2023-08-21 10:27:15', b'0');
INSERT INTO `system_menu` VALUES (2003, '分类查询', 'product:category:query', 3, 1, 2002, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-07-29 15:53:53', '', '2022-07-29 15:53:53', b'0');
INSERT INTO `system_menu` VALUES (2004, '分类创建', 'product:category:create', 3, 2, 2002, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-07-29 15:53:53', '', '2022-07-29 15:53:53', b'0');
INSERT INTO `system_menu` VALUES (2005, '分类更新', 'product:category:update', 3, 3, 2002, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-07-29 15:53:53', '', '2022-07-29 15:53:53', b'0');
INSERT INTO `system_menu` VALUES (2006, '分类删除', 'product:category:delete', 3, 4, 2002, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-07-29 15:53:53', '', '2022-07-29 15:53:53', b'0');
INSERT INTO `system_menu` VALUES (2008, '商品品牌', '', 2, 3, 2000, 'brand', 'ep:chicken', 'mall/product/brand/index', 'ProductBrand', 0, b'1', b'1', b'1', '', '2022-07-30 13:52:44', '1', '2023-08-21 10:27:28', b'0');
INSERT INTO `system_menu` VALUES (2009, '品牌查询', 'product:brand:query', 3, 1, 2008, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-07-30 13:52:44', '', '2022-07-30 13:52:44', b'0');
INSERT INTO `system_menu` VALUES (2010, '品牌创建', 'product:brand:create', 3, 2, 2008, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-07-30 13:52:44', '', '2022-07-30 13:52:44', b'0');
INSERT INTO `system_menu` VALUES (2011, '品牌更新', 'product:brand:update', 3, 3, 2008, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-07-30 13:52:44', '', '2022-07-30 13:52:44', b'0');
INSERT INTO `system_menu` VALUES (2012, '品牌删除', 'product:brand:delete', 3, 4, 2008, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-07-30 13:52:44', '', '2022-07-30 13:52:44', b'0');
INSERT INTO `system_menu` VALUES (2014, '商品列表', '', 2, 1, 2000, 'spu', 'ep:apple', 'mall/product/spu/index', 'ProductSpu', 0, b'1', b'1', b'1', '', '2022-07-30 14:22:58', '1', '2023-08-21 10:27:01', b'0');
INSERT INTO `system_menu` VALUES (2015, '商品查询', 'product:spu:query', 3, 1, 2014, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-07-30 14:22:58', '', '2022-07-30 14:22:58', b'0');
INSERT INTO `system_menu` VALUES (2016, '商品创建', 'product:spu:create', 3, 2, 2014, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-07-30 14:22:58', '', '2022-07-30 14:22:58', b'0');
INSERT INTO `system_menu` VALUES (2017, '商品更新', 'product:spu:update', 3, 3, 2014, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-07-30 14:22:58', '', '2022-07-30 14:22:58', b'0');
INSERT INTO `system_menu` VALUES (2018, '商品删除', 'product:spu:delete', 3, 4, 2014, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-07-30 14:22:58', '', '2022-07-30 14:22:58', b'0');
INSERT INTO `system_menu` VALUES (2019, '商品属性', '', 2, 4, 2000, 'property', 'ep:cold-drink', 'mall/product/property/index', 'ProductProperty', 0, b'1', b'1', b'1', '', '2022-08-01 14:55:35', '1', '2023-08-26 11:01:05', b'0');
INSERT INTO `system_menu` VALUES (2020, '规格查询', 'product:property:query', 3, 1, 2019, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-08-01 14:55:35', '', '2022-12-12 20:26:24', b'0');
INSERT INTO `system_menu` VALUES (2021, '规格创建', 'product:property:create', 3, 2, 2019, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-08-01 14:55:35', '', '2022-12-12 20:26:30', b'0');
INSERT INTO `system_menu` VALUES (2022, '规格更新', 'product:property:update', 3, 3, 2019, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-08-01 14:55:35', '', '2022-12-12 20:26:33', b'0');
INSERT INTO `system_menu` VALUES (2023, '规格删除', 'product:property:delete', 3, 4, 2019, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-08-01 14:55:35', '', '2022-12-12 20:26:37', b'0');
INSERT INTO `system_menu` VALUES (2025, 'Banner', '', 2, 100, 2387, 'banner', 'fa:bandcamp', 'mall/promotion/banner/index', NULL, 0, b'1', b'1', b'1', '', '2022-08-01 14:56:14', '1', '2023-10-24 20:20:06', b'0');
INSERT INTO `system_menu` VALUES (2026, 'Banner查询', 'promotion:banner:query', 3, 1, 2025, '', '', '', '', 0, b'1', b'1', b'1', '', '2022-08-01 14:56:14', '1', '2023-10-24 20:20:18', b'0');
INSERT INTO `system_menu` VALUES (2027, 'Banner创建', 'promotion:banner:create', 3, 2, 2025, '', '', '', '', 0, b'1', b'1', b'1', '', '2022-08-01 14:56:14', '1', '2023-10-24 20:20:23', b'0');
INSERT INTO `system_menu` VALUES (2028, 'Banner更新', 'promotion:banner:update', 3, 3, 2025, '', '', '', '', 0, b'1', b'1', b'1', '', '2022-08-01 14:56:14', '1', '2023-10-24 20:20:28', b'0');
INSERT INTO `system_menu` VALUES (2029, 'Banner删除', 'promotion:banner:delete', 3, 4, 2025, '', '', '', '', 0, b'1', b'1', b'1', '', '2022-08-01 14:56:14', '1', '2023-10-24 20:20:36', b'0');
INSERT INTO `system_menu` VALUES (2030, '营销中心', '', 1, 70, 2362, 'promotion', 'ep:present', NULL, NULL, 0, b'1', b'1', b'1', '1', '2022-10-31 21:25:09', '1', '2023-09-30 11:54:27', b'0');
INSERT INTO `system_menu` VALUES (2032, '优惠劵列表', '', 2, 1, 2365, 'template', 'ep:discount', 'mall/promotion/coupon/template/index', 'PromotionCouponTemplate', 0, b'1', b'1', b'1', '', '2022-10-31 22:27:14', '1', '2023-10-03 12:40:06', b'0');
INSERT INTO `system_menu` VALUES (2033, '优惠劵模板查询', 'promotion:coupon-template:query', 3, 1, 2032, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-10-31 22:27:14', '', '2022-10-31 22:27:14', b'0');
INSERT INTO `system_menu` VALUES (2034, '优惠劵模板创建', 'promotion:coupon-template:create', 3, 2, 2032, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-10-31 22:27:14', '', '2022-10-31 22:27:14', b'0');
INSERT INTO `system_menu` VALUES (2035, '优惠劵模板更新', 'promotion:coupon-template:update', 3, 3, 2032, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-10-31 22:27:14', '', '2022-10-31 22:27:14', b'0');
INSERT INTO `system_menu` VALUES (2036, '优惠劵模板删除', 'promotion:coupon-template:delete', 3, 4, 2032, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-10-31 22:27:14', '', '2022-10-31 22:27:14', b'0');
INSERT INTO `system_menu` VALUES (2038, '领取记录', '', 2, 2, 2365, 'list', 'ep:collection-tag', 'mall/promotion/coupon/index', 'PromotionCoupon', 0, b'1', b'1', b'1', '', '2022-11-03 23:21:31', '1', '2023-10-03 12:55:30', b'0');
INSERT INTO `system_menu` VALUES (2039, '优惠劵查询', 'promotion:coupon:query', 3, 1, 2038, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-11-03 23:21:31', '', '2022-11-03 23:21:31', b'0');
INSERT INTO `system_menu` VALUES (2040, '优惠劵删除', 'promotion:coupon:delete', 3, 4, 2038, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-11-03 23:21:31', '', '2022-11-03 23:21:31', b'0');
INSERT INTO `system_menu` VALUES (2041, '满减送', '', 2, 10, 2390, 'reward-activity', 'ep:goblet-square-full', 'mall/promotion/rewardActivity/index', 'PromotionRewardActivity', 0, b'1', b'1', b'1', '', '2022-11-04 23:47:49', '1', '2023-10-21 19:24:46', b'0');
INSERT INTO `system_menu` VALUES (2042, '满减送活动查询', 'promotion:reward-activity:query', 3, 1, 2041, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-11-04 23:47:49', '', '2022-11-04 23:47:49', b'0');
INSERT INTO `system_menu` VALUES (2043, '满减送活动创建', 'promotion:reward-activity:create', 3, 2, 2041, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-11-04 23:47:49', '', '2022-11-04 23:47:49', b'0');
INSERT INTO `system_menu` VALUES (2044, '满减送活动更新', 'promotion:reward-activity:update', 3, 3, 2041, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-11-04 23:47:50', '', '2022-11-04 23:47:50', b'0');
INSERT INTO `system_menu` VALUES (2045, '满减送活动删除', 'promotion:reward-activity:delete', 3, 4, 2041, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-11-04 23:47:50', '', '2022-11-04 23:47:50', b'0');
INSERT INTO `system_menu` VALUES (2046, '满减送活动关闭', 'promotion:reward-activity:close', 3, 5, 2041, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2022-11-05 10:42:53', '1', '2022-11-05 10:42:53', b'0');
INSERT INTO `system_menu` VALUES (2047, '限时折扣', '', 2, 7, 2390, 'discount-activity', 'ep:timer', 'mall/promotion/discountActivity/index', 'PromotionDiscountActivity', 0, b'1', b'1', b'1', '', '2022-11-05 17:12:15', '1', '2023-10-21 19:24:21', b'0');
INSERT INTO `system_menu` VALUES (2048, '限时折扣活动查询', 'promotion:discount-activity:query', 3, 1, 2047, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-11-05 17:12:15', '', '2022-11-05 17:12:15', b'0');
INSERT INTO `system_menu` VALUES (2049, '限时折扣活动创建', 'promotion:discount-activity:create', 3, 2, 2047, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-11-05 17:12:15', '', '2022-11-05 17:12:15', b'0');
INSERT INTO `system_menu` VALUES (2050, '限时折扣活动更新', 'promotion:discount-activity:update', 3, 3, 2047, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-11-05 17:12:16', '', '2022-11-05 17:12:16', b'0');
INSERT INTO `system_menu` VALUES (2051, '限时折扣活动删除', 'promotion:discount-activity:delete', 3, 4, 2047, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-11-05 17:12:16', '', '2022-11-05 17:12:16', b'0');
INSERT INTO `system_menu` VALUES (2052, '限时折扣活动关闭', 'promotion:discount-activity:close', 3, 5, 2047, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-11-05 17:12:16', '', '2022-11-05 17:12:16', b'0');
INSERT INTO `system_menu` VALUES (2059, '秒杀商品', '', 2, 2, 2209, 'activity', 'ep:basketball', 'mall/promotion/seckill/activity/index', 'PromotionSeckillActivity', 0, b'1', b'1', b'1', '', '2022-11-06 22:24:49', '1', '2023-06-24 18:57:25', b'0');
INSERT INTO `system_menu` VALUES (2060, '秒杀活动查询', 'promotion:seckill-activity:query', 3, 1, 2059, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-11-06 22:24:49', '', '2022-11-06 22:24:49', b'0');
INSERT INTO `system_menu` VALUES (2061, '秒杀活动创建', 'promotion:seckill-activity:create', 3, 2, 2059, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-11-06 22:24:49', '', '2022-11-06 22:24:49', b'0');
INSERT INTO `system_menu` VALUES (2062, '秒杀活动更新', 'promotion:seckill-activity:update', 3, 3, 2059, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-11-06 22:24:49', '', '2022-11-06 22:24:49', b'0');
INSERT INTO `system_menu` VALUES (2063, '秒杀活动删除', 'promotion:seckill-activity:delete', 3, 4, 2059, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-11-06 22:24:49', '', '2022-11-06 22:24:49', b'0');
INSERT INTO `system_menu` VALUES (2066, '秒杀时段', '', 2, 1, 2209, 'config', 'ep:baseball', 'mall/promotion/seckill/config/index', 'PromotionSeckillConfig', 0, b'1', b'1', b'1', '', '2022-11-15 19:46:50', '1', '2023-06-24 18:57:14', b'0');
INSERT INTO `system_menu` VALUES (2067, '秒杀时段查询', 'promotion:seckill-config:query', 3, 1, 2066, '', '', '', '', 0, b'1', b'1', b'1', '', '2022-11-15 19:46:51', '1', '2023-06-24 17:50:25', b'0');
INSERT INTO `system_menu` VALUES (2068, '秒杀时段创建', 'promotion:seckill-config:create', 3, 2, 2066, '', '', '', '', 0, b'1', b'1', b'1', '', '2022-11-15 19:46:51', '1', '2023-06-24 17:48:39', b'0');
INSERT INTO `system_menu` VALUES (2069, '秒杀时段更新', 'promotion:seckill-config:update', 3, 3, 2066, '', '', '', '', 0, b'1', b'1', b'1', '', '2022-11-15 19:46:51', '1', '2023-06-24 17:50:29', b'0');
INSERT INTO `system_menu` VALUES (2070, '秒杀时段删除', 'promotion:seckill-config:delete', 3, 4, 2066, '', '', '', '', 0, b'1', b'1', b'1', '', '2022-11-15 19:46:51', '1', '2023-06-24 17:50:32', b'0');
INSERT INTO `system_menu` VALUES (2072, '订单中心', '', 1, 65, 2362, 'trade', 'ep:eleme', NULL, NULL, 0, b'1', b'1', b'1', '1', '2022-11-19 18:57:19', '1', '2023-09-30 11:54:07', b'0');
INSERT INTO `system_menu` VALUES (2073, '售后退款', '', 2, 2, 2072, 'after-sale', 'ep:refrigerator', 'mall/trade/afterSale/index', 'TradeAfterSale', 0, b'1', b'1', b'1', '', '2022-11-19 20:15:32', '1', '2023-10-01 21:42:21', b'0');
INSERT INTO `system_menu` VALUES (2074, '售后查询', 'trade:after-sale:query', 3, 1, 2073, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-11-19 20:15:33', '1', '2022-12-10 21:04:29', b'0');
INSERT INTO `system_menu` VALUES (2075, '秒杀活动关闭', 'promotion:seckill-activity:close', 3, 5, 2059, '', '', '', '', 0, b'1', b'1', b'1', '1', '2022-11-28 20:20:15', '1', '2023-10-03 18:34:28', b'0');
INSERT INTO `system_menu` VALUES (2076, '订单列表', '', 2, 1, 2072, 'order', 'ep:list', 'mall/trade/order/index', 'TradeOrder', 0, b'1', b'1', b'1', '1', '2022-12-10 21:05:44', '1', '2023-10-01 21:42:08', b'0');
INSERT INTO `system_menu` VALUES (2083, '地区管理', '', 2, 14, 1, 'area', 'row', 'system/area/index', 'SystemArea', 1, b'0', b'0', b'0', '1', '2022-12-23 17:35:05', '1', '2024-02-19 10:52:41', b'0');
INSERT INTO `system_menu` VALUES (2084, '公众号管理', '', 1, 100, 0, '/mp', 'wechat', NULL, NULL, 1, b'0', b'1', b'0', '1', '2023-01-01 20:11:04', '1', '2024-02-17 12:13:00', b'0');
INSERT INTO `system_menu` VALUES (2085, '账号管理', '', 2, 1, 2084, 'account', 'phone', 'mp/account/index', 'MpAccount', 0, b'1', b'1', b'1', '1', '2023-01-01 20:13:31', '1', '2023-02-09 23:56:39', b'0');
INSERT INTO `system_menu` VALUES (2086, '新增账号', 'mp:account:create', 3, 1, 2085, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-01-01 20:21:40', '1', '2023-01-07 17:32:53', b'0');
INSERT INTO `system_menu` VALUES (2087, '修改账号', 'mp:account:update', 3, 2, 2085, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-01-07 17:32:46', '1', '2023-01-07 17:32:46', b'0');
INSERT INTO `system_menu` VALUES (2088, '查询账号', 'mp:account:query', 3, 0, 2085, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-01-07 17:33:07', '1', '2023-01-07 17:33:07', b'0');
INSERT INTO `system_menu` VALUES (2089, '删除账号', 'mp:account:delete', 3, 3, 2085, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-01-07 17:33:21', '1', '2023-01-07 17:33:21', b'0');
INSERT INTO `system_menu` VALUES (2090, '生成二维码', 'mp:account:qr-code', 3, 4, 2085, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-01-07 17:33:58', '1', '2023-01-07 17:33:58', b'0');
INSERT INTO `system_menu` VALUES (2091, '清空 API 配额', 'mp:account:clear-quota', 3, 5, 2085, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-01-07 18:20:32', '1', '2023-01-07 18:20:59', b'0');
INSERT INTO `system_menu` VALUES (2092, '数据统计', 'mp:statistics:query', 2, 2, 2084, 'statistics', 'chart', 'mp/statistics/index', 'MpStatistics', 0, b'1', b'1', b'1', '1', '2023-01-07 20:17:36', '1', '2023-02-09 23:58:34', b'0');
INSERT INTO `system_menu` VALUES (2093, '标签管理', '', 2, 3, 2084, 'tag', 'rate', 'mp/tag/index', 'MpTag', 0, b'1', b'1', b'1', '1', '2023-01-08 11:37:32', '1', '2023-02-09 23:58:47', b'0');
INSERT INTO `system_menu` VALUES (2094, '查询标签', 'mp:tag:query', 3, 0, 2093, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-01-08 11:59:03', '1', '2023-01-08 11:59:03', b'0');
INSERT INTO `system_menu` VALUES (2095, '新增标签', 'mp:tag:create', 3, 1, 2093, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-01-08 11:59:23', '1', '2023-01-08 11:59:23', b'0');
INSERT INTO `system_menu` VALUES (2096, '修改标签', 'mp:tag:update', 3, 2, 2093, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-01-08 11:59:41', '1', '2023-01-08 11:59:41', b'0');
INSERT INTO `system_menu` VALUES (2097, '删除标签', 'mp:tag:delete', 3, 3, 2093, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-01-08 12:00:04', '1', '2023-01-08 12:00:13', b'0');
INSERT INTO `system_menu` VALUES (2098, '同步标签', 'mp:tag:sync', 3, 4, 2093, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-01-08 12:00:29', '1', '2023-01-08 12:00:29', b'0');
INSERT INTO `system_menu` VALUES (2099, '粉丝管理', '', 2, 4, 2084, 'user', 'people', 'mp/user/index', 'MpUser', 0, b'1', b'1', b'1', '1', '2023-01-08 16:51:20', '1', '2023-02-09 23:58:21', b'0');
INSERT INTO `system_menu` VALUES (2100, '查询粉丝', 'mp:user:query', 3, 0, 2099, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-01-08 17:16:59', '1', '2023-01-08 17:17:23', b'0');
INSERT INTO `system_menu` VALUES (2101, '修改粉丝', 'mp:user:update', 3, 1, 2099, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-01-08 17:17:11', '1', '2023-01-08 17:17:11', b'0');
INSERT INTO `system_menu` VALUES (2102, '同步粉丝', 'mp:user:sync', 3, 2, 2099, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-01-08 17:17:40', '1', '2023-01-08 17:17:40', b'0');
INSERT INTO `system_menu` VALUES (2103, '消息管理', '', 2, 5, 2084, 'message', 'email', 'mp/message/index', 'MpMessage', 0, b'1', b'1', b'1', '1', '2023-01-08 18:44:19', '1', '2023-02-09 23:58:02', b'0');
INSERT INTO `system_menu` VALUES (2104, '图文发表记录', '', 2, 10, 2084, 'free-publish', 'education', 'mp/freePublish/index', 'MpFreePublish', 0, b'1', b'1', b'1', '1', '2023-01-13 00:30:50', '1', '2023-02-09 23:57:22', b'0');
INSERT INTO `system_menu` VALUES (2105, '查询发布列表', 'mp:free-publish:query', 3, 1, 2104, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-01-13 07:19:17', '1', '2023-01-13 07:19:17', b'0');
INSERT INTO `system_menu` VALUES (2106, '发布草稿', 'mp:free-publish:submit', 3, 2, 2104, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-01-13 07:19:46', '1', '2023-01-13 07:19:46', b'0');
INSERT INTO `system_menu` VALUES (2107, '删除发布记录', 'mp:free-publish:delete', 3, 3, 2104, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-01-13 07:20:01', '1', '2023-01-13 07:20:01', b'0');
INSERT INTO `system_menu` VALUES (2108, '图文草稿箱', '', 2, 9, 2084, 'draft', 'edit', 'mp/draft/index', 'MpDraft', 0, b'1', b'1', b'1', '1', '2023-01-13 07:40:21', '1', '2023-02-09 23:56:58', b'0');
INSERT INTO `system_menu` VALUES (2109, '新建草稿', 'mp:draft:create', 3, 1, 2108, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-01-13 23:15:30', '1', '2023-01-13 23:15:44', b'0');
INSERT INTO `system_menu` VALUES (2110, '修改草稿', 'mp:draft:update', 3, 2, 2108, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-01-14 10:08:47', '1', '2023-01-14 10:08:47', b'0');
INSERT INTO `system_menu` VALUES (2111, '查询草稿', 'mp:draft:query', 3, 0, 2108, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-01-14 10:09:01', '1', '2023-01-14 10:09:01', b'0');
INSERT INTO `system_menu` VALUES (2112, '删除草稿', 'mp:draft:delete', 3, 3, 2108, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-01-14 10:09:19', '1', '2023-01-14 10:09:19', b'0');
INSERT INTO `system_menu` VALUES (2113, '素材管理', '', 2, 8, 2084, 'material', 'skill', 'mp/material/index', 'MpMaterial', 0, b'1', b'1', b'1', '1', '2023-01-14 14:12:07', '1', '2023-02-09 23:57:36', b'0');
INSERT INTO `system_menu` VALUES (2114, '上传临时素材', 'mp:material:upload-temporary', 3, 1, 2113, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-01-14 15:33:55', '1', '2023-01-14 15:33:55', b'0');
INSERT INTO `system_menu` VALUES (2115, '上传永久素材', 'mp:material:upload-permanent', 3, 2, 2113, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-01-14 15:34:14', '1', '2023-01-14 15:34:14', b'0');
INSERT INTO `system_menu` VALUES (2116, '删除素材', 'mp:material:delete', 3, 3, 2113, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-01-14 15:35:37', '1', '2023-01-14 15:35:37', b'0');
INSERT INTO `system_menu` VALUES (2117, '上传图文图片', 'mp:material:upload-news-image', 3, 4, 2113, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-01-14 15:36:31', '1', '2023-01-14 15:36:31', b'0');
INSERT INTO `system_menu` VALUES (2118, '查询素材', 'mp:material:query', 3, 5, 2113, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-01-14 15:39:22', '1', '2023-01-14 15:39:22', b'0');
INSERT INTO `system_menu` VALUES (2119, '菜单管理', '', 2, 6, 2084, 'menu', 'button', 'mp/menu/index', 'MpMenu', 0, b'1', b'1', b'1', '1', '2023-01-14 17:43:54', '1', '2023-02-09 23:57:50', b'0');
INSERT INTO `system_menu` VALUES (2120, '自动回复', '', 2, 7, 2084, 'auto-reply', 'eye', 'mp/autoReply/index', 'MpAutoReply', 0, b'1', b'1', b'1', '1', '2023-01-15 22:13:09', '1', '2023-02-09 23:56:28', b'0');
INSERT INTO `system_menu` VALUES (2121, '查询回复', 'mp:auto-reply:query', 3, 0, 2120, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-01-16 22:28:41', '1', '2023-01-16 22:28:41', b'0');
INSERT INTO `system_menu` VALUES (2122, '新增回复', 'mp:auto-reply:create', 3, 1, 2120, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-01-16 22:28:54', '1', '2023-01-16 22:28:54', b'0');
INSERT INTO `system_menu` VALUES (2123, '修改回复', 'mp:auto-reply:update', 3, 2, 2120, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-01-16 22:29:05', '1', '2023-01-16 22:29:05', b'0');
INSERT INTO `system_menu` VALUES (2124, '删除回复', 'mp:auto-reply:delete', 3, 3, 2120, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-01-16 22:29:34', '1', '2023-01-16 22:29:34', b'0');
INSERT INTO `system_menu` VALUES (2125, '查询菜单', 'mp:menu:query', 3, 0, 2119, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-01-17 23:05:41', '1', '2023-01-17 23:05:41', b'0');
INSERT INTO `system_menu` VALUES (2126, '保存菜单', 'mp:menu:save', 3, 1, 2119, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-01-17 23:06:01', '1', '2023-01-17 23:06:01', b'0');
INSERT INTO `system_menu` VALUES (2127, '删除菜单', 'mp:menu:delete', 3, 2, 2119, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-01-17 23:06:16', '1', '2023-01-17 23:06:16', b'0');
INSERT INTO `system_menu` VALUES (2128, '查询消息', 'mp:message:query', 3, 0, 2103, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-01-17 23:07:14', '1', '2023-01-17 23:07:14', b'0');
INSERT INTO `system_menu` VALUES (2129, '发送消息', 'mp:message:send', 3, 1, 2103, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-01-17 23:07:26', '1', '2023-01-17 23:07:26', b'0');
INSERT INTO `system_menu` VALUES (2130, '邮箱管理', '', 2, 11, 1, 'mail', 'email', NULL, NULL, 1, b'0', b'0', b'0', '1', '2023-01-25 17:27:44', '1', '2024-02-19 10:53:13', b'0');
INSERT INTO `system_menu` VALUES (2131, '邮箱账号', '', 2, 0, 2130, 'mail-account', 'user', 'system/mail/account/index', 'SystemMailAccount', 0, b'1', b'1', b'1', '', '2023-01-25 09:33:48', '1', '2023-04-08 08:53:43', b'0');
INSERT INTO `system_menu` VALUES (2132, '账号查询', 'system:mail-account:query', 3, 1, 2131, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-01-25 09:33:48', '', '2023-01-25 09:33:48', b'0');
INSERT INTO `system_menu` VALUES (2133, '账号创建', 'system:mail-account:create', 3, 2, 2131, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-01-25 09:33:48', '', '2023-01-25 09:33:48', b'0');
INSERT INTO `system_menu` VALUES (2134, '账号更新', 'system:mail-account:update', 3, 3, 2131, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-01-25 09:33:48', '', '2023-01-25 09:33:48', b'0');
INSERT INTO `system_menu` VALUES (2135, '账号删除', 'system:mail-account:delete', 3, 4, 2131, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-01-25 09:33:48', '', '2023-01-25 09:33:48', b'0');
INSERT INTO `system_menu` VALUES (2136, '邮件模版', '', 2, 0, 2130, 'mail-template', 'education', 'system/mail/template/index', 'SystemMailTemplate', 0, b'1', b'1', b'1', '', '2023-01-25 12:05:31', '1', '2023-04-08 08:53:34', b'0');
INSERT INTO `system_menu` VALUES (2137, '模版查询', 'system:mail-template:query', 3, 1, 2136, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-01-25 12:05:31', '', '2023-01-25 12:05:31', b'0');
INSERT INTO `system_menu` VALUES (2138, '模版创建', 'system:mail-template:create', 3, 2, 2136, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-01-25 12:05:31', '', '2023-01-25 12:05:31', b'0');
INSERT INTO `system_menu` VALUES (2139, '模版更新', 'system:mail-template:update', 3, 3, 2136, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-01-25 12:05:31', '', '2023-01-25 12:05:31', b'0');
INSERT INTO `system_menu` VALUES (2140, '模版删除', 'system:mail-template:delete', 3, 4, 2136, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-01-25 12:05:31', '', '2023-01-25 12:05:31', b'0');
INSERT INTO `system_menu` VALUES (2141, '邮件记录', '', 2, 0, 2130, 'mail-log', 'log', 'system/mail/log/index', 'SystemMailLog', 0, b'1', b'1', b'1', '', '2023-01-26 02:16:50', '1', '2023-04-08 08:53:49', b'0');
INSERT INTO `system_menu` VALUES (2142, '日志查询', 'system:mail-log:query', 3, 1, 2141, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-01-26 02:16:50', '', '2023-01-26 02:16:50', b'0');
INSERT INTO `system_menu` VALUES (2143, '发送测试邮件', 'system:mail-template:send-mail', 3, 5, 2136, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-01-26 23:29:15', '1', '2023-01-26 23:29:15', b'0');
INSERT INTO `system_menu` VALUES (2144, '站内信管理', '', 1, 11, 1, 'notify', 'message', NULL, NULL, 1, b'0', b'1', b'0', '1', '2023-01-28 10:25:18', '1', '2024-02-19 10:53:20', b'0');
INSERT INTO `system_menu` VALUES (2145, '模板管理', '', 2, 0, 2144, 'notify-template', 'education', 'system/notify/template/index', 'SystemNotifyTemplate', 0, b'1', b'1', b'1', '', '2023-01-28 02:26:42', '1', '2023-04-08 08:54:39', b'0');
INSERT INTO `system_menu` VALUES (2146, '站内信模板查询', 'system:notify-template:query', 3, 1, 2145, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-01-28 02:26:42', '', '2023-01-28 02:26:42', b'0');
INSERT INTO `system_menu` VALUES (2147, '站内信模板创建', 'system:notify-template:create', 3, 2, 2145, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-01-28 02:26:42', '', '2023-01-28 02:26:42', b'0');
INSERT INTO `system_menu` VALUES (2148, '站内信模板更新', 'system:notify-template:update', 3, 3, 2145, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-01-28 02:26:42', '', '2023-01-28 02:26:42', b'0');
INSERT INTO `system_menu` VALUES (2149, '站内信模板删除', 'system:notify-template:delete', 3, 4, 2145, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-01-28 02:26:42', '', '2023-01-28 02:26:42', b'0');
INSERT INTO `system_menu` VALUES (2150, '发送测试站内信', 'system:notify-template:send-notify', 3, 5, 2145, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-01-28 10:54:43', '1', '2023-01-28 10:54:43', b'0');
INSERT INTO `system_menu` VALUES (2151, '消息记录', '', 2, 0, 2144, 'notify-message', 'edit', 'system/notify/message/index', 'SystemNotifyMessage', 0, b'1', b'1', b'1', '', '2023-01-28 04:28:22', '1', '2023-04-08 08:54:11', b'0');
INSERT INTO `system_menu` VALUES (2152, '站内信消息查询', 'system:notify-message:query', 3, 1, 2151, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-01-28 04:28:22', '', '2023-01-28 04:28:22', b'0');
INSERT INTO `system_menu` VALUES (2153, '大屏设计器', '', 2, 2, 1281, 'go-view', 'dashboard', 'report/goview/index', 'JimuReport', 0, b'1', b'1', b'1', '1', '2023-02-07 00:03:19', '1', '2023-04-08 10:48:15', b'0');
INSERT INTO `system_menu` VALUES (2154, '创建项目', 'report:go-view-project:create', 3, 1, 2153, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-02-07 19:25:14', '1', '2023-02-07 19:25:14', b'0');
INSERT INTO `system_menu` VALUES (2155, '更新项目', 'report:go-view-project:delete', 3, 2, 2153, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-02-07 19:25:34', '1', '2023-02-07 19:25:34', b'0');
INSERT INTO `system_menu` VALUES (2156, '查询项目', 'report:go-view-project:query', 3, 0, 2153, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-02-07 19:25:53', '1', '2023-02-07 19:25:53', b'0');
INSERT INTO `system_menu` VALUES (2157, '使用 SQL 查询数据', 'report:go-view-data:get-by-sql', 3, 3, 2153, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-02-07 19:26:15', '1', '2023-02-07 19:26:15', b'0');
INSERT INTO `system_menu` VALUES (2158, '使用 HTTP 查询数据', 'report:go-view-data:get-by-http', 3, 4, 2153, '', '', '', NULL, 0, b'1', b'1', b'1', '1', '2023-02-07 19:26:35', '1', '2023-02-07 19:26:35', b'0');
INSERT INTO `system_menu` VALUES (2159, 'Boot 开发文档', '', 1, 1, 0, 'https://doc.iocoder.cn/', 'ep:document', NULL, NULL, 1, b'0', b'1', b'0', '1', '2023-02-10 22:46:28', '1', '2024-02-17 12:11:57', b'0');
INSERT INTO `system_menu` VALUES (2160, 'Cloud 开发文档', '', 1, 2, 0, 'https://cloud.iocoder.cn', 'ep:document-copy', NULL, NULL, 1, b'0', b'1', b'0', '1', '2023-02-10 22:47:07', '1', '2024-02-17 12:12:12', b'0');
INSERT INTO `system_menu` VALUES (2161, '接入示例', '', 1, 99, 1117, 'demo', 'fa-solid:dragon', 'pay/demo/index', NULL, 0, b'1', b'1', b'1', '', '2023-02-11 14:21:42', '1', '2024-01-18 23:50:00', b'0');
INSERT INTO `system_menu` VALUES (2162, '商品导出', 'product:spu:export', 3, 5, 2014, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2022-07-30 14:22:58', '', '2022-07-30 14:22:58', b'0');
INSERT INTO `system_menu` VALUES (2164, '配送管理', '', 1, 3, 2072, 'delivery', 'ep:shopping-cart', '', '', 0, b'1', b'1', b'1', '1', '2023-05-18 09:18:02', '1', '2023-09-28 10:58:09', b'0');
INSERT INTO `system_menu` VALUES (2165, '快递发货', '', 1, 0, 2164, 'express', 'ep:bicycle', '', '', 0, b'1', b'1', b'1', '1', '2023-05-18 09:22:06', '1', '2023-08-30 21:02:49', b'0');
INSERT INTO `system_menu` VALUES (2166, '门店自提', '', 1, 1, 2164, 'pick-up-store', 'ep:add-location', '', '', 0, b'1', b'1', b'1', '1', '2023-05-18 09:23:14', '1', '2023-08-30 21:03:21', b'0');
INSERT INTO `system_menu` VALUES (2167, '快递公司', '', 2, 0, 2165, 'express', 'ep:compass', 'mall/trade/delivery/express/index', 'Express', 0, b'1', b'1', b'1', '1', '2023-05-18 09:27:21', '1', '2023-08-30 21:02:59', b'0');
INSERT INTO `system_menu` VALUES (2168, '快递公司查询', 'trade:delivery:express:query', 3, 1, 2167, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-05-18 09:37:53', '', '2023-05-18 09:37:53', b'0');
INSERT INTO `system_menu` VALUES (2169, '快递公司创建', 'trade:delivery:express:create', 3, 2, 2167, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-05-18 09:37:53', '', '2023-05-18 09:37:53', b'0');
INSERT INTO `system_menu` VALUES (2170, '快递公司更新', 'trade:delivery:express:update', 3, 3, 2167, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-05-18 09:37:53', '', '2023-05-18 09:37:53', b'0');
INSERT INTO `system_menu` VALUES (2171, '快递公司删除', 'trade:delivery:express:delete', 3, 4, 2167, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-05-18 09:37:53', '', '2023-05-18 09:37:53', b'0');
INSERT INTO `system_menu` VALUES (2172, '快递公司导出', 'trade:delivery:express:export', 3, 5, 2167, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-05-18 09:37:53', '', '2023-05-18 09:37:53', b'0');
INSERT INTO `system_menu` VALUES (2173, '运费模版', 'trade:delivery:express-template:query', 2, 1, 2165, 'express-template', 'ep:coordinate', 'mall/trade/delivery/expressTemplate/index', 'ExpressTemplate', 0, b'1', b'1', b'1', '1', '2023-05-20 06:48:10', '1', '2023-08-30 21:03:13', b'0');
INSERT INTO `system_menu` VALUES (2174, '快递运费模板查询', 'trade:delivery:express-template:query', 3, 1, 2173, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-05-20 06:49:53', '', '2023-05-20 06:49:53', b'0');
INSERT INTO `system_menu` VALUES (2175, '快递运费模板创建', 'trade:delivery:express-template:create', 3, 2, 2173, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-05-20 06:49:53', '', '2023-05-20 06:49:53', b'0');
INSERT INTO `system_menu` VALUES (2176, '快递运费模板更新', 'trade:delivery:express-template:update', 3, 3, 2173, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-05-20 06:49:53', '', '2023-05-20 06:49:53', b'0');
INSERT INTO `system_menu` VALUES (2177, '快递运费模板删除', 'trade:delivery:express-template:delete', 3, 4, 2173, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-05-20 06:49:53', '', '2023-05-20 06:49:53', b'0');
INSERT INTO `system_menu` VALUES (2178, '快递运费模板导出', 'trade:delivery:express-template:export', 3, 5, 2173, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-05-20 06:49:53', '', '2023-05-20 06:49:53', b'0');
INSERT INTO `system_menu` VALUES (2179, '门店管理', '', 2, 1, 2166, 'pick-up-store', 'ep:basketball', 'mall/trade/delivery/pickUpStore/index', 'PickUpStore', 0, b'1', b'1', b'1', '1', '2023-05-25 10:50:00', '1', '2023-08-30 21:03:28', b'0');
INSERT INTO `system_menu` VALUES (2180, '自提门店查询', 'trade:delivery:pick-up-store:query', 3, 1, 2179, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-05-25 10:53:29', '', '2023-05-25 10:53:29', b'0');
INSERT INTO `system_menu` VALUES (2181, '自提门店创建', 'trade:delivery:pick-up-store:create', 3, 2, 2179, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-05-25 10:53:29', '', '2023-05-25 10:53:29', b'0');
INSERT INTO `system_menu` VALUES (2182, '自提门店更新', 'trade:delivery:pick-up-store:update', 3, 3, 2179, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-05-25 10:53:29', '', '2023-05-25 10:53:29', b'0');
INSERT INTO `system_menu` VALUES (2183, '自提门店删除', 'trade:delivery:pick-up-store:delete', 3, 4, 2179, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-05-25 10:53:29', '', '2023-05-25 10:53:29', b'0');
INSERT INTO `system_menu` VALUES (2184, '自提门店导出', 'trade:delivery:pick-up-store:export', 3, 5, 2179, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-05-25 10:53:29', '', '2023-05-25 10:53:29', b'0');
INSERT INTO `system_menu` VALUES (2209, '秒杀活动', '', 2, 3, 2030, 'seckill', 'ep:place', '', '', 0, b'1', b'1', b'1', '1', '2023-06-24 17:39:13', '1', '2023-06-24 18:55:15', b'0');
INSERT INTO `system_menu` VALUES (2262, '会员中心', '', 1, 55, 0, '/member', 'ep:bicycle', NULL, NULL, 1, b'0', b'1', b'0', '1', '2023-06-10 00:42:03', '1', '2024-02-17 12:12:46', b'0');
INSERT INTO `system_menu` VALUES (2275, '会员配置', '', 2, 0, 2262, 'config', 'fa:archive', 'member/config/index', 'MemberConfig', 0, b'1', b'1', b'1', '', '2023-06-10 02:07:44', '1', '2023-10-01 23:41:29', b'0');
INSERT INTO `system_menu` VALUES (2276, '积分设置查询', 'point:config:query', 3, 1, 2275, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-06-10 02:07:44', '', '2023-06-10 02:07:44', b'0');
INSERT INTO `system_menu` VALUES (2277, '积分设置创建', 'point:config:save', 3, 2, 2275, '', '', '', '', 0, b'1', b'1', b'1', '', '2023-06-10 02:07:44', '1', '2023-06-27 20:32:31', b'0');
INSERT INTO `system_menu` VALUES (2281, '签到配置', '', 2, 2, 2300, 'config', 'ep:calendar', 'member/signin/config/index', 'SignInConfig', 0, b'1', b'1', b'1', '', '2023-06-10 03:26:12', '1', '2023-08-20 19:25:51', b'0');
INSERT INTO `system_menu` VALUES (2282, '积分签到规则查询', 'point:sign-in-config:query', 3, 1, 2281, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-06-10 03:26:12', '', '2023-06-10 03:26:12', b'0');
INSERT INTO `system_menu` VALUES (2283, '积分签到规则创建', 'point:sign-in-config:create', 3, 2, 2281, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-06-10 03:26:12', '', '2023-06-10 03:26:12', b'0');
INSERT INTO `system_menu` VALUES (2284, '积分签到规则更新', 'point:sign-in-config:update', 3, 3, 2281, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-06-10 03:26:12', '', '2023-06-10 03:26:12', b'0');
INSERT INTO `system_menu` VALUES (2285, '积分签到规则删除', 'point:sign-in-config:delete', 3, 4, 2281, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-06-10 03:26:12', '', '2023-06-10 03:26:12', b'0');
INSERT INTO `system_menu` VALUES (2287, '会员积分', '', 2, 10, 2262, 'record', 'fa:asterisk', 'member/point/record/index', 'PointRecord', 0, b'1', b'1', b'1', '', '2023-06-10 04:18:50', '1', '2023-10-01 23:42:11', b'0');
INSERT INTO `system_menu` VALUES (2288, '用户积分记录查询', 'point:record:query', 3, 1, 2287, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-06-10 04:18:50', '', '2023-06-10 04:18:50', b'0');
INSERT INTO `system_menu` VALUES (2293, '签到记录', '', 2, 3, 2300, 'record', 'ep:chicken', 'member/signin/record/index', 'SignInRecord', 0, b'1', b'1', b'1', '', '2023-06-10 04:48:22', '1', '2023-08-20 19:26:02', b'0');
INSERT INTO `system_menu` VALUES (2294, '用户签到积分查询', 'point:sign-in-record:query', 3, 1, 2293, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-06-10 04:48:22', '', '2023-06-10 04:48:22', b'0');
INSERT INTO `system_menu` VALUES (2297, '用户签到积分删除', 'point:sign-in-record:delete', 3, 4, 2293, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-06-10 04:48:22', '', '2023-06-10 04:48:22', b'0');
INSERT INTO `system_menu` VALUES (2300, '会员签到', '', 1, 11, 2262, 'signin', 'ep:alarm-clock', '', '', 0, b'1', b'1', b'1', '1', '2023-06-27 22:49:53', '1', '2023-08-20 09:23:48', b'0');
INSERT INTO `system_menu` VALUES (2301, '回调通知', '', 2, 5, 1117, 'notify', 'ep:mute-notification', 'pay/notify/index', 'PayNotify', 0, b'1', b'1', b'1', '', '2023-07-20 04:41:32', '1', '2024-01-18 23:56:48', b'0');
INSERT INTO `system_menu` VALUES (2302, '支付通知查询', 'pay:notify:query', 3, 1, 2301, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-07-20 04:41:32', '', '2023-07-20 04:41:32', b'0');
INSERT INTO `system_menu` VALUES (2303, '拼团活动', '', 2, 3, 2030, 'combination', 'fa:group', '', '', 0, b'1', b'1', b'1', '1', '2023-08-12 17:19:54', '1', '2023-08-12 17:20:05', b'0');
INSERT INTO `system_menu` VALUES (2304, '拼团商品', '', 2, 1, 2303, 'acitivity', 'ep:apple', 'mall/promotion/combination/activity/index', 'PromotionCombinationActivity', 0, b'1', b'1', b'1', '1', '2023-08-12 17:22:03', '1', '2023-08-12 17:22:29', b'0');
INSERT INTO `system_menu` VALUES (2305, '拼团活动查询', 'promotion:combination-activity:query', 3, 1, 2304, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-08-12 17:54:32', '1', '2023-11-24 11:57:40', b'0');
INSERT INTO `system_menu` VALUES (2306, '拼团活动创建', 'promotion:combination-activity:create', 3, 2, 2304, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-08-12 17:54:49', '1', '2023-08-12 17:54:49', b'0');
INSERT INTO `system_menu` VALUES (2307, '拼团活动更新', 'promotion:combination-activity:update', 3, 3, 2304, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-08-12 17:55:04', '1', '2023-08-12 17:55:04', b'0');
INSERT INTO `system_menu` VALUES (2308, '拼团活动删除', 'promotion:combination-activity:delete', 3, 4, 2304, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-08-12 17:55:23', '1', '2023-08-12 17:55:23', b'0');
INSERT INTO `system_menu` VALUES (2309, '拼团活动关闭', 'promotion:combination-activity:close', 3, 5, 2304, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-08-12 17:55:37', '1', '2023-10-06 10:51:57', b'0');
INSERT INTO `system_menu` VALUES (2310, '砍价活动', '', 2, 4, 2030, 'bargain', 'ep:box', '', '', 0, b'1', b'1', b'1', '1', '2023-08-13 00:27:25', '1', '2023-08-13 00:27:25', b'0');
INSERT INTO `system_menu` VALUES (2311, '砍价商品', '', 2, 1, 2310, 'activity', 'ep:burger', 'mall/promotion/bargain/activity/index', 'PromotionBargainActivity', 0, b'1', b'1', b'1', '1', '2023-08-13 00:28:49', '1', '2023-10-05 01:16:23', b'0');
INSERT INTO `system_menu` VALUES (2312, '砍价活动查询', 'promotion:bargain-activity:query', 3, 1, 2311, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-08-13 00:32:30', '1', '2023-08-13 00:32:30', b'0');
INSERT INTO `system_menu` VALUES (2313, '砍价活动创建', 'promotion:bargain-activity:create', 3, 2, 2311, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-08-13 00:32:44', '1', '2023-08-13 00:32:44', b'0');
INSERT INTO `system_menu` VALUES (2314, '砍价活动更新', 'promotion:bargain-activity:update', 3, 3, 2311, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-08-13 00:32:55', '1', '2023-08-13 00:32:55', b'0');
INSERT INTO `system_menu` VALUES (2315, '砍价活动删除', 'promotion:bargain-activity:delete', 3, 4, 2311, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-08-13 00:34:50', '1', '2023-08-13 00:34:50', b'0');
INSERT INTO `system_menu` VALUES (2316, '砍价活动关闭', 'promotion:bargain-activity:close', 3, 5, 2311, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-08-13 00:35:02', '1', '2023-08-13 00:35:02', b'0');
INSERT INTO `system_menu` VALUES (2317, '会员管理', '', 2, 0, 2262, 'user', 'ep:avatar', 'member/user/index', 'MemberUser', 0, b'1', b'1', b'1', '', '2023-08-19 04:12:15', '1', '2023-08-24 00:50:55', b'0');
INSERT INTO `system_menu` VALUES (2318, '会员用户查询', 'member:user:query', 3, 1, 2317, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-08-19 04:12:15', '', '2023-08-19 04:12:15', b'0');
INSERT INTO `system_menu` VALUES (2319, '会员用户更新', 'member:user:update', 3, 3, 2317, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-08-19 04:12:15', '', '2023-08-19 04:12:15', b'0');
INSERT INTO `system_menu` VALUES (2320, '会员标签', '', 2, 1, 2262, 'tag', 'ep:collection-tag', 'member/tag/index', 'MemberTag', 0, b'1', b'1', b'1', '', '2023-08-20 01:03:08', '1', '2023-08-20 09:23:19', b'0');
INSERT INTO `system_menu` VALUES (2321, '会员标签查询', 'member:tag:query', 3, 1, 2320, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-08-20 01:03:08', '', '2023-08-20 01:03:08', b'0');
INSERT INTO `system_menu` VALUES (2322, '会员标签创建', 'member:tag:create', 3, 2, 2320, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-08-20 01:03:08', '', '2023-08-20 01:03:08', b'0');
INSERT INTO `system_menu` VALUES (2323, '会员标签更新', 'member:tag:update', 3, 3, 2320, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-08-20 01:03:08', '', '2023-08-20 01:03:08', b'0');
INSERT INTO `system_menu` VALUES (2324, '会员标签删除', 'member:tag:delete', 3, 4, 2320, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-08-20 01:03:08', '', '2023-08-20 01:03:08', b'0');
INSERT INTO `system_menu` VALUES (2325, '会员等级', '', 2, 2, 2262, 'level', 'fa:level-up', 'member/level/index', 'MemberLevel', 0, b'1', b'1', b'1', '', '2023-08-22 12:41:01', '1', '2023-08-22 21:47:00', b'0');
INSERT INTO `system_menu` VALUES (2326, '会员等级查询', 'member:level:query', 3, 1, 2325, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-08-22 12:41:02', '', '2023-08-22 12:41:02', b'0');
INSERT INTO `system_menu` VALUES (2327, '会员等级创建', 'member:level:create', 3, 2, 2325, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-08-22 12:41:02', '', '2023-08-22 12:41:02', b'0');
INSERT INTO `system_menu` VALUES (2328, '会员等级更新', 'member:level:update', 3, 3, 2325, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-08-22 12:41:02', '', '2023-08-22 12:41:02', b'0');
INSERT INTO `system_menu` VALUES (2329, '会员等级删除', 'member:level:delete', 3, 4, 2325, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-08-22 12:41:02', '', '2023-08-22 12:41:02', b'0');
INSERT INTO `system_menu` VALUES (2330, '会员分组', '', 2, 3, 2262, 'group', 'fa:group', 'member/group/index', 'MemberGroup', 0, b'1', b'1', b'1', '', '2023-08-22 13:50:06', '1', '2023-10-01 23:42:01', b'0');
INSERT INTO `system_menu` VALUES (2331, '用户分组查询', 'member:group:query', 3, 1, 2330, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-08-22 13:50:06', '', '2023-08-22 13:50:06', b'0');
INSERT INTO `system_menu` VALUES (2332, '用户分组创建', 'member:group:create', 3, 2, 2330, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-08-22 13:50:06', '', '2023-08-22 13:50:06', b'0');
INSERT INTO `system_menu` VALUES (2333, '用户分组更新', 'member:group:update', 3, 3, 2330, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-08-22 13:50:06', '', '2023-08-22 13:50:06', b'0');
INSERT INTO `system_menu` VALUES (2334, '用户分组删除', 'member:group:delete', 3, 4, 2330, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-08-22 13:50:06', '', '2023-08-22 13:50:06', b'0');
INSERT INTO `system_menu` VALUES (2335, '用户等级修改', 'member:user:update-level', 3, 5, 2317, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-08-23 16:49:05', '', '2023-08-23 16:50:48', b'0');
INSERT INTO `system_menu` VALUES (2336, '商品评论', '', 2, 5, 2000, 'comment', 'ep:comment', 'mall/product/comment/index', 'ProductComment', 0, b'1', b'1', b'1', '1', '2023-08-26 11:03:00', '1', '2023-08-26 11:03:38', b'0');
INSERT INTO `system_menu` VALUES (2337, '评论查询', 'product:comment:query', 3, 1, 2336, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-08-26 11:04:01', '1', '2023-08-26 11:04:01', b'0');
INSERT INTO `system_menu` VALUES (2338, '添加自评', 'product:comment:create', 3, 2, 2336, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-08-26 11:04:23', '1', '2023-08-26 11:08:18', b'0');
INSERT INTO `system_menu` VALUES (2339, '商家回复', 'product:comment:update', 3, 3, 2336, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-08-26 11:04:37', '1', '2023-08-26 11:04:37', b'0');
INSERT INTO `system_menu` VALUES (2340, '显隐评论', 'product:comment:update', 3, 4, 2336, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-08-26 11:04:55', '1', '2023-08-26 11:04:55', b'0');
INSERT INTO `system_menu` VALUES (2341, '优惠劵发送', 'promotion:coupon:send', 3, 2, 2038, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-09-02 00:03:14', '1', '2023-09-02 00:03:14', b'0');
INSERT INTO `system_menu` VALUES (2342, '交易配置', '', 2, 0, 2072, 'config', 'ep:setting', 'trade/config/index', 'TradeConfig', 0, b'1', b'1', b'1', '', '2023-09-28 02:46:22', '1', '2023-09-28 10:57:36', b'0');
INSERT INTO `system_menu` VALUES (2343, '交易中心配置查询', 'trade:config:query', 3, 1, 2342, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-09-28 02:46:22', '', '2023-09-28 02:46:22', b'0');
INSERT INTO `system_menu` VALUES (2344, '交易中心配置保存', 'trade:config:save', 3, 2, 2342, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-09-28 02:46:22', '', '2023-09-28 02:46:22', b'0');
INSERT INTO `system_menu` VALUES (2345, '分销管理', '', 1, 4, 2072, 'brokerage', 'fa-solid:project-diagram', '', '', 0, b'1', b'1', b'1', '', '2023-09-28 02:46:22', '1', '2023-09-28 10:58:44', b'0');
INSERT INTO `system_menu` VALUES (2346, '分销用户', '', 2, 0, 2345, 'brokerage-user', 'fa-solid:user-tie', 'trade/brokerage/user/index', 'TradeBrokerageUser', 0, b'1', b'1', b'1', '', '2023-09-28 02:46:22', '', '2023-09-28 02:46:22', b'0');
INSERT INTO `system_menu` VALUES (2347, '分销用户查询', 'trade:brokerage-user:query', 3, 1, 2346, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-09-28 02:46:22', '', '2023-09-28 02:46:22', b'0');
INSERT INTO `system_menu` VALUES (2348, '分销用户推广人查询', 'trade:brokerage-user:user-query', 3, 2, 2346, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-09-28 02:46:22', '', '2023-09-28 02:46:22', b'0');
INSERT INTO `system_menu` VALUES (2349, '分销用户推广订单查询', 'trade:brokerage-user:order-query', 3, 3, 2346, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-09-28 02:46:22', '', '2023-09-28 02:46:22', b'0');
INSERT INTO `system_menu` VALUES (2350, '分销用户修改推广资格', 'trade:brokerage-user:update-brokerage-enable', 3, 4, 2346, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-09-28 02:46:22', '', '2023-09-28 02:46:22', b'0');
INSERT INTO `system_menu` VALUES (2351, '分销用户修改推广员', 'trade:brokerage-user:update-bind-user', 3, 5, 2346, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-09-28 02:46:22', '', '2023-09-28 02:46:22', b'0');
INSERT INTO `system_menu` VALUES (2352, '分销用户清除推广员', 'trade:brokerage-user:clear-bind-user', 3, 6, 2346, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-09-28 02:46:22', '', '2023-09-28 02:46:22', b'0');
INSERT INTO `system_menu` VALUES (2353, '佣金记录', '', 2, 1, 2345, 'brokerage-record', 'fa:money', 'trade/brokerage/record/index', 'TradeBrokerageRecord', 0, b'1', b'1', b'1', '', '2023-09-28 02:46:22', '', '2023-09-28 02:46:22', b'0');
INSERT INTO `system_menu` VALUES (2354, '佣金记录查询', 'trade:brokerage-record:query', 3, 1, 2353, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-09-28 02:46:22', '', '2023-09-28 02:46:22', b'0');
INSERT INTO `system_menu` VALUES (2355, '佣金提现', '', 2, 2, 2345, 'brokerage-withdraw', 'fa:credit-card', 'trade/brokerage/withdraw/index', 'TradeBrokerageWithdraw', 0, b'1', b'1', b'1', '', '2023-09-28 02:46:22', '', '2023-09-28 02:46:22', b'0');
INSERT INTO `system_menu` VALUES (2356, '佣金提现查询', 'trade:brokerage-withdraw:query', 3, 1, 2355, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-09-28 02:46:22', '', '2023-09-28 02:46:22', b'0');
INSERT INTO `system_menu` VALUES (2357, '佣金提现审核', 'trade:brokerage-withdraw:audit', 3, 2, 2355, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-09-28 02:46:22', '', '2023-09-28 02:46:22', b'0');
INSERT INTO `system_menu` VALUES (2358, '统计中心', '', 1, 75, 2362, 'statistics', 'ep:data-line', '', '', 0, b'1', b'1', b'1', '', '2023-09-30 03:22:40', '1', '2023-09-30 11:54:48', b'0');
INSERT INTO `system_menu` VALUES (2359, '交易统计', '', 2, 4, 2358, 'trade', 'fa-solid:credit-card', 'statistics/trade/index', 'TradeStatistics', 0, b'1', b'1', b'1', '', '2023-09-30 03:22:40', '', '2023-09-30 03:22:40', b'0');
INSERT INTO `system_menu` VALUES (2360, '交易统计查询', 'statistics:trade:query', 3, 1, 2359, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-09-30 03:22:40', '', '2023-09-30 03:22:40', b'0');
INSERT INTO `system_menu` VALUES (2361, '交易统计导出', 'statistics:trade:export', 3, 2, 2359, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-09-30 03:22:40', '', '2023-09-30 03:22:40', b'0');
INSERT INTO `system_menu` VALUES (2362, '商城系统', '', 1, 59, 0, '/mall', 'ep:shop', '', '', 1, b'0', b'1', b'0', '1', '2023-09-30 11:52:02', '1', '2024-02-17 12:12:52', b'0');
INSERT INTO `system_menu` VALUES (2363, '用户积分修改', 'member:user:update-point', 3, 6, 2317, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-01 14:39:43', '', '2023-10-01 14:39:43', b'0');
INSERT INTO `system_menu` VALUES (2364, '用户余额修改', 'member:user:update-balance', 3, 7, 2317, '', '', '', '', 0, b'1', b'1', b'1', '', '2023-10-01 14:39:43', '1', '2023-10-01 22:42:31', b'0');
INSERT INTO `system_menu` VALUES (2365, '优惠劵', '', 1, 2, 2030, 'coupon', 'fa-solid:disease', '', '', 0, b'1', b'1', b'1', '1', '2023-10-03 12:39:15', '1', '2023-10-05 00:16:07', b'0');
INSERT INTO `system_menu` VALUES (2366, '砍价记录', '', 2, 2, 2310, 'record', 'ep:list', 'mall/promotion/bargain/record/index', 'PromotionBargainRecord', 0, b'1', b'1', b'1', '', '2023-10-05 02:49:06', '1', '2023-10-05 10:50:38', b'0');
INSERT INTO `system_menu` VALUES (2367, '砍价记录查询', 'promotion:bargain-record:query', 3, 1, 2366, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-05 02:49:06', '', '2023-10-05 02:49:06', b'0');
INSERT INTO `system_menu` VALUES (2368, '助力记录查询', 'promotion:bargain-help:query', 3, 2, 2366, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-10-05 12:27:49', '1', '2023-10-05 12:27:49', b'0');
INSERT INTO `system_menu` VALUES (2369, '拼团记录', 'promotion:combination-record:query', 2, 2, 2303, 'record', 'ep:avatar', 'mall/promotion/combination/record/index.vue', 'PromotionCombinationRecord', 0, b'1', b'1', b'1', '1', '2023-10-08 07:10:22', '1', '2023-10-08 07:34:11', b'0');
INSERT INTO `system_menu` VALUES (2374, '会员统计', '', 2, 2, 2358, 'member', 'ep:avatar', 'statistics/member/index', 'MemberStatistics', 0, b'1', b'1', b'1', '', '2023-10-11 04:39:24', '1', '2023-10-11 12:50:22', b'0');
INSERT INTO `system_menu` VALUES (2375, '会员统计查询', 'statistics:member:query', 3, 1, 2374, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-11 04:39:24', '', '2023-10-11 04:39:24', b'0');
INSERT INTO `system_menu` VALUES (2376, '订单核销', 'trade:order:pick-up', 3, 10, 2076, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-10-14 17:11:58', '1', '2023-10-14 17:11:58', b'0');
INSERT INTO `system_menu` VALUES (2377, '文章分类', '', 2, 0, 2387, 'article/category', 'fa:certificate', 'mall/promotion/article/category/index', 'ArticleCategory', 0, b'1', b'1', b'1', '', '2023-10-16 01:26:18', '1', '2023-10-16 09:38:26', b'0');
INSERT INTO `system_menu` VALUES (2378, '分类查询', 'promotion:article-category:query', 3, 1, 2377, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-16 01:26:18', '', '2023-10-16 01:26:18', b'0');
INSERT INTO `system_menu` VALUES (2379, '分类创建', 'promotion:article-category:create', 3, 2, 2377, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-16 01:26:18', '', '2023-10-16 01:26:18', b'0');
INSERT INTO `system_menu` VALUES (2380, '分类更新', 'promotion:article-category:update', 3, 3, 2377, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-16 01:26:18', '', '2023-10-16 01:26:18', b'0');
INSERT INTO `system_menu` VALUES (2381, '分类删除', 'promotion:article-category:delete', 3, 4, 2377, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-16 01:26:18', '', '2023-10-16 01:26:18', b'0');
INSERT INTO `system_menu` VALUES (2382, '文章列表', '', 2, 2, 2387, 'article', 'ep:connection', 'mall/promotion/article/index', 'Article', 0, b'1', b'1', b'1', '', '2023-10-16 01:26:18', '1', '2023-10-16 09:41:19', b'0');
INSERT INTO `system_menu` VALUES (2383, '文章管理查询', 'promotion:article:query', 3, 1, 2382, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-16 01:26:18', '', '2023-10-16 01:26:18', b'0');
INSERT INTO `system_menu` VALUES (2384, '文章管理创建', 'promotion:article:create', 3, 2, 2382, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-16 01:26:18', '', '2023-10-16 01:26:18', b'0');
INSERT INTO `system_menu` VALUES (2385, '文章管理更新', 'promotion:article:update', 3, 3, 2382, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-16 01:26:18', '', '2023-10-16 01:26:18', b'0');
INSERT INTO `system_menu` VALUES (2386, '文章管理删除', 'promotion:article:delete', 3, 4, 2382, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-16 01:26:18', '', '2023-10-16 01:26:18', b'0');
INSERT INTO `system_menu` VALUES (2387, '内容管理', '', 1, 1, 2030, 'content', 'ep:collection', '', '', 0, b'1', b'1', b'1', '1', '2023-10-16 09:37:31', '1', '2023-10-16 09:37:31', b'0');
INSERT INTO `system_menu` VALUES (2388, '商城首页', '', 2, 1, 2362, 'home', 'ep:home-filled', 'mall/home/index', 'MallHome', 0, b'1', b'1', b'1', '', '2023-10-16 12:10:33', '', '2023-10-16 12:10:33', b'0');
INSERT INTO `system_menu` VALUES (2389, '核销订单', '', 2, 2, 2166, 'pick-up-order', 'ep:list', 'mall/trade/delivery/pickUpOrder/index', 'PickUpOrder', 0, b'1', b'1', b'1', '', '2023-10-19 16:09:51', '', '2023-10-19 16:09:51', b'0');
INSERT INTO `system_menu` VALUES (2390, '优惠活动', '', 1, 99, 2030, 'youhui', 'ep:aim', '', '', 0, b'1', b'1', b'1', '1', '2023-10-21 19:23:49', '1', '2023-10-21 19:23:49', b'0');
INSERT INTO `system_menu` VALUES (2391, '客户管理', '', 2, 0, 2397, 'customer', 'fa:address-book-o', 'crm/customer/index', 'CrmCustomer', 0, b'1', b'1', b'1', '', '2023-10-29 09:04:21', '1', '2024-01-15 21:27:42', b'0');
INSERT INTO `system_menu` VALUES (2392, '客户查询', 'crm:customer:query', 3, 1, 2391, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 09:04:21', '', '2023-10-29 09:04:21', b'0');
INSERT INTO `system_menu` VALUES (2393, '客户创建', 'crm:customer:create', 3, 2, 2391, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 09:04:21', '', '2023-10-29 09:04:21', b'0');
INSERT INTO `system_menu` VALUES (2394, '客户更新', 'crm:customer:update', 3, 3, 2391, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 09:04:21', '', '2023-10-29 09:04:21', b'0');
INSERT INTO `system_menu` VALUES (2395, '客户删除', 'crm:customer:delete', 3, 4, 2391, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 09:04:21', '', '2023-10-29 09:04:21', b'0');
INSERT INTO `system_menu` VALUES (2396, '客户导出', 'crm:customer:export', 3, 5, 2391, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 09:04:21', '', '2023-10-29 09:04:21', b'0');
INSERT INTO `system_menu` VALUES (2397, '客户管理系统', '', 1, 200, 0, '/crm', 'ep:avatar', '', '', 1, b'0', b'1', b'0', '1', '2023-10-29 17:08:30', '1', '2024-02-17 12:13:07', b'0');
INSERT INTO `system_menu` VALUES (2398, '合同管理', '', 2, 1, 2397, 'contract', 'ep:notebook', 'crm/contract/index', 'CrmContract', 0, b'1', b'1', b'1', '', '2023-10-29 10:50:41', '1', '2023-10-29 18:55:53', b'0');
INSERT INTO `system_menu` VALUES (2399, '合同查询', 'crm:contract:query', 3, 1, 2398, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 10:50:41', '', '2023-10-29 10:50:41', b'0');
INSERT INTO `system_menu` VALUES (2400, '合同创建', 'crm:contract:create', 3, 2, 2398, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 10:50:41', '', '2023-10-29 10:50:41', b'0');
INSERT INTO `system_menu` VALUES (2401, '合同更新', 'crm:contract:update', 3, 3, 2398, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 10:50:41', '', '2023-10-29 10:50:41', b'0');
INSERT INTO `system_menu` VALUES (2402, '合同删除', 'crm:contract:delete', 3, 4, 2398, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 10:50:41', '', '2023-10-29 10:50:41', b'0');
INSERT INTO `system_menu` VALUES (2403, '合同导出', 'crm:contract:export', 3, 5, 2398, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 10:50:41', '', '2023-10-29 10:50:41', b'0');
INSERT INTO `system_menu` VALUES (2404, '线索管理', '', 2, 0, 2397, 'clue', 'fa:pagelines', 'crm/clue/index', 'CrmClue', 0, b'1', b'1', b'1', '', '2023-10-29 11:06:29', '1', '2023-10-29 19:08:35', b'0');
INSERT INTO `system_menu` VALUES (2405, '线索查询', 'crm:clue:query', 3, 1, 2404, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 11:06:29', '', '2023-10-29 11:06:29', b'0');
INSERT INTO `system_menu` VALUES (2406, '线索创建', 'crm:clue:create', 3, 2, 2404, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 11:06:29', '', '2023-10-29 11:06:29', b'0');
INSERT INTO `system_menu` VALUES (2407, '线索更新', 'crm:clue:update', 3, 3, 2404, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 11:06:29', '', '2023-10-29 11:06:29', b'0');
INSERT INTO `system_menu` VALUES (2408, '线索删除', 'crm:clue:delete', 3, 4, 2404, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 11:06:29', '', '2023-10-29 11:06:29', b'0');
INSERT INTO `system_menu` VALUES (2409, '线索导出', 'crm:clue:export', 3, 5, 2404, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 11:06:29', '', '2023-10-29 11:06:29', b'0');
INSERT INTO `system_menu` VALUES (2410, '商机管理', '', 2, 0, 2397, 'business', 'fa:bus', 'crm/business/index', 'CrmBusiness', 0, b'1', b'1', b'1', '', '2023-10-29 11:12:35', '1', '2023-10-29 19:13:01', b'0');
INSERT INTO `system_menu` VALUES (2411, '商机查询', 'crm:business:query', 3, 1, 2410, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 11:12:35', '', '2023-10-29 11:12:35', b'0');
INSERT INTO `system_menu` VALUES (2412, '商机创建', 'crm:business:create', 3, 2, 2410, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 11:12:35', '', '2023-10-29 11:12:35', b'0');
INSERT INTO `system_menu` VALUES (2413, '商机更新', 'crm:business:update', 3, 3, 2410, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 11:12:35', '', '2023-10-29 11:12:35', b'0');
INSERT INTO `system_menu` VALUES (2414, '商机删除', 'crm:business:delete', 3, 4, 2410, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 11:12:35', '', '2023-10-29 11:12:35', b'0');
INSERT INTO `system_menu` VALUES (2415, '商机导出', 'crm:business:export', 3, 5, 2410, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 11:12:35', '', '2023-10-29 11:12:35', b'0');
INSERT INTO `system_menu` VALUES (2416, '联系人管理', '', 2, 0, 2397, 'contact', 'fa:address-book-o', 'crm/contact/index', 'CrmContact', 0, b'1', b'1', b'1', '', '2023-10-29 11:14:56', '1', '2023-12-01 19:39:50', b'0');
INSERT INTO `system_menu` VALUES (2417, '联系人查询', 'crm:contact:query', 3, 1, 2416, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 11:14:56', '', '2023-10-29 11:14:56', b'0');
INSERT INTO `system_menu` VALUES (2418, '联系人创建', 'crm:contact:create', 3, 2, 2416, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 11:14:56', '', '2023-10-29 11:14:56', b'0');
INSERT INTO `system_menu` VALUES (2419, '联系人更新', 'crm:contact:update', 3, 3, 2416, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 11:14:56', '', '2023-10-29 11:14:56', b'0');
INSERT INTO `system_menu` VALUES (2420, '联系人删除', 'crm:contact:delete', 3, 4, 2416, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 11:14:56', '', '2023-10-29 11:14:56', b'0');
INSERT INTO `system_menu` VALUES (2421, '联系人导出', 'crm:contact:export', 3, 5, 2416, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 11:14:56', '', '2023-10-29 11:14:56', b'0');
INSERT INTO `system_menu` VALUES (2422, '回款管理', '', 2, 0, 2397, 'receivable', 'ep:money', 'crm/receivable/index', 'CrmReceivable', 0, b'1', b'1', b'1', '', '2023-10-29 11:18:09', '1', '2023-10-29 19:18:52', b'0');
INSERT INTO `system_menu` VALUES (2423, '回款管理查询', 'crm:receivable:query', 3, 1, 2422, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 11:18:09', '', '2023-10-29 11:18:09', b'0');
INSERT INTO `system_menu` VALUES (2424, '回款管理创建', 'crm:receivable:create', 3, 2, 2422, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 11:18:09', '', '2023-10-29 11:18:09', b'0');
INSERT INTO `system_menu` VALUES (2425, '回款管理更新', 'crm:receivable:update', 3, 3, 2422, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 11:18:09', '', '2023-10-29 11:18:09', b'0');
INSERT INTO `system_menu` VALUES (2426, '回款管理删除', 'crm:receivable:delete', 3, 4, 2422, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 11:18:09', '', '2023-10-29 11:18:09', b'0');
INSERT INTO `system_menu` VALUES (2427, '回款管理导出', 'crm:receivable:export', 3, 5, 2422, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 11:18:09', '', '2023-10-29 11:18:09', b'0');
INSERT INTO `system_menu` VALUES (2428, '回款计划', '', 2, 0, 2397, 'receivable-plan', 'fa:money', 'crm/receivable/plan/index', 'CrmReceivablePlan', 0, b'1', b'1', b'1', '', '2023-10-29 11:18:09', '1', '2023-12-01 19:55:48', b'0');
INSERT INTO `system_menu` VALUES (2429, '回款计划查询', 'crm:receivable-plan:query', 3, 1, 2428, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 11:18:09', '', '2023-10-29 11:18:09', b'0');
INSERT INTO `system_menu` VALUES (2430, '回款计划创建', 'crm:receivable-plan:create', 3, 2, 2428, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 11:18:09', '', '2023-10-29 11:18:09', b'0');
INSERT INTO `system_menu` VALUES (2431, '回款计划更新', 'crm:receivable-plan:update', 3, 3, 2428, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 11:18:09', '', '2023-10-29 11:18:09', b'0');
INSERT INTO `system_menu` VALUES (2432, '回款计划删除', 'crm:receivable-plan:delete', 3, 4, 2428, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 11:18:09', '', '2023-10-29 11:18:09', b'0');
INSERT INTO `system_menu` VALUES (2433, '回款计划导出', 'crm:receivable-plan:export', 3, 5, 2428, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 11:18:09', '', '2023-10-29 11:18:09', b'0');
INSERT INTO `system_menu` VALUES (2435, '商城装修', '', 2, 20, 2030, 'diy-template', 'fa6-solid:brush', 'mall/promotion/diy/template/index', 'DiyTemplate', 0, b'1', b'1', b'1', '', '2023-10-29 14:19:25', '', '2023-10-29 14:19:25', b'0');
INSERT INTO `system_menu` VALUES (2436, '装修模板', '', 2, 1, 2435, 'diy-template', 'fa6-solid:brush', 'mall/promotion/diy/template/index', 'DiyTemplate', 0, b'1', b'1', b'1', '', '2023-10-29 14:19:25', '', '2023-10-29 14:19:25', b'0');
INSERT INTO `system_menu` VALUES (2437, '装修模板查询', 'promotion:diy-template:query', 3, 1, 2436, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 14:19:25', '', '2023-10-29 14:19:25', b'0');
INSERT INTO `system_menu` VALUES (2438, '装修模板创建', 'promotion:diy-template:create', 3, 2, 2436, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 14:19:25', '', '2023-10-29 14:19:25', b'0');
INSERT INTO `system_menu` VALUES (2439, '装修模板更新', 'promotion:diy-template:update', 3, 3, 2436, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 14:19:25', '', '2023-10-29 14:19:25', b'0');
INSERT INTO `system_menu` VALUES (2440, '装修模板删除', 'promotion:diy-template:delete', 3, 4, 2436, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 14:19:25', '', '2023-10-29 14:19:25', b'0');
INSERT INTO `system_menu` VALUES (2441, '装修模板使用', 'promotion:diy-template:use', 3, 5, 2436, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 14:19:25', '', '2023-10-29 14:19:25', b'0');
INSERT INTO `system_menu` VALUES (2442, '装修页面', '', 2, 2, 2435, 'diy-page', 'foundation:page-edit', 'mall/promotion/diy/page/index', 'DiyPage', 0, b'1', b'1', b'1', '', '2023-10-29 14:19:25', '', '2023-10-29 14:19:25', b'0');
INSERT INTO `system_menu` VALUES (2443, '装修页面查询', 'promotion:diy-page:query', 3, 1, 2442, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 14:19:25', '', '2023-10-29 14:19:25', b'0');
INSERT INTO `system_menu` VALUES (2444, '装修页面创建', 'promotion:diy-page:create', 3, 2, 2442, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 14:19:26', '', '2023-10-29 14:19:26', b'0');
INSERT INTO `system_menu` VALUES (2445, '装修页面更新', 'promotion:diy-page:update', 3, 3, 2442, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 14:19:26', '', '2023-10-29 14:19:26', b'0');
INSERT INTO `system_menu` VALUES (2446, '装修页面删除', 'promotion:diy-page:delete', 3, 4, 2442, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-10-29 14:19:26', '', '2023-10-29 14:19:26', b'0');
INSERT INTO `system_menu` VALUES (2447, '三方登录', '', 1, 10, 1, 'social', 'fa:500px', '', '', 1, b'0', b'1', b'0', '1', '2023-11-04 12:12:01', '1', '2024-02-19 10:53:27', b'0');
INSERT INTO `system_menu` VALUES (2448, '三方应用', '', 2, 1, 2447, 'client', 'ep:set-up', 'views/system/social/client/index.vue', 'SocialClient', 0, b'1', b'1', b'1', '1', '2023-11-04 12:17:19', '1', '2023-11-04 12:17:19', b'0');
INSERT INTO `system_menu` VALUES (2449, '三方应用查询', 'system:social-client:query', 3, 1, 2448, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-11-04 12:43:12', '1', '2023-11-04 12:43:33', b'0');
INSERT INTO `system_menu` VALUES (2450, '三方应用创建', 'system:social-client:create', 3, 2, 2448, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-11-04 12:43:58', '1', '2023-11-04 12:43:58', b'0');
INSERT INTO `system_menu` VALUES (2451, '三方应用更新', 'system:social-client:update', 3, 3, 2448, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-11-04 12:44:27', '1', '2023-11-04 12:44:27', b'0');
INSERT INTO `system_menu` VALUES (2452, '三方应用删除', 'system:social-client:delete', 3, 4, 2448, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-11-04 12:44:43', '1', '2023-11-04 12:44:43', b'0');
INSERT INTO `system_menu` VALUES (2453, '三方用户', 'system:social-user:query', 2, 2, 2447, 'user', 'ep:avatar', 'system/social/user/index.vue', 'SocialUser', 0, b'1', b'1', b'1', '1', '2023-11-04 14:01:05', '1', '2023-11-04 14:01:05', b'0');
INSERT INTO `system_menu` VALUES (2472, '主子表（内嵌）', '', 2, 12, 1070, 'demo03-inner', 'fa:power-off', 'infra/demo/demo03/inner/index', 'Demo03StudentInner', 0, b'1', b'1', b'1', '', '2023-11-13 04:39:51', '1', '2023-11-16 23:53:46', b'0');
INSERT INTO `system_menu` VALUES (2478, '单表（增删改查）', '', 2, 1, 1070, 'demo01-contact', 'ep:bicycle', 'infra/demo/demo01/index', 'Demo01Contact', 0, b'1', b'1', b'1', '', '2023-11-15 14:42:30', '1', '2023-11-16 20:34:40', b'0');
INSERT INTO `system_menu` VALUES (2479, '示例联系人查询', 'infra:demo01-contact:query', 3, 1, 2478, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-11-15 14:42:30', '', '2023-11-15 14:42:30', b'0');
INSERT INTO `system_menu` VALUES (2480, '示例联系人创建', 'infra:demo01-contact:create', 3, 2, 2478, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-11-15 14:42:30', '', '2023-11-15 14:42:30', b'0');
INSERT INTO `system_menu` VALUES (2481, '示例联系人更新', 'infra:demo01-contact:update', 3, 3, 2478, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-11-15 14:42:30', '', '2023-11-15 14:42:30', b'0');
INSERT INTO `system_menu` VALUES (2482, '示例联系人删除', 'infra:demo01-contact:delete', 3, 4, 2478, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-11-15 14:42:30', '', '2023-11-15 14:42:30', b'0');
INSERT INTO `system_menu` VALUES (2483, '示例联系人导出', 'infra:demo01-contact:export', 3, 5, 2478, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-11-15 14:42:30', '', '2023-11-15 14:42:30', b'0');
INSERT INTO `system_menu` VALUES (2484, '树表（增删改查）', '', 2, 2, 1070, 'demo02-category', 'fa:tree', 'infra/demo/demo02/index', 'Demo02Category', 0, b'1', b'1', b'1', '', '2023-11-16 12:18:27', '1', '2023-11-16 20:35:01', b'0');
INSERT INTO `system_menu` VALUES (2485, '示例分类查询', 'infra:demo02-category:query', 3, 1, 2484, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-11-16 12:18:27', '', '2023-11-16 12:18:27', b'0');
INSERT INTO `system_menu` VALUES (2486, '示例分类创建', 'infra:demo02-category:create', 3, 2, 2484, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-11-16 12:18:27', '', '2023-11-16 12:18:27', b'0');
INSERT INTO `system_menu` VALUES (2487, '示例分类更新', 'infra:demo02-category:update', 3, 3, 2484, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-11-16 12:18:27', '', '2023-11-16 12:18:27', b'0');
INSERT INTO `system_menu` VALUES (2488, '示例分类删除', 'infra:demo02-category:delete', 3, 4, 2484, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-11-16 12:18:27', '', '2023-11-16 12:18:27', b'0');
INSERT INTO `system_menu` VALUES (2489, '示例分类导出', 'infra:demo02-category:export', 3, 5, 2484, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-11-16 12:18:27', '', '2023-11-16 12:18:27', b'0');
INSERT INTO `system_menu` VALUES (2490, '主子表（标准）', '', 2, 10, 1070, 'demo03-normal', 'fa:battery-3', 'infra/demo/demo03/normal/index', 'Demo03StudentNormal', 0, b'1', b'1', b'1', '', '2023-11-16 12:53:37', '1', '2023-11-16 23:10:03', b'0');
INSERT INTO `system_menu` VALUES (2491, '学生查询', 'infra:demo03-student:query', 3, 1, 2490, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-11-16 12:53:37', '', '2023-11-16 12:53:37', b'0');
INSERT INTO `system_menu` VALUES (2492, '学生创建', 'infra:demo03-student:create', 3, 2, 2490, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-11-16 12:53:37', '', '2023-11-16 12:53:37', b'0');
INSERT INTO `system_menu` VALUES (2493, '学生更新', 'infra:demo03-student:update', 3, 3, 2490, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-11-16 12:53:37', '', '2023-11-16 12:53:37', b'0');
INSERT INTO `system_menu` VALUES (2494, '学生删除', 'infra:demo03-student:delete', 3, 4, 2490, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-11-16 12:53:37', '', '2023-11-16 12:53:37', b'0');
INSERT INTO `system_menu` VALUES (2495, '学生导出', 'infra:demo03-student:export', 3, 5, 2490, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-11-16 12:53:37', '', '2023-11-16 12:53:37', b'0');
INSERT INTO `system_menu` VALUES (2497, '主子表（ERP）', '', 2, 11, 1070, 'demo03-erp', 'ep:calendar', 'infra/demo/demo03/erp/index', 'Demo03StudentERP', 0, b'1', b'1', b'1', '', '2023-11-16 15:50:59', '1', '2023-11-17 13:19:56', b'0');
INSERT INTO `system_menu` VALUES (2516, '客户公海配置', '', 2, 0, 2524, 'customer-pool-config', 'ep:data-analysis', 'crm/customer/poolConfig/index', 'CrmCustomerPoolConfig', 0, b'1', b'1', b'1', '', '2023-11-18 13:33:31', '1', '2024-01-03 19:52:06', b'0');
INSERT INTO `system_menu` VALUES (2517, '客户公海配置保存', 'crm:customer-pool-config:update', 3, 1, 2516, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-11-18 13:33:31', '', '2023-11-18 13:33:31', b'0');
INSERT INTO `system_menu` VALUES (2518, '客户限制配置', '', 2, 0, 2524, 'customer-limit-config', 'ep:avatar', 'crm/customer/limitConfig/index', 'CrmCustomerLimitConfig', 0, b'1', b'1', b'1', '', '2023-11-18 13:33:53', '1', '2024-01-03 19:58:25', b'0');
INSERT INTO `system_menu` VALUES (2519, '客户限制配置查询', 'crm:customer-limit-config:query', 3, 1, 2518, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-11-18 13:33:53', '', '2023-11-18 13:33:53', b'0');
INSERT INTO `system_menu` VALUES (2520, '客户限制配置创建', 'crm:customer-limit-config:create', 3, 2, 2518, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-11-18 13:33:53', '', '2023-11-18 13:33:53', b'0');
INSERT INTO `system_menu` VALUES (2521, '客户限制配置更新', 'crm:customer-limit-config:update', 3, 3, 2518, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-11-18 13:33:53', '', '2023-11-18 13:33:53', b'0');
INSERT INTO `system_menu` VALUES (2522, '客户限制配置删除', 'crm:customer-limit-config:delete', 3, 4, 2518, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-11-18 13:33:53', '', '2023-11-18 13:33:53', b'0');
INSERT INTO `system_menu` VALUES (2523, '客户限制配置导出', 'crm:customer-limit-config:export', 3, 5, 2518, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-11-18 13:33:53', '', '2023-11-18 13:33:53', b'0');
INSERT INTO `system_menu` VALUES (2524, '系统配置', '', 1, 99, 2397, 'config', 'ep:connection', '', '', 0, b'1', b'1', b'1', '1', '2023-11-18 21:58:00', '1', '2023-11-18 21:58:00', b'0');
INSERT INTO `system_menu` VALUES (2525, 'WebSocket 测试', '', 2, 7, 2, 'websocket', 'ep:connection', 'infra/webSocket/index', 'InfraWebSocket', 1, b'0', b'0', b'0', '1', '2023-11-23 19:41:55', '1', '2024-02-19 10:51:14', b'0');
INSERT INTO `system_menu` VALUES (2526, '产品管理', '', 2, 10, 2397, 'product', 'fa:product-hunt', 'crm/product/index', 'CrmProduct', 0, b'1', b'1', b'1', '1', '2023-12-05 22:45:26', '1', '2023-12-05 22:46:45', b'0');
INSERT INTO `system_menu` VALUES (2527, '产品查询', 'crm:product:query', 3, 1, 2526, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-12-05 22:47:16', '1', '2023-12-05 22:47:16', b'0');
INSERT INTO `system_menu` VALUES (2528, '产品创建', 'crm:product:create', 3, 2, 2526, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-12-05 22:47:41', '1', '2023-12-05 22:47:48', b'0');
INSERT INTO `system_menu` VALUES (2529, '产品更新', 'crm:product:update', 3, 3, 2526, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-12-05 22:48:03', '1', '2023-12-05 22:48:03', b'0');
INSERT INTO `system_menu` VALUES (2530, '产品删除', 'crm:product:delete', 3, 4, 2526, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-12-05 22:48:17', '1', '2023-12-05 22:48:17', b'0');
INSERT INTO `system_menu` VALUES (2531, '产品导出', 'crm:product:export', 3, 5, 2526, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-12-05 22:48:29', '1', '2023-12-05 22:48:29', b'0');
INSERT INTO `system_menu` VALUES (2532, '产品分类配置', '', 2, 3, 2524, 'product/category', 'fa-solid:window-restore', 'crm/product/category/index', 'CrmProductCategory', 0, b'1', b'1', b'1', '1', '2023-12-06 12:52:36', '1', '2023-12-06 12:52:51', b'0');
INSERT INTO `system_menu` VALUES (2533, '产品分类查询', 'crm:product-category:query', 3, 1, 2532, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-12-06 12:53:23', '1', '2023-12-06 12:53:23', b'0');
INSERT INTO `system_menu` VALUES (2534, '产品分类创建', 'crm:product-category:create', 3, 2, 2532, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-12-06 12:53:41', '1', '2023-12-06 12:53:41', b'0');
INSERT INTO `system_menu` VALUES (2535, '产品分类更新', 'crm:product-category:update', 3, 3, 2532, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-12-06 12:53:59', '1', '2023-12-06 12:53:59', b'0');
INSERT INTO `system_menu` VALUES (2536, '产品分类删除', 'crm:product-category:delete', 3, 4, 2532, '', '', '', '', 0, b'1', b'1', b'1', '1', '2023-12-06 12:54:14', '1', '2023-12-06 12:54:14', b'0');
INSERT INTO `system_menu` VALUES (2537, 'Ureport2报表管理', '', 2, 0, 1281, 'ureport-data', '', 'report/ureport/index', 'UReportData', 0, b'1', b'1', b'1', '', '2023-12-06 12:55:55', '', '2023-12-06 12:55:55', b'0');
INSERT INTO `system_menu` VALUES (2538, 'Ureport2报表查询', 'report:ureport-data:query', 3, 1, 2537, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-12-06 12:55:55', '', '2023-12-06 12:55:55', b'0');
INSERT INTO `system_menu` VALUES (2539, 'Ureport2报表创建', 'report:ureport-data:create', 3, 2, 2537, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-12-06 12:55:55', '', '2023-12-06 12:55:55', b'0');
INSERT INTO `system_menu` VALUES (2540, 'Ureport2报表更新', 'report:ureport-data:update', 3, 3, 2537, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-12-06 12:55:55', '', '2023-12-06 12:55:55', b'0');
INSERT INTO `system_menu` VALUES (2541, 'Ureport2报表删除', 'report:ureport-data:delete', 3, 4, 2537, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-12-06 12:55:55', '', '2023-12-06 12:55:55', b'0');
INSERT INTO `system_menu` VALUES (2542, 'Ureport2报表导出', 'report:ureport-data:export', 3, 5, 2537, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-12-06 12:55:55', '', '2023-12-06 12:55:55', b'0');
INSERT INTO `system_menu` VALUES (2543, '关联商机', 'crm:contact:create-business', 3, 10, 2416, '', '', '', '', 0, b'1', b'1', b'1', '1', '2024-01-02 17:28:25', '1', '2024-01-02 17:28:25', b'0');
INSERT INTO `system_menu` VALUES (2544, '取关商机', 'crm:contact:delete-business', 3, 11, 2416, '', '', '', '', 0, b'1', b'1', b'1', '1', '2024-01-02 17:28:43', '1', '2024-01-02 17:28:51', b'0');
INSERT INTO `system_menu` VALUES (2545, '商品统计', '', 2, 3, 2358, 'product', 'fa:product-hunt', 'statistics/product/index', 'ProductStatistics', 0, b'1', b'1', b'1', '', '2023-12-15 18:54:28', '1', '2024-01-17 23:11:54', b'0');
INSERT INTO `system_menu` VALUES (2546, '客户公海', '', 2, -1, 2397, 'customer/pool', 'fa-solid:swimming-pool', 'crm/customer/pool/index', 'CrmCustomerPool', 0, b'1', b'1', b'1', '1', '2024-01-15 21:29:34', '1', '2023-12-29 19:47:32', b'0');
INSERT INTO `system_menu` VALUES (2547, '订单查询', 'trade:order:query', 3, 1, 2076, '', '', '', '', 0, b'1', b'1', b'1', '1', '2024-01-16 08:52:00', '1', '2024-01-16 08:52:00', b'0');
INSERT INTO `system_menu` VALUES (2548, '订单更新', 'trade:order:update', 3, 2, 2076, '', '', '', '', 0, b'1', b'1', b'1', '1', '2024-01-16 08:52:21', '1', '2024-01-16 08:52:21', b'0');
INSERT INTO `system_menu` VALUES (2549, '支付&退款案例', '', 2, 1, 2161, 'order', 'fa:paypal', 'pay/demo/order/index', '', 0, b'1', b'1', b'1', '1', '2024-01-18 23:45:00', '1', '2024-01-18 23:47:21', b'0');
INSERT INTO `system_menu` VALUES (2550, '转账案例', '', 2, 2, 2161, 'transfer', 'fa:transgender-alt', 'pay/demo/transfer/index', '', 0, b'1', b'1', b'1', '1', '2024-01-18 23:51:16', '1', '2024-01-18 23:51:16', b'0');
INSERT INTO `system_menu` VALUES (2551, '钱包管理', '', 1, 4, 1117, 'wallet', 'ep:caret-right', '', '', 0, b'1', b'1', b'1', '', '2023-12-29 02:32:54', '1', '2024-01-18 23:56:52', b'0');
INSERT INTO `system_menu` VALUES (2552, '充值套餐', '', 2, 2, 2551, 'wallet-recharge-package', 'fa:leaf', 'pay/wallet/rechargePackage/index', 'WalletRechargePackage', 0, b'1', b'1', b'1', '', '2023-12-29 02:32:54', '', '2023-12-29 02:32:54', b'0');
INSERT INTO `system_menu` VALUES (2553, '钱包充值套餐查询', 'pay:wallet-recharge-package:query', 3, 1, 2552, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-12-29 02:32:54', '', '2023-12-29 02:32:54', b'0');
INSERT INTO `system_menu` VALUES (2554, '钱包充值套餐创建', 'pay:wallet-recharge-package:create', 3, 2, 2552, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-12-29 02:32:54', '', '2023-12-29 02:32:54', b'0');
INSERT INTO `system_menu` VALUES (2555, '钱包充值套餐更新', 'pay:wallet-recharge-package:update', 3, 3, 2552, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-12-29 02:32:54', '', '2023-12-29 02:32:54', b'0');
INSERT INTO `system_menu` VALUES (2556, '钱包充值套餐删除', 'pay:wallet-recharge-package:delete', 3, 4, 2552, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-12-29 02:32:54', '', '2023-12-29 02:32:54', b'0');
INSERT INTO `system_menu` VALUES (2557, '钱包余额', '', 2, 1, 2551, 'wallet-balance', 'fa:leaf', 'pay/wallet/balance/index', 'WalletBalance', 0, b'1', b'1', b'1', '', '2023-12-29 02:32:54', '', '2023-12-29 02:32:54', b'0');
INSERT INTO `system_menu` VALUES (2558, '钱包余额查询', 'pay:wallet:query', 3, 1, 2557, '', '', '', NULL, 0, b'1', b'1', b'1', '', '2023-12-29 02:32:54', '', '2023-12-29 02:32:54', b'0');
INSERT INTO `system_menu` VALUES (2559, '转账订单', '', 2, 3, 1117, 'transfer', 'ep:credit-card', 'pay/transfer/index', 'PayTransfer', 0, b'1', b'1', b'1', '', '2023-12-29 02:32:54', '', '2023-12-29 02:32:54', b'0');

-- ----------------------------
-- Table structure for system_notice
-- ----------------------------
DROP TABLE IF EXISTS `system_notice`;
CREATE TABLE `system_notice`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '公告标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '公告内容',
  `type` tinyint NOT NULL COMMENT '公告类型（1通知 2公告）',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '公告状态（0正常 1关闭）',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '通知公告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_notice
-- ----------------------------
INSERT INTO `system_notice` VALUES (1, '芋道的公众', '<p>新版本内容133</p>', 1, 0, 'admin', '2021-01-05 17:03:48', '1', '2022-05-04 21:00:20', b'0', 1);
INSERT INTO `system_notice` VALUES (2, '维护通知：2018-07-01 系统凌晨维护', '<p><img src=\"http://test.bi.iocoder.cn/b7cb3cf49b4b3258bf7309a09dd2f4e5.jpg\" alt=\"\" data-href=\"\" style=\"\"/>11112222</p>', 2, 1, 'admin', '2021-01-05 17:03:48', '1', '2023-12-02 20:07:26', b'0', 1);
INSERT INTO `system_notice` VALUES (4, '我是测试标题', '<p>哈哈哈哈123</p>', 1, 0, '110', '2022-02-22 01:01:25', '110', '2022-02-22 01:01:46', b'0', 121);

-- ----------------------------
-- Table structure for system_notify_message
-- ----------------------------
DROP TABLE IF EXISTS `system_notify_message`;
CREATE TABLE `system_notify_message`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `user_type` tinyint NOT NULL COMMENT '用户类型',
  `template_id` bigint NOT NULL COMMENT '模版编号',
  `template_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模板编码',
  `template_nickname` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模版发送人名称',
  `template_content` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模版内容',
  `template_type` int NOT NULL COMMENT '模版类型',
  `template_params` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模版参数',
  `read_status` bit(1) NOT NULL COMMENT '是否已读',
  `read_time` datetime NULL DEFAULT NULL COMMENT '阅读时间',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '站内信消息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_notify_message
-- ----------------------------
INSERT INTO `system_notify_message` VALUES (2, 1, 2, 1, 'test', '123', '我是 1，我开始 2 了', 1, '{\"name\":\"1\",\"what\":\"2\"}', b'1', '2023-02-10 00:47:04', '1', '2023-01-28 11:44:08', '1', '2023-02-10 00:47:04', b'0', 1);
INSERT INTO `system_notify_message` VALUES (3, 1, 2, 1, 'test', '123', '我是 1，我开始 2 了', 1, '{\"name\":\"1\",\"what\":\"2\"}', b'1', '2023-02-10 00:47:04', '1', '2023-01-28 11:45:04', '1', '2023-02-10 00:47:04', b'0', 1);
INSERT INTO `system_notify_message` VALUES (4, 103, 2, 2, 'register', '系统消息', '你好，欢迎 哈哈 加入大家庭！', 2, '{\"name\":\"哈哈\"}', b'0', NULL, '1', '2023-01-28 21:02:20', '1', '2023-01-28 21:02:20', b'0', 1);
INSERT INTO `system_notify_message` VALUES (5, 1, 2, 1, 'test', '123', '我是 芋艿，我开始 写代码 了', 1, '{\"name\":\"芋艿\",\"what\":\"写代码\"}', b'1', '2023-02-10 00:47:04', '1', '2023-01-28 22:21:42', '1', '2023-02-10 00:47:04', b'0', 1);
INSERT INTO `system_notify_message` VALUES (6, 1, 2, 1, 'test', '123', '我是 芋艿，我开始 写代码 了', 1, '{\"name\":\"芋艿\",\"what\":\"写代码\"}', b'1', '2023-01-29 10:52:06', '1', '2023-01-28 22:22:07', '1', '2023-01-29 10:52:06', b'0', 1);
INSERT INTO `system_notify_message` VALUES (7, 1, 2, 1, 'test', '123', '我是 2，我开始 3 了', 1, '{\"name\":\"2\",\"what\":\"3\"}', b'1', '2023-01-29 10:52:06', '1', '2023-01-28 23:45:21', '1', '2023-01-29 10:52:06', b'0', 1);
INSERT INTO `system_notify_message` VALUES (8, 1, 2, 2, 'register', '系统消息', '你好，欢迎 123 加入大家庭！', 2, '{\"name\":\"123\"}', b'1', '2023-01-29 10:52:06', '1', '2023-01-28 23:50:21', '1', '2023-01-29 10:52:06', b'0', 1);
INSERT INTO `system_notify_message` VALUES (9, 247, 1, 4, 'brokerage_withdraw_audit_approve', 'system', '您在2023-09-28 08:35:46提现￥0.09元的申请已通过审核', 2, '{\"reason\":null,\"createTime\":\"2023-09-28 08:35:46\",\"price\":\"0.09\"}', b'0', NULL, '1', '2023-09-28 16:36:22', '1', '2023-09-28 16:36:22', b'0', 1);
INSERT INTO `system_notify_message` VALUES (10, 247, 1, 4, 'brokerage_withdraw_audit_approve', 'system', '您在2023-09-30 20:59:40提现￥1.00元的申请已通过审核', 2, '{\"reason\":null,\"createTime\":\"2023-09-30 20:59:40\",\"price\":\"1.00\"}', b'0', NULL, '1', '2023-10-03 12:11:34', '1', '2023-10-03 12:11:34', b'0', 1);

-- ----------------------------
-- Table structure for system_notify_template
-- ----------------------------
DROP TABLE IF EXISTS `system_notify_template`;
CREATE TABLE `system_notify_template`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模板名称',
  `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模版编码',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '发送人名称',
  `content` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模版内容',
  `type` tinyint NOT NULL COMMENT '类型',
  `params` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '参数数组',
  `status` tinyint NOT NULL COMMENT '状态',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '站内信模板表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_notify_template
-- ----------------------------

-- ----------------------------
-- Table structure for system_oauth2_access_token
-- ----------------------------
DROP TABLE IF EXISTS `system_oauth2_access_token`;
CREATE TABLE `system_oauth2_access_token`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `user_type` tinyint NOT NULL COMMENT '用户类型',
  `access_token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '访问令牌',
  `refresh_token` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '刷新令牌',
  `client_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '客户端编号',
  `scopes` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '授权范围',
  `expires_time` datetime NOT NULL COMMENT '过期时间',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_access_token`(`access_token` ASC) USING BTREE,
  INDEX `idx_refresh_token`(`refresh_token` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4407 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'OAuth2 访问令牌' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_oauth2_access_token
-- ----------------------------
INSERT INTO `system_oauth2_access_token` VALUES (4335, 1, 2, '98dce32fae70430d93e4f0be0b38ffb5', 'e149b6dd0a924dbc87151e723beb67da', 'default', NULL, '2024-02-17 12:18:22', NULL, '2024-02-17 11:48:22', NULL, '2024-02-17 11:52:30', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4336, 1, 2, 'f027bcb43f374418a26ffbc57b7a62c5', 'e31b903aec5746e2ac76c3277cf823b0', 'default', NULL, '2024-02-17 12:22:39', NULL, '2024-02-17 11:52:39', NULL, '2024-02-17 12:25:23', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4337, 1, 2, 'aec44c7a75ca4427870c31a4af8ee44d', 'e31b903aec5746e2ac76c3277cf823b0', 'default', NULL, '2024-02-17 12:55:24', NULL, '2024-02-17 12:25:24', NULL, '2024-02-17 12:59:23', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4338, 1, 2, '7a4d4b98996a4e56860c85295aa52214', 'e31b903aec5746e2ac76c3277cf823b0', 'default', NULL, '2024-02-17 13:29:24', NULL, '2024-02-17 12:59:24', NULL, '2024-02-17 13:31:24', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4339, 1, 2, '6e63797ae734403381c4d54a4c760e93', 'e31b903aec5746e2ac76c3277cf823b0', 'default', NULL, '2024-02-17 14:01:24', NULL, '2024-02-17 13:31:24', NULL, '2024-02-17 13:36:03', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4340, 1, 2, '3dc64b5c061f4406b8dac473aec682ce', 'c6297b0a92a7492ba518181b0d11ebd5', 'default', NULL, '2024-02-17 14:06:11', NULL, '2024-02-17 13:36:11', NULL, '2024-02-17 14:08:12', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4341, 1, 2, '9aad5146eed3419cb7707c4efec954ab', 'c6297b0a92a7492ba518181b0d11ebd5', 'default', NULL, '2024-02-17 14:38:12', NULL, '2024-02-17 14:08:12', NULL, '2024-02-17 14:40:24', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4342, 1, 2, '84ad5a84ba4646eaaca14cb8a7eaa070', 'c6297b0a92a7492ba518181b0d11ebd5', 'default', NULL, '2024-02-17 15:10:24', NULL, '2024-02-17 14:40:24', NULL, '2024-02-17 15:17:41', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4343, 1, 2, 'dcc51ee6bad14222ba989f02d27f076b', 'c6297b0a92a7492ba518181b0d11ebd5', 'default', NULL, '2024-02-17 15:47:42', NULL, '2024-02-17 15:17:42', NULL, '2024-02-17 15:17:50', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4344, 1, 2, '135563b0b6074d08ad89f4dfdf0a0637', '0a05e82f09fe4ab899cdce0e4b110be8', 'default', NULL, '2024-02-17 15:47:56', NULL, '2024-02-17 15:17:56', NULL, '2024-02-17 15:17:56', b'0', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4345, 1, 2, '2376888703bf4aa984d20d9eccbd1410', '97196d33bad34ecb8e88c47c57f3bb11', 'default', NULL, '2024-02-17 17:19:18', NULL, '2024-02-17 16:49:18', NULL, '2024-02-17 16:54:51', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4346, 1, 2, '53a3d82968f148208bacef1da412a787', 'd577cc4c5d504bc583a8a8652fa4458d', 'default', NULL, '2024-02-17 17:25:03', NULL, '2024-02-17 16:55:03', NULL, '2024-02-17 16:59:03', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4347, 1, 2, 'affdbbebed4a47cebc21ae783626a868', 'e55a016c58c44feb857cc06461e8b5f8', 'default', NULL, '2024-02-17 17:29:08', NULL, '2024-02-17 16:59:08', NULL, '2024-02-17 17:31:03', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4348, 1, 2, '6b65a8685fa945508d2ad7de235f5c91', 'e55a016c58c44feb857cc06461e8b5f8', 'default', NULL, '2024-02-17 18:01:04', NULL, '2024-02-17 17:31:04', NULL, '2024-02-17 17:34:59', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4349, 1, 2, 'f678bbaf817c40b8b8900ab9d15be64e', '9e7d1f67529d4ae7a64238527b26844f', 'default', NULL, '2024-02-17 18:05:10', NULL, '2024-02-17 17:35:10', NULL, '2024-02-17 18:07:04', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4350, 1, 2, '47a83ee007af4324b646f32ddbd53796', '9e7d1f67529d4ae7a64238527b26844f', 'default', NULL, '2024-02-17 18:37:04', NULL, '2024-02-17 18:07:04', NULL, '2024-02-17 19:04:35', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4351, 1, 2, 'ca359d0bad634708aebf9a1bd2233b07', '9e7d1f67529d4ae7a64238527b26844f', 'default', NULL, '2024-02-17 19:34:35', NULL, '2024-02-17 19:04:35', NULL, '2024-02-19 10:39:59', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4352, 1, 2, 'b06280c693184f61ac7fcc9fb9905859', '2a6e7a1efd5748d9ada8c12a0af690d6', 'default', NULL, '2024-02-19 11:10:23', NULL, '2024-02-19 10:40:23', NULL, '2024-02-19 11:13:50', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4353, 1, 2, 'c03667a35904464fab150bb731d992f5', '2a6e7a1efd5748d9ada8c12a0af690d6', 'default', NULL, '2024-02-19 11:43:50', NULL, '2024-02-19 11:13:50', NULL, '2024-02-19 11:17:21', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4354, 110, 2, '7891798e7b9e4a53a97dd7ce4deccca6', 'be1a8e5a43a04b8db3e529bf65d1d6ab', 'default', NULL, '2024-02-19 11:48:10', NULL, '2024-02-19 11:18:10', NULL, '2024-02-19 11:28:05', b'1', 121);
INSERT INTO `system_oauth2_access_token` VALUES (4355, 129, 2, '00e030737d4749568ae933aa63a5257b', '23621d87369142859f56a123c4c2f37f', 'default', NULL, '2024-02-19 11:58:17', NULL, '2024-02-19 11:28:17', NULL, '2024-02-19 12:00:44', b'1', 153);
INSERT INTO `system_oauth2_access_token` VALUES (4356, 129, 2, '53e95b820ec04d42b9b06b96d16fa040', '23621d87369142859f56a123c4c2f37f', 'default', NULL, '2024-02-19 12:30:45', NULL, '2024-02-19 12:00:45', NULL, '2024-02-19 12:32:45', b'1', 153);
INSERT INTO `system_oauth2_access_token` VALUES (4357, 129, 2, 'ff8ef00ce7a24478a0869068c483f6d3', '23621d87369142859f56a123c4c2f37f', 'default', NULL, '2024-02-19 13:02:45', NULL, '2024-02-19 12:32:45', NULL, '2024-02-19 13:13:00', b'1', 153);
INSERT INTO `system_oauth2_access_token` VALUES (4358, 129, 2, '07b120f13a024d019534bc9883ae3ff6', '23621d87369142859f56a123c4c2f37f', 'default', NULL, '2024-02-19 13:43:00', NULL, '2024-02-19 13:13:00', NULL, '2024-02-19 13:46:13', b'1', 153);
INSERT INTO `system_oauth2_access_token` VALUES (4359, 129, 2, '4048b376676d4da282da9d52f466ae88', '23621d87369142859f56a123c4c2f37f', 'default', NULL, '2024-02-19 14:16:13', NULL, '2024-02-19 13:46:13', NULL, '2024-02-19 14:18:13', b'1', 153);
INSERT INTO `system_oauth2_access_token` VALUES (4360, 129, 2, '82877662306048fbb9f1fcc12cc01957', '23621d87369142859f56a123c4c2f37f', 'default', NULL, '2024-02-19 14:48:13', NULL, '2024-02-19 14:18:13', NULL, '2024-02-19 14:50:13', b'1', 153);
INSERT INTO `system_oauth2_access_token` VALUES (4361, 129, 2, 'cea656521c414fac91fb3ebe06630f1b', '23621d87369142859f56a123c4c2f37f', 'default', NULL, '2024-02-19 15:20:13', NULL, '2024-02-19 14:50:13', NULL, '2024-02-19 15:23:00', b'1', 153);
INSERT INTO `system_oauth2_access_token` VALUES (4362, 129, 2, '61914294edbb4c978b857343f7847de4', '23621d87369142859f56a123c4c2f37f', 'default', NULL, '2024-02-19 15:53:00', NULL, '2024-02-19 15:23:00', NULL, '2024-02-19 15:55:00', b'1', 153);
INSERT INTO `system_oauth2_access_token` VALUES (4363, 129, 2, '0b78b7d7e2a540e084104ed72194058d', '23621d87369142859f56a123c4c2f37f', 'default', NULL, '2024-02-19 16:25:00', NULL, '2024-02-19 15:55:00', NULL, '2024-02-19 16:27:00', b'1', 153);
INSERT INTO `system_oauth2_access_token` VALUES (4364, 129, 2, 'ac7e9b88451c4d74a3cf21ad64f80a23', '23621d87369142859f56a123c4c2f37f', 'default', NULL, '2024-02-19 16:57:00', NULL, '2024-02-19 16:27:00', NULL, '2024-02-19 16:56:48', b'1', 153);
INSERT INTO `system_oauth2_access_token` VALUES (4365, 129, 2, '5205ab9b445c47088995879ed1d0b44b', 'dae55ff05c9b4e39aaa17b04bc89c6ab', 'default', NULL, '2024-02-19 17:27:06', NULL, '2024-02-19 16:57:06', NULL, '2024-02-19 17:29:06', b'1', 153);
INSERT INTO `system_oauth2_access_token` VALUES (4366, 129, 2, '80466fdd23fa4cd29abe1a626e9dcbee', 'dae55ff05c9b4e39aaa17b04bc89c6ab', 'default', NULL, '2024-02-19 17:59:07', NULL, '2024-02-19 17:29:07', NULL, '2024-02-19 18:01:06', b'1', 153);
INSERT INTO `system_oauth2_access_token` VALUES (4367, 129, 2, 'be75196a7a78464ca1afb1239086d9c3', 'dae55ff05c9b4e39aaa17b04bc89c6ab', 'default', NULL, '2024-02-19 18:31:07', NULL, '2024-02-19 18:01:07', NULL, '2024-02-19 18:33:07', b'1', 153);
INSERT INTO `system_oauth2_access_token` VALUES (4368, 129, 2, '813c50afaa884047a04d8198bedbf0c9', 'dae55ff05c9b4e39aaa17b04bc89c6ab', 'default', NULL, '2024-02-19 19:03:07', NULL, '2024-02-19 18:33:07', NULL, '2024-02-28 13:40:48', b'1', 153);
INSERT INTO `system_oauth2_access_token` VALUES (4369, 1, 2, '1486924312b94c889d123da538c0ef6e', '8eafaa77707440dfafb8bf21e95fc9cc', 'default', NULL, '2024-02-28 14:11:03', NULL, '2024-02-28 13:41:03', NULL, '2024-02-28 14:01:47', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4370, 129, 2, 'ed9df6fa410d441cac980f8888161d53', 'e5038612c2d6464f840ab67fe2f6ccc3', 'default', NULL, '2024-02-28 14:32:06', NULL, '2024-02-28 14:02:06', NULL, '2024-02-28 14:03:58', b'1', 153);
INSERT INTO `system_oauth2_access_token` VALUES (4371, 1, 2, '3870258f8361437599aadf8720bcc8ec', 'b8d420e11e1048e1affbc7a392476ca3', 'default', NULL, '2024-02-28 14:37:59', NULL, '2024-02-28 14:07:59', NULL, '2024-02-28 14:08:39', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4372, 1, 2, 'f660ecfdb5884482af6b434e5fa932a1', '3a29b87d03a04572b5f3070468c3366d', 'default', NULL, '2024-02-28 14:43:01', NULL, '2024-02-28 14:13:01', NULL, '2024-02-28 14:13:15', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4373, 1, 2, 'b34adec9ef614cdb893f9e57ec49e34f', '5ffd484fc1d748a9a4833d33b377ff13', 'default', NULL, '2024-02-28 14:46:56', NULL, '2024-02-28 14:16:56', NULL, '2024-02-28 14:21:31', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4374, 129, 2, '2cb0268b642e46739e4515a7f7dc3c6c', '44ea194ba6494ce6a6f39c54398e66e4', 'default', NULL, '2024-02-28 14:52:08', NULL, '2024-02-28 14:22:08', NULL, '2024-02-28 14:23:15', b'1', 153);
INSERT INTO `system_oauth2_access_token` VALUES (4375, 1, 2, '45fbaf70c5e44a82b5fd5eee82627247', '0a4762b75cf6483285cd89dff987c567', 'default', NULL, '2024-02-28 14:53:58', NULL, '2024-02-28 14:23:58', NULL, '2024-02-28 14:24:10', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4376, 1, 2, '731f969bd00d4be7b13372ad1cbfaf9c', '0c850ed84ff84d62a76f6a3e72ade875', 'default', NULL, '2024-02-28 14:54:50', NULL, '2024-02-28 14:24:50', NULL, '2024-02-28 14:31:56', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4377, 1, 2, 'ddc5cb2b5107477b999f234299bc44d2', '3ad4a3e788e6479588ad359a6523774f', 'default', NULL, '2024-02-28 15:02:05', NULL, '2024-02-28 14:32:05', NULL, '2024-02-28 14:34:09', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4378, 129, 2, 'ecbac5e71df4412a8cb384e2d35f8fc5', '34c8b7cf98914a57a8e028bb427ba1a2', 'default', NULL, '2024-02-28 15:04:32', NULL, '2024-02-28 14:34:32', NULL, '2024-02-28 14:35:48', b'1', 153);
INSERT INTO `system_oauth2_access_token` VALUES (4379, 1, 2, '05ac41f540284c50bbdd9fa9147ee703', '14b6413445f7467ab3ae3938f6a27fe3', 'default', NULL, '2024-02-28 15:05:53', NULL, '2024-02-28 14:35:53', NULL, '2024-02-28 14:37:57', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4380, 129, 2, 'd5dc65573eea420195a78454636eaaeb', 'd028eeeda2d6480488ab2a7fe21b5fde', 'default', NULL, '2024-02-28 15:09:01', NULL, '2024-02-28 14:39:01', NULL, '2024-02-28 14:43:25', b'1', 153);
INSERT INTO `system_oauth2_access_token` VALUES (4381, 1, 2, 'ec60cf1b7a5f41ba925ea2f3af71af67', 'ee71ec08b888491db25ac7b39fb37732', 'default', NULL, '2024-02-28 15:14:58', NULL, '2024-02-28 14:44:58', NULL, '2024-02-28 15:05:05', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4382, 1, 2, 'dceca7f21e0549618932e51e208a64e4', '7232076d4c06429eb32b8df276b274a7', 'default', NULL, '2024-02-28 15:37:49', NULL, '2024-02-28 15:07:49', NULL, '2024-02-28 15:09:08', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4383, 1, 2, 'cd0f91fc6c38435c9f57d326e5fd8dfc', '63aa872e3dd3448db38418d8eb871a96', 'default', NULL, '2024-02-28 15:39:13', NULL, '2024-02-28 15:09:13', NULL, '2024-02-28 15:41:13', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4384, 1, 2, '367db7b59cf04893973d77564477d42e', '63aa872e3dd3448db38418d8eb871a96', 'default', NULL, '2024-02-28 16:11:13', NULL, '2024-02-28 15:41:13', NULL, '2024-02-28 16:15:01', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4385, 1, 2, '6272f8207dd849c7a73aeb420f7b1655', '63aa872e3dd3448db38418d8eb871a96', 'default', NULL, '2024-02-28 16:45:01', NULL, '2024-02-28 16:15:01', NULL, '2024-02-28 16:47:01', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4386, 1, 2, '0647cd5b98d54186900d0e5159ed8c56', '63aa872e3dd3448db38418d8eb871a96', 'default', NULL, '2024-02-28 17:17:01', NULL, '2024-02-28 16:47:01', NULL, '2024-02-28 17:17:34', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4387, 1, 2, 'bfc86a801dda45b1a33f75d81b3dfe41', 'c2c727934b8c42ad8707023be5cfc8bb', 'default', NULL, '2024-02-28 18:30:19', NULL, '2024-02-28 18:00:19', NULL, '2024-02-28 18:06:59', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4388, 1, 2, '5d8cc914b09d4761b38f07327cdeaf83', '2f4a6503b285433487bb49b5381f2c70', 'default', NULL, '2024-02-28 18:37:03', NULL, '2024-02-28 18:07:03', NULL, '2024-02-28 18:23:06', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4389, 1, 2, 'b894d095dd934d58bea742d2876edda6', 'a1d3d3b8173845e6a51df01221f9c4af', 'default', NULL, '2024-02-28 18:53:26', NULL, '2024-02-28 18:23:26', NULL, '2024-02-28 18:27:50', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4390, 1, 2, '2a094087aad54e56b74a94976e8455d1', '174d26aa4a2348e6afbb0ab0e9fed4f0', 'default', NULL, '2024-02-28 19:00:38', NULL, '2024-02-28 18:30:38', NULL, '2024-02-28 18:43:20', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4391, 1, 2, 'b7987bb2682e46d19db576c6a3201733', '0ea6bc92d81d4228907b4a3d7c92c6f7', 'default', NULL, '2024-02-28 19:13:25', NULL, '2024-02-28 18:43:25', NULL, '2024-02-28 18:45:16', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4392, 129, 2, '2492cb166f9741eb94746e9ef830536d', 'c13f79f7e12749de851e014a987abe7f', 'default', NULL, '2024-02-28 19:15:28', NULL, '2024-02-28 18:45:28', NULL, '2024-02-28 18:46:47', b'1', 153);
INSERT INTO `system_oauth2_access_token` VALUES (4393, 129, 2, '9b0bdd11b51a406ebb9684f61aba0649', '03cc44821105470297998aadfde26de0', 'default', NULL, '2024-02-28 19:17:43', NULL, '2024-02-28 18:47:43', NULL, '2024-02-28 18:50:30', b'1', 153);
INSERT INTO `system_oauth2_access_token` VALUES (4394, 129, 2, 'c4865d4fb6b845eab51474328059ab4f', 'a1a311fa33174f00bd5bc50ea5bdae2d', 'default', NULL, '2024-02-28 19:20:45', NULL, '2024-02-28 18:50:45', NULL, '2024-02-28 18:51:16', b'1', 153);
INSERT INTO `system_oauth2_access_token` VALUES (4395, 1, 2, '85186431a2364d3c946c4ac461393c17', '9296dae9a8064a9cb2fda82c6a9080d7', 'default', NULL, '2024-02-28 19:21:18', NULL, '2024-02-28 18:51:18', NULL, '2024-02-28 18:51:55', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4396, 129, 2, 'c5778e3274f5470cb224986bb24dd419', 'a43e2060b0c54fc09588ea7a1b2e0449', 'default', NULL, '2024-02-28 19:22:08', NULL, '2024-02-28 18:52:08', NULL, '2024-02-28 18:55:46', b'1', 153);
INSERT INTO `system_oauth2_access_token` VALUES (4397, 129, 2, '3373d1bdbf5e41ef978fcd35107b6fc0', 'd7acfc708e154ca69b6ae1b8996fc633', 'default', NULL, '2024-02-28 19:25:57', NULL, '2024-02-28 18:55:57', NULL, '2024-02-28 18:56:51', b'1', 153);
INSERT INTO `system_oauth2_access_token` VALUES (4398, 1, 2, '32b509e89cc74144b05fa9612f45f2dd', 'e83c7f0bce104d55ba9474c592386280', 'default', NULL, '2024-02-28 19:26:54', NULL, '2024-02-28 18:56:54', NULL, '2024-02-28 18:58:08', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4399, 129, 2, 'a4cf90be543544ee94006bb23ee992c6', 'b3a447e4cd7b42c4bd996471f4866582', 'default', NULL, '2024-02-28 19:28:18', NULL, '2024-02-28 18:58:18', NULL, '2024-02-29 10:55:25', b'1', 153);
INSERT INTO `system_oauth2_access_token` VALUES (4400, 1, 2, 'b5e23e4cf28343488bd8df99ff44bfd2', '06a65ba6d81148d3b9237a691d91e276', 'default', NULL, '2024-02-29 11:25:43', NULL, '2024-02-29 10:55:43', NULL, '2024-02-29 10:55:55', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4401, 129, 2, '0c7989093e4e4a6a9ebe64eb460d5401', 'f1751a593dd849668a04e598243833c1', 'default', NULL, '2024-02-29 11:26:05', NULL, '2024-02-29 10:56:05', NULL, '2024-02-29 10:56:26', b'1', 153);
INSERT INTO `system_oauth2_access_token` VALUES (4402, 1, 2, '40c6d22621bb409495f383c54824888a', '6519187d129d44b48b533f109df9b48b', 'default', NULL, '2024-02-29 17:37:15', NULL, '2024-02-29 17:07:15', NULL, '2024-02-29 17:07:15', b'0', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4403, 1, 2, '22cfc707a4b04bf38b4581a422642c5f', '39b563fbc3fb43039e8022f44ef41113', 'default', NULL, '2024-02-29 17:40:07', NULL, '2024-02-29 17:10:07', NULL, '2024-02-29 17:42:19', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4404, 1, 2, '7db43665a86a4fce845e11c252d8fe7f', '39b563fbc3fb43039e8022f44ef41113', 'default', NULL, '2024-02-29 18:12:20', NULL, '2024-02-29 17:42:20', NULL, '2024-02-29 18:16:19', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4405, 1, 2, 'd8370f840e784beabfe72c559a546192', '39b563fbc3fb43039e8022f44ef41113', 'default', NULL, '2024-02-29 18:46:20', NULL, '2024-02-29 18:16:20', NULL, '2024-02-29 18:50:19', b'1', 1);
INSERT INTO `system_oauth2_access_token` VALUES (4406, 1, 2, 'ec37d012f57c4a34acd73bbbe1843953', '39b563fbc3fb43039e8022f44ef41113', 'default', NULL, '2024-02-29 19:20:20', NULL, '2024-02-29 18:50:20', NULL, '2024-02-29 18:50:20', b'0', 1);

-- ----------------------------
-- Table structure for system_oauth2_approve
-- ----------------------------
DROP TABLE IF EXISTS `system_oauth2_approve`;
CREATE TABLE `system_oauth2_approve`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `user_type` tinyint NOT NULL COMMENT '用户类型',
  `client_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '客户端编号',
  `scope` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '授权范围',
  `approved` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否接受',
  `expires_time` datetime NOT NULL COMMENT '过期时间',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 82 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'OAuth2 批准表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_oauth2_approve
-- ----------------------------

-- ----------------------------
-- Table structure for system_oauth2_client
-- ----------------------------
DROP TABLE IF EXISTS `system_oauth2_client`;
CREATE TABLE `system_oauth2_client`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `client_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '客户端编号',
  `secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '客户端密钥',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '应用名',
  `logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '应用图标',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '应用描述',
  `status` tinyint NOT NULL COMMENT '状态',
  `access_token_validity_seconds` int NOT NULL COMMENT '访问令牌的有效期',
  `refresh_token_validity_seconds` int NOT NULL COMMENT '刷新令牌的有效期',
  `redirect_uris` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '可重定向的 URI 地址',
  `authorized_grant_types` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '授权类型',
  `scopes` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '授权范围',
  `auto_approve_scopes` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '自动通过的授权范围',
  `authorities` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '权限',
  `resource_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '资源',
  `additional_information` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '附加信息',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'OAuth2 客户端表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_oauth2_client
-- ----------------------------
INSERT INTO `system_oauth2_client` VALUES (1, 'default', 'admin123', '芋道源码', 'http://test.bi.iocoder.cn/a5e2e244368878a366b516805a4aabf1.png', '我是描述', 0, 1800, 43200, '[\"https://www.iocoder.cn\",\"https://doc.iocoder.cn\"]', '[\"password\",\"authorization_code\",\"implicit\",\"refresh_token\"]', '[\"user.read\",\"user.write\"]', '[]', '[\"user.read\",\"user.write\"]', '[]', '{}', '1', '2022-05-11 21:47:12', '1', '2022-07-05 16:23:52', b'0');
INSERT INTO `system_oauth2_client` VALUES (40, 'test', 'test2', 'biubiu', 'http://test.bi.iocoder.cn/277a899d573723f1fcdfb57340f00379.png', '啦啦啦啦', 0, 1800, 43200, '[\"https://www.iocoder.cn\"]', '[\"password\",\"authorization_code\",\"implicit\"]', '[\"user_info\",\"projects\"]', '[\"user_info\"]', '[]', '[]', '{}', '1', '2022-05-12 00:28:20', '1', '2023-12-02 21:01:01', b'0');
INSERT INTO `system_oauth2_client` VALUES (41, 'bi-sso-demo-by-code', 'test', '基于授权码模式，如何实现 SSO 单点登录？', 'http://test.bi.iocoder.cn/fe4ed36596adad5120036ef61a6d0153654544d44af8dd4ad3ffe8f759933d6f.png', NULL, 0, 1800, 43200, '[\"http://127.0.0.1:18080\"]', '[\"authorization_code\",\"refresh_token\"]', '[\"user.read\",\"user.write\"]', '[]', '[]', '[]', NULL, '1', '2022-09-29 13:28:31', '1', '2022-09-29 13:28:31', b'0');
INSERT INTO `system_oauth2_client` VALUES (42, 'bi-sso-demo-by-password', 'test', '基于密码模式，如何实现 SSO 单点登录？', 'http://test.bi.iocoder.cn/604bdc695e13b3b22745be704d1f2aa8ee05c5f26f9fead6d1ca49005afbc857.jpeg', NULL, 0, 1800, 43200, '[\"http://127.0.0.1:18080\"]', '[\"password\",\"refresh_token\"]', '[\"user.read\",\"user.write\"]', '[]', '[]', '[]', NULL, '1', '2022-10-04 17:40:16', '1', '2022-10-04 20:31:21', b'0');

-- ----------------------------
-- Table structure for system_oauth2_code
-- ----------------------------
DROP TABLE IF EXISTS `system_oauth2_code`;
CREATE TABLE `system_oauth2_code`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `user_type` tinyint NOT NULL COMMENT '用户类型',
  `code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '授权码',
  `client_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '客户端编号',
  `scopes` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '授权范围',
  `expires_time` datetime NOT NULL COMMENT '过期时间',
  `redirect_uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '可重定向的 URI 地址',
  `state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '状态',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 147 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'OAuth2 授权码表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_oauth2_code
-- ----------------------------

-- ----------------------------
-- Table structure for system_oauth2_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `system_oauth2_refresh_token`;
CREATE TABLE `system_oauth2_refresh_token`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `refresh_token` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '刷新令牌',
  `user_type` tinyint NOT NULL COMMENT '用户类型',
  `client_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '客户端编号',
  `scopes` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '授权范围',
  `expires_time` datetime NOT NULL COMMENT '过期时间',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1353 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'OAuth2 刷新令牌' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_oauth2_refresh_token
-- ----------------------------
INSERT INTO `system_oauth2_refresh_token` VALUES (1309, 1, 'e149b6dd0a924dbc87151e723beb67da', 2, 'default', NULL, '2024-02-17 23:48:21', NULL, '2024-02-17 11:48:21', NULL, '2024-02-17 11:52:30', b'1', 1);
INSERT INTO `system_oauth2_refresh_token` VALUES (1310, 1, 'e31b903aec5746e2ac76c3277cf823b0', 2, 'default', NULL, '2024-02-17 23:52:39', NULL, '2024-02-17 11:52:39', NULL, '2024-02-17 13:36:03', b'1', 1);
INSERT INTO `system_oauth2_refresh_token` VALUES (1311, 1, 'c6297b0a92a7492ba518181b0d11ebd5', 2, 'default', NULL, '2024-02-18 01:36:11', NULL, '2024-02-17 13:36:11', NULL, '2024-02-17 15:17:50', b'1', 1);
INSERT INTO `system_oauth2_refresh_token` VALUES (1312, 1, '0a05e82f09fe4ab899cdce0e4b110be8', 2, 'default', NULL, '2024-02-18 03:17:56', NULL, '2024-02-17 15:17:56', NULL, '2024-02-17 15:17:56', b'0', 1);
INSERT INTO `system_oauth2_refresh_token` VALUES (1313, 1, '97196d33bad34ecb8e88c47c57f3bb11', 2, 'default', NULL, '2024-02-18 04:49:18', NULL, '2024-02-17 16:49:18', NULL, '2024-02-17 16:54:51', b'1', 1);
INSERT INTO `system_oauth2_refresh_token` VALUES (1314, 1, 'd577cc4c5d504bc583a8a8652fa4458d', 2, 'default', NULL, '2024-02-18 04:55:03', NULL, '2024-02-17 16:55:03', NULL, '2024-02-17 16:59:03', b'1', 1);
INSERT INTO `system_oauth2_refresh_token` VALUES (1315, 1, 'e55a016c58c44feb857cc06461e8b5f8', 2, 'default', NULL, '2024-02-18 04:59:08', NULL, '2024-02-17 16:59:08', NULL, '2024-02-17 17:34:59', b'1', 1);
INSERT INTO `system_oauth2_refresh_token` VALUES (1316, 1, '9e7d1f67529d4ae7a64238527b26844f', 2, 'default', NULL, '2024-02-18 05:35:10', NULL, '2024-02-17 17:35:10', NULL, '2024-02-19 10:40:00', b'1', 1);
INSERT INTO `system_oauth2_refresh_token` VALUES (1317, 1, '2a6e7a1efd5748d9ada8c12a0af690d6', 2, 'default', NULL, '2024-02-19 22:40:23', NULL, '2024-02-19 10:40:23', NULL, '2024-02-19 11:17:21', b'1', 1);
INSERT INTO `system_oauth2_refresh_token` VALUES (1318, 110, 'be1a8e5a43a04b8db3e529bf65d1d6ab', 2, 'default', NULL, '2024-02-19 23:18:10', NULL, '2024-02-19 11:18:10', NULL, '2024-02-19 11:28:05', b'1', 121);
INSERT INTO `system_oauth2_refresh_token` VALUES (1319, 129, '23621d87369142859f56a123c4c2f37f', 2, 'default', NULL, '2024-02-19 23:28:17', NULL, '2024-02-19 11:28:17', NULL, '2024-02-19 16:56:48', b'1', 153);
INSERT INTO `system_oauth2_refresh_token` VALUES (1320, 129, 'dae55ff05c9b4e39aaa17b04bc89c6ab', 2, 'default', NULL, '2024-02-20 04:57:06', NULL, '2024-02-19 16:57:06', NULL, '2024-02-28 13:40:48', b'1', 153);
INSERT INTO `system_oauth2_refresh_token` VALUES (1321, 1, '8eafaa77707440dfafb8bf21e95fc9cc', 2, 'default', NULL, '2024-02-29 01:41:03', NULL, '2024-02-28 13:41:03', NULL, '2024-02-28 14:01:47', b'1', 1);
INSERT INTO `system_oauth2_refresh_token` VALUES (1322, 129, 'e5038612c2d6464f840ab67fe2f6ccc3', 2, 'default', NULL, '2024-02-29 02:02:06', NULL, '2024-02-28 14:02:06', NULL, '2024-02-28 14:03:58', b'1', 153);
INSERT INTO `system_oauth2_refresh_token` VALUES (1323, 1, 'b8d420e11e1048e1affbc7a392476ca3', 2, 'default', NULL, '2024-02-29 02:07:59', NULL, '2024-02-28 14:07:59', NULL, '2024-02-28 14:08:39', b'1', 1);
INSERT INTO `system_oauth2_refresh_token` VALUES (1324, 1, '3a29b87d03a04572b5f3070468c3366d', 2, 'default', NULL, '2024-02-29 02:13:01', NULL, '2024-02-28 14:13:01', NULL, '2024-02-28 14:13:15', b'1', 1);
INSERT INTO `system_oauth2_refresh_token` VALUES (1325, 1, '5ffd484fc1d748a9a4833d33b377ff13', 2, 'default', NULL, '2024-02-29 02:16:56', NULL, '2024-02-28 14:16:56', NULL, '2024-02-28 14:21:31', b'1', 1);
INSERT INTO `system_oauth2_refresh_token` VALUES (1326, 129, '44ea194ba6494ce6a6f39c54398e66e4', 2, 'default', NULL, '2024-02-29 02:22:08', NULL, '2024-02-28 14:22:08', NULL, '2024-02-28 14:23:15', b'1', 153);
INSERT INTO `system_oauth2_refresh_token` VALUES (1327, 1, '0a4762b75cf6483285cd89dff987c567', 2, 'default', NULL, '2024-02-29 02:23:58', NULL, '2024-02-28 14:23:58', NULL, '2024-02-28 14:24:10', b'1', 1);
INSERT INTO `system_oauth2_refresh_token` VALUES (1328, 1, '0c850ed84ff84d62a76f6a3e72ade875', 2, 'default', NULL, '2024-02-29 02:24:50', NULL, '2024-02-28 14:24:50', NULL, '2024-02-28 14:31:56', b'1', 1);
INSERT INTO `system_oauth2_refresh_token` VALUES (1329, 1, '3ad4a3e788e6479588ad359a6523774f', 2, 'default', NULL, '2024-02-29 02:32:05', NULL, '2024-02-28 14:32:05', NULL, '2024-02-28 14:34:09', b'1', 1);
INSERT INTO `system_oauth2_refresh_token` VALUES (1330, 129, '34c8b7cf98914a57a8e028bb427ba1a2', 2, 'default', NULL, '2024-02-29 02:34:32', NULL, '2024-02-28 14:34:32', NULL, '2024-02-28 14:35:48', b'1', 153);
INSERT INTO `system_oauth2_refresh_token` VALUES (1331, 1, '14b6413445f7467ab3ae3938f6a27fe3', 2, 'default', NULL, '2024-02-29 02:35:53', NULL, '2024-02-28 14:35:53', NULL, '2024-02-28 14:37:57', b'1', 1);
INSERT INTO `system_oauth2_refresh_token` VALUES (1332, 129, 'd028eeeda2d6480488ab2a7fe21b5fde', 2, 'default', NULL, '2024-02-29 02:39:01', NULL, '2024-02-28 14:39:01', NULL, '2024-02-28 14:43:25', b'1', 153);
INSERT INTO `system_oauth2_refresh_token` VALUES (1333, 1, 'ee71ec08b888491db25ac7b39fb37732', 2, 'default', NULL, '2024-02-29 02:44:58', NULL, '2024-02-28 14:44:58', NULL, '2024-02-28 15:05:05', b'1', 1);
INSERT INTO `system_oauth2_refresh_token` VALUES (1334, 1, '7232076d4c06429eb32b8df276b274a7', 2, 'default', NULL, '2024-02-29 03:07:49', NULL, '2024-02-28 15:07:49', NULL, '2024-02-28 15:09:08', b'1', 1);
INSERT INTO `system_oauth2_refresh_token` VALUES (1335, 1, '63aa872e3dd3448db38418d8eb871a96', 2, 'default', NULL, '2024-02-29 03:09:13', NULL, '2024-02-28 15:09:13', NULL, '2024-02-28 17:17:34', b'1', 1);
INSERT INTO `system_oauth2_refresh_token` VALUES (1336, 1, 'c2c727934b8c42ad8707023be5cfc8bb', 2, 'default', NULL, '2024-02-29 06:00:19', NULL, '2024-02-28 18:00:19', NULL, '2024-02-28 18:06:59', b'1', 1);
INSERT INTO `system_oauth2_refresh_token` VALUES (1337, 1, '2f4a6503b285433487bb49b5381f2c70', 2, 'default', NULL, '2024-02-29 06:07:03', NULL, '2024-02-28 18:07:03', NULL, '2024-02-28 18:23:06', b'1', 1);
INSERT INTO `system_oauth2_refresh_token` VALUES (1338, 1, 'a1d3d3b8173845e6a51df01221f9c4af', 2, 'default', NULL, '2024-02-29 06:23:26', NULL, '2024-02-28 18:23:26', NULL, '2024-02-28 18:27:50', b'1', 1);
INSERT INTO `system_oauth2_refresh_token` VALUES (1339, 1, '174d26aa4a2348e6afbb0ab0e9fed4f0', 2, 'default', NULL, '2024-02-29 06:30:38', NULL, '2024-02-28 18:30:38', NULL, '2024-02-28 18:43:20', b'1', 1);
INSERT INTO `system_oauth2_refresh_token` VALUES (1340, 1, '0ea6bc92d81d4228907b4a3d7c92c6f7', 2, 'default', NULL, '2024-02-29 06:43:25', NULL, '2024-02-28 18:43:25', NULL, '2024-02-28 18:45:16', b'1', 1);
INSERT INTO `system_oauth2_refresh_token` VALUES (1341, 129, 'c13f79f7e12749de851e014a987abe7f', 2, 'default', NULL, '2024-02-29 06:45:28', NULL, '2024-02-28 18:45:28', NULL, '2024-02-28 18:46:47', b'1', 153);
INSERT INTO `system_oauth2_refresh_token` VALUES (1342, 129, '03cc44821105470297998aadfde26de0', 2, 'default', NULL, '2024-02-29 06:47:43', NULL, '2024-02-28 18:47:43', NULL, '2024-02-28 18:50:30', b'1', 153);
INSERT INTO `system_oauth2_refresh_token` VALUES (1343, 129, 'a1a311fa33174f00bd5bc50ea5bdae2d', 2, 'default', NULL, '2024-02-29 06:50:45', NULL, '2024-02-28 18:50:45', NULL, '2024-02-28 18:51:16', b'1', 153);
INSERT INTO `system_oauth2_refresh_token` VALUES (1344, 1, '9296dae9a8064a9cb2fda82c6a9080d7', 2, 'default', NULL, '2024-02-29 06:51:18', NULL, '2024-02-28 18:51:18', NULL, '2024-02-28 18:51:56', b'1', 1);
INSERT INTO `system_oauth2_refresh_token` VALUES (1345, 129, 'a43e2060b0c54fc09588ea7a1b2e0449', 2, 'default', NULL, '2024-02-29 06:52:08', NULL, '2024-02-28 18:52:08', NULL, '2024-02-28 18:55:46', b'1', 153);
INSERT INTO `system_oauth2_refresh_token` VALUES (1346, 129, 'd7acfc708e154ca69b6ae1b8996fc633', 2, 'default', NULL, '2024-02-29 06:55:57', NULL, '2024-02-28 18:55:57', NULL, '2024-02-28 18:56:52', b'1', 153);
INSERT INTO `system_oauth2_refresh_token` VALUES (1347, 1, 'e83c7f0bce104d55ba9474c592386280', 2, 'default', NULL, '2024-02-29 06:56:54', NULL, '2024-02-28 18:56:54', NULL, '2024-02-28 18:58:08', b'1', 1);
INSERT INTO `system_oauth2_refresh_token` VALUES (1348, 129, 'b3a447e4cd7b42c4bd996471f4866582', 2, 'default', NULL, '2024-02-29 06:58:18', NULL, '2024-02-28 18:58:18', NULL, '2024-02-29 10:55:25', b'1', 153);
INSERT INTO `system_oauth2_refresh_token` VALUES (1349, 1, '06a65ba6d81148d3b9237a691d91e276', 2, 'default', NULL, '2024-02-29 22:55:43', NULL, '2024-02-29 10:55:43', NULL, '2024-02-29 10:55:55', b'1', 1);
INSERT INTO `system_oauth2_refresh_token` VALUES (1350, 129, 'f1751a593dd849668a04e598243833c1', 2, 'default', NULL, '2024-02-29 22:56:05', NULL, '2024-02-29 10:56:05', NULL, '2024-02-29 10:56:26', b'1', 153);
INSERT INTO `system_oauth2_refresh_token` VALUES (1351, 1, '6519187d129d44b48b533f109df9b48b', 2, 'default', NULL, '2024-03-01 05:07:15', NULL, '2024-02-29 17:07:15', NULL, '2024-02-29 17:07:15', b'0', 1);
INSERT INTO `system_oauth2_refresh_token` VALUES (1352, 1, '39b563fbc3fb43039e8022f44ef41113', 2, 'default', NULL, '2024-03-01 05:10:07', NULL, '2024-02-29 17:10:07', NULL, '2024-02-29 17:10:07', b'0', 1);

-- ----------------------------
-- Table structure for system_operate_log
-- ----------------------------
DROP TABLE IF EXISTS `system_operate_log`;
CREATE TABLE `system_operate_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `trace_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '链路追踪编号',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `user_type` tinyint NOT NULL DEFAULT 0 COMMENT '用户类型',
  `module` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模块标题',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作名',
  `type` bigint NOT NULL DEFAULT 0 COMMENT '操作分类',
  `content` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '操作内容',
  `exts` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '拓展字段',
  `request_method` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '请求方法名',
  `request_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '请求地址',
  `user_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户 IP',
  `user_agent` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '浏览器 UA',
  `java_method` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT 'Java 方法名',
  `java_method_args` varchar(8000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT 'Java 方法的参数',
  `start_time` datetime NOT NULL COMMENT '操作时间',
  `duration` int NOT NULL COMMENT '执行时长',
  `result_code` int NOT NULL DEFAULT 0 COMMENT '结果码',
  `result_msg` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '结果提示',
  `result_data` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '结果数据',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9775 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '操作日志记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_operate_log
-- ----------------------------
INSERT INTO `system_operate_log` VALUES (9706, '', 1, 2, '管理后台 - 菜单', '修改菜单', 3, '', '', 'PUT', '/admin-api/system/menu/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.permission.MenuController.updateMenu(MenuSaveVO)', '{\"updateReqVO\":{\"id\":2159,\"name\":\"Boot 开发文档\",\"permission\":\"\",\"type\":1,\"sort\":1,\"parentId\":0,\"path\":\"https://doc.iocoder.cn/\",\"icon\":\"ep:document\",\"component\":null,\"componentName\":null,\"status\":1,\"visible\":false,\"keepAlive\":true,\"alwaysShow\":false}}', '2024-02-17 12:11:57', 94, 0, '', 'true', '1', '2024-02-17 12:11:57', '1', '2024-02-17 12:11:57', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9707, '', 1, 2, '管理后台 - 菜单', '修改菜单', 3, '', '', 'PUT', '/admin-api/system/menu/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.permission.MenuController.updateMenu(MenuSaveVO)', '{\"updateReqVO\":{\"id\":1254,\"name\":\"作者动态\",\"permission\":\"\",\"type\":1,\"sort\":0,\"parentId\":0,\"path\":\"https://www.iocoder.cn\",\"icon\":\"ep:avatar\",\"component\":null,\"componentName\":null,\"status\":1,\"visible\":false,\"keepAlive\":true,\"alwaysShow\":false}}', '2024-02-17 12:12:05', 65, 0, '', 'true', '1', '2024-02-17 12:12:05', '1', '2024-02-17 12:12:05', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9708, '', 1, 2, '管理后台 - 菜单', '修改菜单', 3, '', '', 'PUT', '/admin-api/system/menu/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.permission.MenuController.updateMenu(MenuSaveVO)', '{\"updateReqVO\":{\"id\":2160,\"name\":\"Cloud 开发文档\",\"permission\":\"\",\"type\":1,\"sort\":2,\"parentId\":0,\"path\":\"https://cloud.iocoder.cn\",\"icon\":\"ep:document-copy\",\"component\":null,\"componentName\":null,\"status\":1,\"visible\":false,\"keepAlive\":true,\"alwaysShow\":false}}', '2024-02-17 12:12:12', 64, 0, '', 'true', '1', '2024-02-17 12:12:13', '1', '2024-02-17 12:12:13', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9709, '', 1, 2, '管理后台 - 菜单', '修改菜单', 3, '', '', 'PUT', '/admin-api/system/menu/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.permission.MenuController.updateMenu(MenuSaveVO)', '{\"updateReqVO\":{\"id\":1117,\"name\":\"支付管理\",\"permission\":\"\",\"type\":1,\"sort\":30,\"parentId\":0,\"path\":\"/pay\",\"icon\":\"money\",\"component\":null,\"componentName\":null,\"status\":1,\"visible\":false,\"keepAlive\":true,\"alwaysShow\":false}}', '2024-02-17 12:12:23', 68, 0, '', 'true', '1', '2024-02-17 12:12:23', '1', '2024-02-17 12:12:23', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9710, '', 1, 2, '管理后台 - 菜单', '修改菜单', 3, '', '', 'PUT', '/admin-api/system/menu/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.permission.MenuController.updateMenu(MenuSaveVO)', '{\"updateReqVO\":{\"id\":1281,\"name\":\"报表管理\",\"permission\":\"\",\"type\":1,\"sort\":40,\"parentId\":0,\"path\":\"/report\",\"icon\":\"chart\",\"component\":null,\"componentName\":null,\"status\":1,\"visible\":false,\"keepAlive\":true,\"alwaysShow\":false}}', '2024-02-17 12:12:32', 67, 0, '', 'true', '1', '2024-02-17 12:12:32', '1', '2024-02-17 12:12:32', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9711, '', 1, 2, '管理后台 - 菜单', '修改菜单', 3, '', '', 'PUT', '/admin-api/system/menu/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.permission.MenuController.updateMenu(MenuSaveVO)', '{\"updateReqVO\":{\"id\":1185,\"name\":\"工作流程\",\"permission\":\"\",\"type\":1,\"sort\":50,\"parentId\":0,\"path\":\"/bpm\",\"icon\":\"tool\",\"component\":null,\"componentName\":null,\"status\":1,\"visible\":false,\"keepAlive\":true,\"alwaysShow\":false}}', '2024-02-17 12:12:39', 64, 0, '', 'true', '1', '2024-02-17 12:12:39', '1', '2024-02-17 12:12:39', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9712, '', 1, 2, '管理后台 - 菜单', '修改菜单', 3, '', '', 'PUT', '/admin-api/system/menu/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.permission.MenuController.updateMenu(MenuSaveVO)', '{\"updateReqVO\":{\"id\":2262,\"name\":\"会员中心\",\"permission\":\"\",\"type\":1,\"sort\":55,\"parentId\":0,\"path\":\"/member\",\"icon\":\"ep:bicycle\",\"component\":null,\"componentName\":null,\"status\":1,\"visible\":false,\"keepAlive\":true,\"alwaysShow\":false}}', '2024-02-17 12:12:46', 61, 0, '', 'true', '1', '2024-02-17 12:12:46', '1', '2024-02-17 12:12:46', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9713, '', 1, 2, '管理后台 - 菜单', '修改菜单', 3, '', '', 'PUT', '/admin-api/system/menu/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.permission.MenuController.updateMenu(MenuSaveVO)', '{\"updateReqVO\":{\"id\":2362,\"name\":\"商城系统\",\"permission\":\"\",\"type\":1,\"sort\":59,\"parentId\":0,\"path\":\"/mall\",\"icon\":\"ep:shop\",\"component\":\"\",\"componentName\":\"\",\"status\":1,\"visible\":false,\"keepAlive\":true,\"alwaysShow\":false}}', '2024-02-17 12:12:52', 91, 0, '', 'true', '1', '2024-02-17 12:12:53', '1', '2024-02-17 12:12:53', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9714, '', 1, 2, '管理后台 - 菜单', '修改菜单', 3, '', '', 'PUT', '/admin-api/system/menu/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.permission.MenuController.updateMenu(MenuSaveVO)', '{\"updateReqVO\":{\"id\":2084,\"name\":\"公众号管理\",\"permission\":\"\",\"type\":1,\"sort\":100,\"parentId\":0,\"path\":\"/mp\",\"icon\":\"wechat\",\"component\":null,\"componentName\":null,\"status\":1,\"visible\":false,\"keepAlive\":true,\"alwaysShow\":false}}', '2024-02-17 12:13:00', 63, 0, '', 'true', '1', '2024-02-17 12:13:00', '1', '2024-02-17 12:13:00', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9715, '', 1, 2, '管理后台 - 菜单', '修改菜单', 3, '', '', 'PUT', '/admin-api/system/menu/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.permission.MenuController.updateMenu(MenuSaveVO)', '{\"updateReqVO\":{\"id\":2397,\"name\":\"客户管理系统\",\"permission\":\"\",\"type\":1,\"sort\":200,\"parentId\":0,\"path\":\"/crm\",\"icon\":\"ep:avatar\",\"component\":\"\",\"componentName\":\"\",\"status\":1,\"visible\":false,\"keepAlive\":true,\"alwaysShow\":false}}', '2024-02-17 12:13:07', 62, 0, '', 'true', '1', '2024-02-17 12:13:07', '1', '2024-02-17 12:13:07', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9716, '', 1, 2, '管理后台 - 数据源配置', '创建数据源配置', 2, '', '', 'POST', '/admin-api/infra/data-source-config/create', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.infra.controller.admin.db.DataSourceConfigController.createDataSourceConfig(DataSourceConfigSaveReqVO)', '{\"createReqVO\":{\"id\":null,\"name\":\"futech-bi-1\",\"url\":\"jdbc:mysql://127.0.0.1:3306/futech-bi-1?allowMultiQueries=true&useUnicode=true&useSSL=false&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&autoReconnect=true&nullCatalogMeansCurrent=true\",\"username\":\"root\",\"password\":\"123456\"}}', '2024-02-17 13:35:23', 849, 0, '', '14', '1', '2024-02-17 13:35:24', '1', '2024-02-17 13:35:24', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9717, '', 1, 2, '管理后台 - 菜单', '修改菜单', 3, '', '', 'PUT', '/admin-api/system/menu/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.permission.MenuController.updateMenu(MenuSaveVO)', '{\"updateReqVO\":{\"id\":115,\"name\":\"代码生成\",\"permission\":\"infra:codegen:query\",\"type\":2,\"sort\":1,\"parentId\":2,\"path\":\"codegen\",\"icon\":\"code\",\"component\":\"infra/codegen/index\",\"componentName\":\"InfraCodegen\",\"status\":1,\"visible\":false,\"keepAlive\":false,\"alwaysShow\":false}}', '2024-02-19 10:50:04', 97, 0, '', 'true', '1', '2024-02-19 10:50:05', '1', '2024-02-19 10:50:05', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9718, '', 1, 2, '管理后台 - 菜单', '修改菜单', 3, '', '', 'PUT', '/admin-api/system/menu/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.permission.MenuController.updateMenu(MenuSaveVO)', '{\"updateReqVO\":{\"id\":1070,\"name\":\"代码生成案例\",\"permission\":\"\",\"type\":1,\"sort\":1,\"parentId\":2,\"path\":\"demo\",\"icon\":\"ep:aim\",\"component\":\"infra/testDemo/index\",\"componentName\":null,\"status\":1,\"visible\":false,\"keepAlive\":true,\"alwaysShow\":false}}', '2024-02-19 10:50:16', 85, 0, '', 'true', '1', '2024-02-19 10:50:16', '1', '2024-02-19 10:50:16', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9719, '', 1, 2, '管理后台 - 菜单', '修改菜单', 3, '', '', 'PUT', '/admin-api/system/menu/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.permission.MenuController.updateMenu(MenuSaveVO)', '{\"updateReqVO\":{\"id\":1255,\"name\":\"数据源配置\",\"permission\":\"\",\"type\":2,\"sort\":1,\"parentId\":2,\"path\":\"data-source-config\",\"icon\":\"rate\",\"component\":\"infra/dataSourceConfig/index\",\"componentName\":\"InfraDataSourceConfig\",\"status\":1,\"visible\":false,\"keepAlive\":false,\"alwaysShow\":false}}', '2024-02-19 10:50:25', 73, 0, '', 'true', '1', '2024-02-19 10:50:25', '1', '2024-02-19 10:50:25', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9720, '', 1, 2, '管理后台 - 菜单', '修改菜单', 3, '', '', 'PUT', '/admin-api/system/menu/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.permission.MenuController.updateMenu(MenuSaveVO)', '{\"updateReqVO\":{\"id\":114,\"name\":\"表单构建\",\"permission\":\"infra:build:list\",\"type\":2,\"sort\":2,\"parentId\":2,\"path\":\"build\",\"icon\":\"build\",\"component\":\"infra/build/index\",\"componentName\":\"InfraBuild\",\"status\":1,\"visible\":false,\"keepAlive\":false,\"alwaysShow\":false}}', '2024-02-19 10:50:36', 73, 0, '', 'true', '1', '2024-02-19 10:50:36', '1', '2024-02-19 10:50:36', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9721, '', 1, 2, '管理后台 - 菜单', '修改菜单', 3, '', '', 'PUT', '/admin-api/system/menu/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.permission.MenuController.updateMenu(MenuSaveVO)', '{\"updateReqVO\":{\"id\":116,\"name\":\"系统接口\",\"permission\":\"infra:swagger:list\",\"type\":2,\"sort\":3,\"parentId\":2,\"path\":\"swagger\",\"icon\":\"swagger\",\"component\":\"infra/swagger/index\",\"componentName\":\"InfraSwagger\",\"status\":1,\"visible\":false,\"keepAlive\":false,\"alwaysShow\":false}}', '2024-02-19 10:50:43', 93, 0, '', 'true', '1', '2024-02-19 10:50:43', '1', '2024-02-19 10:50:43', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9722, '', 1, 2, '管理后台 - 菜单', '修改菜单', 3, '', '', 'PUT', '/admin-api/system/menu/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.permission.MenuController.updateMenu(MenuSaveVO)', '{\"updateReqVO\":{\"id\":1076,\"name\":\"数据库文档\",\"permission\":\"\",\"type\":2,\"sort\":4,\"parentId\":2,\"path\":\"db-doc\",\"icon\":\"table\",\"component\":\"infra/dbDoc/index\",\"componentName\":\"InfraDBDoc\",\"status\":1,\"visible\":false,\"keepAlive\":false,\"alwaysShow\":false}}', '2024-02-19 10:50:52', 101, 0, '', 'true', '1', '2024-02-19 10:50:52', '1', '2024-02-19 10:50:52', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9723, '', 1, 2, '管理后台 - 菜单', '修改菜单', 3, '', '', 'PUT', '/admin-api/system/menu/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.permission.MenuController.updateMenu(MenuSaveVO)', '{\"updateReqVO\":{\"id\":1243,\"name\":\"文件管理\",\"permission\":\"\",\"type\":2,\"sort\":5,\"parentId\":2,\"path\":\"file\",\"icon\":\"download\",\"component\":null,\"componentName\":\"\",\"status\":1,\"visible\":false,\"keepAlive\":false,\"alwaysShow\":false}}', '2024-02-19 10:51:05', 89, 0, '', 'true', '1', '2024-02-19 10:51:05', '1', '2024-02-19 10:51:05', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9724, '', 1, 2, '管理后台 - 菜单', '修改菜单', 3, '', '', 'PUT', '/admin-api/system/menu/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.permission.MenuController.updateMenu(MenuSaveVO)', '{\"updateReqVO\":{\"id\":2525,\"name\":\"WebSocket 测试\",\"permission\":\"\",\"type\":2,\"sort\":7,\"parentId\":2,\"path\":\"websocket\",\"icon\":\"ep:connection\",\"component\":\"infra/webSocket/index\",\"componentName\":\"InfraWebSocket\",\"status\":1,\"visible\":false,\"keepAlive\":false,\"alwaysShow\":false}}', '2024-02-19 10:51:14', 72, 0, '', 'true', '1', '2024-02-19 10:51:14', '1', '2024-02-19 10:51:14', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9725, '', 1, 2, '管理后台 - 菜单', '修改菜单', 3, '', '', 'PUT', '/admin-api/system/menu/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.permission.MenuController.updateMenu(MenuSaveVO)', '{\"updateReqVO\":{\"id\":111,\"name\":\"MySQL 监控\",\"permission\":\"\",\"type\":2,\"sort\":9,\"parentId\":2,\"path\":\"druid\",\"icon\":\"druid\",\"component\":\"infra/druid/index\",\"componentName\":\"InfraDruid\",\"status\":1,\"visible\":false,\"keepAlive\":false,\"alwaysShow\":false}}', '2024-02-19 10:51:25', 71, 0, '', 'true', '1', '2024-02-19 10:51:25', '1', '2024-02-19 10:51:25', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9726, '', 1, 2, '管理后台 - 菜单', '修改菜单', 3, '', '', 'PUT', '/admin-api/system/menu/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.permission.MenuController.updateMenu(MenuSaveVO)', '{\"updateReqVO\":{\"id\":113,\"name\":\"Redis 监控\",\"permission\":\"\",\"type\":2,\"sort\":10,\"parentId\":2,\"path\":\"redis\",\"icon\":\"redis\",\"component\":\"infra/redis/index\",\"componentName\":\"InfraRedis\",\"status\":1,\"visible\":false,\"keepAlive\":false,\"alwaysShow\":false}}', '2024-02-19 10:51:32', 74, 0, '', 'true', '1', '2024-02-19 10:51:32', '1', '2024-02-19 10:51:32', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9727, '', 1, 2, '管理后台 - 菜单', '修改菜单', 3, '', '', 'PUT', '/admin-api/system/menu/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.permission.MenuController.updateMenu(MenuSaveVO)', '{\"updateReqVO\":{\"id\":112,\"name\":\"Java 监控\",\"permission\":\"\",\"type\":2,\"sort\":11,\"parentId\":2,\"path\":\"admin-server\",\"icon\":\"server\",\"component\":\"infra/server/index\",\"componentName\":\"InfraAdminServer\",\"status\":1,\"visible\":false,\"keepAlive\":false,\"alwaysShow\":false}}', '2024-02-19 10:51:40', 68, 0, '', 'true', '1', '2024-02-19 10:51:40', '1', '2024-02-19 10:51:40', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9728, '', 1, 2, '管理后台 - 菜单', '修改菜单', 3, '', '', 'PUT', '/admin-api/system/menu/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.permission.MenuController.updateMenu(MenuSaveVO)', '{\"updateReqVO\":{\"id\":1077,\"name\":\"监控平台\",\"permission\":\"\",\"type\":2,\"sort\":13,\"parentId\":2,\"path\":\"skywalking\",\"icon\":\"eye-open\",\"component\":\"infra/skywalking/index\",\"componentName\":\"InfraSkyWalking\",\"status\":1,\"visible\":false,\"keepAlive\":false,\"alwaysShow\":false}}', '2024-02-19 10:51:48', 72, 0, '', 'true', '1', '2024-02-19 10:51:48', '1', '2024-02-19 10:51:48', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9729, '', 1, 2, '管理后台 - 菜单', '修改菜单', 3, '', '', 'PUT', '/admin-api/system/menu/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.permission.MenuController.updateMenu(MenuSaveVO)', '{\"updateReqVO\":{\"id\":110,\"name\":\"定时任务\",\"permission\":\"\",\"type\":2,\"sort\":12,\"parentId\":2,\"path\":\"job\",\"icon\":\"job\",\"component\":\"infra/job/index\",\"componentName\":\"InfraJob\",\"status\":1,\"visible\":false,\"keepAlive\":false,\"alwaysShow\":false}}', '2024-02-19 10:51:57', 75, 0, '', 'true', '1', '2024-02-19 10:51:57', '1', '2024-02-19 10:51:57', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9730, '', 1, 2, '管理后台 - 菜单', '修改菜单', 3, '', '', 'PUT', '/admin-api/system/menu/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.permission.MenuController.updateMenu(MenuSaveVO)', '{\"updateReqVO\":{\"id\":1083,\"name\":\"API 日志\",\"permission\":\"\",\"type\":2,\"sort\":8,\"parentId\":2,\"path\":\"log\",\"icon\":\"log\",\"component\":null,\"componentName\":null,\"status\":1,\"visible\":false,\"keepAlive\":false,\"alwaysShow\":false}}', '2024-02-19 10:52:10', 70, 0, '', 'true', '1', '2024-02-19 10:52:10', '1', '2024-02-19 10:52:10', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9731, '', 1, 2, '管理后台 - 菜单', '修改菜单', 3, '', '', 'PUT', '/admin-api/system/menu/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.permission.MenuController.updateMenu(MenuSaveVO)', '{\"updateReqVO\":{\"id\":2083,\"name\":\"地区管理\",\"permission\":\"\",\"type\":2,\"sort\":14,\"parentId\":1,\"path\":\"area\",\"icon\":\"row\",\"component\":\"system/area/index\",\"componentName\":\"SystemArea\",\"status\":1,\"visible\":false,\"keepAlive\":false,\"alwaysShow\":false}}', '2024-02-19 10:52:41', 70, 0, '', 'true', '1', '2024-02-19 10:52:41', '1', '2024-02-19 10:52:41', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9732, '', 1, 2, '管理后台 - 菜单', '修改菜单', 3, '', '', 'PUT', '/admin-api/system/menu/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.permission.MenuController.updateMenu(MenuSaveVO)', '{\"updateReqVO\":{\"id\":1247,\"name\":\"敏感词管理\",\"permission\":\"\",\"type\":2,\"sort\":13,\"parentId\":1,\"path\":\"sensitive-word\",\"icon\":\"education\",\"component\":\"system/sensitiveWord/index\",\"componentName\":\"SystemSensitiveWord\",\"status\":1,\"visible\":false,\"keepAlive\":false,\"alwaysShow\":false}}', '2024-02-19 10:52:53', 70, 0, '', 'true', '1', '2024-02-19 10:52:53', '1', '2024-02-19 10:52:53', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9733, '', 1, 2, '管理后台 - 菜单', '修改菜单', 3, '', '', 'PUT', '/admin-api/system/menu/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.permission.MenuController.updateMenu(MenuSaveVO)', '{\"updateReqVO\":{\"id\":1093,\"name\":\"短信管理\",\"permission\":\"\",\"type\":1,\"sort\":11,\"parentId\":1,\"path\":\"sms\",\"icon\":\"validCode\",\"component\":null,\"componentName\":null,\"status\":1,\"visible\":false,\"keepAlive\":true,\"alwaysShow\":false}}', '2024-02-19 10:53:05', 79, 0, '', 'true', '1', '2024-02-19 10:53:05', '1', '2024-02-19 10:53:05', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9734, '', 1, 2, '管理后台 - 菜单', '修改菜单', 3, '', '', 'PUT', '/admin-api/system/menu/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.permission.MenuController.updateMenu(MenuSaveVO)', '{\"updateReqVO\":{\"id\":2130,\"name\":\"邮箱管理\",\"permission\":\"\",\"type\":2,\"sort\":11,\"parentId\":1,\"path\":\"mail\",\"icon\":\"email\",\"component\":null,\"componentName\":null,\"status\":1,\"visible\":false,\"keepAlive\":false,\"alwaysShow\":false}}', '2024-02-19 10:53:13', 70, 0, '', 'true', '1', '2024-02-19 10:53:13', '1', '2024-02-19 10:53:13', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9735, '', 1, 2, '管理后台 - 菜单', '修改菜单', 3, '', '', 'PUT', '/admin-api/system/menu/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.permission.MenuController.updateMenu(MenuSaveVO)', '{\"updateReqVO\":{\"id\":2144,\"name\":\"站内信管理\",\"permission\":\"\",\"type\":1,\"sort\":11,\"parentId\":1,\"path\":\"notify\",\"icon\":\"message\",\"component\":null,\"componentName\":null,\"status\":1,\"visible\":false,\"keepAlive\":true,\"alwaysShow\":false}}', '2024-02-19 10:53:20', 75, 0, '', 'true', '1', '2024-02-19 10:53:20', '1', '2024-02-19 10:53:20', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9736, '', 1, 2, '管理后台 - 菜单', '修改菜单', 3, '', '', 'PUT', '/admin-api/system/menu/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.permission.MenuController.updateMenu(MenuSaveVO)', '{\"updateReqVO\":{\"id\":2447,\"name\":\"三方登录\",\"permission\":\"\",\"type\":1,\"sort\":10,\"parentId\":1,\"path\":\"social\",\"icon\":\"fa:500px\",\"component\":\"\",\"componentName\":\"\",\"status\":1,\"visible\":false,\"keepAlive\":true,\"alwaysShow\":false}}', '2024-02-19 10:53:27', 78, 0, '', 'true', '1', '2024-02-19 10:53:27', '1', '2024-02-19 10:53:27', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9737, '', 1, 2, '管理后台 - 菜单', '修改菜单', 3, '', '', 'PUT', '/admin-api/system/menu/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.permission.MenuController.updateMenu(MenuSaveVO)', '{\"updateReqVO\":{\"id\":1261,\"name\":\"OAuth 2.0\",\"permission\":\"\",\"type\":1,\"sort\":10,\"parentId\":1,\"path\":\"oauth2\",\"icon\":\"people\",\"component\":null,\"componentName\":null,\"status\":1,\"visible\":false,\"keepAlive\":true,\"alwaysShow\":false}}', '2024-02-19 10:53:54', 73, 0, '', 'true', '1', '2024-02-19 10:53:54', '1', '2024-02-19 10:53:54', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9738, '', 1, 2, '管理后台 - 菜单', '修改菜单', 3, '', '', 'PUT', '/admin-api/system/menu/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.permission.MenuController.updateMenu(MenuSaveVO)', '{\"updateReqVO\":{\"id\":107,\"name\":\"通知公告\",\"permission\":\"\",\"type\":2,\"sort\":8,\"parentId\":1,\"path\":\"notice\",\"icon\":\"message\",\"component\":\"system/notice/index\",\"componentName\":\"SystemNotice\",\"status\":1,\"visible\":false,\"keepAlive\":false,\"alwaysShow\":false}}', '2024-02-19 10:56:09', 126, 0, '', 'true', '1', '2024-02-19 10:56:09', '1', '2024-02-19 10:56:09', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9739, '', 1, 2, '管理后台 - 菜单', '修改菜单', 3, '', '', 'PUT', '/admin-api/system/menu/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.permission.MenuController.updateMenu(MenuSaveVO)', '{\"updateReqVO\":{\"id\":105,\"name\":\"字典管理\",\"permission\":\"\",\"type\":2,\"sort\":6,\"parentId\":1,\"path\":\"dict\",\"icon\":\"dict\",\"component\":\"system/dict/index\",\"componentName\":\"SystemDictType\",\"status\":1,\"visible\":false,\"keepAlive\":false,\"alwaysShow\":false}}', '2024-02-19 10:56:21', 73, 0, '', 'true', '1', '2024-02-19 10:56:21', '1', '2024-02-19 10:56:21', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9740, '', 1, 2, '管理后台 - 菜单', '修改菜单', 3, '', '', 'PUT', '/admin-api/system/menu/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.permission.MenuController.updateMenu(MenuSaveVO)', '{\"updateReqVO\":{\"id\":1110,\"name\":\"错误码管理\",\"permission\":\"\",\"type\":2,\"sort\":12,\"parentId\":1,\"path\":\"error-code\",\"icon\":\"code\",\"component\":\"system/errorCode/index\",\"componentName\":\"SystemErrorCode\",\"status\":1,\"visible\":false,\"keepAlive\":false,\"alwaysShow\":false}}', '2024-02-19 10:57:43', 74, 0, '', 'true', '1', '2024-02-19 10:57:43', '1', '2024-02-19 10:57:43', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9741, '', 110, 2, '管理后台 - 用户', '新增用户', 2, '', '', 'POST', '/admin-api/system/user/create', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.user.UserController.createUser(UserSaveReqVO)', '{\"reqVO\":{\"id\":null,\"username\":\"meng\",\"nickname\":\"meng\",\"remark\":\"\",\"deptId\":110,\"postIds\":[],\"email\":\"\",\"mobile\":\"\",\"sex\":null,\"avatar\":null,\"password\":\"admin\"}}', '2024-02-19 11:21:05', 95, 0, '', '128', '110', '2024-02-19 11:21:05', '110', '2024-02-19 11:21:05', b'0', 121);
INSERT INTO `system_operate_log` VALUES (9742, '', 110, 2, '管理后台 - 租户套餐', '创建租户套餐', 2, '', '', 'POST', '/admin-api/system/tenant-package/create', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.tenant.TenantPackageController.createTenantPackage(TenantPackageSaveReqVO)', '{\"createReqVO\":{\"id\":null,\"name\":\"meng套餐\",\"status\":0,\"remark\":null,\"menuIds\":[1,100,101,102,1063,103,1064,1001,1065,1002,1003,1004,1005,1006,1007,1008,1009,1010,1011,1012,1013,1014,1015,1016,1017,1018,1019,1020]}}', '2024-02-19 11:23:46', 38, 0, '', '112', '110', '2024-02-19 11:23:46', '110', '2024-02-19 11:23:46', b'0', 121);
INSERT INTO `system_operate_log` VALUES (9743, '', 110, 2, '管理后台 - 租户', '创建租户', 2, '', '', 'POST', '/admin-api/system/tenant/create', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.tenant.TenantController.createTenant(TenantSaveReqVO)', '{\"createReqVO\":{\"id\":null,\"name\":\"meng租户\",\"contactName\":\"meng\",\"contactMobile\":null,\"status\":0,\"website\":\"www.meng.com\",\"packageId\":112,\"expireTime\":1929970800000,\"accountCount\":2,\"username\":\"meng\",\"password\":\"meng\"}}', '2024-02-19 11:27:03', 250, 0, '', '153', '110', '2024-02-19 11:27:03', '110', '2024-02-19 11:27:03', b'0', 121);
INSERT INTO `system_operate_log` VALUES (9744, '', 110, 2, '管理后台 - 租户', '更新租户', 3, '', '', 'PUT', '/admin-api/system/tenant/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.tenant.TenantController.updateTenant(TenantSaveReqVO)', '{\"updateReqVO\":{\"id\":153,\"name\":\"meng租户\",\"contactName\":\"meng\",\"contactMobile\":null,\"status\":0,\"website\":\"www.meng.com\",\"packageId\":112,\"expireTime\":1929970800000,\"accountCount\":2,\"username\":null,\"password\":null}}', '2024-02-19 11:27:13', 72, 0, '', 'true', '110', '2024-02-19 11:27:13', '110', '2024-02-19 11:27:13', b'0', 121);
INSERT INTO `system_operate_log` VALUES (9745, '', 129, 2, '管理后台 - 部门', '创建部门', 2, '', '', 'POST', '/admin-api/system/dept/create', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.dept.DeptController.createDept(DeptSaveReqVO)', '{\"createReqVO\":{\"id\":null,\"name\":\"meng部门1\",\"parentId\":0,\"sort\":1,\"leaderUserId\":129,\"phone\":null,\"email\":null,\"status\":0}}', '2024-02-19 11:31:59', 62, 0, '', '114', '129', '2024-02-19 11:31:59', '129', '2024-02-19 11:31:59', b'0', 153);
INSERT INTO `system_operate_log` VALUES (9746, '', 129, 2, '管理后台 - 部门', '创建部门', 2, '', '', 'POST', '/admin-api/system/dept/create', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.dept.DeptController.createDept(DeptSaveReqVO)', '{\"createReqVO\":{\"id\":null,\"name\":\"meng部门2\",\"parentId\":114,\"sort\":2,\"leaderUserId\":null,\"phone\":null,\"email\":null,\"status\":0}}', '2024-02-19 11:32:17', 75, 0, '', '115', '129', '2024-02-19 11:32:18', '129', '2024-02-19 11:32:18', b'0', 153);
INSERT INTO `system_operate_log` VALUES (9747, '', 129, 2, '管理后台 - 部门', '创建部门', 2, '', '', 'POST', '/admin-api/system/dept/create', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.dept.DeptController.createDept(DeptSaveReqVO)', '{\"createReqVO\":{\"id\":null,\"name\":\"meng部门2-2\",\"parentId\":114,\"sort\":2,\"leaderUserId\":null,\"phone\":null,\"email\":null,\"status\":0}}', '2024-02-19 11:32:37', 74, 0, '', '116', '129', '2024-02-19 11:32:37', '129', '2024-02-19 11:32:37', b'0', 153);
INSERT INTO `system_operate_log` VALUES (9748, '', 1, 2, '管理后台 - 用户个人中心', '上传用户个人头像', 2, '', '', 'POST', '/admin-api/system/user/profile/update-avatar', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.user.UserProfileController.updateUserAvatar(MultipartFile)', '{\"file\":\"[ignore]\"}', '2024-02-28 18:05:02', 102, 500, 'ServiceUnavailable: [503] during [POST] to [http://infra-server/rpc-api/infra/file/create] [FileApi#createFile(FileCreateReqDTO)]: [Load balancer does not contain an instance for the service infra-server]', 'null', '1', '2024-02-28 18:05:02', '1', '2024-02-28 18:05:02', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9749, '', 1, 2, '管理后台 - 用户个人中心', '修改用户个人信息', 3, '', '', 'PUT', '/admin-api/system/user/profile/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.user.UserProfileController.updateUserProfile(UserProfileUpdateReqVO)', '{\"reqVO\":{\"nickname\":\"fu-admin\",\"email\":\"xiaoming.meng@futech.co.jp\",\"mobile\":\"15840488511\",\"sex\":1}}', '2024-02-28 18:06:28', 108, 0, '', 'true', '1', '2024-02-28 18:06:28', '1', '2024-02-28 18:06:28', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9750, '', 1, 2, '管理后台 - 用户个人中心', '上传用户个人头像', 2, '', '', 'POST', '/admin-api/system/user/profile/update-avatar', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.user.UserProfileController.updateUserAvatar(MultipartFile)', '{\"file\":\"[ignore]\"}', '2024-02-28 18:06:52', 34, 500, 'ServiceUnavailable: [503] during [POST] to [http://infra-server/rpc-api/infra/file/create] [FileApi#createFile(FileCreateReqDTO)]: [Load balancer does not contain an instance for the service infra-server]', 'null', '1', '2024-02-28 18:06:52', '1', '2024-02-28 18:06:52', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9751, '', 1, 2, '管理后台 - 用户个人中心', '上传用户个人头像', 2, '', '', 'POST', '/admin-api/system/user/profile/update-avatar', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.user.UserProfileController.updateUserAvatar(MultipartFile)', '{\"file\":\"[ignore]\"}', '2024-02-28 18:19:24', 35, 500, 'ServiceUnavailable: [503] during [POST] to [http://infra-server/rpc-api/infra/file/create] [FileApi#createFile(FileCreateReqDTO)]: [Load balancer does not contain an instance for the service infra-server]', 'null', '1', '2024-02-28 18:19:24', '1', '2024-02-28 18:19:24', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9752, '', 1, 2, '管理后台 - 用户个人中心', '修改用户个人信息', 3, '', '', 'PUT', '/admin-api/system/user/profile/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.user.UserProfileController.updateUserProfile(UserProfileUpdateReqVO)', '{\"reqVO\":{\"nickname\":\"fu-admin\",\"email\":\"xiaoming.meng@futech.co.jp\",\"mobile\":\"15840488511\",\"sex\":1}}', '2024-02-28 18:19:32', 117, 0, '', 'true', '1', '2024-02-28 18:19:32', '1', '2024-02-28 18:19:32', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9753, '', 1, 2, '管理后台 - 用户个人中心', '上传用户个人头像', 2, '', '', 'POST', '/admin-api/system/user/profile/update-avatar', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.user.UserProfileController.updateUserAvatar(MultipartFile)', '{\"file\":\"[ignore]\"}', '2024-02-28 18:21:48', 2642, 500, 'ServiceException: 系统异常', 'null', '1', '2024-02-28 18:21:51', '1', '2024-02-28 18:21:51', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9754, '', 1, 2, '管理后台 - 用户个人中心', '上传用户个人头像', 2, '', '', 'POST', '/admin-api/system/user/profile/update-avatar', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.user.UserProfileController.updateUserAvatar(MultipartFile)', '{\"file\":\"[ignore]\"}', '2024-02-28 18:22:10', 969, 500, 'ServiceException: 系统异常', 'null', '1', '2024-02-28 18:22:11', '1', '2024-02-28 18:22:11', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9755, '', 1, 2, '管理后台 - 用户个人中心', '修改用户个人信息', 3, '', '', 'PUT', '/admin-api/system/user/profile/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.user.UserProfileController.updateUserProfile(UserProfileUpdateReqVO)', '{\"reqVO\":{\"nickname\":\"fu-admin\",\"email\":\"xiaoming.meng@futech.co.jp\",\"mobile\":\"15840488511\",\"sex\":1}}', '2024-02-28 18:22:15', 133, 0, '', 'true', '1', '2024-02-28 18:22:15', '1', '2024-02-28 18:22:15', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9756, '', 1, 2, '管理后台 - 部门', '更新部门', 3, '', '', 'PUT', '/admin-api/system/dept/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.dept.DeptController.updateDept(DeptSaveReqVO)', '{\"updateReqVO\":{\"id\":100,\"name\":\"futech.co\",\"parentId\":0,\"sort\":0,\"leaderUserId\":1,\"phone\":\"15888888888\",\"email\":\"\",\"status\":0}}', '2024-02-28 18:24:22', 119, 0, '', 'true', '1', '2024-02-28 18:24:22', '1', '2024-02-28 18:24:22', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9757, '', 1, 2, '管理后台 - 部门', '更新部门', 3, '', '', 'PUT', '/admin-api/system/dept/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.dept.DeptController.updateDept(DeptSaveReqVO)', '{\"updateReqVO\":{\"id\":101,\"name\":\"日本总公司\",\"parentId\":100,\"sort\":1,\"leaderUserId\":104,\"phone\":\"15888888888\",\"email\":\"ry@qq.com\",\"status\":0}}', '2024-02-28 18:24:34', 114, 0, '', 'true', '1', '2024-02-28 18:24:34', '1', '2024-02-28 18:24:34', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9758, '', 1, 2, '管理后台 - 部门', '更新部门', 3, '', '', 'PUT', '/admin-api/system/dept/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.dept.DeptController.updateDept(DeptSaveReqVO)', '{\"updateReqVO\":{\"id\":102,\"name\":\"菲律宾分公司\",\"parentId\":100,\"sort\":2,\"leaderUserId\":null,\"phone\":\"15888888888\",\"email\":\"ry@qq.com\",\"status\":0}}', '2024-02-28 18:24:47', 97, 0, '', 'true', '1', '2024-02-28 18:24:47', '1', '2024-02-28 18:24:47', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9759, '', 1, 2, '管理后台 - 用户个人中心', '上传用户个人头像', 2, '', '', 'POST', '/admin-api/system/user/profile/update-avatar', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.user.UserProfileController.updateUserAvatar(MultipartFile)', '{\"file\":\"[ignore]\"}', '2024-02-28 18:32:09', 983, 500, 'ServiceException: 系统异常', 'null', '1', '2024-02-28 18:32:10', '1', '2024-02-28 18:32:10', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9760, '', 1, 2, '管理后台 - 用户个人中心', '上传用户个人头像', 2, '', '', 'POST', '/admin-api/system/user/profile/update-avatar', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.user.UserProfileController.updateUserAvatar(MultipartFile)', '{\"file\":\"[ignore]\"}', '2024-02-28 18:32:24', 483, 500, 'ServiceException: 系统异常', 'null', '1', '2024-02-28 18:32:25', '1', '2024-02-28 18:32:25', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9761, '', 1, 2, '管理后台 - 用户个人中心', '修改用户个人信息', 3, '', '', 'PUT', '/admin-api/system/user/profile/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.user.UserProfileController.updateUserProfile(UserProfileUpdateReqVO)', '{\"reqVO\":{\"nickname\":\"fu-admin\",\"email\":\"xiaoming.meng@futech.co.jp\",\"mobile\":\"15840488511\",\"sex\":1}}', '2024-02-28 18:33:00', 120, 0, '', 'true', '1', '2024-02-28 18:33:01', '1', '2024-02-28 18:33:01', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9762, '', 1, 2, '管理后台 - 菜单', '修改菜单', 3, '', '', 'PUT', '/admin-api/system/menu/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.permission.MenuController.updateMenu(MenuSaveVO)', '{\"updateReqVO\":{\"id\":2,\"name\":\"基础设施\",\"permission\":\"\",\"type\":1,\"sort\":20,\"parentId\":0,\"path\":\"/infra\",\"icon\":\"monitor\",\"component\":null,\"componentName\":null,\"status\":1,\"visible\":false,\"keepAlive\":true,\"alwaysShow\":false}}', '2024-02-28 18:33:31', 75, 0, '', 'true', '1', '2024-02-28 18:33:31', '1', '2024-02-28 18:33:31', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9763, '', 1, 2, '管理后台 - 租户套餐', '更新租户套餐', 3, '', '', 'PUT', '/admin-api/system/tenant-package/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.tenant.TenantPackageController.updateTenantPackage(TenantPackageSaveReqVO)', '{\"updateReqVO\":{\"id\":112,\"name\":\"高级套餐\",\"status\":0,\"remark\":\"\",\"menuIds\":[1024,1025,1,1040,1042,1043,1045,100,101,102,1063,103,1064,104,1001,1065,1002,1003,1004,108,1005,1006,1007,1008,1009,1010,1011,1012,500,1013,501,1014,1015,1016,1017,1018,1019,1020,1021,1022,1023]}}', '2024-02-28 18:41:44', 147, 0, '', 'true', '1', '2024-02-28 18:41:44', '1', '2024-02-28 18:41:44', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9764, '', 1, 2, '管理后台 - 租户', '更新租户', 3, '', '', 'PUT', '/admin-api/system/tenant/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.tenant.TenantController.updateTenant(TenantSaveReqVO)', '{\"updateReqVO\":{\"id\":153,\"name\":\"google\",\"contactName\":\"meng\",\"contactMobile\":null,\"status\":0,\"website\":\"google.futech.com\",\"packageId\":112,\"expireTime\":1929970800000,\"accountCount\":20,\"username\":null,\"password\":null}}', '2024-02-28 18:42:38', 70, 0, '', 'true', '1', '2024-02-28 18:42:38', '1', '2024-02-28 18:42:38', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9765, '', 1, 2, '管理后台 - 租户', '更新租户', 3, '', '', 'PUT', '/admin-api/system/tenant/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.tenant.TenantController.updateTenant(TenantSaveReqVO)', '{\"updateReqVO\":{\"id\":153,\"name\":\"google\",\"contactName\":\"meng\",\"contactMobile\":null,\"status\":0,\"website\":\"google.futech.com\",\"packageId\":112,\"expireTime\":1929970800000,\"accountCount\":20,\"username\":null,\"password\":null}}', '2024-02-28 18:42:57', 67, 0, '', 'true', '1', '2024-02-28 18:42:57', '1', '2024-02-28 18:42:57', b'0', 1);
INSERT INTO `system_operate_log` VALUES (9766, '', 129, 2, '管理后台 - 用户', '修改用户', 3, '', '', 'PUT', '/admin-api/system/user/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.user.UserController.updateUser(UserSaveReqVO)', '{\"reqVO\":{\"id\":129,\"username\":\"meng\",\"nickname\":\"g-admin\",\"remark\":null,\"deptId\":null,\"postIds\":null,\"email\":\"\",\"mobile\":\"\",\"sex\":0,\"avatar\":\"\",\"password\":null}}', '2024-02-28 18:46:06', 143, 0, '', 'true', '129', '2024-02-28 18:46:06', '129', '2024-02-28 18:46:06', b'0', 153);
INSERT INTO `system_operate_log` VALUES (9767, '', 129, 2, '管理后台 - 用户', '重置用户密码', 3, '', '', 'PUT', '/admin-api/system/user/update-password', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.user.UserController.updateUserPassword(UserUpdatePasswordReqVO)', '{\"reqVO\":{\"id\":129,\"password\":\"gadmin\"}}', '2024-02-28 18:47:58', 76, 0, '', 'true', '129', '2024-02-28 18:47:58', '129', '2024-02-28 18:47:58', b'0', 153);
INSERT INTO `system_operate_log` VALUES (9768, '', 129, 2, '管理后台 - 部门', '更新部门', 3, '', '', 'PUT', '/admin-api/system/dept/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.dept.DeptController.updateDept(DeptSaveReqVO)', '{\"updateReqVO\":{\"id\":114,\"name\":\"google总部\",\"parentId\":0,\"sort\":1,\"leaderUserId\":129,\"phone\":null,\"email\":null,\"status\":0}}', '2024-02-28 18:48:39', 107, 0, '', 'true', '129', '2024-02-28 18:48:39', '129', '2024-02-28 18:48:39', b'0', 153);
INSERT INTO `system_operate_log` VALUES (9769, '', 129, 2, '管理后台 - 部门', '更新部门', 3, '', '', 'PUT', '/admin-api/system/dept/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.dept.DeptController.updateDept(DeptSaveReqVO)', '{\"updateReqVO\":{\"id\":115,\"name\":\"营销部\",\"parentId\":114,\"sort\":2,\"leaderUserId\":null,\"phone\":null,\"email\":null,\"status\":0}}', '2024-02-28 18:48:54', 113, 0, '', 'true', '129', '2024-02-28 18:48:55', '129', '2024-02-28 18:48:55', b'0', 153);
INSERT INTO `system_operate_log` VALUES (9770, '', 129, 2, '管理后台 - 部门', '更新部门', 3, '', '', 'PUT', '/admin-api/system/dept/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.dept.DeptController.updateDept(DeptSaveReqVO)', '{\"updateReqVO\":{\"id\":116,\"name\":\"销售部\",\"parentId\":114,\"sort\":2,\"leaderUserId\":null,\"phone\":null,\"email\":null,\"status\":0}}', '2024-02-28 18:49:01', 88, 0, '', 'true', '129', '2024-02-28 18:49:02', '129', '2024-02-28 18:49:02', b'0', 153);
INSERT INTO `system_operate_log` VALUES (9771, '', 129, 2, '管理后台 - 用户', '修改用户', 3, '', '', 'PUT', '/admin-api/system/user/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.user.UserController.updateUser(UserSaveReqVO)', '{\"reqVO\":{\"id\":129,\"username\":\"gadmin\",\"nickname\":\"google管理员\",\"remark\":null,\"deptId\":114,\"postIds\":null,\"email\":\"\",\"mobile\":\"\",\"sex\":1,\"avatar\":\"\",\"password\":null}}', '2024-02-28 18:50:21', 111, 0, '', 'true', '129', '2024-02-28 18:50:21', '129', '2024-02-28 18:50:21', b'0', 153);
INSERT INTO `system_operate_log` VALUES (9772, '', 129, 2, '管理后台 - 权限', '赋予角色菜单', 2, '', '', 'POST', '/admin-api/system/permission/assign-role-menu', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.permission.PermissionController.assignRoleMenu(PermissionAssignRoleMenuReqVO)', '{\"reqVO\":{\"roleId\":142,\"menuIds\":[1,1040,1042,1043,1045,100,101,102,1063,103,1064,1001,1065,1002,1003,1004,108,1005,1006,1007,1008,1009,1010,1011,1012,500,1013,501,1014,1015,1016,1017,1018,1019,1020]}}', '2024-02-28 18:55:36', 81, 0, '', 'true', '129', '2024-02-28 18:55:36', '129', '2024-02-28 18:55:36', b'0', 153);
INSERT INTO `system_operate_log` VALUES (9773, '', 129, 2, '管理后台 - 权限', '赋予角色菜单', 2, '', '', 'POST', '/admin-api/system/permission/assign-role-menu', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.permission.PermissionController.assignRoleMenu(PermissionAssignRoleMenuReqVO)', '{\"reqVO\":{\"roleId\":142,\"menuIds\":[1024,1,1025,1040,1042,1043,1045,100,101,102,1063,103,1064,104,1001,1065,1002,1003,1004,108,1005,1006,1007,1008,1009,1010,1011,1012,500,1013,501,1014,1015,1016,1017,1018,1019,1020,1021,1022,1023]}}', '2024-02-28 18:55:43', 79, 0, '', 'true', '129', '2024-02-28 18:55:43', '129', '2024-02-28 18:55:43', b'0', 153);
INSERT INTO `system_operate_log` VALUES (9774, '', 1, 2, '管理后台 - 租户套餐', '更新租户套餐', 3, '', '', 'PUT', '/admin-api/system/tenant-package/update', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36', 'CommonResult jp.co.futech.module.system.controller.admin.tenant.TenantPackageController.updateTenantPackage(TenantPackageSaveReqVO)', '{\"updateReqVO\":{\"id\":112,\"name\":\"高级套餐\",\"status\":0,\"remark\":\"\",\"menuIds\":[1024,1025,1,1040,1042,1043,1045,100,101,1063,103,1064,104,1001,1065,1002,1003,1004,108,1005,1006,1007,1008,1009,1010,1011,1012,500,501,1017,1018,1019,1020,1021,1022,1023]}}', '2024-02-28 18:57:15', 108, 0, '', 'true', '1', '2024-02-28 18:57:15', '1', '2024-02-28 18:57:15', b'0', 1);

-- ----------------------------
-- Table structure for system_operate_log_v2
-- ----------------------------
DROP TABLE IF EXISTS `system_operate_log_v2`;
CREATE TABLE `system_operate_log_v2`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `trace_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '链路追踪编号',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `user_type` tinyint NOT NULL DEFAULT 0 COMMENT '用户类型',
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作模块类型',
  `sub_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作名',
  `biz_id` bigint NOT NULL COMMENT '操作数据模块编号',
  `action` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '操作内容',
  `extra` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '拓展字段',
  `request_method` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '请求方法名',
  `request_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '请求地址',
  `user_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户 IP',
  `user_agent` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '浏览器 UA',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8888 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '操作日志记录 V2 版本' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_operate_log_v2
-- ----------------------------

-- ----------------------------
-- Table structure for system_post
-- ----------------------------
DROP TABLE IF EXISTS `system_post`;
CREATE TABLE `system_post`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '岗位编码',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '岗位名称',
  `sort` int NOT NULL COMMENT '显示顺序',
  `status` tinyint NOT NULL COMMENT '状态（0正常 1停用）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '岗位信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_post
-- ----------------------------
INSERT INTO `system_post` VALUES (1, 'ceo', '董事长', 1, 0, '', 'admin', '2021-01-06 17:03:48', '1', '2023-02-11 15:19:04', b'0', 1);
INSERT INTO `system_post` VALUES (2, 'se', '项目经理', 2, 0, '', 'admin', '2021-01-05 17:03:48', '1', '2023-11-15 09:18:20', b'0', 1);
INSERT INTO `system_post` VALUES (4, 'user', '普通员工', 4, 0, '111', 'admin', '2021-01-05 17:03:48', '1', '2023-12-02 10:04:37', b'0', 1);

-- ----------------------------
-- Table structure for system_role
-- ----------------------------
DROP TABLE IF EXISTS `system_role`;
CREATE TABLE `system_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色名称',
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色权限字符串',
  `sort` int NOT NULL COMMENT '显示顺序',
  `data_scope` tinyint NOT NULL DEFAULT 1 COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `data_scope_dept_ids` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '数据范围(指定部门数组)',
  `status` tinyint NOT NULL COMMENT '角色状态（0正常 1停用）',
  `type` tinyint NOT NULL COMMENT '角色类型',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 143 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_role
-- ----------------------------
INSERT INTO `system_role` VALUES (1, '超级管理员', 'super_admin', 1, 1, '', 0, 1, '超级管理员', 'admin', '2021-01-05 17:03:48', '', '2022-02-22 05:08:21', b'0', 1);
INSERT INTO `system_role` VALUES (2, '普通角色', 'common', 2, 2, '', 0, 1, '普通角色', 'admin', '2021-01-05 17:03:48', '', '2022-02-22 05:08:20', b'0', 1);
INSERT INTO `system_role` VALUES (101, '测试账号', 'test', 0, 2, '[]', 0, 2, '我想测试', '', '2021-01-06 13:49:35', '1', '2023-12-07 08:41:16', b'0', 1);
INSERT INTO `system_role` VALUES (109, '租户管理员', 'tenant_admin', 0, 1, '', 0, 1, '系统自动生成', '1', '2022-02-22 00:56:14', '1', '2022-02-22 00:56:14', b'0', 121);
INSERT INTO `system_role` VALUES (110, '测试角色', 'test', 0, 1, '[]', 0, 2, '嘿嘿', '110', '2022-02-23 00:14:34', '110', '2022-02-23 13:14:58', b'0', 121);
INSERT INTO `system_role` VALUES (111, '租户管理员', 'tenant_admin', 0, 1, '', 0, 1, '系统自动生成', '1', '2022-03-07 21:37:58', '1', '2022-03-07 21:37:58', b'0', 122);
INSERT INTO `system_role` VALUES (113, '租户管理员', 'tenant_admin', 0, 1, '', 0, 1, '系统自动生成', '1', '2022-05-17 10:07:10', '1', '2022-05-17 10:07:10', b'0', 124);
INSERT INTO `system_role` VALUES (114, '租户管理员', 'tenant_admin', 0, 1, '', 0, 1, '系统自动生成', '1', '2022-12-30 11:32:03', '1', '2022-12-30 11:32:03', b'0', 125);
INSERT INTO `system_role` VALUES (115, '租户管理员', 'tenant_admin', 0, 1, '', 0, 1, '系统自动生成', '1', '2022-12-30 11:33:42', '1', '2022-12-30 11:33:42', b'0', 126);
INSERT INTO `system_role` VALUES (116, '租户管理员', 'tenant_admin', 0, 1, '', 0, 1, '系统自动生成', '1', '2022-12-30 11:33:48', '1', '2022-12-30 11:33:48', b'0', 127);
INSERT INTO `system_role` VALUES (118, '租户管理员', 'tenant_admin', 0, 1, '', 0, 1, '系统自动生成', '1', '2022-12-30 11:47:52', '1', '2022-12-30 11:47:52', b'0', 129);
INSERT INTO `system_role` VALUES (136, '租户管理员', 'tenant_admin', 0, 1, '', 0, 1, '系统自动生成', '1', '2023-03-05 21:23:32', '1', '2023-03-05 21:23:32', b'0', 147);
INSERT INTO `system_role` VALUES (137, '租户管理员', 'tenant_admin', 0, 1, '', 0, 1, '系统自动生成', '1', '2023-03-05 21:42:27', '1', '2023-03-05 21:42:27', b'0', 148);
INSERT INTO `system_role` VALUES (138, '租户管理员', 'tenant_admin', 0, 1, '', 0, 1, '系统自动生成', '1', '2023-03-05 21:59:02', '1', '2023-03-05 21:59:02', b'0', 149);
INSERT INTO `system_role` VALUES (139, '租户管理员', 'tenant_admin', 0, 1, '', 0, 1, '系统自动生成', '1', '2023-07-25 23:06:04', '1', '2023-07-25 23:06:04', b'0', 150);
INSERT INTO `system_role` VALUES (140, '租户管理员', 'tenant_admin', 0, 1, '', 0, 1, '系统自动生成', '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_role` VALUES (141, '租户管理员', 'tenant_admin', 0, 1, '', 0, 1, '系统自动生成', '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role` VALUES (142, '租户管理员', 'tenant_admin', 0, 1, '', 0, 1, '系统自动生成', '110', '2024-02-19 11:27:03', '110', '2024-02-19 11:27:03', b'0', 153);

-- ----------------------------
-- Table structure for system_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_role_menu`;
CREATE TABLE `system_role_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增编号',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3968 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_role_menu
-- ----------------------------
INSERT INTO `system_role_menu` VALUES (263, 109, 1, '1', '2022-02-22 00:56:14', '1', '2022-02-22 00:56:14', b'0', 121);
INSERT INTO `system_role_menu` VALUES (434, 2, 1, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', b'0', 1);
INSERT INTO `system_role_menu` VALUES (454, 2, 1093, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', b'0', 1);
INSERT INTO `system_role_menu` VALUES (455, 2, 1094, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', b'0', 1);
INSERT INTO `system_role_menu` VALUES (460, 2, 1100, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', b'0', 1);
INSERT INTO `system_role_menu` VALUES (467, 2, 1107, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', b'0', 1);
INSERT INTO `system_role_menu` VALUES (470, 2, 1110, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', b'0', 1);
INSERT INTO `system_role_menu` VALUES (476, 2, 1117, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', b'0', 1);
INSERT INTO `system_role_menu` VALUES (477, 2, 100, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', b'0', 1);
INSERT INTO `system_role_menu` VALUES (478, 2, 101, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', b'0', 1);
INSERT INTO `system_role_menu` VALUES (479, 2, 102, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', b'0', 1);
INSERT INTO `system_role_menu` VALUES (480, 2, 1126, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', b'0', 1);
INSERT INTO `system_role_menu` VALUES (481, 2, 103, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', b'0', 1);
INSERT INTO `system_role_menu` VALUES (483, 2, 104, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', b'0', 1);
INSERT INTO `system_role_menu` VALUES (485, 2, 105, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', b'0', 1);
INSERT INTO `system_role_menu` VALUES (488, 2, 107, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', b'0', 1);
INSERT INTO `system_role_menu` VALUES (490, 2, 108, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', b'0', 1);
INSERT INTO `system_role_menu` VALUES (492, 2, 109, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', b'0', 1);
INSERT INTO `system_role_menu` VALUES (498, 2, 1138, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', b'0', 1);
INSERT INTO `system_role_menu` VALUES (523, 2, 1224, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', b'0', 1);
INSERT INTO `system_role_menu` VALUES (524, 2, 1225, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', b'0', 1);
INSERT INTO `system_role_menu` VALUES (541, 2, 500, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', b'0', 1);
INSERT INTO `system_role_menu` VALUES (543, 2, 501, '1', '2022-02-22 13:09:12', '1', '2022-02-22 13:09:12', b'0', 1);
INSERT INTO `system_role_menu` VALUES (675, 2, 2, '1', '2022-02-22 13:16:57', '1', '2022-02-22 13:16:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (689, 2, 1077, '1', '2022-02-22 13:16:57', '1', '2022-02-22 13:16:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (690, 2, 1078, '1', '2022-02-22 13:16:57', '1', '2022-02-22 13:16:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (692, 2, 1083, '1', '2022-02-22 13:16:57', '1', '2022-02-22 13:16:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (693, 2, 1084, '1', '2022-02-22 13:16:57', '1', '2022-02-22 13:16:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (699, 2, 1090, '1', '2022-02-22 13:16:57', '1', '2022-02-22 13:16:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (703, 2, 106, '1', '2022-02-22 13:16:57', '1', '2022-02-22 13:16:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (704, 2, 110, '1', '2022-02-22 13:16:57', '1', '2022-02-22 13:16:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (705, 2, 111, '1', '2022-02-22 13:16:57', '1', '2022-02-22 13:16:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (706, 2, 112, '1', '2022-02-22 13:16:57', '1', '2022-02-22 13:16:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (707, 2, 113, '1', '2022-02-22 13:16:57', '1', '2022-02-22 13:16:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1296, 110, 1, '110', '2022-02-23 00:23:55', '110', '2022-02-23 00:23:55', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1489, 1, 1, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1490, 1, 2, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1494, 1, 1077, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1495, 1, 1078, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1496, 1, 1083, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1497, 1, 1084, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1498, 1, 1090, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1499, 1, 1093, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1500, 1, 1094, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1501, 1, 1100, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1502, 1, 1107, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1503, 1, 1110, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1505, 1, 1117, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1506, 1, 100, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1507, 1, 101, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1508, 1, 102, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1509, 1, 1126, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1510, 1, 103, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1511, 1, 104, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1512, 1, 105, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1513, 1, 106, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1514, 1, 107, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1515, 1, 108, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1516, 1, 109, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1517, 1, 110, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1518, 1, 111, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1519, 1, 112, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1520, 1, 113, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1522, 1, 1138, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1525, 1, 1224, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1526, 1, 1225, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1527, 1, 500, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1528, 1, 501, '1', '2022-02-23 20:03:57', '1', '2022-02-23 20:03:57', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1578, 111, 1, '1', '2022-03-07 21:37:58', '1', '2022-03-07 21:37:58', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1604, 101, 1216, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1605, 101, 1217, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1606, 101, 1218, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1607, 101, 1219, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1608, 101, 1220, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1609, 101, 1221, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1610, 101, 5, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1611, 101, 1222, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1612, 101, 1118, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1613, 101, 1119, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1614, 101, 1120, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1615, 101, 1185, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1616, 101, 1186, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1617, 101, 1187, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1618, 101, 1188, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1619, 101, 1189, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1620, 101, 1190, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1621, 101, 1191, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1622, 101, 1192, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1623, 101, 1193, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1624, 101, 1194, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1625, 101, 1195, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1626, 101, 1196, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1627, 101, 1197, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1628, 101, 1198, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1629, 101, 1199, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1630, 101, 1200, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1631, 101, 1201, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1632, 101, 1202, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1633, 101, 1207, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1634, 101, 1208, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1635, 101, 1209, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1636, 101, 1210, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1637, 101, 1211, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1638, 101, 1212, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1639, 101, 1213, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1640, 101, 1215, '1', '2022-03-19 21:45:52', '1', '2022-03-19 21:45:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1641, 101, 2, '1', '2022-04-01 22:21:24', '1', '2022-04-01 22:21:24', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1642, 101, 1031, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1643, 101, 1032, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1644, 101, 1033, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1645, 101, 1034, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1646, 101, 1035, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1647, 101, 1050, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1648, 101, 1051, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1649, 101, 1052, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1650, 101, 1053, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1651, 101, 1054, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1652, 101, 1056, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1653, 101, 1057, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1654, 101, 1058, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1655, 101, 1059, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1656, 101, 1060, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1657, 101, 1066, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1658, 101, 1067, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1659, 101, 1070, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1664, 101, 1075, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1665, 101, 1076, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1666, 101, 1077, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1667, 101, 1078, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1668, 101, 1082, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1669, 101, 1083, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1670, 101, 1084, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1671, 101, 1085, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1672, 101, 1086, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1673, 101, 1087, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1674, 101, 1088, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1675, 101, 1089, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1679, 101, 1237, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1680, 101, 1238, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1681, 101, 1239, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1682, 101, 1240, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1683, 101, 1241, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1684, 101, 1242, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1685, 101, 1243, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1687, 101, 106, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1688, 101, 110, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1689, 101, 111, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1690, 101, 112, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1691, 101, 113, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1692, 101, 114, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1693, 101, 115, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1694, 101, 116, '1', '2022-04-01 22:21:37', '1', '2022-04-01 22:21:37', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1712, 113, 1024, '1', '2022-05-17 10:07:10', '1', '2022-05-17 10:07:10', b'0', 124);
INSERT INTO `system_role_menu` VALUES (1713, 113, 1025, '1', '2022-05-17 10:07:10', '1', '2022-05-17 10:07:10', b'0', 124);
INSERT INTO `system_role_menu` VALUES (1714, 113, 1, '1', '2022-05-17 10:07:10', '1', '2022-05-17 10:07:10', b'0', 124);
INSERT INTO `system_role_menu` VALUES (1715, 113, 102, '1', '2022-05-17 10:07:10', '1', '2022-05-17 10:07:10', b'0', 124);
INSERT INTO `system_role_menu` VALUES (1716, 113, 103, '1', '2022-05-17 10:07:10', '1', '2022-05-17 10:07:10', b'0', 124);
INSERT INTO `system_role_menu` VALUES (1717, 113, 104, '1', '2022-05-17 10:07:10', '1', '2022-05-17 10:07:10', b'0', 124);
INSERT INTO `system_role_menu` VALUES (1718, 113, 1013, '1', '2022-05-17 10:07:10', '1', '2022-05-17 10:07:10', b'0', 124);
INSERT INTO `system_role_menu` VALUES (1719, 113, 1014, '1', '2022-05-17 10:07:10', '1', '2022-05-17 10:07:10', b'0', 124);
INSERT INTO `system_role_menu` VALUES (1720, 113, 1015, '1', '2022-05-17 10:07:10', '1', '2022-05-17 10:07:10', b'0', 124);
INSERT INTO `system_role_menu` VALUES (1721, 113, 1016, '1', '2022-05-17 10:07:10', '1', '2022-05-17 10:07:10', b'0', 124);
INSERT INTO `system_role_menu` VALUES (1722, 113, 1017, '1', '2022-05-17 10:07:10', '1', '2022-05-17 10:07:10', b'0', 124);
INSERT INTO `system_role_menu` VALUES (1723, 113, 1018, '1', '2022-05-17 10:07:10', '1', '2022-05-17 10:07:10', b'0', 124);
INSERT INTO `system_role_menu` VALUES (1724, 113, 1019, '1', '2022-05-17 10:07:10', '1', '2022-05-17 10:07:10', b'0', 124);
INSERT INTO `system_role_menu` VALUES (1725, 113, 1020, '1', '2022-05-17 10:07:10', '1', '2022-05-17 10:07:10', b'0', 124);
INSERT INTO `system_role_menu` VALUES (1726, 113, 1021, '1', '2022-05-17 10:07:10', '1', '2022-05-17 10:07:10', b'0', 124);
INSERT INTO `system_role_menu` VALUES (1727, 113, 1022, '1', '2022-05-17 10:07:10', '1', '2022-05-17 10:07:10', b'0', 124);
INSERT INTO `system_role_menu` VALUES (1728, 113, 1023, '1', '2022-05-17 10:07:10', '1', '2022-05-17 10:07:10', b'0', 124);
INSERT INTO `system_role_menu` VALUES (1729, 109, 100, '1', '2022-09-21 22:08:51', '1', '2022-09-21 22:08:51', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1730, 109, 101, '1', '2022-09-21 22:08:51', '1', '2022-09-21 22:08:51', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1731, 109, 1063, '1', '2022-09-21 22:08:51', '1', '2022-09-21 22:08:51', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1732, 109, 1064, '1', '2022-09-21 22:08:51', '1', '2022-09-21 22:08:51', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1733, 109, 1001, '1', '2022-09-21 22:08:51', '1', '2022-09-21 22:08:51', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1734, 109, 1065, '1', '2022-09-21 22:08:51', '1', '2022-09-21 22:08:51', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1735, 109, 1002, '1', '2022-09-21 22:08:51', '1', '2022-09-21 22:08:51', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1736, 109, 1003, '1', '2022-09-21 22:08:51', '1', '2022-09-21 22:08:51', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1737, 109, 1004, '1', '2022-09-21 22:08:51', '1', '2022-09-21 22:08:51', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1738, 109, 1005, '1', '2022-09-21 22:08:51', '1', '2022-09-21 22:08:51', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1739, 109, 1006, '1', '2022-09-21 22:08:51', '1', '2022-09-21 22:08:51', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1740, 109, 1007, '1', '2022-09-21 22:08:51', '1', '2022-09-21 22:08:51', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1741, 109, 1008, '1', '2022-09-21 22:08:51', '1', '2022-09-21 22:08:51', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1742, 109, 1009, '1', '2022-09-21 22:08:51', '1', '2022-09-21 22:08:51', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1743, 109, 1010, '1', '2022-09-21 22:08:51', '1', '2022-09-21 22:08:51', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1744, 109, 1011, '1', '2022-09-21 22:08:51', '1', '2022-09-21 22:08:51', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1745, 109, 1012, '1', '2022-09-21 22:08:51', '1', '2022-09-21 22:08:51', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1746, 111, 100, '1', '2022-09-21 22:08:52', '1', '2022-09-21 22:08:52', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1747, 111, 101, '1', '2022-09-21 22:08:52', '1', '2022-09-21 22:08:52', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1748, 111, 1063, '1', '2022-09-21 22:08:52', '1', '2022-09-21 22:08:52', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1749, 111, 1064, '1', '2022-09-21 22:08:52', '1', '2022-09-21 22:08:52', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1750, 111, 1001, '1', '2022-09-21 22:08:52', '1', '2022-09-21 22:08:52', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1751, 111, 1065, '1', '2022-09-21 22:08:52', '1', '2022-09-21 22:08:52', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1752, 111, 1002, '1', '2022-09-21 22:08:52', '1', '2022-09-21 22:08:52', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1753, 111, 1003, '1', '2022-09-21 22:08:52', '1', '2022-09-21 22:08:52', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1754, 111, 1004, '1', '2022-09-21 22:08:52', '1', '2022-09-21 22:08:52', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1755, 111, 1005, '1', '2022-09-21 22:08:52', '1', '2022-09-21 22:08:52', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1756, 111, 1006, '1', '2022-09-21 22:08:52', '1', '2022-09-21 22:08:52', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1757, 111, 1007, '1', '2022-09-21 22:08:52', '1', '2022-09-21 22:08:52', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1758, 111, 1008, '1', '2022-09-21 22:08:52', '1', '2022-09-21 22:08:52', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1759, 111, 1009, '1', '2022-09-21 22:08:52', '1', '2022-09-21 22:08:52', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1760, 111, 1010, '1', '2022-09-21 22:08:52', '1', '2022-09-21 22:08:52', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1761, 111, 1011, '1', '2022-09-21 22:08:52', '1', '2022-09-21 22:08:52', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1762, 111, 1012, '1', '2022-09-21 22:08:52', '1', '2022-09-21 22:08:52', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1763, 109, 100, '1', '2022-09-21 22:08:53', '1', '2022-09-21 22:08:53', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1764, 109, 101, '1', '2022-09-21 22:08:53', '1', '2022-09-21 22:08:53', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1765, 109, 1063, '1', '2022-09-21 22:08:53', '1', '2022-09-21 22:08:53', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1766, 109, 1064, '1', '2022-09-21 22:08:53', '1', '2022-09-21 22:08:53', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1767, 109, 1001, '1', '2022-09-21 22:08:53', '1', '2022-09-21 22:08:53', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1768, 109, 1065, '1', '2022-09-21 22:08:53', '1', '2022-09-21 22:08:53', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1769, 109, 1002, '1', '2022-09-21 22:08:53', '1', '2022-09-21 22:08:53', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1770, 109, 1003, '1', '2022-09-21 22:08:53', '1', '2022-09-21 22:08:53', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1771, 109, 1004, '1', '2022-09-21 22:08:53', '1', '2022-09-21 22:08:53', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1772, 109, 1005, '1', '2022-09-21 22:08:53', '1', '2022-09-21 22:08:53', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1773, 109, 1006, '1', '2022-09-21 22:08:53', '1', '2022-09-21 22:08:53', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1774, 109, 1007, '1', '2022-09-21 22:08:53', '1', '2022-09-21 22:08:53', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1775, 109, 1008, '1', '2022-09-21 22:08:53', '1', '2022-09-21 22:08:53', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1776, 109, 1009, '1', '2022-09-21 22:08:53', '1', '2022-09-21 22:08:53', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1777, 109, 1010, '1', '2022-09-21 22:08:53', '1', '2022-09-21 22:08:53', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1778, 109, 1011, '1', '2022-09-21 22:08:53', '1', '2022-09-21 22:08:53', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1779, 109, 1012, '1', '2022-09-21 22:08:53', '1', '2022-09-21 22:08:53', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1780, 111, 100, '1', '2022-09-21 22:08:54', '1', '2022-09-21 22:08:54', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1781, 111, 101, '1', '2022-09-21 22:08:54', '1', '2022-09-21 22:08:54', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1782, 111, 1063, '1', '2022-09-21 22:08:54', '1', '2022-09-21 22:08:54', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1783, 111, 1064, '1', '2022-09-21 22:08:54', '1', '2022-09-21 22:08:54', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1784, 111, 1001, '1', '2022-09-21 22:08:54', '1', '2022-09-21 22:08:54', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1785, 111, 1065, '1', '2022-09-21 22:08:54', '1', '2022-09-21 22:08:54', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1786, 111, 1002, '1', '2022-09-21 22:08:54', '1', '2022-09-21 22:08:54', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1787, 111, 1003, '1', '2022-09-21 22:08:54', '1', '2022-09-21 22:08:54', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1788, 111, 1004, '1', '2022-09-21 22:08:54', '1', '2022-09-21 22:08:54', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1789, 111, 1005, '1', '2022-09-21 22:08:54', '1', '2022-09-21 22:08:54', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1790, 111, 1006, '1', '2022-09-21 22:08:54', '1', '2022-09-21 22:08:54', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1791, 111, 1007, '1', '2022-09-21 22:08:54', '1', '2022-09-21 22:08:54', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1792, 111, 1008, '1', '2022-09-21 22:08:54', '1', '2022-09-21 22:08:54', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1793, 111, 1009, '1', '2022-09-21 22:08:54', '1', '2022-09-21 22:08:54', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1794, 111, 1010, '1', '2022-09-21 22:08:54', '1', '2022-09-21 22:08:54', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1795, 111, 1011, '1', '2022-09-21 22:08:54', '1', '2022-09-21 22:08:54', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1796, 111, 1012, '1', '2022-09-21 22:08:54', '1', '2022-09-21 22:08:54', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1797, 109, 100, '1', '2022-09-21 22:08:55', '1', '2022-09-21 22:08:55', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1798, 109, 101, '1', '2022-09-21 22:08:55', '1', '2022-09-21 22:08:55', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1799, 109, 1063, '1', '2022-09-21 22:08:55', '1', '2022-09-21 22:08:55', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1800, 109, 1064, '1', '2022-09-21 22:08:55', '1', '2022-09-21 22:08:55', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1801, 109, 1001, '1', '2022-09-21 22:08:55', '1', '2022-09-21 22:08:55', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1802, 109, 1065, '1', '2022-09-21 22:08:55', '1', '2022-09-21 22:08:55', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1803, 109, 1002, '1', '2022-09-21 22:08:55', '1', '2022-09-21 22:08:55', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1804, 109, 1003, '1', '2022-09-21 22:08:55', '1', '2022-09-21 22:08:55', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1805, 109, 1004, '1', '2022-09-21 22:08:55', '1', '2022-09-21 22:08:55', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1806, 109, 1005, '1', '2022-09-21 22:08:55', '1', '2022-09-21 22:08:55', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1807, 109, 1006, '1', '2022-09-21 22:08:55', '1', '2022-09-21 22:08:55', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1808, 109, 1007, '1', '2022-09-21 22:08:55', '1', '2022-09-21 22:08:55', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1809, 109, 1008, '1', '2022-09-21 22:08:55', '1', '2022-09-21 22:08:55', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1810, 109, 1009, '1', '2022-09-21 22:08:55', '1', '2022-09-21 22:08:55', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1811, 109, 1010, '1', '2022-09-21 22:08:55', '1', '2022-09-21 22:08:55', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1812, 109, 1011, '1', '2022-09-21 22:08:55', '1', '2022-09-21 22:08:55', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1813, 109, 1012, '1', '2022-09-21 22:08:55', '1', '2022-09-21 22:08:55', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1814, 111, 100, '1', '2022-09-21 22:08:56', '1', '2022-09-21 22:08:56', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1815, 111, 101, '1', '2022-09-21 22:08:56', '1', '2022-09-21 22:08:56', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1816, 111, 1063, '1', '2022-09-21 22:08:56', '1', '2022-09-21 22:08:56', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1817, 111, 1064, '1', '2022-09-21 22:08:56', '1', '2022-09-21 22:08:56', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1818, 111, 1001, '1', '2022-09-21 22:08:56', '1', '2022-09-21 22:08:56', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1819, 111, 1065, '1', '2022-09-21 22:08:56', '1', '2022-09-21 22:08:56', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1820, 111, 1002, '1', '2022-09-21 22:08:56', '1', '2022-09-21 22:08:56', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1821, 111, 1003, '1', '2022-09-21 22:08:56', '1', '2022-09-21 22:08:56', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1822, 111, 1004, '1', '2022-09-21 22:08:56', '1', '2022-09-21 22:08:56', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1823, 111, 1005, '1', '2022-09-21 22:08:56', '1', '2022-09-21 22:08:56', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1824, 111, 1006, '1', '2022-09-21 22:08:56', '1', '2022-09-21 22:08:56', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1825, 111, 1007, '1', '2022-09-21 22:08:56', '1', '2022-09-21 22:08:56', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1826, 111, 1008, '1', '2022-09-21 22:08:56', '1', '2022-09-21 22:08:56', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1827, 111, 1009, '1', '2022-09-21 22:08:56', '1', '2022-09-21 22:08:56', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1828, 111, 1010, '1', '2022-09-21 22:08:56', '1', '2022-09-21 22:08:56', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1829, 111, 1011, '1', '2022-09-21 22:08:56', '1', '2022-09-21 22:08:56', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1830, 111, 1012, '1', '2022-09-21 22:08:56', '1', '2022-09-21 22:08:56', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1831, 109, 103, '1', '2022-09-21 22:43:23', '1', '2022-09-21 22:43:23', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1832, 109, 1017, '1', '2022-09-21 22:43:23', '1', '2022-09-21 22:43:23', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1833, 109, 1018, '1', '2022-09-21 22:43:23', '1', '2022-09-21 22:43:23', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1834, 109, 1019, '1', '2022-09-21 22:43:23', '1', '2022-09-21 22:43:23', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1835, 109, 1020, '1', '2022-09-21 22:43:23', '1', '2022-09-21 22:43:23', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1836, 111, 103, '1', '2022-09-21 22:43:24', '1', '2022-09-21 22:43:24', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1837, 111, 1017, '1', '2022-09-21 22:43:24', '1', '2022-09-21 22:43:24', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1838, 111, 1018, '1', '2022-09-21 22:43:24', '1', '2022-09-21 22:43:24', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1839, 111, 1019, '1', '2022-09-21 22:43:24', '1', '2022-09-21 22:43:24', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1840, 111, 1020, '1', '2022-09-21 22:43:24', '1', '2022-09-21 22:43:24', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1841, 109, 1036, '1', '2022-09-21 22:48:13', '1', '2022-09-21 22:48:13', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1842, 109, 1037, '1', '2022-09-21 22:48:13', '1', '2022-09-21 22:48:13', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1843, 109, 1038, '1', '2022-09-21 22:48:13', '1', '2022-09-21 22:48:13', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1844, 109, 1039, '1', '2022-09-21 22:48:13', '1', '2022-09-21 22:48:13', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1845, 109, 107, '1', '2022-09-21 22:48:13', '1', '2022-09-21 22:48:13', b'0', 121);
INSERT INTO `system_role_menu` VALUES (1846, 111, 1036, '1', '2022-09-21 22:48:13', '1', '2022-09-21 22:48:13', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1847, 111, 1037, '1', '2022-09-21 22:48:13', '1', '2022-09-21 22:48:13', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1848, 111, 1038, '1', '2022-09-21 22:48:13', '1', '2022-09-21 22:48:13', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1849, 111, 1039, '1', '2022-09-21 22:48:13', '1', '2022-09-21 22:48:13', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1850, 111, 107, '1', '2022-09-21 22:48:13', '1', '2022-09-21 22:48:13', b'0', 122);
INSERT INTO `system_role_menu` VALUES (1851, 114, 1, '1', '2022-12-30 11:32:03', '1', '2022-12-30 11:32:03', b'0', 125);
INSERT INTO `system_role_menu` VALUES (1852, 114, 1036, '1', '2022-12-30 11:32:03', '1', '2022-12-30 11:32:03', b'0', 125);
INSERT INTO `system_role_menu` VALUES (1853, 114, 1037, '1', '2022-12-30 11:32:03', '1', '2022-12-30 11:32:03', b'0', 125);
INSERT INTO `system_role_menu` VALUES (1854, 114, 1038, '1', '2022-12-30 11:32:03', '1', '2022-12-30 11:32:03', b'0', 125);
INSERT INTO `system_role_menu` VALUES (1855, 114, 1039, '1', '2022-12-30 11:32:03', '1', '2022-12-30 11:32:03', b'0', 125);
INSERT INTO `system_role_menu` VALUES (1856, 114, 100, '1', '2022-12-30 11:32:03', '1', '2022-12-30 11:32:03', b'0', 125);
INSERT INTO `system_role_menu` VALUES (1857, 114, 101, '1', '2022-12-30 11:32:03', '1', '2022-12-30 11:32:03', b'0', 125);
INSERT INTO `system_role_menu` VALUES (1858, 114, 1063, '1', '2022-12-30 11:32:03', '1', '2022-12-30 11:32:03', b'0', 125);
INSERT INTO `system_role_menu` VALUES (1859, 114, 103, '1', '2022-12-30 11:32:03', '1', '2022-12-30 11:32:03', b'0', 125);
INSERT INTO `system_role_menu` VALUES (1860, 114, 1064, '1', '2022-12-30 11:32:03', '1', '2022-12-30 11:32:03', b'0', 125);
INSERT INTO `system_role_menu` VALUES (1861, 114, 1001, '1', '2022-12-30 11:32:03', '1', '2022-12-30 11:32:03', b'0', 125);
INSERT INTO `system_role_menu` VALUES (1862, 114, 1065, '1', '2022-12-30 11:32:03', '1', '2022-12-30 11:32:03', b'0', 125);
INSERT INTO `system_role_menu` VALUES (1863, 114, 1002, '1', '2022-12-30 11:32:03', '1', '2022-12-30 11:32:03', b'0', 125);
INSERT INTO `system_role_menu` VALUES (1864, 114, 1003, '1', '2022-12-30 11:32:03', '1', '2022-12-30 11:32:03', b'0', 125);
INSERT INTO `system_role_menu` VALUES (1865, 114, 107, '1', '2022-12-30 11:32:03', '1', '2022-12-30 11:32:03', b'0', 125);
INSERT INTO `system_role_menu` VALUES (1866, 114, 1004, '1', '2022-12-30 11:32:03', '1', '2022-12-30 11:32:03', b'0', 125);
INSERT INTO `system_role_menu` VALUES (1867, 114, 1005, '1', '2022-12-30 11:32:03', '1', '2022-12-30 11:32:03', b'0', 125);
INSERT INTO `system_role_menu` VALUES (1868, 114, 1006, '1', '2022-12-30 11:32:03', '1', '2022-12-30 11:32:03', b'0', 125);
INSERT INTO `system_role_menu` VALUES (1869, 114, 1007, '1', '2022-12-30 11:32:03', '1', '2022-12-30 11:32:03', b'0', 125);
INSERT INTO `system_role_menu` VALUES (1870, 114, 1008, '1', '2022-12-30 11:32:03', '1', '2022-12-30 11:32:03', b'0', 125);
INSERT INTO `system_role_menu` VALUES (1871, 114, 1009, '1', '2022-12-30 11:32:03', '1', '2022-12-30 11:32:03', b'0', 125);
INSERT INTO `system_role_menu` VALUES (1872, 114, 1010, '1', '2022-12-30 11:32:03', '1', '2022-12-30 11:32:03', b'0', 125);
INSERT INTO `system_role_menu` VALUES (1873, 114, 1011, '1', '2022-12-30 11:32:03', '1', '2022-12-30 11:32:03', b'0', 125);
INSERT INTO `system_role_menu` VALUES (1874, 114, 1012, '1', '2022-12-30 11:32:03', '1', '2022-12-30 11:32:03', b'0', 125);
INSERT INTO `system_role_menu` VALUES (1875, 114, 1017, '1', '2022-12-30 11:32:03', '1', '2022-12-30 11:32:03', b'0', 125);
INSERT INTO `system_role_menu` VALUES (1876, 114, 1018, '1', '2022-12-30 11:32:03', '1', '2022-12-30 11:32:03', b'0', 125);
INSERT INTO `system_role_menu` VALUES (1877, 114, 1019, '1', '2022-12-30 11:32:03', '1', '2022-12-30 11:32:03', b'0', 125);
INSERT INTO `system_role_menu` VALUES (1878, 114, 1020, '1', '2022-12-30 11:32:03', '1', '2022-12-30 11:32:03', b'0', 125);
INSERT INTO `system_role_menu` VALUES (1879, 115, 1, '1', '2022-12-30 11:33:42', '1', '2022-12-30 11:33:42', b'0', 126);
INSERT INTO `system_role_menu` VALUES (1880, 115, 1036, '1', '2022-12-30 11:33:42', '1', '2022-12-30 11:33:42', b'0', 126);
INSERT INTO `system_role_menu` VALUES (1881, 115, 1037, '1', '2022-12-30 11:33:42', '1', '2022-12-30 11:33:42', b'0', 126);
INSERT INTO `system_role_menu` VALUES (1882, 115, 1038, '1', '2022-12-30 11:33:42', '1', '2022-12-30 11:33:42', b'0', 126);
INSERT INTO `system_role_menu` VALUES (1883, 115, 1039, '1', '2022-12-30 11:33:42', '1', '2022-12-30 11:33:42', b'0', 126);
INSERT INTO `system_role_menu` VALUES (1884, 115, 100, '1', '2022-12-30 11:33:42', '1', '2022-12-30 11:33:42', b'0', 126);
INSERT INTO `system_role_menu` VALUES (1885, 115, 101, '1', '2022-12-30 11:33:42', '1', '2022-12-30 11:33:42', b'0', 126);
INSERT INTO `system_role_menu` VALUES (1886, 115, 1063, '1', '2022-12-30 11:33:42', '1', '2022-12-30 11:33:42', b'0', 126);
INSERT INTO `system_role_menu` VALUES (1887, 115, 103, '1', '2022-12-30 11:33:42', '1', '2022-12-30 11:33:42', b'0', 126);
INSERT INTO `system_role_menu` VALUES (1888, 115, 1064, '1', '2022-12-30 11:33:42', '1', '2022-12-30 11:33:42', b'0', 126);
INSERT INTO `system_role_menu` VALUES (1889, 115, 1001, '1', '2022-12-30 11:33:42', '1', '2022-12-30 11:33:42', b'0', 126);
INSERT INTO `system_role_menu` VALUES (1890, 115, 1065, '1', '2022-12-30 11:33:42', '1', '2022-12-30 11:33:42', b'0', 126);
INSERT INTO `system_role_menu` VALUES (1891, 115, 1002, '1', '2022-12-30 11:33:42', '1', '2022-12-30 11:33:42', b'0', 126);
INSERT INTO `system_role_menu` VALUES (1892, 115, 1003, '1', '2022-12-30 11:33:42', '1', '2022-12-30 11:33:42', b'0', 126);
INSERT INTO `system_role_menu` VALUES (1893, 115, 107, '1', '2022-12-30 11:33:42', '1', '2022-12-30 11:33:42', b'0', 126);
INSERT INTO `system_role_menu` VALUES (1894, 115, 1004, '1', '2022-12-30 11:33:42', '1', '2022-12-30 11:33:42', b'0', 126);
INSERT INTO `system_role_menu` VALUES (1895, 115, 1005, '1', '2022-12-30 11:33:42', '1', '2022-12-30 11:33:42', b'0', 126);
INSERT INTO `system_role_menu` VALUES (1896, 115, 1006, '1', '2022-12-30 11:33:42', '1', '2022-12-30 11:33:42', b'0', 126);
INSERT INTO `system_role_menu` VALUES (1897, 115, 1007, '1', '2022-12-30 11:33:42', '1', '2022-12-30 11:33:42', b'0', 126);
INSERT INTO `system_role_menu` VALUES (1898, 115, 1008, '1', '2022-12-30 11:33:42', '1', '2022-12-30 11:33:42', b'0', 126);
INSERT INTO `system_role_menu` VALUES (1899, 115, 1009, '1', '2022-12-30 11:33:42', '1', '2022-12-30 11:33:42', b'0', 126);
INSERT INTO `system_role_menu` VALUES (1900, 115, 1010, '1', '2022-12-30 11:33:42', '1', '2022-12-30 11:33:42', b'0', 126);
INSERT INTO `system_role_menu` VALUES (1901, 115, 1011, '1', '2022-12-30 11:33:42', '1', '2022-12-30 11:33:42', b'0', 126);
INSERT INTO `system_role_menu` VALUES (1902, 115, 1012, '1', '2022-12-30 11:33:42', '1', '2022-12-30 11:33:42', b'0', 126);
INSERT INTO `system_role_menu` VALUES (1903, 115, 1017, '1', '2022-12-30 11:33:42', '1', '2022-12-30 11:33:42', b'0', 126);
INSERT INTO `system_role_menu` VALUES (1904, 115, 1018, '1', '2022-12-30 11:33:42', '1', '2022-12-30 11:33:42', b'0', 126);
INSERT INTO `system_role_menu` VALUES (1905, 115, 1019, '1', '2022-12-30 11:33:42', '1', '2022-12-30 11:33:42', b'0', 126);
INSERT INTO `system_role_menu` VALUES (1906, 115, 1020, '1', '2022-12-30 11:33:42', '1', '2022-12-30 11:33:42', b'0', 126);
INSERT INTO `system_role_menu` VALUES (1907, 116, 1, '1', '2022-12-30 11:33:48', '1', '2022-12-30 11:33:48', b'0', 127);
INSERT INTO `system_role_menu` VALUES (1908, 116, 1036, '1', '2022-12-30 11:33:48', '1', '2022-12-30 11:33:48', b'0', 127);
INSERT INTO `system_role_menu` VALUES (1909, 116, 1037, '1', '2022-12-30 11:33:48', '1', '2022-12-30 11:33:48', b'0', 127);
INSERT INTO `system_role_menu` VALUES (1910, 116, 1038, '1', '2022-12-30 11:33:48', '1', '2022-12-30 11:33:48', b'0', 127);
INSERT INTO `system_role_menu` VALUES (1911, 116, 1039, '1', '2022-12-30 11:33:48', '1', '2022-12-30 11:33:48', b'0', 127);
INSERT INTO `system_role_menu` VALUES (1912, 116, 100, '1', '2022-12-30 11:33:48', '1', '2022-12-30 11:33:48', b'0', 127);
INSERT INTO `system_role_menu` VALUES (1913, 116, 101, '1', '2022-12-30 11:33:48', '1', '2022-12-30 11:33:48', b'0', 127);
INSERT INTO `system_role_menu` VALUES (1914, 116, 1063, '1', '2022-12-30 11:33:48', '1', '2022-12-30 11:33:48', b'0', 127);
INSERT INTO `system_role_menu` VALUES (1915, 116, 103, '1', '2022-12-30 11:33:48', '1', '2022-12-30 11:33:48', b'0', 127);
INSERT INTO `system_role_menu` VALUES (1916, 116, 1064, '1', '2022-12-30 11:33:48', '1', '2022-12-30 11:33:48', b'0', 127);
INSERT INTO `system_role_menu` VALUES (1917, 116, 1001, '1', '2022-12-30 11:33:48', '1', '2022-12-30 11:33:48', b'0', 127);
INSERT INTO `system_role_menu` VALUES (1918, 116, 1065, '1', '2022-12-30 11:33:48', '1', '2022-12-30 11:33:48', b'0', 127);
INSERT INTO `system_role_menu` VALUES (1919, 116, 1002, '1', '2022-12-30 11:33:48', '1', '2022-12-30 11:33:48', b'0', 127);
INSERT INTO `system_role_menu` VALUES (1920, 116, 1003, '1', '2022-12-30 11:33:48', '1', '2022-12-30 11:33:48', b'0', 127);
INSERT INTO `system_role_menu` VALUES (1921, 116, 107, '1', '2022-12-30 11:33:48', '1', '2022-12-30 11:33:48', b'0', 127);
INSERT INTO `system_role_menu` VALUES (1922, 116, 1004, '1', '2022-12-30 11:33:48', '1', '2022-12-30 11:33:48', b'0', 127);
INSERT INTO `system_role_menu` VALUES (1923, 116, 1005, '1', '2022-12-30 11:33:48', '1', '2022-12-30 11:33:48', b'0', 127);
INSERT INTO `system_role_menu` VALUES (1924, 116, 1006, '1', '2022-12-30 11:33:48', '1', '2022-12-30 11:33:48', b'0', 127);
INSERT INTO `system_role_menu` VALUES (1925, 116, 1007, '1', '2022-12-30 11:33:48', '1', '2022-12-30 11:33:48', b'0', 127);
INSERT INTO `system_role_menu` VALUES (1926, 116, 1008, '1', '2022-12-30 11:33:48', '1', '2022-12-30 11:33:48', b'0', 127);
INSERT INTO `system_role_menu` VALUES (1927, 116, 1009, '1', '2022-12-30 11:33:48', '1', '2022-12-30 11:33:48', b'0', 127);
INSERT INTO `system_role_menu` VALUES (1928, 116, 1010, '1', '2022-12-30 11:33:48', '1', '2022-12-30 11:33:48', b'0', 127);
INSERT INTO `system_role_menu` VALUES (1929, 116, 1011, '1', '2022-12-30 11:33:48', '1', '2022-12-30 11:33:48', b'0', 127);
INSERT INTO `system_role_menu` VALUES (1930, 116, 1012, '1', '2022-12-30 11:33:48', '1', '2022-12-30 11:33:48', b'0', 127);
INSERT INTO `system_role_menu` VALUES (1931, 116, 1017, '1', '2022-12-30 11:33:48', '1', '2022-12-30 11:33:48', b'0', 127);
INSERT INTO `system_role_menu` VALUES (1932, 116, 1018, '1', '2022-12-30 11:33:48', '1', '2022-12-30 11:33:48', b'0', 127);
INSERT INTO `system_role_menu` VALUES (1933, 116, 1019, '1', '2022-12-30 11:33:48', '1', '2022-12-30 11:33:48', b'0', 127);
INSERT INTO `system_role_menu` VALUES (1934, 116, 1020, '1', '2022-12-30 11:33:48', '1', '2022-12-30 11:33:48', b'0', 127);
INSERT INTO `system_role_menu` VALUES (1963, 118, 1, '1', '2022-12-30 11:47:52', '1', '2022-12-30 11:47:52', b'0', 129);
INSERT INTO `system_role_menu` VALUES (1964, 118, 1036, '1', '2022-12-30 11:47:52', '1', '2022-12-30 11:47:52', b'0', 129);
INSERT INTO `system_role_menu` VALUES (1965, 118, 1037, '1', '2022-12-30 11:47:52', '1', '2022-12-30 11:47:52', b'0', 129);
INSERT INTO `system_role_menu` VALUES (1966, 118, 1038, '1', '2022-12-30 11:47:52', '1', '2022-12-30 11:47:52', b'0', 129);
INSERT INTO `system_role_menu` VALUES (1967, 118, 1039, '1', '2022-12-30 11:47:52', '1', '2022-12-30 11:47:52', b'0', 129);
INSERT INTO `system_role_menu` VALUES (1968, 118, 100, '1', '2022-12-30 11:47:52', '1', '2022-12-30 11:47:52', b'0', 129);
INSERT INTO `system_role_menu` VALUES (1969, 118, 101, '1', '2022-12-30 11:47:52', '1', '2022-12-30 11:47:52', b'0', 129);
INSERT INTO `system_role_menu` VALUES (1970, 118, 1063, '1', '2022-12-30 11:47:52', '1', '2022-12-30 11:47:52', b'0', 129);
INSERT INTO `system_role_menu` VALUES (1971, 118, 103, '1', '2022-12-30 11:47:52', '1', '2022-12-30 11:47:52', b'0', 129);
INSERT INTO `system_role_menu` VALUES (1972, 118, 1064, '1', '2022-12-30 11:47:52', '1', '2022-12-30 11:47:52', b'0', 129);
INSERT INTO `system_role_menu` VALUES (1973, 118, 1001, '1', '2022-12-30 11:47:52', '1', '2022-12-30 11:47:52', b'0', 129);
INSERT INTO `system_role_menu` VALUES (1974, 118, 1065, '1', '2022-12-30 11:47:52', '1', '2022-12-30 11:47:52', b'0', 129);
INSERT INTO `system_role_menu` VALUES (1975, 118, 1002, '1', '2022-12-30 11:47:52', '1', '2022-12-30 11:47:52', b'0', 129);
INSERT INTO `system_role_menu` VALUES (1976, 118, 1003, '1', '2022-12-30 11:47:52', '1', '2022-12-30 11:47:52', b'0', 129);
INSERT INTO `system_role_menu` VALUES (1977, 118, 107, '1', '2022-12-30 11:47:52', '1', '2022-12-30 11:47:52', b'0', 129);
INSERT INTO `system_role_menu` VALUES (1978, 118, 1004, '1', '2022-12-30 11:47:52', '1', '2022-12-30 11:47:52', b'0', 129);
INSERT INTO `system_role_menu` VALUES (1979, 118, 1005, '1', '2022-12-30 11:47:52', '1', '2022-12-30 11:47:52', b'0', 129);
INSERT INTO `system_role_menu` VALUES (1980, 118, 1006, '1', '2022-12-30 11:47:52', '1', '2022-12-30 11:47:52', b'0', 129);
INSERT INTO `system_role_menu` VALUES (1981, 118, 1007, '1', '2022-12-30 11:47:52', '1', '2022-12-30 11:47:52', b'0', 129);
INSERT INTO `system_role_menu` VALUES (1982, 118, 1008, '1', '2022-12-30 11:47:52', '1', '2022-12-30 11:47:52', b'0', 129);
INSERT INTO `system_role_menu` VALUES (1983, 118, 1009, '1', '2022-12-30 11:47:52', '1', '2022-12-30 11:47:52', b'0', 129);
INSERT INTO `system_role_menu` VALUES (1984, 118, 1010, '1', '2022-12-30 11:47:52', '1', '2022-12-30 11:47:52', b'0', 129);
INSERT INTO `system_role_menu` VALUES (1985, 118, 1011, '1', '2022-12-30 11:47:52', '1', '2022-12-30 11:47:52', b'0', 129);
INSERT INTO `system_role_menu` VALUES (1986, 118, 1012, '1', '2022-12-30 11:47:52', '1', '2022-12-30 11:47:52', b'0', 129);
INSERT INTO `system_role_menu` VALUES (1987, 118, 1017, '1', '2022-12-30 11:47:52', '1', '2022-12-30 11:47:52', b'0', 129);
INSERT INTO `system_role_menu` VALUES (1988, 118, 1018, '1', '2022-12-30 11:47:52', '1', '2022-12-30 11:47:52', b'0', 129);
INSERT INTO `system_role_menu` VALUES (1989, 118, 1019, '1', '2022-12-30 11:47:52', '1', '2022-12-30 11:47:52', b'0', 129);
INSERT INTO `system_role_menu` VALUES (1990, 118, 1020, '1', '2022-12-30 11:47:52', '1', '2022-12-30 11:47:52', b'0', 129);
INSERT INTO `system_role_menu` VALUES (1991, 2, 1024, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1992, 2, 1025, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1993, 2, 1026, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1994, 2, 1027, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1995, 2, 1028, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1996, 2, 1029, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1997, 2, 1030, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1998, 2, 1031, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (1999, 2, 1032, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2000, 2, 1033, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2001, 2, 1034, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2002, 2, 1035, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2003, 2, 1036, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2004, 2, 1037, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2005, 2, 1038, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2006, 2, 1039, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2007, 2, 1040, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2008, 2, 1042, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2009, 2, 1043, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2010, 2, 1045, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2011, 2, 1046, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2012, 2, 1048, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2013, 2, 1050, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2014, 2, 1051, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2015, 2, 1052, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2016, 2, 1053, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2017, 2, 1054, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2018, 2, 1056, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2019, 2, 1057, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2020, 2, 1058, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2021, 2, 2083, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2022, 2, 1059, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2023, 2, 1060, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2024, 2, 1063, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2025, 2, 1064, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2026, 2, 1065, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2027, 2, 1066, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2028, 2, 1067, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2029, 2, 1070, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2034, 2, 1075, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2035, 2, 1076, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2036, 2, 1082, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2037, 2, 1085, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2038, 2, 1086, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2039, 2, 1087, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2040, 2, 1088, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2041, 2, 1089, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2042, 2, 1091, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2043, 2, 1092, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2044, 2, 1095, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2045, 2, 1096, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2046, 2, 1097, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2047, 2, 1098, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2048, 2, 1101, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2049, 2, 1102, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2050, 2, 1103, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2051, 2, 1104, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2052, 2, 1105, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2053, 2, 1106, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2054, 2, 1108, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2055, 2, 1109, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2056, 2, 1111, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2057, 2, 1112, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2058, 2, 1113, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2059, 2, 1114, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2060, 2, 1115, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2061, 2, 1127, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2062, 2, 1128, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2063, 2, 1129, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2064, 2, 1130, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2066, 2, 1132, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2067, 2, 1133, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2068, 2, 1134, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2069, 2, 1135, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2070, 2, 1136, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2071, 2, 1137, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2072, 2, 114, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2073, 2, 1139, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2074, 2, 115, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2075, 2, 1140, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2076, 2, 116, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2077, 2, 1141, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2078, 2, 1142, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2079, 2, 1143, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2080, 2, 1150, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2081, 2, 1161, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2082, 2, 1162, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2083, 2, 1163, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2084, 2, 1164, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2085, 2, 1165, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2086, 2, 1166, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2087, 2, 1173, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2088, 2, 1174, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2089, 2, 1175, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2090, 2, 1176, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2091, 2, 1177, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2092, 2, 1178, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2099, 2, 1226, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2100, 2, 1227, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2101, 2, 1228, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2102, 2, 1229, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2103, 2, 1237, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2104, 2, 1238, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2105, 2, 1239, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2106, 2, 1240, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2107, 2, 1241, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2108, 2, 1242, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2109, 2, 1243, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2110, 2, 1247, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2111, 2, 1248, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2112, 2, 1249, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2113, 2, 1250, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2114, 2, 1251, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2115, 2, 1252, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2116, 2, 1254, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2117, 2, 1255, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2118, 2, 1256, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2119, 2, 1257, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2120, 2, 1258, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2121, 2, 1259, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2122, 2, 1260, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2123, 2, 1261, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2124, 2, 1263, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2125, 2, 1264, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2126, 2, 1265, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2127, 2, 1266, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2128, 2, 1267, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2129, 2, 1001, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2130, 2, 1002, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2131, 2, 1003, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2132, 2, 1004, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2133, 2, 1005, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2134, 2, 1006, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2135, 2, 1007, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2136, 2, 1008, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2137, 2, 1009, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2138, 2, 1010, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2139, 2, 1011, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2140, 2, 1012, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2141, 2, 1013, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2142, 2, 1014, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2143, 2, 1015, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2144, 2, 1016, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2145, 2, 1017, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2146, 2, 1018, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2147, 2, 1019, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2148, 2, 1020, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2149, 2, 1021, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2150, 2, 1022, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2151, 2, 1023, '1', '2023-01-25 08:42:52', '1', '2023-01-25 08:42:52', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2152, 2, 1281, '1', '2023-01-25 08:42:58', '1', '2023-01-25 08:42:58', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2153, 2, 1282, '1', '2023-01-25 08:42:58', '1', '2023-01-25 08:42:58', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2154, 2, 2000, '1', '2023-01-25 08:42:58', '1', '2023-01-25 08:42:58', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2155, 2, 2002, '1', '2023-01-25 08:42:58', '1', '2023-01-25 08:42:58', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2156, 2, 2003, '1', '2023-01-25 08:42:58', '1', '2023-01-25 08:42:58', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2157, 2, 2004, '1', '2023-01-25 08:42:58', '1', '2023-01-25 08:42:58', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2158, 2, 2005, '1', '2023-01-25 08:42:58', '1', '2023-01-25 08:42:58', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2159, 2, 2006, '1', '2023-01-25 08:42:58', '1', '2023-01-25 08:42:58', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2160, 2, 2008, '1', '2023-01-25 08:42:58', '1', '2023-01-25 08:42:58', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2161, 2, 2009, '1', '2023-01-25 08:42:58', '1', '2023-01-25 08:42:58', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2162, 2, 2010, '1', '2023-01-25 08:42:58', '1', '2023-01-25 08:42:58', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2163, 2, 2011, '1', '2023-01-25 08:42:58', '1', '2023-01-25 08:42:58', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2164, 2, 2012, '1', '2023-01-25 08:42:58', '1', '2023-01-25 08:42:58', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2170, 2, 2019, '1', '2023-01-25 08:42:58', '1', '2023-01-25 08:42:58', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2171, 2, 2020, '1', '2023-01-25 08:42:58', '1', '2023-01-25 08:42:58', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2172, 2, 2021, '1', '2023-01-25 08:42:58', '1', '2023-01-25 08:42:58', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2173, 2, 2022, '1', '2023-01-25 08:42:58', '1', '2023-01-25 08:42:58', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2174, 2, 2023, '1', '2023-01-25 08:42:58', '1', '2023-01-25 08:42:58', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2175, 2, 2025, '1', '2023-01-25 08:42:58', '1', '2023-01-25 08:42:58', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2177, 2, 2027, '1', '2023-01-25 08:42:58', '1', '2023-01-25 08:42:58', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2178, 2, 2028, '1', '2023-01-25 08:42:58', '1', '2023-01-25 08:42:58', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2179, 2, 2029, '1', '2023-01-25 08:42:58', '1', '2023-01-25 08:42:58', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2180, 2, 2014, '1', '2023-01-25 08:43:12', '1', '2023-01-25 08:43:12', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2181, 2, 2015, '1', '2023-01-25 08:43:12', '1', '2023-01-25 08:43:12', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2182, 2, 2016, '1', '2023-01-25 08:43:12', '1', '2023-01-25 08:43:12', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2183, 2, 2017, '1', '2023-01-25 08:43:12', '1', '2023-01-25 08:43:12', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2184, 2, 2018, '1', '2023-01-25 08:43:12', '1', '2023-01-25 08:43:12', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2188, 101, 1024, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2189, 101, 1, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2190, 101, 1025, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2191, 101, 1026, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2192, 101, 1027, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2193, 101, 1028, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2194, 101, 1029, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2195, 101, 1030, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2196, 101, 1036, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2197, 101, 1037, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2198, 101, 1038, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2199, 101, 1039, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2200, 101, 1040, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2201, 101, 1042, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2202, 101, 1043, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2203, 101, 1045, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2204, 101, 1046, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2205, 101, 1048, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2206, 101, 2083, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2207, 101, 1063, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2208, 101, 1064, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2209, 101, 1065, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2210, 101, 1093, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2211, 101, 1094, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2212, 101, 1095, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2213, 101, 1096, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2214, 101, 1097, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2215, 101, 1098, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2216, 101, 1100, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2217, 101, 1101, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2218, 101, 1102, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2219, 101, 1103, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2220, 101, 1104, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2221, 101, 1105, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2222, 101, 1106, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2223, 101, 2130, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2224, 101, 1107, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2225, 101, 2131, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2226, 101, 1108, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2227, 101, 2132, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2228, 101, 1109, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2229, 101, 2133, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2230, 101, 2134, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2231, 101, 1110, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2232, 101, 2135, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2233, 101, 1111, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2234, 101, 2136, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2235, 101, 1112, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2236, 101, 2137, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2237, 101, 1113, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2238, 101, 2138, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2239, 101, 1114, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2240, 101, 2139, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2241, 101, 1115, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2242, 101, 2140, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2243, 101, 2141, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2244, 101, 2142, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2245, 101, 2143, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2246, 101, 2144, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2247, 101, 2145, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2248, 101, 2146, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2249, 101, 2147, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2250, 101, 100, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2251, 101, 2148, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2252, 101, 101, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2253, 101, 2149, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2254, 101, 102, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2255, 101, 2150, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2256, 101, 103, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2257, 101, 2151, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2258, 101, 104, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2259, 101, 2152, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2260, 101, 105, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2261, 101, 107, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2262, 101, 108, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2263, 101, 109, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2264, 101, 1138, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2265, 101, 1139, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2266, 101, 1140, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2267, 101, 1141, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2268, 101, 1142, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2269, 101, 1143, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2270, 101, 1224, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2271, 101, 1225, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2272, 101, 1226, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2273, 101, 1227, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2274, 101, 1228, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2275, 101, 1229, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2276, 101, 1247, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2277, 101, 1248, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2278, 101, 1249, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2279, 101, 1250, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2280, 101, 1251, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2281, 101, 1252, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2282, 101, 1261, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2283, 101, 1263, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2284, 101, 1264, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2285, 101, 1265, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2286, 101, 1266, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2287, 101, 1267, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2288, 101, 1001, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2289, 101, 1002, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2290, 101, 1003, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2291, 101, 1004, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2292, 101, 1005, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2293, 101, 1006, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2294, 101, 1007, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2295, 101, 1008, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2296, 101, 1009, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2297, 101, 1010, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2298, 101, 1011, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2299, 101, 1012, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2300, 101, 500, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2301, 101, 1013, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2302, 101, 501, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2303, 101, 1014, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2304, 101, 1015, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2305, 101, 1016, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2306, 101, 1017, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2307, 101, 1018, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2308, 101, 1019, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2309, 101, 1020, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2310, 101, 1021, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2311, 101, 1022, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2312, 101, 1023, '1', '2023-02-09 23:49:46', '1', '2023-02-09 23:49:46', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2789, 136, 1, '1', '2023-03-05 21:23:32', '1', '2023-03-05 21:23:32', b'0', 147);
INSERT INTO `system_role_menu` VALUES (2790, 136, 1036, '1', '2023-03-05 21:23:32', '1', '2023-03-05 21:23:32', b'0', 147);
INSERT INTO `system_role_menu` VALUES (2791, 136, 1037, '1', '2023-03-05 21:23:32', '1', '2023-03-05 21:23:32', b'0', 147);
INSERT INTO `system_role_menu` VALUES (2792, 136, 1038, '1', '2023-03-05 21:23:32', '1', '2023-03-05 21:23:32', b'0', 147);
INSERT INTO `system_role_menu` VALUES (2793, 136, 1039, '1', '2023-03-05 21:23:32', '1', '2023-03-05 21:23:32', b'0', 147);
INSERT INTO `system_role_menu` VALUES (2794, 136, 100, '1', '2023-03-05 21:23:32', '1', '2023-03-05 21:23:32', b'0', 147);
INSERT INTO `system_role_menu` VALUES (2795, 136, 101, '1', '2023-03-05 21:23:32', '1', '2023-03-05 21:23:32', b'0', 147);
INSERT INTO `system_role_menu` VALUES (2796, 136, 1063, '1', '2023-03-05 21:23:32', '1', '2023-03-05 21:23:32', b'0', 147);
INSERT INTO `system_role_menu` VALUES (2797, 136, 103, '1', '2023-03-05 21:23:32', '1', '2023-03-05 21:23:32', b'0', 147);
INSERT INTO `system_role_menu` VALUES (2798, 136, 1064, '1', '2023-03-05 21:23:32', '1', '2023-03-05 21:23:32', b'0', 147);
INSERT INTO `system_role_menu` VALUES (2799, 136, 1001, '1', '2023-03-05 21:23:32', '1', '2023-03-05 21:23:32', b'0', 147);
INSERT INTO `system_role_menu` VALUES (2800, 136, 1065, '1', '2023-03-05 21:23:32', '1', '2023-03-05 21:23:32', b'0', 147);
INSERT INTO `system_role_menu` VALUES (2801, 136, 1002, '1', '2023-03-05 21:23:32', '1', '2023-03-05 21:23:32', b'0', 147);
INSERT INTO `system_role_menu` VALUES (2802, 136, 1003, '1', '2023-03-05 21:23:32', '1', '2023-03-05 21:23:32', b'0', 147);
INSERT INTO `system_role_menu` VALUES (2803, 136, 107, '1', '2023-03-05 21:23:32', '1', '2023-03-05 21:23:32', b'0', 147);
INSERT INTO `system_role_menu` VALUES (2804, 136, 1004, '1', '2023-03-05 21:23:32', '1', '2023-03-05 21:23:32', b'0', 147);
INSERT INTO `system_role_menu` VALUES (2805, 136, 1005, '1', '2023-03-05 21:23:32', '1', '2023-03-05 21:23:32', b'0', 147);
INSERT INTO `system_role_menu` VALUES (2806, 136, 1006, '1', '2023-03-05 21:23:32', '1', '2023-03-05 21:23:32', b'0', 147);
INSERT INTO `system_role_menu` VALUES (2807, 136, 1007, '1', '2023-03-05 21:23:32', '1', '2023-03-05 21:23:32', b'0', 147);
INSERT INTO `system_role_menu` VALUES (2808, 136, 1008, '1', '2023-03-05 21:23:32', '1', '2023-03-05 21:23:32', b'0', 147);
INSERT INTO `system_role_menu` VALUES (2809, 136, 1009, '1', '2023-03-05 21:23:32', '1', '2023-03-05 21:23:32', b'0', 147);
INSERT INTO `system_role_menu` VALUES (2810, 136, 1010, '1', '2023-03-05 21:23:32', '1', '2023-03-05 21:23:32', b'0', 147);
INSERT INTO `system_role_menu` VALUES (2811, 136, 1011, '1', '2023-03-05 21:23:32', '1', '2023-03-05 21:23:32', b'0', 147);
INSERT INTO `system_role_menu` VALUES (2812, 136, 1012, '1', '2023-03-05 21:23:32', '1', '2023-03-05 21:23:32', b'0', 147);
INSERT INTO `system_role_menu` VALUES (2813, 136, 1017, '1', '2023-03-05 21:23:32', '1', '2023-03-05 21:23:32', b'0', 147);
INSERT INTO `system_role_menu` VALUES (2814, 136, 1018, '1', '2023-03-05 21:23:32', '1', '2023-03-05 21:23:32', b'0', 147);
INSERT INTO `system_role_menu` VALUES (2815, 136, 1019, '1', '2023-03-05 21:23:32', '1', '2023-03-05 21:23:32', b'0', 147);
INSERT INTO `system_role_menu` VALUES (2816, 136, 1020, '1', '2023-03-05 21:23:32', '1', '2023-03-05 21:23:32', b'0', 147);
INSERT INTO `system_role_menu` VALUES (2817, 137, 1, '1', '2023-03-05 21:42:27', '1', '2023-03-05 21:42:27', b'0', 148);
INSERT INTO `system_role_menu` VALUES (2818, 137, 1036, '1', '2023-03-05 21:42:27', '1', '2023-03-05 21:42:27', b'0', 148);
INSERT INTO `system_role_menu` VALUES (2819, 137, 1037, '1', '2023-03-05 21:42:27', '1', '2023-03-05 21:42:27', b'0', 148);
INSERT INTO `system_role_menu` VALUES (2820, 137, 1038, '1', '2023-03-05 21:42:27', '1', '2023-03-05 21:42:27', b'0', 148);
INSERT INTO `system_role_menu` VALUES (2821, 137, 1039, '1', '2023-03-05 21:42:27', '1', '2023-03-05 21:42:27', b'0', 148);
INSERT INTO `system_role_menu` VALUES (2822, 137, 100, '1', '2023-03-05 21:42:27', '1', '2023-03-05 21:42:27', b'0', 148);
INSERT INTO `system_role_menu` VALUES (2823, 137, 101, '1', '2023-03-05 21:42:27', '1', '2023-03-05 21:42:27', b'0', 148);
INSERT INTO `system_role_menu` VALUES (2824, 137, 1063, '1', '2023-03-05 21:42:27', '1', '2023-03-05 21:42:27', b'0', 148);
INSERT INTO `system_role_menu` VALUES (2825, 137, 103, '1', '2023-03-05 21:42:27', '1', '2023-03-05 21:42:27', b'0', 148);
INSERT INTO `system_role_menu` VALUES (2826, 137, 1064, '1', '2023-03-05 21:42:27', '1', '2023-03-05 21:42:27', b'0', 148);
INSERT INTO `system_role_menu` VALUES (2827, 137, 1001, '1', '2023-03-05 21:42:27', '1', '2023-03-05 21:42:27', b'0', 148);
INSERT INTO `system_role_menu` VALUES (2828, 137, 1065, '1', '2023-03-05 21:42:27', '1', '2023-03-05 21:42:27', b'0', 148);
INSERT INTO `system_role_menu` VALUES (2829, 137, 1002, '1', '2023-03-05 21:42:27', '1', '2023-03-05 21:42:27', b'0', 148);
INSERT INTO `system_role_menu` VALUES (2830, 137, 1003, '1', '2023-03-05 21:42:27', '1', '2023-03-05 21:42:27', b'0', 148);
INSERT INTO `system_role_menu` VALUES (2831, 137, 107, '1', '2023-03-05 21:42:27', '1', '2023-03-05 21:42:27', b'0', 148);
INSERT INTO `system_role_menu` VALUES (2832, 137, 1004, '1', '2023-03-05 21:42:27', '1', '2023-03-05 21:42:27', b'0', 148);
INSERT INTO `system_role_menu` VALUES (2833, 137, 1005, '1', '2023-03-05 21:42:27', '1', '2023-03-05 21:42:27', b'0', 148);
INSERT INTO `system_role_menu` VALUES (2834, 137, 1006, '1', '2023-03-05 21:42:27', '1', '2023-03-05 21:42:27', b'0', 148);
INSERT INTO `system_role_menu` VALUES (2835, 137, 1007, '1', '2023-03-05 21:42:27', '1', '2023-03-05 21:42:27', b'0', 148);
INSERT INTO `system_role_menu` VALUES (2836, 137, 1008, '1', '2023-03-05 21:42:27', '1', '2023-03-05 21:42:27', b'0', 148);
INSERT INTO `system_role_menu` VALUES (2837, 137, 1009, '1', '2023-03-05 21:42:27', '1', '2023-03-05 21:42:27', b'0', 148);
INSERT INTO `system_role_menu` VALUES (2838, 137, 1010, '1', '2023-03-05 21:42:27', '1', '2023-03-05 21:42:27', b'0', 148);
INSERT INTO `system_role_menu` VALUES (2839, 137, 1011, '1', '2023-03-05 21:42:27', '1', '2023-03-05 21:42:27', b'0', 148);
INSERT INTO `system_role_menu` VALUES (2840, 137, 1012, '1', '2023-03-05 21:42:27', '1', '2023-03-05 21:42:27', b'0', 148);
INSERT INTO `system_role_menu` VALUES (2841, 137, 1017, '1', '2023-03-05 21:42:27', '1', '2023-03-05 21:42:27', b'0', 148);
INSERT INTO `system_role_menu` VALUES (2842, 137, 1018, '1', '2023-03-05 21:42:27', '1', '2023-03-05 21:42:27', b'0', 148);
INSERT INTO `system_role_menu` VALUES (2843, 137, 1019, '1', '2023-03-05 21:42:27', '1', '2023-03-05 21:42:27', b'0', 148);
INSERT INTO `system_role_menu` VALUES (2844, 137, 1020, '1', '2023-03-05 21:42:27', '1', '2023-03-05 21:42:27', b'0', 148);
INSERT INTO `system_role_menu` VALUES (2845, 138, 1, '1', '2023-03-05 21:59:02', '1', '2023-03-05 21:59:02', b'0', 149);
INSERT INTO `system_role_menu` VALUES (2846, 138, 1036, '1', '2023-03-05 21:59:02', '1', '2023-03-05 21:59:02', b'0', 149);
INSERT INTO `system_role_menu` VALUES (2847, 138, 1037, '1', '2023-03-05 21:59:02', '1', '2023-03-05 21:59:02', b'0', 149);
INSERT INTO `system_role_menu` VALUES (2848, 138, 1038, '1', '2023-03-05 21:59:02', '1', '2023-03-05 21:59:02', b'0', 149);
INSERT INTO `system_role_menu` VALUES (2849, 138, 1039, '1', '2023-03-05 21:59:02', '1', '2023-03-05 21:59:02', b'0', 149);
INSERT INTO `system_role_menu` VALUES (2850, 138, 100, '1', '2023-03-05 21:59:02', '1', '2023-03-05 21:59:02', b'0', 149);
INSERT INTO `system_role_menu` VALUES (2851, 138, 101, '1', '2023-03-05 21:59:02', '1', '2023-03-05 21:59:02', b'0', 149);
INSERT INTO `system_role_menu` VALUES (2852, 138, 1063, '1', '2023-03-05 21:59:02', '1', '2023-03-05 21:59:02', b'0', 149);
INSERT INTO `system_role_menu` VALUES (2853, 138, 103, '1', '2023-03-05 21:59:02', '1', '2023-03-05 21:59:02', b'0', 149);
INSERT INTO `system_role_menu` VALUES (2854, 138, 1064, '1', '2023-03-05 21:59:02', '1', '2023-03-05 21:59:02', b'0', 149);
INSERT INTO `system_role_menu` VALUES (2855, 138, 1001, '1', '2023-03-05 21:59:02', '1', '2023-03-05 21:59:02', b'0', 149);
INSERT INTO `system_role_menu` VALUES (2856, 138, 1065, '1', '2023-03-05 21:59:02', '1', '2023-03-05 21:59:02', b'0', 149);
INSERT INTO `system_role_menu` VALUES (2857, 138, 1002, '1', '2023-03-05 21:59:02', '1', '2023-03-05 21:59:02', b'0', 149);
INSERT INTO `system_role_menu` VALUES (2858, 138, 1003, '1', '2023-03-05 21:59:02', '1', '2023-03-05 21:59:02', b'0', 149);
INSERT INTO `system_role_menu` VALUES (2859, 138, 107, '1', '2023-03-05 21:59:02', '1', '2023-03-05 21:59:02', b'0', 149);
INSERT INTO `system_role_menu` VALUES (2860, 138, 1004, '1', '2023-03-05 21:59:02', '1', '2023-03-05 21:59:02', b'0', 149);
INSERT INTO `system_role_menu` VALUES (2861, 138, 1005, '1', '2023-03-05 21:59:02', '1', '2023-03-05 21:59:02', b'0', 149);
INSERT INTO `system_role_menu` VALUES (2862, 138, 1006, '1', '2023-03-05 21:59:02', '1', '2023-03-05 21:59:02', b'0', 149);
INSERT INTO `system_role_menu` VALUES (2863, 138, 1007, '1', '2023-03-05 21:59:02', '1', '2023-03-05 21:59:02', b'0', 149);
INSERT INTO `system_role_menu` VALUES (2864, 138, 1008, '1', '2023-03-05 21:59:02', '1', '2023-03-05 21:59:02', b'0', 149);
INSERT INTO `system_role_menu` VALUES (2865, 138, 1009, '1', '2023-03-05 21:59:02', '1', '2023-03-05 21:59:02', b'0', 149);
INSERT INTO `system_role_menu` VALUES (2866, 138, 1010, '1', '2023-03-05 21:59:02', '1', '2023-03-05 21:59:02', b'0', 149);
INSERT INTO `system_role_menu` VALUES (2867, 138, 1011, '1', '2023-03-05 21:59:02', '1', '2023-03-05 21:59:02', b'0', 149);
INSERT INTO `system_role_menu` VALUES (2868, 138, 1012, '1', '2023-03-05 21:59:02', '1', '2023-03-05 21:59:02', b'0', 149);
INSERT INTO `system_role_menu` VALUES (2869, 138, 1017, '1', '2023-03-05 21:59:02', '1', '2023-03-05 21:59:02', b'0', 149);
INSERT INTO `system_role_menu` VALUES (2870, 138, 1018, '1', '2023-03-05 21:59:02', '1', '2023-03-05 21:59:02', b'0', 149);
INSERT INTO `system_role_menu` VALUES (2871, 138, 1019, '1', '2023-03-05 21:59:02', '1', '2023-03-05 21:59:02', b'0', 149);
INSERT INTO `system_role_menu` VALUES (2872, 138, 1020, '1', '2023-03-05 21:59:02', '1', '2023-03-05 21:59:02', b'0', 149);
INSERT INTO `system_role_menu` VALUES (2873, 139, 1, '1', '2023-07-25 23:06:04', '1', '2023-07-25 23:06:04', b'0', 150);
INSERT INTO `system_role_menu` VALUES (2874, 139, 1036, '1', '2023-07-25 23:06:04', '1', '2023-07-25 23:06:04', b'0', 150);
INSERT INTO `system_role_menu` VALUES (2875, 139, 1037, '1', '2023-07-25 23:06:04', '1', '2023-07-25 23:06:04', b'0', 150);
INSERT INTO `system_role_menu` VALUES (2876, 139, 1038, '1', '2023-07-25 23:06:04', '1', '2023-07-25 23:06:04', b'0', 150);
INSERT INTO `system_role_menu` VALUES (2877, 139, 1039, '1', '2023-07-25 23:06:04', '1', '2023-07-25 23:06:04', b'0', 150);
INSERT INTO `system_role_menu` VALUES (2878, 139, 100, '1', '2023-07-25 23:06:04', '1', '2023-07-25 23:06:04', b'0', 150);
INSERT INTO `system_role_menu` VALUES (2879, 139, 101, '1', '2023-07-25 23:06:04', '1', '2023-07-25 23:06:04', b'0', 150);
INSERT INTO `system_role_menu` VALUES (2880, 139, 1063, '1', '2023-07-25 23:06:04', '1', '2023-07-25 23:06:04', b'0', 150);
INSERT INTO `system_role_menu` VALUES (2881, 139, 103, '1', '2023-07-25 23:06:04', '1', '2023-07-25 23:06:04', b'0', 150);
INSERT INTO `system_role_menu` VALUES (2882, 139, 1064, '1', '2023-07-25 23:06:04', '1', '2023-07-25 23:06:04', b'0', 150);
INSERT INTO `system_role_menu` VALUES (2883, 139, 1001, '1', '2023-07-25 23:06:04', '1', '2023-07-25 23:06:04', b'0', 150);
INSERT INTO `system_role_menu` VALUES (2884, 139, 1065, '1', '2023-07-25 23:06:04', '1', '2023-07-25 23:06:04', b'0', 150);
INSERT INTO `system_role_menu` VALUES (2885, 139, 1002, '1', '2023-07-25 23:06:04', '1', '2023-07-25 23:06:04', b'0', 150);
INSERT INTO `system_role_menu` VALUES (2886, 139, 1003, '1', '2023-07-25 23:06:04', '1', '2023-07-25 23:06:04', b'0', 150);
INSERT INTO `system_role_menu` VALUES (2887, 139, 107, '1', '2023-07-25 23:06:04', '1', '2023-07-25 23:06:04', b'0', 150);
INSERT INTO `system_role_menu` VALUES (2888, 139, 1004, '1', '2023-07-25 23:06:04', '1', '2023-07-25 23:06:04', b'0', 150);
INSERT INTO `system_role_menu` VALUES (2889, 139, 1005, '1', '2023-07-25 23:06:04', '1', '2023-07-25 23:06:04', b'0', 150);
INSERT INTO `system_role_menu` VALUES (2890, 139, 1006, '1', '2023-07-25 23:06:04', '1', '2023-07-25 23:06:04', b'0', 150);
INSERT INTO `system_role_menu` VALUES (2891, 139, 1007, '1', '2023-07-25 23:06:04', '1', '2023-07-25 23:06:04', b'0', 150);
INSERT INTO `system_role_menu` VALUES (2892, 139, 1008, '1', '2023-07-25 23:06:04', '1', '2023-07-25 23:06:04', b'0', 150);
INSERT INTO `system_role_menu` VALUES (2893, 139, 1009, '1', '2023-07-25 23:06:04', '1', '2023-07-25 23:06:04', b'0', 150);
INSERT INTO `system_role_menu` VALUES (2894, 139, 1010, '1', '2023-07-25 23:06:04', '1', '2023-07-25 23:06:04', b'0', 150);
INSERT INTO `system_role_menu` VALUES (2895, 139, 1011, '1', '2023-07-25 23:06:04', '1', '2023-07-25 23:06:04', b'0', 150);
INSERT INTO `system_role_menu` VALUES (2896, 139, 1012, '1', '2023-07-25 23:06:04', '1', '2023-07-25 23:06:04', b'0', 150);
INSERT INTO `system_role_menu` VALUES (2897, 139, 1017, '1', '2023-07-25 23:06:04', '1', '2023-07-25 23:06:04', b'0', 150);
INSERT INTO `system_role_menu` VALUES (2898, 139, 1018, '1', '2023-07-25 23:06:04', '1', '2023-07-25 23:06:04', b'0', 150);
INSERT INTO `system_role_menu` VALUES (2899, 139, 1019, '1', '2023-07-25 23:06:04', '1', '2023-07-25 23:06:04', b'0', 150);
INSERT INTO `system_role_menu` VALUES (2900, 139, 1020, '1', '2023-07-25 23:06:04', '1', '2023-07-25 23:06:04', b'0', 150);
INSERT INTO `system_role_menu` VALUES (2901, 101, 1117, '1', '2023-12-02 21:33:13', '1', '2023-12-02 21:33:13', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2902, 101, 1126, '1', '2023-12-02 21:33:13', '1', '2023-12-02 21:33:13', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2903, 101, 1127, '1', '2023-12-02 21:33:13', '1', '2023-12-02 21:33:13', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2904, 101, 1128, '1', '2023-12-02 21:33:13', '1', '2023-12-02 21:33:13', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2905, 101, 1129, '1', '2023-12-02 21:33:13', '1', '2023-12-02 21:33:13', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2906, 101, 1130, '1', '2023-12-02 21:33:13', '1', '2023-12-02 21:33:13', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2907, 101, 1132, '1', '2023-12-02 21:33:13', '1', '2023-12-02 21:33:13', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2908, 101, 1133, '1', '2023-12-02 21:33:13', '1', '2023-12-02 21:33:13', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2909, 101, 1134, '1', '2023-12-02 21:33:13', '1', '2023-12-02 21:33:13', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2910, 101, 1135, '1', '2023-12-02 21:33:13', '1', '2023-12-02 21:33:13', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2911, 101, 1136, '1', '2023-12-02 21:33:13', '1', '2023-12-02 21:33:13', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2912, 101, 1137, '1', '2023-12-02 21:33:13', '1', '2023-12-02 21:33:13', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2913, 101, 2161, '1', '2023-12-02 21:33:13', '1', '2023-12-02 21:33:13', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2914, 101, 1150, '1', '2023-12-02 21:33:13', '1', '2023-12-02 21:33:13', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2915, 101, 1161, '1', '2023-12-02 21:33:13', '1', '2023-12-02 21:33:13', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2916, 101, 1162, '1', '2023-12-02 21:33:13', '1', '2023-12-02 21:33:13', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2917, 101, 1163, '1', '2023-12-02 21:33:13', '1', '2023-12-02 21:33:13', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2918, 101, 1164, '1', '2023-12-02 21:33:13', '1', '2023-12-02 21:33:13', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2919, 101, 1165, '1', '2023-12-02 21:33:13', '1', '2023-12-02 21:33:13', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2920, 101, 1166, '1', '2023-12-02 21:33:13', '1', '2023-12-02 21:33:13', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2921, 101, 1173, '1', '2023-12-02 21:33:13', '1', '2023-12-02 21:33:13', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2922, 101, 1174, '1', '2023-12-02 21:33:13', '1', '2023-12-02 21:33:13', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2923, 101, 1175, '1', '2023-12-02 21:33:13', '1', '2023-12-02 21:33:13', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2924, 101, 1176, '1', '2023-12-02 21:33:13', '1', '2023-12-02 21:33:13', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2925, 101, 1177, '1', '2023-12-02 21:33:13', '1', '2023-12-02 21:33:13', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2926, 101, 1178, '1', '2023-12-02 21:33:13', '1', '2023-12-02 21:33:13', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2927, 101, 2301, '1', '2023-12-02 21:33:13', '1', '2023-12-02 21:33:13', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2928, 101, 2302, '1', '2023-12-02 21:33:13', '1', '2023-12-02 21:33:13', b'0', 1);
INSERT INTO `system_role_menu` VALUES (2929, 109, 1224, '1', '2023-12-02 23:19:40', '1', '2023-12-02 23:19:40', b'0', 121);
INSERT INTO `system_role_menu` VALUES (2930, 109, 1225, '1', '2023-12-02 23:19:40', '1', '2023-12-02 23:19:40', b'0', 121);
INSERT INTO `system_role_menu` VALUES (2931, 109, 1226, '1', '2023-12-02 23:19:40', '1', '2023-12-02 23:19:40', b'0', 121);
INSERT INTO `system_role_menu` VALUES (2932, 109, 1227, '1', '2023-12-02 23:19:40', '1', '2023-12-02 23:19:40', b'0', 121);
INSERT INTO `system_role_menu` VALUES (2933, 109, 1228, '1', '2023-12-02 23:19:40', '1', '2023-12-02 23:19:40', b'0', 121);
INSERT INTO `system_role_menu` VALUES (2934, 109, 1229, '1', '2023-12-02 23:19:40', '1', '2023-12-02 23:19:40', b'0', 121);
INSERT INTO `system_role_menu` VALUES (2935, 109, 1138, '1', '2023-12-02 23:19:40', '1', '2023-12-02 23:19:40', b'0', 121);
INSERT INTO `system_role_menu` VALUES (2936, 109, 1139, '1', '2023-12-02 23:19:40', '1', '2023-12-02 23:19:40', b'0', 121);
INSERT INTO `system_role_menu` VALUES (2937, 109, 1140, '1', '2023-12-02 23:19:40', '1', '2023-12-02 23:19:40', b'0', 121);
INSERT INTO `system_role_menu` VALUES (2938, 109, 1141, '1', '2023-12-02 23:19:40', '1', '2023-12-02 23:19:40', b'0', 121);
INSERT INTO `system_role_menu` VALUES (2939, 109, 1142, '1', '2023-12-02 23:19:40', '1', '2023-12-02 23:19:40', b'0', 121);
INSERT INTO `system_role_menu` VALUES (2940, 109, 1143, '1', '2023-12-02 23:19:40', '1', '2023-12-02 23:19:40', b'0', 121);
INSERT INTO `system_role_menu` VALUES (2941, 111, 1224, '1', '2023-12-02 23:19:40', '1', '2023-12-02 23:19:40', b'0', 122);
INSERT INTO `system_role_menu` VALUES (2942, 111, 1225, '1', '2023-12-02 23:19:40', '1', '2023-12-02 23:19:40', b'0', 122);
INSERT INTO `system_role_menu` VALUES (2943, 111, 1226, '1', '2023-12-02 23:19:40', '1', '2023-12-02 23:19:40', b'0', 122);
INSERT INTO `system_role_menu` VALUES (2944, 111, 1227, '1', '2023-12-02 23:19:40', '1', '2023-12-02 23:19:40', b'0', 122);
INSERT INTO `system_role_menu` VALUES (2945, 111, 1228, '1', '2023-12-02 23:19:40', '1', '2023-12-02 23:19:40', b'0', 122);
INSERT INTO `system_role_menu` VALUES (2946, 111, 1229, '1', '2023-12-02 23:19:40', '1', '2023-12-02 23:19:40', b'0', 122);
INSERT INTO `system_role_menu` VALUES (2947, 111, 1138, '1', '2023-12-02 23:19:40', '1', '2023-12-02 23:19:40', b'0', 122);
INSERT INTO `system_role_menu` VALUES (2948, 111, 1139, '1', '2023-12-02 23:19:40', '1', '2023-12-02 23:19:40', b'0', 122);
INSERT INTO `system_role_menu` VALUES (2949, 111, 1140, '1', '2023-12-02 23:19:40', '1', '2023-12-02 23:19:40', b'0', 122);
INSERT INTO `system_role_menu` VALUES (2950, 111, 1141, '1', '2023-12-02 23:19:40', '1', '2023-12-02 23:19:40', b'0', 122);
INSERT INTO `system_role_menu` VALUES (2951, 111, 1142, '1', '2023-12-02 23:19:40', '1', '2023-12-02 23:19:40', b'0', 122);
INSERT INTO `system_role_menu` VALUES (2952, 111, 1143, '1', '2023-12-02 23:19:40', '1', '2023-12-02 23:19:40', b'0', 122);
INSERT INTO `system_role_menu` VALUES (2953, 140, 1, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_role_menu` VALUES (2954, 140, 1224, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_role_menu` VALUES (2955, 140, 1225, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_role_menu` VALUES (2956, 140, 1226, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_role_menu` VALUES (2957, 140, 1227, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_role_menu` VALUES (2958, 140, 1228, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_role_menu` VALUES (2959, 140, 1036, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_role_menu` VALUES (2960, 140, 1229, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_role_menu` VALUES (2961, 140, 1037, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_role_menu` VALUES (2962, 140, 1038, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_role_menu` VALUES (2963, 140, 1039, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_role_menu` VALUES (2964, 140, 100, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_role_menu` VALUES (2965, 140, 101, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_role_menu` VALUES (2966, 140, 1063, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_role_menu` VALUES (2967, 140, 103, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_role_menu` VALUES (2968, 140, 1064, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_role_menu` VALUES (2969, 140, 1001, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_role_menu` VALUES (2970, 140, 1065, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_role_menu` VALUES (2971, 140, 1002, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_role_menu` VALUES (2972, 140, 1003, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_role_menu` VALUES (2973, 140, 107, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_role_menu` VALUES (2974, 140, 1004, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_role_menu` VALUES (2975, 140, 1005, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_role_menu` VALUES (2976, 140, 1006, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_role_menu` VALUES (2977, 140, 1007, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_role_menu` VALUES (2978, 140, 1008, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_role_menu` VALUES (2979, 140, 1009, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_role_menu` VALUES (2980, 140, 1138, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_role_menu` VALUES (2981, 140, 1010, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_role_menu` VALUES (2982, 140, 1139, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_role_menu` VALUES (2983, 140, 1011, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_role_menu` VALUES (2984, 140, 1140, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_role_menu` VALUES (2985, 140, 1012, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_role_menu` VALUES (2986, 140, 1141, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_role_menu` VALUES (2987, 140, 1142, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_role_menu` VALUES (2988, 140, 1143, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_role_menu` VALUES (2989, 140, 1017, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_role_menu` VALUES (2990, 140, 1018, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_role_menu` VALUES (2991, 140, 1019, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_role_menu` VALUES (2992, 140, 1020, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_role_menu` VALUES (2993, 109, 2, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (2994, 109, 1031, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (2995, 109, 1032, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (2996, 109, 1033, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (2997, 109, 1034, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (2998, 109, 1035, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (2999, 109, 1050, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3000, 109, 1051, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3001, 109, 1052, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3002, 109, 1053, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3003, 109, 1054, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3004, 109, 1056, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3005, 109, 1057, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3006, 109, 1058, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3007, 109, 1059, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3008, 109, 1060, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3009, 109, 1066, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3010, 109, 1067, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3011, 109, 1070, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3012, 109, 1075, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3013, 109, 1076, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3014, 109, 1077, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3015, 109, 1078, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3016, 109, 1082, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3017, 109, 1083, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3018, 109, 1084, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3019, 109, 1085, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3020, 109, 1086, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3021, 109, 1087, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3022, 109, 1088, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3023, 109, 1089, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3024, 109, 1090, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3025, 109, 1091, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3026, 109, 1092, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3027, 109, 106, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3028, 109, 110, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3029, 109, 111, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3030, 109, 112, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3031, 109, 113, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3032, 109, 114, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3033, 109, 115, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3034, 109, 116, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3035, 109, 2472, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3036, 109, 2478, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3037, 109, 2479, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3038, 109, 2480, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3039, 109, 2481, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3040, 109, 2482, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3041, 109, 2483, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3042, 109, 2484, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3043, 109, 2485, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3044, 109, 2486, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3045, 109, 2487, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3046, 109, 2488, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3047, 109, 2489, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3048, 109, 2490, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3049, 109, 2491, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3050, 109, 2492, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3051, 109, 2493, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3052, 109, 2494, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3053, 109, 2495, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3054, 109, 2497, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3055, 109, 1237, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3056, 109, 1238, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3057, 109, 1239, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3058, 109, 1240, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3059, 109, 1241, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3060, 109, 1242, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3061, 109, 1243, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3062, 109, 2525, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3063, 109, 1255, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3064, 109, 1256, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3065, 109, 1257, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3066, 109, 1258, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3067, 109, 1259, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3068, 109, 1260, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3069, 111, 2, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3070, 111, 1031, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3071, 111, 1032, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3072, 111, 1033, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3073, 111, 1034, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3074, 111, 1035, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3075, 111, 1050, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3076, 111, 1051, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3077, 111, 1052, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3078, 111, 1053, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3079, 111, 1054, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3080, 111, 1056, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3081, 111, 1057, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3082, 111, 1058, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3083, 111, 1059, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3084, 111, 1060, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3085, 111, 1066, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3086, 111, 1067, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3087, 111, 1070, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3088, 111, 1075, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3089, 111, 1076, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3090, 111, 1077, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3091, 111, 1078, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3092, 111, 1082, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3093, 111, 1083, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3094, 111, 1084, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3095, 111, 1085, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3096, 111, 1086, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3097, 111, 1087, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3098, 111, 1088, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3099, 111, 1089, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3100, 111, 1090, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3101, 111, 1091, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3102, 111, 1092, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3103, 111, 106, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3104, 111, 110, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3105, 111, 111, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3106, 111, 112, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3107, 111, 113, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3108, 111, 114, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3109, 111, 115, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3110, 111, 116, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3111, 111, 2472, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3112, 111, 2478, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3113, 111, 2479, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3114, 111, 2480, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3115, 111, 2481, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3116, 111, 2482, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3117, 111, 2483, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3118, 111, 2484, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3119, 111, 2485, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3120, 111, 2486, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3121, 111, 2487, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3122, 111, 2488, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3123, 111, 2489, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3124, 111, 2490, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3125, 111, 2491, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3126, 111, 2492, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3127, 111, 2493, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3128, 111, 2494, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3129, 111, 2495, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3130, 111, 2497, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3131, 111, 1237, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3132, 111, 1238, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3133, 111, 1239, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3134, 111, 1240, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3135, 111, 1241, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3136, 111, 1242, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3137, 111, 1243, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3138, 111, 2525, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3139, 111, 1255, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3140, 111, 1256, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3141, 111, 1257, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3142, 111, 1258, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3143, 111, 1259, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3144, 111, 1260, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3145, 140, 2, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3146, 140, 1031, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3147, 140, 1032, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3148, 140, 1033, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3149, 140, 1034, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3150, 140, 1035, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3151, 140, 1050, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3152, 140, 1051, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3153, 140, 1052, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3154, 140, 1053, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3155, 140, 1054, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3156, 140, 1056, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3157, 140, 1057, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3158, 140, 1058, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3159, 140, 1059, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3160, 140, 1060, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3161, 140, 1066, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3162, 140, 1067, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3163, 140, 1070, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3164, 140, 1075, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3165, 140, 1076, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3166, 140, 1077, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3167, 140, 1078, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3168, 140, 1082, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3169, 140, 1083, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3170, 140, 1084, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3171, 140, 1085, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3172, 140, 1086, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3173, 140, 1087, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3174, 140, 1088, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3175, 140, 1089, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3176, 140, 1090, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3177, 140, 1091, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3178, 140, 1092, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3179, 140, 106, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3180, 140, 110, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3181, 140, 111, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3182, 140, 112, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3183, 140, 113, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3184, 140, 114, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3185, 140, 115, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3186, 140, 116, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3187, 140, 2472, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3188, 140, 2478, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3189, 140, 2479, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3190, 140, 2480, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3191, 140, 2481, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3192, 140, 2482, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3193, 140, 2483, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3194, 140, 2484, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3195, 140, 2485, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3196, 140, 2486, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3197, 140, 2487, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3198, 140, 2488, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3199, 140, 2489, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3200, 140, 2490, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3201, 140, 2491, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3202, 140, 2492, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3203, 140, 2493, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3204, 140, 2494, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3205, 140, 2495, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3206, 140, 2497, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3207, 140, 1237, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3208, 140, 1238, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3209, 140, 1239, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3210, 140, 1240, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3211, 140, 1241, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3212, 140, 1242, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3213, 140, 1243, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3214, 140, 2525, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3215, 140, 1255, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3216, 140, 1256, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3217, 140, 1257, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3218, 140, 1258, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3219, 140, 1259, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3220, 140, 1260, '1', '2023-12-02 23:41:02', '1', '2023-12-02 23:41:02', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3221, 109, 102, '1', '2023-12-30 11:42:36', '1', '2023-12-30 11:42:36', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3222, 109, 1013, '1', '2023-12-30 11:42:36', '1', '2023-12-30 11:42:36', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3223, 109, 1014, '1', '2023-12-30 11:42:36', '1', '2023-12-30 11:42:36', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3224, 109, 1015, '1', '2023-12-30 11:42:36', '1', '2023-12-30 11:42:36', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3225, 109, 1016, '1', '2023-12-30 11:42:36', '1', '2023-12-30 11:42:36', b'0', 121);
INSERT INTO `system_role_menu` VALUES (3226, 111, 102, '1', '2023-12-30 11:42:36', '1', '2023-12-30 11:42:36', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3227, 111, 1013, '1', '2023-12-30 11:42:36', '1', '2023-12-30 11:42:36', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3228, 111, 1014, '1', '2023-12-30 11:42:36', '1', '2023-12-30 11:42:36', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3229, 111, 1015, '1', '2023-12-30 11:42:36', '1', '2023-12-30 11:42:36', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3230, 111, 1016, '1', '2023-12-30 11:42:36', '1', '2023-12-30 11:42:36', b'0', 122);
INSERT INTO `system_role_menu` VALUES (3231, 140, 102, '1', '2023-12-30 11:42:36', '1', '2023-12-30 11:42:36', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3232, 140, 1013, '1', '2023-12-30 11:42:36', '1', '2023-12-30 11:42:36', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3233, 140, 1014, '1', '2023-12-30 11:42:36', '1', '2023-12-30 11:42:36', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3234, 140, 1015, '1', '2023-12-30 11:42:36', '1', '2023-12-30 11:42:36', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3235, 140, 1016, '1', '2023-12-30 11:42:36', '1', '2023-12-30 11:42:36', b'0', 151);
INSERT INTO `system_role_menu` VALUES (3236, 141, 1, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3237, 141, 2, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3238, 141, 1031, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3239, 141, 1032, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3240, 141, 1033, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3241, 141, 1034, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3242, 141, 1035, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3243, 141, 1036, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3244, 141, 1037, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3245, 141, 1038, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3246, 141, 1039, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3247, 141, 1050, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3248, 141, 1051, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3249, 141, 1052, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3250, 141, 1053, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3251, 141, 1054, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3252, 141, 1056, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3253, 141, 1057, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3254, 141, 1058, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3255, 141, 1059, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3256, 141, 1060, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3257, 141, 1063, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3258, 141, 1064, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3259, 141, 1065, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3260, 141, 1066, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3261, 141, 1067, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3262, 141, 1070, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3263, 141, 1075, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3264, 141, 1076, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3265, 141, 1077, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3266, 141, 1078, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3267, 141, 1082, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3268, 141, 1083, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3269, 141, 1084, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3270, 141, 1085, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3271, 141, 1086, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3272, 141, 1087, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3273, 141, 1088, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3274, 141, 1089, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3275, 141, 1090, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3276, 141, 1091, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3277, 141, 1092, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3278, 141, 100, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3279, 141, 101, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3280, 141, 102, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3281, 141, 103, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3282, 141, 106, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3283, 141, 107, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3284, 141, 110, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3285, 141, 111, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3286, 141, 112, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3287, 141, 113, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3288, 141, 1138, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3289, 141, 114, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3290, 141, 1139, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3291, 141, 115, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3292, 141, 1140, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3293, 141, 116, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3294, 141, 1141, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3295, 141, 1142, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3296, 141, 1143, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3297, 141, 2472, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3298, 141, 2478, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3299, 141, 2479, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3300, 141, 2480, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3301, 141, 2481, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3302, 141, 2482, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3303, 141, 2483, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3304, 141, 2484, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3305, 141, 2485, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3306, 141, 2486, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3307, 141, 2487, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3308, 141, 2488, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3309, 141, 2489, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3310, 141, 2490, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3311, 141, 2491, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3312, 141, 2492, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3313, 141, 2493, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3314, 141, 2494, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3315, 141, 2495, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3316, 141, 2497, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3317, 141, 1224, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3318, 141, 1225, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3319, 141, 1226, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3320, 141, 1227, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3321, 141, 1228, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3322, 141, 1229, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3323, 141, 1237, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3324, 141, 1238, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3325, 141, 1239, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3326, 141, 1240, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3327, 141, 1241, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3328, 141, 1242, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3329, 141, 1243, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3330, 141, 2525, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3331, 141, 1255, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3332, 141, 1256, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3333, 141, 1001, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3334, 141, 1257, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3335, 141, 1002, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3336, 141, 1258, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3337, 141, 1003, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3338, 141, 1259, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3339, 141, 1004, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3340, 141, 1260, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3341, 141, 1005, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3342, 141, 1006, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3343, 141, 1007, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3344, 141, 1008, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3345, 141, 1009, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3346, 141, 1010, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3347, 141, 1011, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3348, 141, 1012, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3349, 141, 1013, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3350, 141, 1014, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3351, 141, 1015, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3352, 141, 1016, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3353, 141, 1017, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3354, 141, 1018, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3355, 141, 1019, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3356, 141, 1020, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_role_menu` VALUES (3357, 1, 1024, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3358, 1, 2048, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3359, 1, 1025, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3360, 1, 2049, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3361, 1, 1026, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3362, 1, 2050, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3363, 1, 1027, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3364, 1, 2051, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3365, 1, 1028, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3366, 1, 2052, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3367, 1, 1029, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3368, 1, 5, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3369, 1, 1030, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3370, 1, 1031, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3371, 1, 1032, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3372, 1, 1033, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3373, 1, 1034, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3374, 1, 1035, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3375, 1, 2059, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3376, 1, 1036, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3377, 1, 2060, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3378, 1, 1037, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3379, 1, 2061, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3380, 1, 1038, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3381, 1, 2062, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3382, 1, 1039, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3383, 1, 2063, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3384, 1, 1040, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3385, 1, 1042, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3386, 1, 2066, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3387, 1, 1043, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3388, 1, 2067, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3389, 1, 2068, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3390, 1, 1045, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3391, 1, 2069, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3392, 1, 1046, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3393, 1, 2070, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3394, 1, 1048, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3395, 1, 2072, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3396, 1, 2073, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3397, 1, 1050, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3398, 1, 2074, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3399, 1, 1051, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3400, 1, 2075, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3401, 1, 1052, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3402, 1, 2076, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3403, 1, 1053, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3404, 1, 1054, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3405, 1, 1056, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3406, 1, 1057, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3407, 1, 1058, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3408, 1, 2083, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3409, 1, 1059, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3410, 1, 1060, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3411, 1, 2084, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3412, 1, 2085, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3413, 1, 2086, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3414, 1, 1063, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3415, 1, 2087, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3416, 1, 1064, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3417, 1, 2088, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3418, 1, 1065, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3419, 1, 2089, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3420, 1, 1066, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3421, 1, 2090, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3422, 1, 1067, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3423, 1, 2091, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3424, 1, 2092, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3425, 1, 2093, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3426, 1, 1070, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3427, 1, 2094, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3428, 1, 2095, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3429, 1, 2096, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3430, 1, 2097, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3431, 1, 2098, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3432, 1, 1075, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3433, 1, 2099, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3434, 1, 1076, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3435, 1, 2100, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3436, 1, 2101, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3437, 1, 2102, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3438, 1, 2103, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3439, 1, 2104, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3440, 1, 2105, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3441, 1, 1082, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3442, 1, 2106, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3443, 1, 2107, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3444, 1, 2108, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3445, 1, 1085, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3446, 1, 2109, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3447, 1, 1086, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3448, 1, 2110, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3449, 1, 1087, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3450, 1, 2111, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3451, 1, 1088, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3452, 1, 2112, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3453, 1, 1089, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3454, 1, 2113, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3455, 1, 2114, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3456, 1, 1091, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3457, 1, 2115, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3458, 1, 1092, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3459, 1, 2116, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3460, 1, 2117, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3461, 1, 2118, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3462, 1, 1095, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3463, 1, 2119, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3464, 1, 1096, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3465, 1, 2120, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3466, 1, 1097, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3467, 1, 2121, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3468, 1, 1098, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3469, 1, 2122, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3470, 1, 2123, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3471, 1, 2124, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3472, 1, 1101, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3473, 1, 2125, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3474, 1, 1102, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3475, 1, 2126, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3476, 1, 1103, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3477, 1, 2127, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3478, 1, 1104, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3479, 1, 2128, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3480, 1, 1105, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3481, 1, 2129, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3482, 1, 1106, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3483, 1, 2130, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3484, 1, 2131, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3485, 1, 1108, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3486, 1, 2132, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3487, 1, 1109, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3488, 1, 2133, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3489, 1, 2134, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3490, 1, 2135, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3491, 1, 1111, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3492, 1, 2136, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3493, 1, 1112, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3494, 1, 2137, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3495, 1, 1113, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3496, 1, 2138, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3497, 1, 1114, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3498, 1, 2139, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3499, 1, 1115, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3500, 1, 2140, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3501, 1, 2141, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3502, 1, 2142, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3503, 1, 1118, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3504, 1, 2143, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3505, 1, 1119, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3506, 1, 2144, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3507, 1, 1120, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3508, 1, 2145, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3509, 1, 2146, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3510, 1, 2147, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3511, 1, 2148, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3512, 1, 2149, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3513, 1, 2150, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3514, 1, 2151, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3515, 1, 1127, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3516, 1, 2152, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3517, 1, 1128, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3518, 1, 1129, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3519, 1, 2153, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3520, 1, 1130, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3521, 1, 2154, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3522, 1, 2155, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3523, 1, 1132, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3524, 1, 2156, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3525, 1, 1133, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3526, 1, 2157, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3527, 1, 1134, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3528, 1, 2158, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3529, 1, 2159, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3530, 1, 1135, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3531, 1, 2160, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3532, 1, 1136, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3533, 1, 1137, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3534, 1, 2161, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3535, 1, 114, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3536, 1, 2162, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3537, 1, 1139, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3538, 1, 115, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3539, 1, 1140, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3540, 1, 116, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3541, 1, 2164, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3542, 1, 1141, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3543, 1, 2165, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3544, 1, 1142, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3545, 1, 2166, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3546, 1, 1143, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3547, 1, 2167, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3548, 1, 2168, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3549, 1, 2169, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3550, 1, 2170, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3551, 1, 2171, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3552, 1, 2172, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3553, 1, 2173, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3554, 1, 1150, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3555, 1, 2174, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3556, 1, 2175, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3557, 1, 2176, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3558, 1, 2177, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3559, 1, 2178, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3560, 1, 2179, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3561, 1, 2180, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3562, 1, 2181, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3563, 1, 2182, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3564, 1, 2183, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3565, 1, 2184, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3566, 1, 1161, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3567, 1, 1162, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3568, 1, 1163, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3569, 1, 1164, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3570, 1, 1165, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3571, 1, 1166, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3572, 1, 1173, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3573, 1, 1174, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3574, 1, 1175, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3575, 1, 1176, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3576, 1, 1177, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3577, 1, 1178, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3578, 1, 1185, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3579, 1, 2209, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3580, 1, 1186, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3581, 1, 1187, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3582, 1, 1188, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3583, 1, 1189, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3584, 1, 1190, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3585, 1, 1191, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3586, 1, 1192, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3587, 1, 1193, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3588, 1, 1194, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3589, 1, 1195, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3590, 1, 1196, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3591, 1, 1197, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3592, 1, 1198, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3593, 1, 1199, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3594, 1, 1200, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3595, 1, 1201, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3596, 1, 1202, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3597, 1, 1207, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3598, 1, 1208, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3599, 1, 1209, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3600, 1, 1210, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3601, 1, 1211, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3602, 1, 1212, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3603, 1, 1213, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3604, 1, 1215, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3605, 1, 1216, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3606, 1, 1217, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3607, 1, 1218, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3608, 1, 1219, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3609, 1, 1220, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3610, 1, 1221, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3611, 1, 1222, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3612, 1, 1226, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3613, 1, 1227, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3614, 1, 1228, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3615, 1, 1229, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3616, 1, 1237, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3617, 1, 1238, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3618, 1, 2262, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3619, 1, 1239, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3620, 1, 1240, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3621, 1, 1241, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3622, 1, 1242, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3623, 1, 1243, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3624, 1, 1247, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3625, 1, 1248, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3626, 1, 1249, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3627, 1, 1250, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3628, 1, 1251, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3629, 1, 2275, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3630, 1, 1252, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3631, 1, 2276, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3632, 1, 2277, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3633, 1, 1254, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3634, 1, 1255, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3635, 1, 1256, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3636, 1, 1257, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3637, 1, 2281, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3638, 1, 1258, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3639, 1, 2282, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3640, 1, 1259, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3641, 1, 2283, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3642, 1, 1260, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3643, 1, 2284, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3644, 1, 1261, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3645, 1, 2285, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3646, 1, 1263, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3647, 1, 2287, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3648, 1, 1264, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3649, 1, 2288, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3650, 1, 1265, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3651, 1, 1266, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3652, 1, 1267, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3653, 1, 2293, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3654, 1, 2294, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3655, 1, 2297, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3656, 1, 2300, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3657, 1, 2301, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3658, 1, 2302, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3659, 1, 2303, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3660, 1, 2304, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3661, 1, 1281, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3662, 1, 2305, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3663, 1, 1282, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3664, 1, 2306, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3665, 1, 2307, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3666, 1, 2308, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3667, 1, 2309, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3668, 1, 2310, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3669, 1, 2311, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3670, 1, 2312, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3671, 1, 2313, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3672, 1, 2314, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3673, 1, 2315, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3674, 1, 2316, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3675, 1, 2317, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3676, 1, 2318, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3677, 1, 2319, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3678, 1, 2320, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3679, 1, 2321, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3680, 1, 2322, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3681, 1, 2323, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3682, 1, 2324, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3683, 1, 2325, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3684, 1, 2326, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3685, 1, 2327, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3686, 1, 2328, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3687, 1, 2329, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3688, 1, 2330, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3689, 1, 2331, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3690, 1, 2332, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3691, 1, 2333, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3692, 1, 2334, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3693, 1, 2335, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3694, 1, 2336, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3695, 1, 2337, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3696, 1, 2338, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3697, 1, 2339, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3698, 1, 2340, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3699, 1, 2341, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3700, 1, 2342, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3701, 1, 2343, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3702, 1, 2344, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3703, 1, 2345, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3704, 1, 2346, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3705, 1, 2347, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3706, 1, 2348, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3707, 1, 2349, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3708, 1, 2350, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3709, 1, 2351, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3710, 1, 2352, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3711, 1, 2353, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3712, 1, 2354, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3713, 1, 2355, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3714, 1, 2356, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3715, 1, 2357, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3716, 1, 2358, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3717, 1, 2359, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3718, 1, 2360, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3719, 1, 2361, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3720, 1, 2362, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3721, 1, 2363, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3722, 1, 2364, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3723, 1, 2365, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3724, 1, 2366, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3725, 1, 2367, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3726, 1, 2368, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3727, 1, 2369, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3728, 1, 2374, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3729, 1, 2375, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3730, 1, 2376, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3731, 1, 2377, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3732, 1, 2378, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3733, 1, 2379, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3734, 1, 2380, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3735, 1, 2381, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3736, 1, 2382, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3737, 1, 2383, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3738, 1, 2384, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3739, 1, 2385, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3740, 1, 2386, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3741, 1, 2387, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3742, 1, 2388, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3743, 1, 2389, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3744, 1, 2390, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3745, 1, 2391, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3746, 1, 2392, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3747, 1, 2393, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3748, 1, 2394, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3749, 1, 2395, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3750, 1, 2396, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3751, 1, 2397, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3752, 1, 2398, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3753, 1, 2399, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3754, 1, 2400, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3755, 1, 2401, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3756, 1, 2402, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3757, 1, 2403, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3758, 1, 2404, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3759, 1, 2405, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3760, 1, 2406, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3761, 1, 2407, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3762, 1, 2408, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3763, 1, 2409, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3764, 1, 2410, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3765, 1, 2411, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3766, 1, 2412, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3767, 1, 2413, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3768, 1, 2414, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3769, 1, 2415, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3770, 1, 2416, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3771, 1, 2417, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3772, 1, 2418, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3773, 1, 2419, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3774, 1, 2420, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3775, 1, 2421, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3776, 1, 2422, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3777, 1, 2423, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3778, 1, 2424, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3779, 1, 2425, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3780, 1, 2426, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3781, 1, 2427, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3782, 1, 2428, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3783, 1, 2429, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3784, 1, 2430, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3785, 1, 2431, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3786, 1, 2432, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3787, 1, 2433, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3788, 1, 2435, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3789, 1, 2436, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3790, 1, 2437, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3791, 1, 2438, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3792, 1, 2439, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3793, 1, 2440, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3794, 1, 2441, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3795, 1, 2442, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3796, 1, 2443, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3797, 1, 2444, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3798, 1, 2445, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3799, 1, 2446, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3800, 1, 2447, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3801, 1, 2448, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3802, 1, 2449, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3803, 1, 2450, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3804, 1, 2451, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3805, 1, 2452, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3806, 1, 2453, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3807, 1, 2472, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3808, 1, 2478, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3809, 1, 2479, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3810, 1, 2480, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3811, 1, 2481, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3812, 1, 2482, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3813, 1, 2483, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3814, 1, 2484, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3815, 1, 2485, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3816, 1, 2486, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3817, 1, 2487, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3818, 1, 2488, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3819, 1, 2489, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3820, 1, 2490, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3821, 1, 2491, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3822, 1, 2492, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3823, 1, 2493, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3824, 1, 2494, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3825, 1, 2495, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3826, 1, 2497, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3827, 1, 2516, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3828, 1, 2517, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3829, 1, 2518, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3830, 1, 2519, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3831, 1, 2520, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3832, 1, 2521, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3833, 1, 2522, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3834, 1, 2523, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3835, 1, 2524, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3836, 1, 2525, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3837, 1, 2526, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3838, 1, 2527, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3839, 1, 2528, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3840, 1, 2529, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3841, 1, 2530, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3842, 1, 2531, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3843, 1, 2532, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3844, 1, 2533, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3845, 1, 2534, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3846, 1, 2535, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3847, 1, 2536, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3848, 1, 2537, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3849, 1, 2538, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3850, 1, 2539, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3851, 1, 2540, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3852, 1, 2541, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3853, 1, 2542, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3854, 1, 2543, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3855, 1, 2544, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3856, 1, 2000, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3857, 1, 2002, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3858, 1, 2003, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3859, 1, 2004, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3860, 1, 2005, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3861, 1, 2006, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3862, 1, 2008, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3863, 1, 2009, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3864, 1, 2010, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3865, 1, 2011, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3866, 1, 2012, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3867, 1, 2014, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3868, 1, 2015, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3869, 1, 2016, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3870, 1, 2017, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3871, 1, 2018, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3872, 1, 2019, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3873, 1, 2020, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3874, 1, 2021, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3875, 1, 2022, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3876, 1, 2023, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3877, 1, 1001, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3878, 1, 2025, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3879, 1, 1002, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3880, 1, 2026, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3881, 1, 1003, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3882, 1, 2027, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3883, 1, 1004, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3884, 1, 2028, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3885, 1, 1005, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3886, 1, 2029, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3887, 1, 1006, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3888, 1, 2030, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3889, 1, 1007, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3890, 1, 1008, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3891, 1, 2032, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3892, 1, 1009, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3893, 1, 2033, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3894, 1, 1010, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3895, 1, 2034, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3896, 1, 1011, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3897, 1, 2035, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3898, 1, 1012, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3899, 1, 2036, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3900, 1, 1013, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3901, 1, 1014, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3902, 1, 2038, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3903, 1, 1015, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3904, 1, 2039, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3905, 1, 1016, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3906, 1, 2040, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3907, 1, 1017, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3908, 1, 2041, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3909, 1, 1018, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3910, 1, 2042, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3911, 1, 1019, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3912, 1, 2043, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3913, 1, 1020, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3914, 1, 2044, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3915, 1, 1021, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3916, 1, 2045, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3917, 1, 1022, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3918, 1, 2046, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3919, 1, 1023, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3920, 1, 2047, '1', '2024-01-02 17:35:25', '1', '2024-01-02 17:35:25', b'0', 1);
INSERT INTO `system_role_menu` VALUES (3921, 142, 1, '110', '2024-02-19 11:27:03', '110', '2024-02-19 11:27:03', b'0', 153);
INSERT INTO `system_role_menu` VALUES (3922, 142, 100, '110', '2024-02-19 11:27:03', '110', '2024-02-19 11:27:03', b'0', 153);
INSERT INTO `system_role_menu` VALUES (3923, 142, 101, '110', '2024-02-19 11:27:03', '110', '2024-02-19 11:27:03', b'0', 153);
INSERT INTO `system_role_menu` VALUES (3924, 142, 102, '110', '2024-02-19 11:27:03', '110', '2024-02-28 18:57:15', b'1', 153);
INSERT INTO `system_role_menu` VALUES (3925, 142, 1063, '110', '2024-02-19 11:27:03', '110', '2024-02-19 11:27:03', b'0', 153);
INSERT INTO `system_role_menu` VALUES (3926, 142, 103, '110', '2024-02-19 11:27:03', '110', '2024-02-19 11:27:03', b'0', 153);
INSERT INTO `system_role_menu` VALUES (3927, 142, 1064, '110', '2024-02-19 11:27:03', '110', '2024-02-19 11:27:03', b'0', 153);
INSERT INTO `system_role_menu` VALUES (3928, 142, 1001, '110', '2024-02-19 11:27:03', '110', '2024-02-19 11:27:03', b'0', 153);
INSERT INTO `system_role_menu` VALUES (3929, 142, 1065, '110', '2024-02-19 11:27:03', '110', '2024-02-19 11:27:03', b'0', 153);
INSERT INTO `system_role_menu` VALUES (3930, 142, 1002, '110', '2024-02-19 11:27:03', '110', '2024-02-19 11:27:03', b'0', 153);
INSERT INTO `system_role_menu` VALUES (3931, 142, 1003, '110', '2024-02-19 11:27:03', '110', '2024-02-19 11:27:03', b'0', 153);
INSERT INTO `system_role_menu` VALUES (3932, 142, 1004, '110', '2024-02-19 11:27:03', '110', '2024-02-19 11:27:03', b'0', 153);
INSERT INTO `system_role_menu` VALUES (3933, 142, 1005, '110', '2024-02-19 11:27:03', '110', '2024-02-19 11:27:03', b'0', 153);
INSERT INTO `system_role_menu` VALUES (3934, 142, 1006, '110', '2024-02-19 11:27:03', '110', '2024-02-19 11:27:03', b'0', 153);
INSERT INTO `system_role_menu` VALUES (3935, 142, 1007, '110', '2024-02-19 11:27:03', '110', '2024-02-19 11:27:03', b'0', 153);
INSERT INTO `system_role_menu` VALUES (3936, 142, 1008, '110', '2024-02-19 11:27:03', '110', '2024-02-19 11:27:03', b'0', 153);
INSERT INTO `system_role_menu` VALUES (3937, 142, 1009, '110', '2024-02-19 11:27:03', '110', '2024-02-19 11:27:03', b'0', 153);
INSERT INTO `system_role_menu` VALUES (3938, 142, 1010, '110', '2024-02-19 11:27:03', '110', '2024-02-19 11:27:03', b'0', 153);
INSERT INTO `system_role_menu` VALUES (3939, 142, 1011, '110', '2024-02-19 11:27:03', '110', '2024-02-19 11:27:03', b'0', 153);
INSERT INTO `system_role_menu` VALUES (3940, 142, 1012, '110', '2024-02-19 11:27:03', '110', '2024-02-19 11:27:03', b'0', 153);
INSERT INTO `system_role_menu` VALUES (3941, 142, 1013, '110', '2024-02-19 11:27:03', '110', '2024-02-28 18:57:15', b'1', 153);
INSERT INTO `system_role_menu` VALUES (3942, 142, 1014, '110', '2024-02-19 11:27:03', '110', '2024-02-28 18:57:15', b'1', 153);
INSERT INTO `system_role_menu` VALUES (3943, 142, 1015, '110', '2024-02-19 11:27:03', '110', '2024-02-28 18:57:15', b'1', 153);
INSERT INTO `system_role_menu` VALUES (3944, 142, 1016, '110', '2024-02-19 11:27:03', '110', '2024-02-28 18:57:15', b'1', 153);
INSERT INTO `system_role_menu` VALUES (3945, 142, 1017, '110', '2024-02-19 11:27:03', '110', '2024-02-19 11:27:03', b'0', 153);
INSERT INTO `system_role_menu` VALUES (3946, 142, 1018, '110', '2024-02-19 11:27:03', '110', '2024-02-19 11:27:03', b'0', 153);
INSERT INTO `system_role_menu` VALUES (3947, 142, 1019, '110', '2024-02-19 11:27:03', '110', '2024-02-19 11:27:03', b'0', 153);
INSERT INTO `system_role_menu` VALUES (3948, 142, 1020, '110', '2024-02-19 11:27:03', '110', '2024-02-19 11:27:03', b'0', 153);
INSERT INTO `system_role_menu` VALUES (3949, 142, 1024, '1', '2024-02-28 18:41:44', '1', '2024-02-28 18:55:36', b'1', 153);
INSERT INTO `system_role_menu` VALUES (3950, 142, 1025, '1', '2024-02-28 18:41:44', '1', '2024-02-28 18:55:36', b'1', 153);
INSERT INTO `system_role_menu` VALUES (3951, 142, 1040, '1', '2024-02-28 18:41:44', '1', '2024-02-28 18:41:44', b'0', 153);
INSERT INTO `system_role_menu` VALUES (3952, 142, 1042, '1', '2024-02-28 18:41:44', '1', '2024-02-28 18:41:44', b'0', 153);
INSERT INTO `system_role_menu` VALUES (3953, 142, 1043, '1', '2024-02-28 18:41:44', '1', '2024-02-28 18:41:44', b'0', 153);
INSERT INTO `system_role_menu` VALUES (3954, 142, 1045, '1', '2024-02-28 18:41:44', '1', '2024-02-28 18:41:44', b'0', 153);
INSERT INTO `system_role_menu` VALUES (3955, 142, 104, '1', '2024-02-28 18:41:44', '1', '2024-02-28 18:55:36', b'1', 153);
INSERT INTO `system_role_menu` VALUES (3956, 142, 108, '1', '2024-02-28 18:41:44', '1', '2024-02-28 18:41:44', b'0', 153);
INSERT INTO `system_role_menu` VALUES (3957, 142, 500, '1', '2024-02-28 18:41:44', '1', '2024-02-28 18:41:44', b'0', 153);
INSERT INTO `system_role_menu` VALUES (3958, 142, 501, '1', '2024-02-28 18:41:44', '1', '2024-02-28 18:41:44', b'0', 153);
INSERT INTO `system_role_menu` VALUES (3959, 142, 1021, '1', '2024-02-28 18:41:44', '1', '2024-02-28 18:55:36', b'1', 153);
INSERT INTO `system_role_menu` VALUES (3960, 142, 1022, '1', '2024-02-28 18:41:44', '1', '2024-02-28 18:55:36', b'1', 153);
INSERT INTO `system_role_menu` VALUES (3961, 142, 1023, '1', '2024-02-28 18:41:44', '1', '2024-02-28 18:55:36', b'1', 153);
INSERT INTO `system_role_menu` VALUES (3962, 142, 1024, '129', '2024-02-28 18:55:43', '129', '2024-02-28 18:55:43', b'0', 153);
INSERT INTO `system_role_menu` VALUES (3963, 142, 1025, '129', '2024-02-28 18:55:43', '129', '2024-02-28 18:55:43', b'0', 153);
INSERT INTO `system_role_menu` VALUES (3964, 142, 104, '129', '2024-02-28 18:55:43', '129', '2024-02-28 18:55:43', b'0', 153);
INSERT INTO `system_role_menu` VALUES (3965, 142, 1021, '129', '2024-02-28 18:55:43', '129', '2024-02-28 18:55:43', b'0', 153);
INSERT INTO `system_role_menu` VALUES (3966, 142, 1022, '129', '2024-02-28 18:55:43', '129', '2024-02-28 18:55:43', b'0', 153);
INSERT INTO `system_role_menu` VALUES (3967, 142, 1023, '129', '2024-02-28 18:55:43', '129', '2024-02-28 18:55:43', b'0', 153);

-- ----------------------------
-- Table structure for system_sensitive_word
-- ----------------------------
DROP TABLE IF EXISTS `system_sensitive_word`;
CREATE TABLE `system_sensitive_word`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '敏感词',
  `description` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '描述',
  `tags` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标签数组',
  `status` tinyint NOT NULL COMMENT '状态',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '敏感词' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_sensitive_word
-- ----------------------------
INSERT INTO `system_sensitive_word` VALUES (3, '土豆', '好呀', '蔬菜,短信', 0, '1', '2022-04-08 21:07:12', '1', '2022-04-09 10:28:14', b'0');
INSERT INTO `system_sensitive_word` VALUES (4, 'XXX', NULL, '短信', 0, '1', '2022-04-08 21:27:49', '1', '2022-06-19 00:36:50', b'0');
INSERT INTO `system_sensitive_word` VALUES (5, '白痴', 'lll', '测试', 0, '1', '2022-12-31 19:08:25', '1', '2023-12-02 21:57:17', b'0');

-- ----------------------------
-- Table structure for system_sms_channel
-- ----------------------------
DROP TABLE IF EXISTS `system_sms_channel`;
CREATE TABLE `system_sms_channel`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `signature` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短信签名',
  `code` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '渠道编码',
  `status` tinyint NOT NULL COMMENT '开启状态',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `api_key` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短信 API 的账号',
  `api_secret` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '短信 API 的秘钥',
  `callback_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '短信发送回调 URL',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短信渠道' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_sms_channel
-- ----------------------------
INSERT INTO `system_sms_channel` VALUES (2, 'Ballcat', 'ALIYUN', 0, '你要改哦，只有我可以用！！！！', 'LTAI5tCnKso2uG3kJ5gRav88', 'fGJ5SNXL7P1NHNRmJ7DJaMJGPyE55C', NULL, '', '2021-03-31 11:53:10', '1', '2023-12-02 22:10:17', b'0');
INSERT INTO `system_sms_channel` VALUES (4, '测试渠道', 'DEBUG_DING_TALK', 0, '123', '696b5d8ead48071237e4aa5861ff08dbadb2b4ded1c688a7b7c9afc615579859', 'SEC5c4e5ff888bc8a9923ae47f59e7ccd30af1f14d93c55b4e2c9cb094e35aeed67', NULL, '1', '2021-04-13 00:23:14', '1', '2022-03-27 20:29:49', b'0');
INSERT INTO `system_sms_channel` VALUES (6, '测试演示', 'DEBUG_DING_TALK', 0, '仅测试', '696b5d8ead48071237e4aa5861ff08dbadb2b4ded1c688a7b7c9afc615579859', 'SEC5c4e5ff888bc8a9923ae47f59e7ccd30af1f14d93c55b4e2c9cb094e35aeed67', NULL, '1', '2022-04-10 23:07:59', '1', '2023-12-02 22:10:08', b'0');

-- ----------------------------
-- Table structure for system_sms_code
-- ----------------------------
DROP TABLE IF EXISTS `system_sms_code`;
CREATE TABLE `system_sms_code`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '手机号',
  `code` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '验证码',
  `create_ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建 IP',
  `scene` tinyint NOT NULL COMMENT '发送场景',
  `today_index` tinyint NOT NULL COMMENT '今日发送的第几条',
  `used` tinyint NOT NULL COMMENT '是否使用',
  `used_time` datetime NULL DEFAULT NULL COMMENT '使用时间',
  `used_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '使用 IP',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_mobile`(`mobile` ASC) USING BTREE COMMENT '手机号'
) ENGINE = InnoDB AUTO_INCREMENT = 612 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '手机验证码' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_sms_code
-- ----------------------------

-- ----------------------------
-- Table structure for system_sms_log
-- ----------------------------
DROP TABLE IF EXISTS `system_sms_log`;
CREATE TABLE `system_sms_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `channel_id` bigint NOT NULL COMMENT '短信渠道编号',
  `channel_code` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短信渠道编码',
  `template_id` bigint NOT NULL COMMENT '模板编号',
  `template_code` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模板编码',
  `template_type` tinyint NOT NULL COMMENT '短信类型',
  `template_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短信内容',
  `template_params` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短信参数',
  `api_template_id` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短信 API 的模板编号',
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '手机号',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户编号',
  `user_type` tinyint NULL DEFAULT NULL COMMENT '用户类型',
  `send_status` tinyint NOT NULL DEFAULT 0 COMMENT '发送状态',
  `send_time` datetime NULL DEFAULT NULL COMMENT '发送时间',
  `api_send_code` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '短信 API 发送结果的编码',
  `api_send_msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '短信 API 发送失败的提示',
  `api_request_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '短信 API 发送返回的唯一请求 ID',
  `api_serial_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '短信 API 发送返回的序号',
  `receive_status` tinyint NOT NULL DEFAULT 0 COMMENT '接收状态',
  `receive_time` datetime NULL DEFAULT NULL COMMENT '接收时间',
  `api_receive_code` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'API 接收结果的编码',
  `api_receive_msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'API 接收结果的说明',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 586 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短信日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_sms_log
-- ----------------------------

-- ----------------------------
-- Table structure for system_sms_template
-- ----------------------------
DROP TABLE IF EXISTS `system_sms_template`;
CREATE TABLE `system_sms_template`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `type` tinyint NOT NULL COMMENT '短信签名',
  `status` tinyint NOT NULL COMMENT '开启状态',
  `code` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模板编码',
  `name` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模板名称',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模板内容',
  `params` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '参数数组',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `api_template_id` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短信 API 的模板编号',
  `channel_id` bigint NOT NULL COMMENT '短信渠道编号',
  `channel_code` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短信渠道编码',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '短信模板' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_sms_template
-- ----------------------------
INSERT INTO `system_sms_template` VALUES (2, 1, 0, 'test_01', '测试验证码短信', '正在进行登录操作{operation}，您的验证码是{code}', '[\"operation\",\"code\"]', '测试备注', '4383920', 6, 'DEBUG_DING_TALK', '', '2021-03-31 10:49:38', '1', '2023-12-02 22:32:47', b'0');
INSERT INTO `system_sms_template` VALUES (3, 1, 0, 'test_02', '公告通知', '您的验证码{code}，该验证码5分钟内有效，请勿泄漏于他人！', '[\"code\"]', NULL, 'SMS_207945135', 2, 'ALIYUN', '', '2021-03-31 11:56:30', '1', '2021-04-10 01:22:02', b'0');
INSERT INTO `system_sms_template` VALUES (6, 3, 0, 'test-01', '测试模板', '哈哈哈 {name}', '[\"name\"]', 'f哈哈哈', '4383920', 6, 'DEBUG_DING_TALK', '1', '2021-04-10 01:07:21', '1', '2022-12-10 21:26:09', b'0');
INSERT INTO `system_sms_template` VALUES (7, 3, 0, 'test-04', '测试下', '老鸡{name}，牛逼{code}', '[\"name\",\"code\"]', '哈哈哈哈', 'suibian', 4, 'DEBUG_DING_TALK', '1', '2021-04-13 00:29:53', '1', '2023-12-02 22:35:34', b'0');
INSERT INTO `system_sms_template` VALUES (8, 1, 0, 'user-sms-login', '前台用户短信登录', '您的验证码是{code}', '[\"code\"]', NULL, '4372216', 6, 'DEBUG_DING_TALK', '1', '2021-10-11 08:10:00', '1', '2022-12-10 21:25:59', b'0');
INSERT INTO `system_sms_template` VALUES (9, 2, 0, 'bpm_task_assigned', '【工作流】任务被分配', '您收到了一条新的待办任务：{processInstanceName}-{taskName}，申请人：{startUserNickname}，处理链接：{detailUrl}', '[\"processInstanceName\",\"taskName\",\"startUserNickname\",\"detailUrl\"]', NULL, 'suibian', 4, 'DEBUG_DING_TALK', '1', '2022-01-21 22:31:19', '1', '2022-01-22 00:03:36', b'0');
INSERT INTO `system_sms_template` VALUES (10, 2, 0, 'bpm_process_instance_reject', '【工作流】流程被不通过', '您的流程被审批不通过：{processInstanceName}，原因：{reason}，查看链接：{detailUrl}', '[\"processInstanceName\",\"reason\",\"detailUrl\"]', NULL, 'suibian', 4, 'DEBUG_DING_TALK', '1', '2022-01-22 00:03:31', '1', '2022-05-01 12:33:14', b'0');
INSERT INTO `system_sms_template` VALUES (11, 2, 0, 'bpm_process_instance_approve', '【工作流】流程被通过', '您的流程被审批通过：{processInstanceName}，查看链接：{detailUrl}', '[\"processInstanceName\",\"detailUrl\"]', NULL, 'suibian', 4, 'DEBUG_DING_TALK', '1', '2022-01-22 00:04:31', '1', '2022-03-27 20:32:21', b'0');
INSERT INTO `system_sms_template` VALUES (12, 2, 0, 'demo', '演示模板', '我就是测试一下下', '[]', NULL, 'biubiubiu', 6, 'DEBUG_DING_TALK', '1', '2022-04-10 23:22:49', '1', '2023-03-24 23:45:07', b'0');
INSERT INTO `system_sms_template` VALUES (14, 1, 0, 'user-update-mobile', '会员用户 - 修改手机', '您的验证码{code}，该验证码 5 分钟内有效，请勿泄漏于他人！', '[\"code\"]', '', 'null', 4, 'DEBUG_DING_TALK', '1', '2023-08-19 18:58:01', '1', '2023-08-19 11:34:04', b'0');
INSERT INTO `system_sms_template` VALUES (15, 1, 0, 'user-update-password', '会员用户 - 修改密码', '您的验证码{code}，该验证码 5 分钟内有效，请勿泄漏于他人！', '[\"code\"]', '', 'null', 4, 'DEBUG_DING_TALK', '1', '2023-08-19 18:58:01', '1', '2023-08-19 11:34:18', b'0');
INSERT INTO `system_sms_template` VALUES (16, 1, 0, 'user-reset-password', '会员用户 - 重置密码', '您的验证码{code}，该验证码 5 分钟内有效，请勿泄漏于他人！', '[\"code\"]', '', 'null', 4, 'DEBUG_DING_TALK', '1', '2023-08-19 18:58:01', '1', '2023-12-02 22:35:27', b'0');

-- ----------------------------
-- Table structure for system_social_client
-- ----------------------------
DROP TABLE IF EXISTS `system_social_client`;
CREATE TABLE `system_social_client`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '应用名',
  `social_type` tinyint NOT NULL COMMENT '社交平台的类型',
  `user_type` tinyint NOT NULL COMMENT '用户类型',
  `client_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '客户端编号',
  `client_secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '客户端密钥',
  `agent_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '代理编号',
  `status` tinyint NOT NULL COMMENT '状态',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '社交客户端表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_social_client
-- ----------------------------
INSERT INTO `system_social_client` VALUES (1, '钉钉', 20, 2, 'dingvrnreaje3yqvzhxg', 'i8E6iZyDvZj51JIb0tYsYfVQYOks9Cq1lgryEjFRqC79P3iJcrxEwT6Qk2QvLrLI', NULL, 0, '', '2023-10-18 11:21:18', '1', '2023-12-20 21:28:26', b'1', 1);
INSERT INTO `system_social_client` VALUES (2, '钉钉（王土豆）', 20, 2, 'dingtsu9hpepjkbmthhw', 'FP_bnSq_HAHKCSncmJjw5hxhnzs6vaVDSZZn3egj6rdqTQ_hu5tQVJyLMpgCakdP', NULL, 0, '', '2023-10-18 11:21:18', '', '2023-12-20 21:28:26', b'1', 121);
INSERT INTO `system_social_client` VALUES (3, '微信公众号', 31, 1, 'wx5b23ba7a5589ecbb', '2a7b3b20c537e52e74afd395eb85f61f', NULL, 0, '', '2023-10-18 16:07:46', '1', '2023-12-20 21:28:23', b'1', 1);
INSERT INTO `system_social_client` VALUES (43, '微信小程序', 34, 1, 'wx63c280fe3248a3e7', '6f270509224a7ae1296bbf1c8cb97aed', NULL, 0, '', '2023-10-19 13:37:41', '1', '2023-12-20 21:28:25', b'1', 1);

-- ----------------------------
-- Table structure for system_social_user
-- ----------------------------
DROP TABLE IF EXISTS `system_social_user`;
CREATE TABLE `system_social_user`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键(自增策略)',
  `type` tinyint NOT NULL COMMENT '社交平台的类型',
  `openid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '社交 openid',
  `token` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '社交 token',
  `raw_token_info` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始 Token 数据，一般是 JSON 格式',
  `nickname` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户头像',
  `raw_user_info` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始用户数据，一般是 JSON 格式',
  `code` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '最后一次的认证 code',
  `state` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '最后一次的认证 state',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '社交用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_social_user
-- ----------------------------

-- ----------------------------
-- Table structure for system_social_user_bind
-- ----------------------------
DROP TABLE IF EXISTS `system_social_user_bind`;
CREATE TABLE `system_social_user_bind`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键(自增策略)',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `user_type` tinyint NOT NULL COMMENT '用户类型',
  `social_type` tinyint NOT NULL COMMENT '社交平台的类型',
  `social_user_id` bigint NOT NULL COMMENT '社交用户的编号',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 119 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '社交绑定表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_social_user_bind
-- ----------------------------

-- ----------------------------
-- Table structure for system_tenant
-- ----------------------------
DROP TABLE IF EXISTS `system_tenant`;
CREATE TABLE `system_tenant`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '租户编号',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '租户名',
  `contact_user_id` bigint NULL DEFAULT NULL COMMENT '联系人的用户编号',
  `contact_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '联系人',
  `contact_mobile` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系手机',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '租户状态（0正常 1停用）',
  `website` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '绑定域名',
  `package_id` bigint NOT NULL COMMENT '租户套餐编号',
  `expire_time` datetime NOT NULL COMMENT '过期时间',
  `account_count` int NOT NULL COMMENT '账号数量',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 154 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '租户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_tenant
-- ----------------------------
INSERT INTO `system_tenant` VALUES (1, 'futech', NULL, '芋艿', '17321315478', 0, 'www.iocoder.cn', 0, '2099-02-19 17:14:16', 9999, '1', '2021-01-05 17:03:47', '1', '2024-02-28 14:06:05', b'0');
INSERT INTO `system_tenant` VALUES (121, '小租户', 110, '小王2', '15601691300', 0, 'zsxq.iocoder.cn', 111, '2024-03-11 00:00:00', 20, '1', '2022-02-22 00:56:14', '1', '2023-11-06 11:41:47', b'0');
INSERT INTO `system_tenant` VALUES (122, '测试租户', 113, '芋道', '15601691300', 0, 'test.iocoder.cn', 111, '2022-04-30 00:00:00', 50, '1', '2022-03-07 21:37:58', '1', '2023-11-06 11:41:53', b'0');
INSERT INTO `system_tenant` VALUES (151, '大租户', 126, '土豆大', NULL, 0, 'https://tudou.iocoder.cn', 111, '2023-12-08 00:00:00', 10, '1', '2023-12-02 23:35:05', '1', '2023-12-08 23:39:56', b'0');
INSERT INTO `system_tenant` VALUES (152, '新租户', 127, '土豆', NULL, 0, 'http://xx.iocoder.cn', 111, '2025-12-31 00:00:00', 50, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0');
INSERT INTO `system_tenant` VALUES (153, 'google', 129, 'meng', NULL, 0, 'google.futech.com', 112, '2031-02-28 00:00:00', 20, '110', '2024-02-19 11:27:03', '1', '2024-02-28 18:42:57', b'0');

-- ----------------------------
-- Table structure for system_tenant_package
-- ----------------------------
DROP TABLE IF EXISTS `system_tenant_package`;
CREATE TABLE `system_tenant_package`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '套餐编号',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '套餐名',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '租户状态（0正常 1停用）',
  `remark` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '备注',
  `menu_ids` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '关联的菜单编号',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 113 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '租户套餐表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_tenant_package
-- ----------------------------
INSERT INTO `system_tenant_package` VALUES (111, '普通套餐', 0, '小功能', '[1,2,1031,1032,1033,1034,1035,1036,1037,1038,1039,1050,1051,1052,1053,1054,1056,1057,1058,1059,1060,1063,1064,1065,1066,1067,1070,1075,1076,1077,1078,1082,1083,1084,1085,1086,1087,1088,1089,1090,1091,1092,100,101,102,103,106,107,110,111,112,113,1138,114,1139,115,1140,116,1141,1142,1143,2472,2478,2479,2480,2481,2482,2483,2484,2485,2486,2487,2488,2489,2490,2491,2492,2493,2494,2495,2497,1224,1225,1226,1227,1228,1229,1237,1238,1239,1240,1241,1242,1243,2525,1255,1256,1001,1257,1002,1258,1003,1259,1004,1260,1005,1006,1007,1008,1009,1010,1011,1012,1013,1014,1015,1016,1017,1018,1019,1020]', '1', '2022-02-22 00:54:00', '1', '2023-12-30 11:42:36', b'0');
INSERT INTO `system_tenant_package` VALUES (112, '高级套餐', 0, '', '[1024,1025,1,1040,1042,1043,1045,100,101,1063,103,1064,104,1001,1065,1002,1003,1004,108,1005,1006,1007,1008,1009,1010,1011,1012,500,501,1017,1018,1019,1020,1021,1022,1023]', '110', '2024-02-19 11:23:46', '1', '2024-02-28 18:57:15', b'0');

-- ----------------------------
-- Table structure for system_user_post
-- ----------------------------
DROP TABLE IF EXISTS `system_user_post`;
CREATE TABLE `system_user_post`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint NOT NULL DEFAULT 0 COMMENT '用户ID',
  `post_id` bigint NOT NULL DEFAULT 0 COMMENT '岗位ID',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 118 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户岗位表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_user_post
-- ----------------------------
INSERT INTO `system_user_post` VALUES (112, 1, 1, 'admin', '2022-05-02 07:25:24', 'admin', '2022-05-02 07:25:24', b'0', 1);
INSERT INTO `system_user_post` VALUES (113, 100, 1, 'admin', '2022-05-02 07:25:24', 'admin', '2022-05-02 07:25:24', b'0', 1);
INSERT INTO `system_user_post` VALUES (114, 114, 3, 'admin', '2022-05-02 07:25:24', 'admin', '2022-05-02 07:25:24', b'0', 1);
INSERT INTO `system_user_post` VALUES (115, 104, 1, '1', '2022-05-16 19:36:28', '1', '2022-05-16 19:36:28', b'0', 1);
INSERT INTO `system_user_post` VALUES (116, 117, 2, '1', '2022-07-09 17:40:26', '1', '2022-07-09 17:40:26', b'0', 1);
INSERT INTO `system_user_post` VALUES (117, 118, 1, '1', '2022-07-09 17:44:44', '1', '2022-07-09 17:44:44', b'0', 1);

-- ----------------------------
-- Table structure for system_user_role
-- ----------------------------
DROP TABLE IF EXISTS `system_user_role`;
CREATE TABLE `system_user_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增编号',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_user_role
-- ----------------------------
INSERT INTO `system_user_role` VALUES (1, 1, 1, '', '2022-01-11 13:19:45', '', '2022-05-12 12:35:17', b'0', 1);
INSERT INTO `system_user_role` VALUES (2, 2, 2, '', '2022-01-11 13:19:45', '', '2022-05-12 12:35:13', b'0', 1);
INSERT INTO `system_user_role` VALUES (4, 100, 101, '', '2022-01-11 13:19:45', '', '2022-05-12 12:35:13', b'0', 1);
INSERT INTO `system_user_role` VALUES (5, 100, 1, '', '2022-01-11 13:19:45', '', '2022-05-12 12:35:12', b'0', 1);
INSERT INTO `system_user_role` VALUES (6, 100, 2, '', '2022-01-11 13:19:45', '', '2022-05-12 12:35:11', b'0', 1);
INSERT INTO `system_user_role` VALUES (10, 103, 1, '1', '2022-01-11 13:19:45', '1', '2022-01-11 13:19:45', b'0', 1);
INSERT INTO `system_user_role` VALUES (11, 107, 106, '1', '2022-02-20 22:59:33', '1', '2022-02-20 22:59:33', b'0', 118);
INSERT INTO `system_user_role` VALUES (12, 108, 107, '1', '2022-02-20 23:00:50', '1', '2022-02-20 23:00:50', b'0', 119);
INSERT INTO `system_user_role` VALUES (13, 109, 108, '1', '2022-02-20 23:11:50', '1', '2022-02-20 23:11:50', b'0', 120);
INSERT INTO `system_user_role` VALUES (14, 110, 109, '1', '2022-02-22 00:56:14', '1', '2022-02-22 00:56:14', b'0', 121);
INSERT INTO `system_user_role` VALUES (15, 111, 110, '110', '2022-02-23 13:14:38', '110', '2022-02-23 13:14:38', b'0', 121);
INSERT INTO `system_user_role` VALUES (16, 113, 111, '1', '2022-03-07 21:37:58', '1', '2022-03-07 21:37:58', b'0', 122);
INSERT INTO `system_user_role` VALUES (17, 114, 101, '1', '2022-03-19 21:51:13', '1', '2022-03-19 21:51:13', b'0', 1);
INSERT INTO `system_user_role` VALUES (18, 1, 2, '1', '2022-05-12 20:39:29', '1', '2022-05-12 20:39:29', b'0', 1);
INSERT INTO `system_user_role` VALUES (19, 116, 113, '1', '2022-05-17 10:07:10', '1', '2022-05-17 10:07:10', b'0', 124);
INSERT INTO `system_user_role` VALUES (20, 104, 101, '1', '2022-05-28 15:43:57', '1', '2022-05-28 15:43:57', b'0', 1);
INSERT INTO `system_user_role` VALUES (22, 115, 2, '1', '2022-07-21 22:08:30', '1', '2022-07-21 22:08:30', b'0', 1);
INSERT INTO `system_user_role` VALUES (23, 119, 114, '1', '2022-12-30 11:32:04', '1', '2022-12-30 11:32:04', b'0', 125);
INSERT INTO `system_user_role` VALUES (24, 120, 115, '1', '2022-12-30 11:33:42', '1', '2022-12-30 11:33:42', b'0', 126);
INSERT INTO `system_user_role` VALUES (25, 121, 116, '1', '2022-12-30 11:33:49', '1', '2022-12-30 11:33:49', b'0', 127);
INSERT INTO `system_user_role` VALUES (26, 122, 118, '1', '2022-12-30 11:47:53', '1', '2022-12-30 11:47:53', b'0', 129);
INSERT INTO `system_user_role` VALUES (27, 112, 101, '1', '2023-02-09 23:18:51', '1', '2023-02-09 23:18:51', b'0', 1);
INSERT INTO `system_user_role` VALUES (28, 123, 136, '1', '2023-03-05 21:23:35', '1', '2023-03-05 21:23:35', b'0', 147);
INSERT INTO `system_user_role` VALUES (29, 124, 137, '1', '2023-03-05 21:42:27', '1', '2023-03-05 21:42:27', b'0', 148);
INSERT INTO `system_user_role` VALUES (30, 125, 138, '1', '2023-03-05 21:59:03', '1', '2023-03-05 21:59:03', b'0', 149);
INSERT INTO `system_user_role` VALUES (31, 126, 139, '1', '2023-07-25 23:06:04', '1', '2023-07-25 23:06:04', b'0', 150);
INSERT INTO `system_user_role` VALUES (32, 126, 140, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_user_role` VALUES (33, 127, 141, '1', '2023-12-30 11:43:17', '1', '2023-12-30 11:43:17', b'0', 152);
INSERT INTO `system_user_role` VALUES (34, 129, 142, '110', '2024-02-19 11:27:03', '110', '2024-02-19 11:27:03', b'0', 153);

-- ----------------------------
-- Table structure for system_users
-- ----------------------------
DROP TABLE IF EXISTS `system_users`;
CREATE TABLE `system_users`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户账号',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '密码',
  `nickname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户昵称',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `dept_id` bigint NULL DEFAULT NULL COMMENT '部门ID',
  `post_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '岗位编号数组',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '用户邮箱',
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '手机号码',
  `sex` tinyint NULL DEFAULT 0 COMMENT '用户性别',
  `avatar` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '头像地址',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '帐号状态（0正常 1停用）',
  `login_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_username`(`username` ASC, `update_time` ASC, `tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 130 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_users
-- ----------------------------
INSERT INTO `system_users` VALUES (1, 'admin', '$2a$10$mRMIYLDtRHlf6.9ipiqH1.Z.bh/R9dO9d5iHiGYPigi6r5KOoR2Wm', 'fu-admin', '管理员', 103, '[1]', 'xiaoming.meng@futech.co.jp', '15840488511', 1, 'http://127.0.0.1:48080/admin-api/infra/file/4/get/37e56010ecbee472cdd821ac4b608e151e62a74d9633f15d085aee026eedeb60.png', 0, '10.54.5.148', '2024-02-29 17:10:07', 'admin', '2021-01-05 17:03:47', NULL, '2024-02-29 17:10:07', b'0', 1);
INSERT INTO `system_users` VALUES (100, 'bi', '$2a$10$11U48RhyJ5pSBYWSn12AD./ld671.ycSzJHbyrtpeoMeYiw31eo8a', '芋道', '不要吓我', 104, '[1]', 'bi@iocoder.cn', '15601691300', 1, '', 1, '127.0.0.1', '2022-07-09 23:03:33', '', '2021-01-07 09:07:17', NULL, '2022-07-09 23:03:33', b'0', 1);
INSERT INTO `system_users` VALUES (103, 'yuanma', '$2a$10$YMpimV4T6BtDhIaA8jSW.u8UTGBeGhc/qwXP4oxoMr4mOw9.qttt6', '源码', NULL, 106, NULL, 'yuanma@iocoder.cn', '15601701300', 0, '', 0, '127.0.0.1', '2022-07-08 01:26:27', '', '2021-01-13 23:50:35', NULL, '2022-07-08 01:26:27', b'0', 1);
INSERT INTO `system_users` VALUES (104, 'test', '$2a$10$GP8zvqHB//TekuzYZSBYAuBQJiNq1.fxQVDYJ.uBCOnWCtDVKE4H6', '测试号', NULL, 107, '[1,2]', '111@qq.com', '15601691200', 1, '', 0, '0:0:0:0:0:0:0:1', '2023-09-24 18:21:19', '', '2021-01-21 02:13:53', NULL, '2023-09-24 18:21:19', b'0', 1);
INSERT INTO `system_users` VALUES (107, 'admin107', '$2a$10$dYOOBKMO93v/.ReCqzyFg.o67Tqk.bbc2bhrpyBGkIw9aypCtr2pm', '芋艿', NULL, NULL, NULL, '', '15601691300', 0, '', 0, '', NULL, '1', '2022-02-20 22:59:33', '1', '2022-02-27 08:26:51', b'0', 118);
INSERT INTO `system_users` VALUES (108, 'admin108', '$2a$10$y6mfvKoNYL1GXWak8nYwVOH.kCWqjactkzdoIDgiKl93WN3Ejg.Lu', '芋艿', NULL, NULL, NULL, '', '15601691300', 0, '', 0, '', NULL, '1', '2022-02-20 23:00:50', '1', '2022-02-27 08:26:53', b'0', 119);
INSERT INTO `system_users` VALUES (109, 'admin109', '$2a$10$JAqvH0tEc0I7dfDVBI7zyuB4E3j.uH6daIjV53.vUS6PknFkDJkuK', '芋艿', NULL, NULL, NULL, '', '15601691300', 0, '', 0, '', NULL, '1', '2022-02-20 23:11:50', '1', '2022-02-27 08:26:56', b'0', 120);
INSERT INTO `system_users` VALUES (110, 'admin110', '$2a$10$mRMIYLDtRHlf6.9ipiqH1.Z.bh/R9dO9d5iHiGYPigi6r5KOoR2Wm', '小王', NULL, NULL, NULL, '', '15601691300', 0, '', 0, '0:0:0:0:0:0:0:1', '2024-02-19 11:18:10', '1', '2022-02-22 00:56:14', NULL, '2024-02-19 11:18:10', b'0', 121);
INSERT INTO `system_users` VALUES (111, 'test', '$2a$10$mRMIYLDtRHlf6.9ipiqH1.Z.bh/R9dO9d5iHiGYPigi6r5KOoR2Wm', '测试用户', NULL, NULL, '[]', '', '', 0, '', 0, '0:0:0:0:0:0:0:1', '2023-12-30 11:42:17', '110', '2022-02-23 13:14:33', NULL, '2023-12-30 11:42:17', b'0', 121);
INSERT INTO `system_users` VALUES (112, 'newobject', '$2a$10$3alwklxqfq8/hKoW6oUV0OJp0IdQpBDauLy4633SpUjrRsStl6kMa', '新对象', NULL, 100, '[]', '', '', 1, '', 0, '0:0:0:0:0:0:0:1', '2023-02-10 13:48:13', '1', '2022-02-23 19:08:03', NULL, '2023-02-10 13:48:13', b'0', 1);
INSERT INTO `system_users` VALUES (113, 'aoteman', '$2a$10$0acJOIk2D25/oC87nyclE..0lzeu9DtQ/n3geP4fkun/zIVRhHJIO', '芋道', NULL, NULL, NULL, '', '15601691300', 0, '', 0, '127.0.0.1', '2022-03-19 18:38:51', '1', '2022-03-07 21:37:58', NULL, '2022-03-19 18:38:51', b'0', 122);
INSERT INTO `system_users` VALUES (114, 'hrmgr', '$2a$10$TR4eybBioGRhBmDBWkqWLO6NIh3mzYa8KBKDDB5woiGYFVlRAi.fu', 'hr 小姐姐', NULL, NULL, '[3]', '', '', 0, '', 0, '127.0.0.1', '2022-03-19 22:15:43', '1', '2022-03-19 21:50:58', NULL, '2022-03-19 22:15:43', b'0', 1);
INSERT INTO `system_users` VALUES (115, 'aotemane', '$2a$10$/WCwGHu1eq0wOVDd/u8HweJ0gJCHyLS6T7ndCqI8UXZAQom1etk2e', '1', '11', 101, '[]', '', '', 1, '', 0, '', NULL, '1', '2022-04-30 02:55:43', '1', '2022-06-22 13:34:58', b'0', 1);
INSERT INTO `system_users` VALUES (116, '15601691302', '$2a$10$L5C4S0U6adBWMvFv1Wwl4.DI/NwYS3WIfLj5Q.Naqr5II8CmqsDZ6', '小豆', NULL, NULL, NULL, '', '', 0, '', 0, '', NULL, '1', '2022-05-17 10:07:10', '1', '2022-05-17 10:07:10', b'0', 124);
INSERT INTO `system_users` VALUES (117, 'admin123', '$2a$10$WI8Gg/lpZQIrOEZMHqka7OdFaD4Nx.B/qY8ZGTTUKrOJwaHFqibaC', '测试号', '1111', 100, '[2]', '', '15601691234', 1, '', 0, '', NULL, '1', '2022-07-09 17:40:26', '1', '2022-07-09 17:40:26', b'0', 1);
INSERT INTO `system_users` VALUES (118, 'goudan', '$2a$04$OB1SuphCdiLVRpiYRKeqH.8NYS7UIp5vmIv1W7U4w6toiFeOAATVK', '狗蛋', NULL, 103, '[1]', '', '', 2, '', 0, '', NULL, '1', '2022-07-09 17:44:43', '1', '2023-12-05 19:33:20', b'0', 1);
INSERT INTO `system_users` VALUES (126, 'tudou123', '$2a$04$lecJZ/CqgknEp7mDV2d4ou0beyj1GbM3.nVEZe//8WgQpR.JBgnAu', '土豆', NULL, NULL, NULL, '', '', 0, '', 0, '', NULL, '1', '2023-12-02 23:35:05', '1', '2023-12-02 23:35:05', b'0', 151);
INSERT INTO `system_users` VALUES (127, 'admin2024', '$2a$04$cHdZ7N6AUKysa2XTUG/J/egYtAzdwtpnNpcMVHDrupt1dyn4teOku', '土豆', NULL, NULL, NULL, '', '', 0, '', 0, '0:0:0:0:0:0:0:1', '2023-12-30 11:43:28', '1', '2023-12-30 11:43:17', NULL, '2023-12-30 11:43:28', b'0', 152);
INSERT INTO `system_users` VALUES (128, 'meng', '$2a$04$Vg9bdmQK5H0mOUWlBneE/uxFnU1Id51shhwkiMKJeesUBHUNKdZIO', 'meng', '', 110, '[]', '', '', 0, '', 0, '', NULL, '110', '2024-02-19 11:21:05', '110', '2024-02-19 11:21:05', b'0', 121);
INSERT INTO `system_users` VALUES (129, 'gadmin', '$2a$04$/rjrMg3/00HWRpLKBzJqW.A5C2qQAsK2MxGCE6VesaNT05DmWuJKa', 'google管理员', NULL, 114, NULL, '', '', 1, '', 0, '0:0:0:0:0:0:0:1', '2024-02-29 10:56:05', '110', '2024-02-19 11:27:03', NULL, '2024-02-29 10:56:05', b'0', 153);

SET FOREIGN_KEY_CHECKS = 1;
