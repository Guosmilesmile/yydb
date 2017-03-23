/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50151
Source Host           : localhost:3306
Source Database       : yydb

Target Server Type    : MYSQL
Target Server Version : 50151
File Encoding         : 65001

Date: 2017-03-23 15:37:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cmspermission
-- ----------------------------
DROP TABLE IF EXISTS `cmspermission`;
CREATE TABLE `cmspermission` (
  `pmsid` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `name` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `permission` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `parentid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`pmsid`),
  KEY `FK_dl60a0nqnys11h5b1xahciren` (`parentid`),
  CONSTRAINT `cmspermission_ibfk_1` FOREIGN KEY (`parentid`) REFERENCES `cmspermission` (`pmsid`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of cmspermission
-- ----------------------------
INSERT INTO `cmspermission` VALUES ('1', '管理后台系统的全部信息', '系统管理', 'system:manage', null);
INSERT INTO `cmspermission` VALUES ('2', '系统管理员信息权限管理', '用户管理', 'system:user', '1');
INSERT INTO `cmspermission` VALUES ('3', '系统角色管理', '角色管理', 'system:role', '1');
INSERT INTO `cmspermission` VALUES ('4', '管理用户与角色绑定', '用户角色管理', 'system:userrole', '1');
INSERT INTO `cmspermission` VALUES ('5', '角色与权限的绑定', '角色权限管理', 'system:rolepermission', '1');
INSERT INTO `cmspermission` VALUES ('6', '客户管理', '客户管理', 'customer:total', null);
INSERT INTO `cmspermission` VALUES ('7', '客户管理', '客户管理', 'customer:manage', '6');
INSERT INTO `cmspermission` VALUES ('8', '管理商品', '商品管理', 'goods:total', null);
INSERT INTO `cmspermission` VALUES ('9', '管理商品分类', '分类管理', 'goods:category', '8');
INSERT INTO `cmspermission` VALUES ('10', '管理商品的详细信息', '商品管理', 'goods:manage', '8');
INSERT INTO `cmspermission` VALUES ('11', '管理夺宝', '夺宝管理', 'db:total', null);
INSERT INTO `cmspermission` VALUES ('12', '夺宝计划详细信息管理', '夺宝计划管理', 'db:dbplan', '11');
INSERT INTO `cmspermission` VALUES ('13', '夺宝参与计划情况管理', '夺宝参与情况管理', 'db:dbattend', '11');
INSERT INTO `cmspermission` VALUES ('14', '夺宝中奖情况管理', '夺宝情况管理', 'db:dbsituation', '11');

-- ----------------------------
-- Table structure for cmsrole
-- ----------------------------
DROP TABLE IF EXISTS `cmsrole`;
CREATE TABLE `cmsrole` (
  `roleid` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `name` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`roleid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of cmsrole
-- ----------------------------
INSERT INTO `cmsrole` VALUES ('1', '拥有最高权限的超级管理员', '超级管理员');
INSERT INTO `cmsrole` VALUES ('2', '权限为空', '游客');

-- ----------------------------
-- Table structure for cmsrolepms
-- ----------------------------
DROP TABLE IF EXISTS `cmsrolepms`;
CREATE TABLE `cmsrolepms` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleid` bigint(20) NOT NULL,
  `pmsid` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_iiaf860xrkux4d333n06gqdap` (`pmsid`),
  KEY `FK_6sphp8ycu59cwo68b71u2jcye` (`roleid`),
  CONSTRAINT `cmsrolepms_ibfk_1` FOREIGN KEY (`roleid`) REFERENCES `cmsrole` (`roleid`),
  CONSTRAINT `cmsrolepms_ibfk_2` FOREIGN KEY (`pmsid`) REFERENCES `cmspermission` (`pmsid`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of cmsrolepms
-- ----------------------------
INSERT INTO `cmsrolepms` VALUES ('1', '1', '2');
INSERT INTO `cmsrolepms` VALUES ('19', '1', '3');
INSERT INTO `cmsrolepms` VALUES ('20', '1', '4');
INSERT INTO `cmsrolepms` VALUES ('21', '1', '5');
INSERT INTO `cmsrolepms` VALUES ('22', '1', '6');
INSERT INTO `cmsrolepms` VALUES ('23', '1', '7');
INSERT INTO `cmsrolepms` VALUES ('24', '1', '9');
INSERT INTO `cmsrolepms` VALUES ('25', '1', '10');
INSERT INTO `cmsrolepms` VALUES ('27', '1', '12');
INSERT INTO `cmsrolepms` VALUES ('28', '1', '13');
INSERT INTO `cmsrolepms` VALUES ('29', '1', '14');

-- ----------------------------
-- Table structure for cmsuser
-- ----------------------------
DROP TABLE IF EXISTS `cmsuser`;
CREATE TABLE `cmsuser` (
  `userid` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `nickname` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `password` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`userid`),
  UNIQUE KEY `UK_726c0ppn7oftcawwsq6n4u6gn` (`account`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of cmsuser
-- ----------------------------
INSERT INTO `cmsuser` VALUES ('1', 'admin', '管理员', '$2a$10$RtCDFfOKE82JwsO4XOkSk.dyG9098Q.91PLsO8OiqlVtEGeUoo0mC');
INSERT INTO `cmsuser` VALUES ('2', 'admin1', '123', '$2a$10$RtCDFfOKE82JwsO4XOkSk.dyG9098Q.91PLsO8OiqlVtEGeUoo0mC');
INSERT INTO `cmsuser` VALUES ('5', 'user1', 'user12', '$2a$10$ug4/Xg5V6hZU8qOpJks/8ec73.k4IVmm6IKyE8kW0RoMCGlSoDSdC');
INSERT INTO `cmsuser` VALUES ('6', 'user2', 'user2', '$2a$10$H5jH0FQVCmMusAinn/Ybr.D.CneHePD50lgiYmAORsuoETXh6MajK');

-- ----------------------------
-- Table structure for cmsuserrole
-- ----------------------------
DROP TABLE IF EXISTS `cmsuserrole`;
CREATE TABLE `cmsuserrole` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userid` bigint(20) NOT NULL,
  `roleid` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_7txs9153uvcm24p3n34pa1c8p` (`roleid`),
  KEY `FK_h8hq3ed5gko0yq3jforlirm9i` (`userid`),
  CONSTRAINT `cmsuserrole_ibfk_1` FOREIGN KEY (`roleid`) REFERENCES `cmsrole` (`roleid`),
  CONSTRAINT `cmsuserrole_ibfk_2` FOREIGN KEY (`userid`) REFERENCES `cmsuser` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of cmsuserrole
-- ----------------------------
INSERT INTO `cmsuserrole` VALUES ('1', '1', '1');
INSERT INTO `cmsuserrole` VALUES ('2', '2', '2');
INSERT INTO `cmsuserrole` VALUES ('3', '1', '2');

-- ----------------------------
-- Table structure for c_category
-- ----------------------------
DROP TABLE IF EXISTS `c_category`;
CREATE TABLE `c_category` (
  `categoryid` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT '',
  PRIMARY KEY (`categoryid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of c_category
-- ----------------------------
INSERT INTO `c_category` VALUES ('1', '生活');
INSERT INTO `c_category` VALUES ('2', '食品');
INSERT INTO `c_category` VALUES ('3', '家电');
INSERT INTO `c_category` VALUES ('4', '电子');
INSERT INTO `c_category` VALUES ('5', '住房');

-- ----------------------------
-- Table structure for c_customer
-- ----------------------------
DROP TABLE IF EXISTS `c_customer`;
CREATE TABLE `c_customer` (
  `customerid` bigint(20) NOT NULL AUTO_INCREMENT,
  `wechatid` varchar(50) DEFAULT '',
  `balance` double(20,2) DEFAULT NULL,
  `address` varchar(255) DEFAULT '',
  `isshop` int(11) DEFAULT NULL,
  `phone` bigint(20) DEFAULT NULL,
  `name` varchar(200) DEFAULT '',
  PRIMARY KEY (`customerid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of c_customer
-- ----------------------------
INSERT INTO `c_customer` VALUES ('1', 'chris', '2.60', '', '0', '0', '');
INSERT INTO `c_customer` VALUES ('2', 'ssddddff', '0.00', '厦门市', '1', '12365488978', '戒不掉傻笑');
INSERT INTO `c_customer` VALUES ('3', 'fsfsf', '0.00', '', '0', '0', '222');
INSERT INTO `c_customer` VALUES ('4', 'dfgd', '0.00', '水电费发', '1', '424', '333');
INSERT INTO `c_customer` VALUES ('5', 'khjhk', '0.00', '胜多负少', '1', '0', '444');
INSERT INTO `c_customer` VALUES ('6', 'sdfs', '0.00', '是范德萨', '0', '23424', '555');

-- ----------------------------
-- Table structure for db_dbattend
-- ----------------------------
DROP TABLE IF EXISTS `db_dbattend`;
CREATE TABLE `db_dbattend` (
  `attendid` bigint(20) NOT NULL AUTO_INCREMENT,
  `dbplanid` bigint(20) DEFAULT NULL,
  `customerid` bigint(20) DEFAULT NULL,
  `createtime` varchar(20) DEFAULT NULL,
  `isplay` int(2) DEFAULT NULL,
  PRIMARY KEY (`attendid`),
  KEY `FK_lmlk434u8sad3se1j06033c2u` (`customerid`),
  KEY `FK_jaikym0vv8t3f1c0wn8pfa1rl` (`dbplanid`),
  CONSTRAINT `FK_jaikym0vv8t3f1c0wn8pfa1rl` FOREIGN KEY (`dbplanid`) REFERENCES `db_dbplan` (`dbplanid`),
  CONSTRAINT `FK_lmlk434u8sad3se1j06033c2u` FOREIGN KEY (`customerid`) REFERENCES `c_customer` (`customerid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of db_dbattend
-- ----------------------------
INSERT INTO `db_dbattend` VALUES ('1', '3', '1', '1489319672', '1');
INSERT INTO `db_dbattend` VALUES ('2', '1', '4', '1489456948', '1');
INSERT INTO `db_dbattend` VALUES ('3', '3', '6', '1489459179', '1');
INSERT INTO `db_dbattend` VALUES ('4', '3', '2', '1489459179', '1');

-- ----------------------------
-- Table structure for db_dbplan
-- ----------------------------
DROP TABLE IF EXISTS `db_dbplan`;
CREATE TABLE `db_dbplan` (
  `dbplanid` bigint(20) NOT NULL AUTO_INCREMENT,
  `goodsid` bigint(20) DEFAULT NULL,
  `block` int(1) DEFAULT NULL COMMENT '分区（1：一元区，2：十元区，3：百元区，4：千元区）',
  `split` bigint(20) DEFAULT NULL,
  `starttime` varchar(20) DEFAULT NULL,
  `endtime` varchar(20) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `money` double DEFAULT NULL,
  `isfinish` int(1) DEFAULT NULL,
  PRIMARY KEY (`dbplanid`),
  KEY `FK_2iyr965ah1f6h0nvulr9k6gvg` (`goodsid`),
  CONSTRAINT `FK_2iyr965ah1f6h0nvulr9k6gvg` FOREIGN KEY (`goodsid`) REFERENCES `g_goods` (`goodsid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of db_dbplan
-- ----------------------------
INSERT INTO `db_dbplan` VALUES ('1', '26', '2', '50', '1489319672', '1489748075', '4', '200', '0');
INSERT INTO `db_dbplan` VALUES ('3', '25', '2', '50', '1489319672', '1489748076', '4', '200', '0');

-- ----------------------------
-- Table structure for db_situation
-- ----------------------------
DROP TABLE IF EXISTS `db_situation`;
CREATE TABLE `db_situation` (
  `situationid` bigint(20) NOT NULL AUTO_INCREMENT,
  `dbplanid` bigint(20) DEFAULT NULL,
  `customerid` bigint(20) DEFAULT NULL,
  `istake` int(2) DEFAULT NULL COMMENT '是否领取 ',
  PRIMARY KEY (`situationid`),
  KEY `FK_ewx04f6je9lvyjm7ubtetbevu` (`customerid`),
  KEY `FK_iyocdaxpw5t7teq3lduvkli7c` (`dbplanid`),
  CONSTRAINT `FK_ewx04f6je9lvyjm7ubtetbevu` FOREIGN KEY (`customerid`) REFERENCES `c_customer` (`customerid`),
  CONSTRAINT `FK_iyocdaxpw5t7teq3lduvkli7c` FOREIGN KEY (`dbplanid`) REFERENCES `db_dbplan` (`dbplanid`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of db_situation
-- ----------------------------
INSERT INTO `db_situation` VALUES ('9', '1', null, null);
INSERT INTO `db_situation` VALUES ('10', '3', '2', '0');

-- ----------------------------
-- Table structure for g_goods
-- ----------------------------
DROP TABLE IF EXISTS `g_goods`;
CREATE TABLE `g_goods` (
  `goodsid` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT '',
  `imgurls` text,
  `categoryid` bigint(20) DEFAULT NULL,
  `shopid` bigint(20) DEFAULT NULL,
  `summary` text,
  PRIMARY KEY (`goodsid`),
  KEY `FK_ihyitlqw009wf831t0h32e1e8` (`categoryid`),
  KEY `FK_fase2nlaf82nwn85iixkgfsfh` (`shopid`),
  CONSTRAINT `FK_fase2nlaf82nwn85iixkgfsfh` FOREIGN KEY (`shopid`) REFERENCES `c_customer` (`customerid`),
  CONSTRAINT `FK_ihyitlqw009wf831t0h32e1e8` FOREIGN KEY (`categoryid`) REFERENCES `c_category` (`categoryid`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of g_goods
-- ----------------------------
INSERT INTO `g_goods` VALUES ('25', '杂粮煎饼', 'upload/1489027333012.jpg;upload/1489027414137.gif;', '1', '4', '是非得');
INSERT INTO `g_goods` VALUES ('26', '是否', 'upload/1489027432083.jpg;upload/1489027432105.gif;upload/1489027436960.jpg;upload/1489027530085.JPG;', '2', '5', '沙发');

-- ----------------------------
-- Procedure structure for p_insertgoods
-- ----------------------------
DROP PROCEDURE IF EXISTS `p_insertgoods`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `p_insertgoods`(in goodsname varchar(50),in categoryid bigint,in shopid bigint,in summary text,out returnid bigint)
BEGIN
insert into g_goods (name,categoryid,shopid,summary,imgurls) values (goodsname,categoryid,shopid,summary,"");
SELECT @@Identity into returnid;
select returnid;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for p_insertsituation
-- ----------------------------
DROP PROCEDURE IF EXISTS `p_insertsituation`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `p_insertsituation`(IN `dbplanid` bigint,IN `customerid` bigint,IN `istake` int)
BEGIN
DECLARE result int DEFAULT 0 ;
DECLARE nownumber int DEFAULT 0 ;
DECLARE totalnumber int DEFAULT 0 ;
select db_dbplan.number into totalnumber from db_dbplan where db_dbplan.dbplanid = dbplanid;
select count(db_situation.situationid) into nownumber from db_situation where db_situation.dbplanid = dbplanid;
IF totalnumber > nownumber THEN
	insert into db_situation (dbplanid,customerid,istake) VALUES (dbplanid,customerid,istake);
	set result = 1;
end if;
select result;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for s_selectluckydog
-- ----------------------------
DROP PROCEDURE IF EXISTS `s_selectluckydog`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `s_selectluckydog`()
BEGIN
DECLARE t_error INTEGER DEFAULT 0;  
DECLARE Done INT DEFAULT 0;
declare nowtime bigint DEFAULT UNIX_TIMESTAMP(sysdate());
declare singleDbplanid BIGINT default 0;
DECLARE DbPlanNumber int DEFAULT 0;#计划人数
declare outCount int DEFAULT 0;
DECLARE current int DEFAULT 0;
DECLARE DbAttendNumber int DEFAULT 0;#实际参与人数
DECLARE luckycustomerid BIGINT DEFAULT null;
DECLARE rs CURSOR FOR select db_dbplan.dbplanid,db_dbplan.number from db_dbplan where db_dbplan.endtime<nowtime and db_dbplan.isfinish = 0;
DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET t_error=1;
select count(dbplanid) into outCount from db_dbplan where db_dbplan.endtime<nowtime and db_dbplan.isfinish = 0;
if outCount=0 THEN
	set DONE = 1;
end if;

open rs;
#从dbplan中获取已经到时间的计划，选出未被选出结果的计划
FETCH NEXT FROM rs INTO singleDbplanid,DbPlanNumber;
repeat
IF NOT Done THEN
	#select singleDbNumber;
	set current = current +1 ;
	if outCount=current THEN
		set done = 1;
	end if;
	#检查id对应的参与情况，判断是否流标
	SELECT count(db_dbattend.attendid) INTO DbAttendNumber from db_dbattend where db_dbattend.dbplanid = singleDbplanid and db_dbattend.isplay = 1;
	START TRANSACTION;  
		if DbAttendNumber < DbPlanNumber then#如果参与人数不足计划人数，流标
		#select DbAttendNumber;
		insert into db_situation (dbplanid,customerid,istake) values (singleDbplanid,null,null);
		update db_dbplan set db_dbplan.isfinish = 1 where db_dbplan.dbplanid = singleDbplanid;
	ELSEIF DbAttendNumber >= DbPlanNumber then#如果大于或者超过选出幸运儿
		#SELECT DbPlanNumber;
		select customerid into luckycustomerid from db_dbattend WHERE db_dbattend.dbplanid = singleDbplanid order by rand() limit 1;
		insert into db_situation (dbplanid,customerid,istake) values (singleDbplanid,luckycustomerid,0);
		update db_dbplan set db_dbplan.isfinish = 1 where db_dbplan.dbplanid = singleDbplanid;
	end if;
	IF t_error = 1 THEN  
		ROLLBACK;	
	ELSE
		COMMIT;
	END IF;
	set t_error = 0;
end if;
FETCH NEXT FROM rs INTO singleDbplanid,DbPlanNumber;
UNTIL Done END REPEAT;
CLOSE rs;

END
;;
DELIMITER ;

-- ----------------------------
-- Event structure for even_selectluckydog
-- ----------------------------
DROP EVENT IF EXISTS `even_selectluckydog`;
DELIMITER ;;
CREATE EVENT `even_selectluckydog` ON SCHEDULE EVERY 2 SECOND STARTS '2017-03-19 10:22:20' ON COMPLETION NOT PRESERVE ENABLE DO call s_selectluckydog()
;;
DELIMITER ;
