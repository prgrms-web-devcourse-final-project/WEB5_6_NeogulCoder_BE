package grep.neogul_coder.domain.study.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import grep.neogul_coder.domain.study.StudyMember;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import static grep.neogul_coder.domain.study.QStudyMember.studyMember;

@Repository
public class StudyMemberQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public StudyMemberQueryRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public StudyMember findByStudyIdAndUserId(long studyId, long userId) {
        return queryFactory.selectFrom(studyMember)
                .where(
                        studyMember.id.eq(studyId),
                        studyMember.userId.eq(userId),
                        studyMember.activated.isTrue()
                )
                .fetchOne();
    }
}
