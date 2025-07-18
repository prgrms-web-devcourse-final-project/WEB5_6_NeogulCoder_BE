package grep.neogul_coder.domain.users.repository;

import grep.neogul_coder.domain.users.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickname);
    Page<User> findByEmailContainingIgnoreCase(String email, Pageable pageable);
    List<User> findByIdIn(List<Long> userIds);
}
