-- csbc_msg_process_0, csbc_msg_process_yy_M_d_H
CREATE TABLE `csbc_msg_process`
(
    `id`            bigint       NOT NULL,
    `business_key`  varchar(100) NOT NULL DEFAULT '',
    `csbc_id`       bigint       NOT NULL,
    `type`          varchar(20)  NOT NULL DEFAULT 'NONE',
    `event_id`      varchar(100)          DEFAULT NULL,
    `asset_id`      varchar(100)          DEFAULT NULL,
    `version`       int                   DEFAULT NULL,
    `process_state` varchar(20)           DEFAULT 'NONE',
    `exceptionLog`  varchar(255)          DEFAULT 'NONE',
    `created_time`  timestamp    NOT NULL DEFAULT current_timestamp,
    `updated_time`  timestamp    NOT NULL DEFAULT current_timestamp on update current_timestamp,
    PRIMARY KEY (`id`)
)