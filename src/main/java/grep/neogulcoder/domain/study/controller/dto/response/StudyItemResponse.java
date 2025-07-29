package grep.neogulcoder.domain.study.controller.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import grep.neogulcoder.domain.study.enums.Category;
import grep.neogulcoder.domain.study.enums.StudyType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StudyItemResponse {

    @Schema(description = "스터디 번호", example = "3")
    private final Long studyId;

    @Schema(description = "스터디 이름", example = "자바 스터디")
    private final String name;

    @Schema(description = "스터디장 닉네임", example = "너굴")
    private final String leaderNickname;

    @Schema(description = "정원", example = "4")
    private final int capacity;

    @Schema(description = "인원수", example = "3")
    private final int currentCount;

    @Schema(description = "시작일", example = "2025-07-15")
    private final LocalDateTime startDate;

    @Schema(description = "종료일", example = "2025-07-28")
    private final LocalDateTime endDate;

    @Schema(description = "대표 이미지", example = "http://localhost:8083/image.jpg")
    private final String imageUrl;

    @Schema(description = "스터디 소개", example = "자바 스터디입니다.")
    private final String introduction;

    @Schema(description = "카테고리", example = "IT")
    private final Category category;

    @Schema(description = "타입", example = "ONLINE")
    private final StudyType studyType;

    @Schema(description = "종료 여부", example = "false")
    private final boolean finished;

    @QueryProjection
    public StudyItemResponse(Long studyId, String name, String leaderNickname, int capacity, int currentCount, LocalDateTime startDate,
                             LocalDateTime endDate, String imageUrl, String introduction, Category category, StudyType studyType, boolean finished) {
        this.studyId = studyId;
        this.name = name;
        this.leaderNickname = leaderNickname;
        this.capacity = capacity;
        this.currentCount = currentCount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.imageUrl = imageUrl;
        this.introduction = introduction;
        this.category = category;
        this.studyType = studyType;
        this.finished = finished;
    }
}
