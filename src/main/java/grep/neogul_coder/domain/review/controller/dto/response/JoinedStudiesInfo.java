package grep.neogul_coder.domain.review.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
public class JoinedStudiesInfo {

    @Schema(example = "[ { studyId: 2, studyName: 자바 스터디, imageUrl: www.s3.com } ]")
    private List<StudyInfo> studiesInfo;

    public JoinedStudiesInfo() {
    }

    @ToString
    @Getter
    static class StudyInfo {

        @Schema(example = "2", description = "스터디 ID")
        private long studyId;

        @Schema(example = "자바 스터디", description = "스터디 이름")
        private String studyName;

        @Schema(example = "www.s3.com", description = "스터디 이미지 URL")
        private String imageUrl;

        public StudyInfo(long studyId, String studyName, String imageUrl) {
            this.studyId = studyId;
            this.studyName = studyName;
            this.imageUrl = imageUrl;
        }
    }
}
