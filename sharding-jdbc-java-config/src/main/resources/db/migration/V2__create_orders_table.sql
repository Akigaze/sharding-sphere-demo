CREATE TABLE `orders`
(
    `id`           BIGINT    NOT NULL AUTO_INCREMENT,
    `user_id`      BIGINT    NOT NULL,
    `good_id`      BIGINT    NOT NULL,
    `size`         INT       NOT NULL DEFAULT -1,
    `created_time` TIMESTAMP NOT NULL DEFAULT current_timestamp,
    `updated_time` TIMESTAMP NOT NULL DEFAULT current_timestamp on update current_timestamp,
    PRIMARY KEY (`id`)
);