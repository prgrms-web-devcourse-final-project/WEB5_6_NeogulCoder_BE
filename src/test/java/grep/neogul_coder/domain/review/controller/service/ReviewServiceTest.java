package grep.neogul_coder.domain.review.controller.service;

import grep.neogul_coder.domain.review.controller.dto.response.ReviewTargetUsersInfo;
import grep.neogul_coder.domain.study.Study;
import grep.neogul_coder.domain.study.StudyMember;
import grep.neogul_coder.domain.study.enums.Category;
import grep.neogul_coder.domain.study.repository.StudyMemberRepository;
import grep.neogul_coder.domain.study.repository.StudyRepository;
import grep.neogul_coder.domain.users.entity.User;
import grep.neogul_coder.domain.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

//@Transactional
//@ActiveProfiles("test")
//@SpringBootTest
//class ReviewServiceTest {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private StudyRepository studyRepository;
//
//    @Autowired
//    private StudyMemberRepository studyMemberRepository;
//
//    @Autowired
//    private ReviewService reviewService;
//
//    private long userId;
//    private long studyId;
//
//    @BeforeEach
//    void init(){
//        userId = userRepository.save(createUser("테스터")).getId();
//        Study study = studyRepository.save(createStudy("자바 스터디", Category.IT));
//        studyId = study.getId();
//        createStudyMember(study, userId);
//    }
//
//    @DisplayName("리뷰 대상 회원들의 정보를 조회 합니다.")
//    @Test
//    void getReviewTargetUsersInfo() {
//        //given
//        Study study = createStudy("자바 스터디", Category.IT);
//
//        for(int i = 0; i < 4; i++) {
//            User user = createUser("테스터" + i);
//            userRepository.save(user);
//
//            StudyMember studyMember = createStudyMember(study, user.getId());
//            studyMemberRepository.save(studyMember);
//        }
//
//        //when
//        ReviewTargetUsersInfo response = reviewService.getReviewTargetUsersInfo(study.getId(), "테스터0");
//        System.out.println(response);
//
//        //then
//    }
//
//    public static User createUser(String nickname){
//        return User.builder()
//                .nickname(nickname)
//                .password("tempPassword")
//                .build();
//    }
//
//    private static Study createStudy(String name, Category category){
//        return Study.builder()
//                .name(name)
//                .category(category)
//                .build();
//    }
//
//    private static StudyMember createStudyMember(Study study, long userId){
//        return StudyMember.builder()
//                .study(study)
//                .userId(userId)
//                .build();
//    }
//}