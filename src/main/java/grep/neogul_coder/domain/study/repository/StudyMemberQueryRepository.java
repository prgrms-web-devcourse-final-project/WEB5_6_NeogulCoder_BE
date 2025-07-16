package grep.neogul_coder.domain.study.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import grep.neogul_coder.domain.study.StudyMember;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static grep.neogul_coder.domain.study.QStudy.study;
import static grep.neogul_coder.domain.study.QStudyMember.studyMember;

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

    public StudyMember findByStudyIdAndUserId(long studyId, long userId) {
        return queryFactory.selectFrom(studyMember)
                .where(
                        studyMember.study.id.eq(studyId),
                        studyMember.userId.eq(userId),
                        studyMember.activated.isTrue()
                )
                .fetchOne();
    }
}
