package grep.neogulcoder.domain;

import grep.neogulcoder.domain.review.repository.MyReviewTagRepository;
import grep.neogulcoder.domain.review.repository.ReviewRepository;
import grep.neogulcoder.domain.review.repository.ReviewTagRepository;
import grep.neogulcoder.domain.review.service.ReviewService;
import grep.neogulcoder.domain.study.repository.StudyMemberRepository;
import grep.neogulcoder.domain.study.repository.StudyRepository;
import grep.neogulcoder.domain.users.repository.UserRepository;
import grep.neogulcoder.global.auth.jwt.JwtAuthenticationFilter;
import grep.neogulcoder.global.auth.jwt.JwtExceptionFilter;
import grep.neogulcoder.global.auth.repository.UserBlackListRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

@Transactional
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Import(IntegrationTestSupport.SecurityMocks.class)
public abstract class IntegrationTestSupport {

    @TestConfiguration
    static class SecurityMocks {
        @Bean JwtAuthenticationFilter jwtAuthenticationFilter() {
            return Mockito.mock(JwtAuthenticationFilter.class);
        }
        @Bean JwtExceptionFilter jwtExceptionFilter() {
            return Mockito.mock(JwtExceptionFilter.class);
        }
        @Bean UserBlackListRepository userBlackListRepository() {
            return Mockito.mock(UserBlackListRepository.class);
        }
    }

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
