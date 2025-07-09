package grep.neogul_coder.domain.study;

import grep.neogul_coder.domain.study.enums.StudyMemberRole;
import grep.neogul_coder.global.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "study_member")
public class StudyMember extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studyMemberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private StudyMemberRole role;

    private Boolean isDeleted = false;
}
