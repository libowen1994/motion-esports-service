# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.23)
# Database: motion_mega_db
# Generation Time: 2018-08-30 08:12:36 +0000
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

INSERT INTO `mall_product` (`id`, `uuid`, `icon_url`, `type`, `name`, `short_desc`, `category_code`, `lang_code`, `product_id`, `price`, `currency`, `discount`, `description`, `sort`, `enabled`, `created_at`, `updated_at`)
VALUES
	(31,'765d0b93-07aa-4dbe-b4fd-b2436d44ed94','',1,'Q币','','qq_coin','zh_CN','1218222',1,'CNY',0.95,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(32,'90e0f84b-3a2e-42a3-ba7d-3d5200b30e01','',1,'QQ超级会员','','qq_member','zh_CN','1220147',20,'CNY',0.9,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(33,'34c98075-e3c0-4352-bace-d2612f3cac26','',1,'QQ会员','','qq_member','zh_CN','1219972',10,'CNY',0.9,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(34,'eda858cd-44f9-443c-b57e-4df8cfceafa1','',1,'QQ黄钻','','qq_diamonds','zh_CN','1219976',10,'CNY',0.9,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(35,'f13da347-350e-4f37-b0ce-5be5e2840adb','',1,'QQ蓝钻','','qq_diamonds','zh_CN','1219974',10,'CNY',0.94,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(36,'75275155-82a3-4b94-915f-2a000381842d','',1,'CF点券','','cf_voucher','zh_CN','1218223',1,'CNY',0.93,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(37,'8c77bed4-2b30-444e-bad8-47a1b389a809','',1,'DNF点券','','dnf_voucher','zh_CN','1218225',1,'CNY',0.93,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(38,'6eefd3f4-fbf7-41f5-8cdf-a118708ee2fc','',1,'LOL','','lol_coin','zh_CN','1218889',1,'CNY',0.93,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(39,'89daeed5-ab4e-48fd-b268-62ddffb2641b','',2,'网易一卡通','','netes_card','zh_CN','1218868',1,'CNY',0.98,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(40,'f01a6730-cf48-4123-995b-ff7724ac8f63','',1,'魔兽世界月卡','','wow_card','zh_CN','1224007',75,'CNY',0.985,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(41,'c5e2c9dc-fc14-4733-82c2-748249536cb8','',1,'魔兽世界年卡','','wow_card','zh_CN','1224008',198,'CNY',0.985,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(42,'dc279e8a-ce59-442d-867a-ff0b5c483f26','',1,'盛大点券','','sdo_voucher','zh_CN','1219895',1,'CNY',0.97,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(43,'0bfad29e-68e5-4ac3-bd26-df0c8b367a13','',1,'完美点券','','wm_voucher','zh_CN','1220211',1,'CNY',0.995,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(44,'17d2e35a-751e-4ba0-8178-95b8485d9aca','',2,'搜狐一卡通','','sh_card','zh_CN','1219935',1,'CNY',0.98,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(45,'602266ed-694f-469a-8c30-afb8cb69190b','',2,'API-WYsteam30美金 ','','steam','zh_CN','1224191',210,'CNY',0.92,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(46,'2dbff42d-1583-4d6a-8dfa-dab5bc7c791e','',2,'API-WYsteam20美金 ','','steam','zh_CN','1224190',140,'CNY',0.92,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(47,'c19fd2fe-5a72-43c6-b278-37183095e5b2','',2,'API-WYsteam15美金 ','','steam','zh_CN','1224189',105,'CNY',0.92,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(48,'a01fdbfe-190b-4093-aaed-65aa99fa1b62','',2,'API-WYsteam 100美金 ','','steam','zh_CN','1223860',700,'CNY',0.92,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(49,'148d7816-403b-4f98-a573-eb720be832b6','',2,'API-WYsteam 50美金','','steam','zh_CN','1223858',350,'CNY',0.92,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(50,'683eb776-1259-42f4-ad49-d90a83728027','',2,'API-WYsteam 10美金 ','','steam','zh_CN','1223856',70,'CNY',0.92,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(51,'5b11aac2-95d6-4ef5-b24a-1fe4b8c1cf68','',2,'API-WYsteam 5美金','','steam','zh_CN','1223855',35,'CNY',0.92,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(52,'d04e031d-5d89-474d-beff-243d28dfdaff','',2,' AppStore-100元 ','','appstore','zh_CN','1224490',100,'CNY',0.98,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(53,'f72b03f8-d03b-4e8e-b15a-136a93747751','',2,'AppStore-200元 ','','appstore','zh_CN','1227376',200,'CNY',0.98,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(54,'dc38c003-00a5-4406-8b11-b559c5bca335','',2,'AppStore-500元 ','','appstore','zh_CN','1224530',500,'CNY',0.98,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(55,'8c15ef95-b68b-470c-932e-810f3afe684e','',2,'AppStore-1000元 ','','appstore','zh_CN','1226887',1000,'CNY',0.98,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(56,'6298fa3b-93ca-46aa-8255-50d6181864c1','',2,'骏网一卡通100元 通用卡 ','','jcard','zh_CN','1222847',100,'CNY',0.975,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(57,'fff1c718-7ee8-4024-89a5-5830935b4ed4','',2,'骏网一卡通200元 通用卡 ','','jcard','zh_CN','1222848',200,'CNY',0.975,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(58,'e5ba2061-9664-42a9-a111-e643fdb2d76f','',2,'骏网一卡通300元 通用卡 ','','jcard','zh_CN','1222849',300,'CNY',0.975,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(59,'d5756581-7d52-433a-9f65-7429132316a3','',2,'骏网一卡通500元 通用卡 ','','jcard','zh_CN','1222850',500,'CNY',0.975,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(60,'e8f2e5f6-fb20-42aa-8c2e-07a8fc458d90','',2,'骏网一卡通1000元 通用卡 ','','jcard','zh_CN','1222853',1000,'CNY',0.975,NULL,NULL,1,'2018-08-21 16:08:49','2018-08-30 02:41:16'),
	(61,'06d40efa-c587-4648-b2f5-6604fc1fea83','',2,'ofo骑行月卡','','ofo','zh_CN','',20,'CNY',0.9,NULL,NULL,1,'2018-08-21 16:16:02','2018-08-30 02:41:16'),
	(62,'f13d18d8-271f-4ab9-afef-f61ebf3adff7','',2,'摩拜骑行券','','mobo','zh_CN','',10,'CNY',0.9,NULL,NULL,1,'2018-08-21 16:16:02','2018-08-30 02:41:16'),
	(63,'5fa0e65a-3ffe-490f-9653-433cab36db0f','',2,'全国通用单次洗车券小车','','cdd','zh_CN','',25,'CNY',0.9,NULL,NULL,1,'2018-08-21 16:16:02','2018-08-30 02:41:16'),
	(64,'e3714419-e42a-42f1-b2e7-c551a68f3e32','',2,'全国通用单次洗车券SUV','','cdd','zh_CN','',30,'CNY',0.85,NULL,NULL,1,'2018-08-21 16:16:02','2018-08-30 02:41:16'),
	(65,'6278b436-80f4-44ac-9fd4-97b0e8dff08f','',2,'全国通用洗车半年卡','','cdd','zh_CN','',145,'CNY',0.8,NULL,NULL,1,'2018-08-21 16:16:02','2018-08-30 02:41:16'),
	(66,'b6524651-dc00-46bb-996d-8527365e2592','',2,'全国通用洗车全年卡','','cdd','zh_CN','',260,'CNY',0.8,NULL,NULL,1,'2018-08-21 16:16:02','2018-08-30 02:41:16'),
	(73,'2160e3ab-1535-4023-9ec7-ae51bda75640','',2,'滴滴打车代驾券','','dd_driving','zh_CN','',1,'CNY',0.89,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(74,'b18440a6-b4b0-4e94-8df0-f30db8e6313d','',2,'滴滴打车代驾券','','dd_driving','zh_CN','',19,'CNY',0.91,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(75,'f22e8f0f-5237-4977-a933-d33039371363','',2,'滴滴打车代驾券','','dd_driving','zh_CN','',39,'CNY',0.93,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(76,'be0a3b50-da67-4d5d-9188-3c7474973b64','',2,'滴滴打车代驾券','','dd_driving','zh_CN','',60,'CNY',0.95,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(77,'94114608-a14a-4ce2-a62e-f7a98642bd84','',2,'滴滴打车专车券','','dd_coupons','zh_CN','',10,'CNY',0.9,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(78,'09872e83-a9ce-46da-b7d0-a82687e414b7','',2,'滴滴打车快车券','','dd_coupons','zh_CN','',10,'CNY',0.94,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(79,'f05aecdd-7622-4b94-86b5-fee32514138d','',2,'超级会员','','ctrip_menber','zh_CN','',188,'CNY',0.92,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(80,'b07fd8ca-73aa-494e-8b28-9640180208aa','',2,'携程任我游礼品卡','','ctrip_card','zh_CN','',100,'CNY',1,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(81,'006196ec-7cca-4606-931e-731e3e352254','',2,'携程任我行礼品卡','','ctrip_card','zh_CN','',88,'CNY',1,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(82,'7ad24a01-4d0d-4a8a-b476-c023055652ba','',2,'e袋洗E卡直充','','edaixi_card','zh_CN','',50,'CNY',0.78,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(83,'592092b4-265c-44b7-be68-28bb59de777d','',2,'礼品卡','','womai_card','zh_CN','',88,'CNY',0.98,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(84,'b787f9aa-cb41-44a2-8e4c-359b4cd9e359','',2,'亚马逊礼品卡','','amazon_card','zh_CN','',88,'CNY',0.98,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(85,'4c63512b-846e-453b-b9db-51b48104fd91','',2,'当当礼品卡','','dang_card','zh_CN','',50,'CNY',1,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(86,'29421bc8-feaa-44cb-adfa-c4d83aaad63d','',2,'网易严选礼品卡','','netes_card','zh_CN','',100,'CNY',0.99,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(87,'c35ddfcc-4f55-4f3f-a0e2-85cd8d2b4731','',2,'网易严选代金券','','netes_coupon','zh_CN','',100,'CNY',0.97,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(88,'e69cac10-a78e-4430-9d32-e8517ff14741','',2,'唯品会礼品卡','','vip_card','zh_CN','',100,'CNY',0.97,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(89,'63cc7c72-7e76-48d6-b683-2a11cc38edc4','',2,'京东E卡','','jd_card','zh_CN','',100,'CNY',0.993,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(90,'f99a3d2d-5107-4ba1-a632-d8d5cd4fdc75','',2,'电子卡','','zon_card','zh_CN','',100,'CNY',0.99,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(91,'220a223d-e3c1-4241-9289-206fd86a90e7','',2,'苏宁易购礼品卡','','su_card','zh_CN','',98,'CNY',0.988,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(92,'412e8a90-9616-4ec6-96e5-43a8e34f72cf','',2,'麦德龙电子购物卡','','metro_card','zh_CN','',100,'CNY',0.985,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(93,'6200ae7f-c0b9-49d0-9628-8988b4b8c762','',2,'家乐福电子购物卡','','jlf_ccard','zh_CN','',100,'CNY',0.985,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(94,'7a364ad2-1887-4d77-9d9d-462ff4464f3a','',2,'沃尔玛电子购物卡','','wal_card','zh_CN','',103,'CNY',0.985,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(95,'90957990-32a0-4913-9c34-39434166d8e1','',2,'本来生活礼品卡','','bl_card','zh_CN','',100,'CNY',0.99,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(96,'99cb8990-7871-437c-9c23-2fee2ec1a095','',2,'肯德基优惠券','','kfc_coupon','zh_CN','',50,'CNY',0.97,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(97,'a4302b1c-a53b-4998-8c19-adcbb5ae3e0f','',2,'百胜心意礼品卡','','baison_card','zh_CN','',30,'CNY',0.97,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(98,'54d00e47-d0d8-4bb2-baae-6c21a13060e1','',2,'电子代金券','','hgds_coupon','zh_CN','',35,'CNY',0.9,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(99,'f2b79a42-cf85-4e97-a098-9debbc2aa985','',2,'APP电子代金券','','bcw_coupon','zh_CN','',10,'CNY',0.99,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(100,'36a62632-dc39-4597-b895-f01886406f8b','',2,'电子券','','qj_coupon','zh_CN','',20,'CNY',0.93,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(101,'863cec00-4d63-448d-9b7c-98843920fb69','',2,'电子代金券','','mbxy_coupon','zh_CN','',10,'CNY',0.9,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(102,'414a52f3-c5b2-4d04-95da-495377c1fd3f','',2,'百果园','','bgy_coupon','zh_CN','',30,'CNY',0.96,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(103,'d13ec4c1-ac9f-44f2-b59e-d8d812893c61','',2,'饿了么满减券（满 25减 6）','','elm_coupon','zh_CN','',6,'CNY',0.85,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(104,'3ac6abcd-a71d-443d-a9d5-cc2a8039a7b4','',2,'星巴克代金券','','baike_coupon','zh_CN','',30,'CNY',0.98,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(105,'9f6316fe-1a12-4bcd-9a8e-d5bc2033dea0','',2,'丽人、娱乐券','','lr_coupon','zh_CN','',20,'CNY',0.82,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(107,'d651550b-4ae8-4df6-84b9-a45bf31ec7b9','',2,'酒旅券','','jl_coupon','zh_CN','',70,'CNY',0.72,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(108,'d916f29f-8425-4054-a6ef-4fc66448a994','',2,'酒旅券','','jl_coupon','zh_CN','',150,'CNY',0.82,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(109,'cdec3a57-4c11-447d-b9f1-016c8fe810d0','',2,'美食券','','food_coupon','zh_CN','',50,'CNY',0.95,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(110,'8f293f4d-72f0-49a8-94b1-b899d3fd7c90','',2,'美食券','','food_coupon','zh_CN','',100,'CNY',0.92,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(111,'9ff2cb6d-e8f5-4e31-989c-14b101a54c34','',2,'通兑券','','share_coupon','zh_CN','',40,'CNY',0.92,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(112,'0002c6f7-3aa2-48fd-a9ef-3c755135d25b','',2,'通兑券','','share_coupon','zh_CN','',10,'CNY',0.96,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(113,'59d2edab-38d1-40df-b415-ee3c6f37051a','',2,'代金券','','mt_coupon','zh_CN','',5,'CNY',0.88,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(114,'fd6cd39e-eb77-4a56-a84d-e6f3999e61c7','',2,'代金券','','lppz_coupon','zh_CN','',0,'CNY',0.98,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(115,'91ebba96-eef4-4211-bf11-8b885277a934','',2,'代金券','','today_coupon','zh_CN','',0,'CNY',0.98,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(116,'a9d5d130-5cd4-4d82-9931-7aa279e40080','',2,'周黑鸭代金券','','zhy_coupon','zh_CN','',10,'CNY',0.96,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(117,'978ebcf6-c1ee-4191-8932-7c4e95b4b95c','',2,'心E礼品卡成人','','dsn_card','zh_CN','',575,'CNY',0.985,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(131,'1ced4088-3741-4e40-80bb-d3bb0fdca7be','',2,'咪咕电影卡','','migu_card','zh_CN','',10,'CNY',0.94,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(132,'3e6c2c07-50db-413b-bfe8-ed2ad4ede446','',2,'乐卡通','','lkt_coupon','zh_CN','',38,'CNY',0.99,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(133,'c7d309cb-c917-4d1b-a17e-54d529d44028','',2,'永乐票务代金券','','228_coupon','zh_CN','',38,'CNY',0.99,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(134,'5e93228a-ae24-424a-a417-014fec2bed27','',2,'阳澄湖大闸蟹','','dyx_gift','zh_CN','',345,'CNY',0.75,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(135,'cd321bbb-f7fd-4cfd-b3c0-2341f47b2baf','',2,'蟹','','hxh_gift','zh_CN','',345,'CNY',0.7,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(136,'f0866dac-0cb1-4ac5-ad63-9064a3e2767f','',2,'全国版C套餐','','gat_card','zh_CN','',700,'CNY',0.82,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(137,'9ad3fd81-a0ab-45e3-a25a-540c540b7bf7','',2,'全国版B套餐','','gat_card','zh_CN','',500,'CNY',0.82,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(138,'efd92009-426c-4911-b985-debf39d3bc42','',2,'全国版A套餐','','gat_card','zh_CN','',300,'CNY',0.82,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(139,'3f626524-c164-4a08-937b-4b87c728dcb0','',2,'常规入职体检','','gat_card','zh_CN','',158,'CNY',0.82,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(140,'4a7ae097-8e79-475a-a5ca-8dce6ba794bd','',2,'中石化加油卡','','zsh_card','zh_CN','',500,'CNY',1,NULL,NULL,1,'2018-08-21 16:26:44','2018-08-30 02:41:16'),
	(141,'0dada61c-d987-43cc-84ec-ab4a97205b87','',2,'当当礼品卡','','dang_card','zh_CN','',100,'CNY',1,NULL,NULL,1,'2018-08-21 16:54:35','2018-08-30 02:41:16'),
	(142,'ccf5e2b7-7af8-49f7-aeff-d231aacf59d9','',2,'当当礼品卡','','dang_card','zh_CN','',200,'CNY',1,NULL,NULL,1,'2018-08-21 16:54:56','2018-08-30 02:41:16'),
	(143,'054347f3-5b55-480b-80fd-0cfce6e25694','',2,'当当礼品卡','','dang_card','zh_CN','',500,'CNY',1,NULL,NULL,1,'2018-08-21 16:55:07','2018-08-30 02:41:16'),
	(144,'8e0956e4-b067-4004-8e23-ec69d213baa9','',2,'肯德基优惠券','','kfc_coupon','zh_CN','',100,'CNY',0.97,NULL,NULL,1,'2018-08-21 19:24:07','2018-08-30 02:41:16'),
	(145,'7b19ce6e-633e-4119-9aee-18e29484963b','',2,'肯德基优惠券','','kfc_coupon','zh_CN','',200,'CNY',0.97,NULL,NULL,1,'2018-08-21 19:24:14','2018-08-30 02:41:16'),
	(146,'baff9cce-f3bf-43e3-a707-4210d16a8106','',2,'肯德基优惠券','','kfc_coupon','zh_CN','',500,'CNY',0.97,NULL,NULL,1,'2018-08-21 19:24:19','2018-08-30 02:41:16'),
	(147,'323a9b69-57d3-475e-86af-493fd17fe32f','',2,'百胜心意礼品卡','','baison_card','zh_CN','',200,'CNY',0.97,NULL,NULL,1,'2018-08-21 19:25:46','2018-08-30 02:41:16'),
	(148,'89b12b49-b5bd-407e-ac25-adc56dc9135b','',2,'电子代金券','','hgds_coupon','zh_CN','',50,'CNY',0.9,NULL,NULL,1,'2018-08-21 19:26:50','2018-08-30 02:41:16'),
	(149,'5c91a109-19fc-4bcf-ac0f-938545375e25','',2,'电子代金券','','hgds_coupon','zh_CN','',100,'CNY',0.9,NULL,NULL,1,'2018-08-21 19:26:59','2018-08-30 02:41:16'),
	(150,'c0b66901-ba06-4c7f-b15c-c0775327bd3b','',2,'APP电子代金券','','bcw_coupon','zh_CN','',30,'CNY',0.99,NULL,NULL,1,'2018-08-21 19:28:12','2018-08-30 02:41:16'),
	(151,'277fb630-d867-4e29-8bf6-2bc99547170e','',2,'APP电子代金券','','bcw_coupon','zh_CN','',50,'CNY',0.99,NULL,NULL,1,'2018-08-21 19:28:14','2018-08-30 02:41:16'),
	(152,'9c4e7257-6939-4d52-bd35-28a73e4f727c','',2,'电子代金券','','mbxy_coupon','zh_CN','',30,'CNY',0.9,NULL,NULL,1,'2018-08-21 19:29:41','2018-08-30 02:41:16'),
	(153,'bda02de7-474c-4a9f-866e-db18c5451a16','',2,'电子代金券','','mbxy_coupon','zh_CN','',50,'CNY',0.9,NULL,NULL,1,'2018-08-21 19:29:50','2018-08-30 02:41:16'),
	(154,'918b86d4-1e10-4935-9ee0-9441f5361640','',2,'电子代金券','','mbxy_coupon','zh_CN','',100,'CNY',0.9,NULL,NULL,1,'2018-08-21 19:29:58','2018-08-30 02:41:16'),
	(155,'e4118800-51a4-4868-afa5-9ab11de146a0','',2,'电子代金券','','mbxy_coupon','zh_CN','',200,'CNY',0.9,NULL,NULL,1,'2018-08-21 19:30:02','2018-08-30 02:41:16'),
	(156,'001a6183-2d0b-4438-a5dd-64805083092f','',2,'星巴克代金券','','baike_coupon','zh_CN','',50,'CNY',0.98,NULL,NULL,1,'2018-08-21 19:31:19','2018-08-30 02:41:16'),
	(157,'6702585b-2feb-4551-bbff-25ed9b633173','',2,'代金券','','mt_coupon','zh_CN','',10,'CNY',0.88,NULL,NULL,1,'2018-08-21 19:34:31','2018-08-30 02:41:16'),
	(158,'22d0d4c5-afa8-4338-864e-21aafe7a1b77','',2,'代金券','','mt_coupon','zh_CN','',15,'CNY',0.88,NULL,NULL,1,'2018-08-21 19:34:38','2018-08-30 02:41:16'),
	(159,'978d4714-a70f-4874-a4be-3a9ee89397fd','',2,'代金券','','mt_coupon','zh_CN','',20,'CNY',0.88,NULL,NULL,1,'2018-08-21 19:34:40','2018-08-30 02:41:16'),
	(160,'eff61464-387a-42df-bfb8-e8a29cfb3113','',2,'中石化加油卡','','zsh_card','zh_CN','',1000,'CNY',1,NULL,NULL,1,'2018-08-21 19:36:02','2018-08-30 02:41:16'),
	(161,'4c3671a8-a2a1-4b33-903f-7ed9441425d2','',1,'e袋洗E卡卡密','','edaixi_card','zh_CN','',50,'CNY',0.78,NULL,NULL,1,'2018-08-21 19:40:50','2018-08-30 02:41:16'),
	(162,'1cabc1d9-7471-434b-b775-dd5fcaadd43f','',1,'百果园','','bgy_coupon','zh_CN','',30,'CNY',0.96,NULL,NULL,1,'2018-08-21 19:42:15','2018-08-30 02:41:16');

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
	(15,'f71cd04c-2275-43c3-82a5-bcc978cfcf6f',1,'ofo卡','zh_CN','ofo',1,1,'2018-08-23 18:35:20','2018-08-30 02:41:16'),
	(16,'8af82ab3-a2b9-4fc4-89ed-2ecf1878af95',1,'摩拜卡','zh_CN','mobo',1,1,'2018-08-23 18:38:26','2018-08-30 02:41:16'),
	(17,'13acd824-ece9-4a8b-b213-5db0a285c69a',1,'车点点洗车卡','zh_CN','cdd',1,1,'2018-08-23 18:40:23','2018-08-30 02:41:16'),
	(18,'3daae887-5b88-4578-8363-e4cffc7d1648',1,'滴滴代驾券','zh_CN','dd_driving',1,1,'2018-08-23 18:44:13','2018-08-30 02:41:16'),
	(19,'67e46a57-7ce3-4046-94fd-7774d5712ff0',1,'滴滴乘车券','zh_CN','dd_coupons',1,1,'2018-08-24 14:02:33','2018-08-30 02:41:16'),
	(20,'9e23d2ba-92ed-4a5f-bd0d-9218ed8cda18',1,'携程旅游超级会员','zh_CN','ctrip_menber',1,1,'2018-08-24 14:04:10','2018-08-30 02:41:16'),
	(21,'5f16ab4d-e29c-4fd6-ba29-edfb7c2c7145',1,'携程旅游礼品卡','zh_CN','ctrip_card',1,1,'2018-08-24 14:05:09','2018-08-30 02:41:16'),
	(22,'59f3ec8e-734b-43f7-b372-021712442c56',1,'e袋洗直冲卡','zh_CN','edaixi_card',1,1,'2018-08-24 14:06:53','2018-08-30 02:41:16'),
	(23,'7f57a220-499a-4872-9c57-683febb84220',1,'我买网礼品卡','zh_CN','womai_card',1,1,'2018-08-24 14:08:25','2018-08-30 02:41:16'),
	(24,'b297e4c3-101f-412d-8732-20d02c5b098d',1,'亚马逊礼品卡','zh_CN','amazon_card',1,1,'2018-08-24 14:09:11','2018-08-30 02:41:16'),
	(25,'b845d9bc-522a-42c8-8984-7762bd527ffc',1,'当当网礼品卡','zh_CN','dang_card',1,1,'2018-08-24 14:09:55','2018-08-30 02:41:16'),
	(26,'f4c735b5-57a3-41b2-a42c-2d028c9878b7',1,'网易礼品 卡','zh_CN','netes_card',1,1,'2018-08-24 14:11:37','2018-08-30 02:41:16'),
	(27,'5fdc7879-6302-484b-b16f-8bf83159273d',1,'网易代金券','zh_CN','netes_coupon',1,1,'2018-08-24 14:11:55','2018-08-30 02:41:16'),
	(28,'1a7bf0d0-0d77-4508-a9ca-5a61acf240df',1,'唯品会礼品卡','zh_CN','vip_card',1,1,'2018-08-24 14:12:34','2018-08-30 02:41:16'),
	(29,'fad78a47-e285-4eea-a02e-f9442d1e6ace',1,'京东E卡','zh_CN','jd_card',1,1,'2018-08-24 14:13:33','2018-08-30 02:41:16'),
	(30,'be132663-c1e1-4b51-90cd-4f70d31f1478',1,'中百电子卡','zh_CN','zon_card',1,1,'2018-08-24 14:14:18','2018-08-30 02:41:16'),
	(31,'4aa15e5e-f994-45d7-b2d3-22b7a53e143b',1,'苏宁易购礼品卡','zh_CN','su_card',1,1,'2018-08-24 14:15:38','2018-08-30 02:41:16'),
	(32,'f35d12f5-f3fc-4319-91cb-7985e4799695',1,'麦德龙礼品卡','zh_CN','metro_card',1,1,'2018-08-24 14:17:05','2018-08-30 02:41:16'),
	(33,'221cd600-ceb0-41bf-9be3-270db29fceef',1,'家乐福电子购物卡','zh_CN','jlf_ccard',1,1,'2018-08-24 14:27:43','2018-08-30 02:41:16'),
	(34,'3401ccbf-4f48-469c-91d1-d1bb73641b82',1,'沃尔玛电子购物卡','zh_CN','wal_card',1,1,'2018-08-24 14:28:49','2018-08-30 02:41:16'),
	(35,'0f4e0ad9-3fe8-4041-bef0-c394abe768de',1,'本来生活礼品卡','zh_CN','bl_card',1,1,'2018-08-24 14:30:08','2018-08-30 02:41:16'),
	(36,'5d04054b-c1da-4d74-87b3-349430b404ee',1,'肯德基优惠券','zh_CN','kfc_coupon',1,1,'2018-08-24 14:30:49','2018-08-30 02:41:16'),
	(37,'7b6a85ce-bd04-4232-b99b-2fa4e92e7694',1,'百胜礼品卡','zh_CN','baison_card',1,1,'2018-08-24 14:35:28','2018-08-30 02:41:16'),
	(38,'8c1499b4-48f9-486b-893d-1d46e51781f4',1,'哈根达斯代金券','zh_CN','hgds_coupon',1,1,'2018-08-24 14:36:31','2018-08-30 02:41:16'),
	(39,'80875aae-9716-4031-b945-605411d763f5',1,'仟吉电子券','zh_CN','qj_coupon',1,1,'2018-08-24 15:45:03','2018-08-30 02:41:16'),
	(40,'19307d95-a364-4c66-b8bb-b652d702fee7',1,'面包新语电子代金券','zh_CN','mbxy_coupon',1,1,'2018-08-24 15:46:20','2018-08-30 02:41:16'),
	(41,'419ba3db-9142-4566-9079-1acf04fc5c88',1,'百果园代金券','zh_CN','bgy_coupon',1,1,'2018-08-24 15:47:32','2018-08-30 02:41:16'),
	(42,'91b0c106-4d6e-4027-84a1-b91b2c0a7e07',1,'饿了么代金券(满减)','zh_CN','elm_coupon',1,1,'2018-08-24 15:48:47','2018-08-30 02:41:16'),
	(43,'18e5b424-37a4-43f8-9e63-957762e84133',1,'星巴克代金券','zh_CN','baike_coupon',1,1,'2018-08-24 15:49:37','2018-08-30 02:41:16'),
	(44,'5b40efdd-5ed0-4c7e-b932-a0815af4983e',1,'丽人娱乐券','zh_CN','lr_coupon',1,1,'2018-08-24 15:51:05','2018-08-30 02:41:16'),
	(45,'1143218c-47e1-4554-8842-5eea2d6d1809',1,'酒旅代金券','zh_CN','jl_coupon',1,1,'2018-08-24 15:52:32','2018-08-30 02:41:16'),
	(46,'6d323113-0fc6-40a6-af11-5ac51524f929',1,'美食券','zh_CN','food_coupon',1,1,'2018-08-24 15:53:39','2018-08-30 02:41:16'),
	(47,'25b701c8-93c7-49e4-83f9-358d52095628',1,'通兑券','zh_CN','share_coupon',1,1,'2018-08-24 15:55:17','2018-08-30 02:41:16'),
	(48,'efbedbcd-b257-4cae-b5a8-55531b4d14e4',1,'美团代金券','zh_CN','mt_coupon',1,1,'2018-08-24 15:56:06','2018-08-30 02:41:16'),
	(49,'1a7097b5-5059-4859-a732-fa728bee882e',1,'良品铺子代金券','zh_CN','lppz_coupon',1,1,'2018-08-24 15:56:58','2018-08-30 02:41:16'),
	(50,'d3201a6b-8085-4aa4-8033-7f5b35bee484',1,'Today便利店代金券','zh_CN','today_coupon',1,1,'2018-08-24 16:02:53','2018-08-30 02:41:16'),
	(51,'50bf5b3d-7ae8-4091-8cd8-df6ae7af490b',1,'周黑鸭代金券','zh_CN','zhy_coupon',1,1,'2018-08-24 16:40:17','2018-08-30 02:41:16'),
	(52,'4510ea2b-b70d-404b-a9f6-dc2d0020d17b',1,'迪士尼礼品卡','zh_CN','dsn_card',1,1,'2018-08-24 16:41:47','2018-08-30 02:41:16'),
	(53,'8268bac3-9e2e-4716-9d86-326db696ccc9',1,'咪咕卡','zh_CN','migu_card',1,1,'2018-08-24 16:42:42','2018-08-30 02:41:16'),
	(54,'7c863337-9916-4e9c-b44b-515524cc4af9',1,'乐卡通代金券','zh_CN','lkt_coupon',1,1,'2018-08-24 16:43:50','2018-08-30 02:41:16'),
	(55,'5efa0aa1-4451-48dc-bf66-7262df361c68',1,'永乐票务代金券','zh_CN','228_coupon',1,1,'2018-08-24 16:44:45','2018-08-30 02:41:16'),
	(56,'872b91eb-36d3-4bd2-b099-d4a934ec2f02',1,'得一鲜礼品券','zh_CN','dyx_gift',1,1,'2018-08-24 16:53:21','2018-08-30 02:41:16'),
	(57,'4d2243b7-d82d-4489-a51d-60dd59b9be4f',1,'好蟹汇礼品券','zh_CN','hxh_gift',1,1,'2018-08-24 16:54:08','2018-08-30 02:41:16'),
	(58,'cc845631-4a88-4884-b4f2-18758fc2a8e3',1,'关爱通体检卡','zh_CN','gat_card',1,1,'2018-08-24 16:54:44','2018-08-30 02:41:16'),
	(59,'f0fdf8b4-bb8a-4bb8-bb93-37f526ee2b39',1,'中石化加油卡','zh_CN','zsh_card',1,1,'2018-08-24 16:57:02','2018-08-30 02:41:16');

/*!40000 ALTER TABLE `mall_product_category` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;