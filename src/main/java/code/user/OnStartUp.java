package code.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
@Slf4j
class OnStartUp {

    @Value("${onStartUp.mainAdminUsername}")
    private String mainAdminUsername;

    @Value("${onStartUp.mainAdminPassword}")
    private String mainAdminPassword;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    OnStartUp(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void createMainAdmin() {
        if (userRepository.existsById(mainAdminUsername)) {
            log.info("Main admin already exists");
        } else {
            var mainAdmin = new User(
                    mainAdminUsername,
                    passwordEncoder.encode(mainAdminPassword),
                    UserRole.ADMIN
            );
            userRepository.add(mainAdmin);
            log.info("Main admin added.Username: {}" + " .Password: {}", mainAdminUsername, mainAdminPassword);
        }
    }
}
