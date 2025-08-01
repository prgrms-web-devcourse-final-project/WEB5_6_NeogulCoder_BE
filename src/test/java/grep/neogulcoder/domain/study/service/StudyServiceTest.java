package grep.neogulcoder.domain.study.service;

import grep.neogulcoder.domain.IntegrationTestSupport;
import grep.neogulcoder.domain.study.Study;
import grep.neogulcoder.domain.study.StudyMember;
import grep.neogulcoder.domain.study.controller.dto.request.StudyCreateRequest;
import grep.neogulcoder.domain.study.controller.dto.request.StudyUpdateRequest;
import grep.neogulcoder.domain.study.controller.dto.response.StudyItemPagingResponse;
import grep.neogulcoder.domain.study.enums.Category;
import grep.neogulcoder.domain.study.enums.StudyMemberRole;
import grep.neogulcoder.domain.study.enums.StudyType;
import grep.neogulcoder.domain.study.repository.StudyMemberRepository;
import grep.neogulcoder.domain.study.repository.StudyRepository;
import grep.neogulcoder.domain.users.entity.User;
import grep.neogulcoder.domain.users.repository.UserRepository;
import grep.neogulcoder.global.exception.business.BusinessException;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static grep.neogulcoder.domain.study.enums.StudyMemberRole.LEADER;
import static grep.neogulcoder.domain.study.enums.StudyMemberRole.MEMBER;
import static grep.neogulcoder.domain.study.exception.code.StudyErrorCode.NOT_STUDY_LEADER;
import static grep.neogulcoder.domain.study.exception.code.StudyErrorCode.STUDY_CREATE_LIMIT_EXCEEDED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StudyServiceTest extends IntegrationTestSupport {

    @Autowired
    private EntityManager em;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private StudyService studyService;

    @Autowired
    private StudyMemberRepository studyMemberRepository;

    private Long userId;

    @BeforeEach
    void init() {
        User user1 = createUser("test1");
        User user2 = createUser("test2");
        userRepository.saveAll(List.of(user1, user2));
        userId = user1.getId();
    }

    @DisplayName("가입한 스터디 목록을 조회합니다.")
    @Test
    void getStudies() {
        // given
        Pageable pageable = PageRequest.of(0, 12);

        Study study = createStudy("스터디", Category.IT, 3, StudyType.OFFLINE, "서울", LocalDateTime.of(2025, 7, 18, 0, 0, 0),
            LocalDateTime.of(2025, 7, 28, 0, 0, 0), "스터디입니다.", "http://localhost:8083/image.url");
        studyRepository.save(study);

        StudyMember studyMember = createStudyMember(study, userId, LEADER);
        studyMemberRepository.save(studyMember);

        // when
        StudyItemPagingResponse response = studyService.getMyStudiesPaging(pageable, userId, false);

        // then
        assertThat(response.getStudies().getFirst().getName()).isEqualTo("스터디");
    }

    @DisplayName("스터디를 생성합니다.")
    @Test
    void createStudy() throws IOException {
        // given
        StudyCreateRequest request = StudyCreateRequest.builder()
                .name("스터디")
                .category(Category.IT)
                .capacity(5)
                .studyType(StudyType.OFFLINE)
                .location("서울")
                .startDate(LocalDateTime.of(2025, 7, 20, 0, 0, 0))
                .endDate(LocalDateTime.of(2026, 7, 28, 0, 0, 0))
                .introduction("스터디입니다.")
                .build();
        MultipartFile image = null;

        // when
        Long id = studyService.createStudy(request, userId, image);
        em.flush();
        em.clear();

        // then
        Study findStudy = studyRepository.findByIdAndActivatedTrue(id).orElseThrow();
        assertThat(findStudy.getName()).isEqualTo("스터디");
    }

    @DisplayName("종료되지 않은 스터디를 10개 초과로 생성할 시 예외가 발생합니다.")
    @Test
    void createStudyFail() throws IOException {
        // given
        StudyCreateRequest request = StudyCreateRequest.builder()
            .name("스터디")
            .category(Category.IT)
            .capacity(5)
            .studyType(StudyType.OFFLINE)
            .location("서울")
            .startDate(LocalDateTime.of(2025, 7, 20, 0, 0, 0))
            .endDate(LocalDateTime.of(2026, 7, 28, 0, 0, 0))
            .introduction("스터디입니다.")
            .build();
        MultipartFile image = null;

        for (int i = 0; i < 10; i++) {
            studyService.createStudy(request, userId, image);
            em.flush();
            em.clear();
        }

        // when then
        assertThatThrownBy(() ->
            studyService.createStudy(request, userId, image))
            .isInstanceOf(BusinessException.class).hasMessage(STUDY_CREATE_LIMIT_EXCEEDED.getMessage());
    }

    @DisplayName("스터디장이 스터디를 수정합니다.")
    @Test
    void updateStudy() throws IOException {
        // given
        Study study = createStudy("스터디", Category.IT, 3, StudyType.OFFLINE, "서울", LocalDateTime.of(2026, 7, 18, 0, 0, 0),
                LocalDateTime.of(2025, 7, 28, 0, 0, 0), "스터디입니다.", "http://localhost:8083/image.url");
        studyRepository.save(study);
        Long studyId = study.getId();

        StudyMember studyMember = createStudyMember(study, userId, LEADER);
        studyMemberRepository.save(studyMember);

        StudyUpdateRequest request = StudyUpdateRequest.builder()
                .name("스터디 수정")
                .category(Category.DESIGN)
                .capacity(8)
                .studyType(StudyType.OFFLINE)
                .location("서울")
                .startDate(LocalDateTime.of(2026, 7, 20, 0, 0, 0))
                .introduction("Updated")
                .build();
        MultipartFile image = null;

        // when
        studyService.updateStudy(studyId, request, userId, image);
        em.flush();
        em.clear();

        // then
        Study findStudy = studyRepository.findByIdAndActivatedTrue(studyId).orElseThrow();
        assertThat(findStudy.getName()).isEqualTo("스터디 수정");
    }

    @DisplayName("스터디원이 스터디 수정 시 예외가 발생합니다.")
    @Test
    void updateStudyFail() {
        // given
        Study study = createStudy("스터디", Category.IT, 3, StudyType.OFFLINE, "서울", LocalDateTime.of(2025, 7, 18, 0, 0, 0),
                LocalDateTime.of(2025, 7, 28, 0, 0, 0), "스터디입니다.", "http://localhost:8083/image.url");
        studyRepository.save(study);
        Long studyId = study.getId();

        StudyMember studyMember = createStudyMember(study, userId, MEMBER);
        studyMemberRepository.save(studyMember);

        StudyUpdateRequest request = StudyUpdateRequest.builder()
                .name("스터디 수정")
                .category(Category.DESIGN)
                .capacity(8)
                .studyType(StudyType.OFFLINE)
                .location("서울")
                .startDate(LocalDateTime.now())
                .introduction("Updated")
                .build();
        MultipartFile image = null;

        // when then
        assertThatThrownBy(() ->
                studyService.updateStudy(studyId, request, userId, image))
                .isInstanceOf(BusinessException.class).hasMessage(NOT_STUDY_LEADER.getMessage());
    }

    @DisplayName("스터디장이 스터디를 삭제합니다.")
    @Test
    void deleteStudy() {
        // given
        Study study = createStudy("스터디", Category.IT, 3, StudyType.OFFLINE, "서울", LocalDateTime.of(2025, 7, 18, 0, 0, 0),
                LocalDateTime.of(2025, 7, 28, 0, 0, 0), "스터디입니다.", "http://localhost:8083/image.url");
        studyRepository.save(study);
        Long studyId = study.getId();

        StudyMember studyMember = createStudyMember(study, userId, LEADER);
        studyMemberRepository.save(studyMember);

        // when
        studyService.deleteStudy(studyId, userId);
        em.flush();
        em.clear();

        Study deletedStudy = studyRepository.findById(studyId).orElseThrow();

        // then
        assertThat(deletedStudy.isActivated()).isFalse();
    }

    private static User createUser(String nickname) {
        return User.builder()
                .nickname(nickname)
                .build();
    }

    private Study createStudy(String name, Category category, int capacity, StudyType studyType,
                              String location, LocalDateTime startDate, LocalDateTime endDate, String introduction, String imageUrl) {
        return Study.builder()
                .name(name)
                .category(category)
                .capacity(capacity)
                .studyType(studyType)
                .location(location)
                .startDate(startDate)
                .endDate(endDate)
                .introduction(introduction)
                .imageUrl(imageUrl)
                .build();
    }

    private StudyMember createStudyMember(Study study, Long userId, StudyMemberRole role) {
        return StudyMember.builder()
                .study(study)
                .userId(userId)
                .role(role)
                .build();
    }
}