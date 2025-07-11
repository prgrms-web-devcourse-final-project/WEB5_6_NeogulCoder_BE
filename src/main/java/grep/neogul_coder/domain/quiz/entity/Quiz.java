package grep.neogul_coder.domain.quiz.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private Long postId;

    @NotNull
    private String quiz;

    @NotNull
    private boolean quizAnswer;

}
