package com.ssg.echodairy.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TodayResponse {

    private String nickname;
    private TodayRequest diary;

}
