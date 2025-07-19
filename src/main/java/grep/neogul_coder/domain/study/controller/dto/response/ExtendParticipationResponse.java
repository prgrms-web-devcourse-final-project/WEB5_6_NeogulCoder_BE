package grep.neogul_coder.domain.study.controller.dto.response;

import grep.neogul_coder.domain.study.StudyMember;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ExtendParticipationResponse {

    @Schema(description = "유저 Id", example = "1")
    private Long userId;

    @Schema(description = "닉네임", example = "너굴")
    private String nickname;

    @Schema(description = "참여 여부", example = "true")
    private boolean participated;

    @Builder
    public ExtendParticipationResponse(Long userId, String nickname, boolean participated) {
        this.userId = userId;
        this.nickname = nickname;
        this.participated = participated;
    }

    public static ExtendParticipationResponse from(StudyMember studyMember, String nickname) {
        return ExtendParticipationResponse.builder()
            .userId(studyMember.getUserId())
            .nickname(nickname)
            .participated(studyMember.isParticipated())
            .build();
    }
}
