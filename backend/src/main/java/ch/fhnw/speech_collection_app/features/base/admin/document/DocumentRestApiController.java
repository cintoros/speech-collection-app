package ch.fhnw.speech_collection_app.features.base.admin.document;

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

    @GetMapping
    public List<Source> getDocumentSource(@PathVariable long groupId) {
        return documentService.getDocumentSource(groupId);
    }

    @DeleteMapping("{originalTextId}")
    public void deleteSource(@PathVariable long groupId, @PathVariable long originalTextId) {
        documentService.deleteSource(groupId, originalTextId);
    }

    @GetMapping("{originalTextId}/element")
    public List<TextElementDto> getTextElement(@PathVariable long groupId, @PathVariable long originalTextId) {
        return documentService.getTextElement(groupId, originalTextId);
    }

    @DeleteMapping("{originalTextId}/element/{excerptId}")
    public void deleteElement(@PathVariable long groupId, @PathVariable long originalTextId, @PathVariable long excerptId) {
        documentService.deleteElement(groupId, originalTextId, excerptId);
    }
}
