package grep.neogul_coder.domain.prtemplate.service;

import grep.neogul_coder.domain.prtemplate.controller.dto.request.LinkUpdateRequest;
import grep.neogul_coder.domain.prtemplate.entity.Link;
import grep.neogul_coder.domain.prtemplate.repository.LinkRepository;
import grep.neogul_coder.domain.prtemplate.repository.PrTemplateRepository;
import jakarta.transaction.Transactional;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LinkService {

    private final LinkRepository linkRepository;
    private final PrTemplateRepository prTemplateRepository;

    public void deleteByUserId(Long userId) {
        List<Link> links = linkRepository.findAllByUserId(userId);
        for (Link link : links) {
            link.delete();
        }
    }

    public void update(Long userId, List<LinkUpdateRequest> requests) {
        List<Link> links = linkRepository.findAllByUserId(userId);

        if (requests.size() != 2) {
            throw new IllegalArgumentException("링크는 정확히 2개가 전달되어야 합니다.");
        }

        for (int i = 0; i < links.size(); i++) {
            applyRequestToLink(links.get(i), requests.get(i));
        }
    }

    private void applyRequestToLink(Link link, LinkUpdateRequest request) {
        if (isRequestLinkEmpty(request)) {
            link.delete();
        } else {
            link.updateUrlName(request.getUrlName());
            link.updatePrUrl(request.getPrUrl());
            link.reactivate();
        }
    }

    private boolean isRequestLinkEmpty(LinkUpdateRequest request) {
        String prUrl = request.getPrUrl();
        return prUrl == null || prUrl.isBlank();
    }

}
