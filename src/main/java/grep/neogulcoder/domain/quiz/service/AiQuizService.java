package grep.neogulcoder.domain.quiz.service;

import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import grep.neogulcoder.domain.quiz.controller.dto.response.QuizResponse;

@AiService
public interface AiQuizService {

    @UserMessage("""
        다음은 스터디 게시글의 내용이야.
        
        이 내용을 바탕으로 퀴즈를 생성해줘.
        퀴즈는 한 문장으로, 사용자가 내용을 이해했는지 확인할 수 있는 문제여야 해.
        응답은 반드시 JSON 형식으로 반환해줘. 예시:
        {
          "postContent": "자바에서 클래스와 객체의 차이는 무엇인가요?",
          "quizAnswer": true
        }
        
        게시글 내용:
        {{postContent}}
        """)
    QuizResponse createQuiz(String postContent);
}