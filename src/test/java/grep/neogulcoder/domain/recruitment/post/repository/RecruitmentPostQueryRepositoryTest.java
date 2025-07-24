package grep.neogulcoder.domain.recruitment.post.repository;

import grep.neogulcoder.domain.IntegrationTestSupport;
import grep.neogulcoder.domain.recruitment.RecruitmentPostStatus;
import grep.neogulcoder.domain.recruitment.post.RecruitmentPost;
import grep.neogulcoder.domain.recruitment.post.controller.dto.response.RecruitmentPostWithStudyInfo;
import grep.neogulcoder.domain.study.Study;
import grep.neogulcoder.domain.study.enums.Category;
import grep.neogulcoder.domain.study.enums.StudyType;
import grep.neogulcoder.domain.study.repository.StudyRepository;
import grep.neogulcoder.domain.users.entity.User;
import grep.neogulcoder.domain.users.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static grep.neogulcoder.domain.recruitment.RecruitmentPostStatus.IN_PROGRESS;
import static org.assertj.core.api.Assertions.assertThat;

class RecruitmentPostQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private RecruitmentPostRepository recruitmentPostRepository;

    @Autowired
    private RecruitmentPostQueryRepository postQueryRepository;

    @DisplayName("모집글과 연관된 스터디 정보를 같이 조회 합니다.")
    @Test
    void findPostWithStudyInfo() {
        //given
        User user = createUser("테스터");
        userRepository.save(user);

        Study study = createStudy("자바 스터디", Category.IT, StudyType.ONLINE);
        studyRepository.save(study);

        RecruitmentPost recruitmentPost = createRecruitmentPost(study.getId(), user.getId(), "제목", "내용", IN_PROGRESS);
        recruitmentPostRepository.save(recruitmentPost);

        //when
        RecruitmentPostWithStudyInfo response = postQueryRepository.findPostWithStudyInfo(recruitmentPost.getId());

        //then
        assertThat(response)
                .extracting("nickname", "subject", "content", "category", "studyType")
                .containsExactlyInAnyOrder("테스터", "제목", "내용", "IT", "ONLINE");
    }

    private User createUser(String nickname) {
        return User.builder()
                .nickname(nickname)
                .password("tempPassword")
                .build();
    }

    private Study createStudy(String name, Category category, StudyType studyType) {
        return Study.builder()
                .name(name)
                .category(category)
                .studyType(studyType)
                .build();
    }

    private RecruitmentPost createRecruitmentPost(long studyId, long userId, String subject, String content) {
        return RecruitmentPost.builder()
                .studyId(studyId)
                .userId(userId)
                .subject(subject)
                .content(content)
                .build();
    }

    private RecruitmentPost createRecruitmentPost(long studyId, long userId, String subject, String content, RecruitmentPostStatus status) {
        return RecruitmentPost.builder()
                .studyId(studyId)
                .userId(userId)
                .subject(subject)
                .content(content)
                .status(status)
                .build();
    }

}