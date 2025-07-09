package grep.neogul_coder.domain.prtemplate.entity;

import grep.neogul_coder.global.entity.BaseEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;

public class PrTemplate extends BaseEntity {

    @Id
    Long templateId;

    @NotNull
    Long userId;

    @NotNull
    String introductionContent;

    String introductionDetail;

    String location;

}
