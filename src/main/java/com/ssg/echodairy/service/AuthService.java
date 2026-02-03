package com.ssg.echodairy.service;


import com.ssg.echodairy.domain.Client;
import com.ssg.echodairy.dto.SignupRequest;
import com.ssg.echodairy.mapper.ClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ClientMapper clientMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailAuthService emailAuthService;

    @Transactional
    public void signup(SignupRequest req) {

        // 0️⃣ 이메일 인증 여부 (🔥 핵심 추가)
        if (!emailAuthService.isVerified(req.getEmail())) {
            throw new IllegalStateException("이메일 인증이 필요합니다.");
        }

        // 1. 비밀번호 확인
        if (!req.getPassword().equals(req.getPasswordConfirm())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 2. 아이디 중복
        if (clientMapper.existsByLoginId(req.getLoginId())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        // 3. 닉네임 중복
        if (clientMapper.existsByNickname(req.getNickname())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        // 4️⃣ 이메일 중복 (🔥 추가)
        if (clientMapper.existsByEmail(req.getEmail()) > 0) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        // 4. 비밀번호 암호화
        String encodedPassword =
                passwordEncoder.encode(req.getPassword());

        // 5. 저장
        clientMapper.insertClient(
                req.getLoginId(),
                encodedPassword,
                req.getNickname(),
                req.getEmail()
        );
    }




}
