package grep.neogul_coder.domain.study.repository;

import grep.neogul_coder.domain.study.Study;
import grep.neogul_coder.domain.study.StudyMember;
import grep.neogul_coder.domain.study.enums.StudyMemberRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {
    List<StudyMember> findByStudyId(long studyId);

    @Query("select m.study from StudyMember m where m.userId = :userId and m.study.activated = true")
    List<Study> findStudiesByUserId(@Param("userId") long userId);

    @Query("select sm from StudyMember sm join fetch sm.study where sm.study.id = :studyId")
    List<StudyMember> findByStudyIdFetchStudy(@Param("studyId") long studyId);

    int countByStudyIdAndActivatedTrue(Long studyId);

    boolean existsByStudyIdAndUserIdAndActivatedTrue(Long studyId, Long userId);

    @Modifying(clearAutomatically = true)
    @Query("update StudyMember m set m.activated = false where m.study.id = :studyId")
    void deactivateByStudyId(@Param("studyId") Long studyId);

    Optional<StudyMember> findByStudyIdAndUserId(Long studyId, Long userId);

    @Query("select m from StudyMember m where m.study.id = :studyId and m.role = 'MEMBER' and m.activated = true")
    List<StudyMember> findAvailableNewLeaders(@Param("studyId") Long studyId);

    @Query("select m.createdDate from StudyMember m where m.study.id = :studyId and m.userId = :userId and m.activated = true")
    LocalDateTime findCreatedDateByStudyIdAndUserId(@Param("studyId") Long studyId, @Param("userId") Long userId);

    boolean existsByStudyIdAndUserId(Long studyId, Long id);

    boolean existsByStudyIdAndUserIdAndRole(Long studyId, Long userId, StudyMemberRole role);
}
