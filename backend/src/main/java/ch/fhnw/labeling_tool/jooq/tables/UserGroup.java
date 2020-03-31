/*
 * This file is generated by jOOQ.
 */
package ch.fhnw.labeling_tool.jooq.tables;


import ch.fhnw.labeling_tool.jooq.Indexes;
import ch.fhnw.labeling_tool.jooq.Keys;
import ch.fhnw.labeling_tool.jooq.LabelingTool;
import ch.fhnw.labeling_tool.jooq.tables.records.UserGroupRecord;

import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row3;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserGroup extends TableImpl<UserGroupRecord> {

    private static final long serialVersionUID = 761974554;

    public static final UserGroup USER_GROUP = new UserGroup();

    @Override
    public Class<UserGroupRecord> getRecordType() {
        return UserGroupRecord.class;
    }

    public final TableField<UserGroupRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    public final TableField<UserGroupRecord, String> NAME = createField(DSL.name("name"), org.jooq.impl.SQLDataType.VARCHAR(100).defaultValue(org.jooq.impl.DSL.inline("NULL", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    public final TableField<UserGroupRecord, String> DESCRIPTION = createField(DSL.name("description"), org.jooq.impl.SQLDataType.CLOB.defaultValue(org.jooq.impl.DSL.inline("NULL", org.jooq.impl.SQLDataType.CLOB)), this, "");

    public UserGroup() {
        this(DSL.name("user_group"), null);
    }

    public UserGroup(String alias) {
        this(DSL.name(alias), USER_GROUP);
    }

    public UserGroup(Name alias) {
        this(alias, USER_GROUP);
    }

    private UserGroup(Name alias, Table<UserGroupRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserGroup(Name alias, Table<UserGroupRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> UserGroup(Table<O> child, ForeignKey<O, UserGroupRecord> key) {
        super(child, key, USER_GROUP);
    }

    @Override
    public Schema getSchema() {
        return LabelingTool.LABELING_TOOL;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.USER_GROUP_PRIMARY);
    }

    @Override
    public Identity<UserGroupRecord, Long> getIdentity() {
        return Keys.IDENTITY_USER_GROUP;
    }

    @Override
    public UniqueKey<UserGroupRecord> getPrimaryKey() {
        return Keys.KEY_USER_GROUP_PRIMARY;
    }

    @Override
    public List<UniqueKey<UserGroupRecord>> getKeys() {
        return Arrays.<UniqueKey<UserGroupRecord>>asList(Keys.KEY_USER_GROUP_PRIMARY);
    }

    @Override
    public UserGroup as(String alias) {
        return new UserGroup(DSL.name(alias), this);
    }

    @Override
    public UserGroup as(Name alias) {
        return new UserGroup(alias, this);
    }

    @Override
    public UserGroup rename(String name) {
        return new UserGroup(DSL.name(name), null);
    }

    @Override
    public UserGroup rename(Name name) {
        return new UserGroup(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<Long, String, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
