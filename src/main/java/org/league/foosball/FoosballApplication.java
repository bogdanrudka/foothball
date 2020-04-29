package org.league.foosball;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication
@EnableSwagger2
@EnableCaching
@Log4j2
public class FoosballApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoosballApplication.class, args);
    }


    // Fix the CORS errors
    @Bean
    public FilterRegistrationBean simpleCorsFilter(@Value("${cors.ui.url}") String appUrl) {

        log.info("Configuring CORS support for the frontend app under url: {}", appUrl);

        var source = new UrlBasedCorsConfigurationSource();
        var config = new CorsConfiguration();
        config.setAllowCredentials(true);

        // *** URL below needs to match the Vue client URL and port ***
        config.setAllowedOrigins(Collections.singletonList(appUrl));
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        source.registerCorsConfiguration("/**", config);

        var bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
