package grep.neogulcoder.domain.quiz.repository;

import grep.neogulcoder.domain.quiz.entity.Quiz;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AiQuizRepository extends JpaRepository<Quiz, Long> {
    Optional<Quiz> findByPostId(Long postId);
}