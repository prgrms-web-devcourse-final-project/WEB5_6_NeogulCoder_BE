package grep.neogul_coder.domain.study.service;

import grep.neogul_coder.domain.study.Study;
import grep.neogul_coder.domain.study.StudyMember;
import grep.neogul_coder.domain.study.controller.dto.request.StudyCreateRequest;
import grep.neogul_coder.domain.study.controller.dto.response.StudyItemResponse;
import grep.neogul_coder.domain.study.enums.StudyMemberRole;
import grep.neogul_coder.domain.study.repository.StudyMemberRepository;
import grep.neogul_coder.domain.study.repository.StudyQueryRepository;
import grep.neogul_coder.domain.study.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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
