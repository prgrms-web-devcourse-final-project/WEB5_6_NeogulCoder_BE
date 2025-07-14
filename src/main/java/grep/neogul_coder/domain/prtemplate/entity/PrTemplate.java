package grep.neogul_coder.domain.prtemplate.entity;

import grep.neogul_coder.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class PrTemplate extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;

    private String introduction;

    private String location;

    private Boolean isDeleted;

    @Builder
    private PrTemplate(Long templateId, Long userId, String introduction, String location, Boolean isDeleted) {
        this.id = templateId;
        this.userId = userId;
        this.introduction = introduction;
        this.location = location;
        this.isDeleted = isDeleted;
    }

    public static PrTemplate PrTemplateInit(Long userId, String introduction, String location) {
        return PrTemplate.builder()
            .userId(userId)
            .introduction(introduction != null ? introduction : "안녕하세요! 저는 oo입니다.")
            .location(location)
            .build();
    }

    public void delete() {
        this.isDeleted = true;
    }

    protected PrTemplate() {}
}