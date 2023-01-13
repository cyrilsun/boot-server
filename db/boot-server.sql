/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : boot-server

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 13/01/2023 15:54:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `parent_id` int unsigned DEFAULT NULL COMMENT '父id',
  `name` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '名称',
  `path` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '路径',
  `component` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '组件',
  `title` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '标题',
  `icon` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图标',
  `code` varchar(128) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '编码',
  `url` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '路径',
  `type` tinyint unsigned DEFAULT NULL COMMENT '类型 0目录 1菜单 2按钮',
  `sort_no` int unsigned DEFAULT NULL COMMENT '排序',
  `is_leaf` tinyint unsigned DEFAULT NULL COMMENT '是否叶子结点 0否 1是',
  `del_flag` tinyint unsigned DEFAULT '0' COMMENT '删除状态 0未删除 1已删除',
  `create_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1086443525 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='权限表';

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_permission` VALUES (1, 0, 'System', 'system', 'Layout', '系统管理', 'system', 'system', NULL, 0, 1, 0, 0, '1', '2022-11-13 15:55:35', 'admin', '2022-11-23 17:17:54');
INSERT INTO `sys_permission` VALUES (1086443521, 1, 'User', 'user', '/sys/user', '用户管理', 'user', 'system:user', NULL, 1, 2, NULL, 0, 'admin', '2022-11-23 10:58:46', 'admin', '2022-11-23 11:00:03');
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色名称',
  `role_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色编码',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '描述',
  `create_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'admin', NULL, '1', '2022-11-13 15:54:28', NULL, NULL);
