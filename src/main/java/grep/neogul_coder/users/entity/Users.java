package grep.neogul_coder.users.entity;

import grep.neogul_coder.global.auth.code.Role;
import grep.neogul_coder.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Entity
@Getter
public class Users extends BaseEntity {

    @Id
    Long id;

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

}
