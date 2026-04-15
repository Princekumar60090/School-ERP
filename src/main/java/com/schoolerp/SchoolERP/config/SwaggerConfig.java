package com.schoolerp.SchoolERP.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(name = "basicAuth", type= SecuritySchemeType.HTTP,scheme = "basic")
public class SwaggerConfig {

   @Bean
    public OpenAPI myCustomAPI(){
       return new OpenAPI()
               .info(
                       new Info().title("SchoolERP APIs")
                               .description("By Prince")
               );
   }
}
