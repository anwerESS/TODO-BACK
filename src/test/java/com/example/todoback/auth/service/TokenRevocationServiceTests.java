package com.example.todoback.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.todoback.auth.domain.RevokedToken;
import com.example.todoback.auth.repository.RevokedTokenRepository;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TokenRevocationServiceTests {

    private static final Instant NOW = Instant.parse("2026-05-16T10:00:00Z");

    @Mock
    private RevokedTokenRepository revokedTokenRepository;

    @Test
    void revokePersistsTokenUntilItExpires() {
        TokenRevocationService service = new TokenRevocationService(
                revokedTokenRepository,
                Clock.fixed(NOW, ZoneOffset.UTC)
        );

        service.revoke("token-id", NOW.plusSeconds(60));

        ArgumentCaptor<RevokedToken> tokenCaptor = ArgumentCaptor.forClass(RevokedToken.class);
        verify(revokedTokenRepository).deleteByExpiresAtBefore(NOW);
        verify(revokedTokenRepository).save(tokenCaptor.capture());

        RevokedToken token = tokenCaptor.getValue();
        assertThat(token.getTokenId()).isEqualTo("token-id");
        assertThat(token.getExpiresAt()).isEqualTo(NOW.plusSeconds(60));
    }

    @Test
    void revokedTokenIsRejectedUntilItExpires() {
        TokenRevocationService service = new TokenRevocationService(
                revokedTokenRepository,
                Clock.fixed(NOW, ZoneOffset.UTC)
        );
        when(revokedTokenRepository.existsByTokenIdAndExpiresAtAfter("token-id", NOW)).thenReturn(true);

        assertThat(service.isRevoked("token-id")).isTrue();
        assertThat(service.isRevoked("another-token")).isFalse();
    }

    @Test
    void expiredTokenIsNotPersisted() {
        TokenRevocationService service = new TokenRevocationService(
                revokedTokenRepository,
                Clock.fixed(NOW, ZoneOffset.UTC)
        );

        service.revoke("token-id", NOW.minusSeconds(1));

        verify(revokedTokenRepository, never()).save(any());
    }
}
