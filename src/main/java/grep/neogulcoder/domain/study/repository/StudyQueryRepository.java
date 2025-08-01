package grep.neogulcoder.domain.study.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import grep.neogulcoder.domain.study.QStudyMember;
import grep.neogulcoder.domain.study.Study;
import grep.neogulcoder.domain.study.controller.dto.response.QStudyItemResponse;
import grep.neogulcoder.domain.study.controller.dto.response.StudyItemResponse;
import grep.neogulcoder.domain.study.controller.dto.response.StudyMemberResponse;
import grep.neogulcoder.domain.study.enums.Category;
import grep.neogulcoder.domain.study.enums.StudyMemberRole;
import grep.neogulcoder.domain.users.entity.QUser;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Supplier;

import static grep.neogulcoder.domain.study.QStudy.study;
import static grep.neogulcoder.domain.study.QStudyMember.studyMember;
import static grep.neogulcoder.domain.users.entity.QUser.user;

@Repository
public class StudyQueryRepository {

    private final JPAQueryFactory queryFactory;

    public StudyQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Page<StudyItemResponse> findMyStudiesPaging(Pageable pageable, Long userId, Boolean finished) {

        QStudyMember leaderMember = new QStudyMember("leaderMember");
        QUser leaderUser = new QUser("leaderUser");

        List<StudyItemResponse> studies = queryFactory
            .select(new QStudyItemResponse(
                study.id,
                study.name,
                leaderUser.nickname,
                study.capacity,
                study.currentCount,
                study.startDate,
                study.endDate,
                study.imageUrl,
                study.introduction,
                study.category,
                study.studyType,
                study.finished
            ))
            .from(studyMember)
            .join(study).on(study.id.eq(studyMember.study.id))
            .join(leaderMember).on(
                leaderMember.study.id.eq(study.id),
                leaderMember.role.eq(StudyMemberRole.LEADER),
                leaderMember.activated.isTrue()
            )
            .join(leaderUser).on(leaderUser.id.eq(leaderMember.userId))
            .where(
                studyMember.userId.eq(userId),
                studyMember.activated.isTrue(),
                equalsStudyFinished(finished)
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Long total = queryFactory
            .select(study.id.count())
            .from(studyMember)
            .join(study).on(study.id.eq(studyMember.study.id))
            .where(
                studyMember.userId.eq(userId),
                studyMember.activated.eq(true),
                equalsStudyFinished(finished)
            )
            .fetchOne();

        return new PageImpl<>(studies, pageable, total == null ? 0 : total);
    }

    public List<StudyItemResponse> findMyUnfinishedStudies(Long userId) {

        QStudyMember leaderMember = new QStudyMember("leaderMember");
        QUser leaderUser = new QUser("leaderUser");

        return queryFactory
            .select(new QStudyItemResponse(
                study.id,
                study.name,
                leaderUser.nickname,
                study.capacity,
                study.currentCount,
                study.startDate,
                study.endDate,
                study.imageUrl,
                study.introduction,
                study.category,
                study.studyType,
                study.finished
            ))
            .from(studyMember)
            .join(study).on(study.id.eq(studyMember.study.id))
            .join(leaderMember).on(
                leaderMember.study.id.eq(study.id),
                leaderMember.role.eq(StudyMemberRole.LEADER),
                leaderMember.activated.isTrue()
            )
            .join(leaderUser).on(leaderUser.id.eq(leaderMember.userId))
            .where(
                studyMember.userId.eq(userId),
                studyMember.activated.isTrue(),
                studyMember.study.finished.isFalse()
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
                user.profileImageUrl,
                studyMember.role
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

    public Page<Study> adminSearchStudy(String name, Category category, Pageable pageable) {
        List<Study> content = queryFactory.selectFrom(study)
            .where(
                name != null ? study.name.containsIgnoreCase(name) : null,
                category != null ? study.category.eq(category) : null
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        long total = queryFactory.selectFrom(study)
            .where(
                name != null ? study.name.containsIgnoreCase(name) : null,
                category != null ? study.category.eq(category) : null
            )
            .fetchCount();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanBuilder equalsStudyFinished(Boolean finished) {
        return nullSafeBuilder(() -> study.finished.eq(finished));
    }

    private BooleanBuilder nullSafeBuilder(Supplier<BooleanExpression> supplier) {
        try {
            return new BooleanBuilder(supplier.get());
        } catch (Exception e) {
            return new BooleanBuilder();
        }
    }
}
