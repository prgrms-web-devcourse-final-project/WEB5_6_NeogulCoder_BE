package grep.neogul_coder.domain.calender.repository;

import grep.neogul_coder.domain.calender.entity.TeamCalendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TeamCalendarRepository extends JpaRepository<TeamCalendar, Long> {

    // 특정 스터디(studyId)의 전체 팀 일정 조회
    List<TeamCalendar> findByStudyId(Long studyId);
}