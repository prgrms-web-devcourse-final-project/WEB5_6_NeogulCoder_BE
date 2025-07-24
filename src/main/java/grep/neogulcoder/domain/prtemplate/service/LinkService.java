package grep.neogulcoder.domain.prtemplate.service;

import grep.neogulcoder.domain.prtemplate.controller.dto.request.LinkUpdateRequest;
import grep.neogulcoder.domain.prtemplate.entity.Link;
import grep.neogulcoder.domain.prtemplate.repository.LinkRepository;
import grep.neogulcoder.domain.quiz.exception.code.QuizErrorCode;
import grep.neogulcoder.global.exception.business.BusinessException;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class LinkService {

    private final LinkRepository linkRepository;

    public void deleteByUserId(Long userId) {
        List<Link> links = linkRepository.findAllByUserId(userId);
        for (Link link : links) {
            link.delete();
        }
    }

    public void update(Long userId, List<LinkUpdateRequest> requests) {
        List<Link> links = linkRepository.findAllByUserId(userId);

        if (requests.size() != 2) {
            throw new BusinessException(QuizErrorCode.NEED_CORRECT_COUNT);
        }

        for (int i = 0; i < links.size(); i++) {
            applyRequestToLink(links.get(i), requests.get(i));
        }
    }

    private void applyRequestToLink(Link link, LinkUpdateRequest request) {
        if (Link.isRequestLinkEmpty(request)) {
            link.delete();
        } else {
            link.updateUrlName(request.getUrlName());
            link.updatePrUrl(request.getPrUrl());
            link.reactivate();
        }
    }

}