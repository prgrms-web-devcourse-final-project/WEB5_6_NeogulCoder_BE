package grep.neogul_coder.domain.recruitment.post.service;

import grep.neogul_coder.domain.recruitment.post.controller.dto.response.ParticipatedStudiesInfo;
import grep.neogul_coder.domain.recruitment.post.repository.RecruitmentPostRepository;
import grep.neogul_coder.domain.recruitment.post.service.request.RecruitmentPostCreateServiceRequest;
import grep.neogul_coder.domain.study.Study;
import grep.neogul_coder.domain.study.StudyMember;
import grep.neogul_coder.domain.study.repository.StudyMemberQueryRepository;
import grep.neogul_coder.global.exception.business.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static grep.neogul_coder.domain.recruitment.RecruitmentErrorCode.NOT_STUDY_READER;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RecruitmentPostSaveService {

    private final StudyMemberQueryRepository studyMemberRepository;
    private final RecruitmentPostRepository recruitmentPostRepository;

    @Transactional
    public long create(RecruitmentPostCreateServiceRequest request, long userId) {
        StudyMember studyMember = studyMemberRepository.findByStudyIdAndUserId(request.getStudyId(), userId);

        if (studyMember.hasNotRoleReader()) {
            throw new BusinessException(NOT_STUDY_READER);
        }

        return recruitmentPostRepository.save(request.toEntity(userId)).getId();
    }

    public ParticipatedStudiesInfo getParticipatedStudyInfo(long userId) {
        List<StudyMember> studyMembers = studyMemberRepository.findAllFetchStudyByUserId(userId);
        List<Study> studyList = toStudyList(studyMembers);
        return ParticipatedStudiesInfo.of(studyList);
    }

    private List<Study> toStudyList(List<StudyMember> studyMembers) {
        return studyMembers.stream()
                .map(StudyMember::getStudy)
                .toList();
    }
}
