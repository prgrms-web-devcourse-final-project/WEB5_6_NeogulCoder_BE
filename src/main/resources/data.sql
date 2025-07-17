INSERT INTO member (email, password, nickname, profile_image_url, role, oauth_id, oauth_provider) VALUES ('eungyeonghwang@ju.com',  '$2a$10$WZzvlwlN6FVtQvGUXw2CIeNQvT5fPfA4qN99NisD2GOyCeuC4W0t2','&QT5Jklx#t', 'https://www.lorempixel.com/227/245', 'ROLE_ADMIN', NULL, NULL);
INSERT INTO member (email, password, nickname, profile_image_url, role, oauth_id, oauth_provider) VALUES ('aryu@ihanjang.kr',  '$2a$10$WZzvlwlN6FVtQvGUXw2CIeNQvT5fPfA4qN99NisD2GOyCeuC4W0t2', '!h1nRU1oKH','https://placeimg.com/350/227/any', 'ROLE_USER', 2139, 'google');
INSERT INTO member (email, password, nickname, profile_image_url, role, oauth_id, oauth_provider) VALUES ('eunjeongi@hotmail.com',  '$2a$10$WZzvlwlN6FVtQvGUXw2CIeNQvT5fPfA4qN99NisD2GOyCeuC4W0t2', 'm#T99Yfl@*','https://placekitten.com/101/440', 'ROLE_USER', 5741, 'google');
INSERT INTO member (email, password, nickname, profile_image_url, role, oauth_id, oauth_provider) VALUES ('rryu@yuhanhoesa.com',  '$2a$10$WZzvlwlN6FVtQvGUXw2CIeNQvT5fPfA4qN99NisD2GOyCeuC4W0t2','NPy2EpXd$6', 'https://placekitten.com/842/456', 'ROLE_USER', NULL, NULL);
INSERT INTO member (email, password, nickname, profile_image_url, role, oauth_id, oauth_provider) VALUES ('yeonghoseo@nate.com',  '$2a$10$WZzvlwlN6FVtQvGUXw2CIeNQvT5fPfA4qN99NisD2GOyCeuC4W0t2', '$c3VDMSkF)','https://placekitten.com/812/623', 'ROLE_ADMIN', NULL, NULL);

INSERT INTO study (origin_study_id, name, category, capacity, current_count, study_type, location, start_date, end_date, introduction, image_url, is_finished) VALUES (NULL, '자바 스터디', 'IT', 10, 1, 'ONLINE', NULL, '2025-08-01', '2025-12-31', '자바 스터디에 오신 것을 환영합니다.', 'https://example.com/image.jpg', FALSE);
INSERT INTO study (origin_study_id, name, category, capacity, current_count, study_type, location, start_date, end_date, introduction, image_url, is_finished) VALUES (NULL, '파이썬 스터디', 'IT', 8, 1, 'OFFLINE', '대구', '2025-09-01', '2026-01-31', '파이썬 기초부터 심화까지 학습합니다.', 'https://example.com/python.jpg', FALSE);
INSERT INTO study (origin_study_id, name, category, capacity, current_count, study_type, location, start_date, end_date, introduction, image_url, is_finished) VALUES (NULL, '디자인 스터디', 'DESIGN', 6, 1, 'HYBRID', '서울', '2025-07-15', '2025-10-15', 'UI/UX 디자인 실습 중심 스터디입니다.', 'https://example.com/design.jpg', FALSE);
INSERT INTO study (origin_study_id, name, category, capacity, current_count, study_type, location, start_date, end_date, introduction, image_url, is_finished) VALUES (NULL, '7급 공무원 스터디', 'EXAM', 12, 1, 'ONLINE', NULL, '2025-08-10', '2025-12-20', '7급 공무원 대비 스터디입니다.', 'https://example.com/exam.jpg', FALSE);
INSERT INTO study (origin_study_id, name, category, capacity, current_count, study_type, location, start_date, end_date, introduction, image_url, is_finished) VALUES (NULL, '토익 스터디', 'LANGUAGE', 9, 1, 'OFFLINE', '광주', '2025-09-05', '2026-02-28', '토익 스터디입니다.', 'https://example.com/datascience.jpg', FALSE);

