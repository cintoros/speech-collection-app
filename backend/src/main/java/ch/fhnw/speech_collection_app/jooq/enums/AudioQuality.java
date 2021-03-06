/*
 * This file is generated by jOOQ.
 */
package ch.fhnw.speech_collection_app.jooq.enums;


import org.jooq.Catalog;
import org.jooq.EnumType;
import org.jooq.Schema;


@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public enum AudioQuality implements EnumType {

    INTEGRATED("INTEGRATED"),

    DEDICATED("DEDICATED");

    private final String literal;

    private AudioQuality(String literal) {
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
        return "audio_quality";
    }

    @Override
    public String getLiteral() {
        return literal;
    }
}
