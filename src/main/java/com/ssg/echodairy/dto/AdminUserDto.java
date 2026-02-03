package com.ssg.echodairy.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AdminUserDto {
    private Long userId;          // user.id
    private String loginId;         // user.login
    private String nickname;      // client.nickname
    private String role;          // user.role
    private LocalDateTime createdAt;
}
