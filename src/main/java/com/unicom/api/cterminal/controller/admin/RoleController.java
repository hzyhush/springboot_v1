package com.unicom.api.cterminal.controller.admin;

import com.github.pagehelper.PageInfo;
import com.unicom.api.cterminal.controller.base.BaseController;
import com.unicom.api.cterminal.entity.admin.Role;
import com.unicom.api.cterminal.entity.other.PageResult;
import com.unicom.api.cterminal.entity.other.Tablepar;
import com.unicom.api.cterminal.entity.other.TitleVo;
import com.unicom.api.cterminal.service.RoleService;
import com.unicom.api.cterminal.util.AjaxResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/role")
public class RoleController extends BaseController {

    private String prefix = "admin/role";
    @Resource
    private RoleService roleService;


    /**
     * 去角色页面
     * @return
     */
    @GetMapping(value = "/goRole")
    @RequiresPermissions("role:view")
    public String goRole(Model model){
        setTitle(model,new TitleVo("角色列表","角色管理",true,"欢迎进入角色管理页面",false,false));
        return prefix+"/list";
    }


    /**
     * 分页查询数据
     * @param tablepar
     * @param roleName
     * @return
     */
    @PostMapping(value = "/listPage")
    @RequiresPermissions("role:select")//判断是否有该权限
    @ResponseBody
    public PageResult<Role> listPage(Tablepar tablepar, @RequestParam(value = "roleName",required = false) String roleName){
        PageInfo<Role> info = roleService.listPage(tablepar,roleName);
        PageResult result = new PageResult(info.getPageNum(),info.getTotal(),info.getList());
        return result;
    }

    /**
     * 去新增-角色页面
     * @return
     */
    @GetMapping(value = "/goAdd")
    @RequiresPermissions("role:save")
    public String goAdd(){
        return prefix+"/add";
    }

    /**
     * 验证角色名称是否唯一
     * @param role_name 角色名称
     * @param role_id 角色编号
     * @return
     */
    @GetMapping(value = "/checkNameUnique")
    @ResponseBody
    public boolean checkNameUnique(@RequestParam(value = "role_name")String role_name,
                                   @RequestParam(value = "role_id",required = false)Integer role_id){
        return roleService.checkNameUnique(role_name,role_id);
    }

    /**
     * 角色添加
     * @param role
     * @return
     */
    @PostMapping("/saveRole")
    @RequiresPermissions("role:save")
    @ResponseBody
    public AjaxResult add(Role role, @RequestParam(value = "prem",required = false) String prem){
        return AjaxResult.result(roleService.saveRole(role,prem));
    }

    /**
     * 去修改-角色页面
     * @return
     */
    @GetMapping(value = "/goEdit/{id}")
    @RequiresPermissions("role:update")
    public String goEdit(@PathVariable("id") Integer role_id,Model model){
        Role role = roleService.findById(role_id);
        model.addAttribute("role",role);
        return prefix+"/edit";
    }

    /**
     * 角色修改
     * @param role
     * @return
     */
    @PostMapping("/updateRole")
    @RequiresPermissions("role:update")
    @ResponseBody
    public AjaxResult editRole(Role role, @RequestParam(value = "prem",required = false) String prem){
        return AjaxResult.result(roleService.updateRole(role,prem));
    }

    /**
     * 删除角色
     * @param ids 编号数组
     * @return
     */
    @PostMapping("/delIds")
    @RequiresPermissions("role:delete")
    @ResponseBody
    public AjaxResult editPwd(int[] ids){
        return AjaxResult.result(roleService.deleteIdS(ids));
    }
}
