package com.unicom.api.cterminal.controller.admin;

import com.github.pagehelper.PageInfo;
import com.unicom.api.cterminal.controller.base.BaseController;
import com.unicom.api.cterminal.dao.UserDao;
import com.unicom.api.cterminal.entity.admin.Role;
import com.unicom.api.cterminal.entity.other.PageResult;
import com.unicom.api.cterminal.entity.other.Tablepar;
import com.unicom.api.cterminal.entity.other.TitleVo;
import com.unicom.api.cterminal.entity.admin.User;
import com.unicom.api.cterminal.service.RoleService;
import com.unicom.api.cterminal.service.UserService;
import com.unicom.api.cterminal.util.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping(value = "/user")
@Api(value = "用户管理",description = "用户管理")
public class UserController extends BaseController {

    private String prefix = "/admin/user";
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    /**
     * 去用户页面
     * @return
     */
    @GetMapping(value = "/goUser")
    @RequiresPermissions("user:view")
    public String goUser(Model model){
        setTitle(model,new TitleVo("用户列表","用户管理",true,"欢迎进入用户页面",false,false));
        return prefix+"/list";
    }

    /**
     * 去新增-用户页面
     * @return
     */
    @GetMapping(value = "/goAdd")
    public String goAdd(Model model){
        List<Role> roles = roleService.findAll();
        model.addAttribute("roles",roles);
        return prefix+"/add";
    }


    /**
     * 分页查询数据
     * @param tablepar
     * @param userName
     * @return
     */
    @ApiOperation(value = "分页查询用户数据",notes = "分页查询用户数据",httpMethod = "POST",response = PageResult.class,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页",required = true,dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "pageSize",value = "每页显示数量",required = true,dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "userName",value = "用户名",required = false,dataType = "String",paramType = "query")
    })
    @PostMapping(value = "/listPage")
    @RequiresPermissions("user:select")//判断是否有该权限
    @ResponseBody
    public PageResult<User> listPage(Tablepar tablepar, @RequestParam(value = "userName",required = false) String userName){
        PageInfo<User> info = userService.listPage(tablepar,userName);
        PageResult result = new PageResult(info.getPageNum(),info.getTotal(),info.getList());
        return result;
    }

    /**
     * 检查用户
     * @param
     * @return
     */
    @GetMapping(value = "/checkLoginNameUnique")
    @ResponseBody
    public boolean checkLoginNameUnique(@RequestParam("user_name") String user_name){
        return userService.checkLoginNameUnique(user_name);
    }

    /**
     * 添加用户
     * @param user
     * @param roles
     * @return
     */
    @PostMapping("/add")
    @RequiresPermissions("user:save")
    @ResponseBody
    public AjaxResult add(User user,@RequestParam(value="roles", required = false)List<Integer> roles){
        boolean flag = userService.saveUser(user,roles);
        return AjaxResult.result(flag);

    }

    /**
     * 去修改-用户页面
     * @return
     */
    @GetMapping(value = "/goEdit/{id}")
    public String goEdit(@PathVariable("id") Integer user_id, Model model){
        List<Role> roles = roleService.findUseRole(user_id);
        User user = userService.findById(user_id);
        model.addAttribute("roles",roles);
        model.addAttribute("user",user);
        return prefix+"/edit";
    }

    /**
     * 修改用户
     * @param user
     * @param roles
     * @return
     */
    @PostMapping("/edit")
    @RequiresPermissions("user:update")
    @ResponseBody
    public AjaxResult edit(User user, @RequestParam(value="roles", required = false)List<Integer> roles){
        boolean flag = userService.updateUser(user,roles);
        return AjaxResult.result(flag);
    }

    /**
     * 去修改-密码页面
     * @return
     */
    @GetMapping(value = "/goEditPwd/{id}")
    public String goEditPwd(@PathVariable("id") Integer user_id, Model model){
        User user = userService.findById(user_id);
        model.addAttribute("user",user);
        return prefix+"/editPwd";
    }

    /**
     * 修改用户密码
     * @param user
     * @return
     */
    @PostMapping("/editPwd")
    //@RequiresPermissions("user:updatePwd")
    @ResponseBody
    public AjaxResult editPwd(User user){
        boolean flag = userService.updatePwd(user);
        return AjaxResult.result(flag);
    }

    /**
     * 删除用户
     * @param ids 编号数组
     * @return
     */
    @PostMapping("/delIds")
    @RequiresPermissions("user:delete")
    @ResponseBody
    public AjaxResult editPwd(int[] ids){
        boolean flag = userService.delIds(ids);
        return AjaxResult.result(flag);
    }
}
