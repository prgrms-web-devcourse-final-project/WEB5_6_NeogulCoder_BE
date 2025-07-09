package grep.neogul_coder.domain.recruitment.post.controller.dto;

import lombok.Getter;

@Getter
public enum RecruitmentPostMessage {
    CREATE("모집글이 생성 되었습니다."),
    EDIT("모집글이 수정 되었습니다."),
    DELETE("모집글이 삭제 되었습니다.");

    RecruitmentPostMessage(String description) {
        this.description = description;
    }

    private final String description;
}
