package cn.cl.love.listener;

import cn.cl.love.bean.Permission;
import cn.cl.love.manager.service.PermissionService;
import cn.cl.love.util.Const;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sound.midi.Soundbank;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 标题：保存项目路径的过滤器
 * 作者：何处是归程
 * 时间：2020/1/28 - 9:05
 */
public class StartSystemListener implements ServletContextListener {
    // 在服务器，创建application对象时需要执行
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // 将上下文路径（request.getContextPath()）放到application域中
        ServletContext servletContext = servletContextEvent.getServletContext();
        String contextPath = servletContext.getContextPath();
        servletContext.setAttribute("APP_PATH", contextPath);

        // 加载所有的许可路径
        ApplicationContext ioc = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        PermissionService permissionService = ioc.getBean(PermissionService.class);
        List<Permission> permissionsAll = permissionService.getPermissionsAll();
        Set<String> allUris = new HashSet<String>();
        // 循环遍历获取所有uri
        for (Permission permission : permissionsAll) {
            allUris.add("/" + permission.getUrl());
        }
        servletContext.setAttribute(Const.ALL_URIS, allUris);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
