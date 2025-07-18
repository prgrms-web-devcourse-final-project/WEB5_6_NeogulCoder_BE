package grep.neogul_coder.domain.study;

import grep.neogul_coder.domain.study.enums.Category;
import grep.neogul_coder.domain.study.enums.StudyType;
import grep.neogul_coder.global.entity.BaseEntity;
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

    private boolean isFinished;

    protected Study() {}

    @Builder
    private Study(String name, Category category, int capacity, StudyType studyType, String location, LocalDateTime startDate, LocalDateTime endDate, String introduction, String imageUrl) {
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
        this.isFinished = false;
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

    public void decreaseMemberCount() {
        currentCount--;
    }
}
