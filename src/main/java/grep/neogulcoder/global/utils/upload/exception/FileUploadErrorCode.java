package grep.neogulcoder.global.utils.upload.exception;

import grep.neogulcoder.global.response.code.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum FileUploadErrorCode implements ErrorCode {

  FILE_EMPTY("UPLOAD_001", HttpStatus.BAD_REQUEST, "업로드할 파일이 비어있습니다."),
  FILE_NAME_INVALID("UPLOAD_002", HttpStatus.BAD_REQUEST, "파일 이름이 유효하지 않습니다."),
  FILE_UPLOAD_FAIL("UPLOAD_003", HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드 중 오류가 발생했습니다."),
  FILE_TYPE_INVALID("UPLOAD_004", HttpStatus.BAD_REQUEST, "지원하지 않는 파일 타입입니다."),
  FILE_NOT_FOUND("UPLOAD_005", HttpStatus.NOT_FOUND, "파일을 찾을 수 없습니다."),

  GCP_UPLOAD_FAIL("UPLOAD_GCP_001", HttpStatus.INTERNAL_SERVER_ERROR, "GCP 업로드 중 오류가 발생했습니다."),
  AWS_UPLOAD_FAIL("UPLOAD_AWS_001", HttpStatus.INTERNAL_SERVER_ERROR, "AWS 업로드 중 오류가 발생했습니다."),
  LOCAL_UPLOAD_FAIL("UPLOAD_LOCAL_001", HttpStatus.INTERNAL_SERVER_ERROR, "로컬 업로드 중 오류가 발생했습니다.");

  private final String code;
  private final HttpStatus status;
  private final String message;

  FileUploadErrorCode(String code, HttpStatus status, String message) {
    this.code = code;
    this.status = status;
    this.message = message;
  }

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public HttpStatus getStatus() {
    return status;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
