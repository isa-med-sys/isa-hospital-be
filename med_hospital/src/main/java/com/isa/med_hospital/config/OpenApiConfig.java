package com.isa.med_hospital.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Medical equipment",
                        email = "medicalequipment753@gmail.com",
                        url = " " // url for the website
                ),
                description = "OpenApi documentation",
                title = "OpenApi documentation",
                version = "1.0"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8082"
                )
        }
)
public class OpenApiConfig {

}
