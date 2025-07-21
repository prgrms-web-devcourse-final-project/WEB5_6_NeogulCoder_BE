package grep.neogul_coder.domain.prtemplate.service;

import grep.neogul_coder.domain.buddy.controller.dto.response.BuddyEnergyResponse;
import grep.neogul_coder.domain.buddy.service.BuddyEnergyService;
import grep.neogul_coder.domain.prtemplate.controller.dto.response.PrPageResponse;
import grep.neogul_coder.domain.prtemplate.entity.Link;
import grep.neogul_coder.domain.prtemplate.entity.PrTemplate;
import grep.neogul_coder.domain.prtemplate.exception.code.PrTemplateErrorCode;
import grep.neogul_coder.domain.prtemplate.repository.LinkRepository;
import grep.neogul_coder.domain.prtemplate.repository.PrTemplateQueryRepository;
import grep.neogul_coder.domain.prtemplate.repository.PrTemplateRepository;
import grep.neogul_coder.domain.review.entity.ReviewEntity;
import grep.neogul_coder.domain.review.repository.ReviewRepository;
import grep.neogul_coder.domain.users.entity.User;
import grep.neogul_coder.domain.users.exception.UserNotFoundException;
import grep.neogul_coder.domain.users.exception.code.UserErrorCode;
import grep.neogul_coder.domain.users.repository.UserRepository;
import grep.neogul_coder.global.exception.business.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrTemplateService {

    private final PrTemplateRepository prTemplateRepository;
    private final PrTemplateQueryRepository prTemplateQueryRepository;
    private final LinkRepository linkRepository;
    private final ReviewRepository reviewRepository;
    private final BuddyEnergyService buddyEnergyService;
    private final UserRepository userRepository;

    @Transactional
    public void deleteByUserId(Long userId) {
        PrTemplate prTemplate = getPrTemplateByUserId(userId);
        prTemplate.delete();
    }

    @Transactional
    public void update(Long userId, String location) {
        PrTemplate prTemplate = getPrTemplateByUserId(userId);
        prTemplate.update(location);
    }

    @Transactional
    public void updateIntroduction(Long userId, String introduction) {
        PrTemplate prTemplate = getPrTemplateByUserId(userId);
        prTemplate.updateIntroduction(introduction);
    }

    @Transactional(readOnly = true)
    public PrPageResponse toResponse(Long userId) {

        PrTemplate prTemplate = getPrTemplateByUserId(userId);
        List<Link> links = linkRepository.findAllByUserIdAndActivatedTrue(userId);
        List<ReviewEntity> reviews = reviewRepository.findAllByTargetUserId(userId);

        List<PrPageResponse.UserProfileDto> prUser = getPrUser(userId);
        List<PrPageResponse.UserLocationAndLink> userLocationAndLinks = getUserLocationAndLinks(links, prTemplate);
        BuddyEnergyResponse buddyEnergy = buddyEnergyService.getBuddyEnergy(userId);
        List<PrPageResponse.ReviewTypeDto> reviewTypeDto = getReviewTypeDto(userId);
        List<PrPageResponse.ReviewContentDto> reviewContents = getReviewContents(reviews);

        return PrPageResponse.builder()
                .userProfiles(prUser)
                .userLocationAndLinks(userLocationAndLinks)
                .buddyEnergy(buddyEnergy)
                .reviewTypes(reviewTypeDto)
                .reviewContents(reviewContents)
                .introduction(prTemplate.getIntroduction())
                .build();
    }

    private PrTemplate getPrTemplateByUserId(Long userId) {
        return prTemplateRepository.findByUserId(userId).orElseThrow(
                () -> new NotFoundException(PrTemplateErrorCode.TEMPLATE_NOT_FOUND));
    }

    private List<PrPageResponse.UserProfileDto> getPrUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));
        return List.of(
                PrPageResponse.UserProfileDto.builder()
                        .nickname(user.getNickname())
                        .profileImgUrl(user.getProfileImageUrl())
                        .build()
        );
    }

    private List<PrPageResponse.UserLocationAndLink> getUserLocationAndLinks(List<Link> links, PrTemplate prTemplate) {
        return List.of(PrPageResponse.UserLocationAndLink.builder()
                .location(prTemplate.getLocation())
                .links(toLinkInfoList(links))
                .build());
    }

    private List<PrPageResponse.UserLocationAndLink.LinkInfo> toLinkInfoList(List<Link> links) {
        return links.stream()
                .map(link -> PrPageResponse.UserLocationAndLink.LinkInfo.builder()
                        .linkName(link.getUrlName())
                        .link(link.getPrUrl())
                        .build())
                .toList();
    }

    private List<PrPageResponse.ReviewTypeDto> getReviewTypeDto(Long targetUserId) {
        return prTemplateQueryRepository.findReviewTypeCountsByTargetUser(targetUserId);
    }

    private List<PrPageResponse.ReviewContentDto> getReviewContents(List<ReviewEntity> reviews) {
        return reviews.stream()
                .sorted(Comparator.comparing(ReviewEntity::getCreatedDate).reversed())
                .limit(5)
                .map(review -> {
                    User writer = userRepository.findById(review.getWriteUserId()).orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));
                    return PrPageResponse.ReviewContentDto.builder()
                            .reviewUserId(writer.getId())
                            .reviewUserNickname(writer.getNickname())
                            .reviewUserImgUrl(writer.getProfileImageUrl())
                            .reviewComment(review.getContent())
                            .reviewDate(review.getCreatedDate().toLocalDate())
                            .build();
                })
                .toList();

    }
}
