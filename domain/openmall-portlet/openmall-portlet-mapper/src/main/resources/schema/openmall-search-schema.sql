-- ----------------------------
-- openmall_portlet
-- ----------------------------
CREATE DATABASE IF NOT EXISTS `openmall_portlet`;
USE `openmall_portlet`;

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