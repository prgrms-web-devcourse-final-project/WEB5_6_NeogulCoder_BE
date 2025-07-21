package grep.neogul_coder.domain.recruitment.comment.service;

import grep.neogul_coder.domain.recruitment.RecruitmentErrorCode;
import grep.neogul_coder.domain.recruitment.comment.RecruitmentPostComment;
import grep.neogul_coder.domain.recruitment.comment.controller.dto.request.RecruitmentCommentSaveRequest;
import grep.neogul_coder.domain.recruitment.comment.controller.dto.request.RecruitmentCommentUpdateRequest;
import grep.neogul_coder.domain.recruitment.comment.repository.RecruitmentPostCommentQueryRepository;
import grep.neogul_coder.domain.recruitment.comment.repository.RecruitmentPostCommentRepository;
import grep.neogul_coder.domain.recruitment.post.RecruitmentPost;
import grep.neogul_coder.domain.recruitment.post.repository.RecruitmentPostQueryRepository;
import grep.neogul_coder.global.exception.business.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static grep.neogul_coder.domain.recruitment.RecruitmentErrorCode.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class RecruitmentPostCommentService {

    private final RecruitmentPostQueryRepository postRepository;
    private final RecruitmentPostCommentRepository commentRepository;
    private final RecruitmentPostCommentQueryRepository commentQueryRepository;

    @Transactional
    public long save(RecruitmentCommentSaveRequest request, long userId) {
        RecruitmentPost myPost = postRepository.findMyPostBy(request.getPostId(), userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND));

        return commentRepository.save(request.toEntity(myPost, userId)).getId();
    }

    @Transactional
    public void update(RecruitmentCommentUpdateRequest request, long commentId, long userId) {
        RecruitmentPostComment comment = commentQueryRepository.findMyCommentBy(commentId, userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_COMMENT));

        comment.update(request.getContent());
    }

    @Transactional
    public void delete(long commentId, long userId) {
        RecruitmentPostComment comment = commentQueryRepository.findMyCommentBy(commentId, userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_COMMENT));

        comment.delete();
    }
}
