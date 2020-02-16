package cn.cl.love.manager.service;

import java.util.List;
import java.util.Map;

/**
 * 标题：
 * 作者：何处是归程
 * 时间：2020/2/8 - 11:07
 */
public interface CerttypeService {
	// 查询资质与账户类型之间的关系
	List<Map<String, Object>> queryCertAccttype();

	// 添加资质
	void insertAcctTypeCert(Map<String, Object> paramMap);

	// 删除资质
	void deleteAcctTypeCert(Map<String, Object> paramMap);
}
