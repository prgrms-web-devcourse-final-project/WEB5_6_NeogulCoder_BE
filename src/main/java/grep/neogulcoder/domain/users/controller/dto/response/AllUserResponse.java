package grep.neogulcoder.domain.users.controller.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
public class AllUserResponse {

    private Long id;
    private String email;
    private String nickname;
    private String profileImageUrl;

    public AllUserResponse toResponse(Long id, String email, String nickname, String profileImageUrl) {
        return AllUserResponse.builder()
            .id(this.id)
            .email(this.email)
            .nickname(this.nickname)
            .profileImageUrl(this.profileImageUrl)
            .build();
    }

    @Builder
    private AllUserResponse(Long id, String email, String nickname, String profileImageUrl) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

}
