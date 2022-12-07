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
 * @date 2022/12/6 19:44
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ServletComponentScan
@MapperScan("com.jm.jmdjodr.mapper")
@ComponentScan("com.jm.*")
@EnableDistributedTransaction
public class RuleController {

    public static void main(String[] args) {
        SpringApplication.run(RuleController.class,args);
    }
}