-- refactor database for audio,image,text matching between each other.

-- old recordings etc. are not migrated as they only contain a small amount of test data - that can be re-imported/generated
DROP TABLE checked_recording,checked_text_audio;
DROP TABLE text_audio,recording;
DROP TABLE source,excerpt;
DROP TABLE speaker,original_text;

ALTER TABLE user_group
    ADD COLUMN meta_information JSON DEFAULT NULL COMMENT 'contains meta_information. for example which features are activated per user_group';
ALTER TABLE user
    ADD COLUMN last_online DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP;

INSERT INTO dialect(county_id, county_name, language_id) VALUE ('DE-de', 'Standard German', 2);

CREATE TABLE source
(
    id               BIGINT   NOT NULL AUTO_INCREMENT,
    user_id          BIGINT   NOT NULL,
    dialect_id       BIGINT            DEFAULT NULL,
    domain_id        BIGINT            DEFAULT NULL,
    user_group_id    BIGINT   NOT NULL,
    created_time     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    path_to_raw_file TEXT              DEFAULT NULL,
    name             TEXT              DEFAULT NULL,
    licence          TEXT              DEFAULT NULL,
    meta_information JSON              DEFAULT NULL COMMENT 'contains meta_information that are different depending on the source etc.',
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    FOREIGN KEY (dialect_id) REFERENCES dialect (id) ON DELETE CASCADE,
    FOREIGN KEY (domain_id) REFERENCES domain (id) ON DELETE CASCADE,
    FOREIGN KEY (user_group_id) REFERENCES user_group (id) ON DELETE CASCADE
) ENGINE = INNODB
  DEFAULT CHARSET = UTF8MB4
    COMMENT 'a source can be generated based on something uploaded by the user i.e a text document.
Or it can be imported by a script this is why most fields are optional.';

