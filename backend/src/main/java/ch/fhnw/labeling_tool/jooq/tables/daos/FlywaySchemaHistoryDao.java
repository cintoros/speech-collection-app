/*
 * This file is generated by jOOQ.
 */
package ch.fhnw.labeling_tool.jooq.tables.daos;


import ch.fhnw.labeling_tool.jooq.tables.FlywaySchemaHistory;
import ch.fhnw.labeling_tool.jooq.tables.records.FlywaySchemaHistoryRecord;

import java.sql.Timestamp;
import java.util.List;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Repository
public class FlywaySchemaHistoryDao extends DAOImpl<FlywaySchemaHistoryRecord, ch.fhnw.labeling_tool.jooq.tables.pojos.FlywaySchemaHistory, Integer> {

    public FlywaySchemaHistoryDao() {
        super(FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY, ch.fhnw.labeling_tool.jooq.tables.pojos.FlywaySchemaHistory.class);
    }

    @Autowired
    public FlywaySchemaHistoryDao(Configuration configuration) {
        super(FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY, ch.fhnw.labeling_tool.jooq.tables.pojos.FlywaySchemaHistory.class, configuration);
    }

    @Override
    protected Integer getId(ch.fhnw.labeling_tool.jooq.tables.pojos.FlywaySchemaHistory object) {
        return object.getInstalledRank();
    }

    public List<ch.fhnw.labeling_tool.jooq.tables.pojos.FlywaySchemaHistory> fetchByInstalledRank(Integer... values) {
        return fetch(FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.INSTALLED_RANK, values);
    }

    public ch.fhnw.labeling_tool.jooq.tables.pojos.FlywaySchemaHistory fetchOneByInstalledRank(Integer value) {
        return fetchOne(FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.INSTALLED_RANK, value);
    }

    public List<ch.fhnw.labeling_tool.jooq.tables.pojos.FlywaySchemaHistory> fetchByVersion(String... values) {
        return fetch(FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.VERSION, values);
    }

    public List<ch.fhnw.labeling_tool.jooq.tables.pojos.FlywaySchemaHistory> fetchByDescription(String... values) {
        return fetch(FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.DESCRIPTION, values);
    }

    public List<ch.fhnw.labeling_tool.jooq.tables.pojos.FlywaySchemaHistory> fetchByType(String... values) {
        return fetch(FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.TYPE, values);
    }

    public List<ch.fhnw.labeling_tool.jooq.tables.pojos.FlywaySchemaHistory> fetchByScript(String... values) {
        return fetch(FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.SCRIPT, values);
    }

    public List<ch.fhnw.labeling_tool.jooq.tables.pojos.FlywaySchemaHistory> fetchByChecksum(Integer... values) {
        return fetch(FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.CHECKSUM, values);
    }

    public List<ch.fhnw.labeling_tool.jooq.tables.pojos.FlywaySchemaHistory> fetchByInstalledBy(String... values) {
        return fetch(FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.INSTALLED_BY, values);
    }

    public List<ch.fhnw.labeling_tool.jooq.tables.pojos.FlywaySchemaHistory> fetchByInstalledOn(Timestamp... values) {
        return fetch(FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.INSTALLED_ON, values);
    }

    public List<ch.fhnw.labeling_tool.jooq.tables.pojos.FlywaySchemaHistory> fetchByExecutionTime(Integer... values) {
        return fetch(FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.EXECUTION_TIME, values);
    }

    public List<ch.fhnw.labeling_tool.jooq.tables.pojos.FlywaySchemaHistory> fetchBySuccess(Byte... values) {
        return fetch(FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.SUCCESS, values);
    }
}
