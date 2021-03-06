/*
 * This file is generated by jOOQ.
 */
package ch.fhnw.speech_collection_app.jooq.tables;


import ch.fhnw.speech_collection_app.jooq.Indexes;
import ch.fhnw.speech_collection_app.jooq.Keys;
import ch.fhnw.speech_collection_app.jooq.SpeechCollectionApp;
import ch.fhnw.speech_collection_app.jooq.enums.AchievementsDependsOn;
import ch.fhnw.speech_collection_app.jooq.tables.records.AchievementsRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row17;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Achievements extends TableImpl<AchievementsRecord> {

    private static final long serialVersionUID = -299691367;

    public static final Achievements ACHIEVEMENTS = new Achievements();

    @Override
    public Class<AchievementsRecord> getRecordType() {
        return AchievementsRecord.class;
    }

    public final TableField<AchievementsRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    public final TableField<AchievementsRecord, Long> DOMAIN_ID = createField(DSL.name("domain_id"), org.jooq.impl.SQLDataType.BIGINT.defaultValue(org.jooq.impl.DSL.inline("NULL", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    public final TableField<AchievementsRecord, String> NAME = createField(DSL.name("name"), org.jooq.impl.SQLDataType.CLOB.defaultValue(org.jooq.impl.DSL.inline("NULL", org.jooq.impl.SQLDataType.CLOB)), this, "");

    public final TableField<AchievementsRecord, String> BATCH_NAME = createField(DSL.name("batch_name"), org.jooq.impl.SQLDataType.CLOB.defaultValue(org.jooq.impl.DSL.inline("NULL", org.jooq.impl.SQLDataType.CLOB)), this, "");

    public final TableField<AchievementsRecord, String> TITLE = createField(DSL.name("title"), org.jooq.impl.SQLDataType.CLOB.defaultValue(org.jooq.impl.DSL.inline("NULL", org.jooq.impl.SQLDataType.CLOB)), this, "");

    public final TableField<AchievementsRecord, Timestamp> START_TIME = createField(DSL.name("start_time"), org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("current_timestamp()", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    public final TableField<AchievementsRecord, Timestamp> END_TIME = createField(DSL.name("end_time"), org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("current_timestamp()", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    public final TableField<AchievementsRecord, Long> POINTS_LVL1 = createField(DSL.name("points_lvl1"), org.jooq.impl.SQLDataType.BIGINT.defaultValue(org.jooq.impl.DSL.inline("NULL", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    public final TableField<AchievementsRecord, Long> POINTS_LVL2 = createField(DSL.name("points_lvl2"), org.jooq.impl.SQLDataType.BIGINT.defaultValue(org.jooq.impl.DSL.inline("NULL", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    public final TableField<AchievementsRecord, Long> POINTS_LVL3 = createField(DSL.name("points_lvl3"), org.jooq.impl.SQLDataType.BIGINT.defaultValue(org.jooq.impl.DSL.inline("NULL", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    public final TableField<AchievementsRecord, Long> POINTS_LVL4 = createField(DSL.name("points_lvl4"), org.jooq.impl.SQLDataType.BIGINT.defaultValue(org.jooq.impl.DSL.inline("NULL", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    public final TableField<AchievementsRecord, String> DESCRIPTION_LVL1 = createField(DSL.name("description_lvl1"), org.jooq.impl.SQLDataType.CLOB.defaultValue(org.jooq.impl.DSL.inline("NULL", org.jooq.impl.SQLDataType.CLOB)), this, "");

    public final TableField<AchievementsRecord, String> DESCRIPTION_LVL2 = createField(DSL.name("description_lvl2"), org.jooq.impl.SQLDataType.CLOB.defaultValue(org.jooq.impl.DSL.inline("NULL", org.jooq.impl.SQLDataType.CLOB)), this, "");

    public final TableField<AchievementsRecord, String> DESCRIPTION_LVL3 = createField(DSL.name("description_lvl3"), org.jooq.impl.SQLDataType.CLOB.defaultValue(org.jooq.impl.DSL.inline("NULL", org.jooq.impl.SQLDataType.CLOB)), this, "");

    public final TableField<AchievementsRecord, String> DESCRIPTION_LVL4 = createField(DSL.name("description_lvl4"), org.jooq.impl.SQLDataType.CLOB.defaultValue(org.jooq.impl.DSL.inline("NULL", org.jooq.impl.SQLDataType.CLOB)), this, "");

    public final TableField<AchievementsRecord, AchievementsDependsOn> DEPENDS_ON = createField(DSL.name("depends_on"), org.jooq.impl.SQLDataType.VARCHAR(19).nullable(false).asEnumDataType(ch.fhnw.speech_collection_app.jooq.enums.AchievementsDependsOn.class), this, "");

    public final TableField<AchievementsRecord, Boolean> IS_VISIBLE = createField(DSL.name("is_visible"), org.jooq.impl.SQLDataType.BOOLEAN.defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.BOOLEAN)), this, "");

    public Achievements() {
        this(DSL.name("achievements"), null);
    }

    public Achievements(String alias) {
        this(DSL.name(alias), ACHIEVEMENTS);
    }

    public Achievements(Name alias) {
        this(alias, ACHIEVEMENTS);
    }

    private Achievements(Name alias, Table<AchievementsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Achievements(Name alias, Table<AchievementsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> Achievements(Table<O> child, ForeignKey<O, AchievementsRecord> key) {
        super(child, key, ACHIEVEMENTS);
    }

    @Override
    public Schema getSchema() {
        return SpeechCollectionApp.SPEECH_COLLECTION_APP;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.ACHIEVEMENTS_DOMAIN_ID);
    }

    @Override
    public Identity<AchievementsRecord, Long> getIdentity() {
        return Keys.IDENTITY_ACHIEVEMENTS;
    }

    @Override
    public UniqueKey<AchievementsRecord> getPrimaryKey() {
        return Keys.KEY_ACHIEVEMENTS_PRIMARY;
    }

    @Override
    public List<UniqueKey<AchievementsRecord>> getKeys() {
        return Arrays.<UniqueKey<AchievementsRecord>>asList(Keys.KEY_ACHIEVEMENTS_PRIMARY);
    }

    @Override
    public List<ForeignKey<AchievementsRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<AchievementsRecord, ?>>asList(Keys.ACHIEVEMENTS_IBFK_1);
    }

    public Domain domain() {
        return new Domain(this, Keys.ACHIEVEMENTS_IBFK_1);
    }

    @Override
    public Achievements as(String alias) {
        return new Achievements(DSL.name(alias), this);
    }

    @Override
    public Achievements as(Name alias) {
        return new Achievements(alias, this);
    }

    @Override
    public Achievements rename(String name) {
        return new Achievements(DSL.name(name), null);
    }

    @Override
    public Achievements rename(Name name) {
        return new Achievements(name, null);
    }

    // -------------------------------------------------------------------------
    // Row17 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row17<Long, Long, String, String, String, Timestamp, Timestamp, Long, Long, Long, Long, String, String, String, String, AchievementsDependsOn, Boolean> fieldsRow() {
        return (Row17) super.fieldsRow();
    }
}
