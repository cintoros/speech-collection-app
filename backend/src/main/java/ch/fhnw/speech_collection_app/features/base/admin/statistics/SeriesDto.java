package ch.fhnw.speech_collection_app.features.base.admin.statistics;

import java.util.List;

public class SeriesDto {
    public final String name;
    public final List<SeriesValueDto> series;

    public SeriesDto(String name, List<SeriesValueDto> series) {
        this.name = name;
        this.series = series;
    }
}
