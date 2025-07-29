package grep.neogulcoder.domain.recruitment.post.service;

import grep.neogulcoder.domain.recruitment.post.controller.dto.response.save.JoinedStudyLoadInfo;
import grep.neogulcoder.domain.recruitment.post.controller.dto.response.save.JoinedStudiesInfo;
import grep.neogulcoder.domain.recruitment.post.repository.RecruitmentPostRepository;
import grep.neogulcoder.domain.recruitment.post.service.request.RecruitmentPostCreateServiceRequest;
import grep.neogulcoder.domain.study.Study;
import grep.neogulcoder.domain.study.StudyMember;
import grep.neogulcoder.domain.study.repository.StudyMemberQueryRepository;
import grep.neogulcoder.global.exception.business.BusinessException;
import grep.neogulcoder.global.exception.business.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static grep.neogulcoder.domain.recruitment.RecruitmentErrorCode.*;
import static grep.neogulcoder.domain.recruitment.RecruitmentErrorCode.NOT_FOUND_STUDY_MEMBER;
import static grep.neogulcoder.domain.recruitment.RecruitmentErrorCode.NOT_STUDY_LEADER;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RecruitmentPostSaveService {

    private final StudyMemberQueryRepository studyMemberRepository;
    private final RecruitmentPostRepository recruitmentPostRepository;

    @Transactional
    public long create(RecruitmentPostCreateServiceRequest request, long userId) {
        StudyMember studyMember = studyMemberRepository.findMemberWithStudyBy(request.getStudyId(), userId);

        if (studyMember.hasNotRoleLeader()) {
            throw new BusinessException(NOT_STUDY_LEADER);
        }

        if(isStudyAlreadyEndedDateTime(studyMember.getStudy(), request.getExpiredDate())){
            throw new BusinessException(END_DATE_ERROR);
        }

        return recruitmentPostRepository.save(request.toEntity(userId)).getId();
    }

    public JoinedStudiesInfo getJoinedStudyInfo(long userId) {
        List<StudyMember> studyMembers = studyMemberRepository.findMembersByUserId(userId);
        List<Study> studyList = toStudyList(studyMembers);
        return JoinedStudiesInfo.of(studyList);
    }

    public JoinedStudyLoadInfo getJoinedStudyLoadInfo(long studyId, long userId) {
        Study study = findValidStudy(studyId, userId);
        long currentCount = studyMemberRepository.findCurrentCountBy(studyId);
        long remainSlots = study.calculateRemainSlots(currentCount);

        return JoinedStudyLoadInfo.of(study, remainSlots);
    }

    private boolean isStudyAlreadyEndedDateTime(Study study, LocalDateTime expiredDateTime) {
        return study.hasEndDateBefore(expiredDateTime);
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
