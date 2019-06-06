package com.hangzhou.springbootdemo.websocket.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class helloController {

    @RequestMapping("world")
    public String hello(){
        System.out.println("helloworld");
        return "hello/world";
    }


}

