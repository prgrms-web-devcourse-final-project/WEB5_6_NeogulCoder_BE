package grep.neogul_coder.global.utils.upload;

import grep.neogul_coder.global.utils.upload.exception.FileUploadErrorCode;
import grep.neogul_coder.global.utils.upload.exception.FileUploadException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public abstract class AbstractFileManager {

  // 여러 개의 파일을 업로드 처리하고, 결과를 리스트로 반환
  public List<FileUploadResponse> upload(List<MultipartFile> files, Long uploaderId, FileUsageType usageType, Long usageRefId) throws IOException {
    List<FileUploadResponse> uploadResults = new ArrayList<>();

    if (files == null || files.isEmpty() || files.getFirst().isEmpty()) {
      throw new FileUploadException(FileUploadErrorCode.FILE_EMPTY);
    }

    for (MultipartFile file : files) {
      uploadResults.add(upload(file, uploaderId, usageType, usageRefId));
    }

    return uploadResults;
  }

  // 단일 파일 업로드 처리 및 결과 반환
  public FileUploadResponse upload(MultipartFile file, Long uploaderId, FileUsageType usageType, Long usageRefId) throws IOException {
    if (file == null || file.isEmpty()) {
      throw new FileUploadException(FileUploadErrorCode.FILE_EMPTY);
    }

    String contentType = file.getContentType();
    if(contentType == null || !ALLOWED_TYPES.contains(contentType)) {
      throw new FileUploadException(FileUploadErrorCode.FILE_TYPE_INVALID);
    }

    String originFileName = file.getOriginalFilename();
    if (originFileName == null) {
      throw new FileUploadException(FileUploadErrorCode.FILE_NAME_INVALID);
    }

    String renameFileName = generateRenameFileName(originFileName); // 파일명 UUID로 변경
    String savePath = generateSavePath(usageType, uploaderId, null, usageRefId); // 저장 경로 생성
    String fileUrl = generateFileUrl(savePath, renameFileName); // 저장소별 파일 URL 생성

    uploadFile(file, buildFullPath(savePath, renameFileName)); // 실제 파일 업로드(구현체에서 구현)

    return new FileUploadResponse(
        originFileName,
        renameFileName,
        usageType,
        savePath,
        fileUrl,
        uploaderId,
        usageRefId
    );
  }

  // 실제 파일 업로드를 수행
  protected abstract void uploadFile(MultipartFile file, String fullPath) throws IOException;

  // 파일 저장 경로를 생성 (용도/참조 ID/날짜 기반)
  public static String generateSavePath(FileUsageType usageType, Long uploaderId, Long studyId, Long usageRefId) {
    LocalDate now = LocalDate.now();
    if (usageType == FileUsageType.PROFILE && usageRefId != null) {
      // 프로필: user/profile/{userId}/...
      return "user/profile/" + usageRefId + "/" +
          now.getYear() + "/" +
          now.getMonthValue() + "/" +
          now.getDayOfMonth() + "/";
    } else {
      // 스터디 커버, 게시글 경로: {usageType}/{usageRefId}/...
      return usageType.name().toLowerCase() + "/" + usageRefId + "/" +
          now.getYear() + "/" +
          now.getMonthValue() + "/" +
          now.getDayOfMonth() + "/";
    }
  }

  // 파일명을 UUID 기반으로 리네이밍
  protected String generateRenameFileName(String originFileName) {
    String ext = originFileName.substring(originFileName.lastIndexOf("."));
    return UUID.randomUUID() + ext;
  }

  // 저장된 파일의 URL 을 반환
  protected abstract String generateFileUrl(String savePath, String renameFileName);

  // 전체 저장 경로(폴더+파일명)를 반환
  protected String buildFullPath(String savePath, String renameFileName) {
    return savePath + "/" + renameFileName;
  }

  // MIME 타입 허용 목록 (이미지 파일만 허용)
  protected static final List<String> ALLOWED_TYPES = List.of(
      "image/png", "image/jpeg", "image/jpg", "image/gif", "image/webp"
  );
}
