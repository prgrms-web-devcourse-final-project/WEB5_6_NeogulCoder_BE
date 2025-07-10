package grep.neogul_coder.domain.studyapplication.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApplicationResponse {

    @Schema(description = "신청자 닉네임", example = "너굴")
    private String nickname;

    @Schema(description = "신청자 버디에너지", example = "30")
    private int buddyEnergy;

    @Schema(description = "신청 날짜", example = "2025-07-10T15:30:00")
    private LocalDateTime createdDate;

    @Schema(description = "스터디 신청 지원 동기", example = "자바를 더 공부하고싶어 지원하였습니다.")
    private String applicationReason;
}
