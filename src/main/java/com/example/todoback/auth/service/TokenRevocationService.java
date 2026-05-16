package com.example.todoback.auth.service;

import com.example.todoback.auth.domain.RevokedToken;
import com.example.todoback.auth.repository.RevokedTokenRepository;
import java.time.Clock;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TokenRevocationService {

    private final RevokedTokenRepository revokedTokenRepository;
    private final Clock clock;

    @Transactional
    public void revoke(String tokenId, Instant expiresAt) {
        cleanupExpiredTokens();
        if (tokenId == null || expiresAt == null || expiresAt.isBefore(Instant.now(clock))) {
            return;
        }
        if (revokedTokenRepository.existsById(tokenId)) {
            return;
        }

        revokedTokenRepository.save(new RevokedToken(tokenId, expiresAt));
    }

    @Transactional(readOnly = true)
    public boolean isRevoked(String tokenId) {
        if (tokenId == null) {
            return false;
        }

        return revokedTokenRepository.existsByTokenIdAndExpiresAtAfter(tokenId, Instant.now(clock));
    }

    private long cleanupExpiredTokens() {
        return revokedTokenRepository.deleteByExpiresAtBefore(Instant.now(clock));
    }
}
