package grep.neogul_coder.global.response;

import grep.neogul_coder.global.response.code.Code;
import grep.neogul_coder.global.response.code.CommonCode;
import lombok.Getter;

@Getter
public class ApiResponse<T> {

    private String code;
    private String message;
    private T data;

    private ApiResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private static <T> ApiResponse<T> of(String code, String message, T data){
        return new ApiResponse<>(code,message,data);
    }

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.of(CommonCode.OK.getCode(), CommonCode.OK.getMessage(), data);
    }

    public static <T> ApiResponse<T> successWithCode(T data){
        return ApiResponse.of(CommonCode.OK.getCode(), CommonCode.OK.getMessage(), data);
    }

    public static <T> ApiResponse<T> noContent(){
        return ApiResponse.of(CommonCode.NO_CONTENT.getCode(), CommonCode.NO_CONTENT.getMessage(), null);
    }

    public static <T> ApiResponse<T> create(){
        return ApiResponse.of(CommonCode.CREATED.getCode(), CommonCode.CREATED.getMessage(), null);
    }

    public static <T> ApiResponse<T> badRequest() {
        return ApiResponse.of(CommonCode.BAD_REQUEST.getCode(), CommonCode.BAD_REQUEST.getMessage(), null);
    }

    public static <T> ApiResponse<T> errorWithoutData(Code code) {
        return ApiResponse.of(code.getCode(), code.getMessage(), null);
    }

    public static <T> ApiResponse<T> error(Code code, T data) {
        return ApiResponse.of(code.getCode(), code.getMessage(), data);
    }
}
