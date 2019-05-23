package com.unicom.api.cterminal.controller.admin;

import com.github.pagehelper.PageInfo;
import com.unicom.api.cterminal.controller.base.BaseController;
import com.unicom.api.cterminal.entity.admin.Menu;
import com.unicom.api.cterminal.entity.admin.Role;
import com.unicom.api.cterminal.entity.admin.User;
import com.unicom.api.cterminal.entity.other.PageResult;
import com.unicom.api.cterminal.entity.other.Tablepar;
import com.unicom.api.cterminal.entity.other.TitleVo;
import com.unicom.api.cterminal.service.MenuService;
import com.unicom.api.cterminal.util.AjaxResult;
import javafx.scene.input.Mnemonic;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "/menu")
public class MenuController extends BaseController {

    private String prefix = "/admin/menu";
    @Resource
    private MenuService menuService;

    /**
     * 去菜单页面
     * @return
     */
    @GetMapping(value = "/goMenu")
    @RequiresPermissions("menu:view")
    public String goMenu(Model model){
        setTitle(model,new TitleVo("菜单列表","菜单管理",true,"欢迎进入菜单页面",false,false));
        return prefix+"/list";
    }

    /**
     * 分页查询数据
     * @param tablepar
     * @param menuName
     * @return
     */
    @PostMapping(value = "/listPage")
    @RequiresPermissions("menu:list")//判断是否有该权限
    @ResponseBody
    public PageResult<User> listPage(Tablepar tablepar, @RequestParam(value = "menuName",required = false) String menuName){
        PageInfo<Menu> info = menuService.listPage(tablepar,menuName);
        PageResult result = new PageResult(info.getPageNum(),info.getTotal(),info.getList());
        return result;
    }

    /**
     * 去新增-菜单页面
     * @return
     */
    @GetMapping(value = "/goAdd")
    public String goAdd(){
        return prefix+"/add";
    }

    @GetMapping("/three")
    public String three(){
        return prefix +"/three";
    }

    /**
     * 树结构数据
     * @return
     */
    @PostMapping("/threeJson/{flag}")
    @ResponseBody
    public AjaxResult threeJson(@PathVariable(value ="flag") boolean flag){
        return AjaxResult.successData(200,menuService.getMenuTree(flag,null));

    }

    /**
     * 根据角色id获取树菜单和打钩权限
     * @return
     */
    @PostMapping("/threeJson/{flag}/{role_id}")
    @ResponseBody
    public AjaxResult threeJson(@PathVariable(value ="flag") boolean flag,
                                @PathVariable(value = "role_id")Integer role_id){
        return AjaxResult.successData(200,menuService.getMenuTree(flag,role_id));

    }


    /**
     * 验证菜单名称是否唯一
     * @param menu_name 菜单名称
     * @param menu_id 菜单编号
     * @return
     */
    @GetMapping(value = "/checkMenuNameUnique")
    @ResponseBody
    public boolean checkMenuNameUnique(@RequestParam(value = "menu_name")String menu_name,
                                       @RequestParam(value = "menu_id",required = false)String menu_id){
        return menuService.checkMenuNameUnique(menu_name,menu_id);
    }

    /**
     * 验证请求地址是否唯一
     * @param menu_url 菜单名称
     * @param menu_id 菜单编号
     * @return
     */
    @GetMapping(value = "/checkURLUnique")
    @ResponseBody
    public boolean checkURLUnique(@RequestParam(value = "menu_url")String menu_url,
                                       @RequestParam(value = "menu_id",required = false)String menu_id){
        return menuService.checkURLUnique(menu_url,menu_id);
    }

    /**
     * 验证权限名称是否唯一
     * @param permission 菜单名称
     * @param menu_id 菜单编号
     * @return
     */
    @GetMapping(value = "/checkPermsUnique")
    @ResponseBody
    public boolean checkPermsUnique(@RequestParam(value = "permission")String permission,
                                  @RequestParam(value = "menu_id",required = false)String menu_id){
        return menuService.checkPermsUnique(permission,menu_id);
    }

    /**
     * 新增菜单
     * @param menu
     * @return
     */
    @PostMapping(value = "/saveMenu")
    @RequiresPermissions("menu:save")
    @ResponseBody
    public AjaxResult saveMenu(Menu menu){
        return AjaxResult.result(menuService.saveMenu(menu));
    }

    /**
     * 去修改-用户页面
     * @return
     */
    @GetMapping(value = "/goEdit/{id}")
    public String goEdit(@PathVariable("id") Integer menu_id, Model model){
        Menu menu = menuService.findById(menu_id);
        Menu parentMenu = menuService.findParentMenu(menu.getParent_id());
        model.addAttribute("menu",menu);
        model.addAttribute("parentMenu",parentMenu);
        return prefix+"/edit";
    }

    /**
     * 修改菜单
     * @param menu
     * @return
     */
    @PostMapping(value = "/updateMenu")
    @RequiresPermissions("menu:update")
    @ResponseBody
    public AjaxResult updateMenu(Menu menu){
        return AjaxResult.result(menuService.updateMenu(menu));
    };

    /**
     * 删除菜单
     * @param ids 编号数组
     * @return
     */
    @PostMapping("/delIds")
    @RequiresPermissions("menu:delete")
    @ResponseBody
    public AjaxResult delIds(int[] ids){
        return AjaxResult.result(menuService.delIds(ids));
    }

}
