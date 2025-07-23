package grep.neogulcoder.domain.study.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class StudyItemPagingResponse {

    @Schema(
        description = "스터디 목록",
        example = "[{" +
            "\"studyId\": 1," +
            "\"name\": \"자바 스터디\"," +
            "\"leaderNickname\": \"test\"," +
            "\"capacity\": 4," +
            "\"currentCount\": 1," +
            "\"startDate\": \"2025-07-15\"," +
            "\"endDate\": \"2025-07-28\"," +
            "\"imageUrl\": \"http://localhost:8083/image.jpg\"," +
            "\"introduction\": \"자바 스터디입니다.\"," +
            "\"category\": \"IT\"," +
            "\"studyType\": \"ONLINE\"," +
            "\"finished\": false" +
            "}]"
    )
    private List<StudyItemResponse> studies;

    @Schema(description = "총 페이지 수", example = "2")
    private int totalPage;

    @Schema(description = "총 요소 개수", example = "10")
    private int totalElementCount;

    @Schema(example = "false", description = "다음 페이지 여부")
    private boolean hasNext;

    @Builder
    private StudyItemPagingResponse(Page<StudyItemResponse> page) {
        this.studies = page.getContent();
        this.totalPage = page.getTotalPages();
        this.totalElementCount = (int) page.getTotalElements();
        this.hasNext = page.hasNext();
    }

    public static StudyItemPagingResponse of(Page<StudyItemResponse> page) {
        return new StudyItemPagingResponse(page);
    }
}
