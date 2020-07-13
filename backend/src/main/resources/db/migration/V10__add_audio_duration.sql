-- add audio duration
ALTER TABLE audio
    ADD COLUMN duration FLOAT DEFAULT 0 COMMENT 'audio duration in seconds';
