package grep.neogul_coder.domain.studypost.comment;

import grep.neogul_coder.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class StudyPostComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long postId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    protected StudyPostComment() {
    }

    @Builder
    private StudyPostComment(Long postId, Long userId, String content) {
        this.postId = postId;
        this.userId = userId;
        this.content = content;
    }
}
