package grep.neogulcoder.domain.calender.entity;

import grep.neogulcoder.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "personal_calendar")
public class PersonalCalendar extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Calendar calendar;

    @Column(name = "user_id")
    private Long userId;

    public PersonalCalendar(Long userId, Calendar calendar) {
        this.userId = userId;
        this.calendar = calendar;
    }

    public PersonalCalendar() {

    }

    public void delete() {
        this.activated = false;
    }
}

