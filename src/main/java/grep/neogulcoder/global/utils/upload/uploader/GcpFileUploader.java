package grep.neogulcoder.global.utils.upload.uploader;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import grep.neogulcoder.global.utils.upload.exception.FileUploadException;
import grep.neogulcoder.global.utils.upload.AbstractFileManager;
import grep.neogulcoder.global.utils.upload.exception.FileUploadErrorCode;
import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Profile("prod")
@Slf4j
public class GcpFileUploader extends AbstractFileManager {

    @Value("${google.cloud.storage.bucket}")
    private String bucket;

    private static final String STORAGE_BASE_URL = "https://storage.googleapis.com/";

    // GCP Cloud Storage 에 파일을 업로드
    @Override
    protected void uploadFile(MultipartFile file, String fullPath) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            Storage storage = StorageOptions.getDefaultInstance().getService();
            BlobId blobId = BlobId.of(bucket, fullPath);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setContentType(file.getContentType())
                    .build();
            storage.createFrom(blobInfo, inputStream);
        } catch (IOException e) {
            log.error("GCP 파일 업로드 실패 - 원본 파일명: {}", file.getOriginalFilename(), e);
            throw new FileUploadException(FileUploadErrorCode.GCP_UPLOAD_FAIL, e);
        }
    }

    // 업로드된 파일의 GCP Storage URL 을 생성
    // 전체 파일 URL (https://storage.googleapis.com/bucket/경로/파일명)
    @Override
    public String generateFileUrl(String savePath, String renameFileName) {
        return STORAGE_BASE_URL + bucket + "/" + savePath + "/" + renameFileName;
    }
}
