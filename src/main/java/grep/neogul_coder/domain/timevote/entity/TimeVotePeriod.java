package grep.neogul_coder.domain.timevote.entity;

import grep.neogul_coder.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class TimeVotePeriod extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long periodId;

  @Column(name = "study_id", nullable = false)
  private Long studyId;

  @Column(nullable = false)
  private LocalDateTime startDate;

  @Column(nullable = false)
  private LocalDateTime endDate;

  protected TimeVotePeriod() {}

  @Builder
  public TimeVotePeriod(Long periodId, Long studyId, LocalDateTime startDate, LocalDateTime endDate) {
    this.periodId = periodId;
    this.studyId = studyId;
    this.startDate = startDate;
    this.endDate = endDate;
  }
}
