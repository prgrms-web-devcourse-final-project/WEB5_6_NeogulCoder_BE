package grep.neogul_coder.domain.studyapplication;

import grep.neogul_coder.global.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
public class StudyApplication extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studyApplicationId;

    @Column(nullable = false)
    private Long recruitmentPostId;

    @Column(nullable = false)
    private String applicationReason;

    private boolean isRead;

    private ApplicationStatus status;
}
