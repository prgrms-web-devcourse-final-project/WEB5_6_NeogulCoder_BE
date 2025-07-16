package grep.neogul_coder.domain.calender.repository;

import grep.neogul_coder.domain.calender.entity.TeamCalendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TeamCalendarRepository extends JpaRepository<TeamCalendar, Long> {
    List<TeamCalendar> findByStudyId(Long studyId);
    List<TeamCalendar> findByStudyIdAndCalendar_ScheduledStartBetween(Long studyId, LocalDateTime start, LocalDateTime end);
}