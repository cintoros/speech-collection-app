package ch.fhnw.speech_collection_app.admin;

import ch.fhnw.speech_collection_app.jooq.Tables;
import ch.fhnw.speech_collection_app.jooq.tables.pojos.UserGroup;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/")
public class AdminRestApiController {
    private final DSLContext dslContext;

    @Autowired
    public AdminRestApiController(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @PostMapping("user_group")
    public void postUserGroup(@RequestBody UserGroup userGroup) {
        dslContext.newRecord(Tables.USER_GROUP, userGroup).store();
    }

    @GetMapping("user_group")
    public List<UserGroup> getUserGroup() {
        return dslContext.selectFrom(Tables.USER_GROUP).fetchInto(UserGroup.class);
    }

}
