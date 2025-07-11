package grep.neogul_coder.domain.quiz.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "퀴즈 생성 요청 DTO - AI가 게시글 내용을 바탕으로 퀴즈를 생성합니다.")
@Data
public class QuizRequest {

    @Schema(description = "스터디 게시글의 타입입니다.", example = "자유 게시글")
    private String postCategory;

    @Schema(description = "AI가 퀴즈를 생성할 기준이 되는 스터디 게시글의 본문 내용"
        , example = "자바에서 클래스와 객체의 차이에 대해 설명한 스터디 요약입니다.")
    private String postContent;


}