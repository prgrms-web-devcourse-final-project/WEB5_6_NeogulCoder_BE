package grep.neogul_coder.domain.study.controller.dto.response;

import grep.neogul_coder.domain.attendance.controller.dto.response.AttendanceResponse;
import grep.neogul_coder.domain.calender.controller.dto.response.TeamCalenderResponse;
import grep.neogul_coder.domain.studypost.dto.StudyPostListResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class StudyResponse {

    @Schema(description = "스터디 진행 일수", example = "45")
    private int progressDays;

    @Schema(description = "스터디 총 기간", example = "320")
    private int totalDays;

    @Schema(description = "정원", example = "4")
    private int capacity;

    @Schema(description = "인원수", example = "3")
    private int currentCount;

    @Schema(description = "출석 정보")
    private List<AttendanceResponse> attendances;

    @Schema(description = "팀 달력")
    private List<TeamCalenderResponse> teamCalenders;

    @Schema(description = "게시글 리스트")
    private StudyPostListResponse studyPosts;
}
