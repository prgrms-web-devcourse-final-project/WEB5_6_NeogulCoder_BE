package grep.neogulcoder.domain.buddy.enums;

import lombok.Getter;

@Getter
public enum BuddyEnergyReason {

    SIGN_UP("회원가입", 50),
    POSITIVE_REVIEW("긍정 리뷰", 1),
    NEGATIVE_REVIEW("부정 리뷰", -1),
    STUDY_DONE("스터디 완료", 1),
    TEAM_LEADER_BONUS("스터디 팀장 보너스", 2);


    private final String description;
    private final int point;

    BuddyEnergyReason(String description, int point) {
        this.description = description;
        this.point = point;
    }

}
