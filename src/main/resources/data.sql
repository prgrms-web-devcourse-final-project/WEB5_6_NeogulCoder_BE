-- [ member ]
INSERT INTO member (activated, email, password, nickname, profile_image_url, role, oauth_id, oauth_provider) VALUES (true, 'eungyeonghwang@ju.com',  '{bcrypt}$2b$12$TK9zyc6f5qjHE1sSUSULieLOJVDBraWqSz2HRrIKgEKjUES0J2kva','&QT5Jklx#t', 'https://www.lorempixel.com/227/245', 'ROLE_ADMIN', NULL, NULL);
INSERT INTO member (activated, email, password, nickname, profile_image_url, role, oauth_id, oauth_provider) VALUES (true, 'aryu@ihanjang.kr',  '{bcrypt}$2b$12$TK9zyc6f5qjHE1sSUSULieLOJVDBraWqSz2HRrIKgEKjUES0J2kva', '!h1nRU1oKH','https://placeimg.com/350/227/any', 'ROLE_USER', 2139, 'google');
INSERT INTO member (activated, email, password, nickname, profile_image_url, role, oauth_id, oauth_provider) VALUES (true, 'eunjeongi@hotmail.com',  '{bcrypt}$2b$12$TK9zyc6f5qjHE1sSUSULieLOJVDBraWqSz2HRrIKgEKjUES0J2kva', 'm#T99Yfl@*','https://placekitten.com/101/440', 'ROLE_USER', 5741, 'google');
INSERT INTO member (activated, email, password, nickname, profile_image_url, role, oauth_id, oauth_provider) VALUES (true, 'rryu@yuhanhoesa.com',  '{bcrypt}$2b$12$TK9zyc6f5qjHE1sSUSULieLOJVDBraWqSz2HRrIKgEKjUES0J2kva','NPy2EpXd$6', 'https://placekitten.com/842/456', 'ROLE_USER', NULL, NULL);
INSERT INTO member (activated, email, password, nickname, profile_image_url, role, oauth_id, oauth_provider) VALUES (true, 'yeonghoseo@nate.com',  '{bcrypt}$2b$12$TK9zyc6f5qjHE1sSUSULieLOJVDBraWqSz2HRrIKgEKjUES0J2kva', '$c3VDMSkF)','https://placekitten.com/812/623', 'ROLE_ADMIN', NULL, NULL);
INSERT INTO member (activated, email, password, nickname, profile_image_url, role, oauth_id, oauth_provider) VALUES (true,'test@test.com',  '{bcrypt}$2b$12$TK9zyc6f5qjHE1sSUSULieLOJVDBraWqSz2HRrIKgEKjUES0J2kva', 'test','https://placekitten.com/812/624', 'ROLE_USER', NULL, NULL);
INSERT INTO member (activated, email, password, nickname, profile_image_url, role, oauth_id, oauth_provider) VALUES (true,'test2@test.com',  '{bcrypt}$2b$12$TK9zyc6f5qjHE1sSUSULieLOJVDBraWqSz2HRrIKgEKjUES0J2kva', 'test2','https://placekitten.com/812/625', 'ROLE_USER', NULL, NULL);
INSERT INTO member (activated, email, password, nickname, profile_image_url, role, oauth_id, oauth_provider) VALUES (true,'test3@test.com',  '{bcrypt}$2b$12$TK9zyc6f5qjHE1sSUSULieLOJVDBraWqSz2HRrIKgEKjUES0J2kva', 'test3','https://placekitten.com/812/626', 'ROLE_USER', NULL, NULL);

