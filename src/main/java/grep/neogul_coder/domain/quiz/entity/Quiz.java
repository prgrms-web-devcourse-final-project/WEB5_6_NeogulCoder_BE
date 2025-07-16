package grep.neogul_coder.domain.quiz.entity;

import grep.neogul_coder.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Entity
public class Quiz extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long postId;

    @NotNull
    private String quiz;

    @NotNull
    private boolean quizAnswer;

    @Builder
    protected Quiz(Long id, Long postId, String quiz, boolean quizAnswer) {
        this.id = id;
        this.postId = postId;
        this.quiz = quiz;
        this.quizAnswer = quizAnswer;
    }

    protected Quiz() {

    }

    public Quiz QuizInit(Long postId, String quiz, boolean quizAnswer){
        return Quiz.builder()
            .postId(postId)
            .quiz(quiz)
            .quizAnswer(quizAnswer)
            .build();
    }
}
