package ch.fhnw.speech_collection_app.admin;

import ch.fhnw.speech_collection_app.jooq.tables.daos.UserGroupDao;
import ch.fhnw.speech_collection_app.jooq.tables.pojos.UserGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/")
public class AdminRestApiController {
    private final UserGroupDao userGroupDao;

    @Autowired
    public AdminRestApiController(UserGroupDao userGroupDao) {
        this.userGroupDao = userGroupDao;
    }

    @PostMapping("user_group")
    public void postUserGroup(@RequestBody UserGroup userGroup) {
        userGroupDao.insert(userGroup);
    }

    @GetMapping("user_group")
    public List<UserGroup> getUserGroup() {
        return userGroupDao.findAll();
    }

}