-- [ study ]
INSERT INTO study (user_id, origin_study_id, name, category, capacity, current_count, study_type, location, start_date, end_date, introduction, image_url, extended, activated, finished) VALUES (1, NULL, '자바 스터디', 'IT', 10, 1, 'ONLINE', NULL, '2025-08-01', '2025-12-31', '자바 스터디에 오신 것을 환영합니다.', 'https://example.com/image.jpg', FALSE, TRUE, FALSE);
INSERT INTO study (user_id, origin_study_id, name, category, capacity, current_count, study_type, location, start_date, end_date, introduction, image_url, extended, activated, finished) VALUES (1, NULL, '파이썬 스터디', 'IT', 8, 1, 'OFFLINE', '대구', '2025-09-01', '2026-01-31', '파이썬 기초부터 심화까지 학습합니다.', 'https://example.com/python.jpg', FALSE, TRUE, FALSE);
INSERT INTO study (user_id, origin_study_id, name, category, capacity, current_count, study_type, location, start_date, end_date, introduction, image_url, extended, activated, finished) VALUES (1, NULL, '디자인 스터디', 'DESIGN', 6, 1, 'HYBRID', '서울', '2025-07-15', '2025-10-15', 'UI/UX 디자인 실습 중심 스터디입니다.', 'https://example.com/design.jpg', FALSE, TRUE, FALSE);
INSERT INTO study (user_id, origin_study_id, name, category, capacity, current_count, study_type, location, start_date, end_date, introduction, image_url, extended, activated, finished) VALUES (1, NULL, '7급 공무원 스터디', 'EXAM', 12, 1, 'ONLINE', NULL, '2025-08-10', '2025-12-20', '7급 공무원 대비 스터디입니다.', 'https://example.com/exam.jpg', FALSE, TRUE, FALSE);
INSERT INTO study (user_id, origin_study_id, name, category, capacity, current_count, study_type, location, start_date, end_date, introduction, image_url, extended, activated, finished) VALUES (1, NULL, '토익 스터디', 'LANGUAGE', 9, 1, 'OFFLINE', '광주', '2025-09-05', '2026-02-28', '토익 스터디입니다.', 'https://example.com/datascience.jpg', FALSE, TRUE, FALSE);
INSERT INTO study (user_id, origin_study_id, name, category, capacity, current_count, study_type, location, start_date, end_date, introduction, image_url, extended, activated, finished) VALUES (1, NULL, 'wibby 프로젝트', 'IT', 9, 1, 'OFFLINE', '광주', '2025-09-05', '2026-02-28', '테스트 스터디입니다.', 'https://example.com/datascience.jpg', FALSE, TRUE, FALSE);

-- [ study_post ]
INSERT INTO study_post (study_id, user_id, title, category, content, activated) VALUES (5, 3, '자바 스터디 1주차 공지.', 'NOTICE', '1주차 스터디 내용은 가위바위보 게임 만들기 입니다. 모두 각자 만드시고 설명 하는 시간을 가지겠습니다.', true);
INSERT INTO study_post (study_id, user_id, title, category, content, activated) VALUES (4, 4, '익명 클래스 자료 공유', 'FREE', '동물 이라는 인터페이스가 있을때 구현체는 강아지, 고양이 등이 있습니다. 구현을 하면 여러 구현 클래스가 필요합니다 이를 줄이기 위해 익명클래스를 사용할 수 있습니다.', true);
INSERT INTO study_post (study_id, user_id, title, category, content, activated) VALUES (4, 2, 'ArrayList LinkedList 차이', 'FREE', 'ArrayList LinkedList 차이가 궁금 합니다!', true);
INSERT INTO study_post (study_id, user_id, title, category, content, activated) VALUES (2, 2, '개발 유튜브 공유', 'FREE', '재미니의 개발실무 ( 토스 ); 개발바닥', true);
INSERT INTO study_post (study_id, user_id, title, category, content, activated) VALUES (5, 5, '점심 메뉴 추천', 'FREE', '오늘 점심 뭐먹을지 추천 받습니다!', true);

-- [ comment ]
-- INSERT INTO comment (post_id, user_id, content) VALUES (5, 4, '확인 했습니다!');
-- INSERT INTO comment (post_id, user_id, content) VALUES (5, 4, '좋은 정보 감사합니다!');
-- INSERT INTO comment (post_id, user_id, content) VALUES (3, 2, '관련된 블로그 공유 드립니다!');
-- INSERT INTO comment (post_id, user_id, content) VALUES (2, 5, '정보 감사합니다!');
-- INSERT INTO comment (post_id, user_id, content) VALUES (4, 1, '제육 돈까스');

