package grep.neogulcoder.domain.prtemplate.service;

import grep.neogulcoder.domain.buddy.controller.dto.response.BuddyEnergyResponse;
import grep.neogulcoder.domain.buddy.service.BuddyEnergyService;
import grep.neogulcoder.domain.prtemplate.controller.dto.response.PrPageResponse;
import grep.neogulcoder.domain.prtemplate.entity.Link;
import grep.neogulcoder.domain.prtemplate.entity.PrTemplate;
import grep.neogulcoder.domain.prtemplate.exception.code.PrTemplateErrorCode;
import grep.neogulcoder.domain.prtemplate.repository.LinkRepository;
import grep.neogulcoder.domain.prtemplate.repository.PrTemplateQueryRepository;
import grep.neogulcoder.domain.prtemplate.repository.PrTemplateRepository;
import grep.neogulcoder.domain.review.entity.ReviewEntity;
import grep.neogulcoder.domain.review.repository.ReviewRepository;
import grep.neogulcoder.domain.users.entity.User;
import grep.neogulcoder.domain.users.exception.UserNotFoundException;
import grep.neogulcoder.domain.users.exception.code.UserErrorCode;
import grep.neogulcoder.domain.users.repository.UserRepository;
import grep.neogulcoder.global.exception.business.NotFoundException;
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
