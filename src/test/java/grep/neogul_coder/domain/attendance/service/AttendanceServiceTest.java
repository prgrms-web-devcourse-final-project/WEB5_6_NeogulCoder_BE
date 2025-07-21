package grep.neogul_coder.domain.attendance.service;

import grep.neogul_coder.domain.IntegrationTestSupport;
import grep.neogul_coder.domain.attendance.Attendance;
import grep.neogul_coder.domain.attendance.controller.dto.response.AttendanceInfoResponse;
import grep.neogul_coder.domain.attendance.repository.AttendanceRepository;
import grep.neogul_coder.domain.study.Study;
import grep.neogul_coder.domain.study.StudyMember;
import grep.neogul_coder.domain.study.repository.StudyMemberRepository;
import grep.neogul_coder.domain.study.repository.StudyRepository;
import grep.neogul_coder.domain.users.entity.User;
import grep.neogul_coder.domain.users.repository.UserRepository;
import grep.neogul_coder.global.exception.business.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static grep.neogul_coder.domain.attendance.exception.code.AttendanceErrorCode.ATTENDANCE_ALREADY_CHECKED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AttendanceServiceTest extends IntegrationTestSupport {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private StudyMemberRepository studyMemberRepository;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private AttendanceRepository attendanceRepository;

    private Long userId;
    private Long studyId;

    @BeforeEach
    void init() {
        User user = createUser("test1");
        userRepository.save(user);
        userId = user.getId();

        Study study = createStudy("스터디", LocalDateTime.parse("2025-07-25T20:20:20"), LocalDateTime.parse("2025-07-28T20:20:20"));
        studyRepository.save(study);
        studyId = study.getId();

        StudyMember studyMember = createStudyMember(study, userId);
        studyMemberRepository.save(studyMember);
    }

    @DisplayName("출석을 조회합니다.")
    @Test
    void getAttendances() {
        // given
        Attendance attendance = Attendance.create(studyId, userId);
        attendanceRepository.save(attendance);

        // when
        AttendanceInfoResponse response = attendanceService.getAttendances(studyId, userId);

        // then
        assertThat(response.getAttendances().getFirst().getStudyId()).isEqualTo(studyId);
    }

    @DisplayName("스터디에 출석 체크를 합니다.")
    @Test
    void createAttendance() {
        // given
        Attendance attendance = Attendance.create(studyId, userId);

        // when
        attendanceRepository.save(attendance);

        // then
        assertThat(attendance.getAttendanceDate().toLocalDate()).isEqualTo(LocalDate.now());
    }

    @DisplayName("스터디에 이미 출석한 경우 예외가 발생합니다.")
    @Test
    void createAttendanceFail() {
        // given
        Attendance attendance = Attendance.create(studyId, userId);
        attendanceRepository.save(attendance);

        // when then
        assertThatThrownBy(() ->
            attendanceService.createAttendance(studyId, userId))
            .isInstanceOf(BusinessException.class).hasMessage(ATTENDANCE_ALREADY_CHECKED.getMessage());
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

    private StudyMember createStudyMember(Study study, long userId) {
        return StudyMember.builder()
            .study(study)
            .userId(userId)
            .build();
    }
}