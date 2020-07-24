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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    /**
     * calculate size of audio file every hour.<br>
     * <br>
     * this is needed because:<br>
     * 1. there is no java libary that supports webm/vorbis<br>
     * 2. chrome does not set the duration header of the webm/vorbis (firefox 77.0 does set it).<br>
     * see https://bugs.chromium.org/p/chromium/issues/detail?id=642012
     */
    @Scheduled(fixedDelay = 1000 * 60 * 60)
    public void calculateAudioFileSizes() {
        logger.info("scheduled calculateAudioFileSizes");
        try {
            var process = Runtime.getRuntime().exec(speechCollectionAppConfig.getCondaExec() + " 3 ");
            List<String> list = IOUtils.readLines(process.getErrorStream());
            if (!list.isEmpty()) {
                logger.error(String.join("\n", list));
            }
        } catch (Exception e) {
            logger.error("Exception Raised", e);
        }
    }

    /**
     * fetches the query from the database and returns it ready to be displayed by the frontend.
     *
     * @implNote this implementation only makes sense for relatively small queries as else a lot of dates have to be filled with zeroes
     */
    public List<SeriesDto> getAudioDurationStatisticsSince(LocalDate since) {
        Timestamp timestamp = Timestamp.valueOf(since.atStartOfDay());
        var date = DSL.date(DATA_ELEMENT.CREATED_TIME);

        var sinceDate = since.atStartOfDay();
        var allDates = sinceDate.toLocalDate().datesUntil(LocalDate.now().plusDays(1))
                .collect(Collectors.toMap(localDate -> localDate, o -> 0.0));
        var datesWithData = dslContext.select(date, DSL.sum(AUDIO.DURATION).divide(3600))
                .from(DATA_ELEMENT.innerJoin(AUDIO).on(AUDIO.DATA_ELEMENT_ID.eq(DATA_ELEMENT.ID)))
                .where((DATA_ELEMENT.CREATED_TIME.greaterOrEqual(timestamp)))
                .groupBy(date)
                .fetch()
                .stream()
                .filter(r -> r.component1() != null)
                .collect(Collectors.toMap(r -> r.component1().toLocalDate(), r -> r.component2().doubleValue()));
        allDates.putAll(datesWithData);
        var select1 = allDates.entrySet().stream().map(r -> new SeriesValueDto(r.getKey(), r.getValue())).collect(Collectors.toList());
        //TODO change..

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

    /**
     * fetches the query from the database and returns it ready to be displayed by the frontend.
     *
     * @implNote this implementation only makes sense for relatively small queries as else a lot of dates have to be filled with zeroes.
     */
    private SeriesDto toSeriesDto(SelectConditionStep<Record2<Date, Integer>> select, String checked_texts, LocalDate since, Field<Date> date) {
        var sinceDate = since.atStartOfDay();
        var allDates = sinceDate.toLocalDate().datesUntil(LocalDate.now().plusDays(1))
                .collect(Collectors.toMap(localDate -> localDate, o -> 0));
        var datesWithData = select.groupBy(date)
                .fetch()
                .stream()
                .filter(r -> r.component1() != null)
                .collect(Collectors.toMap(r -> r.component1().toLocalDate(), Record2::component2));
        allDates.putAll(datesWithData);
        var res = allDates.entrySet().stream().map(r -> new SeriesValueDto(r.getKey(), r.getValue())).collect(Collectors.toList());
        return new SeriesDto(checked_texts, res);
    }

    public List<SeriesValueDto> getCumulativeCounts() {
        var record = dslContext.select(DSL.sum(AUDIO.DURATION), DSL.count())
                .from(AUDIO)
                .fetchOne();
        var audioDuration = record.component1();
        var audioCount = record.component2();
        var checkedRecordings = dslContext.select(DSL.count())
                .from(CHECKED_DATA_TUPLE)
                .where(CHECKED_DATA_TUPLE.TYPE.notEqual(CheckedDataTupleType.SKIPPED))
                .fetchOne().component1();
        var meanAudioDuration = audioDuration.divide(BigDecimal.valueOf(audioCount), 2, RoundingMode.HALF_DOWN);
        return List.of(new SeriesValueDto("checked recordings", checkedRecordings), new SeriesValueDto("number of recording", audioCount),
                new SeriesValueDto("mean recording duration in sec", meanAudioDuration), new SeriesValueDto("total recordings duration in h",
                        audioDuration.divide(BigDecimal.valueOf(3600), 2, RoundingMode.HALF_DOWN)));
    }

    public List<List<SeriesValueDto>> getTop3User() {
        var topRecording = dslContext.select(USER.USERNAME, DSL.count())
                .from(DATA_ELEMENT.innerJoin(AUDIO).on(AUDIO.DATA_ELEMENT_ID.eq(DATA_ELEMENT.ID))
                        .innerJoin(USER).on(DATA_ELEMENT.USER_ID.eq(USER.ID)))
                .groupBy(DATA_ELEMENT.USER_ID).orderBy(DSL.count().desc())
                .limit(3)
                .fetch()
                .stream()
                .filter(r -> r.component1() != null)
                .map(r -> new SeriesValueDto(r.component1(), r.component2()))
                .collect(Collectors.toList());
        var topChecking = dslContext.select(USER.USERNAME, DSL.count())
                .from(CHECKED_DATA_TUPLE
                        .innerJoin(USER).on(CHECKED_DATA_TUPLE.USER_ID.eq(USER.ID)))
                .groupBy(CHECKED_DATA_TUPLE.USER_ID).orderBy(DSL.count().desc())
                .limit(3)
                .fetch()
                .stream()
                .filter(r -> r.component1() != null)
                .map(r -> new SeriesValueDto(r.component1(), r.component2()))
                .collect(Collectors.toList());
        return List.of(topRecording, topChecking);
    }
}
