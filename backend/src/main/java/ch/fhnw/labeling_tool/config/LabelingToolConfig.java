package ch.fhnw.labeling_tool.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
@ConfigurationProperties(prefix = "labeling-tool", ignoreUnknownFields = false)
public class LabelingToolConfig {
    private Path basePath;
    private String condaExec;
    private Long publicGroupId;
    private Features features;

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
