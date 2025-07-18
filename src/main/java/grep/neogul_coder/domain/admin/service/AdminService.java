package grep.neogul_coder.domain.admin.service;

import grep.neogul_coder.domain.admin.controller.dto.response.AdminRecruitmentPostResponse;
import grep.neogul_coder.domain.admin.controller.dto.response.AdminStudyResponse;
import grep.neogul_coder.domain.admin.controller.dto.response.AdminUserResponse;
import grep.neogul_coder.domain.recruitment.post.repository.RecruitmentPostRepository;
import grep.neogul_coder.domain.study.Study;
import grep.neogul_coder.domain.study.repository.StudyRepository;
import grep.neogul_coder.domain.study.service.StudyService;
import grep.neogul_coder.domain.users.repository.UserRepository;
import grep.neogul_coder.domain.users.service.UserService;
import grep.neogul_coder.global.exception.business.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static grep.neogul_coder.domain.study.exception.code.StudyErrorCode.STUDY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final StudyRepository studyRepository;
    private final RecruitmentPostRepository recruitmentPostRepository;

    @Transactional(readOnly = true)
    public Page<AdminUserResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
            .map(AdminUserResponse::from);
    }

    @Transactional(readOnly = true)
    public Page<AdminStudyResponse> getAllStudies(int page) {
        int size = 10;
        return studyRepository.findAll(PageRequest.of(page, size))
            .map(AdminStudyResponse::from);
    }

    @Transactional(readOnly = true)
    public Page<AdminRecruitmentPostResponse> getAllRecruitmentPosts(int page) {
        int size = 10;
        return recruitmentPostRepository.findAll(PageRequest.of(page, size))
            .map(AdminRecruitmentPostResponse::from);
    }

    @Transactional
    public void deleteUser(Long userId){
        userService.deleteUser(userId);
    }

    @Transactional
    public void deleteStudy(Long studyId){
        Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new NotFoundException(STUDY_NOT_FOUND));

        study.delete();
    }

    @Transactional
    public void deleteRecruitmentPost(Long recruitmentPostId){
        recruitmentPostRepository.deleteById(recruitmentPostId);
    }

}
