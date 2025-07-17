package grep.neogul_coder.domain.recruitment.post.controller.dto.response;

import grep.neogul_coder.domain.recruitment.post.RecruitmentPost;
import grep.neogul_coder.domain.recruitment.comment.RecruitmentPostComment;
import grep.neogul_coder.domain.study.Study;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@ToString
@Getter
public class RecruitmentPostPagingInfo {

    private List<RecruitmentPostInfo> postInfos;

    public RecruitmentPostPagingInfo(List<RecruitmentPostInfo> postInfos) {
        this.postInfos = postInfos;
    }

    public static RecruitmentPostPagingInfo of(List<RecruitmentPost> recruitmentPosts,
                                               Map<Long, Study> studyIdMap,
                                               Map<Long, List<RecruitmentPostComment>> postIdMap) {

        List<RecruitmentPostInfo> postInfos = recruitmentPosts.stream()
                .map(post -> new RecruitmentPostInfo(post, studyIdMap.get(post.getStudyId()), postIdMap.get(post.getId())))
                .toList();

        return new RecruitmentPostPagingInfo(postInfos);
    }

    @ToString
    @Getter
    static class RecruitmentPostInfo {

        @Schema(example = "자바 스터디 모집 합니다!", description = "제목")
        private String subject;

        @Schema(example = "자바 스터디는 주 3회 오후 6시에 진행 됩니다.", description = "내용")
        private String content;

        @Schema(example = "IT", description = "카테고리")
        private String category;

        @Schema(example = "온라인", description = "스터디 타입")
        private String studyType;

        @Schema(example = "모집중", description = "모집글 상태")
        private String status;

        @Schema(example = "3", description = "댓글 수")
        private int commentCount;

        @Schema(example = "2025-07-15", description = "생성일")
        private LocalDate createAt;

        private RecruitmentPostInfo(RecruitmentPost post, Study study, List<RecruitmentPostComment> comments) {
            this.subject = post.getSubject();
            this.content = post.getContent();
            this.category = study.getCategory().name();
            this.studyType = study.getStudyType().name();
            this.status = post.getStatus().name();
            this.commentCount = comments.size();
            this.createAt = post.getCreatedDate().toLocalDate();
        }
    }
}
