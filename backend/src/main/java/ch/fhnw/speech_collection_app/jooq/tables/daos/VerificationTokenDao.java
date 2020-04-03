/*
 * This file is generated by jOOQ.
 */
package ch.fhnw.speech_collection_app.jooq.tables.daos;


import ch.fhnw.speech_collection_app.jooq.enums.VerificationTokenTokenType;
import ch.fhnw.speech_collection_app.jooq.tables.VerificationToken;
import ch.fhnw.speech_collection_app.jooq.tables.records.VerificationTokenRecord;

import java.sql.Timestamp;
import java.util.List;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Repository
public class VerificationTokenDao extends DAOImpl<VerificationTokenRecord, ch.fhnw.speech_collection_app.jooq.tables.pojos.VerificationToken, Long> {

    public VerificationTokenDao() {
        super(VerificationToken.VERIFICATION_TOKEN, ch.fhnw.speech_collection_app.jooq.tables.pojos.VerificationToken.class);
    }

    @Autowired
    public VerificationTokenDao(Configuration configuration) {
        super(VerificationToken.VERIFICATION_TOKEN, ch.fhnw.speech_collection_app.jooq.tables.pojos.VerificationToken.class, configuration);
    }

    @Override
    public Long getId(ch.fhnw.speech_collection_app.jooq.tables.pojos.VerificationToken object) {
        return object.getId();
    }

    public List<ch.fhnw.speech_collection_app.jooq.tables.pojos.VerificationToken> fetchRangeOfId(Long lowerInclusive, Long upperInclusive) {
        return fetchRange(VerificationToken.VERIFICATION_TOKEN.ID, lowerInclusive, upperInclusive);
    }

    public List<ch.fhnw.speech_collection_app.jooq.tables.pojos.VerificationToken> fetchById(Long... values) {
        return fetch(VerificationToken.VERIFICATION_TOKEN.ID, values);
    }

    public ch.fhnw.speech_collection_app.jooq.tables.pojos.VerificationToken fetchOneById(Long value) {
        return fetchOne(VerificationToken.VERIFICATION_TOKEN.ID, value);
    }

    public List<ch.fhnw.speech_collection_app.jooq.tables.pojos.VerificationToken> fetchRangeOfToken(String lowerInclusive, String upperInclusive) {
        return fetchRange(VerificationToken.VERIFICATION_TOKEN.TOKEN, lowerInclusive, upperInclusive);
    }

    public List<ch.fhnw.speech_collection_app.jooq.tables.pojos.VerificationToken> fetchByToken(String... values) {
        return fetch(VerificationToken.VERIFICATION_TOKEN.TOKEN, values);
    }

    public List<ch.fhnw.speech_collection_app.jooq.tables.pojos.VerificationToken> fetchRangeOfTokenType(VerificationTokenTokenType lowerInclusive, VerificationTokenTokenType upperInclusive) {
        return fetchRange(VerificationToken.VERIFICATION_TOKEN.TOKEN_TYPE, lowerInclusive, upperInclusive);
    }

    public List<ch.fhnw.speech_collection_app.jooq.tables.pojos.VerificationToken> fetchByTokenType(VerificationTokenTokenType... values) {
        return fetch(VerificationToken.VERIFICATION_TOKEN.TOKEN_TYPE, values);
    }

    public List<ch.fhnw.speech_collection_app.jooq.tables.pojos.VerificationToken> fetchRangeOfCreatedTime(Timestamp lowerInclusive, Timestamp upperInclusive) {
        return fetchRange(VerificationToken.VERIFICATION_TOKEN.CREATED_TIME, lowerInclusive, upperInclusive);
    }

    public List<ch.fhnw.speech_collection_app.jooq.tables.pojos.VerificationToken> fetchByCreatedTime(Timestamp... values) {
        return fetch(VerificationToken.VERIFICATION_TOKEN.CREATED_TIME, values);
    }

    public List<ch.fhnw.speech_collection_app.jooq.tables.pojos.VerificationToken> fetchRangeOfUserId(Long lowerInclusive, Long upperInclusive) {
        return fetchRange(VerificationToken.VERIFICATION_TOKEN.USER_ID, lowerInclusive, upperInclusive);
    }

    public List<ch.fhnw.speech_collection_app.jooq.tables.pojos.VerificationToken> fetchByUserId(Long... values) {
        return fetch(VerificationToken.VERIFICATION_TOKEN.USER_ID, values);
    }
}
