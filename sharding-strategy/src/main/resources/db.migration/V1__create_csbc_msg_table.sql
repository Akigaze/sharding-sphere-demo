-- csbc_msg_bc_0, csbc_msg_bl_0 ...
CREATE TABLE `csbc_msg`
(
    `id`             bigint       NOT NULL,
    `business_key`   varchar(100) NOT NULL DEFAULT '',
    `type`           varchar(20)  NOT NULL DEFAULT 'NONE',
    `event_id`       varchar(100)          DEFAULT NULL,
    `asset_id`       varchar(100)          DEFAULT NULL,
    `sender`         varchar(100)          DEFAULT NULL,
    `receiver`       varchar(100)          DEFAULT NULL,
    `receiver_roles` varchar(100)          DEFAULT NULL,
    `version`        int                   DEFAULT NULL,
    `content`        longtext,
    `exception_log`  longtext,
    `process_state`  varchar(20)           DEFAULT 'NONE',
    `created_time` timestamp    NOT NULL DEFAULT current_timestamp,
    `updated_time` timestamp    NOT NULL DEFAULT current_timestamp on update current_timestamp,
    PRIMARY KEY (`id`)
)