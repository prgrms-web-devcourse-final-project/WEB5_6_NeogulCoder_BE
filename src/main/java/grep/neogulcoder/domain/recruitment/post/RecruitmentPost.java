package grep.neogulcoder.domain.recruitment.post;

import grep.neogulcoder.domain.recruitment.RecruitmentPostStatus;
import grep.neogulcoder.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

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

    public void update(String subject, String content, int recruitmentCount, LocalDateTime expiredDateTime) {
        this.subject = subject;
        this.content = content;
        this.recruitmentCount = recruitmentCount;
        this.expiredDate = expiredDateTime;
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
