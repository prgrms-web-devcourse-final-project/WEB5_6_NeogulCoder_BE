package grep.neogulcoder.domain.admin.controller.dto.response;

import grep.neogulcoder.domain.users.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Schema(description = "관리자용 사용자 응답 DTO")
public class AdminUserResponse {

    @Schema(description = "사용자 ID", example = "10")
    private Long id;

    @Schema(description = "이메일 주소", example = "admin@example.com")
    private String email;

    @Schema(description = "사용자 닉네임", example = "관리자")
    private String nickname;

    @Schema(description = "활성화 여부", example = "true")
    private Boolean activated;

    @Builder
    private AdminUserResponse(Long id, String email, String nickname, Boolean activated) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.activated = activated;
    }

    public static AdminUserResponse from(User user) {
        return new AdminUserResponse(
            user.getId(),
            user.getEmail(),
            user.getNickname(),
            user.isActivated()
        );
    }

}

