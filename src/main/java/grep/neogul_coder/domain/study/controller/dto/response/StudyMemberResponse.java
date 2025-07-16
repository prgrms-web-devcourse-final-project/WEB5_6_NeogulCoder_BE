package grep.neogul_coder.domain.study.controller.dto.response;

import grep.neogul_coder.domain.study.StudyMember;
import grep.neogul_coder.domain.users.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StudyMemberResponse {

    @Schema(description = "유저 Id", example = "1")
    private Long userId;

    @Schema(description = "스터디원 닉네임", example = "너굴")
    private String nickname;

    @Schema(description = "스터디원 프로필 사진", example = "http://localhost:8083/image.jpg")
    private String profileImageUrl;

    @Builder
    public StudyMemberResponse(Long userId, String nickname, String profileImageUrl) {
        this.userId = userId;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

    public static StudyMemberResponse from(StudyMember studyMember, User user) {
        return StudyMemberResponse.builder()
            .userId(user.getId())
            .nickname(user.getNickname())
            .profileImageUrl(user.getProfileImageUrl())
            .build();
    }
}