-- [ study_member ]
INSERT INTO study_member (study_id, user_id, role, participated, activated) VALUES (1, 3, 'LEADER', FALSE, TRUE);
INSERT INTO study_member (study_id, user_id, role, participated, activated) VALUES (1, 4, 'MEMBER', FALSE, TRUE);
INSERT INTO study_member (study_id, user_id, role, participated, activated) VALUES (2, 5, 'LEADER', FALSE, TRUE);
INSERT INTO study_member (study_id, user_id, role, participated, activated) VALUES (3, 2, 'LEADER', FALSE, TRUE);
INSERT INTO study_member (study_id, user_id, role, participated, activated) VALUES (3, 1, 'MEMBER', FALSE, TRUE);
INSERT INTO study_member (study_id, user_id, role, participated, activated) VALUES (6, 6, 'LEADER', FALSE, TRUE);
INSERT INTO study_member (study_id, user_id, role, participated, activated) VALUES (6, 7, 'MEMBER', FALSE, TRUE);
INSERT INTO study_member (study_id, user_id, role, participated, activated) VALUES (6, 8, 'MEMBER', FALSE, TRUE);

-- [ recruitment_post ]
INSERT INTO recruitment_post (user_id, study_id, subject, content, recruitment_count, expired_date, status, activated, created_date) VALUES (3, 1, '자바 스터디 모집', '이펙티브 자바 공부하실분 구해요!!', 3, '2025-04-19', 'COMPLETE', true, '2025-08-02 12:00:00');
INSERT INTO recruitment_post (user_id, study_id, subject, content, recruitment_count, expired_date, status, activated, created_date) VALUES (2, 4, '면접 스터디 모집', '삼성 면접 1차 합격자 분들 같이 준비하실분들 찾습니다', 1, '2025-07-08', 'COMPLETE', true, '2025-08-11 12:00:00');
INSERT INTO recruitment_post (user_id, study_id, subject, content, recruitment_count, expired_date, status, activated, created_date) VALUES (5, 2, '호밀밭의 파수꾼 책 같이 읽으실분', '책 같이 읽고 의견을 공유 하실분을 찾고 있습니다!! 많은 참여 부탁드려요', 5, '2025-06-10', 'IN_PROGRESS', true, '2025-09-02 12:00:00');
INSERT INTO recruitment_post (user_id, study_id, subject, content, recruitment_count, expired_date, status, activated, created_date) VALUES (3, 5, '클라이밍 같이 하실분 모집 합니다', '클라이밍 좋아하시는 분들의 많은 관심 바랍니다!', 5, '2025-06-08', 'IN_PROGRESS', true, '2025-09-06 12:00:00');
INSERT INTO recruitment_post (user_id, study_id, subject, content, recruitment_count, expired_date, status, activated, created_date) VALUES (2, 3, '토익 스터디', '토익 고수들의 많은 참여 부탁 드립니다', 2, '2025-03-31', 'IN_PROGRESS', true, '2025-07-16 12:00:00');

-- [ study_application ]
INSERT INTO study_application (user_id, recruitment_post_id, application_reason, is_read, status, activated) VALUES (1, 1, '자바 스터디에 참여하고 싶습니다.', FALSE, 'APPLYING', TRUE);
INSERT INTO study_application (user_id, recruitment_post_id, application_reason, is_read, status, activated) VALUES (2, 1, '열심히 참여하겠습니다.', TRUE, 'APPROVED', TRUE);
INSERT INTO study_application (user_id, recruitment_post_id, application_reason, is_read, status, activated) VALUES (3, 3, '관심있어 지원합니다.', FALSE, 'APPLYING', TRUE);
INSERT INTO study_application (user_id, recruitment_post_id, application_reason, is_read, status, activated) VALUES (4, 2, '시간이 맞아 지원합니다.', FALSE, 'REJECTED', TRUE);
INSERT INTO study_application (user_id, recruitment_post_id, application_reason, is_read, status, activated) VALUES (5, 3, '프로젝트 경험 쌓고 싶습니다.', TRUE, 'APPROVED', TRUE);

