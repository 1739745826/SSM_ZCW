package cn.cl.love.manager.controller;

import cn.cl.love.util.AjaxResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 标题：
 * 作者：何处是归程
 * 时间：2020/2/6 - 18:06
 */
@Controller
@RequestMapping("/process")
public class ProcessController {
	@Autowired
	private RepositoryService repositoryService;

	/**
	 * @功能: 跳转主页
	 * @作者: 高志红
	 */
	@RequestMapping("/toIndex")
	public String toIndex() {
		return "process/index";
	}

	/**
	 * @功能: 查询数据
	 * @作者: 高志红
	 */
	@ResponseBody
	@RequestMapping("/index")
	public Object index(@RequestParam(value = "begin", defaultValue = "1") Integer begin) {
		try {
			PageHelper.startPage(begin, 10);
			// 查询流程定义集合数据可能出现的自关联，导致Jackson组件无法将集合序列化为JSON串
			List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().list();
			if (list != null) {
				// 对结果进行封装
				List<Map<String, Object>> mylist = new ArrayList<Map<String, Object>>();
				for (ProcessDefinition processDefinition : list) {
					Map<String, Object> pd = new HashMap<String, Object>();
					pd.put("id", processDefinition.getId());
					pd.put("name", processDefinition.getName());
					pd.put("key", processDefinition.getKey());
					pd.put("version", processDefinition.getVersion());
					mylist.add(pd);
				}
				PageInfo info = new PageInfo(mylist, 10);
				return AjaxResult.success("info", info);
			}else {
				return AjaxResult.fail("查询结果为空！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return AjaxResult.fail("查询数据失败");
		}
	}

	/**
	 * @功能: 部署流程文件
	 * @作者: 高志红
	 */
	@ResponseBody
	@RequestMapping("/deploy")
	public Object deploy(HttpServletRequest request) {
		try {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipartHttpServletRequest.getFile("ProcessDefFile");
			InputStream inputStream = multipartFile.getInputStream();
			repositoryService.createDeployment().addInputStream(multipartFile.getOriginalFilename(), inputStream).deploy();
			return AjaxResult.success();
		} catch (Exception e) {
			e.printStackTrace();
			return AjaxResult.fail("部署流程定义失败");
		}
	}

	/**
	 * @功能: 删除流程定义
	 * @作者: 高志红
	 */
	@ResponseBody
	@RequestMapping("/del")
	public Object del(String id) { //
		try {
			ProcessDefinition processDefinition = repositoryService
					.createProcessDefinitionQuery()
					.processDefinitionId(id)
					.singleResult();
			repositoryService.deleteDeployment(processDefinition.getDeploymentId(), true);
			return AjaxResult.success();
		} catch (Exception e) {
			e.printStackTrace();
			return AjaxResult.fail("流程定义删除失败");
		}
	}
}
