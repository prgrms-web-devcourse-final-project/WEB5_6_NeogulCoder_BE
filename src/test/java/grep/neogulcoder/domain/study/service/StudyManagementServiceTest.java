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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
    private StudyManagementService studyManagementService;

    private Long userId1;
    private Long userId2;
    private Long userId3;
    private Study study;
    private Long studyId;

    @BeforeEach
    void init() {
        User user1 = createUser("test1");
        User user2 = createUser("test2");
        User user3 = createUser("test3");
        userRepository.saveAll(List.of(user1, user2, user3));
        userId1 = user1.getId();
        userId2 = user2.getId();
        userId3 = user3.getId();

        study = createStudy("스터디", LocalDateTime.parse("2025-09-05T20:20:20"), LocalDateTime.parse("2025-09-28T20:20:20"));
        studyRepository.save(study);
        studyId = study.getId();

        StudyMember studyLeader = createStudyLeader(study, userId1);
        StudyMember studyMember1 = createStudyMember(study, userId2);
        study.increaseMemberCount();
        StudyMember studyMember2 = createStudyMember(study, userId3);
        study.increaseMemberCount();

        studyMemberRepository.saveAll(List.of(studyLeader, studyMember1, studyMember2));
        studyRepository.save(study);

        em.clear();
    }

    @DisplayName("2명의 멤버가 스터디를 동시에 탈퇴합니다.")
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Test
    void leaveStudy() throws InterruptedException {
        // given
        final int threads = 2;
        ExecutorService executorService = Executors.newFixedThreadPool(threads);
        CountDownLatch countDownLatch = new CountDownLatch(threads);

        List<Long> targetUserIds = List.of(userId2, userId3);

        // when
        for (Long targetUserId : targetUserIds) {
            executorService.submit(() -> {
                try {
                    studyManagementService.leaveStudy(studyId, targetUserId);
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