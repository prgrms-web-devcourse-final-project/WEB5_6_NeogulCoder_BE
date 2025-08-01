package grep.neogulcoder.domain.study.controller.dto.response;

import grep.neogulcoder.domain.study.StudyMember;
import grep.neogulcoder.domain.study.enums.StudyMemberRole;
import grep.neogulcoder.domain.users.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StudyMemberResponse {

    @Schema(description = "유저 Id", example = "1")
    private final Long userId;

    @Schema(description = "스터디원 닉네임", example = "너굴")
    private final String nickname;

    @Schema(description = "스터디원 프로필 사진", example = "http://localhost:8083/image.jpg")
    private final String profileImageUrl;

    @Schema(description = "스터디 멤버 역할", example = "LEADER")
    private final StudyMemberRole role;

    @Builder
    public StudyMemberResponse(Long userId, String nickname, String profileImageUrl, StudyMemberRole role) {
        this.userId = userId;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.role = role;
    }

    public static StudyMemberResponse from(StudyMember studyMember, User user) {
        return StudyMemberResponse.builder()
            .userId(user.getId())
            .nickname(user.getNickname())
            .profileImageUrl(user.getProfileImageUrl())
            .role(studyMember.getRole())
            .build();
    }
}
