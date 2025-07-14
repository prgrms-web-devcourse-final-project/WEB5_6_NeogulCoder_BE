package grep.neogul_coder.global.code;

import org.springframework.http.HttpStatus;

public interface Code {
    String getCode();
    HttpStatus getStatus();
    String getMessage();
}
