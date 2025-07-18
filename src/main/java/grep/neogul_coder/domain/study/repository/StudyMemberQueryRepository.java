package grep.neogul_coder.domain.study.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import grep.neogul_coder.domain.study.StudyMember;
import grep.neogul_coder.domain.study.controller.dto.response.ExtendParticipationResponse;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static grep.neogul_coder.domain.study.QStudy.study;
import static grep.neogul_coder.domain.study.QStudyMember.studyMember;
import static grep.neogul_coder.domain.users.entity.QUser.user;

@Repository
public class StudyMemberQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public StudyMemberQueryRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<StudyMember> findAllFetchStudyByUserId(long userId) {
        return queryFactory.selectFrom(studyMember)
                .where(
                        studyMember.userId.eq(userId),
                        studyMember.activated.isTrue()
                )
                .join(studyMember.study, study).fetchJoin()
                .fetch();
    }

    public long findCurrentCountBy(long studyId){
        Long participatedCount = queryFactory.select(studyMember.count())
                .from(studyMember)
                .where(
                        studyMember.study.id.eq(studyId),
                        studyMember.activated.isTrue()
                )
                .join(studyMember.study, study)
                .fetchOne();

        return participatedCount != null ? participatedCount : 0;
    }

    public StudyMember findByStudyIdAndUserId(long studyId, long userId) {
        return queryFactory.selectFrom(studyMember)
                .where(
                        studyMember.study.id.eq(studyId),
                        studyMember.userId.eq(userId),
                        studyMember.activated.isTrue()
                )
                .fetchOne();
    }

    public List<ExtendParticipationResponse> findExtendParticipation(Long studyId) {
        return queryFactory
            .select(Projections.constructor(
                ExtendParticipationResponse.class,
                studyMember.userId,
                user.nickname,
                studyMember.isParticipated
            ))
            .from(studyMember)
            .join(user).on(user.id.eq(studyMember.userId))
            .where(studyMember.study.id.eq(studyId))
            .fetch();
    }
}
