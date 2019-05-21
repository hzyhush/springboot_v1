package com.unicom.api.cterminal.service;

import com.github.pagehelper.PageInfo;
import com.unicom.api.cterminal.entity.admin.Menu;
import com.unicom.api.cterminal.entity.other.Tablepar;

public interface MenuService {

    /**
     * 获取菜单目录
     * @param flag  是否显示按钮，true显示/false不显示
     * @param role_id 选择的角色编号,主要用来判断菜单是否选中,可以为null
     * @return
     */
    Menu getMenuTree(boolean flag,Integer role_id);

    /**
     * 分页查询菜单信息
     * @param tablepar 分页数据实体
     * @param menuName 菜单名称
     * @return
     */
    PageInfo<Menu> listPage(Tablepar tablepar, String menuName);

    /**
     * 验证菜单名称是否唯一
     * @param menu_name 菜单名称
     * @param menu_id 菜单编号
     * @return
     */
    boolean checkMenuNameUnique(String menu_name,String menu_id);

    /**
     * 验证请求路径是否唯一
     * @param menu_url 请求路径
     * @param menu_id 菜单编号
     * @return
     */
    boolean checkURLUnique(String menu_url,String menu_id);

    /**
     * 验证权限名称是否唯一
     * @param permission 权限名称
     * @param menu_id 菜单编号
     * @return
     */
    boolean checkPermsUnique(String permission,String menu_id);

    /**
     * 新增菜单
     * @param menu 菜单实体
     * @return
     */
    boolean saveMenu(Menu menu);

    /**
     * 查询指定编号的菜单信息
     * @param menu_id 菜单编号
     * @return 菜单实体
     */
    Menu findById(Integer menu_id);

    /**
     * 查询父菜单信息
     * @param parent_id 父编号编号
     * @return 菜单实体
     */
    Menu findParentMenu(Integer parent_id);

    /**
     * 修改菜单信息
     * @param menu 要修改的菜单实体
     * @return 是否成功
     */
    boolean updateMenu(Menu menu);

    /**
     * 批量删除记录
     * @param ids 菜单编号数组
     * @return 是否成功
     */
    boolean delIds(int[] ids);
}
