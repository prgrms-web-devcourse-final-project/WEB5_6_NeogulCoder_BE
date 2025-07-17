package grep.neogul_coder.domain.calender.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import grep.neogul_coder.domain.calender.entity.QCalendar;
import grep.neogul_coder.domain.calender.entity.QTeamCalendar;
import grep.neogul_coder.domain.calender.entity.TeamCalendar;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TeamCalendarQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<TeamCalendar> findByStudyIdAndDate(Long studyId, LocalDate date) {
        // 쿼리DSL용 Q타입: 팀 캘린더
        QTeamCalendar tc = QTeamCalendar.teamCalendar;
        QCalendar calendar = tc.calendar;

        LocalDateTime start = date.atStartOfDay();          // 00:00:00
        LocalDateTime end = date.atTime(LocalTime.MAX);     // 23:59:59.999...

        // 쿼리:
        // - 해당 studyId인 팀 일정 중
        // - 일정 시작이 하루 끝 이전
        // - 일정 종료가 하루 시작 이후
        return queryFactory
            .selectFrom(tc)
            .where(
                tc.studyId.eq(studyId),
                calendar.scheduledStart.loe(end),
                calendar.scheduledEnd.goe(start)
            )
            .fetch();
    }
}
