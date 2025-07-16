package grep.neogul_coder.domain.quiz.repository;

import grep.neogul_coder.domain.quiz.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AiQuizRepository extends JpaRepository<Quiz, Long> {

}