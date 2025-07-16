package grep.neogul_coder.domain.study.repository;

import grep.neogul_coder.domain.IntegrationTestSupport;
import grep.neogul_coder.domain.study.Study;
import grep.neogul_coder.domain.study.StudyMember;
import grep.neogul_coder.domain.study.enums.Category;
import grep.neogul_coder.domain.study.enums.StudyMemberRole;
import grep.neogul_coder.domain.users.entity.User;
import grep.neogul_coder.domain.users.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static grep.neogul_coder.domain.study.enums.StudyMemberRole.MEMBER;
import static org.assertj.core.api.Assertions.assertThat;

class StudyMemberQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private StudyMemberRepository studyMemberRepository;

    @Autowired
    private StudyMemberQueryRepository studyMemberQueryRepository;

    @DisplayName("자신이 참여한 스터디의 정보를 조회 합니다.")
    @Test
    void findAllFetchStudyByUserId() {
        //given
        User user = createUser("테스터");
        userRepository.save(user);

        Study study1 = createStudy("자바 스터디", Category.IT);
        Study study2 = createStudy("운영체제 스터디", Category.IT);
        studyRepository.saveAll(List.of(study1, study2));

        StudyMember studyMember1 = createStudyMember(study1, user.getId(), MEMBER);
        StudyMember studyMember2 = createStudyMember(study2, user.getId(), MEMBER);
        studyMemberRepository.saveAll(List.of(studyMember1, studyMember2));

        //when
        List<StudyMember> studyMembers = studyMemberQueryRepository.findAllFetchStudyByUserId(user.getId());

        //then
        assertThat(studyMembers).hasSize(2)
                .extracting(studyMember -> studyMember.getStudy().getName())
                .containsExactly("자바 스터디", "운영체제 스터디");
    }

    @DisplayName("스터디에 참여중인 회원수를 조회 합니다.")
    @Test
    void findCurrentCountBy() {
        //given
        User user1 = createUser("테스터1");
        User user2 = createUser("테스터2");
        userRepository.saveAll(List.of(user1, user2));

        Study study = createStudy("자바 스터디", Category.IT);
        studyRepository.save(study);

        List<StudyMember> studyMembers = List.of(
                createStudyMember(study, user1.getId(), MEMBER),
                createStudyMember(study, user2.getId(), MEMBER)
        );
        studyMemberRepository.saveAll(studyMembers);

        //when
        long currentCount = studyMemberQueryRepository.findCurrentCountBy(study.getId());

        //then
        assertThat(currentCount).isEqualTo(2);
    }

    private User createUser(String nickname) {
        return User.builder()
                .nickname(nickname)
                .build();
    }

    private Study createStudy(String name, Category category) {
        return Study.builder()
                .name(name)
                .category(category)
                .build();
    }

    private StudyMember createStudyMember(Study study, long userId, StudyMemberRole role) {
        return StudyMember.builder()
                .study(study)
                .userId(userId)
                .role(role)
                .build();
    }

}