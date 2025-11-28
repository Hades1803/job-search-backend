package com.jobportal.backend.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt_secret}")
    private String secret;

    private final String ISSUER = "JobPortalSystem";

    public String generateToken(String email) {
        try {
            return JWT.create()
                    .withSubject("User Details")
                    .withClaim("email", email)
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                    .withIssuer(ISSUER)
                    .sign(Algorithm.HMAC256(secret));
        } catch (JWTCreationException ex) {
            throw new RuntimeException("JWT creation failed!");
        }
    }

    public String validateTokenAndRetrieveSubject(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                    .withSubject("User Details")
                    .withIssuer(ISSUER)
                    .build();

            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("email").asString();

        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("Invalid or expired JWT token");
        }
    }
}
