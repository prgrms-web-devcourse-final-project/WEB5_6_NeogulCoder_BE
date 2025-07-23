package grep.neogulcoder.global.utils.upload.exception;

public class FileUploadException extends RuntimeException {

    private final FileUploadErrorCode errorCode;

    public FileUploadException(FileUploadErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
    
    public FileUploadException(FileUploadErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }

    public FileUploadException(FileUploadErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public FileUploadException(FileUploadErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public FileUploadErrorCode getErrorCode() {
        return errorCode;
    }
}
