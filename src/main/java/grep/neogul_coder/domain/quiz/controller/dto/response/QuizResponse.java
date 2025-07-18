package grep.neogul_coder.domain.quiz.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Schema(description = "퀴즈 응답 DTO")
@Getter
@Data
public class QuizResponse {

    @Schema(description = "퀴즈 본문 내용", example = "자바에서 클래스와 객체의 차이는 무엇인가요?")
    private String quizContent;

    @Schema(description = "퀴즈 정답 여부", example = "true")
    private Boolean quizAnswer;

    @Builder
    private QuizResponse(String quizContent, Boolean quizAnswer) {
        this.quizContent = quizContent;
        this.quizAnswer = quizAnswer;
    }

    public static QuizResponse toResponse(String quizContent, Boolean quizAnswer) {
        return QuizResponse.builder()
            .quizContent(quizContent)
            .quizAnswer(quizAnswer)
            .build();
    }

}
