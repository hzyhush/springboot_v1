package com.unicom.api.cterminal.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.unicom.api.cterminal.dao.UserDao;
import com.unicom.api.cterminal.dao.UserRoleDao;
import com.unicom.api.cterminal.entity.admin.UserRole;
import com.unicom.api.cterminal.entity.other.Tablepar;
import com.unicom.api.cterminal.entity.admin.User;
import com.unicom.api.cterminal.service.UserService;
import com.unicom.api.cterminal.util.CreateSalt;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


@Service
@CacheConfig(cacheNames = "user")
@Transactional
public class UserServiceImpl implements UserService{

    @Resource
    private UserDao userMapper;
    @Resource
    private UserRoleDao userRoleDao;

    /**
     * 根据用户名查询
     * @param userName 用户名
     * @return
     */
    public User findByUserName(String userName) {
        return userMapper.findByUserName(userName);
    }

    /**
     * 分页查询
     * @return
     */
    public PageInfo<User> listPage(Tablepar tablepar, String name){
        PageHelper.startPage(tablepar.getPageNum(),tablepar.getPageSize());
        List<User> list = userMapper.findList(name);
        PageInfo<User> info = new PageInfo<User>(list);
        return info;
    };

    /**
     * 查询用户角色信息
     * @param userName 用户名
     * @return
     */
    @Override
    public Set<String> findRoleName(String userName) {
        return userMapper.findRoleName(userName);
    }

    /**
     * 查询用户权限
     * @param userName 用户名
     * @return
     */
    public Set<String> findMenuQX(String userName) {
        return userMapper.findMenuQX(userName);
    }

    /**
     * 查询单个用户信息
     * @param user_id 用户编号
     * @return
     */
    @Override
    public User findById(Integer user_id) {
        return userMapper.findById(user_id);
    }

    /**
     * 验证用户名是否唯一
     * @param userName 用户名
     * @return
     */
    public boolean checkLoginNameUnique(String userName) {
        Integer count = userMapper.checkLoginNameUnique(userName);
        if(count > 0){
           return false;
        }
        return true;
    }

    /***
     * 新增用户
     * @param user 用户实体
     * @return
     */
    public boolean saveUser(User user,List<Integer> roles){
        String salt = CreateSalt.getSalt();//盐值
        user.setUser_salt(salt);
        user.setUser_pwd(new SimpleHash("MD5", user.getUser_pwd(), salt, 2).toString());
        boolean flag = userMapper.saveUser(user);
        if(flag && roles != null){
            for (Integer role:roles) {
                UserRole userRole = new UserRole(user.getUser_id(),role);
                userRoleDao.saveUserRole(userRole);
            }
        }
        return flag;
    };

    /**
     * 修改用户信息
     * @param user 用户实体
     * @param roles 角色集合
     * @return
     */
    public boolean updateUser(User user, List<Integer> roles){
        boolean flag = userRoleDao.delUserId(user.getUser_id());//先删除用户角色
        if(roles != null){
            for (Integer role:roles) {
                UserRole userRole = new UserRole(user.getUser_id(),role);
                userRoleDao.saveUserRole(userRole);
            }
        }
        return true;
    };

    /**
     * 修改用户密码
     * @param user 用户实体
     * @return
     */
    public boolean updatePwd(User user){
        String salt = CreateSalt.getSalt();//盐值
        user.setUser_salt(salt);
        user.setUser_pwd(new SimpleHash("MD5", user.getUser_pwd(), salt, 2).toString());
        return userMapper.updatePwd(user);
    };

    /**
     * 批量删除用户
     * @param ids 用户编号数组
     * @return
     */
    public boolean delIds(int[] ids){
        return userMapper.delIds(ids);
    };

}
