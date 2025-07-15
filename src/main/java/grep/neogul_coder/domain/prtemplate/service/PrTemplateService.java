package grep.neogul_coder.domain.prtemplate.service;

import grep.neogul_coder.domain.prtemplate.controller.dto.response.PrPageResponse;
import grep.neogul_coder.domain.prtemplate.entity.Link;
import grep.neogul_coder.domain.prtemplate.entity.PrTemplate;
import grep.neogul_coder.domain.prtemplate.exception.code.PrTemplateErrorCode;
import grep.neogul_coder.domain.prtemplate.repository.PrTemplateRepository;
import grep.neogul_coder.global.exception.business.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class PrTemplateService {

    private final PrTemplateRepository prTemplateRepository;

    public void deleteByUserId(Long userId) {
        PrTemplate prTemplate = prTemplateRepository.findByUserId(userId);
        prTemplate.delete();
    }

    public void update(Long id, String location) {
        PrTemplate prTemplate = prTemplateRepository.findById(id).orElseThrow(
            () -> new NotFoundException(PrTemplateErrorCode.TEMPLATE_NOT_FOUND, "템플릿이 존재하지 않습니다."));
        prTemplate.update(location);
    }

    public void updateIntroduction(Long id, String introduction){
        PrTemplate prTemplate = prTemplateRepository.findById(id).orElseThrow(
            () -> new NotFoundException(PrTemplateErrorCode.TEMPLATE_NOT_FOUND, "템플릿이 존재하지 않습니다."));
        prTemplate.updateIntroduction(introduction);
    }
}