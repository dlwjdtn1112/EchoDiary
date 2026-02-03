package com.ssg.echodairy.service;

import com.ssg.echodairy.dto.DiaryAiDto;
import com.ssg.echodairy.dto.WeeklyAiResponse;
import com.ssg.echodairy.mapper.AiFeedbackMapper;
import com.ssg.echodairy.mapper.DiaryMapper;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AiService {

    private final DiaryMapper diaryMapper;
    private final AiFeedbackMapper aiFeedbackMapper;

    private final ChatClient chatClient;

    /**
     * 🤖 하루 기록 기반 AI 피드백 생성
     */
    @Transactional
    public String generateDailyFeedback(Long userId, String diaryDate) {

        // 1. AI 전용 diary 조회
        DiaryAiDto diary =
                diaryMapper.findDiaryForAi(userId, diaryDate);

        if (diary == null) {
            throw new IllegalStateException("해당 날짜의 기록이 없습니다.");
        }

        // 2. 프롬프트 구성
        String prompt = """
        아래는 사용자의 하루 기록입니다.

        [감정]
        %s

        [내용]
        %s

        요구사항:
        1. 첫 문장은 공감
        2. 두 번째 문장은 하루 요약
        3. 마지막은 내일을 위한 한 문장 조언
        4. 총 3~5문장
        """.formatted(diary.getEmotion(), diary.getContent());

        // 3. AI 호출
        String aiContent =
                chatClient.prompt(prompt)
                        .call()
                        .content();

        // 4. DB 저장
        aiFeedbackMapper.insertFeedback(
                diary.getDiaryId(),
                diary.getUserId(),
                aiContent,
                "v1"
        );

        return aiContent;
    }

    public WeeklyAiResponse generateWeeklyReport(Long userId, String dateStr) {

        // 1️⃣ 날짜 파싱
        LocalDate selected = LocalDate.parse(dateStr);

        // 2️⃣ 주간 계산 (일요일 ~ 토요일 기준)
//        LocalDate startDate = date.with(DayOfWeek.SUNDAY);
//        LocalDate endDate = date.with(DayOfWeek.SATURDAY);
        // 월요일 시작 / 일요일 종료 (ISO 표준)
        LocalDate startDate = selected.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endDate   = selected.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        // 3️⃣ 해당 주간 AI 피드백 조회
        List<String> feedbackList =
                aiFeedbackMapper.findFeedbackContents(
                        userId,
                        startDate.atStartOfDay(),
                        endDate.plusDays(1).atStartOfDay()
                );

        if (feedbackList.isEmpty()) {
            return new WeeklyAiResponse(
                    startDate.toString(),
                    endDate.toString(),
                    "이번 주에는 AI 피드백이 충분하지 않았어요.",
                    "다음 주에는 하루 한 줄이라도 기록해보는 건 어떨까요?"
            );
        }

        // 4️⃣ 프롬프트 구성
        String prompt = """
        아래는 사용자가 일주일 동안 받은 AI 피드백 목록입니다.

        %s

        이 내용을 바탕으로 다음 형식으로 답변하세요.

        1. 이번 주 전체 요약 (3~4문장)
        2. 감정 흐름 분석
        3. 다음 주를 위한 조언과 위로 (따뜻한 톤)
        """.formatted(String.join("\n- ", feedbackList));

        // 5️⃣ GPT 호출
        String aiResult = chatClient
                .prompt(prompt)
                .call()
                .content();

        return new WeeklyAiResponse(
                startDate.toString(),
                endDate.toString(),
                "이번 주를 돌아본 요약입니다.",
                aiResult
        );
    }











}
