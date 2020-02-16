package cn.cl.love.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 标题：获取IOC容器的工具类
 * 作者：何处是归程
 * 时间：2020/2/9 - 14:02
 */
@Component
public class ApplicationContextUtils implements ApplicationContextAware {
	public   static  ApplicationContext applicationContext;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ApplicationContextUtils.applicationContext = applicationContext;
	}
}
