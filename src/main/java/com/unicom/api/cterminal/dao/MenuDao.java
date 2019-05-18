package com.unicom.api.cterminal.dao;

import com.unicom.api.cterminal.entity.admin.Menu;
import com.unicom.api.cterminal.entity.other.BootstrapThree;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MenuDao {

    /**
     * 查询根节点
     */
    @Select("select * from sys_menu where menu_name = #{menu_name} and parent_id = 0 and menu_type = 0")
    Menu findRoot(@Param("menu_name") String menu_name);

    /**
     * 获取根路径
     * @return
     */
    @Select("select menu_name,menu_icon,menu_id,menu_url from sys_menu where menu_type = 0 and parent_id = 0 order by order_number asc")
    Menu findByRoot();

    /**
     * 查询子菜单目录
     * @param menu_id 父级编号
     * @param flag 是否显示按钮，true显示/false不显示
     * @return
     */
    List<Menu> findChildrenMenu(@Param("menu_id") Integer menu_id,@Param("flag")boolean flag);


    /**
     * 查询菜单信息
     * @param menuName 菜单名称
     * @return
     */
    List<Menu> findList(@Param("menuName")String menuName);

    /**
     * 验证菜单名称是否唯一
     * @param menu_name 菜单名称
     * @param menu_id 菜单编号
     * @return
     */
    Integer checkMenuNameUnique(@Param("menu_name")String menu_name,@Param("menu_id")String menu_id);

    /**
     * 验证请求路径是否唯一
     * @param menu_url 请求路径
     * @param menu_id 菜单编号
     * @return
     */
    Integer checkURLUnique(@Param("menu_url")String menu_url,@Param("menu_id")String menu_id);

    /**
     * 验证权限名称是否唯一
     * @param permission 权限名称
     * @param menu_id 菜单编号
     * @return
     */
    Integer checkPermsUnique(@Param("permission")String permission,@Param("menu_id")String menu_id);

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
    @Select("select * from sys_menu where menu_id = #{menu_id}")
    Menu findById(@Param("menu_id")Integer menu_id);

    /**
     * 查询父菜单信息
     * @param parent_id 父编号编号
     * @return 菜单实体
     */
    @Select("select * from sys_menu where menu_id = #{parent_id}")
    Menu findParentMenu(@Param("parent_id")Integer parent_id);

    /**
     * 修改菜单信息
     * @param menu 要修改的菜单实体
     * @return 是否成功
     */
    boolean updateMenu(Menu menu);

    /**
     * 删除菜单
     * @param menu_id 菜单编号
     * @return 是否成功
     */
    @Delete("delete from sys_menu where menu_id= #{menu_id}")
    boolean delete(@Param("menu_id")Integer menu_id);

    /**
     * 删除子菜单菜单
     * @param menu_id 菜单编号
     * @return 是否成功
     */
    @Delete("delete from sys_menu where parent_id= #{menu_id}")
    boolean deleteChildren(@Param("menu_id")Integer menu_id);

    /**
     * 根据角色id查询所用户菜单
     * @param role_id
     * @return
     */
    @Select("select menu_id from sys_role_menu where role_id = #{role_id}")
    List<Integer> findMenuByRoleId(@Param("role_id")Integer role_id);
}
