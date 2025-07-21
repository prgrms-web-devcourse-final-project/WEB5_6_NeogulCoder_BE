package grep.neogul_coder.domain.studypost.service;

import grep.neogul_coder.domain.study.Study;
import grep.neogul_coder.domain.study.StudyMember;
import grep.neogul_coder.domain.study.repository.StudyMemberQueryRepository;
import grep.neogul_coder.domain.studypost.StudyPost;
import grep.neogul_coder.domain.studypost.comment.repository.StudyPostCommentQueryRepository;
import grep.neogul_coder.domain.studypost.controller.dto.request.StudyPostPagingCondition;
import grep.neogul_coder.domain.studypost.controller.dto.request.StudyPostSaveRequest;
import grep.neogul_coder.domain.studypost.controller.dto.request.StudyPostUpdateRequest;
import grep.neogul_coder.domain.studypost.controller.dto.response.*;
import grep.neogul_coder.domain.studypost.repository.StudyPostQueryRepository;
import grep.neogul_coder.domain.studypost.repository.StudyPostRepository;
import grep.neogul_coder.global.exception.business.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static grep.neogul_coder.domain.studypost.StudyPostErrorCode.NOT_FOUND_POST;
import static grep.neogul_coder.domain.studypost.StudyPostErrorCode.NOT_JOINED_STUDY_USER;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class StudyPostService {

    private final StudyMemberQueryRepository studyQueryRepository;

    private final StudyPostRepository studyPostRepository;
    private final StudyPostQueryRepository studyPostQueryRepository;

    private final StudyPostCommentQueryRepository commentQueryRepository;

    public StudyPostDetailResponse findOne(Long postId) {
        PostInfo postInfo = studyPostQueryRepository.findPostWriterInfo(postId);
        List<CommentInfo> commentInfos = commentQueryRepository.findWriterInfosByPostId(postId);
        return new StudyPostDetailResponse(postInfo, commentInfos, commentInfos.size());
    }

    public PostPagingResult findPagingInfo(StudyPostPagingCondition condition, Long studyId) {
        Page<PostPagingInfo> pages = studyPostQueryRepository.findPagingFilteredBy(condition, studyId);
        List<NoticePostInfo> noticeInfos = studyPostQueryRepository.findLatestNoticeInfoBy(studyId);
        return new PostPagingResult(noticeInfos, pages);
    }

    @Transactional
    public long create(StudyPostSaveRequest request, long userId) {
        List<StudyMember> myStudies = studyQueryRepository.findAllFetchStudyByUserId(userId);
        Study study = extractTargetStudyById(myStudies, request.getStudyId());
        return studyPostRepository.save(request.toEntity(study, userId)).getId();
    }

    @Transactional
    public void update(StudyPostUpdateRequest request, Long postId, long userId) {
        StudyPost studyPost = studyPostQueryRepository.findByIdAndUserId(postId, userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_POST));

        studyPost.update(
                request.getCategory(),
                request.getTitle(),
                request.getContent()
        );
    }

    @Transactional
    public void delete(Long postId, long userId) {
        StudyPost studyPost = studyPostQueryRepository.findByIdAndUserId(postId, userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_POST));

        studyPost.delete();
    }

    private Study extractTargetStudyById(List<StudyMember> studyMembers, long studyId) {
        return studyMembers.stream()
                .map(StudyMember::getStudy)
                .filter(study -> studyId == study.getId())
                .findFirst()
                .orElseThrow(() -> new NotFoundException(NOT_JOINED_STUDY_USER));
    }
}
