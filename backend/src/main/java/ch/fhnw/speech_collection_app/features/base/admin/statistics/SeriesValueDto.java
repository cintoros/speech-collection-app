package ch.fhnw.speech_collection_app.features.base.admin.statistics;

import java.time.LocalDate;

public class SeriesValueDto {
    public final LocalDate name;
    public final Integer value;

    public SeriesValueDto(LocalDate name, Integer value) {
        this.name = name;
        this.value = value;
    }
}
