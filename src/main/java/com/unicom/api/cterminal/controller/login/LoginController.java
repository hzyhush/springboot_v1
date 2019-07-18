package com.unicom.api.cterminal.controller.login;

import com.unicom.api.cterminal.controller.base.BaseController;
import com.unicom.api.cterminal.entity.admin.Menu;
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
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 去登录页
     * @return
     */
    @GetMapping(value = "/")
    public ModelAndView toLogin(){
        return new ModelAndView("login");
    }

    /**
     * 去局部刷新区域
     * @return
     */
    @GetMapping("/main")
    @RequiresPermissions("system:index")
    public String main(Model model) {
        return "admin/main";
    }

    /**
     * 去首页
     * @return
     */
    @GetMapping(value = "/index")
    public String index(Model model){
        //获取首页左侧菜单
        Menu menu = menuService.getMenuTree(false,null);
        if(menu != null){
            model.addAttribute(Const.MENULIST,menu);
            /*//判断是否将用户权限存储到redis，没有就获取用户权限并存储到session中
            Set<String> menuQX = (Set<String>)redisTemplate.opsForValue().get("user::findMenuQX_"+getUserName());
            if(menuQX == null){
                //获取用户权限，并保存到session中
                session.setAttribute(Const.MENUQX,userService.findMenuQX(getUserName()));
            }*/
        }
        return "admin/index";
    }

    /**
     * 退出
     * @return
     */
    @GetMapping(value = "/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }

    /**
     * 登录
     * @return
     */
    @PostMapping(value="/login")
    public String login(HttpSession session,String username,String password,String code,Model model){
        try {
            String sesionCode = (String)session.getAttribute(Const.CAPTCHA_KEY);
            if(StringUtils.isNotBlank(sesionCode) && code.equals(sesionCode.toLowerCase())){//验证码验证成功
                Subject subject = SecurityUtils.getSubject();
                UsernamePasswordToken token = new UsernamePasswordToken(username,password);
                subject.login(token);//执行到这步如果没有出现异常说明登录成功
                /*//用户第一次登陆成功后，会将权限存储在redisli
                Object user = redisTemplate.opsForValue().get("shiro_cache:com.unicom.api.cterminal.config.ShiroRealm.authorizationCache:"+username);
                //说明已经登陆过一次，不允许重复登陆，回到登录页面
                if(user != null){
                    model.addAttribute("hasmess", true);
                }else{
                    return "redirect:index";
                }*/
                session.setAttribute(Const.MENUQX,userService.findMenuQX(getUserName()));
                return "redirect:index";
            }else{
                model.addAttribute("errormess", "验证码输入错误!");
            }

        }catch (IncorrectCredentialsException ice){
            model.addAttribute("errormess", "账号或密码错误!");
        }catch (UnknownAccountException uae){
            model.addAttribute("errormess", "用户不存在!");
        }catch (LockedAccountException uae){
            model.addAttribute("errormess", "账号已被禁用!");
        }catch (Exception e){
            logger.error(e.toString(),e);
        }
        model.addAttribute("username", username);
        return "login";
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

    //被踢出后跳转的页面
    @RequestMapping(value = "/kickout", method = RequestMethod.GET)
    public String kickOut() {
        return "login";
    }
}
