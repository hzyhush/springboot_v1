package com.unicom.api.cterminal.dao;

import com.unicom.api.cterminal.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserMapper {

    /**
     * 根据用户名查询
     * @param userName
     * @return
     */
    @Select("select user_id,user_name,user_pwd,user_salt,user_status from tb_user where user_name = #{userName}")
    User findByUserName(@Param("userName")String userName);

    @Select("select user_id,user_name,user_pwd,user_salt,user_status from tb_user order by user_id asc")
    List<User> findAll();

    /**
     * 查询用户角色信息
     * @param userName
     * @return
     */
    @Select("select r.role_name from tb_user_role ur inner join tb_role r on ur.role_id = r.role_id " +
            "where user_id = (select user_id from tb_user where user_name = #{userName} and user_status =1) and r.role_available =0")
    Set<String> findRoleName(@Param("userName") String userName);

    /**
     * 查询用户权限
     * @param userName
     * @return
     */
    @Select("select menu_qx from tb_role_menu rm inner join tb_menu m on m.menu_id = rm.menu_id where m.menu_available = 0 and m.menu_type='button' " +
            "and rm.role_id in (select ur.role_id from tb_user_role ur inner join tb_role r on ur.role_id = r.role_id " +
            "where user_id = (select user_id from tb_user where user_name = #{userName} and user_status =1) and r.role_available =0) " +
            "and menu_qx  is not null and menu_qx != ''")
    Set<String> findMenuQX(@Param("userName") String userName);

    /**
     * 查询单个用户信息
     * @param user_id
     * @return
     */
    @Select("select user_id,user_name,user_pwd,user_salt,user_status from tb_user where user_id = #{user_id}")
    User findById(@Param("user_id")Integer user_id);

}
