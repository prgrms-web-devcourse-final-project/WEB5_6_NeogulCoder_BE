package grep.neogulcoder.domain.studyapplication.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class ReceivedApplicationPagingResponse {

    @Schema(
        description = "내 모집글에 지원한 신청 목록",
        example = "[{" +
            "\"applicationId\": 1," +
            "\"nickname\": \"너굴\"," +
            "\"profileImageUrl\": \"http://localhost:8083/image.jpg\"," +
            "\"buddyEnergy\": 30," +
            "\"createdDate\": \"2025-07-10T15:30:00\"," +
            "\"applicationReason\": \"자바를 더 공부하고 싶어 지원합니다.\"" +
            "}]"
    )
    private List<ReceivedApplicationResponse> receivedApplications;

    @Schema(description = "총 페이지 수", example = "2")
    private int totalPage;

    @Schema(description = "총 요소 개수", example = "10")
    private int totalElementCount;

    @Schema(description = "다음 페이지 여부", example = "false")
    private boolean hasNext;

    @Builder
    private ReceivedApplicationPagingResponse(Page<ReceivedApplicationResponse> page) {
        this.receivedApplications = page.getContent();
        this.totalPage = page.getTotalPages();
        this.totalElementCount = (int) page.getTotalElements();
        this.hasNext = page.hasNext();
    }

    public static ReceivedApplicationPagingResponse of(Page<ReceivedApplicationResponse> page) {
        return new ReceivedApplicationPagingResponse(page);
    }
}
