package grep.neogulcoder.domain.quiz.entity;

import grep.neogulcoder.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class Quiz extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long postId;

    private String quizContent;

    private boolean quizAnswer;

    @Builder
    protected Quiz(Long id, Long postId, String quiz, boolean quizAnswer) {
        this.id = id;
        this.postId = postId;
        this.quizContent = quiz;
        this.quizAnswer = quizAnswer;
    }

    protected Quiz() {

    }

    public Quiz QuizInit(Long postId, String quiz, boolean quizAnswer) {
        return Quiz.builder()
            .postId(postId)
            .quiz(quiz)
            .quizAnswer(quizAnswer)
            .build();
    }
}
