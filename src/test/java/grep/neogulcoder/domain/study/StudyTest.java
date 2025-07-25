package grep.neogulcoder.domain.study;

import grep.neogulcoder.domain.users.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class StudyTest {

    @DisplayName("리뷰가 가능한 시간은 스터디가 종류된 직후 시간부터 가능 하다.")
    @Test
    void isReviewableAt() {
        //given
        LocalDateTime endDateTime = LocalDateTime.of(2025, 7, 1, 0, 0, 0);
        Study study = createStudy("스터디", endDateTime);

        LocalDateTime currentDateTime = LocalDateTime.of(2025, 7, 1, 0, 0, 0);

        //when
        boolean reviewable = study.isReviewableAt(currentDateTime);

        //then
        assertThat(reviewable).isTrue();
    }

    @DisplayName("리뷰 가능한 시간 이후에는 리뷰를 할수 없습니다.")
    @Test
    void isReviewableAt_WhenIsAfterExpiredDateTime_WhenNotReviewable() {
        //given
        LocalDateTime endDateTime = LocalDateTime.of(2025, 7, 1, 0, 0, 0);
        Study study = createStudy("스터디", endDateTime);

        LocalDateTime currentDateTime = LocalDateTime.of(2025, 7, 8, 0, 0, 1);

        //when
        boolean reviewable = study.isReviewableAt(currentDateTime);

        //then
        assertThat(reviewable).isFalse();
    }

    public User createUser(String nickname) {
        return User.builder()
                .nickname(nickname)
                .password("tempPassword")
                .build();
    }

    private Study createStudy(String name, LocalDateTime endDate) {
        return Study.builder()
                .name(name)
                .endDate(endDate)
                .build();
    }
}