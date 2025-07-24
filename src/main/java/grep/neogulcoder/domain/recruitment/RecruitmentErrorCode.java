package grep.neogulcoder.domain.recruitment;

import grep.neogulcoder.global.response.code.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum RecruitmentErrorCode implements ErrorCode {
    BAD_REQUEST_STUDY_CATEGORY(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.name(), "요청한 스터디 카테고리를 찾지 못했습니다."),
    BAD_REQUEST_STUDY_TYPE(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.name(), "요청한 스터디 타입을 찾지 못했습니다."),
    BAD_REQUEST_STATUS(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.name(), "요청한 모집글 상태를 찾지 못했습니다."),
    NOT_STUDY_LEADER(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.name(), "스터디의 리더가 아닙니다."),

    NOT_FOUND(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.name(), "모집글을 찾지 못했습니다."),
    NOT_FOUND_STUDY_MEMBER(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.name(), "스터디에 참여하고 있지 않은 회원 입니다."),
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.name(), "댓글을 찾지 못했습니다"),
    NOT_OWNER(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.name(), "모집글을 등록한 당사자가 아닙니다.");

    private static final String BASIC_MESSAGE = "RECRUITMENT";
    private final HttpStatus status;
    private final String code;
    private final String message;

    RecruitmentErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = BASIC_MESSAGE + " " + code;
        this.message = message;
    }

}
