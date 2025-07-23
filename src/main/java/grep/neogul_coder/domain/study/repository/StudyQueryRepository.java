package grep.neogul_coder.domain.study.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import grep.neogul_coder.domain.study.Study;
import grep.neogul_coder.domain.study.controller.dto.response.QStudyItemResponse;
import grep.neogul_coder.domain.study.controller.dto.response.StudyItemResponse;
import grep.neogul_coder.domain.study.controller.dto.response.StudyMemberResponse;
import grep.neogul_coder.domain.study.enums.Category;
import grep.neogul_coder.domain.study.enums.StudyMemberRole;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Supplier;

import static grep.neogul_coder.domain.study.QStudy.study;
import static grep.neogul_coder.domain.study.QStudyMember.studyMember;
import static grep.neogul_coder.domain.users.entity.QUser.user;

@Repository
public class StudyQueryRepository {

    private final JPAQueryFactory queryFactory;

    public StudyQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Page<StudyItemResponse> findMyStudiesPaging(Pageable pageable, Long userId, Boolean finished) {

        List<StudyItemResponse> studies = queryFactory
            .select(new QStudyItemResponse(
                study.id,
                study.name,
                user.nickname,
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
            .join(user).on(user.id.eq(studyMember.userId))
            .join(study).on(study.id.eq(studyMember.study.id))
            .where(
                studyMember.userId.eq(userId),
                studyMember.activated.isTrue(),
                equalsStudyFinished(finished)
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Long total = queryFactory
            .select(study.count())
            .from(studyMember)
            .where(
                studyMember.userId.eq(userId),
                studyMember.activated.eq(true),
                equalsStudyFinished(finished)
            )
            .fetchOne();

        return new PageImpl<>(studies, pageable, total);
    }

    public List<StudyItemResponse> findMyStudies(Long userId) {
        return queryFactory
            .select(new QStudyItemResponse(
                study.id,
                study.name,
                user.nickname,
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
