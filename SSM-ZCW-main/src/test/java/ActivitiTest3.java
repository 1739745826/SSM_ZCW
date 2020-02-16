import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 标题：Activiti
 * 作者：何处是归程
 * 时间：2020/2/2 - 9:23
 */
public class ActivitiTest3 {
	ApplicationContext ioc = new ClassPathXmlApplicationContext("spring/spring-*.xml");
	ProcessEngine processEngine = (ProcessEngine) ioc.getBean("processEngine");

	// 1. 创建流程引擎 创建23张表
	@Test
	public void test() {
		// ProcessEngine processEngine = (ProcessEngine)ioc.getBean("processEngine");
		System.out.println(processEngine);
	}

	// 2. 部署流程定义
	@Test
	public void test2() {
		RepositoryService repositoryService = processEngine.getRepositoryService();
		Deployment deploy = repositoryService
				.createDeployment()
				.addClasspathResource("Activiti_BHWG.bpmn")
				// .addClasspathResource("ActivitiOne.png")
				.deploy();
		System.out.println("deploy：" + deploy);
	}

	// 3. 查询部署的流程定义
	@Test
	public void test3() {
		RepositoryService repositoryService = processEngine.getRepositoryService();
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
		List<ProcessDefinition> list = processDefinitionQuery.list();
		for (ProcessDefinition processDefinition : list) {
			System.out.println("流程定义的id：" + processDefinition.getId());
			System.out.println("流程定义的name：" + processDefinition.getName());
			System.out.println("流程定义的版本：" + processDefinition.getVersion());
		}
		long count = processDefinitionQuery.count();
		System.out.println("数量：" + count);
	}

	// 4. 启动流程实例
	@Test
	public void test4() {
		ProcessDefinition processDefinition = processEngine
				.getRepositoryService()
				.createProcessDefinitionQuery()
				.processDefinitionKey("BHWG")
				.singleResult();
		Map<String, Object> variblea = new HashMap<String, Object>();
		variblea.put("days", "5");
		variblea.put("mayne", "3000");
		ProcessInstance processInstance = processEngine
				.getRuntimeService()
				.startProcessInstanceById(processDefinition.getId(), variblea);
		System.out.println(processDefinition);
	}


	// 6. 查询流程实例的任务，并执行任务
	@Test
	public void test6() {
		TaskService taskService = processEngine.getTaskService();
		TaskQuery taskQuery = taskService.createTaskQuery();
		List<Task> list = taskQuery.taskAssignee("高志红").list();
		for (Task task : list) {
			taskService.complete(task.getId());
			System.out.println(task.getAssignee() + "完成了任务：" + task.getName() + "，任务ID为：" + task.getId() + "。");
		}
	}

	// 7. 查询历史记录
	@Test
	public void test7() {
		HistoryService historyService = processEngine.getHistoryService();
		HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();
		List<HistoricProcessInstance> activitiTwo = historicProcessInstanceQuery.processDefinitionKey("ActivitiTwo").finished().list();
		for (HistoricProcessInstance historicProcessInstance : activitiTwo) {
			System.out.println(historicProcessInstance);
		}
	}
}
