/*
 * This file is generated by jOOQ.
 */
package ch.fhnw.labeling_tool.jooq.enums;


import org.jooq.Catalog;
import org.jooq.EnumType;
import org.jooq.Schema;


@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public enum RecordingNoiseLevel implements EnumType {

    NO_NOISE("NO_NOISE"),

    MODERATE_NOISE("MODERATE_NOISE"),

    VERY_NOISY("VERY_NOISY");

    private final String literal;

    private RecordingNoiseLevel(String literal) {
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
        return "recording_noise_level";
    }

    @Override
    public String getLiteral() {
        return literal;
    }
}
