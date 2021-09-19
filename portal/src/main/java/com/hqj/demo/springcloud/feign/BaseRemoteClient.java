package com.hqj.demo.springcloud.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "base",  fallback = BaseRemoteHystrix.class)
public interface BaseRemoteClient {

    @GetMapping("/echo/{echoStr}")
    String echo(@PathVariable("echoStr") String echoStr);
}
