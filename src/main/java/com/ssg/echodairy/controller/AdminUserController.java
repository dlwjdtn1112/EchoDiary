package com.ssg.echodairy.controller;


import com.ssg.echodairy.dto.AdminUserDto;
import com.ssg.echodairy.mapper.AdminUserMapper;
import com.ssg.echodairy.sercurity.CustomUserDetails;
import com.ssg.echodairy.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminUserController {


    private final AdminUserService adminUserService;
    private final AdminUserMapper adminUserMapper;

//    @GetMapping("/users")
//    public String userList(Model model) {
//        model.addAttribute("users", adminUserService.getAllUsers());
//        return "admin-user-list";
//    }

    @GetMapping("/users")
    public String adminUsers(Model model) {

        // 1️⃣ 로그인한 관리자 정보 가져오기
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

        // 2️⃣ 관리자 닉네임 전달
        model.addAttribute("nickname", userDetails.getClient().getNickname());

        // 3️⃣ 회원 목록
        List<AdminUserDto> users = adminUserMapper.findAllUsers();
        model.addAttribute("users", users);

        return "admin-user-list";
    }

}
