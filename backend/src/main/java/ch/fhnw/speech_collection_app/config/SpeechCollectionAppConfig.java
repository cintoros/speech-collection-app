package ch.fhnw.speech_collection_app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.nio.file.Path;

@Component
@ConfigurationProperties(prefix = "speech-collection-app", ignoreUnknownFields = false)
@Validated
public class SpeechCollectionAppConfig {
    @NotNull
    private Path basePath;
    @NotNull
    private String condaExec;
    @NotNull
    private Long publicGroupId;
    @NotNull
    private Long minNumChecks;
    @NotNull
    private Features features;
    @NotNull
    private String baseUrl;
    private String fromEmail;

    public Path getBasePath() {
        return basePath;
    }

    public void setBasePath(Path basePath) {
        this.basePath = basePath;
    }

    public String getCondaExec() {
        return condaExec;
    }

    public void setCondaExec(String condaExec) {
        this.condaExec = condaExec;
    }

    public Long getPublicGroupId() {
        return publicGroupId;
    }

    public void setPublicGroupId(Long publicGroupId) {
        this.publicGroupId = publicGroupId;
    }

    public Features getFeatures() {
        return features;
    }

    public void setFeatures(Features features) {
        this.features = features;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public Long getMinNumChecks() {
        return minNumChecks;
    }

    public void setMinNumChecks(Long minNumChecks) {
        this.minNumChecks = minNumChecks;
    }

    public static class Features {
        private boolean emailIntegration;

        public boolean isEmailIntegration() {
            return emailIntegration;
        }

        public void setEmailIntegration(boolean emailIntegration) {
            this.emailIntegration = emailIntegration;
        }
    }
}
