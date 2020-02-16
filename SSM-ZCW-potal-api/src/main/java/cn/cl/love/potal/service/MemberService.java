package cn.cl.love.potal.service;

import cn.cl.love.bean.Member;

import java.util.List;
import java.util.Map;

/**
 * 标题：
 * 作者：何处是归程
 * 时间：2020/2/7 - 12:01
 */
public interface MemberService {
	// 会员登录
	Member queryMemberLogin(String loginacct, String userpswd);

	// 更新账户类型
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
