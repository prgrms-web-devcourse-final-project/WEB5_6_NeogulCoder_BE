package grep.neogul_coder.global.response.code;

import org.springframework.http.HttpStatus;

public interface Code {
    String getCode();
    HttpStatus getStatus();
    String getMessage();
}
