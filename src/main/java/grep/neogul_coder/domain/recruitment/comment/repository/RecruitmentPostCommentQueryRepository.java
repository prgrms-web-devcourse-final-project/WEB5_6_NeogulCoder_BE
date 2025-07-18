package grep.neogul_coder.domain.recruitment.comment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import grep.neogul_coder.domain.recruitment.comment.RecruitmentPostComment;
import grep.neogul_coder.domain.recruitment.comment.controller.dto.response.CommentsWithWriterInfo;
import grep.neogul_coder.domain.recruitment.comment.controller.dto.response.QCommentsWithWriterInfo;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static grep.neogul_coder.domain.recruitment.comment.QRecruitmentPostComment.recruitmentPostComment;
import static grep.neogul_coder.domain.users.entity.QUser.user;

@Repository
public class RecruitmentPostCommentQueryRepository {

    private final EntityManager em;
    private JPAQueryFactory queryFactory;

    public RecruitmentPostCommentQueryRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<RecruitmentPostComment> findByPostIdIn(List<Long> postIds) {
        return queryFactory.selectFrom(recruitmentPostComment)
                .where(
                        recruitmentPostComment.recruitmentPost.id.in(postIds),
                        recruitmentPostComment.activated.isTrue()
                )
                .fetch();
    }

    public List<CommentsWithWriterInfo> findCommentsWithWriterInfo(Long recruitmentPostId) {
        return queryFactory.select(
                        new QCommentsWithWriterInfo(
                                user.id,
                                user.nickname,
                                user.profileImageUrl.as("imageUrl"),
                                recruitmentPostComment.content,
                                recruitmentPostComment.createdDate,
                                user.activated
                        )
                )
                .from(recruitmentPostComment)
                .join(user).on(recruitmentPostComment.userId.eq(user.id))
                .where(
                        recruitmentPostComment.recruitmentPost.id.eq(recruitmentPostId),
                        recruitmentPostComment.activated.isTrue()
                )
                .fetch();
    }
}
