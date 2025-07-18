package grep.neogul_coder.domain.recruitment.post;

import grep.neogul_coder.domain.recruitment.RecruitmentPostStatus;
import grep.neogul_coder.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class RecruitmentPost extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long userId;
    private long studyId;

    private String subject;
    private String content;
    private int recruitmentCount;
    private LocalDateTime expiredDate;

    @Enumerated(EnumType.STRING)
    private RecruitmentPostStatus status;

    @Builder
    private RecruitmentPost(long studyId, String subject, String content, long userId,
        int recruitmentCount, LocalDateTime expiredDate, RecruitmentPostStatus status) {
        this.studyId = studyId;
        this.userId = userId;
        this.subject = subject;
        this.content = content;
        this.recruitmentCount = recruitmentCount;
        this.expiredDate = expiredDate;
        this.status = status;
    }

    protected RecruitmentPost() {
    }

    public void update(String subject, String content, int recruitmentCount) {
        this.subject = subject;
        this.content = content;
        this.recruitmentCount = recruitmentCount;
    }

    public boolean isNotOwnedBy(long userId) {
        return this.userId != userId;
    }

    public void updateStatus(RecruitmentPostStatus status) {
        this.status = status;
    }

    public void delete() {
        this.activated = false;
    }
}
