package grep.neogul_coder.domain.recruitment.post;

import grep.neogul_coder.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class RecruitmentPostComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_post_id")
    private RecruitmentPost recruitmentPost;

    private long userId;
    private String content;

    protected RecruitmentPostComment() {
    }

    @Builder
    private RecruitmentPostComment(RecruitmentPost recruitmentPost, long userId, String content) {
        this.recruitmentPost = recruitmentPost;
        this.userId = userId;
        this.content = content;
    }
}
