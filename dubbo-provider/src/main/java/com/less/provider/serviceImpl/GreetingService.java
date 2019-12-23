package com.less.provider.serviceImpl;

//import com.alibaba.dubbo.config.annotation.Service;
import com.less.api.model.Greeting;
import com.less.api.service.IGreetingService;
import com.less.provider.lib.RedisLib;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import java.util.UUID;


//@Service(version = "${demo.service.version}")
@Service
@Slf4j
@com.alibaba.dubbo.config.annotation.Service
public class GreetingService implements IGreetingService {
    static int n = 0;

    @Autowired
    RedisLib redisLib;

    @Override
    public void sayHello(Greeting greeting) {
        Jedis jedis = redisLib.getJedis();
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        String key = "keyo";
        try {
            log.info("key is ({}), uuid is ({})", key, uuid);
            boolean getLock = redisLib.tryGetDistributedLock(jedis, key, uuid, 10000);
            if (getLock) {
                log.info("Get global lock ok");
                n++;
            }
        } finally {
            redisLib.releaseDistributedLock(jedis, key, uuid);
            redisLib.release(jedis);
        }
        log.info("{}", n);
    }

    @Override
    public String say() {
        return "hello";
    }
}

// ab -n10 -c10 "http://localhost:9091/api/hello/QAQ"        -n 请求次数  -c 并发数