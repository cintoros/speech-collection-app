/*
 * This file is generated by jOOQ.
 */
package ch.fhnw.labeling_tool.jooq.tables.pojos;


import ch.fhnw.labeling_tool.jooq.enums.UserLicence;
import ch.fhnw.labeling_tool.jooq.enums.UserSex;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class User implements Serializable {

    private static final long serialVersionUID = -1894335027;

    private final Long        id;
    private final String      firstName;
    private final String      lastName;
    private final String      email;
    private final String      username;
    private final String      password;
    private final String      canton;
    private final UserSex     sex;
    private final UserLicence licence;

    public User(User value) {
        this.id = value.id;
        this.firstName = value.firstName;
        this.lastName = value.lastName;
        this.email = value.email;
        this.username = value.username;
        this.password = value.password;
        this.canton = value.canton;
        this.sex = value.sex;
        this.licence = value.licence;
    }

    public User(
        Long        id,
        String      firstName,
        String      lastName,
        String      email,
        String      username,
        String      password,
        String      canton,
        UserSex     sex,
        UserLicence licence
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.canton = canton;
        this.sex = sex;
        this.licence = licence;
    }

    public Long getId() {
        return this.id;
    }

    @NotNull
    @Size(max = 100)
    public String getFirstName() {
        return this.firstName;
    }

    @NotNull
    @Size(max = 100)
    public String getLastName() {
        return this.lastName;
    }

    @NotNull
    @Size(max = 100)
    public String getEmail() {
        return this.email;
    }

    @NotNull
    @Size(max = 100)
    public String getUsername() {
        return this.username;
    }

    @NotNull
    @Size(max = 100)
    public String getPassword() {
        return this.password;
    }

    @NotNull
    @Size(max = 45)
    public String getCanton() {
        return this.canton;
    }

    public UserSex getSex() {
        return this.sex;
    }

    public UserLicence getLicence() {
        return this.licence;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("User (");

        sb.append(id);
        sb.append(", ").append(firstName);
        sb.append(", ").append(lastName);
        sb.append(", ").append(email);
        sb.append(", ").append(username);
        sb.append(", ").append(password);
        sb.append(", ").append(canton);
        sb.append(", ").append(sex);
        sb.append(", ").append(licence);

        sb.append(")");
        return sb.toString();
    }
}
