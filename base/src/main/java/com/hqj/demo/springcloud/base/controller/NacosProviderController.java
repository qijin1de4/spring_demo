package com.hqj.demo.springcloud.base.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NacosProviderController {

    @RequestMapping(value = "/echo/{echoStr}", method = RequestMethod.GET)
    public String echo(@PathVariable String echoStr) {
        return "Hello Nacos Discovery " + echoStr;
    }
}
