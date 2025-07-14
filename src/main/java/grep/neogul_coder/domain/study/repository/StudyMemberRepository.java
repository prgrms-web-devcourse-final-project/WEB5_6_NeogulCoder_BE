package grep.neogul_coder.domain.study.repository;

import grep.neogul_coder.domain.study.StudyMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {
    List<StudyMember> findByStudyId(long studyId);
}
