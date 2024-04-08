package ru.edu.pasteservice.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.token.key}")
    private String key;

    public String extractUsername(String jwt) {
        DecodedJWT decodedJWT = decodeJwt(jwt);
        return decodedJWT.getSubject();
    }

    public String extractRole(String jwt) {
        DecodedJWT decodedJWT = decodeJwt(jwt);
        return decodedJWT.getClaim("role").asString();
    }

    public String generateJwt(UserDetails userDetails) {
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withClaim("role", userDetails.getAuthorities().stream().toList().get(0).getAuthority())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(60*60*24))
                .sign(Algorithm.HMAC256(key.getBytes()));
    }

    public boolean isJwtValid(String jwt, UserDetails userDetails) {
        String username = extractUsername(jwt);
        return username.equals(userDetails.getUsername()) && !isJwtExpired(jwt);
    }

    private boolean isJwtExpired(String jwt) {
        DecodedJWT decodedJWT = decodeJwt(jwt);
        return decodedJWT.getExpiresAt().before(Date.from(Instant.now()));
    }

    private DecodedJWT decodeJwt(String jwt) {
        return JWT.require(Algorithm.HMAC256(key.getBytes())).build().verify(jwt);
    }
}
