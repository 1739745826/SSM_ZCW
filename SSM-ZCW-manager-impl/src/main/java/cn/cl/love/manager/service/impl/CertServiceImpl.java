package cn.cl.love.manager.service.impl;

import java.util.List;
import java.util.Map;

import cn.cl.love.VO.Data;
import cn.cl.love.bean.Cert;
import cn.cl.love.bean.MemberCert;
import cn.cl.love.manager.dao.CertMapper;
import cn.cl.love.manager.service.CertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CertServiceImpl implements CertService {

	@Autowired
	private CertMapper certDao;

	public Cert queryCert(Map<String, Object> paramMap) {
		return certDao.queryCert(paramMap);
	}

	public int queryCount(Map<String, Object> paramMap) {
		return certDao.queryCount(paramMap);
	}

	public void insertCert(Cert cert) {
		certDao.insertCert(cert);
	}

	public Cert queryById(Integer id) {
		return certDao.queryById(id);
	}

	public int updateCert(Cert cert) {
		return certDao.updateCert(cert);
	}

	public int deleteCert(Integer id) {
		return certDao.deleteCert(id);
	}

	public int deleteCerts(Data ds) {
		return certDao.deleteCerts(ds);
	}

	public List<Cert> queryCertByAccttype(String accttype) {
		return certDao.queryCertByAccttype(accttype);
	}

	@Override
	public List<Cert> queryAllCert() {
		return certDao.queryAllCert();
	}

	@Override
	public List<Map<String, Object>> queryAllAccttypeCert() {
		return certDao.queryAllAccttypeCert();
	}

	@Override
	public int insertAccttypeCert(Map<String, Object> map) {
		return certDao.insertAccttypeCert(map);
	}

	@Override
	public int deleteAccttypeCert(Map<String, Object> map) {
		return certDao.deleteAccttypeCert(map);
	}

	@Override
	public void saveMemberCert(List<MemberCert> certimgs) {
		for (MemberCert memberCert : certimgs) {
			System.out.println(memberCert);
			certDao.insertMemberCert(memberCert);
		}
	}
}
