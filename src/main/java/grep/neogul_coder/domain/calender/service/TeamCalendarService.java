package grep.neogul_coder.domain.calender.service;

import grep.neogul_coder.domain.calender.controller.dto.requset.TeamCalendarRequest;
import grep.neogul_coder.domain.calender.controller.dto.response.TeamCalendarResponse;
import grep.neogul_coder.domain.calender.entity.Calendar;
import grep.neogul_coder.domain.calender.entity.TeamCalendar;
import grep.neogul_coder.domain.calender.exception.CalendarNotFoundException;
import grep.neogul_coder.domain.calender.exception.CalendarValidationException;
import grep.neogul_coder.domain.calender.exception.code.CalendarErrorCode;
import grep.neogul_coder.domain.calender.repository.TeamCalendarQueryRepository;
import grep.neogul_coder.domain.calender.repository.TeamCalendarRepository;
import grep.neogul_coder.domain.users.entity.User;
import grep.neogul_coder.domain.users.service.UserService;
import grep.neogul_coder.global.exception.business.NotFoundException;
import grep.neogul_coder.global.exception.validation.ValidationException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static grep.neogul_coder.domain.calender.exception.code.CalendarErrorCode.CALENDAR_NOT_FOUND;
import static grep.neogul_coder.domain.calender.exception.code.CalendarErrorCode.NOT_CALENDAR_OWNER;
import static grep.neogul_coder.global.response.code.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamCalendarService {

    private final TeamCalendarRepository teamCalendarRepository;
    private final TeamCalendarQueryRepository teamCalendarQueryRepository;
    private final UserService userService;

    public List<TeamCalendarResponse> findAll(Long studyId) {
        return teamCalendarRepository.findAllWithCalendarByStudyId(studyId).stream()
            .map(tc -> {
                User user = userService.get(tc.getUserId());
                return TeamCalendarResponse.from(tc, user);
            })
            .toList();
    }

    public List<TeamCalendarResponse> findByDate(Long studyId, LocalDate date) {

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();

        return teamCalendarQueryRepository
            .findByStudyIdAndDate(studyId, start, end).stream()
            .map(tc -> {
                User user = userService.get(tc.getUserId());
                return TeamCalendarResponse.from(tc, user);
            })
            .toList();
    }

    @Transactional
    public Long create(Long studyId, Long userId, TeamCalendarRequest request) {
        validateRequest(request);

        Calendar calendar = request.toCalendar();
        TeamCalendar teamCalendar = new TeamCalendar(studyId, userId, calendar);
        teamCalendarRepository.save(teamCalendar);

        return teamCalendar.getId();
    }

    @Transactional
    public void update(Long studyId, Long userId, Long teamCalendarId, TeamCalendarRequest request) {
        validateRequest(request);

        TeamCalendar calendar = teamCalendarRepository.findById(teamCalendarId)
            // 본인이 작성한 일정만 수정할 수 있음
            .filter(tc -> tc.getStudyId().equals(studyId) && tc.getUserId().equals(userId))
            // 예외처리
            .orElseThrow(() ->  new ValidationException(NOT_CALENDAR_OWNER));
        calendar.getCalendar().update(request.toCalendar());
    }

    @Transactional
    public void delete(Long studyId, Long userId, Long teamCalendarId) {
        TeamCalendar calendar = teamCalendarRepository.findById(teamCalendarId)
            // 본인이 작성한 일정만 삭제할 수 있음
            .filter(tc -> tc.getStudyId().equals(studyId) && tc.getUserId().equals(userId))
            // 예외처리
            .orElseThrow(() ->  new ValidationException(NOT_CALENDAR_OWNER));
        calendar.delete();
    }

    // 공통 유효성 검증 메서드
    private void validateRequest(TeamCalendarRequest request) {
        if (request.getTitle() == null || request.getStartTime() == null || request.getEndTime() == null) {
            throw new ValidationException(CalendarErrorCode.MISSING_REQUIRED_FIELDS);
        }
    }

}
