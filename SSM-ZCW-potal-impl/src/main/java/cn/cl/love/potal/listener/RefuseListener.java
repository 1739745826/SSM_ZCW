package cn.cl.love.potal.listener;

import cn.cl.love.bean.Member;
import cn.cl.love.potal.service.MemberService;
import cn.cl.love.potal.service.TicketService;
import cn.cl.love.util.ApplicationContextUtils;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.context.ApplicationContext;

//实名认证审核拒绝需要执行的操作
public class RefuseListener implements ExecutionListener {

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		// 获取IOC容器以及对象
		ApplicationContext applicationContext = ApplicationContextUtils.applicationContext;
		MemberService memberService = applicationContext.getBean(MemberService.class);
		TicketService ticketService = applicationContext.getBean(TicketService.class);

		// 更新t_member表的authstatus字段1 ---> 2（已实名认证）
		Integer memberid = (Integer) execution.getVariable("memberid");
		Member memberById = memberService.getMemberById(memberid);
		memberById.setAuthstatus("0");
		memberService.updateAuthstatus(memberById);

		// 更新t_ticket表的status字段 0 ---> 1
		ticketService.updateStatus(memberById);
	}
}
