package grep.neogul_coder.domain.buddy.service;

import grep.neogul_coder.domain.buddy.entity.BuddyEnergy;
import grep.neogul_coder.domain.buddy.entity.BuddyLog;
import grep.neogul_coder.domain.buddy.enums.BuddyEnergyReason;
import grep.neogul_coder.domain.buddy.exception.BuddyEnergyNotFoundException;
import grep.neogul_coder.domain.buddy.exception.code.BuddyEnergyErrorCode;
import grep.neogul_coder.domain.buddy.repository.BuddyEnergyLogRepository;
import grep.neogul_coder.domain.buddy.repository.BuddyEnergyRepository;
import grep.neogul_coder.domain.buddy.controller.dto.response.BuddyEnergyResponse;
import grep.neogul_coder.domain.review.ReviewType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static grep.neogul_coder.domain.buddy.exception.code.BuddyEnergyErrorCode.BUDDY_ENERGY_NOT_FOUND;

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
    public BuddyEnergyResponse updateEnergyByStudy(Long userId, boolean isLeader) {
        BuddyEnergy energy = buddyEnergyRepository.findByUserId(userId)
            .orElseThrow(() -> new BuddyEnergyNotFoundException(BUDDY_ENERGY_NOT_FOUND));

        int points = BuddyEnergyReason.STUDY_DONE.getPoint();
        BuddyEnergyReason reason = BuddyEnergyReason.STUDY_DONE;

        if (isLeader) {
            points += BuddyEnergyReason.TEAM_LEADER_BONUS.getPoint();
            reason = BuddyEnergyReason.TEAM_LEADER_BONUS;
        }

        energy.updateLevel(energy.getLevel() + points);
        buddyEnergyLogRepository.save(BuddyLog.of(energy, reason));
        buddyEnergyRepository.save(energy);

        return BuddyEnergyResponse.from(energy);
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
