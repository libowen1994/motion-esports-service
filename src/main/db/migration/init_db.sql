CREATE TABLE `mall_product_category` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uuid` CHAR(36) NOT NULL DEFAULT '' COMMENT 'uuid',
  `type` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '1,树鱼直充；2，树鱼卡密；3，自定义',
  `name` VARCHAR(128) NOT NULL DEFAULT '' COMMENT '名字',
  `category_code` VARCHAR(12) NOT NULL DEFAULT '' COMMENT '分类代码',
  `sort` BIGINT(20) DEFAULT NULL COMMENT '排序',
  `enabled` TINYINT(1) NOT NULL DEFAULT '1' COMMENT '启用/禁用',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `created_at` (`created_at`),
  KEY `updated_at` (`updated_at`),
  KEY `enabled` (`enabled`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE `mall_product` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uuid` CHAR(36) NOT NULL DEFAULT '' COMMENT 'uuid',
  `icon_url` VARCHAR(128) NOT NULL DEFAULT ''COMMENT '图标',
  `type` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '1,树鱼直充；2，树鱼卡密；3，自定义',
  `name` VARCHAR(128) NOT NULL DEFAULT '' COMMENT '名字',
  `short_desc` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '短描述',
  `category_code` VARCHAR(12) NOT NULL DEFAULT '' COMMENT '分类代码',
  `product_id` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '商品代码',
  `price` DOUBLE NOT NULL COMMENT '单价',
  `currency` CHAR(3) NOT NULL DEFAULT 'CNY' COMMENT '币种',
  `discount` FLOAT DEFAULT NULL COMMENT '折扣',
  `description` TEXT COMMENT '商品描述',
  `sort` BIGINT(20) DEFAULT NULL COMMENT '排序',
  `enabled` TINYINT(1) NOT NULL DEFAULT '1' COMMENT '启用/禁用',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `created_at` (`created_at`),
  KEY `updated_at` (`updated_at`),
  KEY `sort` (`sort`),
  KEY `category_code` (`category_code`),
  KEY `product_id` (`product_id`),
  KEY `type` (`type`),
  KEY `enabled` (`enabled`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE `mall_order` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uuid` CHAR(36) NOT NULL DEFAULT '' COMMENT 'uuid',
  `order_id` VARCHAR(128) NOT NULL DEFAULT ''COMMENT '订单编号',
  `user_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '用户ID',
  `type` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '商品类型：1,树鱼直充；2，树鱼卡密；3，自定义',
  `name` VARCHAR(128) NOT NULL DEFAULT '' COMMENT '名字',
  `category_code` VARCHAR(12) NOT NULL DEFAULT '' COMMENT '分类代码',
  `product_id` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '商品代码',
  `amount` INT(10) NOT NULL DEFAULT 1 COMMENT '商品数量',
  `price` DOUBLE NOT NULL COMMENT '单价',
  `currency` CHAR(3) NOT NULL DEFAULT 'CNY' COMMENT '币种',
  `discount` FLOAT DEFAULT NULL COMMENT '折扣',
  `mtn_amount` DOUBLE NOT NULL COMMENT 'MTN价格',
  `pay_type` TINYINT(1) NOT NULL COMMENT '付款方式(1法币付款，2mtn付款)',
  `pay_status` TINYINT(1) NOT NULL COMMENT '订单付款状态：1未付款、2已付款、3已退款, 4付款失败，5已取消',
  `pay_result` TEXT COMMENT '支付结果',
  `exchange_status` TINYINT(1) NOT NULL COMMENT '订单状态：1未兑换，2兑换进行中，2已兌換成功，3兑换失败',
  `exchange_result` TEXT COMMENT '兑换结果',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uuid` (`uuid`),
  KEY `created_at` (`created_at`),
  KEY `updated_at` (`updated_at`),
  KEY `pay_status` (`pay_status`),
  KEY `exchange_status` (`exchange_status`),
  UNIQUE KEY `order_id` (`order_id`),
  KEY `user_id` (`user_id`),
  KEY `category_code` (`category_code`),
  KEY `product_id` (`product_id`),
  KEY `type` (`type`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;