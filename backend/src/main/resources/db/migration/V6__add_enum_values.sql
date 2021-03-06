ALTER TABLE checked_recording
    MODIFY COLUMN label ENUM ('SKIPPED', 'CORRECT', 'WRONG','PRIVATE','SENTENCE_ERROR') DEFAULT NULL;
ALTER TABLE checked_text_audio
    MODIFY COLUMN label ENUM ('SKIPPED', 'CORRECT', 'WRONG','PRIVATE','SENTENCE_ERROR') DEFAULT NULL;
ALTER TABLE excerpt
    CHANGE COLUMN isPrivate is_private BOOLEAN DEFAULT FALSE,
    CHANGE COLUMN isSkipped is_skipped INT     DEFAULT 0;
ALTER TABLE text_audio
    ADD COLUMN is_sentence_error BOOLEAN DEFAULT FALSE,
    ADD COLUMN is_private        BOOLEAN DEFAULT FALSE;
