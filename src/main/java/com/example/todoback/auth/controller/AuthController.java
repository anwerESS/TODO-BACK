package com.example.todoback.auth.controller;

import com.example.todoback.auth.dto.LoginRequest;
import com.example.todoback.auth.dto.LoginResponse;
import com.example.todoback.auth.dto.LogoutResponse;
import com.example.todoback.auth.service.JwtTokenService;
import com.example.todoback.auth.service.TokenRevocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final TokenRevocationService tokenRevocationService;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        return jwtTokenService.createToken(authentication);
    }

    @PostMapping("/logout")
    public LogoutResponse logout(@AuthenticationPrincipal Jwt jwt) {
        tokenRevocationService.revoke(jwt.getId(), jwt.getExpiresAt());
        return new LogoutResponse(true);
    }
}
