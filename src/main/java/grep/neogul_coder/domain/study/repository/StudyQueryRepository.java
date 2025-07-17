package grep.neogul_coder.domain.study.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import grep.neogul_coder.domain.study.controller.dto.response.StudyItemResponse;
import grep.neogul_coder.domain.study.controller.dto.response.StudyMemberResponse;
import grep.neogul_coder.domain.study.enums.StudyMemberRole;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static grep.neogul_coder.domain.study.QStudy.study;
import static grep.neogul_coder.domain.study.QStudyMember.*;
import static grep.neogul_coder.domain.users.entity.QUser.*;

@Repository
public class StudyQueryRepository {

    private final JPAQueryFactory queryFactory;

    public StudyQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Page<StudyItemResponse> findMyStudiesPaging(Pageable pageable, Long userId) {
        List<StudyItemResponse> studies = queryFactory
            .select(Projections.constructor(
                StudyItemResponse.class,
                study.id,
                study.name,
                user.nickname,
                study.capacity,
                study.currentCount,
                study.startDate,
                study.imageUrl,
                study.introduction,
                study.category,
                study.studyType,
                study.isFinished
            ))
            .from(studyMember)
            .join(user).on(user.id.eq(studyMember.userId))
            .join(study).on(study.id.eq(studyMember.study.id))
            .where(
                studyMember.userId.eq(userId),
                studyMember.activated.isTrue()
            )
            .fetch();

        Long total = queryFactory
            .select(study.count())
            .from(studyMember)
            .where(
                studyMember.userId.eq(userId),
                studyMember.activated.eq(true)
            )
            .fetchOne();

        return new PageImpl<>(studies, pageable, total);
    }

    public List<StudyItemResponse> findMyStudies(Long userId) {
        return queryFactory
            .select(Projections.constructor(
                StudyItemResponse.class,
                study.id,
                study.name,
                user.nickname,
                study.capacity,
                study.currentCount,
                study.startDate,
                study.imageUrl,
                study.introduction,
                study.category,
                study.studyType,
                study.isFinished
            ))
            .from(studyMember)
            .join(user).on(user.id.eq(studyMember.userId))
            .join(study).on(study.id.eq(studyMember.study.id))
            .where(
                studyMember.userId.eq(userId),
                studyMember.activated.isTrue()
            )
            .fetch();
    }

    public StudyMemberRole findMyRole(Long studyId, Long userId) {
        return queryFactory
            .select(studyMember.role)
            .from(studyMember)
            .join(study).on(study.id.eq(studyMember.study.id))
            .where(
                studyMember.study.id.eq(studyId),
                studyMember.userId.eq(userId),
                studyMember.activated.isTrue(),
                study.activated.isTrue()
            )
            .fetchOne();
    }

    public List<StudyMemberResponse> findStudyMembers(Long studyId) {
        return queryFactory
            .select(Projections.constructor(
                StudyMemberResponse.class,
                user.id,
                user.nickname,
                user.profileImageUrl
            ))
            .from(studyMember)
            .join(study).on(study.id.eq(studyMember.study.id))
            .join(user).on(user.id.eq(studyMember.userId))
            .where(
                studyMember.study.id.eq(studyId),
                studyMember.activated.isTrue(),
                study.activated.isTrue(),
                user.activated.isTrue()
            )
            .fetch();
    }
}
