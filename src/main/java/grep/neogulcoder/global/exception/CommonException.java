package grep.neogulcoder.global.exception;

import grep.neogulcoder.global.response.code.Code;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonException extends RuntimeException {

    private final Code code;

    public CommonException(Code code) {
        this.code = code;
    }

    public CommonException(Code code, Exception e) {
        this.code = code;
        log.error(e.getMessage(), e);
    }

    public Code code() {
        return code;
    }
}