INSERT INTO study_post (study_id, user_id, title, category, content) VALUES (5, 3, '자바 스터디 1주차 공지.', 'NOTICE', '1주차 스터디 내용은 가위바위보 게임 만들기 입니다. 모두 각자 만드시고 설명 하는 시간을 가지겠습니다.');
INSERT INTO study_post (study_id, user_id, title, category, content) VALUES (4, 4, '익명 클래스 자료 공유', 'FREE', '동물 이라는 인터페이스가 있을때 구현체는 강아지, 고양이 등이 있습니다. 구현을 하면 여러 구현 클래스가 필요합니다 이를 줄이기 위해 익명클래스를 사용할 수 있습니다.');
INSERT INTO study_post (study_id, user_id, title, category, content) VALUES (4, 2, 'ArrayList LinkedList 차이', 'FREE', 'ArrayList LinkedList 차이가 궁금 합니다!');
INSERT INTO study_post (study_id, user_id, title, category, content) VALUES (2, 2, '개발 유튜브 공유', 'FREE', '재미니의 개발실무 ( 토스 ); 개발바닥');
INSERT INTO study_post (study_id, user_id, title, category, content) VALUES (5, 5, '점심 메뉴 추천', 'FREE', '오늘 점심 뭐먹을지 추천 받습니다!');

INSERT INTO comment (post_id, user_id, content) VALUES (5, 4, '확인 했습니다!');
INSERT INTO comment (post_id, user_id, content) VALUES (5, 4, '좋은 정보 감사합니다!');
INSERT INTO comment (post_id, user_id, content) VALUES (3, 2, '관련된 블로그 공유 드립니다!');
INSERT INTO comment (post_id, user_id, content) VALUES (2, 5, '정보 감사합니다!');
INSERT INTO comment (post_id, user_id, content) VALUES (4, 1, '제육 돈까스');

INSERT INTO study_member (study_id, user_id, role) VALUES (1, 3, 'LEADER');
INSERT INTO study_member (study_id, user_id, role) VALUES (1, 4, 'MEMBER');
INSERT INTO study_member (study_id, user_id, role) VALUES (2, 5, 'LEADER');
INSERT INTO study_member (study_id, user_id, role) VALUES (3, 2, 'LEADER');
INSERT INTO study_member (study_id, user_id, role) VALUES (3, 1, 'MEMBER');

INSERT INTO recruitment_post (user_id, study_id, subject, content, recruitment_count, expired_date, status) VALUES (3, 1, '자바 스터디 모집', '이펙티브 자바 공부하실분 구해요!!', 3, '2025-04-19', 'COMPLETE');
INSERT INTO recruitment_post (user_id, study_id, subject, content, recruitment_count, expired_date, status) VALUES (2, 4, '면접 스터디 모집', '삼성 면접 1차 합격자 분들 같이 준비하실분들 찾습니다', 1, '2025-07-08', 'COMPLETE');
INSERT INTO recruitment_post (user_id, study_id, subject, content, recruitment_count, expired_date, status) VALUES (5, 2, '호밀밭의 파수꾼 책 같이 읽으실분', '책 같이 읽고 의견을 공유 하실분을 찾고 있습니다!! 많은 참여 부탁드려요', 5, '2025-06-10', 'IN_PROGRESS');
INSERT INTO recruitment_post (user_id, study_id, subject, content, recruitment_count, expired_date, status) VALUES (3, 5, '클라이밍 같이 하실분 모집 합니다', '클라이밍 좋아하시는 분들의 많은 관심 바랍니다!', 5, '2025-06-08', 'IN_PROGRESS');
INSERT INTO recruitment_post (user_id, study_id, subject, content, recruitment_count, expired_date, status) VALUES (2, 3, '토익 스터디', '토익 고수들의 많은 참여 부탁 드립니다', 2, '2025-03-31', 'IN_PROGRESS');

