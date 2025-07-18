package grep.neogul_coder.domain.admin.controller.dto.response;

import grep.neogul_coder.domain.recruitment.post.RecruitmentPost;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
public class AdminRecruitmentPostResponse {

    private LocalDate expiredDate;
    private boolean activated;

    public static AdminRecruitmentPostResponse from(RecruitmentPost post) {
        return AdminRecruitmentPostResponse
            .builder()
            .expiredDate(post.getExpiredDate())
            .activated(post.getActivated())
            .build();
    }

    @Builder
    private AdminRecruitmentPostResponse(LocalDate expiredDate, boolean activated) {
        this.expiredDate = expiredDate;
        this.activated = activated;
    }
}
