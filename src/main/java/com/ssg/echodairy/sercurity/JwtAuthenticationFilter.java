package com.ssg.echodairy.sercurity;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

//    @Override
//    protected void doFilterInternal(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            FilterChain filterChain
//    ) throws ServletException, IOException {
//
//        String path = request.getRequestURI();
//
//        System.out.println("🔥 [JWT FILTER] 진입: " + request.getMethod() + " " + path);
//
//        // ✅ 인증 제외 경로
//        if (
//                path.equals("/") ||
//                        path.equals("/login") ||
//                        path.equals("/signup") ||
//
//                        path.startsWith("/css") ||
//                        path.startsWith("/js") ||
//                        path.startsWith("/images")
//        ) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//
//        // ✅ AccessToken 쿠키 추출
//        String accessToken = extractAccessToken(request);
//
//        if (accessToken == null) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        try {
//            // ✅ JWT 파싱
//            Claims claims = jwtUtil.parse(accessToken);
//            String loginId = claims.getSubject();
//
//            // ✅ UserDetails 재조회
//            UserDetails userDetails =
//                    userDetailsService.loadUserByUsername(loginId);
//
//            // ✅ Authentication 생성
//            UsernamePasswordAuthenticationToken authentication =
//                    new UsernamePasswordAuthenticationToken(
//                            userDetails,
//                            null,
//                            userDetails.getAuthorities()
//                    );
//
//            SecurityContextHolder.getContext()
//                    .setAuthentication(authentication);
//
//        } catch (Exception e) {
//            SecurityContextHolder.clearContext();
//        }
//
//        filterChain.doFilter(request, response);
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // 1. 필터 진입 로그
        System.out.println("🔥 [STEP 1] 필터 진입 경로: " + path);

        // 제외 경로 설정 (정적 리소스 및 공통 페이지)
        if (path.equals("/") || path.equals("/login") || path.equals("/signup") ||
                path.startsWith("/css") || path.startsWith("/js") || path.startsWith("/images") ||
                path.equals("/favicon.ico")) {
            System.out.println("⏩ [SKIP] 제외 경로이므로 필터 통과");
            filterChain.doFilter(request, response);
            return;
        }

        // 2. 쿠키 추출 및 확인
        String accessToken = null;
        if (request.getCookies() != null) {
            accessToken = Arrays.stream(request.getCookies())
                    .filter(c -> "accessToken".equals(c.getName()))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);
        }

        if (accessToken == null) {
            System.out.println("🔎 [STEP 2] 결과: 쿠키에 accessToken이 없습니다.");
        } else {
            System.out.println("🔎 [STEP 2] 결과: 쿠키에서 토큰 추출 성공");

            try {
                // 3. 토큰 파싱 및 인증
                Claims claims = jwtUtil.parse(accessToken);
                String loginId = claims.getSubject();
                System.out.println("👤 [STEP 3] 토큰 사용자 아이디: " + loginId);

                UserDetails userDetails = userDetailsService.loadUserByUsername(loginId);

                // 인증 객체 생성
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // SecurityContext에 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // ✅ 중요: 실제 부여된 권한(Role) 로그 출력
                System.out.println("📜 [STEP 3] 최종 부여된 권한: " + authentication.getAuthorities());
                System.out.println("✅ [STEP 3] SecurityContext에 인증 정보 저장 완료");

            } catch (Exception e) {
                System.out.println("❌ [ERROR] 토큰 검증 중 오류 발생: " + e.getMessage());
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractAccessToken(HttpServletRequest request) {
        if (request.getCookies() == null) return null;

        return Arrays.stream(request.getCookies())
                .filter(c -> "accessToken".equals(c.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }
}
