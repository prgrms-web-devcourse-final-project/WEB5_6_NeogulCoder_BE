package grep.neogul_coder.domain.studypost.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import grep.neogul_coder.domain.studypost.QStudyPost;
import grep.neogul_coder.domain.studypost.StudyPost;
import grep.neogul_coder.domain.studypost.controller.dto.response.PostInfo;
import grep.neogul_coder.domain.studypost.controller.dto.response.QPostInfo;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static grep.neogul_coder.domain.users.entity.QUser.user;

@Repository
public class StudyPostQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    private final QStudyPost studyPost = QStudyPost.studyPost;

    public StudyPostQueryRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public PostInfo findPostWriterInfo(Long postId) {
        return queryFactory.select(
                        new QPostInfo(
                                user.id,
                                user.nickname,
                                user.profileImageUrl,
                                studyPost.id.as("postId"),
                                studyPost.title,
                                studyPost.category,
                                studyPost.content,
                                studyPost.createdDate
                        )
                )
                .from(studyPost)
                .join(user).on(studyPost.userId.eq(user.id))
                .where(
                        studyPost.id.eq(postId),
                        studyPost.activated.isTrue()
                )
                .fetchOne();
    }

    public Optional<StudyPost> findByIdAndUserId(Long postId, long userId) {
        StudyPost findStudyPost = queryFactory.selectFrom(studyPost)
                .where(
                        studyPost.id.eq(postId),
                        studyPost.userId.eq(userId),
                        studyPost.activated.isTrue()
                )
                .fetchOne();

        return Optional.ofNullable(findStudyPost);
    }
}
