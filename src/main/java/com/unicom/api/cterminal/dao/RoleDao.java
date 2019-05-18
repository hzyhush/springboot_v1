package com.unicom.api.cterminal.dao;

import com.unicom.api.cterminal.entity.admin.Role;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleDao {

    /**
     * 查询所有角色
     * @return
     */
    @Select("select * from sys_role order by role_id asc")
    List<Role> findAll();

    /**
     * 查询用户拥有的角色
     * @param user_id
     * @return
     */
    @Select("select r.role_id,r.role_name from sys_user_role ur inner join sys_role r on r.role_id = ur.role_id " +
            "where user_id = #{user_id} order by  r.role_id desc")
    List<Role> findUseRole(@Param("user_id") Integer user_id);

    /**
     * 查询角色信息
     * @param roleName 角色名称
     * @return 角色集合
     */
    List<Role> findList(@Param("roleName") String roleName);

    /**
     * 验证角色名称是否唯一
     * @param role_name 角色名称
     * @param role_id 角色编号
     * @return
     */
    Integer checkNameUnique(@Param("role_name") String role_name,@Param("role_id")Integer role_id);

    /**
     * 新增角色
     * @param role 角色实体
     * @return
     */
    boolean saveRole(Role role);

    /**
     * 查询自增长编号
     * @return
     */
    @Select("select AUTO_INCREMENT id from INFORMATION_SCHEMA.TABLES where TABLE_NAME='sys_role' and TABLE_SCHEMA = 'springboot'")
    Integer selectAutoId();

    /**
     * 查询指定编号的角色信息
     * @param role_id 角色编号
     * @return
     */
    @Select("select role_id,role_name,role_description from sys_role where role_id = #{role_id}")
    Role findById(@Param("role_id")Integer role_id);

    /**
     * 批量删除
     * @param array 角色编号数组
     * @return 是否删除
     */
    boolean deleteIdS(int[] array);

    /**
     * 修改角色信息
     * @param role
     * @return
     */
    boolean updateRole(Role role);
}
