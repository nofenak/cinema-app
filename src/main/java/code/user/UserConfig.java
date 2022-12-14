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

    @Bean
    UserFacade authFacade(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager
    ) {
        return new UserFacade(
                userRepository,
                passwordEncoder,
                authenticationManager
        );
    }

    @Bean
    UserRepository userRepository(JpaUserRepository jpaUserRepository) {
        return new JpaUserRepositoryAdapter(jpaUserRepository);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
