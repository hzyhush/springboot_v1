package com.unicom.api.cterminal.service;

import com.github.pagehelper.PageInfo;
import com.unicom.api.cterminal.entity.admin.Role;
import com.unicom.api.cterminal.entity.other.Tablepar;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleService {

    /**
     * 查询所有角色
     * @return
     */
    List<Role> findAll();

    /**
     * 查询用户拥有的角色
     * @param user_id
     * @return
     */
    List<Role> findUseRole(Integer user_id);

    /**
     * 分页查询角色信息
     * @param tablepar 分页数据实体
     * @param roleName 角色名称
     * @return 分页数据
     */
    PageInfo<Role> listPage(Tablepar tablepar, String roleName);

    /**
     * 验证角色名称是否唯一
     * @param role_name 角色名称
     * @param role_id 角色编号
     * @return
     */
    boolean checkNameUnique(String role_name,Integer role_id);

    /**
     * 新增角色
     * @param role 角色实体
     * @return
     */
    boolean saveRole(Role role,String prem);

    /**
     * 查询指定编号的角色信息
     * @param role_id 角色编号
     * @return
     */
    Role findById(@Param("role_id")Integer role_id);

    /**
     * 批量删除
     * @param array 角色编号数组
     * @return 是否删除
     */
    boolean deleteIdS(int[] array);

    /**
     * 修改角色信息
     * @param role 角色实体
     * @param prem 权限编号数组
     * @return
     */
    boolean updateRole(Role role,String prem);
}
