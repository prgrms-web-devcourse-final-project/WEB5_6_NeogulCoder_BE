package grep.neogulcoder.domain.timevote.exception.code;

import grep.neogulcoder.global.response.code.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum TimeVoteErrorCode implements ErrorCode {

  FORBIDDEN_TIME_VOTE_CREATE("T001", HttpStatus.BAD_REQUEST, "모임 일정 조율 투표 생성은 스터디장만 가능합니다."),
  STUDY_NOT_FOUND("T002", HttpStatus.NOT_FOUND, "해당 스터디를 찾을 수 없습니다."),
  STUDY_MEMBER_NOT_FOUND("T003", HttpStatus.NOT_FOUND, "해당 스터디의 멤버가 아닙니다."),
  TIME_VOTE_PERIOD_NOT_FOUND("T004", HttpStatus.NOT_FOUND, "해당 스터디에 대한 투표 기간이 존재하지 않습니다."),
  INVALID_TIME_VOTE_PERIOD("T005", HttpStatus.BAD_REQUEST, "모일 일정 조율 기간은 최대 7일까지 설정할 수 있습니다."),
  TIME_VOTE_ALREADY_SUBMITTED("T006", HttpStatus.CONFLICT, "이미 투표를 제출했습니다. PUT 요청으로 기존의 제출한 투표를 수정하세요."),
  TIME_VOTE_OUT_OF_RANGE("T007", HttpStatus.BAD_REQUEST, "선택한 시간이 투표 기간을 벗어났습니다."),
  TIME_VOTE_NOT_FOUND("T008", HttpStatus.BAD_REQUEST, "시간 투표 이력이 존재하지 않습니다."),
  TIME_VOTE_STAT_CONFLICT("T009", HttpStatus.CONFLICT, "투표 통계 저장 중 충돌이 발생했습니다. 다시 시도해주세요."),
  TIME_VOTE_THREAD_INTERRUPTED("T010", HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 스레드 오류가 발생했습니다. 다시 시도해주세요.");

  private final String code;
  private final HttpStatus status;
  private final String message;

  TimeVoteErrorCode(String code, HttpStatus status, String message) {
    this.code = code;
    this.status = status;
    this.message = message;
  }
}
