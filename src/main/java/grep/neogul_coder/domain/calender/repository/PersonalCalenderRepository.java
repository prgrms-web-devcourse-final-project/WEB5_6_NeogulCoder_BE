package grep.neogul_coder.domain.calender.repository;

import grep.neogul_coder.domain.calender.entity.PersonalCalender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface PersonalCalenderRepository extends JpaRepository<PersonalCalender, Long> {

    @Query("""
        SELECT c FROM PersonalCalender c
        WHERE c.user.id = :userId
          AND (
                (c.startAt <= :endDate AND c.endAt >= :startDate)
              )
        """)
    List<PersonalCalender> findOverlappingSchedules(Long userId, LocalDate startDate, LocalDate endDate);
}
