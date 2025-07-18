package grep.neogul_coder.domain.admin.controller.dto.response;

import grep.neogul_coder.domain.users.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
public class AdminUserResponse {

    private Long id;
    private String email;
    private String nickname;
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
            user.getActivated()
        );
    }

}

