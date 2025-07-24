package grep.neogulcoder.domain.timevote.entity;

import grep.neogulcoder.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class TimeVoteStat extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "period_id", nullable = false)
  private TimeVotePeriod period;

  @Column(nullable = false)
  private LocalDateTime timeSlot;

  @Column(nullable = false)
  private Long voteCount;

  @Version
  private Long version;

  protected TimeVoteStat() {};

  @Builder
  public TimeVoteStat(TimeVotePeriod period, LocalDateTime timeSlot, Long voteCount) {
    this.period = period;
    this.timeSlot = timeSlot;
    this.voteCount = voteCount;
  }

  public static TimeVoteStat of(TimeVotePeriod period, LocalDateTime timeSlot, Long voteCount) {
    return TimeVoteStat.builder()
        .period(period)
        .timeSlot(timeSlot)
        .voteCount(voteCount)
        .build();
  }

  public void addVotes(Long countToAdd) {
    this.voteCount += countToAdd;
  }
}
