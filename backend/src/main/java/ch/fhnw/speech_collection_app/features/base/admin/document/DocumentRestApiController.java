package ch.fhnw.speech_collection_app.features.base.admin.document;

import ch.fhnw.speech_collection_app.jooq.tables.pojos.Source;
import ch.fhnw.speech_collection_app.jooq.tables.pojos.Text;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

//TODO rename all endpoints/methods once everything is ready
@RestController
@RequestMapping("/api/user_group/{groupId}/admin/original_text/")
public class DocumentRestApiController {
    private final DocumentService documentService;

    public DocumentRestApiController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping
    public void postOriginalText(@PathVariable long groupId, @RequestParam long domainId, @RequestParam MultipartFile[] files, @RequestParam String documentLicence) {
        documentService.postOriginalText(groupId, domainId, files, documentLicence);
    }

    @GetMapping
    public List<Source> getOriginalText(@PathVariable long groupId) {
        return documentService.getOriginalText(groupId);
    }

    @DeleteMapping("{originalTextId}")
    public void deleteOriginalText(@PathVariable long groupId, @PathVariable long originalTextId) {
        documentService.deleteOriginalText(groupId, originalTextId);
    }

    @GetMapping("{originalTextId}/excerpt")
    public List<Text> getExcerpt(@PathVariable long groupId, @PathVariable long originalTextId) {
        return documentService.getExcerpt(groupId, originalTextId);
    }

    @DeleteMapping("{originalTextId}/excerpt/{excerptId}")
    public void deleteExcerpt(@PathVariable long groupId, @PathVariable long originalTextId, @PathVariable long excerptId) {
        documentService.deleteExcerpt(groupId, originalTextId, excerptId);
    }
}
