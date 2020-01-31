/*
 * This file is generated by jOOQ.
 */
package ch.fhnw.labeling_tool.jooq.tables.records;


import ch.fhnw.labeling_tool.jooq.enums.UserLicence;
import ch.fhnw.labeling_tool.jooq.enums.UserSex;
import ch.fhnw.labeling_tool.jooq.tables.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
import org.jooq.impl.UpdatableRecordImpl;


@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserRecord extends UpdatableRecordImpl<UserRecord> implements Record9<Long, String, String, String, String, String, String, UserSex, UserLicence> {

    private static final long serialVersionUID = -197725586;

    public void setId(Long value) {
        set(0, value);
    }

    public Long getId() {
        return (Long) get(0);
    }

    public void setFirstName(String value) {
        set(1, value);
    }

    @NotNull
    @Size(max = 100)
    public String getFirstName() {
        return (String) get(1);
    }

    public void setLastName(String value) {
        set(2, value);
    }

    @NotNull
    @Size(max = 100)
    public String getLastName() {
        return (String) get(2);
    }

    public void setEmail(String value) {
        set(3, value);
    }

    @NotNull
    @Size(max = 100)
    public String getEmail() {
        return (String) get(3);
    }

    public void setUsername(String value) {
        set(4, value);
    }

    @NotNull
    @Size(max = 100)
    public String getUsername() {
        return (String) get(4);
    }

    public void setPassword(String value) {
        set(5, value);
    }

    @NotNull
    @Size(max = 100)
    public String getPassword() {
        return (String) get(5);
    }

    public void setCanton(String value) {
        set(6, value);
    }

    @NotNull
    @Size(max = 45)
    public String getCanton() {
        return (String) get(6);
    }

    public void setSex(UserSex value) {
        set(7, value);
    }

    public UserSex getSex() {
        return (UserSex) get(7);
    }

    public void setLicence(UserLicence value) {
        set(8, value);
    }

    public UserLicence getLicence() {
        return (UserLicence) get(8);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record9 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row9<Long, String, String, String, String, String, String, UserSex, UserLicence> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    @Override
    public Row9<Long, String, String, String, String, String, String, UserSex, UserLicence> valuesRow() {
        return (Row9) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return User.USER.ID;
    }

    @Override
    public Field<String> field2() {
        return User.USER.FIRST_NAME;
    }

    @Override
    public Field<String> field3() {
        return User.USER.LAST_NAME;
    }

    @Override
    public Field<String> field4() {
        return User.USER.EMAIL;
    }

    @Override
    public Field<String> field5() {
        return User.USER.USERNAME;
    }

    @Override
    public Field<String> field6() {
        return User.USER.PASSWORD;
    }

    @Override
    public Field<String> field7() {
        return User.USER.CANTON;
    }

    @Override
    public Field<UserSex> field8() {
        return User.USER.SEX;
    }

    @Override
    public Field<UserLicence> field9() {
        return User.USER.LICENCE;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getFirstName();
    }

    @Override
    public String component3() {
        return getLastName();
    }

    @Override
    public String component4() {
        return getEmail();
    }

    @Override
    public String component5() {
        return getUsername();
    }

    @Override
    public String component6() {
        return getPassword();
    }

    @Override
    public String component7() {
        return getCanton();
    }

    @Override
    public UserSex component8() {
        return getSex();
    }

    @Override
    public UserLicence component9() {
        return getLicence();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getFirstName();
    }

    @Override
    public String value3() {
        return getLastName();
    }

    @Override
    public String value4() {
        return getEmail();
    }

    @Override
    public String value5() {
        return getUsername();
    }

    @Override
    public String value6() {
        return getPassword();
    }

    @Override
    public String value7() {
        return getCanton();
    }

    @Override
    public UserSex value8() {
        return getSex();
    }

    @Override
    public UserLicence value9() {
        return getLicence();
    }

    @Override
    public UserRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public UserRecord value2(String value) {
        setFirstName(value);
        return this;
    }

    @Override
    public UserRecord value3(String value) {
        setLastName(value);
        return this;
    }

    @Override
    public UserRecord value4(String value) {
        setEmail(value);
        return this;
    }

    @Override
    public UserRecord value5(String value) {
        setUsername(value);
        return this;
    }

    @Override
    public UserRecord value6(String value) {
        setPassword(value);
        return this;
    }

    @Override
    public UserRecord value7(String value) {
        setCanton(value);
        return this;
    }

    @Override
    public UserRecord value8(UserSex value) {
        setSex(value);
        return this;
    }

    @Override
    public UserRecord value9(UserLicence value) {
        setLicence(value);
        return this;
    }

    @Override
    public UserRecord values(Long value1, String value2, String value3, String value4, String value5, String value6, String value7, UserSex value8, UserLicence value9) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    public UserRecord() {
        super(User.USER);
    }

    public UserRecord(Long id, String firstName, String lastName, String email, String username, String password, String canton, UserSex sex, UserLicence licence) {
        super(User.USER);

        set(0, id);
        set(1, firstName);
        set(2, lastName);
        set(3, email);
        set(4, username);
        set(5, password);
        set(6, canton);
        set(7, sex);
        set(8, licence);
    }
}
