package com.unicom.api.cterminal.resolver;

import com.unicom.api.cterminal.util.AjaxResult;
import net.sf.json.JSONObject;
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
            logger.error(e.getMessage(),e);
            if(isAjax(httpServletRequest)){//是ajax请求
                try {
                    JSONObject json = JSONObject.fromObject(AjaxResult.error("您没有权限"));
                    httpServletResponse.setContentType("text/html;charset=utf-8");
                    httpServletResponse.getWriter().print(json);
                }catch (Exception ex){
                    logger.error(ex.getMessage(),ex);
                }
            }else{
                ModelAndView mv = new ModelAndView("/error/403");
                return mv;
            }
        }
        return null;
    }

    /**
     * 判断是否是ajax请求
     * @param request
     * @return
     */
    public boolean isAjax(HttpServletRequest request) {
        return (request.getHeader("X-Requested-With") != null &&
                "XMLHttpRequest".equals(request.getHeader("X-Requested-With").toString()));
    }

}
