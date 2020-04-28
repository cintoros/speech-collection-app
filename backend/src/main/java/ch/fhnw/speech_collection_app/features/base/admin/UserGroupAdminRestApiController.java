package ch.fhnw.speech_collection_app.features.base.admin;

import ch.fhnw.speech_collection_app.features.base.user_group.OverviewOccurrence;
import ch.fhnw.speech_collection_app.jooq.enums.UserGroupRoleRole;
import ch.fhnw.speech_collection_app.jooq.tables.pojos.Domain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/user_group/{groupId}/admin")
public class UserGroupAdminRestApiController {
    private final UserGroupAdminService userGroupAdminService;

    @Autowired
    public UserGroupAdminRestApiController(UserGroupAdminService userGroupAdminService) {
        this.userGroupAdminService = userGroupAdminService;
    }

    @PutMapping("text_audio")
    public void putTextAudio(@PathVariable long groupId, @RequestBody TextAudioDto textAudio) {
        userGroupAdminService.putTextAudio(groupId, textAudio);
    }

    @GetMapping("text_audio/{dataElementId}")
    public TextAudioDto getTextAudio(@PathVariable long groupId, @PathVariable Long dataElementId) {
        return userGroupAdminService.getTextAudio(groupId, dataElementId);
    }

    @GetMapping(value = "text_audio/audio/{textAudioId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public byte[] getTextAudioAudio(@PathVariable long groupId, @PathVariable long textAudioId) throws IOException {
        return userGroupAdminService.getTextAudioAudio(groupId, textAudioId);
    }

    @GetMapping("overview_occurrence")
    public List<OverviewOccurrence> getOverviewOccurrence(@PathVariable long groupId) {
        return userGroupAdminService.getOverviewOccurrence(groupId);
    }

    @DeleteMapping("user_group_role")
    public void deleteUserGroupRole(@RequestParam long id) {
        userGroupAdminService.deleteUserGroupRole(id);
    }

    @PostMapping("user_group_role")
    public boolean postUserGroupRole(@PathVariable long groupId, @RequestParam String email, @RequestParam UserGroupRoleRole mode) {
        return userGroupAdminService.postUserGroupRole(email, mode, groupId);
    }

    @GetMapping("user_group_role")
    public List<UserGroupRoleDto> getUserGroupRole(@PathVariable long groupId, @RequestParam UserGroupRoleRole mode) {
        return userGroupAdminService.getUserGroupRole(mode, groupId);
    }

    @GetMapping("domain")
    public List<Domain> getDomain() {
        return userGroupAdminService.getDomain();
    }

    @PutMapping("description")
    public void putDescription(@PathVariable long groupId, @RequestBody String description) {
        userGroupAdminService.putDescription(groupId, description);
    }

}
