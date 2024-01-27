package com.example.solidconnection.config.token;

import com.example.solidconnection.custom.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

import static com.example.solidconnection.custom.exception.ErrorCode.*;

@Component
@RequiredArgsConstructor
public class TokenValidator {
    private final RedisTemplate<String, String> redisTemplate;

    @Value("${jwt.secret}")
    private String secretKey;

    public void validateAccessToken(String token) {
        validateTokenNotEmpty(token);
        validateAccessTokenNotExpired(token);
        validateRefreshToken(token);
        // TODO : validateNotLogOut 함수 생성 및 추가
    }

    private void validateRefreshToken(String token) {
        String email = getClaim(token).getSubject();
        if (redisTemplate.opsForValue().get(TokenType.REFRESH.getPrefix() + email) != null) {
            throw new CustomException(REFRESH_TOKEN_EXPIRED);
        }
    }

    private void validateAccessTokenNotExpired(String token) {
        Date expiration = getClaim(token).getExpiration();
        long now = new Date().getTime();
        if((expiration.getTime() - now) < 0){
            throw new CustomException(ACCESS_TOKEN_EXPIRED);
        }
    }

    private void validateTokenNotEmpty(String token) {
        if (!StringUtils.hasText(token)) {
            throw new CustomException(INVALID_TOKEN);
        }
    }

    private Claims getClaim(String token) {
        return Jwts.parser()
                .setSigningKey(this.secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}
