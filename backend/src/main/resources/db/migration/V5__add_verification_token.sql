CREATE TABLE verification_token
(
    id           BIGINT                    NOT NULL AUTO_INCREMENT,
    token        TEXT                      NOT NULL,
    token_type   ENUM ('EMAIL','PASSWORD') NOT NULL,
    created_time DATETIME                  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id      BIGINT                    NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE

) ENGINE = INNODB
  DEFAULT CHARSET = UTF8MB4;
