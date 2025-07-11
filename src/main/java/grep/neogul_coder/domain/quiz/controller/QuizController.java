package grep.neogul_coder.domain.quiz.controller;

import grep.neogul_coder.domain.quiz.controller.dto.request.QuizRequest;
import grep.neogul_coder.domain.quiz.controller.dto.response.QuizResponse;
import grep.neogul_coder.global.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post/ai")
public class QuizController implements QuizSpecification{

    @GetMapping("/{id}")
    public ApiResponse<QuizResponse> get(@PathVariable("id") Long postId,
        @RequestBody QuizRequest request) {
        QuizResponse response = null;
        return ApiResponse.success(response);
    }

}
