package ch.fhnw.speech_collection_app.features.base.user_group;

import ch.fhnw.speech_collection_app.jooq.enums.CheckedDataElementType;
import ch.fhnw.speech_collection_app.jooq.enums.DataTupleType;
import ch.fhnw.speech_collection_app.jooq.tables.pojos.DataElement;
import ch.fhnw.speech_collection_app.jooq.tables.pojos.Image;
import ch.fhnw.speech_collection_app.jooq.tables.pojos.Text;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user_group/{groupId}/")
public class UserGroupRestApiController {
    private final UserGroupService userGroupService;
    private final ObjectMapper objectMapper;
    private final AchievementsService achievementsService;

    @Autowired
    public UserGroupRestApiController(UserGroupService userGroupService, ObjectMapper objectMapper,
                                      AchievementsService achievementsService) {
        this.userGroupService = userGroupService;
        this.objectMapper = objectMapper;
        this.achievementsService = achievementsService;
    }

    @PostMapping("recording")
    public void postRecording(@PathVariable long groupId, @RequestParam String recording,
                              @RequestParam MultipartFile file, @RequestParam String otherDataElement,
                              @RequestParam String otherElementType) throws IOException {
        userGroupService.postRecording(groupId, objectMapper.readValue(recording, RecordingDto.class), file,
                objectMapper.readValue(otherDataElement, DataElement.class),
                ElementType.valueOf(otherElementType));
    }

    @PostMapping("element/{dataElementId}/checked")
    public void postCheckedDataElement(
            @PathVariable long groupId, @PathVariable long dataElementId, @RequestParam CheckedDataElementType type) {
        userGroupService.postCheckedDataElement(groupId, dataElementId, type);
    }

    @PostMapping("excerpt")
    public ReturnWrapper postExcerpt(
            @PathVariable long groupId, @RequestParam String text, @RequestParam String otherDataElement,
            @RequestParam String otherElementType) throws IOException {
        return userGroupService.postExcerpt(groupId, objectMapper.readValue(text, Text.class),
                objectMapper.readValue(otherDataElement, DataElement.class),
                ElementType.valueOf(otherElementType));
    }

    @PostMapping("next")
    public ReturnWrapper getNext(@PathVariable long groupId, @RequestParam String selectedElement) {
        System.out.println(selectedElement);
        return userGroupService.getNext(groupId, ElementType.valueOf(selectedElement));
    }

    @GetMapping("excerpt")
    public Text getExcerpt(@PathVariable long groupId) {
        return userGroupService.getExcerpt(groupId);
    }

    @GetMapping("textDto/{dataElementId}")
    public Text getTextDto(@PathVariable long dataElementId) {
        return userGroupService.getTextDto(dataElementId);
    }

    @GetMapping("image_dto")
    public Image getImageDto(@PathVariable long groupId) {
        return userGroupService.getImage(groupId);
    }

    @PostMapping("occurrence/check")
    public void postCheckedOccurrence(@PathVariable long groupId, @RequestBody CheckedOccurrence checkedOccurrence) {
        userGroupService.postCheckedOccurrence(groupId, checkedOccurrence);
    }

    @GetMapping("occurrence/next")
    public Optional<Occurrence> getNextOccurrences(@PathVariable long groupId) {
        return userGroupService.getNextOccurrence(groupId);
    }

    @PostMapping("check-next")
    public CheckWrapper getNextTuple(@PathVariable long groupId, @RequestParam String selectedTupleType) {
        return userGroupService.getNextTuple(groupId, DataTupleType.valueOf(selectedTupleType));
    }

    @GetMapping("check-next")
    public CheckWrapper getNextTuple(@PathVariable long groupId) {
        return userGroupService.getNextTuple(groupId);
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

    @GetMapping(value = "achievements/active")
    public List<AchievementWrapper> getActiveAchievements() {
        return achievementsService.getActiveAchievements();
    }

    @GetMapping(value = "achievements/nonactive")
    public List<AchievementWrapper> getNonActiveAchievements() {
        return achievementsService.getNonActiveAchievements();
    }

    @GetMapping(value = "achievement/active")
    public AchievementWrapper getActiveAchievement() {
        return achievementsService.getActiveAchievement();
    }

    @GetMapping(value = "numNewAchievements")
    public Long numberOfNewAchievements() {
        return achievementsService.numberOfNewAchievements();
    }

    @GetMapping(value = "map")
    public List<MapWrapper> getMapPercent() {
        return achievementsService.getMapPercent();
    }
}
