package com.ssg.echodairy.controller;


import com.ssg.echodairy.dto.TodayRequest;
import com.ssg.echodairy.dto.TodayResponse;
import com.ssg.echodairy.sercurity.CustomUserDetails;
import com.ssg.echodairy.service.TodayService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/today")
@RequiredArgsConstructor
public class TodayController {

    private final TodayService todayService;



//    @GetMapping("/page")
//    public String todayPage(
//            @RequestParam String date,
//            Model model
//    ) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//        if (auth != null && auth.getPrincipal() instanceof CustomUserDetails userDetails) {
//            model.addAttribute("nickname", userDetails.getClient().getNickname());
//        }
//
//        model.addAttribute("date", date);
//        return "todaysnote";
//    }
//
//    /* =========================
//       오늘의 기록 저장
//       POST /api/today
//    ========================= */
//    @PostMapping
//    public void saveTodayDiary(@RequestBody TodayRequest request) {
//
//        CustomUserDetails userDetails = getUserDetails();
//        Long userId = userDetails.getClient().getUserId();
//
//        todayService.saveDiary(userId, request);
//    }
//
//    /* =========================
//       오늘의 기록 + 닉네임 조회
//       GET /api/today?date=YYYY-MM-DD
//    ========================= */
//    @GetMapping
//    public TodayResponse getTodayDiary(@RequestParam String date) {
//
//        CustomUserDetails userDetails = getUserDetails();
//        Long userId = userDetails.getClient().getUserId();
//        String nickname = userDetails.getClient().getNickname();
//
//        TodayRequest diary = todayService.getDiaryByDate(userId, date);
//
//        return TodayResponse.builder()
//                .nickname(nickname)
//                .diary(diary)
//                .build();
//    }



    @GetMapping("/todaysnote")
    public String todayNote(@RequestParam String date, Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.getPrincipal() instanceof CustomUserDetails userDetails) {
            model.addAttribute("nickname", userDetails.getClient().getNickname());
        }

        model.addAttribute("date", date);
        return "todaysnote";
    }


    /* =========================
       오늘 기록 저장 (API)
       POST /today
    ========================= */
    @PostMapping
    @ResponseBody
    public void saveTodayDiary(@RequestBody TodayRequest request) {

        CustomUserDetails userDetails = getUserDetails();
        todayService.saveDiary(
                userDetails.getClient().getUserId(),
                request
        );
    }

    /* =========================
       오늘 기록 조회 (API)
       GET /today
    ========================= */
    @GetMapping
    @ResponseBody
    public TodayResponse getTodayDiary(@RequestParam String date) {

        CustomUserDetails userDetails = getUserDetails();

        return TodayResponse.builder()
                .nickname(userDetails.getClient().getNickname())
                .diary(
                        todayService.getDiaryByDate(
                                userDetails.getClient().getUserId(),
                                date
                        )
                )
                .build();
    }

    /* =========================
       공통 인증 처리
    ========================= */
    private CustomUserDetails getUserDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !(auth.getPrincipal() instanceof CustomUserDetails userDetails)) {
            throw new RuntimeException("인증되지 않은 사용자");
        }

        return userDetails;
    }


}
