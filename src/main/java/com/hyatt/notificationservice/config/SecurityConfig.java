package com.hyatt.notificationservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Profile("!test")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] STATIC_REGEX_PATTERNS = {"/", "/error", "/favicon.ico", "/**/*.png", "/**/*.gif",
            "/**/*.svg", "/**/*.jpg", "/**/*.html", "/**/*.css", "/**/*.js", "/*.html", "/webjars"};

    private static final String[] SWAGGER_PATTERNS = {"/swagger-ui.html/**", "/webjars/**", "/swagger-resources/**", "/v2/api-docs/**", "/csrf"};


    private static final String USER_ROLE = "USER";
    private static final String NOOP_SECRET = "{noop}";
    private static final String[] SOCKET_REGEX = {"/socket/**", "/notifications/**", "/notifications"};

    @Value("${app.user.name:extensionuser}")
    private String name;

    @Value("${app.user.password:extensionuserpass}")
    private String password;

    // @formatter:off
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                    .disable()
                .authorizeRequests()
                    .antMatchers(HttpMethod.OPTIONS)
                        .permitAll()
                    .antMatchers(STATIC_REGEX_PATTERNS)
                        .permitAll()
                    .antMatchers(SWAGGER_PATTERNS)
                        .permitAll()
                    .antMatchers(SOCKET_REGEX)
                        .permitAll()
                    .anyRequest()
                        .authenticated()
                    .and()
                .httpBasic();
    }
    // @formatter:on

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(name)
                .password(NOOP_SECRET + password)
                .roles(USER_ROLE);
    }
}