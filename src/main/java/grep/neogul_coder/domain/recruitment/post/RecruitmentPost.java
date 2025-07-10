package grep.neogul_coder.domain.recruitment.post;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class RecruitmentPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long studyId;

    private String subject;
    private String content;
    private int recruitmentCount;
    private LocalDate expiredDate;
}
