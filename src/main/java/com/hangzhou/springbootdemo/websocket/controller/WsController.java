package com.hangzhou.springbootdemo.websocket.controller;

import com.hangzhou.springbootdemo.websocket.request.WiselyMessage;
import com.hangzhou.springbootdemo.websocket.response.WiselyResponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WsController {

    @MessageMapping("/welcome") //当浏览器向服务端发送请求时，通过@MessageMapping映射/welcome这个地址，类似于@RequestMapping
    //如果浏览器订阅了@SendTo中的路径，当服务器有消息时，就会向这些浏览器发送消息;
    @SendTo("/topic/getResponse")//当服务端有消息时，监听了/topic/getResponse的客户端会接收消息
    public WiselyResponse say(WiselyMessage message) throws Exception {
        System.out.println("连接成功......");
        Thread.sleep(3000);
        return new WiselyResponse("Welcome, " + message.getName() + "!");
    }

}
