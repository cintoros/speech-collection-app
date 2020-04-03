package ch.fhnw.speech_collection_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DevTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevTestApplication.class, "--spring.profiles.active=dev-test");
    }
}
