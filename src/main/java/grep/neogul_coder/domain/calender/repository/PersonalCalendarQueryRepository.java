package grep.neogul_coder.domain.calender.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import grep.neogul_coder.domain.calender.entity.PersonalCalendar;
import grep.neogul_coder.domain.calender.entity.QCalendar;
import grep.neogul_coder.domain.calender.entity.QPersonalCalendar;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PersonalCalendarQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<PersonalCalendar> findByUserIdAndDate(Long userId, LocalDate date) {
        // Q타입 : 쿼리DSL 에서 사용하는 엔티티 기반 쿼리 클래스
        QPersonalCalendar pc = QPersonalCalendar.personalCalendar;
        QCalendar calendar = pc.calendar;

        // 조회 기준 : 하루의 시작과 끝 시간
        LocalDateTime start = date.atStartOfDay();          // 00:00:00
        LocalDateTime end = date.atTime(LocalTime.MAX);     // 23:59:59.999

        // 쿼리 실행:
        // 해당 유저의 일정 중
        // 시작 시간 <= 당일 끝시간 이고
        // 종료 시간 >= 당일 시작시간인 일정들을 모두 조회
        return queryFactory
            .selectFrom(pc)
            .join(pc.calendar, QCalendar.calendar).fetchJoin()
            .where(
                pc.userId.eq(userId),
                calendar.scheduledStart.loe(end),
                calendar.scheduledEnd.goe(start)
            )
            .fetch();
    }
}
