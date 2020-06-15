package ch.fhnw.speech_collection_app.features.base.admin.statistics;

public class SeriesValueDto {
    public final Object name;
    public final Number value;

    public SeriesValueDto(Object name, Number value) {
        this.name = name;
        this.value = value;
    }
}
