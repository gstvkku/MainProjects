package com.creativetouch.auth_service.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.creativetouch.auth_service.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TokenConfig {

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(User user) {

        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withSubject(user.getId().toString())
                .withClaim("email", user.getEmail())
                .withExpiresAt(Instant.now().plusSeconds(86400))
                .sign(algorithm);
    }

    public Optional<JWTUserData> validateToken(String token) {

        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);

            DecodedJWT decode = JWT.require(algorithm)
                    .build()
                    .verify(token);

            UUID userId = UUID.fromString(decode.getSubject());

            return Optional.of(
                    JWTUserData.builder()
                            .userId(userId)
                            .email(decode.getClaim("email").asString())
                            .build()
            );

        } catch (JWTVerificationException | IllegalArgumentException ex) {
            return Optional.empty();
        }
    }
}