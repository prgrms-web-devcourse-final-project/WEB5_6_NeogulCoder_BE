package grep.neogulcoder.domain.users.service;

import grep.neogulcoder.domain.users.exception.EmailDuplicationException;
import grep.neogulcoder.domain.users.exception.MailSendException;
import grep.neogulcoder.domain.users.exception.NotVerifiedEmailException;
import grep.neogulcoder.domain.users.exception.code.UserErrorCode;
import grep.neogulcoder.domain.users.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class MailService {

    private final ConcurrentHashMap<String, LocalDateTime> emailExpiredTimeMap = new ConcurrentHashMap<>();
    private final Set<String> verifiedEmailSet = ConcurrentHashMap.newKeySet();
    private final JavaMailSender mailSender;
    private final UserRepository userRepository;

    private static final int VERIFY_LIMIT_MINUTE = 5;

    @Scheduled(cron = "0 0 0 * * * ")
    public void clearStores() {
        emailExpiredTimeMap.clear();
        verifiedEmailSet.clear();
        log.info("회원 인증 코드 저장 Map, Set Clear");
    }

    @Async("mailExecutor")
    public void sendCodeTo(String email, LocalDateTime currentDateTime) {
        if (isDuplicateEmail(email)) {
            throw new EmailDuplicationException(UserErrorCode.IS_DUPLICATED_MALI);
        }

        LocalDateTime expiredDateTime = currentDateTime.plusMinutes(VERIFY_LIMIT_MINUTE);
        emailExpiredTimeMap.put(email, expiredDateTime);
        sendCodeTo(email);
    }

    public boolean verifyEmailCode(String email, LocalDateTime currentDateTime) {
        LocalDateTime expiredTime = Optional.ofNullable(emailExpiredTimeMap.get(email))
                .orElseThrow(() -> new NotVerifiedEmailException(UserErrorCode.NOT_VERIFIED_EMAIL));

        boolean isVerify = currentDateTime.isBefore(expiredTime);
        if (isVerify) {
            verifiedEmailSet.add(email);
        }
        return isVerify;
    }

    public boolean confirmNotVerifiedEmail(String email) {
        return !verifiedEmailSet.contains(email);
    }

    private boolean isDuplicateEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private void sendCodeTo(String email) {
        String code = generateRandomIntCode();
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(email);
            helper.setSubject("[wibby] 이메일 인증 코드");
            String htmlContent = getContent(code);
            helper.setText(htmlContent, true);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new MailSendException(UserErrorCode.MAIL_SEND_EXCEPTION);
        }
    }

    private String generateRandomIntCode() {
        return String.format("%06d", new Random().nextInt(1000000));
    }

    private String getContent(String code) {
        return "<div style='font-family:Arial,sans-serif;line-height:1.6;color:#333;'>"
                + "    <h2 style='color:#4CAF50;'>[wibby] 이메일 인증</h2>"
                + "    <p>이용해 주셔서 감사 합니다!</p>"
                + "    <p>아래 인증 코드를 입력해 주세요</p>"
                + "    <div style='font-size:24px;font-weight:bold;"
                + "                padding:10px 20px;"
                + "                background:#f4f4f4;"
                + "                border:1px solid #ddd;"
                + "                display:inline-block;"
                + "                margin:20px 0;'>"
                + code
                + "    </div>"
                + "    <p style='font-size:12px;color:#888;'>이 코드는 5분간 유효합니다.</p>"
                + "</div>";
    }
}
