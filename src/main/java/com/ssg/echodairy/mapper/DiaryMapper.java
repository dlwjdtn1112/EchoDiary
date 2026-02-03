package com.ssg.echodairy.mapper;

import com.ssg.echodairy.dto.DiaryAiDto;
import com.ssg.echodairy.dto.TodayRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;

@Mapper
public interface DiaryMapper {

//    /* 저장 */
//    void insertDiary(
//            @Param("userId") Long userId,
//            @Param("content") String content,
//            @Param("emotion") String emotion,
//            @Param("diaryDate") LocalDate diaryDate
//    );
//
//    /* 조회 */
//    TodayRequest findDiaryByDate(
//            @Param("userId") Long userId,
//            @Param("diaryDate") LocalDate diaryDate
//    );

    void insertDiary(
            @Param("userId") Long userId,
            @Param("req") TodayRequest req
    );

    TodayRequest findDiaryByDate(
            @Param("userId") Long userId,
            @Param("diaryDate") String diaryDate
    );

    void updateDiary(
            @Param("userId") Long userId,
            @Param("req") TodayRequest req
    );

    DiaryAiDto findDiaryForAi(
            @Param("userId") Long userId,
            @Param("diaryDate") String diaryDate
    );
}
