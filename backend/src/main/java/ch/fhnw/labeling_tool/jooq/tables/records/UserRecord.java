/*
 * This file is generated by jOOQ.
 */
package ch.fhnw.labeling_tool.jooq.tables.records;


import ch.fhnw.labeling_tool.jooq.enums.UserAge;
import ch.fhnw.labeling_tool.jooq.enums.UserLicence;
import ch.fhnw.labeling_tool.jooq.enums.UserSex;
import ch.fhnw.labeling_tool.jooq.tables.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record12;
import org.jooq.Row12;
import org.jooq.impl.UpdatableRecordImpl;


@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserRecord extends UpdatableRecordImpl<UserRecord> implements Record12<Long, String, String, String, String, String, UserSex, UserLicence, UserAge, Boolean, Long, String> {

    private static final long serialVersionUID = -2072332609;

    public void setId(Long value) {
        set(0, value);
    }

    public Long getId() {
        return (Long) get(0);
    }

    public void setFirstName(String value) {
        set(1, value);
    }

    @Size(max = 100)
    public String getFirstName() {
        return (String) get(1);
    }

    public void setLastName(String value) {
        set(2, value);
    }

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

    public void setSex(UserSex value) {
        set(6, value);
    }

    public UserSex getSex() {
        return (UserSex) get(6);
    }

    public void setLicence(UserLicence value) {
        set(7, value);
    }

    public UserLicence getLicence() {
        return (UserLicence) get(7);
    }

    public void setAge(UserAge value) {
        set(8, value);
    }

    public UserAge getAge() {
        return (UserAge) get(8);
    }

    public void setEnabled(Boolean value) {
        set(9, value);
    }

    public Boolean getEnabled() {
        return (Boolean) get(9);
    }

    public void setDialectId(Long value) {
        set(10, value);
    }

    @NotNull
    public Long getDialectId() {
        return (Long) get(10);
    }

    public void setZipCode(String value) {
        set(11, value);
    }

    @NotNull
    @Size(max = 45)
    public String getZipCode() {
        return (String) get(11);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record12 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row12<Long, String, String, String, String, String, UserSex, UserLicence, UserAge, Boolean, Long, String> fieldsRow() {
        return (Row12) super.fieldsRow();
    }

    @Override
    public Row12<Long, String, String, String, String, String, UserSex, UserLicence, UserAge, Boolean, Long, String> valuesRow() {
        return (Row12) super.valuesRow();
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
    public Field<UserSex> field7() {
        return User.USER.SEX;
    }

    @Override
    public Field<UserLicence> field8() {
        return User.USER.LICENCE;
    }

    @Override
    public Field<UserAge> field9() {
        return User.USER.AGE;
    }

    @Override
    public Field<Boolean> field10() {
        return User.USER.ENABLED;
    }

    @Override
    public Field<Long> field11() {
        return User.USER.DIALECT_ID;
    }

    @Override
    public Field<String> field12() {
        return User.USER.ZIP_CODE;
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
    public UserSex component7() {
        return getSex();
    }

    @Override
    public UserLicence component8() {
        return getLicence();
    }

    @Override
    public UserAge component9() {
        return getAge();
    }

    @Override
    public Boolean component10() {
        return getEnabled();
    }

    @Override
    public Long component11() {
        return getDialectId();
    }

    @Override
    public String component12() {
        return getZipCode();
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
    public UserSex value7() {
        return getSex();
    }

    @Override
    public UserLicence value8() {
        return getLicence();
    }

    @Override
    public UserAge value9() {
        return getAge();
    }

    @Override
    public Boolean value10() {
        return getEnabled();
    }

    @Override
    public Long value11() {
        return getDialectId();
    }

    @Override
    public String value12() {
        return getZipCode();
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
    public UserRecord value7(UserSex value) {
        setSex(value);
        return this;
    }

    @Override
    public UserRecord value8(UserLicence value) {
        setLicence(value);
        return this;
    }

    @Override
    public UserRecord value9(UserAge value) {
        setAge(value);
        return this;
    }

    @Override
    public UserRecord value10(Boolean value) {
        setEnabled(value);
        return this;
    }

    @Override
    public UserRecord value11(Long value) {
        setDialectId(value);
        return this;
    }

    @Override
    public UserRecord value12(String value) {
        setZipCode(value);
        return this;
    }

    @Override
    public UserRecord values(Long value1, String value2, String value3, String value4, String value5, String value6, UserSex value7, UserLicence value8, UserAge value9, Boolean value10, Long value11, String value12) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        value12(value12);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    public UserRecord() {
        super(User.USER);
    }

    public UserRecord(Long id, String firstName, String lastName, String email, String username, String password, UserSex sex, UserLicence licence, UserAge age, Boolean enabled, Long dialectId, String zipCode) {
        super(User.USER);

        set(0, id);
        set(1, firstName);
        set(2, lastName);
        set(3, email);
        set(4, username);
        set(5, password);
        set(6, sex);
        set(7, licence);
        set(8, age);
        set(9, enabled);
        set(10, dialectId);
        set(11, zipCode);
    }
}