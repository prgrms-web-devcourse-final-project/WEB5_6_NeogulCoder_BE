package grep.neogulcoder.domain.prtemplate.controller.dto.response;

import grep.neogulcoder.domain.buddy.controller.dto.response.BuddyEnergyResponse;
import grep.neogulcoder.domain.review.ReviewType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;

import lombok.*;


@Data
@Builder
@Schema(description = "회원 PR 응답 DTO")
public class PrPageResponse {

    @Schema(description = "유저 프로필 목록")
    private List<UserProfileDto> userProfiles;

    @Schema(description = "유저 위치 및 링크 목록")
    private List<UserLocationAndLink> userLocationAndLinks;

    @Schema(description = "버디 에너지 수치", example = "85")
    private BuddyEnergyResponse buddyEnergy;

    @Schema(description = "리뷰 타입 목록")
    private List<ReviewTypeDto> reviewTypes;

    @Schema(description = "리뷰 내용 목록")
    private List<ReviewContentDto> reviewContents;

    @Schema(description = "자기소개", example = "안녕하세요! 여행을 좋아하는 OO입니다.")
    private String introduction;

    @Data
    @Builder
    @Schema(description = "사용자 위치 및 링크 정보")
    public static class UserLocationAndLink {

        @Schema(description = "위치", example = "서울")
        private String location;

        @Schema(description = "링크 목록")
        private List<LinkInfo> links;

        @Data
        @Builder
        @Schema(description = "링크 정보")
        public static class LinkInfo {
            @Schema(description = "링크 이름", example = "인스타그램")
            private String linkName;

            @Schema(description = "링크 URL", example = "https://instagram.com/example")
            private String link;
        }
    }


    @Data
    @Builder
    @Schema(description = "유저 프로필 정보")
    public static class UserProfileDto {

        @Schema(description = "닉네임", example = "홍길동")
        private String nickname;

        @Schema(description = "프로필 이미지 URL", example = "https://example.com/profile.jpg")
        private String profileImgUrl;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "리뷰 타입 정보")
    public static class ReviewTypeDto {

        @Schema(description = "리뷰 유형", example = "친절함")
        private ReviewType reviewType;

        @Schema(description = "리뷰 개수", example = "12")
        private int reviewCount;

    }

    @Data
    @Builder
    @Schema(description = "리뷰 내용 정보")
    public static class ReviewContentDto {

        @Schema(description = "리뷰한 사용자 ID", example = "4")
        private Long reviewUserId;

        @Schema(description = "리뷰한 사용자 닉네임", example = "홍길동")
        private String reviewUserNickname;

        @Schema(description = "리뷰한 사용자 프로필 이미지 URL", example = "https://example.com/reviewer.jpg")
        private String reviewUserImgUrl;

        @Schema(description = "리뷰 코멘트", example = "정말 좋은 사람이에요!")
        private String reviewComment;

        @Schema(description = "리뷰 날짜", example = "2025-07-11")
        private LocalDate reviewDate;
    }

}
