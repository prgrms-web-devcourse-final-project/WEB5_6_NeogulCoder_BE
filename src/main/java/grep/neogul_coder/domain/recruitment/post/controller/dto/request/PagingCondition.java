package grep.neogul_coder.domain.recruitment.post.controller.dto.request;

import grep.neogul_coder.domain.study.enums.Category;
import grep.neogul_coder.domain.study.enums.StudyType;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Getter
public class PagingCondition {

    private int page;
    private int pageSize;

    private Category category;
    private StudyType studyType;
    private String content;

    private PagingCondition() {
    }

    public PagingCondition(int page, int pageSize, Category category, StudyType studyType, String content) {
        this.page = page;
        this.pageSize = pageSize;
        this.category = category;
        this.studyType = studyType;
        this.content = content;
    }

    public Pageable toPageable() {
        return PageRequest.of(page, pageSize);
    }
}
