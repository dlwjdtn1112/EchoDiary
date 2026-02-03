
# 2️⃣ member 테이블 (사용자)
CREATE TABLE client (
                      user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      login_id VARCHAR(255) NOT NULL UNIQUE,
                      password VARCHAR(255) NOT NULL,
                      nickname VARCHAR(50) NOT NULL UNIQUE,
                      role VARCHAR(20) DEFAULT 'USER', -- USER / ADMIN
                      created_at DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;


# 3️⃣ DIARY 테이블 (일기)
CREATE TABLE diary (
                       diary_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
                       user_id    BIGINT NOT NULL,
                       content    TEXT NOT NULL,

                       emotion ENUM('HAPPY', 'NEUTRAL', 'SAD', 'ANGRY')
                           COMMENT '감정 상태',

                       diary_date DATE NOT NULL,
                       created_at DATETIME DEFAULT CURRENT_TIMESTAMP,

                       CONSTRAINT fk_diary_user
                           FOREIGN KEY (user_id)
                               REFERENCES client(user_id)
                               ON DELETE CASCADE
) ENGINE=InnoDB;

ALTER TABLE diary
    ADD CONSTRAINT uq_user_date UNIQUE (user_id, diary_date);

# 4️⃣ AI_FEEDBACK 테이블 (AI Echo)
CREATE TABLE ai_feedback (
                             feedback_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             diary_id BIGINT NOT NULL,
                             user_id BIGINT NOT NULL,
                             content TEXT NOT NULL,
                             prompt_version VARCHAR(50),
                             created_at DATETIME DEFAULT CURRENT_TIMESTAMP,

                             CONSTRAINT fk_feedback_diary
                                 FOREIGN KEY (diary_id)
                                     REFERENCES diary(diary_id)
                                     ON DELETE CASCADE,

                             CONSTRAINT fk_feedback_user
                                 FOREIGN KEY (user_id)
                                     REFERENCES client(user_id)
                                     ON DELETE CASCADE
) ENGINE=InnoDB;

# 5️⃣ WEEKLY_REPORT 테이블 (주간 리포트)
CREATE TABLE weekly_report (
                               report_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               user_id BIGINT NOT NULL,
                               start_date DATE NOT NULL,
                               end_date DATE NOT NULL,
                               summary_text TEXT,
                               dominant_emotion VARCHAR(20),
                               created_at DATETIME DEFAULT CURRENT_TIMESTAMP,

                               CONSTRAINT fk_report_user
                                   FOREIGN KEY (user_id)
                                       REFERENCES client(user_id)
                                       ON DELETE CASCADE
) ENGINE=InnoDB;

ALTER TABLE weekly_report
    ADD CONSTRAINT uq_weekly_report_user_period
        UNIQUE (user_id, start_date, end_date);



# 6️⃣ AI_LOG 테이블 (관리자/운영용)
CREATE TABLE ai_log (
                        log_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        feedback_id BIGINT NOT NULL,
                        status VARCHAR(20),           -- SUCCESS / FAIL
                        response_time INT,            -- ms
                        created_at DATETIME DEFAULT CURRENT_TIMESTAMP,

                        CONSTRAINT fk_log_feedback
                            FOREIGN KEY (feedback_id)
                                REFERENCES ai_feedback(feedback_id)
                                ON DELETE CASCADE
) ENGINE=InnoDB;


# 1️⃣ refresh_token 테이블(리플레시 토큰)
CREATE TABLE refresh_token (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,

                               user_id BIGINT NOT NULL,                -- client.user_id FK
                               refresh_token VARCHAR(255) NOT NULL UNIQUE,

                               issued_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               expires_at DATETIME NOT NULL,

                               CONSTRAINT fk_refresh_token_user
                                   FOREIGN KEY (user_id)
                                       REFERENCES client(user_id)
                                       ON DELETE CASCADE
) ENGINE=InnoDB;



