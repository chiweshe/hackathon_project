package com.hackathon.verification.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Configuration
public class VerificationOpenApiConfig {

    @Bean
    @Primary
    public OpenAPI trustVerifyApiDoc() {
        Server localServer = new Server();
        localServer.setUrl("http://localhost:8080");
        localServer.setDescription("Local Development Server");

        Server productionServer = new Server();
        productionServer.setUrl("https://api.trustverify.com");
        productionServer.setDescription("Production Server");

        Contact contact = new Contact();
        contact.setEmail("info@trustverify.com");
        contact.setName("TrustVerify Support");
        contact.setUrl("https://www.trustverify.com/support");

        License mitLicense = new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT");

        Info info = new Info()
                .title("TrustVerify API")
                .version("1.0")
                .contact(contact)
                .description("This API provides verification services for land, vehicles, and landlord/tenant relationships.")
                .termsOfService("https://www.trustverify.com/terms")
                .license(mitLicense);

        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer, productionServer));
    }
}
