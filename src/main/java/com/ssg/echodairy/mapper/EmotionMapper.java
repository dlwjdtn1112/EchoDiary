package com.ssg.echodairy.mapper;


import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmotionMapper {

    public static String emoji(String emotion) {
        return switch (emotion) {
            case "HAPPY" -> "😊";
            case "NEUTRAL" -> "😐";
            case "SAD" -> "😨";
            case "ANGRY" -> "😡";
            default -> "😐";
        };
    }

    public static String label(String emotion) {
        return switch (emotion) {
            case "HAPPY" -> "좋음";
            case "NEUTRAL" -> "보통";
            case "SAD" -> "우울";
            case "ANGRY" -> "화남";
            default -> "보통";
        };
    }

    public static String description(String emotion) {
        return switch (emotion) {
            case "HAPPY" -> "이번 주에 가장 긍정적인 감정이 많이 나타났어요.";
            case "NEUTRAL" -> "이번 주는 비교적 평온한 감정 흐름이었어요.";
            case "SAD" -> "걱정이나 우울한 감정이 자주 느껴졌던 한 주였어요.";
            case "ANGRY" -> "스트레스나 분노 감정이 자주 나타났어요.";
            default -> "이번 주의 대표 감정이에요.";
        };
    }



}
