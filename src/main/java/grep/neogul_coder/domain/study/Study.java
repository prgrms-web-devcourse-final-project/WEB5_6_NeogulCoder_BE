package grep.neogul_coder.domain.study;

import grep.neogul_coder.domain.study.enums.Category;
import grep.neogul_coder.domain.study.enums.StudyMemberRole;
import grep.neogul_coder.domain.study.enums.StudyType;
import grep.neogul_coder.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class Study extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studyId;

    private Long originStudyId;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private int currentCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StudyType studyType;

    private String location;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    private String introduction;

    @Column(nullable = false)
    private String imageUrl;

    private boolean isFinished;

    protected Study() {}

    @Builder
    public Study(String name, Category category, int capacity, StudyType studyType, String location, LocalDate startDate, LocalDate endDate, String introduction, String imageUrl) {
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

    public StudyMember createLeader(Long userId) {
        return StudyMember.builder()
            .study(this)
            .userId(userId)
            .role(StudyMemberRole.LEADER)
            .build();
    }
}
