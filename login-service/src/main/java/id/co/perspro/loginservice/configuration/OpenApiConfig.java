package id.co.perspro.loginservice.configuration;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class OpenApiConfig {

  @Value("${perspro.openapi.url}")
  private String openApiUrl;

  @Bean
  public OpenAPI openApi() {

    Server server = new Server();
    server.setUrl(openApiUrl);
    server.setDescription("OpenApi Perspro Project");

    Contact contact = new Contact();
    contact.setEmail("rahmat.hidayatulloh2010@gmail.com");
    contact.setName("Perspro");
    contact.setUrl("https://www.perspro.com");

    License mitLicense =
        new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

    Info info = new Info().title("Perspro Management API").version("1.0").contact(contact)
        .description("This API exposes endpoints to manage perspro.")
        .termsOfService("https://www.perspro.com/terms").license(mitLicense);

    return new OpenAPI().info(info).servers(List.of(server));
  }

}
