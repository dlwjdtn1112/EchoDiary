EchoDiary 📝

기록은 데이터가 되고, 데이터는 통찰이 된다.

AI가 사용자의 계획과 감정 기록을 분석하여
개인화된 피드백을 제공하는 RAG 기반 다이어리 웹 서비스입니다.

🔎 프로젝트 소개

EchoDiary는 하루의 계획(Intent)과 감정·행동 기록(Diary)을 작성하면
AI가 이를 분석하고 맥락을 이해하여 맞춤형 피드백을 제공하는 서비스입니다.

단순 요약이 아닌,
과거 기록을 기반으로 의미 유사도를 분석하는 벡터 검색 기반 AI 구조를 설계했습니다.

🛠 기술 스택
Backend

Java 17

Spring Boot

Spring Security

MyBatis

Frontend

HTML

CSS

JavaScript

Database

MySQL (기본 데이터 저장)

PostgreSQL + pgvector (벡터 검색)

AI

Spring AI

OpenAI API

RAG (Retrieval-Augmented Generation)

Auth

JWT (Access / Refresh Token)

Infra

AWS EC2 (Ubuntu)

Embedded Tomcat

Docker (PostgreSQL 컨테이너 운영)

Tool

IntelliJ IDEA

Git / GitHub

⚙ 주요 기능

회원가입 / 로그인 (JWT 기반 인증)

하루 계획(Intent) 작성

감정·행동 기록(Diary) 작성

기록 임베딩 벡터 저장

의미 유사도 기반 기록 검색

AI 요약 및 맥락 반영 피드백 생성

마이페이지 기록 조회

🧠 Resona 1.0 (Echo AI Engine)

EchoDiary에는 자체 설계한 AI 피드백 엔진 Resona 1.0이 적용되어 있습니다.

Resona는 단순 GPT 호출이 아닌,
사용자의 과거 기록을 벡터화하여 맥락을 반영하는 RAG 기반 AI 구조입니다.

🔍 동작 구조

사용자의 질문 또는 기록 입력

OpenAI Embedding 모델을 통해 벡터 생성

PostgreSQL(pgvector)에서 코사인 유사도 기반 검색

Top-K 기록을 context로 구성

GPT에 전달하여 맞춤형 응답 생성

🎯 핵심 설계 포인트

코사인 유사도 기반 의미 검색

Top-K + Threshold 전략 적용

긴 기록에서 의미 희석 문제 분석 및 개선

벡터 검색과 GPT 응답 분리 설계

서비스 레벨에서 유사도 로그 출력 및 튜닝

🚀 Resona 2.0 (2026년 4월 출시 예정)

Resona 2.0은 RAG 구조를 확장한 차세대 AI 엔진입니다.

단순 의미 검색을 넘어
사용자의 감정 흐름을 분석하고 패턴을 추적하는 방향으로 고도화 예정입니다.

🔮 주요 예정 기능

감정 시계열 분석

Recency Weight 기반 가중치 반영

Hybrid Search (Vector + Keyword)

주간/월간 감정 리포트 자동 생성

개인별 감정 성향 프로파일링

Resona 2.0은
“질문에 답하는 AI”가 아닌
“사용자의 감정 흐름을 이해하는 AI”를 목표로 합니다.
