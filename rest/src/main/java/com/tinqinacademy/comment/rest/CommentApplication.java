package com.tinqinacademy.comment.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.tinqinacademy.comment.persistence.entities")
@EnableJpaRepositories(basePackages = "com.tinqinacademy.comment.persistence.repositories")
@ComponentScan(basePackages = "com.tinqinacademy.comment")
public class CommentApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommentApplication.class, args);
	}

}
