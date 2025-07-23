package grep.neogulcoder.domain.calender.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import grep.neogulcoder.domain.calender.entity.PersonalCalendar;
import grep.neogulcoder.domain.calender.entity.QPersonalCalendar;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static grep.neogulcoder.domain.calender.entity.QCalendar.calendar;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PersonalCalendarQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<PersonalCalendar> findByUserIdAndDate(Long userId, LocalDateTime start, LocalDateTime end) {
        // Q타입 : 쿼리DSL 에서 사용하는 엔티티 기반 쿼리 클래스
        QPersonalCalendar pc = QPersonalCalendar.personalCalendar;

        // 쿼리 실행:
        // 해당 유저의 일정 중
        // 시작 시간 <= 당일 끝시간 이고
        // 종료 시간 >= 당일 시작시간인 일정들을 모두 조회
        return queryFactory
            .selectFrom(pc)
            .join(pc.calendar, calendar).fetchJoin()
            .where(
                pc.userId.eq(userId),
                pc.activated.eq(true),
                calendar.scheduledStart.lt(end),
                calendar.scheduledEnd.goe(start)
            )
            .fetch();
    }
}
