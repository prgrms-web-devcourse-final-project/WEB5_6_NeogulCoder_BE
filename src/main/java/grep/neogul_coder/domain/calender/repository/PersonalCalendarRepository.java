package grep.neogul_coder.domain.calender.repository;

import grep.neogul_coder.domain.calender.entity.PersonalCalendar;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonalCalendarRepository extends JpaRepository<PersonalCalendar, Long> {
    List<PersonalCalendar> findByUserId(Long userId);
    List<PersonalCalendar> findByUserIdAndCalendar_ScheduledStartLessThanEqualAndCalendar_ScheduledEndGreaterThanEqual(
        Long userId, LocalDateTime endOfDay, LocalDateTime startOfDay
    );}