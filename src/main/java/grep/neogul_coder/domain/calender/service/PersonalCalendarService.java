package grep.neogul_coder.domain.calender.service;

import grep.neogul_coder.domain.calender.controller.dto.requset.PersonalCalendarRequest;
import grep.neogul_coder.domain.calender.controller.dto.response.PersonalCalendarResponse;
import grep.neogul_coder.domain.calender.entity.Calendar;
import grep.neogul_coder.domain.calender.entity.PersonalCalendar;
import grep.neogul_coder.domain.calender.exception.CalendarNotFoundException;
import grep.neogul_coder.domain.calender.exception.code.CalendarErrorCode;
import grep.neogul_coder.domain.calender.repository.PersonalCalendarRepository;
import grep.neogul_coder.domain.users.entity.User;
import grep.neogul_coder.domain.users.service.UserService;
import grep.neogul_coder.global.exception.business.NotFoundException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static grep.neogul_coder.global.response.code.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class PersonalCalendarService {

    private final PersonalCalendarRepository personalCalendarRepository;
    private final UserService userService;

    public List<PersonalCalendarResponse> findAll(Long userId) {
        User user = userService.get(userId);
        return personalCalendarRepository.findByUserId(userId).stream()
            .map(pc -> PersonalCalendarResponse.from(pc, user))
            .toList();
    }

    public List<PersonalCalendarResponse> findByDate(Long userId, LocalDate date) {
        User user = userService.get(userId);

        LocalDateTime startOfDay = date.atStartOfDay();                 // 00:00:00
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);            // 23:59:59.999999999

        return personalCalendarRepository
            .findByUserIdAndCalendar_ScheduledStartLessThanEqualAndCalendar_ScheduledEndGreaterThanEqual(
                userId, endOfDay, startOfDay
            ).stream()
            .map(pc -> PersonalCalendarResponse.from(pc, user))
            .toList();
    }

    public void create(Long userId, PersonalCalendarRequest request) {
        Calendar calendar = request.toCalendar();
        PersonalCalendar personalCalendar = new PersonalCalendar(userId, calendar);
        personalCalendarRepository.save(personalCalendar);
    }

    public void update(Long userId, Long calendarId, PersonalCalendarRequest request) {
        PersonalCalendar calendar = personalCalendarRepository.findById(calendarId)
            .filter(pc -> pc.getUserId().equals(userId))
            .orElseThrow(() -> new CalendarNotFoundException(CalendarErrorCode.CALENDAR_NOT_FOUND));
        calendar.getCalendar().update(request.toCalendar());
    }

    public void delete(Long userId, Long calendarId) {
        PersonalCalendar calendar = personalCalendarRepository.findById(calendarId)
            .filter(pc -> pc.getUserId().equals(userId))
            .orElseThrow(() -> new CalendarNotFoundException(CalendarErrorCode.CALENDAR_NOT_FOUND));
        personalCalendarRepository.delete(calendar);
    }
}
