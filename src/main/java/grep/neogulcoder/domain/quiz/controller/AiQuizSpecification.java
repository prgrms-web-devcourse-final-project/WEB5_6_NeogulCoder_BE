package grep.neogulcoder.domain.quiz.controller;

import grep.neogulcoder.domain.quiz.controller.dto.response.QuizResponse;
import grep.neogulcoder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Ai_Quiz", description = "Ai 퀴즈 API")
public interface AiQuizSpecification {

    @Operation(summary = "Ai 퀴즈 가져오기", description = "Ai퀴즈를 저장하고 가져옵니다.")
    ApiResponse<QuizResponse> get(@PathVariable("id") Long postId);

}
