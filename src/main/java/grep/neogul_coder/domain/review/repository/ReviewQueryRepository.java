package grep.neogul_coder.domain.review.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import grep.neogul_coder.domain.review.entity.ReviewEntity;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static grep.neogul_coder.domain.review.entity.QReviewEntity.reviewEntity;

@Repository
public class ReviewQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public ReviewQueryRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Page<ReviewEntity> findContentsPagingBy(Pageable pageable, long userId) {
        List<ReviewEntity> reviews = queryFactory.selectFrom(reviewEntity)
                .where(reviewEntity.targetUserId.eq(userId), reviewEntity.content.isNotNull())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory.select(reviewEntity.count())
                .from(reviewEntity)
                .where(reviewEntity.targetUserId.eq(userId), reviewEntity.content.isNotNull())
                .fetchOne();

        return new PageImpl<>(reviews, pageable, (total != null) ? total : 0L);
    }

    public ReviewEntity findBy(long studyId, long targetUserId, long writeUserId) {
        return queryFactory.selectFrom(reviewEntity)
                .where(
                        reviewEntity.studyId.eq(studyId),
                        reviewEntity.targetUserId.eq(targetUserId),
                        reviewEntity.writeUserId.eq(writeUserId)
                )
                .fetchOne();
    }
}
