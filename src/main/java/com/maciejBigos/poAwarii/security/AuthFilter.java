package com.maciejBigos.poAwarii.security;

import com.maciejBigos.poAwarii.user.User;
import com.maciejBigos.poAwarii.user.UserRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.hibernate.internal.util.StringHelper.isEmpty;

public class AuthFilter extends OncePerRequestFilter {
    private final JsonWebToken jsonWebToken;
    private final UserRepository userRepository;

    public AuthFilter(JsonWebToken jsonWebToken, UserRepository userRepository) {
        this.jsonWebToken = jsonWebToken;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain chain) throws ServletException, IOException {

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (isEmpty(header)) {
            chain.doFilter(request, response);
            return;
        }

        final String token = header.split(" ")[1].trim();
        if (!jsonWebToken.validateJwtToken(token)) {
            chain.doFilter(request, response);
            return;
        }

        User user =  userRepository.findByEmail(jsonWebToken.extractUsernameFromJsonWebToken(token));
        CustomUserDetails userDetails = new CustomUserDetails(user);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
