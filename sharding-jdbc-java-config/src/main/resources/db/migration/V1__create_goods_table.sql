CREATE TABLE `goods`
(
    `id`           BIGINT       NOT NULL AUTO_INCREMENT,
    `name`         VARCHAR(255) NOT NULL default '',
    `category`     INT          NOT NULL DEFAULT -1,
    `created_time` TIMESTAMP    NOT NULL DEFAULT current_timestamp,
    `updated_time` TIMESTAMP    NOT NULL DEFAULT current_timestamp on update current_timestamp,
    PRIMARY KEY (`id`)
);