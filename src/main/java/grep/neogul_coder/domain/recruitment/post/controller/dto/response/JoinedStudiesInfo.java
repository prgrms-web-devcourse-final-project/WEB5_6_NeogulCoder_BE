package grep.neogul_coder.domain.recruitment.post.controller.dto.response;

import grep.neogul_coder.domain.study.Study;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class JoinedStudiesInfo {

    @Schema(example = "[ {studyId: 1, name: 자바 스터디}, {studyId: 2, name: 면접 스터디} ]", description = "참여 중인 스터디 이름들")
    private List<StudyNameInfo> studyInfos;

    private JoinedStudiesInfo(List<StudyNameInfo> studyInfos) {
        this.studyInfos = studyInfos;
    }

    public static JoinedStudiesInfo of(List<Study> studyList) {
        List<StudyNameInfo> studyInfos = studyList.stream()
                .map(study -> new StudyNameInfo(study.getId(), study.getName()))
                .toList();

        return new JoinedStudiesInfo(studyInfos);
    }

    @Getter
    static class StudyNameInfo {

        @Schema(example = "1", description = "스터디 식별자 값")
        private long studyId;

        @Schema(example = "자바 스터디", description = "스터디 이름")
        private String name;

        private StudyNameInfo(long studyId, String name) {
            this.studyId = studyId;
            this.name = name;
        }
    }
}
