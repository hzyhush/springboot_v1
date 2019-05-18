package com.unicom.api.cterminal.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.unicom.api.cterminal.dao.MenuDao;
import com.unicom.api.cterminal.entity.admin.Menu;
import com.unicom.api.cterminal.entity.other.BootstrapThree;
import com.unicom.api.cterminal.entity.other.Tablepar;
import com.unicom.api.cterminal.service.MenuService;
import com.unicom.api.cterminal.util.Const;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    @Qualifier("menuDao")
    private MenuDao menuDao;

    /**
     * 获取菜单目录
     * @param flag  是否显示按钮，true显示/false不显示
     * @param role_id 选择的角色编号,主要用来判断菜单是否选中，可以为null
     * @return
     */
    public Menu getMenuTree(boolean flag,Integer role_id){
        Menu menu = menuDao.findByRoot();
        if(menu != null){
            List<Menu> menuList = getMenuTree(menu,flag,role_id);
            menu.setNodes(menuList);
            menu = setState(menu,role_id);
        }
        return menu;
    }

    /**
     * 递归查询父菜单下的子菜单
     * @param flag  是否显示按钮，true显示/false不显示
     * @param menu 菜单实体
     * @param role_id 选择的角色编号,主要用来判断菜单是否选中，可以为null
     * @return
     */
    public List<Menu> getMenuTree(Menu menu,boolean flag,Integer role_id){
        List<Menu> menuList = addTree(menu,flag,role_id);
        for (Menu item:menuList) {
            if(item.isChilren()){
                item.setNodes(getMenuTree(item,flag,role_id));

            }
        }
        return menuList;
    };

    /**
     * 根据父菜单查询子菜单
     * @param menu
     * @param flag  是否显示按钮，true显示/false不显示
     * @param role_id 选择的角色编号,主要用来判断菜单是否选中，可以为null
     * @return
     */
    public List<Menu> addTree(Menu menu,boolean flag,Integer role_id){
        List<Menu> childrenList = menuDao.findChildrenMenu(menu.getMenu_id(),flag);//查询子菜单
        for (Menu item:childrenList) {
            item = setState(item,role_id);//设置是否选中
            if(isChilren(item,flag)){//判断是否有子菜单
                item.setChilren(true);
            }else{
                item.setChilren(false);
            }
        }
        return childrenList;
    }

    /**
     * 判断是否有子菜单
     * @param menu
     * @param flag  是否显示按钮，true显示/false不显示
     * @return
     */
    private boolean isChilren(Menu menu,boolean flag){
        List<Menu> childrenList = menuDao.findChildrenMenu(menu.getMenu_id(),flag);
        if (!childrenList.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 分页查询菜单信息
     * @param tablepar 分页数据实体
     * @param menuName 菜单名称
     * @return
     */
    public PageInfo<Menu> listPage(Tablepar tablepar, String menuName){
        PageHelper.startPage(tablepar.getPageNum(),tablepar.getPageSize());
        List<Menu> menuList = menuDao.findList(menuName);
        PageInfo<Menu> info = new PageInfo<>(menuList);
        return info;
    };

    /**
     * 验证菜单名称是否唯一
     * @param menu_name 菜单名称
     * @param menu_id 菜单编号
     * @return
     */
    public boolean checkMenuNameUnique(String menu_name,String menu_id){
        Integer count = menuDao.checkMenuNameUnique(menu_name,menu_id);
        if(count > 0){
            return false;
        }
        return true;
    };

    /**
     * 验证请求路径是否唯一
     * @param menu_url 请求路径
     * @param menu_id 菜单编号
     * @return
     */
    public boolean checkURLUnique(String menu_url,String menu_id){
        Integer count = menuDao.checkURLUnique(menu_url,menu_id);
        if(count > 0){
            return false;
        }
        return true;
    };

    /**
     * 验证权限名称是否唯一
     * @param permission 权限名称
     * @param menu_id 菜单编号
     * @return
     */
    public boolean checkPermsUnique(String permission,String menu_id){
        Integer count = menuDao.checkPermsUnique(permission,menu_id);
        if(count > 0){
            return false;
        }
        return true;
    };

    /**
     * 新增菜单
     * @param menu 菜单实体
     * @return
     */
    public boolean saveMenu(Menu menu){
        //判断为目录的时候添加父id为0
        if(menu.getMenu_type() == 0){
            menu.setParent_id(menuDao.findRoot(Const.ROOT_MENU_NAME).getMenu_id());//
        }
        return menuDao.saveMenu(menu);
    };

    /**
     * 查询指定编号的菜单信息
     * @param menu_id 菜单编号
     * @return 菜单实体
     */
    public Menu findById(Integer menu_id){
        return menuDao.findById(menu_id);
    };

    /**
     * 查询父菜单信息
     * @param parent_id 父编号编号
     * @return 菜单实体
     */
    public Menu findParentMenu(Integer parent_id){
        return menuDao.findParentMenu(parent_id);
    };

    /**
     * 修改菜单信息
     * @param menu 要修改的菜单实体
     * @return 是否成功
     */
    public boolean updateMenu(Menu menu){
        //判断为目录的时候添加父id为0
        if(menu.getMenu_type() == 0){
            menu.setParent_id(menuDao.findRoot(Const.ROOT_MENU_NAME).getMenu_id());//
        }
        return menuDao.updateMenu(menu);
    };

    /**
     * 批量删除记录
     * @param ids 菜单编号数组
     * @return 是否成功
     */
    public boolean delIds(int[] ids){
        for (int itme:ids) {
            Menu menu = menuDao.findById(itme);
            deleteChildren(menu);
            menuDao.delete(menu.getMenu_id());
        }
        return true;
    }

    /**
     * 递归删除子菜单
     * @param menu 父菜单
     */
    public void deleteChildren(Menu menu){
        List<Menu> childrenList = menuDao.findChildrenMenu(menu.getMenu_id(),true);
        if(childrenList != null){
            for (Menu item:childrenList) {
                menuDao.delete(item.getMenu_id());
                deleteChildren(item);
            }
        }
    }

    /**
     * 设置选择参数 是否选中
     * @param menu 当前菜单实体
     * @param role_id 角色编号
     * @return
     */
    public Menu setState(Menu menu,Integer role_id){
        if(role_id != null){
            List<Integer> menus = menuDao.findMenuByRoleId(role_id);
            if(menus != null && menus.size()>0){
                if(menus.contains(menu.getMenu_id())){
                    Map<String,Object> map = new HashMap<String,Object>();
                    //设置选中
                    map.put("checked", true);
                    //设置展开
                    map.put("expanded", false);
                    menu.setState(map);
                }
            }
        }
        return menu;
    }
}
