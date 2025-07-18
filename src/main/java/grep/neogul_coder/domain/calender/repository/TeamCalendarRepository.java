package grep.neogul_coder.domain.calender.repository;

import grep.neogul_coder.domain.calender.entity.TeamCalendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeamCalendarRepository extends JpaRepository<TeamCalendar, Long> {

    // 특정 스터디(studyId)의 전체 팀 일정 조회
    @Query("SELECT tc FROM TeamCalendar tc JOIN FETCH tc.calendar WHERE tc.studyId = :studyId AND tc.activated = true")
    List<TeamCalendar> findAllWithCalendarByStudyId(@Param("studyId") Long studyId);
}