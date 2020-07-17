package ch.fhnw.speech_collection_app.config;

import org.jooq.conf.Settings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class BeanConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        //NOTE: by default spring boot adds a ROLE_ prefix to the roles i.e. ROLE_ADMIN
        return new GrantedAuthorityDefaults("");
    }

    @Profile("dev-test")
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowCredentials(true)
                        .allowedHeaders("*")
                        .allowedMethods("*")
                        .allowedOrigins("*");
            }
        };
    }

    /**
     * Disable the schema mapping so it is possible to just change the datasource in the spring configuration
     * without breaking anything. It is also possible to use override it  in case of multiple schemas.
     * see https://www.jooq.org/doc/3.13/manual/sql-building/dsl-context/custom-settings/settings-render-mapping/
     */
    @Bean
    public Settings settings() {
        return new Settings().withRenderSchema(false);
    }
}
