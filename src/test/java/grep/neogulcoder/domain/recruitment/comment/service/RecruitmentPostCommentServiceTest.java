package grep.neogulcoder.domain.recruitment.comment.service;

import grep.neogulcoder.domain.IntegrationTestSupport;
import grep.neogulcoder.domain.alram.entity.Alarm;
import grep.neogulcoder.domain.alram.repository.AlarmRepository;
import grep.neogulcoder.domain.alram.type.AlarmType;
import grep.neogulcoder.domain.recruitment.comment.controller.dto.request.RecruitmentCommentSaveRequest;
import grep.neogulcoder.domain.recruitment.post.RecruitmentPost;
import grep.neogulcoder.domain.recruitment.post.repository.RecruitmentPostRepository;
import grep.neogulcoder.domain.study.Study;
import grep.neogulcoder.domain.study.enums.Category;
import grep.neogulcoder.domain.study.repository.StudyRepository;
import grep.neogulcoder.domain.users.entity.User;
import grep.neogulcoder.domain.users.repository.UserRepository;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RecruitmentPostCommentServiceTest extends IntegrationTestSupport {

    @Autowired
    private RecruitmentPostCommentService commentService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private RecruitmentPostRepository postRepository;

    @Autowired
    private AlarmRepository alarmRepository;

    @DisplayName("모집글 댓글을 저장할 때 모집글을 만든 회원이 아니라면 알림이 간다.")
    @Test
    void save() {
        //given
        User postCreator = createUser("모집글 만든이");
        User user = createUser("회원");
        userRepository.saveAll(List.of(postCreator, user));

        Study study = createStudy("스터디 이름", Category.IT);
        studyRepository.save(study);

        RecruitmentPost recruitmentPost = createRecruitmentPost(study.getId(), postCreator.getId(), "모집글 제목", "모집글 내용");
        postRepository.save(recruitmentPost);

        RecruitmentCommentSaveRequest request = createCommentSaveRequest("댓글 내용");

        //when
        commentService.save(recruitmentPost.getId(), request, user.getId());

        //then
        List<Alarm> alarms = alarmRepository.findAll();
        assertThat(alarms).hasSize(1)
                .extracting("receiverUserId", "alarmType")
                .containsExactly(Tuple.tuple(
                        postCreator.getId(), AlarmType.RECRUITMENT_POST_COMMENT
                ));
    }

    private static User createUser(String nickname) {
        return User.builder()
                .nickname(nickname)
                .build();
    }

    private Study createStudy(String name, Category category) {
        return Study.builder()
                .name(name)
                .category(category)
                .build();
    }

    private RecruitmentPost createRecruitmentPost(long studyId, long userId, String subject, String content) {
        return RecruitmentPost.builder()
                .studyId(studyId)
                .userId(userId)
                .subject(subject)
                .content(content)
                .build();
    }

    private RecruitmentCommentSaveRequest createCommentSaveRequest(String content) {
        return new RecruitmentCommentSaveRequest(content);
    }

}