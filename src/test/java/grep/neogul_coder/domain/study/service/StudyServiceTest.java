package grep.neogul_coder.domain.study.service;

import grep.neogul_coder.domain.IntegrationTestSupport;
import grep.neogul_coder.domain.study.Study;
import grep.neogul_coder.domain.study.StudyMember;
import grep.neogul_coder.domain.study.controller.dto.request.StudyCreateRequest;
import grep.neogul_coder.domain.study.controller.dto.request.StudyUpdateRequest;
import grep.neogul_coder.domain.study.controller.dto.response.StudyItemPagingResponse;
import grep.neogul_coder.domain.study.enums.Category;
import grep.neogul_coder.domain.study.enums.StudyMemberRole;
import grep.neogul_coder.domain.study.enums.StudyType;
import grep.neogul_coder.domain.study.repository.StudyMemberRepository;
import grep.neogul_coder.domain.study.repository.StudyRepository;
import grep.neogul_coder.domain.users.entity.User;
import grep.neogul_coder.domain.users.repository.UserRepository;
import grep.neogul_coder.global.exception.business.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static grep.neogul_coder.domain.study.enums.StudyMemberRole.*;
import static grep.neogul_coder.domain.study.exception.code.StudyErrorCode.STUDY_NOT_LEADER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StudyServiceTest extends IntegrationTestSupport {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private StudyService studyService;

    @Autowired
    private StudyMemberRepository studyMemberRepository;

    private Long userId1;

    @BeforeEach
    void init() {
        User user1 = createUser("test1");
        User user2 = createUser("test2");
        userRepository.saveAll(List.of(user1, user2));
        userId1 = user1.getId();
    }

    @DisplayName("스터디를 생성합니다.")
    @Test
    void createStudy() {
        // given
        StudyCreateRequest request = StudyCreateRequest.builder()
            .name("스터디")
            .category(Category.IT)
            .capacity(5)
            .studyType(StudyType.OFFLINE)
            .location("서울")
            .startDate(LocalDate.of(2025, 7, 20))
            .endDate(LocalDate.of(2025, 7, 28))
            .introduction("스터디입니다.")
            .imageUrl("http://localhost:8083/image.url")
            .build();

        // when
        Long id = studyService.createStudy(request, userId1);

        // then
        Study findStudy = studyRepository.findByIdAndActivatedTrue(id).orElseThrow();
        assertThat(findStudy.getName()).isEqualTo("스터디");
    }

    @DisplayName("가입한 스터디 목록을 페이징 조회합니다.")
    @Test
    void getStudies() {
        // given
        Pageable pageable = PageRequest.of(0, 12);

        Study study = createStudy("스터디", Category.IT, 3, StudyType.OFFLINE, "서울", LocalDate.of(2025, 7, 18),
            LocalDate.of(2025, 7, 28), "스터디입니다.", "http://localhost:8083/image.url");
        studyRepository.save(study);
        Long studyId = study.getId();

        StudyMember studyMember = createStudyMember(study, userId1, LEADER);
        studyMemberRepository.save(studyMember);

        // when
        StudyItemPagingResponse response = studyService.getMyStudies(pageable, studyId);

        // then
        assertThat(response.getStudies().getFirst().getName()).isEqualTo("스터디");
    }

    @DisplayName("스터디장이 스터디를 수정합니다.")
    @Test
    void updateStudy() {
        // given
        Study study = createStudy("스터디", Category.IT, 3, StudyType.OFFLINE, "서울", LocalDate.of(2025, 7, 18),
            LocalDate.of(2025, 7, 28), "스터디입니다.", "http://localhost:8083/image.url");
        studyRepository.save(study);
        Long studyId = study.getId();

        StudyMember studyMember = createStudyMember(study, userId1, LEADER);
        studyMemberRepository.save(studyMember);

        StudyUpdateRequest request = StudyUpdateRequest.builder()
            .name("스터디 수정")
            .category(Category.DESIGN)
            .capacity(8)
            .studyType(StudyType.OFFLINE)
            .location("서울")
            .startDate(LocalDate.now())
            .introduction("Updated")
            .imageUrl("http://localhost:8083/image.url")
            .build();

        // when
        studyService.updateStudy(studyId, request, userId1);

        // then
        Study findStudy = studyRepository.findByIdAndActivatedTrue(studyId).orElseThrow();
        assertThat(findStudy.getName()).isEqualTo("스터디 수정");
    }

    @DisplayName("스터디원이 스터디 수정 시 예외가 발생합니다.")
    @Test
    void updateStudyFail() {
        // given
        Study study = createStudy("스터디", Category.IT, 3, StudyType.OFFLINE, "서울", LocalDate.of(2025, 7, 18),
            LocalDate.of(2025, 7, 28), "스터디입니다.", "http://localhost:8083/image.url");
        studyRepository.save(study);
        Long studyId = study.getId();

        StudyMember studyMember = createStudyMember(study, userId1, MEMBER);
        studyMemberRepository.save(studyMember);

        StudyUpdateRequest request = StudyUpdateRequest.builder()
            .name("스터디 수정")
            .category(Category.DESIGN)
            .capacity(8)
            .studyType(StudyType.OFFLINE)
            .location("서울")
            .startDate(LocalDate.now())
            .introduction("Updated")
            .imageUrl("http://localhost:8083/image.url")
            .build();

        // when then
        assertThatThrownBy(() ->
            studyService.updateStudy(studyId, request, userId1))
            .isInstanceOf(BusinessException.class).hasMessage(STUDY_NOT_LEADER.getMessage());
    }

    private static User createUser(String nickname) {
        return User.builder()
            .nickname(nickname)
            .build();
    }

    private Study createStudy(String name, Category category, int capacity, StudyType studyType,
                              String location, LocalDate startDate, LocalDate endDate, String introduction, String imageUrl) {
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