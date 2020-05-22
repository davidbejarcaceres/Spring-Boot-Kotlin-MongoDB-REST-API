package com.davidbejarcaceres.kotlinmongo

import org.springframework.context.annotation.Bean
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.Contact
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc
import java.util.Collections


@Configuration
@EnableSwagger2WebMvc
class SwaggerConfig {
    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/players/**"))
                .build()
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfo("Players API",
                "Players Micro-Service, in Charge of players, CRUD operations on top of MongoDB with Feign capabilities.",
                "1", "termsOfServiceUrl",
                Contact(
                        "David Bejar Caceres",
                        "https://www.linkedin.com/in/davidbejarcaceres/",
                        "dbc770@inlumine.ual.es"),
                "GPL 2", "https://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html",
                Collections.emptyList())
    }
}