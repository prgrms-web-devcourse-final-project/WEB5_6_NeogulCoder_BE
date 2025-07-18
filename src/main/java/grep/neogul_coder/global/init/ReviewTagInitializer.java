package grep.neogul_coder.global.init;

import grep.neogul_coder.domain.review.BadReviewTag;
import grep.neogul_coder.domain.review.ExcellentReviewTag;
import grep.neogul_coder.domain.review.GoodReviewTag;
import grep.neogul_coder.domain.review.ReviewTag;
import grep.neogul_coder.domain.review.entity.ReviewTagEntity;
import grep.neogul_coder.domain.review.repository.ReviewTagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@Component
public class ReviewTagInitializer {

    private final ReviewTagRepository reviewTagRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initReviewTags() {
        long count = reviewTagRepository.count();
        if (count == 0 || count == 5) {
            List<ReviewTagEntity> reviewTagEntities = Stream.of(
                            GoodReviewTag.values(),
                            BadReviewTag.values(),
                            ExcellentReviewTag.values())
                    .flatMap(Arrays::stream)
                    .map(ReviewTag.class::cast)
                    .map(reviewTag -> new ReviewTagEntity(reviewTag.getReviewType(), reviewTag))
                    .toList();

            reviewTagRepository.saveAll(reviewTagEntities);
            log.info("리뷰 태그 저장 실행");
            return;
        }

        log.info("리뷰 태그 초기화 실행 X");
    }
}
