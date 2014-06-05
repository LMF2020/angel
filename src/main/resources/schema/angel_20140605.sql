/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.6.16-log : Database - angel
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`angel` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `angel`;

/*Table structure for table `t_achieve` */

DROP TABLE IF EXISTS `t_achieve`;

CREATE TABLE `t_achieve` (
  `achieve_date` datetime DEFAULT NULL COMMENT '计算日期',
  `PPV` double DEFAULT NULL COMMENT '个人业绩',
  `DBV` double DEFAULT NULL COMMENT '直接业绩',
  `IBV` double DEFAULT NULL COMMENT '间接业绩',
  `ATNPV` double DEFAULT NULL COMMENT '累计整网业绩',
  `TNPV` double DEFAULT NULL COMMENT '整网业绩',
  `GPV` double DEFAULT NULL COMMENT '小组业绩',
  `APPV` double DEFAULT NULL COMMENT '累计个人业绩',
  `purchaser_code` varchar(8) NOT NULL COMMENT '会员编码(主键)',
  `tier` int(11) NOT NULL COMMENT '层级',
  `rank_name` varchar(255) DEFAULT NULL COMMENT '等级名称',
  `rank_code` varchar(11) DEFAULT NULL COMMENT '等级编码',
  `upper_codes` longtext COMMENT '保存所有上线编码',
  PRIMARY KEY (`purchaser_code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `t_achieve` */

/*Table structure for table `t_achieve_his` */

DROP TABLE IF EXISTS `t_achieve_his`;

CREATE TABLE `t_achieve_his` (
  `achieve_date` datetime DEFAULT NULL COMMENT '计算日期',
  `PPV` double DEFAULT NULL COMMENT '个人业绩',
  `DBV` double DEFAULT NULL COMMENT '直接业绩',
  `IBV` double DEFAULT NULL COMMENT '间接业绩',
  `ATNPV` double DEFAULT NULL COMMENT '累计整网业绩',
  `TNPV` double DEFAULT NULL COMMENT '整网业绩',
  `GPV` double DEFAULT NULL COMMENT '小组业绩',
  `APPV` double DEFAULT NULL COMMENT '累计个人业绩',
  `purchaser_code` varchar(8) DEFAULT NULL COMMENT '会员编码',
  `tier` int(11) NOT NULL COMMENT '层级',
  `rank_name` varchar(255) DEFAULT NULL COMMENT '等级名称',
  `rank_code` varchar(11) DEFAULT NULL COMMENT '等级编码',
  `upper_codes` longtext COMMENT '保存所有上线编码',
  `id` int(255) NOT NULL COMMENT '主键',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `t_achieve_his` */

/*Table structure for table `t_bouns` */

DROP TABLE IF EXISTS `t_bouns`;

CREATE TABLE `t_bouns` (
  `bouns_date` datetime DEFAULT NULL COMMENT '计算日期',
  `direct_bouns` double DEFAULT NULL COMMENT '直接奖',
  `indirect_bouns` double DEFAULT NULL COMMENT '间接奖',
  `leader_bouns` double DEFAULT NULL COMMENT '领导奖',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `rank_name` varchar(255) DEFAULT NULL COMMENT '等级名称',
  `rank_code` varchar(11) DEFAULT NULL COMMENT '等级编码',
  `purchaser_code` varchar(8) NOT NULL COMMENT '会员编码(主键)',
  `purchaser_name` varchar(255) DEFAULT NULL COMMENT '会员姓名',
  PRIMARY KEY (`purchaser_code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `t_bouns` */

/*Table structure for table `t_bouns_his` */

DROP TABLE IF EXISTS `t_bouns_his`;

CREATE TABLE `t_bouns_his` (
  `bouns_date` datetime DEFAULT NULL COMMENT '计算日期',
  `direct_bouns` double DEFAULT NULL COMMENT '直接奖',
  `indirect_bouns` double DEFAULT NULL COMMENT '间接奖',
  `leader_bouns` double DEFAULT NULL COMMENT '领导奖',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `rank_name` varchar(255) DEFAULT NULL COMMENT '等级名称',
  `rank_code` varchar(11) DEFAULT NULL COMMENT '等级编码',
  `purchaser_code` varchar(8) DEFAULT NULL COMMENT '会员编码',
  `purchaser_name` varchar(255) DEFAULT NULL COMMENT '会员姓名',
  `id` int(255) NOT NULL COMMENT '主键',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `t_bouns_his` */

/*Table structure for table `t_order` */

DROP TABLE IF EXISTS `t_order`;

CREATE TABLE `t_order` (
  `BV` double DEFAULT NULL COMMENT 'BV值',
  `PV` double DEFAULT NULL COMMENT 'PV值',
  `book` int(11) DEFAULT NULL COMMENT 'BOOK',
  `purchaser_code` varchar(255) DEFAULT NULL COMMENT '经销商编码',
  `purchaser_name` varchar(255) DEFAULT NULL COMMENT '经销商姓名',
  `floors` int(11) DEFAULT NULL COMMENT '层级',
  `order_code` varchar(255) NOT NULL COMMENT '订单流水号(主键)',
  `product_code` varchar(255) DEFAULT NULL COMMENT '产品编码',
  `product_name` varchar(255) DEFAULT NULL COMMENT '产品名称',
  `product_price` double DEFAULT NULL COMMENT '产品价格',
  `sale_number` int(11) DEFAULT NULL COMMENT '购买数量',
  `sale_time` datetime DEFAULT NULL COMMENT '购买时间',
  `shop_code` varchar(255) DEFAULT NULL COMMENT '店铺编码',
  `shop_name` varchar(255) DEFAULT NULL COMMENT '店铺名称',
  `sum_price` double DEFAULT NULL COMMENT '累计价格',
  PRIMARY KEY (`order_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_order` */

insert  into `t_order`(`BV`,`PV`,`book`,`purchaser_code`,`purchaser_name`,`floors`,`order_code`,`product_code`,`product_name`,`product_price`,`sale_number`,`sale_time`,`shop_code`,`shop_name`,`sum_price`) values (NULL,NULL,1,'000006',NULL,NULL,'201406030039450736','A18',NULL,NULL,2,'2014-06-03 00:39:58',NULL,NULL,18),(NULL,NULL,NULL,'000005',NULL,NULL,'201406030039479618','A02',NULL,NULL,2,'2014-06-03 00:39:39',NULL,NULL,42),(NULL,NULL,1,'000001',NULL,NULL,'201406030039702145','E18',NULL,NULL,3,'2014-06-03 00:39:12',NULL,NULL,15),(NULL,NULL,1,'000002',NULL,NULL,'201406030039839025','A01',NULL,NULL,2,'2014-06-03 00:39:24',NULL,NULL,34),(NULL,NULL,2,'000006',NULL,NULL,'20140603004049721','A05',NULL,NULL,3,'2014-06-03 00:41:45',NULL,NULL,30),(NULL,NULL,5,'000002',NULL,NULL,'201406030040563249','A02',NULL,NULL,1,'2014-06-03 00:40:49',NULL,NULL,21),(NULL,NULL,1,'000007',NULL,NULL,'201406030041431095','A02',NULL,NULL,4,'2014-06-03 00:41:11',NULL,NULL,84);

/*Table structure for table `t_product` */

DROP TABLE IF EXISTS `t_product`;

CREATE TABLE `t_product` (
  `product_code` varchar(15) NOT NULL COMMENT '产品编码(主键)',
  `product_name` varchar(64) NOT NULL COMMENT '产品名称',
  `product_price` double NOT NULL COMMENT '产品价格',
  `product_pv` double NOT NULL COMMENT 'PV值',
  `product_bv` double NOT NULL COMMENT 'BV值',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` varchar(1) DEFAULT NULL COMMENT '产品状态(上架/下架)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`product_code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `t_product` */

insert  into `t_product`(`product_code`,`product_name`,`product_price`,`product_pv`,`product_bv`,`remark`,`status`,`create_time`) values ('A05','创可贴',10,10,10,'','1',NULL),('A02','洗发水',21,21,21,'','1','2014-05-28 17:21:07'),('A01','矿泉水',17,17,17,'','1','2014-05-28 17:21:10'),('A18','玻璃杯',9,9,4.5,'','1','2014-05-28 17:21:16'),('E18','啤酒杯',5,2.52,2.52,'','1','2014-05-28 17:21:26');

/*Table structure for table `t_purchaser` */

DROP TABLE IF EXISTS `t_purchaser`;

CREATE TABLE `t_purchaser` (
  `shop_code` varchar(11) DEFAULT NULL COMMENT '所属店铺编码',
  `rank_code` varchar(11) DEFAULT NULL COMMENT '等级编码',
  `purchaser_code` varchar(8) NOT NULL COMMENT '经销商编码(主键)',
  `purchaser_name` varchar(255) DEFAULT NULL COMMENT '经销商名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `address` varchar(255) DEFAULT NULL COMMENT '联系地址',
  `phone` varchar(40) DEFAULT NULL COMMENT '联系号码',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `sponsor_code` varchar(8) DEFAULT NULL COMMENT '上级经销商编码',
  `floors` int(11) DEFAULT '0' COMMENT '层级 最上面是第一层 以此类推',
  `bankAcc` int(255) DEFAULT '0' COMMENT '银行账户',
  `rank_name` varchar(11) DEFAULT NULL COMMENT '等级名称',
  `shop_name` varchar(11) DEFAULT NULL COMMENT '所属店铺名称',
  `sponsor_name` varchar(255) DEFAULT NULL COMMENT '上级经销商名称',
  `purchaser_pass` varchar(255) DEFAULT '0000' COMMENT '经销商登陆密码',
  `purchaser_gender` varchar(255) DEFAULT NULL COMMENT '经销商性别',
  `purchaser_age` int(3) DEFAULT '0' COMMENT '经销商年龄',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `upper_codes` longtext COMMENT '所有上线编号集合',
  PRIMARY KEY (`purchaser_code`),
  KEY `FK_Reference_2` (`shop_code`),
  KEY `FK_Reference_4` (`rank_code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `t_purchaser` */

insert  into `t_purchaser`(`shop_code`,`rank_code`,`purchaser_code`,`purchaser_name`,`create_time`,`address`,`phone`,`remark`,`sponsor_code`,`floors`,`bankAcc`,`rank_name`,`shop_name`,`sponsor_name`,`purchaser_pass`,`purchaser_gender`,`purchaser_age`,`update_time`,`upper_codes`) values ('CG982002','102009','000000','系统管理员','2012-02-06 17:59:11','1','1',NULL,'*',0,0,'九星','*','*','0000','男',26,NULL,'0'),('CG982001','102001','000012','B2','2014-06-03 00:31:14',NULL,NULL,NULL,'000002',2,NULL,NULL,NULL,NULL,'',NULL,NULL,'2014-06-03 00:31:14','0,000000,000002'),('CG982001','102001','000011','B1','2014-06-03 00:30:43',NULL,NULL,NULL,'000002',2,NULL,NULL,NULL,NULL,'',NULL,NULL,'2014-06-03 00:30:43','0,000000,000002'),('CG982002','102001','000010','A23','2014-06-03 00:29:55',NULL,NULL,NULL,'000004',3,NULL,NULL,NULL,NULL,'',NULL,NULL,'2014-06-03 00:29:55','0,000000,000001,000004'),('CG982002','102001','000009','A22','2014-06-03 00:29:37',NULL,NULL,NULL,'000004',3,NULL,NULL,NULL,NULL,'',NULL,NULL,'2014-06-03 00:29:37','0,000000,000001,000004'),('CG982002','102001','000008','A21','2014-06-03 00:29:21',NULL,NULL,NULL,'000004',3,NULL,NULL,NULL,NULL,'',NULL,NULL,'2014-06-03 00:29:21','0,000000,000001,000004'),('CG982002','102001','000007','A13','2014-06-03 00:28:41',NULL,NULL,NULL,'000003',3,NULL,NULL,NULL,NULL,'',NULL,NULL,'2014-06-03 00:28:41','0,000000,000001,000003'),('CG982002','102001','000006','A12','2014-06-03 00:28:23',NULL,NULL,NULL,'000003',3,NULL,NULL,NULL,NULL,'',NULL,NULL,'2014-06-03 00:28:23','0,000000,000001,000003'),('CG982002','102001','000005','A11','2014-06-03 00:27:52',NULL,NULL,NULL,'000003',3,NULL,NULL,NULL,NULL,'',NULL,NULL,'2014-06-03 00:27:52','0,000000,000001,000003'),('CG982002','102001','000004','A2','2014-06-03 00:22:49',NULL,NULL,NULL,'000001',2,NULL,NULL,NULL,NULL,'',NULL,NULL,'2014-06-03 00:22:49','0,000000,000001'),('CG982002','102001','000003','A1','2014-06-03 00:22:02',NULL,NULL,NULL,'000001',2,NULL,NULL,NULL,NULL,'',NULL,NULL,'2014-06-03 00:22:13','0,000000,000001'),('CG982002','102001','000002','B','2014-06-03 00:21:36',NULL,NULL,NULL,'000000',1,NULL,NULL,NULL,NULL,'',NULL,NULL,'2014-06-03 00:31:29','0,000000'),('CG982002','102001','000001','A','2014-06-03 00:21:10',NULL,NULL,NULL,'000000',1,NULL,NULL,NULL,NULL,'',NULL,NULL,'2014-06-03 00:21:10','0,000000');

/*Table structure for table `t_rank` */

DROP TABLE IF EXISTS `t_rank`;

CREATE TABLE `t_rank` (
  `rank_code` varchar(11) NOT NULL COMMENT '等级编码',
  `rank_name` varchar(64) DEFAULT NULL COMMENT '等级名称',
  `direct_bouns_rate` double DEFAULT NULL COMMENT '直接奖比例',
  `indirect_bouns_rate` double DEFAULT NULL COMMENT '间接奖比例',
  `leader_bouns_rate` double DEFAULT NULL COMMENT '领导奖比例',
  PRIMARY KEY (`rank_code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `t_rank` */

insert  into `t_rank`(`rank_code`,`rank_name`,`direct_bouns_rate`,`indirect_bouns_rate`,`leader_bouns_rate`) values ('102001','一星',5,NULL,NULL),('102002','二星',15,NULL,NULL),('102003','三星',22,NULL,NULL),('102004','四星',26,NULL,NULL),('102005','五星',30,NULL,NULL),('102006','六星',32,NULL,NULL),('102007','七星',37,NULL,NULL),('102008','八星',43,NULL,NULL),('102009','九星',45,NULL,NULL);

/*Table structure for table `t_shop` */

DROP TABLE IF EXISTS `t_shop`;

CREATE TABLE `t_shop` (
  `shop_code` varchar(8) NOT NULL COMMENT '店铺编码(主键)',
  `shop_name` varchar(64) NOT NULL COMMENT '店铺名称',
  `shop_owner` varchar(8) NOT NULL COMMENT '店主',
  `shop_country` varchar(64) DEFAULT NULL COMMENT '所在国家',
  `shop_city` varchar(64) DEFAULT NULL COMMENT '所在城市',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `shop_street` varchar(255) DEFAULT NULL COMMENT '店铺街道',
  PRIMARY KEY (`shop_code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `t_shop` */

insert  into `t_shop`(`shop_code`,`shop_name`,`shop_owner`,`shop_country`,`shop_city`,`create_time`,`shop_street`) values ('CG982002','Koufoutila','00000002','cg','cg','2012-02-03 23:41:52','cg'),('CG982001','Milandou','00000001','cg','cg','2012-02-03 23:34:27','cg'),('CG982201','Mm','00000003','cg','cg','2012-02-03 23:43:30','cg'),('CG982301','Gladiss','00010000','cg','cg','2012-02-03 23:43:57','cg'),('CG982000','Company','000000','cg','cg','2012-02-03 23:59:48','cg');

/* Procedure structure for procedure `proc_network` */

/*!50003 DROP PROCEDURE IF EXISTS  `proc_network` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_network`(
	p_distributorCode CHAR(8),
	p_date VARCHAR(100)
    )
BEGIN
	DECLARE v_id INT(8);
	DECLARE v_floor INT(11);
	SELECT u.res_id ,u.floors INTO v_id ,v_floor FROM t_distributor u WHERE u.distributor_code=p_distributorCode;
	SELECT t.floors AS FLOOR ,t.sponsor_code AS SPONSOR_CODE ,s.shop_code AS SHOP_CODE ,
	t.distributor_name AS NAME,t.sponsor_name AS SPONSOR_NAME,t.distributor_code AS CODE,r.rank_name AS RANK,
	g.accu_achieve AS ACCU_PV , g.accu_p_achieve AS PERSONAL_APV ,g.net_achieve AS TNPV ,g.person_achieve AS PERSONAL_PV,
	g.bonus_achieve AS PERSONAL_BV ,g.cell_achieve AS GPV 
	FROM t_distributor t 
	LEFT JOIN t_shop_info s ON s.res_id=t.shop_id 
	LEFT  JOIN t_distributor_rank r ON r.res_id=t.rank_id 
	LEFT JOIN t_distributor_grade_his g ON g.distributor_code=t.distributor_code 
	WHERE FIND_IN_SET(t.res_id,getChildLst(v_id)) 
	AND t.floors>v_floor-1 AND DATE_FORMAT(g.achieve_date,'%Y-%m-%d')= p_date
	ORDER BY t.floors ASC; 
    END */$$
DELIMITER ;

/* Procedure structure for procedure `test_procedure` */

/*!50003 DROP PROCEDURE IF EXISTS  `test_procedure` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `test_procedure`()
BEGIN
	SELECT VERSION();
    END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
