package co.com.bancolombia.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Trazabilidad de Pedidos")
                        .description("Microservicio para el manejo de trazabilidad de cambios de estado de pedidos en el sistema Plazoleta")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipo Plazoleta")
                                .email("plazoleta@example.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
}
