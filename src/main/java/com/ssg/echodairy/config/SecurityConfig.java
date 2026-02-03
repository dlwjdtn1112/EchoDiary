package com.ssg.echodairy.config;


import com.ssg.echodairy.sercurity.CustomUserDetailsService;
import com.ssg.echodairy.sercurity.JwtAuthenticationFilter;
import com.ssg.echodairy.sercurity.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http
//                // ✅ CSRF 비활성화 (JWT 사용)
//                .csrf(csrf -> csrf.disable())
//
//                // ✅ 세션 사용 안 함 (JWT)
//                .sessionManagement(session ->
//                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//
//                // ✅ 폼 로그인 / httpBasic 비활성화
//                .formLogin(form -> form.disable())
//                .httpBasic(basic -> basic.disable())
//
//                // ✅ 접근 권한 설정
//                .authorizeHttpRequests(auth -> auth
//                        // 로그인 & 정적 리소스 허용
//                        .requestMatchers(
//                                "/login",
//                                "/css/**",
//                                "/js/**",
//                                "/images/**",
//                                "/",
//                                "/signup"
//
//                        ).permitAll()
//                        .requestMatchers("/calendar").hasRole("USER")
//
//                        // 관리자 전용
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
//
//                        // 그 외는 인증 필요
//                        .anyRequest().authenticated()
//                )
//
//                // ✅ JWT 필터 등록
//                .addFilterBefore(
//                        new JwtAuthenticationFilter(jwtUtil, customUserDetailsService),
//                        UsernamePasswordAuthenticationFilter.class
//                );
//
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // ✅ CSRF 방어 (쿠키 기반)
                // 🔥 CSRF 완전 비활성화 (핵심)
                .csrf(csrf -> csrf.disable())

                // ✅ Stateless
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .securityContext(securityContext ->
                        securityContext.requireExplicitSave(false)
                )

                // ✅ 기본 인증 방식 비활성화
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .logout(logout -> logout.disable())
                // ✅ 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/login",
                                "/signup",
                                "/",
                                "/auth/refresh",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/auth/email/**"

                        ).permitAll()
                        .requestMatchers("/calendar").hasRole("USER")

                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )

                // ✅ JWT 필터
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtUtil,customUserDetailsService),
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http
//                .csrf(csrf -> csrf.disable())
//
//                .sessionManagement(session ->
//                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//
//                .securityContext(securityContext ->
//                        securityContext.requireExplicitSave(false)
//                )
//
//                .formLogin(form -> form.disable())
//                .httpBasic(basic -> basic.disable())
//
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(
//                                "/login",
//                                "/signup",
//                                "/",
//                                "/logout",
//                                "/auth/refresh",
//                                "/css/**",
//                                "/js/**",
//                                "/images/**"
//                        ).permitAll()
//
//                        .requestMatchers(
//                                "/calendar",
//                                "/todaysnote",
//                                "/todaysnote/**"
//                        ).authenticated()
//
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                        .anyRequest().authenticated()
//                )
//
//                .exceptionHandling(exception -> exception
//                        .authenticationEntryPoint((request, response, authException) -> {
//                            response.sendRedirect("/login");
//                        })
//                )
//
//                // 🔴 Spring Security 기본 로그아웃 끔
//                .logout(logout -> logout.disable())
//
//                .addFilterBefore(
//                        new JwtAuthenticationFilter(jwtUtil, customUserDetailsService),
//                        UsernamePasswordAuthenticationFilter.class
//                );
//
//        return http.build();
//    }









//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())
//                .sessionManagement(session ->
//                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                // ✅ 이 부분이 반드시 명시되어 있어야 리다이렉트 후에도 컨텍스트를 유지하거나 다시 생성함
//                .securityContext(securityContext ->
//                        securityContext.requireExplicitSave(false)
//                )
//                .formLogin(form -> form.disable())
//                .httpBasic(basic -> basic.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(
//                                "/login", "/signup", "/", "/auth/refresh",
//                                "/css/**", "/js/**", "/images/**", "/favicon.ico"
//                        ).permitAll()
//                        // ✅ /calendar가 permitAll에 없으므로 필터에서 반드시 인증을 해줘야 함
//                        .requestMatchers("/calendar", "/todaysnote").authenticated()
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                        .anyRequest().authenticated()
//                )
//                // ✅ 필터 순서 확인
//                .addFilterBefore(
//                        new JwtAuthenticationFilter(jwtUtil, customUserDetailsService),
//                        UsernamePasswordAuthenticationFilter.class
//                );
//
//        return http.build();
//    }







    // ✅ PasswordEncoder Bean (에러 원인 해결 포인트)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
