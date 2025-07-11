package grep.neogul_coder.domain.users.controller.dto.response;

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

    @Schema(description = "프로필 이미지 URL")
    private String profileImgUrl;

    @Builder
    private UserResponse(Long id, String email, String nickname, String profileImgUrl) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
    }

    public static UserResponse toUserResponse(Long id, String email, String nickname, String profileImgUrl){
        return UserResponse.builder()
            .id(id)
            .email(email)
            .nickname(nickname)
            .profileImgUrl(profileImgUrl)
            .build();
    }
}