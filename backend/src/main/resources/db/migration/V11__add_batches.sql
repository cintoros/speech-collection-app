CREATE TABLE achievements
(
    id            BIGINT   NOT NULL AUTO_INCREMENT,
    domain_id     BIGINT            DEFAULT NULL,
    name          TEXT              DEFAULT NULL,
    description   TEXT              DEFAULT NULL,
    start_time    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    end_time      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    points_lvl1   BIGINT            DEFAULT NULL,
    points_lvl2   BIGINT            DEFAULT NULL,
    points_lvl3   BIGINT            DEFAULT NULL,
    points_lvl4   BIGINT            DEFAULT NULL,
    depends_on    ENUM ('TEXT_CREATED', 'AUDIO_CREATED', 'IMAGE_CREATED', 'TOTAL_CREATED','TEXT_TEXT_CHECKED','AUDIO_AUDIO_CHECKED','TEXT_AUDIO_CHECKED','AUDIO_TEXT_CHECKED','IMAGE_AUDIO_CHECKED','IMAGE_TEXT_CHECKED', 'TOTAL_CHECKED', 'ALL', 'MANUAL') NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (domain_id) REFERENCES domain (id) ON DELETE CASCADE
) ENGINE = INNODB
  DEFAULT CHARSET = UTF8MB4;

CREATE TABLE user_achievements
(
    id            BIGINT   NOT NULL AUTO_INCREMENT,
    user_id       BIGINT            DEFAULT NULL,
    achievements_id BIGINT          DEFAULT NULL,
    points        BIGINT            DEFAULT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    FOREIGN KEY (achievements_id) REFERENCES achievements (id) ON DELETE CASCADE
) ENGINE = INNODB
  DEFAULT CHARSET = UTF8MB4;
