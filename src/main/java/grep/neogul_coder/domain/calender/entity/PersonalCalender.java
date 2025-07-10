package grep.neogul_coder.domain.calender.entity;

import grep.neogul_coder.domain.users.entity.User;
import grep.neogul_coder.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class PersonalCalender extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Calender calendar;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private boolean isDeleted = false;
}

