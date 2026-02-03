package com.ssg.echodairy.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WeeklyAiResponse {

    private String startDate;
    private String endDate;

    private String summary;   // 주간 요약
    private String echo;      // 다음 주 방향 + 위로

}
