package com.less.provider.serviceImpl;

//import com.alibaba.dubbo.config.annotation.Service;
import com.less.api.model.Greeting;
import com.less.api.service.IGreetingService;
import com.less.provider.lib.RedisLib;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


//@Service(version = "${demo.service.version}")
@Service
@com.alibaba.dubbo.config.annotation.Service
public class GreetingService implements IGreetingService {
    @Autowired
    RedisLib redisLib;

    @Override
    public void sayHello(Greeting greeting) {
        for (int i = 0; i < 10; i++) {
//            Jedis jedis = redisLib.getJedis();
            boolean r = redisLib.tryGetDistributedLock("a", "b", 10000);
//            redisLib.release(jedis);
            System.out.println("www");
            System.out.println(r);
        }
        System.out.println(greeting.getMessage());
    }
    @Override
    public String say() {
        return "hello";
    }
}
