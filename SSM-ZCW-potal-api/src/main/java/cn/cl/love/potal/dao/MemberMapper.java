package cn.cl.love.potal.dao;

import cn.cl.love.bean.Member;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MemberMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(Member record);

	Member selectByPrimaryKey(Integer id);

	List<Member> selectAll();

	int updateByPrimaryKey(Member record);

	// 会员登录
	Member queryMemberLogin(@Param("loginacct") String loginacct, @Param("userpswd") String userpswd);

	// 更新状态
	void updateAcctType(Member loginMember);

	// 上传基本信息
	void updateBasicinfo(Member loginMember);

	// 更新邮箱
	void updateEmail(Member loginMember);

	// 更新用户状态
	void updateAuthstatus(Member loginMember);

	// 根据ID查询会员
	Member getMemberById(Integer memberId);

	// 根据会员id查询资质图片信息
	List<Map<String, Object>> queryCertBymemberId(Integer memberId);
}