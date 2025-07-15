package grep.neogul_coder.domain.users.repository;

import grep.neogul_coder.domain.users.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickname);

    List<User> findByIdIn(List<Long> userIds);

    @Query("select nickname from User where id = :id")
    String findNicknameById(@Param("id") Long id);
}
