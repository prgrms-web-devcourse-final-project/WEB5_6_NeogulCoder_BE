package grep.neogul_coder.domain.recruitment.post.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import grep.neogul_coder.domain.recruitment.post.RecruitmentPost;
import grep.neogul_coder.domain.recruitment.post.controller.dto.response.QRecruitmentPostWithStudyInfo;
import grep.neogul_coder.domain.recruitment.post.controller.dto.response.RecruitmentPostWithStudyInfo;
import grep.neogul_coder.domain.study.enums.Category;
import grep.neogul_coder.domain.study.enums.StudyType;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static grep.neogul_coder.domain.recruitment.post.QRecruitmentPost.recruitmentPost;
import static grep.neogul_coder.domain.study.QStudy.study;
import static grep.neogul_coder.domain.users.entity.QUser.user;

@Repository
public class RecruitmentPostQueryRepository {

    private final EntityManager em;
    private JPAQueryFactory queryFactory;

    public RecruitmentPostQueryRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public RecruitmentPostWithStudyInfo findPostWithStudyInfo(Long recruitmentPostId) {
        return queryFactory.select(
                        new QRecruitmentPostWithStudyInfo(
                                user.nickname,
                                recruitmentPost.id,
                                recruitmentPost.subject,
                                recruitmentPost.content,
                                recruitmentPost.recruitmentCount,
                                recruitmentPost.status,
                                recruitmentPost.createdDate,
                                recruitmentPost.expiredDate,
                                study.category,
                                study.location,
                                study.studyType,
                                study.startDate,
                                study.endDate
                        )
                )
                .from(recruitmentPost)
                .join(study).on(recruitmentPost.studyId.eq(study.id))
                .join(user).on(recruitmentPost.userId.eq(user.id))
                .where(
                        recruitmentPost.id.eq(recruitmentPostId),
                        recruitmentPost.activated.isTrue()
                ).fetchOne();
    }

    public Page<RecruitmentPost> findAllByFilter(Pageable pageable, Category category, StudyType studyType, String keyword) {
        List<RecruitmentPost> content = queryFactory.select(recruitmentPost)
                .from(recruitmentPost)
                .join(study).on(recruitmentPost.studyId.eq(study.id))
                .where(
                        recruitmentPost.activated.isTrue(),

                        equalsStudyCategory(category),
                        equalsStudyType(studyType),
                        likeContent(keyword)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(recruitmentPost.createdDate.desc())
                .fetch();

        Long count = queryFactory.select(recruitmentPost.count())
                .from(recruitmentPost)
                .join(study).on(recruitmentPost.studyId.eq(study.id))
                .where(
                        recruitmentPost.activated.isTrue(),

                        equalsStudyCategory(category),
                        equalsStudyType(studyType),
                        likeContent(keyword)
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, count == null ? 0 : count);
    }

    public Page<RecruitmentPost> findAllByFilter(Pageable pageable, Category category, StudyType studyType, String keyword, Long userId) {
        List<RecruitmentPost> content = queryFactory.select(recruitmentPost)
                .from(recruitmentPost)
                .join(study).on(recruitmentPost.studyId.eq(study.id))
                .where(
                        recruitmentPost.userId.eq(userId),
                        recruitmentPost.activated.isTrue(),

                        equalsStudyCategory(category),
                        equalsStudyType(studyType),
                        likeContent(keyword)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(recruitmentPost.createdDate.desc())
                .fetch();


        Long count = queryFactory.select(recruitmentPost.count())
                .from(recruitmentPost)
                .join(study).on(recruitmentPost.studyId.eq(study.id))
                .where(
                        recruitmentPost.userId.eq(userId),
                        recruitmentPost.activated.isTrue(),

                        equalsStudyCategory(category),
                        equalsStudyType(studyType),
                        likeContent(keyword)
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, count == null ? 0 : count);
    }

    public Optional<RecruitmentPost> findMyPostBy(long postId, long userId) {
        RecruitmentPost findRecruitmentPost = queryFactory.selectFrom(recruitmentPost)
                .where(
                        recruitmentPost.activated.isTrue(),
                        recruitmentPost.userId.eq(userId),
                        recruitmentPost.id.eq(postId)
                )
                .fetchOne();
        return Optional.ofNullable(findRecruitmentPost);
    }

    public Optional<RecruitmentPost> findPostBy(long postId) {
        RecruitmentPost findRecruitmentPost = queryFactory.selectFrom(recruitmentPost)
                .where(
                        recruitmentPost.activated.isTrue(),
                        recruitmentPost.id.eq(postId)
                )
                .fetchOne();
        return Optional.ofNullable(findRecruitmentPost);
    }

    private BooleanBuilder equalsStudyType(StudyType studyType) {
        return nullSafeBuilder(() -> study.studyType.eq(studyType));
    }

    private BooleanBuilder equalsStudyCategory(Category category) {
        return nullSafeBuilder(() -> study.category.eq(category));
    }

    private BooleanBuilder likeContent(String content) {
        return nullSafeBuilder(() -> recruitmentPost.content.contains(content));
    }

    private BooleanBuilder nullSafeBuilder(Supplier<BooleanExpression> supplier) {
        try {
            return new BooleanBuilder(supplier.get());
        } catch (Exception e) {
            return new BooleanBuilder();
        }
    }
}
