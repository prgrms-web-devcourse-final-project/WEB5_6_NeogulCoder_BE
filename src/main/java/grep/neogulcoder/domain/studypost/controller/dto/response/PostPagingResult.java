package grep.neogulcoder.domain.studypost.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PostPagingResult {

    @Schema(description = "최신 공지글")
    private List<NoticePostInfo> noticePostInfos;

    @Schema(description = "게시글 페이징 리스트")
    private List<PostPagingInfo> postInfos;

    @Schema(example = "3", description = "총 페이지수")
    private long totalPage;

    @Schema(example = "3", description = "총 요소 개수")
    private long totalElementCount;

    @Schema(example = "true", description = "다음 페이지 여부")
    private boolean hasNext;

    public PostPagingResult(List<NoticePostInfo> noticePostInfos, Page<PostPagingInfo> page) {
        this.noticePostInfos = noticePostInfos;
        this.postInfos = page.getContent();
        this.totalPage = page.getTotalPages();
        this.totalElementCount = page.getTotalElements();
        this.hasNext = page.hasNext();
    }
}
