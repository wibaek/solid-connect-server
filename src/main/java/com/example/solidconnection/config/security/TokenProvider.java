package com.example.solidconnection.config.security;

import com.example.solidconnection.custom.exception.CustomException;
import com.example.solidconnection.siteuser.repository.SiteUserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.example.solidconnection.custom.exception.ErrorCode.EMAIL_NOT_FOUND;
import static com.example.solidconnection.custom.exception.ErrorCode.REFRESH_TOKEN_EXPIRED;

@Component
@RequiredArgsConstructor
public class TokenProvider {
    private final RedisTemplate<String, String> redisTemplate;
    private final SiteUserRepository siteUserRepository;

    @Value("${jwt.secret}")
    private String secretKey;

    public String generateToken(String email, TokenType tokenType) {
        Claims claims = Jwts.claims().setSubject(email);


        var now = new Date();
        var expiredDate = new Date(now.getTime() + tokenType.getExpireTime());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS512, this.secretKey)
                .compact();
    }

    public String saveToken(String email, TokenType tokenType) {
        String token = generateToken(email, tokenType);

        redisTemplate.opsForValue().set(
                tokenType.getPrefix() + email,
                token,
                tokenType.getExpireTime(),
                TimeUnit.MILLISECONDS
        );
        return token;
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = (UserDetails) siteUserRepository.findByEmail(this.getUserEmail(token))
                .orElseThrow(() -> new CustomException(EMAIL_NOT_FOUND));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserEmail(String token) {
        return this.parseClaims(token).getSubject();
    }

    public boolean validateAccessToken(String token) {
        if (!StringUtils.hasText(token)) {
            return false;
        }
        validateToken(token, TokenType.REFRESH);
        return isExpired(token);
    }

    private void validateToken(String token, TokenType tokenType) {
        if (token.equals(redisTemplate.opsForValue().get(tokenType.getPrefix() + getUserEmail(token)))) {
            throw new CustomException(REFRESH_TOKEN_EXPIRED);
        }
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public boolean isExpired(String token) {
        Date expiration = Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody().getExpiration();
        long now = new Date().getTime();
        return (expiration.getTime() - now) < 0;
    }
}