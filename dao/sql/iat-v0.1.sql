/*
Navicat MySQL Data Transfer

Source Server         : 192.168.3.10
Source Server Version : 50725
Source Host           : 192.168.3.10:3306
Source Database       : iat

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2019-03-04 15:00:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for api
-- ----------------------------
DROP TABLE IF EXISTS `api`;
CREATE TABLE `api` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `service_id` bigint(10) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `path` varchar(128) DEFAULT NULL,
  `method` varchar(12) DEFAULT NULL,
  `version` int(4) DEFAULT NULL,
  `description` varchar(2048) DEFAULT NULL,
  `deleted` tinyint(1) unsigned zerofill DEFAULT '0',
  `create_user` varchar(128) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_user` varchar(128) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=606 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for assert
-- ----------------------------
DROP TABLE IF EXISTS `assert`;
CREATE TABLE `assert` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `keyword_api_id` bigint(10) NOT NULL,
  `locale` varchar(20) NOT NULL COMMENT '断言类型：httpcode,header,body',
  `method` varchar(32) NOT NULL COMMENT '断言方法，包含，等于，大于，小于',
  `rule` varchar(512) NOT NULL COMMENT '提取规则',
  `type` varchar(32) NOT NULL COMMENT '提取类型：正则，json',
  `value` varchar(1024) NOT NULL COMMENT '断言值,可以是变量',
  `description` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for body
-- ----------------------------
DROP TABLE IF EXISTS `body`;
CREATE TABLE `body` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `api_id` bigint(10) DEFAULT NULL,
  `body` text,
  `default` text,
  `type` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=762 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for client
-- ----------------------------
DROP TABLE IF EXISTS `client`;
CREATE TABLE `client` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `client` varchar(16) DEFAULT NULL,
  `key` varchar(64) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `registration_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `last_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `client` (`client`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for env
-- ----------------------------
DROP TABLE IF EXISTS `env`;
CREATE TABLE `env` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `service_id` bigint(10) NOT NULL,
  `env` varchar(36) NOT NULL COMMENT 'qa,dev,prod',
  `host` varchar(256) NOT NULL,
  `port` int(5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for extractor
-- ----------------------------
DROP TABLE IF EXISTS `extractor`;
CREATE TABLE `extractor` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `keyword_api_id` bigint(10) NOT NULL,
  `type` varchar(20) NOT NULL,
  `rule` varchar(512) NOT NULL,
  `name` varchar(32) NOT NULL,
  `description` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `extractor_kai_name_unique` (`keyword_api_id`,`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for form_data
-- ----------------------------
DROP TABLE IF EXISTS `form_data`;
CREATE TABLE `form_data` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `api_id` bigint(10) DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `value` varchar(1024) DEFAULT NULL,
  `default` varchar(1024) DEFAULT NULL,
  `type` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1938 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for header
-- ----------------------------
DROP TABLE IF EXISTS `header`;
CREATE TABLE `header` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `api_id` bigint(10) DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `value` varchar(1024) DEFAULT NULL,
  `default` varchar(1024) DEFAULT NULL,
  `type` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=391 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for keyword
-- ----------------------------
DROP TABLE IF EXISTS `keyword`;
CREATE TABLE `keyword` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `service_id` bigint(10) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `create_user` varchar(128) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_user` varchar(128) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for keyword_api
-- ----------------------------
DROP TABLE IF EXISTS `keyword_api`;
CREATE TABLE `keyword_api` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `keyword_id` bigint(10) DEFAULT NULL,
  `api_id` bigint(10) DEFAULT NULL,
  `idx` int(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for parameter_key
-- ----------------------------
DROP TABLE IF EXISTS `parameter_key`;
CREATE TABLE `parameter_key` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `testcase_id` bigint(10) NOT NULL,
  `key_name` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for parameter_value
-- ----------------------------
DROP TABLE IF EXISTS `parameter_value`;
CREATE TABLE `parameter_value` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `testcase_id` bigint(10) NOT NULL,
  `key_id` bigint(10) NOT NULL,
  `row_num` varchar(255) NOT NULL,
  `value` varchar(1024) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=331 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for result_code
-- ----------------------------
DROP TABLE IF EXISTS `result_code`;
CREATE TABLE `result_code` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `api_id` bigint(10) DEFAULT NULL,
  `code` int(6) DEFAULT NULL,
  `description` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=487 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) NOT NULL COMMENT 'system,service',
  `code` varchar(255) DEFAULT NULL COMMENT 'admin,normal ',
  PRIMARY KEY (`id`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for service
-- ----------------------------
DROP TABLE IF EXISTS `service`;
CREATE TABLE `service` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `unique_key` varchar(36) DEFAULT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `create_user` varchar(64) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(64) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for service_user
-- ----------------------------
DROP TABLE IF EXISTS `service_user`;
CREATE TABLE `service_user` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `service_id` bigint(10) NOT NULL,
  `user_id` bigint(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `serviceUser-serviceId-userId` (`service_id`,`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `service_id` bigint(10) DEFAULT NULL,
  `testplan_id` bigint(10) DEFAULT NULL,
  `testplan_name` varchar(64) DEFAULT NULL,
  `tag` varchar(128) DEFAULT NULL,
  `pass_rate` int(3) DEFAULT NULL COMMENT '通过率：0-100',
  `coverage` int(3) DEFAULT NULL COMMENT 'api覆盖率:0-100',
  `status` varchar(16) DEFAULT NULL COMMENT '任务状态：create,running,timeout,failed,succeed,interrupt',
  `client` varchar(64) DEFAULT NULL,
  `create_user` varchar(128) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_user` varchar(128) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `message` text,
  `start_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `end_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `env` varchar(2048) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=184 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for task_testcase
-- ----------------------------
DROP TABLE IF EXISTS `task_testcase`;
CREATE TABLE `task_testcase` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `task_id` bigint(10) DEFAULT NULL,
  `testcase_id` bigint(10) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `idx` int(4) DEFAULT NULL,
  `status` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=207 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for task_testcase_keyword
-- ----------------------------
DROP TABLE IF EXISTS `task_testcase_keyword`;
CREATE TABLE `task_testcase_keyword` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `task_id` bigint(10) DEFAULT NULL,
  `testcase_id` bigint(10) DEFAULT NULL,
  `testcase_keyword_id` bigint(10) DEFAULT NULL,
  `keyword_id` bigint(10) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `idx` int(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=539 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for task_testcase_keyword_api
-- ----------------------------
DROP TABLE IF EXISTS `task_testcase_keyword_api`;
CREATE TABLE `task_testcase_keyword_api` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `service_id` bigint(10) DEFAULT NULL,
  `task_id` bigint(10) DEFAULT NULL,
  `testplan_id` bigint(10) DEFAULT NULL,
  `testcase_id` bigint(10) DEFAULT NULL,
  `testcase_keyword_id` bigint(10) DEFAULT NULL,
  `keyword_id` bigint(10) DEFAULT NULL,
  `keyword_api_id` int(11) DEFAULT NULL,
  `api_id` bigint(10) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `method` varchar(12) DEFAULT NULL,
  `version` int(4) DEFAULT NULL,
  `description` varchar(2048) DEFAULT NULL,
  `idx` int(4) DEFAULT NULL,
  `headers` text,
  `formdatas` text,
  `body` text,
  `extractors` text,
  `asserts` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=854 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for task_testcase_keyword_api_result
-- ----------------------------
DROP TABLE IF EXISTS `task_testcase_keyword_api_result`;
CREATE TABLE `task_testcase_keyword_api_result` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `task_id` bigint(10) DEFAULT NULL,
  `testplan_id` bigint(10) DEFAULT NULL,
  `testcase_id` bigint(10) DEFAULT NULL,
  `parameter_id` bigint(10) DEFAULT NULL,
  `testcase_keyword_id` bigint(10) DEFAULT NULL,
  `keyword_id` bigint(10) DEFAULT NULL,
  `keyword_api_id` bigint(10) DEFAULT NULL,
  `api_id` bigint(10) DEFAULT NULL,
  `url` varchar(512) DEFAULT NULL,
  `method` varchar(10) DEFAULT NULL,
  `request_headers` text,
  `request_formdatas` text,
  `request_body` longtext,
  `response_headers` text,
  `response_body` longtext,
  `extractors` varchar(255) DEFAULT NULL,
  `asserts` varchar(255) DEFAULT NULL,
  `status` varchar(16) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `end_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1494 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for task_testcase_parameter
-- ----------------------------
DROP TABLE IF EXISTS `task_testcase_parameter`;
CREATE TABLE `task_testcase_parameter` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `task_id` bigint(10) DEFAULT NULL,
  `testplan_id` bigint(10) DEFAULT NULL,
  `testcase_id` bigint(10) DEFAULT NULL,
  `parameters` text,
  `status` varchar(16) DEFAULT NULL,
  `message` text,
  `start_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `end_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=348 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for testcase
-- ----------------------------
DROP TABLE IF EXISTS `testcase`;
CREATE TABLE `testcase` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `service_id` bigint(10) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `create_user` varchar(128) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_user` varchar(128) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for testcase_keyword
-- ----------------------------
DROP TABLE IF EXISTS `testcase_keyword`;
CREATE TABLE `testcase_keyword` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `keyword_id` bigint(10) DEFAULT NULL,
  `testcase_id` bigint(10) DEFAULT NULL,
  `idx` int(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for testplan
-- ----------------------------
DROP TABLE IF EXISTS `testplan`;
CREATE TABLE `testplan` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `service_id` bigint(10) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `strategy` varchar(20) DEFAULT NULL COMMENT '策略，crontab',
  `env` varchar(2048) DEFAULT NULL,
  `create_user` varchar(128) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_user` varchar(128) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for testplan_testcase
-- ----------------------------
DROP TABLE IF EXISTS `testplan_testcase`;
CREATE TABLE `testplan_testcase` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `testcase_id` bigint(10) NOT NULL,
  `testplan_id` bigint(10) NOT NULL,
  `idx` int(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `phone` varchar(16) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) DEFAULT NULL COMMENT 'admin,normal ',
  `create_user` varchar(128) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_user` varchar(128) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;


INSERT INTO `user` VALUES ('1', 'admin', null, '21232f297a57a5a743894a0e4a801fc3', 'ADMIN', 'ADMIN', '2019-01-16 04:08:16', null, '2019-01-16 04:08:16', '0');
INSERT INTO `service` VALUES ('1', 'iat', 'bc5abb3a-0b2a-11e9-bd16-080027546a5b', 'api管理、api调试、api测试', 'admin', '2018-12-21 11:12:35', 'admin', '2019-01-15 21:47:02');




