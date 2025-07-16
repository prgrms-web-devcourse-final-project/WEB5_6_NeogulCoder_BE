package grep.neogul_coder.domain.prtemplate.service;

import grep.neogul_coder.domain.buddy.entity.BuddyEnergy;
import grep.neogul_coder.domain.buddy.repository.BuddyEnergyRepository;
import grep.neogul_coder.domain.prtemplate.controller.dto.response.PrPageResponse;
import grep.neogul_coder.domain.prtemplate.entity.Link;
import grep.neogul_coder.domain.prtemplate.entity.PrTemplate;
import grep.neogul_coder.domain.prtemplate.exception.code.PrTemplateErrorCode;
import grep.neogul_coder.domain.prtemplate.repository.LinkRepository;
import grep.neogul_coder.domain.prtemplate.repository.PrTemplateRepository;
import grep.neogul_coder.domain.review.entity.ReviewEntity;
import grep.neogul_coder.domain.review.repository.ReviewRepository;
import grep.neogul_coder.domain.users.entity.User;
import grep.neogul_coder.domain.users.exception.code.UserErrorCode;
import grep.neogul_coder.domain.users.repository.UserRepository;
import grep.neogul_coder.global.exception.business.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class PrTemplateService {

    private final PrTemplateRepository prTemplateRepository;
    private final LinkRepository linkRepository;
    private final ReviewRepository reviewRepository;
    private final BuddyEnergyRepository buddyEnergyRepository;
    private final UserRepository userRepository;

    public void deleteByUserId(Long userId) {
        PrTemplate prTemplate = prTemplateRepository.findByUserId(userId);
        prTemplate.delete();
    }

    public void update(Long id, String location) {
        PrTemplate prTemplate = prTemplateRepository.findById(id).orElseThrow(
            () -> new NotFoundException(PrTemplateErrorCode.TEMPLATE_NOT_FOUND));
        prTemplate.update(location);
    }

    public void updateIntroduction(Long id, String introduction) {
        PrTemplate prTemplate = prTemplateRepository.findById(id).orElseThrow(
            () -> new NotFoundException(PrTemplateErrorCode.TEMPLATE_NOT_FOUND));
        prTemplate.updateIntroduction(introduction);
    }

    public PrPageResponse toResponse(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(
            UserErrorCode.USER_NOT_FOUND));
        PrTemplate prTemplate = prTemplateRepository.findByUserId(userId);
        List<Link> links = linkRepository.findAllByPrIdAndActivatedTrue(prTemplate.getId());
        List<ReviewEntity> reviews = reviewRepository.findAllByTargetUserId(userId);

        List<PrPageResponse.UserProfileDto> userProfiles = List.of(
            PrPageResponse.UserProfileDto.builder()
                .nickname(user.getNickname())
                .profileImgUrl(user.getProfileImageUrl())
                .build()
        );

        List<PrPageResponse.UserLocationAndLink> userLocationAndLinks = links.stream()
            .map(link -> PrPageResponse.UserLocationAndLink.builder()
                .location(prTemplate.getLocation())
                .linkName(link.getUrlName())
                .link(link.getPrUrl())
                .build())
            .toList();

        int buddyEnergy = buddyEnergyRepository.findByUserId(userId)
            .map(BuddyEnergy::getLevel)
            .orElse(0);

        Map<String, Long> tagMap = reviews.stream()
            .flatMap(review -> review.getReviewTags().stream())
            .map(myTag -> myTag.getReviewTag().getReviewTag())
            .collect(Collectors.groupingBy(tag -> tag, Collectors.counting()));

        List<PrPageResponse.ReviewTagDto> reviewTags = tagMap.entrySet().stream()
            .map(entry -> PrPageResponse.ReviewTagDto.builder()
                .reviewType(entry.getKey())
                .reviewCount(entry.getValue().intValue())
                .build())
            .toList();

        List<PrPageResponse.ReviewContentDto> reviewContents = reviews.stream()
            .sorted(Comparator.comparing(ReviewEntity::getCreatedDate).reversed())
            .limit(5)
            .map(review -> {
                User writer = userRepository.findById(review.getWriteUserId())
                    .orElseThrow(() -> new NotFoundException(UserErrorCode.USER_NOT_FOUND));
                return PrPageResponse.ReviewContentDto.builder()
                    .reviewUserId(writer.getId())
                    .reviewUserImgUrl(writer.getProfileImageUrl())
                    .reviewComment(review.getContent())
                    .reviewDate(review.getCreatedDate().toLocalDate())
                    .build();
            })
            .toList();

        return PrPageResponse.builder()
            .userProfiles(userProfiles)
            .userLocationAndLinks(userLocationAndLinks)
            .buddyEnergy(50)
            .reviewTags(reviewTags)
            .reviewContents(reviewContents)
            .introduction(prTemplate.getIntroduction())
            .build();
    }
}