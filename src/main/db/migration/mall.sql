# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.23)
# Database: motion_mega_db
# Generation Time: 2018-09-11 06:25:05 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table mall_order
# ------------------------------------------------------------

DROP TABLE IF EXISTS `mall_order`;

CREATE TABLE `mall_order` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uuid` char(36) NOT NULL DEFAULT '' COMMENT 'uuid',
  `order_id` varchar(128) NOT NULL DEFAULT '' COMMENT '订单编号',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户ID',
  `type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '商品类型：1,树鱼直充；2，树鱼卡密；3，自定义',
  `product_name` varchar(128) NOT NULL DEFAULT '' COMMENT '名字',
  `category_code` varchar(12) NOT NULL DEFAULT '' COMMENT '分类代码',
  `lang_code` varchar(5) NOT NULL DEFAULT '' COMMENT '商品语言代码',
  `ip_address` varchar(128) NOT NULL DEFAULT '' COMMENT 'ip',
  `product_id` varchar(20) NOT NULL DEFAULT '' COMMENT '商品代码',
  `amount` int(10) NOT NULL DEFAULT '1' COMMENT '商品数量',
  `price` double NOT NULL COMMENT '单价',
  `total_amount` double NOT NULL COMMENT '总价',
  `fee` double NOT NULL COMMENT '手续费',
  `currency` char(3) NOT NULL DEFAULT 'CNY' COMMENT '币种',
  `discount` float DEFAULT NULL COMMENT '折扣',
  `mtn_amount` double NOT NULL COMMENT 'MTN价格',
  `pay_type` tinyint(1) NOT NULL COMMENT '付款方式(1mtn付款, 2：IPS, 3：SHB)',
  `payment_order_id` varchar(50) NOT NULL DEFAULT '' COMMENT '支付系统订单号',
  `pay_status` tinyint(1) NOT NULL COMMENT '订单付款状态：1未付款、2已付款、3已退款, 4付款失败，5已取消',
  `pay_result_code` varchar(128) DEFAULT NULL COMMENT '支付结果代码',
  `pay_result` text COMMENT '支付结果',
  `exchange_order_id` varchar(50) NOT NULL DEFAULT '' COMMENT '兑换订单号',
  `exchange_status` tinyint(1) NOT NULL COMMENT '订单状态：1未兑换，2兑换进行中，2已兌換成功，3兑换失败',
  `exchange_result_code` varchar(128) DEFAULT NULL COMMENT '兑换结果代码',
  `exchange_result` text COMMENT '兑换结果',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `attach` varchar(255) DEFAULT NULL COMMENT '附加字段',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uuid` (`uuid`),
  UNIQUE KEY `order_id` (`order_id`),
  KEY `created_at` (`created_at`),
  KEY `updated_at` (`updated_at`),
  KEY `pay_status` (`pay_status`),
  KEY `exchange_status` (`exchange_status`),
  KEY `user_id` (`user_id`),
  KEY `category_code` (`category_code`),
  KEY `product_id` (`product_id`),
  KEY `type` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table mall_product
# ------------------------------------------------------------

DROP TABLE IF EXISTS `mall_product`;

CREATE TABLE `mall_product` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uuid` char(36) DEFAULT NULL COMMENT 'uuid',
  `icon_url` varchar(128) NOT NULL DEFAULT '' COMMENT '图标',
  `type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1,树鱼直充；2，树鱼卡密；3，自定义',
  `name` varchar(128) NOT NULL DEFAULT '' COMMENT '名字',
  `short_desc` varchar(255) NOT NULL DEFAULT '' COMMENT '短描述',
  `category_code` varchar(12) NOT NULL DEFAULT '' COMMENT '分类代码',
  `lang_code` varchar(10) NOT NULL DEFAULT '' COMMENT '语言代码（zh_CN,zh_TW...）',
  `product_id` varchar(20) NOT NULL DEFAULT '' COMMENT '商品代码',
  `price` double NOT NULL COMMENT '单价',
  `currency` char(3) NOT NULL DEFAULT 'CNY' COMMENT '币种',
  `discount` float DEFAULT NULL COMMENT '折扣',
  `invoice_rate` float NOT NULL DEFAULT '0' COMMENT '发票点数',
  `sales_volume` int(11) NOT NULL DEFAULT '0' COMMENT '销量',
  `description` text COMMENT '商品描述',
  `sort` bigint(20) DEFAULT NULL COMMENT '排序',
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '启用/禁用',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `created_at` (`created_at`),
  KEY `updated_at` (`updated_at`),
  KEY `sort` (`sort`),
  KEY `category_code` (`category_code`),
  KEY `product_id` (`product_id`),
  KEY `type` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `mall_product` WRITE;
/*!40000 ALTER TABLE `mall_product` DISABLE KEYS */;

INSERT INTO `mall_product` (`id`, `uuid`, `icon_url`, `type`, `name`, `short_desc`, `category_code`, `lang_code`, `product_id`, `price`, `currency`, `discount`, `invoice_rate`, `sales_volume`, `description`, `sort`, `enabled`, `created_at`, `updated_at`)
VALUES
	(31,'765d0b93-07aa-4dbe-b4fd-b2436d44ed94','',1,'Q币','','qq_coin','zh_CN','1218222',1,'CNY',0.95,0,0,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(32,'90e0f84b-3a2e-42a3-ba7d-3d5200b30e01','',1,'QQ超级会员','','qq_member','zh_CN','1220147',20,'CNY',0.9,0,0,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(33,'34c98075-e3c0-4352-bace-d2612f3cac26','',1,'QQ会员','','qq_member','zh_CN','1219972',10,'CNY',0.9,0,0,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(34,'eda858cd-44f9-443c-b57e-4df8cfceafa1','',1,'QQ黄钻','','qq_diamonds','zh_CN','1219976',10,'CNY',0.9,0,0,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(35,'f13da347-350e-4f37-b0ce-5be5e2840adb','',1,'QQ蓝钻','','qq_diamonds','zh_CN','1219974',10,'CNY',0.94,0,0,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(36,'75275155-82a3-4b94-915f-2a000381842d','',1,'CF点券','','cf_voucher','zh_CN','1218223',1,'CNY',0.93,0,0,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(37,'8c77bed4-2b30-444e-bad8-47a1b389a809','',1,'DNF点券','','dnf_voucher','zh_CN','1218225',1,'CNY',0.93,0,0,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(38,'6eefd3f4-fbf7-41f5-8cdf-a118708ee2fc','',1,'LOL','','lol_coin','zh_CN','1218889',1,'CNY',0.93,0,0,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(39,'89daeed5-ab4e-48fd-b268-62ddffb2641b','',2,'网易一卡通','','netes_card','zh_CN','1218868',1,'CNY',0.98,0,0,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(40,'f01a6730-cf48-4123-995b-ff7724ac8f63','',1,'魔兽世界月卡','','wow_card','zh_CN','1224007',75,'CNY',0.985,0,0,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(41,'c5e2c9dc-fc14-4733-82c2-748249536cb8','',1,'魔兽世界年卡','','wow_card','zh_CN','1224008',198,'CNY',0.985,0,0,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(42,'dc279e8a-ce59-442d-867a-ff0b5c483f26','',1,'盛大点券','','sdo_voucher','zh_CN','1219895',1,'CNY',0.97,0,0,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(43,'0bfad29e-68e5-4ac3-bd26-df0c8b367a13','',1,'完美点券','','wm_voucher','zh_CN','1220211',1,'CNY',0.995,0,0,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(44,'17d2e35a-751e-4ba0-8178-95b8485d9aca','',2,'搜狐一卡通','','sh_card','zh_CN','1219935',1,'CNY',0.98,0,0,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(45,'602266ed-694f-469a-8c30-afb8cb69190b','',2,'API-WYsteam30美金 ','','steam','zh_CN','1224191',210,'CNY',0.92,0,0,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(46,'2dbff42d-1583-4d6a-8dfa-dab5bc7c791e','',2,'API-WYsteam20美金 ','','steam','zh_CN','1224190',140,'CNY',0.92,0,0,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(47,'c19fd2fe-5a72-43c6-b278-37183095e5b2','',2,'API-WYsteam15美金 ','','steam','zh_CN','1224189',105,'CNY',0.92,0,0,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(48,'a01fdbfe-190b-4093-aaed-65aa99fa1b62','',2,'API-WYsteam 100美金 ','','steam','zh_CN','1223860',700,'CNY',0.92,0,0,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(49,'148d7816-403b-4f98-a573-eb720be832b6','',2,'API-WYsteam 50美金','','steam','zh_CN','1223858',350,'CNY',0.92,0,0,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(50,'683eb776-1259-42f4-ad49-d90a83728027','',2,'API-WYsteam 10美金 ','','steam','zh_CN','1223856',70,'CNY',0.92,0,0,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(51,'5b11aac2-95d6-4ef5-b24a-1fe4b8c1cf68','',2,'API-WYsteam 5美金','','steam','zh_CN','1223855',35,'CNY',0.92,0,0,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(52,'d04e031d-5d89-474d-beff-243d28dfdaff','',2,' AppStore-100元 ','','appstore','zh_CN','1224490',100,'CNY',0.98,0,0,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(53,'f72b03f8-d03b-4e8e-b15a-136a93747751','',2,'AppStore-200元 ','','appstore','zh_CN','1227376',200,'CNY',0.98,0,0,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(54,'dc38c003-00a5-4406-8b11-b559c5bca335','',2,'AppStore-500元 ','','appstore','zh_CN','1224530',500,'CNY',0.98,0,0,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(55,'8c15ef95-b68b-470c-932e-810f3afe684e','',2,'AppStore-1000元 ','','appstore','zh_CN','1226887',1000,'CNY',0.98,0,0,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(56,'6298fa3b-93ca-46aa-8255-50d6181864c1','',2,'骏网一卡通100元 通用卡 ','','jcard','zh_CN','1222847',100,'CNY',0.975,0,0,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(57,'fff1c718-7ee8-4024-89a5-5830935b4ed4','',2,'骏网一卡通200元 通用卡 ','','jcard','zh_CN','1222848',200,'CNY',0.975,0,0,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(58,'e5ba2061-9664-42a9-a111-e643fdb2d76f','',2,'骏网一卡通300元 通用卡 ','','jcard','zh_CN','1222849',300,'CNY',0.975,0,0,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(59,'d5756581-7d52-433a-9f65-7429132316a3','',2,'骏网一卡通500元 通用卡 ','','jcard','zh_CN','1222850',500,'CNY',0.975,0,0,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(60,'e8f2e5f6-fb20-42aa-8c2e-07a8fc458d90','',2,'骏网一卡通1000元 通用卡 ','','jcard','zh_CN','1222853',1000,'CNY',0.975,0,0,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16');

/*!40000 ALTER TABLE `mall_product` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table mall_product_category
# ------------------------------------------------------------

DROP TABLE IF EXISTS `mall_product_category`;

CREATE TABLE `mall_product_category` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uuid` char(36) NOT NULL DEFAULT '' COMMENT 'uuid',
  `type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1,树鱼直充；2，树鱼卡密；3，自定义',
  `name` varchar(128) NOT NULL DEFAULT '' COMMENT '名字',
  `lang_code` varchar(10) NOT NULL DEFAULT '' COMMENT '语言代码（zh_CN,zh_TW...）',
  `category_code` varchar(12) NOT NULL DEFAULT '' COMMENT '分类代码',
  `sort` bigint(20) DEFAULT NULL COMMENT '排序',
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '启用/禁用',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `created_at` (`created_at`),
  KEY `updated_at` (`updated_at`),
  KEY `enabled` (`enabled`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `mall_product_category` WRITE;
/*!40000 ALTER TABLE `mall_product_category` DISABLE KEYS */;

INSERT INTO `mall_product_category` (`id`, `uuid`, `type`, `name`, `lang_code`, `category_code`, `sort`, `enabled`, `created_at`, `updated_at`)
VALUES
	(1,'484341b2-056b-472c-aa6a-6397c421a239',1,'q币','zh_CN','qq_coin',1,1,'2018-08-23 17:32:09','2018-08-30 02:41:16'),
	(2,'79d43ed9-9566-495a-af18-76da1a743a04',1,'qq会员','zh_CN','qq_member',1,1,'2018-08-23 18:02:50','2018-08-30 02:41:16'),
	(3,'cbef567b-d17c-42a8-b598-adb641ff6492',1,'q钻','zh_CN','qq_diamonds',1,1,'2018-08-23 18:03:02','2018-08-30 02:41:16'),
	(4,'95a7346e-5726-41e3-9f76-b2230e7a7a35',1,'CF点券','zh_CN','cf_voucher',1,1,'2018-08-23 18:06:51','2018-08-30 02:41:16'),
	(5,'07141178-b0c0-41d1-a125-1eb9d795fbca',1,'DNF点券','zh_CN','dnf_voucher',1,1,'2018-08-23 18:13:30','2018-08-30 02:41:16'),
	(6,'0d61bb1a-7176-4354-aecb-8d20ac07f51b',1,'LOL','zh_CN','lol_coin',1,1,'2018-08-23 18:14:21','2018-08-30 02:41:16'),
	(7,'2dbb2f09-2478-4584-b88d-742b7ee3d8b8',1,'网易一卡通','zh_CN','netes_card',1,1,'2018-08-23 18:16:58','2018-08-30 02:41:16'),
	(8,'e253377d-5a29-439f-88a7-55afe2706a1a',1,'魔兽世界','zh_CN','wow_card',1,1,'2018-08-23 18:28:01','2018-08-30 02:41:16'),
	(9,'123347a3-8579-475c-9c0c-48d1cdf25dd3',1,'盛大点券','zh_CN','sdo_voucher',1,1,'2018-08-23 18:28:03','2018-08-30 02:41:16'),
	(10,'ced21e2a-be00-4021-8965-98a014aa9021',1,'完美点券','zh_CN','wm_voucher',1,1,'2018-08-23 18:31:23','2018-08-30 02:41:16'),
	(11,'8f3f23cd-a1eb-4b37-b0f4-baf3a0fd6975',1,'搜狐一卡通','zh_CN','sh_card',1,1,'2018-08-23 18:31:28','2018-08-30 02:41:16'),
	(12,'0ed82e3b-7340-4d0e-9c1d-54c276b5bad2',1,'steam 美金卡','zh_CN','steam',1,1,'2018-08-23 18:32:55','2018-08-30 02:41:16'),
	(13,'10a3ea32-e955-4865-8b93-73e2cf118d49',1,'AppStore','zh_CN','appstore',1,1,'2018-08-23 18:33:00','2018-08-30 02:41:16'),
	(14,'16bfcee2-4531-45ae-b308-521b980c68e3',1,'骏网通用卡','zh_CN','jcard',1,1,'2018-08-23 18:34:49','2018-08-30 02:41:16'),
	(26,'f4c735b5-57a3-41b2-a42c-2d028c9878b7',1,'网易礼品 卡','zh_CN','netes_card',1,1,'2018-08-24 14:11:37','2018-08-30 02:41:16');

/*!40000 ALTER TABLE `mall_product_category` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
