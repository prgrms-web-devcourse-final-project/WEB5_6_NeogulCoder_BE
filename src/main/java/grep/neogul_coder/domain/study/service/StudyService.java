package grep.neogul_coder.domain.study.service;

import grep.neogul_coder.domain.study.Study;
import grep.neogul_coder.domain.study.StudyMember;
import grep.neogul_coder.domain.study.controller.dto.request.StudyCreateRequest;
import grep.neogul_coder.domain.study.controller.dto.response.*;
import grep.neogul_coder.domain.study.enums.StudyMemberRole;
import grep.neogul_coder.domain.study.exception.NotStudyLeaderException;
import grep.neogul_coder.domain.study.repository.StudyMemberRepository;
import grep.neogul_coder.domain.study.repository.StudyQueryRepository;
import grep.neogul_coder.domain.study.repository.StudyRepository;
import grep.neogul_coder.global.exception.business.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static grep.neogul_coder.domain.study.exception.code.StudyErrorCode.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class StudyService {

    private final StudyRepository studyRepository;
    private final StudyMemberRepository studyMemberRepository;
    private final StudyQueryRepository studyQueryRepository;

    public List<StudyItemResponse> getMyStudies(Long userId) {
        List<Study> myStudies = studyMemberRepository.findStudiesByUserId(userId);
        List<Long> studyIds = myStudies.stream()
            .map(Study::getId)
            .toList();
        Map<Long, String> leaderNicknames = studyQueryRepository.findLeaderNicknamesByStudyIds(studyIds);

        return myStudies.stream()
            .map(study -> StudyItemResponse.from(
                study, leaderNicknames.get(study.getId())
            ))
            .toList();
    }

    public StudyHeaderResponse getStudyHeader(Long studyId) {
        Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new NotFoundException(STUDY_NOT_FOUND, STUDY_NOT_FOUND.getMessage()));

        return StudyHeaderResponse.from(study);
    }

    public List<StudyImageResponse> getStudyImages(Long userId) {
        List<Study> myStudies = studyMemberRepository.findStudiesByUserId(userId);

        return myStudies.stream()
            .map(StudyImageResponse::from)
            .toList();
    }

    public StudyInfoResponse getStudyInfo(Long studyId, Long userId) {
        if (!studyQueryRepository.isStudyLeader(studyId, userId)) {
            throw new NotStudyLeaderException(STUDY_NOT_LEADER, STUDY_NOT_LEADER.getMessage());
        }

        Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new NotFoundException(STUDY_NOT_FOUND, STUDY_NOT_FOUND.getMessage()));

        List<StudyMemberResponse> members = studyQueryRepository.findStudyMembers(studyId);

        return StudyInfoResponse.from(study, members);
    }

    @Transactional
    public void createStudy(StudyCreateRequest request, Long userId) {
        Study study = studyRepository.save(request.toEntity());
        StudyMember leader = StudyMember.builder()
            .study(study)
            .userId(userId)
            .role(StudyMemberRole.LEADER)
            .build();
        studyMemberRepository.save(leader);
    }
}
