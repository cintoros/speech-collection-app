/*
 * This file is generated by jOOQ.
 */
package ch.fhnw.labeling_tool.jooq.tables.pojos;


import java.io.Serializable;

import javax.validation.constraints.Size;


@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserGroup implements Serializable {

    private static final long serialVersionUID = -413823138;

    private Long   id;
    private String name;
    private String description;

    public UserGroup() {}

    public UserGroup(UserGroup value) {
        this.id = value.id;
        this.name = value.name;
        this.description = value.description;
    }

    public UserGroup(
        Long   id,
        String name,
        String description
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Size(max = 100)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Size(max = 16777215)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("UserGroup (");

        sb.append(id);
        sb.append(", ").append(name);
        sb.append(", ").append(description);

        sb.append(")");
        return sb.toString();
    }
}
