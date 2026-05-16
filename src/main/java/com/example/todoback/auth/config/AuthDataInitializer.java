package com.example.todoback.auth.config;

import com.example.todoback.auth.domain.AppUser;
import com.example.todoback.auth.repository.AppUserRepository;
import java.time.Clock;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
@Order(0)
public class AuthDataInitializer implements CommandLineRunner {

    private final AppUserRepository userRepository;
    private final DefaultUserProperties defaultUserProperties;
    private final PasswordEncoder passwordEncoder;
    private final Clock clock;

    @Override
    @Transactional
    public void run(String... args) {
        if (userRepository.existsByUsername(defaultUserProperties.username())) {
            return;
        }

        userRepository.save(AppUser.builder()
                .username(defaultUserProperties.username())
                .password(passwordEncoder.encode(defaultUserProperties.password()))
                .enabled(true)
                .createdAt(Instant.now(clock))
                .build());
    }
}
