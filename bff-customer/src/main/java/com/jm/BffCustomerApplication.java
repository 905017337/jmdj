package com.jm;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/4 13:12
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan("com.jm.*")
@EnableDistributedTransaction
public class BffCustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BffCustomerApplication.class, args);
    }

}