INSERT INTO study_application ( recruitment_post_id, application_reason, is_read, status) VALUES (1, '자바 스터디에 참여하고 싶습니다.', FALSE, 'PENDING');
INSERT INTO study_application ( recruitment_post_id, application_reason, is_read, status) VALUES (1, '열심히 참여하겠습니다.', TRUE, 'APPROVED');
INSERT INTO study_application ( recruitment_post_id, application_reason, is_read, status) VALUES (3, '관심있어 지원합니다.', FALSE, 'PENDING');
INSERT INTO study_application ( recruitment_post_id, application_reason, is_read, status) VALUES (2, '시간이 맞아 지원합니다.', FALSE, 'REJECTED');
INSERT INTO study_application ( recruitment_post_id, application_reason, is_read, status) VALUES (3, '프로젝트 경험 쌓고 싶습니다.', TRUE, 'APPROVED');

INSERT INTO calendar (scheduled_start, scheduled_end, title, content) VALUES ('2025-07-20 10:00:00', '2025-07-20 12:00:00', '스터디 회의', '진행 상황 공유');
INSERT INTO calendar (scheduled_start, scheduled_end, title, content) VALUES ('2025-07-21 09:00:00', '2025-07-21 10:30:00', '모각코', '혼자 코딩하기');
INSERT INTO calendar (scheduled_start, scheduled_end, title, content) VALUES ('2025-07-22 15:00:00', '2025-07-22 16:00:00', '운동', '헬스장 가기');
INSERT INTO calendar (scheduled_start, scheduled_end, title, content) VALUES ('2025-07-23 13:00:00', '2025-07-23 14:30:00', '리팩토링', '코드 개선');
INSERT INTO calendar (scheduled_start, scheduled_end, title, content) VALUES ('2025-07-24 11:00:00', '2025-07-24 12:00:00', '디자인 회의', 'UI 피드백');

INSERT INTO personal_calendar (user_id, calendar_id) VALUES (1, 1);
INSERT INTO personal_calendar (user_id, calendar_id) VALUES (1, 2);
INSERT INTO personal_calendar (user_id, calendar_id) VALUES (2, 3);
INSERT INTO personal_calendar (user_id, calendar_id) VALUES (3, 4);
INSERT INTO personal_calendar (user_id, calendar_id) VALUES (4, 5);

INSERT INTO team_calendar (study_id, user_id, calendar_id) VALUES (1, 1, 1);
INSERT INTO team_calendar (study_id, user_id, calendar_id) VALUES (2, 1, 2);
INSERT INTO team_calendar (study_id, user_id, calendar_id) VALUES (1, 2, 3);
INSERT INTO team_calendar (study_id, user_id, calendar_id) VALUES (3, 3, 4);
INSERT INTO team_calendar (study_id, user_id, calendar_id) VALUES (4, 4, 5);

INSERT INTO time_vote_period (study_id, start_date, end_date) VALUES (2, '2025-07-15 00:00:00', '2025-07-22 00:00:00');

INSERT INTO time_vote (period_id, study_member_id, start_time, end_time) VALUES (1, 1, '2025-07-15 10:00:00', '2025-07-15 12:00:00');
INSERT INTO time_vote (period_id, study_member_id, start_time, end_time) VALUES (1, 2, '2025-07-16 14:00:00', '2025-07-16 16:00:00');
INSERT INTO time_vote (period_id, study_member_id, start_time, end_time) VALUES (1, 3, '2025-07-17 09:30:00', '2025-07-17 11:30:00');
INSERT INTO time_vote (period_id, study_member_id, start_time, end_time) VALUES (1, 4, '2025-07-18 13:00:00', '2025-07-18 15:00:00');
INSERT INTO time_vote (period_id, study_member_id, start_time, end_time) VALUES (1, 5, '2025-07-19 19:00:00', '2025-07-19 21:00:00');

