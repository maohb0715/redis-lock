package com.mhb.service.impl;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mhb.service.TestService;
import com.mhb.util.RedisLockUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;




@Service
public class TestServiceImpl implements TestService{
	
	private final static Logger LOG  = LoggerFactory.getLogger(TestServiceImpl.class);
	
	@Autowired
	private JedisPool jedisPool;
	

	@Override
	public void test() {
		Jedis jedis = null;
		jedis = jedisPool.getResource();
		String lock = RedisLockUtil.lock(jedis, "mhb");
		
		LOG.info(lock);
		lock = "123";
		String unlock = RedisLockUtil.unlock(jedis, "mhb", lock);
		LOG.info(unlock);
		
	}
	
	
	
	
}
