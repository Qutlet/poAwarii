package com.maciejBigos.poAwarii.security;

import com.maciejBigos.poAwarii.user.CustomUserDetails;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.interfaces.ECKey;
import java.util.Date;

@Component
public class JsonWebToken {
    private static final Logger logger = LoggerFactory.getLogger(JsonWebToken.class);


    public String generateJsonWebToken(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 3600000))
                .signWith(SignatureAlgorithm.HS512, "some secret code, hope it never leaks")
                .compact();
    }

    public String extractUsernameFromJsonWebToken(String JSON) {
        return Jwts.parser().setSigningKey("some secret code, hope it never leaks").parseClaimsJws(JSON).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey("some secret code, hope it never leaks").parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
