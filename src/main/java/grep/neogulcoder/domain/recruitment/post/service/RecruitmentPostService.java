package grep.neogulcoder.domain.recruitment.post.service;

import grep.neogulcoder.domain.recruitment.comment.RecruitmentPostComment;
import grep.neogulcoder.domain.recruitment.comment.controller.dto.response.CommentsWithWriterInfo;
import grep.neogulcoder.domain.recruitment.comment.repository.RecruitmentPostCommentQueryRepository;
import grep.neogulcoder.domain.recruitment.post.RecruitmentPost;
import grep.neogulcoder.domain.recruitment.post.controller.dto.response.RecruitmentPostInfo;
import grep.neogulcoder.domain.recruitment.post.controller.dto.response.RecruitmentPostPagingInfo;
import grep.neogulcoder.domain.recruitment.post.controller.dto.response.RecruitmentPostWithStudyInfo;
import grep.neogulcoder.domain.recruitment.post.repository.RecruitmentPostQueryRepository;
import grep.neogulcoder.domain.recruitment.post.repository.RecruitmentPostRepository;
import grep.neogulcoder.domain.recruitment.post.service.request.RecruitmentPostStatusUpdateServiceRequest;
import grep.neogulcoder.domain.recruitment.post.service.request.RecruitmentPostUpdateServiceRequest;
import grep.neogulcoder.domain.study.Study;
import grep.neogulcoder.domain.study.enums.Category;
import grep.neogulcoder.domain.study.enums.StudyType;
import grep.neogulcoder.domain.study.repository.StudyRepository;
import grep.neogulcoder.domain.studyapplication.StudyApplication;
import grep.neogulcoder.domain.studyapplication.repository.ApplicationRepository;
import grep.neogulcoder.global.exception.business.BusinessException;
import grep.neogulcoder.global.exception.business.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static grep.neogulcoder.domain.recruitment.RecruitmentErrorCode.NOT_FOUND;
import static grep.neogulcoder.domain.recruitment.RecruitmentErrorCode.NOT_OWNER;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RecruitmentPostService {

    private final StudyRepository studyRepository;
    private final RecruitmentPostRepository postRepository;
    private final RecruitmentPostQueryRepository postQueryRepository;

    private final ApplicationRepository applicationRepository;
    private final RecruitmentPostCommentQueryRepository commentQueryRepository;

    public RecruitmentPostInfo get(long recruitmentPostId) {
        RecruitmentPost post = postRepository.findByIdAndActivatedTrue(recruitmentPostId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND));

        RecruitmentPostWithStudyInfo postInfo = postQueryRepository.findPostWithStudyInfo(post.getId());
        List<CommentsWithWriterInfo> comments = findCommentsWithWriterInfo(post);
        List<StudyApplication> applications = applicationRepository.findByRecruitmentPostId(post.getId());

        return new RecruitmentPostInfo(postInfo, comments, applications.size());
    }

    public RecruitmentPostPagingInfo search(Pageable pageable, Category category, StudyType studyType, String keyword, Long userId) {
        Page<RecruitmentPost> pages = searchRecruitmentPost(pageable, category, studyType, keyword, userId);
        List<RecruitmentPost> content = pages.getContent();
        List<Long> recruitmentPostIds = extractId(content);

        List<Study> studies = findConnectedStudiesFrom(content);
        Map<Long, Study> studyIdMap = groupedStudyIdMapFrom(studies);

        List<RecruitmentPostComment> comments = commentQueryRepository.findByPostIdIn(recruitmentPostIds);
        Map<Long, List<RecruitmentPostComment>> postIdMap = groupedPostIdBy(comments);

        return RecruitmentPostPagingInfo.of(
                content, studyIdMap, postIdMap,
                pages.getTotalPages(), pages.getTotalElements(), pages.hasNext()
        );
    }

    @Transactional
    public long update(RecruitmentPostUpdateServiceRequest request, long recruitmentPostId, long userId) {
        RecruitmentPost recruitmentPost = findRecruitmentPost(recruitmentPostId, userId);

        recruitmentPost.update(
                request.getSubject(),
                request.getContent(),
                request.getRecruitmentCount(),
                request.getExpiredDateTime()
        );
        return recruitmentPost.getId();
    }

    @Transactional
    public long updateStatus(RecruitmentPostStatusUpdateServiceRequest request, long recruitmentPostId, long userId) {
        RecruitmentPost recruitmentPost = findRecruitmentPost(recruitmentPostId, userId);
        recruitmentPost.updateStatus(request.getStatus());
        return recruitmentPost.getId();
    }

    @Transactional
    public void delete(long recruitmentPostId, long userId) {
        RecruitmentPost recruitmentPost = findRecruitmentPost(recruitmentPostId, userId);
        recruitmentPost.delete();
    }

    private List<CommentsWithWriterInfo> findCommentsWithWriterInfo(RecruitmentPost post) {
        List<CommentsWithWriterInfo> comments = commentQueryRepository.findCommentsWithWriterInfo(post.getId());
        List<CommentsWithWriterInfo> withdrawnUserComments = withdrawnUserChangeNameFrom(comments);
        return applyWithdrawnUserNameChanges(comments, withdrawnUserComments);
    }

    private Page<RecruitmentPost> searchRecruitmentPost(Pageable pageable, Category category, StudyType studyType,
                                                        String keyword, Long userId) {
        if (userId == null) {
            return postQueryRepository.search(pageable, category, studyType, keyword);
        }
        return postQueryRepository.search(pageable, category, studyType, keyword, userId);
    }

    private List<CommentsWithWriterInfo> withdrawnUserChangeNameFrom(List<CommentsWithWriterInfo> comments) {
        return comments.stream()
                .filter(info -> !info.isActivated())
                .map(info -> info.updateNickName("탈퇴한 회원"))
                .toList();
    }

    private List<CommentsWithWriterInfo> applyWithdrawnUserNameChanges(List<CommentsWithWriterInfo> comments,
                                                                       List<CommentsWithWriterInfo> withdrawnUserComments) {
        Map<Long, CommentsWithWriterInfo> groupedNicknameMap = withdrawnUserComments.stream()
                .collect(Collectors.toMap(
                        CommentsWithWriterInfo::getUserId,
                        Function.identity()
                ));

        return comments.stream()
                .map(info -> groupedNicknameMap.getOrDefault(info.getUserId(), info))
                .toList();
    }

    private List<Long> extractStudyId(List<RecruitmentPost> recruitmentPosts) {
        return recruitmentPosts.stream()
                .map(RecruitmentPost::getStudyId)
                .toList();
    }

    private List<Study> findConnectedStudiesFrom(List<RecruitmentPost> recruitmentPosts) {
        List<Long> studyIds = extractStudyId(recruitmentPosts);
        return studyRepository.findByIdIn(studyIds);
    }

    private List<Long> extractId(List<RecruitmentPost> recruitmentPosts) {
        return recruitmentPosts.stream()
                .map(RecruitmentPost::getId)
                .toList();
    }

    private Map<Long, Study> groupedStudyIdMapFrom(List<Study> studies) {
        return studies.stream()
                .collect(Collectors.toMap(
                        Study::getId,
                        Function.identity()
                ));
    }

    private Map<Long, List<RecruitmentPostComment>> groupedPostIdBy(List<RecruitmentPostComment> comments) {
        return comments.stream()
                .collect(Collectors.groupingBy(
                        comment -> comment.getRecruitmentPost().getId()
                ));
    }

    private RecruitmentPost findRecruitmentPost(long recruitmentPostId, long userId) {
        RecruitmentPost recruitmentPost = postRepository.findByIdAndActivatedTrue(recruitmentPostId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND));

        if (recruitmentPost.isNotOwnedBy(userId)) {
            throw new BusinessException(NOT_OWNER);
        }
        return recruitmentPost;
    }
}
