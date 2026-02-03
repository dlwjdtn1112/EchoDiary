package com.ssg.echodairy.controller;


import com.ssg.echodairy.sercurity.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WeeklyReportController {

    @GetMapping("/weekly-report")
    public String weeklyReportPage(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getPrincipal() instanceof CustomUserDetails userDetails) {
            model.addAttribute(
                    "nickname",
                    userDetails.getClient().getNickname()
            );
        }

        return "weeklyreport";
    }
}
