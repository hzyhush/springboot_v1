package com.unicom.api.cterminal.controller;

import com.unicom.api.cterminal.entity.User;
import com.unicom.api.cterminal.service.UserService;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private UserService userService;

    @GetMapping(value = "/findAll")
    @RequiresPermissions("user:query")//判断是否有该权限
    public List<User> findAll(){
        /*try {
            return userService.findAll().toString();
        }catch(AuthorizationException aie){
            logger.error("您没有权限");
            logger.error(aie.toString(),aie);
        }catch (Exception e){
            logger.error(e.toString(),e);
        }*/
        return userService.findAll();
    }
}
