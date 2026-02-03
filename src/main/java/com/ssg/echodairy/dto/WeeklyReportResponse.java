package com.ssg.echodairy.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WeeklyReportResponse {

    private String startDate;
    private String endDate;

    private int recordDays;

    private TopEmotion topEmotion;

    private String summary;
    private String echo;

    @Getter
    @Builder
    public static class TopEmotion {
        private String emoji;
        private String label;
        private int count;
        private String description;
    }
}
