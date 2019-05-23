package com.unicom.api.cterminal.controller.base;

import com.unicom.api.cterminal.entity.other.TitleVo;
import com.unicom.api.cterminal.entity.admin.User;
import com.unicom.api.cterminal.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

import javax.annotation.Resource;


public class BaseController {

    @Resource
    private UserService userService;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取当前用户名
     * @return
     */
    public String getUserName(){
        Subject subject = SecurityUtils.getSubject();
        return (String)subject.getPrincipal();
    }

    /**
     * 获取当前用户信息
     * @return
     */
    public User getUser(){
        return userService.findByUserName(getUserName());
    }

    /**
     * 设置标题通用方法
     * @param model
     */
    public void setTitle(Model model, TitleVo titleVo){
        //标题
        model.addAttribute("title",titleVo.getTitle());
        model.addAttribute("parenttitle",titleVo.getParenttitle());
        //是否打开欢迎语
        model.addAttribute("isMsg",titleVo.isMsg());
        //欢迎语
        model.addAttribute("msgHTML",titleVo.getMsgHtml());
        //小控件
        model.addAttribute("isControl",titleVo.isControl());
        model.addAttribute("isribbon", titleVo.isIsribbon());
    }
}
