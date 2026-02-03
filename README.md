# EchoDiary 📝
AI가 계획과 감정 기록을 분석하는 다이어리 웹 서비스

## 🔎 프로젝트 소개
EchoDiary는 사용자가 하루의 계획과 감정·행동 기록을 작성하면  
AI가 이를 요약·분석하여 개인화된 피드백을 제공하는 AI 기반 다이어리 웹 서비스입니다.

---

## 🛠 기술 스택
- Backend: Java 17, Spring Boot, Spring Security, MyBatis
- Frontend: HTML, CSS, JavaScript
- Database: MySQL
- AI: Spring AI, OpenAI API
- Auth: JWT (Access / Refresh Token)
- Infra: AWS EC2, Ubuntu, Embedded Tomcat
- Tool: IntelliJ IDEA, Git, GitHub

---

## ⚙ 주요 기능
- 회원가입 / 로그인 (JWT 기반 인증)
- 하루 계획(Intent) 작성
- 감정·행동 기록(Diary) 작성
- AI 요약 및 피드백 생성
- 마이페이지에서 기록 조회

---

## 🚀 배포
- AWS EC2(Ubuntu) 환경에 Spring Boot 애플리케이션 직접 배포
- MySQL 서버 구축 및 계정/권한 설정
- 8080 포트 인바운드 규칙 설정
- JAR 빌드 후 실행하여 서비스 운영

---

## 🧩 시스템 구조
Client → Spring Boot API → MySQL  
Client → Spring Boot → Spring AI → OpenAI

---

## 💡 개발 포인트
- JWT 기반 인증 구조 설계
- MyBatis를 활용한 SQL 중심 데이터 처리
- Spring AI 기반 OpenAI 연동
- 환경변수 분리 및 gitignore로 민감정보 관리

---

## 📌 향후 개선 사항
- 주간/월간 리포트 기능
- 감정 통계 시각화
- Docker 기반 배포 자동화

---

## 🧑‍💻 개발자
정수 (Backend Developer)

