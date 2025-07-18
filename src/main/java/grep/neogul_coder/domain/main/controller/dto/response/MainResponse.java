package grep.neogul_coder.domain.main.controller.dto.response;

import grep.neogul_coder.domain.recruitment.post.controller.dto.response.RecruitmentPostPagingInfo;
import grep.neogul_coder.domain.study.controller.dto.response.StudyItemResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MainResponse {

    @Schema(description = "참여중인 스터디 목록")
    private List<StudyItemResponse> myStudies;

    @Schema(description = "모집중인 스터디 목록")
    private RecruitmentPostPagingInfo recruitingStudies;

    @Builder
    private MainResponse(List<StudyItemResponse> myStudies, RecruitmentPostPagingInfo recruitingStudies) {
        this.myStudies = myStudies;
        this.recruitingStudies = recruitingStudies;
    }

    public static MainResponse from(List<StudyItemResponse> myStudies, RecruitmentPostPagingInfo recruitingStudies) {
        return MainResponse.builder()
            .myStudies(myStudies)
            .recruitingStudies(recruitingStudies)
            .build();
    }
}
