package grep.neogul_coder.global.utils.upload;

public record FileUploadResponse(
    String originFileName, // 원본 파일명
    String renameFileName, // UUID로 변경된 파일명
    FileUsageType usageType, // 파일 사용 목적
    String savePath, // 저장 경로
    String fileUrl, // 전체 URL
    Long uploaderId, // 업로더 ID
    Long usageRefId // 파일이 참조되는 도메인 ID
) {

}
