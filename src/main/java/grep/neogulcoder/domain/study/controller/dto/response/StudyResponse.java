package grep.neogulcoder.domain.study.controller.dto.response;

import grep.neogulcoder.domain.calendar.controller.dto.response.TeamCalendarResponse;
import grep.neogulcoder.domain.study.Study;
import grep.neogulcoder.domain.studypost.controller.dto.response.FreePostInfo;
import grep.neogulcoder.domain.studypost.controller.dto.response.NoticePostInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class StudyResponse {

    @Schema(description = "스터디 진행 일수", example = "45")
    private final int progressDays;

    @Schema(description = "스터디 총 기간", example = "320")
    private final int totalDays;

    @Schema(description = "정원", example = "4")
    private final int capacity;

    @Schema(description = "인원수", example = "3")
    private final int currentCount;

    @Schema(description = "스터디 총 게시물 수", example = "10")
    private final int totalPostCount;

    @Schema(description = "팀 달력")
    private final List<TeamCalendarResponse> teamCalendars;

    @Schema(description = "스터디 공지글 2개")
    private final List<NoticePostInfo> noticePosts;

    @Schema(description = "스터디 자유글 3개")
    private final List<FreePostInfo> freePosts;

    @Builder
    private StudyResponse(int progressDays, int totalDays, int capacity, int currentCount, int totalPostCount,
                          List<TeamCalendarResponse> teamCalendars, List<NoticePostInfo> noticePosts, List<FreePostInfo> freePosts) {
        this.progressDays = progressDays;
        this.totalDays = totalDays;
        this.capacity = capacity;
        this.currentCount = currentCount;
        this.totalPostCount = totalPostCount;
        this.teamCalendars = teamCalendars;
        this.noticePosts = noticePosts;
        this.freePosts = freePosts;
    }

    public static StudyResponse from(Study study, int progressDays, int totalDays, int totalPostCount,
                                     List<TeamCalendarResponse> teamCalendars, List<NoticePostInfo> noticePosts, List<FreePostInfo> freePosts) {
        return StudyResponse.builder()
            .progressDays(progressDays)
            .totalDays(totalDays)
            .capacity(study.getCapacity())
            .currentCount(study.getCurrentCount())
            .totalPostCount(totalPostCount)
            .teamCalendars(teamCalendars)
            .noticePosts(noticePosts)
            .freePosts(freePosts)
            .build();
    }
}
