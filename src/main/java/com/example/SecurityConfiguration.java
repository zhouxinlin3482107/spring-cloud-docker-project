package com.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableOAuth2Sso
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${test-home-page}")
    String redirectUrl;

    @Bean
    public static OAuth2AuthenticationProcessingFilterBeanPostProcessor oauth2AuthenticationProcessingFilterBeanPostProcessor() {
        return OAuth2AuthenticationProcessingFilterBeanPostProcessor.instance;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        OAuth2AuthenticationProcessingFilterBeanPostProcessor.instance.redirectUrl = redirectUrl;
        http
                .authorizeRequests()
                .antMatchers("/users/current", "/", "/settings").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl(redirectUrl)
        ;
    }

}