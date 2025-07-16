package grep.neogul_coder.domain.quiz.controller;

import grep.neogul_coder.domain.quiz.controller.dto.response.QuizResponse;
import grep.neogul_coder.domain.quiz.exception.PostNotFreeException;
import grep.neogul_coder.domain.quiz.exception.code.QuizErrorCode;
import grep.neogul_coder.domain.quiz.service.AiQuizServiceImpl;
import grep.neogul_coder.domain.studypost.Category;
import grep.neogul_coder.domain.studypost.StudyPost;
import grep.neogul_coder.domain.studypost.service.StudyPostService;
import grep.neogul_coder.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/ai")
public class AiQuizController implements AiQuizSpecification {

    private final StudyPostService studyPostService;
    private final AiQuizServiceImpl aiQuizServiceImpl;

    @GetMapping("/{postId}")
    public ApiResponse<QuizResponse> get(@PathVariable("postId") Long postId) {
        StudyPost post = studyPostService.findById(postId);

        if(!Category.FREE.equals((post.getCategory()))){
            throw new PostNotFreeException(QuizErrorCode.POST_NOT_FREE_ERROR);
        }

        QuizResponse quizResponse = aiQuizServiceImpl.createAndSaveQuiz(postId,post.getContent());
        return ApiResponse.success(quizResponse);
    }

}
