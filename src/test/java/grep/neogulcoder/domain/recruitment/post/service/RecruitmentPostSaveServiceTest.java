package grep.neogulcoder.domain.recruitment.post.service;

import grep.neogulcoder.domain.IntegrationTestSupport;
import grep.neogulcoder.domain.recruitment.post.RecruitmentPost;
import grep.neogulcoder.domain.recruitment.post.controller.dto.response.save.JoinedStudyLoadInfo;
import grep.neogulcoder.domain.recruitment.post.controller.dto.response.save.JoinedStudiesInfo;
import grep.neogulcoder.domain.recruitment.post.repository.RecruitmentPostRepository;
import grep.neogulcoder.domain.recruitment.post.service.request.RecruitmentPostCreateServiceRequest;
import grep.neogulcoder.domain.study.Study;
import grep.neogulcoder.domain.study.StudyMember;
import grep.neogulcoder.domain.study.enums.Category;
import grep.neogulcoder.domain.study.enums.StudyMemberRole;
import grep.neogulcoder.domain.study.enums.StudyType;
import grep.neogulcoder.domain.study.repository.StudyMemberRepository;
import grep.neogulcoder.domain.study.repository.StudyRepository;
import grep.neogulcoder.domain.users.entity.User;
import grep.neogulcoder.domain.users.repository.UserRepository;
import grep.neogulcoder.global.exception.business.BusinessException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

import static grep.neogulcoder.domain.study.enums.StudyMemberRole.LEADER;
import static grep.neogulcoder.domain.study.enums.StudyMemberRole.MEMBER;
import static grep.neogulcoder.domain.study.enums.StudyType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RecruitmentPostSaveServiceTest extends IntegrationTestSupport {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private StudyMemberRepository studyMemberRepository;

    @Autowired
    private RecruitmentPostSaveService recruitmentPostService;

    @Autowired
    private RecruitmentPostRepository recruitmentPostRepository;

    private long userId;
    private long recruitmentPostId;

    @BeforeEach
    void init() {
        User user = userRepository.save(createUser("테스터"));
        userId = user.getId();

        RecruitmentPost recruitmentPost = createRecruitmentPost(userId, "제목", "내용", 1);
        recruitmentPostRepository.save(recruitmentPost);
        recruitmentPostId = recruitmentPost.getId();
    }

    @DisplayName("스터디 모집글을 작성 합니다.")
    @TestFactory
    Collection<DynamicTest> create() {

        Study study = createStudy("자바 스터디", Category.IT);
        studyRepository.save(study);

        return List.of(
                DynamicTest.dynamicTest("스터디의 리더는 모집글을 작성할 수 있습니다.", () -> {
                    //given
                    studyMemberRepository.save(createStudyMember(study, userId, LEADER));

                    RecruitmentPostCreateServiceRequest request =
                            createRecruitmentPostServiceRequest(study.getId(), "제목", "내용");

                    //when
                    long recruitmentPostId = recruitmentPostService.create(request, userId);

                    //then
                    RecruitmentPost findRecruitmentPost = recruitmentPostRepository.findById(recruitmentPostId).orElseThrow();
                    assertThat(findRecruitmentPost)
                            .extracting("userId", "subject", "content")
                            .containsExactly(userId, "제목", "내용");
                }),

                DynamicTest.dynamicTest("스터디의 리더가 아닌 회원은 모집글을 작성할 수 없습니다.", () -> {
                    //given
                    User member = createUser("맴버");
                    userRepository.save(member);

                    studyMemberRepository.save(createStudyMember(study, member.getId(), MEMBER));

                    RecruitmentPostCreateServiceRequest request =
                            createRecruitmentPostServiceRequest(study.getId(), "제목", "내용");

                    //when //then
                    assertThatThrownBy(() -> recruitmentPostService.create(request, member.getId()))
                            .isInstanceOf(BusinessException.class).hasMessage("스터디의 리더가 아닙니다.");
                })
        );
    }

    @DisplayName("참여 중인 스터디의 정보를 조회 합니다.")
    @Test
    void getJoinedStudyInfo() {
        //given
        Study study1 = createStudy("자바 스터디", Category.IT);
        Study study2 = createStudy("클라이밍 동아리", Category.HOBBY);
        Study study3 = createStudy("책 읽기", Category.ETC);
        studyRepository.saveAll(List.of(study1, study2, study3));

        List<StudyMember> studyMembers = List.of(
                createStudyMember(study1, userId, MEMBER),
                createStudyMember(study2, userId, MEMBER),
                createStudyMember(study3, userId, MEMBER)
        );
        studyMemberRepository.saveAll(studyMembers);

        //when
        JoinedStudiesInfo result = recruitmentPostService.getJoinedStudyInfo(userId);
        System.out.println("result = " + result);
        //then
        assertThat(result.getStudyInfos()).hasSize(3)
                .extracting("name")
                .containsExactlyInAnyOrder("자바 스터디", "클라이밍 동아리", "책 읽기");
    }

    @DisplayName("스터디 정보를 불러옵니다.")
    @Test
    void getJoinedStudyLoadInfo() {
        //given
        User user1 = createUser("테스터1");
        User user2 = createUser("테스터2");
        userRepository.saveAll(List.of(user1, user2));

        Study study = createStudy("자바 스터디", Category.IT, ONLINE, 5);
        studyRepository.save(study);

        List<StudyMember> studyMembers = List.of(
                createStudyMember(study, user1.getId(), MEMBER),
                createStudyMember(study, user2.getId(), MEMBER)
        );
        studyMemberRepository.saveAll(studyMembers);

        //when
        JoinedStudyLoadInfo result = recruitmentPostService.getJoinedStudyLoadInfo(study.getId(), user1.getId());

        //then
        assertThat(result)
                .extracting("category", "studyType", "remainSlots")
                .containsExactlyInAnyOrder("IT", "ONLINE", 3L);
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

    private Study createStudy(String name, Category category, StudyType studyType, int capacity) {
        return Study.builder()
                .name(name)
                .category(category)
                .studyType(studyType)
                .capacity(capacity)
                .build();
    }

    private StudyMember createStudyMember(Study study, long userId, StudyMemberRole role) {
        return StudyMember.builder()
                .study(study)
                .userId(userId)
                .role(role)
                .build();
    }

    private RecruitmentPost createRecruitmentPost(long userId, String subject, String content, int count) {
        return RecruitmentPost.builder()
                .subject(subject)
                .content(content)
                .recruitmentCount(count)
                .userId(userId)
                .build();
    }

    private RecruitmentPostCreateServiceRequest createRecruitmentPostServiceRequest(long studyId, String subject, String content) {
        return RecruitmentPostCreateServiceRequest.builder()
                .studyId(studyId)
                .subject(subject)
                .content(content)
                .build();
    }
}