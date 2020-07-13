package ch.fhnw.speech_collection_app.features.base.admin.document;

import ch.fhnw.speech_collection_app.features.base.admin.pagination.PaginationResultDto;
import ch.fhnw.speech_collection_app.jooq.tables.pojos.Source;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/user_group/{groupId}/admin/source/")
public class DocumentRestApiController {
    private final DocumentService documentService;

    public DocumentRestApiController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping
    public void postDocumentSource(@PathVariable long groupId, @RequestParam long domainId, @RequestParam MultipartFile[] files, @RequestParam String documentLicence) {
        documentService.postDocumentSource(groupId, domainId, files, documentLicence);
    }

    @PostMapping("image")
    public void postImageSource(@PathVariable long groupId, @RequestParam long domainId, @RequestParam MultipartFile[] files, @RequestParam String documentLicence) {
        documentService.postImageSource(groupId, domainId, files, documentLicence);
    }

    @GetMapping
    public List<Source> getDocumentSource(@PathVariable long groupId) {
        return documentService.getDocumentSource(groupId);
    }

    @DeleteMapping("{sourceId}")
    public void deleteSource(@PathVariable long groupId, @PathVariable long sourceId) {
        documentService.deleteSource(groupId, sourceId);
    }

    @GetMapping("{dataElementId}/element")
    public PaginationResultDto<TextElementDto> getTextElement(
            @PathVariable long groupId, @PathVariable long dataElementId, @RequestParam long lastId,
            @RequestParam long pageSize, @RequestParam boolean before
    ) {
        return documentService.getTextElement(groupId, dataElementId, lastId, pageSize,before);
    }

    @DeleteMapping("{sourceId}/element/{dataElementId}")
    public void deleteDataElement(@PathVariable long groupId, @PathVariable long sourceId, @PathVariable long dataElementId) {
        documentService.deleteDataElement(groupId, sourceId, dataElementId);
    }
}
