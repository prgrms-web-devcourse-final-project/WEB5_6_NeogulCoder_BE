package grep.neogul_coder.global.exception;

import grep.neogul_coder.global.response.CommonCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonException extends RuntimeException {

    private final CommonCode code;

    public CommonException(CommonCode code) {
        this.code = code;
    }

    public CommonException(CommonCode code, Exception e) {
        this.code = code;
        log.error(e.getMessage(), e);
    }

    public CommonCode code() {
        return code;
    }
}