INSERT INTO time_vote_stat (period_id, start_time, end_time, vote_count) VALUES (1, '2025-07-15 10:00:00', '2025-07-15 12:00:00', 2);
INSERT INTO time_vote_stat (period_id, start_time, end_time, vote_count) VALUES (1, '2025-07-16 14:00:00', '2025-07-16 16:00:00', 3);
INSERT INTO time_vote_stat (period_id, start_time, end_time, vote_count) VALUES (1, '2025-07-17 09:30:00', '2025-07-17 11:30:00', 1);
INSERT INTO time_vote_stat (period_id, start_time, end_time, vote_count) VALUES (1, '2025-07-18 13:00:00', '2025-07-18 15:00:00', 4);
INSERT INTO time_vote_stat (period_id, start_time, end_time, vote_count) VALUES (1, '2025-07-19 19:00:00', '2025-07-19 21:00:00', 5);

-- INSERT INTO alarm (receive_user_id, alram_type, message, is_read, redirect_url) VALUES (5, 'APPLICATION', 'jiweon01님이 스터디 신청을 했습니다.', FALSE, '/admin/studies/2/applications');
-- INSERT INTO alarm (receive_user_id, alram_type, message, is_read, redirect_url) VALUES (3, 'APPLICATION_STATUS', '스터디 신청이 수락되었습니다.', FALSE, '/my/applications');
-- INSERT INTO alarm (receive_user_id, alram_type, message, is_read, redirect_url) VALUES (2, 'COMMENT', 'eunji35님이 댓글을 남겼습니다.', FALSE, '/posts/3');
-- INSERT INTO alarm (receive_user_id, alram_type, message, is_read, redirect_url) VALUES (1, 'TIME_VOTE', '스터디 모임 시간 투표가 시작되었습니다.', FALSE, '/studies/5/time-vote');
-- INSERT INTO alarm (receive_user_id, alram_type, message, is_read, redirect_url) VALUES (4, 'APPLICATION_STATUS', '스터디 신청이 거절되었습니다.', TRUE, '/my/applications');

INSERT INTO quiz (post_id, quiz, quiz_answer) VALUES (1, 'Java에서 List는 인터페이스다.', TRUE);
INSERT INTO quiz (post_id, quiz, quiz_answer) VALUES (4, 'Spring Boot는 톰캣을 기본 내장하지 않는다.', FALSE);
INSERT INTO quiz (post_id, quiz, quiz_answer) VALUES (1, 'JPA는 SQL을 직접 작성하지 않아도 된다.', TRUE);
INSERT INTO quiz (post_id, quiz, quiz_answer) VALUES (2, 'React는 서버 사이드 렌더링만 지원한다.', FALSE);
INSERT INTO quiz (post_id, quiz, quiz_answer) VALUES (5, '의존성 주입은 Spring의 핵심 기능 중 하나이다.', TRUE);

