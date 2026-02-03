package com.ssg.echodairy.mapper;


import com.ssg.echodairy.dto.DiaryAiDto;
import com.ssg.echodairy.dto.TodayRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface AiFeedbackMapper {

    void insertFeedback(
            @Param("diaryId") Long diaryId,
            @Param("userId") Long userId,
            @Param("content") String content,
            @Param("promptVersion") String promptVersion
    );


    List<String> findFeedbackContents(
            @Param("userId") Long userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );


}
