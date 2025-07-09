package grep.neogul_coder.domain.recruitment.post;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class RecruitmentPost {

    @Id
    private Long id;
    private long studyId;

    private String subject;
    private String content;
    private int recruitmentCount;
}
