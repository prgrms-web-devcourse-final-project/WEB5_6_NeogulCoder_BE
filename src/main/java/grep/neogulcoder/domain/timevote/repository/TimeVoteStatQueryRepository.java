package grep.neogulcoder.domain.timevote.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import grep.neogulcoder.domain.timevote.TimeVotePeriod;
import grep.neogulcoder.domain.timevote.TimeVoteStat;
import grep.neogulcoder.domain.timevote.QTimeVote;
import grep.neogulcoder.domain.timevote.QTimeVoteStat;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class TimeVoteStatQueryRepository {

  private final JPAQueryFactory queryFactory;
  private final EntityManager em;

  public TimeVoteStatQueryRepository(EntityManager em) {
    this.em = em;
    this.queryFactory = new JPAQueryFactory(em);
  }

  public List<TimeVoteStat> countStatsByPeriod(TimeVotePeriod period) {
    QTimeVote timeVote = QTimeVote.timeVote;

    List<Tuple> result = queryFactory
        .select(timeVote.timeSlot, timeVote.count())
        .from(timeVote)
        .where(timeVote.period.eq(period))
        .groupBy(timeVote.timeSlot)
        .fetch();

    return result.stream()
        .map(tuple -> TimeVoteStat.of(period, tuple.get(timeVote.timeSlot), tuple.get(timeVote.count())))
        .collect(Collectors.toList());
  }

  public void incrementOrInsert(TimeVotePeriod period, LocalDateTime slot, Long countToAdd) {
    QTimeVoteStat stat = QTimeVoteStat.timeVoteStat;

    TimeVoteStat existing = queryFactory
        .selectFrom(stat)
        .where(stat.period.eq(period), stat.timeSlot.eq(slot))
        .fetchOne();

    if (existing != null) {
      existing.addVotes(countToAdd);
    } else {
      TimeVoteStat newStat = TimeVoteStat.of(period, slot, countToAdd);
      em.persist(newStat);
      em.flush();
    }
  }
}
