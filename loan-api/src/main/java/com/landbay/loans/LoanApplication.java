package com.landbay.loans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class LoanApplication extends SpringBootServletInitializer {

    public static final String PACKAGE_TO_SCAN = "com.landbay.loans";
    public static void main(String [] args) {
        SpringApplication.run(LoanApplication.class,args);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer applicationConfigurer() {
        final PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        return configurer;
    }


    @Override
    protected final SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
        return application.sources(LoanApplication.class);
    }
}
