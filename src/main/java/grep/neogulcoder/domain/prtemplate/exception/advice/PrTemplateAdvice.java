package grep.neogulcoder.domain.prtemplate.exception.advice;

import grep.neogulcoder.domain.prtemplate.exception.TemplateNotFoundException;
import grep.neogulcoder.domain.prtemplate.exception.code.PrTemplateErrorCode;
import grep.neogulcoder.global.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PrTemplateAdvice {

    @ExceptionHandler(TemplateNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> templateNotFoundException(TemplateNotFoundException ex) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.errorWithoutData(PrTemplateErrorCode.TEMPLATE_NOT_FOUND));
    }

}
