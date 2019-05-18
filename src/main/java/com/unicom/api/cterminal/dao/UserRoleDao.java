package com.unicom.api.cterminal.dao;

import com.unicom.api.cterminal.entity.admin.UserRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserRoleDao {

    /**
     * 添加用户角色信息
     * @return
     */
    @Insert("insert into sys_user_role(user_id,role_id) values(#{user_id},#{role_id})")
    boolean saveUserRole(UserRole userRole);

    /**
     * 删除用户角色
     * @param user_id
     * @return
     */
    @Delete("delete from sys_user_role where user_id = #{user_id}")
    boolean delUserId(@Param("user_id") Integer user_id);
}
