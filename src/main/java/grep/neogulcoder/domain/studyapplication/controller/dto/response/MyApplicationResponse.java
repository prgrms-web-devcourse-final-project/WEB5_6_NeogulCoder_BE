package grep.neogulcoder.domain.studyapplication.controller.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import grep.neogulcoder.domain.study.enums.Category;
import grep.neogulcoder.domain.study.enums.StudyType;
import grep.neogulcoder.domain.studyapplication.ApplicationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyApplicationResponse {

    @Schema(description = "신청 번호", example = "1")
    private Long applicationId;

    @Schema(description = "스터디 이름", example = "자바 스터디")
    private String name;

    @Schema(description = "스터디장 닉네임", example = "너굴")
    private String leaderNickname;

    @Schema(description = "정원", example = "4")
    private int capacity;

    @Schema(description = "인원수", example = "3")
    private int currentCount;

    @Schema(description = "시작일", example = "2025-07-15")
    private LocalDateTime startDate;

    @Schema(description = "대표 이미지", example = "http://localhost:8083/image.jpg")
    private String imageUrl;

    @Schema(description = "스터디 소개", example = "자바 스터디입니다.")
    private String introduction;

    @Schema(description = "카테고리", example = "IT")
    private Category category;

    @Schema(description = "타입", example = "ONLINE")
    private StudyType studyType;

    @Schema(description = "열람 여부", example = "true")
    private boolean isRead;

    @Schema(description = "신청 상태", example = "PENDING")
    private ApplicationStatus status;

    @QueryProjection
    public MyApplicationResponse(Long applicationId, String name, String leaderNickname, int capacity, int currentCount, LocalDateTime startDate,
                                  String imageUrl,String introduction, Category category, StudyType studyType, boolean isRead, ApplicationStatus status) {
        this.applicationId = applicationId;
        this.name = name;
        this.leaderNickname = leaderNickname;
        this.capacity = capacity;
        this.currentCount = currentCount;
        this.startDate = startDate;
        this.imageUrl = imageUrl;
        this.introduction = introduction;
        this.category = category;
        this.studyType = studyType;
        this.isRead = isRead;
        this.status = status;
    }
}
