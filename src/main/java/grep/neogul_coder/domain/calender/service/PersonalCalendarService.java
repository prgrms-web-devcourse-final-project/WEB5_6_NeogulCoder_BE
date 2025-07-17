package grep.neogul_coder.domain.calender.service;

import grep.neogul_coder.domain.calender.controller.PersonalCalendarController;
import grep.neogul_coder.domain.calender.controller.dto.requset.PersonalCalendarRequest;
import grep.neogul_coder.domain.calender.controller.dto.response.PersonalCalendarResponse;
import grep.neogul_coder.domain.calender.entity.Calendar;
import grep.neogul_coder.domain.calender.entity.PersonalCalendar;
import grep.neogul_coder.domain.calender.exception.CalendarNotFoundException;
import grep.neogul_coder.domain.calender.exception.code.CalendarErrorCode;
import grep.neogul_coder.domain.calender.repository.PersonalCalendarQueryRepository;
import grep.neogul_coder.domain.calender.repository.PersonalCalendarRepository;
import grep.neogul_coder.domain.users.entity.User;
import grep.neogul_coder.domain.users.service.UserService;
import grep.neogul_coder.global.exception.business.NotFoundException;
import grep.neogul_coder.global.exception.validation.ValidationException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

import static grep.neogul_coder.domain.calender.exception.code.CalendarErrorCode.CALENDAR_NOT_FOUND;
import static grep.neogul_coder.global.response.code.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class PersonalCalendarService {

    private final PersonalCalendarRepository personalCalendarRepository;
    private final PersonalCalendarQueryRepository personalCalendarQueryRepository;
    private final UserService userService;

    // 해당 사용자의 전체 개인 일정 조회
    public List<PersonalCalendarResponse> findAll(Long userId) {
        User user = userService.get(userId);
        return personalCalendarRepository.findAllWithCalendarByUserId(userId).stream()
            .map(pc -> PersonalCalendarResponse.from(pc, user))
            .toList();
    }

    // 특정 날짜의 일정 필터링해서 조회
    public List<PersonalCalendarResponse> findByDate(Long userId, LocalDate date) {
        User user = userService.get(userId);

        LocalDateTime startOfDay = date.atStartOfDay();                 // 00:00:00
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);            // 23:59:59.999999999

        return personalCalendarQueryRepository
            .findByUserIdAndDate(userId, date).stream()
            .map(pc -> PersonalCalendarResponse.from(pc, user))
            .toList();
    }

    // 개인 일정 생성
    public void create(Long userId, PersonalCalendarRequest request) {
        // 필수 필드  입력 안할 시 유효성 예외 발생
        if (request.getTitle() == null || request.getStartTime() == null || request.getEndTime() == null) {
            throw new ValidationException(CalendarErrorCode.MISSING_REQUIRED_FIELDS);
        }

        Calendar calendar = request.toCalendar();
        PersonalCalendar personalCalendar = new PersonalCalendar(userId, calendar);
        personalCalendarRepository.save(personalCalendar);
    }

    // 개인 일정 수정
    @Transactional
    public void update(Long userId, Long personalCalendarId, PersonalCalendarRequest request) {
        // 필수 필드 검증
        if (request.getTitle() == null || request.getStartTime() == null || request.getEndTime() == null) {
            throw new ValidationException(CalendarErrorCode.MISSING_REQUIRED_FIELDS);
        }

        // 해당 일정이 존재하고 본인이 소유한 일정인지 확인
        PersonalCalendar calendar = personalCalendarRepository.findById(personalCalendarId)
            .filter(pc -> pc.getUserId().equals(userId))
            .orElseThrow(() -> new NotFoundException(CALENDAR_NOT_FOUND));
        // 기존 Calendar 엔티티 안의 필드들을 새 요청으로 갱신
        calendar.getCalendar().update(request.toCalendar());
    }

    // 개인 일정 삭제
    public void delete(Long userId, Long personalCalendarId) {
        // 일정 조회 및 본인 확인
        PersonalCalendar calendar = personalCalendarRepository.findById(personalCalendarId)
            .filter(pc -> pc.getUserId().equals(userId))
            .orElseThrow(() -> new NotFoundException(CALENDAR_NOT_FOUND));
        personalCalendarRepository.delete(calendar);
    }
}
