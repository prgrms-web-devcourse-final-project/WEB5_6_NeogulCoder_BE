package grep.neogul_coder.domain.calender.entity;

import grep.neogul_coder.global.entity.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;

@Entity
@Getter
public class Calender extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime scheduledStart;

    private LocalDateTime scheduledEnd;

    private String title;

    private String content;

}
