package grep.neogulcoder.domain.timevote.exception.code;

import grep.neogulcoder.global.response.code.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum TimeVoteErrorCode implements ErrorCode {

  // Access To Vote (e.g. 멤버 아님 등 접근 문제)
  STUDY_MEMBER_NOT_FOUND("ATV_001", HttpStatus.NOT_FOUND, "해당 스터디의 멤버가 아닙니다."),
  TIME_VOTE_PERIOD_NOT_FOUND("ATV_002", HttpStatus.NOT_FOUND, "해당 스터디에 대한 투표 기간이 존재하지 않습니다."),

  // Time Vote Period (e.g. 기간 생성 관련)
  FORBIDDEN_TIME_VOTE_CREATE("TVP_001", HttpStatus.BAD_REQUEST, "모임 일정 조율 투표 생성은 스터디장만 가능합니다."),
  STUDY_NOT_FOUND("TVP_002", HttpStatus.NOT_FOUND, "해당 스터디를 찾을 수 없습니다."),
  INVALID_TIME_VOTE_PERIOD("TVP_003", HttpStatus.BAD_REQUEST, "모일 일정 조율 기간은 최대 7일까지 설정할 수 있습니다."),
  TIME_VOTE_PERIOD_START_DATE_IN_PAST("TVP_004", HttpStatus.BAD_REQUEST, "투표 시작일은 현재 시각보다 이전일 수 없습니다."),
  TIME_VOTE_INVALID_DATE_RANGE("TVP_005", HttpStatus.BAD_REQUEST, "종료일은 시작일보다 이후여야 합니다."),

  // Time Vote, Time Vote Stats (e.g. 투표 기간 관련)
  TIME_VOTE_OUT_OF_RANGE("TVAS_001", HttpStatus.BAD_REQUEST, "선택한 시간이 투표 기간을 벗어났습니다."),

  // Time Vote (e.g. 투표 제출, 수정)
  TIME_VOTE_ALREADY_SUBMITTED("TV_001", HttpStatus.CONFLICT, "이미 투표를 제출했습니다. PUT 요청으로 기존의 제출한 투표를 수정하세요."),
  TIME_VOTE_NOT_FOUND("TV_002", HttpStatus.BAD_REQUEST, "시간 투표 이력이 존재하지 않습니다."),
  TIME_VOTE_DUPLICATED_TIME_SLOT("TV_003", HttpStatus.BAD_REQUEST, "중복된 시간이 포함되어 있습니다."),
  TIME_VOTE_PERIOD_EXPIRED("TV_004", HttpStatus.BAD_REQUEST, "투표 기간이 만료되었습니다."),
  TIME_VOTE_EMPTY("TV_005", HttpStatus.BAD_REQUEST, "한 개 이상의 시간을 선택해주세요."),

  // Time Vote Stats (e.g. 통계 충돌 등)
  TIME_VOTE_STAT_CONFLICT("TVS_001", HttpStatus.CONFLICT, "투표 통계 저장 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요."),
  TIME_VOTE_THREAD_INTERRUPTED("TVS_002", HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다. 다시 시도해주세요."),
  TIME_VOTE_STAT_FATAL("TVS_003", HttpStatus.INTERNAL_SERVER_ERROR, "투표 통계 처리 중 오류가 발생했습니다. 관리자에게 문의하세요.");

  private final String code;
  private final HttpStatus status;
  private final String message;

  TimeVoteErrorCode(String code, HttpStatus status, String message) {
    this.code = code;
    this.status = status;
    this.message = message;
  }
}
