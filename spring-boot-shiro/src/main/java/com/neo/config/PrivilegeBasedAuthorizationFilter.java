package com.neo.config;

import com.neo.sevice.PermissionService;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Set;

/**
 * spring-boot-shiro-PrivilegeBasedAuthorizationFilter
 * 重新设计perms鉴权方法，所有权限控制转换为通过权限点控制;
 * 不再使用roles等
 * example: url - perms[resource:type] type:c/r/u/d
 *
 * @author yh
 * @since 0.0.1 2017-09-04 22:03
 */
public class PrivilegeBasedAuthorizationFilter extends AuthorizationFilter {

    @Autowired
    private PermissionService permissionService;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        //1.限定创建角色，权限点的权限只有默认的admin有权限，其他用户只有查看的权限
        //2.针对每个url，先判断
        Subject subject = getSubject(request, response);
        String[] rolesArray = (String[]) mappedValue;

        if (rolesArray == null || rolesArray.length == 0) {
            //no roles specified, so nothing to check - allow access.
            return true;
        }

        Set<String> roles = CollectionUtils.asSet(rolesArray);
        return subject.hasAllRoles(roles);
    }
}
