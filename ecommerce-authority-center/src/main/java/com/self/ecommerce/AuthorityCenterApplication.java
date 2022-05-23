package com.self.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author wangqichao
 * 授权中心
 * EnableJpaAuditing 允许jpa自动审计
 */
@EnableJpaAuditing
@EnableDiscoveryClient
@SpringBootApplication
public class AuthorityCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorityCenterApplication.class, args);
    }

}
