package grep.neogul_coder.domain.studypost;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "카테고리: NOTICE(공지), FREE(자유)", example = "NOTICE")
public enum Category {
    NOTICE("공지"),
    FREE("자유");

    private final String korean;

    Category(String korean) {
        this.korean = korean;
    }

    public String getKorean() {
        return korean;
    }

}
