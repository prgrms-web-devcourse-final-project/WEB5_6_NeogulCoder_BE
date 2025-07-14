package grep.neogul_coder.domain.review.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public ReviewQueryRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public void findMyReviewTagFetchAll(List<Long> reviewIds) {
//        queryFactory.selectFrom(reviewTagEntity)
//                .join(reviewTagEntity)
    }
}
