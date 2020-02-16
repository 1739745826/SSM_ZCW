package cn.cl.love.potal.controller;

import cn.cl.love.VO.Data;
import cn.cl.love.bean.Cert;
import cn.cl.love.bean.Member;
import cn.cl.love.bean.MemberCert;
import cn.cl.love.bean.Ticket;
import cn.cl.love.manager.service.CertService;
import cn.cl.love.potal.listener.PassListener;
import cn.cl.love.potal.listener.RefuseListener;
import cn.cl.love.potal.service.MemberService;
import cn.cl.love.potal.service.TicketService;
import cn.cl.love.util.AjaxResult;
import cn.cl.love.util.Const;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 标题：
 * 作者：何处是归程
 * 时间：2020/2/7 - 16:46
 */
@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private TicketService ticketService;

	@Autowired
	private CertService certService;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	/**
	 * @功能: 跳转实名认证页面
	 * @作者: 高志红
	 */
	@RequestMapping("/accttype")
	public String accttype() {
		return "member/accttype";
	}

	/**
	 * @功能: 跳转第一关页面
	 * @作者: 高志红
	 */
	@RequestMapping("/basicinfo")
	public String basicinfo() {
		return "member/basicinfo";
	}

	/**
	 * @功能: 跳转第二关页面
	 * @作者: 高志红
	 */
	@RequestMapping("/uploadCert")
	public String uploadCert() {
		return "member/uploadCert";
	}

	/**
	 * @功能: 跳转第三关页面
	 * @作者: 高志红
	 */
	@RequestMapping("/checkemail")
	public String checkemail(HttpSession session, Model model) {
		Member member = (Member) session.getAttribute(Const.LOGIN_Member);
		model.addAttribute("loginMember", member);
		return "member/checkemail";
	}

	/**
	 * @功能: 跳转第四关页面
	 * @作者: 高志红
	 */
	@RequestMapping("/checkauthcode")
	public String checkauthcode() {
		return "member/checkauthcode";
	}

	/**
	 * 更新账户类型
	 */
	@ResponseBody
	@RequestMapping("/updateAcctType")
	public Object updateAcctType(HttpSession session, String accttype) {
		try {
			// 获取登录会员信息
			Member loginMember = (Member) session.getAttribute(Const.LOGIN_Member);
			loginMember.setAccttype(accttype);
			// 更新账户类型
			memberService.updateAcctType(loginMember);
			// 记录流程步骤
			Ticket ticket = ticketService.getTicketByMemberId(loginMember.getId());
			ticket.setPstep("accttype");
			ticketService.updatePstep(ticket);
			return AjaxResult.success();
		} catch (Exception e) {
			e.printStackTrace();
			return AjaxResult.fail("更新状态失败");
		}
	}

	/**
	 * 上传基本信息
	 */
	@ResponseBody
	@RequestMapping("/updateBasicinfo")
	public Object updateBasicinfo(HttpSession session, Member member) {
		try {
			// 获取登录会员信息
			Member loginMember = (Member) session.getAttribute(Const.LOGIN_Member);
			loginMember.setCardnum(member.getCardnum());
			loginMember.setRealname(member.getRealname());
			loginMember.setTel(member.getTel());
			memberService.updateBasicinfo(loginMember);
			// 记录流程步骤
			Ticket ticket = ticketService.getTicketByMemberId(loginMember.getId());
			ticket.setPstep("basicinfo");
			ticketService.updatePstep(ticket);
			return AjaxResult.success();
		} catch (Exception e) {
			e.printStackTrace();
			return AjaxResult.fail("上传基本信息失败");
		}
	}

	/**
	 * 上传图片
	 */
	@ResponseBody
	@RequestMapping("/doUploadCert")
	public Object doUploadCert(HttpSession session, Data data) throws IOException {
		try {
			// 获取登录会员信息
			Member loginMember = (Member) session.getAttribute(Const.LOGIN_Member);
			// 保存会员与资质关系数据
			// 资质文件上传
			List<MemberCert> certimgs = data.getCertimgs();
			String realPath = session.getServletContext().getRealPath("/pics");
			for (MemberCert certimg : certimgs) {
				MultipartFile fileImg = certimg.getFileImg();
				String extName = fileImg.getOriginalFilename().substring(fileImg.getOriginalFilename().lastIndexOf("."));
				String temname = UUID.randomUUID().toString() + extName;
				String filename = realPath + "/cert/" + temname;
				fileImg.transferTo(new File(filename)); // 上传文件
				certimg.setIconpath(temname); // 封装数据
				certimg.setMemberid(loginMember.getId());
			}

			// 保存会员与资质关系数据
			certService.saveMemberCert(certimgs);

			// 记录流程步骤
			Ticket ticket = ticketService.getTicketByMemberId(loginMember.getId());
			ticket.setPstep("uploadcert");
			ticketService.updatePstep(ticket);
			return AjaxResult.success();
		} catch (Exception e) {
			e.printStackTrace();
			return AjaxResult.fail("资料上传失败");
		}
	}

	/**
	 * @功能: 发送验证码
	 * @作者: 高志红
	 */
	@ResponseBody
	@RequestMapping("startProcess")
	public Object startProcess(HttpSession session, String email) {
		try {
			// 获取登录会员信息
			Member loginMember = (Member) session.getAttribute(Const.LOGIN_Member);
			// 如果用户输入新的邮箱，将旧的邮箱地址替换
			if (loginMember.getEmail().equals(email)) {
				loginMember.setEmail(email);
				memberService.updateEmail(loginMember);
			}
			// 启动实名认证流程 - 系统自动发送邮件，生成验证码，验证邮箱地址是否合法
			ProcessDefinition processDefinition = repositoryService
					.createProcessDefinitionQuery()
					.processDefinitionKey("auth")
					.singleResult();
			Random random = new Random();
			StringBuffer sb = new StringBuffer();
			for (int i = 1; i <= 4; i++) {
				int num = random.nextInt(10);
				sb.append(num);
			}
			Map<String, Object> val = new HashMap<String, Object>();
			val.put("toEmail", email);
			val.put("loginacct", loginMember.getLoginacct());
			val.put("authcode", sb.toString());
			val.put("passListener", new PassListener());
			val.put("refuseListener", new RefuseListener());
			ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), val);
			// 记录流程步骤
			Ticket ticket = ticketService.getTicketByMemberId(loginMember.getId());
			ticket.setPstep("checkemail");
			ticket.setPiid(processDefinition.getDeploymentId());
			ticket.setAuthcode(sb.toString());
			ticketService.updatePiidAndPstepAndAuthcode(ticket);
			return AjaxResult.success();
		} catch (Exception e) {
			e.printStackTrace();
			return AjaxResult.fail("验证码发送失败");
		}
	}

	/**
	 * @功能: 校验验证码
	 * @作者: 高志红
	 */
	@ResponseBody
	@RequestMapping("/finishApply")
	public Object finishApply(HttpSession session, String authcode) {
		System.out.println(authcode);
		// 获取登录会员信息
		Member loginMember = (Member) session.getAttribute(Const.LOGIN_Member);
		// 让当前系统用户完成验证码的审核任务
		Ticket ticket = ticketService.getTicketByMemberId(loginMember.getId());
		if (ticket.getAuthcode().equals(authcode)) {
			// 完成审核验证码的任务
			Task task = taskService.createTaskQuery()
					.processInstanceId(ticket.getPiid())
					.taskAssignee(loginMember.getLoginacct())
					.singleResult();
			System.out.println(ticket.getPiid());
			System.out.println(loginMember.getLoginacct());
			if (task == null) {
				return AjaxResult.fail("任务获取失败");
			}
			taskService.complete(task.getId());
			// 更新用户申请状态
			loginMember.setAuthstatus("1");
			memberService.updateAuthstatus(loginMember);
			// 记录流程步骤
			ticket.setPstep("finishApply");
			ticketService.updatePstep(ticket);
			return AjaxResult.success();
		} else {
			return AjaxResult.fail("验证码输入不正确，请重新输入");
		}
	}

	// 流程单
	@RequestMapping("/apply")
	public String apply(HttpSession session, Model model) {
		Member member = (Member) session.getAttribute(Const.LOGIN_Member);
		Ticket ticket = ticketService.getTicketByMemberId(member.getId());
		if (ticket == null) {
			ticket = new Ticket();
			ticket.setMemberid(member.getId());
			ticket.setPstep("apply");
			ticket.setStatus("0");
			ticketService.saveTicket(ticket);
		} else {
			String pstep = ticket.getPstep();
			if ("acttype".equals(pstep)) {
				return "member/basicinfo";
			} else if ("basicinfo".equals(pstep)) {
				// 根据当前用户查询账户类型，然后根据账户类型查找需要上传的资质
				List<Cert> certList = certService.queryCertByAccttype(member.getAccttype());
				model.addAttribute("certList", certList);
				return "member/uploadCert";
			} else if ("uploadcert".equals(pstep)) {
				return "member/checkemail";
			} else if ("checkemail".equals(pstep)) {
				return "member/checkauthcode";
			}
		}
		return "member/accttype";
	}
}
