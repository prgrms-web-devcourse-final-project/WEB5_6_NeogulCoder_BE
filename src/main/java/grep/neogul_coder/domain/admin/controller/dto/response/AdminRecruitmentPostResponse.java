package grep.neogul_coder.domain.admin.controller.dto.response;

import grep.neogul_coder.domain.recruitment.post.RecruitmentPost;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Schema(description = "관리자용 모집글 응답 DTO")
public class AdminRecruitmentPostResponse {

    @Schema(description = "모집글 ID", example = "123")
    private Long id;

    @Schema(description = "모집 마감일", example = "2025-08-01")
    private LocalDate expiredDate;

    @Schema(description = "활성화 여부", example = "true")
    private boolean activated;

    public static AdminRecruitmentPostResponse from(RecruitmentPost post) {
        return AdminRecruitmentPostResponse
            .builder()
            .id(post.getId())
            .expiredDate(post.getExpiredDate())
            .activated(post.getActivated())
            .build();
    }

    @Builder
    private AdminRecruitmentPostResponse(Long id, LocalDate expiredDate, boolean activated) {
        this.id = id;
        this.expiredDate = expiredDate;
        this.activated = activated;
    }
}
