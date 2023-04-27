package com.sprinboot.blog;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@OpenAPIDefinition ( info = @Info(
		title = "Spring Boot Blog App REST API's",
		description = "Spring Boot Blog App Rest Api Documentation",
		version = "v1.0",
		contact = @Contact(
				name = "Shubham",
				email = "sawant0420@gmail.com"
		),
		license = @License(
				name = "Apache 2.0",
				url = "https://wwww.javaguides.net/license"
		)
))
//@ComponentScan(basePackages="com.sprinboot.blog")

public class SpringbootBlogRestapiApplication {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBlogRestapiApplication.class, args);
	}

}
