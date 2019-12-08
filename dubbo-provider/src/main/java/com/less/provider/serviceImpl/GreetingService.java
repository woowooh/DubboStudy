package com.less.provider.serviceImpl;

//import com.alibaba.dubbo.config.annotation.Service;
import com.less.api.model.Greeting;
import com.less.api.service.IGreetingService;
import org.springframework.stereotype.Service;


//@Service(version = "${demo.service.version}")
@Service
@com.alibaba.dubbo.config.annotation.Service
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
