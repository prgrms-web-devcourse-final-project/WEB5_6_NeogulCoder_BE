package grep.neogul_coder.domain.calender.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
@Schema(description = "개인 캘린더 응답 DTO")
public class PersonalCalendarResponse {

    @Schema(description = "일정 ID", example = "1001")
    private Long calendarId;

    @Schema(description = "사용자 ID", example = "1")
    private Long userId;

    @Schema(description = "작성자 닉네임", example = "유강현")
    private String writerNickname;

    @Schema(description = "작성자 프로필 이미지 URL", example = "https://wibby.com/profile/유강현.jpg")
    private String writerProfileImageUrl;

    @Schema(description = "일정 제목", example = "면접 준비")
    private String title;

    @Schema(description = "일정 설명", example = "코테 대비 공부")
    private String description;

    @Schema(description = "시작 시간", example = "2025-07-10T09:00:00")
    private LocalDateTime startTime;

    @Schema(description = "종료 시간", example = "2025-07-10T10:00:00")
    private LocalDateTime endTime;

}
