package com.ssg.echodairy.service;


import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class EmailAuthService {


    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    // email -> code
    private final Map<String, String> authStore = new ConcurrentHashMap<>();

    // 인증 완료된 이메일
    private final Set<String> verifiedEmails = ConcurrentHashMap.newKeySet();

    public void sendCode(String email) {
        String code = String.valueOf((int)(Math.random() * 900000) + 100000);
        authStore.put(email, code);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(email);
            helper.setSubject("[EchoDiary] 이메일 인증번호");

            String html = """
                <div style="font-family:Arial; padding:20px">
                    <h2>이메일 인증번호</h2>
                    <p>아래 인증번호를 입력해주세요.</p>
                    <div style="font-size:28px; font-weight:bold;">%s</div>
                    <p style="color:#888;">5분 이내 입력</p>
                </div>
            """.formatted(code);

            helper.setText(html, true);
            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("메일 전송 실패", e);
        }
    }

    public boolean verify(String email, String inputCode) {
        String savedCode = authStore.get(email);

        if (savedCode != null && savedCode.equals(inputCode)) {
            authStore.remove(email);
            verifiedEmails.add(email);
            return true;
        }
        return false;
    }

    public boolean isVerified(String email) {
        return verifiedEmails.contains(email);
    }

    public void clearVerified(String email) {
        verifiedEmails.remove(email);
    }
}
