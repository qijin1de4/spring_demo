package com.hqj.demo.springcloud.portal.controller;
import com.hqj.demo.springcloud.feign.BaseRemoteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignController {

    @Autowired
    private BaseRemoteClient baseRemoteClient;

    @GetMapping("/echo-feign/{echoStr}")
    public String feignEcho(@PathVariable String echoStr) {
        return baseRemoteClient.echo(echoStr);
    }
}