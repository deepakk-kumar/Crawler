	package com.test.tsys.crawler.util;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.AuthorizationCodeGrantBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.TokenEndpoint;
import springfox.documentation.service.TokenRequestEndpoint;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	
	@Value("${security.jwt.client-id}")
	   private String clientId;

	   @Value("${security.jwt.client-secret}")
	   private String clientSecret;
	   private static final String AUTH_SERVER="http://localhost:8080/oauth";
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select().apis(RequestHandlerSelectors.basePackage("com.test.tsys.crawler.controller"))
                .build().securitySchemes(Arrays.asList(securityScheme()))
                .securityContexts(Arrays.asList(securityContext())).apiInfo(apiInformation());
    }
    
    
    private ApiInfo apiInformation() {
        ApiInfo apiInfo = new ApiInfo(
                "Web Crawler REST API",
                "T Systems Assignment",
                "1.0",
                "Terms of service",
                new Contact("Deepak", "http://localhost:8080/swagger-ui.html#", "dk9513@gmail.com"),
                "Licence",
                "http://localhost:8080/swagger-ui.html#");
        return apiInfo;
    }
    
    private SecurityScheme securityScheme() {
        GrantType grantType = new AuthorizationCodeGrantBuilder()
            .tokenEndpoint(new TokenEndpoint(AUTH_SERVER + "/token", "oauthtoken"))
            .tokenRequestEndpoint(
              new TokenRequestEndpoint(AUTH_SERVER + "/authorize", clientId, clientSecret))
            .build();
     
        SecurityScheme oauth = new OAuthBuilder().name("spring_oauth")
            .grantTypes(Arrays.asList(grantType))
            .scopes(Arrays.asList(scopes()))
            .build();
        return oauth;
    }
    
    private AuthorizationScope[] scopes() {
        AuthorizationScope[] scopes = { 
          new AuthorizationScope("read", "for read operations"), 
          new AuthorizationScope("write", "for write operations"), 
          new AuthorizationScope("foo", "Access foo API") };
        return scopes;
    }
    
    private SecurityContext securityContext() {
        return SecurityContext.builder()
          .securityReferences(
            Arrays.asList(new SecurityReference("spring_oauth", scopes())))
          .forPaths(PathSelectors.regex("/*"))
          .build();
    }
}