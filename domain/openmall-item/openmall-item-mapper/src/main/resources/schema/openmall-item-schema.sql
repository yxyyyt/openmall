-- ----------------------------
-- openmall_item
-- ----------------------------
CREATE DATABASE IF NOT EXISTS `openmall_item`;
USE `openmall_item`;

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
    `item_spec_name` varchar(32) DEFAULT NULL COMMENT '商品规格名称 可为空',
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
-- item_categories
-- ----------------------------
DROP TABLE IF EXISTS `item_categories`;
CREATE TABLE `item_categories`
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