package com.ssg.echodairy.domain;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Client {

    private Long userId;
    private String loginId;
    private String password;
    private String nickname;
    private String role;
    private LocalDateTime createdAt;

}
