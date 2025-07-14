package grep.neogul_coder.domain.study.service;

import grep.neogul_coder.domain.study.Study;
import grep.neogul_coder.domain.study.StudyMember;
import grep.neogul_coder.domain.study.controller.dto.request.StudyCreateRequest;
import grep.neogul_coder.domain.study.controller.dto.response.StudyItemResponse;
import grep.neogul_coder.domain.study.enums.StudyMemberRole;
import grep.neogul_coder.domain.study.repository.StudyMemberRepository;
import grep.neogul_coder.domain.study.repository.StudyRepository;
import grep.neogul_coder.domain.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class StudyService {

    private final StudyRepository studyRepository;
    private final StudyMemberRepository studyMemberRepository;
    private final UserRepository userRepository;

    public List<StudyItemResponse> getMyStudies(Long userId) {
        List<Study> studies = studyMemberRepository.findStudiesByUserId(userId);
        return studies.stream()
            .map(study -> {
                Long leaderUserId = studyRepository.findLeaderUserIdByStudyId(study.getId());
                String leaderNickname = userRepository.findNicknameById(leaderUserId);
                return StudyItemResponse.from(study, leaderNickname);
            })
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
