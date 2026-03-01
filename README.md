# EchoDiary 📝  
**기록은 데이터가 되고, 데이터는 통찰이 된다.**

EchoDiary는 사용자의 계획(Intent)과 감정·행동 기록(Diary)을 분석하여  
AI가 개인화된 피드백을 제공하는 RAG 기반 다이어리 웹 서비스입니다.

---

## 🔎 프로젝트 소개

EchoDiary는 단순 일기 작성 서비스가 아닙니다.

사용자의 과거 기록을 벡터화하여 의미 유사도를 기반으로 검색하고,  
그 결과를 바탕으로 AI가 맥락을 이해한 응답을 생성하는 구조를 설계했습니다.

- 기록 저장
- 임베딩 벡터 생성
- 벡터 기반 검색
- 맥락 반영 응답 생성

AI를 단순 호출하는 구조가 아닌,  
**RAG(Retrieval-Augmented Generation) 아키텍처를 직접 설계 및 구현**했습니다.

---

## 🛠 기술 스택

### Backend
- Java 17
- Spring Boot
- Spring Security
- MyBatis

### Frontend
- HTML / CSS / JavaScript

### Database
- MySQL (기본 데이터 저장)
- PostgreSQL + pgvector (벡터 검색)

### AI
- Spring AI
- OpenAI API
- RAG 기반 벡터 검색

### Auth
- JWT (Access / Refresh Token)

### Infra
- AWS EC2 (Ubuntu)
- Docker (PostgreSQL 컨테이너 운영)
- Embedded Tomcat

### Tool
- IntelliJ IDEA
- Git / GitHub

---

## ⚙ 주요 기능

- 회원가입 / 로그인 (JWT 기반 인증)
- 하루 계획(Intent) 작성
- 감정·행동 기록(Diary) 작성
- 기록 임베딩 벡터 저장 (OpenAI Embedding)
- 의미 유사도 기반 기록 검색 (pgvector)
- AI 요약 및 맥락 반영 피드백 생성
- 마이페이지 기록 조회

---

##📊 Retrieval 성능 검증

Resona 1.0은 단순 Top-K 기반 벡터 검색이 아닌,
Threshold 기반 필터링 전략을 적용하여 Retrieval 품질을 정량적으로 검증하고 최적화했습니다.

🔬 실험 방식

4개 테스트 셋 구성

Threshold(0.25 ~ 0.45) 구간별 Precision / Recall / F1-score 측정

평균값 기반 최적 임계값 도출




| Threshold | F1-score  | Recall | Precision |
| --------- | --------- | ------ | --------- |
| 0.25      | 0.687     | 1.000  | 0.538     |
| 0.30      | **0.792** | 0.875  | 0.750     |
| 0.35      | 0.575     | 0.500  | 0.750     |
| 0.40      | 0.417     | 0.292  | 0.750     |
| 0.45      | 0.000     | 0.000  | 0.000     |









## 🧠 Resona 1.0 (Echo AI Engine)

EchoDiary에는 자체 설계한 AI 엔진 **Resona 1.0**이 적용되어 있습니다.

Resona 1.0은 단순 LLM 호출이 아닌,  
벡터 검색을 활용한 RAG 구조 기반 개인화 AI 시스템입니다.

### 🔍 동작 흐름

1. 사용자의 질문 또는 기록 입력
2. OpenAI Embedding 모델로 벡터 생성
3. PostgreSQL(pgvector)에서 코사인 유사도 기반 검색
4. Top-K 기록 추출 및 Threshold 필터링
5. LLM에 context 전달 후 맞춤 응답 생성

### 🎯 설계 포인트

- `1 - (embedding <=> vector)` 기반 cosine similarity 계산
- Top-K + Threshold 전략 적용
- 긴 문서의 의미 희석 문제 분석
- 유사도 로그 출력 및 튜닝
- 벡터 검색과 응답 생성 로직 분리

---

## 🚀 Resona 2.0 (2026년 4월 출시 예정)

Resona 2.0은 기존 RAG 구조를 확장한 차세대 AI 엔진입니다.

단순 의미 검색을 넘어  
사용자의 감정 흐름과 패턴을 분석하는 방향으로 고도화 예정입니다.

### 🔮 주요 예정 기능

- 감정 시계열 분석
- Recency Weight 기반 가중치 반영
- Hybrid Search (Vector + Keyword)
- 주간/월간 감정 리포트 자동 생성
- 개인별 감정 성향 프로파일링

Resona 2.0은  
“질문에 답하는 AI”에서  
“감정 흐름을 이해하는 AI”로 발전하는 것을 목표로 합니다.
