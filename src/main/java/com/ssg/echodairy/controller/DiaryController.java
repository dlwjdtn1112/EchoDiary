package com.ssg.echodairy.controller;


import com.ssg.echodairy.sercurity.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DiaryController {

//    @GetMapping("/calendar")
//    public String calendar() {
//        return "calendar";
//    }


//    @GetMapping("/calendar")
//    public String calendar(Model model) {
//        addNickname(model);
//        return "calendar";
//    }

    @GetMapping("/calendar")
    public String calendar(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // 🔴 로그인 안 된 상태면 무조건 로그인 페이지로
        if (auth == null ||
                !auth.isAuthenticated() ||
                auth.getPrincipal().equals("anonymousUser")) {

            return "redirect:/login";
        }

        addNickname(model);
        return "calendar";
    }



    /* =========================
       오늘의 기록 페이지
       GET /todaysnote?date=YYYY-MM-DD
    ========================= */
    @GetMapping("/todaysnote")
    public String todaysNote(
            @RequestParam String date,
            Model model
    ) {
        addNickname(model);
        model.addAttribute("date", date);
        return "todaysnote";
    }

    /* =========================
       공통: 닉네임 주입
    ========================= */
    private void addNickname(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.getPrincipal() instanceof CustomUserDetails userDetails) {
            model.addAttribute(
                    "nickname",
                    userDetails.getClient().getNickname()
            );
        }
    }





}
