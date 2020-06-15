package ch.fhnw.speech_collection_app.features.base.admin.statistics;

import ch.fhnw.speech_collection_app.config.SpeechCollectionAppConfig;
import ch.fhnw.speech_collection_app.jooq.enums.CheckedDataElementType;
import ch.fhnw.speech_collection_app.jooq.enums.CheckedDataTupleType;
import org.apache.tika.io.IOUtils;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static ch.fhnw.speech_collection_app.jooq.Tables.*;

@Service
public class StatisticsService {
    private final DSLContext dslContext;
    private final SpeechCollectionAppConfig speechCollectionAppConfig;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public StatisticsService(DSLContext dslContext, SpeechCollectionAppConfig speechCollectionAppConfig) {
        this.dslContext = dslContext;
        this.speechCollectionAppConfig = speechCollectionAppConfig;
    }

    public List<SeriesDto> getStatisticsSince(LocalDate since) {
        Timestamp timestamp = Timestamp.valueOf(since.atStartOfDay());

        var date = DSL.date(CHECKED_DATA_ELEMENT.CREATED_TIME);
        var select = dslContext.select(date, DSL.count())
                .from(CHECKED_DATA_ELEMENT)
                .where(CHECKED_DATA_ELEMENT.TYPE.notEqual(CheckedDataElementType.SKIPPED)
                        .and(CHECKED_DATA_ELEMENT.CREATED_TIME.greaterOrEqual(timestamp)));
        var checkedTexts = toSeriesDto(select, "checked texts", since, date);

        date = DSL.date(CHECKED_DATA_TUPLE.CREATED_TIME);
        select = dslContext.select(date, DSL.count())
                .from(CHECKED_DATA_TUPLE)
                .where(CHECKED_DATA_TUPLE.TYPE.notEqual(CheckedDataTupleType.SKIPPED)
                        .and(CHECKED_DATA_TUPLE.CREATED_TIME.greaterOrEqual(timestamp)));
        var checkedRecordings = toSeriesDto(select, "checked recordings", since, date);

        date = DSL.date(DATA_ELEMENT.CREATED_TIME);
        select = dslContext.select(date, DSL.count())
                .from(DATA_ELEMENT.innerJoin(AUDIO).on(AUDIO.DATA_ELEMENT_ID.eq(DATA_ELEMENT.ID)))
                .where((DATA_ELEMENT.CREATED_TIME.greaterOrEqual(timestamp)));
        var recordings = toSeriesDto(select, "recordings", since, date);

        return List.of(checkedTexts, checkedRecordings, recordings);
    }

    public List<SeriesDto> getAudioDurationStatisticsSince(LocalDate since) {
        // calculate size of audio file. this is needed because:
        // 1. there is no java libary that supports webm/vorbis
        // 2. chrome does not set the duration header of the webm/vorbis (firefox 77.0 does set it).
        // see https://bugs.chromium.org/p/chromium/issues/detail?id=642012
        try {
            var process = Runtime.getRuntime().exec(speechCollectionAppConfig.getCondaExec() + " 3 ");
            List<String> list = IOUtils.readLines(process.getErrorStream());
            if (!list.isEmpty()) {
                logger.error(String.join("\n", list));
            }
        } catch (Exception e) {
            logger.error("Exception Raised", e);
        }
        Timestamp timestamp = Timestamp.valueOf(since.atStartOfDay());
        var date = DSL.date(DATA_ELEMENT.CREATED_TIME);
        var select1 = dslContext.select(date, DSL.sum(AUDIO.DURATION).divide(3600))
                .from(DATA_ELEMENT.innerJoin(AUDIO).on(AUDIO.DATA_ELEMENT_ID.eq(DATA_ELEMENT.ID)))
                .where((DATA_ELEMENT.CREATED_TIME.greaterOrEqual(timestamp)))
                .groupBy(date)
                .fetch()
                .stream()
                .filter(r -> r.component1() != null)
                .map(r -> new SeriesValueDto(r.component1().toLocalDate(), r.component2()))
                .collect(Collectors.toList());

        var select2 = dslContext.select(USER.USERNAME, DSL.sum(AUDIO.DURATION).divide(3600))
                .from(DATA_ELEMENT.innerJoin(AUDIO).on(AUDIO.DATA_ELEMENT_ID.eq(DATA_ELEMENT.ID))
                        .innerJoin(USER).on(DATA_ELEMENT.USER_ID.eq(USER.ID)))
                .where((DATA_ELEMENT.CREATED_TIME.greaterOrEqual(timestamp)))
                .groupBy(DATA_ELEMENT.USER_ID)
                .fetch()
                .stream()
                .filter(r -> r.component1() != null)
                .map(r -> new SeriesValueDto(r.component1(), r.component2()))
                .collect(Collectors.toList());
        return List.of(new SeriesDto("audio in h", select1), new SeriesDto("recordings in h per user", select2));
    }

    private SeriesDto toSeriesDto(SelectConditionStep<Record2<Date, Integer>> select, String checked_texts, LocalDate since, Field<Date> date) {
        var res = select.groupBy(date)
                .fetch()
                .stream()
                .filter(r -> r.component1() != null)
                .map(r -> new SeriesValueDto(r.component1().toLocalDate(), r.component2()))
                .collect(Collectors.toList());
        //we need add a second data-point as else we cannot display the line chart.
        if (res.size() == 1) {
            var localDate = (LocalDate) res.get(0).name;
            var name = (localDate.isEqual(LocalDate.now())) ? localDate.minusDays(1) : localDate.plusDays(1);
            res.add(new SeriesValueDto(name, 0));
        }
        return new SeriesDto(checked_texts, res);
    }
}