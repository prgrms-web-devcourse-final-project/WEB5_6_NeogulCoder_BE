package grep.neogul_coder.domain.recruitment.post.service;

import grep.neogul_coder.domain.recruitment.post.controller.dto.response.save.JoinedStudyLoadInfo;
import grep.neogul_coder.domain.recruitment.post.controller.dto.response.save.JoinedStudiesInfo;
import grep.neogul_coder.domain.recruitment.post.repository.RecruitmentPostRepository;
import grep.neogul_coder.domain.recruitment.post.service.request.RecruitmentPostCreateServiceRequest;
import grep.neogul_coder.domain.study.Study;
import grep.neogul_coder.domain.study.StudyMember;
import grep.neogul_coder.domain.study.repository.StudyMemberQueryRepository;
import grep.neogul_coder.global.exception.business.BusinessException;
import grep.neogul_coder.global.exception.business.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static grep.neogul_coder.domain.recruitment.RecruitmentErrorCode.NOT_FOUND_STUDY_MEMBER;
import static grep.neogul_coder.domain.recruitment.RecruitmentErrorCode.NOT_STUDY_LEADER;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RecruitmentPostSaveService {

    private final StudyMemberQueryRepository studyMemberRepository;
    private final RecruitmentPostRepository recruitmentPostRepository;

    @Transactional
    public long create(RecruitmentPostCreateServiceRequest request, long userId) {
        StudyMember studyMember = studyMemberRepository.findByStudyIdAndUserId(request.getStudyId(), userId);

        if (studyMember.hasNotRoleLeader()) {
            throw new BusinessException(NOT_STUDY_LEADER);
        }

        return recruitmentPostRepository.save(request.toEntity(userId)).getId();
    }

    public JoinedStudiesInfo getJoinedStudyInfo(long userId) {
        List<StudyMember> studyMembers = studyMemberRepository.findAllFetchStudyByUserId(userId);
        List<Study> studyList = toStudyList(studyMembers);
        return JoinedStudiesInfo.of(studyList);
    }

    public JoinedStudyLoadInfo getJoinedStudyLoadInfo(long studyId, long userId) {
        Study study = findValidStudy(studyId, userId);
        long currentCount = studyMemberRepository.findCurrentCountBy(studyId);
        long remainSlots = study.calculateRemainSlots(currentCount);

        return JoinedStudyLoadInfo.of(study, remainSlots);
    }

    private boolean isNotParticipated(StudyMember studyMember) {
        return studyMember == null;
    }

    private List<Study> toStudyList(List<StudyMember> studyMembers) {
        return studyMembers.stream()
                .map(StudyMember::getStudy)
                .toList();
    }

    private Study findValidStudy(long studyId, long userId) {
        StudyMember studyMember = studyMemberRepository.findByStudyIdAndUserId(studyId, userId);

        if(isNotParticipated(studyMember)){
            throw new NotFoundException(NOT_FOUND_STUDY_MEMBER);
        }
        return studyMember.getStudy();
    }

}
