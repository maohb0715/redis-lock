package com.mhb.util;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;



public class RedisLockUtil {
	
	private RedisLockUtil() {};
	
	private final static Logger LOG  = LoggerFactory.getLogger(RedisLockUtil.class);
	
	public static String lock(Jedis jedis,String lockKey) {
		String lockValue = String.valueOf(UUID.randomUUID());
		jedis.set(lockKey, lockValue,"NX","PX",10000L);
		LOG.info("加锁成功：lockKey={}，lockValue={}",lockKey,lockValue);
		return lockValue;
	}

	private static byte[] setexScript = (
			"if redis.call('get',KEYS[1]) == KEYS[2] then"          	+"\r\n" + 
			"    return redis.call('del',KEYS[1])"                  			  	+"\r\n" + 
			"else"                                                                  				+"\r\n" + 
			"    return 0"                                                        			+"\r\n" + 
			"end").getBytes();

	public static String unlock(Jedis jedis,String lockKey,String lockValue) {
		if(null==lockValue) {
			LOG.info("解锁失败：lockKey={}",lockKey);
			return "N";
		}
		Object eval = jedis.eval(setexScript,2,lockKey.getBytes(),lockValue.getBytes());
		return String.valueOf(eval);
	}
	
}
