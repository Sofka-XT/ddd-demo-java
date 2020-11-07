package co.com.sofka.cargame.infra.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.logging.Logger;

@Configuration
public class CorsConfiguration {
    @Value("${server.origin}")
    private String origin;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                if (!origin.isBlank()) {
                    Logger.getLogger("config").info("Allowed Origin ==> " + origin);
                    registry.addMapping("/**").allowedOrigins(origin);
                }
            }
        };
    }
}