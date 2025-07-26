package grep.neogulcoder.domain.timevote;

import grep.neogulcoder.global.entity.BaseEntity;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TimeVotePeriod that = (TimeVotePeriod) o;
    return periodId != null && periodId.equals(that.periodId);
  }

  @Override
  public int hashCode() {
    return periodId != null ? periodId.hashCode() : 0;
  }
}
