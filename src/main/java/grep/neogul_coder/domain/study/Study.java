package grep.neogul_coder.domain.study;

import grep.neogul_coder.domain.study.enums.Category;
import grep.neogul_coder.domain.study.enums.StudyType;
import grep.neogul_coder.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

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

    private LocalDate startDate;

    private LocalDate endDate;

    private String introduction;

    private String imageUrl;

    private boolean isFinished;

    protected Study() {}

    @Builder
    private Study(String name, Category category, int capacity, StudyType studyType, String location, LocalDate startDate, LocalDate endDate, String introduction, String imageUrl) {
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

    public long calculateRemainSlots(long currentCount) {
        return this.capacity - currentCount;
    }
}
