package grep.neogulcoder.global.auth.repository;

import grep.neogulcoder.global.auth.entity.TokenBlackList;
import org.springframework.data.repository.CrudRepository;

public interface AccessTokenBlackListRepository extends CrudRepository<TokenBlackList,String> {

}
