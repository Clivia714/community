Hello

## 资料
[Spring 文档]
http://elasticsearch.cn/explore

http://spring.io

https://docs.github.com/en/developers/apps/building-oauth-apps

数据库脚本

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
`id` int NOT NULL AUTO_INCREMENT,
`account_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
`name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
`token` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
`gmt_create` date NULL DEFAULT NULL,
`gmt_modified` date NULL DEFAULT NULL,
PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;
