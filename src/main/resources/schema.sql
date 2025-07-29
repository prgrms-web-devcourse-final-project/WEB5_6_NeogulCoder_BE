-- 시간 투표 통계 테이블에서 기간(period_id) + 시간 슬롯(time_slot) 조합의 중복을 방지하고
-- upsert 시 ON CONFLICT 조건을 성능 저하 없이 처리하기 위한 복합 유니크 인덱스
CREATE UNIQUE INDEX uq_time_vote_stat_period_slot ON time_vote_stat (period_id, time_slot);

-- 시간 투표 테이블에서 기간 및 사용자 기준으로 빠른 조인을 위해 인덱스 추가
-- 사용자 기준 삭제 성능 향상을 위해 추가
-- getSubmissionStatusList() 쿼리의 성능 최적화 목적
CREATE INDEX idx_time_vote_period_member ON time_vote (period_id, study_member_id);
