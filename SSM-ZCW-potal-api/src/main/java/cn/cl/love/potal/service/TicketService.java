package cn.cl.love.potal.service;


import cn.cl.love.bean.Member;
import cn.cl.love.bean.Ticket;

/**
 * 标题：
 * 作者：何处是归程
 * 时间：2020/2/7 - 19:29
 */
public abstract class TicketService {

	// 查询流程
	public abstract Ticket getTicketByMemberId(Integer id);

	// 保存流程
	public abstract void saveTicket(Ticket ticket);

	// 记录流程步骤
	public abstract void updatePstep(Ticket ticket);

	// 更新流程id验证码流程进度
	public abstract void updatePiidAndPstepAndAuthcode(Ticket ticket);

	// 根据流程ID查询会员
	public abstract Member getMemberByPiid(String processInstanceId);

	// 更新t_ticket表的status字段
	public abstract void updateStatus(Member memberById);
}
