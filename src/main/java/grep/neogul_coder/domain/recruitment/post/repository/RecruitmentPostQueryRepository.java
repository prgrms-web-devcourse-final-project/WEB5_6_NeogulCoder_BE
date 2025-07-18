package grep.neogul_coder.domain.recruitment.post.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import grep.neogul_coder.domain.recruitment.post.RecruitmentPost;
import grep.neogul_coder.domain.recruitment.post.controller.dto.response.QRecruitmentPostWithStudyInfo;
import grep.neogul_coder.domain.recruitment.post.controller.dto.response.RecruitmentPostWithStudyInfo;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public List<RecruitmentPost> findPaging(Pageable pageable) {
        return queryFactory.selectFrom(recruitmentPost)
                .where(recruitmentPost.activated.isTrue())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(recruitmentPost.createdDate.desc())
                .fetch();
    }
}
