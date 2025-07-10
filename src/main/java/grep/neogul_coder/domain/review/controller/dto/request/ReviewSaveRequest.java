package grep.neogul_coder.domain.review.controller.dto.request;

import grep.neogul_coder.domain.review.ReviewType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class ReviewSaveRequest {

    @Schema(example = "3", description = "스터디 ID")
    private long studyId;

    @Schema(example = "EXCELLENT", description = "리뷰 타입")
    private ReviewType reviewType;

    @Schema(example = "[항상 먼저 도와주고 분위기를 이끌어주는 팀원이었어요., 책임감이 넘치고 맡은 일 이상으로 기여해줘서 감동이었어요.]", description = "리뷰 메시지들")
    private List<String> reviewTag;
}
