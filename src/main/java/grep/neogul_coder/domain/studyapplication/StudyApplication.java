package grep.neogul_coder.domain.studyapplication;

import grep.neogul_coder.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;

@Entity
public class StudyApplication extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studyApplicationId;

    @Column(nullable = false)
    private Long recruitmentPostId;

    private long userId;

    @Column(nullable = false)
    private String applicationReason;

    private boolean isRead;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @Builder
    private StudyApplication(Long recruitmentPostId, String applicationReason, ApplicationStatus status,
                             boolean isRead, long userId) {
        this.recruitmentPostId = recruitmentPostId;
        this.applicationReason = applicationReason;
        this.isRead = isRead;
        this.status = status;
        this.userId = userId;
    }

    protected StudyApplication() {}
}
