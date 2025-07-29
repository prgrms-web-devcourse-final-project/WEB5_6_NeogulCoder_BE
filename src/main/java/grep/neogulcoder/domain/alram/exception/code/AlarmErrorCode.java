package grep.neogulcoder.domain.alram.exception.code;

import grep.neogulcoder.global.response.code.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AlarmErrorCode implements ErrorCode {

    ALARM_NOT_FOUND("A001",HttpStatus.NOT_FOUND,"알람을 찾을 수 없습니다."),
    ALREADY_CHECKED("A002",HttpStatus.BAD_REQUEST,"이미 읽은 알림입니다."),

    INVALID_ALARM_TYPE("A003", HttpStatus.BAD_REQUEST, "지원하지 않는 알림 타입입니다."),
    INVALID_DOMAIN_TYPE_FOR_STUDY_INVITE_ALARM("A004", HttpStatus.BAD_REQUEST, "스터디 초대 알림은 STUDY 도메인에만 해당됩니다."),
    INVALID_DOMAIN_TYPE_FOR_STUDY_EXTENSION_ALARM("A005", HttpStatus.BAD_REQUEST, "스터디 연장 알림은 STUDY 도메인에만 해당됩니다."),
    INVALID_DOMAIN_TYPE_FOR_STUDY_ENDING_ALARM("A006", HttpStatus.BAD_REQUEST, "스터디 종료 7일 전 연장 가능 알림은 STUDY 도메인에만 해당됩니다."),
    INVALID_DOMAIN_TYPE_FOR_TIME_VOTE_ALARM("A007", HttpStatus.BAD_REQUEST, "시간 투표 요청은 TIME_VOTE 도메인에만 해당됩니다."),
    INVALID_DOMAIN_TYPE_FOR_APPLICATION_APPROVED_ALARM("A008", HttpStatus.BAD_REQUEST, "스터디 신청 승인 알림은 STUDY_APPLICATION 도메인에만 해당됩니다."),
    INVALID_DOMAIN_TYPE_FOR_APPLICATION_REJECTED_ALARM("A009", HttpStatus.BAD_REQUEST, "스터디 신청 거절 알림은 STUDY_APPLICATION 도메인에만 해당됩니다."),
    INVALID_DOMAIN_TYPE_FOR_RECRUITMENT_ALARM("A010", HttpStatus.BAD_REQUEST, "스터디 신청 알림은 RECRUITMENT_POST 도메인에만 해당됩니다.");

    private final String code;
    private final HttpStatus status;
    private final String message;

    AlarmErrorCode(String code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
