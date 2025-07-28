package grep.neogulcoder.domain.timevote.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import grep.neogulcoder.domain.study.QStudyMember;
import grep.neogulcoder.domain.timevote.dto.response.TimeVoteSubmissionStatusResponse;
import grep.neogulcoder.domain.timevote.QTimeVote;
import grep.neogulcoder.domain.users.entity.QUser;
import jakarta.persistence.EntityManager;
import com.querydsl.core.Tuple;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class TimeVoteQueryRepository {

  private final JPAQueryFactory queryFactory;

  public TimeVoteQueryRepository(EntityManager em) {
    this.queryFactory = new JPAQueryFactory(em);
  }

  public List<TimeVoteSubmissionStatusResponse> findSubmissionStatuses(Long studyId,
      Long periodId) {
    QStudyMember studyMember = QStudyMember.studyMember;
    QTimeVote timeVote = QTimeVote.timeVote;
    QUser user = QUser.user;

    BooleanExpression existsVoteSubquery = JPAExpressions
        .selectOne()
        .from(timeVote)
        .where(
            timeVote.studyMemberId.eq(studyMember.id)
                .and(timeVote.period.periodId.eq(periodId))
                .and(timeVote.activated.isTrue())
        )
        .exists();


    List<Tuple> results = queryFactory
        .select(
            studyMember.id,
            user.nickname,
            user.profileImageUrl,
            existsVoteSubquery
        )
        .from(studyMember)
        .join(user).on(studyMember.userId.eq(user.id))
        .where(
            studyMember.study.id.eq(studyId),
            studyMember.activated.isTrue()
        )
        .fetch();

    return results.stream()
        .map(tuple -> TimeVoteSubmissionStatusResponse.builder()
            .studyMemberId(tuple.get(studyMember.id))
            .nickname(tuple.get(user.nickname))
            .profileImageUrl(tuple.get(user.profileImageUrl))
            .isSubmitted(tuple.get(existsVoteSubquery))
            .build())
        .collect(Collectors.toList());
  }
}
