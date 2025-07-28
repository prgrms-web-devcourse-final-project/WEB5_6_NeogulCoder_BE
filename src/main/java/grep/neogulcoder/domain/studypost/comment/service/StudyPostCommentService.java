package grep.neogulcoder.domain.studypost.comment.service;

import grep.neogulcoder.domain.studypost.StudyPost;
import grep.neogulcoder.domain.studypost.StudyPostErrorCode;
import grep.neogulcoder.domain.studypost.comment.StudyPostComment;
import grep.neogulcoder.domain.studypost.comment.dto.StudyPostCommentSaveRequest;
import grep.neogulcoder.domain.studypost.comment.dto.request.StudyCommentUpdateRequest;
import grep.neogulcoder.domain.studypost.comment.event.StudyPostCommentEvent;
import grep.neogulcoder.domain.studypost.comment.repository.StudyPostCommentQueryRepository;
import grep.neogulcoder.domain.studypost.comment.repository.StudyPostCommentRepository;
import grep.neogulcoder.domain.studypost.repository.StudyPostRepository;
import grep.neogulcoder.global.exception.business.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class StudyPostCommentService {

    private final StudyPostRepository postRepository;
    private final StudyPostCommentRepository commentRepository;
    private final StudyPostCommentQueryRepository commentQueryRepository;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public Long create(StudyPostCommentSaveRequest request, long postId, long userId) {
        StudyPostComment comment = request.toEntity(postId, userId);
        Long commentId = commentRepository.save(comment).getId();

        StudyPost post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(StudyPostErrorCode.NOT_FOUND_POST));

        if (post.isNotOwned(userId)) {
            publisher.publishEvent(new StudyPostCommentEvent(post.getUserId(), post.getId()));
        }
        return commentId;
    }

    @Transactional
    public void update(StudyCommentUpdateRequest request, long commentId, long userId) {
        StudyPostComment comment = commentQueryRepository.findById(commentId, userId)
                .orElseThrow(() -> new NotFoundException(StudyPostErrorCode.NOT_FOUND_COMMENT));

        comment.update(request.getContent());
    }

    @Transactional
    public void delete(long commentId, long userId) {
        StudyPostComment comment = commentQueryRepository.findById(commentId, userId)
                .orElseThrow(() -> new NotFoundException(StudyPostErrorCode.NOT_FOUND_COMMENT));

        comment.delete();
    }
}
