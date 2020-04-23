package ch.fhnw.speech_collection_app.features.base.user_group;

import ch.fhnw.speech_collection_app.jooq.enums.CheckedDataElementType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/user_group/{groupId}/")
public class UserGroupRestApiController {
    private final UserGroupService userGroupService;
    private final ObjectMapper objectMapper;

    @Autowired
    public UserGroupRestApiController(UserGroupService userGroupService, ObjectMapper objectMapper) {
        this.userGroupService = userGroupService;
        this.objectMapper = objectMapper;
    }

    //TODO not sure how we want to upload design the one where we also translate the text -> one endpoint/requests or two
    @PostMapping("recording")
    public void postRecording(@PathVariable long groupId, @RequestParam String recording, @RequestParam MultipartFile file) throws IOException {
        userGroupService.postRecording(groupId, objectMapper.readValue(recording, RecordingDto.class), file);
    }

    @PostMapping("element/{elementId}/checked")
    public void postCheckedDataElement(@PathVariable long groupId, @PathVariable long elementId, @RequestParam CheckedDataElementType type) {
        userGroupService.postCheckedDataElement(groupId, elementId, type);
    }

    @GetMapping("excerpt")
    public TextDto getExcerpt(@PathVariable long groupId) {
        return userGroupService.getExcerpt(groupId);
    }

    @PostMapping("occurrence/check")
    public void postCheckedOccurrence(@PathVariable long groupId, @RequestBody CheckedOccurrence checkedOccurrence) {
        userGroupService.postCheckedOccurrence(groupId, checkedOccurrence);
    }

    @GetMapping("occurrence/next")
    public List<Occurrence> getNextOccurrences(@PathVariable long groupId) {
        return userGroupService.getNextOccurrences(groupId);
    }

    @GetMapping(value = "occurrence/audio/{elementId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public byte[] getAudio(@PathVariable long groupId, @PathVariable long elementId) throws IOException {
        return userGroupService.getAudio(groupId, elementId);
    }
}
