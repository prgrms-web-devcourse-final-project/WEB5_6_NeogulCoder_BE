package grep.neogul_coder.domain.prtemplate.service;

import grep.neogul_coder.domain.prtemplate.controller.dto.request.LinkUpdateRequest;
import grep.neogul_coder.domain.prtemplate.controller.dto.request.PrUpdateRequest;
import grep.neogul_coder.domain.prtemplate.entity.Link;
import grep.neogul_coder.domain.prtemplate.entity.PrTemplate;
import grep.neogul_coder.domain.prtemplate.exception.code.PrTemplateErrorCode;
import grep.neogul_coder.domain.prtemplate.repository.LinkRepository;
import grep.neogul_coder.domain.prtemplate.repository.PrTemplateRepository;
import grep.neogul_coder.global.exception.business.NotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class LinkService {

    private final LinkRepository linkRepository;
    private final PrTemplateRepository prTemplateRepository;

    public void deleteByPrId(Long prId) {
        Link link = linkRepository.findByPrId(prId);
        link.delete();
    }

    public void update(Long prId, List<LinkUpdateRequest> prUrls) {
        linkRepository.findByPrId(prId);

        for (LinkUpdateRequest request : prUrls) {
            Link link = Link.builder()
                .prId(prId)
                .prUrl(request.getPrUrl())
                .build();

            linkRepository.save(link);
        }
    }
}
