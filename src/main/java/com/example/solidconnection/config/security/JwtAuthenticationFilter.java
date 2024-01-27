package com.example.solidconnection.config.security;

import com.example.solidconnection.config.token.TokenService;
import com.example.solidconnection.config.token.TokenValidator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    private final TokenService tokenService;
    private final TokenValidator tokenValidator;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        AntPathMatcher pathMatcher = new AntPathMatcher();
        for (String endpoint : getPermitAllEndpoints()) {
            if (pathMatcher.match(endpoint, request.getRequestURI())) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        try {
            String token = this.resolveAccessTokenFromRequest(request); // 웹 요청에서 토큰 추출
            tokenValidator.validateAccessToken(token); // 유효한 액세스 토큰인지 검증
            Authentication auth = this.tokenService.getAuthentication(token); // 토큰에서 인증 정보 가져옴
            SecurityContextHolder.getContext().setAuthentication(auth);// 인증 정보를 보안 컨텍스트에 설정
            filterChain.doFilter(request, response); // 다음 필터로 요청과 응답 전달
        } catch (AuthenticationException e) {
            jwtAuthenticationEntryPoint.commence(request, response, e);
        }
    }

    private String resolveAccessTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_HEADER);

        if (!ObjectUtils.isEmpty(token) && token.startsWith(TOKEN_PREFIX)) { // 토큰이 비어 있지 않고, Bearer로 시작한다면
            return token.substring(TOKEN_PREFIX.length()); // Bearer 제외한 실제 토큰 부분 반환
        }
        return null;
    }

    private HashSet<String> getPermitAllEndpoints() {
        var permitAllEndpoints = new HashSet<String>();

        // 서버 정상 작동 확인
        permitAllEndpoints.add("/");
        permitAllEndpoints.add("/index.html");
        permitAllEndpoints.add("/favicon.ico");

        // 이미지 업로드
        permitAllEndpoints.add("/img-upload/profile");
        permitAllEndpoints.add("/img-upload/gpa");
        permitAllEndpoints.add("/img-upload/language");

        // 토큰이 필요하지 않은 인증
        permitAllEndpoints.add("/auth/kakao");
        permitAllEndpoints.add("/auth/sign-up");

        return permitAllEndpoints;
    }
}