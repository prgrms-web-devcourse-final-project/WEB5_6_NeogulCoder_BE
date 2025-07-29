package grep.neogulcoder.domain.study.controller.dto.response;

import grep.neogulcoder.domain.study.StudyMember;
import grep.neogulcoder.domain.study.enums.StudyMemberRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ExtendParticipationResponse {

    @Schema(description = "유저 Id", example = "1")
    private final Long userId;

    @Schema(description = "닉네임", example = "너굴")
    private final String nickname;

    @Schema(description = "역할", example = "LEADER")
    private final StudyMemberRole role;

    @Schema(description = "참여 여부", example = "true")
    private final boolean participated;

    @Builder
    public ExtendParticipationResponse(Long userId, String nickname, StudyMemberRole role, boolean participated) {
        this.userId = userId;
        this.nickname = nickname;
        this.role = role;
        this.participated = participated;
    }

    public static ExtendParticipationResponse from(StudyMember studyMember, String nickname) {
        return ExtendParticipationResponse.builder()
            .userId(studyMember.getUserId())
            .nickname(nickname)
            .role(studyMember.getRole())
            .participated(studyMember.isParticipated())
            .build();
    }
}
