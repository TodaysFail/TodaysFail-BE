package com.todaysfailbe.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.useDefaultResponseMessages(false)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.todaysfailbe"))
				.paths(PathSelectors.ant("/api/v1/**"))
				.build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Todays Fail Backend - DEV")
				.description("Todays Fail - DEV 서버 API 명세")
				.version("1.0")
				.contact(new Contact("Todays Fail", null, "me@dev-domo.com"))
				.license("Copyrigth ⓒ 2023 Todays Fail All Rights Reserved.")
				.licenseUrl("https://todaysfail.com")
				.build();
	}
}
