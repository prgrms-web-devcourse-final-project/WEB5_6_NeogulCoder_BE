package grep.neogul_coder.domain.review;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReviewTagFinder {

    private static final Map<String, ReviewTag> tagMap = Stream.of(
                    GoodReviewTag.values(),
                    BadReviewTag.values(),
                    ExcellentReviewTag.values()
            )
            .flatMap(Arrays::stream)
            .map(ReviewTag.class::cast)
            .collect(Collectors.toMap(
                    ReviewTag::getDescription,
                    tag -> tag
            ));

    public ReviewTag findBy(String reviewTag) {
        return Optional.of(tagMap.get(reviewTag))
                .orElseThrow(() -> new IllegalArgumentException("일치 하는 태그가 없습니다."));
    }
}
