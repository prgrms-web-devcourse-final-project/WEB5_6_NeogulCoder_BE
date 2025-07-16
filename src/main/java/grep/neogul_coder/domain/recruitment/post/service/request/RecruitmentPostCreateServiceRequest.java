package grep.neogul_coder.domain.recruitment.post.service.request;

import grep.neogul_coder.domain.recruitment.RecruitmentPostStatus;
import grep.neogul_coder.domain.recruitment.post.RecruitmentPost;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class RecruitmentPostCreateServiceRequest {

    private long studyId;

    private String subject;
    private String content;
    private int recruitmentCount;
    private LocalDate expiredDate;

    @Builder
    private RecruitmentPostCreateServiceRequest(long studyId, String subject, String content,
                                                int recruitmentCount, LocalDate expiredDate) {
        this.studyId = studyId;
        this.subject = subject;
        this.content = content;
        this.recruitmentCount = recruitmentCount;
        this.expiredDate = expiredDate;
    }

    public RecruitmentPost toEntity(long userId) {
        return RecruitmentPost.builder()
                .studyId(studyId)
                .userId(userId)
                .subject(subject)
                .content(content)
                .recruitmentCount(recruitmentCount)
                .expiredDate(expiredDate)
                .status(RecruitmentPostStatus.IN_PROGRESS)
                .build();
    }
}
