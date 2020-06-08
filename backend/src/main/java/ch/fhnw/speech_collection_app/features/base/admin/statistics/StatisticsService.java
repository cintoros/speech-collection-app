package ch.fhnw.speech_collection_app.features.base.admin.statistics;

import ch.fhnw.speech_collection_app.jooq.enums.CheckedDataElementType;
import ch.fhnw.speech_collection_app.jooq.enums.CheckedDataTupleType;
import org.jooq.DSLContext;
import org.jooq.Record2;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
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

    @Autowired
    public StatisticsService(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public List<SeriesDto> getStatisticsSince(LocalDate since) {

        var date = DSL.date(CHECKED_DATA_ELEMENT.CREATED_TIME);
        var select = dslContext.select(date, DSL.count())
                .from(CHECKED_DATA_ELEMENT)
                .where(CHECKED_DATA_ELEMENT.TYPE.notEqual(CheckedDataElementType.SKIPPED)
                        .and(CHECKED_DATA_ELEMENT.CREATED_TIME.ge(Timestamp.valueOf(since.atStartOfDay()))));
        var checkedTexts = toSeriesDto(select, "checked texts");

        date = DSL.date(CHECKED_DATA_TUPLE.CREATED_TIME);
        select = dslContext.select(date, DSL.count())
                .from(CHECKED_DATA_TUPLE)
                .where(CHECKED_DATA_TUPLE.TYPE.notEqual(CheckedDataTupleType.SKIPPED)
                        .and(CHECKED_DATA_TUPLE.CREATED_TIME.ge(Timestamp.valueOf(since.atStartOfDay()))));
        var checkedRecordings = toSeriesDto(select, "checked recordings");

        date = DSL.date(DATA_ELEMENT.CREATED_TIME);
        select = dslContext.select(date, DSL.count())
                .from(DATA_ELEMENT).innerJoin(AUDIO).on(AUDIO.DATA_ELEMENT_ID.eq(DATA_ELEMENT.ID))
                .where((DATA_ELEMENT.CREATED_TIME.ge(Timestamp.valueOf(since.atStartOfDay()))));
        var recordings = toSeriesDto(select, "recordings");

        return List.of(checkedTexts, checkedRecordings, recordings);
    }

    private SeriesDto toSeriesDto(SelectConditionStep<Record2<Date, Integer>> select, String checked_texts) {
        var res = select.fetch()
                .stream()
                .filter(dateIntegerRecord2 -> dateIntegerRecord2.component1() != null)
                .map(record -> new SeriesValueDto(record.component1().toString(), record.component2()))
                .collect(Collectors.toList());
        return new SeriesDto(checked_texts, res);
    }
}
