package grep.neogul_coder.domain.studypost;

import grep.neogul_coder.global.response.code.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum StudyPostErrorCode implements ErrorCode {
    NOT_FOUND_STUDY(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.name(), "해당 스터디를 찾지 못했습니다."),
    NOT_FOUND_POST(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.name(), "해당 스터디 게시글을 찾지 못했습니다.");

    private static final String BASIC_ERROR_NAME = "STUDY_POST";
    private final HttpStatus status;
    private final String code;
    private final String message;

    StudyPostErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = BASIC_ERROR_NAME + ": " + code;
        this.message = message;
    }
}
