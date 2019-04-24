package com.unicom.api.cterminal.service;

import com.unicom.api.cterminal.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface UserService {

    /**
     * 根据用户名查询
     * @param userName
     * @return
     */
    User findByUserName(String userName);

    List<User> findAll();

    /**
     * 查询用户角色信息
     * @param userName
     * @return
     */
    Set<String> findRoleName(String userName);

    /**
     * 查询用户权限
     * @param userName
     * @return
     */
    Set<String> findMenuQX(String userName);

    /**
     * 查询单个用户信息
     * @param user_id
     * @return
     */
    User findById(Integer user_id);

}
