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

    @PostMapping("recording")
    public void postRecording(@PathVariable long groupId, @RequestParam String recording,
            @RequestParam MultipartFile file) throws IOException {
        userGroupService.postRecording(groupId, objectMapper.readValue(recording, RecordingDto.class), file);
    }

    @PostMapping("element/{dataElementId}/checked")
    public void postCheckedDataElement(@PathVariable long groupId, @PathVariable long dataElementId,
            @RequestParam CheckedDataElementType type) {
        userGroupService.postCheckedDataElement(groupId, dataElementId, type);
    }

    @PostMapping("excerpt")
    public long postExcerpt(@PathVariable long groupId, @PathVariable long idOfOtherDataElement,
            @RequestParam String text, @RequestParam String data_element) throws IOException {
        return userGroupService.postExcerpt(groupId, idOfOtherDataElement, objectMapper.readValue(text, TextDto.class),
                objectMapper.readValue(data_element, DataElementDto.class));
    }

    @PostMapping("next")
    public ReturnWrapper getNext(@PathVariable long groupId, @RequestParam boolean textAllowed,
            @RequestParam boolean audioAllowed, @RequestParam boolean imageAllowed) {
        return userGroupService.getNext(groupId, textAllowed, audioAllowed, imageAllowed);
    }

    @GetMapping("excerpt")
    public TextDto getExcerpt(@PathVariable long groupId) {
        return userGroupService.getExcerpt(groupId);
    }

    @GetMapping("image_dto")
    public ImageDto getImageDto(@PathVariable long groupId) {
        return userGroupService.getImageDto(groupId);
    }

    @PostMapping("occurrence/check")
    public void postCheckedOccurrence(@PathVariable long groupId, @RequestBody CheckedOccurrence checkedOccurrence) {
        userGroupService.postCheckedOccurrence(groupId, checkedOccurrence);
    }

    @GetMapping("occurrence/next")
    public List<Occurrence> getNextOccurrences(@PathVariable long groupId) {
        return userGroupService.getNextOccurrences(groupId);
    }

    @GetMapping(value = "occurrence/audio/{dataElementId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public byte[] getAudio(@PathVariable long groupId, @PathVariable long dataElementId) throws IOException {
        return userGroupService.getAudio(groupId, dataElementId);
    }

    @GetMapping(value = "image/{dataElementId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public byte[] getImage(@PathVariable long groupId, @PathVariable long dataElementId) throws IOException {
        return userGroupService.getImage(groupId, dataElementId);
    }
}
