package grep.neogul_coder.domain.timevote.entity;

import grep.neogul_coder.domain.study.StudyMember;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class TimeVote {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long voteId;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "period_id", nullable = false)
  private TimeVotePeriod period;

  @Column(nullable = false)
  private Long studyMemberId;

  @Column(nullable = false)
  private LocalDateTime startTime;

  @Column(nullable = false)
  private LocalDateTime endTime;
}
