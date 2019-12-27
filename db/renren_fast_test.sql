/*
 Navicat MySQL Data Transfer

 Source Server         : 192.168.172.25
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : 192.168.172.25
 Source Database       : renren_fast_test

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : utf-8

 Date: 12/27/2019 09:12:38 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `QRTZ_BLOB_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
CREATE TABLE `QRTZ_BLOB_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_BLOB_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `QRTZ_CALENDARS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
CREATE TABLE `QRTZ_CALENDARS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `QRTZ_CRON_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
CREATE TABLE `QRTZ_CRON_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_CRON_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `QRTZ_CRON_TRIGGERS`
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_CRON_TRIGGERS` VALUES ('RenrenScheduler', 'TASK_1', 'DEFAULT', '0 0/30 * * * ?', 'Asia/Shanghai');
COMMIT;

-- ----------------------------
--  Table structure for `QRTZ_FIRED_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
CREATE TABLE `QRTZ_FIRED_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `QRTZ_JOB_DETAILS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
CREATE TABLE `QRTZ_JOB_DETAILS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `QRTZ_JOB_DETAILS`
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_JOB_DETAILS` VALUES ('RenrenScheduler', 'TASK_1', 'DEFAULT', null, 'io.renren.modules.job.utils.ScheduleJob', '0', '0', '0', '0', 0xaced0005737200156f72672e71756172747a2e4a6f62446174614d61709fb083e8bfa9b0cb020000787200266f72672e71756172747a2e7574696c732e537472696e674b65794469727479466c61674d61708208e8c3fbc55d280200015a0013616c6c6f77735472616e7369656e74446174617872001d6f72672e71756172747a2e7574696c732e4469727479466c61674d617013e62ead28760ace0200025a000564697274794c00036d617074000f4c6a6176612f7574696c2f4d61703b787001737200116a6176612e7574696c2e486173684d61700507dac1c31660d103000246000a6c6f6164466163746f724900097468726573686f6c6478703f4000000000000c7708000000100000000174000d4a4f425f504152414d5f4b45597372002e696f2e72656e72656e2e6d6f64756c65732e6a6f622e656e746974792e5363686564756c654a6f62456e7469747900000000000000010200074c00086265616e4e616d657400124c6a6176612f6c616e672f537472696e673b4c000a63726561746554696d657400104c6a6176612f7574696c2f446174653b4c000e63726f6e45787072657373696f6e71007e00094c00056a6f6249647400104c6a6176612f6c616e672f4c6f6e673b4c0006706172616d7371007e00094c000672656d61726b71007e00094c00067374617475737400134c6a6176612f6c616e672f496e74656765723b7870740008746573745461736b7372000e6a6176612e7574696c2e44617465686a81014b597419030000787077080000016ed4200aa07874000e3020302f3330202a202a202a203f7372000e6a6176612e6c616e672e4c6f6e673b8be490cc8f23df0200014a000576616c7565787200106a6176612e6c616e672e4e756d62657286ac951d0b94e08b0200007870000000000000000174000672656e72656e74000ce58f82e695b0e6b58be8af95737200116a6176612e6c616e672e496e746567657212e2a0a4f781873802000149000576616c75657871007e0013000000007800);
COMMIT;

-- ----------------------------
--  Table structure for `QRTZ_LOCKS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_LOCKS`;
CREATE TABLE `QRTZ_LOCKS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `QRTZ_LOCKS`
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_LOCKS` VALUES ('RenrenScheduler', 'STATE_ACCESS'), ('RenrenScheduler', 'TRIGGER_ACCESS');
COMMIT;

-- ----------------------------
--  Table structure for `QRTZ_PAUSED_TRIGGER_GRPS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `QRTZ_SCHEDULER_STATE`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
CREATE TABLE `QRTZ_SCHEDULER_STATE` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `QRTZ_SCHEDULER_STATE`
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_SCHEDULER_STATE` VALUES ('RenrenScheduler', 'mikeliu.local1577327181073', '1577409148540', '15000');
COMMIT;

-- ----------------------------
--  Table structure for `QRTZ_SIMPLE_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `QRTZ_SIMPROP_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_SIMPROP_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `QRTZ_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
CREATE TABLE `QRTZ_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `QRTZ_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `QRTZ_TRIGGERS`
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_TRIGGERS` VALUES ('RenrenScheduler', 'TASK_1', 'DEFAULT', 'TASK_1', 'DEFAULT', null, '1577410200000', '1577408400000', '5', 'WAITING', 'CRON', '1575531577000', '0', null, '2', 0xaced0005737200156f72672e71756172747a2e4a6f62446174614d61709fb083e8bfa9b0cb020000787200266f72672e71756172747a2e7574696c732e537472696e674b65794469727479466c61674d61708208e8c3fbc55d280200015a0013616c6c6f77735472616e7369656e74446174617872001d6f72672e71756172747a2e7574696c732e4469727479466c61674d617013e62ead28760ace0200025a000564697274794c00036d617074000f4c6a6176612f7574696c2f4d61703b787001737200116a6176612e7574696c2e486173684d61700507dac1c31660d103000246000a6c6f6164466163746f724900097468726573686f6c6478703f4000000000000c7708000000100000000174000d4a4f425f504152414d5f4b45597372002e696f2e72656e72656e2e6d6f64756c65732e6a6f622e656e746974792e5363686564756c654a6f62456e7469747900000000000000010200074c00086265616e4e616d657400124c6a6176612f6c616e672f537472696e673b4c000a63726561746554696d657400104c6a6176612f7574696c2f446174653b4c000e63726f6e45787072657373696f6e71007e00094c00056a6f6249647400104c6a6176612f6c616e672f4c6f6e673b4c0006706172616d7371007e00094c000672656d61726b71007e00094c00067374617475737400134c6a6176612f6c616e672f496e74656765723b7870740008746573745461736b7372000e6a6176612e7574696c2e44617465686a81014b597419030000787077080000016ed4200aa07874000e3020302f3330202a202a202a203f7372000e6a6176612e6c616e672e4c6f6e673b8be490cc8f23df0200014a000576616c7565787200106a6176612e6c616e672e4e756d62657286ac951d0b94e08b0200007870000000000000000174000672656e72656e74000ce58f82e695b0e6b58be8af95737200116a6176612e6c616e672e496e746567657212e2a0a4f781873802000149000576616c75657871007e0013000000007800);
COMMIT;

-- ----------------------------
--  Table structure for `schedule_job`
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job`;
CREATE TABLE `schedule_job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT 'cron表达式',
  `status` tinyint(4) DEFAULT NULL COMMENT '任务状态  0：正常  1：暂停',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='定时任务';

-- ----------------------------
--  Records of `schedule_job`
-- ----------------------------
BEGIN;
INSERT INTO `schedule_job` VALUES ('1', 'testTask', 'renren', '0 0/30 * * * ?', '0', '参数测试', '2019-12-05 11:35:00');
COMMIT;

-- ----------------------------
--  Table structure for `schedule_job_log`
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job_log`;
CREATE TABLE `schedule_job_log` (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志id',
  `job_id` bigint(20) NOT NULL COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `status` tinyint(4) NOT NULL COMMENT '任务状态    0：成功    1：失败',
  `error` varchar(2000) DEFAULT NULL COMMENT '失败信息',
  `times` int(11) NOT NULL COMMENT '耗时(单位：毫秒)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`log_id`),
  KEY `job_id` (`job_id`)
) ENGINE=InnoDB AUTO_INCREMENT=740 DEFAULT CHARSET=utf8mb4 COMMENT='定时任务日志';

-- ----------------------------
--  Records of `schedule_job_log`
-- ----------------------------
BEGIN;
INSERT INTO `schedule_job_log` VALUES ('1', '1', 'testTask', 'renren', '0', null, '3', '2019-12-05 16:00:00'), ('2', '1', 'testTask', 'renren', '0', null, '17', '2019-12-05 16:30:00'), ('3', '1', 'testTask', 'renren', '0', null, '3', '2019-12-05 17:00:00'), ('4', '1', 'testTask', 'renren', '0', null, '3', '2019-12-05 18:00:00'), ('5', '1', 'testTask', 'renren', '0', null, '4', '2019-12-05 18:30:00'), ('6', '1', 'testTask', 'renren', '0', null, '33', '2019-12-05 19:00:00'), ('7', '1', 'testTask', 'renren', '0', null, '33', '2019-12-05 19:30:01'), ('8', '1', 'testTask', 'renren', '0', null, '47', '2019-12-05 20:00:00'), ('9', '1', 'testTask', 'renren', '0', null, '4', '2019-12-05 20:30:00'), ('10', '1', 'testTask', 'renren', '0', null, '6', '2019-12-05 21:00:00'), ('11', '1', 'testTask', 'renren', '0', null, '5', '2019-12-05 21:30:00'), ('12', '1', 'testTask', 'renren', '0', null, '2', '2019-12-05 22:00:00'), ('13', '1', 'testTask', 'renren', '0', null, '2', '2019-12-05 22:30:00'), ('14', '1', 'testTask', 'renren', '0', null, '1', '2019-12-05 23:00:00'), ('15', '1', 'testTask', 'renren', '0', null, '2', '2019-12-05 23:30:00'), ('16', '1', 'testTask', 'renren', '0', null, '176', '2019-12-06 00:00:00'), ('17', '1', 'testTask', 'renren', '0', null, '9', '2019-12-06 00:30:00'), ('18', '1', 'testTask', 'renren', '0', null, '3', '2019-12-06 01:00:00'), ('19', '1', 'testTask', 'renren', '0', null, '4', '2019-12-06 01:30:00'), ('20', '1', 'testTask', 'renren', '0', null, '5', '2019-12-06 02:00:00'), ('21', '1', 'testTask', 'renren', '0', null, '2', '2019-12-06 02:30:00'), ('22', '1', 'testTask', 'renren', '0', null, '1', '2019-12-06 03:00:00'), ('23', '1', 'testTask', 'renren', '0', null, '4', '2019-12-06 03:30:00'), ('24', '1', 'testTask', 'renren', '0', null, '3', '2019-12-06 04:00:00'), ('25', '1', 'testTask', 'renren', '0', null, '2', '2019-12-06 04:30:00'), ('26', '1', 'testTask', 'renren', '0', null, '4', '2019-12-06 05:00:00'), ('27', '1', 'testTask', 'renren', '0', null, '8', '2019-12-06 05:30:00'), ('28', '1', 'testTask', 'renren', '0', null, '6', '2019-12-06 06:00:00'), ('29', '1', 'testTask', 'renren', '0', null, '6', '2019-12-06 06:30:00'), ('30', '1', 'testTask', 'renren', '0', null, '5', '2019-12-06 07:00:00'), ('31', '1', 'testTask', 'renren', '0', null, '4', '2019-12-06 07:30:00'), ('32', '1', 'testTask', 'renren', '0', null, '3', '2019-12-06 08:00:00'), ('33', '1', 'testTask', 'renren', '0', null, '5', '2019-12-06 08:30:00'), ('34', '1', 'testTask', 'renren', '0', null, '13', '2019-12-06 09:00:00'), ('35', '1', 'testTask', 'renren', '0', null, '52', '2019-12-06 09:30:00'), ('36', '1', 'testTask', 'renren', '0', null, '9', '2019-12-06 10:00:00'), ('37', '1', 'testTask', 'renren', '0', null, '13', '2019-12-06 10:30:00'), ('38', '1', 'testTask', 'renren', '0', null, '158', '2019-12-06 11:00:00'), ('39', '1', 'testTask', 'renren', '0', null, '9', '2019-12-06 11:30:00'), ('40', '1', 'testTask', 'renren', '0', null, '19', '2019-12-06 12:00:00'), ('41', '1', 'testTask', 'renren', '0', null, '2', '2019-12-06 12:30:00'), ('42', '1', 'testTask', 'renren', '0', null, '5', '2019-12-06 13:00:00'), ('43', '1', 'testTask', 'renren', '0', null, '16', '2019-12-06 13:30:00'), ('44', '1', 'testTask', 'renren', '0', null, '3', '2019-12-06 14:00:00'), ('45', '1', 'testTask', 'renren', '0', null, '12', '2019-12-06 14:30:00'), ('46', '1', 'testTask', 'renren', '0', null, '16', '2019-12-06 15:00:00'), ('47', '1', 'testTask', 'renren', '0', null, '11', '2019-12-06 15:30:01'), ('48', '1', 'testTask', 'renren', '0', null, '1', '2019-12-12 16:30:00'), ('49', '1', 'testTask', 'renren', '0', null, '2', '2019-12-12 17:00:00'), ('50', '1', 'testTask', 'renren', '0', null, '4', '2019-12-12 17:30:00'), ('51', '1', 'testTask', 'renren', '0', null, '2', '2019-12-12 18:00:00'), ('52', '1', 'testTask', 'renren', '0', null, '3', '2019-12-12 18:30:00'), ('53', '1', 'testTask', 'renren', '0', null, '2', '2019-12-12 19:00:00'), ('54', '1', 'testTask', 'renren', '0', null, '2', '2019-12-12 19:30:00'), ('55', '1', 'testTask', 'renren', '0', null, '3', '2019-12-12 20:00:00'), ('56', '1', 'testTask', 'renren', '0', null, '3', '2019-12-12 20:30:00'), ('57', '1', 'testTask', 'renren', '0', null, '3', '2019-12-12 21:00:00'), ('58', '1', 'testTask', 'renren', '0', null, '2', '2019-12-12 21:30:00'), ('59', '1', 'testTask', 'renren', '0', null, '3', '2019-12-12 22:00:00'), ('60', '1', 'testTask', 'renren', '0', null, '1', '2019-12-12 22:30:00'), ('61', '1', 'testTask', 'renren', '0', null, '3', '2019-12-12 23:00:00'), ('62', '1', 'testTask', 'renren', '0', null, '1', '2019-12-12 23:30:00'), ('63', '1', 'testTask', 'renren', '0', null, '125', '2019-12-13 00:00:00'), ('64', '1', 'testTask', 'renren', '0', null, '6', '2019-12-13 00:30:00'), ('65', '1', 'testTask', 'renren', '0', null, '11', '2019-12-13 01:00:00'), ('66', '1', 'testTask', 'renren', '0', null, '0', '2019-12-13 01:30:00'), ('67', '1', 'testTask', 'renren', '0', null, '1', '2019-12-13 02:00:00'), ('68', '1', 'testTask', 'renren', '0', null, '2', '2019-12-13 02:30:00'), ('69', '1', 'testTask', 'renren', '0', null, '3', '2019-12-13 03:00:00'), ('70', '1', 'testTask', 'renren', '0', null, '2', '2019-12-13 03:30:00'), ('71', '1', 'testTask', 'renren', '0', null, '2', '2019-12-13 04:00:00'), ('72', '1', 'testTask', 'renren', '0', null, '1', '2019-12-13 04:30:00'), ('73', '1', 'testTask', 'renren', '0', null, '2', '2019-12-13 05:00:00'), ('74', '1', 'testTask', 'renren', '0', null, '3', '2019-12-13 05:30:00'), ('75', '1', 'testTask', 'renren', '0', null, '2', '2019-12-13 06:00:00'), ('76', '1', 'testTask', 'renren', '0', null, '3', '2019-12-13 06:30:00'), ('77', '1', 'testTask', 'renren', '0', null, '5', '2019-12-13 07:00:00'), ('78', '1', 'testTask', 'renren', '0', null, '5', '2019-12-13 07:30:00'), ('79', '1', 'testTask', 'renren', '0', null, '0', '2019-12-13 08:00:00'), ('80', '1', 'testTask', 'renren', '0', null, '0', '2019-12-13 08:30:00'), ('81', '1', 'testTask', 'renren', '0', null, '1', '2019-12-13 09:00:00'), ('82', '1', 'testTask', 'renren', '0', null, '4', '2019-12-13 09:30:00'), ('83', '1', 'testTask', 'renren', '0', null, '5', '2019-12-13 10:00:00'), ('84', '1', 'testTask', 'renren', '0', null, '6', '2019-12-13 10:30:00'), ('85', '1', 'testTask', 'renren', '0', null, '2', '2019-12-13 11:00:00'), ('86', '1', 'testTask', 'renren', '0', null, '2', '2019-12-13 11:30:00'), ('87', '1', 'testTask', 'renren', '0', null, '1', '2019-12-13 12:00:00'), ('88', '1', 'testTask', 'renren', '0', null, '1', '2019-12-13 12:30:00'), ('89', '1', 'testTask', 'renren', '0', null, '4', '2019-12-13 13:00:00'), ('90', '1', 'testTask', 'renren', '0', null, '1', '2019-12-13 13:30:00'), ('91', '1', 'testTask', 'renren', '0', null, '1', '2019-12-13 14:00:00'), ('92', '1', 'testTask', 'renren', '0', null, '1', '2019-12-13 14:30:00'), ('93', '1', 'testTask', 'renren', '0', null, '1', '2019-12-13 15:00:00'), ('94', '1', 'testTask', 'renren', '0', null, '2', '2019-12-13 15:30:00'), ('95', '1', 'testTask', 'renren', '0', null, '3', '2019-12-13 16:00:00'), ('96', '1', 'testTask', 'renren', '0', null, '1', '2019-12-13 16:30:00'), ('97', '1', 'testTask', 'renren', '0', null, '1', '2019-12-13 17:00:00'), ('98', '1', 'testTask', 'renren', '0', null, '1', '2019-12-13 17:30:00'), ('99', '1', 'testTask', 'renren', '0', null, '1', '2019-12-13 18:00:00'), ('100', '1', 'testTask', 'renren', '0', null, '3', '2019-12-13 18:30:00'), ('101', '1', 'testTask', 'renren', '0', null, '0', '2019-12-13 19:00:00'), ('102', '1', 'testTask', 'renren', '0', null, '1', '2019-12-13 19:30:00'), ('103', '1', 'testTask', 'renren', '0', null, '1', '2019-12-13 20:00:00'), ('104', '1', 'testTask', 'renren', '0', null, '1', '2019-12-13 20:30:00'), ('105', '1', 'testTask', 'renren', '0', null, '2', '2019-12-13 21:00:00'), ('106', '1', 'testTask', 'renren', '0', null, '0', '2019-12-13 21:30:00'), ('107', '1', 'testTask', 'renren', '0', null, '2', '2019-12-13 22:00:00'), ('108', '1', 'testTask', 'renren', '0', null, '0', '2019-12-13 22:30:00'), ('109', '1', 'testTask', 'renren', '0', null, '2', '2019-12-13 23:00:00'), ('110', '1', 'testTask', 'renren', '0', null, '1', '2019-12-13 23:30:00'), ('111', '1', 'testTask', 'renren', '0', null, '79', '2019-12-14 00:00:00'), ('112', '1', 'testTask', 'renren', '0', null, '4', '2019-12-14 00:30:00'), ('113', '1', 'testTask', 'renren', '0', null, '1', '2019-12-14 01:00:00'), ('114', '1', 'testTask', 'renren', '0', null, '0', '2019-12-14 01:30:00'), ('115', '1', 'testTask', 'renren', '0', null, '0', '2019-12-14 02:00:00'), ('116', '1', 'testTask', 'renren', '0', null, '1', '2019-12-14 02:30:00'), ('117', '1', 'testTask', 'renren', '0', null, '0', '2019-12-14 03:00:00'), ('118', '1', 'testTask', 'renren', '0', null, '1', '2019-12-14 03:30:00'), ('119', '1', 'testTask', 'renren', '0', null, '3', '2019-12-14 04:00:00'), ('120', '1', 'testTask', 'renren', '0', null, '0', '2019-12-14 04:30:00'), ('121', '1', 'testTask', 'renren', '0', null, '4', '2019-12-14 05:00:00'), ('122', '1', 'testTask', 'renren', '0', null, '1', '2019-12-14 05:30:00'), ('123', '1', 'testTask', 'renren', '0', null, '2', '2019-12-14 06:00:00'), ('124', '1', 'testTask', 'renren', '0', null, '0', '2019-12-14 06:30:00'), ('125', '1', 'testTask', 'renren', '0', null, '2', '2019-12-14 07:00:00'), ('126', '1', 'testTask', 'renren', '0', null, '3', '2019-12-14 07:30:00'), ('127', '1', 'testTask', 'renren', '0', null, '1', '2019-12-14 08:00:00'), ('128', '1', 'testTask', 'renren', '0', null, '1', '2019-12-14 08:30:00'), ('129', '1', 'testTask', 'renren', '0', null, '4', '2019-12-14 09:00:00'), ('130', '1', 'testTask', 'renren', '0', null, '3', '2019-12-14 09:30:00'), ('131', '1', 'testTask', 'renren', '0', null, '1', '2019-12-14 10:00:00'), ('132', '1', 'testTask', 'renren', '0', null, '2', '2019-12-14 10:30:00'), ('133', '1', 'testTask', 'renren', '0', null, '2', '2019-12-14 11:00:00'), ('134', '1', 'testTask', 'renren', '0', null, '3', '2019-12-14 11:30:00'), ('135', '1', 'testTask', 'renren', '0', null, '0', '2019-12-14 12:00:00'), ('136', '1', 'testTask', 'renren', '0', null, '3', '2019-12-14 12:30:00'), ('137', '1', 'testTask', 'renren', '0', null, '2', '2019-12-14 13:00:00'), ('138', '1', 'testTask', 'renren', '0', null, '3', '2019-12-14 13:30:00'), ('139', '1', 'testTask', 'renren', '0', null, '3', '2019-12-14 14:00:00'), ('140', '1', 'testTask', 'renren', '0', null, '3', '2019-12-14 14:30:00'), ('141', '1', 'testTask', 'renren', '0', null, '3', '2019-12-14 15:00:00'), ('142', '1', 'testTask', 'renren', '0', null, '3', '2019-12-14 15:30:00'), ('143', '1', 'testTask', 'renren', '0', null, '4', '2019-12-14 16:00:00'), ('144', '1', 'testTask', 'renren', '0', null, '4', '2019-12-14 16:30:00'), ('145', '1', 'testTask', 'renren', '0', null, '1', '2019-12-14 17:00:00'), ('146', '1', 'testTask', 'renren', '0', null, '1', '2019-12-14 17:30:00'), ('147', '1', 'testTask', 'renren', '0', null, '4', '2019-12-14 18:00:00'), ('148', '1', 'testTask', 'renren', '0', null, '1', '2019-12-14 18:30:00'), ('149', '1', 'testTask', 'renren', '0', null, '3', '2019-12-14 19:00:00'), ('150', '1', 'testTask', 'renren', '0', null, '1', '2019-12-14 19:30:00'), ('151', '1', 'testTask', 'renren', '0', null, '0', '2019-12-14 20:00:00'), ('152', '1', 'testTask', 'renren', '0', null, '3', '2019-12-14 20:30:00'), ('153', '1', 'testTask', 'renren', '0', null, '3', '2019-12-14 21:00:00'), ('154', '1', 'testTask', 'renren', '0', null, '4', '2019-12-14 21:30:00'), ('155', '1', 'testTask', 'renren', '0', null, '3', '2019-12-14 22:00:00'), ('156', '1', 'testTask', 'renren', '0', null, '3', '2019-12-14 22:30:00'), ('157', '1', 'testTask', 'renren', '0', null, '2', '2019-12-14 23:00:00'), ('158', '1', 'testTask', 'renren', '0', null, '4', '2019-12-14 23:30:00'), ('159', '1', 'testTask', 'renren', '0', null, '7', '2019-12-15 00:00:00'), ('160', '1', 'testTask', 'renren', '0', null, '1', '2019-12-15 00:30:00'), ('161', '1', 'testTask', 'renren', '0', null, '0', '2019-12-15 01:00:00'), ('162', '1', 'testTask', 'renren', '0', null, '1', '2019-12-15 01:30:00'), ('163', '1', 'testTask', 'renren', '0', null, '3', '2019-12-15 02:00:00'), ('164', '1', 'testTask', 'renren', '0', null, '3', '2019-12-15 02:30:00'), ('165', '1', 'testTask', 'renren', '0', null, '1', '2019-12-15 03:00:00'), ('166', '1', 'testTask', 'renren', '0', null, '3', '2019-12-15 03:30:00'), ('167', '1', 'testTask', 'renren', '0', null, '1', '2019-12-15 04:00:00'), ('168', '1', 'testTask', 'renren', '0', null, '0', '2019-12-15 04:30:00'), ('169', '1', 'testTask', 'renren', '0', null, '3', '2019-12-15 05:00:00'), ('170', '1', 'testTask', 'renren', '0', null, '3', '2019-12-15 05:30:00'), ('171', '1', 'testTask', 'renren', '0', null, '3', '2019-12-15 06:00:00'), ('172', '1', 'testTask', 'renren', '0', null, '3', '2019-12-15 06:30:00'), ('173', '1', 'testTask', 'renren', '0', null, '1', '2019-12-15 07:00:00'), ('174', '1', 'testTask', 'renren', '0', null, '1', '2019-12-15 07:30:00'), ('175', '1', 'testTask', 'renren', '0', null, '3', '2019-12-15 08:00:00'), ('176', '1', 'testTask', 'renren', '0', null, '1', '2019-12-15 08:30:00'), ('177', '1', 'testTask', 'renren', '0', null, '4', '2019-12-15 09:00:00'), ('178', '1', 'testTask', 'renren', '0', null, '3', '2019-12-15 09:30:00'), ('179', '1', 'testTask', 'renren', '0', null, '1', '2019-12-15 10:00:00'), ('180', '1', 'testTask', 'renren', '0', null, '1', '2019-12-15 10:30:00'), ('181', '1', 'testTask', 'renren', '0', null, '2', '2019-12-15 11:00:00'), ('182', '1', 'testTask', 'renren', '0', null, '1', '2019-12-15 11:30:00'), ('183', '1', 'testTask', 'renren', '0', null, '3', '2019-12-15 12:00:00'), ('184', '1', 'testTask', 'renren', '0', null, '3', '2019-12-15 12:30:00'), ('185', '1', 'testTask', 'renren', '0', null, '3', '2019-12-15 13:00:00'), ('186', '1', 'testTask', 'renren', '0', null, '2', '2019-12-15 13:30:00'), ('187', '1', 'testTask', 'renren', '0', null, '3', '2019-12-15 14:00:00'), ('188', '1', 'testTask', 'renren', '0', null, '3', '2019-12-15 14:30:00'), ('189', '1', 'testTask', 'renren', '0', null, '4', '2019-12-15 15:00:00'), ('190', '1', 'testTask', 'renren', '0', null, '2', '2019-12-15 15:30:00'), ('191', '1', 'testTask', 'renren', '0', null, '2', '2019-12-15 16:00:00'), ('192', '1', 'testTask', 'renren', '0', null, '3', '2019-12-15 16:30:00'), ('193', '1', 'testTask', 'renren', '0', null, '4', '2019-12-15 17:00:00'), ('194', '1', 'testTask', 'renren', '0', null, '2', '2019-12-15 17:30:00'), ('195', '1', 'testTask', 'renren', '0', null, '3', '2019-12-15 18:00:00'), ('196', '1', 'testTask', 'renren', '0', null, '1', '2019-12-15 18:30:00'), ('197', '1', 'testTask', 'renren', '0', null, '2', '2019-12-15 19:00:00'), ('198', '1', 'testTask', 'renren', '0', null, '1', '2019-12-15 19:30:00'), ('199', '1', 'testTask', 'renren', '0', null, '3', '2019-12-15 20:00:00'), ('200', '1', 'testTask', 'renren', '0', null, '0', '2019-12-15 20:30:00'), ('201', '1', 'testTask', 'renren', '0', null, '2', '2019-12-15 21:00:00'), ('202', '1', 'testTask', 'renren', '0', null, '3', '2019-12-15 21:30:00'), ('203', '1', 'testTask', 'renren', '0', null, '3', '2019-12-15 22:00:00'), ('204', '1', 'testTask', 'renren', '0', null, '3', '2019-12-15 22:30:00'), ('205', '1', 'testTask', 'renren', '0', null, '0', '2019-12-15 23:00:00'), ('206', '1', 'testTask', 'renren', '0', null, '3', '2019-12-15 23:30:00'), ('207', '1', 'testTask', 'renren', '0', null, '6', '2019-12-16 00:00:00'), ('208', '1', 'testTask', 'renren', '0', null, '1', '2019-12-16 00:30:00'), ('209', '1', 'testTask', 'renren', '0', null, '3', '2019-12-16 01:00:00'), ('210', '1', 'testTask', 'renren', '0', null, '1', '2019-12-16 01:30:00'), ('211', '1', 'testTask', 'renren', '0', null, '4', '2019-12-16 02:00:00'), ('212', '1', 'testTask', 'renren', '0', null, '3', '2019-12-16 02:30:00'), ('213', '1', 'testTask', 'renren', '0', null, '4', '2019-12-16 03:00:00'), ('214', '1', 'testTask', 'renren', '0', null, '2', '2019-12-16 03:30:00'), ('215', '1', 'testTask', 'renren', '0', null, '1', '2019-12-16 04:00:00'), ('216', '1', 'testTask', 'renren', '0', null, '0', '2019-12-16 04:30:00'), ('217', '1', 'testTask', 'renren', '0', null, '3', '2019-12-16 05:00:00'), ('218', '1', 'testTask', 'renren', '0', null, '3', '2019-12-16 05:30:00'), ('219', '1', 'testTask', 'renren', '0', null, '3', '2019-12-16 06:00:00'), ('220', '1', 'testTask', 'renren', '0', null, '1', '2019-12-16 06:30:00'), ('221', '1', 'testTask', 'renren', '0', null, '3', '2019-12-16 07:00:00'), ('222', '1', 'testTask', 'renren', '0', null, '1', '2019-12-16 07:30:00'), ('223', '1', 'testTask', 'renren', '0', null, '4', '2019-12-16 08:00:00'), ('224', '1', 'testTask', 'renren', '0', null, '4', '2019-12-16 08:30:00'), ('225', '1', 'testTask', 'renren', '0', null, '3', '2019-12-16 09:00:00'), ('226', '1', 'testTask', 'renren', '0', null, '2', '2019-12-16 09:30:00'), ('227', '1', 'testTask', 'renren', '0', null, '2', '2019-12-16 10:00:00'), ('228', '1', 'testTask', 'renren', '0', null, '14', '2019-12-16 10:30:00'), ('229', '1', 'testTask', 'renren', '0', null, '3', '2019-12-16 11:00:00'), ('230', '1', 'testTask', 'renren', '0', null, '8', '2019-12-16 11:30:00'), ('231', '1', 'testTask', 'renren', '0', null, '4', '2019-12-16 12:00:00'), ('232', '1', 'testTask', 'renren', '0', null, '1', '2019-12-16 15:00:00'), ('233', '1', 'testTask', 'renren', '0', null, '1', '2019-12-16 15:30:00'), ('234', '1', 'testTask', 'renren', '0', null, '3', '2019-12-16 16:00:00'), ('235', '1', 'testTask', 'renren', '0', null, '2', '2019-12-16 16:30:00'), ('236', '1', 'testTask', 'renren', '0', null, '2', '2019-12-16 17:00:00'), ('237', '1', 'testTask', 'renren', '0', null, '4', '2019-12-16 17:30:00'), ('238', '1', 'testTask', 'renren', '0', null, '3', '2019-12-16 18:00:00'), ('239', '1', 'testTask', 'renren', '0', null, '2', '2019-12-16 18:30:00'), ('240', '1', 'testTask', 'renren', '0', null, '1', '2019-12-16 19:00:00'), ('241', '1', 'testTask', 'renren', '0', null, '2', '2019-12-16 19:30:00'), ('242', '1', 'testTask', 'renren', '0', null, '2', '2019-12-16 20:00:00'), ('243', '1', 'testTask', 'renren', '0', null, '0', '2019-12-16 20:30:00'), ('244', '1', 'testTask', 'renren', '0', null, '1', '2019-12-16 21:00:00'), ('245', '1', 'testTask', 'renren', '0', null, '1', '2019-12-16 21:30:00'), ('246', '1', 'testTask', 'renren', '0', null, '1', '2019-12-16 22:00:00'), ('247', '1', 'testTask', 'renren', '0', null, '1', '2019-12-16 22:30:00'), ('248', '1', 'testTask', 'renren', '0', null, '1', '2019-12-16 23:00:00'), ('249', '1', 'testTask', 'renren', '0', null, '0', '2019-12-16 23:30:00'), ('250', '1', 'testTask', 'renren', '0', null, '46', '2019-12-17 00:00:00'), ('251', '1', 'testTask', 'renren', '0', null, '4', '2019-12-17 00:30:00'), ('252', '1', 'testTask', 'renren', '0', null, '1', '2019-12-17 01:00:00'), ('253', '1', 'testTask', 'renren', '0', null, '3', '2019-12-17 01:30:00'), ('254', '1', 'testTask', 'renren', '0', null, '0', '2019-12-17 02:00:00'), ('255', '1', 'testTask', 'renren', '0', null, '1', '2019-12-17 02:30:00'), ('256', '1', 'testTask', 'renren', '0', null, '3', '2019-12-17 03:00:00'), ('257', '1', 'testTask', 'renren', '0', null, '0', '2019-12-17 03:30:00'), ('258', '1', 'testTask', 'renren', '0', null, '3', '2019-12-17 04:00:00'), ('259', '1', 'testTask', 'renren', '0', null, '1', '2019-12-17 04:30:00'), ('260', '1', 'testTask', 'renren', '0', null, '1', '2019-12-17 05:00:00'), ('261', '1', 'testTask', 'renren', '0', null, '1', '2019-12-17 05:30:00'), ('262', '1', 'testTask', 'renren', '0', null, '3', '2019-12-17 06:00:00'), ('263', '1', 'testTask', 'renren', '0', null, '0', '2019-12-17 06:30:00'), ('264', '1', 'testTask', 'renren', '0', null, '1', '2019-12-17 07:00:00'), ('265', '1', 'testTask', 'renren', '0', null, '0', '2019-12-17 07:30:00'), ('266', '1', 'testTask', 'renren', '0', null, '1', '2019-12-17 08:00:00'), ('267', '1', 'testTask', 'renren', '0', null, '3', '2019-12-17 08:30:00'), ('268', '1', 'testTask', 'renren', '0', null, '0', '2019-12-17 09:00:00'), ('269', '1', 'testTask', 'renren', '0', null, '1', '2019-12-17 09:30:00'), ('270', '1', 'testTask', 'renren', '0', null, '1', '2019-12-17 10:00:00'), ('271', '1', 'testTask', 'renren', '0', null, '0', '2019-12-17 10:30:00'), ('272', '1', 'testTask', 'renren', '0', null, '4', '2019-12-17 11:00:00'), ('273', '1', 'testTask', 'renren', '0', null, '2', '2019-12-17 11:30:00'), ('274', '1', 'testTask', 'renren', '0', null, '5', '2019-12-17 12:00:00'), ('275', '1', 'testTask', 'renren', '0', null, '3', '2019-12-17 12:30:00'), ('276', '1', 'testTask', 'renren', '0', null, '2', '2019-12-17 13:00:00'), ('277', '1', 'testTask', 'renren', '0', null, '3', '2019-12-17 13:30:00'), ('278', '1', 'testTask', 'renren', '0', null, '2', '2019-12-17 14:00:00'), ('279', '1', 'testTask', 'renren', '0', null, '2', '2019-12-17 14:30:00'), ('280', '1', 'testTask', 'renren', '0', null, '10', '2019-12-17 15:00:00'), ('281', '1', 'testTask', 'renren', '0', null, '6', '2019-12-17 15:30:00'), ('282', '1', 'testTask', 'renren', '0', null, '7', '2019-12-17 16:00:00'), ('283', '1', 'testTask', 'renren', '0', null, '2', '2019-12-17 16:30:00'), ('284', '1', 'testTask', 'renren', '0', null, '2', '2019-12-17 17:00:00'), ('285', '1', 'testTask', 'renren', '0', null, '3', '2019-12-17 17:30:00'), ('286', '1', 'testTask', 'renren', '0', null, '4', '2019-12-17 18:30:00'), ('287', '1', 'testTask', 'renren', '0', null, '1', '2019-12-17 19:00:00'), ('288', '1', 'testTask', 'renren', '0', null, '6', '2019-12-17 19:30:00'), ('289', '1', 'testTask', 'renren', '0', null, '9', '2019-12-17 20:30:00'), ('290', '1', 'testTask', 'renren', '0', null, '1', '2019-12-17 21:00:00'), ('291', '1', 'testTask', 'renren', '0', null, '1', '2019-12-17 21:30:00'), ('292', '1', 'testTask', 'renren', '0', null, '2', '2019-12-17 22:00:00'), ('293', '1', 'testTask', 'renren', '0', null, '0', '2019-12-17 22:30:00'), ('294', '1', 'testTask', 'renren', '0', null, '2', '2019-12-17 23:00:00'), ('295', '1', 'testTask', 'renren', '0', null, '1', '2019-12-17 23:30:00'), ('296', '1', 'testTask', 'renren', '0', null, '54', '2019-12-18 00:00:00'), ('297', '1', 'testTask', 'renren', '0', null, '3', '2019-12-18 00:30:00'), ('298', '1', 'testTask', 'renren', '0', null, '1', '2019-12-18 01:00:00'), ('299', '1', 'testTask', 'renren', '0', null, '1', '2019-12-18 01:30:00'), ('300', '1', 'testTask', 'renren', '0', null, '1', '2019-12-18 02:00:00'), ('301', '1', 'testTask', 'renren', '0', null, '0', '2019-12-18 02:30:00'), ('302', '1', 'testTask', 'renren', '0', null, '1', '2019-12-18 03:00:00'), ('303', '1', 'testTask', 'renren', '0', null, '1', '2019-12-18 03:30:00'), ('304', '1', 'testTask', 'renren', '0', null, '1', '2019-12-18 04:00:00'), ('305', '1', 'testTask', 'renren', '0', null, '1', '2019-12-18 04:30:00'), ('306', '1', 'testTask', 'renren', '0', null, '2', '2019-12-18 05:00:00'), ('307', '1', 'testTask', 'renren', '0', null, '2', '2019-12-18 05:30:00'), ('308', '1', 'testTask', 'renren', '0', null, '0', '2019-12-18 06:00:00'), ('309', '1', 'testTask', 'renren', '0', null, '4', '2019-12-18 06:30:00'), ('310', '1', 'testTask', 'renren', '0', null, '8', '2019-12-18 07:00:00'), ('311', '1', 'testTask', 'renren', '0', null, '0', '2019-12-18 07:30:00'), ('312', '1', 'testTask', 'renren', '0', null, '3', '2019-12-18 08:00:00'), ('313', '1', 'testTask', 'renren', '0', null, '4', '2019-12-18 08:30:00'), ('314', '1', 'testTask', 'renren', '0', null, '2', '2019-12-18 09:00:00'), ('315', '1', 'testTask', 'renren', '0', null, '50', '2019-12-18 09:30:00'), ('316', '1', 'testTask', 'renren', '0', null, '2', '2019-12-18 10:00:00'), ('317', '1', 'testTask', 'renren', '0', null, '1', '2019-12-18 10:30:00'), ('318', '1', 'testTask', 'renren', '0', null, '1', '2019-12-18 11:00:00'), ('319', '1', 'testTask', 'renren', '0', null, '6', '2019-12-18 12:00:00'), ('320', '1', 'testTask', 'renren', '0', null, '2', '2019-12-18 12:30:00'), ('321', '1', 'testTask', 'renren', '0', null, '0', '2019-12-18 13:00:00'), ('322', '1', 'testTask', 'renren', '0', null, '2', '2019-12-18 13:30:00'), ('323', '1', 'testTask', 'renren', '0', null, '3', '2019-12-18 14:00:00'), ('324', '1', 'testTask', 'renren', '0', null, '6', '2019-12-18 14:30:00'), ('325', '1', 'testTask', 'renren', '0', null, '5', '2019-12-18 15:00:00'), ('326', '1', 'testTask', 'renren', '0', null, '8', '2019-12-18 15:30:00'), ('327', '1', 'testTask', 'renren', '0', null, '1', '2019-12-18 16:00:00'), ('328', '1', 'testTask', 'renren', '0', null, '0', '2019-12-18 16:30:00'), ('329', '1', 'testTask', 'renren', '0', null, '1', '2019-12-18 17:00:00'), ('330', '1', 'testTask', 'renren', '0', null, '13', '2019-12-18 17:30:00'), ('331', '1', 'testTask', 'renren', '0', null, '2', '2019-12-18 18:00:00'), ('332', '1', 'testTask', 'renren', '0', null, '1', '2019-12-18 18:30:00'), ('333', '1', 'testTask', 'renren', '0', null, '1', '2019-12-18 19:00:00'), ('334', '1', 'testTask', 'renren', '0', null, '1', '2019-12-18 19:30:00'), ('335', '1', 'testTask', 'renren', '0', null, '6', '2019-12-18 20:00:00'), ('336', '1', 'testTask', 'renren', '0', null, '3', '2019-12-18 20:30:00'), ('337', '1', 'testTask', 'renren', '0', null, '2', '2019-12-18 21:00:00'), ('338', '1', 'testTask', 'renren', '0', null, '2', '2019-12-18 21:30:00'), ('339', '1', 'testTask', 'renren', '0', null, '5', '2019-12-18 22:00:00'), ('340', '1', 'testTask', 'renren', '0', null, '2', '2019-12-18 22:30:00'), ('341', '1', 'testTask', 'renren', '0', null, '2', '2019-12-18 23:00:00'), ('342', '1', 'testTask', 'renren', '0', null, '3', '2019-12-18 23:30:00'), ('343', '1', 'testTask', 'renren', '0', null, '164', '2019-12-19 00:00:00'), ('344', '1', 'testTask', 'renren', '0', null, '19', '2019-12-19 00:30:00'), ('345', '1', 'testTask', 'renren', '0', null, '2', '2019-12-19 01:00:00'), ('346', '1', 'testTask', 'renren', '0', null, '2', '2019-12-19 01:30:00'), ('347', '1', 'testTask', 'renren', '0', null, '3', '2019-12-19 02:00:00'), ('348', '1', 'testTask', 'renren', '0', null, '3', '2019-12-19 02:30:00'), ('349', '1', 'testTask', 'renren', '0', null, '2', '2019-12-19 03:00:00'), ('350', '1', 'testTask', 'renren', '0', null, '2', '2019-12-19 03:30:00'), ('351', '1', 'testTask', 'renren', '0', null, '7', '2019-12-19 04:00:00'), ('352', '1', 'testTask', 'renren', '0', null, '4', '2019-12-19 04:30:00'), ('353', '1', 'testTask', 'renren', '0', null, '1', '2019-12-19 05:00:00'), ('354', '1', 'testTask', 'renren', '0', null, '3', '2019-12-19 05:30:00'), ('355', '1', 'testTask', 'renren', '0', null, '3', '2019-12-19 06:00:00'), ('356', '1', 'testTask', 'renren', '0', null, '2', '2019-12-19 06:30:00'), ('357', '1', 'testTask', 'renren', '0', null, '3', '2019-12-19 07:00:00'), ('358', '1', 'testTask', 'renren', '0', null, '4', '2019-12-19 07:30:00'), ('359', '1', 'testTask', 'renren', '0', null, '1', '2019-12-19 08:00:00'), ('360', '1', 'testTask', 'renren', '0', null, '3', '2019-12-19 08:30:00'), ('361', '1', 'testTask', 'renren', '0', null, '1', '2019-12-19 09:00:00'), ('362', '1', 'testTask', 'renren', '0', null, '2', '2019-12-19 09:30:00'), ('363', '1', 'testTask', 'renren', '0', null, '0', '2019-12-19 10:00:00'), ('364', '1', 'testTask', 'renren', '0', null, '0', '2019-12-19 10:30:00'), ('365', '1', 'testTask', 'renren', '0', null, '9', '2019-12-19 11:00:00'), ('366', '1', 'testTask', 'renren', '0', null, '2', '2019-12-19 11:30:00'), ('367', '1', 'testTask', 'renren', '0', null, '1', '2019-12-19 12:00:00'), ('368', '1', 'testTask', 'renren', '0', null, '4', '2019-12-19 12:30:00'), ('369', '1', 'testTask', 'renren', '0', null, '1', '2019-12-19 13:00:00'), ('370', '1', 'testTask', 'renren', '0', null, '3', '2019-12-19 13:30:00'), ('371', '1', 'testTask', 'renren', '0', null, '5', '2019-12-19 14:00:00'), ('372', '1', 'testTask', 'renren', '0', null, '6', '2019-12-19 14:30:00'), ('373', '1', 'testTask', 'renren', '0', null, '2', '2019-12-19 15:00:00'), ('374', '1', 'testTask', 'renren', '0', null, '1', '2019-12-19 15:30:00'), ('375', '1', 'testTask', 'renren', '0', null, '38', '2019-12-19 16:00:00'), ('376', '1', 'testTask', 'renren', '0', null, '3', '2019-12-19 16:30:00'), ('377', '1', 'testTask', 'renren', '0', null, '0', '2019-12-19 17:00:00'), ('378', '1', 'testTask', 'renren', '0', null, '125', '2019-12-19 17:30:01'), ('379', '1', 'testTask', 'renren', '0', null, '18', '2019-12-19 18:00:00'), ('380', '1', 'testTask', 'renren', '0', null, '3', '2019-12-19 18:30:00'), ('381', '1', 'testTask', 'renren', '0', null, '16', '2019-12-19 19:00:00'), ('382', '1', 'testTask', 'renren', '0', null, '1', '2019-12-19 19:30:00'), ('383', '1', 'testTask', 'renren', '0', null, '44', '2019-12-19 20:00:00'), ('384', '1', 'testTask', 'renren', '0', null, '0', '2019-12-19 20:30:00'), ('385', '1', 'testTask', 'renren', '0', null, '1', '2019-12-19 21:00:00'), ('386', '1', 'testTask', 'renren', '0', null, '1', '2019-12-19 21:30:00'), ('387', '1', 'testTask', 'renren', '0', null, '1', '2019-12-19 22:00:00'), ('388', '1', 'testTask', 'renren', '0', null, '1', '2019-12-19 22:30:00'), ('389', '1', 'testTask', 'renren', '0', null, '1', '2019-12-19 23:00:00'), ('390', '1', 'testTask', 'renren', '0', null, '1', '2019-12-19 23:30:00'), ('391', '1', 'testTask', 'renren', '0', null, '19', '2019-12-20 00:00:00'), ('392', '1', 'testTask', 'renren', '0', null, '2', '2019-12-20 00:30:00'), ('393', '1', 'testTask', 'renren', '0', null, '2', '2019-12-20 01:00:00'), ('394', '1', 'testTask', 'renren', '0', null, '1', '2019-12-20 01:30:00'), ('395', '1', 'testTask', 'renren', '0', null, '1', '2019-12-20 02:00:00'), ('396', '1', 'testTask', 'renren', '0', null, '2', '2019-12-20 02:30:00'), ('397', '1', 'testTask', 'renren', '0', null, '1', '2019-12-20 03:00:00'), ('398', '1', 'testTask', 'renren', '0', null, '3', '2019-12-20 03:30:00'), ('399', '1', 'testTask', 'renren', '0', null, '1', '2019-12-20 04:00:00'), ('400', '1', 'testTask', 'renren', '0', null, '1', '2019-12-20 04:30:00'), ('401', '1', 'testTask', 'renren', '0', null, '2', '2019-12-20 05:00:00'), ('402', '1', 'testTask', 'renren', '0', null, '4', '2019-12-20 05:30:00'), ('403', '1', 'testTask', 'renren', '0', null, '3', '2019-12-20 06:00:00'), ('404', '1', 'testTask', 'renren', '0', null, '2', '2019-12-20 06:30:00'), ('405', '1', 'testTask', 'renren', '0', null, '2', '2019-12-20 07:00:00'), ('406', '1', 'testTask', 'renren', '0', null, '2', '2019-12-20 07:30:00'), ('407', '1', 'testTask', 'renren', '0', null, '3', '2019-12-20 08:00:00'), ('408', '1', 'testTask', 'renren', '0', null, '2', '2019-12-20 08:30:00'), ('409', '1', 'testTask', 'renren', '0', null, '15', '2019-12-20 09:00:00'), ('410', '1', 'testTask', 'renren', '0', null, '3', '2019-12-20 09:30:00'), ('411', '1', 'testTask', 'renren', '0', null, '2', '2019-12-20 10:00:00'), ('412', '1', 'testTask', 'renren', '0', null, '7', '2019-12-20 10:30:00'), ('413', '1', 'testTask', 'renren', '0', null, '8', '2019-12-20 11:00:01'), ('414', '1', 'testTask', 'renren', '0', null, '1', '2019-12-20 11:30:00'), ('415', '1', 'testTask', 'renren', '0', null, '1', '2019-12-20 12:00:00'), ('416', '1', 'testTask', 'renren', '0', null, '2', '2019-12-20 12:30:00'), ('417', '1', 'testTask', 'renren', '0', null, '1', '2019-12-20 13:00:00'), ('418', '1', 'testTask', 'renren', '0', null, '1', '2019-12-20 13:30:00'), ('419', '1', 'testTask', 'renren', '0', null, '6', '2019-12-20 14:00:00'), ('420', '1', 'testTask', 'renren', '0', null, '2', '2019-12-20 14:30:00'), ('421', '1', 'testTask', 'renren', '0', null, '1', '2019-12-20 15:00:00'), ('422', '1', 'testTask', 'renren', '0', null, '3', '2019-12-20 15:30:00'), ('423', '1', 'testTask', 'renren', '0', null, '1', '2019-12-20 16:00:00'), ('424', '1', 'testTask', 'renren', '0', null, '3', '2019-12-20 16:30:00'), ('425', '1', 'testTask', 'renren', '0', null, '2', '2019-12-20 17:00:00'), ('426', '1', 'testTask', 'renren', '0', null, '2', '2019-12-20 17:30:02'), ('427', '1', 'testTask', 'renren', '0', null, '2', '2019-12-20 18:00:00'), ('428', '1', 'testTask', 'renren', '0', null, '4', '2019-12-20 18:30:00'), ('429', '1', 'testTask', 'renren', '0', null, '3', '2019-12-20 19:00:00'), ('430', '1', 'testTask', 'renren', '0', null, '2', '2019-12-20 19:30:00'), ('431', '1', 'testTask', 'renren', '0', null, '2', '2019-12-20 20:00:00'), ('432', '1', 'testTask', 'renren', '0', null, '0', '2019-12-20 20:30:00'), ('433', '1', 'testTask', 'renren', '0', null, '2', '2019-12-20 21:00:00'), ('434', '1', 'testTask', 'renren', '0', null, '4', '2019-12-20 21:30:00'), ('435', '1', 'testTask', 'renren', '0', null, '3', '2019-12-20 22:00:00'), ('436', '1', 'testTask', 'renren', '0', null, '3', '2019-12-20 22:30:00'), ('437', '1', 'testTask', 'renren', '0', null, '4', '2019-12-20 23:00:00'), ('438', '1', 'testTask', 'renren', '0', null, '2', '2019-12-20 23:30:00'), ('439', '1', 'testTask', 'renren', '0', null, '182', '2019-12-21 00:00:00'), ('440', '1', 'testTask', 'renren', '0', null, '7', '2019-12-21 00:30:00'), ('441', '1', 'testTask', 'renren', '0', null, '5', '2019-12-21 01:00:00'), ('442', '1', 'testTask', 'renren', '0', null, '9', '2019-12-21 01:30:00'), ('443', '1', 'testTask', 'renren', '0', null, '0', '2019-12-21 02:00:00'), ('444', '1', 'testTask', 'renren', '0', null, '2', '2019-12-21 02:30:00'), ('445', '1', 'testTask', 'renren', '0', null, '3', '2019-12-21 03:00:00'), ('446', '1', 'testTask', 'renren', '0', null, '3', '2019-12-21 03:30:00'), ('447', '1', 'testTask', 'renren', '0', null, '1', '2019-12-21 04:00:00'), ('448', '1', 'testTask', 'renren', '0', null, '4', '2019-12-21 04:30:00'), ('449', '1', 'testTask', 'renren', '0', null, '3', '2019-12-21 05:00:00'), ('450', '1', 'testTask', 'renren', '0', null, '6', '2019-12-21 05:30:00'), ('451', '1', 'testTask', 'renren', '0', null, '3', '2019-12-21 06:00:00'), ('452', '1', 'testTask', 'renren', '0', null, '1', '2019-12-21 06:30:00'), ('453', '1', 'testTask', 'renren', '0', null, '2', '2019-12-21 07:00:00'), ('454', '1', 'testTask', 'renren', '0', null, '1', '2019-12-21 07:30:00'), ('455', '1', 'testTask', 'renren', '0', null, '3', '2019-12-21 08:00:00'), ('456', '1', 'testTask', 'renren', '0', null, '2', '2019-12-21 08:30:00'), ('457', '1', 'testTask', 'renren', '0', null, '1', '2019-12-21 09:00:00'), ('458', '1', 'testTask', 'renren', '0', null, '1', '2019-12-21 09:30:01'), ('459', '1', 'testTask', 'renren', '0', null, '3', '2019-12-21 10:00:00'), ('460', '1', 'testTask', 'renren', '0', null, '2', '2019-12-21 10:30:00'), ('461', '1', 'testTask', 'renren', '0', null, '5', '2019-12-21 11:00:00'), ('462', '1', 'testTask', 'renren', '0', null, '2', '2019-12-21 11:30:00'), ('463', '1', 'testTask', 'renren', '0', null, '0', '2019-12-21 12:00:00'), ('464', '1', 'testTask', 'renren', '0', null, '1', '2019-12-21 12:30:00'), ('465', '1', 'testTask', 'renren', '0', null, '3', '2019-12-21 13:00:00'), ('466', '1', 'testTask', 'renren', '0', null, '2', '2019-12-21 13:30:00'), ('467', '1', 'testTask', 'renren', '0', null, '3', '2019-12-21 14:00:00'), ('468', '1', 'testTask', 'renren', '0', null, '0', '2019-12-21 14:30:00'), ('469', '1', 'testTask', 'renren', '0', null, '2', '2019-12-21 15:00:00'), ('470', '1', 'testTask', 'renren', '0', null, '4', '2019-12-21 15:30:00'), ('471', '1', 'testTask', 'renren', '0', null, '3', '2019-12-21 16:00:00'), ('472', '1', 'testTask', 'renren', '0', null, '2', '2019-12-21 16:30:00'), ('473', '1', 'testTask', 'renren', '0', null, '0', '2019-12-21 17:00:00'), ('474', '1', 'testTask', 'renren', '0', null, '0', '2019-12-21 17:30:00'), ('475', '1', 'testTask', 'renren', '0', null, '1', '2019-12-21 18:00:00'), ('476', '1', 'testTask', 'renren', '0', null, '4', '2019-12-21 18:30:00'), ('477', '1', 'testTask', 'renren', '0', null, '2', '2019-12-21 19:00:00'), ('478', '1', 'testTask', 'renren', '0', null, '2', '2019-12-21 19:30:00'), ('479', '1', 'testTask', 'renren', '0', null, '5', '2019-12-21 20:00:00'), ('480', '1', 'testTask', 'renren', '0', null, '1', '2019-12-21 20:30:00'), ('481', '1', 'testTask', 'renren', '0', null, '5', '2019-12-21 21:00:00'), ('482', '1', 'testTask', 'renren', '0', null, '1', '2019-12-21 21:30:00'), ('483', '1', 'testTask', 'renren', '0', null, '3', '2019-12-21 22:00:00'), ('484', '1', 'testTask', 'renren', '0', null, '1', '2019-12-21 22:30:00'), ('485', '1', 'testTask', 'renren', '0', null, '0', '2019-12-21 23:00:00'), ('486', '1', 'testTask', 'renren', '0', null, '1', '2019-12-21 23:30:00'), ('487', '1', 'testTask', 'renren', '0', null, '5', '2019-12-22 00:00:00'), ('488', '1', 'testTask', 'renren', '0', null, '1', '2019-12-22 00:30:00'), ('489', '1', 'testTask', 'renren', '0', null, '2', '2019-12-22 01:00:00'), ('490', '1', 'testTask', 'renren', '0', null, '4', '2019-12-22 01:30:00'), ('491', '1', 'testTask', 'renren', '0', null, '2', '2019-12-22 02:00:00'), ('492', '1', 'testTask', 'renren', '0', null, '1', '2019-12-22 02:30:00'), ('493', '1', 'testTask', 'renren', '0', null, '4', '2019-12-22 03:00:00'), ('494', '1', 'testTask', 'renren', '0', null, '3', '2019-12-22 03:30:00'), ('495', '1', 'testTask', 'renren', '0', null, '1', '2019-12-22 04:00:00'), ('496', '1', 'testTask', 'renren', '0', null, '1', '2019-12-22 04:30:00'), ('497', '1', 'testTask', 'renren', '0', null, '2', '2019-12-22 05:00:00'), ('498', '1', 'testTask', 'renren', '0', null, '2', '2019-12-22 05:30:00'), ('499', '1', 'testTask', 'renren', '0', null, '4', '2019-12-22 06:00:00'), ('500', '1', 'testTask', 'renren', '0', null, '3', '2019-12-22 06:30:00'), ('501', '1', 'testTask', 'renren', '0', null, '3', '2019-12-22 07:00:00'), ('502', '1', 'testTask', 'renren', '0', null, '2', '2019-12-22 07:30:00'), ('503', '1', 'testTask', 'renren', '0', null, '3', '2019-12-22 08:00:00'), ('504', '1', 'testTask', 'renren', '0', null, '1', '2019-12-22 08:30:00'), ('505', '1', 'testTask', 'renren', '0', null, '1', '2019-12-22 09:00:00'), ('506', '1', 'testTask', 'renren', '0', null, '2', '2019-12-22 09:30:00'), ('507', '1', 'testTask', 'renren', '0', null, '1', '2019-12-22 10:00:00'), ('508', '1', 'testTask', 'renren', '0', null, '3', '2019-12-22 10:30:00'), ('509', '1', 'testTask', 'renren', '0', null, '1', '2019-12-22 11:00:00'), ('510', '1', 'testTask', 'renren', '0', null, '3', '2019-12-22 11:30:00'), ('511', '1', 'testTask', 'renren', '0', null, '3', '2019-12-22 12:00:00'), ('512', '1', 'testTask', 'renren', '0', null, '3', '2019-12-22 12:30:00'), ('513', '1', 'testTask', 'renren', '0', null, '0', '2019-12-22 13:00:00'), ('514', '1', 'testTask', 'renren', '0', null, '2', '2019-12-22 13:30:00'), ('515', '1', 'testTask', 'renren', '0', null, '3', '2019-12-22 14:00:00'), ('516', '1', 'testTask', 'renren', '0', null, '1', '2019-12-22 14:30:00'), ('517', '1', 'testTask', 'renren', '0', null, '3', '2019-12-22 15:00:00'), ('518', '1', 'testTask', 'renren', '0', null, '4', '2019-12-22 15:30:00'), ('519', '1', 'testTask', 'renren', '0', null, '3', '2019-12-22 16:00:00'), ('520', '1', 'testTask', 'renren', '0', null, '2', '2019-12-22 16:30:00'), ('521', '1', 'testTask', 'renren', '0', null, '1', '2019-12-22 17:00:00'), ('522', '1', 'testTask', 'renren', '0', null, '2', '2019-12-22 17:30:00'), ('523', '1', 'testTask', 'renren', '0', null, '3', '2019-12-22 18:00:00'), ('524', '1', 'testTask', 'renren', '0', null, '3', '2019-12-22 18:30:00'), ('525', '1', 'testTask', 'renren', '0', null, '2', '2019-12-22 19:00:00'), ('526', '1', 'testTask', 'renren', '0', null, '0', '2019-12-22 19:30:00'), ('527', '1', 'testTask', 'renren', '0', null, '0', '2019-12-22 20:00:00'), ('528', '1', 'testTask', 'renren', '0', null, '2', '2019-12-22 20:30:00'), ('529', '1', 'testTask', 'renren', '0', null, '1', '2019-12-22 21:00:00'), ('530', '1', 'testTask', 'renren', '0', null, '1', '2019-12-22 21:30:00'), ('531', '1', 'testTask', 'renren', '0', null, '0', '2019-12-22 22:00:00'), ('532', '1', 'testTask', 'renren', '0', null, '2', '2019-12-22 22:30:00'), ('533', '1', 'testTask', 'renren', '0', null, '3', '2019-12-22 23:00:00'), ('534', '1', 'testTask', 'renren', '0', null, '3', '2019-12-22 23:30:00'), ('535', '1', 'testTask', 'renren', '0', null, '6', '2019-12-23 00:00:00'), ('536', '1', 'testTask', 'renren', '0', null, '4', '2019-12-23 00:30:00'), ('537', '1', 'testTask', 'renren', '0', null, '3', '2019-12-23 01:00:00'), ('538', '1', 'testTask', 'renren', '0', null, '0', '2019-12-23 01:30:00'), ('539', '1', 'testTask', 'renren', '0', null, '3', '2019-12-23 02:00:00'), ('540', '1', 'testTask', 'renren', '0', null, '1', '2019-12-23 02:30:00'), ('541', '1', 'testTask', 'renren', '0', null, '4', '2019-12-23 03:00:00'), ('542', '1', 'testTask', 'renren', '0', null, '2', '2019-12-23 03:30:00'), ('543', '1', 'testTask', 'renren', '0', null, '1', '2019-12-23 04:00:00'), ('544', '1', 'testTask', 'renren', '0', null, '0', '2019-12-23 04:30:00'), ('545', '1', 'testTask', 'renren', '0', null, '3', '2019-12-23 05:00:00'), ('546', '1', 'testTask', 'renren', '0', null, '4', '2019-12-23 05:30:00'), ('547', '1', 'testTask', 'renren', '0', null, '3', '2019-12-23 06:00:00'), ('548', '1', 'testTask', 'renren', '0', null, '3', '2019-12-23 06:30:00'), ('549', '1', 'testTask', 'renren', '0', null, '4', '2019-12-23 07:00:00'), ('550', '1', 'testTask', 'renren', '0', null, '2', '2019-12-23 07:30:00'), ('551', '1', 'testTask', 'renren', '0', null, '0', '2019-12-23 08:00:00'), ('552', '1', 'testTask', 'renren', '0', null, '0', '2019-12-23 08:30:00'), ('553', '1', 'testTask', 'renren', '0', null, '1', '2019-12-23 09:00:00'), ('554', '1', 'testTask', 'renren', '0', null, '2', '2019-12-23 09:30:00'), ('555', '1', 'testTask', 'renren', '0', null, '3', '2019-12-23 10:00:00'), ('556', '1', 'testTask', 'renren', '0', null, '4', '2019-12-23 10:30:00'), ('557', '1', 'testTask', 'renren', '0', null, '89', '2019-12-23 11:00:01'), ('558', '1', 'testTask', 'renren', '0', null, '9', '2019-12-23 11:30:00'), ('559', '1', 'testTask', 'renren', '0', null, '5', '2019-12-23 12:00:00'), ('560', '1', 'testTask', 'renren', '0', null, '1', '2019-12-23 12:30:00'), ('561', '1', 'testTask', 'renren', '0', null, '2', '2019-12-23 13:00:00'), ('562', '1', 'testTask', 'renren', '0', null, '1', '2019-12-23 13:30:00'), ('563', '1', 'testTask', 'renren', '0', null, '2', '2019-12-23 14:00:00'), ('564', '1', 'testTask', 'renren', '0', null, '2', '2019-12-23 14:30:00'), ('565', '1', 'testTask', 'renren', '0', null, '4', '2019-12-23 15:00:00'), ('566', '1', 'testTask', 'renren', '0', null, '5', '2019-12-23 15:30:00'), ('567', '1', 'testTask', 'renren', '0', null, '3', '2019-12-23 16:00:00'), ('568', '1', 'testTask', 'renren', '0', null, '0', '2019-12-23 16:30:00'), ('569', '1', 'testTask', 'renren', '0', null, '3', '2019-12-23 17:00:00'), ('570', '1', 'testTask', 'renren', '0', null, '2', '2019-12-23 17:30:00'), ('571', '1', 'testTask', 'renren', '0', null, '3', '2019-12-23 18:00:00'), ('572', '1', 'testTask', 'renren', '0', null, '2', '2019-12-23 18:30:00'), ('573', '1', 'testTask', 'renren', '0', null, '1', '2019-12-23 19:00:00'), ('574', '1', 'testTask', 'renren', '0', null, '2', '2019-12-23 19:30:00'), ('575', '1', 'testTask', 'renren', '0', null, '2', '2019-12-23 20:00:00'), ('576', '1', 'testTask', 'renren', '0', null, '1', '2019-12-23 20:30:00'), ('577', '1', 'testTask', 'renren', '0', null, '7', '2019-12-23 21:00:00'), ('578', '1', 'testTask', 'renren', '0', null, '5', '2019-12-23 21:30:00'), ('579', '1', 'testTask', 'renren', '0', null, '1', '2019-12-23 22:00:00'), ('580', '1', 'testTask', 'renren', '0', null, '0', '2019-12-23 22:30:00'), ('581', '1', 'testTask', 'renren', '0', null, '4', '2019-12-23 23:00:00'), ('582', '1', 'testTask', 'renren', '0', null, '0', '2019-12-23 23:30:00'), ('583', '1', 'testTask', 'renren', '0', null, '140', '2019-12-24 00:00:00'), ('584', '1', 'testTask', 'renren', '0', null, '2', '2019-12-24 00:30:00'), ('585', '1', 'testTask', 'renren', '0', null, '0', '2019-12-24 01:00:00'), ('586', '1', 'testTask', 'renren', '0', null, '1', '2019-12-24 01:30:00'), ('587', '1', 'testTask', 'renren', '0', null, '1', '2019-12-24 02:00:00'), ('588', '1', 'testTask', 'renren', '0', null, '0', '2019-12-24 02:30:00'), ('589', '1', 'testTask', 'renren', '0', null, '2', '2019-12-24 03:00:00'), ('590', '1', 'testTask', 'renren', '0', null, '1', '2019-12-24 03:30:00'), ('591', '1', 'testTask', 'renren', '0', null, '5', '2019-12-24 04:00:00'), ('592', '1', 'testTask', 'renren', '0', null, '2', '2019-12-24 04:30:00'), ('593', '1', 'testTask', 'renren', '0', null, '4', '2019-12-24 05:00:00'), ('594', '1', 'testTask', 'renren', '0', null, '1', '2019-12-24 05:30:00'), ('595', '1', 'testTask', 'renren', '0', null, '0', '2019-12-24 06:00:00'), ('596', '1', 'testTask', 'renren', '0', null, '4', '2019-12-24 06:30:00'), ('597', '1', 'testTask', 'renren', '0', null, '2', '2019-12-24 07:00:00'), ('598', '1', 'testTask', 'renren', '0', null, '4', '2019-12-24 07:30:00'), ('599', '1', 'testTask', 'renren', '0', null, '0', '2019-12-24 08:00:00'), ('600', '1', 'testTask', 'renren', '0', null, '6', '2019-12-24 08:30:00'), ('601', '1', 'testTask', 'renren', '0', null, '2', '2019-12-24 09:00:00'), ('602', '1', 'testTask', 'renren', '0', null, '2', '2019-12-24 09:30:00'), ('603', '1', 'testTask', 'renren', '0', null, '8', '2019-12-24 10:00:00'), ('604', '1', 'testTask', 'renren', '0', null, '8', '2019-12-24 10:30:00'), ('605', '1', 'testTask', 'renren', '0', null, '1', '2019-12-24 11:00:00'), ('606', '1', 'testTask', 'renren', '0', null, '15', '2019-12-24 11:30:00'), ('607', '1', 'testTask', 'renren', '0', null, '3', '2019-12-24 12:00:00'), ('608', '1', 'testTask', 'renren', '0', null, '6', '2019-12-24 12:30:00'), ('609', '1', 'testTask', 'renren', '0', null, '1', '2019-12-24 13:00:00'), ('610', '1', 'testTask', 'renren', '0', null, '2', '2019-12-24 13:30:00'), ('611', '1', 'testTask', 'renren', '0', null, '2', '2019-12-24 14:00:00'), ('612', '1', 'testTask', 'renren', '0', null, '3', '2019-12-24 14:30:00'), ('613', '1', 'testTask', 'renren', '0', null, '3', '2019-12-24 15:00:00'), ('614', '1', 'testTask', 'renren', '0', null, '4', '2019-12-24 15:30:00'), ('615', '1', 'testTask', 'renren', '0', null, '4', '2019-12-24 16:00:00'), ('616', '1', 'testTask', 'renren', '0', null, '6', '2019-12-24 16:30:00'), ('617', '1', 'testTask', 'renren', '0', null, '2', '2019-12-24 17:00:00'), ('618', '1', 'testTask', 'renren', '0', null, '3', '2019-12-24 17:30:00'), ('619', '1', 'testTask', 'renren', '0', null, '6', '2019-12-24 18:00:01'), ('620', '1', 'testTask', 'renren', '0', null, '3', '2019-12-24 18:30:00'), ('621', '1', 'testTask', 'renren', '0', null, '2', '2019-12-24 19:00:00'), ('622', '1', 'testTask', 'renren', '0', null, '2', '2019-12-24 19:30:00'), ('623', '1', 'testTask', 'renren', '0', null, '8', '2019-12-24 20:00:00'), ('624', '1', 'testTask', 'renren', '0', null, '1', '2019-12-24 20:30:00'), ('625', '1', 'testTask', 'renren', '0', null, '3', '2019-12-24 21:00:00'), ('626', '1', 'testTask', 'renren', '0', null, '2', '2019-12-24 21:30:00'), ('627', '1', 'testTask', 'renren', '0', null, '3', '2019-12-24 22:00:00'), ('628', '1', 'testTask', 'renren', '0', null, '1', '2019-12-24 22:30:00'), ('629', '1', 'testTask', 'renren', '0', null, '3', '2019-12-24 23:00:00'), ('630', '1', 'testTask', 'renren', '0', null, '3', '2019-12-24 23:30:00'), ('631', '1', 'testTask', 'renren', '0', null, '139', '2019-12-25 00:00:00'), ('632', '1', 'testTask', 'renren', '0', null, '12', '2019-12-25 00:30:00'), ('633', '1', 'testTask', 'renren', '0', null, '0', '2019-12-25 01:00:00'), ('634', '1', 'testTask', 'renren', '0', null, '2', '2019-12-25 01:30:00'), ('635', '1', 'testTask', 'renren', '0', null, '1', '2019-12-25 02:00:00'), ('636', '1', 'testTask', 'renren', '0', null, '3', '2019-12-25 02:30:00'), ('637', '1', 'testTask', 'renren', '0', null, '3', '2019-12-25 03:00:00'), ('638', '1', 'testTask', 'renren', '0', null, '2', '2019-12-25 03:30:00'), ('639', '1', 'testTask', 'renren', '0', null, '1', '2019-12-25 04:00:00'), ('640', '1', 'testTask', 'renren', '0', null, '1', '2019-12-25 04:30:00'), ('641', '1', 'testTask', 'renren', '0', null, '2', '2019-12-25 05:00:00'), ('642', '1', 'testTask', 'renren', '0', null, '1', '2019-12-25 05:30:00'), ('643', '1', 'testTask', 'renren', '0', null, '0', '2019-12-25 06:00:00'), ('644', '1', 'testTask', 'renren', '0', null, '3', '2019-12-25 06:30:00'), ('645', '1', 'testTask', 'renren', '0', null, '2', '2019-12-25 07:00:00'), ('646', '1', 'testTask', 'renren', '0', null, '3', '2019-12-25 07:30:00'), ('647', '1', 'testTask', 'renren', '0', null, '2', '2019-12-25 08:00:00'), ('648', '1', 'testTask', 'renren', '0', null, '3', '2019-12-25 08:30:00'), ('649', '1', 'testTask', 'renren', '0', null, '4', '2019-12-25 09:00:00'), ('650', '1', 'testTask', 'renren', '0', null, '5', '2019-12-25 09:30:00'), ('651', '1', 'testTask', 'renren', '0', null, '4', '2019-12-25 10:00:00'), ('652', '1', 'testTask', 'renren', '0', null, '2', '2019-12-25 10:30:00'), ('653', '1', 'testTask', 'renren', '0', null, '5', '2019-12-25 11:00:00'), ('654', '1', 'testTask', 'renren', '0', null, '3', '2019-12-25 11:30:00'), ('655', '1', 'testTask', 'renren', '0', null, '1', '2019-12-25 12:00:00'), ('656', '1', 'testTask', 'renren', '0', null, '0', '2019-12-25 12:30:00'), ('657', '1', 'testTask', 'renren', '0', null, '1', '2019-12-25 13:00:00'), ('658', '1', 'testTask', 'renren', '0', null, '2', '2019-12-25 13:30:00'), ('659', '1', 'testTask', 'renren', '0', null, '6', '2019-12-25 16:30:00'), ('660', '1', 'testTask', 'renren', '0', null, '0', '2019-12-25 17:00:00'), ('661', '1', 'testTask', 'renren', '0', null, '4', '2019-12-25 17:30:00'), ('662', '1', 'testTask', 'renren', '0', null, '17', '2019-12-25 18:00:00'), ('663', '1', 'testTask', 'renren', '0', null, '2', '2019-12-25 18:30:00'), ('664', '1', 'testTask', 'renren', '0', null, '2', '2019-12-25 19:00:00'), ('665', '1', 'testTask', 'renren', '0', null, '2', '2019-12-25 19:30:00'), ('666', '1', 'testTask', 'renren', '0', null, '5', '2019-12-25 20:00:00'), ('667', '1', 'testTask', 'renren', '0', null, '0', '2019-12-25 20:30:00'), ('668', '1', 'testTask', 'renren', '0', null, '2', '2019-12-25 21:00:00'), ('669', '1', 'testTask', 'renren', '0', null, '0', '2019-12-25 21:30:00'), ('670', '1', 'testTask', 'renren', '0', null, '4', '2019-12-25 22:00:00'), ('671', '1', 'testTask', 'renren', '0', null, '1', '2019-12-25 22:30:00'), ('672', '1', 'testTask', 'renren', '0', null, '0', '2019-12-25 23:00:00'), ('673', '1', 'testTask', 'renren', '0', null, '0', '2019-12-25 23:30:00'), ('674', '1', 'testTask', 'renren', '0', null, '101', '2019-12-26 00:00:00'), ('675', '1', 'testTask', 'renren', '0', null, '8', '2019-12-26 00:30:00'), ('676', '1', 'testTask', 'renren', '0', null, '3', '2019-12-26 01:00:00'), ('677', '1', 'testTask', 'renren', '0', null, '1', '2019-12-26 01:30:00'), ('678', '1', 'testTask', 'renren', '0', null, '2', '2019-12-26 02:00:00'), ('679', '1', 'testTask', 'renren', '0', null, '3', '2019-12-26 02:30:00'), ('680', '1', 'testTask', 'renren', '0', null, '2', '2019-12-26 03:00:00'), ('681', '1', 'testTask', 'renren', '0', null, '2', '2019-12-26 03:30:00'), ('682', '1', 'testTask', 'renren', '0', null, '2', '2019-12-26 04:00:00'), ('683', '1', 'testTask', 'renren', '0', null, '2', '2019-12-26 04:30:00'), ('684', '1', 'testTask', 'renren', '0', null, '4', '2019-12-26 05:00:00'), ('685', '1', 'testTask', 'renren', '0', null, '4', '2019-12-26 05:30:00'), ('686', '1', 'testTask', 'renren', '0', null, '6', '2019-12-26 06:00:00'), ('687', '1', 'testTask', 'renren', '0', null, '3', '2019-12-26 06:30:00'), ('688', '1', 'testTask', 'renren', '0', null, '1', '2019-12-26 07:00:00'), ('689', '1', 'testTask', 'renren', '0', null, '0', '2019-12-26 07:30:00'), ('690', '1', 'testTask', 'renren', '0', null, '2', '2019-12-26 08:00:00'), ('691', '1', 'testTask', 'renren', '0', null, '3', '2019-12-26 08:30:00'), ('692', '1', 'testTask', 'renren', '0', null, '2', '2019-12-26 09:00:00'), ('693', '1', 'testTask', 'renren', '0', null, '29', '2019-12-26 09:30:00'), ('694', '1', 'testTask', 'renren', '0', null, '2', '2019-12-26 10:30:00'), ('695', '1', 'testTask', 'renren', '0', null, '7', '2019-12-26 11:00:00'), ('696', '1', 'testTask', 'renren', '0', null, '10', '2019-12-26 11:30:00'), ('697', '1', 'testTask', 'renren', '0', null, '12', '2019-12-26 12:00:00'), ('698', '1', 'testTask', 'renren', '0', null, '3', '2019-12-26 12:30:00'), ('699', '1', 'testTask', 'renren', '0', null, '2', '2019-12-26 13:00:00'), ('700', '1', 'testTask', 'renren', '0', null, '2', '2019-12-26 13:30:00'), ('701', '1', 'testTask', 'renren', '0', null, '3', '2019-12-26 14:00:00'), ('702', '1', 'testTask', 'renren', '0', null, '3', '2019-12-26 14:30:00'), ('703', '1', 'testTask', 'renren', '0', null, '8', '2019-12-26 15:00:00'), ('704', '1', 'testTask', 'renren', '0', null, '4', '2019-12-26 15:30:00'), ('705', '1', 'testTask', 'renren', '0', null, '9', '2019-12-26 16:00:00'), ('706', '1', 'testTask', 'renren', '0', null, '19', '2019-12-26 16:30:00'), ('707', '1', 'testTask', 'renren', '0', null, '4', '2019-12-26 17:00:00'), ('708', '1', 'testTask', 'renren', '0', null, '20', '2019-12-26 17:30:00'), ('709', '1', 'testTask', 'renren', '0', null, '7', '2019-12-26 18:00:00'), ('710', '1', 'testTask', 'renren', '0', null, '6', '2019-12-26 18:30:00'), ('711', '1', 'testTask', 'renren', '0', null, '5', '2019-12-26 19:00:00'), ('712', '1', 'testTask', 'renren', '0', null, '1', '2019-12-26 19:30:00'), ('713', '1', 'testTask', 'renren', '0', null, '3', '2019-12-26 20:00:00'), ('714', '1', 'testTask', 'renren', '0', null, '17', '2019-12-26 20:30:00'), ('715', '1', 'testTask', 'renren', '0', null, '4', '2019-12-26 21:00:00'), ('716', '1', 'testTask', 'renren', '0', null, '9', '2019-12-26 21:30:00'), ('717', '1', 'testTask', 'renren', '0', null, '3', '2019-12-26 22:00:00'), ('718', '1', 'testTask', 'renren', '0', null, '5', '2019-12-26 22:30:00'), ('719', '1', 'testTask', 'renren', '0', null, '9', '2019-12-26 23:00:00'), ('720', '1', 'testTask', 'renren', '0', null, '5', '2019-12-26 23:30:00'), ('721', '1', 'testTask', 'renren', '0', null, '345', '2019-12-27 00:00:00'), ('722', '1', 'testTask', 'renren', '0', null, '17', '2019-12-27 00:30:00'), ('723', '1', 'testTask', 'renren', '0', null, '2', '2019-12-27 01:00:00'), ('724', '1', 'testTask', 'renren', '0', null, '6', '2019-12-27 01:30:00'), ('725', '1', 'testTask', 'renren', '0', null, '2', '2019-12-27 02:00:00'), ('726', '1', 'testTask', 'renren', '0', null, '18', '2019-12-27 02:30:00'), ('727', '1', 'testTask', 'renren', '0', null, '3', '2019-12-27 03:00:00'), ('728', '1', 'testTask', 'renren', '0', null, '2', '2019-12-27 03:30:00'), ('729', '1', 'testTask', 'renren', '0', null, '1', '2019-12-27 04:00:00'), ('730', '1', 'testTask', 'renren', '0', null, '0', '2019-12-27 04:30:00'), ('731', '1', 'testTask', 'renren', '0', null, '6', '2019-12-27 05:00:00'), ('732', '1', 'testTask', 'renren', '0', null, '1', '2019-12-27 05:30:00'), ('733', '1', 'testTask', 'renren', '0', null, '1', '2019-12-27 06:00:00'), ('734', '1', 'testTask', 'renren', '0', null, '1', '2019-12-27 06:30:00'), ('735', '1', 'testTask', 'renren', '0', null, '4', '2019-12-27 07:00:00'), ('736', '1', 'testTask', 'renren', '0', null, '1', '2019-12-27 07:30:00'), ('737', '1', 'testTask', 'renren', '0', null, '2', '2019-12-27 08:00:00'), ('738', '1', 'testTask', 'renren', '0', null, '0', '2019-12-27 08:30:00'), ('739', '1', 'testTask', 'renren', '0', null, '0', '2019-12-27 09:00:00');
COMMIT;

-- ----------------------------
--  Table structure for `sys_captcha`
-- ----------------------------
DROP TABLE IF EXISTS `sys_captcha`;
CREATE TABLE `sys_captcha` (
  `uuid` char(36) NOT NULL COMMENT 'uuid',
  `code` varchar(6) NOT NULL COMMENT '验证码',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统验证码';

-- ----------------------------
--  Records of `sys_captcha`
-- ----------------------------
BEGIN;
INSERT INTO `sys_captcha` VALUES ('0f5e28ce-69c3-4a0e-8cae-47bce0eb091e', 'cn7eb', '2019-12-13 09:27:30'), ('2b9e9188-cf19-4753-8c35-53da860def58', '5ge27', '2019-12-25 17:37:23'), ('2bc55799-b685-4f9a-8981-bb2773e3ed07', 'd2pna', '2019-12-19 10:18:43'), ('3363b3f0-8188-4d5f-8183-56866c0a312e', 'mpad5', '2019-12-20 08:58:17'), ('346d3161-4bc8-4e72-8456-0060f0a068e2', '85p7a', '2019-12-17 16:04:10'), ('43ba18a3-01ad-462a-8b5f-b10a380d351c', '24m8y', '2019-12-13 09:42:27'), ('4ad1c977-af0d-4c8a-869e-22321ab04b6d', '7db43', '2019-12-13 09:30:16'), ('71b5417a-26f0-4dc9-8e05-424c940d171f', '5em85', '2019-12-19 20:33:11'), ('8497530a-adf8-4f68-8c9f-eeab1640cc71', 'dp46x', '2019-12-05 19:27:18'), ('8fcf2c45-f10f-40be-829d-72f4322bf3c5', 'fn43d', '2019-12-13 09:38:08'), ('95d66da9-461b-4b33-879c-a36316a6d552', '3nd32', '2019-12-26 09:46:25'), ('a2933735-ec7a-43da-8917-3ad97f9901a9', 'mn3xx', '2019-12-13 09:33:51'), ('c70fe155-e54c-405f-8793-fcf5210e9f55', 'abbnb', '2019-12-12 16:09:14'), ('c974f019-91f8-4def-84e6-db6e21642fa2', 'y5mnn', '2019-12-16 14:58:31'), ('d64b09d1-80bd-419d-84f9-2f7e4f79db53', 'y3n26', '2019-12-05 18:42:12'), ('d9371ce9-38a6-4de1-8abd-da325d3e80ec', '5p7ac', '2019-12-12 15:56:31'), ('fc5b5859-b45e-4831-8676-2652e7bd3a3d', 'cyf5w', '2019-12-05 18:46:02');
COMMIT;

-- ----------------------------
--  Table structure for `sys_config`
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `param_key` varchar(50) DEFAULT NULL COMMENT 'key',
  `param_value` varchar(2000) DEFAULT NULL COMMENT 'value',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态   0：隐藏   1：显示',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `param_key` (`param_key`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COMMENT='系统配置信息表';

-- ----------------------------
--  Records of `sys_config`
-- ----------------------------
BEGIN;
INSERT INTO `sys_config` VALUES ('1', 'CLOUD_STORAGE_CONFIG_KEY', '{\"aliyunAccessKeyId\":\"\",\"aliyunAccessKeySecret\":\"\",\"aliyunBucketName\":\"\",\"aliyunDomain\":\"\",\"aliyunEndPoint\":\"\",\"aliyunPrefix\":\"\",\"qcloudBucketName\":\"\",\"qcloudDomain\":\"\",\"qcloudPrefix\":\"\",\"qcloudSecretId\":\"\",\"qcloudSecretKey\":\"\",\"qiniuAccessKey\":\"NrgMfABZxWLo5B-YYSjoE8-AZ1EISdi1Z3ubLOeZ\",\"qiniuBucketName\":\"ios-app\",\"qiniuDomain\":\"http://7xqbwh.dl1.z0.glb.clouddn.com\",\"qiniuPrefix\":\"upload\",\"qiniuSecretKey\":\"uIwJHevMRWU0VLxFvgy0tAcOdGqasdtVlJkdy6vV\",\"type\":1}', '0', '云存储配置信息'), ('5', 'MASTER_JMETER_HOME_KEY', '/Users/young/Desktop/apache-jmeter-5.0', '1', '本地Jmeter_home绝对路径（Jmeter版本不要高于引擎版本）'), ('6', 'MASTER_JMETER_CASES_HOME_KEY', '/Users/young/Desktop/EstressTestCases', '1', '本地保存用例数据的绝对路径，不要随意切换会导致文件找不到错误'), ('7', 'MASTER_JMETER_USE_SCRIPT_KEY', 'false', '1', 'false:在服务器进程内启动Jmeter压测。true:启动Jmeter_home中的命令压测'), ('8', 'MASTER_JMETER_REPLACE_FILE_KEY', '1', '1', '0：同名文件禁止上传；1：同名文件上传覆盖（禁止上传第二个）；2：允许不同用例的同名文件（支持上传覆盖）；默认值1'), ('9', 'MASTER_JMETER_GENERATE_REPORT_KEY', 'true', '1', 'true:本地web程序进程生成测试报告，可以多线程并发生成。false:使用Jmeter_home中的命令生成测试报告'), ('10', 'JMETER_THREADGROUP_SET_KEY', 'true', '1', 'true：开启线程组管理功能，上传脚本时线程组配置将入库管理，默认false。'), ('11', 'SCRIPT_SCHEDULER_DURATION_KEY', 'true', '1', 'true:脚本限时执行生效，具体时间由脚本单独配置，是默认值 false:取消脚本限时执行');
COMMIT;

-- ----------------------------
--  Table structure for `sys_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `time` bigint(20) NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COMMENT='系统日志';

-- ----------------------------
--  Records of `sys_log`
-- ----------------------------
BEGIN;
INSERT INTO `sys_log` VALUES ('1', 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":41,\"parentId\":0,\"name\":\"性能测试\",\"url\":\"\",\"perms\":\"\",\"type\":0,\"icon\":\"oss\",\"orderNum\":0}]', '210', '0:0:0:0:0:0:0:1', '2019-12-05 15:01:10'), ('2', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":36,\"parentId\":41,\"name\":\"商品管理\",\"url\":\"generator/goods\",\"type\":1,\"icon\":\"config\",\"orderNum\":6}]', '62', '0:0:0:0:0:0:0:1', '2019-12-05 15:48:49'), ('3', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":42,\"parentId\":41,\"name\":\"性能测试用例\",\"url\":\"performance/performancecase\",\"type\":1,\"icon\":\"config\",\"orderNum\":6}]', '43', '0:0:0:0:0:0:0:1', '2019-12-05 15:49:17'), ('4', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":47,\"parentId\":41,\"name\":\"性能测试用例文件\",\"url\":\"performance/performancecasefile\",\"type\":1,\"icon\":\"config\",\"orderNum\":6}]', '45', '0:0:0:0:0:0:0:1', '2019-12-05 15:49:59'), ('5', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":52,\"parentId\":41,\"name\":\"性能测试分布式节点\",\"url\":\"performance/performanceslave\",\"type\":1,\"icon\":\"config\",\"orderNum\":6}]', '56', '0:0:0:0:0:0:0:1', '2019-12-05 15:50:30'), ('6', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":57,\"parentId\":41,\"name\":\"性能测试报告\",\"url\":\"performance/performancecasereports\",\"type\":1,\"icon\":\"config\",\"orderNum\":6}]', '47', '0:0:0:0:0:0:0:1', '2019-12-05 15:50:58'), ('7', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":62,\"parentId\":41,\"name\":\"线程组管理\",\"url\":\"performance/performancethreadset\",\"type\":1,\"icon\":\"config\",\"orderNum\":6}]', '54', '0:0:0:0:0:0:0:1', '2019-12-05 15:51:24'), ('8', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":67,\"parentId\":41,\"name\":\"调试测试报告\",\"url\":\"performance/debugcasereports\",\"type\":1,\"icon\":\"config\",\"orderNum\":6}]', '43', '0:0:0:0:0:0:0:1', '2019-12-05 15:52:00'), ('9', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":41,\"parentId\":0,\"name\":\"性能测试\",\"url\":\"\",\"perms\":\"\",\"type\":0,\"icon\":\"oss\",\"orderNum\":0}]', '52', '0:0:0:0:0:0:0:1', '2019-12-05 16:17:53'), ('10', 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[37]', '204', '0:0:0:0:0:0:0:1', '2019-12-12 16:05:35'), ('11', 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[38]', '89', '0:0:0:0:0:0:0:1', '2019-12-12 16:05:47'), ('12', 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[39]', '80', '0:0:0:0:0:0:0:1', '2019-12-12 16:05:56'), ('13', 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[40]', '70', '0:0:0:0:0:0:0:1', '2019-12-12 16:07:06'), ('14', 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[36]', '67', '0:0:0:0:0:0:0:1', '2019-12-12 16:07:16'), ('15', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":1,\"parentId\":0,\"name\":\"系统管理\",\"type\":0,\"icon\":\"system\",\"orderNum\":1}]', '37', '0:0:0:0:0:0:0:1', '2019-12-12 16:19:30'), ('16', 'admin', '保存配置', 'io.renren.modules.sys.controller.SysConfigController.save()', '[{\"id\":2,\"paramKey\":\"ces\",\"paramValue\":\"ss\",\"remark\":\"\"}]', '209', '0:0:0:0:0:0:0:1', '2019-12-16 14:55:56'), ('17', 'admin', '删除配置', 'io.renren.modules.sys.controller.SysConfigController.delete()', '[[2]]', '131', '0:0:0:0:0:0:0:1', '2019-12-16 14:56:02'), ('18', 'admin', '保存配置', 'io.renren.modules.sys.controller.SysConfigController.save()', '[{\"id\":3,\"paramKey\":\"test\",\"paramValue\":\"test\",\"remark\":\"\"}]', '29', '0:0:0:0:0:0:0:1', '2019-12-16 14:56:09'), ('19', 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":72,\"parentId\":42,\"name\":\"上传文件\",\"url\":\"\",\"perms\":\"performance:performancecase:upload\",\"type\":2,\"icon\":\"\",\"orderNum\":0}]', '67', '0:0:0:0:0:0:0:1', '2019-12-16 15:37:39'), ('20', 'admin', '删除配置', 'io.renren.modules.sys.controller.SysConfigController.delete()', '[[3]]', '122', '0:0:0:0:0:0:0:1', '2019-12-16 15:42:05'), ('21', 'admin', '保存配置', 'io.renren.modules.sys.controller.SysConfigController.save()', '[{\"id\":4,\"paramKey\":\"test\",\"paramValue\":\"test\",\"remark\":\"\"}]', '143', '0:0:0:0:0:0:0:1', '2019-12-16 16:04:29'), ('22', 'admin', '删除配置', 'io.renren.modules.sys.controller.SysConfigController.delete()', '[[4]]', '327', '0:0:0:0:0:0:0:1', '2019-12-16 16:04:35'), ('23', 'admin', '保存配置', 'io.renren.modules.sys.controller.SysConfigController.save()', '[{\"id\":5,\"paramKey\":\"MASTER_JMETER_HOME_KEY\",\"paramValue\":\"/Users/young/Desktop/apache-jmeter-5.0\",\"remark\":\"本地Jmeter_home绝对路径（Jmeter版本不要高于引擎版本）\"}]', '35', '0:0:0:0:0:0:0:1', '2019-12-16 16:05:19'), ('24', 'admin', '保存配置', 'io.renren.modules.sys.controller.SysConfigController.save()', '[{\"id\":6,\"paramKey\":\"MASTER_JMETER_CASES_HOME_KEY\",\"paramValue\":\"/Users/young/Desktop/EstressTestCases\",\"remark\":\"本地保存用例数据的绝对路径，不要随意切换会导致文件找不到错误\"}]', '27', '0:0:0:0:0:0:0:1', '2019-12-16 16:06:04'), ('25', 'admin', '保存配置', 'io.renren.modules.sys.controller.SysConfigController.save()', '[{\"id\":7,\"paramKey\":\"MASTER_JMETER_USE_SCRIPT_KEY\",\"paramValue\":\"false\",\"remark\":\"false:在服务器进程内启动Jmeter压测。true:启动Jmeter_home中的命令压测\"}]', '32', '0:0:0:0:0:0:0:1', '2019-12-16 16:06:54'), ('26', 'admin', '保存配置', 'io.renren.modules.sys.controller.SysConfigController.save()', '[{\"id\":8,\"paramKey\":\"MASTER_JMETER_REPLACE_FILE_KEY\",\"paramValue\":\"1\",\"remark\":\"0：同名文件禁止上传；1：同名文件上传覆盖（禁止上传第二个）；2：允许不同用例的同名文件（支持上传覆盖）；默认值1\"}]', '29', '0:0:0:0:0:0:0:1', '2019-12-16 16:12:04'), ('27', 'admin', '保存配置', 'io.renren.modules.sys.controller.SysConfigController.save()', '[{\"id\":9,\"paramKey\":\"MASTER_JMETER_GENERATE_REPORT_KEY\",\"paramValue\":\"true\",\"remark\":\"true:本地web程序进程生成测试报告，可以多线程并发生成。false:使用Jmeter_home中的命令生成测试报告\"}]', '30', '0:0:0:0:0:0:0:1', '2019-12-16 16:12:40'), ('28', 'admin', '保存配置', 'io.renren.modules.sys.controller.SysConfigController.save()', '[{\"id\":10,\"paramKey\":\"JMETER_THREADGROUP_SET_KEY\",\"paramValue\":\"true\",\"remark\":\"true：开启线程组管理功能，上传脚本时线程组配置将入库管理，默认false。\"}]', '26', '0:0:0:0:0:0:0:1', '2019-12-16 16:13:10'), ('29', 'admin', '保存配置', 'io.renren.modules.sys.controller.SysConfigController.save()', '[{\"id\":11,\"paramKey\":\"SCRIPT_SCHEDULER_DURATION_KEY\",\"paramValue\":\"true\",\"remark\":\"true:脚本限时执行生效，具体时间由脚本单独配置，是默认值 false:取消脚本限时执行\"}]', '27', '0:0:0:0:0:0:0:1', '2019-12-16 16:13:36');
COMMIT;

-- ----------------------------
--  Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8mb4 COMMENT='菜单管理';

-- ----------------------------
--  Records of `sys_menu`
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES ('1', '0', '系统管理', null, null, '0', 'system', '1'), ('2', '1', '管理员列表', 'sys/user', null, '1', 'admin', '1'), ('3', '1', '角色管理', 'sys/role', null, '1', 'role', '2'), ('4', '1', '菜单管理', 'sys/menu', null, '1', 'menu', '3'), ('5', '1', 'SQL监控', 'http://localhost:8080/renren-fast/druid/sql.html', null, '1', 'sql', '4'), ('6', '1', '定时任务', 'job/schedule', null, '1', 'job', '5'), ('7', '6', '查看', null, 'sys:schedule:list,sys:schedule:info', '2', null, '0'), ('8', '6', '新增', null, 'sys:schedule:save', '2', null, '0'), ('9', '6', '修改', null, 'sys:schedule:update', '2', null, '0'), ('10', '6', '删除', null, 'sys:schedule:delete', '2', null, '0'), ('11', '6', '暂停', null, 'sys:schedule:pause', '2', null, '0'), ('12', '6', '恢复', null, 'sys:schedule:resume', '2', null, '0'), ('13', '6', '立即执行', null, 'sys:schedule:run', '2', null, '0'), ('14', '6', '日志列表', null, 'sys:schedule:log', '2', null, '0'), ('15', '2', '查看', null, 'sys:user:list,sys:user:info', '2', null, '0'), ('16', '2', '新增', null, 'sys:user:save,sys:role:select', '2', null, '0'), ('17', '2', '修改', null, 'sys:user:update,sys:role:select', '2', null, '0'), ('18', '2', '删除', null, 'sys:user:delete', '2', null, '0'), ('19', '3', '查看', null, 'sys:role:list,sys:role:info', '2', null, '0'), ('20', '3', '新增', null, 'sys:role:save,sys:menu:list', '2', null, '0'), ('21', '3', '修改', null, 'sys:role:update,sys:menu:list', '2', null, '0'), ('22', '3', '删除', null, 'sys:role:delete', '2', null, '0'), ('23', '4', '查看', null, 'sys:menu:list,sys:menu:info', '2', null, '0'), ('24', '4', '新增', null, 'sys:menu:save,sys:menu:select', '2', null, '0'), ('25', '4', '修改', null, 'sys:menu:update,sys:menu:select', '2', null, '0'), ('26', '4', '删除', null, 'sys:menu:delete', '2', null, '0'), ('27', '1', '参数管理', 'sys/config', 'sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete', '1', 'config', '6'), ('29', '1', '系统日志', 'sys/log', 'sys:log:list', '1', 'log', '7'), ('30', '1', '文件上传', 'oss/oss', 'sys:oss:all', '1', 'oss', '6'), ('41', '0', '性能测试', '', '', '0', 'oss', '0'), ('42', '41', '性能测试用例', 'performance/performancecase', null, '1', 'config', '6'), ('43', '42', '查看', null, 'performance:performancecase:list,performance:performancecase:info', '2', null, '6'), ('44', '42', '新增', null, 'performance:performancecase:save', '2', null, '6'), ('45', '42', '修改', null, 'performance:performancecase:update', '2', null, '6'), ('46', '42', '删除', null, 'performance:performancecase:delete', '2', null, '6'), ('47', '41', '性能测试用例文件', 'performance/performancecasefile', null, '1', 'config', '6'), ('48', '47', '查看', null, 'performance:performancecasefile:list,performance:performancecasefile:info', '2', null, '6'), ('49', '47', '新增', null, 'performance:performancecasefile:save', '2', null, '6'), ('50', '47', '修改', null, 'performance:performancecasefile:update', '2', null, '6'), ('51', '47', '删除', null, 'performance:performancecasefile:delete', '2', null, '6'), ('52', '41', '性能测试分布式节点', 'performance/performanceslave', null, '1', 'config', '6'), ('53', '52', '查看', null, 'performance:performanceslave:list,performance:performanceslave:info', '2', null, '6'), ('54', '52', '新增', null, 'performance:performanceslave:save', '2', null, '6'), ('55', '52', '修改', null, 'performance:performanceslave:update', '2', null, '6'), ('56', '52', '删除', null, 'performance:performanceslave:delete', '2', null, '6'), ('57', '41', '性能测试报告', 'performance/performancecasereports', null, '1', 'config', '6'), ('58', '57', '查看', null, 'performance:performancecasereports:list,performance:performancecasereports:info', '2', null, '6'), ('59', '57', '新增', null, 'performance:performancecasereports:save', '2', null, '6'), ('60', '57', '修改', null, 'performance:performancecasereports:update', '2', null, '6'), ('61', '57', '删除', null, 'performance:performancecasereports:delete', '2', null, '6'), ('62', '41', '线程组管理', 'performance/performancethreadset', null, '1', 'config', '6'), ('63', '62', '查看', null, 'performance:performancethreadset:list,performance:performancethreadset:info', '2', null, '6'), ('64', '62', '新增', null, 'performance:performancethreadset:save', '2', null, '6'), ('65', '62', '修改', null, 'performance:performancethreadset:update', '2', null, '6'), ('66', '62', '删除', null, 'performance:performancethreadset:delete', '2', null, '6'), ('67', '41', '调试测试报告', 'performance/debugcasereports', null, '1', 'config', '6'), ('68', '67', '查看', null, 'performance:debugcasereports:list,performance:debugcasereports:info', '2', null, '6'), ('69', '67', '新增', null, 'performance:debugcasereports:save', '2', null, '6'), ('70', '67', '修改', null, 'performance:debugcasereports:update', '2', null, '6'), ('71', '67', '删除', null, 'performance:debugcasereports:delete', '2', null, '6'), ('72', '42', '上传文件', '', 'performance:performancecase:upload', '2', '', '0');
COMMIT;

-- ----------------------------
--  Table structure for `sys_oss`
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss`;
CREATE TABLE `sys_oss` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(200) DEFAULT NULL COMMENT 'URL地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件上传';

-- ----------------------------
--  Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色';

-- ----------------------------
--  Table structure for `sys_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色与菜单对应关系';

-- ----------------------------
--  Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `salt` varchar(20) DEFAULT NULL COMMENT '盐',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='系统用户';

-- ----------------------------
--  Records of `sys_user`
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES ('1', 'admin', '9ec9750e709431dad22365cabc5c625482e574c74adaebba7dd02f1129e4ce1d', 'YzcmCZNvbXocrsz9dm8e', 'root@renren.io', '13612345678', '1', '1', '2016-11-11 11:11:11');
COMMIT;

-- ----------------------------
--  Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户与角色对应关系';

-- ----------------------------
--  Table structure for `sys_user_token`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_token`;
CREATE TABLE `sys_user_token` (
  `user_id` bigint(20) NOT NULL,
  `token` varchar(100) NOT NULL COMMENT 'token',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `token` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户Token';

-- ----------------------------
--  Records of `sys_user_token`
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_token` VALUES ('1', '24f6d60add5ebfe4376af045f7f56b24', '2019-12-27 03:15:40', '2019-12-26 15:15:40');
COMMIT;

-- ----------------------------
--  Table structure for `tb_user`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `mobile` varchar(20) NOT NULL COMMENT '手机号',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='用户';

-- ----------------------------
--  Records of `tb_user`
-- ----------------------------
BEGIN;
INSERT INTO `tb_user` VALUES ('1', 'mark', '13612345678', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '2017-03-23 22:37:41');
COMMIT;

-- ----------------------------
--  Table structure for `test_debug_case_reports`
-- ----------------------------
DROP TABLE IF EXISTS `test_debug_case_reports`;
CREATE TABLE `test_debug_case_reports` (
  `report_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `case_id` bigint(20) NOT NULL COMMENT '所关联的用例',
  `file_id` bigint(20) NOT NULL COMMENT '所关联的用例文件',
  `origin_name` varchar(200) NOT NULL COMMENT '测试报告名称',
  `report_name` varchar(200) NOT NULL COMMENT '避免跨系统编码错误，随机化了结果文件名，存储了相对路径',
  `file_size` bigint(20) DEFAULT NULL COMMENT '测试结果文件大小',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态  0：初始状态  1：正在运行  2：成功执行  3：运行出现异常',
  `remark` varchar(300) DEFAULT NULL COMMENT '描述',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `add_by` bigint(20) DEFAULT NULL COMMENT '提交用户id',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '修改用户id',
  PRIMARY KEY (`report_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='调试/接口测试报告文件表';

-- ----------------------------
--  Table structure for `test_performance_case`
-- ----------------------------
DROP TABLE IF EXISTS `test_performance_case`;
CREATE TABLE `test_performance_case` (
  `case_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `case_name` varchar(50) NOT NULL COMMENT '用例名',
  `project` varchar(50) DEFAULT NULL COMMENT '所属项目',
  `module` varchar(50) DEFAULT NULL COMMENT '所属模块',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态  0：禁用   1：正常',
  `operator` varchar(20) DEFAULT NULL COMMENT '拥有者名字',
  `remark` varchar(300) DEFAULT NULL COMMENT '描述',
  `priority` int(11) DEFAULT NULL COMMENT '优先级用于过滤',
  `case_dir` varchar(200) DEFAULT NULL COMMENT 'master节点保存用例信息的文件夹',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `add_by` bigint(20) DEFAULT NULL COMMENT '提交用户id',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '修改用户id',
  PRIMARY KEY (`case_id`),
  UNIQUE KEY `case_name` (`case_name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='性能测试用例表';

-- ----------------------------
--  Records of `test_performance_case`
-- ----------------------------
BEGIN;
INSERT INTO `test_performance_case` VALUES ('1', '登录性能', '测试项目', '测试模块', '0', 'test', 'test', null, '', '2019-12-05 16:28:30', null, '2019-12-05 16:28:30', null), ('4', 'test', 'test', 'test', '0', 'test', 'test', null, '2019-12-16 15:28:26421', '2019-12-16 15:28:47', null, '2019-12-16 15:28:47', null), ('6', 'test3', 'test', 'test', '0', 'test', 'test', null, '2019-12-16 16:15:50226', '2019-12-16 16:16:11', null, '2019-12-16 16:16:11', null);
COMMIT;

-- ----------------------------
--  Table structure for `test_performance_case_file`
-- ----------------------------
DROP TABLE IF EXISTS `test_performance_case_file`;
CREATE TABLE `test_performance_case_file` (
  `file_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `case_id` bigint(20) NOT NULL COMMENT '所关联的用例',
  `slave_id` bigint(20) DEFAULT NULL COMMENT '所关联的同步过的slave子节点',
  `origin_name` varchar(200) NOT NULL COMMENT '上传的文件名带后缀',
  `file_name` varchar(200) DEFAULT NULL COMMENT '防止汉字编码错误实际保存的文件名带后缀',
  `file_md5` varchar(100) DEFAULT NULL COMMENT '文件的MD5对于参数化文件有效主要用于节点的参数化文件校验',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态  0：初始状态  1：正在运行  2：成功执行  3：运行出现异常   -1：不被搜索出来',
  `report_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态  0：保存测试报告原始文件  1：不需要测试报告',
  `webchart_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态  0：需要前端监控  1：不需要前端监控',
  `debug_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态  0：关闭debug  1：开始debug调试模式',
  `duration` int(11) NOT NULL DEFAULT '14400' COMMENT '期间，执行时间，单位秒，脚本执行多久停止，0代表永远执行',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `add_by` bigint(20) DEFAULT NULL COMMENT '提交用户id',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '修改用户id',
  PRIMARY KEY (`file_id`),
  UNIQUE KEY `case_origin_name` (`case_id`,`origin_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='性能测试用例文件表';

-- ----------------------------
--  Records of `test_performance_case_file`
-- ----------------------------
BEGIN;
INSERT INTO `test_performance_case_file` VALUES ('1', '6', null, '6F93BB7BE454FA54388A9BA60C74572B.jpg', '2019-12-16 16:15:50226/6F93BB7BE454FA54388A9BA60C74572B.jpg', '6f93bb7be454fa54388a9ba60c74572b', '0', '0', '0', '0', '14400', '2019-12-20 10:45:17', null, '2019-12-20 10:45:17', null), ('2', '6', null, '秒杀活动.jmx', '2019-12-16 16:15:50226/case2019-12-16 16:16:11441.jmx', '3cafd8bde266e42b5f49015f914d552f', '0', '0', '0', '0', '14400', '2019-12-26 15:27:27', null, '2019-12-26 15:27:49', null);
COMMIT;

-- ----------------------------
--  Table structure for `test_performance_case_reports`
-- ----------------------------
DROP TABLE IF EXISTS `test_performance_case_reports`;
CREATE TABLE `test_performance_case_reports` (
  `report_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `case_id` bigint(20) NOT NULL COMMENT '所关联的用例',
  `file_id` bigint(20) NOT NULL COMMENT '所关联的用例文件',
  `origin_name` varchar(200) NOT NULL COMMENT '测试报告名称',
  `report_name` varchar(200) NOT NULL COMMENT '避免跨系统编码错误，实际文件存储的报告名，名称和测试报告文件夹名称一致',
  `file_size` bigint(20) DEFAULT NULL COMMENT '测试结果文件大小',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态  0：初始状态  1：正在运行  2：成功执行  3：运行出现异常',
  `remark` varchar(300) DEFAULT NULL COMMENT '描述',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `add_by` bigint(20) DEFAULT NULL COMMENT '提交用户id',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '修改用户id',
  PRIMARY KEY (`report_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='性能测试报告文件表';

-- ----------------------------
--  Table structure for `test_performance_slave`
-- ----------------------------
DROP TABLE IF EXISTS `test_performance_slave`;
CREATE TABLE `test_performance_slave` (
  `slave_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `slave_name` varchar(50) NOT NULL COMMENT '子节点名称',
  `ip` varchar(50) NOT NULL COMMENT 'ip地址',
  `jmeter_port` int(11) NOT NULL DEFAULT '1099' COMMENT 'Jmeter链接端口号',
  `user_name` varchar(100) DEFAULT NULL COMMENT '节点机用户名',
  `passwd` varchar(100) DEFAULT NULL COMMENT '节点机密码',
  `ssh_port` int(11) NOT NULL DEFAULT '22' COMMENT 'ssh链接端口号',
  `home_dir` varchar(200) DEFAULT NULL COMMENT '子节点的Jmeter路径',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态  0：禁用   1：正常',
  `weight` int(11) NOT NULL DEFAULT '100' COMMENT 'slave节点机的权重，取值在1-99999，slave的权重可以大于或者小于master',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `add_by` bigint(20) DEFAULT NULL COMMENT '提交用户id',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '修改用户id',
  PRIMARY KEY (`slave_id`),
  UNIQUE KEY `ip` (`ip`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='性能测试分布式节点表';

-- ----------------------------
--  Records of `test_performance_slave`
-- ----------------------------
BEGIN;
INSERT INTO `test_performance_slave` VALUES ('1', 'LocalHost', '127.0.0.1', '1099', null, null, '22', '', '0', '100', '2018-06-18 18:18:18', null, '2018-06-18 18:18:18', null);
COMMIT;

-- ----------------------------
--  Table structure for `test_performance_thread_set`
-- ----------------------------
DROP TABLE IF EXISTS `test_performance_thread_set`;
CREATE TABLE `test_performance_thread_set` (
  `set_id` varchar(40) NOT NULL,
  `parent_id` varchar(40) DEFAULT NULL COMMENT '所属ID，一级配置为0',
  `name` varchar(100) DEFAULT NULL COMMENT '配置名称',
  `key` varchar(100) DEFAULT NULL COMMENT '配置项',
  `value` varchar(100) DEFAULT NULL COMMENT '配置内容',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：脚本   1：线程组   2：配置',
  `explain` varchar(200) DEFAULT NULL COMMENT '配置说明',
  `order_num` int(11) DEFAULT '0' COMMENT '排序',
  `file_id` bigint(20) DEFAULT NULL COMMENT '所属脚本文件ID',
  PRIMARY KEY (`set_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='线程组管理';

SET FOREIGN_KEY_CHECKS = 1;
