package grep.neogul_coder.domain.studypost.service;

import grep.neogul_coder.domain.study.Study;
import grep.neogul_coder.domain.study.StudyMember;
import grep.neogul_coder.domain.study.repository.StudyMemberQueryRepository;
import grep.neogul_coder.domain.studypost.Category;
import grep.neogul_coder.domain.studypost.StudyPost;
import grep.neogul_coder.domain.studypost.comment.repository.StudyPostCommentQueryRepository;
import grep.neogul_coder.domain.studypost.controller.dto.request.StudyPostSaveRequest;
import grep.neogul_coder.domain.studypost.controller.dto.request.StudyPostUpdateRequest;
import grep.neogul_coder.domain.studypost.controller.dto.response.*;
import grep.neogul_coder.domain.studypost.repository.StudyPostQueryRepository;
import grep.neogul_coder.domain.studypost.repository.StudyPostRepository;
import grep.neogul_coder.global.exception.business.NotFoundException;
import grep.neogul_coder.global.utils.upload.AbstractFileManager;
import grep.neogul_coder.global.utils.upload.FileUploadResponse;
import grep.neogul_coder.global.utils.upload.FileUsageType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static grep.neogul_coder.domain.studypost.StudyPostErrorCode.NOT_FOUND_POST;
import static grep.neogul_coder.domain.studypost.StudyPostErrorCode.NOT_JOINED_STUDY_USER;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class StudyPostService {

    private final AbstractFileManager fileUploader;
    private final StudyMemberQueryRepository studyQueryRepository;

    private final StudyPostRepository studyPostRepository;
    private final StudyPostQueryRepository studyPostQueryRepository;
    private final StudyPostCommentQueryRepository commentQueryRepository;

    public StudyPostDetailResponse findOne(Long postId) {
        PostInfo postInfo = studyPostQueryRepository.findPostWriterInfo(postId);
        List<CommentInfo> commentInfos = commentQueryRepository.findWriterInfosByPostId(postId);
        return new StudyPostDetailResponse(postInfo, commentInfos, commentInfos.size());
    }

    public PostPagingResult findPagingInfo(long studyId, Pageable pageable, Category category, String keyword) {
        Page<PostPagingInfo> pages = studyPostQueryRepository.findPagingFilteredBy(studyId, pageable, category, keyword);
        List<NoticePostInfo> noticeInfos = studyPostQueryRepository.findLatestNoticeInfoBy(studyId);
        return new PostPagingResult(noticeInfos, pages);
    }

    @Transactional
    public long create(StudyPostSaveRequest request, long studyId, long userId) {
        List<StudyMember> myStudies = studyQueryRepository.findAllFetchStudyByUserId(userId);
        Study study = extractTargetStudyById(myStudies, studyId);
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

    public String uploadPostImage(MultipartFile file, long userId) throws IOException {
        FileUploadResponse response = fileUploader.upload(file, userId, FileUsageType.POST, userId);
        return response.getFileUrl();
    }
}
