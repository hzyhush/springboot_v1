package com.unicom.api.cterminal;

import com.unicom.api.cterminal.entity.admin.User;
import com.unicom.api.cterminal.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CTerminalApplicationTests {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Resource
    private UserService userService;

    @Test
    public void contextLoads() {
       Object user = redisTemplate.opsForValue().get("shiro_cache:com.unicom.api.cterminal.config.ShiroRealm.authorizationCache:hzy");
       System.out.println(user);
    }

}

