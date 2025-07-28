package grep.neogulcoder.domain.calendar.service;

import grep.neogulcoder.domain.calendar.controller.dto.requset.TeamCalendarRequest;
import grep.neogulcoder.domain.calendar.controller.dto.response.TeamCalendarResponse;
import grep.neogulcoder.domain.calendar.entity.Calendar;
import grep.neogulcoder.domain.calendar.entity.TeamCalendar;
import grep.neogulcoder.domain.calendar.exception.code.CalendarErrorCode;
import grep.neogulcoder.domain.calendar.repository.TeamCalendarQueryRepository;
import grep.neogulcoder.domain.calendar.repository.TeamCalendarRepository;
import grep.neogulcoder.domain.users.entity.User;
import grep.neogulcoder.domain.users.service.UserService;
import grep.neogulcoder.global.exception.validation.ValidationException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static grep.neogulcoder.domain.calendar.exception.code.CalendarErrorCode.NOT_CALENDAR_OWNER;

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

    // 특정 월(한 달 단위) 조회
    public List<TeamCalendarResponse> findByMonth(Long studyId, int year, int month) {
        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDateTime start = startOfMonth.atStartOfDay();
        LocalDateTime end = startOfMonth.plusMonths(1).atStartOfDay();

        return teamCalendarQueryRepository
            .findByStudyIdAndMonth(studyId, start, end).stream()
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
