package grep.neogulcoder.domain.studypost.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import grep.neogulcoder.domain.studypost.Category;
import grep.neogulcoder.domain.studypost.QStudyPost;
import grep.neogulcoder.domain.studypost.StudyPost;
import grep.neogulcoder.domain.studypost.controller.dto.response.*;
import grep.neogulcoder.global.exception.validation.ValidationException;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import static grep.neogulcoder.domain.studypost.Category.FREE;
import static grep.neogulcoder.domain.studypost.Category.NOTICE;
import static grep.neogulcoder.domain.studypost.StudyPostErrorCode.NOT_VALID_CONDITION;
import static grep.neogulcoder.domain.studypost.comment.QStudyPostComment.studyPostComment;
import static grep.neogulcoder.domain.users.entity.QUser.user;

@Repository
public class StudyPostQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public static final int NOTICE_POST_LIMIT = 2;
    public static final int FREE_POST_LIMIT = 3;

    private final QStudyPost studyPost = QStudyPost.studyPost;

    public StudyPostQueryRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public PostInfo findPostWriterInfo(Long postId) {
        return queryFactory.select(
                        new QPostInfo(
                                user.id,
                                user.nickname,
                                user.profileImageUrl,
                                studyPost.id.as("postId"),
                                studyPost.title,
                                studyPost.category,
                                studyPost.content,
                                studyPost.createdDate
                        )
                )
                .from(studyPost)
                .join(user).on(studyPost.userId.eq(user.id))
                .where(
                        studyPost.id.eq(postId),
                        studyPost.activated.isTrue()
                )
                .fetchOne();
    }

    public Optional<StudyPost> findByIdAndUserId(Long postId, long userId) {
        StudyPost findStudyPost = queryFactory.selectFrom(studyPost)
                .where(
                        studyPost.id.eq(postId),
                        studyPost.userId.eq(userId),
                        studyPost.activated.isTrue()
                )
                .fetchOne();

        return Optional.ofNullable(findStudyPost);
    }

    public Page<PostPagingInfo> findPagingFilteredBy(Long studyId, Pageable pageable, Category category, String keyword, Long userId) {

        JPAQuery<PostPagingInfo> query = queryFactory.select(
                        new QPostPagingInfo(
                                studyPost.id,
                                studyPost.title,
                                studyPost.category,
                                studyPost.content,
                                studyPost.createdDate,
                                studyPostComment.countDistinct()
                        )
                )
                .from(studyPost)
                .leftJoin(studyPostComment).on(studyPost.id.eq(studyPostComment.postId))
                .where(
                        studyPost.activated.isTrue(),
                        studyPostComment.activated.isTrue(),
                        studyPost.study.id.eq(studyId),

                        equalsUserId(userId),
                        likeContent(keyword),
                        equalsCategory(category)
                )
                .groupBy(studyPost.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        pageable.getSort().stream()
                .map(order -> resolveOrderSpecifier(order.getProperty(), order.isAscending()))
                .filter(Objects::nonNull)
                .forEach(query::orderBy);

        Long count = queryFactory.select(studyPost.count())
                .from(studyPost)
                .where(
                        studyPost.activated.isTrue(),
                        studyPost.study.id.eq(studyId),
                        likeContent(keyword),
                        equalsCategory(category)
                )
                .fetchOne();

        return new PageImpl<>(query.fetch(), pageable, count == null ? 0 : count);
    }

    public List<NoticePostInfo> findLatestNoticeInfoBy(Long studyId) {
        return queryFactory.select(
                        new QNoticePostInfo(
                                studyPost.id,
                                studyPost.category,
                                studyPost.title,
                                studyPost.createdDate
                        )
                )
                .from(studyPost)
                .where(
                        studyPost.activated.isTrue(),
                        studyPost.study.id.eq(studyId),
                        studyPost.category.eq(NOTICE)
                )
                .orderBy(studyPost.createdDate.desc())
                .limit(NOTICE_POST_LIMIT)
                .fetch();
    }

    public List<FreePostInfo> findLatestFreeInfoBy(Long studyId) {
        return queryFactory.select(
                        new QFreePostInfo(
                                studyPost.id,
                                studyPost.category,
                                studyPost.title,
                                studyPost.createdDate
                        )
                )
                .from(studyPost)
                .where(
                        studyPost.activated.isTrue(),
                        studyPost.study.id.eq(studyId),
                        studyPost.category.eq(FREE)
                )
                .orderBy(studyPost.createdDate.desc())
                .limit(FREE_POST_LIMIT)
                .fetch();
    }

    private OrderSpecifier<?> resolveOrderSpecifier(String attributeName, Boolean isAsc) {
        if (attributeName == null || isAsc == null) {
            return null;
        }

        if (attributeName.equalsIgnoreCase("commentCount")) {
            NumberExpression<Long> commentCount = studyPostComment.id.countDistinct();
            return isAsc ? commentCount.asc() : commentCount.desc();
        }

        if (attributeName.equalsIgnoreCase("createDateTime")) {
            return isAsc ? studyPost.createdDate.asc() : studyPost.createdDate.desc();
        }

        throw new ValidationException(NOT_VALID_CONDITION);
    }

    private BooleanBuilder equalsUserId(Long userId) {
        return nullSafeBuilder(() -> studyPost.userId.eq(userId));
    }

    private BooleanBuilder likeContent(String content) {
        return nullSafeBuilder(() -> studyPost.content.contains(content).or(studyPost.title.contains(content)));
    }

    private BooleanBuilder equalsCategory(Category category) {
        return nullSafeBuilder(() -> studyPost.category.eq(category));
    }

    private BooleanBuilder nullSafeBuilder(Supplier<BooleanExpression> supplier) {
        try {
            return new BooleanBuilder(supplier.get());
        } catch (Exception e) {
            return new BooleanBuilder();
        }
    }
}
