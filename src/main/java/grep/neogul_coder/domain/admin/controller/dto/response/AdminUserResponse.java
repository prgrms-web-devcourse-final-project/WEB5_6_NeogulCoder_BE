package grep.neogul_coder.domain.admin.controller.dto.response;

import grep.neogul_coder.domain.users.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
public class AdminUserResponse {

    private String email;
    private String nickname;
    private Boolean activated;

    @Builder
    private AdminUserResponse(String email, String nickname, Boolean activated) {
        this.email = email;
        this.nickname = nickname;
        this.activated = activated;
    }

    public static AdminUserResponse from(User user) {
        return new AdminUserResponse(
            user.getEmail(),
            user.getNickname(),
            user.getActivated()
        );
    }

}

