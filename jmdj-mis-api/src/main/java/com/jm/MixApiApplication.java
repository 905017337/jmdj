package com.jm;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/2 21:29
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ServletComponentScan
@MapperScan("com.jm.mapper")
@ComponentScan("com.jm.*")
@EnableDistributedTransaction
public class MixApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MixApiApplication.class,args);
    }
}