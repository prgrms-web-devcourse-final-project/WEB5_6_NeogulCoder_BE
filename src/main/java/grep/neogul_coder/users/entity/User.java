package grep.neogul_coder.users.entity;

import grep.neogul_coder.global.auth.code.Role;
import grep.neogul_coder.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "oauth_id")
    private String oauthId;

    @Column(name = "oauth_provider")
    private String oauthProvider;

    @NotBlank
    @Email
    String email;

    @NotBlank
    String password;

    @NotBlank
    String nickname;

    String profile_image_url;

    @Enumerated(EnumType.STRING)
    Role role;

    Boolean isDeleted;

    protected User(Long id, String oauthId, String oauthProvider, String email, String password,
        String nickname, String profile_image_url, Role role, Boolean isDeleted) {
        this.id = id;
        this.oauthId = oauthId;
        this.oauthProvider = oauthProvider;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profile_image_url = profile_image_url;
        this.role = role;
        this.isDeleted = isDeleted;
    }

    protected User() {
    }
}
