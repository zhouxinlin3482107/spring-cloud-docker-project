package com.example;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class OAuth2AuthenticationProcessingFilterBeanPostProcessor implements BeanPostProcessor {

    protected String redirectUrl = "/";
    protected static OAuth2AuthenticationProcessingFilterBeanPostProcessor instance = new OAuth2AuthenticationProcessingFilterBeanPostProcessor();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean != null && bean instanceof FilterChainProxy) {

            FilterChainProxy chains = (FilterChainProxy) bean;

            for (SecurityFilterChain chain : chains.getFilterChains()) {
                chain.getFilters().stream().filter(filter -> filter instanceof OAuth2ClientAuthenticationProcessingFilter).forEach(filter -> {
                    OAuth2ClientAuthenticationProcessingFilter oAuth2ClientAuthenticationProcessingFilter = (OAuth2ClientAuthenticationProcessingFilter) filter;
                    SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
                    successHandler.setDefaultTargetUrl(redirectUrl);
                    oAuth2ClientAuthenticationProcessingFilter.setAuthenticationSuccessHandler(successHandler);
                });
            }
        }
        return bean;
    }
}
