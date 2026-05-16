package com.example.todoback.auth.repository;

import com.example.todoback.auth.domain.RevokedToken;
import java.time.Instant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RevokedTokenRepository extends JpaRepository<RevokedToken, String> {

    boolean existsByTokenIdAndExpiresAtAfter(String tokenId, Instant now);

    long deleteByExpiresAtBefore(Instant now);
}
