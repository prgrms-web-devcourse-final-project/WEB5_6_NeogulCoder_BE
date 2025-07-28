package grep.neogulcoder.domain.timevote;

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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Entity
public class TimeVoteStat extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long statId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "period_id", nullable = false)
  private TimeVotePeriod period;

  @Column(nullable = false)
  private LocalDateTime timeSlot;

  @Column(nullable = false)
  private Long voteCount;

  @Version
  @Column(nullable = false)
  private Long version;

  protected TimeVoteStat() {}

  @Builder
  public TimeVoteStat(TimeVotePeriod period, LocalDateTime timeSlot, Long voteCount, Long version) {
    this.period = period;
    this.timeSlot = timeSlot;
    this.voteCount = voteCount;
    this.version = version;
  }

  public static TimeVoteStat of(TimeVotePeriod period, LocalDateTime timeSlot, Long voteCount) {
    return TimeVoteStat.builder()
        .period(period)
        .timeSlot(timeSlot)
        .voteCount(voteCount)
        .version(0L)
        .build();
  }

  public void addVotes(Long countToAdd) {
    log.debug("addVotes: 이전 voteCount={}, 추가 count={}, 이전 version={}", this.voteCount, countToAdd, this.version);
    this.voteCount += countToAdd;
  }
}
