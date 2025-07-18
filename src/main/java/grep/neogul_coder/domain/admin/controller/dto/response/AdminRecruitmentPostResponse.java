package grep.neogul_coder.domain.admin.controller.dto.response;

import grep.neogul_coder.domain.recruitment.post.RecruitmentPost;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
public class AdminRecruitmentPostResponse {

    private Long id;
    private LocalDate expiredDate;
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
