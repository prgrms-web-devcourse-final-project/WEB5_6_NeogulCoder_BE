package grep.neogul_coder.domain.recruitment.post.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import grep.neogul_coder.domain.recruitment.post.RecruitmentPost;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static grep.neogul_coder.domain.recruitment.post.QRecruitmentPost.recruitmentPost;

@Repository
public class RecruitmentPostQueryRepository {

    private final EntityManager em;
    private JPAQueryFactory queryFactory;

    public RecruitmentPostQueryRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<RecruitmentPost> findPaging(Pageable pageable) {
        return queryFactory.selectFrom(recruitmentPost)
                .where(recruitmentPost.activated.isTrue())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(recruitmentPost.createdDate.desc())
                .fetch();
    }
}
