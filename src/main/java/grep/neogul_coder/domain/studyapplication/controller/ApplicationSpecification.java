package grep.neogul_coder.domain.studyapplication.controller;

import grep.neogul_coder.domain.study.enums.Category;
import grep.neogul_coder.domain.studyapplication.ApplicationStatus;
import grep.neogul_coder.domain.studyapplication.controller.dto.request.ApplicationCreateRequest;
import grep.neogul_coder.domain.studyapplication.controller.dto.response.MyApplicationPagingResponse;
import grep.neogul_coder.domain.studyapplication.controller.dto.response.MyApplicationResponse;
import grep.neogul_coder.global.auth.Principal;
import grep.neogul_coder.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Tag(name = "StudyApplication", description = "스터디 신청 API")
public interface ApplicationSpecification {

    @Operation(summary = "내 스터디 신청 목록 조회", description = "내가 신청한 스터디의 목록을 조회합니다.")
    ApiResponse<MyApplicationPagingResponse> getMyStudyApplications(Long recruitmentPostId, Pageable pageable, ApplicationStatus status, Principal userDetails);

    @Operation(summary = "스터디 신청 생성", description = "스터디를 신청합니다.")
    ApiResponse<Long> createApplication(Long recruitmentPostId, ApplicationCreateRequest request, Principal userDetails);

    @Operation(summary = "스터디 신청 승인", description = "스터디장이 스터디 신청을 승인합니다.")
    ApiResponse<Void> approveApplication(Long applicationId, Principal userDetails);

    @Operation(summary = "스터디 신청 거절", description = "스터디장이 스터디 신청을 거절합니다.")
    ApiResponse<Void> rejectApplication(Long applicationId, Principal userDetails);
}
