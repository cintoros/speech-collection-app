/*
 * This file is generated by jOOQ.
 */
package ch.fhnw.labeling_tool.jooq;


import ch.fhnw.labeling_tool.jooq.tables.CheckedUtterance;
import ch.fhnw.labeling_tool.jooq.tables.Excerpt;
import ch.fhnw.labeling_tool.jooq.tables.FlywaySchemaHistory;
import ch.fhnw.labeling_tool.jooq.tables.OriginalText;
import ch.fhnw.labeling_tool.jooq.tables.Recording;
import ch.fhnw.labeling_tool.jooq.tables.Speaker;
import ch.fhnw.labeling_tool.jooq.tables.TextAudio;
import ch.fhnw.labeling_tool.jooq.tables.User;
import ch.fhnw.labeling_tool.jooq.tables.UserAndTextAudio;
import ch.fhnw.labeling_tool.jooq.tables.UserGroup;
import ch.fhnw.labeling_tool.jooq.tables.UserGroupRole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class LabelingTool extends SchemaImpl {

    private static final long serialVersionUID = -1907923883;

    public static final LabelingTool LABELING_TOOL = new LabelingTool();

    public final CheckedUtterance CHECKED_UTTERANCE = ch.fhnw.labeling_tool.jooq.tables.CheckedUtterance.CHECKED_UTTERANCE;

    public final Excerpt EXCERPT = ch.fhnw.labeling_tool.jooq.tables.Excerpt.EXCERPT;

    public final FlywaySchemaHistory FLYWAY_SCHEMA_HISTORY = ch.fhnw.labeling_tool.jooq.tables.FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY;

    public final OriginalText ORIGINAL_TEXT = ch.fhnw.labeling_tool.jooq.tables.OriginalText.ORIGINAL_TEXT;

    public final Recording RECORDING = ch.fhnw.labeling_tool.jooq.tables.Recording.RECORDING;

    public final Speaker SPEAKER = ch.fhnw.labeling_tool.jooq.tables.Speaker.SPEAKER;

    public final TextAudio TEXT_AUDIO = ch.fhnw.labeling_tool.jooq.tables.TextAudio.TEXT_AUDIO;

    public final User USER = ch.fhnw.labeling_tool.jooq.tables.User.USER;

    public final UserAndTextAudio USER_AND_TEXT_AUDIO = ch.fhnw.labeling_tool.jooq.tables.UserAndTextAudio.USER_AND_TEXT_AUDIO;

    public final UserGroup USER_GROUP = ch.fhnw.labeling_tool.jooq.tables.UserGroup.USER_GROUP;

    public final UserGroupRole USER_GROUP_ROLE = ch.fhnw.labeling_tool.jooq.tables.UserGroupRole.USER_GROUP_ROLE;

    private LabelingTool() {
        super("labeling-tool", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            CheckedUtterance.CHECKED_UTTERANCE,
            Excerpt.EXCERPT,
            FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY,
            OriginalText.ORIGINAL_TEXT,
            Recording.RECORDING,
            Speaker.SPEAKER,
            TextAudio.TEXT_AUDIO,
            User.USER,
            UserAndTextAudio.USER_AND_TEXT_AUDIO,
            UserGroup.USER_GROUP,
            UserGroupRole.USER_GROUP_ROLE);
    }
}
