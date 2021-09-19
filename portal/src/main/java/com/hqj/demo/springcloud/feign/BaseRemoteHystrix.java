package com.hqj.demo.springcloud.feign;

import org.springframework.stereotype.Component;

@Component
public class BaseRemoteHystrix implements BaseRemoteClient {
    @Override
    public String echo(String echoStr) {
        return "请求超时了";
    }
}