INSERT INTO `sys_role` VALUES (2, '用户', 'user', NULL, 'admin', '2022-11-22 14:00:30', 'admin', '2022-11-22 14:17:20');
INSERT INTO `sys_role` VALUES (5, 'test', 'test', NULL, 'admin', '2022-12-23 11:28:25', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `role_id` int unsigned NOT NULL COMMENT '角色id',
  `permission_id` int unsigned NOT NULL COMMENT '权限id',
  PRIMARY KEY (`role_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_permission` VALUES (1, 1);
INSERT INTO `sys_role_permission` VALUES (1, 1086443521);
INSERT INTO `sys_role_permission` VALUES (2, 1);
INSERT INTO `sys_role_permission` VALUES (2, 1086443521);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '姓名',
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '密码',
  `gender` tinyint(1) DEFAULT NULL COMMENT '性别 1男 2女 3保密',
  `avatar` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '头像',
  `phone` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '电话',
  `email` varchar(45) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱',
  `del_flag` tinyint unsigned DEFAULT '0' COMMENT '删除状态 0未删除 1已删除',
  `create_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (1, 'admin', 'admin', '$2a$10$9T5lMR2LU2JmqwPgFw7ncucuOGjoFDQnHgeBA7eH8lMbOvSrYKPEu', 1, NULL, '123122', NULL, 0, NULL, '2022-11-17 10:36:47', 'admin', '2022-12-23 16:28:52');
INSERT INTO `sys_user` VALUES (2, 'user', '用户1', '$2a$10$zdEe52kXo4y/qbYyd.5FHeZEcTA5oTVLThxJG0bP4LPXjR5R9LeTO', 1, NULL, '1323', NULL, 0, NULL, NULL, 'admin', '2023-01-13 11:33:41');
INSERT INTO `sys_user` VALUES (11, 'user1', '用户1', '$2a$10$fT35Qd1iP.kL37qlgksunec1riFBPyw0/zzd.CCh9QSnSTTdqzpA2', NULL, NULL, '13328928292', NULL, 0, 'admin', '2022-12-14 13:23:23', 'admin', '2023-01-13 11:33:45');
INSERT INTO `sys_user` VALUES (12, 'test', 'test', '$2a$10$obLGOeSq.yojUUJ5KGRJwesiTaqoW8f8tFXHLI7t2ffb34CDFAiJ.', 1, NULL, '133', NULL, 0, 'admin', '2022-12-23 11:28:51', 'admin', '2022-12-23 11:30:26');
INSERT INTO `sys_user` VALUES (13, '11', '11', '$2a$10$pVM/7yuUU7QXrjWmnzIXDuGM6QhQCoyncO.XKTiullBCurhta4QhW', NULL, NULL, '11', NULL, 0, 'admin', '2022-12-23 16:02:36', 'admin', '2022-12-23 16:28:55');
INSERT INTO `sys_user` VALUES (14, '22', '22', '$2a$10$/nY4zUq0dVuDA3NiZkmvl.Kz2Zza0mMYnWWfM6NXJ.dNUvPeTo4UW', NULL, NULL, '22', NULL, 0, 'admin', '2022-12-23 16:29:05', 'admin', '2022-12-23 16:34:38');
INSERT INTO `sys_user` VALUES (15, '111', '111', '$2a$10$Ml8CoWLDgVrvH6exWreMN.jRtNTPmMomhgWLcXdYYDl3hr/ace7aK', NULL, NULL, '111', NULL, 0, 'admin', '2022-12-23 16:38:44', NULL, NULL);
INSERT INTO `sys_user` VALUES (16, '222', '222', '$2a$10$MU3vT9x/cpDJTQHxnL4YoeR5jb7ya6ZQTu2uFt00ixnTfXFu11Rgy', NULL, NULL, '222', NULL, 0, 'admin', '2022-12-23 16:42:16', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` int unsigned NOT NULL COMMENT '用户id',
  `role_id` int unsigned NOT NULL COMMENT '角色id',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (2, 2);
INSERT INTO `sys_user_role` VALUES (12, 5);
INSERT INTO `sys_user_role` VALUES (14, 2);
INSERT INTO `sys_user_role` VALUES (16, 2);
COMMIT;

-- ----------------------------
-- Table structure for tb_dict
-- ----------------------------
DROP TABLE IF EXISTS `tb_dict`;
CREATE TABLE `tb_dict` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` int unsigned DEFAULT '0' COMMENT '父id',
  `name` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '名称',
  `dict_code` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '编码',
  `dict_value` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '值',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='字典表';

-- ----------------------------
-- Records of tb_dict
-- ----------------------------
BEGIN;
INSERT INTO `tb_dict` VALUES (1, 0, '系统参数', '', '', 'system', '2022-12-23 14:03:15', NULL, NULL);
INSERT INTO `tb_dict` VALUES (2, 1, '初始密码', 'init_password', 'asdf.1234', 'system', '2022-12-23 14:10:08', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for tb_password_group
-- ----------------------------
DROP TABLE IF EXISTS `tb_password_group`;
CREATE TABLE `tb_password_group` (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分组名称',
  `create_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='密码分组';

-- ----------------------------
-- Records of tb_password_group
-- ----------------------------
BEGIN;
INSERT INTO `tb_password_group` VALUES (1604748096133160962, '默认组', 'admin', '2022-12-19 15:58:56', 'admin', '2022-12-19 16:10:45');
INSERT INTO `tb_password_group` VALUES (1604751840408203266, 'test', 'admin', '2022-12-19 16:13:48', NULL, NULL);
INSERT INTO `tb_password_group` VALUES (1605443676118392833, '默认组', 'user', '2022-12-21 14:02:55', NULL, NULL);
INSERT INTO `tb_password_group` VALUES (1605489731276115970, '疫情防控', 'user', '2022-12-21 17:05:55', NULL, NULL);
INSERT INTO `tb_password_group` VALUES (1605492052496347137, '智慧社区', 'user', '2022-12-21 17:15:09', NULL, NULL);
INSERT INTO `tb_password_group` VALUES (1605498619228712961, '公司', 'user', '2022-12-21 17:41:14', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for tb_password_management
-- ----------------------------
DROP TABLE IF EXISTS `tb_password_management`;
CREATE TABLE `tb_password_management` (
  `id` bigint unsigned NOT NULL COMMENT '主键',
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '名称',
  `website` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '站点',
  `username` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户名',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '密码',
  `tip` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '提示',
  `local_storage` tinyint unsigned DEFAULT '0' COMMENT '本地存储 0否 1是',
  `salt` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '盐',
  `custom` tinyint unsigned DEFAULT '0' COMMENT '自定义秘钥 0否 1是',
  `group_id` bigint DEFAULT NULL COMMENT '分组id',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `fill` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '填充',
  `create_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='密码管理表';

-- ----------------------------
-- Records of tb_password_management
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
