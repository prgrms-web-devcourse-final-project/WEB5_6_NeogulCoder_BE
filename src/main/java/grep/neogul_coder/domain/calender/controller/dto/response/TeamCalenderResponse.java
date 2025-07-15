package grep.neogul_coder.domain.calender.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "팀 캘린더 응답 DTO")
public class TeamCalenderResponse {

    @Schema(description = "일정 ID", example = "2001")
    private Long scheduleId;

    @Schema(description = "팀 ID", example = "101")
    private Long teamId;

    @Schema(description = "작성자 ID", example = "123")
    private Long writerId;

    @Schema(description = "작성자 닉네임", example = "유강현")
    private String writerNickname;

    @Schema(description = "작성자 프로필 이미지 URL", example = "https://wibby.com/profile/유강현.jpg")
    private String writerProfileImageUrl;

    @Schema(description = "일정 제목", example = "스터디A")
    private String title;

    @Schema(description = "일정 설명", example = "기획 회의")
    private String description;

    @Schema(description = "시작 시간", example = "2025-07-12T14:00:00")
    private LocalDateTime startTime;

    @Schema(description = "종료 시간", example = "2025-07-12T15:00:00")
    private LocalDateTime endTime;

}
