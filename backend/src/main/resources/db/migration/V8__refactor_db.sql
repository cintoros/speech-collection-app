-- refactor database for audio,image,text matching between each other.

-- TODO not sure if it makes sense to migrate or if we want to re-create the database?

-- TODO probably add triggers to update occurrence counts on insert and delete (or join each time for overviews) once the base data structure is clear.
create table labeled_data
(
    id                BIGINT NOT NULL AUTO_INCREMENT,
    is_sentence_error BOOLEAN DEFAULT FALSE,
    is_private        BOOLEAN DEFAULT FALSE,
    correct           BIGINT  DEFAULT 0,
    wrong             BIGINT  DEFAULT 0,
    skipped           BIGINT  DEFAULT 0,
    PRIMARY KEY (id)
) ENGINE = INNODB
  DEFAULT CHARSET = UTF8MB4
    COMMENT 'this table is used to save aggregated checked_states of the audio,text, images and occurrences.';

RENAME TABLE source TO transcript_source;

CREATE TABLE source
(
    id               BIGINT   NOT NULL AUTO_INCREMENT,
    user_id          BIGINT   NOT NULL,
    user_group_id    BIGINT   NOT NULL,
    created_time     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    uploaded         BOOLEAN           DEFAULT FALSE,
    path_to_raw_file TEXT              DEFAULT NULL,
    name             TEXT              DEFAULT NULL,
    licence          TEXT              DEFAULT NULL,
    dialect_id       BIGINT            DEFAULT NULL,
    domain_id        BIGINT            DEFAULT NULL,
    meta_information JSON              DEFAULT NULL COMMENT 'contains meta_information that are different depending on the source etc.',
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    FOREIGN KEY (user_group_id) REFERENCES user_group (id) ON DELETE CASCADE,
    FOREIGN KEY (dialect_id) REFERENCES dialect (id) ON DELETE CASCADE,
    FOREIGN KEY (domain_id) REFERENCES domain (id) ON DELETE CASCADE
) ENGINE = INNODB
  DEFAULT CHARSET = UTF8MB4
    COMMENT 'the source mappings can be uploaded by user and/or auto generated based on something uploaded by the user.
or it can be imported/upload by a script etc this is why the more "advanced" fields are only filled in case it was uploaded';

CREATE TABLE text
(
    id              BIGINT   NOT NULL AUTO_INCREMENT,
    text            TEXT     NOT NULL,
    source_id       BIGINT   NOT NULL,
    language_id     BIGINT   NOT NULL,
    created_time    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    labeled_data_id BIGINT   NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (source_id) REFERENCES source (id) ON DELETE CASCADE,
    FOREIGN KEY (language_id) REFERENCES language (id) ON DELETE CASCADE,
    FOREIGN KEY (labeled_data_id) REFERENCES labeled_data (id) ON DELETE CASCADE

) ENGINE = INNODB
  DEFAULT CHARSET = UTF8MB4;

CREATE TABLE audio
(
    id              BIGINT   NOT NULL AUTO_INCREMENT,
    path            TEXT     NOT NULL,
    source_id       BIGINT   NOT NULL,
    language_id     BIGINT   NOT NULL,
    created_time    DATETIME NOT NULL                               DEFAULT CURRENT_TIMESTAMP,
    quality         ENUM ('INTEGRATED','DEDICATED')                 DEFAULT NULL,
    noise_level     ENUM ('NO_NOISE','MODERATE_NOISE','VERY_NOISY') DEFAULT NULL,
    browser_version TEXT                                            DEFAULT NULL,
    labeled_data_id BIGINT   NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (source_id) REFERENCES source (id) ON DELETE CASCADE,
    FOREIGN KEY (language_id) REFERENCES language (id) ON DELETE CASCADE,
    FOREIGN KEY (labeled_data_id) REFERENCES labeled_data (id) ON DELETE CASCADE

) ENGINE = INNODB
  DEFAULT CHARSET = UTF8MB4;

