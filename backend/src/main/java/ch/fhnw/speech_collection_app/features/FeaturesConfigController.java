package ch.fhnw.speech_collection_app.features;

import ch.fhnw.speech_collection_app.config.SpeechCollectionAppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeaturesConfigController {
    private final SpeechCollectionAppConfig speechCollectionAppConfig;

    @Autowired
    public FeaturesConfigController(SpeechCollectionAppConfig speechCollectionAppConfig) {
        this.speechCollectionAppConfig = speechCollectionAppConfig;
    }

    @GetMapping("/api/public/features")
    public SpeechCollectionAppConfig.Features getProperties() {
        return speechCollectionAppConfig.getFeatures();
    }
}
