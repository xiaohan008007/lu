package com.taotaosou.lu.redis.secondKill;

import com.taotaosou.lu.redis.secondKill.aop.CacheLock;
import com.taotaosou.lu.redis.secondKill.aop.LockedObject;

public interface SeckillInterface {
	@CacheLock(lockedPrefix="TEST_PREFIX")
	public Long secKill(String arg1,@LockedObject Long arg2);
}