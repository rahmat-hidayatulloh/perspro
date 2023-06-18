package id.co.perspro.loginservice.configuration;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class OpenApiConfig {

  @Value("${perspro.openapi.url}")
  private String openApiUrl;
  
 @Bean
  public OpenAPI openApi() {
    return new OpenAPI()
            .info(apiInfo())
            .servers(List.of(apiServer()))
            .addSecurityItem(new SecurityRequirement()
                    .addList("bearerAuth"))
            .components(new Components()
                    .addSecuritySchemes("bearerAuth", new SecurityScheme()
                            .name("bearerAuth")
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")));
  }

  private Server apiServer() {
    return new Server()
            .url(openApiUrl)
            .description("OpenApi Perspro Project");
  }

  private Info apiInfo() {
    return new Info()
            .title("Perspro Management API")
            .version("1.0")
            .contact(apiContact())
            .description("This API exposes endpoints to manage perspro.")
            .termsOfService("https://www.perspro.com/terms")
            .license(apiLicense());
  }

  private License apiLicense() {
    return new License()
            .name("MIT License")
            .url("https://choosealicense.com/licenses/mit/");
  }

  private Contact apiContact() {
    return new Contact()
            .email("rahmat.hidayatulloh2010@gmail.com")
            .name("Perspro")
            .url("https://www.perspro.com");
  }
}
