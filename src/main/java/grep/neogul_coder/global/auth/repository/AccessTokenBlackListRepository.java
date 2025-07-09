package grep.neogul_coder.global.auth.repository;

import grep.neogul_coder.global.auth.entity.TokenBlackList;
import org.springframework.data.repository.CrudRepository;

public interface AccessTokenBlackListRepository extends CrudRepository<TokenBlackList,String> {

}
