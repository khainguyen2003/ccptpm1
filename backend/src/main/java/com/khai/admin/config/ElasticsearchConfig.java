//package com.khai.admin.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.elasticsearch.client.ClientConfiguration;
//import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchConfiguration;
//import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
//
//@Configuration
//@EnableElasticsearchRepositories(basePackages = "com.khai.admin.repository.elasticsearch")
//@ComponentScan(basePackages = { "com.khai.admin.service.elastic" })
//public class ElasticsearchConfig extends ReactiveElasticsearchConfiguration {
//    @Value("${elasticsearch.url}")
//    private String elsUrl;
//
//    @Bean
//    @Override
//    public ClientConfiguration clientConfiguration() {
//        final ClientConfiguration config = ClientConfiguration.builder()
//                .connectedTo(elsUrl)
//                .build();
//        return config;
//    }
//}
