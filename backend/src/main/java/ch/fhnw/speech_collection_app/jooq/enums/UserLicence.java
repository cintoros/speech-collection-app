/*
 * This file is generated by jOOQ.
 */
package ch.fhnw.speech_collection_app.jooq.enums;


import org.jooq.Catalog;
import org.jooq.EnumType;
import org.jooq.Schema;


@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public enum UserLicence implements EnumType {

    PUBLIC("PUBLIC"),

    ACADEMIC("ACADEMIC");

    private final String literal;

    private UserLicence(String literal) {
        this.literal = literal;
    }

    @Override
    public Catalog getCatalog() {
        return null;
    }

    @Override
    public Schema getSchema() {
        return null;
    }

    @Override
    public String getName() {
        return "user_licence";
    }

    @Override
    public String getLiteral() {
        return literal;
    }
}
