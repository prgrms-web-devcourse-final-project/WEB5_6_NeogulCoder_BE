package grep.neogul_coder.global.utils.upload.entity;

import grep.neogul_coder.global.entity.BaseEntity;
import grep.neogul_coder.global.utils.upload.FileUsageType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class UploadImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String originFileName;

    @Column(nullable = false)
    private String renameFileName;

    @Column(nullable = false, length = 1000)
    private String fileUrl;

    @Column(nullable = false)
    private String savePath;

    @Column(nullable = false)
    private Long uploaderId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FileUsageType usageType;

    private Long usageRefId; // 파일이 속한 도메인 ID(게시글 ID 등)

    @Builder
    public UploadImage(String originFileName, String renameFileName, String fileUrl, String savePath,
        Long uploaderId, FileUsageType usageType, Long usageRefId) {
        this.originFileName = originFileName;
        this.renameFileName = renameFileName;
        this.fileUrl = fileUrl;
        this.savePath = savePath;
        this.uploaderId = uploaderId;
        this.usageType = usageType;
        this.usageRefId = usageRefId;
    }

    public static UploadImage of(String originFileName, String renameFileName, String fileUrl,
        String savePath, Long uploaderId, FileUsageType usageType, Long usageRefId) {
        return builder()
            .originFileName(originFileName)
            .renameFileName(renameFileName)
            .fileUrl(fileUrl)
            .savePath(savePath)
            .uploaderId(uploaderId)
            .usageType(usageType)
            .usageRefId(usageRefId)
            .build();
    }

    // 파일의 참조 ID(usageRefId)를 변경(파일 이동 등)
    public void updateUsageRefId(Long usageRefId) {
        this.usageRefId = usageRefId;
    }

    // 파일의 저장 경로와 URL 을 변경(파일 이동 등)
    public void updateSavePathAndFileUrl(String savePath, String fileUrl) {
        this.savePath = savePath;
        this.fileUrl = fileUrl;
    }
}
