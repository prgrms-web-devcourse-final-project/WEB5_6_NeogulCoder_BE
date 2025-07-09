package grep.neogul_coder.domain.attendance;

import grep.neogul_coder.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Attendance extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attendanceId;

    private Long studyId;

    private Long userId;

    private LocalDate attendanceDate;

    private boolean isPresent;
}
