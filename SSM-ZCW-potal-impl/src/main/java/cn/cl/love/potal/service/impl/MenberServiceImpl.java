package cn.cl.love.potal.service.impl;

import cn.cl.love.bean.Member;
import cn.cl.love.potal.dao.MemberMapper;
import cn.cl.love.potal.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 标题：
 * 作者：何处是归程
 * 时间：2020/2/7 - 12:01
 */
@Service
public class MenberServiceImpl implements MemberService {
	@Autowired
	private MemberMapper memberMapper;

	// 会员登录
	@Override
	public Member queryMemberLogin(String loginacct, String userpswd) {
		return memberMapper.queryMemberLogin(loginacct, userpswd);
	}

	// 更新状态
	@Override
	public void updateAcctType(Member loginMember) {
		memberMapper.updateAcctType(loginMember);
	}

	// 上传基本信息
	@Override
	public void updateBasicinfo(Member loginMember) {
		memberMapper.updateBasicinfo(loginMember);
	}

	// 更新邮箱
	@Override
	public void updateEmail(Member loginMember) {
		memberMapper.updateEmail(loginMember);
	}

	// 更新用户状态
	@Override
	public void updateAuthstatus(Member loginMember) {
		memberMapper.updateAuthstatus(loginMember);
	}

	// 根据ID查询会员
	@Override
	public Member getMemberById(Integer memberId) {
		return memberMapper.getMemberById(memberId);
	}

	// 根据会员id查询资质图片信息
	@Override
	public List<Map<String, Object>> queryCertBymemberId(Integer memberId) {
		return memberMapper.queryCertBymemberId(memberId);
	}
}
