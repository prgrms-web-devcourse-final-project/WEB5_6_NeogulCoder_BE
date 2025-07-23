package grep.neogulcoder.global.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import grep.neogulcoder.global.auth.entity.RefreshToken;
import java.time.Duration;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper ObjectMapper;

    public RefreshToken saveWithAtId(String atId){
        RefreshToken refreshToken = new RefreshToken(atId);
        redisTemplate.opsForValue().set(atId, refreshToken, Duration.ofSeconds(refreshToken.getTtl()));
        return refreshToken;
    }

    public void deleteByAccessTokenId(String atId){
        redisTemplate.delete(atId);
    }

    public RefreshToken renewingToken(String id, String newTokenId){
        RefreshToken refreshToken = findByAccessTokenId(id);

        if(refreshToken == null) return null;

        RefreshToken gracePeriodToken = new RefreshToken(id);
        gracePeriodToken.setToken(refreshToken.getToken());

        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setAtId(newTokenId);

        redisTemplate.opsForValue().set(newTokenId, refreshToken, Duration.ofSeconds(refreshToken.getTtl()));
        redisTemplate.opsForValue().set(id, gracePeriodToken, Duration.ofSeconds(10));
        return refreshToken;
    }

    public RefreshToken findByAccessTokenId(String atId){
        return ObjectMapper.convertValue(redisTemplate.opsForValue().get(atId), RefreshToken.class);
    }

}
