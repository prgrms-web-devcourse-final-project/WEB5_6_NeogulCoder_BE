package grep.neogul_coder.domain.review.controller.dto.response;

import grep.neogul_coder.domain.study.Study;
import grep.neogul_coder.domain.users.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class ReviewTargetUsersInfo {

    @Schema(example = "[ { nickname: 짱구 }, { nickname: 철수 } ]", description = "회원 이름들")
    private final List<TargetUserInfo> userInfos;

    @Schema(example = "{ nickname: 자바 스터디, imageUrl: www.s3.com }")
    private final StudyInfo studyInfo;

    private ReviewTargetUsersInfo(List<TargetUserInfo> userInfos, StudyInfo studyInfo) {
        this.userInfos = userInfos;
        this.studyInfo = studyInfo;
    }

    public static ReviewTargetUsersInfo of(Study study, List<User> users) {
        List<TargetUserInfo> targetUserInfos = users.stream()
                .map(user -> new TargetUserInfo(user.getNickname()))
                .toList();

        StudyInfo studyInfo = new StudyInfo(study.getName(), study.getImageUrl());
        return new ReviewTargetUsersInfo(targetUserInfos, studyInfo);
    }

    @ToString
    @Getter
    static class TargetUserInfo {
        @Schema(example = "짱구", description = "리뷰 대상 닉네임")
        private String nickname;

        private TargetUserInfo(String nickname) {
            this.nickname = nickname;
        }
    }

    @ToString
    @Getter
    static class StudyInfo {
        @Schema(example = "자바 스터디", description = "스터디 이름")
        private String studyName;

        @Schema(example = "www.s3.com", description = "스터디 이미지 URL")
        private String imageUrl;

        private StudyInfo(String studyName, String imageUrl) {
            this.studyName = studyName;
            this.imageUrl = imageUrl;
        }
    }
}
