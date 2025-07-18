package grep.neogul_coder.global.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ActiveProfileLogger {
    private final Environment environment;

    public ActiveProfileLogger(Environment environment) {
        this.environment = environment;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void logActiveProfiles() {
        String[] activeProfiles = environment.getActiveProfiles();
        log.info("==============================================================================");
        log.info("★ ★ ★ ★ ★  [실행 프로파일] Active Spring Profiles: {} ★ ★ ★ ★ ★  ", (Object) activeProfiles);
        log.info("==============================================================================");
    }
}
