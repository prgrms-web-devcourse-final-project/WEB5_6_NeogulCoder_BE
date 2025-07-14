package grep.neogul_coder.domain.review.controller.dto.response;

import grep.neogul_coder.domain.users.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class ReviewTargetUsersInfo {

    @Schema(example = "[ {nickname: 짱구}, {nickname: 철수} ]", description = "회원 이름들")
    private List<TargetUserInfo> userInfos;

    @Schema(example = "자바 스터디", description = "스터디 이름")
    private String studyName;

    private ReviewTargetUsersInfo(List<TargetUserInfo> userInfos) {
        this.userInfos = userInfos;
    }

    public ReviewTargetUsersInfo() {
    }

    public static ReviewTargetUsersInfo of(List<User> users) {
        List<TargetUserInfo> targetUserInfos = users.stream()
                .map(user -> new TargetUserInfo(user.getNickname()))
                .toList();

        return new ReviewTargetUsersInfo(targetUserInfos);
    }

    @Getter
    static class TargetUserInfo {
        @Schema(example = "짱구", description = "리뷰 대상 닉네임")
        private String nickname;

        private TargetUserInfo(String nickname) {
            this.nickname = nickname;
        }
    }
}
