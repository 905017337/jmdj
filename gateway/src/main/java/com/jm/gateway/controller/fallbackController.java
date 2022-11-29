package com.jm.gateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class fallbackController {

    @RequestMapping(value = "/fallback")
    public String fallback(){
        return "fallback nothing";
    }



}
