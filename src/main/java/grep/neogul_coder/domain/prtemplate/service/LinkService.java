package grep.neogul_coder.domain.prtemplate.service;

import grep.neogul_coder.domain.prtemplate.controller.dto.request.LinkUpdateRequest;
import grep.neogul_coder.domain.prtemplate.entity.Link;
import grep.neogul_coder.domain.prtemplate.repository.LinkRepository;
import grep.neogul_coder.domain.prtemplate.repository.PrTemplateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LinkService {

    private final LinkRepository linkRepository;
    private final PrTemplateRepository prTemplateRepository;

    public void deleteByPrId(Long prId) {
        Link link = linkRepository.findByPrId(prId);
        if(link == null){
            return;
        }
        link.delete();
    }

    public void update(Long prId, List<LinkUpdateRequest> prUrls) {
        List<Link> existingLinks = linkRepository.findAllByPrId(prId);
        for (Link link : existingLinks) {
            link.delete();
        }
        linkRepository.saveAll(existingLinks);

        for (LinkUpdateRequest request : prUrls) {
            Link link = Link.builder()
                    .prId(prId)
                    .urlName(request.getUrlName())
                    .prUrl(request.getPrUrl())
                    .activated(true)
                    .build();

            linkRepository.save(link);
        }
    }
}
