-- ----------------------------
-- openmall_user
-- ----------------------------
CREATE DATABASE IF NOT EXISTS `openmall_user`;
USE `openmall_user`;

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