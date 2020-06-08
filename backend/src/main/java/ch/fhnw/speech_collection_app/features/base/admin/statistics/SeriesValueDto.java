package ch.fhnw.speech_collection_app.features.base.admin.statistics;

public class SeriesValueDto {
    public final String name;
    public final Integer value;

    public SeriesValueDto(String name, Integer value) {
        this.name = name;
        this.value = value;
    }
}
