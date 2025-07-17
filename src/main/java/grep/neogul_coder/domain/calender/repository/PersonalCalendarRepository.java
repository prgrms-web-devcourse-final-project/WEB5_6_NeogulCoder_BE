package grep.neogul_coder.domain.calender.repository;

import grep.neogul_coder.domain.calender.entity.PersonalCalendar;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonalCalendarRepository extends JpaRepository<PersonalCalendar, Long> {
    // 특정 사용자가 등록한 모든 개인 일정 전체 조회
    List<PersonalCalendar> findByUserId(Long userId);

}