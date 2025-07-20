package grep.neogul_coder.domain.attendance.service;

import grep.neogul_coder.domain.attendance.Attendance;
import grep.neogul_coder.domain.attendance.repository.AttendanceRepository;
import grep.neogul_coder.domain.study.Study;
import grep.neogul_coder.domain.study.repository.StudyRepository;
import grep.neogul_coder.global.exception.business.BusinessException;
import grep.neogul_coder.global.exception.business.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static grep.neogul_coder.domain.attendance.exception.code.AttendanceErrorCode.*;
import static grep.neogul_coder.domain.study.exception.code.StudyErrorCode.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudyRepository studyRepository;

    @Transactional
    public Long createAttendance(Long studyId, Long userId) {
        Study study = studyRepository.findByIdAndActivatedTrue(studyId)
            .orElseThrow(() -> new NotFoundException(STUDY_NOT_FOUND));

        validateNotAlreadyChecked(studyId, userId);

        Attendance attendance = Attendance.create(studyId, userId);
        attendanceRepository.save(attendance);
        return attendance.getId();
    }

    private void validateNotAlreadyChecked(Long studyId, Long userId) {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);
        if (attendanceRepository.existsTodayAttendance(studyId, userId, startOfDay, endOfDay)) {
            throw new BusinessException(ATTENDANCE_ALREADY_CHECKED);
        }
    }
}
