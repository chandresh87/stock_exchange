package com.jp.stock.api.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configuration class for Swagger
 *
 * @author chandresh.mishra
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket api() {
    final Contact contact =
        new Contact(
            "simple stock exchange", "https://www.jpmorgan.com/", "media.requests@jpmorgan.com");
    final ApiInfo info =
        new ApiInfoBuilder()
            .title("Exchange API")
            .description("simple stock exchange API")
            .version("1.0.0")
            .termsOfServiceUrl("http://terms-of-service.url")
            .contact(contact)
            .license("License")
            .licenseUrl("http://license.url")
            .build();
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(info)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.regex("/.*"))
        .paths(Predicates.not(PathSelectors.regex("/error")))
        .build()
        .pathMapping("/")
        .genericModelSubstitutes(ResponseEntity.class)
        .useDefaultResponseMessages(false);
  }
}
