package grep.neogulcoder.domain.study.repository;

import grep.neogulcoder.domain.study.Study;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StudyRepository extends JpaRepository<Study, Long> {
    List<Study> findByIdIn(List<Long> studyIds);

    Optional<Study> findByIdAndActivatedTrue(Long studyId);

    Optional<Study> findByOriginStudyIdAndActivatedTrue(Long originStudyId);

    @Modifying(clearAutomatically = true)
    @Query("update Study s set s.activated = false where s.id = :studyId")
    void deactivateByStudyId(@Param("studyId") Long studyId);

    @Query("select s from Study s where s.endDate >= :endDateStart and s.endDate < :endDateEnd and s.finished = false and s.activated = true")
    List<Study> findStudiesEndingIn7Days(@Param("endDateStart") LocalDateTime endDateStart, @Param("endDateEnd") LocalDateTime endDateEnd);

    @Query("select s from Study s where s.endDate < :now and s.finished = false and s.activated = true")
    List<Study> findStudiesToBeFinished(@Param("now") LocalDateTime now);

    int countByUserIdAndActivatedTrueAndFinishedFalse(Long userId);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT s FROM Study s WHERE s.id = :id")
    Optional<Study> findByIdWithLock(@Param("id") Long id);
}
