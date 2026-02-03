package com.ssg.echodairy.dto;

import lombok.Data;

@Data
public class TodayRequest {

    private String diaryDate;   // yyyy-MM-dd
    private String emotion;     // HAPPY, NEUTRAL, SAD, ANGRY
    private String content;

}
