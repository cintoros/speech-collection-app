package ch.fhnw.labeling_tool.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        if (authException instanceof DisabledException) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Please verify your email before you can login.");
        } else if (authException instanceof BadCredentialsException) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Password or username incorrect.");
        } else {
            logger.warn("unexpected AuthenticationException: ", authException);
            response.sendError(HttpStatus.UNAUTHORIZED.value(), authException.getMessage());
        }
    }
}
