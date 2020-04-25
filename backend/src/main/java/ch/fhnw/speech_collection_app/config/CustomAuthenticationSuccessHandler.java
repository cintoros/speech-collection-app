package ch.fhnw.speech_collection_app.config;

import ch.fhnw.speech_collection_app.features.base.user.CustomUserDetails;
import ch.fhnw.speech_collection_app.features.base.user.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public CustomAuthenticationSuccessHandler(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        var user = (CustomUserDetails) authentication.getDetails();
        customUserDetailsService.updateLastLogin(user.user.getId());

    }
}
