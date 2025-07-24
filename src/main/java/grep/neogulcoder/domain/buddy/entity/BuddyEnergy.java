package grep.neogulcoder.domain.buddy.entity;

import grep.neogulcoder.domain.buddy.enums.BuddyEnergyReason;
import grep.neogulcoder.domain.review.ReviewType;
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

    protected BuddyEnergy() {

    }


    private BuddyEnergy(Long userId, int level) {
        this.userId = userId;
        this.level = level;
    }

    public static BuddyEnergy createDefault(Long userId) {
        return new BuddyEnergy(userId, BuddyEnergyReason.SIGN_UP.getPoint());
    }

    public void updateLevel(int newLevel) {
        this.level = newLevel;
    }

    // 리뷰 타입 기반 에너지 변경 //
    public void updateEnergy(ReviewType reviewType) {
        if (reviewType == ReviewType.GOOD || reviewType == ReviewType.EXCELLENT) {
            this.level += 1;
        } else if (reviewType == ReviewType.BAD) {
            this.level -= 1;
        }
    }

    public BuddyEnergyReason findReasonFrom(ReviewType reviewType) {
        if(reviewType.isPositive()){
            return BuddyEnergyReason.POSITIVE_REVIEW;
        }
        return BuddyEnergyReason.NEGATIVE_REVIEW;
    }
}
