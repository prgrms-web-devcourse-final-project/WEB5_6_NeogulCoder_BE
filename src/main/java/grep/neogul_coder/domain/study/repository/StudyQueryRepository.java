package grep.neogul_coder.domain.study.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import grep.neogul_coder.domain.study.QStudy;
import grep.neogul_coder.domain.study.QStudyMember;
import grep.neogul_coder.domain.study.controller.dto.response.StudyMemberResponse;
import grep.neogul_coder.domain.users.entity.QUser;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static grep.neogul_coder.domain.study.enums.StudyMemberRole.LEADER;

@Repository
public class StudyQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final QStudyMember qStudyMember = QStudyMember.studyMember;
    private final QUser qUser = QUser.user;
    private final QStudy qStudy = QStudy.study;

    public StudyQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Map<Long, String> findLeaderNicknamesByStudyIds(List<Long> studyIds) {
        return queryFactory
            .select(qStudy.id, qUser.nickname)
            .from(qStudyMember)
            .join(qUser).on(qUser.id.eq(qStudyMember.userId))
            .join(qStudy).on(qStudy.id.eq(qStudyMember.study.id))
            .where(
                qStudyMember.study.id.in(studyIds),
                qStudyMember.role.eq(LEADER)
            )
            .fetch()
            .stream()
            .collect(Collectors.toMap(
                row -> row.get(qStudy.id),
                row -> row.get(qUser.nickname)
            ));
    }

    public boolean isStudyLeader(Long studyId, Long userId) {
        return queryFactory
            .selectOne()
            .from(qStudyMember)
            .where(
                qStudyMember.study.id.eq(studyId),
                qStudyMember.userId.eq(userId),
                qStudyMember.role.eq(LEADER)
            )
            .fetchFirst() != null;
    }

    public List<StudyMemberResponse> findStudyMembers(Long studyId) {
        return queryFactory
            .select(qUser.id, qUser.nickname, qUser.profileImageUrl)
            .from(qStudyMember)
            .join(qUser).on(qUser.id.eq(qStudyMember.studyMemberId))
            .where(qStudyMember.study.id.eq(studyId))
            .fetch()
            .stream()
            .map(row -> new StudyMemberResponse(
                row.get(qUser.id),
                row.get(qUser.nickname),
                row.get(qUser.profileImageUrl)
            ))
            .toList();
    }
}
