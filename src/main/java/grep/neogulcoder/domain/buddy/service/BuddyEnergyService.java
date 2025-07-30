package grep.neogulcoder.domain.buddy.service;

import grep.neogulcoder.domain.buddy.entity.BuddyEnergy;
import grep.neogulcoder.domain.buddy.entity.BuddyLog;
import grep.neogulcoder.domain.buddy.enums.BuddyEnergyReason;
import grep.neogulcoder.domain.buddy.exception.BuddyEnergyNotFoundException;
import grep.neogulcoder.domain.buddy.repository.BuddyEnergyLogRepository;
import grep.neogulcoder.domain.buddy.repository.BuddyEnergyRepository;
import grep.neogulcoder.domain.buddy.controller.dto.response.BuddyEnergyResponse;
import grep.neogulcoder.domain.review.ReviewType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static grep.neogulcoder.domain.buddy.exception.code.BuddyEnergyErrorCode.BUDDY_ENERGY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class BuddyEnergyService {

    private final BuddyEnergyRepository buddyEnergyRepository;
    private final BuddyEnergyLogRepository buddyEnergyLogRepository;

    // 현재 버디 에너지 조회
    @Transactional
    public BuddyEnergyResponse getBuddyEnergy(Long userId) {
        BuddyEnergy energy = buddyEnergyRepository.findByUserId(userId)
            .orElseThrow(() -> new BuddyEnergyNotFoundException(BUDDY_ENERGY_NOT_FOUND));
        return BuddyEnergyResponse.from(energy);
    }

    // ReviewType 기반 업데이트 //
    @Transactional
    public BuddyEnergyResponse updateEnergyByReview(Long userId, ReviewType reviewType) {
        BuddyEnergy energy = buddyEnergyRepository.findByUserId(userId)
            .orElseThrow(() -> new BuddyEnergyNotFoundException(BUDDY_ENERGY_NOT_FOUND));

        // ReviewType에 따른 에너지 증감
        energy.updateEnergy(reviewType);

        buddyEnergyLogRepository.save(BuddyLog.of(energy, toBuddyEnergyReason(reviewType)));
        buddyEnergyRepository.save(energy);

        return BuddyEnergyResponse.from(energy);
    }

    // 스터디 종료 시 +1, 리더면 +2
    @Transactional
    public void updateEnergyByStudy(BuddyEnergy energy, boolean isLeader) {
        energy.updateEnergy(isLeader);

        buddyEnergyLogRepository.save(BuddyLog.of(energy, BuddyEnergyReason.STUDY_DONE));
        if (isLeader) {
            buddyEnergyLogRepository.save(BuddyLog.of(energy, BuddyEnergyReason.TEAM_LEADER_BONUS));
        }
        BuddyEnergyResponse.from(energy);
    }

    // 회원가입 시 기본 에너지 생성
    @Transactional
    public BuddyEnergyResponse createDefaultEnergy(Long userId) {
        BuddyEnergy energy = BuddyEnergy.createDefault(userId);
        BuddyEnergy saved = buddyEnergyRepository.save(energy);

        buddyEnergyLogRepository.save(BuddyLog.of(saved, BuddyEnergyReason.SIGN_UP));
        return BuddyEnergyResponse.from(saved);
    }


    // ReviewType → BuddyEnergyReason 매핑 메서드
    private BuddyEnergyReason toBuddyEnergyReason(ReviewType reviewType) {
        return switch (reviewType) {
            case GOOD, EXCELLENT -> BuddyEnergyReason.POSITIVE_REVIEW;
            case BAD -> BuddyEnergyReason.NEGATIVE_REVIEW;
        };
    }
}
