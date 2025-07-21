package grep.neogul_coder.domain.studypost.controller.dto.request;

import grep.neogul_coder.domain.studypost.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Getter
public class StudyPostPagingCondition {

    private int page;

    @Positive
    private int pageSize;

    @Schema(example = "NOTICE", description = " 스터디 공지 타입")
    private Category category;

    @Schema(example = "내용", description = "자바 내용")
    private String content;

    @Schema(example = "commentCount, createDateTime", description = "생성일 정렬")
    private String attributeName;

    @Schema(example = "ASC, DESC", description = "댓글순 정렬")
    private String sort;

    private StudyPostPagingCondition() {
    }

    public StudyPostPagingCondition(int page, int pageSize, Category category,
                                    String content, String attributeName, String sort) {
        this.page = page;
        this.pageSize = pageSize;
        this.category = category;
        this.content = content;
        this.attributeName = attributeName;
        this.sort = sort;
    }

    public Pageable toPageable() {
        return PageRequest.of(page, pageSize);
    }
}
