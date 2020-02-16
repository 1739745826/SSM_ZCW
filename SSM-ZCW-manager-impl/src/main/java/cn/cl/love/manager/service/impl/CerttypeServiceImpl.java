package cn.cl.love.manager.service.impl;

import cn.cl.love.manager.dao.AccountTypeCertMapper;
import cn.cl.love.manager.service.CerttypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 标题：
 * 作者：何处是归程
 * 时间：2020/2/8 - 11:07
 */
@Service
public class CerttypeServiceImpl  implements CerttypeService {
	@Autowired
	private AccountTypeCertMapper accountTypeCertMapper;

	// 查询资质与账户类型之间的关系
	@Override
	public List<Map<String, Object>> queryCertAccttype() {
		List<Map<String, Object>> maps = accountTypeCertMapper.queryCertAccttype();
		return maps;
	}

	// 添加资质
	@Override
	public void insertAcctTypeCert(Map<String, Object> paramMap) {
		accountTypeCertMapper.insertAcctTypeCert(paramMap);
	}

	// 删除资质
	@Override
	public void deleteAcctTypeCert(Map<String, Object> paramMap) {
		accountTypeCertMapper.deleteAcctTypeCert(paramMap);
	}
}
