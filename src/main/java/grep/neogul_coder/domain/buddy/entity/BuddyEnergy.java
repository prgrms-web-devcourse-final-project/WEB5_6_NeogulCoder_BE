package grep.neogul_coder.domain.buddy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class BuddyEnergy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long buddyEnergyId;

    @Column(name = "user_id")
    private Long userId;

    private int level;

}
