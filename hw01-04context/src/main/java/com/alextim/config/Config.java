package com.alextim.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Configuration
public class Config {

    @Bean
    public ApplicationSettings applicationSettings() {
        return new ApplicationSettings();
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms =  new ReloadableResourceBundleMessageSource();
        ms.setBasename("/i18n/bundle");
        ms.setDefaultEncoding("UTF-8");
        return ms ;
    }

    @Bean
    public BufferedReader bufferedReader() {
        return new BufferedReader(new InputStreamReader(System.in));
    }
}
