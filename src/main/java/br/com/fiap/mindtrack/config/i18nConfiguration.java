package br.com.fiap.mindtrack.config;


import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class i18nConfiguration implements WebMvcConfigurer {
    @Bean
    MessageSource messageSource(){
        var messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("i18n/settings", "i18n/register",
                "i18n/login", "i18n/humor", "i18n/history", "i18n/sidebar",
                "i18n/index", "i18n/footer", "i18n/dashboard"
        );
        return messageSource;
    }

    @Bean
    LocaleChangeInterceptor localeChangeInterceptor(){
        var localeInterceptor = new LocaleChangeInterceptor();
        localeInterceptor.setParamName("lang");
        return localeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    LocaleResolver localeResolver(){
        var localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(new Locale("pt", "BR"));
        return localeResolver;
    }
}