-- [ calendar ]
INSERT INTO calendar (scheduled_start, scheduled_end, title, content, activated) VALUES ('2025-07-20 10:00:00', '2025-07-20 12:00:00', '스터디 회의', '진행 상황 공유', TRUE);
INSERT INTO calendar (scheduled_start, scheduled_end, title, content, activated) VALUES ('2025-07-21 09:00:00', '2025-07-21 10:30:00', '모각코', '혼자 코딩하기', TRUE);
INSERT INTO calendar (scheduled_start, scheduled_end, title, content, activated) VALUES ('2025-07-22 15:00:00', '2025-07-22 16:00:00', '운동', '헬스장 가기', TRUE);
INSERT INTO calendar (scheduled_start, scheduled_end, title, content, activated) VALUES ('2025-07-23 13:00:00', '2025-07-23 14:30:00', '리팩토링', '코드 개선', TRUE);
INSERT INTO calendar (scheduled_start, scheduled_end, title, content, activated) VALUES ('2025-07-24 11:00:00', '2025-07-24 12:00:00', '디자인 회의', 'UI 피드백', TRUE);

INSERT INTO personal_calendar (user_id, calendar_id, activated) VALUES (1, 1, TRUE);
INSERT INTO personal_calendar (user_id, calendar_id, activated) VALUES (1, 2, TRUE);
INSERT INTO personal_calendar (user_id, calendar_id, activated) VALUES (2, 3, TRUE);
INSERT INTO personal_calendar (user_id, calendar_id, activated) VALUES (3, 4, TRUE);
INSERT INTO personal_calendar (user_id, calendar_id, activated) VALUES (4, 5, TRUE);

INSERT INTO team_calendar (study_id, user_id, calendar_id, activated) VALUES (1, 1, 1, TRUE);
INSERT INTO team_calendar (study_id, user_id, calendar_id, activated) VALUES (2, 1, 2, TRUE);
INSERT INTO team_calendar (study_id, user_id, calendar_id, activated) VALUES (1, 2, 3, TRUE);
INSERT INTO team_calendar (study_id, user_id, calendar_id, activated) VALUES (3, 3, 4, TRUE);
INSERT INTO team_calendar (study_id, user_id, calendar_id, activated) VALUES (4, 4, 5, TRUE);

-- [ time_vote_period ]
INSERT INTO time_vote_period (study_id, start_date, end_date, activated) VALUES (6, '2025-07-25 00:00:00', '2025-07-30 23:59:59', TRUE);

