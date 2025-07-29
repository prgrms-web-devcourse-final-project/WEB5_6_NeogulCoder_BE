package grep.neogulcoder.domain.recruitment.post.controller.dto.request.save;

import grep.neogulcoder.domain.recruitment.post.service.request.RecruitmentPostCreateServiceRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class RecruitmentPostCreateRequest {

    @Schema(example = "2", description = "스터디 ID")
    @NotNull(message = "스터디 ID 값은 필수 입니다.")
    private long studyId;

    @Schema(example = "모각코 1일 스터디 모집 합니다.", description = "제목")
    @NotBlank(message = "제목은 필수 입니다.")
    private String subject;

    @Schema(example = "모각코 1일 스터디에 참여하실분들은 신청 해주시면 감사합니다!", description = "소개 글 내용")
    @NotBlank(message = "내용은 필수 입니다.")
    private String content;

    @Schema(example = "3", description = "모집글 인원 수")
    @Positive(message = "모집인원은 최소 1명을 입력해 주세요")
    private int recruitmentCount;

    @Schema(example = "2025-07-10", description = "모집 마감일")
    @NotNull
    private LocalDateTime expiredDate;

    private RecruitmentPostCreateRequest() {
    }

    @Builder
    private RecruitmentPostCreateRequest(long studyId, String subject, String content,
                                         int recruitmentCount, LocalDateTime expiredDate) {
        this.studyId = studyId;
        this.subject = subject;
        this.content = content;
        this.recruitmentCount = recruitmentCount;
        this.expiredDate = expiredDate;
    }

    public RecruitmentPostCreateServiceRequest toServiceRequest() {
        return RecruitmentPostCreateServiceRequest.builder()
                .studyId(this.studyId)
                .subject(this.subject)
                .content(this.content)
                .recruitmentCount(this.recruitmentCount)
                .expiredDate(this.expiredDate)
                .build();
    }

    public boolean hasExpiredDateBefore(LocalDate date) {
        return this.expiredDate.toLocalDate().isBefore(date);
    }
}
