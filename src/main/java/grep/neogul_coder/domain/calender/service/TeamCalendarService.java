package grep.neogul_coder.domain.calender.service;

import grep.neogul_coder.domain.calender.controller.dto.requset.TeamCalendarRequest;
import grep.neogul_coder.domain.calender.controller.dto.response.TeamCalendarResponse;
import grep.neogul_coder.domain.calender.entity.Calendar;
import grep.neogul_coder.domain.calender.entity.TeamCalendar;
import grep.neogul_coder.domain.calender.repository.TeamCalendarRepository;
import grep.neogul_coder.global.exception.business.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static grep.neogul_coder.global.response.code.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class TeamCalendarService {

    private final TeamCalendarRepository teamCalendarRepository;

    public List<TeamCalendarResponse> findAll(Long studyId) {
        return teamCalendarRepository.findByStudyId(studyId).stream()
            .map(TeamCalendarResponse::from)
            .toList();
    }

    public List<TeamCalendarResponse> findByDate(Long studyId, LocalDate date) {
        return teamCalendarRepository.findByStudyIdAndCalendar_ScheduledStartBetween(
            studyId,
            date.atStartOfDay(),
            date.plusDays(1).atStartOfDay()
        ).stream().map(TeamCalendarResponse::from).toList();
    }

    public TeamCalendarResponse findOne(Long studyId, Long calendarId) {
        TeamCalendar calendar = teamCalendarRepository.findById(calendarId)
            .filter(tc -> tc.getStudyId().equals(studyId))
            .orElseThrow(() -> new NotFoundException(CALENDAR_NOT_FOUND));
        return TeamCalendarResponse.from(calendar);
    }

    public void create(Long studyId, TeamCalendarRequest request) {
        Calendar calendar = request.toCalendar();
        TeamCalendar teamCalendar = new TeamCalendar(studyId, calendar);
        teamCalendarRepository.save(teamCalendar);
    }

    public void update(Long studyId, Long calendarId, TeamCalendarRequest request) {
        TeamCalendar calendar = teamCalendarRepository.findById(calendarId)
            .filter(tc -> tc.getStudyId().equals(studyId))
            .orElseThrow(() -> new NotFoundException(CALENDAR_NOT_FOUND));
        calendar.getCalendar().update(request.toCalendar());
    }

    public void delete(Long studyId, Long calendarId) {
        TeamCalendar calendar = teamCalendarRepository.findById(calendarId)
            .filter(tc -> tc.getStudyId().equals(studyId))
            .orElseThrow(() -> new NotFoundException(CALENDAR_NOT_FOUND));
        teamCalendarRepository.delete(calendar);
    }
}