-- [ time_vote ]
-- 2025-07-25 10:00:00 - 2명
INSERT INTO time_vote (period_id, study_member_id, time_slot, activated) VALUES (1, 6, '2025-07-25 10:00:00', TRUE);
INSERT INTO time_vote (period_id, study_member_id, time_slot, activated) VALUES (1, 8, '2025-07-25 10:00:00', TRUE);
-- 2025-07-25 11:00:00 - 2명
INSERT INTO time_vote (period_id, study_member_id, time_slot, activated) VALUES (1, 6, '2025-07-25 11:00:00', TRUE);
INSERT INTO time_vote (period_id, study_member_id, time_slot, activated) VALUES (1, 8, '2025-07-25 11:00:00', TRUE);
-- 2025-07-26 14:00:00 - 3명
INSERT INTO time_vote (period_id, study_member_id, time_slot, activated) VALUES (1, 6, '2025-07-26 14:00:00', TRUE);
INSERT INTO time_vote (period_id, study_member_id, time_slot, activated) VALUES (1, 7, '2025-07-26 14:00:00', TRUE);
INSERT INTO time_vote (period_id, study_member_id, time_slot, activated) VALUES (1, 8, '2025-07-26 14:00:00', TRUE);
-- 2025-07-26 15:00:00 - 3명
INSERT INTO time_vote (period_id, study_member_id, time_slot, activated) VALUES (1, 6, '2025-07-26 15:00:00', TRUE);
INSERT INTO time_vote (period_id, study_member_id, time_slot, activated) VALUES (1, 7, '2025-07-26 15:00:00', TRUE);
INSERT INTO time_vote (period_id, study_member_id, time_slot, activated) VALUES (1, 8, '2025-07-26 15:00:00', TRUE);
-- 2025-07-27 09:00:00 - 1명
INSERT INTO time_vote (period_id, study_member_id, time_slot, activated) VALUES (1, 8, '2025-07-27 09:00:00', TRUE);
-- 2025-07-27 13:00:00 - 1명
INSERT INTO time_vote (period_id, study_member_id, time_slot, activated) VALUES (1, 8, '2025-07-27 13:00:00', TRUE);
-- 2025-07-28 13:00:00 - 3명
INSERT INTO time_vote (period_id, study_member_id, time_slot, activated) VALUES (1, 6, '2025-07-28 13:00:00', TRUE);
INSERT INTO time_vote (period_id, study_member_id, time_slot, activated) VALUES (1, 7, '2025-07-28 13:00:00', TRUE);
INSERT INTO time_vote (period_id, study_member_id, time_slot, activated) VALUES (1, 8, '2025-07-28 13:00:00', TRUE);
-- 2025-07-28 14:00:00 - 3명
INSERT INTO time_vote (period_id, study_member_id, time_slot, activated) VALUES (1, 6, '2025-07-28 14:00:00', TRUE);
INSERT INTO time_vote (period_id, study_member_id, time_slot, activated) VALUES (1, 7, '2025-07-28 14:00:00', TRUE);
INSERT INTO time_vote (period_id, study_member_id, time_slot, activated) VALUES (1, 8, '2025-07-28 14:00:00', TRUE);
-- 2025-07-29 19:00:00 - 2명
INSERT INTO time_vote (period_id, study_member_id, time_slot, activated) VALUES (1, 6, '2025-07-29 19:00:00', TRUE);
INSERT INTO time_vote (period_id, study_member_id, time_slot, activated) VALUES (1, 7, '2025-07-29 19:00:00', TRUE);
-- 2025-07-29 20:00:00 - 1명
INSERT INTO time_vote (period_id, study_member_id, time_slot, activated) VALUES (1, 8, '2025-07-29 20:00:00', TRUE);

-- [ time_vote_stat ]
INSERT INTO time_vote_stat (period_id, time_slot, vote_count, activated) VALUES (1, '2025-07-25 10:00:00', 2, TRUE);
INSERT INTO time_vote_stat (period_id, time_slot, vote_count, activated) VALUES (1, '2025-07-25 11:00:00', 2, TRUE);
INSERT INTO time_vote_stat (period_id, time_slot, vote_count, activated) VALUES (1, '2025-07-26 14:00:00', 3, TRUE);
INSERT INTO time_vote_stat (period_id, time_slot, vote_count, activated) VALUES (1, '2025-07-26 15:00:00', 3, TRUE);
INSERT INTO time_vote_stat (period_id, time_slot, vote_count, activated) VALUES (1, '2025-07-27 09:00:00', 1, TRUE);
INSERT INTO time_vote_stat (period_id, time_slot, vote_count, activated) VALUES (1, '2025-07-27 13:00:00', 1, TRUE);
INSERT INTO time_vote_stat (period_id, time_slot, vote_count, activated) VALUES (1, '2025-07-28 13:00:00', 3, TRUE);
INSERT INTO time_vote_stat (period_id, time_slot, vote_count, activated) VALUES (1, '2025-07-28 14:00:00', 3, TRUE);
INSERT INTO time_vote_stat (period_id, time_slot, vote_count, activated) VALUES (1, '2025-07-29 19:00:00', 2, TRUE);
INSERT INTO time_vote_stat (period_id, time_slot, vote_count, activated) VALUES (1, '2025-07-29 20:00:00', 1, TRUE);

