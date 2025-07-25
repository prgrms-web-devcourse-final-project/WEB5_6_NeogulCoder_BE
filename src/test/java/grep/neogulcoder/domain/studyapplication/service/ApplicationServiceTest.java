package grep.neogulcoder.domain.studyapplication.service;

import grep.neogulcoder.domain.IntegrationTestSupport;
import grep.neogulcoder.domain.recruitment.post.RecruitmentPost;
import grep.neogulcoder.domain.recruitment.post.repository.RecruitmentPostRepository;
import grep.neogulcoder.domain.study.Study;
import grep.neogulcoder.domain.study.StudyMember;
import grep.neogulcoder.domain.study.repository.StudyMemberRepository;
import grep.neogulcoder.domain.study.repository.StudyRepository;
import grep.neogulcoder.domain.studyapplication.StudyApplication;
import grep.neogulcoder.domain.studyapplication.controller.dto.request.ApplicationCreateRequest;
import grep.neogulcoder.domain.studyapplication.repository.ApplicationRepository;
import grep.neogulcoder.domain.users.entity.User;
import grep.neogulcoder.domain.users.repository.UserRepository;
import grep.neogulcoder.global.exception.business.BusinessException;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static grep.neogulcoder.domain.study.enums.StudyMemberRole.*;
import static grep.neogulcoder.domain.studyapplication.ApplicationStatus.*;
import static grep.neogulcoder.domain.studyapplication.exception.code.ApplicationErrorCode.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ApplicationServiceTest extends IntegrationTestSupport {

    @Autowired
    private EntityManager em;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private StudyMemberRepository studyMemberRepository;

    @Autowired
    private RecruitmentPostRepository recruitmentPostRepository;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ApplicationRepository applicationRepository;

    private Long userId1;
    private Long userId2;
    private Long studyId;
    private Long recruitmentPostId;

    @BeforeEach
    void init() {
        User user1 = createUser("test1");
        User user2 = createUser("test2");
        userRepository.saveAll(List.of(user1, user2));
        userId1 = user1.getId();
        userId2 = user2.getId();

        Study study = createStudy("스터디", LocalDateTime.parse("2025-07-25T20:20:20"), LocalDateTime.parse("2025-07-28T20:20:20"));
        studyRepository.save(study);
        studyId = study.getId();

        StudyMember studyLeader = createStudyLeader(study, userId1);
        StudyMember studyMember = createStudyMember(study, userId2);
        studyMemberRepository.saveAll(List.of(studyLeader, studyMember));

        RecruitmentPost recruitmentPost = createRecruitmentPost(studyId, userId1, "제목", "내용", 1);
        recruitmentPostRepository.save(recruitmentPost);
        recruitmentPostId = recruitmentPost.getId();
    }

    @DisplayName("신청서를 생성합니다.")
    @Test
    void createApplication() {
        // given
        ApplicationCreateRequest request = ApplicationCreateRequest.builder()
            .applicationReason("자바를 더 공부하고 싶어 지원합니다.")
            .build();

        // when
        Long id = applicationService.createApplication(recruitmentPostId, request, userId2);
        em.flush();
        em.clear();

        // then
        StudyApplication application = applicationRepository.findByIdAndActivatedTrue(id).orElseThrow();
        assertThat(application.getApplicationReason()).isEqualTo("자바를 더 공부하고 싶어 지원합니다.");
    }

    @DisplayName("종료되지 않은 스터디를 10개를 진행중일 때 신청서 생성 시 예외가 발생합니다.")
    @Test
    void createApplicationFail() {
        // given
        ApplicationCreateRequest request = ApplicationCreateRequest.builder()
            .applicationReason("자바를 더 공부하고 싶어 지원합니다.")
            .build();

        for (int i = 0; i < 9; i++) {
            Study study = createStudy("스터디", LocalDateTime.parse("2025-07-25T20:20:20"), LocalDateTime.parse("2026-07-28T20:20:20"));
            studyRepository.save(study);
            StudyMember studyLeader = createStudyLeader(study, userId2);
            studyMemberRepository.save(studyLeader);
            em.flush();
            em.clear();
        }

        // when then
        assertThatThrownBy(() ->
            applicationService.createApplication(recruitmentPostId, request, userId2))
            .isInstanceOf(BusinessException.class).hasMessage(APPLICATION_PARTICIPATION_LIMIT_EXCEEDED.getMessage());
    }

    @DisplayName("스터디장이 신청서를 승인합니다.")
    @Test
    void approveApplication() {
        // given
        StudyApplication application = createApplication(recruitmentPostId, userId2);
        applicationRepository.save(application);
        Long id = application.getId();

        // when
        applicationService.approveApplication(id, userId1);
        em.flush();
        em.clear();

        // then
        assertThat(application.getStatus()).isEqualTo(APPROVED);
    }

    @DisplayName("참여 중인 스터디가 10개인 사용자의 신청서 승인 시 예외가 발생합니다.")
    @Test
    void approveApplicationFail() {
        // given
        StudyApplication application = createApplication(recruitmentPostId, userId2);
        applicationRepository.save(application);
        Long id = application.getId();

        for (int i = 0; i < 9; i++) {
            Study study = createStudy("스터디", LocalDateTime.parse("2025-07-25T20:20:20"), LocalDateTime.parse("2026-07-28T20:20:20"));
            studyRepository.save(study);
            StudyMember studyLeader = createStudyLeader(study, userId2);
            studyMemberRepository.save(studyLeader);
            em.flush();
            em.clear();
        }

        // when then
        assertThatThrownBy(() ->
            applicationService.approveApplication(id, userId1))
            .isInstanceOf(BusinessException.class).hasMessage(APPLICATION_PARTICIPANT_LIMIT_EXCEEDED.getMessage());
    }

    @DisplayName("스터디장이 신청서를 거절합니다.")
    @Test
    void rejectApplication() {
        // given
        StudyApplication application = createApplication(recruitmentPostId, userId2);
        applicationRepository.save(application);
        Long id = application.getId();

        // when
        applicationService.rejectApplication(id, userId1);
        em.flush();
        em.clear();

        // then
        assertThat(application.getStatus()).isEqualTo(REJECTED);
    }

    @DisplayName("스터디장이 이미 승인이나 거절한 경우 신청서 거절 시 예외가 발생합니다.")
    @Test
    void rejectApplicationFail() {
        // given
        StudyApplication application = createApplication(recruitmentPostId, userId2);
        applicationRepository.save(application);
        Long id = application.getId();
        applicationService.rejectApplication(id, userId1);

        // when then
        assertThatThrownBy(() ->
            applicationService.rejectApplication(id, userId1))
            .isInstanceOf(BusinessException.class).hasMessage(APPLICATION_NOT_APPLYING.getMessage());
    }

    private static User createUser(String nickname) {
        return User.builder()
            .nickname(nickname)
            .build();
    }

    private static Study createStudy(String name, LocalDateTime startDate, LocalDateTime endDate) {
        return Study.builder()
            .name(name)
            .startDate(startDate)
            .endDate(endDate)
            .build();
    }

    private StudyMember createStudyLeader(Study study, Long userId) {
        return StudyMember.builder()
            .study(study)
            .userId(userId)
            .role(LEADER)
            .build();
    }

    private StudyMember createStudyMember(Study study, Long userId) {
        return StudyMember.builder()
            .study(study)
            .userId(userId)
            .role(MEMBER)
            .build();
    }

    private RecruitmentPost createRecruitmentPost(Long studyId ,Long userId, String subject, String content, int count) {
        return RecruitmentPost.builder()
            .subject(subject)
            .content(content)
            .recruitmentCount(count)
            .studyId(studyId)
            .userId(userId)
            .build();
    }

    private StudyApplication createApplication(Long recruitmentPostId, Long userId) {
        return StudyApplication.builder()
            .recruitmentPostId(recruitmentPostId)
            .userId(userId)
            .applicationReason("자바를 더 공부하고 싶어 지원합니다.")
            .status(APPLYING)
            .build();
    }
}