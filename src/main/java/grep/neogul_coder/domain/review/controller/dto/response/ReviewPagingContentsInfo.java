package grep.neogul_coder.domain.review.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class ReviewPagingContentsInfo {

    @Schema(example = "[ { nickname: 너굴, imageAccessUrl: example.com, createdAt: 2025-07-10, content: 열심히 하십니다 } ]")
    private List<ReviewContentsInfo> reviewContents;

    @Schema(example = "30", description = "전체 요소 개수")
    private int size;

    @Schema(example = "10", description = "전체 페이지 수")
    private int totalPages;

    @Schema(example = "false", description = "다음 페이지 여부")
    private boolean hasNext;

    @Getter
    private static class ReviewContentsInfo {

        @Schema(example = "짱구", description = "회원 닉네임")
        private String nickname;

        @Schema(example = "example.com", description = "이미지 URL")
        private String imageAccessUrl;

        @Schema(example = "2025-07-10", description = "생성 일자")
        private LocalDate createdAt;

        @Schema(example = "열심히 하십니다", description = "주관 리뷰 내용")
        private String content;
    }
}
