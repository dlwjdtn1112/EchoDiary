package com.ssg.echodairy.domain;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RefreshToken {

    private Long id;
    private Long userId;
    private String refreshToken;
    private LocalDateTime issuedAt;
    private LocalDateTime expiresAt;

}
