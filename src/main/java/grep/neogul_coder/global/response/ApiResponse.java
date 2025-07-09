package grep.neogul_coder.global.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponse<T> {

    @Schema(example = "OK")
    private HttpStatus status;

    @Schema(example = "200")
    private int code;

    @Schema(example = "성공적으로 저장 되었습니다.")
    private String message;

    private T data;

    public ApiResponse(HttpStatus status, String message, T data) {
        this.status = status;
        this.code = status.value();
        this.data = data;
        this.message = message;
    }

    public static <T> ApiResponse<T> of(HttpStatus status, String message, T data){
        return new ApiResponse<>(status, message, data);
    }

    public static <T> ApiResponse<T> of(HttpStatus status, T data){
        return ApiResponse.of(status,status.name(), data);
    }

    public static <T> ApiResponse<T> ok(T data){
        return ApiResponse.of(HttpStatus.OK, data);
    }

    public static <T> ApiResponse<T> ok(String message){
        return ApiResponse.of(HttpStatus.OK, message, null);
    }
}
