package ch.fhnw.speech_collection_app;

import ch.fhnw.speech_collection_app.config.SpeechCollectionAppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;

@Component
public class SetupApplicationRunner implements ApplicationRunner {
    private final SpeechCollectionAppConfig speechCollectionAppConfig;

    @Autowired
    public SetupApplicationRunner(SpeechCollectionAppConfig speechCollectionAppConfig) {
        this.speechCollectionAppConfig = speechCollectionAppConfig;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String[] a = {"extracted_text", "original_text", "source", "recording", "images", "text_audio"};
        for (String s : a) {
            var path = speechCollectionAppConfig.getBasePath().resolve(s);
            Files.createDirectories(path);
        }

    }
}
