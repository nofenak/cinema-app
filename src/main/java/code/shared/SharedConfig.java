package code.shared;

import com.google.common.eventbus.EventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.ZoneOffset;

@Configuration
class SharedConfig {

    @Bean
    EventBus eventBus() {
        return new EventBus();
    }

    @Bean
    @Profile("prod")
    Clock prodClock() {
        return Clock.systemUTC();
    }

    @Bean
    @Profile("test")
    Clock testClock() {
        var currentYear = Year.now().getValue();
        var testDateAsInstant = LocalDateTime
                .of(currentYear, 5, 8, 18, 30)
                .toInstant(ZoneOffset.UTC);
        return Clock.fixed(
                testDateAsInstant,
                ZoneOffset.UTC
        );
    }
}
