package grep.neogul_coder.domain.calender.controller.dto.response;

import grep.neogul_coder.domain.calender.entity.Calendar;
import grep.neogul_coder.domain.calender.entity.TeamCalendar;
import grep.neogul_coder.domain.users.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "팀 캘린더 응답 DTO")
public class TeamCalendarResponse {

    @Schema(description = "팀 일정 ID", example = "2001")
    private Long teamCalendarId;

    @Schema(description = "스터디 ID", example = "101")
    private Long studyId;

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

    @Schema(description = "시작 기간", example = "2025-07-12T14:00:00")
    private LocalDateTime startTime;

    @Schema(description = "종료 기간", example = "2025-07-12T15:00:00")
    private LocalDateTime endTime;

    @Builder
    protected TeamCalendarResponse(Long teamCalendarId, Long studyId, Long writerId, String writerNickname,
        String writerProfileImageUrl, String title, String description, LocalDateTime startTime,
        LocalDateTime endTime) {
        this.teamCalendarId = teamCalendarId;
        this.studyId = studyId;
        this.writerId = writerId;
        this.writerNickname = writerNickname;
        this.writerProfileImageUrl = writerProfileImageUrl;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static TeamCalendarResponse from(TeamCalendar teamCalendar, User user) {
        Calendar calendar = teamCalendar.getCalendar();

        return TeamCalendarResponse.builder()
            .teamCalendarId(teamCalendar.getId())
            .title(calendar.getTitle())
            .description(calendar.getContent())
            .startTime(calendar.getScheduledStart())
            .endTime(calendar.getScheduledEnd())
            .writerId(user.getId())
            .writerNickname(user.getNickname())
            .writerProfileImageUrl(user.getProfileImageUrl())
            .studyId(teamCalendar.getStudyId())
            .build();
    }


}
