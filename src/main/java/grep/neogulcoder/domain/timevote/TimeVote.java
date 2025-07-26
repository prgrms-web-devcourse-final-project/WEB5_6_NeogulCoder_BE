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
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class TimeVote extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long voteId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "period_id", nullable = false)
  private TimeVotePeriod period;

  @Column(name = "study_member_id", nullable = false)
  private Long studyMemberId;

  @Column(nullable = false)
  private LocalDateTime timeSlot;

  protected TimeVote() {};

  @Builder
  public TimeVote(TimeVotePeriod period, Long studyMemberId, LocalDateTime timeSlot) {
    this.period = period;
    this.studyMemberId = studyMemberId;
    this.timeSlot = timeSlot;
  }
}
