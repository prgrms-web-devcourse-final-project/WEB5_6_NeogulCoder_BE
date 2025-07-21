package grep.neogul_coder.domain.studyapplication.controller.dto.request;

import grep.neogul_coder.domain.recruitment.post.RecruitmentPost;
import grep.neogul_coder.domain.studyapplication.ApplicationStatus;
import grep.neogul_coder.domain.studyapplication.StudyApplication;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ApplicationCreateRequest {

    @Schema(description = "모집글 번호", example = "1")
    private Long recruitmentPostId;

    @NotNull
    @Schema(description = "유저 번호", example = "1")
    private Long userId;

    @NotBlank
    @Schema(description = "스터디 신청 지원 동기", example = "자바를 더 공부하고싶어 지원하였습니다.")
    private String applicationReason;

    private ApplicationCreateRequest() {
    }

    @Builder
    private ApplicationCreateRequest(Long recruitmentPostId, Long userId, String applicationReason) {
        this.recruitmentPostId = recruitmentPostId;
        this.userId = userId;
        this.applicationReason = applicationReason;
    }

    public StudyApplication toEntity() {
        return StudyApplication.builder()
            .recruitmentPostId(this.recruitmentPostId)
            .userId(this.userId)
            .applicationReason(this.applicationReason)
            .isRead(false)
            .status(ApplicationStatus.APPLYING)
            .build();
    }
}
