package com.unicom.api.cterminal.config.shiro;

import com.unicom.api.cterminal.util.Const;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * Redis实现shiro的SessionDao存取session
 */
public class RedisSessionDAO extends EnterpriseCacheSessionDAO {

    private static Logger logger = LoggerFactory.getLogger(RedisSessionDAO.class);
    private static String prefix = "shiro_session:";

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    // 创建session，保存到数据库
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = super.doCreate(session);
        //logger.debug("创建session:{}", session.getId());
        //设置超时时间
        redisTemplate.opsForValue().set(prefix + sessionId.toString(), session, Const.SESSION_TIMEOUT,TimeUnit.MINUTES);
        return sessionId;
    }

    // 获取session
    @Override
    protected Session doReadSession(Serializable sessionId) {
        //logger.debug("获取session:{}", sessionId);
        // 先从缓存中获取session，如果没有再去数据库中获取
        Session session = super.doReadSession(sessionId);
        if (session == null) {
            session = (Session) redisTemplate.opsForValue().get(prefix + sessionId.toString());
        }
        return session;
    }

    // 更新session的最后一次访问时间
    @Override
    protected void doUpdate(Session session) {
        super.doUpdate(session);
        //logger.debug("获取session:{}", session.getId());
        String key = prefix + session.getId().toString();
        if (!redisTemplate.hasKey(key)) {
            redisTemplate.opsForValue().set(key, session);
        }
        redisTemplate.expire(key, Const.SESSION_TIMEOUT, TimeUnit.MINUTES);
    }

    // 删除session
    @Override
    protected void doDelete(Session session) {
        //logger.debug("删除session:{}", session.getId());
        super.doDelete(session);
        redisTemplate.delete(prefix + session.getId().toString());
    }

}
