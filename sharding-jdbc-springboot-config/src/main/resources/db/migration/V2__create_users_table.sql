CREATE TABLE `users`
(
    `id`           BIGINT       NOT NULL AUTO_INCREMENT,
    `username`     VARCHAR(255) NOT NULL,
    `password`     VARCHAR(255) NOT NULL,
    `deleted`      TINYINT      NOT NULL DEFAULT 0,
    `created_time` TIMESTAMP    NOT NULL DEFAULT current_timestamp,
    `updated_time` TIMESTAMP    NOT NULL DEFAULT current_timestamp on update current_timestamp,
    PRIMARY KEY (`id`)
);