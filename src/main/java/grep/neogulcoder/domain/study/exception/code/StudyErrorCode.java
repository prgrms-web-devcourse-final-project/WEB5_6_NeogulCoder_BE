package grep.neogulcoder.domain.study.exception.code;

import grep.neogulcoder.global.response.code.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum StudyErrorCode implements ErrorCode {

    STUDY_NOT_FOUND("S001",HttpStatus.NOT_FOUND,"스터디를 찾을 수 없습니다."),
    STUDY_MEMBER_NOT_FOUND("S002", HttpStatus.NOT_FOUND, "스터디 멤버가 아닙니다."),
    EXTENDED_STUDY_NOT_FOUND("S003", HttpStatus.NOT_FOUND, "연장된 스터디를 찾을 수 없습니다."),
    STUDY_LEADER_NOT_FOUND("S004", HttpStatus.NOT_FOUND, "스터디장을 찾을 수 없습니다."),

    STUDY_CREATE_LIMIT_EXCEEDED("S005", HttpStatus.BAD_REQUEST, "종료되지 않은 스터디는 최대 10개까지만 생성할 수 있습니다."),
    STUDY_ALREADY_STARTED("S006", HttpStatus.BAD_REQUEST, "이미 시작된 스터디의 시작일은 변경할 수 없습니다."),
    STUDY_DELETE_NOT_ALLOWED("S007", HttpStatus.BAD_REQUEST, "스터디 멤버가 1명일 때만 삭제할 수 있습니다."),

    STUDY_EXTENSION_NOT_AVAILABLE("S008", HttpStatus.BAD_REQUEST, "스터디 연장은 스터디 종료일 7일 전부터 가능합니다."),
    END_DATE_BEFORE_ORIGIN_STUDY("S009", HttpStatus.BAD_REQUEST, "연장 스터디 종료일은 기존 스터디 종료일 이후여야 합니다."),
    ALREADY_EXTENDED_STUDY("S010", HttpStatus.BAD_REQUEST, "이미 연장된 스터디입니다."),
    ALREADY_REGISTERED_PARTICIPATION("S011", HttpStatus.BAD_REQUEST, "연장 스터디 참여는 한 번만 등록할 수 있습니다."),

    NOT_STUDY_LEADER("S012", HttpStatus.FORBIDDEN, "스터디장만 접근이 가능합니다."),
    LEADER_CANNOT_LEAVE_STUDY("S013", HttpStatus.BAD_REQUEST, "스터디장은 스터디를 탈퇴할 수 없습니다."),
    LEADER_CANNOT_DELEGATE_TO_SELF("S014", HttpStatus.BAD_REQUEST, "자기 자신에게는 스터디장 위임이 불가능합니다."),

    STUDY_MEMBER_COUNT_UPDATE_FAILED("S015", HttpStatus.CONFLICT, "스터디 인원 수 업데이트에 실패했습니다. 잠시 후 다시 시도해주세요.");

    private final String code;
    private final HttpStatus status;
    private final String message;

    StudyErrorCode(String code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
