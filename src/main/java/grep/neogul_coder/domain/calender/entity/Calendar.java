package grep.neogul_coder.domain.calender.entity;

import grep.neogul_coder.global.entity.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;


@Entity
@Getter
public class Calendar extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime scheduledStart;

    private LocalDateTime scheduledEnd;

    private String title;

    private String content;

    @Builder
    public Calendar(String title, String content, LocalDateTime scheduledStart, LocalDateTime scheduledEnd) {
        this.title = title;
        this.content = content;
        this.scheduledStart = scheduledStart;
        this.scheduledEnd = scheduledEnd;
    }

    public Calendar() {

    }

    public void update(Calendar updated) {
        this.title = updated.title;
        this.content = updated.content;
        this.scheduledStart = updated.scheduledStart;
        this.scheduledEnd = updated.scheduledEnd;
    }
}
