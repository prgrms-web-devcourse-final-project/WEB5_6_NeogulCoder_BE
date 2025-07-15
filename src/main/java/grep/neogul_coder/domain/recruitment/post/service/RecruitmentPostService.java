package grep.neogul_coder.domain.recruitment.post.service;

import grep.neogul_coder.domain.recruitment.post.RecruitmentPost;
import grep.neogul_coder.domain.recruitment.post.repository.RecruitmentPostRepository;
import grep.neogul_coder.domain.recruitment.post.service.request.RecruitmentPostCreateServiceRequest;
import grep.neogul_coder.domain.recruitment.post.service.request.RecruitmentPostStatusUpdateServiceRequest;
import grep.neogul_coder.domain.recruitment.post.service.request.RecruitmentPostUpdateServiceRequest;
import grep.neogul_coder.global.exception.business.BusinessException;
import grep.neogul_coder.global.exception.business.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static grep.neogul_coder.domain.recruitment.RecruitmentErrorCode.NOT_FOUND;
import static grep.neogul_coder.domain.recruitment.RecruitmentErrorCode.NOT_OWNER;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RecruitmentPostService {

    private final RecruitmentPostRepository recruitmentPostRepository;

    //TODO 스터디장만 스터디 모집글 생성 가능 하도록 변경 필요
    @Transactional
    public void create(RecruitmentPostCreateServiceRequest request, long userId) {
        recruitmentPostRepository.save(request.toEntity(userId));
    }

    @Transactional
    public void update(RecruitmentPostUpdateServiceRequest request, long recruitmentPostId, long userId) {
        RecruitmentPost recruitmentPost = findRecruitmentPost(recruitmentPostId, userId);

        recruitmentPost.update(
                request.getSubject(),
                request.getContent(),
                request.getRecruitmentCount()
        );
    }

    @Transactional
    public void updateStatus(RecruitmentPostStatusUpdateServiceRequest request, long recruitmentPostId, long userId) {
        RecruitmentPost recruitmentPost = findRecruitmentPost(recruitmentPostId, userId);
        recruitmentPost.updateStatus(request.getStatus());
    }

    @Transactional
    public void delete(long recruitmentPostId, long userId) {
        RecruitmentPost recruitmentPost = findRecruitmentPost(recruitmentPostId, userId);
        // recruitmentPost.delete();
    }

    private RecruitmentPost findRecruitmentPost(long recruitmentPostId, long userId) {
        RecruitmentPost recruitmentPost = recruitmentPostRepository.findById(recruitmentPostId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND, NOT_FOUND.getMessage()));

        if (recruitmentPost.isNotOwnedBy(userId)) {
            throw new BusinessException(NOT_OWNER, NOT_OWNER.getMessage());
        }
        return recruitmentPost;
    }
}
