select * from client;

select client.login_id from client where client.login_id like '%11';
select * from client;


select * from diary where diary_date < '2026-01-11' and emotion in ('HAPPY','SAD');

select * from diary where content like '%회사%';

select * from diary where diary_date between '2026-01-03' and '2026-01-12';

select * from diary order by diary_date desc limit 5;

select * from diary order by diary_date;

select * from diary;

select * from client;

select distinct emotion from diary;

select emotion as '이모션', count(*) as '각 이모션 갯수' from diary group by emotion;

select * from diary where emotion = 'HAPPY' order by diary_date desc limit 5;

#일기를 많이 쓴 사용자
select user_id, count(*) as cnt from diary group by user_id order by cnt desc limit 1;

SELECT COUNT(*) FROM diary WHERE diary_date BETWEEN '2026-01-01' AND '2026-01-31';

select * from ai_feedback;

select d.diary_date from diary d right outer join  ai_feedback f on d.diary_id = f.diary_id;

select * from ai_feedback;

#문제 1 AI 피드백이 존재하는 일기만 조회하라!
select d.diary_id, d.diary_date, d.emotion, af.content from diary d  join ai_feedback af on d.diary_id = af.diary_id;

#문제 2 AI 피드백이 없는 일기까지 포함해서 모든 일기를 최신순으로 조회하라.
select * from ai_feedback;

#문제 3 user_id = 3 인 사용자의 일기 중 AI 피드백이 있는 것만 조회하라.
select d.diary_date,d.content ,af.content from diary d inner join ai_feedback af on d.diary_id = af.diary_id where d.user_id = 2;

#문제 4 2026년 1월에 작성된 일기와 그에 대한 AI 피드백을 조회하라.
select distinct d.diary_date, af.content from diary d left join ai_feedback af on d.diary_id = af.diary_id where d.diary_date between '2026-01-01' and '2026-01-31';


select * from refresh_token;




