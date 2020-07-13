ALTER TABLE user
    ADD gamification_on BOOLEAN DEFAULT TRUE;

ALTER TABLE user_achievements
    ADD is_new BOOLEAN DEFAULT TRUE;

ALTER TABLE achievements
    ADD is_visible BOOLEAN DEFAULT TRUE;
