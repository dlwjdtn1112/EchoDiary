package com.ssg.echodairy.controller;
import com.ssg.echodairy.dto.AiRequest;
import com.ssg.echodairy.dto.AiResponse;
import com.ssg.echodairy.dto.WeeklyAiRequest;
import com.ssg.echodairy.dto.WeeklyAiResponse;
import com.ssg.echodairy.sercurity.CustomUserDetails;
import com.ssg.echodairy.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ai")
public class AIController {

    private final AiService aiService;

    /**
     * 🤖 AI 응답 생성
     * POST /ai/feedback
     */
    @PostMapping("/feedback")
    public AiResponse generateFeedback(@RequestBody AiRequest request) {

        Long userId = getLoginUserId();

        String aiContent =
                aiService.generateDailyFeedback(userId, request.getDate());

        return new AiResponse(aiContent);
    }

    /**
     * 🔐 로그인 사용자 ID 추출
     */
    private Long getLoginUserId() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails user =
                (CustomUserDetails) authentication.getPrincipal();

        return user.getUserId();
    }


    @PostMapping("/weekly")
    public WeeklyAiResponse generateWeeklyReport(
            @RequestBody WeeklyAiRequest request
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

        Long userId = userDetails.getClient().getUserId();

        return aiService.generateWeeklyReport(userId, request.getDate());
    }




}
