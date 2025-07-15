package grep.neogul_coder.domain.review.controller.dto.response;

import grep.neogul_coder.domain.review.ReviewTag;
import grep.neogul_coder.domain.review.ReviewType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ToString
@Getter
public class MyReviewTagsInfo {

    @Schema(example = "{ GOOD: [ { reviewTag: 시간을 잘 지켜요, reviewTagCount: 3}, { reviewTag: 친절해요, reviewTagCount: 2} ], " +
            "BAD : [ { reviewTag: 지각했어요, reviewTagCount: 2 } ]", description = "Map<String, List<ReviewTagCountInfo>>")
    private Map<String, List<ReviewTagCountInfo>> reviewTypeMap;

    private MyReviewTagsInfo(Map<String, List<ReviewTagCountInfo>> reviewTypeMap) {
        this.reviewTypeMap = reviewTypeMap;
    }

    public static MyReviewTagsInfo of(Map<ReviewType, Map<ReviewTag, Integer>> tagCountMapByType) {
        Map<String, List<ReviewTagCountInfo>> result = tagCountMapByType.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().getDescription(),
                        entry -> convertToReviewTagCountInfoList(entry.getValue())
                ));

        return new MyReviewTagsInfo(result);
    }

    private static List<ReviewTagCountInfo> convertToReviewTagCountInfoList(Map<ReviewTag, Integer> reviewTagCountMap) {
        return reviewTagCountMap.entrySet().stream()
                .map(entry -> new ReviewTagCountInfo(entry.getKey().getDescription(), entry.getValue()))
                .toList();
    }

    @ToString
    @Getter
    private static class ReviewTagCountInfo {

        @Schema(example = "항상 먼저 도와주고 분위기를 이끌어주는 팀원이었어요.", description = "리뷰 태그")
        private String reviewTag;

        @Schema(example = "3", description = "리뷰 태그 개수")
        private int reviewTagCount;

        private ReviewTagCountInfo(String reviewTag, int reviewTagCount) {
            this.reviewTag = reviewTag;
            this.reviewTagCount = reviewTagCount;
        }
    }
}
