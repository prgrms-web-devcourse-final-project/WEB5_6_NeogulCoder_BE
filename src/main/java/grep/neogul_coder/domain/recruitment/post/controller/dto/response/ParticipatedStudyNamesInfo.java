package grep.neogul_coder.domain.recruitment.post.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class ParticipatedStudyNamesInfo {

    @Schema(example = "[ {studyId: 1, name: 자바 스터디}, {studyId: 2, name: 면접 스터디} ]", description = "참여 중인 스터디 이름들")
    private List<StudyNameInfo> studyNameInfos;

    @Getter
    static class StudyNameInfo{

        @Schema(example = "1", description = "스터디 식별자 값")
        private long studyId;

        @Schema(example = "자바 스터디", description = "스터디 이름")
        private String name;
    }
}
