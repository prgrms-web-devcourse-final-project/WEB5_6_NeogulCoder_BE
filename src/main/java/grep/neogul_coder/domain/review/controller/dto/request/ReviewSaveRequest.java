package grep.neogul_coder.domain.review.controller.dto.request;

import grep.neogul_coder.domain.review.ReviewType;
import grep.neogul_coder.domain.review.service.request.ReviewSaveServiceRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ReviewSaveRequest {

    @Schema(example = "3", description = "스터디 ID")
    @NotNull
    private long studyId;

    @Schema(example = "2", description = "리뷰 대상 회원 ID")
    private long targetUserId;

    @Schema(example = "EXCELLENT", description = "리뷰 타입")
    @NotNull
    private ReviewType reviewType;

    @Schema(example = "[항상 먼저 도와주고 분위기를 이끌어주는 팀원이었어요., 책임감이 넘치고 맡은 일 이상으로 기여해줘서 감동이었어요.]", description = "리뷰 메시지들")
    @NotEmpty
    private List<String> reviewTag;

    @Schema(example = "너무 친절 하세요!", description = "주관 리뷰")
    private String content;

    private ReviewSaveRequest() {}

    @Builder
    private ReviewSaveRequest(long studyId, long targetUserId, ReviewType reviewType,
                              List<String> reviewTag, String content) {
        this.studyId = studyId;
        this.targetUserId = targetUserId;
        this.reviewType = reviewType;
        this.reviewTag = reviewTag;
        this.content = content;
    }

    public ReviewSaveServiceRequest toServiceRequest(){
        return ReviewSaveServiceRequest.builder()
                .studyId(this.studyId)
                .targetUserId(this.targetUserId)
                .reviewTag(this.reviewTag)
                .reviewType(this.reviewType)
                .content(this.content)
                .build();
    }
}
