package grep.neogulcoder.domain.attendance;

import grep.neogulcoder.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class Attendance extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long studyId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private LocalDateTime attendanceDate;

    protected Attendance() {}

    @Builder
    private Attendance(Long studyId, Long userId, LocalDateTime attendanceDate) {
        this.studyId = studyId;
        this.userId = userId;
        this.attendanceDate = attendanceDate;
    }

    public static Attendance create(Long studyId, Long userId) {
        return Attendance.builder()
            .studyId(studyId)
            .userId(userId)
            .attendanceDate(LocalDateTime.now())
            .build();
    }
}
