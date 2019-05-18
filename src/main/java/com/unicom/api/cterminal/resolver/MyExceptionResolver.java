package com.unicom.api.cterminal.resolver;

import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 配置自定义shiro无权限异常处理器，无权限会进入指定页面
 */
public class MyExceptionResolver implements HandlerExceptionResolver {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

        //如果是shiro无权操作，因为shiro 在操作auno等一部分不进行转发至无权限url
        if(e instanceof UnauthorizedException){
            System.out.println("shiro无权限异常开始");
            logger.error(e.getMessage(),e);
            ModelAndView mv = new ModelAndView("/error/403");
            return mv;
        }
        return null;
    }
}
