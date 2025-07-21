package grep.neogul_coder.domain.prtemplate.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import grep.neogul_coder.domain.prtemplate.controller.dto.response.PrPageResponse;
import grep.neogul_coder.domain.review.entity.QMyReviewTagEntity;
import grep.neogul_coder.domain.review.entity.QReviewEntity;
import grep.neogul_coder.domain.review.entity.QReviewTagEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PrTemplateQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<PrPageResponse.ReviewTypeDto> findReviewTypeCountsByTargetUser(Long targetUserId) {
        QReviewEntity review = QReviewEntity.reviewEntity;
        QMyReviewTagEntity myReviewTag = QMyReviewTagEntity.myReviewTagEntity;
        QReviewTagEntity reviewTag = QReviewTagEntity.reviewTagEntity;

        return queryFactory
                .select(Projections.constructor(
                        PrPageResponse.ReviewTypeDto.class,
                        reviewTag.reviewType,
                        myReviewTag.count().intValue()
                ))
                .from(myReviewTag)
                .join(myReviewTag.reviewEntity, review)
                .join(myReviewTag.reviewTag, reviewTag)
                .where(review.targetUserId.eq(targetUserId))
                .groupBy(reviewTag.reviewType)
                .fetch();
    }



}
