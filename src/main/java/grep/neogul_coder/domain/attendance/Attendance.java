package grep.neogul_coder.domain.attendance;

import grep.neogul_coder.global.entity.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Attendance extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attendanceId;

    @Column(nullable = false)
    private Long studyId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private LocalDate attendanceDate;
}
