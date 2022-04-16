-- ----------------------------
-- openmall_order
-- ----------------------------
CREATE DATABASE IF NOT EXISTS `openmall_order`;
USE `openmall_order`;

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