package com.unicom.api.cterminal.dao;

import com.unicom.api.cterminal.entity.admin.RoleMenu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMenuDao {

    /**
     * 添加角色菜单对应关系
     * @param list 角色菜单关系集合
     * @return
     */
    boolean saveRoleMenu(List<RoleMenu> list);

    /**
     * 批量删除
     * @param array 角色编号数组
     * @return 是否删除
     */
    boolean deleteIdS(int[] array);

    /**
     * 删除角色信息
     * @param role_id 角色编号
     * @return
     */
    @Delete("delete from sys_role_menu where role_id = #{role_id} ")
    boolean delete(@Param("role_id") Integer role_id);
}
