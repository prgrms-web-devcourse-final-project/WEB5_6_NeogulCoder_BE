package grep.neogul_coder.domain.prtemplate.entity;

import grep.neogul_coder.global.entity.BaseEntity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;

public class PrTemplate extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long templateId;

    Long userId;

    String introductionContent;

    String introductionDetail;

    String location;

}