CREATE TABLE image
(
    id              BIGINT   NOT NULL AUTO_INCREMENT,
    path            TEXT     NOT NULL,
    created_time    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    source_id       BIGINT   NOT NULL,
    labeled_data_id BIGINT   NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (source_id) REFERENCES source (id) ON DELETE CASCADE,
    FOREIGN KEY (labeled_data_id) REFERENCES labeled_data (id) ON DELETE CASCADE
) ENGINE = INNODB
  DEFAULT CHARSET = UTF8MB4;

CREATE TABLE checked_data
(
    id           BIGINT                                      NOT NULL AUTO_INCREMENT,
    created_time DATETIME                                    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    type         enum ('SKIPPED','PRIVATE','SENTENCE_ERROR') NOT NULL,
    user_id      BIGINT                                      NOT NULL,
    text_1       BIGINT                                               DEFAULT NULL,
    image_1      BIGINT                                               DEFAULT NULL,
    audio_1      BIGINT                                               DEFAULT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    FOREIGN KEY (text_1) REFERENCES text (id) ON DELETE CASCADE,
    FOREIGN KEY (audio_1) REFERENCES audio (id) ON DELETE CASCADE,
    FOREIGN KEY (image_1) REFERENCES image (id) ON DELETE CASCADE
) ENGINE = INNODB
  DEFAULT CHARSET = UTF8MB4
    COMMENT ='this table is used to save the label of a text,audio,image by a user so we can revert it in case a user produces nonsense while recording/transcribing';

CREATE TABLE occurrence
(
    id              BIGINT                                                                   NOT NULL AUTO_INCREMENT,
    type            ENUM ('TEXT_TEXT','AUDIO_AUDIO','TEXT_AUDIO','AUDIO_TEXT','IMAGE_AUDIO') NOT NULL,
    text_1          BIGINT DEFAULT NULL,
    text_2          BIGINT DEFAULT NULL,
    audio_1         BIGINT DEFAULT NULL,
    audio_2         BIGINT DEFAULT NULL,
    image_1         BIGINT DEFAULT NULL,
    labeled_data_id BIGINT                                                                   NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (text_1) REFERENCES text (id) ON DELETE CASCADE,
    FOREIGN KEY (text_2) REFERENCES text (id) ON DELETE CASCADE,
    FOREIGN KEY (audio_1) REFERENCES audio (id) ON DELETE CASCADE,
    FOREIGN KEY (audio_2) REFERENCES audio (id) ON DELETE CASCADE,
    FOREIGN KEY (image_1) REFERENCES image (id) ON DELETE CASCADE,
    FOREIGN KEY (labeled_data_id) REFERENCES labeled_data (id) ON DELETE CASCADE
) ENGINE = INNODB
  DEFAULT CHARSET = UTF8MB4
    COMMENT 'this table is used to save the audio,text, images occurrences and the aggregated checked_states.
the type describes which foreign keys are set i.e TEXT_AUDIO(text_1,audio_2),IMAGE_AUDIO(image_1,audio_2)';

CREATE TABLE checked_occurrence
(
    id            BIGINT                                                        NOT NULL AUTO_INCREMENT,
    type          enum ('SKIPPED','CORRECT','WRONG','PRIVATE','SENTENCE_ERROR') NOT NULL,
    created_time  DATETIME                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id       BIGINT                                                        NOT NULL,
    occurrence_id BIGINT                                                        NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    FOREIGN KEY (occurrence_id) REFERENCES occurrence (id) ON DELETE CASCADE
) ENGINE = INNODB
  DEFAULT CHARSET = UTF8MB4
    COMMENT 'this table is used to save the label of a occurrence by a user so we can revert it in case a user produces nonsense';

ALTER TABLE user_group
    ADD COLUMN meta_information JSON DEFAULT NULL COMMENT 'contains meta_information. for example which features are activated per user_group';

# TODO maybe implement data migration here before dropping the old tables

DROP TABLE checked_recording,checked_text_audio;
DROP TABLE text_audio,recording;
DROP TABLE transcript_source,excerpt;
DROP TABLE speaker,original_text;
