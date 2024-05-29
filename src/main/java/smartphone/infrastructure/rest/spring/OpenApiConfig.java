package smartphone.infrastructure.rest.spring;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(servers = {@Server(url = "${openapi.server-url}")})
public class OpenApiConfig {

    @Bean
    public OpenAPI customizeOpenApi() {

        return new OpenAPI()
                .info(
                        new Info()
                                .title("SmartphoneApp")
                                .description("SmartphoneApp API.")
                                .version("v1.0.0"));
    }
}
