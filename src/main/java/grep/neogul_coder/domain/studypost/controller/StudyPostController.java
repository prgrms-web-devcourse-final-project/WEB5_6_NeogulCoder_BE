package grep.neogul_coder.domain.studypost.controller;

import grep.neogul_coder.domain.studypost.dto.StudyPostDetailResponse;
import grep.neogul_coder.domain.studypost.dto.StudyPostListResponse;
import grep.neogul_coder.domain.studypost.dto.StudyPostRequest;
import grep.neogul_coder.global.response.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/studies/{studyId}/posts")
public class StudyPostController implements StudyPostSpecification {

  @PostMapping
  public ApiResponse<Void> create(
      @PathVariable("studyId") Long studyId,
      @RequestBody @Valid StudyPostRequest request
  ) {
    return ApiResponse.noContent();
  }

  @GetMapping("/all")
  public ApiResponse<List<StudyPostListResponse>> findAllWithoutPagination(
      @PathVariable("studyId") Long studyId
  ) {
    List<StudyPostListResponse> content = List.of(new StudyPostListResponse());
    return ApiResponse.success(content);
  }

  @GetMapping("/{postId}")
  public ApiResponse<StudyPostDetailResponse> findOne(
      @PathVariable("studyId") Long studyId,
      @PathVariable("postId") Long postId
  ) {
    return ApiResponse.success(new StudyPostDetailResponse());
  }

  @PutMapping("/{postId}")
  public ApiResponse<Void> update(
      @PathVariable("studyId") Long studyId,
      @PathVariable("postId") Long postId,
      @RequestBody @Valid StudyPostRequest request
  ) {
    return ApiResponse.noContent();
  }

  @DeleteMapping("/{postId}")
  public ApiResponse<Void> delete(
      @PathVariable("studyId") Long studyId,
      @PathVariable("postId") Long postId
  ) {
    return ApiResponse.noContent();
  }
}
