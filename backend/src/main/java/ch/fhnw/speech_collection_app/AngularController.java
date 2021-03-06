package ch.fhnw.speech_collection_app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AngularController {
    //see https://stackoverflow.com/a/53376572
    @RequestMapping({"/{[path:[^\\\\.]*}", "/admin/{[path:[^\\\\.]*}"})
    public String forwardAngular() {
        return "forward:/index.html";
    }
}
