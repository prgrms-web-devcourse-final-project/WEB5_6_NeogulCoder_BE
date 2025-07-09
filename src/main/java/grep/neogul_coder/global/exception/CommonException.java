package grep.neogul_coder.global.exception;

import grep.neogul_coder.global.response.ResponseCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonException extends RuntimeException {

    private final ResponseCode code;

    public CommonException(ResponseCode code) {
        this.code = code;
    }

    public CommonException(ResponseCode code, Exception e) {
        this.code = code;
        log.error(e.getMessage(), e);
    }

    public ResponseCode code() {
        return code;
    }
}
