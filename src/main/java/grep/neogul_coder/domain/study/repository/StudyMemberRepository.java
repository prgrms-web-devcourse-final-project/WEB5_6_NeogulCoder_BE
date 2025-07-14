package grep.neogul_coder.domain.study.repository;

import grep.neogul_coder.domain.study.StudyMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {
}
