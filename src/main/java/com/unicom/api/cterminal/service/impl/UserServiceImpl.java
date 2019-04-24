package com.unicom.api.cterminal.service.impl;

import com.unicom.api.cterminal.dao.UserMapper;
import com.unicom.api.cterminal.entity.User;
import com.unicom.api.cterminal.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Resource
    private UserMapper userMapper;

    /**
     * 根据用户名查询
     * @param userName
     * @return
     */
    public User findByUserName(String userName) {
        return userMapper.findByUserName(userName);
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    /**
     * 查询用户角色信息
     * @param userName
     * @return
     */
    @Override
    public Set<String> findRoleName(String userName) {
        return userMapper.findRoleName(userName);
    }

    /**
     * 查询用户权限
     * @param userName
     * @return
     */
    @Override
    public Set<String> findMenuQX(String userName) {
        return userMapper.findMenuQX(userName);
    }

    /**
     * 查询单个用户信息
     * @param user_id
     * @return
     */
    @Override
    public User findById(Integer user_id) {
        return userMapper.findById(user_id);
    }
}
