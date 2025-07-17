package grep.neogul_coder.domain.study.service;

import grep.neogul_coder.domain.recruitment.post.repository.RecruitmentPostRepository;
import grep.neogul_coder.domain.study.controller.dto.response.StudyItemPagingResponse;
import grep.neogul_coder.domain.study.Study;
import grep.neogul_coder.domain.study.StudyMember;
import grep.neogul_coder.domain.study.controller.dto.request.StudyCreateRequest;
import grep.neogul_coder.domain.study.controller.dto.request.StudyUpdateRequest;
import grep.neogul_coder.domain.study.controller.dto.response.*;
import grep.neogul_coder.domain.study.enums.StudyMemberRole;
import grep.neogul_coder.domain.study.enums.StudyType;
import grep.neogul_coder.domain.study.repository.StudyMemberRepository;
import grep.neogul_coder.domain.study.repository.StudyQueryRepository;
import grep.neogul_coder.domain.study.repository.StudyRepository;
import grep.neogul_coder.global.exception.business.BusinessException;
import grep.neogul_coder.global.exception.business.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static grep.neogul_coder.domain.study.enums.StudyMemberRole.LEADER;
import static grep.neogul_coder.domain.study.exception.code.StudyErrorCode.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class StudyService {

    private final StudyRepository studyRepository;
    private final StudyMemberRepository studyMemberRepository;
    private final StudyQueryRepository studyQueryRepository;
    private final RecruitmentPostRepository recruitmentPostRepository;

    public StudyItemPagingResponse getMyStudies(Pageable pageable, Long userId) {
        Page<StudyItemResponse> page = studyQueryRepository.findMyStudies(pageable, userId);
        return StudyItemPagingResponse.of(page);
    }

    public StudyHeaderResponse getStudyHeader(Long studyId) {
        Study study = studyRepository.findByIdAndActivatedTrue(studyId)
            .orElseThrow(() -> new NotFoundException(STUDY_NOT_FOUND));

        return StudyHeaderResponse.from(study);
    }

    public List<StudyImageResponse> getStudyImages(Long userId) {
        List<Study> myStudies = studyMemberRepository.findStudiesByUserId(userId);

        return myStudies.stream()
            .map(StudyImageResponse::from)
            .toList();
    }

    public StudyInfoResponse getStudyInfo(Long studyId, Long userId) {
        Study study = studyRepository.findByIdAndActivatedTrue(studyId)
            .orElseThrow(() -> new NotFoundException(STUDY_NOT_FOUND));

        validateStudyMember(studyId, userId);
        validateStudyLeader(studyId, userId);

        List<StudyMemberResponse> members = studyQueryRepository.findStudyMembers(studyId);

        return StudyInfoResponse.from(study, members);
    }

    @Transactional
    public Long createStudy(StudyCreateRequest request, Long userId) {
        Study study = studyRepository.save(request.toEntity());

        validateLocation(request.getStudyType(), request.getLocation());

        StudyMember leader = StudyMember.builder()
            .study(study)
            .userId(userId)
            .role(LEADER)
            .build();
        studyMemberRepository.save(leader);

        return study.getId();
    }

    @Transactional
    public void updateStudy(Long studyId, StudyUpdateRequest request, Long userId) {
        Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new NotFoundException(STUDY_NOT_FOUND));

        validateLocation(request.getStudyType(), request.getLocation());
        validateStudyMember(studyId, userId);
        validateStudyLeader(studyId, userId);
        validateStudyStartDate(request, study);

        study.update(
            request.getName(),
            request.getCategory(),
            request.getCapacity(),
            request.getStudyType(),
            request.getLocation(),
            request.getStartDate(),
            request.getIntroduction(),
            request.getImageUrl()
        );
    }

    @Transactional
    public void deleteStudy(Long studyId, Long userId) {
        Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new NotFoundException(STUDY_NOT_FOUND));

        validateStudyMember(studyId, userId);
        validateStudyLeader(studyId, userId);
        validateStudyDeletable(studyId);

        study.delete();
        studyMemberRepository.deactivateByStudyId(studyId);
        recruitmentPostRepository.deactivateByStudyId(studyId);
    }

    private static void validateLocation(StudyType studyType, String location) {
        if ((studyType == StudyType.OFFLINE || studyType == StudyType.HYBRID) && (location == null || location.isBlank())) {
            throw new BusinessException(STUDY_LOCATION_REQUIRED);
        }
    }

    private void validateStudyMember(Long studyId, Long userId) {
        boolean exists = studyMemberRepository.existsByStudyIdAndUserIdAndActivatedTrue(studyId, userId);
        if (!exists) {
            throw new BusinessException(STUDY_NOT_MEMBER);
        }
    }

    private void validateStudyLeader(Long studyId, Long userId) {
        StudyMemberRole role = studyQueryRepository.findMyRole(studyId, userId);
        if (!role.equals(LEADER)) {
            throw new BusinessException(STUDY_NOT_LEADER);
        }
    }

    private static void validateStudyStartDate(StudyUpdateRequest request, Study study) {
        if (study.hasStarted() && !study.getStartDate().equals(request.getStartDate())) {
            throw new BusinessException(STUDY_ALREADY_STARTED);
        }
    }

    private void validateStudyDeletable(Long studyId) {
        int memberCount = studyMemberRepository.countByStudyIdAndActivatedTrue(studyId);
        if (memberCount != 1) {
            throw new BusinessException(STUDY_DELETE_NOT_ALLOWED);
        }
    }
}
