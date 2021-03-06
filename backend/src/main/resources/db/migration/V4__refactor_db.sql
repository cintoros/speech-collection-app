# refactor tables
ALTER TABLE user
    MODIFY COLUMN first_name VARCHAR(100) DEFAULT NULL,
    MODIFY COLUMN last_name VARCHAR(100) DEFAULT NULL,
    MODIFY COLUMN licence ENUM ('PUBLIC','ACADEMIC') DEFAULT 'PUBLIC',
    ADD COLUMN not_CH   BOOLEAN     DEFAULT FALSE,
    ADD COLUMN zip_code VARCHAR(45) DEFAULT NULL;
ALTER TABLE source
    ADD COLUMN licence TEXT DEFAULT '';
ALTER TABLE original_text
    ADD COLUMN licence TEXT DEFAULT '';
ALTER TABLE recording
    ADD COLUMN deleted     DATETIME                                        DEFAULT NULL,
    ADD COLUMN quality     ENUM ('INTEGRATED','DEDICATED')                 DEFAULT 'INTEGRATED',
    ADD COLUMN noise_level ENUM ('NO_NOISE','MODERATE_NOISE','VERY_NOISY') DEFAULT 'NO_NOISE';
ALTER TABLE text_audio
    ADD COLUMN deleted DATETIME DEFAULT NULL;
ALTER TABLE user_group
    ADD COLUMN description MEDIUMTEXT DEFAULT NULL;
# set missing charset to UTF8MB4
ALTER TABLE excerpt
    CONVERT TO CHARACTER SET UTF8MB4;
ALTER TABLE source
    CONVERT TO CHARACTER SET UTF8MB4;
ALTER TABLE original_text
    CONVERT TO CHARACTER SET UTF8MB4;
ALTER TABLE domain
    CONVERT TO CHARACTER SET UTF8MB4;
