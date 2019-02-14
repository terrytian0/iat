/*
Navicat MySQL Data Transfer

Source Server         : 192.168.3.10
Source Server Version : 50724
Source Host           : 192.168.3.10:3306
Source Database       : iat

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2019-01-18 10:20:38
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
) ENGINE=InnoDB AUTO_INCREMENT=265 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=189 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=455 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for parameter_key
-- ----------------------------
DROP TABLE IF EXISTS `parameter_key`;
CREATE TABLE `parameter_key` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `testcase_id` bigint(10) NOT NULL,
  `key_name` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=323 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for testcase_keyword_api
-- ----------------------------
DROP TABLE IF EXISTS `testcase_keyword_api`;
CREATE TABLE `testcase_keyword_api` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `keyword_id` bigint(10) DEFAULT NULL,
  `testcase_id` bigint(10) DEFAULT NULL,
  `idx` int(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
