-- refactor database for audio,image,text matching between each other.

-- TODO not sure if it makes sense to migrate or if we want to re-create the database?
DROP TABLE checked_recording,checked_text_audio;
DROP TABLE text_audio,recording;
DROP TABLE source,excerpt;
DROP TABLE speaker,original_text;

-- TODO probably add triggers to update occurrence counts on insert and delete (or join each time for overviews) once the base data structure is clear.
create table checked_data
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
    COMMENT 'this table is used to save aggregated checked_states of an element (audio,text,images) and occurrences.';

CREATE TABLE source
(
    id               BIGINT   NOT NULL AUTO_INCREMENT,
    user_id          BIGINT   NOT NULL,
    user_group_id    BIGINT   NOT NULL,
    created_time     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
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
    COMMENT 'the source mappings can be generated based on something uploaded by the user.
Or it can be imported/upload by a script etc this is why the more "advanced" fields are only filled in case it was uploaded';

CREATE TABLE excerpt
(
    id         BIGINT NOT NULL AUTO_INCREMENT,
    text       TEXT   NOT NULL,
    dialect_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (dialect_id) REFERENCES dialect (id) ON DELETE CASCADE
) ENGINE = INNODB
  DEFAULT CHARSET = UTF8MB4;

CREATE TABLE audio
(
    id              BIGINT NOT NULL AUTO_INCREMENT,
    path            TEXT   NOT NULL,
    dialect_id      BIGINT NOT NULL,
    quality         ENUM ('INTEGRATED','DEDICATED')                 DEFAULT NULL,
    noise_level     ENUM ('NO_NOISE','MODERATE_NOISE','VERY_NOISY') DEFAULT NULL,
    browser_version TEXT                                            DEFAULT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (dialect_id) REFERENCES dialect (id) ON DELETE CASCADE
) ENGINE = INNODB
  DEFAULT CHARSET = UTF8MB4;

CREATE TABLE image
(
    id      BIGINT NOT NULL AUTO_INCREMENT,
    path    TEXT   NOT NULL,
    licence TEXT DEFAULT NULL,
    PRIMARY KEY (id)
) ENGINE = INNODB
  DEFAULT CHARSET = UTF8MB4;

CREATE TABLE element
(
    id              BIGINT   NOT NULL AUTO_INCREMENT,
    excerpt_id      BIGINT            DEFAULT NULL,
    audio_id        BIGINT            DEFAULT NULL,
    image_id        BIGINT            DEFAULT NULL,
    created_time    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    source_id       BIGINT            DEFAULT NULL,
    user_id         BIGINT            DEFAULT NULL,
    checked_data_id BIGINT   NOT NULL,
    finished        BOOLEAN           DEFAULT FALSE,
    PRIMARY KEY (id),
    FOREIGN KEY (source_id) REFERENCES source (id) ON DELETE CASCADE,
    FOREIGN KEY (excerpt_id) REFERENCES excerpt (id) ON DELETE CASCADE,
    FOREIGN KEY (audio_id) REFERENCES audio (id) ON DELETE CASCADE,
    FOREIGN KEY (image_id) REFERENCES image (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    FOREIGN KEY (checked_data_id) REFERENCES checked_data (id) ON DELETE CASCADE
) ENGINE = INNODB
  DEFAULT CHARSET = UTF8MB4 COMMENT 'the element can be an audio,text or image.
In case of text & audio it can be uploaded normally or generated using a source i.e. transcript, text-document
The finished flag is used to prevent cycles like excerpt=>recording=>transcript=>recording=>transcript...
It can also be used to flag an element that already has enough transcripts/recordings';

CREATE TABLE occurrence
(
    id              BIGINT                                                                   NOT NULL AUTO_INCREMENT,
    type            ENUM ('TEXT_TEXT','AUDIO_AUDIO','TEXT_AUDIO','AUDIO_TEXT','IMAGE_AUDIO') NOT NULL,
    element_id_1    BIGINT                                                                   NOT NULL,
    element_id_2    BIGINT                                                                   NOT NULL,
    checked_data_id BIGINT                                                                   NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (element_id_1) REFERENCES element (id) ON DELETE CASCADE,
    FOREIGN KEY (element_id_2) REFERENCES element (id) ON DELETE CASCADE,
    FOREIGN KEY (checked_data_id) REFERENCES checked_data (id) ON DELETE CASCADE
) ENGINE = INNODB
  DEFAULT CHARSET = UTF8MB4
    COMMENT 'this table is used to save the audio,text, images occurrences.
the type describes which foreign keys are set i.e TEXT_AUDIO(text_1,audio_2),IMAGE_AUDIO(image_1,audio_2)';

CREATE TABLE checked_data_user
(
    id              BIGINT                                                        NOT NULL AUTO_INCREMENT,
    type            enum ('SKIPPED','CORRECT','WRONG','PRIVATE','SENTENCE_ERROR') NOT NULL,
    created_time    DATETIME                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id         BIGINT                                                        NOT NULL,
    checked_data_id BIGINT                                                        NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    FOREIGN KEY (checked_data_id) REFERENCES checked_data (id) ON DELETE CASCADE
) ENGINE = INNODB
  DEFAULT CHARSET = UTF8MB4
    COMMENT 'this table is used to save the label of a occurrence by a user so we can revert it in case a user produces nonsense';

ALTER TABLE user_group
    ADD COLUMN meta_information JSON DEFAULT NULL COMMENT 'contains meta_information. for example which features are activated per user_group';
