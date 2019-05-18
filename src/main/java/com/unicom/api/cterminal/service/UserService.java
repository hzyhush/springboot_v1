package com.unicom.api.cterminal.service;

import com.github.pagehelper.PageInfo;
import com.unicom.api.cterminal.entity.other.Tablepar;
import com.unicom.api.cterminal.entity.admin.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface UserService {

    /**
     * 根据用户名查询
     * @param userName 用户名
     * @return
     */
    User findByUserName(String userName);

    /**
     * 分页查询
     * @return
     */
    PageInfo<User> listPage(Tablepar tablepar, String name);

    /**
     * 查询用户角色信息
     * @param userName 用户名
     * @return
     */
    Set<String> findRoleName(String userName);

    /**
     * 查询用户权限
     * @param userName 用户名
     * @return
     */
    Set<String> findMenuQX(String userName);

    /**
     * 查询单个用户信息
     * @param user_id 用户编号
     * @return
     */
    User findById(Integer user_id);

    /**
     * 验证用户名是否唯一
     * @param userName 用户名
     * @return
     */
    boolean checkLoginNameUnique(@Param("userName") String userName);

    /***
     * 新增用户
     * @param user 用户实体
     * @return
     */
    boolean saveUser(User user, List<Integer> roles);

    /**
     * 修改用户信息
     * @param user 用户实体
     * @param roles 角色集合
     * @return
     */
    boolean updateUser(User user, List<Integer> roles);

    /**
     * 修改密码
     * @param user 用户实体
     * @return
     */
    boolean updatePwd(User user);

    /**
     * 批量删除用户
     * @param ids 用户编号数组
     * @return
     */
    boolean delIds(int[] ids);
}
