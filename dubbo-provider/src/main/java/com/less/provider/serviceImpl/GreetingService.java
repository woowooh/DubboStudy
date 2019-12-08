package com.less.provider.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.less.api.model.Greeting;
import com.less.api.service.IGreetingService;


@Service(version = "${demo.service.version}")
public class GreetingService implements IGreetingService {
    @Override
    public void sayHello(Greeting greeting) {
        System.out.println(greeting.getMessage());
    }
    @Override
    public String say() {
        return "hello";
    }
}
