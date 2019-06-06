package com.hangzhou.springbootdemo.websocket.controller;

import com.hangzhou.springbootdemo.websocket.request.WiselyMessage;
import com.hangzhou.springbootdemo.websocket.response.WiselyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class WsController {

    @Autowired
    private SimpMessagingTemplate messageingTemplate;//通过SimpMessagingTemplate向浏览器发送信息

    @MessageMapping("/welcome") //当浏览器向服务端发送请求时，通过@MessageMapping映射/welcome这个地址，类似于@RequestMapping
    //如果浏览器订阅了@SendTo中的路径，当服务器有消息时，就会向这些浏览器发送消息;
    @SendTo("/topic/getResponse")//当服务端有消息时，监听了/topic/getResponse的客户端会接收消息
    public WiselyResponse say(WiselyMessage message) throws Exception {
        System.out.println("连接成功......");
        Thread.sleep(3000);
        return new WiselyResponse("Welcome, " + message.getName() + "!");
    }


    @MessageMapping("/chat")
    public void handleChat(Principal principal, String msg){
        //Spring MVC中可以直接在参数中获得principal，principal中包含当前用户的信息
        System.out.println("聊天成功......");
        if(principal.getName().equals("wjs")){
            //测试用，实际情况需根据需求编写这里判断若发件人是wyf，则发给wisely；若是wisely则发给wyf
            messageingTemplate.convertAndSendToUser("ktt", "/queue/notifications", principal.getName() + "-send:" + msg);
            //通过messageingTemplate.convertAndSendToUser向用户发送信息
            //第一个参数为接收用户，第二个参数为订阅地址，第三个为消息
        }else{
            messageingTemplate.convertAndSendToUser("wjs", "/queue/notifications", principal.getName() + "-send:" + msg);
        }
    }

}
