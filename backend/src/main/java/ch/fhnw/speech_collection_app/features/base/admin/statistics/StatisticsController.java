package ch.fhnw.speech_collection_app.features.base.admin.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class StatisticsController {
    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @RequestMapping("admin/statistics/basic")
    public List<SeriesDto> getBasicStatisticsSince(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime since) {
        return statisticsService.getStatisticsSince(since.toLocalDate());
    }

    @RequestMapping("admin/statistics/audio_duration_statistics")
    public List<SeriesDto> getAudioDurationStatisticsSince(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime since) {
        return statisticsService.getAudioDurationStatisticsSince(since.toLocalDate());
    }

    @RequestMapping("statistics/top_3_user")
    public List<List<SeriesValueDto>> getTop3User() {
        return statisticsService.getTop3User();
    }

    @RequestMapping("statistics/cumulative_counts")
    public List<SeriesValueDto> getCumulativeCounts() {
        return statisticsService.getCumulativeCounts();
    }
}
