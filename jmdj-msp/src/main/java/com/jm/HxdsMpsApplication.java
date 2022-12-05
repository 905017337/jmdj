package com.jm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/4 13:53
 */
@SpringBootApplication
@ServletComponentScan
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan("com.jm.*")
public class HxdsMpsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HxdsMpsApplication.class, args);
    }

}