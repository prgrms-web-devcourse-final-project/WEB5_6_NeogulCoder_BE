package grep.neogulcoder.domain.review;

import grep.neogulcoder.global.exception.business.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReviewTagsTest {

    @DisplayName("리뷰 태그들의 리뷰 타입을 확인 합니다.")
    @TestFactory
    Collection<DynamicTest> ensureSingleReviewType() {

        return List.of(
                DynamicTest.dynamicTest("리뷰 태그가 하나의 리뷰 타입을 가진 경우 리뷰 타입을 반환 합니다.", () -> {
                    //given
                    List<ReviewTag> reviewTagList = List.of(
                            ExcellentReviewTag.INITIATIVE,
                            ExcellentReviewTag.EFFICIENT,
                            ExcellentReviewTag.COMMUNICATOR
                    );

                    ReviewTags reviewTags = ReviewTags.from(reviewTagList);

                    //when
                    ReviewType reviewType = reviewTags.ensureSingleReviewType();

                    //then
                    assertThat(reviewType).isEqualTo(ReviewType.EXCELLENT);
                }),

                DynamicTest.dynamicTest("리뷰 태그가 여러 리뷰 타입을 가진 경우 예외가 발생 합니다.", () -> {
                    //given
                    List<ReviewTag> reviewTagList = List.of(
                            ExcellentReviewTag.INITIATIVE,
                            ExcellentReviewTag.EFFICIENT,
                            GoodReviewTag.GOOD_ADAPTATION
                    );

                    ReviewTags reviewTags = ReviewTags.from(reviewTagList);

                    //when //then
                    assertThatThrownBy(reviewTags::ensureSingleReviewType)
                            .isInstanceOf(BusinessException.class).hasMessage("단일 리뷰 타입이 아닙니다. ( ex) GOOD, BAD 혼합 )");
                })
        );
    }
}