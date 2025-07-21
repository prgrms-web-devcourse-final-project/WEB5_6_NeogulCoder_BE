package grep.neogul_coder.domain.study.controller.dto.response;

import grep.neogul_coder.domain.studypost.controller.dto.StudyPostListResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class StudyMyContentResponse {

    @Schema(description = "게시글 리스트")
    private List<StudyPostListResponse> studyPosts;
}
