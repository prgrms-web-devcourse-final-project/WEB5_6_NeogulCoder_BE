package grep.neogul_coder.domain.users.controller.dto;

import lombok.Data;

@Data
public class UpdateProfileRequest {

    String nickname;
    String profileImgUrl;

}