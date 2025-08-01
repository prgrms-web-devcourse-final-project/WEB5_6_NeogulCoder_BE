package grep.neogulcoder.domain.calendar.service;

import grep.neogulcoder.domain.calendar.controller.dto.requset.PersonalCalendarRequest;
import grep.neogulcoder.domain.calendar.controller.dto.response.PersonalCalendarResponse;
import grep.neogulcoder.domain.calendar.entity.Calendar;
import grep.neogulcoder.domain.calendar.entity.PersonalCalendar;
import grep.neogulcoder.domain.calendar.exception.code.CalendarErrorCode;
import grep.neogulcoder.domain.calendar.repository.PersonalCalendarQueryRepository;
import grep.neogulcoder.domain.calendar.repository.PersonalCalendarRepository;
import grep.neogulcoder.domain.users.entity.User;
import grep.neogulcoder.domain.users.service.UserService;
import grep.neogulcoder.global.exception.business.NotFoundException;
import grep.neogulcoder.global.exception.validation.ValidationException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

import static grep.neogulcoder.domain.calendar.exception.code.CalendarErrorCode.CALENDAR_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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
        LocalDateTime start = date.atStartOfDay();                      // 2025-07-23 00:00
        LocalDateTime end = date.plusDays(1).atStartOfDay();   // 2025-07-24 00:00

        return personalCalendarQueryRepository
            .findByUserIdAndDate(userId, start, end).stream()
            .map(pc -> PersonalCalendarResponse.from(pc, user))
            .toList();
    }

    // 개인 일정 생성
    @Transactional
    public Long create(Long userId, PersonalCalendarRequest request) {
        // 필수 필드  입력 안할 시 유효성 예외 발생
        validateRequiredFields(request); // 메서드로 분리

        Calendar calendar = request.toCalendar();
        PersonalCalendar personalCalendar = new PersonalCalendar(userId, calendar);
        personalCalendarRepository.save(personalCalendar);
        return personalCalendar.getId();
    }

    // 개인 일정 수정
    @Transactional
    public void update(Long userId, Long personalCalendarId, PersonalCalendarRequest request) {
        // 필수 필드 검증
        validateRequiredFields(request);  // 메서드로 분리
        // 해당 일정이 존재하고 본인이 소유한 일정인지 확인
        PersonalCalendar calendar = personalCalendarRepository.findById(personalCalendarId)
            .filter(pc -> pc.getUserId().equals(userId))
            .orElseThrow(() -> new NotFoundException(CALENDAR_NOT_FOUND));
        // 기존 Calendar 엔티티 안의 필드들을 새 요청으로 갱신
        calendar.getCalendar().update(request.toCalendar());
    }

    // 개인 일정 삭제
    @Transactional
    public void delete(Long userId, Long personalCalendarId) {
        // 일정 조회 및 본인 확인
        PersonalCalendar calendar = personalCalendarRepository.findById(personalCalendarId)
            .filter(pc -> pc.getUserId().equals(userId))
            .orElseThrow(() -> new NotFoundException(CALENDAR_NOT_FOUND));
        calendar.delete();
    }

    // 공통 유효성 검증 메서드
    private void validateRequiredFields(PersonalCalendarRequest request) {
        if (request.getTitle() == null || request.getStartTime() == null || request.getEndTime() == null) {
            throw new ValidationException(CalendarErrorCode.MISSING_REQUIRED_FIELDS);
        }
    }
}
