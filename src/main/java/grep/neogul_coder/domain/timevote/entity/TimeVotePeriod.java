package grep.neogul_coder.domain.timevote.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class TimeVotePeriod {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long periodId;

  @Column(nullable = false)
  private Long studyId;

  @Column(nullable = false)
  private LocalDateTime startDate;

  @Column(nullable = false)
  private LocalDateTime endDate;
}
