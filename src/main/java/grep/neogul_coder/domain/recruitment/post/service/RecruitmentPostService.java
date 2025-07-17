package grep.neogul_coder.domain.recruitment.post.service;

import grep.neogul_coder.domain.recruitment.post.RecruitmentPost;
import grep.neogul_coder.domain.recruitment.comment.RecruitmentPostComment;
import grep.neogul_coder.domain.recruitment.post.controller.dto.response.RecruitmentPostPagingInfo;
import grep.neogul_coder.domain.recruitment.comment.repository.RecruitmentPostCommentQueryRepository;
import grep.neogul_coder.domain.recruitment.post.repository.RecruitmentPostRepository;
import grep.neogul_coder.domain.recruitment.post.service.request.RecruitmentPostStatusUpdateServiceRequest;
import grep.neogul_coder.domain.recruitment.post.service.request.RecruitmentPostUpdateServiceRequest;
import grep.neogul_coder.domain.study.Study;
import grep.neogul_coder.domain.study.repository.StudyRepository;
import grep.neogul_coder.global.exception.business.BusinessException;
import grep.neogul_coder.global.exception.business.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static grep.neogul_coder.domain.recruitment.RecruitmentErrorCode.NOT_FOUND;
import static grep.neogul_coder.domain.recruitment.RecruitmentErrorCode.NOT_OWNER;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RecruitmentPostService {

    private final StudyRepository studyRepository;
    private final RecruitmentPostRepository postRepository;
    private final RecruitmentPostQueryRepository postQueryRepository;
    private final RecruitmentPostCommentQueryRepository commentQueryRepository;

    public RecruitmentPostPagingInfo getPagingInfo(Pageable pageable) {
        List<RecruitmentPost> recruitmentPosts = postQueryRepository.findPaging(pageable);
        List<Long> recruitmentPostIds = extractId(recruitmentPosts);

        List<Study> studies = findConnectedStudiesFrom(recruitmentPosts);
        Map<Long, Study> studyIdMap = groupedStudyIdMapFrom(studies);

        List<RecruitmentPostComment> comments = commentQueryRepository.findByPostIdIn(recruitmentPostIds);
        Map<Long, List<RecruitmentPostComment>> postIdMap = groupedPostIdBy(comments);

        return RecruitmentPostPagingInfo.of(recruitmentPosts, studyIdMap, postIdMap);
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
        recruitmentPost.delete();
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
