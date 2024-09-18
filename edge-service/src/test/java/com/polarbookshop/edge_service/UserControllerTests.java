package com.polarbookshop.edge_service;

import com.polarbookshop.edge_service.config.SecurityConfig;
import com.polarbookshop.edge_service.user.User;
import com.polarbookshop.edge_service.user.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@WebFluxTest(UserController.class)
@Import({SecurityConfig.class})
public class UserControllerTests {
    @Autowired
    WebTestClient testClient;
    @MockBean
    ReactiveClientRegistrationRepository repo;

    @Test
    void whenNotAuthenticatedThen401(){
        testClient.get().uri("/user").exchange().expectStatus().isUnauthorized();
    }

    @Test
    void whenAuthenticatedThenReturnUser(){
        var expectedUser = new User("john","john","snow", List.of("employee","customer"));
        testClient
                .mutateWith(configureMockOidcLogin(expectedUser))
                .get().uri("/user").exchange().expectStatus().is2xxSuccessful()
                .expectBody(User.class)
                .value(user -> assertThat(user).isEqualTo(expectedUser));
    }

    private SecurityMockServerConfigurers.OidcLoginMutator configureMockOidcLogin(User user){
        return SecurityMockServerConfigurers.mockOidcLogin().idToken(
                builder->{
                    builder.claim(StandardClaimNames.PREFERRED_USERNAME,user.username());
                    builder.claim(StandardClaimNames.GIVEN_NAME,user.firstName());
                    builder.claim(StandardClaimNames.FAMILY_NAME,user.lastName());
                }
        );
    }
}
