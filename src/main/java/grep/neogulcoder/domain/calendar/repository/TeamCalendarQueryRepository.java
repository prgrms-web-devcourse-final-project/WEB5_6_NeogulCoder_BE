package grep.neogulcoder.domain.calendar.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import grep.neogulcoder.domain.calendar.entity.QTeamCalendar;
import grep.neogulcoder.domain.calendar.entity.TeamCalendar;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static grep.neogulcoder.domain.calendar.entity.QCalendar.calendar;

@Repository
@RequiredArgsConstructor
public class TeamCalendarQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<TeamCalendar> findByStudyIdAndDate(Long studyId, LocalDateTime start, LocalDateTime end) {
        // 쿼리DSL용 Q타입: 팀 캘린더
        QTeamCalendar tc = QTeamCalendar.teamCalendar;

        // 쿼리:
        // - 해당 studyId인 팀 일정 중
        // - 일정 시작이 하루 끝 이전
        // - 일정 종료가 하루 시작 이후
        return queryFactory
            .selectFrom(tc)
            .join(tc.calendar, calendar).fetchJoin()
            .where(
                tc.studyId.eq(studyId),
                tc.activated.eq(true),
                calendar.scheduledStart.lt(end),
                calendar.scheduledEnd.goe(start)
            )
            .fetch();
    }

    // 특정 월 구간 조회
    public List<TeamCalendar> findByStudyIdAndMonth(Long studyId, LocalDateTime start, LocalDateTime end) {
        QTeamCalendar tc = QTeamCalendar.teamCalendar;

        return queryFactory
            .selectFrom(tc)
            .join(tc.calendar, calendar).fetchJoin()
            .where(
                tc.studyId.eq(studyId),
                tc.activated.eq(true),
                calendar.scheduledStart.lt(end),
                calendar.scheduledEnd.goe(start)
            )
            .fetch();
    }
}
