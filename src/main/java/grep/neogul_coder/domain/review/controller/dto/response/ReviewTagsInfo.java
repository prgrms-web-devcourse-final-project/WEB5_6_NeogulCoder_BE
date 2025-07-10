package grep.neogul_coder.domain.review.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class ReviewTagsInfo {

    @Schema(example = "{ GOOD: [ { reviewTag: 시간을 잘 지켜요, reviewTagCount: 3}, { reviewTag: 친절해요, reviewTagCount: 2} ], " +
            "BAD : [ { reviewTag: 지각했어요, reviewTagCount: 2 } ]",
            description = "Map<String, List<ReviewTagCountInfo>>")
    private Map<String, List<ReviewTagCountInfo>> reviewTypeMap;

    @Getter
    private static class ReviewTagCountInfo {

        private String reviewTag;
        private int reviewTagCount;
    }
}
