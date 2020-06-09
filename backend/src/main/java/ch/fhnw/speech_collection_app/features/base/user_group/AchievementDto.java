package ch.fhnw.speech_collection_app.features.base.user_group;

import java.sql.Timestamp;
import java.util.Objects;

import ch.fhnw.speech_collection_app.jooq.enums.AchievementsDependsOn;

public class AchievementDto {
    private Long id;
    private Long domain_id;
    private String name;
    private String batch_name;
    private String description;
    private Timestamp start_time;
    private Timestamp end_time;
    private Long points_lvl1;
    private Long points_lvl2;
    private Long points_lvl3;
    private Long points_lvl4;
    private AchievementsDependsOn depends_on;

    public AchievementDto() {
    }

    public AchievementDto(Long id, Long domain_id, String name, String batch_name, String description,
            Timestamp start_time, Timestamp end_time, Long points_lvl1, Long points_lvl2, Long points_lvl3,
            Long points_lvl4, AchievementsDependsOn depends_on) {
        this.id = id;
        this.domain_id = domain_id;
        this.name = name;
        this.batch_name = batch_name;
        this.description = description;
        this.start_time = start_time;
        this.end_time = end_time;
        this.points_lvl1 = points_lvl1;
        this.points_lvl2 = points_lvl2;
        this.points_lvl3 = points_lvl3;
        this.points_lvl4 = points_lvl4;
        this.depends_on = depends_on;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDomain_id() {
        return this.domain_id;
    }

    public void setDomain_id(Long domain_id) {
        this.domain_id = domain_id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBatch_name() {
        return this.batch_name;
    }

    public void setBatch_name(String batch_name) {
        this.batch_name = batch_name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getStart_time() {
        return this.start_time;
    }

    public void setStart_time(Timestamp start_time) {
        this.start_time = start_time;
    }

    public Timestamp getEnd_time() {
        return this.end_time;
    }

    public void setEnd_time(Timestamp end_time) {
        this.end_time = end_time;
    }

    public Long getPoints_lvl1() {
        return this.points_lvl1;
    }

    public void setPoints_lvl1(Long points_lvl1) {
        this.points_lvl1 = points_lvl1;
    }

    public Long getPoints_lvl2() {
        return this.points_lvl2;
    }

    public void setPoints_lvl2(Long points_lvl2) {
        this.points_lvl2 = points_lvl2;
    }

    public Long getPoints_lvl3() {
        return this.points_lvl3;
    }

    public void setPoints_lvl3(Long points_lvl3) {
        this.points_lvl3 = points_lvl3;
    }

    public Long getPoints_lvl4() {
        return this.points_lvl4;
    }

    public void setPoints_lvl4(Long points_lvl4) {
        this.points_lvl4 = points_lvl4;
    }

    public AchievementsDependsOn getDepends_on() {
        return this.depends_on;
    }

    public void setDepends_on(AchievementsDependsOn depends_on) {
        this.depends_on = depends_on;
    }

    public AchievementDto id(Long id) {
        this.id = id;
        return this;
    }

    public AchievementDto domain_id(Long domain_id) {
        this.domain_id = domain_id;
        return this;
    }

    public AchievementDto name(String name) {
        this.name = name;
        return this;
    }

    public AchievementDto batch_name(String batch_name) {
        this.batch_name = batch_name;
        return this;
    }

    public AchievementDto description(String description) {
        this.description = description;
        return this;
    }

    public AchievementDto start_time(Timestamp start_time) {
        this.start_time = start_time;
        return this;
    }

    public AchievementDto end_time(Timestamp end_time) {
        this.end_time = end_time;
        return this;
    }

    public AchievementDto points_lvl1(Long points_lvl1) {
        this.points_lvl1 = points_lvl1;
        return this;
    }

    public AchievementDto points_lvl2(Long points_lvl2) {
        this.points_lvl2 = points_lvl2;
        return this;
    }

    public AchievementDto points_lvl3(Long points_lvl3) {
        this.points_lvl3 = points_lvl3;
        return this;
    }

    public AchievementDto points_lvl4(Long points_lvl4) {
        this.points_lvl4 = points_lvl4;
        return this;
    }

    public AchievementDto depends_on(AchievementsDependsOn depends_on) {
        this.depends_on = depends_on;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof AchievementDto)) {
            return false;
        }
        AchievementDto achievementDto = (AchievementDto) o;
        return Objects.equals(id, achievementDto.id) && Objects.equals(domain_id, achievementDto.domain_id)
                && Objects.equals(name, achievementDto.name) && Objects.equals(batch_name, achievementDto.batch_name)
                && Objects.equals(description, achievementDto.description)
                && Objects.equals(start_time, achievementDto.start_time)
                && Objects.equals(end_time, achievementDto.end_time)
                && Objects.equals(points_lvl1, achievementDto.points_lvl1)
                && Objects.equals(points_lvl2, achievementDto.points_lvl2)
                && Objects.equals(points_lvl3, achievementDto.points_lvl3)
                && Objects.equals(points_lvl4, achievementDto.points_lvl4)
                && Objects.equals(depends_on, achievementDto.depends_on);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, domain_id, name, batch_name, description, start_time, end_time, points_lvl1,
                points_lvl2, points_lvl3, points_lvl4, depends_on);
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", domain_id='" + getDomain_id() + "'" + ", name='" + getName() + "'"
                + ", batch_name='" + getBatch_name() + "'" + ", description='" + getDescription() + "'"
                + ", start_time='" + getStart_time() + "'" + ", end_time='" + getEnd_time() + "'" + ", points_lvl1='"
                + getPoints_lvl1() + "'" + ", points_lvl2='" + getPoints_lvl2() + "'" + ", points_lvl3='"
                + getPoints_lvl3() + "'" + ", points_lvl4='" + getPoints_lvl4() + "'" + ", depends_on='"
                + getDepends_on() + "'" + "}";
    }

}
