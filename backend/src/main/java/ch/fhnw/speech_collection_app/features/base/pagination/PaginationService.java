package ch.fhnw.speech_collection_app.features.base.pagination;

import org.springframework.stereotype.Service;

@Service
public class PaginationService {
void addPagination(){
//    TODO add abstract implementation so it is possible to add pagination to almost anything
    //NOTE: this probably is only used for the admin stuff as the user itself never load more than 1/10 objects
    //TODO NOTE: test the implementation on the CommonVoice source at it has a stupid amount of data
    // -> maybe we need the after object id x instead of the page offset to increase performance not sure about this.
}
}