-- INSERT INTO upload_img (origin_file_name, rename_file_name, file_url, save_path, uploader_id, usage_type, usage_ref_id) VALUES ('soluta.png', '63922d43-ee89-49ba-8a31-b658a59cd5f9.png', 'https://storage.googleapis.com/neogulcoder-wibby/post/2025/07/14/63922d43-ee89-49ba-8a31-b658a59cd5f9.png', 'post/2025/07/14/', 2, 'POST', 3);
-- INSERT INTO upload_img (origin_file_name, rename_file_name, file_url, save_path, uploader_id, usage_type, usage_ref_id) VALUES ('accusantium.png', '37337416-66da-4ab9-890a-ad855cea1c49.png', 'https://storage.googleapis.com/neogulcoder-wibby/post/2025/07/14/37337416-66da-4ab9-890a-ad855cea1c49.png', 'post/2025/07/14/', 3, 'POST', 4);
-- INSERT INTO upload_img (origin_file_name, rename_file_name, file_url, save_path, uploader_id, usage_type, usage_ref_id) VALUES ('ullam.png', 'e6225095-edd4-49bd-98ec-abe3f26c13e8.png', 'https://storage.googleapis.com/neogulcoder-wibby/post/2025/07/14/e6225095-edd4-49bd-98ec-abe3f26c13e8.png', 'post/2025/07/14/', 1, 'POST', 9);
-- INSERT INTO upload_img (origin_file_name, rename_file_name, file_url, save_path, uploader_id, usage_type, usage_ref_id) VALUES ('aliquid.png', 'da163f7e-6a68-4e04-8aae-034cf8dd587c.png', 'https://storage.googleapis.com/neogulcoder-wibby/profile/2025/07/14/da163f7e-6a68-4e04-8aae-034cf8dd587c.png', 'profile/2025/07/14/', 1, 'PROFILE', 9);
-- INSERT INTO upload_img (origin_file_name, rename_file_name, file_url, save_path, uploader_id, usage_type, usage_ref_id) VALUES ('earum.png', 'f65140dd-4cd2-49bb-9981-031e24a4e725.png', 'https://storage.googleapis.com/neogulcoder-wibby/profile/2025/07/14/f65140dd-4cd2-49bb-9981-031e24a4e725.png', 'profile/2025/07/14/', 1, 'PROFILE', 4);

INSERT INTO buddy_energy (user_id, level) VALUES (1, 50);
INSERT INTO buddy_energy (user_id, level) VALUES (2, 53);
INSERT INTO buddy_energy (user_id, level) VALUES (3, 49);

INSERT INTO buddy_log (reason, buddy_energy_id) VALUES ('SIGN_UP', 1);
INSERT INTO buddy_log (reason, buddy_energy_id) VALUES ('STUDY_DONE', 2);
INSERT INTO buddy_log (reason, buddy_energy_id) VALUES ('TEAM_LEADER_BONUS', 2);
INSERT INTO buddy_log (reason, buddy_energy_id) VALUES ('NEGATIVE_REVIEW', 3);

INSERT INTO pr_template (user_id, introduction, location) VALUES (1, '함께 성장하는 백엔드 개발자입니다. 협업과 커뮤니케이션을 중요하게 생각하며, 문제 해결에 집중하는 개발 문화를 지향합니다.', '서울시 강남구');
INSERT INTO pr_template (user_id, introduction, location) VALUES (3, '기획부터 배포까지 경험한 풀스택 개발자입니다.React와 Spring Boot를 주로 사용하며, 사용자 경험 개선에 관심이 많습니다.', '경기도 성남시');
INSERT INTO pr_template (user_id, introduction, location) VALUES (2, '실용적인 코드 작성을 지향하는 개발자입니다. 코드를 통해 팀의 효율을 높이고, 항상 학습하고 성장하는 자세를 가지고 있습니다.', '부산시 해운대구');
INSERT INTO pr_template (user_id, introduction, location) VALUES (4, '초심을 잃지 않는 프론트엔드 개발자입니다. Vue, React 기반 프로젝트 경험이 있으며, UI/UX에 대한 관심도 많습니다.', '대전시 유성구');
INSERT INTO pr_template (user_id, introduction, location) VALUES (5, '문제를 해결하는 것이 즐거운 백엔드 개발자입니다. JPA, QueryDSL 기반의 안정적인 데이터 처리와 아키텍처 설계에 관심이 있습니다.', '인천시 연수구');

