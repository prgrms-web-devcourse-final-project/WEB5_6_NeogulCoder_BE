package grep.neogul_coder.global.auth.repository;

import grep.neogul_coder.global.auth.entity.UserBlackList;
import org.springframework.data.repository.CrudRepository;

public interface UserBlackListRepository extends CrudRepository<UserBlackList, String> {
    void deleteByEmail(String email);
}
