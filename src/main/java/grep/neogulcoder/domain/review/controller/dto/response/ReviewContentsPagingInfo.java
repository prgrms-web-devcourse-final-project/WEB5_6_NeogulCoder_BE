package grep.neogulcoder.domain.review.controller.dto.response;

import grep.neogulcoder.domain.review.entity.ReviewEntity;
import grep.neogulcoder.domain.users.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@ToString
@Getter
public class ReviewContentsPagingInfo {

    @Schema(example = "[ { nickname: 너굴, imageAccessUrl: example.com, createdAt: 2025-07-10, content: 열심히 하십니다 } ]")
    private List<ReviewContentsInfo> reviewContents;

    @Schema(example = "30", description = "전체 요소 개수")
    private long totalElementCount;

    @Schema(example = "10", description = "전체 페이지 수")
    private int totalPages;

    @Schema(example = "false", description = "다음 페이지 여부")
    private boolean hasNext;

    @Builder
    private ReviewContentsPagingInfo(Page<ReviewEntity> page, Map<Long, User> userMap) {
        List<ReviewEntity> reviews = page.getContent();

        this.reviewContents = mapToReviewContentsInfo(reviews, userMap);
        this.totalElementCount = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.hasNext = page.hasNext();
    }

    public static ReviewContentsPagingInfo of(Page<ReviewEntity> reviewEntities, Map<Long, User> userMap) {
        return ReviewContentsPagingInfo.builder()
                .page(reviewEntities)
                .userMap(userMap)
                .build();
    }

    private List<ReviewContentsInfo> mapToReviewContentsInfo(List<ReviewEntity> reviews, Map<Long, User> userMap) {
        return reviews.stream()
                .map(review -> {
                    User user = userMap.get(review.getWriteUserId());
                    String nickname = user.getNickname();
                    String imageUrl = user.getProfileImageUrl();
                    LocalDate createdDate = review.getCreatedDate().toLocalDate();

                    return ReviewContentsInfo.of(nickname, imageUrl, createdDate, review.getContent());
                }).toList();
    }

    @ToString
    @Getter
    private static class ReviewContentsInfo {

        @Schema(example = "짱구", description = "회원 닉네임")
        private String nickname;

        @Schema(example = "example.com", description = "이미지 URL")
        private String imageAccessUrl;

        @Schema(example = "2025-07-10", description = "리뷰 생성 일자")
        private LocalDate createdAt;

        @Schema(example = "열심히 하십니다", description = "주관 리뷰 내용")
        private String content;

        @Builder
        private ReviewContentsInfo(String nickname, String imageAccessUrl, LocalDate createdAt, String content) {
            this.nickname = nickname;
            this.imageAccessUrl = imageAccessUrl;
            this.createdAt = createdAt;
            this.content = content;
        }

        private static ReviewContentsInfo of(String nickname, String imageAccessUrl, LocalDate createdAt, String content) {
            return ReviewContentsInfo.builder()
                    .nickname(nickname)
                    .imageAccessUrl(imageAccessUrl)
                    .createdAt(createdAt)
                    .content(content)
                    .build();
        }
    }
}