INSERT INTO link (pr_id, pr_url, url_name) VALUES (1, 'https://github.com/yeongho', 'GitHub 포트폴리오');
INSERT INTO link (pr_id, pr_url, url_name) VALUES (2, 'https://velog.io/@jiweon01', '기술 블로그 (Velog)');
INSERT INTO link (pr_id, pr_url, url_name) VALUES (5, 'https://notion.so/dev-profile', '기술 이력서 (Notion)');
INSERT INTO link (pr_id, pr_url, url_name) VALUES (5, 'https://toss.im/team/gimgim', '팀 프로젝트 소개');
INSERT INTO link (pr_id, pr_url, url_name) VALUES (1, 'https://linkedin.com/in/eungyeong', 'LinkedIn 프로필');

INSERT INTO attendance (study_id, user_id, attendance_date) VALUES (1, 3, '2025-07-01');
INSERT INTO attendance (study_id, user_id, attendance_date) VALUES (1, 3, '2025-07-02');
INSERT INTO attendance (study_id, user_id, attendance_date) VALUES (1, 4, '2025-07-01');
INSERT INTO attendance (study_id, user_id, attendance_date) VALUES (2, 5, '2025-07-01');
INSERT INTO attendance (study_id, user_id, attendance_date) VALUES (3, 2, '2025-07-03');

INSERT INTO group_chat_room (study_id) VALUES (1);
INSERT INTO group_chat_room (study_id) VALUES (2);

INSERT INTO group_chat_message (room_id, user_id, content) VALUES (1, 1, '안녕하세요! 스터디 언제 시작하나요?');
INSERT INTO group_chat_message (room_id, user_id, content) VALUES (1, 2, '오늘 저녁 8시에 시작해요!');
INSERT INTO group_chat_message (room_id, user_id, content) VALUES (2, 3, '파일 올렸어요. 확인 부탁드려요.');
INSERT INTO group_chat_message (room_id, user_id, content) VALUES (2, 1, '네 확인했어요. 감사합니다!');

INSERT INTO review (study_id, write_user_id, target_user_id, content, created_date) VALUES (1, 1, 1, '열심히 참여하셨어요.', '2025-07-10 10:00:00');
INSERT INTO review (study_id, write_user_id, target_user_id, content, created_date) VALUES (1, 2, 2, '피드백이 빠르고 정확했어요. 하지만 지각을 자주하십니다', '2025-07-11 09:30:00');
INSERT INTO review (study_id, write_user_id, target_user_id, content, created_date) VALUES (1, 3, 2, '커뮤니케이션이 좋았어요', '2025-07-12 14:20:00');
INSERT INTO review (study_id, write_user_id, target_user_id, content, created_date) VALUES (2, 1, 2, '책임감이 느껴졌어요.', '2025-07-13 16:45:00');
INSERT INTO review (study_id, write_user_id, target_user_id, content, created_date) VALUES (2, 2, 1, '팀워크가 훌륭했어요.', '2025-07-14 11:15:00');

INSERT INTO review_tag (review_type, review_tag) VALUES ('GOOD', '항상 밝고 긍정적으로 참여 하십니다');
INSERT INTO review_tag (review_type, review_tag) VALUES ('BAD', '자주 지각을 하십니다');
INSERT INTO review_tag (review_type, review_tag) VALUES ('GOOD', '다른 분들에게 도움을 주려고 노력하십니다');
INSERT INTO review_tag (review_type, review_tag) VALUES ('GOOD', '일정 관리를 잘 하십니다');
INSERT INTO review_tag (review_type, review_tag) VALUES ('BAD', '스터디에 불성실 하게 참여 하였습니다');

INSERT INTO my_review_tag (review_tag_id, review_id) VALUES (1, 1);
INSERT INTO my_review_tag (review_tag_id, review_id) VALUES (3, 1);
INSERT INTO my_review_tag (review_tag_id, review_id) VALUES (2, 2);
INSERT INTO my_review_tag (review_tag_id, review_id) VALUES (5, 2);
INSERT INTO my_review_tag (review_tag_id, review_id) VALUES (4, 3);