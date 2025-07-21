package grep.neogul_coder.domain.attendance.service;

import grep.neogul_coder.domain.attendance.Attendance;
import grep.neogul_coder.domain.attendance.controller.dto.response.AttendanceInfoResponse;
import grep.neogul_coder.domain.attendance.controller.dto.response.AttendanceResponse;
import grep.neogul_coder.domain.attendance.repository.AttendanceRepository;
import grep.neogul_coder.domain.study.Study;
import grep.neogul_coder.domain.study.repository.StudyMemberRepository;
import grep.neogul_coder.domain.study.repository.StudyRepository;
import grep.neogul_coder.global.exception.business.BusinessException;
import grep.neogul_coder.global.exception.business.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static grep.neogul_coder.domain.attendance.exception.code.AttendanceErrorCode.ATTENDANCE_ALREADY_CHECKED;
import static grep.neogul_coder.domain.study.exception.code.StudyErrorCode.STUDY_NOT_FOUND;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudyRepository studyRepository;
    private final StudyMemberRepository studyMemberRepository;

    public AttendanceInfoResponse getAttendances(Long studyId, Long userId) {
        Study study = studyRepository.findByIdAndActivatedTrue(studyId)
            .orElseThrow(() -> new NotFoundException(STUDY_NOT_FOUND));

        List<Attendance> attendances = attendanceRepository.findByStudyIdAndUserId(studyId, userId);
        List<AttendanceResponse> responses = attendances.stream()
            .map(AttendanceResponse::from)
            .toList();

        int attendanceRate = getAttendanceRate(studyId, userId, study, responses);

        return AttendanceInfoResponse.of(responses, attendanceRate);
    }

    @Transactional
    public Long createAttendance(Long studyId, Long userId) {
        Study study = studyRepository.findByIdAndActivatedTrue(studyId)
            .orElseThrow(() -> new NotFoundException(STUDY_NOT_FOUND));

        validateNotAlreadyChecked(studyId, userId);

        Attendance attendance = Attendance.create(studyId, userId);
        attendanceRepository.save(attendance);
        return attendance.getId();
    }

    private int getAttendanceRate(Long studyId, Long userId, Study study, List<AttendanceResponse> responses) {
        LocalDate start = study.getStartDate().toLocalDate();
        LocalDate participated = studyMemberRepository.findCreatedDateByStudyIdAndUserId(studyId, userId).toLocalDate();
        LocalDate attendanceStart = start.isAfter(participated) ? start : participated;
        LocalDate end = study.getEndDate().toLocalDate();

        int totalDays = (int) ChronoUnit.DAYS.between(attendanceStart, end) + 1;
        int attendDays = responses.size();
        int attendanceRate = totalDays == 0 ? 0 : Math.round(((float) attendDays / totalDays) * 100);

        return attendanceRate;
    }

    private void validateNotAlreadyChecked(Long studyId, Long userId) {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);

        if (attendanceRepository.existsTodayAttendance(studyId, userId, startOfDay, endOfDay)) {
            throw new BusinessException(ATTENDANCE_ALREADY_CHECKED);
        }
    }
}
