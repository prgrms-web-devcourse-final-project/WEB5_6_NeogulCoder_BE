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
public class Link extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long prId;

    private String prUrl;

    private String urlName;

    @Builder
    private Link(Long id, Long prId, String prUrl, String urlName, Boolean activated) {
        this.id = id;
        this.prId = prId;
        this.prUrl = prUrl;
        this.urlName = urlName;
        this.activated = activated;
    }

    public static Link LinkInit(Long prId, String prUrl, String urlName) {
        return Link.builder()
            .prId(prId)
            .prUrl(prUrl)
            .urlName(urlName)
            .activated(true)
            .build();
    }

    public void delete() {
        this.activated = false;
    }

    protected Link() {
    }
}