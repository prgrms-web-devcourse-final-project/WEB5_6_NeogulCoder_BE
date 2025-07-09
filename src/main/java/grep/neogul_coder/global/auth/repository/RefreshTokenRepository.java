package grep.neogul_coder.global.auth.repository;

import grep.neogul_coder.global.auth.entity.RefreshToken;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    Optional<RefreshToken> findByAtId(String atId);
    void deleteByAtId(String atId);
}
