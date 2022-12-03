package me.niloybiswas.spblog.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getInfo())
                .securityContexts(securityContexts())
                .securitySchemes(apiKeys())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo getInfo() {
        return new ApiInfo(
                "Blog App Spring Boot: Backend",
                "This is a project used for learning Spring Boot framework",
                "1.0",
                "Terms of Service",
                new Contact("Niloy", "https://www.niloybiswas.me", "n@b.com"),
                "License",
                "License URL",
                Collections.emptyList()
        );
    }

    private List<SecurityContext> securityContexts() {
        return Arrays.asList(SecurityContext
                .builder()
                .securityReferences(securityReferences())
                .build()
        );
    }

    private List<SecurityReference> securityReferences() {
        AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");
        return Arrays.asList(new SecurityReference("JWT", new AuthorizationScope[]{scope}));
    }

    private List<SecurityScheme> apiKeys() {
        return Arrays.asList(new ApiKey("JWT", AUTHORIZATION, "header"));
    }

}
