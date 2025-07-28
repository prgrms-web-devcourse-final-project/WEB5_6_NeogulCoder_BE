package grep.neogulcoder.domain.recruitment.post.controller.dto.request;

import grep.neogulcoder.domain.recruitment.post.service.request.RecruitmentPostUpdateServiceRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RecruitmentPostUpdateRequest {

    @Schema(example = "모각코 1일 스터디 모집 합니다.", description = "제목")
    @NotBlank(message = "제목은 필수값 입니다.")
    private String subject;

    @Schema(example = "3", description = "모집글 인원수")
    @Positive(message = "모집인원은 0보다 큰수만 가능 합니다.")
    private int recruitmentCount;

    @Schema(example = "모각코 1일 스터디에 참여하실분들은 신청 해주시면 감사합니다!", description = "소개 글 내용")
    @NotBlank(message = "내용은 필수값 입니다.")
    private String content;

    @Future
    @Schema(example = "2025-07-21T23:59:59", description = "모집글 마감 기간")
    private LocalDateTime expiredDate;

    @Builder
    private RecruitmentPostUpdateRequest(String subject, String content, int recruitmentCount, LocalDateTime expiredDate) {
        this.subject = subject;
        this.content = content;
        this.recruitmentCount = recruitmentCount;
        this.expiredDate = expiredDate;
    }

    public RecruitmentPostUpdateServiceRequest toServiceRequest() {
        return RecruitmentPostUpdateServiceRequest.builder()
                .subject(this.subject)
                .content(this.content)
                .recruitmentCount(this.recruitmentCount)
                .expiredDateTime(this.expiredDate)
                .build();
    }

    private RecruitmentPostUpdateRequest() {
    }
}
