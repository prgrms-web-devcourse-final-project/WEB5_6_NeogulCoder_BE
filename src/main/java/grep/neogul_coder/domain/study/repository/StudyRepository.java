package grep.neogul_coder.domain.study.repository;

import grep.neogul_coder.domain.study.Study;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

public interface StudyRepository extends JpaRepository<Study, Long> {
    List<Study> findByIdIn(List<Long> studyIds);

    Optional<Study> findByIdAndActivatedTrue(Long studyId);

    Optional<Study> findByOriginStudyIdAndActivatedTrue(Long originStudyId);

    @Modifying(clearAutomatically = true)
    @Query("update Study s set s.activated = false where s.id = :studyId")
    void deactivateByStudyId(@Param("studyId") Long studyId);

    @Query("select s from Study s where s.endDate >= :endDateStart and s.endDate < :endDateEnd and s.finished = false and s.activated = true")
    List<Study> findStudiesEndingIn7Days(@Param("endDateStart") LocalDateTime endDateStart, @Param("endDateEnd") LocalDateTime endDateEnd);
}
