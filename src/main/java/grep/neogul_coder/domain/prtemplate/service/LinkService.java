package grep.neogul_coder.domain.prtemplate.service;

import grep.neogul_coder.domain.prtemplate.entity.Link;
import grep.neogul_coder.domain.prtemplate.repository.LinkRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class LinkService {

    private final LinkRepository linkRepository;

    public void deleteByUserId(Long userId){
        Link link = linkRepository.findByUserId(userId);
        link.delete();
    }
}
