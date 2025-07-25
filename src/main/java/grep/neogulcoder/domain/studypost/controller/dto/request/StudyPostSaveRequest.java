package grep.neogulcoder.domain.studypost.controller.dto.request;

import grep.neogulcoder.domain.study.Study;
import grep.neogulcoder.domain.studypost.Category;
import grep.neogulcoder.domain.studypost.StudyPost;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "스터디 게시글 저장 요청 DTO")
public class StudyPostSaveRequest {

    @Schema(description = "제목", example = "스터디 공지")
    @NotBlank
    private String title;

    @Schema(description = "카테고리: NOTICE(공지), FREE(자유)", example = "NOTICE")
    @NotNull
    private Category category;

    @Schema(description = "내용", example = "오늘은 각자 공부한 내용에 대해 발표가 있는 날 입니다!")
    @NotBlank
    private String content;

    private StudyPostSaveRequest() {
    }

    @Builder
    private StudyPostSaveRequest(String title, Category category, String content) {
        this.title = title;
        this.category = category;
        this.content = content;
    }

    public StudyPost toEntity(Study study, long userId) {
        StudyPost studyPost = StudyPost.builder()
                .userId(userId)
                .title(this.title)
                .category(this.category)
                .content(this.content)
                .build();

        studyPost.connectStudy(study);
        return studyPost;
    }
}
