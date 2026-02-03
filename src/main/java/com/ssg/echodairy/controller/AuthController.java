package com.ssg.echodairy.controller;

import com.ssg.echodairy.domain.Client;
import com.ssg.echodairy.dto.SignupRequest;
import com.ssg.echodairy.mapper.ClientMapper;
import com.ssg.echodairy.sercurity.CustomUserDetails;
import com.ssg.echodairy.sercurity.JwtUtil;
import com.ssg.echodairy.service.AuthService;
import com.ssg.echodairy.service.EmailAuthService;
import com.ssg.echodairy.service.RefreshTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final ClientMapper clientMapper;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final EmailAuthService emailAuthService;

    /* =========================
       Root
    ========================= */
    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }

    /* =========================
       Login
    ========================= */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String loginId,
            @RequestParam String password,
            HttpServletResponse response
    ) {
        // 1️⃣ 사용자 조회
        Client client = clientMapper.findByLoginId(loginId);

        if (client == null) {
            return "redirect:/login?error=notfound";
        }

        // 2️⃣ 비밀번호 검증
        if (!passwordEncoder.matches(password, client.getPassword())) {
            return "redirect:/login?error=wrongpw";
        }

        // 3️⃣ Access Token 발급
        String accessToken = jwtUtil.generateToken(client);

        Cookie accessCookie = new Cookie("accessToken", accessToken);
        accessCookie.setHttpOnly(true);
        accessCookie.setPath("/");
        accessCookie.setMaxAge(60 * 60);
        response.addCookie(accessCookie);

        // 4️⃣ Refresh Token 발급
        String refreshToken = refreshTokenService.createAndSave(client.getUserId());

        Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setPath("/auth/refresh");
        refreshCookie.setMaxAge(60 * 60 * 24 * 14);
        response.addCookie(refreshCookie);

        // 5️⃣ SecurityContext 인증 등록
        CustomUserDetails userDetails = new CustomUserDetails(client);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 6️⃣ 🔥 role 기반 분기
        if ("ADMIN".equals(client.getRole())) {
            return "redirect:/admin/users";
        } else {
            return "redirect:/calendar";
        }
    }

    /* =========================
       Signup
    ========================= */
    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(SignupRequest request) {
        authService.signup(request);
        return "redirect:/login";
    }

    /* =========================
       Email Duplicate Check
    ========================= */
//    @GetMapping("/auth/email/check")
//    @ResponseBody
//    public Map<String, Integer> checkEmail(@RequestParam String email) {
//        int available = clientMapper.existsByEmail(email);
//        return Map.of("available", available);
//    }

    @GetMapping("/auth/email/check")
    @ResponseBody
    public Map<String, Boolean> checkEmail(@RequestParam String email) {
        boolean exists = clientMapper.existsByEmail(email) > 0;
        return Map.of("available", !exists);
    }


    /* =========================
       Send Auth Code
    ========================= */
    @PostMapping("/auth/email/send")
    @ResponseBody
    public Map<String, Boolean> sendCode(@RequestBody Map<String, String> body) {
        emailAuthService.sendCode(body.get("email"));
        return Map.of("success", true);
    }

    /* =========================
       Verify Auth Code
    ========================= */
    @PostMapping("/auth/email/verify")
    @ResponseBody
    public Map<String, Boolean> verifyCode(@RequestBody Map<String, String> body) {
        boolean result = emailAuthService.verify(
                body.get("email"),
                body.get("code")
        );
        return Map.of("success", result);
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {

        // 1️⃣ 현재 인증된 사용자 userId 추출
        Object principal =
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal();

        if (principal instanceof CustomUserDetails userDetails) {
            Long userId = userDetails.getClient().getUserId();

            // 🔥 user_id 기준 전체 refresh token 삭제
            System.out.println("userid : " +  userId);
            refreshTokenService.deleteByUserId(userId);
        }

        // 2️⃣ 쿠키 제거
        Cookie access = new Cookie("accessToken", null);
        access.setPath("/");
        access.setMaxAge(0);

        Cookie refresh = new Cookie("refreshToken", null);
        refresh.setPath("/");
        refresh.setMaxAge(0);

        response.addCookie(access);
        response.addCookie(refresh);

        // 3️⃣ SecurityContext 정리
        SecurityContextHolder.clearContext();

        return "redirect:/login";
    }


    @PostMapping("/auth/refresh")
    public String refresh(HttpServletRequest request,
                          HttpServletResponse response) {

        String refreshToken = Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals("refreshToken"))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);

        if (refreshToken == null ||
                !refreshTokenService.isValid(refreshToken)) {
            return "redirect:/login";
        }

        Long userId = refreshTokenService.getUserId(refreshToken);
        Client client = clientMapper.findByUserId(userId);

        String newAccessToken = jwtUtil.generateToken(client);

        Cookie accessCookie = new Cookie("accessToken", newAccessToken);
        accessCookie.setHttpOnly(true);
        accessCookie.setPath("/");
        accessCookie.setMaxAge(60 * 60);
        response.addCookie(accessCookie);

        return "redirect:/calendar";
    }

}
