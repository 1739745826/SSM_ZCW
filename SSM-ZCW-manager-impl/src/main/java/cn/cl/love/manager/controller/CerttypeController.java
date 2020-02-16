package cn.cl.love.manager.controller;

import cn.cl.love.bean.Cert;
import cn.cl.love.manager.service.CertService;
import cn.cl.love.manager.service.CerttypeService;
import cn.cl.love.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 标题：资质类型
 * 作者：何处是归程
 * 时间：2020/2/8 - 11:05
 */
@Controller
@RequestMapping("/certtype")
public class CerttypeController {
	@Autowired
	private CerttypeService certtypeService;
	@Autowired
	private CertService certService;


	/**
	 * @功能: 查询与回显
	 * @作者: 高志红
	*/
	@RequestMapping("/toIndex")
	public String toIndex(Model model) {
		// 查询所有的资质
		List<Cert> queryAllCert = certService.queryAllCert();
		model.addAttribute("queryAllCert", queryAllCert);
		// 查询资质与账户类型之间的关系（标识之前给庄户类型分配过的资质）
		List<Map<String, Object>> list = certtypeService.queryCertAccttype();
		model.addAttribute("certaccttype", list);
		return "certtype/index";
	}


	/**
	 * @功能: 添加资质
	 * @作者: 高志红
	 */
	@ResponseBody
	@RequestMapping("/insertAcctTypeCert")
	public Object insertAcctTypeCert(Integer certid, String accttype) {
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("certid", certid);
			paramMap.put("accttype", accttype);
			certtypeService.insertAcctTypeCert(paramMap);
			return AjaxResult.success();
		} catch (Exception e) {
			e.printStackTrace();
			return AjaxResult.fail("添加资质失败");
		}
	}

	/**
	 * @功能: 删除资质
	 * @作者: 高志红
	 */
	@ResponseBody
	@RequestMapping("/deleteAcctTypeCert")
	public Object deleteAcctTypeCert(Integer certid, String accttype) {

		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("certid", certid);
			paramMap.put("accttype", accttype);
			certtypeService.deleteAcctTypeCert(paramMap);
			return AjaxResult.success();
		} catch (Exception e) {
			e.printStackTrace();
			return AjaxResult.fail("删除资质失败");
		}
	}
}
