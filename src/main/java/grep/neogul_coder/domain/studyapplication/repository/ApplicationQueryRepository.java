package grep.neogul_coder.domain.studyapplication.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import grep.neogul_coder.domain.studyapplication.controller.dto.response.MyApplicationResponse;
import grep.neogul_coder.domain.studyapplication.controller.dto.response.QMyApplicationResponse;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static grep.neogul_coder.domain.recruitment.post.QRecruitmentPost.recruitmentPost;
import static grep.neogul_coder.domain.study.QStudy.study;
import static grep.neogul_coder.domain.study.QStudyMember.studyMember;
import static grep.neogul_coder.domain.study.enums.StudyMemberRole.LEADER;
import static grep.neogul_coder.domain.studyapplication.QStudyApplication.studyApplication;
import static grep.neogul_coder.domain.users.entity.QUser.user;

@Repository
public class ApplicationQueryRepository {

    private final JPAQueryFactory queryFactory;

    public ApplicationQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Page<MyApplicationResponse> findMyStudyApplicationsPaging(Pageable pageable, Long userId) {
        List<MyApplicationResponse> applications = queryFactory
            .select(new QMyApplicationResponse(
                studyApplication.id,
                study.name,
                user.nickname,
                study.capacity,
                study.currentCount,
                study.startDate,
                study.imageUrl,
                study.introduction,
                study.category,
                study.studyType,
                studyApplication.isRead,
                studyApplication.status
            ))
            .from(studyApplication)
            .join(recruitmentPost).on(recruitmentPost.id.eq(studyApplication.recruitmentPostId))
            .join(study).on(study.id.eq(recruitmentPost.studyId))
            .join(studyMember).on(studyMember.study.id.eq(study.id), studyMember.role.eq(LEADER))
            .join(user).on(user.id.eq(studyMember.userId))
            .where(studyApplication.userId.eq(userId))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Long total = queryFactory
            .select(studyApplication.count())
            .from(studyApplication)
            .join(recruitmentPost).on(recruitmentPost.id.eq(studyApplication.recruitmentPostId))
            .join(study).on(study.id.eq(recruitmentPost.studyId))
            .join(studyMember).on(studyMember.study.id.eq(study.id), studyMember.role.eq(LEADER))
            .where(studyApplication.userId.eq(userId))
            .fetchOne();

        return new PageImpl<>(applications, pageable, total == null ? 0 : total);
    }
}
