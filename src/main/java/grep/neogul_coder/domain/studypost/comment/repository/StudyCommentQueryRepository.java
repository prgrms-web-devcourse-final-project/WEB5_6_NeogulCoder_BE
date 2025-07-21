package grep.neogul_coder.domain.studypost.comment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import grep.neogul_coder.domain.studypost.comment.StudyPostComment;
import grep.neogul_coder.domain.studypost.controller.dto.response.CommentInfo;
import grep.neogul_coder.domain.studypost.controller.dto.response.QCommentInfo;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static grep.neogul_coder.domain.studypost.QStudyPost.studyPost;
import static grep.neogul_coder.domain.studypost.comment.QStudyPostComment.studyPostComment;
import static grep.neogul_coder.domain.users.entity.QUser.*;

@Repository
public class StudyCommentQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public StudyCommentQueryRepository(EntityManager em) {
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
}
