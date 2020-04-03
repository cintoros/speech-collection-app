/*
 * This file is generated by jOOQ.
 */
package ch.fhnw.speech_collection_app.jooq.tables.daos;


import ch.fhnw.speech_collection_app.jooq.enums.CheckedTextAudioLabel;
import ch.fhnw.speech_collection_app.jooq.tables.CheckedTextAudio;
import ch.fhnw.speech_collection_app.jooq.tables.records.CheckedTextAudioRecord;

import java.sql.Timestamp;
import java.util.List;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Repository
public class CheckedTextAudioDao extends DAOImpl<CheckedTextAudioRecord, ch.fhnw.speech_collection_app.jooq.tables.pojos.CheckedTextAudio, Long> {

    public CheckedTextAudioDao() {
        super(CheckedTextAudio.CHECKED_TEXT_AUDIO, ch.fhnw.speech_collection_app.jooq.tables.pojos.CheckedTextAudio.class);
    }

    @Autowired
    public CheckedTextAudioDao(Configuration configuration) {
        super(CheckedTextAudio.CHECKED_TEXT_AUDIO, ch.fhnw.speech_collection_app.jooq.tables.pojos.CheckedTextAudio.class, configuration);
    }

    @Override
    public Long getId(ch.fhnw.speech_collection_app.jooq.tables.pojos.CheckedTextAudio object) {
        return object.getId();
    }

    public List<ch.fhnw.speech_collection_app.jooq.tables.pojos.CheckedTextAudio> fetchRangeOfId(Long lowerInclusive, Long upperInclusive) {
        return fetchRange(CheckedTextAudio.CHECKED_TEXT_AUDIO.ID, lowerInclusive, upperInclusive);
    }

    public List<ch.fhnw.speech_collection_app.jooq.tables.pojos.CheckedTextAudio> fetchById(Long... values) {
        return fetch(CheckedTextAudio.CHECKED_TEXT_AUDIO.ID, values);
    }

    public ch.fhnw.speech_collection_app.jooq.tables.pojos.CheckedTextAudio fetchOneById(Long value) {
        return fetchOne(CheckedTextAudio.CHECKED_TEXT_AUDIO.ID, value);
    }

    public List<ch.fhnw.speech_collection_app.jooq.tables.pojos.CheckedTextAudio> fetchRangeOfTextAudioId(Long lowerInclusive, Long upperInclusive) {
        return fetchRange(CheckedTextAudio.CHECKED_TEXT_AUDIO.TEXT_AUDIO_ID, lowerInclusive, upperInclusive);
    }

    public List<ch.fhnw.speech_collection_app.jooq.tables.pojos.CheckedTextAudio> fetchByTextAudioId(Long... values) {
        return fetch(CheckedTextAudio.CHECKED_TEXT_AUDIO.TEXT_AUDIO_ID, values);
    }

    public List<ch.fhnw.speech_collection_app.jooq.tables.pojos.CheckedTextAudio> fetchRangeOfUserId(Long lowerInclusive, Long upperInclusive) {
        return fetchRange(CheckedTextAudio.CHECKED_TEXT_AUDIO.USER_ID, lowerInclusive, upperInclusive);
    }

    public List<ch.fhnw.speech_collection_app.jooq.tables.pojos.CheckedTextAudio> fetchByUserId(Long... values) {
        return fetch(CheckedTextAudio.CHECKED_TEXT_AUDIO.USER_ID, values);
    }

    public List<ch.fhnw.speech_collection_app.jooq.tables.pojos.CheckedTextAudio> fetchRangeOfLabel(CheckedTextAudioLabel lowerInclusive, CheckedTextAudioLabel upperInclusive) {
        return fetchRange(CheckedTextAudio.CHECKED_TEXT_AUDIO.LABEL, lowerInclusive, upperInclusive);
    }

    public List<ch.fhnw.speech_collection_app.jooq.tables.pojos.CheckedTextAudio> fetchByLabel(CheckedTextAudioLabel... values) {
        return fetch(CheckedTextAudio.CHECKED_TEXT_AUDIO.LABEL, values);
    }

    public List<ch.fhnw.speech_collection_app.jooq.tables.pojos.CheckedTextAudio> fetchRangeOfTime(Timestamp lowerInclusive, Timestamp upperInclusive) {
        return fetchRange(CheckedTextAudio.CHECKED_TEXT_AUDIO.TIME, lowerInclusive, upperInclusive);
    }

    public List<ch.fhnw.speech_collection_app.jooq.tables.pojos.CheckedTextAudio> fetchByTime(Timestamp... values) {
        return fetch(CheckedTextAudio.CHECKED_TEXT_AUDIO.TIME, values);
    }
}
