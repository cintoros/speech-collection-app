-- new field get added to allow for Image_Text pairs in the DB

ALTER TABLE data_tuple MODIFY COLUMN type
ENUM ('TEXT_TEXT','AUDIO_AUDIO','TEXT_AUDIO','AUDIO_TEXT','IMAGE_AUDIO','RECORDING','IMAGE_TEXT');
