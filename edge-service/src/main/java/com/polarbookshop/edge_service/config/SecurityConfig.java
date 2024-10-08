package com.polarbookshop.edge_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(
            ServerHttpSecurity http,
            ReactiveClientRegistrationRepository repo) {
        return http.authorizeExchange(exchange-> exchange
                        .pathMatchers("/","/*.css","/*.js","/favicon.ico").permitAll()
                        .pathMatchers(HttpMethod.GET,"/books/**").permitAll()
                        .anyExchange().authenticated())
                .exceptionHandling(exceptionHandling->
                        exceptionHandling
                                .authenticationEntryPoint(
                                        new HttpStatusServerEntryPoint(
                                                HttpStatus.UNAUTHORIZED)))
                .oauth2Login(Customizer.withDefaults())
                .logout(logout-> logout.logoutSuccessHandler(logoutSuccessHandler(repo)))
                .csrf(csrf->csrf.csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse()))
                .build();
    }

    @Bean
    WebFilter csrfWebFilter(){
        return (exchange, chain) -> {
            exchange.getResponse().beforeCommit(()-> Mono.defer(
                    ()->{
                        Mono<CsrfToken> csrfToken = exchange.getAttribute(CsrfToken.class.getName());
                        return csrfToken!=null? csrfToken.then():Mono.empty();
                    }
            ));
            return chain.filter(exchange);
        };
    }

    private ServerLogoutSuccessHandler logoutSuccessHandler(ReactiveClientRegistrationRepository repo) {
        var oidcLogoutSuccessHandler = new OidcClientInitiatedServerLogoutSuccessHandler(repo);
        oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}");
        return oidcLogoutSuccessHandler;

    }
}
