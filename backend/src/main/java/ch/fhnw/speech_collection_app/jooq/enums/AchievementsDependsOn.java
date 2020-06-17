/*
 * This file is generated by jOOQ.
 */
package ch.fhnw.speech_collection_app.jooq.enums;


import org.jooq.Catalog;
import org.jooq.EnumType;
import org.jooq.Schema;


@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public enum AchievementsDependsOn implements EnumType {

    TEXT_CREATED("TEXT_CREATED"),

    AUDIO_CREATED("AUDIO_CREATED"),

    IMAGE_CREATED("IMAGE_CREATED"),

    TOTAL_CREATED("TOTAL_CREATED"),

    TEXT_TEXT_CHECKED("TEXT_TEXT_CHECKED"),

    AUDIO_AUDIO_CHECKED("AUDIO_AUDIO_CHECKED"),

    TEXT_AUDIO_CHECKED("TEXT_AUDIO_CHECKED"),

    AUDIO_TEXT_CHECKED("AUDIO_TEXT_CHECKED"),

    IMAGE_AUDIO_CHECKED("IMAGE_AUDIO_CHECKED"),

    IMAGE_TEXT_CHECKED("IMAGE_TEXT_CHECKED"),

    TOTAL_CHECKED("TOTAL_CHECKED"),

    ALL("ALL"),

    MANUAL("MANUAL");

    private final String literal;

    private AchievementsDependsOn(String literal) {
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
        return "achievements_depends_on";
    }

    @Override
    public String getLiteral() {
        return literal;
    }
}