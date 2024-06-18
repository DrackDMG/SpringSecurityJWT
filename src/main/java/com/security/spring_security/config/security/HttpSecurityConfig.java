package com.security.spring_security.config.security;

import com.security.spring_security.config.security.filter.JwtAuthenticationFilter;
import com.security.spring_security.util.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
@EnableMethodSecurity
public class HttpSecurityConfig {

    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf( csrfConfig -> csrfConfig.disable())
                .sessionManagement(sessionMangConfig -> sessionMangConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
                //.authorizeHttpRequests(builderRequestMatchers()


        return http.build();
    }

    private static Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> builderRequestMatchers() {
        return authorize -> {
            authorize.requestMatchers(HttpMethod.POST, "/auth/login").permitAll();
            authorize.requestMatchers(HttpMethod.GET, "/auth/public-access").permitAll();
            authorize.requestMatchers("/auth/error").permitAll();

            authorize.requestMatchers(HttpMethod.GET, "/products").hasAnyAuthority(Permission.READ_ALL_PRODUCT.name());
            authorize.requestMatchers(HttpMethod.POST, "/products").hasAnyAuthority(Permission.SAVE_ONE_PRODUCT.name());
            authorize.requestMatchers(HttpMethod.GET, "/products/one/**").hasAnyAuthority(Permission.READ_ONE_PRODUCT.name());
            authorize.requestMatchers(HttpMethod.PUT, "/products/edit/**").hasAnyAuthority(Permission.EDIT_ONE_PRODUCT.name());
            authorize.requestMatchers(HttpMethod.DELETE, "/products/delete/**").hasAnyAuthority(Permission.DELETE_ONE_PRODUCT.name());

            authorize.anyRequest().denyAll();

        };
    }
}
