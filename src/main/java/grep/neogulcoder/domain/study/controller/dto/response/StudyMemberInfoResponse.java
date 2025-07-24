package grep.neogulcoder.domain.study.controller.dto.response;

import grep.neogulcoder.domain.study.StudyMember;
import grep.neogulcoder.domain.study.enums.StudyMemberRole;
import grep.neogulcoder.domain.users.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StudyMemberInfoResponse {

    @Schema(description = "유저 Id", example = "1")
    private Long userId;

    @Schema(description = "스터디 Id", example = "1")
    private Long studyId;

    @Schema(description = "스터디 멤버 역할", example = "LEADER")
    private StudyMemberRole role;

    @Schema(description = "닉네임", example = "너굴")
    private String nickname;

    @Builder
    private StudyMemberInfoResponse(Long userId, Long studyId, StudyMemberRole role, String nickname) {
        this.userId = userId;
        this.studyId = studyId;
        this.role = role;
        this.nickname = nickname;
    }

    public static StudyMemberInfoResponse from(StudyMember studyMember, User user) {
        return StudyMemberInfoResponse.builder()
            .userId(user.getId())
            .studyId(studyMember.getStudy().getId())
            .role(studyMember.getRole())
            .nickname(user.getNickname())
            .build();
    }
}
