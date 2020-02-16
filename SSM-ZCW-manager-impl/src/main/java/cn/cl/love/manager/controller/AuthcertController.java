package cn.cl.love.manager.controller;

import cn.cl.love.bean.Member;
import cn.cl.love.potal.service.MemberService;
import cn.cl.love.potal.service.TicketService;
import cn.cl.love.util.AjaxResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 标题：
 * 作者：何处是归程
 * 时间：2020/2/9 - 9:53
 */
@Controller
@RequestMapping("/authcert")
public class AuthcertController {
	@Autowired
	private TaskService taskService;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private TicketService ticketService;

	@Autowired
	private MemberService memberService;

	// 跳转首页
	@RequestMapping("toIndex")
	public String toIndex() {
		return "authcert/index";
	}

	// 跳转show页
	@RequestMapping("/toShow")
	public String toShow(Integer memberid, Model model) {
		Member member = memberService.getMemberById(memberid);
		List<Map<String, Object>> list = memberService.queryCertBymemberId(memberid);
		model.addAttribute("certimgs", list);
		model.addAttribute("member", member);
		return "authcert/show";
	}

	/**
	 * @功能: 列表
	 * @作者: 高志红
	 */
	@ResponseBody
	@RequestMapping("index")
	public Object index(@RequestParam(value = "begin", defaultValue = "1") Integer begin) {
		try {
			// 1. 查询后台backuser委托组的任务
			PageHelper.startPage(begin, 10);
			List<Task> list = taskService.createTaskQuery()
					.processDefinitionKey("auth")
					.taskCandidateGroup("backuser").list();

			// 对结果进行封装
			List<Map<String, Object>> mylist = new ArrayList<Map<String, Object>>();
			for (Task task : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("taskid", task.getId());
				map.put("taskName", task.getName());
				// 2. 根据任务查询流程定义（流程定义名称，流程定义版本）
				ProcessDefinition processDefinition = repositoryService
						.createProcessDefinitionQuery()
						.processDefinitionId(task.getProcessDefinitionId())
						.singleResult();
				map.put("procDefName", processDefinition.getName());
				map.put("procDefVersion", processDefinition.getVersion());
				// 3. 根据任务流程实例（根据流程实例的ID查询流程单，查询用户信息）
				Member member = ticketService.getMemberByPiid(task.getProcessInstanceId());
				map.put("memberName", member.getUsername());
				map.put("memberid", member.getId());
				mylist.add(map);
			}
			PageInfo info = new PageInfo(mylist, 10);
			return AjaxResult.success("info", info);
		} catch (Exception e) {
			e.printStackTrace();
			return AjaxResult.fail("查询审核信息失败");
		}
	}

	/**
	 * @功能: 通过
	 * @作者: 高志红
	 */
	@ResponseBody
	@RequestMapping("/pass")
	public Object pass(String taskid, Integer memberid) {
		try {
			taskService.setVariable(taskid, "flag", true);
			taskService.setVariable(taskid, "memberid", memberid);
			// 传递参数，让流程继续执行
			taskService.complete(taskid);
			return AjaxResult.success();
		} catch (Exception e) {
			e.printStackTrace();
			return AjaxResult.fail("");
		}
	}

	/**
	 * @功能: 拒绝
	 * @作者: 高志红
	 */
	@ResponseBody
	@RequestMapping("/refuse")
	public Object refuse(String taskid, Integer memberid) {
		try {
			taskService.setVariable(taskid, "flag", false);
			taskService.setVariable(taskid, "memberid", memberid);
			// 传递参数，让流程继续执行
			taskService.complete(taskid);
			return AjaxResult.success();
		} catch (Exception e) {
			e.printStackTrace();
			return AjaxResult.fail("");
		}
	}
}
