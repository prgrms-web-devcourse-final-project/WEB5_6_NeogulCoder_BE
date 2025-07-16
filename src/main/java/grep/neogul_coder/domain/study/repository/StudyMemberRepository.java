package grep.neogul_coder.domain.study.repository;

import grep.neogul_coder.domain.study.Study;
import grep.neogul_coder.domain.study.StudyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {
    List<StudyMember> findByStudyId(long studyId);

    @Query("select m.study from StudyMember m where m.userId = :userId")
    List<Study> findStudiesByUserId(long userId);

    @Query("select sm from StudyMember sm join fetch sm.study where sm.study.id = :studyId")
    List<StudyMember> findByStudyIdFetchStudy(@Param("studyId") long studyId);

    int countByStudyIdAndActivatedTrue(Long studyId);

    boolean existsByStudyIdAndUserIdAndActivatedTrue(Long studyId, Long userId);
}
