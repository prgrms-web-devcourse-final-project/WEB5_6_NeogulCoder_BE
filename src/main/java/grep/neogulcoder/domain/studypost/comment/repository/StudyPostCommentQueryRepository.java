package grep.neogulcoder.domain.studypost.comment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import grep.neogulcoder.domain.studypost.comment.StudyPostComment;
import grep.neogulcoder.domain.studypost.controller.dto.response.CommentInfo;
import grep.neogulcoder.domain.studypost.controller.dto.response.QCommentInfo;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static grep.neogulcoder.domain.studypost.QStudyPost.studyPost;
import static grep.neogulcoder.domain.studypost.comment.QStudyPostComment.studyPostComment;
import static grep.neogulcoder.domain.users.entity.QUser.user;

@Repository
public class StudyPostCommentQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public StudyPostCommentQueryRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<StudyPostComment> findAllByPostId(Long postId) {
        return queryFactory.selectFrom(studyPostComment)
                .join(studyPost).on(studyPost.id.eq(studyPostComment.postId))
                .where(
                        studyPostComment.activated.isTrue(),
                        studyPostComment.postId.eq(postId)
                )
                .fetch();
    }

    public List<CommentInfo> findWriterInfosByPostId(long postId) {
        return queryFactory.select(
                        new QCommentInfo(
                                user.id,
                                user.nickname,
                                user.profileImageUrl,
                                studyPostComment.id,
                                studyPostComment.content,
                                studyPostComment.createdDate
                        )
                )
                .from(studyPostComment)
                .join(user).on(studyPostComment.userId.eq(user.id))
                .where(
                        studyPostComment.activated.isTrue(),
                        studyPostComment.postId.eq(postId)
                )
                .fetch();
    }

    public List<StudyPostComment> findByPostIdIn(List<Long> postIds) {
        return queryFactory.select(studyPostComment)
                .from(studyPost)
                .where(
                        studyPostComment.activated.isTrue(),
                        studyPostComment.postId.in(postIds)
                )
                .fetch();
    }

    public Optional<StudyPostComment> findById(long commentId, long userId) {
        StudyPostComment comment = queryFactory.selectFrom(studyPostComment)
                .where(
                        studyPostComment.activated.isTrue(),
                        studyPostComment.id.eq(commentId),
                        studyPostComment.userId.eq(userId)
                ).fetchOne();
        return Optional.ofNullable(comment);
    }
}
