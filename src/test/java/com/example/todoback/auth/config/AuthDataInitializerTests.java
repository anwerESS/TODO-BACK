package com.example.todoback.auth.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.todoback.auth.domain.AppUser;
import com.example.todoback.auth.repository.AppUserRepository;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthDataInitializerTests {

    private static final Instant NOW = Instant.parse("2026-05-16T10:00:00Z");

    @Mock
    private AppUserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void createsDefaultUserWhenMissing() {
        DefaultUserProperties properties = new DefaultUserProperties("user", "1234");
        AuthDataInitializer initializer = new AuthDataInitializer(
                userRepository,
                properties,
                passwordEncoder,
                Clock.fixed(NOW, ZoneOffset.UTC)
        );

        when(userRepository.existsByUsername("user")).thenReturn(false);
        when(passwordEncoder.encode("1234")).thenReturn("encoded-password");

        initializer.run();

        ArgumentCaptor<AppUser> userCaptor = ArgumentCaptor.forClass(AppUser.class);
        verify(userRepository).save(userCaptor.capture());

        AppUser user = userCaptor.getValue();
        assertThat(user.getUsername()).isEqualTo("user");
        assertThat(user.getPassword()).isEqualTo("encoded-password");
        assertThat(user.isEnabled()).isTrue();
        assertThat(user.getCreatedAt()).isEqualTo(NOW);
    }

    @Test
    void keepsExistingDefaultUser() {
        DefaultUserProperties properties = new DefaultUserProperties("user", "1234");
        AuthDataInitializer initializer = new AuthDataInitializer(
                userRepository,
                properties,
                passwordEncoder,
                Clock.fixed(NOW, ZoneOffset.UTC)
        );

        when(userRepository.existsByUsername("user")).thenReturn(true);

        initializer.run();

        verify(userRepository, never()).save(org.mockito.ArgumentMatchers.any());
    }
}
