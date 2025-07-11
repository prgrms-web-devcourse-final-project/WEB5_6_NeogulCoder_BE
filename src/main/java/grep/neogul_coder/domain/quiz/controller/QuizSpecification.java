package grep.neogul_coder.domain.quiz.controller;

import grep.neogul_coder.domain.quiz.controller.dto.request.QuizRequest;
import grep.neogul_coder.domain.quiz.controller.dto.response.QuizResponse;
import grep.neogul_coder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Ai_Quiz", description = "Ai 퀴즈 API")
public interface QuizSpecification {

    @Operation(summary = "Ai 퀴즈 가져오기", description = "Ai퀴즈를 가져옵니다.")
    ApiResponse<QuizResponse> get(@PathVariable("id") Long postId, @RequestBody QuizRequest request);

}
