package com.meows.meows.config;

/* template for jwt config */

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.meows.meows.entity.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TokenConfig {

    /*
    @Value("${jwt.secret}")
    private String secret;
    */
    private String secret = "secret";

    Algorithm algorithm = Algorithm.HMAC256(secret);

    public String generateToken(UserEntity userEntity){
        return JWT.create()
                .withClaim("userId", userEntity.getId())
                .withSubject(userEntity.getNome())
                .withExpiresAt(Instant.now().plusSeconds(86400))
                .withIssuedAt(Instant.now())
                .sign(algorithm);
    }
}