-- [ alarm ]
-- 1. 스터디 초대 알림
INSERT INTO alarm (receiver_user_id, alarm_type, domain_type, domain_id, message, checked, activated) VALUES (101, 'INVITE', 'STUDY', 55, '[알고리즘 마스터즈 스터디] 에서 당신을 초대하고 싶어합니다.', false, TRUE);

-- 2. 모임 일정 투표 요청 알림
INSERT INTO alarm (receiver_user_id, alarm_type, domain_type, domain_id, message, checked, activated) VALUES (8, 'TIME_VOTE_REQUEST', 'TIME_VOTE', 6, '[mock) wibby 프로젝트] 스터디장이 모임 일정 조율을 위한 투표를 요청했습니다.', false, TRUE);
INSERT INTO alarm (receiver_user_id, alarm_type, domain_type, domain_id, message, checked, activated) VALUES (9, 'TIME_VOTE_REQUEST', 'TIME_VOTE', 6, '[mock) wibby 프로젝트] 스터디장이 모임 일정 조율을 위한 투표를 요청했습니다.', false, TRUE);

-- 3. 이미 확인된 알림
INSERT INTO alarm (receiver_user_id, alarm_type, domain_type, domain_id, message, checked, activated) VALUES (103, 'INVITE', 'STUDY', 22, '[네트워크 기초반] 스터디에서 당신을 초대하고 싶어합니다.', true, TRUE);

-- [ quiz ]
INSERT INTO quiz (post_id, quiz_content, quiz_answer, activated) VALUES (1, 'Java에서 List는 인터페이스다.', TRUE, TRUE);
INSERT INTO quiz (post_id, quiz_content, quiz_answer, activated) VALUES (4, 'Spring Boot는 톰캣을 기본 내장하지 않는다.', FALSE, TRUE);
INSERT INTO quiz (post_id, quiz_content, quiz_answer, activated) VALUES (1, 'JPA는 SQL을 직접 작성하지 않아도 된다.', TRUE, TRUE);
INSERT INTO quiz (post_id, quiz_content, quiz_answer, activated) VALUES (2, 'React는 서버 사이드 렌더링만 지원한다.', FALSE, TRUE);
INSERT INTO quiz (post_id, quiz_content, quiz_answer, activated) VALUES (5, '의존성 주입은 Spring의 핵심 기능 중 하나이다.', TRUE, TRUE);

-- [ upload_image ]
INSERT INTO upload_image (origin_file_name, rename_file_name, file_url, save_path, uploader_id, usage_type, usage_ref_id, activated) VALUES ('soluta.png', '63922d43-ee89-49ba-8a31-b658a59cd5f9.png', 'https://storage.googleapis.com/neogulcoder-wibby/post/2025/07/14/63922d43-ee89-49ba-8a31-b658a59cd5f9.png', 'post/2025/07/14/', 2, 'POST', 3, TRUE);
INSERT INTO upload_image (origin_file_name, rename_file_name, file_url, save_path, uploader_id, usage_type, usage_ref_id, activated) VALUES ('accusantium.png', '37337416-66da-4ab9-890a-ad855cea1c49.png', 'https://storage.googleapis.com/neogulcoder-wibby/post/2025/07/14/37337416-66da-4ab9-890a-ad855cea1c49.png', 'post/2025/07/14/', 3, 'POST', 4, TRUE);
INSERT INTO upload_image (origin_file_name, rename_file_name, file_url, save_path, uploader_id, usage_type, usage_ref_id, activated) VALUES ('ullam.png', 'e6225095-edd4-49bd-98ec-abe3f26c13e8.png', 'https://storage.googleapis.com/neogulcoder-wibby/post/2025/07/14/e6225095-edd4-49bd-98ec-abe3f26c13e8.png', 'post/2025/07/14/', 1, 'POST', 9, TRUE);
INSERT INTO upload_image (origin_file_name, rename_file_name, file_url, save_path, uploader_id, usage_type, usage_ref_id, activated) VALUES ('aliquid.png', 'da163f7e-6a68-4e04-8aae-034cf8dd587c.png', 'https://storage.googleapis.com/neogulcoder-wibby/profile/2025/07/14/da163f7e-6a68-4e04-8aae-034cf8dd587c.png', 'profile/2025/07/14/', 1, 'PROFILE', 9, TRUE);
INSERT INTO upload_image (origin_file_name, rename_file_name, file_url, save_path, uploader_id, usage_type, usage_ref_id, activated) VALUES ('earum.png', 'f65140dd-4cd2-49bb-9981-031e24a4e725.png', 'https://storage.googleapis.com/neogulcoder-wibby/profile/2025/07/14/f65140dd-4cd2-49bb-9981-031e24a4e725.png', 'profile/2025/07/14/', 1, 'PROFILE', 4, TRUE);

