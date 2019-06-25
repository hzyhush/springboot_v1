package com.unicom.api.cterminal.controller.error;

import com.unicom.api.cterminal.entity.other.ErrorInfo;
import com.unicom.api.cterminal.util.ErrorInfoBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;
import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理controller
 */
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class GlobalErrorController implements ErrorController {

    @Autowired
    private ErrorInfoBuilder errorInfoBuilder;//错误信息的构建工具.

    private final static String DEFAULT_ERROR_VIEW = "error";//错误信息页
    private final static String DEFAULT_403_VIEW = "error/403";//错误信息页
    private final static String DEFAULT_404_VIEW = "error/404";//错误信息页
    private final static String DEFAULT_405_VIEW = "error/405";//错误信息页
    private final static String DEFAULT_500_VIEW = "error/500";//错误信息页

    /**
     * 情况1：若预期返回类型为text/html,则返回错误信息页(View).
     */
    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletRequest request) {
        String viewName = DEFAULT_500_VIEW;
        Integer statusCode = (Integer) request.getAttribute(WebUtils.ERROR_STATUS_CODE_ATTRIBUTE);
        if(statusCode.equals(403)){
            viewName = DEFAULT_403_VIEW;
        }else if(statusCode.equals(404)){
            viewName = DEFAULT_404_VIEW;
        }else if(statusCode.equals(405)){
            viewName = DEFAULT_405_VIEW;
        }
        return new ModelAndView(viewName, "errorInfo", errorInfoBuilder.getErrorInfo(request));
    }

    /**
     * 情况2：其它预期类型 则返回详细的错误信息(JSON).
     */
    @RequestMapping
    @ResponseBody
    public ErrorInfo error(HttpServletRequest request) {
        return errorInfoBuilder.getErrorInfo(request);
    }


    @Override
    public String getErrorPath() {//获取映射路径
        return errorInfoBuilder.getErrorProperties().getPath();
    }
}
