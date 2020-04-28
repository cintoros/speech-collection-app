/*
 * This file is generated by jOOQ.
 */
package ch.fhnw.speech_collection_app.jooq.enums;


import org.jooq.Catalog;
import org.jooq.EnumType;
import org.jooq.Schema;


@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public enum CheckedDataElementType implements EnumType {

    SKIPPED("SKIPPED"),

    PRIVATE("PRIVATE"),

    SENTENCE_ERROR("SENTENCE_ERROR");

    private final String literal;

    private CheckedDataElementType(String literal) {
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
        return "checked_data_element_type";
    }

    @Override
    public String getLiteral() {
        return literal;
    }
}