package com.self.ecommerce.controller;

import com.self.ecommerce.service.NacosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangqichao
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    NacosService nacosService;

    @GetMapping("/getInstance")
    public Object getInstance(@RequestParam(defaultValue = "ecommerce-alibaba-nacos-client") String serviceId){
        return nacosService.getNacosServiceInstance(serviceId);
    }

}
