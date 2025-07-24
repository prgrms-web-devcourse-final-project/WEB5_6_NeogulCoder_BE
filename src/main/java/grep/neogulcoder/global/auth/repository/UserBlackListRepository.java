package grep.neogulcoder.global.auth.repository;

import grep.neogulcoder.global.auth.entity.UserBlackList;
import org.springframework.data.repository.CrudRepository;

public interface UserBlackListRepository extends CrudRepository<UserBlackList, String> {
    void deleteByEmail(String email);
}
