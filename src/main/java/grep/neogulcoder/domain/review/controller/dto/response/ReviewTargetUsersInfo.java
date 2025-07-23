package grep.neogulcoder.domain.review.controller.dto.response;

import grep.neogulcoder.domain.users.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class ReviewTargetUsersInfo {

    @Schema(example = "[ { userId: 1, nickname: 짱구 }, { userId: 2, nickname: 철수 } ]", description = "회원 이름들")
    private final List<TargetUserInfo> userInfos;

    private ReviewTargetUsersInfo(List<TargetUserInfo> userInfos) {
        this.userInfos = userInfos;
    }

    public static ReviewTargetUsersInfo of(List<User> users) {
        List<TargetUserInfo> targetUserInfos = users.stream()
                .map(user -> new TargetUserInfo(user.getId(), user.getNickname()))
                .toList();

        return new ReviewTargetUsersInfo(targetUserInfos);
    }

    @ToString
    @Getter
    static class TargetUserInfo {

        @Schema(example = "3", description = "회원 ID")
        private long userId;

        @Schema(example = "짱구", description = "리뷰 대상 닉네임")
        private String nickname;

        public TargetUserInfo(long userId, String nickname) {
            this.userId = userId;
            this.nickname = nickname;
        }
    }
}
