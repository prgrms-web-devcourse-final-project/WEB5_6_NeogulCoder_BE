package grep.neogul_coder.domain.study.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import grep.neogul_coder.domain.study.controller.dto.response.StudyMemberResponse;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static grep.neogul_coder.domain.study.QStudy.study;
import static grep.neogul_coder.domain.study.QStudyMember.*;
import static grep.neogul_coder.domain.study.enums.StudyMemberRole.LEADER;
import static grep.neogul_coder.domain.users.entity.QUser.*;

@Repository
public class StudyQueryRepository {

    private final JPAQueryFactory queryFactory;

    public StudyQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Map<Long, String> findLeaderNicknamesByStudyIds(List<Long> studyIds) {
        return queryFactory
            .select(study.id, user.nickname)
            .from(studyMember)
            .join(user).on(user.id.eq(studyMember.userId))
            .join(study).on(study.id.eq(studyMember.study.id))
            .where(
                studyMember.study.id.in(studyIds),
                studyMember.role.eq(LEADER)
            )
            .fetch()
            .stream()
            .collect(Collectors.toMap(
                row -> row.get(study.id),
                row -> row.get(user.nickname)
            ));
    }

    public boolean isStudyLeader(Long studyId, Long userId) {
        return queryFactory
            .selectOne()
            .from(studyMember)
            .where(
                studyMember.study.id.eq(studyId),
                studyMember.userId.eq(userId),
                studyMember.role.eq(LEADER)
            )
            .fetchFirst() != null;
    }

    public List<StudyMemberResponse> findStudyMembers(Long studyId) {
        return queryFactory
            .select(user.id, user.nickname, user.profileImageUrl)
            .from(studyMember)
            .join(user).on(user.id.eq(studyMember.studyMemberId))
            .where(studyMember.study.id.eq(studyId))
            .fetch()
            .stream()
            .map(row -> new StudyMemberResponse(
                row.get(user.id),
                row.get(user.nickname),
                row.get(user.profileImageUrl)
            ))
            .toList();
    }
}
