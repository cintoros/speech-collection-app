/*
 * This file is generated by jOOQ.
 */
package ch.fhnw.labeling_tool.jooq.tables.daos;


import ch.fhnw.labeling_tool.jooq.tables.OriginalText;
import ch.fhnw.labeling_tool.jooq.tables.records.OriginalTextRecord;

import java.sql.Timestamp;
import java.util.List;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Repository
public class OriginalTextDao extends DAOImpl<OriginalTextRecord, ch.fhnw.labeling_tool.jooq.tables.pojos.OriginalText, Long> {

    public OriginalTextDao() {
        super(OriginalText.ORIGINAL_TEXT, ch.fhnw.labeling_tool.jooq.tables.pojos.OriginalText.class);
    }

    @Autowired
    public OriginalTextDao(Configuration configuration) {
        super(OriginalText.ORIGINAL_TEXT, ch.fhnw.labeling_tool.jooq.tables.pojos.OriginalText.class, configuration);
    }

    @Override
    public Long getId(ch.fhnw.labeling_tool.jooq.tables.pojos.OriginalText object) {
        return object.getId();
    }

    public List<ch.fhnw.labeling_tool.jooq.tables.pojos.OriginalText> fetchRangeOfId(Long lowerInclusive, Long upperInclusive) {
        return fetchRange(OriginalText.ORIGINAL_TEXT.ID, lowerInclusive, upperInclusive);
    }

    public List<ch.fhnw.labeling_tool.jooq.tables.pojos.OriginalText> fetchById(Long... values) {
        return fetch(OriginalText.ORIGINAL_TEXT.ID, values);
    }

    public ch.fhnw.labeling_tool.jooq.tables.pojos.OriginalText fetchOneById(Long value) {
        return fetchOne(OriginalText.ORIGINAL_TEXT.ID, value);
    }

    public List<ch.fhnw.labeling_tool.jooq.tables.pojos.OriginalText> fetchRangeOfUserGroupId(Long lowerInclusive, Long upperInclusive) {
        return fetchRange(OriginalText.ORIGINAL_TEXT.USER_GROUP_ID, lowerInclusive, upperInclusive);
    }

    public List<ch.fhnw.labeling_tool.jooq.tables.pojos.OriginalText> fetchByUserGroupId(Long... values) {
        return fetch(OriginalText.ORIGINAL_TEXT.USER_GROUP_ID, values);
    }

    public List<ch.fhnw.labeling_tool.jooq.tables.pojos.OriginalText> fetchRangeOfDomainId(Long lowerInclusive, Long upperInclusive) {
        return fetchRange(OriginalText.ORIGINAL_TEXT.DOMAIN_ID, lowerInclusive, upperInclusive);
    }

    public List<ch.fhnw.labeling_tool.jooq.tables.pojos.OriginalText> fetchByDomainId(Long... values) {
        return fetch(OriginalText.ORIGINAL_TEXT.DOMAIN_ID, values);
    }

    public List<ch.fhnw.labeling_tool.jooq.tables.pojos.OriginalText> fetchRangeOfUserId(Long lowerInclusive, Long upperInclusive) {
        return fetchRange(OriginalText.ORIGINAL_TEXT.USER_ID, lowerInclusive, upperInclusive);
    }

    public List<ch.fhnw.labeling_tool.jooq.tables.pojos.OriginalText> fetchByUserId(Long... values) {
        return fetch(OriginalText.ORIGINAL_TEXT.USER_ID, values);
    }

    public List<ch.fhnw.labeling_tool.jooq.tables.pojos.OriginalText> fetchRangeOfTime(Timestamp lowerInclusive, Timestamp upperInclusive) {
        return fetchRange(OriginalText.ORIGINAL_TEXT.TIME, lowerInclusive, upperInclusive);
    }

    public List<ch.fhnw.labeling_tool.jooq.tables.pojos.OriginalText> fetchByTime(Timestamp... values) {
        return fetch(OriginalText.ORIGINAL_TEXT.TIME, values);
    }

    public List<ch.fhnw.labeling_tool.jooq.tables.pojos.OriginalText> fetchRangeOfLicence(String lowerInclusive, String upperInclusive) {
        return fetchRange(OriginalText.ORIGINAL_TEXT.LICENCE, lowerInclusive, upperInclusive);
    }

    public List<ch.fhnw.labeling_tool.jooq.tables.pojos.OriginalText> fetchByLicence(String... values) {
        return fetch(OriginalText.ORIGINAL_TEXT.LICENCE, values);
    }
}
