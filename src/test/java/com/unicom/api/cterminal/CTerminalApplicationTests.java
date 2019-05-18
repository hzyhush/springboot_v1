package com.unicom.api.cterminal;

import com.unicom.api.cterminal.entity.admin.User;
import com.unicom.api.cterminal.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CTerminalApplicationTests {

    @Resource
    private UserService userService;

    @Test
    public void contextLoads() {
        List<Integer> list = new ArrayList<>();
        list.add(100);
        System.out.println(list.contains(10));
    }

}