CREATE TABLE data_element
(
    id            BIGINT   NOT NULL AUTO_INCREMENT,
    source_id     BIGINT            DEFAULT NULL,
    user_id       BIGINT            DEFAULT NULL,
    user_group_id BIGINT   NOT NULL,
    created_time  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    finished      BOOLEAN           DEFAULT FALSE,
    is_private    BOOLEAN           DEFAULT FALSE,
    skipped       BIGINT            DEFAULT 0,
    PRIMARY KEY (id),
    FOREIGN KEY (source_id) REFERENCES source (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    FOREIGN KEY (user_group_id) REFERENCES user_group (id) ON DELETE CASCADE
) ENGINE = INNODB
  DEFAULT CHARSET = UTF8MB4 COMMENT 'the data_element can be an audio,text or image.
In case of text & audio it can be uploaded normally or generated using a source i.e. transcript, text-document
The finished flag is used to prevent cycles like text=>recording=>text=>recording=>...';

CREATE TABLE text
(
    id                BIGINT NOT NULL AUTO_INCREMENT,
    dialect_id        BIGINT NOT NULL,
    data_element_id   BIGINT NOT NULL,
    is_sentence_error BOOLEAN DEFAULT FALSE,
    text              TEXT   NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (dialect_id) REFERENCES dialect (id) ON DELETE CASCADE,
    FOREIGN KEY (data_element_id) REFERENCES data_element (id) ON DELETE CASCADE
) ENGINE = INNODB
  DEFAULT CHARSET = UTF8MB4;

CREATE TABLE audio
(
    id              BIGINT NOT NULL AUTO_INCREMENT,
    dialect_id      BIGINT NOT NULL,
    data_element_id BIGINT NOT NULL,
    path            TEXT   NOT NULL,
    quality         ENUM ('INTEGRATED','DEDICATED')                 DEFAULT NULL,
    noise_level     ENUM ('NO_NOISE','MODERATE_NOISE','VERY_NOISY') DEFAULT NULL,
    browser_version TEXT                                            DEFAULT NULL,
    audio_start     FLOAT                                           DEFAULT NULL,
    audio_end       FLOAT                                           DEFAULT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (dialect_id) REFERENCES dialect (id) ON DELETE CASCADE,
    FOREIGN KEY (data_element_id) REFERENCES data_element (id) ON DELETE CASCADE
) ENGINE = INNODB
  DEFAULT CHARSET = UTF8MB4 COMMENT 'the audio can be an recording or a extract from a transcipt/audiofile .
depending on this we have different meta data';

CREATE TABLE image
(
    id              BIGINT NOT NULL AUTO_INCREMENT,
    data_element_id BIGINT NOT NULL,
    path            TEXT   NOT NULL,
    licence         TEXT DEFAULT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (data_element_id) REFERENCES data_element (id) ON DELETE CASCADE
) ENGINE = INNODB
  DEFAULT CHARSET = UTF8MB4;

CREATE TABLE data_tuple
(
    id                BIGINT                                                                               NOT NULL AUTO_INCREMENT,
    data_element_id_1 BIGINT                                                                               NOT NULL,
    data_element_id_2 BIGINT                                                                               NOT NULL,
    type              ENUM ('TEXT_TEXT','AUDIO_AUDIO','TEXT_AUDIO','AUDIO_TEXT','IMAGE_AUDIO','RECORDING') NOT NULL,
    finished          BOOLEAN DEFAULT FALSE,
    correct           BIGINT  DEFAULT 0,
    wrong             BIGINT  DEFAULT 0,
    skipped           BIGINT  DEFAULT 0,
    PRIMARY KEY (id),
    FOREIGN KEY (data_element_id_1) REFERENCES data_element (id) ON DELETE CASCADE,
    FOREIGN KEY (data_element_id_2) REFERENCES data_element (id) ON DELETE CASCADE
) ENGINE = INNODB
  DEFAULT CHARSET = UTF8MB4
    COMMENT 'this table is used to save the audio,text, images data_tuples.
the type describes which foreign keys are set i.e TEXT_AUDIO(text_1,audio_2),IMAGE_AUDIO(image_1,audio_2).
the finished can be used to flag an data_element should not be checked at all.';

CREATE TABLE checked_data_element
(
    id              BIGINT                                      NOT NULL AUTO_INCREMENT,
    user_id         BIGINT                                      NOT NULL,
    data_element_id BIGINT                                      NOT NULL,
    type            ENUM ('SKIPPED','PRIVATE','SENTENCE_ERROR') NOT NULL,
    created_time    DATETIME                                    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    FOREIGN KEY (data_element_id) REFERENCES data_element (id) ON DELETE CASCADE
) ENGINE = INNODB
  DEFAULT CHARSET = UTF8MB4
    COMMENT 'this table is used to save the label of a data_element by a user so we can revert it in case a user produces nonsense';

CREATE TABLE checked_data_tuple
(
    id            BIGINT                             NOT NULL AUTO_INCREMENT,
    user_id       BIGINT                             NOT NULL,
    data_tuple_id BIGINT                             NOT NULL,
    type          ENUM ('SKIPPED','CORRECT','WRONG') NOT NULL,
    created_time  DATETIME                           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    FOREIGN KEY (data_tuple_id) REFERENCES data_tuple (id) ON DELETE CASCADE
) ENGINE = INNODB
  DEFAULT CHARSET = UTF8MB4
    COMMENT 'this table is used to save the label of a data_tuple by a user so we can revert it in case a user produces nonsense';

DELIMITER //

CREATE TRIGGER checked_data_element_insert_trigger
    AFTER INSERT
    ON checked_data_element
    FOR EACH ROW
BEGIN
    IF NEW.type = 'SKIPPED' THEN
        UPDATE data_element
        SET data_element.skipped = data_element.skipped + 1
        WHERE data_element.id = NEW.data_element_id;
    elseif NEW.type = 'SENTENCE_ERROR' THEN
        UPDATE text
        SET text.is_sentence_error = true
        WHERE text.data_element_id = NEW.data_element_id;
    elseif NEW.type = 'PRIVATE' THEN
        UPDATE data_element
        SET data_element.is_private = true
        WHERE data_element.id = NEW.data_element_id;
    END IF;
END;
//

CREATE TRIGGER checked_data_element_delete_trigger
    AFTER DELETE
    ON checked_data_element
    FOR EACH ROW
BEGIN
    IF OLD.type = 'SKIPPED' THEN
        UPDATE data_element
        SET data_element.skipped = data_element.skipped - 1
        WHERE data_element.id = OLD.data_element_id;
    elseif OLD.type = 'SENTENCE_ERROR' THEN
        UPDATE text
        SET text.is_sentence_error = false
        WHERE text.data_element_id = OLD.data_element_id;
    elseif OLD.type = 'PRIVATE' THEN
        UPDATE data_element
        SET data_element.is_private = false
        WHERE data_element.id = OLD.data_element_id;
    END IF;
END;
//


CREATE TRIGGER checked_data_tuple_insert_trigger
    AFTER INSERT
    ON checked_data_tuple
    FOR EACH ROW
BEGIN
    IF NEW.type = 'CORRECT' THEN
        UPDATE data_tuple
        SET data_tuple.correct = data_tuple.correct + 1
        WHERE data_tuple.id = NEW.data_tuple_id;
    elseif NEW.type = 'WRONG' THEN
        UPDATE data_tuple
        SET data_tuple.wrong = data_tuple.wrong + 1
        WHERE data_tuple.id = NEW.data_tuple_id;
    elseif NEW.type = 'SKIPPED' THEN
        UPDATE data_tuple
        SET data_tuple.skipped = data_tuple.skipped + 1
        WHERE data_tuple.id = NEW.data_tuple_id;
    END IF;
END;
//

CREATE TRIGGER checked_data_tuple_delete_trigger
    AFTER DELETE
    ON checked_data_tuple
    FOR EACH ROW
BEGIN
    IF OLD.type = 'CORRECT' THEN
        UPDATE data_tuple
        SET data_tuple.correct = data_tuple.correct - 1
        WHERE data_tuple.id = OLD.data_tuple_id;
    elseif OLD.type = 'WRONG' THEN
        UPDATE data_tuple
        SET data_tuple.wrong = data_tuple.wrong - 1
        WHERE data_tuple.id = OLD.data_tuple_id;
    elseif OLD.type = 'SKIPPED' THEN
        UPDATE data_tuple
        SET data_tuple.skipped = data_tuple.skipped - 1
        WHERE data_tuple.id = OLD.data_tuple_id;
    END IF;
END;
//

DELIMITER ;
