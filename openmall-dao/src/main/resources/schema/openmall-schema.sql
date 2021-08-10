-- ----------------------------
-- openmall
-- ----------------------------
CREATE DATABASE IF NOT EXISTS `openmall`;
USE `openmall`;

-- ----------------------------
-- carousels
-- ----------------------------
DROP TABLE IF EXISTS `carousels`;
CREATE TABLE `carousels`
(
    `id`               varchar(64)  NOT NULL COMMENT '轮播图主键',
    `image_url`        varchar(128) NOT NULL COMMENT '图片地址',
    `background_color` varchar(32) DEFAULT NULL COMMENT '背景色',
    `item_id`          varchar(64) DEFAULT NULL COMMENT '商品外键',
    `cat_id`           varchar(64) DEFAULT NULL COMMENT '商品分类外键',
    `type`             int(11)      NOT NULL COMMENT '轮播图类型，可以根据商品主键或者分类进行页面跳转 1：商品 2：分类',
    `sort`             int(11)      NOT NULL COMMENT '轮播图展示顺序',
    `is_show`          int(11)      NOT NULL COMMENT '是否展示',
    `create_time`      datetime     NOT NULL COMMENT '创建时间',
    `update_time`      datetime     NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='轮播图表';

-- ----------------------------
-- categories
-- ----------------------------
DROP TABLE IF EXISTS `categories`;
CREATE TABLE `categories`
(
    `id`        int(11)     NOT NULL AUTO_INCREMENT COMMENT '商品分类主键',
    `name`      varchar(32) NOT NULL COMMENT '分类名称',
    `type`      int(11)     NOT NULL COMMENT '分类类型',
    `parent_id` int(11)     NOT NULL COMMENT '商品分类外键',
    `logo`      varchar(64) DEFAULT NULL COMMENT '图标',
    `slogan`    varchar(64) DEFAULT NULL COMMENT '口号',
    `cat_image` varchar(64) DEFAULT NULL COMMENT '分类图',
    `bg_color`  varchar(32) DEFAULT NULL COMMENT '背景颜色',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='商品分类表';

-- ----------------------------
-- items
-- ----------------------------
DROP TABLE IF EXISTS `items`;
CREATE TABLE `items`
(
    `id`            varchar(64) NOT NULL COMMENT '商品主键',
    `item_name`     varchar(32) NOT NULL COMMENT '商品名称',
    `cat_id`        int(11)     NOT NULL COMMENT '商品分类外键',
    `root_cat_id`   int(11)     NOT NULL COMMENT '商品一级分类外键',
    `sell_counts`   int(11)     NOT NULL COMMENT '累计销售',
    `on_off_status` int(11)     NOT NULL COMMENT '上下架状态 1：上架 2：下架',
    `content`       text        NOT NULL COMMENT '商品内容',
    `created_time`  datetime    NOT NULL COMMENT '创建时间',
    `updated_time`  datetime    NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='商品表';

-- ----------------------------
-- item_comments
-- ----------------------------
DROP TABLE IF EXISTS `item_comments`;
CREATE TABLE `item_comments`
(
    `id`             varchar(64)  NOT NULL COMMENT '商品评价主键',
    `user_id`        varchar(64) DEFAULT NULL COMMENT '用户外键',
    `item_id`        varchar(64)  NOT NULL COMMENT '商品外键',
    `item_name`      varchar(64) DEFAULT NULL COMMENT '商品名称',
    `item_spec_id`   varchar(64) DEFAULT NULL COMMENT '商品规格外键 可为空',
    `item_sepc_name` varchar(32) DEFAULT NULL COMMENT '商品规格名称 可为空',
    `comment_level`  int(11)      NOT NULL COMMENT '评价等级 1：好评 2：中评 3：差评',
    `content`        varchar(128) NOT NULL COMMENT '评价内容',
    `created_time`   datetime    DEFAULT NULL COMMENT '创建时间',
    `updated_time`   datetime    DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='商品评价表';

-- ----------------------------
-- item_images
-- ----------------------------
DROP TABLE IF EXISTS `item_images`;
CREATE TABLE `item_images`
(
    `id`           varchar(64)  NOT NULL COMMENT '商品图片主键',
    `item_id`      varchar(64)  NOT NULL COMMENT '商品外键',
    `url`          varchar(128) NOT NULL COMMENT '图片地址',
    `sort`         int(11)      NOT NULL COMMENT '图片顺序，从小到大',
    `is_main`      int(11)      NOT NULL COMMENT '是否主图 1：是，0：否',
    `created_time` datetime     NOT NULL COMMENT '创建时间',
    `updated_time` datetime     NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='商品图片表';

-- ----------------------------
-- item_params
-- ----------------------------
DROP TABLE IF EXISTS `item_params`;
CREATE TABLE `item_params`
(
    `id`               varchar(64) NOT NULL COMMENT '商品参数主键',
    `item_id`          varchar(32) NOT NULL COMMENT '商品外键',
    `product_place`    varchar(32) NOT NULL COMMENT '产地，例：中国江苏',
    `food_period`      varchar(32) NOT NULL COMMENT '保质期，例：180天',
    `brand`            varchar(32) NOT NULL COMMENT '品牌名，例：三只大灰狼',
    `factory_name`     varchar(32) NOT NULL COMMENT '生产厂名，例：大灰狼工厂',
    `factory_address`  varchar(32) NOT NULL COMMENT '生产厂址，例：大灰狼生产基地',
    `packaging_method` varchar(32) NOT NULL COMMENT '包装方式，例：袋装',
    `weight`           varchar(32) NOT NULL COMMENT '规格重量，例：35g',
    `storage_method`   varchar(32) NOT NULL COMMENT '存储方法，例：常温5~25°',
    `eat_method`       varchar(32) NOT NULL COMMENT '食用方式，例：开袋即食',
    `created_time`     datetime    NOT NULL COMMENT '创建时间',
    `updated_time`     datetime    NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='商品参数表';

-- ----------------------------
-- item_specs
-- ----------------------------
DROP TABLE IF EXISTS `item_specs`;
CREATE TABLE `item_specs`
(
    `id`             varchar(64)   NOT NULL COMMENT '商品规格主键',
    `item_id`        varchar(64)   NOT NULL COMMENT '商品外键',
    `name`           varchar(32)   NOT NULL COMMENT '规格名称',
    `stock`          int(11)       NOT NULL COMMENT '库存',
    `discounts`      decimal(4, 2) NOT NULL COMMENT '折扣力度',
    `price_discount` int(11)       NOT NULL COMMENT '优惠价',
    `price_normal`   int(11)       NOT NULL COMMENT '原价',
    `created_time`   datetime      NOT NULL COMMENT '创建时间',
    `updated_time`   datetime      NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='商品规格表';

-- ----------------------------
-- orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`
(
    `id`               varchar(64)  NOT NULL COMMENT '订单主键（订单编号）',
    `user_id`          varchar(64)  NOT NULL COMMENT '用户外键',
    `receiver_name`    varchar(32)  NOT NULL COMMENT '收货人快照',
    `receiver_mobile`  varchar(32)  NOT NULL COMMENT '收货人手机号快照',
    `receiver_address` varchar(128) NOT NULL COMMENT '收货地址快照',
    `total_amount`     int(11)      NOT NULL COMMENT '订单总价格',
    `real_pay_amount`  int(11)      NOT NULL COMMENT '实际支付总价格',
    `post_amount`      int(11)      NOT NULL COMMENT '邮费 默认为零，代表包邮',
    `pay_method`       int(11)      NOT NULL COMMENT '支付方式',
    `left_msg`         varchar(128) DEFAULT NULL COMMENT '买家留言',
    `extend`           varchar(32)  DEFAULT NULL COMMENT '扩展字段',
    `is_comment`       int(11)      NOT NULL COMMENT '买家是否评价 1：已评价 0：未评价',
    `is_delete`        int(11)      NOT NULL COMMENT '逻辑删除状态 1：删除 0：未删除',
    `created_time`     datetime     NOT NULL COMMENT '创建时间（成交时间）',
    `updated_time`     datetime     NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='订单表';

-- ----------------------------
-- order_items
-- ----------------------------
DROP TABLE IF EXISTS `order_items`;
CREATE TABLE `order_items`
(
    `id`             varchar(64)  NOT NULL COMMENT '订单商品主键',
    `order_id`       varchar(64)  NOT NULL COMMENT '订单外键',
    `item_id`        varchar(64)  NOT NULL COMMENT '商品外键',
    `item_img`       varchar(128) NOT NULL COMMENT '商品图片',
    `item_name`      varchar(32)  NOT NULL COMMENT '商品名称',
    `item_spec_id`   varchar(32)  NOT NULL COMMENT '商品规格外键',
    `item_spec_name` varchar(32)  NOT NULL COMMENT '商品规格名称',
    `price`          int(11)      NOT NULL COMMENT '成交价格',
    `buy_counts`     int(11)      NOT NULL COMMENT '购买数量',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='订单商品关联表';

-- ----------------------------
-- order_statuses
-- ----------------------------
DROP TABLE IF EXISTS `order_statuses`;
CREATE TABLE `order_statuses`
(
    `order_id`     varchar(64) NOT NULL COMMENT '订单主键',
    `order_status` int(11)     NOT NULL COMMENT '订单状态 10：待付款 20：已付款，待发货 30：已发货，待收货（7天自动确认） 40：交易成功（此时可以评价） 50：交易关闭（待付款时用户取消或长时间未付款，系统识别后自动关闭）',
    `created_time` datetime DEFAULT NULL COMMENT '订单创建时间 对应[10:待付款]状态',
    `pay_time`     datetime DEFAULT NULL COMMENT '支付成功时间 对应[20:已付款，待发货]状态',
    `deliver_time` datetime DEFAULT NULL COMMENT '发货时间 对应[30：已发货，待收货]状态',
    `success_time` datetime DEFAULT NULL COMMENT '交易成功时间 对应[40：交易成功]状态',
    `close_time`   datetime DEFAULT NULL COMMENT '交易关闭时间 对应[50：交易关闭]状态',
    `comment_time` datetime DEFAULT NULL COMMENT '留言时间，用户在交易成功后的留言时间',
    PRIMARY KEY (`order_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='订单状态表';

-- ----------------------------
-- users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`
(
    `id`           varchar(64)   NOT NULL COMMENT '用户主键',
    `username`     varchar(32)   NOT NULL COMMENT '用户名',
    `password`     varchar(64)   NOT NULL COMMENT '密码',
    `nickname`     varchar(32)  DEFAULT NULL COMMENT '昵称',
    `realname`     varchar(128) DEFAULT NULL COMMENT '真实姓名',
    `face`         varchar(1024) NOT NULL COMMENT '头像',
    `mobile`       varchar(32)  DEFAULT NULL COMMENT '手机号',
    `email`        varchar(32)  DEFAULT NULL COMMENT '邮箱地址',
    `sex`          int(11)      DEFAULT NULL COMMENT '性别 1：男 0：女 2：保密',
    `birthday`     date         DEFAULT NULL COMMENT '生日',
    `created_time` datetime      NOT NULL COMMENT '创建时间',
    `updated_time` datetime      NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户表';

-- ----------------------------
-- user_addresses
-- ----------------------------
DROP TABLE IF EXISTS `user_addresses`;
CREATE TABLE `user_addresses`
(
    `id`           varchar(64)  NOT NULL COMMENT '用户地址主键',
    `user_id`      varchar(64)  NOT NULL COMMENT '用户外键',
    `receiver`     varchar(32)  NOT NULL COMMENT '收件人姓名',
    `mobile`       varchar(32)  NOT NULL COMMENT '收件人手机号',
    `province`     varchar(32)  NOT NULL COMMENT '省份',
    `city`         varchar(32)  NOT NULL COMMENT '城市',
    `district`     varchar(32)  NOT NULL COMMENT '区县',
    `detail`       varchar(128) NOT NULL COMMENT '详细地址',
    `extend`       varchar(128) DEFAULT NULL COMMENT '扩展字段',
    `is_default`   int(11)      DEFAULT NULL COMMENT '是否默认地址',
    `created_time` datetime     NOT NULL COMMENT '创建时间',
    `updated_time` datetime     NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户地址表';