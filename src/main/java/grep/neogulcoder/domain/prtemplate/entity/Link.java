package grep.neogulcoder.domain.prtemplate.entity;

import grep.neogulcoder.domain.prtemplate.controller.dto.request.LinkUpdateRequest;
import grep.neogulcoder.global.entity.BaseEntity;
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

    private Long userId;

    private String prUrl;

    private String urlName;

    @Builder
    private Link(Long id, Long prId, String prUrl, String urlName, Boolean activated) {
        this.id = id;
        this.userId = prId;
        this.prUrl = prUrl;
        this.urlName = urlName;
        this.activated = activated;
    }

    public static Link LinkInit(Long prId, String prUrl, String urlName) {
        return Link.builder()
            .prId(prId)
            .prUrl(prUrl)
            .urlName(urlName)
            .activated(false)
            .build();
    }

    public static boolean isRequestLinkEmpty(LinkUpdateRequest request) {
        String prUrl = request.getPrUrl();
        return prUrl == null || prUrl.isBlank();
    }

    protected Link() {
    }

    public void delete() {
        this.activated = false;
    }

    public void reactivate() {
        this.activated = true;
    }

    public void updateUrlName(String urlName) {
        this.urlName = urlName;
    }

    public void updatePrUrl(String prUrl) {
        this.prUrl = prUrl;
    }
}