package cn.cl.love.manager.dao;

import java.util.List;
import java.util.Map;

import cn.cl.love.VO.Data;
import cn.cl.love.bean.Cert;
import cn.cl.love.bean.MemberCert;

public interface CertMapper {

	Cert queryCert(Map<String, Object> paramMap);

	List<Cert> pageQuery(Map<String, Object> paramMap);

	int queryCount(Map<String, Object> paramMap);

	void insertCert(Cert cert);

	Cert queryById(Integer id);

	int updateCert(Cert cert);

	int deleteCert(Integer id);

	int deleteCerts(Data ds);

	List<Cert> queryCertByAccttype(String accttype);

	List<Cert> queryAllCert();

	List<Map<String, Object>> queryAllAccttypeCert();

	int insertAccttypeCert(Map<String, Object> map);

	int deleteAccttypeCert(Map<String, Object> map);

	void insertMemberCert(MemberCert memberCert);
}
