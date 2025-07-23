package grep.neogulcoder.domain.calender.entity;


import grep.neogulcoder.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "team_calendar")
public class TeamCalendar extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Calendar calendar;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "study_id")
    private Long studyId;

    public TeamCalendar(Long studyId, Long userId, Calendar calendar) {
        this.studyId = studyId;
        this.userId = userId;
        this.calendar = calendar;

    }

    public TeamCalendar() {

    }

    public void delete() {
        this.activated = false;
    }
}

