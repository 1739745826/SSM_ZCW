import org.activiti.engine.ProcessEngine;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 标题：
 * 作者：何处是归程
 * 时间：2020/1/28 - 21:38
 */
public class NullPro {
    ApplicationContext ioc = new ClassPathXmlApplicationContext("spring/spring-*.xml");
    ProcessEngine processEngine = (ProcessEngine) ioc.getBean("processEngine");

	@Test
	public void test() throws InterruptedException {
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery()
				.processInstanceId("301")
				.taskAssignee("zs")
				.singleResult();
        System.out.println(task);
	}
}
