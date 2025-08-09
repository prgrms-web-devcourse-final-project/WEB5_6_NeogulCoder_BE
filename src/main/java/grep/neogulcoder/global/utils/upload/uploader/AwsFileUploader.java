package grep.neogulcoder.global.utils.upload.uploader;

import grep.neogulcoder.global.utils.upload.AbstractFileManager;
import grep.neogulcoder.global.utils.upload.exception.FileUploadErrorCode;
import grep.neogulcoder.global.utils.upload.exception.FileUploadException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Component
@Profile("prod-aws")
public class AwsFileUploader extends AbstractFileManager {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region}")
    private String region;

    private S3Client createS3Client() {
        return S3Client.builder()
            .region(Region.of(region))
            .credentialsProvider(DefaultCredentialsProvider.create())
            .build();
    }

    @Override
    protected void uploadFile(MultipartFile file, String fullPath) {
        try (S3Client s3 = createS3Client();
             InputStream inputStream = file.getInputStream()) {

            PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(fullPath)
                .contentType(file.getContentType())
                .build();

            s3.putObject(putRequest, RequestBody.fromInputStream(inputStream, file.getSize()));

        } catch (IOException e) {
            log.error("AWS S3 업로드 실패 - 원본 파일명: {}", file.getOriginalFilename(), e);
            throw new FileUploadException(FileUploadErrorCode.AWS_UPLOAD_FAIL, e);
        }
    }

    @Override
    protected String generateFileUrl(String savePath, String renameFileName) {
        String key = buildFullPath(savePath, renameFileName);
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, region, key);
    }
}
