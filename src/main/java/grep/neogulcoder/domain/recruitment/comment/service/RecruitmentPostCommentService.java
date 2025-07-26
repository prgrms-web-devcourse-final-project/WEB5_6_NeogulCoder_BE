package grep.neogulcoder.domain.recruitment.comment.service;

import grep.neogulcoder.domain.recruitment.comment.RecruitmentPostComment;
import grep.neogulcoder.domain.recruitment.comment.controller.dto.request.RecruitmentCommentSaveRequest;
import grep.neogulcoder.domain.recruitment.comment.controller.dto.request.RecruitmentCommentUpdateRequest;
import grep.neogulcoder.domain.recruitment.comment.repository.RecruitmentPostCommentQueryRepository;
import grep.neogulcoder.domain.recruitment.comment.repository.RecruitmentPostCommentRepository;
import grep.neogulcoder.domain.recruitment.post.RecruitmentPost;
import grep.neogulcoder.domain.recruitment.post.repository.RecruitmentPostQueryRepository;
import grep.neogulcoder.global.exception.business.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static grep.neogulcoder.domain.recruitment.RecruitmentErrorCode.NOT_FOUND;
import static grep.neogulcoder.domain.recruitment.RecruitmentErrorCode.NOT_FOUND_COMMENT;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class RecruitmentPostCommentService {

    private final RecruitmentPostQueryRepository postRepository;
    private final RecruitmentPostCommentRepository commentRepository;
    private final RecruitmentPostCommentQueryRepository commentQueryRepository;

    @Transactional
    public long save(long postId, RecruitmentCommentSaveRequest request, long userId) {
        RecruitmentPost post = postRepository.findPostBy(postId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND));

        return commentRepository.save(request.toEntity(post, userId)).getId();
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
