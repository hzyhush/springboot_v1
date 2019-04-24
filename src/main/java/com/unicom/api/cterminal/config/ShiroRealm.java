package com.unicom.api.cterminal.config;

import com.unicom.api.cterminal.entity.Menu;
import com.unicom.api.cterminal.entity.Role;
import com.unicom.api.cterminal.entity.User;
import com.unicom.api.cterminal.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 自定义身份认证realm
 */
public class ShiroRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    /**
     *  提供用户信息，返回权限信息
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String userName  = (String)principals.getPrimaryPrincipal();
        Set<String> roles = userService.findRoleName(userName);
        Set<String> menuQX = userService.findMenuQX(userName);
        authorizationInfo.addRoles(roles);//添加角色信息
        authorizationInfo.addStringPermissions(menuQX);//添加权限
        return authorizationInfo;
    }

    /**
     * 提供帐户信息，返回认证信息
     * @param token
     * @return
     * @throws AuthenticationException
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("MyShiroRealm.doGetAuthenticationInfo()");
        //获取用户的输入的账号.
        String userName = (String)token.getPrincipal();
        System.out.println(token.getCredentials());
        //通过userName从数据库中查找 User对象，如果找到，没找到.
        User user = userService.findByUserName(userName);
        System.out.println("----->>user="+user);
        if(user == null){
            throw new UnknownAccountException();//账号不存在
        }
        if(user.getUser_status()!= 1){
            throw new LockedAccountException(); //帐号禁用
        }
        //此处是获取数据库内的账号、密码、盐值，进行密码匹配
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUser_name(), //用户名
                user.getUser_pwd(), //密码
                ByteSource.Util.bytes(user.getUser_salt()),//salt=username+salt
                getName()  //realm name
        );
        return authenticationInfo;
    }
}
