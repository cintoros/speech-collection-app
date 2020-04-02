package ch.fhnw.labeling_tool.features;

import ch.fhnw.labeling_tool.config.LabelingToolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeaturesConfigController {
    private final LabelingToolConfig labelingToolConfig;

    @Autowired
    public FeaturesConfigController(LabelingToolConfig labelingToolConfig) {
        this.labelingToolConfig = labelingToolConfig;
    }

    @GetMapping("/api/public/features")
    public LabelingToolConfig.Features getProperties() {
        return labelingToolConfig.getFeatures();
    }
}
