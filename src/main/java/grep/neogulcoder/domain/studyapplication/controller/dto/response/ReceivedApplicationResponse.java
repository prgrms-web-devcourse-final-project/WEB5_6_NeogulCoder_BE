package grep.neogulcoder.domain.studyapplication.controller.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReceivedApplicationResponse {

    @Schema(description = "신청 번호", example = "1")
    private Long applicationId;

    @Schema(description = "신청자 번호", example = "1")
    private Long userId;

    @Schema(description = "신청자 닉네임", example = "너굴")
    private String nickname;

    @Schema(description = "신청자 프로필 이미지 URL", example = "http://localhost:8083/image.jpg")
    private String profileImageUrl;

    @Schema(description = "신청자 버디에너지", example = "30")
    private int buddyEnergy;

    @Schema(description = "신청 날짜", example = "2025-07-10T15:30:00")
    private LocalDateTime createdDate;

    @Schema(description = "스터디 신청 지원 동기", example = "자바를 더 공부하고 싶어 지원합니다.")
    private String applicationReason;

    @QueryProjection
    public ReceivedApplicationResponse(Long applicationId, Long userId, String nickname, String profileImageUrl, int buddyEnergy, LocalDateTime createdDate, String applicationReason) {
        this.applicationId = applicationId;
        this.userId = userId;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.buddyEnergy = buddyEnergy;
        this.createdDate = createdDate;
        this.applicationReason = applicationReason;
    }
}
