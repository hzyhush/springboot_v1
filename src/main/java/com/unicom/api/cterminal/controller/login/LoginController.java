package com.unicom.api.cterminal.controller.login;

import com.unicom.api.cterminal.controller.base.BaseController;
import com.unicom.api.cterminal.entity.admin.Menu;
import com.unicom.api.cterminal.entity.other.BootstrapThree;
import com.unicom.api.cterminal.service.MenuService;
import com.unicom.api.cterminal.service.UserService;
import com.unicom.api.cterminal.util.Const;
import com.unicom.api.cterminal.util.VerifyCodeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

@Controller
public class LoginController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private MenuService menuService;
    @Resource
    private UserService userService;

    /**
     * 去登录页
     * @return
     */
    @GetMapping(value = "/")
    public ModelAndView toLogin(){
        return new ModelAndView("/login");
    }

    /**
     * 去局部刷新区域
     * @return
     */
    @GetMapping("/main")
    public String main() {
        return "/admin/main";
    }

    /**
     * 去首页
     * @return
     */
    @GetMapping(value = "/index")
    public String index(){
        return "/admin/index";
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
    public String login(HttpSession session,String userName,String password,String code,Model model){
        try {
            String sesionCode = (String)session.getAttribute(Const.CAPTCHA_KEY);
            if(StringUtils.isNotBlank(sesionCode) && code.equals(sesionCode.toLowerCase())){//验证码验证成功
                Subject subject = SecurityUtils.getSubject();
                UsernamePasswordToken token = new UsernamePasswordToken(userName,password);
                subject.login(token);//执行到这步如果没有出现异常说明登录成功
                //登录成功后获取菜单目录，并保存到session中
                Menu menu = menuService.getMenuTree(true,null);
                if(menu != null){
                    session.setAttribute(Const.MENUQX,userService.findMenuQX(getUser().getUser_name()));
                    session.setAttribute(Const.MENULIST,menu);
                }
                return "redirect:/index";
            }
            model.addAttribute("errormess", "验证码输入错误!");
        }catch (IncorrectCredentialsException ice){
            model.addAttribute("errormess", "账号或密码错误!");
        }catch (UnknownAccountException uae){
            model.addAttribute("errormess", "用户不存在!");
        }catch (LockedAccountException uae){
            model.addAttribute("errormess", "账号已被禁用!");
        }catch (Exception e){
            logger.error(e.toString(),e);
        }
        model.addAttribute("userName", userName);
        return "/login";
    }

    /**
     * 获取验证码
     * @param request
     * @param response
     * @param session
     * @throws IOException
     */
    @GetMapping(value="/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        // 生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);

        // 生成图片
        int w = 135, h = 40;
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);

        // 将验证码存储在session以便登录时校验
        session.setAttribute(Const.CAPTCHA_KEY, verifyCode.toLowerCase());
    }
}
