package com.unicom.api.cterminal.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.tools.javac.util.Convert;
import com.unicom.api.cterminal.dao.RoleDao;
import com.unicom.api.cterminal.dao.RoleMenuDao;
import com.unicom.api.cterminal.entity.admin.Role;
import com.unicom.api.cterminal.entity.admin.RoleMenu;
import com.unicom.api.cterminal.entity.other.Tablepar;
import com.unicom.api.cterminal.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleDao roleDao;
    @Resource
    private RoleMenuDao roleMenuDao;

    /**
     * 查询所有角色
     * @return
     */
    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    /**
     * 查询用户拥有的角色
     * @param user_id
     * @return
     */
    public List<Role> findUseRole(Integer user_id){
        List<Role> allRole = roleDao.findAll();//全部角色
        List<Role> userRole = roleDao.findUseRole(user_id);//用户拥有的角色
        if(!allRole.isEmpty()){
            for (Role all:allRole) {
                for (Role role:userRole) {
                    if(all.getRole_id() == role.getRole_id()){
                        all.setIscheck(true);//如用户拥有此角色则设置成可见
                    }
                }
            }
        }
        return allRole;
    };

    /**
     * 分页查询角色信息
     * @param tablepar 分页数据实体
     * @param roleName 角色名称
     * @return 分页数据
     */
    public PageInfo<Role> listPage(Tablepar tablepar, String roleName){
        PageHelper.startPage(tablepar.getPageNum(),tablepar.getPageSize());
        List<Role> roleList = roleDao.findList(roleName);
        PageInfo info = new PageInfo(roleList);
        return info;
    };

    /**
     * 验证角色名称是否唯一
     * @param role_name 角色名称
     * @param role_id 角色编号
     * @return
     */
    public boolean checkNameUnique(String role_name,Integer role_id){
      Integer count = roleDao.checkNameUnique(role_name,role_id);
      if(count > 0){
          return false;
      }
      return true;
    };

    /**
     * 新增角色
     * @param role 角色实体
     * @return
     */
    public boolean saveRole(Role role,String prem){
        Integer id = roleDao.selectAutoId();//自增长编号
        role.setRole_id(id);
        List<RoleMenu> list = new ArrayList<RoleMenu>();
        if(StringUtils.isNotBlank(prem)){
            String[] array = prem.split(",");
            for (String item:array) {
                RoleMenu roleMenu = new RoleMenu(id,item);
                list.add(roleMenu);
            }
            roleMenuDao.saveRoleMenu(list);
        }
        return roleDao.saveRole(role);
    };

    /**
     * 查询指定编号的角色信息
     * @param role_id 角色编号
     * @return
     */
    public Role findById(@Param("role_id")Integer role_id){
        return roleDao.findById(role_id);
    };

    /**
     * 批量删除
     * @param array 角色编号数组
     * @return 是否删除
     */
    public boolean deleteIdS(int[] array){
        roleMenuDao.deleteIdS(array);
        return roleDao.deleteIdS(array);
    };

    /**
     * 修改角色信息
     * @param role 角色实体
     * @param prem 权限编号数组
     * @return
     */
    public boolean updateRole(Role role,String prem){
        roleMenuDao.delete(role.getRole_id());//先删除原来的菜单信息
        List<RoleMenu> list = new ArrayList<RoleMenu>();
        if(StringUtils.isNotBlank(prem)){
            String[] array = prem.split(",");
            for (String item:array) {
                RoleMenu roleMenu = new RoleMenu(role.getRole_id(),item);
                list.add(roleMenu);
            }
            roleMenuDao.saveRoleMenu(list);
        }
        return roleDao.updateRole(role);
    };
}
