package grep.neogul_coder.global.utils.upload.uploader;

import grep.neogul_coder.global.utils.upload.exception.FileUploadException;
import grep.neogul_coder.global.utils.upload.AbstractFileManager;
import grep.neogul_coder.global.utils.upload.exception.FileUploadErrorCode;
import java.io.File;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Profile({"local", "test"})
@Component
public class LocalFileUploader extends AbstractFileManager {

    @Value("${upload.path}")
    private String filePath;

    // 로컬 파일 시스템에 파일을 업로드
    @Override
    protected void uploadFile(MultipartFile file, String fullPath) throws IOException {
        File targetFile = new File(filePath + fullPath);
        File parentDir = targetFile.getParentFile();

        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            throw new FileUploadException(FileUploadErrorCode.LOCAL_UPLOAD_FAIL, e);
        }
    }

    // 업로드된 파일의 접근 URL 을 생성
    @Override
    public String generateFileUrl(String savePath, String renameFileName) {
        return "/upload/" + savePath + renameFileName;
    }
}
