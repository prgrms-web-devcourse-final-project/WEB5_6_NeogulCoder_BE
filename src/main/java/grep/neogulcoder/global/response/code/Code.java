package grep.neogulcoder.global.response.code;

import org.springframework.http.HttpStatus;

public interface Code {
    String getCode();
    HttpStatus getStatus();
    String getMessage();
}
