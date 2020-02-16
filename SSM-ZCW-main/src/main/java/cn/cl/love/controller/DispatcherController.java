package cn.cl.love.controller;

import cn.cl.love.bean.Member;
import cn.cl.love.bean.Permission;
import cn.cl.love.bean.User;
import cn.cl.love.manager.service.UserService;
import cn.cl.love.potal.service.MemberService;
import cn.cl.love.util.AjaxResult;
import cn.cl.love.util.CheckCode;
import cn.cl.love.util.Const;
import cn.cl.love.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

/**
 * 标题：
 * 作者：何处是归程
 * 时间：2020/2/7 - 9:22
 */
@Controller
public class DispatcherController {
	@Autowired
	private UserService userService;
	@Autowired
	private MemberService memberService;

	@RequestMapping("/index")
	public String index() {
		return "index";
	}

	@RequestMapping("/member")
	public String member() {
		return "member/member";
	}

	@RequestMapping("/login")
	public String login(HttpServletRequest request, HttpSession session) {
		//判断是否需要自动登录
		//如果之前登录过，cookie中存放了用户信息，需要获取cookie中的信息，并进行数据库的验证
		boolean needLogin = true;
		String logintype = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) { //如果客户端禁用了Cookie，那么无法获取Cookie信息

			for (Cookie cookie : cookies) {
				if ("logincode".equals(cookie.getName())) {
					String logincode = cookie.getValue();
					System.out.println("获取到Cookie中的键值对" + cookie.getName() + "===== " + logincode);
					//loginacct=admin&userpwd=21232f297a57a5a743894a0e4a801fc3&logintype=1
					String[] split = logincode.split("&");
					if (split.length == 3) {
						String loginacct = split[0].split("=")[1];
						String userpwd = split[1].split("=")[1];
						logintype = split[2].split("=")[1];

						if ("user".equals(logintype)) {
							Map<String, Object> paramMap = new HashMap<String, Object>();
							paramMap.put("loginacct", loginacct);
							paramMap.put("userpswd", userpwd);
							paramMap.put("type", logintype);
							User user = userService.login(paramMap);

							if (user != null) {
								session.setAttribute(Const.LOGIN_USER, user);
								needLogin = false;
							}
						} else if ("member".equals(logintype)) {
							Member member = memberService.queryMemberLogin(loginacct, userpwd);
							if (member != null) {
								session.setAttribute(Const.LOGIN_Member, member);
								needLogin = false;
							}
						}
					}
				}
			}
		}
		if (needLogin) {
			return "login";
		} else {
			if ("user".equals(logintype)) {
				return "redirect:/main.htm";
			} else if ("member".equals(logintype)) {
				return "redirect:/member.htm";
			}
		}
		return "login";
	}

	@RequestMapping("main")
	public String main() {
		return "main";
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate(); //销毁session对象,或清理session域.
		return "index";
	}

	/**
	 * @功能: 异步请求
	 * @作者: 高志红
	 */
	@ResponseBody // 结合JackSon组件， 将返回结果转化为字符串，将JSON串以流的形式返回给客户端
	@RequestMapping("/doLogin")
	public Object doLogin(String loginacct, String userpswd, Integer remember_me, String type, String checkcode_input, HttpSession session, HttpServletResponse response) {
		// 使用MD5加密
		userpswd = MD5Util.digest(userpswd);
		String checkcode = (String) session.getAttribute("check_code"); // 随机生成的验证码
		if (!checkcode.equalsIgnoreCase(checkcode_input)) {
			return AjaxResult.fail("验证码有误");
		}
		try {
			if ("user".equals(type)) {
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("loginacct", loginacct);
				paramMap.put("userpswd", userpswd);
				paramMap.put("type", type);
				User user = userService.login(paramMap);
				session.setAttribute(Const.LOGIN_USER, user);
				// ——————————————————————————————————————————
				// 加载当前登录用户的所有许可权限
				List<Permission> myPermission = userService.getPermissionByUserId(user.getId());
				Permission permissionRoot = null;
				// 存放用户能访问的所有路径的集合
				Set<String> myUris = new HashSet<String>();
				Map<Integer, Permission> map = new HashMap<Integer, Permission>();
				for (Permission innerPermission : myPermission) {
					map.put(innerPermission.getId(), innerPermission);
					// 将用户能访问的所有路径存入集合
					myUris.add("/" + innerPermission.getUrl());
				}
				// 将用户能访问的集合存入session域
				session.setAttribute(Const.MY_URIS, myUris);
				for (Permission permission : myPermission) {
					Permission child = permission;
					if (child.getPid() == null) {
						permissionRoot = permission;
					} else {
						// 父节点
						Permission parent = map.get(child.getPid());
						parent.getChildren().add(child);
					}
				}
				session.setAttribute("permissionRoot", permissionRoot);
				// ——————————————————————————————————————————
				// 判断是否记住我
				if (remember_me == 1) {
					String logincode = "\"loginacct=" + user.getLoginacct() + "&userpwd=" + user.getUserpswd() + "&logintype=user\"";
					//loginacct=admin&userpwd=21232f297a57a5a743894a0e4a801fc3&logintype=1
					System.out.println("用户-存放到Cookie中的键值对：logincode::::::::::::" + logincode);
					Cookie c = new Cookie("logincode", logincode);
					c.setMaxAge(60 * 60 * 24 * 14); //2周时间Cookie过期     单位秒
					c.setPath("/"); //表示任何请求路径都可以访问Cookie
					response.addCookie(c);
				}
			} else if ("member".equals(type)) {
				Member member = memberService.queryMemberLogin(loginacct, userpswd);
				session.setAttribute(Const.LOGIN_Member, member);
				// 判断是否记住我
				if (remember_me == 1) {
					String logincode = "\"loginacct=" + member.getLoginacct() + "&userpwd=" + member.getUserpswd() + "&logintype=member\"";
					//loginacct=admin&userpwd=21232f297a57a5a743894a0e4a801fc3&logintype=1
					System.out.println("用户-存放到Cookie中的键值对：logincode::::::::::::" + logincode);
					Cookie c = new Cookie("logincode", logincode);
					c.setMaxAge(60 * 60 * 24 * 14); //2周时间Cookie过期     单位秒
					c.setPath("/"); //表示任何请求路径都可以访问Cookie
					response.addCookie(c);
				}
			} else {
				return AjaxResult.fail("登录类型有误");
			}
			return AjaxResult.success("type", type);
		} catch (Exception e) {
			e.printStackTrace();
			return AjaxResult.fail("用户名密码有误");
		}
	}


	/**
	 * @功能: 注册
	 * @作者: 高志红
	 */
	@ResponseBody
	@RequestMapping("/regUser")
	public Object rag(@Valid User user, BindingResult bindingResult, String checkcode_input, HttpSession session) {
		AjaxResult result = new AjaxResult();
		try {
			// 加密密码
			user.setUserpswd(MD5Util.digest(user.getUserpswd()));
			// 校验验证码
			String checkcode = (String) session.getAttribute("check_code"); // 随机生成的验证码
			if (!checkcode.equalsIgnoreCase(checkcode_input)) {
				result.setSuccess(false);
				result.setMessage("验证码有误");
				return result;
			}
			// 校验用户的各个字段
			if (bindingResult.hasErrors()) {
				// 校验失败
				List<FieldError> errors = bindingResult.getFieldErrors();
				result.setSuccess(false);
				result.setMessage(errors.get(0).getDefaultMessage());
				return result;
			} else {
				result.setSuccess(true);
				userService.insertUser(user);
				return result;
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("注册失败");
			e.printStackTrace();
			return result;
		}
	}


	@RequestMapping(value = "/checkCode")
	public void checkCode(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//设置相应类型,告诉浏览器输出的内容为图片
		response.setContentType("image/jpeg");

		//设置响应头信息，告诉浏览器不要缓存此内容
		response.setHeader("pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expire", 0);

		CheckCode checkCode = new CheckCode();
		try {
			checkCode.getRandcode(request, response);//输出图片方法
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
