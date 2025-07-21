package grep.neogul_coder.global.utils.upload;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FileUploadResponse {
    private String originFileName; // 원본 파일명
    private String renameFileName; // UUID로 변경된 파일명
    private FileUsageType usageType; // 파일 사용 목적
    private String savePath; // 저장 경로
    private String fileUrl; // 전체 URL
    private Long uploaderId; // 업로더 ID
    private Long usageRefId; // 파일이 참조되는 도메인 ID

    @Builder
    public FileUploadResponse(String originFileName, String renameFileName, FileUsageType usageType, String savePath, String fileUrl, Long uploaderId, Long usageRefId) {
        this.originFileName = originFileName;
        this.renameFileName = renameFileName;
        this.usageType = usageType;
        this.savePath = savePath;
        this.fileUrl = fileUrl;
        this.uploaderId = uploaderId;
        this.usageRefId = usageRefId;
    }
}
