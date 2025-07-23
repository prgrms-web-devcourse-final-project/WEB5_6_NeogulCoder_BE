package grep.neogul_coder.domain.studyapplication.controller.dto.response;

import grep.neogul_coder.domain.study.controller.dto.response.StudyItemPagingResponse;
import grep.neogul_coder.domain.study.controller.dto.response.StudyItemResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class MyApplicationPagingResponse {

    @Schema(
        description = "내가 신청한 스터디 목록",
        example = "[{" +
            "\"applicationId\": 1," +
            "\"name\": \"자바 스터디\"," +
            "\"leaderNickname\": \"너굴\"," +
            "\"capacity\": 4," +
            "\"currentCount\": 3," +
            "\"startDate\": \"2025-07-15T00:00:00\"," +
            "\"imageUrl\": \"http://localhost:8083/image.jpg\"," +
            "\"introduction\": \"자바 스터디입니다.\"," +
            "\"category\": \"IT\"," +
            "\"studyType\": \"ONLINE\"," +
            "\"isRead\": true," +
            "\"status\": \"PENDING\"" +
            "}]"
    )
    private List<MyApplicationResponse> applications;

    @Schema(description = "총 페이지 수", example = "2")
    private int totalPage;

    @Schema(description = "총 요소 개수", example = "10")
    private int totalElementCount;

    @Schema(example = "false", description = "다음 페이지 여부")
    private boolean hasNext;

    @Builder
    private MyApplicationPagingResponse(Page<MyApplicationResponse> page) {
        this.applications = page.getContent();
        this.totalPage = page.getTotalPages();
        this.totalElementCount = (int) page.getTotalElements();
        this.hasNext = page.hasNext();
    }

    public static MyApplicationPagingResponse of(Page<MyApplicationResponse> page) {
        return new MyApplicationPagingResponse(page);
    }
}
