package grep.neogul_coder.domain.users.controller.dto.response;

import grep.neogul_coder.global.auth.code.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponse {

    @Schema(description = "회원 ID", example = "11")
    private Long id;

    @Schema(description = "회원 email", example = "example@example.com")
    private String email;

    @Schema(description = "회원 nickname", example = "홍길동")
    private String nickname;

    @Schema(description = "회원 프로필 이미지", example = "profileImageUrl")
    private String profileImageUrl;

    @Schema(description = "Role")
    private Role role;

    @Builder
    private UserResponse(Long id, String email, String nickname,String profileImageUrl, Role role) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.role = role;
    }

    public static UserResponse toUserResponse(Long id, String email, String nickname,String profileImageUrl, Role role){
        return UserResponse.builder()
            .id(id)
            .email(email)
            .nickname(nickname)
            .profileImageUrl(profileImageUrl)
            .role(role)
            .build();
    }
}