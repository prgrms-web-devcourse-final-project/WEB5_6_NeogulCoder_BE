package grep.neogulcoder.domain.study.service;

import grep.neogulcoder.domain.IntegrationTestSupport;
import grep.neogulcoder.domain.study.Study;
import grep.neogulcoder.domain.study.StudyMember;
import grep.neogulcoder.domain.study.repository.StudyMemberRepository;
import grep.neogulcoder.domain.study.repository.StudyRepository;
import grep.neogulcoder.domain.users.entity.User;
import grep.neogulcoder.domain.users.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static grep.neogulcoder.domain.study.enums.StudyMemberRole.LEADER;
import static grep.neogulcoder.domain.study.enums.StudyMemberRole.MEMBER;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class StudyManagementServiceTest extends IntegrationTestSupport {

    @Autowired
    private EntityManager em;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private StudyMemberRepository studyMemberRepository;

    @Autowired
    private StudyManagementServiceFacade studyManagementServiceFacade;

    private Long studyId;

    @BeforeEach
    void init() {
        User user1 = createUser("test1");
        User user2 = createUser("test2");
        User user3 = createUser("test3");
        userRepository.saveAll(List.of(user1, user2, user3));

        Study study = createStudy("스터디", LocalDateTime.parse("2025-09-05T20:20:20"), LocalDateTime.parse("2025-09-28T20:20:20"));
        studyRepository.save(study);
        studyId = study.getId();

        StudyMember member1 = createStudyLeader(study, user1.getId());
        StudyMember member2 = createStudyMember(study, user2.getId());
        study.increaseMemberCount();
        StudyMember member3 = createStudyMember(study, user3.getId());
        study.increaseMemberCount();

        studyRepository.save(study);
        studyMemberRepository.saveAll(List.of(member1, member2, member3));
    }

    @Disabled
    @DisplayName("2명의 멤버가 스터디를 동시에 탈퇴합니다.")
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Test
    void leaveStudy() throws InterruptedException {
        // given
        Study study = studyRepository.findById(studyId)
            .orElseThrow();
        System.out.println(study.getCurrentCount());

        int threads = 2;
        ExecutorService executorService = Executors.newFixedThreadPool(threads);
        CountDownLatch countDownLatch = new CountDownLatch(threads);

        List<Long> memberIds = studyMemberRepository.findFetchStudyByStudyId(studyId)
            .stream().filter(studyMember -> studyMember.getRole().equals(MEMBER))
            .map(StudyMember::getUserId)
            .toList();

        // when
        for (Long targetUserId : memberIds) {
            executorService.submit(() -> {
                try {
                    studyManagementServiceFacade.leaveStudyWithRetry(studyId, targetUserId);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();
        executorService.shutdown();
        em.clear();

        //then
        Study findStudy = studyRepository.findById(studyId).orElseThrow();
        System.out.println(findStudy.getCurrentCount());
        System.out.println("leaveStudyWithRetry 실행 (studyId={}, userId={})");
        assertThat(findStudy.getCurrentCount()).isEqualTo(1);
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
}