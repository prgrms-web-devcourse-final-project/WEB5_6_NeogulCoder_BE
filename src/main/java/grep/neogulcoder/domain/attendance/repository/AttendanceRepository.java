package grep.neogulcoder.domain.attendance.repository;

import grep.neogulcoder.domain.attendance.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    @Query("select count(a) > 0 from Attendance a where a.studyId = :studyId and a.userId = :userId and a.attendanceDate between :startOfDay and :endOfDay")
    boolean existsTodayAttendance(@Param("studyId") Long studyId,
                                  @Param("userId") Long userId,
                                  @Param("startOfDay") LocalDateTime startOfDay,
                                  @Param("endOfDay") LocalDateTime endOfDay);

    List<Attendance> findByStudyIdAndUserId(Long studyId, Long userId);
}
