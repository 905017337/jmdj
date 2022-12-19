package com.jm.jmdjdr;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ServletComponentScan
@MapperScan("com.jm.jmdjdr.mapper")
@ComponentScan("com.jm.*")
@EnableDistributedTransaction
public class JmdjDrApplication {

    public static void main(String[] args) {
        SpringApplication.run(JmdjDrApplication.class, args);
    }


}
