package ch.fhnw.speech_collection_app.features.base.pagination;

public class PaginationRequestDto {
    public final String sort;
    public final String direction;
    public final long pageIndex;
    public final long pageSize;

    public PaginationRequestDto(String sort, String direction, long pageIndex, long pageSize) {
        this.sort = sort;
        this.direction = direction;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }
}
