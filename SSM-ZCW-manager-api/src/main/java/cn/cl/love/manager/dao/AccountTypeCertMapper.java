package cn.cl.love.manager.dao;

import cn.cl.love.bean.AccountTypeCert;
import java.util.List;
import java.util.Map;

public interface AccountTypeCertMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccountTypeCert record);

    AccountTypeCert selectByPrimaryKey(Integer id);

    List<AccountTypeCert> selectAll();

    int updateByPrimaryKey(AccountTypeCert record);

    // 查询资质与账户类型之间的关系
	List<Map<String, Object>> queryCertAccttype();

	// 添加资质
	void insertAcctTypeCert(Map<String, Object> paramMap);

	// 删除资质
	void deleteAcctTypeCert(Map<String, Object> paramMap);
}