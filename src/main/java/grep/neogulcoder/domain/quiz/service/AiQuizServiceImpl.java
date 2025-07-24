package grep.neogulcoder.domain.quiz.service;

import grep.neogulcoder.domain.quiz.controller.dto.response.QuizResponse;
import grep.neogulcoder.domain.quiz.entity.Quiz;
import grep.neogulcoder.domain.quiz.repository.AiQuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AiQuizServiceImpl {

    private final AiQuizService aiQuizService;
    private final AiQuizRepository aiQuizRepository;

    @Transactional
    public QuizResponse createAndSaveQuiz(Long postId, String postContent) {

        QuizResponse quizResponse = aiQuizService.createQuiz(postContent);

        Quiz quiz = Quiz.builder()
            .postId(postId)
            .quiz(quizResponse.getQuizContent())
            .quizAnswer(quizResponse.getQuizAnswer())
            .build();

        aiQuizRepository.save(quiz);

        return quizResponse;

    }

}