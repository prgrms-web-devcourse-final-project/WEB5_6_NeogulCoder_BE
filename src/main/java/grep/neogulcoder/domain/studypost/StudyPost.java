package grep.neogulcoder.domain.studypost;

import grep.neogulcoder.domain.study.Study;
import grep.neogulcoder.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class StudyPost extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id", nullable = false)
    private Study study;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private String content;

    protected StudyPost() {
    }

    @Builder
    private StudyPost(Long userId, String title, Category category, String content) {
        this.userId = userId;
        this.title = title;
        this.category = category;
        this.content = content;
    }

    public void connectStudy(Study study) {
        this.study = study;
    }

    public void update(Category category, String title, String content) {
        this.category = category;
        this.title = title;
        this.content = content;
    }

    public void delete() {
        this.activated = false;
    }

    public boolean isOwned(long userId) {
        return this.userId == userId;
    }

    public boolean isNotOwned(long userId) {
        return !isOwned(userId);
    }
}