-- [ buddy_energy ]
INSERT INTO buddy_energy (user_id, level) VALUES (1, 50);
INSERT INTO buddy_energy (user_id, level) VALUES (2, 53);
INSERT INTO buddy_energy (user_id, level) VALUES (3, 49);
INSERT INTO buddy_energy (user_id, level) VALUES (7, 50);
INSERT INTO buddy_energy (user_id, level) VALUES (8, 50);

-- [ buddy_log ]
INSERT INTO buddy_log (reason, buddy_energy_id) VALUES ('SIGN_UP', 1);
INSERT INTO buddy_log (reason, buddy_energy_id) VALUES ('STUDY_DONE', 2);
INSERT INTO buddy_log (reason, buddy_energy_id) VALUES ('TEAM_LEADER_BONUS', 2);
INSERT INTO buddy_log (reason, buddy_energy_id) VALUES ('NEGATIVE_REVIEW', 3);
INSERT INTO buddy_log (reason, buddy_energy_id) VALUES ('SIGN_UP', 4);
INSERT INTO buddy_log (reason, buddy_energy_id) VALUES ('SIGN_UP', 5);

-- [ pr_template ]
INSERT INTO pr_template (user_id, introduction, location, activated) VALUES (1, '함께 성장하는 백엔드 개발자입니다. 협업과 커뮤니케이션을 중요하게 생각하며, 문제 해결에 집중하는 개발 문화를 지향합니다.', '서울시 강남구', TRUE);
INSERT INTO pr_template (user_id, introduction, location, activated) VALUES (3, '기획부터 배포까지 경험한 풀스택 개발자입니다.React와 Spring Boot를 주로 사용하며, 사용자 경험 개선에 관심이 많습니다.', '경기도 성남시', TRUE);
INSERT INTO pr_template (user_id, introduction, location, activated) VALUES (2, '실용적인 코드 작성을 지향하는 개발자입니다. 코드를 통해 팀의 효율을 높이고, 항상 학습하고 성장하는 자세를 가지고 있습니다.', '부산시 해운대구', TRUE);
INSERT INTO pr_template (user_id, introduction, location, activated) VALUES (4, '초심을 잃지 않는 프론트엔드 개발자입니다. Vue, React 기반 프로젝트 경험이 있으며, UI/UX에 대한 관심도 많습니다.', '대전시 유성구', TRUE);
INSERT INTO pr_template (user_id, introduction, location, activated) VALUES (5, '문제를 해결하는 것이 즐거운 백엔드 개발자입니다. JPA, QueryDSL 기반의 안정적인 데이터 처리와 아키텍처 설계에 관심이 있습니다.', '인천시 연수구', TRUE);

-- [ link ]
INSERT INTO link (user_id, pr_url, url_name, activated) VALUES (1, 'https://github.com/yeongho', 'GitHub 포트폴리오', true);
INSERT INTO link (user_id, pr_url, url_name, activated) VALUES (2, 'https://velog.io/@jiweon01', '기술 블로그 (Velog)', true);
INSERT INTO link (user_id, pr_url, url_name, activated) VALUES (5, 'https://notion.so/dev-profile', '기술 이력서 (Notion)', true);
INSERT INTO link (user_id, pr_url, url_name, activated) VALUES (5, 'https://toss.im/team/gimgim', '팀 프로젝트 소개', true);
INSERT INTO link (user_id, pr_url, url_name, activated) VALUES (1, 'https://linkedin.com/in/eungyeong', 'LinkedIn 프로필', true);

