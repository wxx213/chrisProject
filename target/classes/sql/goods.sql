/*
Navicat MySQL Data Transfer

Source Server         : 测试
Source Server Version : 50729
Source Host           : 192.168.1.100:3306
Source Database       : younong

Target Server Type    : MYSQL
Target Server Version : 50729
File Encoding         : 65001

Date: 2020-08-12 18:01:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `goods_name` varchar(16) DEFAULT NULL COMMENT '商品名称',
  `goods_title` varchar(64) DEFAULT NULL COMMENT '商品标题',
  `goods_img` varchar(64) DEFAULT NULL COMMENT '商品图片',
  `goods_detail` longtext COMMENT '商品介绍详情',
  `goods_price` decimal(10,2) DEFAULT '0.00' COMMENT '商品单价',
  `goods_stock` int(11) DEFAULT '0' COMMENT '商品库存，-1表示没有限制',
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES ('1', ' iPhone X', ' 当天发/12分期/送大礼 Apple/苹果 iPhone X移动联通4G手机中移动', '/img/iphone.jpg', ' 当天发/12分期/送大礼 Apple/苹果 iPhone X移动联通4G手机中移动', '7268.00', '1000', '2018-07-12 19:06:20', '2018-07-12 19:06:20');
INSERT INTO `goods` VALUES ('2', 'xiaomi 8', ' 小米8现货【送小米耳机】Xiaomi/小米 小米8手机8plus中移动8se', '/img/xiaomi.jpg', ' 小米8现货【送小米耳机】Xiaomi/小米 小米8手机8plus中移动8se', '2799.00', '0', '2018-07-12 19:06:20', '2018-07-12 19:06:20');
INSERT INTO `goods` VALUES ('3', '荣耀 10', ' 12期分期/honor/荣耀10手机中移动官方旗舰店正品荣耀10手机playv10 plαy', '/img/rongyao.jpg', ' 12期分期/honor/荣耀10手机中移动官方旗舰店正品荣耀10手机playv10 plαy', '2699.00', '1000', '2018-07-12 19:06:20', '2018-07-12 22:32:20');
INSERT INTO `goods` VALUES ('4', 'oppo find x', ' OPPO R15 oppor15手机全新机限量超薄梦境r15梦镜版r11s find x', '/img/oppo.jpg', ' OPPO R15 oppor15手机全新机限量超薄梦境r15梦镜版r11s find x', '4999.00', '1000', '2018-07-12 19:06:20', '2018-07-12 19:06:20');
