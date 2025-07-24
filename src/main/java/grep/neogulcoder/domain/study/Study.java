package grep.neogulcoder.domain.study;

import grep.neogulcoder.domain.study.enums.Category;
import grep.neogulcoder.domain.study.enums.StudyType;
import grep.neogulcoder.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class Study extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long originStudyId;

    private String name;

    @Enumerated(EnumType.STRING)
    private Category category;

    private int capacity;

    private int currentCount;

    @Enumerated(EnumType.STRING)
    private StudyType studyType;

    private String location;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String introduction;

    private String imageUrl;

    private boolean extended;

    private boolean finished;

    protected Study() {
    }

    @Builder
    private Study(Long originStudyId, String name, Category category, int capacity, StudyType studyType, String location,
                  LocalDateTime startDate, LocalDateTime endDate, String introduction, String imageUrl) {
        this.originStudyId = originStudyId;
        this.name = name;
        this.category = category;
        this.capacity = capacity;
        this.currentCount = 1;
        this.studyType = studyType;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.introduction = introduction;
        this.imageUrl = imageUrl;
        this.extended = false;
        this.finished = false;
    }

    public void update(String name, Category category, int capacity, StudyType studyType,
                       String location, LocalDateTime startDate, String introduction, String imageUrl) {
        this.name = name;
        this.category = category;
        this.capacity = capacity;
        this.studyType = studyType;
        this.location = location;
        this.startDate = startDate;
        this.introduction = introduction;
        this.imageUrl = imageUrl;
    }

    public boolean isStarted() {
        return this.startDate.isBefore(LocalDateTime.now());
    }

    public void delete() {
        this.activated = false;
    }

    public long calculateRemainSlots(long currentCount) {
        return this.capacity - currentCount;
    }

    public void increaseMemberCount() {
        currentCount++;
    }

    public void decreaseMemberCount() {
        currentCount--;
    }

    public boolean alreadyExtended() {
        return this.extended;
    }

    public void extend() {
        this.extended = true;
    }

    public void finish() {
        this.finished = true;
    }

    public boolean isReviewableAt(LocalDateTime currentDateTime) {
        LocalDateTime reviewableDateTime = this.endDate.plusDays(7);

        return (currentDateTime.isEqual(this.endDate) || currentDateTime.isAfter(this.endDate)) &&
                (currentDateTime.isEqual(reviewableDateTime) || currentDateTime.isBefore(reviewableDateTime));
    }
}