-- [ attendance ]
INSERT INTO attendance (study_id, user_id, attendance_date, activated) VALUES (1, 3, '2025-07-01', TRUE);
INSERT INTO attendance (study_id, user_id, attendance_date, activated) VALUES (1, 3, '2025-07-02', TRUE);
INSERT INTO attendance (study_id, user_id, attendance_date, activated) VALUES (1, 4, '2025-07-01', TRUE);
INSERT INTO attendance (study_id, user_id, attendance_date, activated) VALUES (2, 5, '2025-07-01', TRUE);
INSERT INTO attendance (study_id, user_id, attendance_date, activated) VALUES (3, 2, '2025-07-03', TRUE);

-- [ group_chat_room ]
INSERT INTO group_chat_room (study_id, activated) VALUES (1, true);
INSERT INTO group_chat_room (study_id, activated) VALUES (6, true);

-- [ group_chat_message ]
INSERT INTO group_chat_message (room_id, user_id, message, activated) VALUES (1, 1, '안녕하세요! 스터디 언제 시작하나요?', TRUE);
INSERT INTO group_chat_message (room_id, user_id, message, activated) VALUES (1, 2, '오늘 저녁 8시에 시작해요!', TRUE);
INSERT INTO group_chat_message (room_id, user_id, message, activated) VALUES (2, 3, '파일 올렸어요. 확인 부탁드려요.', TRUE);
INSERT INTO group_chat_message (room_id, user_id, message, activated) VALUES (2, 1, '네 확인했어요. 감사합니다!', TRUE);

-- [ review ]
INSERT INTO review (study_id, write_user_id, target_user_id, content, created_date, activated) VALUES (1, 1, 1, '열심히 참여하셨어요.', '2025-07-10 10:00:00', TRUE);
INSERT INTO review (study_id, write_user_id, target_user_id, content, created_date, activated) VALUES (1, 2, 2, '피드백이 빠르고 정확했어요. 하지만 지각을 자주하십니다', '2025-07-11 09:30:00', TRUE);
INSERT INTO review (study_id, write_user_id, target_user_id, content, created_date, activated) VALUES (1, 3, 2, '커뮤니케이션이 좋았어요', '2025-07-12 14:20:00', TRUE);
INSERT INTO review (study_id, write_user_id, target_user_id, content, created_date, activated) VALUES (2, 1, 2, '책임감이 느껴졌어요.', '2025-07-13 16:45:00', TRUE);
INSERT INTO review (study_id, write_user_id, target_user_id, content, created_date, activated) VALUES (2, 2, 1, '팀워크가 훌륭했어요.', '2025-07-14 11:15:00', TRUE);

-- [ review_tag ]
INSERT INTO review_tag (review_type, review_tag, activated) VALUES ('GOOD', '항상 밝고 긍정적으로 참여 하십니다', TRUE);
INSERT INTO review_tag (review_type, review_tag, activated) VALUES ('BAD', '자주 지각을 하십니다', TRUE);
INSERT INTO review_tag (review_type, review_tag, activated) VALUES ('GOOD', '다른 분들에게 도움을 주려고 노력하십니다', TRUE);
INSERT INTO review_tag (review_type, review_tag, activated) VALUES ('GOOD', '일정 관리를 잘 하십니다', TRUE);
INSERT INTO review_tag (review_type, review_tag, activated) VALUES ('BAD', '스터디에 불성실 하게 참여 하였습니다', TRUE);

-- [ my_review_tag ]
INSERT INTO my_review_tag (review_tag_id, review_id, activated) VALUES (1, 1, TRUE);
INSERT INTO my_review_tag (review_tag_id, review_id, activated) VALUES (3, 1, TRUE);
INSERT INTO my_review_tag (review_tag_id, review_id, activated) VALUES (2, 2, TRUE);
INSERT INTO my_review_tag (review_tag_id, review_id, activated) VALUES (5, 2, TRUE);
INSERT INTO my_review_tag (review_tag_id, review_id, activated) VALUES (4, 3, TRUE);
