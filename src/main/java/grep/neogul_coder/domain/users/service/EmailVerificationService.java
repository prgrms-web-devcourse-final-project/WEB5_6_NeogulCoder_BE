package grep.neogul_coder.domain.users.service;

import java.time.Duration;
import java.util.Random;

import grep.neogul_coder.domain.users.exception.EmailDuplicationException;
import grep.neogul_coder.domain.users.exception.code.UserErrorCode;
import grep.neogul_coder.domain.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailVerificationService  {

    private final JavaMailSender mailSender;
    private final RedisTemplate<String, Object> redisTemplate;
    private final UserRepository userRepository;

    private static final long CODE_TTL_SECONDS = 300;

    public void sendVerificationEmail(String email) {

        if(isDuplicateEmail(email)) {
            throw new EmailDuplicationException(UserErrorCode.IS_DUPLICATED_MALI);
        }

        String code = generateRandomCode();

        sendEmail(email,code);

        redisTemplate.opsForValue().set(getRedisKey(email), code, Duration.ofSeconds(CODE_TTL_SECONDS));
    }

    public boolean verifyCode(String email, String inputCode) {
        String redisKey = getRedisKey(email);
        Object storedCode = redisTemplate.opsForValue().get(getRedisKey(email));


        if (isValidCode(storedCode,inputCode)) {
            redisTemplate.delete(redisKey);

            redisTemplate.opsForValue().set(getVerifiedKey(email), "true", Duration.ofMinutes(10));
            return true;
        }
        return false;
    }

    public boolean isNotEmailVerified(String email) {
        Object value = redisTemplate.opsForValue().get(getVerifiedKey(email));
        return !"true".equals(value);
    }

    public void clearVerifiedStatus(String email) {
        redisTemplate.delete(getVerifiedKey(email));
    }

    private void sendEmail(String to, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("[wibby] 이메일 인증 코드");
        message.setText("인증 코드: " + code + "\n5분 안에 입력해주세요.");
        mailSender.send(message);
    }

    private String generateRandomCode() {
        return String.format("%06d", new Random().nextInt(1000000));
    }

    private String getRedisKey(String email) {
        return "email_verification:" + email;
    }

    private String getVerifiedKey(String email) {
        return "email_verified:" + email;
    }

    private boolean isDuplicateEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private boolean isValidCode(Object storedCode, String inputCode) {
        return storedCode != null && storedCode.equals(inputCode);
    }
}
