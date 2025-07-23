package grep.neogulcoder.domain.calender.controller.dto.response;

import grep.neogulcoder.domain.calender.entity.Calendar;
import grep.neogulcoder.domain.calender.entity.PersonalCalendar;
import grep.neogulcoder.domain.users.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "개인 캘린더 응답 DTO")
public class PersonalCalendarResponse {

    @Schema(description = "개인 일정 ID (PersonalCalendar의 ID)", example = "1001")
    private Long personalCalendarId;

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

    @Builder
    protected PersonalCalendarResponse(Long personalCalendarId, Long userId, String writerNickname,
        String writerProfileImageUrl, String title, String description,
        LocalDateTime startTime, LocalDateTime endTime) {
        this.personalCalendarId = personalCalendarId;
        this.userId = userId;
        this.writerNickname = writerNickname;
        this.writerProfileImageUrl = writerProfileImageUrl;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static PersonalCalendarResponse from(PersonalCalendar pc, User user) {
        Calendar calendar = pc.getCalendar();
        return PersonalCalendarResponse.builder()
            .personalCalendarId(pc.getId())
            .userId(user.getId())
            .writerNickname(user.getNickname())
            .writerProfileImageUrl(user.getProfileImageUrl())
            .title(calendar.getTitle())
            .description(calendar.getContent())
            .startTime(calendar.getScheduledStart())
            .endTime(calendar.getScheduledEnd())
            .build();
    }

}
