package grep.neogul_coder.domain.study.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public class StudyMemberResponse {

    @Schema(description = "스터디원 닉네임", example = "너굴")
    private String nickname;

    @Schema(description = "스터디원 프로필 사진", example = "http://localhost:8083/image.jpg")
    private String profileImageUrl;
}
