package grep.neogulcoder.domain.buddy.entity;

import grep.neogulcoder.domain.buddy.enums.BuddyEnergyReason;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;

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

    protected BuddyLog() {

    }

    @Builder
    private BuddyLog(BuddyEnergy buddyEnergy, BuddyEnergyReason reason) {
        this.buddyEnergy = buddyEnergy;
        this.reason = reason;
    }

    public static BuddyLog of(BuddyEnergy energy, BuddyEnergyReason reason) {
        return BuddyLog.builder()
            .buddyEnergy(energy)
            .reason(reason)
            .build();
    }

}