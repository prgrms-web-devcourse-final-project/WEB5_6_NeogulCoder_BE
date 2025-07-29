package grep.neogulcoder.domain.study.service;

import grep.neogulcoder.domain.calendar.controller.dto.response.TeamCalendarResponse;
import grep.neogulcoder.domain.calendar.service.TeamCalendarService;
import grep.neogulcoder.domain.groupchat.entity.GroupChatRoom;
import grep.neogulcoder.domain.groupchat.repository.GroupChatRoomRepository;
import grep.neogulcoder.domain.recruitment.post.repository.RecruitmentPostRepository;
import grep.neogulcoder.domain.study.Study;
import grep.neogulcoder.domain.study.StudyMember;
import grep.neogulcoder.domain.study.controller.dto.request.StudyCreateRequest;
import grep.neogulcoder.domain.study.controller.dto.request.StudyUpdateRequest;
import grep.neogulcoder.domain.study.controller.dto.response.*;
import grep.neogulcoder.domain.study.enums.StudyMemberRole;
import grep.neogulcoder.domain.study.repository.StudyMemberQueryRepository;
import grep.neogulcoder.domain.study.repository.StudyMemberRepository;
import grep.neogulcoder.domain.study.repository.StudyQueryRepository;
import grep.neogulcoder.domain.study.repository.StudyRepository;
import grep.neogulcoder.domain.studypost.controller.dto.response.FreePostInfo;
import grep.neogulcoder.domain.studypost.controller.dto.response.NoticePostInfo;
import grep.neogulcoder.domain.studypost.repository.StudyPostQueryRepository;
import grep.neogulcoder.domain.studypost.repository.StudyPostRepository;
import grep.neogulcoder.domain.users.entity.User;
import grep.neogulcoder.domain.users.repository.UserRepository;
import grep.neogulcoder.global.exception.business.BusinessException;
import grep.neogulcoder.global.exception.business.NotFoundException;
import grep.neogulcoder.global.utils.upload.AbstractFileManager;
import grep.neogulcoder.global.utils.upload.FileUploadResponse;
import grep.neogulcoder.global.utils.upload.FileUsageType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static grep.neogulcoder.domain.study.enums.StudyMemberRole.*;
import static grep.neogulcoder.domain.study.exception.code.StudyErrorCode.*;
import static grep.neogulcoder.domain.users.exception.code.UserErrorCode.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class StudyService {

    private final AbstractFileManager fileUploader;
    private final StudyRepository studyRepository;
    private final StudyMemberRepository studyMemberRepository;
    private final StudyQueryRepository studyQueryRepository;
    private final RecruitmentPostRepository recruitmentPostRepository;
    private final StudyMemberQueryRepository studyMemberQueryRepository;
    private final UserRepository userRepository;
    private final StudyPostRepository studyPostRepository;
    private final StudyPostQueryRepository studyPostQueryRepository;
    private final TeamCalendarService teamCalendarService;
    private final GroupChatRoomRepository groupChatRoomRepository;

    public StudyItemPagingResponse getMyStudiesPaging(Pageable pageable, Long userId, Boolean finished) {
        Page<StudyItemResponse> page = studyQueryRepository.findMyStudiesPaging(pageable, userId, finished);
        return StudyItemPagingResponse.of(page);
    }

    public List<StudyItemResponse> getMyUnfinishedStudies(Long userId) {
        return studyQueryRepository.findMyUnfinishedStudies(userId);
    }

    public StudyHeaderResponse getStudyHeader(Long studyId) {
        Study study = getStudyById(studyId);
        List<StudyMemberResponse> members = studyQueryRepository.findStudyMembers(studyId);

        return StudyHeaderResponse.from(study, members);
    }

    public StudyResponse getStudy(Long studyId) {
        Study study = getStudyById(studyId);

        int progressDays = (int) ChronoUnit.DAYS.between(study.getStartDate().toLocalDate(), LocalDate.now()) + 1;
        int totalDays = (int) ChronoUnit.DAYS.between(study.getStartDate().toLocalDate(), study.getEndDate().toLocalDate()) + 1;
        progressDays = Math.max(0, Math.min(progressDays, totalDays));
        int totalPostCount = studyPostRepository.countByStudyIdAndActivatedTrue(studyId);

        List<TeamCalendarResponse> teamCalendars = getCurrentMonthTeamCalendars(studyId);

        List<NoticePostInfo> noticePosts = studyPostQueryRepository.findLatestNoticeInfoBy(studyId);
        List<FreePostInfo> freePosts = studyPostQueryRepository.findLatestFreeInfoBy(studyId);

        return StudyResponse.from(study, progressDays, totalDays, totalPostCount, teamCalendars, noticePosts, freePosts);
    }

    public List<StudyImageResponse> getStudyImages(Long userId) {
        List<Study> myStudiesImage = studyMemberRepository.findStudiesByUserId(userId);

        return myStudiesImage.stream()
                .map(StudyImageResponse::from)
                .toList();
    }

    public StudyInfoResponse getMyStudyContent(Long studyId, Long userId) {
        Study study = getStudyById(studyId);

        validateStudyMember(studyId, userId);
        validateStudyLeader(studyId, userId);

        List<StudyMemberResponse> members = studyQueryRepository.findStudyMembers(studyId);

        return StudyInfoResponse.from(study, members);
    }

    public StudyMemberInfoResponse getMyStudyMemberInfo(Long studyId, Long userId) {
        StudyMember studyMember = getStudyMemberById(studyId, userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        return StudyMemberInfoResponse.from(studyMember, user);
    }

    @Transactional
    public Long createStudy(StudyCreateRequest request, Long userId, MultipartFile image) throws IOException {
        validateStudyCreateLimit(userId);

        String imageUrl = createImageUrl(userId, image);

        Study study = studyRepository.save(request.toEntity(userId, imageUrl));

        StudyMember leader = StudyMember.createLeader(study, userId);
        studyMemberRepository.save(leader);

        GroupChatRoom chatRoom = new GroupChatRoom(study.getId());
        groupChatRoomRepository.save(chatRoom);

        return study.getId();
    }

    @Transactional
    public void updateStudy(Long studyId, StudyUpdateRequest request, Long userId, MultipartFile image) throws IOException {
        Study study = getStudyById(studyId);

        validateStudyMember(studyId, userId);
        validateStudyLeader(studyId, userId);
        validateStudyStartDate(request, study);

        String imageUrl = updateImageUrl(userId, image, study.getImageUrl());

        study.update(
                request.getName(),
                request.getCategory(),
                request.getCapacity(),
                request.getStudyType(),
                request.getLocation(),
                request.getStartDate(),
                request.getIntroduction(),
                imageUrl
        );
    }

    @Transactional
    public void deleteStudy(Long studyId, Long userId) {
        getStudyById(studyId);

        validateStudyMember(studyId, userId);
        validateStudyLeader(studyId, userId);
        validateStudyDeletable(studyId);

        studyRepository.deactivateByStudyId(studyId);
        studyMemberRepository.deactivateByStudyId(studyId);
        recruitmentPostRepository.deactivateByStudyId(studyId);
    }

    private Study getStudyById(Long studyId) {
        return studyRepository.findById(studyId)
            .orElseThrow(() -> new NotFoundException(STUDY_NOT_FOUND));
    }

    private StudyMember getStudyMemberById(Long studyId, Long userId) {
        return Optional.ofNullable(studyMemberQueryRepository.findByStudyIdAndUserId(studyId, userId))
            .orElseThrow(() -> new NotFoundException(STUDY_MEMBER_NOT_FOUND));
    }

    private void validateStudyCreateLimit(Long userId) {
        int joinedStudyCount = studyRepository.countByUserIdAndActivatedTrueAndFinishedFalse(userId);
        if (Study.isOverJoinLimit(joinedStudyCount)) {
            throw new BusinessException(STUDY_CREATE_LIMIT_EXCEEDED);
        }
    }

    private List<TeamCalendarResponse> getCurrentMonthTeamCalendars(Long studyId) {
        LocalDate now = LocalDate.now();
        int currentYear = now.getYear();
        int currentMonth = now.getMonthValue();
        return teamCalendarService.findByMonth(studyId, currentYear, currentMonth);
    }

    private void validateStudyMember(Long studyId, Long userId) {
        boolean exists = studyMemberRepository.existsByStudyIdAndUserIdAndActivatedTrue(studyId, userId);
        if (!exists) {
            throw new BusinessException(STUDY_MEMBER_NOT_FOUND);
        }
    }

    private void validateStudyLeader(Long studyId, Long userId) {
        StudyMemberRole role = studyQueryRepository.findMyRole(studyId, userId);
        if (!role.equals(LEADER)) {
            throw new BusinessException(NOT_STUDY_LEADER);
        }
    }

    private static void validateStudyStartDate(StudyUpdateRequest request, Study study) {
        if (study.isStarted(LocalDateTime.now()) && !study.getStartDate().equals(request.getStartDate())) {
            throw new BusinessException(STUDY_ALREADY_STARTED);
        }
    }

    private void validateStudyDeletable(Long studyId) {
        int memberCount = studyMemberRepository.countByStudyIdAndActivatedTrue(studyId);
        if (memberCount != 1) {
            throw new BusinessException(STUDY_DELETE_NOT_ALLOWED);
        }
    }

    private String createImageUrl(Long userId, MultipartFile image) throws IOException {
        if (isImgExists(image)) {
            FileUploadResponse response = fileUploader.upload(image, userId, FileUsageType.STUDY_COVER, userId);
            return response.getFileUrl();
        }
        return null;
    }

    private String updateImageUrl(Long userId, MultipartFile image, String originalImageUrl) throws IOException {
        if (isImgExists(image)) {
            FileUploadResponse response = fileUploader.upload(image, userId, FileUsageType.STUDY_COVER, userId);
            return response.getFileUrl();
        }
        return originalImageUrl;
    }

    private boolean isImgExists(MultipartFile image) {
        return image != null && !image.isEmpty();
    }
}
