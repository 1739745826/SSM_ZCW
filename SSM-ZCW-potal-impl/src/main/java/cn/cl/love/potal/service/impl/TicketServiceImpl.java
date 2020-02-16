package cn.cl.love.potal.service.impl;

import cn.cl.love.bean.Member;
import cn.cl.love.bean.Ticket;
import cn.cl.love.potal.dao.TicketMapper;
import cn.cl.love.potal.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 标题：
 * 作者：何处是归程
 * 时间：2020/2/7 - 19:30
 */
@Service
public class TicketServiceImpl extends TicketService {
	@Autowired
	TicketMapper ticketMapper;

	// 查询流程
	@Override
	public Ticket getTicketByMemberId(Integer id) {
		return ticketMapper.getTicketByMemberId(id);
	}

	// 保存流程
	@Override
	public void saveTicket(Ticket ticket) {
		ticketMapper.insert(ticket);
	}

	// 记录流程步骤
	@Override
	public void updatePstep(Ticket ticket) {
		ticketMapper.updatePstep(ticket);
	}

	// 更新流程id验证码流程进度
	@Override
	public void updatePiidAndPstepAndAuthcode(Ticket ticket) {
		ticketMapper.updatePiidAndPstepAndAuthcode(ticket);
	}

	// 根据流程ID查询会员
	@Override
	public Member getMemberByPiid(String processInstanceId) {
		return ticketMapper.getMemberByPiid(processInstanceId);
	}

	// 更新t_ticket表的status字段 0 ---> 1
	@Override
	public void updateStatus(Member memberById) {
		ticketMapper.updateStatus(memberById);
	}
}
