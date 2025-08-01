package grep.neogulcoder.domain.users.entity;

import grep.neogulcoder.global.auth.code.Role;
import grep.neogulcoder.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Table(name = "member")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String oauthId;

    private String oauthProvider;

    @Email
    private String email;

    private String password;

    private String nickname;

    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static User UserInit(String email, String password, String nickname) {
        return User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .role(Role.ROLE_USER)
                .build();
    }

    public void updateProfile(String nickname, String profileImageUrl) {
        this.nickname = nickname;
        if (profileImageUrl != null) {
            this.profileImageUrl = profileImageUrl;
        }
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void delete() {
        this.activated = false;
    }

    @Builder
    private User(Long id, String oauthId, String oauthProvider, String email, String password,
                 String nickname, String profileImageUrl, Role role) {
        this.id = id;
        this.oauthId = oauthId;
        this.oauthProvider = oauthProvider;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.role = role;
    }

    protected User() {
    }

    public boolean isNotEqualsNickname(String nickname) {
        return !this.nickname.equals(nickname);
    }

    public void reactive() {
        this.activated = true;
    }
}
