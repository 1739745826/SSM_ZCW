package cn.cl.love.potal.dao;


import cn.cl.love.bean.Member;
import cn.cl.love.bean.Ticket;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TicketMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(Ticket ticket);

	Ticket selectByPrimaryKey(Integer id);

	List<Ticket> selectAll();

	// 查询流程
	Ticket getTicketByMemberId(Integer memberid);

	// 记录流程步骤
	void updatePstep(Ticket ticket);

	// 更新流程id验证码流程进度
	void updatePiidAndPstepAndAuthcode(Ticket ticket);

	// 根据流程ID查询会员
	Member getMemberByPiid(@Param("piid") String processInstanceId);

	// 更新t_ticket表的status字段 0 ---> 1
	void updateStatus(Member memberById);
}