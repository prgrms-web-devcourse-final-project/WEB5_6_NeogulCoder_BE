package grep.neogul_coder.domain.calender.entity;


import grep.neogul_coder.global.entity.BaseEntity;
import grep.neogul_coder.domain.study.Study;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class TeamCalender extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Calender calendar;

    @ManyToOne(fetch = FetchType.LAZY)
    private Study study;

    private boolean isDeleted = false;
}

