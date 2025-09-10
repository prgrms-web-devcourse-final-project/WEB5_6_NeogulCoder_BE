package grep.neogulcoder.domain.users.service;

import grep.neogulcoder.domain.IntegrationTestSupport;
import grep.neogulcoder.domain.users.repository.UserRepository;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Slf4j
class MailServiceTest extends IntegrationTestSupport {

    @Autowired
    @Qualifier("mailExecutor")
    private Executor mailExecutor;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("회원의 이메일로 인증 코드를 전송 합니다")
    @Test
    void sendCodeTo() {
        //given
        JavaMailSender spyMailSender = spy(mailSender);
        doNothing().when(spyMailSender).send(any(MimeMessage.class));
        MailService spyMailService = new MailService(spyMailSender, userRepository);

        String testEmail = "test@test.com";
        LocalDateTime dateTime = LocalDateTime.of(2025, 9, 7, 16, 30);

        //when //then
        spyMailService.sendCodeTo(testEmail, dateTime);
    }

    @Disabled
    @DisplayName("회원의 이메일로 실제 코드를 전송 하는 테스트")
    @Test
    void realSendToCode() {
        //given
        String testEmail = "test@naver.com";
        LocalDateTime dateTime = LocalDateTime.of(2025, 9, 7, 16, 30);

        //when //then
        mailService.sendCodeTo(testEmail, dateTime);
    }

    @DisplayName("회원의 이메일로 전송된 인증 코드는 5분 이내 검증 가능 하다.")
    @Test
    void verifyEmailCode() {
        //given
        JavaMailSender spyMailSender = spy(mailSender);
        doNothing().when(spyMailSender).send(any(MimeMessage.class));
        MailService spyMailService = new MailService(spyMailSender, userRepository);

        String testEmail = "test@test.com";
        LocalDateTime requestDateTime = LocalDateTime.of(2025, 9, 7, 16, 30);
        LocalDateTime verifyDateTime = LocalDateTime.of(2025, 9, 7, 16, 34, 59);

        //when
        spyMailService.sendCodeTo(testEmail, requestDateTime);
        boolean isVerify = spyMailService.verifyEmailCode(testEmail, verifyDateTime);

        //then
        assertThat(isVerify).isTrue();
    }

    @DisplayName("회원의 이메일로 전송된 인증 코드는 5분 이후 검증 시 실패 한다.")
    @Test
    void verifyEmailCode_WhenExpired_ThenFailed() {
        //given
        JavaMailSender spyMailSender = spy(mailSender);
        doNothing().when(spyMailSender).send(any(MimeMessage.class));
        MailService spyMailService = new MailService(spyMailSender, userRepository);

        String testEmail = "test@test.com";
        LocalDateTime requestDateTime = LocalDateTime.of(2025, 9, 7, 16, 30);
        LocalDateTime verifyDateTime = LocalDateTime.of(2025, 9, 7, 16, 35);

        //when
        spyMailService.sendCodeTo(testEmail, requestDateTime);
        boolean isVerify = spyMailService.verifyEmailCode(testEmail, verifyDateTime);

        //then
        assertThat(isVerify).isFalse();
    }

    @Disabled
    @DisplayName("mailExecutor 테스트")
    @Test
    void mailExecutor() {
        //given
        ThreadPoolTaskExecutor ex = (ThreadPoolTaskExecutor) mailExecutor;
        List<MyTask> myTasks = Stream.generate(MyTask::new)
                .limit(50)
                .toList();

        log.info("executor 시작");
        printExecutor();

        //when
        for (MyTask task : myTasks) {
            ex.execute(task);
            printExecutor();
        }

        log.info("executor 종료");
        ex.shutdown();
        printExecutor();
    }

    private void printExecutor() {
        ThreadPoolTaskExecutor ex = (ThreadPoolTaskExecutor) mailExecutor;
        ThreadPoolExecutor poolEx = ex.getThreadPoolExecutor();

        log.info("poolSize: {}, activeCount: {}, queueSize: {}, completedTaskCount: {}",
                poolEx.getPoolSize(),
                poolEx.getActiveCount(),
                poolEx.getQueue().size(),
                poolEx.getCompletedTaskCount()
        );
    }

    @Slf4j
    static class MyTask implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                log.info("{} 스레드 작업 중", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                log.info("작업 중지");
            }
        }
    }
}