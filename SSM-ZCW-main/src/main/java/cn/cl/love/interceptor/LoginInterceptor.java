package cn.cl.love.interceptor;

import cn.cl.love.bean.Member;
import cn.cl.love.bean.User;
import cn.cl.love.util.Const;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

/**
 * 标题：登录拦截器
 * 作者：何处是归程
 * 时间：2020/2/1 - 16:24
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 定义那些路径是不需要拦截的
        Set<String> uri = new HashSet<String>();
        uri.add("/regUse.do");
        uri.add("/index.htm");
        uri.add("/index.jsp");
        uri.add("/reg.htm");
        uri.add("/login.htm");
        uri.add("/regUser.do");
        uri.add("/doLogin.do");
        uri.add("/logout.do");
        uri.add("/checkCode.do");
        String servletPath = request.getServletPath();
        if (uri.contains(servletPath)) {
            return true;
        }
        // 2. 判断用户是否登录，如果登录就放行
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        Member member = (Member)session.getAttribute(Const.LOGIN_Member);
        if (user != null || member != null) {
            return true;
        } else {
            response.sendRedirect(request.getContextPath() + "/login.htm");
            return false;
        }
    }
}
