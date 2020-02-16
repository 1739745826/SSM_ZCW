package cn.cl.love.interceptor;

import cn.cl.love.util.Const;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * 标题：访问拦截器
 * 作者：何处是归程
 * 时间：2020/2/1 - 17:24
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 查询所有许可
        // 每次查询都要与数据库进行交互，效率太低
        /*  List<Permission> permissionsAll = permissionService.getPermissionsAll();*/
        /* Set<String> allUris = new HashSet<String>();
        // 循环遍历获取所有uri
        for (Permission permission : permissionsAll) {
            allUris.add("/" + permission.getUrl());
        }*/

        // 改进：在服务器启动时加载所有许可路径，并存放到application域中
        Set<String> allUris = (Set<String>) request.getSession().getServletContext().getAttribute(Const.ALL_URIS);

        // 2. 判断请求路径是否在所有许可范围内
        String servlerPath = request.getServletPath();
        if (allUris.contains(servlerPath)) {
            // 3. 判断请求路径是否在用户所拥有的权限内
            Set<String> myUris = (Set<String>) request.getSession().getAttribute(Const.MY_URIS);
            if (myUris.contains(servlerPath)) {
                return true;
            } else {
                response.sendRedirect(request.getContextPath() + "/login.htm");
                return false;
            }
        } else {
            return true;
        }
    }
}
