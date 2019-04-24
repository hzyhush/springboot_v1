package com.unicom.api.cterminal.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.unicom.api.cterminal.entity.User;
import com.unicom.api.cterminal.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private UserService userService;

    /**
     * 去登录页
     * @return
     */
    @GetMapping(value = "/toLogin")
    public ModelAndView toLogin(){
        return new ModelAndView("login");
    }

    /**
     * 去首页
     * @return
     */
    @GetMapping(value = "/index")
    public String index(Model model){
        model.addAttribute("name","hzy");
        model.addAttribute("userList",userService.findAll());
        model.addAttribute("user",userService.findById(1));
        return "/index";
    }

    /**
     * 退出
     * @return
     */
    @GetMapping(value = "/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "/login";
    }

    /**
     * 登录
     * @return
     */
    @PostMapping(value="/login")
    public String login(String user_name,String user_pwd){
        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(user_name,user_pwd);
            subject.login(token);
            return "redirect:/index";
        }catch (IncorrectCredentialsException ice){
            logger.error("密码错误");
            logger.error(ice.toString(),ice);
        }catch (UnknownAccountException uae){
            logger.error("用户不存在");
            logger.error(uae.toString(),uae);
        }catch (LockedAccountException uae){
            logger.error("账号禁用");
            logger.error(uae.toString(),uae);
        }catch (Exception e){
            logger.error(e.toString(),e);
        }
        return "/login";
    }

    @GetMapping(value = "/sayHello")
    @ResponseBody
    public String sayHello(){
        return "hello，Siri";
    }
}
