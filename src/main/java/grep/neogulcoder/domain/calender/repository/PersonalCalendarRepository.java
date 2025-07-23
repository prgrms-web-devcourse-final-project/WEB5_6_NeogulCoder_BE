package grep.neogulcoder.domain.calender.repository;

import grep.neogulcoder.domain.calender.entity.PersonalCalendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonalCalendarRepository extends JpaRepository<PersonalCalendar, Long> {
    // 특정 사용자가 등록한 모든 개인 일정 전체 조회
    // 수정: Calendar 엔티티를 fetch join 하여 LazyInitializationException 방지
    @Query("SELECT pc FROM PersonalCalendar pc JOIN FETCH pc.calendar WHERE pc.userId = :userId AND pc.activated = true")
    List<PersonalCalendar> findAllWithCalendarByUserId(@Param("userId") Long userId);

}