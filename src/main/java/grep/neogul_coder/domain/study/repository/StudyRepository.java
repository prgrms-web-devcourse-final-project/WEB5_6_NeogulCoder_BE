package grep.neogul_coder.domain.study.repository;

import grep.neogul_coder.domain.study.Study;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudyRepository extends JpaRepository<Study, Long> {

    @Query("select m.userId from StudyMember m where m.study.id = :id and m.role = 'LEADER'")
    Long findLeaderUserIdByStudyId(@Param("id") Long id);
}
