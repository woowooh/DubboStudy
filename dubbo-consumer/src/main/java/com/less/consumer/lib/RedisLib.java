package com.less.consumer.lib;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import java.util.Collections;


@Component
@Slf4j
public class RedisLib {
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long RELEASE_SUCCESS = 1L;

    @Autowired
    private JedisPool jedisPool;

    /**
     * 尝试获取分布式锁
     * @param lockKey 锁
     * @param requestId 请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public boolean tryGetDistributedLock(String lockKey, String requestId, int expireTime) {
        Jedis jedis = getJedis();
        String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        release(jedis);
        return LOCK_SUCCESS.equals(result);
    }

    public boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
        return RELEASE_SUCCESS.equals(result);
    }

    public Jedis getJedis() {
        Jedis jedis;
        try {
            jedis = jedisPool.getResource();
        } catch (JedisException e) {
            e.printStackTrace();
            throw e;
        }
        return jedis;
    }

    public void release(Jedis jedis) {
        jedis.close();
    }
//        try {
//            return jedisPool.getResource();
//        } catch (Exception e) {
//            log.debug("getJedis() throws : {}" + e.getMessage());
//        }
//        return jedisPool.getResource();
//    }
//
//    private static void initPool() {
//        try {
//            jedisPool = RedisConfig.redisPoolFactory();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
