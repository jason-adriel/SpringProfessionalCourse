package org.jasonadriel.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.jasonadriel.ApplicationLauncher;
import org.jasonadriel.service.TransactionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackageClasses = ApplicationLauncher.class)
@PropertySource("classpath:/application.properties")
public class BankApplicationConfiguration {
    @Bean
    public ObjectMapper objectMapper() {
       return new ObjectMapper().registerModule(new JavaTimeModule());
    }
}
