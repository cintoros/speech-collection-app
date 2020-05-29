package ch.fhnw.speech_collection_app.features.base.pagination;

import java.util.List;

public class PaginationResultDto<T> {
    public final List<T> items;
    public final int totalCount;

    public PaginationResultDto(List<T> items, int totalCount) {
        this.items = items;
        this.totalCount = totalCount;
    }
}
