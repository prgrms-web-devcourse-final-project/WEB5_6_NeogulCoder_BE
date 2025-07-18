package grep.neogul_coder.domain.buddy.service;

import grep.neogul_coder.domain.buddy.entity.BuddyEnergy;
import grep.neogul_coder.domain.buddy.entity.BuddyLog;
import grep.neogul_coder.domain.buddy.enums.BuddyEnergyReason;
import grep.neogul_coder.domain.buddy.repository.BuddyEnergyLogRepository;
import grep.neogul_coder.domain.buddy.repository.BuddyEnergyRepository;
import grep.neogul_coder.domain.buddy.controller.dto.response.BuddyEnergyResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuddyEnergyService {

    private final BuddyEnergyRepository buddyEnergyRepository;
    private final BuddyEnergyLogRepository buddyEnergyLogRepository;

    // 현재 버디 에너지 조회
    @Transactional
    public BuddyEnergyResponse getBuddyEnergy(Long userId) {
        BuddyEnergy energy = buddyEnergyRepository.findByUserId(userId)
            .orElseThrow(() -> new IllegalArgumentException("해당 유저의 버디 에너지를 찾을 수 없습니다."));
        return BuddyEnergyResponse.from(energy);
    }

    // 버디 에너지 업데이트
    @Transactional
    public BuddyEnergyResponse updateEnergy(Long userId, BuddyEnergyReason reason) {
        BuddyEnergy energy = buddyEnergyRepository.findByUserId(userId)
            .orElseThrow(() -> new IllegalArgumentException("해당 유저의 버디 에너지를 찾을 수 없습니다."));

        int newLevel = energy.getLevel() + reason.getPoint();
        energy.updateLevel(newLevel);

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
}
