package grep.neogul_coder.domain.recruitment.post.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class RecruitmentPagingInfo {

    @Schema(example = "자바 스터디 모집 합니다!", description = "제목")
    private String subject;

    @Schema(example = "자바 스터디는 주 3회 오후 6시에 진행 됩니다.", description = "내용")
    private String content;

    @Schema(example = "IT", description = "카테고리")
    private String category;

    @Schema(example = "온라인", description = "스터디 타입")
    private String studyType;

    @Schema(example = "모집중", description = "모집글 상태")
    private String status;

    @Schema(example = "3", description = "댓글 수")
    private int commentCount;

    @Schema(example = "2025-07-15", description = "생성일")
    private LocalDate createAt;
}
