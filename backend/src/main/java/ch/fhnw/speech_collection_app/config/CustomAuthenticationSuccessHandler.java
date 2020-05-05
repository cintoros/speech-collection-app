package ch.fhnw.speech_collection_app.config;

import ch.fhnw.speech_collection_app.features.base.user.CustomUserDetails;
import ch.fhnw.speech_collection_app.features.base.user.CustomUserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomAuthenticationSuccessHandler extends BasicAuthenticationFilter {
    private final CustomUserDetailsService customUserDetailsService;

    public CustomAuthenticationSuccessHandler(AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService) {
        super(authenticationManager);
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) {
        var user = (CustomUserDetails) authResult.getPrincipal();
        customUserDetailsService.updateLastLogin(user.getUser().getId());
    }
}
