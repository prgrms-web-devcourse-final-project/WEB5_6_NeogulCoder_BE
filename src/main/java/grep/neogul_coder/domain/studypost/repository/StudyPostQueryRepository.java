package grep.neogul_coder.domain.studypost.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import grep.neogul_coder.domain.studypost.QStudyPost;
import grep.neogul_coder.domain.studypost.StudyPost;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class StudyPostQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public StudyPostQueryRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Optional<StudyPost> findByIdAndUserId(Long postId, long userId) {
        StudyPost studyPost = queryFactory.selectFrom(QStudyPost.studyPost)
                .where(
                        QStudyPost.studyPost.id.eq(postId),
                        QStudyPost.studyPost.userId.eq(userId),
                        QStudyPost.studyPost.activated.isTrue()
                )
                .fetchOne();

        return Optional.ofNullable(studyPost);
    }
}
