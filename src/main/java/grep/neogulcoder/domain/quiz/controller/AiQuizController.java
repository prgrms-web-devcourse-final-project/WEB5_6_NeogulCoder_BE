package grep.neogulcoder.domain.quiz.controller;

import grep.neogulcoder.domain.quiz.controller.dto.response.QuizResponse;
import grep.neogulcoder.domain.quiz.entity.Quiz;
import grep.neogulcoder.domain.quiz.exception.PostNotFreeException;
import grep.neogulcoder.domain.quiz.exception.code.QuizErrorCode;
import grep.neogulcoder.domain.quiz.repository.AiQuizRepository;
import grep.neogulcoder.domain.quiz.service.AiQuizServiceImpl;
import grep.neogulcoder.domain.studypost.Category;
import grep.neogulcoder.domain.studypost.StudyPost;
import grep.neogulcoder.domain.studypost.repository.StudyPostRepository;
import grep.neogulcoder.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/ai")
public class AiQuizController implements AiQuizSpecification {

    private final StudyPostRepository studyPostRepository;
    private final AiQuizServiceImpl aiQuizServiceImpl;
    private final AiQuizRepository aiQuizRepository;

    @GetMapping("/{postId}")
    public ApiResponse<QuizResponse> get(@PathVariable("postId") Long postId) {

        StudyPost post = studyPostRepository.findById(postId).orElseThrow();

        if (!Category.FREE.equals((post.getCategory()))) {
            throw new PostNotFreeException(QuizErrorCode.POST_NOT_FREE_ERROR);
        }

        Optional<Quiz> quiz = aiQuizRepository.findByPostId(postId);
        if (quiz.isPresent()) {
            Quiz q = quiz.get();
            QuizResponse quizResponse = QuizResponse.toResponse(q.getQuizContent(), q.isQuizAnswer());
            return ApiResponse.success(quizResponse);
        }

        QuizResponse quizResponse = aiQuizServiceImpl.createAndSaveQuiz(postId, post.getContent());
        return ApiResponse.success(quizResponse);
    }

}