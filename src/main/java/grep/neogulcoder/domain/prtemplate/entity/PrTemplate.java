package grep.neogulcoder.domain.prtemplate.entity;

import grep.neogulcoder.global.entity.BaseEntity;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String introduction;

    private String location;

    @Builder
    private PrTemplate(Long templateId, Long userId, String introduction, String location) {
        this.id = templateId;
        this.userId = userId;
        this.introduction = introduction;
        this.location = location;
    }

    public static PrTemplate PrTemplateInit(Long userId, String introduction, String location) {
        return PrTemplate.builder()
            .userId(userId)
            .introduction(introduction != null ? introduction : "안녕하세요! 저는 oo입니다.")
            .location(location)
            .build();
    }

    public void update(String location) {
        this.location = location;
    }

    public void delete() {
        this.activated = false;
    }

    protected PrTemplate() {}

    public void updateIntroduction(String introduction) {
        this.introduction = introduction;
    }
}