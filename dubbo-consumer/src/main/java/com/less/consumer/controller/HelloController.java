package com.less.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.less.api.model.Greeting;
import com.less.api.service.IGreetingService;
import com.less.consumer.lib.RedisLib;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;


@RestController
@RequestMapping("/api")
public class HelloController {
    static final String template = "%s";
    @Reference
    IGreetingService greetingService;
    @Autowired
    RedisLib redisLib;

    @RequestMapping(value ="/hello/{message}", method = RequestMethod.GET)
    public Greeting hello(@PathVariable("message") String message) {
        Greeting greet = new Greeting();
        System.out.println("greeting service");
        System.out.println(greetingService);
        message = String.format(template, message);
        System.out.println(message);
        greet.setMessage(message);
        greetingService.sayHello(greet);
        return greet;
    }

    @RequestMapping(value ="/say", method = RequestMethod.GET)
    public String say() {

        for (int i = 0; i < 100; i++) {
//            Jedis jedis = redisLib.getJedis();
            boolean r = redisLib.tryGetDistributedLock("a", "b", 10000);
//            redisLib.release(jedis);
            System.out.println("www");
            System.out.println(r);
        }
;
//        return greetingService.say();
//        redisLib.releaseDistributedLock(jedis, "a", "b");
        return "www";
    }
}
