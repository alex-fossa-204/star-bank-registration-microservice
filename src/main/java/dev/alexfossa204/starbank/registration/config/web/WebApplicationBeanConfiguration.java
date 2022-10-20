package dev.alexfossa204.starbank.registration.config.web;

import dev.alexfossa204.starbank.registration.service.exception.UriSyntaxServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@Configuration
public class WebApplicationBeanConfiguration {

    @Value("${verification.code.check.url}")
    private String verificationCodeCheckUrl;

    @Bean
    public URI verificationCodeValidationPostRequestUri() {
        Optional<URI> uriOptional = Optional.empty();
        try {
            URI uri = new URI(verificationCodeCheckUrl);
            uriOptional = Optional.of(uri);
        } catch (URISyntaxException uriSyntaxException) {
            throw new UriSyntaxServiceException(uriSyntaxException.getMessage());
        }
        return uriOptional.get();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

}
