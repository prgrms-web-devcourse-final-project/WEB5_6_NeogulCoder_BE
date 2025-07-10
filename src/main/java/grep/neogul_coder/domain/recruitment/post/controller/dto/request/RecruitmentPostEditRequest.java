package grep.neogul_coder.domain.recruitment.post.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class RecruitmentPostEditRequest {

    @Schema(example = "모각코 1일 스터디 모집 합니다.", description = "제목")
    private String subject;

    @Schema(example = "3", description = "모집글 인원수")
    private int recruitmentCount;

    @Schema(example = "모각코 1일 스터디에 참여하실분들은 신청 해주시면 감사합니다!", description = "소개 글 내용")
    private String content;
}
