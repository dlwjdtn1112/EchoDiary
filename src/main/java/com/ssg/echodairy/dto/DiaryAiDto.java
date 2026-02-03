package com.ssg.echodairy.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DiaryAiDto {

    /** diary 테이블 PK */
    private Long diaryId;

    /** client(user) PK */
    private Long userId;

    /** 일기 내용 */
    private String content;

    /** 감정 (HAPPY, SAD ...) */
    private String emotion;


}
