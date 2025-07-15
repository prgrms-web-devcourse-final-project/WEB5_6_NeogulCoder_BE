package grep.neogul_coder.domain;

import grep.neogul_coder.domain.review.controller.service.ReviewService;
import grep.neogul_coder.domain.review.repository.MyReviewTagRepository;
import grep.neogul_coder.domain.review.repository.ReviewRepository;
import grep.neogul_coder.domain.review.repository.ReviewTagRepository;
import grep.neogul_coder.domain.study.repository.StudyMemberRepository;
import grep.neogul_coder.domain.study.repository.StudyRepository;
import grep.neogul_coder.domain.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
public abstract class IntegrationTestSupport {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private StudyMemberRepository studyMemberRepository;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MyReviewTagRepository myReviewTagRepository;

    @Autowired
    private ReviewTagRepository reviewTagRepository;
}
