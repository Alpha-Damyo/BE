package com.damyo.alpha.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${springdoc.swagger-ui.swg3-address}")
    private String address;

    @Bean
    public OpenAPI openAPI() {
        String jwt = "jwt";
        String providerToken = "providerToken";
        Components components = new Components()
                .addSecuritySchemes(jwt, new SecurityScheme().name(jwt).type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT"))
                .addSecuritySchemes(providerToken, new SecurityScheme().name(providerToken).type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("token"));

        Server server = new Server();
        server.setUrl(address);

        return new OpenAPI()
                .info(apiInfo())
                .components(components)
                .servers(List.of(server));
    }

    private Info apiInfo() {
        return new Info()
                .title("Damyo's Spring Server API Docs")
                .description("This is Damyo's API Docs")
                .version("1.0.0");
    }
}
