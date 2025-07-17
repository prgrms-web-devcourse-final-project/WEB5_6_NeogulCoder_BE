package grep.neogul_coder.domain.recruitment.comment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import grep.neogul_coder.domain.recruitment.comment.RecruitmentPostComment;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static grep.neogul_coder.domain.recruitment.post.QRecruitmentPostComment.recruitmentPostComment;

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
}
