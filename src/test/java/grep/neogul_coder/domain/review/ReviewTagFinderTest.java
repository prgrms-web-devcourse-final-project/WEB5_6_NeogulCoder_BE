package grep.neogul_coder.domain.review;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReviewTagFinderTest {

    @DisplayName("사용자에게 값을 받아 해당하는 리뷰 태그를 찾습니다.")
    @TestFactory
    Collection<DynamicTest> findBy() {
        return List.of(
                DynamicTest.dynamicTest("일치 하는 리뷰 태그가 있다면 리뷰태그를 반환 합니다.", () -> {
                    //given
                    ReviewTagFinder reviewTagFinder = new ReviewTagFinder();
                    String userInput = "약속된 일정에 자주 늦거나 참여율이 낮았어요.";

                    //when
                    ReviewTag reviewTag = reviewTagFinder.findBy(userInput);

                    //then
                    assertThat(reviewTag).isEqualTo(BadReviewTag.LOW_COMMITMENT);
                }),

                DynamicTest.dynamicTest("일치 하는 리뷰 태그가 없다면 예외가 발생 합니다.", () -> {
                    //given
                    ReviewTagFinder reviewTagFinder = new ReviewTagFinder();
                    String userInput = "리뷰태그로 등록 되지 않은 값입니다.";

                    //when //then
                    assertThatThrownBy(() -> reviewTagFinder.findBy(userInput))
                            .isInstanceOf(IllegalArgumentException.class).hasMessage("일치 하는 태그가 없습니다.");
                })
        );
    }
}