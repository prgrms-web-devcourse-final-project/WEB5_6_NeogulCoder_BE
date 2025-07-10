package grep.neogul_coder.global.response;

public record ApiResponse<T>(
    String code,
    String message,
    T data
){

    public static <T> ApiResponse<T> success(T data){
        return new ApiResponse<>(ResponseCode.OK.getCode(), ResponseCode.OK.getMessage(), data);
    }

    public static <T> ApiResponse<T> noContent(String message){
        return new ApiResponse<>(ResponseCode.OK.getCode(), message, null);
    }

    public static <T> ApiResponse<T> badRequest(){
        return new ApiResponse<>(ResponseCode.BAD_REQUEST.getCode(), ResponseCode.BAD_REQUEST.getMessage(), null);
    }

    public static <T> ApiResponse<T> error(ResponseCode code){
        return new ApiResponse<>(code.getCode(), code.getMessage(), null);
    }

    public static <T> ApiResponse<T> error(ResponseCode code, T data){
        return new ApiResponse<>(code.getCode(), code.getMessage(), data);
    }

    public static <T> ApiResponse<T> error(String message){
        return new ApiResponse<>(ResponseCode.SECURITY_INCIDENT.getCode(), message, null);
    }
}
