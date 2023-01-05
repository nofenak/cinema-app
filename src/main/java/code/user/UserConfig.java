package code.user;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@AllArgsConstructor
class UserConfig {

    private final JpaUserRepository jpaUserRepository;

    private final AuthenticationManager authenticationManager;

    @Bean
    UserFacade authFacade(PasswordEncoder passwordEncoder) {
        var userRepository = new JpaUserRepositoryAdapter(jpaUserRepository);
        return new UserFacade(
                userRepository,
                passwordEncoder,
                authenticationManager
        );
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
