package com.maciejBigos.poAwarii.security;

import com.auth0.jwt.exceptions.TokenExpiredException;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JsonWebToken {
    private static final Logger logger = LoggerFactory.getLogger(JsonWebToken.class);


    public String generateJsonWebToken(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .setId(userDetails.getId())
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
            if(!Jwts.parser().setSigningKey("some secret code, hope it never leaks").parseClaimsJws(authToken).getBody().getExpiration().after(new Date())){
                return false;
            }
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        } catch (TokenExpiredException e) {
            logger.error("JWT expired: {}", e.getMessage());
        }

        return false;
    }
}
