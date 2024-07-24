package com.khai.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;

@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "com.khai.admin.repository.elasticsearch")
@EnableJpaRepositories(basePackages = "com.khai.admin.repository.jpa")
public class AdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}

}
