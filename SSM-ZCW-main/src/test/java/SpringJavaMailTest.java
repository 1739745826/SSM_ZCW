import cn.cl.love.util.DesUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

/**
 * 标题：
 * 作者：何处是归程
 * 时间：2020/2/6 - 16:31
 */
public class SpringJavaMailTest {
	public static void main(String[] args) throws Exception {
		// 使用JAVA程序发送邮件
		ApplicationContext application = new ClassPathXmlApplicationContext("spring/spring-*.xml");
		// 邮件发送器，由Spring框架提供的
		JavaMailSenderImpl javaMailSender = (JavaMailSenderImpl) application.getBean("sendMail");
		javaMailSender.setDefaultEncoding("UTF-8");
		MimeMessage mail = javaMailSender.createMimeMessage(); // 一封情书
		MimeMessageHelper helper = new MimeMessageHelper(mail);
		helper.setSubject("检讨书"); // 邮件标题
		StringBuilder content = new StringBuilder();
		// 内容
		String param = "ILY";
		param = DesUtil.encrypt(param, "abcdefghijklmnopquvwxyz");
		content.append("<a href='http://www.atcrowdfunding.com/test/act.do?p=" + param + "'>激活链接</a>");
		helper.setText(content.toString(), true);
		helper.setFrom("admin@qq.com");
		helper.setTo("test@qq.com");
		javaMailSender.send(mail);
	}
}
