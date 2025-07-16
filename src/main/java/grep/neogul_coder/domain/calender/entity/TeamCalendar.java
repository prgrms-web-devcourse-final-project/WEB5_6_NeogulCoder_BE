package grep.neogul_coder.domain.calender.entity;


import grep.neogul_coder.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class TeamCalendar extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Calendar calendar;

    @Column(name = "study_id")
    private Long studyId;

    public TeamCalendar(Long studyId, Calendar calendar) {
        this.studyId = studyId;
        this.calendar = calendar;

    }

    public TeamCalendar() {

    }
}

