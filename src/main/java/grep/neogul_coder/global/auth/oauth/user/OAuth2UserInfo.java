package grep.neogul_coder.global.auth.oauth.user;

import java.util.Map;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface OAuth2UserInfo {

    String getProviderId();
    String getProvider();
    String getName();

    static OAuth2UserInfo createUserInfo(String path, OAuth2User user){
        Map<String, OAuth2UserInfo> map = Map.of(
            "/login/oauth2/code/google", new GoogleOAuth2UserInfo(user.getAttributes())
        );

        return map.get(path);
    }

}
