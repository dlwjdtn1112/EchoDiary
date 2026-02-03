package com.ssg.echodairy.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
    private String email;
    private String loginId;
    private String password;
    private String passwordConfirm;
    private String nickname;

}
