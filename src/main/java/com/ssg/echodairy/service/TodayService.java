package com.ssg.echodairy.service;


import com.ssg.echodairy.dto.TodayRequest;
import com.ssg.echodairy.mapper.DiaryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TodayService {

    private final DiaryMapper diaryMapper;

    /* 오늘 기록 저장 */
//    public void saveDiary(Long userId, TodayRequest req) {
//        diaryMapper.insertDiary(
//                userId,
//                req.getContent(),
//                req.getEmotion(),
//                LocalDate.parse(req.getDiaryDate())
//        );
//    }
    public void saveDiary(Long userId, TodayRequest request) {

        TodayRequest existing =
                diaryMapper.findDiaryByDate(userId, request.getDiaryDate());

        if (existing == null) {
            diaryMapper.insertDiary(userId, request);
        } else {
            diaryMapper.updateDiary(userId, request);
        }
    }


    /* 날짜별 기록 조회 */
    public TodayRequest getDiaryByDate(Long userId, String date) {

        return diaryMapper.findDiaryByDate(
                userId,
                String.valueOf(LocalDate.parse(date))
        );
    }



}
