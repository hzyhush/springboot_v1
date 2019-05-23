package com.unicom.api.cterminal.dao;

import com.unicom.api.cterminal.entity.admin.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Set;

public interface UserDao {

    /**
     * 根据用户名查询
     * @param userName 用户名
     * @return
     */
    @Select("select user_id,user_name,user_pwd,user_salt,user_status from sys_user where user_name = #{userName}")
    User findByUserName(@Param("userName")String userName);

    /**
     * 查询用户信息
     * @return
     */
    List<User> findList(@Param("userName") String userName);

    /**
     * 查询用户角色信息
     * @param userName 用户名
     * @return
     */
    @Select("select r.role_name from sys_user_role ur inner join sys_role r on ur.role_id = r.role_id " +
            "where user_id = (select user_id from sys_user where user_name = #{userName} and user_status =0)")
    Set<String> findRoleName(@Param("userName") String userName);

    /**
     * 查询用户权限
     * @param userName
     * @return
     */
    @Select("select permission from sys_user u inner join sys_user_role ur on ur.user_id = u.user_id inner join sys_role r on r.role_id = ur.role_id " +
            "inner join sys_role_menu rm on rm.role_id = r.role_id inner join sys_menu m on m.menu_id = rm.menu_id  " +
            "where u.user_name =#{userName} and permission  is not null and permission != '' group by permission")
    Set<String> findMenuQX(@Param("userName") String userName);

    /**
     * 查询单个用户信息
     * @param user_id 用户编号
     * @return
     */
    @Select("select user_id,user_name,user_pwd,user_salt,user_status from sys_user where user_id = #{user_id}")
    User findById(@Param("user_id")Integer user_id);

    /**
     * 验证用户名是否唯一
     * @param userName 用户名
     * @return
     */
    @Select("select count(*) from sys_user where user_name = #{userName} and user_status != 2")
    Integer checkLoginNameUnique(@Param("userName") String userName);

    /***
     * 新增用户
     * @param user 用户实体
     * @return
     */
    @Insert("insert into sys_user(user_name,user_pwd,user_salt,user_id) values(#{user_name},#{user_pwd},#{user_salt},#{user_id})")
    boolean saveUser(User user);

    /**
     * 查询自增长编号
     * @return
     */
    @Select("select AUTO_INCREMENT id from INFORMATION_SCHEMA.TABLES where TABLE_NAME='sys_user' and TABLE_SCHEMA = 'springboot'")
    Integer selectAutoId();

    /**
     * 修改密码
     * @param user 用户实体
     * @return
     */
    @Update("update sys_user set user_pwd = #{user_pwd},user_salt = #{user_salt} where user_id = #{user_id}")
    boolean updatePwd(User user);

    /**
     * 批量删除用户
     * @param ids 用户编号数组
     * @return
     */
    boolean delIds(int[] ids);
}
