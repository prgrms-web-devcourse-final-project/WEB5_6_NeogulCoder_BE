package grep.neogulcoder.domain.studyapplication;

import grep.neogulcoder.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class StudyApplication extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public void approve() {
        this.status = ApplicationStatus.APPROVED;
    }

    public void reject() {
        this.status = ApplicationStatus.REJECTED;
    }
}
