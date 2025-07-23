package grep.neogulcoder.domain.timevote.repository;

import com.querydsl.core.types.dsl.BooleanPath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import grep.neogulcoder.domain.study.QStudyMember;
import grep.neogulcoder.domain.timevote.dto.response.TimeVoteSubmissionStatusResponse;
import grep.neogulcoder.domain.timevote.entity.QTimeVote;
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

  public List<TimeVoteSubmissionStatusResponse> findSubmissionStatuses(Long studyId, Long periodId) {
    QStudyMember studyMember = QStudyMember.studyMember;
    QTimeVote timeVote = QTimeVote.timeVote;
    QUser user = QUser.user;

    // select절에서 alias를 지정해 Tuple에서 이름 기반으로 값을 꺼낼 수 있도록 Path 객체 생성
    NumberPath<Long> aliasStudyMemberId = Expressions.numberPath(Long.class, "aliasStudyMemberId");
    StringPath aliasNickname = Expressions.stringPath("aliasNickname");
    StringPath aliasProfileImageUrl = Expressions.stringPath("aliasProfileImageUrl");
    BooleanPath aliasIsSubmitted = Expressions.booleanPath("aliasIsSubmitted");

    List<Tuple> results = queryFactory
        .select(
            studyMember.id.as(aliasStudyMemberId),
            user.nickname.as(aliasNickname),
            user.profileImageUrl.as(aliasProfileImageUrl),
            timeVote.voteId.count().gt(0).as(aliasIsSubmitted)
        )
        .from(studyMember)
        .leftJoin(user).on(studyMember.userId.eq(user.id))
        .leftJoin(timeVote).on(
            timeVote.period.periodId.eq(periodId)
                .and(timeVote.studyMemberId.eq(studyMember.id))
        )
        .where(
            studyMember.study.id.eq(studyId),
            studyMember.activated.isTrue()
        )
        // 중복 방지 (id, 닉네임, 프로필 기준으로 그룹핑)
        .groupBy(studyMember.id, user.nickname, user.profileImageUrl)
        .fetch();

    // Tuple 결과를 DTO 로 변환 (alias 기반으로 값 추출)
    return results.stream()
        .map(tuple -> TimeVoteSubmissionStatusResponse.builder()
            .studyMemberId(tuple.get(aliasStudyMemberId))
            .nickname(tuple.get(aliasNickname))
            .profileImageUrl(tuple.get(aliasProfileImageUrl))
            .isSubmitted(tuple.get(aliasIsSubmitted))
            .build())
        .collect(Collectors.toList());
  }
}
