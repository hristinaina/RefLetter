package com.ftn.sbnz.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private UserDetailsService userDetailsService;

    private JwtTokenUtil tokenUtil;

    protected final Log LOGGER = LogFactory.getLog(getClass());

    public JwtRequestFilter(JwtTokenUtil tokenHelper, UserDetailsService userDetailsService) {
        this.tokenUtil = tokenHelper;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        List<String> pathsToSkip = Arrays.asList("/api/login", "/api/register/**", "/api/unregisteredUser/**", "/api/public");
        return pathsToSkip.stream().anyMatch(path::startsWith);
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String username;
        String authToken = tokenUtil.getToken(request);

        try {
            if (authToken != null) {
                username = tokenUtil.getEmailFromToken(authToken);

                if (username != null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    if (tokenUtil.validateToken(authToken, userDetails)) {
                        TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
                        authentication.setToken(authToken);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } else {
                        throw new AuthenticationCredentialsNotFoundException("Token validation failed");
                    }
                }
            } else {
                throw new AuthenticationCredentialsNotFoundException("No token provided");
            }
        } catch (ExpiredJwtException ex) {
            String isRefreshToken = request.getHeader("isRefreshToken");
            String requestURL = request.getRequestURL().toString();

            if (isRefreshToken != null && isRefreshToken.equals("true") && requestURL.contains("refreshtoken")) {
                allowForRefreshToken(ex, request, authToken);
            } else {
                throw new AuthenticationCredentialsNotFoundException("Token expired");
            }
        } catch (AuthenticationCredentialsNotFoundException ex) {
            throw new ServletException("Authentication failed", ex);
        }

        chain.doFilter(request, response);
    }

    private void allowForRefreshToken(ExpiredJwtException ex, HttpServletRequest request, String authToken) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(tokenUtil.getEmailFromToken(authToken));
        // create a UsernamePasswordAuthenticationToken with null values.
        TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
        authentication.setToken(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Set the claims so that in controller we will be using it to create
        // new JWT
        request.setAttribute("claims", ex.getClaims());

    }

}