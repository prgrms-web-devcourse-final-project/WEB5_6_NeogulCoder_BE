package grep.neogul_coder.domain.buddy.entity;

import grep.neogul_coder.domain.buddy.enums.BuddyEnergyReason;
import grep.neogul_coder.domain.users.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class BuddyLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long buddyLogId;

    @Enumerated(EnumType.STRING)
    private BuddyEnergyReason reason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buddy_energy_id")
    private BuddyEnergy buddyEnergy;

}