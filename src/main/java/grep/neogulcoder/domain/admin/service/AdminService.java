package grep.neogulcoder.domain.admin.service;

import grep.neogulcoder.domain.admin.controller.dto.response.AdminRecruitmentPostResponse;
import grep.neogulcoder.domain.admin.controller.dto.response.AdminStudyResponse;
import grep.neogulcoder.domain.admin.controller.dto.response.AdminUserResponse;
import grep.neogulcoder.domain.recruitment.post.RecruitmentPost;
import grep.neogulcoder.domain.recruitment.post.repository.RecruitmentPostRepository;
import grep.neogulcoder.domain.study.Study;
import grep.neogulcoder.domain.study.enums.Category;
import grep.neogulcoder.domain.study.repository.StudyQueryRepository;
import grep.neogulcoder.domain.study.repository.StudyRepository;
import grep.neogulcoder.domain.users.repository.UserRepository;
import grep.neogulcoder.domain.users.service.UserService;
import grep.neogulcoder.global.exception.business.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static grep.neogulcoder.domain.study.exception.code.StudyErrorCode.STUDY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final StudyQueryRepository studyQueryRepository;
    private final StudyRepository studyRepository;
    private final RecruitmentPostRepository recruitmentPostRepository;

    @Transactional(readOnly = true)
    public Page<AdminUserResponse> getAllUsers(Pageable pageable, String email) {
        if (isContainEmail(email)) {
            return userRepository.findByEmailContainingIgnoreCase(email, pageable)
                .map(AdminUserResponse::from);
        }
        return userRepository.findAll(pageable)
            .map(AdminUserResponse::from);
    }

    @Transactional(readOnly = true)
    public Page<AdminStudyResponse> getAllStudies(int page, String name, Category category) {
        Pageable pageable = PageRequest.of(page, 10);
        return studyQueryRepository.adminSearchStudy(name, category, pageable)
            .map(AdminStudyResponse::from);
    }

    @Transactional(readOnly = true)
    public Page<AdminRecruitmentPostResponse> getAllRecruitmentPosts(int page, String subject) {
        Pageable pageable = PageRequest.of(page, 10);
        if (isContainSubject(subject)) {
            return recruitmentPostRepository.findBySubjectContainingIgnoreCase(subject, pageable)
                .map(AdminRecruitmentPostResponse::from);
        }
        return recruitmentPostRepository.findAll(pageable)
            .map(AdminRecruitmentPostResponse::from);
    }

    @Transactional
    public void deleteUser(Long userId) {
        userService.deleteUser(userId);
    }

    @Transactional
    public void deleteStudy(Long studyId) {
        Study study = studyRepository.findById(studyId)
            .orElseThrow(() -> new NotFoundException(STUDY_NOT_FOUND));
        study.delete();
    }

    @Transactional
    public void deleteRecruitmentPost(Long recruitmentPostId) {
        RecruitmentPost recruitmentPost = recruitmentPostRepository.findById(recruitmentPostId).orElseThrow(() -> new NotFoundException(STUDY_NOT_FOUND));
        recruitmentPost.delete();
    }

    private Boolean isContainEmail(String email) {
        return email != null && !email.isEmpty();
    }

    private Boolean isContainSubject(String subject) {
        return subject != null && !subject.isEmpty();
    }

}
